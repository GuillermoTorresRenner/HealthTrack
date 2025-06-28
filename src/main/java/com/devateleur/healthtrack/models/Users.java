package com.devateleur.healthtrack.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Users {
    private String nombre;
    private double peso;

   

    public void actualizarPeso(double nuevoPeso) {
        /* Error lógico inicial
        this.peso = this.peso - 1;
        */

        //Corrección del error lógico
        this.peso = nuevoPeso; 
     
    }

    public void mostrarInformacion() {
        System.out.println("Usuario: " + nombre + ", Peso Actual: " + peso + " kg");
    }
 
    
}
