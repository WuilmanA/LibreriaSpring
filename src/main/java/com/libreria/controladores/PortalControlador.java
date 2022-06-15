/*
Controlador Principal  por donde Arranca el sistema
 */
package com.libreria.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class PortalControlador {

    //****Levanta el Index********
//    @GetMapping("/")
//    public String index() {
//        return "index.html";
//    }
    
    @GetMapping("/registrar")
    public String registro() {
        return "registrar.html";
    }
    
    @GetMapping ("/loging")
    public String login() {
        return "loging.html";
    }

}
