package kib.project.fast.ui.component.models

data class ViewSmsUiState(
    val shouldStartSMSRetrieval: Boolean = false,
    val receivedSms: String? = null
)