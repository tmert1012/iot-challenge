package com.isidorefarm.uploader.entities

data class Report(val readings: List<Reading>) {

    var timestamp = ""
    var minTemp = 0.0
    var maxTemp = 0.0
    var avgTemp = 0.0
    var minHumidity = 0.0
    var maxHumidity = 0.0
    var avgHumidity = 0.0
    var minPressure = 0.0
    var maxPressure = 0.0
    var avgPressure = 0.0

    // these helper functions are nice, but might be more efficient to traverse the list once and create metrics, even though this would be more code
    // if I were to do that, I'd create a test to make sure the math is done correctly.
    // feel confident in these built in methods
    init {
        timestamp = readings.maxByOrNull { it.timestamp }?.timestamp ?: ""

        minTemp = readings.minByOrNull { it.temperature }?.temperature?.toDouble() ?: 0.0
        maxTemp = readings.maxByOrNull { it.temperature }?.temperature?.toDouble() ?: 0.0
        avgTemp = readings.map { it.temperature.toDouble() }.average()

        minHumidity = readings.minByOrNull { it.humidity }?.humidity?.toDouble() ?: 0.0
        maxHumidity = readings.maxByOrNull { it.humidity }?.humidity?.toDouble() ?: 0.0
        avgHumidity = readings.map { it.humidity.toDouble() }.average()

        minPressure = readings.minByOrNull { it.pressure }?.pressure?.toDouble() ?: 0.0
        maxPressure = readings.maxByOrNull { it.pressure }?.pressure?.toDouble() ?: 0.0
        avgPressure = readings.map { it.pressure.toDouble() }.average()
    }

    fun toJson(): String {
        return """
            {
               "timestamp": ${timestamp},
               "min-temperature": $minTemp,
               "max-temperature": $maxTemp,
               "avg-temperature": $avgTemp,
               "min-humidity": $minHumidity,
               "max-humidity": $maxHumidity,
               "avg-humidity": $avgHumidity,
               "min-pressure": $minPressure,
               "max-pressure": $maxPressure,
               "avg-pressure": $avgPressure
            }
        """.trimIndent()
    }

    fun size(): Int {
        return readings.size
    }


}
