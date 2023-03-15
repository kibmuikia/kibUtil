package kib.project.fast.ui.component

import android.content.Context
import android.content.IntentFilter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.phone.SmsRetriever
import kib.project.fast.ui.component.models.ViewSmsUiState
import kib.project.fast.ui.component.viewmodels.ViewSmsViewModel
import kib.project.fast.utils.AppSmsBroadcastReceiver
import kib.project.fast.utils.startAppSMSRetrieverClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

@Composable
fun ViewSms(
    context: Context,
    modifier: Modifier = Modifier,
    viewModel: ViewSmsViewModel = getViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.value.shouldStartSMSRetrieval) {
        if (uiState.value.shouldStartSMSRetrieval) {
            Timber.i(":: sms retrieval initialized")
            startAppSMSRetrieverClient(context = context)
        }
    }

    LaunchedEffect(1) {
        Timber.i(":: LaunchedEffect(1): init Sms BroadcastReceiver")
        AppSmsBroadcastReceiver().apply {
            context.registerReceiver(this, IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION))

            init(object : AppSmsBroadcastReceiver.AppSmsReceiveListener {
                override fun onOTPReceived(otp: String?) {
                    Timber.i(":: onOTPReceived: otp: $otp")
                    otp?.let { message ->
                        viewModel.setReceivedSms(value = message)
                    }
                }

                override fun onOTPTimeOut() {
                    Timber.i(":: onOTPTimeOut: init")
                }
            })
        }
    }

    ViewSmsContent(
        context = context,
        coroutineScope = coroutineScope,
        modifier = modifier,
        startSmsRetrieval = {
            viewModel.setShouldStartSmsRetrieval(value = true)
        },
        uiState = uiState
    )
}

@Composable
private fun ViewSmsContent(
    context: Context,
    modifier: Modifier,
    coroutineScope: CoroutineScope,
    startSmsRetrieval: () -> Unit = {},
    uiState: State<ViewSmsUiState>
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 120.dp)
            .padding(12.dp),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = modifier.padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sms Processor",
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = modifier.height(12.dp))
            Text(
                text = "Received Sms: \n${uiState.value.receivedSms ?: "No sms has yet to be retrieved"}",
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = modifier.height(12.dp))
            TextButton(
                onClick = { startSmsRetrieval() },
                modifier = modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.End)
            ) {
                Text(
                    text = if (uiState.value.shouldStartSMSRetrieval) { "Retrieval: ON" } else { "Start Sms Retrieval" },
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
private fun ViewSmsContentPreview() {
    val uiState = remember {
        MutableStateFlow(ViewSmsUiState()).asStateFlow()
    }.collectAsState()

    ViewSmsContent(
        context = LocalContext.current,
        modifier = Modifier,
        coroutineScope = rememberCoroutineScope(),
        uiState = uiState
    )
}