package kib.project.fast.utils

import timber.log.Timber

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