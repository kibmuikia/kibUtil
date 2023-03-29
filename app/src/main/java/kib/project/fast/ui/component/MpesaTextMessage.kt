@file:OptIn(ExperimentalPermissionsApi::class)

package kib.project.fast.ui.component

import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kib.project.fast.ui.component.viewmodels.MpesaTextMessageViewModel
import kib.project.fast.utils.ACTION_SMS_RECEIVE
import kib.project.fast.utils.PERMISSION_RECEIVE_SMS
import kib.project.fast.utils.isMpesaMessage
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

@Composable
fun MpesaTextMessage(
    sms: (String) -> Unit = {},
    context: Context,
) {
    val viewModel: MpesaTextMessageViewModel = getViewModel()
    val uiState = viewModel.uiState.collectAsState()
    val permissionState = rememberPermissionState(permission = PERMISSION_RECEIVE_SMS)
    val isPermissionGranted =
        permissionState.status.isGranted && !permissionState.status.shouldShowRationale
    viewModel.setSmsPermissionState(state = isPermissionGranted)

    if (!isPermissionGranted) {
        SinglePermission(
            modifier = Modifier,
            permission = PERMISSION_RECEIVE_SMS,
            actionPermissionGranted = {
                viewModel.setSmsPermissionState(state = true)
            })
    } else {
        SystemBroadcastReceiver(
            context = context,
            systemAction = ACTION_SMS_RECEIVE,
            onSystemEvent = { intent: Intent? ->
                intent?.let { gotIntent ->
                    gotIntent.action?.let { action ->
                        if (action == ACTION_SMS_RECEIVE) {
                            processIntentAndFetchTextMessages(intent = intent).let { triple ->
                                if (triple == null) {
                                    Toast.makeText(
                                        context,
                                        "Received sms is NOT a M-Pesa transaction sms.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                triple?.let {
                                    viewModel.setMpesaMessage(it.second)
                                    sms(it.second)
                                }
                            }
                        }
                    }
                }
            }
        )
        MessageCard(message = uiState.value.mpesaMessage)
    }
}

@Composable
private fun MessageCard(message: String) {
    Card(
        modifier = Modifier.padding(12.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Mpesa Message",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = message.ifEmpty { "No Mpesa message received yet!" },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )

            TextButton(
                onClick = { /* Handle button click */ },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(12.dp)
            ) {
                Text(
                    text = if (message.isEmpty()) {
                        "Hmmm!"
                    } else {
                        "Post Message"
                    }
                )
            }
        }
    }
}

@Composable
@Preview
private fun MessageCardPreview() {
    MessageCard(message = "")
}

private fun processIntentAndFetchTextMessages(intent: Intent): Triple<String?, String, Long>? {
    try {
        val pduType = "pdus"
        val bundle = intent.extras
        val pduArray = bundle?.get(pduType) as Array<*>?
        val format = bundle?.getString("format")
        val messages = mutableListOf<Triple<String, String, Long>>()

        var sender: String? = null
        var smsBody = ""
        var timestampMillis = 0L

        pduArray?.forEachIndexed { _, pdu ->
            val sms = SmsMessage.createFromPdu(pdu as ByteArray, format)
            sender = sms.originatingAddress
            smsBody += sms.messageBody
            timestampMillis = sms.timestampMillis
            messages.add(
                Triple(
                    first = sender.orEmpty(),
                    second = smsBody,
                    third = timestampMillis
                )
            )
        }

        Timber.i(":: Received SMS from $sender: $smsBody (timestamp: $timestampMillis)")

        return if (smsBody.isMpesaMessage()) {
            Triple(first = sender, second = smsBody, third = timestampMillis)
        } else {
            null
        }
    } catch (e: Exception) {
        Timber.e(e)
        return null
    }
}