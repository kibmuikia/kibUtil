package kib.project.fast.ui.component.models

import kib.project.data.database.entities.textMessage.MpesaSms

data class MpesaTextMessageUiState(
    val isSmsPermissionGranted: Boolean = false,
    val mpesaSmsTriple: Triple<String?, String, Long>? = null,
    val previousMpesaMessages: List<MpesaSms> = emptyList()
)
