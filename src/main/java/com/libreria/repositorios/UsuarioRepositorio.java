
package com.libreria.repositorios;

import com.libreria.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ana
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

}
