package kib.project.fast.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kib.project.fast.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SinglePersmission(
    modifier: Modifier = Modifier,
    permission: String, // Eg: android.Manifest.permission.ACCESS_NETWORK_STATE
) {
    val permissionState = rememberPermissionState(permission = permission)

    SinglePersimissionContent(
        modifier = modifier,
        permission = permission,
        permissionState = permissionState
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun SinglePersimissionContent(
    modifier: Modifier,
    permission: String,
    permissionState: PermissionState?,
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (permissionState == null) {
            Text(
                text = "$permission has been granted",
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(all = 8.dp)),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            return
        }
        if (permissionState.status.isGranted) {
            Text(
                text = "$permission has been granted",
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(all = 8.dp)),
                fontSize = 16.sp,
                color = Color.Black
            )
        } else {
            Text(
                text = if (permissionState.status.shouldShowRationale) {
                    "The $permission is important for this app. Please grant the permission."
                } else {
                    "$permission is required for the requested app functionality to be available. \nPlease grant the permission"
                },
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(all = 8.dp)),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 4,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                permissionState.launchPermissionRequest()
            }) {
                Text(
                    text = stringResource(id = R.string.request_permission),
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
private fun SinglePersimissionContentPreview() {
    val permission = android.Manifest.permission.CAMERA

    SinglePersimissionContent(
        modifier = Modifier,
        permission = permission,
        permissionState = null,
    )
}