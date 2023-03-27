package kib.project.fast.ui.component.models

data class MpesaTextMessageUiState(
    val isSmsPermissionGranted: Boolean = false,
    val mpesaMessage: String = ""
)
