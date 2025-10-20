package com.example.feature.holdings.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.coreui.commonUtils.Dimens.size16
import com.example.coreui.commonUtils.Strings.NO_INTERNET_CONNECTION
import com.example.coreui.commonUtils.Strings.NO_INTERNET_MESSAGE
import com.example.coreui.commonUtils.Strings.RETRY

@Composable
fun NoInternetState(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(size16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "📡",
            style = MaterialTheme.typography.displayLarge
        )
        
        Text(
            text = NO_INTERNET_CONNECTION,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = size16)
        )
        
        Text(
            text = NO_INTERNET_MESSAGE,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = size16)
        )
        
        Button(
            onClick = onRetry,
            modifier = Modifier.padding(top = size16)
        ) {
            Text(text = RETRY)
        }
    }
}

@Preview
@Composable
private fun NoInternetStatePreview() {
    NoInternetState()
}
