package com.libreria.servicios;

import com.libreria.entidades.Editorial;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

    @Autowired
    EditorialRepositorio editorialRepositorio;

    @Transactional(rollbackFor = {Exception.class})
    public void crear(String nombreEditorial) throws ErrorServicio {
        validar(nombreEditorial);

        Editorial editorial = new Editorial();

        editorial.setNombre(nombreEditorial);
        editorial.setAlta(true);

        editorialRepositorio.save(editorial);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void modificar(String id, String nombre) throws ErrorServicio {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("La Editorial no existe");
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void bajaEditorial(String id) throws ErrorServicio {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = new Editorial();

            editorial.setAlta(false);

            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("La Editorial no existe");
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void altaEditorial(String id) throws ErrorServicio {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = new Editorial();

            editorial.setAlta(true);

            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("La Editorial no existe");
        }
    }

    @Transactional(readOnly = true)
    public Editorial buscarPorId(String id) throws ErrorServicio {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            return editorial;

        } else {
            throw new ErrorServicio("No existe esa Editorial");
        }
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarTodos() {
        return editorialRepositorio.findAll();
    }

    public void validar(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
    }

}
