
package com.libreria.controladores;

import com.libreria.entidades.Editorial;
import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.EditorialServicio;
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
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    
    @GetMapping("/adminEditorial")
    public String adminEditorial() {
        return "adminEditorial.html";
    }
    
    //*********Levanta las paginas listadoras****************
    @GetMapping("/listarEditorial")
    public String listarEditorial(ModelMap modelo) {
        List<Editorial> editoriales = editorialServicio.listarTodos();
        modelo.put("editoriales", editoriales);
        return "listarEditorial.html";
    }
    
     // +*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*METODOS POST+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*
    
    @PostMapping("/adminEditorial")
    public String cargarAdminEditorial(ModelMap modelo, @RequestParam String nombreEditorial) {

        try {

            editorialServicio.crear(nombreEditorial);
            modelo.put("exito2", "La Editorial " + nombreEditorial + " Se cargo exitosamente");
            return "adminEditorial.html";
        } catch (ErrorServicio ex) {
            modelo.put("ErrorEditorial", ex.getMessage());
            return "adminEditorial.html";
        }
    }
    
    //******************MODIFICAR EDITORIAL**********************
    @GetMapping("/modificarEditorial")
    public String modificarAutor(ModelMap modelo, @RequestParam String id) throws ErrorServicio {
        
         try {

            Editorial editorial = editorialServicio.buscarPorId(id);
            
            modelo.addAttribute("editoriaal",editorial);
            
            return "modificarEditorial.html";
        } catch (ErrorServicio ex) {
            modelo.put("ErrorServicio", ex.getMessage());
            return "modificarEditorial.html";
        }
    }
    
     @PostMapping("/actualizarEditorial")
    public String actualizarAutor(RedirectAttributes attr, @RequestParam String id, @RequestParam String nombre) throws ErrorServicio {
        
         try {

            editorialServicio.modificar(id, nombre);
            
            
        } catch (ErrorServicio ex) {
            attr.addFlashAttribute("ErrorServicio", ex.getMessage());
            return "listarEditorial.html";
        }
         attr.addFlashAttribute("exito", "Exito modificado");
         return "redirect:/listarEditorial";
    }
}
