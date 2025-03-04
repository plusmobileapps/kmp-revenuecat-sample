package com.plusmobileapps.kmp.samples.revenuecat

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}