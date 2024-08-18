package com.tylerlowrey

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform