package org.example.controller;

import org.example.repository.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;


@Controller
public class VistasController {
    @Autowired
    private ModuloRepository moduloRepository;

    @GetMapping("/")
    public String inicio() {
        return "inicio";  // muestra la vista de bienvenida
    }

    @GetMapping("/registro")
    public String vistaRegistro(Model model) {

        model.addAttribute("modulos", moduloRepository.findAll());
        return "registro"; // página con formulario de carga Excel
    }

    @GetMapping("/estadisticas")
    public String vistaEstadisticas(Model model) {
        model.addAttribute("modulos", moduloRepository.findAll());
        return "estadisticas"; // página con formulario para generar Excel
    }
}
