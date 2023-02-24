package kib.project.fast.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kib.project.fast.ui.component.viewmodels.AppProgressDialogViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun AppProgressDialog(
    viewModel: AppProgressDialogViewModel = getViewModel()
) {
    AppProgressDialogContent()
}

@Composable
private fun AppProgressDialogContent() {
    //
}

@Preview
@Composable
private fun AppProgressDialogContentPreview() {
    AppProgressDialogContent()
}