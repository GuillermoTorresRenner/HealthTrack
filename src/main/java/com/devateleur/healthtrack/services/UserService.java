package com.devateleur.healthtrack.services;

import org.springframework.stereotype.Service;
import com.devateleur.healthtrack.models.Users;

@Service
public class UserService {
    
    // Singleton estático para asegurar una única instancia global que persiste entre requests
    private static volatile Users usuarioGlobal = null;
    
    public UserService() {
        // Inicializar solo si no existe
        if (usuarioGlobal == null) {
            synchronized (UserService.class) {
                if (usuarioGlobal == null) {
                    usuarioGlobal = new Users("Juan", 70.0);
                }
            }
        }
    }
    
    public Users getUsuario() {
        // Doble verificación para asegurar inicialización
        if (usuarioGlobal == null) {
            synchronized (UserService.class) {
                if (usuarioGlobal == null) {
                    usuarioGlobal = new Users("Juan", 70.0);
                }
            }
        }
        return usuarioGlobal;
    }
    
    public void actualizarPeso(double nuevoPeso) {
        // Asegurar inicialización antes de usar
        if (usuarioGlobal == null) {
            synchronized (UserService.class) {
                if (usuarioGlobal == null) {
                    usuarioGlobal = new Users("Juan", 70.0);
                }
            }
        }
        usuarioGlobal.actualizarPeso(nuevoPeso);
    }
    
    public void resetearUsuario() {
        synchronized (UserService.class) {
            usuarioGlobal = new Users("Juan", 70.0);
        }
    }
}
