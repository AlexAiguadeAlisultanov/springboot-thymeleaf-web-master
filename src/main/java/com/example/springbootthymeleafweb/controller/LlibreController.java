package com.example.springbootthymeleafweb.controller;

import com.example.springbootthymeleafweb.model.Llibre;
import com.example.springbootthymeleafweb.repository.LlibreRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class LlibreController {

    private LlibreRepository repository;

    public LlibreController(LlibreRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/llibres")
    public String getLlibres(Model model, @RequestParam(name = "error", required = false) String error) {
        List<Llibre> llibres = repository.findAll();
        model.addAttribute("llibres", llibres);
        model.addAttribute("error", error); // Agrega el mensaje de error al modelo
        return "Llibres/mostrar";
    }
    @GetMapping("/esborrar/{isbn}")
    public String esborrarLlibre(@PathVariable("isbn") String isbn, Model model){
        try{
            repository.deleteById(isbn);
        } catch (Exception e) {
            String error = "No s'ha pogut esborrar el llibre amb isbn: " + isbn;
            return "redirect:/llibres?error=" + URLEncoder.encode(error, StandardCharsets.UTF_8);
        }
        return "redirect:/llibres";
    }
    @GetMapping ("/afegirllibres")
    public String afegirLlibre(Model model){
        model.addAttribute("llibre", new Llibre());
        return "Llibres/afegirllibres";
    }
    @GetMapping("/editarllibres/{isbn}")
    public String editarLlibre(@PathVariable("isbn") String isbn, Model model){
        Llibre llibre = repository.findById(isbn).get();
        model.addAttribute("llibre", llibre);
        return "Llibres/editarllibres";
    }

    @PostMapping("/insertarllibres")
    public String insertarLlibre(@ModelAttribute Llibre llibre, Model model){
        try{
            repository.save(llibre);
        } catch (Exception e) {
            String error = "No s'ha pogut afegir el llibre amb isbn: " + llibre.getIsbn();
            return "redirect:/llibres?error=" + URLEncoder.encode(error, StandardCharsets.UTF_8);
        }
        return "redirect:/llibres";
    }

}
