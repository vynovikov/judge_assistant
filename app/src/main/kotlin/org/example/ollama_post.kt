package org.example

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import java.io.File
import java.util.Base64
import kotlinx.serialization.json.Json


val judgeJson = Json { ignoreUnknownKeys = true }

@Serializable
data class OllamaEnvelope(val response: String)

@Serializable
data class AthleteProtocol(
    val name: String,
    val gates: Map<String, Int>
)

suspend fun ollamaPost(
    client: HttpClient,
    config: OllamaConfig,
    rawPrompt: String,
    file: File
): Map<String, AthleteProtocol> {
    val requestBody = buildOllamaJson(config, rawPrompt, file)

    println("Отправка запроса структуры результатов в Ollama...")
    val httpResponse = client.post(config.GENERATE_ENDPOINT) {
        contentType(ContentType.Application.Json)
        setBody(requestBody)
    }

    val responseText = httpResponse.bodyAsText()
    val envelope = judgeJson.decodeFromString<OllamaEnvelope>(responseText)
    val resultJSON = judgeJson.decodeFromString<Map<String, AthleteProtocol>>(envelope.response)

    return resultJSON
}

