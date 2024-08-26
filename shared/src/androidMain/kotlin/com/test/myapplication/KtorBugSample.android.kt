package com.test.myapplication

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient

internal actual fun createPlatformHttpClient(
    config: HttpClientConfig<*>.() -> Unit
): HttpClient = HttpClient(OkHttp) {
    engine {
        preconfigured = OkHttpClient.Builder().run {
            PinningConfig.PinningDisabled.asPinner()?.let { pinner -> certificatePinner(pinner) }
            build()
        }
    }

    config()
}

private fun PinningConfig.asPinner(): CertificatePinner? = when (val conf = this) {
    PinningConfig.PinningDisabled -> null
    is PinningConfig.PublicKeyPinning -> CertificatePinner.Builder().apply {
        for (publicKey in conf.trustedPublicKeys) {
            add(publicKey.host, publicKey.publicKey)
        }
    }.build()
}
