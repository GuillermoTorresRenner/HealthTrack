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
        // Verificar que hay un peso numérico presente (no el fallback 75.5 que indica usuario null)
        assertFalse(body.contains(">75.5<"), "No debe mostrar el valor fallback 75.5 que indica usuario null");
        assertTrue(body.contains("kg"), "La unidad 'kg' debe aparecer para el peso");
        assertTrue(body.contains("Hola Juan"), "El saludo personalizado debe aparecer");
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
    
    @Test
    @Order(4)
    @DisplayName("POST /actualizar: Debería actualizar el peso correctamente")
    void deberiaActualizarPesoEnPOST() throws Exception {
        // Primero obtener el peso inicial
        ResponseEntity<String> responseInicial = restTemplate.getForEntity(getBaseUrl() + "/", String.class);
        assertEquals(200, responseInicial.getStatusCode().value());
        String bodyInicial = responseInicial.getBody();
        assertNotNull(bodyInicial);
        
        // Enviar formulario de actualización
        org.springframework.util.MultiValueMap<String, String> params = new org.springframework.util.LinkedMultiValueMap<>();
        params.add("peso", "85.5");
        
        ResponseEntity<String> responsePost = restTemplate.postForEntity(
            getBaseUrl() + "/actualizar", 
            params, 
            String.class
        );
        
        // Debería hacer redirect (302/303) o retornar 200 si redirect interno
        assertTrue(responsePost.getStatusCode().is3xxRedirection() || responsePost.getStatusCode().is2xxSuccessful());
        
        // Verificar que después del POST, el peso se actualizó
        ResponseEntity<String> responseFinal = restTemplate.getForEntity(getBaseUrl() + "/", String.class);
        assertEquals(200, responseFinal.getStatusCode().value());
        String bodyFinal = responseFinal.getBody();
        assertNotNull(bodyFinal);
        
        // El peso debería ser 85.5, no 75.5 (que es el default cuando usuario es null)
        assertTrue(bodyFinal.contains("85.5"), 
            "El peso debería haberse actualizado a 85.5. Body: " + bodyFinal.substring(0, Math.min(500, bodyFinal.length())));
        assertFalse(bodyFinal.contains(">75.5<"), 
            "No debería mostrar el valor por defecto 75.5 después de la actualización");
    }
    
    @Test
    @Order(5)
    @DisplayName("DEBUG: Verificar estado del usuario después de actualización")
    void debugEstadoUsuarioDespuesDeActualizacion() throws Exception {
        // Verificar estado inicial
        ResponseEntity<String> debugInicial = restTemplate.getForEntity(getBaseUrl() + "/debug", String.class);
        System.out.println("Debug inicial: " + debugInicial.getBody());
        
        // Actualizar peso
        org.springframework.util.MultiValueMap<String, String> params = new org.springframework.util.LinkedMultiValueMap<>();
        params.add("peso", "85.5");
        
        ResponseEntity<String> responsePost = restTemplate.postForEntity(
            getBaseUrl() + "/actualizar", 
            params, 
            String.class
        );
        
        // Verificar estado después del POST
        ResponseEntity<String> debugDespues = restTemplate.getForEntity(getBaseUrl() + "/debug", String.class);
        System.out.println("Debug después: " + debugDespues.getBody());
        
        // Verificar página principal después del POST
        ResponseEntity<String> paginaPrincipal = restTemplate.getForEntity(getBaseUrl() + "/", String.class);
        System.out.println("Página principal contiene 85.5: " + paginaPrincipal.getBody().contains("85.5"));
        System.out.println("Página principal contiene 75.5: " + paginaPrincipal.getBody().contains("75.5"));
        
        // El debug debería mostrar el peso actualizado
        assertTrue(debugDespues.getBody().contains("85.5"), "El debug debe mostrar el peso actualizado");
    }
}