package com.libreria.servicios;
import com.libreria.entidades.Usuario;
import com.libreria.enums.Rol;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServicios {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Transactional(rollbackFor = {Exception.class})
    public void crear(String nombreApellido, String email, String password, String confirmarPassword) throws ErrorServicio {
        
        validarPassword(nombreApellido, email, password, confirmarPassword);
        
        Usuario usuario = new Usuario();

        usuario.setNombreApellido(nombreApellido);
        usuario.setEmail(email);
        
        String passwordEncriptado = new BCryptPasswordEncoder().encode(password);
        usuario.setPassword(passwordEncriptado);
        
        usuario.setRol(Rol.ADMIN);
        
        usuarioRepositorio.save(usuario);
  
    }
    
    private void validarPassword(String nombreApellido, String email, String passWord, String confirmarPassword) throws ErrorServicio{
        
        if (passWord != confirmarPassword) {
            throw new ErrorServicio("La contraseña no coincide");
        }
        
        if (passWord.isEmpty() || confirmarPassword.isEmpty() || nombreApellido.isEmpty() || email.isEmpty() ) {
            throw new ErrorServicio("No puede haber campos vacios");
        }
    }
}
