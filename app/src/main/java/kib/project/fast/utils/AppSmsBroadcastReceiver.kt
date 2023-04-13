package kib.project.fast.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import timber.log.Timber

class AppSmsBroadcastReceiver: BroadcastReceiver() {
    private var appSmsReceiveListener: AppSmsReceiveListener? = null

    fun init(appSmsReceiveListener: AppSmsReceiveListener?) {
        this.appSmsReceiveListener = appSmsReceiveListener
    }

    interface AppSmsReceiveListener {
        fun onOTPReceived(otp: String?)
        fun onOTPTimeOut()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        if (intent == null) return
        if (intent.action.isNullOrBlank()) return

        Timber.i(":: intent.action: ${intent.action}")
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras: Bundle? = intent.extras
            val status: Status = extras?.get(SmsRetriever.EXTRA_STATUS) as Status
            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    try {
                        // Get SMS message contents
                        val messageContents: String = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String
                        Timber.i(":: messageContents: \n** $messageContents \n**")

                        // extract the 6-digit code from the SMS
                        val smsCode = messageContents.let { "[0-9]{6}".toRegex().find(it) }
                        Timber.i(":: smsCode: \n** $smsCode **")

                        smsCode?.value?.let { appSmsReceiveListener?.onOTPReceived(otp = it) }
                    } catch (exception: Exception) {
                        Timber.i(":: error: ${exception.localizedMessage}")
                        Timber.e(exception)
                    }
                }
                CommonStatusCodes.TIMEOUT -> {
                    appSmsReceiveListener?.onOTPTimeOut()
                }
            }
        }
    }
}

fun startAppSMSRetrieverClient(context: Context) {
    /*
    * Note: SMS Retrieval client will be alive for 5min maximum as per google auth SDK.
    * */
    val client: SmsRetrieverClient = SmsRetriever.getClient(context)
    val task = client.startSmsRetriever()
    task.addOnSuccessListener { _ ->
        Timber.i(":: startAppSMSRetrieverClient: addOnSuccessListener")
    }
    task.addOnFailureListener { e ->
        Timber.i(":: startAppSMSRetrieverClient: addOnFailureListener: ${e.localizedMessage}")
        Timber.e(e)
    }
}