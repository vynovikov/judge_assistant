package org.example

import java.io.File
import java.util.Base64

fun buildOllamaJson(config: OllamaConfig, rawPrompt: String, imageFile: File): String {
    val base64Image = Base64.getEncoder().encodeToString(imageFile.readBytes())

    val escapedPrompt = rawPrompt.trimIndent()
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "")

    return """
        {
            "model": "${config.MODEL_NAME}",
            "prompt": "$escapedPrompt",
            "stream": false,
            "format": "json",
            "options": {
                "temperature": ${config.MODEL_TEMPERATURE},
                "top_p": ${config.MODEL_TOP_P},
                "top_k": ${config.MODEL_TOP_K},
                "num_ctx": ${config.NUM_CTX}
            },
            "images": ["$base64Image"]
        }
    """.trimIndent()
}



