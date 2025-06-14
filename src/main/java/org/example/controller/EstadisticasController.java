package org.example.controller;

import org.example.service.EstadisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    @Autowired
    private EstadisticaService estadisticaService;

    @GetMapping("/generar/{moduloId}")
    public ResponseEntity<String> generarEstadisticas(
            @PathVariable Integer moduloId,
            @RequestParam("ruta") String ruta) {
        try {
            estadisticaService.generarExcelEstadistico(moduloId, ruta);
            return ResponseEntity.ok("Archivo generado en: " + ruta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar Excel: " + e.getMessage());
        }
    }
}
