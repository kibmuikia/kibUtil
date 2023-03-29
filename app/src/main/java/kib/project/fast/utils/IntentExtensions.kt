package kib.project.fast.utils

import android.content.Intent
import android.telephony.SmsMessage
import timber.log.Timber

fun Intent.fetchTextMessage(): Triple<String?, String, Long>? = try {
    val pduType = "pdus"
    val bundle = this.extras
    val pduArray = bundle?.get(pduType) as Array<*>?
    val format = bundle?.getString("format")

    var sender: String? = null
    var smsBody = ""
    var timestampMillis = 0L

    pduArray?.forEachIndexed { _, pdu ->
        val sms = SmsMessage.createFromPdu(pdu as ByteArray, format)
        sender = sms.originatingAddress
        smsBody += sms.messageBody
        timestampMillis = sms.timestampMillis
    }

    Timber.i(":: Received SMS from $sender: $smsBody (timestamp: $timestampMillis)")

    if (smsBody.isMpesaMessage()) {
        Triple(first = sender, second = smsBody, third = timestampMillis)
    } else {
        null
    }
} catch (e: Exception) {
    Timber.e(e, e.localizedMessage)
    null
}