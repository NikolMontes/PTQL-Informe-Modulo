package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication(scanBasePackages = "org.example")
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        SpringApplication.run(Main.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void imprimirURL() {
        String port = System.getProperty("server.port", "8082"); // usa el puerto del properties
        System.out.println("✅ Aplicación iniciada correctamente.");
        System.out.println("🌐 URL de acceso: http://localhost:" + port + "/");
    }
}