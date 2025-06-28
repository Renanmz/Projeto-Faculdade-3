package com.api.Agro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.Agro.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}