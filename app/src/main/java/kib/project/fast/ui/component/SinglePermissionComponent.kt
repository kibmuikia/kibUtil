@file:OptIn(ExperimentalPermissionsApi::class)

package kib.project.fast.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kib.project.fast.R

@Composable
fun SinglePermission(
    modifier: Modifier = Modifier,
    permission: String, // Eg: android.Manifest.permission.ACCESS_NETWORK_STATE
    actionPermissionGranted: () -> Unit = {},
) {
    val permissionState = rememberPermissionState(permission = permission)
    SinglePermissionCard(
        modifier = modifier,
        permission = permission,
        permissionState = permissionState,
        actionPermissionGranted = {
            actionPermissionGranted()
        }
    )
}

@Composable
private fun SinglePermissionCard(
    modifier: Modifier,
    permission: String,
    permissionState: PermissionState?,
    actionPermissionGranted: () -> Unit = {}
) {
    val isPermissionGranted =
        permissionState != null && permissionState.status.isGranted && !permissionState.status.shouldShowRationale
    val status = if (isPermissionGranted) {
        "The permission has been granted."
    } else if (permissionState?.status?.shouldShowRationale == true) {
        "Please grant the permission, for the application to function as expected."
    } else {
        "The permission is required for the requested application functionality to be available. \nPlease grant the permission"
    }
    val title = permission.split(".")[2] ?: permission

    Card(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp)
            .padding(top = 8.dp, start = 12.dp, end = 12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(modifier = modifier) {
            Text(
                text = title,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = modifier.height(12.dp))
            Text(
                text = status,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )
            Spacer(modifier = modifier.height(12.dp))
            Button(
                onClick = {
                    if (!isPermissionGranted) {
                        permissionState?.launchPermissionRequest()
                    } else {
                        actionPermissionGranted()
                    }
                },
                modifier = modifier
                    .align(Alignment.End)
            ) {
                Text(
                    text = if (!isPermissionGranted) {
                        stringResource(id = R.string.request_permission)
                    } else {
                        stringResource(id = R.string.perform_task)
                    },
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SinglePermissionCardPreview() {
    val permission = android.Manifest.permission.CAMERA
    SinglePermissionCard(modifier = Modifier, permission = permission, permissionState = null)
}