package kib.project.fast.ui.component

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Telephony
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kib.project.data.database.entities.textMessage.AppSmsMessageUiModel
import kib.project.fast.R
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ReadSmsMessages(
    messages: (List<AppSmsMessageUiModel>) -> Unit = {},
) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(permission = android.Manifest.permission.READ_SMS)
    val isPermissionGranted =
        permissionState.status.isGranted && !permissionState.status.shouldShowRationale

    if (!isPermissionGranted) {
        SinglePermission(
            modifier = Modifier,
            permission = android.Manifest.permission.READ_SMS,
            actionPermissionGranted = {})
    } else {
        processTextMessages(context).let { messages ->
            Timber.i(":: retrieved ${messages.size} messages.")
            showMessages(messages = messages, context = context, modifier = Modifier)
            messages(messages)
        }
    }

}

@SuppressLint("Range")
private fun processTextMessages(context: Context): List<AppSmsMessageUiModel> {
    val messages: MutableList<AppSmsMessageUiModel> = mutableListOf()

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
            val person = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.PERSON))
            val threadId = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.THREAD_ID))
            val protocol = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.PROTOCOL))
            val serviceCenter =
                cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.SERVICE_CENTER))
            val status = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.STATUS))
            val type = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.TYPE))

            val sms = AppSmsMessageUiModel(
                address = address,
                body = body,
                date = date,
                person = person,
                threadId = threadId,
                protocol = protocol,
                serviceCenter = serviceCenter,
                status = status,
                type = type
            )
            Timber.i(":: sms: \n$sms **")
            /*
            * Sample:
            * AppSmsMessageUiModel(address=6505551212, body=Android is always a sweet treat!, date=1679454058177, person=null, threadId=2, protocol=0, serviceCenter=null, status=-1, type=1)
            * */
            messages.add(sms)

            // process the SMS message data here

        } while (cursor.moveToNext())
        cursor.close()
    }

    return messages
}

@Composable
private fun showMessages(
    messages: List<AppSmsMessageUiModel>,
    context: Context,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = stringResource(id = R.string.title_messages),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
        itemsIndexed(items = messages) { index, item ->
            Text(
                text = "$index : $item",
                modifier = modifier.padding(12.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}