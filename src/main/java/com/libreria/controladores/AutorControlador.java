package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.AutorServicio;
import com.libreria.servicios.EditorialServicio;
import com.libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class AutorControlador {

    
    @Autowired
    private AutorServicio autorServicio;

  
    @GetMapping("/adminAutor")
    public String adminAutor() {
        return "adminAutor.html";
    }

    @GetMapping("/modificarAutor")
    public String modificarAutor(ModelMap modelo, @RequestParam String id) throws ErrorServicio {
        
         try {

            Autor autor = autorServicio.buscarPorId(id);
            
            modelo.addAttribute("autor",autor);
            
            return "modificarAutor.html";
        } catch (ErrorServicio ex) {
            modelo.put("ErrorServicio", ex.getMessage());
            return "modificarAutor.html";
        }
    }
    
     @PostMapping("/actualizarAutor")
    public String actualizarAutor(RedirectAttributes attr, @RequestParam String id, @RequestParam String nombre) throws ErrorServicio {
        
         try {

            autorServicio.modificar(id, nombre);
            
            
        } catch (ErrorServicio ex) {
            attr.addFlashAttribute("ErrorServicio", ex.getMessage());
            return "listarAutor.html";
        }
         attr.addFlashAttribute("exito", "Exito modificado");
         return "redirect:/listarAutor";
    }
    
    
    
        
    
    
     @GetMapping("/listarAutor")
    public String listarAutor(ModelMap modelo) {

        List<Autor> autores = autorServicio.listarTodos();
        modelo.put("autores", autores);

        return "listarAutor.html";
    }
    
    @PostMapping("/adminAutor")
    public String cargarAdminAutor(ModelMap modelo, @RequestParam String nombreApellido) throws ErrorServicio{

        try {

            autorServicio.crear(nombreApellido);
            modelo.put("exito", "El Autor " + nombreApellido + " Se cargo exitosamente");
            return "adminAutor.html";
        } catch (ErrorServicio ex) {
            modelo.put("ErrorServicio", ex.getMessage());
            return "adminAutor.html";
        }
    }

}
