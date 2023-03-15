package kib.project.fast.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import timber.log.Timber

class AppAllSmsBroadcastReceiver : BroadcastReceiver() {
    private var appAllSmsReceiveListener: AppAllSmsReceiveListener? = null

    interface AppAllSmsReceiveListener {
        fun onTextMessagesReceived(lisOfTextMessages: Array<SmsMessage> = emptyArray())
        fun onTextMessageTimeOut()
    }

    fun init(appSmsReceiveListener: AppAllSmsReceiveListener?) {
        this.appAllSmsReceiveListener = appSmsReceiveListener
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        if (intent == null) return
        if (intent.action.isNullOrBlank()) return
        Timber.i(":: intent.action[ ${intent.action} ]")

        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            Telephony.Sms.Intents.getMessagesFromIntent(intent)?.let { gotTextMessages: Array<SmsMessage> ->
                gotTextMessages.forEach { smsMessage: SmsMessage ->
                    Timber.i(":: smsMessage:\n[ " +
                            "status: ${smsMessage.status} " +
                            "displayOriginatingAddress: ${smsMessage.displayOriginatingAddress}, " +
                            "displayMessage: ${smsMessage.displayMessageBody}, " +
                            "messageBody: ${smsMessage.messageBody} " +
                            "timestampMillis: ${smsMessage.timestampMillis} " +
                            "]")
                }
                appAllSmsReceiveListener?.onTextMessagesReceived(lisOfTextMessages = gotTextMessages)
            }

        }
    }

}