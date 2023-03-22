package kib.project.fast.ui.component

import android.annotation.SuppressLint
import android.provider.Telephony
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@SuppressLint("Range")
@Composable
fun ReadSmsMessages() {
    val context = LocalContext.current
    val contentResolver = context.contentResolver
    val uri = Telephony.Sms.Inbox.CONTENT_URI
    val cursor = contentResolver.query(
        uri,
        arrayOf(
            Telephony.Sms.Inbox.ADDRESS,
            Telephony.Sms.Inbox.BODY,
            Telephony.Sms.Inbox.DATE,
            Telephony.Sms.Inbox.PERSON,
            Telephony.Sms.Inbox.THREAD_ID,
            Telephony.Sms.Inbox.PROTOCOL,
            Telephony.Sms.Inbox.SERVICE_CENTER,
            Telephony.Sms.Inbox.STATUS,
            Telephony.Sms.Inbox.TYPE
        ),
        null,
        null,
        null
    )

    if (cursor != null && cursor.moveToFirst()) {
        do {
            val address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.ADDRESS))
            val body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.BODY))
            val date = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.DATE))
            // process the SMS message data here
        } while (cursor.moveToNext())
        cursor.close()
    }

}