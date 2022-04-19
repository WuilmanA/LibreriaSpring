
package com.libreria.repositorios;

import com.libreria.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String>{
    
//      @Query("SELECT l FROM autor l WHERE l.id = :id")
//    public Autor buscarPorId(@Param("id") String Id);
//
//    @Query("SELECT l FROM autor l WHERE l.editorial.id like: id")
//    public List<Autor> buscarPorIdEditorial (@Param("id") String ID);
//    
//     @Query("SELECT l FROM autor l WHERE l.autor.id like: id")
//     public List<Autor> buscarPorIdAutor (@Param("id") String ID);
    
}
