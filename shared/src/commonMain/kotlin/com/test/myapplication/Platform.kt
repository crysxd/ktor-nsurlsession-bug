package com.test.myapplication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform