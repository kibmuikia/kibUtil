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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
) {
    val permissionState = rememberPermissionState(permission = permission)
    SinglePermissionCard(
        modifier = modifier,
        permission = permission,
        permissionState = permissionState
    )
}

@Composable
private fun SinglePermissionCard(
    modifier: Modifier,
    permission: String,
    permissionState: PermissionState?,
) {
    val isPermissionGranted =
        permissionState != null && permissionState.status.isGranted && !permissionState.status.shouldShowRationale
    val status = if (isPermissionGranted) {
        "The permission has been granted."
    } else if (permissionState?.status?.shouldShowRationale == true) {
        "The permission is important for the requested application functionality to available. Please grant the permission."
    } else {
        "The permission is required for the requested application functionality to be available. Please grant the permission"
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp)
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(modifier = modifier) {
            Text(
                text = permission,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = modifier.height(12.dp))
            Text(
                text = status,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Spacer(modifier = modifier.height(12.dp))
            if (!isPermissionGranted) {
                Button(
                    onClick = {
                        permissionState?.launchPermissionRequest()
                    }, modifier = modifier
                        .align(Alignment.End)
                        .padding(end = 8.dp, bottom = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.request_permission),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
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