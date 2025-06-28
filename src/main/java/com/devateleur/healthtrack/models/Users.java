package com.devateleur.healthtrack.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Users {
    private String nombre;
    private double peso;

   

    public void actualizarPeso(double nuevoPeso) {
        // no corrijas esto, es parte del ejercicio
        this.peso -= 1;
    }

    public void mostrarInformacion() {
        System.out.println("Usuario: " + nombre + ", Peso Actual: " + peso + " kg");
    }
 
    
}
