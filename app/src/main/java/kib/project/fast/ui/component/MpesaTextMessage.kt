@file:OptIn(ExperimentalPermissionsApi::class)

package kib.project.fast.ui.component

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kib.project.fast.ui.component.viewmodels.MpesaTextMessageViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun MpesaTextMessage(
    mpesaSms: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val viewModel: MpesaTextMessageViewModel = getViewModel()
    val uiState = viewModel.uiState.collectAsState()
    val smsPermission = Manifest.permission.READ_SMS
    val permissionState = rememberPermissionState(permission = smsPermission)
    val isPermissionGranted =
        permissionState.status.isGranted && !permissionState.status.shouldShowRationale
    viewModel.setReadSmsPermissionState(state = isPermissionGranted)

    if (!isPermissionGranted) {
        SinglePermission(
            modifier = Modifier,
            permission = smsPermission,
            actionPermissionGranted = {})
    } else {
        //
    }
}