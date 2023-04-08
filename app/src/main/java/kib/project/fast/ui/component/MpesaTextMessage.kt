@file:OptIn(ExperimentalPermissionsApi::class)

package kib.project.fast.ui.component

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kib.project.data.api.models.requests.PostSmsRequest
import kib.project.data.database.entities.textMessage.MpesaSms
import kib.project.fast.R
import kib.project.fast.ui.component.viewmodels.MpesaTextMessageViewModel
import kib.project.fast.utils.ACTION_SMS_RECEIVE
import kib.project.fast.utils.PERMISSION_RECEIVE_SMS
import kib.project.fast.utils.fetchTextMessage
import kib.project.fast.utils.showToast
import kib.project.fast.utils.toDateTime
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun MpesaTextMessage(
    context: Context,
    sms: (String) -> Unit = {},
) {
    val viewModel: MpesaTextMessageViewModel = getViewModel()
    val coroutineScope = rememberCoroutineScope()
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
            }
        )
    } else {
        SystemBroadcastReceiver(
            context = context,
            systemAction = ACTION_SMS_RECEIVE,
            onSystemEvent = { intent ->
                intent.action?.let { action ->
                    if (action == ACTION_SMS_RECEIVE) {
                        intent.fetchTextMessage().let { triple: Triple<String?, String, Long>? ->
                            if (triple == null) {
                                context.showToast(message = "Received sms is NOT a M-Pesa transaction sms.")
                            }
                            triple?.let {
                                viewModel.setMpesaSmsTriple(triple = triple)
                                sms(it.second)
                            }
                        }
                    }
                }
            }
        )
        MessageCard(
            mpesaSmsTriple = uiState.value.mpesaSmsTriple,
            onClickPostSms = {
                coroutineScope.launch {
                    viewModel.postSms(
                        postSmsRequest = PostSmsRequest(message = it)
                    )
                }
            }
        )

        if (uiState.value.previousMpesaMessages.isNotEmpty()) {
            PreviousMessages(
                previousMpesaMessages = uiState.value.previousMpesaMessages,
                context = context
            )
        }
    }
}

@Composable
private fun MessageCard(
    mpesaSmsTriple: Triple<String?, String, Long>?,
    onClickPostSms: (String) -> Unit = {},
) {
    Card(
        modifier = Modifier.padding(12.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.title_mpesa_message),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = mpesaSmsTriple?.second
                    ?: stringResource(id = R.string.no_mpesa_sms_received),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )

            if (mpesaSmsTriple?.second.orEmpty().isNotEmpty()) {
                TextButton(
                    onClick = {
                        mpesaSmsTriple?.second?.let {
                            onClickPostSms(it)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(12.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.post_message),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun MessageCardPreview() {
    MessageCard(mpesaSmsTriple = null)
}

@Composable
private fun PreviousMessages(
    previousMpesaMessages: List<MpesaSms>,
    context: Context,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = stringResource(id = R.string.title_previous_messages),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
        itemsIndexed(items = previousMpesaMessages) { index, item ->
            Card(
                modifier = Modifier.padding(12.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = item.messageBody,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "Sender: ${item.sender}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "at ${item.timeStamp.toDateTime() ?: item.timeStamp}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviousMessagesPreview() {
    PreviousMessages(previousMpesaMessages = emptyList(), context = LocalContext.current)
}