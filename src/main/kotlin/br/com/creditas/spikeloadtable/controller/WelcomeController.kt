package br.com.creditas.spikeloadtable.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WelcomeController {

    @GetMapping
    fun info(): String {
        return "redirect:/actuator/info"
    }

}