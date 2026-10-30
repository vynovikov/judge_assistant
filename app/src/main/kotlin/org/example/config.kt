package org.example

import io.github.cdimascio.dotenv.dotenv
import java.io.File

private val dotenv = dotenv {
    ignoreIfMissing = true
}

object AppConfig {
    val DEFAULT_SAMPLE_FILE: String = dotenv.get("RECOGNIZE_FILE_PATH")
}

object OllamaConfig {
    const val GENERATE_ENDPOINT = "http://localhost:11434/api/generate"
    const val REQUEST_TIMEOUT_MS = 300_000L
    const val MODEL_NAME = "qwen2.5vl:3b"

    const val MODEL_TEMPERATURE = 0.0
    const val MODEL_TOP_P = 0.01
    const val MODEL_TOP_K = 1
    const val NUM_CTX = 32768

    val PROMPT_FILE_PATH: String = dotenv.get("OLLAMA_PROMPT_PATH")

    val RECOGNIZE_PROMPT: String by lazy {
        val promptFile = File(PROMPT_FILE_PATH)
        if (promptFile.exists()) {
            promptFile.readText(Charsets.UTF_8)
        } else {
            "Дефолтный короткий промпт, если файла нет"
        }
    }
}
