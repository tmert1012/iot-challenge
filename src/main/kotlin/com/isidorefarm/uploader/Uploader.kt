package com.isidorefarm.uploader

import com.isidorefarm.uploader.entities.DiagnosticReporter
import com.isidorefarm.uploader.util.CommandLineProject

class Uploader: CommandLineProject() {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            // command line args
            parseCommandLineArgs(args)

            var file = "readings.csv"
            if (haveGoodArg("file"))
                file = getArg("file")

            var sampleSize = IntParam("sampleSize")
            var reportInterval = IntParam("reportInterval")

            // load all readings from file
            val readings = Reader(file).read()

            // create diagnostic reports
            val reporter = DiagnosticReporter(
                readings,
                if (sampleSize.isValid) sampleSize.value else 4,
                if (reportInterval.isValid) reportInterval.value else 2
            )

            // submit to AWS IoT. submitting everything at once, since this isn't a live device but a CSV.
            Connector("things/thing-for-tmert1012/readings", readings.map { reading -> reading.toJson() })
            Connector("things/thing-for-tmert1012/diagnostics", reporter.reports.map { report -> report.toJson() })

            println("upload complete!")
        }

    }

}