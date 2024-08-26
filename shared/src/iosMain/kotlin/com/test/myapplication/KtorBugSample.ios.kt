package com.test.myapplication

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.engine.darwin.KtorNSURLSessionDelegate
import platform.Foundation.NSURLRequestReloadIgnoringLocalCacheData
import platform.Foundation.NSURLSession
import platform.Foundation.NSURLSessionConfiguration

internal actual fun createPlatformHttpClient(
    config: HttpClientConfig<*>.() -> Unit
): HttpClient = HttpClient(Darwin) {
    // TODO:
    // Comment this block to solve the issue
    engine {
        usePreconfiguredSession(
            session =  NSURLSession.sessionWithConfiguration(
                NSURLSessionConfiguration.defaultSessionConfiguration
            ),
            delegate = KtorNSURLSessionDelegate()
        )
    }

    config()
}