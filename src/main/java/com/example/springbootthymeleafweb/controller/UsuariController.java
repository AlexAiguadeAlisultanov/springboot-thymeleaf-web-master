package com.example.springbootthymeleafweb.controller;

import com.example.springbootthymeleafweb.model.Usuari;
import com.example.springbootthymeleafweb.repository.UsuariRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class UsuariController {

    private UsuariRepository repository;

    public UsuariController(UsuariRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/usuaris")
    public String getUsuaris(Model model, @RequestParam(name = "error", required = false) String error) {
        List<Usuari> usuaris = repository.findAll();
        model.addAttribute("usuaris", usuaris);
        model.addAttribute("error", error);
        return "Usuaris/mostrar";
    }

    @GetMapping("/esborrarusuari/{dni}")
    public String esborrarUsuari(@PathVariable("dni") String dni) {
        try {
            repository.deleteById(dni);
        } catch (Exception e) {
            String error = "No s'ha pogut esborrar l'usuari amb DNI: " + dni;
            return "redirect:/usuaris?error=" + URLEncoder.encode(error, StandardCharsets.UTF_8);
        }
        return "redirect:/usuaris";
    }

    @GetMapping("/afegirusuari")
    public String afegirUsuari(Model model) {
        model.addAttribute("usuari", new Usuari());
        return "Usuaris/afegirusuaris";
    }

    @PostMapping("/insertarusuari")
    public String insertarUsuari(Usuari usuari) {
        repository.save(usuari);
        return "redirect:/usuaris";
    }

    @GetMapping("/editarusuari/{dni}")
    public String editarUsuari(@PathVariable("dni") String dni, Model model) {
        Usuari usuari = repository.findById(dni).orElseThrow(() -> new IllegalArgumentException("Invalid usuari DNI:" + dni));
        model.addAttribute("usuari", usuari);
        return "Usuaris/editarusuaris";
    }
}