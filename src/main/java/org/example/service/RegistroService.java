package org.example.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.Actividad;
import org.example.entity.Modulo;
import org.example.entity.Voluntario;
import org.example.entity.VoluntarioActividad;
import org.example.repository.ActividadRepository;
import org.example.repository.ModuloRepository;
import org.example.repository.VoluntarioActividadRepository;
import org.example.repository.VoluntarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RegistroService {
    @Autowired
    VoluntarioRepository voluntarioRepository;
    @Autowired
    ActividadRepository actividadRepo;
    @Autowired
    VoluntarioActividadRepository participacionRepo;
    @Autowired
    ModuloRepository moduloRepository;


    public void registrarVoluntariosDesdeExcel(String nombre,Integer moduloId, MultipartFile file) throws Exception {
        Modulo modulo = moduloRepository.findById(moduloId)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));
        // Buscar si ya existe la actividad
        Actividad actividad = actividadRepo.findByNombreAndModulo(nombre, modulo)
                .orElseGet(() -> {
                    // Crear una nueva actividad si no existe
                    Actividad nueva = new Actividad();
                    nueva.setNombre(nombre);
                    nueva.setModulo(modulo);
                    return actividadRepo.save(nueva);
                });
        System.out.println("Procesando archivo Excel para la actividad: " + actividad.getNombre());

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // saltar encabezado
            try {
                String dni = getStringValue(row.getCell(2));
                String celular = getStringValue(row.getCell(3));
                String nombreCompleto = getStringValue(row.getCell(4));
                String edadStr = getStringValue(row.getCell(5));
                String distrito = getStringValue(row.getCell(7));
                String carrera = getStringValue(row.getCell(7));

                if (dni.isBlank() || nombreCompleto.isBlank()) {
                    System.out.println("✘ Fila " + row.getRowNum() + " omitida (DNI o nombre vacío)");
                    continue;
                }

                Voluntario voluntario = voluntarioRepository.findByDni(dni)
                        .orElseGet(() -> {
                            Voluntario v = new Voluntario();
                            v.setDni(dni);
                            return v;
                        });

                voluntario.setNombreCompleto(nombreCompleto);
                voluntario.setCelular(celular);
                try {
                    // Quitar palabras como "años", espacios, etc.
                    String edadLimpia = edadStr.replaceAll("[^0-9]", ""); // solo conserva dígitos
                    if (!edadLimpia.isBlank()) {
                        voluntario.setEdad(Integer.parseInt(edadLimpia));
                    } else {
                        voluntario.setEdad(null);
                    }
                } catch (Exception e) {
                    System.out.println("⚠ No se pudo procesar la edad: '" + edadStr + "' en fila " + row.getRowNum());
                    voluntario.setEdad(null);
                }
                voluntario.setDistrito(distrito);
                voluntario.setCarreraUniversitaria(carrera);
                voluntarioRepository.save(voluntario);

                VoluntarioActividad p = new VoluntarioActividad();
                p.setVoluntario(voluntario);
                p.setActividad(actividad);
                participacionRepo.save(p);

                System.out.println("✔ Voluntario registrado: " + nombreCompleto);
            } catch (Exception e) {
                System.out.println("✘ Error al procesar fila: " + row.getRowNum() + ". Causa: " + e.getMessage());
            }
        }
        workbook.close();
    }

    private String getStringValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue()); // elimina decimales
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }
}
