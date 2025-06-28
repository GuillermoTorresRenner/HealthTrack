package com.devateleur.healthtrack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.devateleur.healthtrack.services.UserService;

@Controller
public class UsersController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model){
        if (userService != null) {
            var usuario = userService.getUsuario();
            model.addAttribute("usuario", usuario);
        } else {
            // Fallback en caso de problemas
            model.addAttribute("usuario", null);
        }
        return "index";
    }

    @PostMapping("/actualizar")
    public String actualizarPeso(@RequestParam("peso") double nuevoPeso, RedirectAttributes redirectAttributes) {
        if (userService != null) {
            userService.actualizarPeso(nuevoPeso);
        }
        
        redirectAttributes.addFlashAttribute("mensaje", "Peso actualizado correctamente");
        redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        
        return "redirect:/";
    }

    @GetMapping("/debug")
    @ResponseBody
    public String debug(){
        if (userService != null) {
            var usuario = userService.getUsuario();
            return "Usuario: " + (usuario != null ? usuario.getNombre() + " - " + usuario.getPeso() + "kg" : "null");
        } else {
            return "UserService es null";
        }
    }
}