package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

    @Autowired //Al marcar este atributo como autowire le decimos al servidor de aplicaciones la inicialice el.
    private AutorRepositorio autorRepositorio;

    @Transactional(rollbackFor = {Exception.class})
    public Autor crear(String nombre) throws ErrorServicio {
        validar(nombre);

        Autor autor = new Autor();

        autor.setNombre(nombre);
        autor.setAlta(true);
        

        return autorRepositorio.save(autor);
  
    }

    @Transactional(rollbackFor = {Exception.class})
    public void modificar(String id, String nombre) throws ErrorServicio {

        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }else{
            throw new ErrorServicio("El librono existe");
        }

    }
    
    @Transactional(rollbackFor = {Exception.class})
    public void bajaAutor(String id) throws ErrorServicio {

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(false);       
            autorRepositorio.save(autor);
        }else{
            throw new ErrorServicio("El libro no existe");
        }

    }
    
    @Transactional(rollbackFor = {Exception.class})
     public void altaAutor(String id) throws ErrorServicio {

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(true);
            autorRepositorio.save(autor);
        }else{
            throw new ErrorServicio("El libro no existe");
        }

    }
     
    @Transactional(readOnly = true)
    public Autor buscarPorId(String id) throws ErrorServicio{
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Autor autor=respuesta.get();
            return autor;
            
        }else{
            throw new ErrorServicio("No existe ese Autor");
        }
    }
    
      @Transactional(readOnly = true)
    public List<Autor> listarTodos() {
        return autorRepositorio.findAll();
    }

    public void validar(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }

    }

}
