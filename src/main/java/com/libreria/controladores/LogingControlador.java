
package com.libreria.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LogingControlador {
    
    //*********Levanta las paginas Registro y Login*********
    @GetMapping("/loging")
    public String login() {
        return "loging.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registrar.html";
    }
}
