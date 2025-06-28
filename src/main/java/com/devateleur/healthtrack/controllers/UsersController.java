package com.devateleur.healthtrack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.devateleur.healthtrack.models.Users;

@Controller
public class UsersController {
    private  Users usersModel = new Users("Juan", 70.0);

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("usuario", usersModel);
        return "index";
    }

    @PostMapping("/actualizar")
    public String actualizarPeso(@RequestParam("peso") double nuevoPeso, RedirectAttributes redirectAttributes) {
        usersModel.actualizarPeso(nuevoPeso);
        
        redirectAttributes.addFlashAttribute("mensaje", "Peso actualizado correctamente");
        redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        
        return "redirect:/";
    }

}