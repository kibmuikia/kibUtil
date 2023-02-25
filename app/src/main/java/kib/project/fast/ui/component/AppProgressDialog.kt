package kib.project.fast.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kib.project.fast.R
import kib.project.fast.ui.component.viewmodels.AppProgressDialogViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun AppProgressDialog(
    viewModel: AppProgressDialogViewModel = getViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    AppProgressDialogContent(
        show = uiState.value.show,
        onDismissTriggered = {
            viewModel.setUiStateShow(value = !uiState.value.show)
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AppProgressDialogContent(show: Boolean, onDismissTriggered: () -> Unit = {}) {
    if (show) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_cupio_jumping_dots))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            reverseOnRepeat = false,
            speed = 1F,
        )

        Dialog(
            onDismissRequest = { onDismissTriggered() },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(150.dp)
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .combinedClickable(onLongClick = { onDismissTriggered() }, onClick = {})
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.fillMaxSize(0.8F),
                )
            }
        }
    } else {
        //
    }
}

@Preview
@Composable
private fun AppProgressDialogContentPreview() {
    AppProgressDialogContent(show = true)
}