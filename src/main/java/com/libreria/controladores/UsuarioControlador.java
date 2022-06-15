package com.libreria.controladores;

import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.UsuarioServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicios usuarioServicios;

    @PostMapping("/registrar")
    public String registroDeUsuario(@RequestParam String nombreApellido,@RequestParam String email, @RequestParam String passWord,
            @RequestParam String confirmarPassWord) throws ErrorServicio{

        usuarioServicios.crear(nombreApellido, email, passWord, confirmarPassWord);
        
        return "index.html";
    }
}
