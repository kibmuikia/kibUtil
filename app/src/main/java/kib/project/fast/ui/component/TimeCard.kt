package kib.project.fast.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeCard() {
    val isPlaying = remember {
        mutableStateOf(false)
    }
    val isAmPm = remember { mutableStateOf(true) }
    val currentTime = remember { mutableStateOf(LocalTime.now()) }

    LaunchedEffect(isPlaying.value) {
        while (isPlaying.value) {
            withContext(Dispatchers.Default) {
                val time = LocalTime.now()
                withContext(Dispatchers.Main) {
                    currentTime.value = time
                }
            }
            delay(1L) // Update every millisecond
        }
    }

    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = if (isAmPm.value) {
                    currentTime.value.format(DateTimeFormatter.ofPattern("h:mm:ss a"))
                } else {
                    currentTime.value.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                },
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            ) {
                TextButton(
                    onClick = { isAmPm.value = !isAmPm.value },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(text = if (isAmPm.value) "24H" else "AM/PM")
                }

                TextButton(
                    onClick = { isPlaying.value = !isPlaying.value },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text(text = if (isPlaying.value) "PAUSE" else "PLAY")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
private fun TimeCardPreview() {
    TimeCard()
}
