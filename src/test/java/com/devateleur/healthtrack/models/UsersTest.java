package com.devateleur.healthtrack.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {
    
    private Users usuario;
    
    @BeforeEach
    void setUp() {
        usuario = new Users("Juan", 70.0);
    }
    /*
     * Debería crear un usuario con nombre y peso correctos.
     * Este test verifica que el constructor de la clase Users
     * inicializa correctamente los atributos nombre y peso.
     * Si el constructor falla, este test fallará.
        ACTÚA COMO TEST DE REGRESIÓN
     */
    @Test
    @Tag("regression")
    @DisplayName("Debería crear un usuario con nombre y peso correctos")
    void deberiaCrearUsuarioCorrectamente() {
        assertEquals("Juan", usuario.getNombre());
        assertEquals(70.0, usuario.getPeso());
    }
    
    @Test@DisplayName("Debería actualizar el peso del usuario correctamente")
    void deberiaActualizarPesoCorrectamente() {
        double nuevoPeso = 80.0;
        usuario.actualizarPeso(nuevoPeso);
        // Assert - Este test fallará y mostrará el error
        assertEquals(nuevoPeso, usuario.getPeso(), "El peso no se actualizó correctamente");
    }
    
    
    
   
}