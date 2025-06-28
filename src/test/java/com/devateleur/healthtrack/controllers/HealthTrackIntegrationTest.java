package com.devateleur.healthtrack.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HealthTrackIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @LocalServerPort
    private int port;
    
    private String getBaseUrl() {
        return "http://localhost:" + port;
    }
    
    @Test
    @Order(1)
    @Tag("smoke")
    @DisplayName("Debería poder acceder a la página principal")
    void smokeTestPaginaPrincipal() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl() + "/", String.class);
        assertEquals(200, response.getStatusCode().value());
        String body = response.getBody();
        assertNotNull(body);
        assertTrue(body.contains("HealthTrack")); // Verificar que es la página correcta
    }
    
    @Test
    @Order(2)
    @DisplayName("DATOS: Debería enviar correctamente los atributos del usuario al body")
    void deberiaEnviarAtributosUsuarioAlBody() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl() + "/", String.class);
        
        assertEquals(200, response.getStatusCode().value());
        String body = response.getBody();
        assertNotNull(body);
        assertTrue(body.contains("Juan"), "El nombre del usuario debe aparecer en el HTML");
        assertTrue(body.contains("70.0"), "El peso del usuario debe aparecer en el HTML");
        assertTrue(body.contains("Hola Juan"), "El saludo personalizado debe aparecer");
        assertTrue(body.contains("kg"), "La unidad 'kg' debe aparecer para el peso");
    }
    /*
     * Valida que el formulario de actualización de peso se muestra correctamente
     * y que contiene los elementos esperados. ACTÚA COMO TEST DE REGRESIÓN
     */
    @Test
    @Tag("regression")
    @Order(3)
    @DisplayName("Debería mostrar el formulario de actualización de peso")
    void deberiaMostrarFormularioActualizacion() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl() + "/", String.class);
        
        String body = response.getBody();
        assertNotNull(body);
        assertTrue(body.contains("form"));
        assertTrue(body.contains("action=\"/actualizar\""));
        assertTrue(body.contains("method=\"post\""));
        assertTrue(body.contains("name=\"peso\""));
        assertTrue(body.contains("type=\"number\""));
    }
    
    
    
    
}