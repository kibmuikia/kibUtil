package kib.project.fast.ui.component.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
data class ExpandableCardModel(
    val id: Int,
    val title: String,
    val expandableContent: @Composable () -> Unit = {},
)
