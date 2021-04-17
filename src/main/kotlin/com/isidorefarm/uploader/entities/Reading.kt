package com.isidorefarm.uploader.entities

data class Reading(
    val timestamp: String = "",
    val temperature: String = "",
    val humidity: String = "",
    val pressure: String = ""
) {

    fun valid(): Boolean {
        val intRegex = Regex("\\d+")
        val doubleRegex = Regex("\\d*\\.\\d+")

        return (
            timestamp.trim().matches(intRegex) &&
            temperature.trim().matches(doubleRegex) &&
            humidity.trim().matches(doubleRegex) &&
            pressure.trim().matches(intRegex)
        )
    }

    fun toJson(): String {
        return """
            {
              "timestamp": $timestamp,
              "temperature": $temperature,
              "humidity": $humidity,
              "pressure": $pressure
            }
        """.trimIndent()
    }

}



