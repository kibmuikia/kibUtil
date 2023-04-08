package kib.project.fast.utils

import timber.log.Timber

fun String.isMpesa(): Boolean {
    val isMpesa = this == "MPESA"
    Timber.d(":: isMpesa: String = $this, result: $isMpesa")
    return isMpesa
}

fun String.isMpesaMessage(): Boolean = try {
    /*
    * This function validates if the text message is a M-Pesa transaction message.
    * Sample sms:
    *   RB0719U7GI Confirmed. Ksh70.00 sent to PERSON  DOE 0700112233 on 1/2/23 at 5:24 PM. New M-PESA balance is Ksh0.00. Transaction cost, Ksh0.00. Amount you can transact within the day is 299,930.00. Get a loan today from M-Shwari click https://mpesaapp.page.link/mshwari
    * */
    val regex = Regex("""([A-Z\d]+) .* (\d{10}) .* (M-PESA)""")

    val matchResult = regex.find(this)

    val code = matchResult?.groups?.get(1)?.value
    val phoneNumber = matchResult?.groups?.get(2)?.value
    val mpesa = matchResult?.groups?.get(3)?.value
    val isMpesaMessage =
        !(code.isNullOrBlank() || phoneNumber.isNullOrBlank() || mpesa.isNullOrBlank())
    Timber.i(":: isMpesaMessage[ $isMpesaMessage ], code[ $code ], phoneNumber[ $phoneNumber ], mpesa[ $mpesa ]")

    isMpesaMessage
} catch (e: Exception) {
    Timber.e(e, e.localizedMessage)
    false
}

fun String.hasNewMPESABalance(): Boolean {
    val pattern = Regex("New M-PESA balance is Ksh\\d{1,3}(,\\d{3})*(\\.\\d{2})")
    return pattern.containsMatchIn(this)
}

fun String.removeNewMPESABalance(): String {
    val pattern = Regex("""\sNew M-PESA balance is Ksh\d{1,3}(,\d{3})*(\.\d{1,2})?\.""")
    return pattern.replace(this, "")
}

fun String.replaceMPESABalance(): String {
    /*
    * To prevent a user's original M-Pesa balance being sent to the backend.
    * It replaces it with a constant value of 0.00
    * */
    val pattern = Regex("(?<=\\s)New\\sM-PESA\\sbalance\\sis\\sKsh[\\d,]+(\\.\\d{1,2})?\\.")
    val newBalance = "New M-PESA balance is Ksh0.00."
    return pattern.replace(this, newBalance)
        .replace(Regex("(?<=Ksh)\\s+"), "") // remove spaces between Ksh and currency value
}

