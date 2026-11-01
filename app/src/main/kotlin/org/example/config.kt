package org.example

import io.github.cdimascio.dotenv.dotenv
import java.io.File

private val dotenv = dotenv {
    ignoreIfMissing = true
}

object AppConfig {
    val recognizeFilePath: String = dotenv.get("RECOGNIZE_FILE_PATH")
}

object OllamaConfig {
    val ollamaEndpoint: String = dotenv.get("OLLAMA_ENDPOINT")
    val requestTimeout: Long = dotenv.get("REQUEST_TIMEOUT_MS").toLong()
    val modelName: String =dotenv.get("MODEL_NAME")

    val modelTemperature: Double = dotenv.get("MODEL_TEMPERATURE").toDouble()
    val modelTopP : Double = dotenv.get("MODEL_TOP_P").toDouble()
    val modelTopK: Int = dotenv.get("MODEL_TOP_K").toInt()
    val numCtx: Int = dotenv.get("NUM_CTX").toInt()

    val recognizePromptPath: String = dotenv.get("RECOGNIZE_PROMPT_PATH")

    val recognizePrompt: String by lazy {
        val promptFile = File(recognizePromptPath)
        if (promptFile.exists()) {
            promptFile.readText(Charsets.UTF_8)
        } else {
            "Дефолтный короткий промпт, если файла нет"
        }
    }
}
