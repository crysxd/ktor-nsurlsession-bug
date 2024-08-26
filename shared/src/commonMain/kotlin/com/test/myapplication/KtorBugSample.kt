package com.test.myapplication

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.DefaultJson
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration.Companion.seconds

object KtorBugSample {

    suspend fun reproduceBug(
        onSent: () -> Unit,
        onReceived: () -> Unit,
        onCompleted: (Throwable?) -> Unit,
    ) {
        val httpClient = createPlatformHttpClient {
            // Just to see some logs
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println("KTOR: $message")
                        if (message.startsWith("REQUEST: ")) {
                            onSent()
                        }
                        if (message.startsWith("RESPONSE: ")) {
                            onReceived()
                        }
                    }
                }
            }
        }

        try {
            withTimeout(3.seconds) {
                httpClient.get("https://jsonplaceholder.typicode.com/posts/1").bodyAsText()
            }
            onCompleted(null)
        } catch (e: Exception) {
            e.printStackTrace()
            onCompleted(e)
        }
    }
}

internal expect fun createPlatformHttpClient(
    config: HttpClientConfig<*>.() -> Unit
): HttpClient