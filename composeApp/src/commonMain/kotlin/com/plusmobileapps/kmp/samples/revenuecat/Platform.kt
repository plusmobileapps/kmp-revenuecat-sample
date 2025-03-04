package com.plusmobileapps.kmp.samples.revenuecat

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform