package org.example.taskmanager

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
