package com.devateleur.healthtrack.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Users {
    private String nombre;
    private double peso;

   

    public void actualizarPeso(double nuevoPeso) {
        // Error detectado: el método debería asignar el nuevo peso, pero actualmente resta 1 al peso actual
        // this.peso -= 1; // Esta línea es incorrecta, debería ser unasignación directa
        // Corrección del error lógico
        this.peso = nuevoPeso; // Asignar el nuevo peso directamente
    }

    public void mostrarInformacion() {
        System.out.println("Usuario: " + nombre + ", Peso Actual: " + peso + " kg");
    }
 
    
}
