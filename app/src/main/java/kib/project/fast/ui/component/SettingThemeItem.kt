package kib.project.fast.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kib.project.fast.R

@Composable
fun SettingThemeItem(
    modifier: Modifier = Modifier,
    title: String,
    isEnable: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(PaddingValues(4.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(PaddingValues(12.dp)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, color = MaterialTheme.colorScheme.onTertiary, fontSize = 16.sp)
            Switch(
                checked = isEnable,
                onCheckedChange = { onClick() },
                colors = SwitchDefaults.colors(
                    checkedBorderColor = MaterialTheme.colorScheme.onTertiary,
                    checkedThumbColor = MaterialTheme.colorScheme.tertiary,
                    uncheckedBorderColor = MaterialTheme.colorScheme.secondary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onTertiary,
                )
            )
        }
    }
}

@Preview()
@Composable
fun SettingThemeItemPreview() {
    SettingThemeItem(
        modifier = Modifier,
        title = stringResource(id = R.string.placeholder_title),
        isEnable = false,
        onClick = {}
    )
}