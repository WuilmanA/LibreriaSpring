/*
Controlador Principal  por donde Arranca el sistema
 */
package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
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

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServivio;

    @Autowired
    private LibroServicio libroServicio;

// ******METODOS GET++++++++++++++++++++
    //****Levanta el Index********
    @GetMapping
    public String index() {
        return "index.html";
    }

    //****Levanta las paginas administradoras********
  
    @GetMapping("/adminEditorial")
    public String adminEditorial() {
        return "adminEditorial.html";
    }
    
    

    @GetMapping("/adminLibro")
    public String adminLibro(ModelMap modelo) {

        List<Autor> listAutores = autorServicio.listarTodos();
        modelo.put("autores", listAutores);

        List<Editorial> Editoriales = editorialServivio.listarTodos();
        modelo.put("editoriales", Editoriales);

        return "adminLibro.html";
    }

    //*********Levanta las paginas listadoras****************
    @GetMapping("/listarEditorial")
    public String listarEditorial(ModelMap modelo) {
        List<Editorial> editoriales = editorialServivio.listarTodos();
        modelo.put("editoriales", editoriales);
        return "listarEditorial.html";
    }

    @GetMapping("/listarLibros")
    public String listarLibros(ModelMap modelo) {
        List<Libro>libros=libroServicio.listarTodos();
        System.out.println(libros);
        modelo.put("libros",libros);
        return "listarLibros.html";
    }

   

    //*********Levanta las paginas Registro y Login*********
    @GetMapping("/loging")
    public String login() {
        return "loging.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registrar.html";
    }

    // +*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*METODOS POST+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*
    
    @PostMapping("/adminEditorial")
    public String cargarAdminEditorial(ModelMap modelo, @RequestParam String nombreEditorial) {

        try {

            editorialServivio.crear(nombreEditorial);
            modelo.put("exito2", "La Editorial " + nombreEditorial + " Se cargo exitosamente");
            return "adminEditorial.html";
        } catch (ErrorServicio ex) {
            modelo.put("ErrorEditorial", ex.getMessage());
            return "adminEditorial.html";
        }
    }

    @PostMapping("/adminLibro")
    public String cargarAdminLibro(ModelMap modelo, @RequestParam Long isbn,
            @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares,
            @RequestParam String idAutor, @RequestParam String idEditorial) {
        //Long isbn, String titulo, Integer anio, Integer ejemplares, String idAutor, String idEditorial

        List<Autor> listAutores = autorServicio.listarTodos();
        modelo.put("autores", listAutores);

        List<Editorial> Editoriales = editorialServivio.listarTodos();
        modelo.put("editoriales", Editoriales);

        try {
            libroServicio.crear(isbn, titulo, anio, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "El Libro " + titulo + " Se cargo exitosamente");
            return "adminLibro.html";
        } catch (ErrorServicio ex) {
            modelo.put("ErrorLibro", ex.getMessage());

            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);

            listAutores = autorServicio.listarTodos();
            modelo.put("autores", listAutores);

            Editoriales = editorialServivio.listarTodos();
            modelo.put("editoriales", Editoriales);

            return "adminLibro.html";
        }

    }

}
