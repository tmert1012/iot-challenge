package com.isidorefarm.uploader.entities

class DiagnosticReporter(
    val readings: ArrayList<Reading> = ArrayList(),
    sampleSizeWindow: Int = 0,
    reportInterval: Int = 0
) {

    val reports = ArrayList<Report>()

    // reports are pretty straight forward. pull off a set of readings, slide the window as noted by sample size and report interval
    init {

        var start = 0
        var end = sampleSizeWindow

        println("\nDiagnosticReporter (reading.size: ${readings.size}) ---- ")

        var id = 1
        while (end <= readings.size) {
            println("Report $id ($start -> $end)")

            val report = Report(readings.subList(start, end))

            // keep if enough to fill sample size
            if (report.size() == sampleSizeWindow) {
                report.readings.forEach { reading -> println("\t" + reading) }
                println("\t" + report.toJson())
                reports.add(report)
            }

            start += reportInterval
            end = start + sampleSizeWindow
            id++
        }

    }

}