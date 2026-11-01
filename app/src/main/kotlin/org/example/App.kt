package org.example

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.post
import kotlin.coroutines.cancellation.CancellationException
import java.io.File

suspend fun main() {
    HttpClient(CIO) {
        install(HttpTimeout) { requestTimeoutMillis = OllamaConfig.requestTimeout }
    }.use { client ->
        recognizeProtocol(client, AppConfig, OllamaConfig)
    }
}

suspend fun recognizeProtocol(client: HttpClient, appConfig: AppConfig, ollamaConfig: OllamaConfig) {
    val file = File(appConfig.recognizeFilePath)
    if (!file.exists()) return

    try {
        val resultsMap = ollamaPost(client, ollamaConfig, ollamaConfig.recognizePrompt, file)
        printTable(resultsMap)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
    }
}