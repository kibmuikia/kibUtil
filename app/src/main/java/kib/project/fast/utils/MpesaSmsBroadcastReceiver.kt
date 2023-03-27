package kib.project.fast.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import timber.log.Timber

class MpesaSmsBroadcastReceiver: BroadcastReceiver() {
    private var mpesaSmsReceiveListener: MpesaSmsReceiveListener? = null

    interface MpesaSmsReceiveListener {
        fun onMpesaSmsReceived(mpesaSms: String)
    }

    fun init(mpesaSmsReceiveListener: MpesaSmsReceiveListener) {
        this.mpesaSmsReceiveListener = mpesaSmsReceiveListener
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when {
            context == null -> {
                Timber.i(":: invalid context")
                return
            }
            intent == null -> {
                Timber.i(":: invalid intent")
                return
            }
            intent.action == null -> {
                Timber.i(":: invalid intent.action")
                return
            }
            else -> processIntent(intent = intent)
        }
    }

    private fun processIntent(intent: Intent) {
        intent.action?.let { action ->
            if (action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
                val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
                Timber.i(":: received ${smsMessages.size} smsMessages")
                for (sms in smsMessages) {
                    val messageBody = sms.messageBody
                    Timber.i(":: sms: $sms -- \n$messageBody")

                    validateTextMessage(message = messageBody).let {isValid ->
                        Timber.i(":: validateTextMessage-let-isValid: $isValid")
                        if (isValid) {
                            mpesaSmsReceiveListener?.onMpesaSmsReceived(mpesaSms = messageBody)
                        }
                    }
                }
            }
        }
    }
}

fun validateTextMessage(message: String): Boolean {
    /*
    * This function validates if the text message is a M-Pesa transaction message.
    * Sample sms:
    *   RB0719U7GI Confirmed. Ksh70.00 sent to PERSON  DOE 0700112233 on 1/2/23 at 5:24 PM. New M-PESA balance is Ksh0.00. Transaction cost, Ksh0.00. Amount you can transact within the day is 299,930.00. Get a loan today from M-Shwari click https://mpesaapp.page.link/mshwari
    * */
    val regex = Regex("""([A-Z\d]+) .* (\d{10}) .* (M-PESA)""")

    val matchResult = regex.find(message)

    val code = matchResult?.groups?.get(1)?.value
    val phoneNumber = matchResult?.groups?.get(2)?.value
    val mpesa = matchResult?.groups?.get(3)?.value
    Timber.i(":: code[ $code ], phoneNumber[ $phoneNumber ], mpesa[ $mpesa ]")

    return !(code.isNullOrBlank() || phoneNumber.isNullOrBlank() || mpesa.isNullOrBlank())
}