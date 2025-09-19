package com.example.Usuarios.repository;

import com.example.Usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<Usuario, Long> {

    boolean existsByUsername(String username);
    Optional<Usuario> findByUsername(String username);
}
