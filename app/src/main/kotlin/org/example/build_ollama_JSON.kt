package org.example

// import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString

import java.io.File
import java.util.Base64

@Serializable
data class OllamaOptions(
    val temperature: Double,
    @kotlinx.serialization.SerialName("top_p") val topP: Double,
    @kotlinx.serialization.SerialName("top_k") val topK: Int,
    @kotlinx.serialization.SerialName("num_ctx") val numCtx: Int
)

@Serializable
data class OllamaRequest(
    val model: String,
    val prompt: String,
    val stream: Boolean = false,
    val format: String? = null,
    val options: OllamaOptions,
    val images: List<String>
)

fun buildOllamaJson(config: OllamaConfig, rawPrompt: String, imageFile: File): String {
    val base64Image = Base64.getEncoder().encodeToString(imageFile.readBytes())

    val requestObj = OllamaRequest(
        model = config.modelName,
        prompt = rawPrompt.trimIndent(),
        stream = false,
        format = "json",
        options = OllamaOptions(
            temperature = config.modelTemperature,
            topP = config.modelTopP,
            topK = config.modelTopK,
            numCtx = config.numCtx
        ),
        images = listOf(base64Image)
    )

    return judgeJson.encodeToString(requestObj)

}




