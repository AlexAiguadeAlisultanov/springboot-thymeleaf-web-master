package com.example.springbootthymeleafweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Portada de l'aplicació. Només serveix la plantilla d'inici, sense tocar la base de dades.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String inici() {
        return "index";
    }
}
