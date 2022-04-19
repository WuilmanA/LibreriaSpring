package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.AutorRepositorio;
import com.libreria.repositorios.LibroRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service  // esta clase es un servicio Spring
public class LibroServicio {

    @Autowired //Al marcar este atributo como autowire le decimos al servidor de aplicaciones la inicialice el.
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Transactional(rollbackFor = {Exception.class})
    public void crear(Long isbn, String titulo, Integer anio, Integer ejemplares,
            String idAutor, String idEditorial) throws ErrorServicio {
        
        validar(isbn, titulo, anio, ejemplares, idAutor, idEditorial);

        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(0);
        libro.setRestantes(ejemplares);
        libro.setAlta(true);
        Autor autor = autorServicio.buscarPorId(idAutor);
        libro.setAutor(autor);
        Editorial editorial = editorialServicio.buscarPorId(idEditorial);
        libro.setEditorial(editorial);
        libro.setFecAlta(new Date());

        libroRepositorio.save(libro);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void modificar(String id, Long isbn, String titulo, Integer anio, Integer ejemplares,
            Integer ejemplaresPrestados, String idAutor, String idEditorial) throws ErrorServicio {

        validar(isbn, titulo, anio, ejemplares, idAutor, idEditorial);

        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplares(ejemplaresPrestados);
            libro.setRestantes(ejemplares - libro.getEjemplaresPrestados());
            Autor autor = autorServicio.buscarPorId(idAutor);
            libro.setAutor(autor);
            Editorial editorial = editorialServicio.buscarPorId(idEditorial);
            libro.setEditorial(editorial);
            libroRepositorio.save(libro);

        } else {
            throw new ErrorServicio("El libro No existe");
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public void bajaLibro(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();

            libro.setAlta(false);

            libroRepositorio.save(libro);

        } else {
            throw new ErrorServicio("El libro No existe");
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public void AltaLibro(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();

            libro.setAlta(true);

            libroRepositorio.save(libro);

        } else {
            throw new ErrorServicio("El libro No existe");
        }

    }

    @Transactional(readOnly = true)
    public List<Libro> listarTodos() {
        return libroRepositorio.findAll();
    }

    private void validar(Long isbn, String titulo, Integer anio, Integer ejemplares,
            String autor, String editorial) throws ErrorServicio {

        if (isbn == null || isbn <= 0) {
            throw new ErrorServicio("El ISBN no puede estar vacio o ser igual a 0");
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El nombre del titulo no puede estar vacio");
        }

        if (anio == null || anio <= 0) {
            throw new ErrorServicio("El año no puede estar vacio o ser igual a 0");
        }
        if (ejemplares == null || ejemplares <= 0) {
            throw new ErrorServicio("La cantidad de ejemplares no puede estar vacio o ser igual a 0");
        }

        if (autor == null || autor.isEmpty()) {
            throw new ErrorServicio("El autor no puede estar vacio");
        }

        if (editorial == null || editorial.isEmpty()) {
            throw new ErrorServicio("La editorial no puede estar vacio");
        }
    }

}
