package kib.project.core.utils

import android.content.Context
import android.widget.Toast

fun showToast(
    context: Context,
    message: String,
    duration: Int = Toast.LENGTH_LONG,
) {
    Toast
        .makeText(context, message.ifBlank { "No Message To Show" }, duration)
        .show()
}