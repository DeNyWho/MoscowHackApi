package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.repository.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.locations.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureSecurity()
        configureMonitoring()
        configureSerialization()
    }.start(wait = true)
}
