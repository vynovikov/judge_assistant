package org.example

fun printTable(results: Map<String, AthleteProtocol>) {
    println("\n==================================================================================")
    println("  ПРОТОКОЛ РЕЗУЛЬТАТОВ (Участников: ${results.size})")
    println("==================================================================================")

    results.toSortedMap(compareBy { it.toIntOrNull() ?: 0 }).forEach { (athleteId, protocol) ->
        // Красиво склеиваем ID и фамилию
        val athleteInfo = "$athleteId. ${protocol.name}".padEnd(18)
        print("Атлет: $athleteInfo | ")

        // Сортируем ворота по порядку
        val sortedGates = protocol.gates.toSortedMap(compareBy { it.toIntOrNull() ?: 0 })

        print(sortedGates.entries.joinToString(", ") {
            "[ В_${it.key}: ${it.value.toString().padStart(2)}]"
        })
        println(" Всего штрафа: ${protocol.gates.values.sum().toString().padEnd(2)} с")
    }

    println("==================================================================================\n")
}
