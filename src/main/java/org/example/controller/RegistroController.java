package org.example.controller;

import org.example.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/registro")
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    @PostMapping("/subir")
    public ResponseEntity<String> uploadExcel(
            @RequestParam("actividadNombre") String nombre,
            @RequestParam("moduloId") Integer moduloId,
            @RequestParam("file") MultipartFile file) {
        try {
            registroService.registrarVoluntariosDesdeExcel(nombre, moduloId, file);
            return ResponseEntity.ok("Voluntarios registrados correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el archivo: " + e.getMessage());
        }
    }

}
