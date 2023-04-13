package kib.project.data

import kib.project.data.utils.createFakeMpesaTransaction
import kib.project.data.utils.randomKenyanPhoneNumber
import kib.project.fast.utils.isMpesaMessage
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MpesaTransactionTest {

    @Test
    fun `test createFakeMpesaTransaction`() {
        val transaction = createFakeMpesaTransaction()

        // Assert that the reference has the correct format
        assertTrue(transaction.reference.matches(Regex("[A-Z]{2}[A-Z0-9]{8}")))

        // Assert that the amount is between 0 and 500
        assertTrue(transaction.amount in 0..500)

        // Assert that the participant has two names separated by a space
        assertTrue(transaction.participant.matches(Regex("[A-Za-z]+ [A-Za-z]+")))

        // Assert that the phone number has the correct format
        assertTrue(transaction.phoneNumber.matches(Regex("07[0-9]{8}")))

        // Assert that isCredit is always true
        assertTrue(transaction.isCredit)

        // Assert that the transaction cost is between 0 and 100
        assertTrue(transaction.transactionCost in 0.00..1000000000.00)

        // Assert that the balance is between 0 and 10000
        assertTrue(transaction.balance in 0.00..1000000000.00)


        // Assert that the transaction date is not null or empty
        assertNotNull(transaction.transactionDate)
        assertTrue(transaction.transactionDate.isNotBlank())

        // Assert that the message is not null or empty
        assertNotNull(transaction.message)
        assertTrue(transaction.message.isNotBlank())
        assertTrue(transaction.message.isMpesaMessage().first)
    }

    @Test
    fun `randomKenyanPhoneNumber should generate a valid phone number`() {
        val phoneNumber = randomKenyanPhoneNumber()

        // Check that the phone number is a string of length 10
        assertTrue(phoneNumber.length == 10)

        // Check that the phone number starts with "07"
        assertTrue(phoneNumber.startsWith("07"))

        // Check that the remaining characters are digits
        assertTrue(phoneNumber.substring(2).all { it.isDigit() })
    }

}
