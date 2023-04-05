package kib.project.fast.utils

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toDateTime(): String? = try {
    val simpleDateFormat = SimpleDateFormat("HH:mm - dd/MMM/yyyy", Locale.getDefault())
    simpleDateFormat.format(Date(this))
} catch (e: Exception) {
    Timber.i(e, e.localizedMessage)
    null
}