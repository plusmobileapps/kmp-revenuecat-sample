package com.plusmobileapps.kmp.samples.revenuecat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.ui.revenuecatui.Paywall
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions

@Composable
fun PaywallSample(modifier: Modifier = Modifier) {
    val options = remember {
        PaywallOptions(dismissRequest = { }) {
            shouldDisplayDismissButton = true
        }
    }
    Box(modifier = modifier.fillMaxSize()) {
        Paywall(options = options)
    }
}