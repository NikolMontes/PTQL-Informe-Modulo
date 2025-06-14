package org.example.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.Voluntario;
import org.example.entity.VoluntarioActividad;
import org.example.repository.VoluntarioActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EstadisticaService {
    @Autowired VoluntarioActividadRepository participacionRepo;

    public void generarExcelEstadistico(Integer moduloId, String rutaSalida) throws Exception {
        List<VoluntarioActividad> participaciones = participacionRepo.findByActividad_Modulo_Idmodulo(moduloId);

        Map<String, List<String>> actividadesPorPersona = new HashMap<>();
        Map<String, Voluntario> datosPersona = new HashMap<>();

        for (VoluntarioActividad p : participaciones) {
            String dni = p.getVoluntario().getDni();
            actividadesPorPersona.putIfAbsent(dni, new ArrayList<>());
            actividadesPorPersona.get(dni).add(p.getActividad().getNombre());
            datosPersona.put(dni, p.getVoluntario());
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Estadísticas");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("DNI");
        header.createCell(1).setCellValue("Nombre");
        header.createCell(2).setCellValue("Email");
        header.createCell(3).setCellValue("Celular");
        header.createCell(4).setCellValue("Edad");
        header.createCell(5).setCellValue("Distrito");
        header.createCell(6).setCellValue("Carrera Universitaria/Instituto");
        header.createCell(7).setCellValue("Actividades Participadas");
        header.createCell(8).setCellValue("Cantidad");

        int i = 1;
        for (String dni : actividadesPorPersona.keySet()) {
            try {
                Row row = sheet.createRow(i++);
                Voluntario p = datosPersona.get(dni);
                System.out.println("Procesando voluntario con DNI: " + dni);

                String nombre = (p.getNombreCompleto() == null || p.getNombreCompleto().isBlank()) ? "-" : p.getNombreCompleto();
                String email = (p.getEmail() == null || p.getEmail().isBlank()) ? "-" : p.getEmail();
                String celular = (p.getCelular() == null || p.getCelular().isBlank()) ? "-" : p.getCelular();
                String edad = (p.getEdad() == null) ? "-" : p.getEdad().toString();
                String distrito = (p.getDistrito() == null || p.getDistrito().isBlank()) ? "-" : p.getDistrito();
                String carrera = (p.getCarreraUniversitaria() == null || p.getCarreraUniversitaria().isBlank()) ? "-" : p.getCarreraUniversitaria();

                String actividades = actividadesPorPersona.containsKey(dni) ? String.join(", ", actividadesPorPersona.get(dni)) : "-";
                int cantidad = actividadesPorPersona.containsKey(dni) ? actividadesPorPersona.get(dni).size() : 0;

                row.createCell(0).setCellValue(dni);
                row.createCell(1).setCellValue(nombre);
                row.createCell(2).setCellValue(email);
                row.createCell(3).setCellValue(celular);
                row.createCell(4).setCellValue(edad);
                row.createCell(5).setCellValue(distrito);
                row.createCell(6).setCellValue(carrera);
                row.createCell(7).setCellValue(actividades);
                row.createCell(8).setCellValue(cantidad);

                System.out.println("✔ Estadística agregada para: " + nombre);
            } catch (Exception e) {
                System.out.println("✘ Error al procesar voluntario con DNI: " + dni + ". Causa: " + e.getMessage());
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream(rutaSalida)) {
            workbook.write(fileOut);
            System.out.println("✔ Archivo generado exitosamente en: " + rutaSalida);
        } catch (Exception e) {
            System.out.println("✘ Error al escribir el archivo: " + e.getMessage());
        } finally {
            workbook.close();
        }
    }
}

