package com.codekan.notes

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform