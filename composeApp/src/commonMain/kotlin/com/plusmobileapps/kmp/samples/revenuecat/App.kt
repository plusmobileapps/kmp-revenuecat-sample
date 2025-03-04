package com.plusmobileapps.kmp.samples.revenuecat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.configure
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var isConfigured by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        startupRevenueCat()
        isConfigured = true
    }
    MaterialTheme {
        if (isConfigured) {
            PaywallSample(modifier = Modifier.fillMaxSize())
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

private fun startupRevenueCat() {
    Purchases.logLevel = LogLevel.DEBUG
    Purchases.configure(REVENUE_CAT_API_KEY) {}
}