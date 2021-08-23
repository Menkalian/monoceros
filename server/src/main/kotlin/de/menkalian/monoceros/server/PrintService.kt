package de.menkalian.monoceros.server

import de.menkalian.monoceros.server.printing.PrintInformation
import de.menkalian.monoceros.server.printing.PrintRenderer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.awt.print.Printable
import java.awt.print.PrinterJob
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.print.PrintServiceLookup

@Service
class PrintService {
    val logger = LoggerFactory.getLogger(this::class.java)!!

    fun doPrinting(printInformation: PrintInformation) {
        logger.info("Printing $printInformation")

        val printJob = PrinterJob.getPrinterJob()!!
        val printer = PrinterJob.lookupPrintServices()
            .firstOrNull { it.name == "HP_LASERJET_8" } ?: PrintServiceLookup.lookupDefaultPrintService()

        printJob.printService = printer
        printJob.copies = 1
        printJob.jobName = "Aufkleber_" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        printJob.setPrintable(renderPrintable(printInformation))
        printJob.print()
    }

    private fun renderPrintable(printInformation: PrintInformation): Printable {
        return PrintRenderer(printInformation)
    }
}
