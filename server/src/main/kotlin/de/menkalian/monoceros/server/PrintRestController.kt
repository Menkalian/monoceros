package de.menkalian.monoceros.server

import de.menkalian.monoceros.server.printing.PrintInformation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PrintRestController {
    @PostMapping("print")
    fun print(@RequestBody info: PrintInformation, printService: PrintService) {
        printService.doPrinting(info)
    }
}