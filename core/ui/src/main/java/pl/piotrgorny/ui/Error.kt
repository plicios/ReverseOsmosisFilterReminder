package pl.piotrgorny.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorMessage(error: String) {
    Text(modifier = Modifier.fillMaxWidth(), text = error, color = MaterialTheme.colors.error)
}