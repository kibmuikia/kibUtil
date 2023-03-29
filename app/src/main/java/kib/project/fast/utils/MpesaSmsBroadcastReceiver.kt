package kib.project.fast.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import timber.log.Timber

class MpesaSmsBroadcastReceiver : BroadcastReceiver() {
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

                    if (messageBody.isMpesaMessage()) {
                        mpesaSmsReceiveListener?.onMpesaSmsReceived(mpesaSms = messageBody)
                    }
                }
            }
        }
    }
}