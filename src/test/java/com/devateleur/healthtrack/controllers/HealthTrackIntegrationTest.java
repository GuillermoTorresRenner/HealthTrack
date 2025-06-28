package com.devateleur.healthtrack.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
    @DisplayName("IT-001: Debería cargar la página principal correctamente")
    void deberiaCargarPaginaPrincipal() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl() + "/", String.class);
        
        assertEquals(200, response.getStatusCode().value());
        String body = response.getBody();
        assertNotNull(body);
        assertTrue(body.contains("HealthTrack"));
        assertTrue(body.contains("Juan"));
        assertTrue(body.contains("70.0"));
    }
    
    @Test
    @Order(2)
    @DisplayName("IT-002: Debería mostrar el formulario de actualización de peso")
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