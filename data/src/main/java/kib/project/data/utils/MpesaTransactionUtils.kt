package kib.project.data.utils

import kib.project.data.database.entities.textMessage.MpesaTransaction
import timber.log.Timber
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random

fun randomKenyanPhoneNumber(): String {
    val randomNumber = (10000000..99999999).random()
    return "07$randomNumber"
}

private val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
private val DECIMAL_FORMAT = DecimalFormat("#0.00")
fun createFakeMpesaTransaction(): MpesaTransaction {
    val random = Random()

    val reference =
        buildString { // Create a new StringBuilder and assign it to the 'reference' variable
            for (i in 0 until 10) { // Loop 10 times (i.e., create a string of length 10)
                append( // Add a character to the StringBuilder
                    if (i < 2) { // If it's the first or second character
                        ALPHABET.filter { it.isLetter() }.random()
                            .toString() // Choose a random uppercase letter and add it to the StringBuilder
                    } else { // Otherwise
                        if (random.nextBoolean()) { // With 50% probability
                            ALPHABET.filter { it.isLetter() }.random()
                                .toString() // Choose a random uppercase letter and add it to the StringBuilder
                        } else { // Otherwise
                            random.nextInt(10)
                                .toString() // Choose a random digit and add it to the StringBuilder
                        }
                    }
                )
            }
        }

    val amount = random.nextInt(500)
    val firstName = listOf(
        "John",
        "Mary",
        "William",
        "Elizabeth",
        "James",
        "Sarah",
        "Thomas",
        "Margaret",
        "Joseph",
        "Caroline",
        "Brian",
        "Faith"
    ).random()
    val lastName = listOf(
        "Smith",
        "Johnson",
        "Brown",
        "Taylor",
        "Jones",
        "Wilson",
        "Anderson",
        "Williams",
        "Mwangi",
        "Kimani",
        "Wanjiru",
        "Muthoni"
    ).random()
    val participant = "$firstName $lastName"
    val phoneNumber = randomKenyanPhoneNumber()
    val isCredit = true
    val transactionCost = DECIMAL_FORMAT.format(random.nextDouble() * 10000).toDouble()
    val balance = DECIMAL_FORMAT.format(random.nextDouble() * 10000).toDouble()
    val transactionDate =
        SimpleDateFormat("d/M/yy HH:mm", Locale.getDefault()).format(Date().time)
    val message =
        "$reference Confirmed. You have received Ksh${amount}.00 from $participant $phoneNumber on ${transactionDate}. New M-PESA balance is Ksh${balance}. Use Lipa Na M-PESA Buy Goods option to pay for shopping at NO COST."

    Timber.d(":: Generated fake message: \n$message\n.")

    return MpesaTransaction(
        reference = reference,
        amount = amount,
        participant = participant,
        phoneNumber = phoneNumber,
        isCredit = isCredit,
        transactionCost = transactionCost,
        balance = balance,
        transactionDate = transactionDate,
        message = message
    )
}
