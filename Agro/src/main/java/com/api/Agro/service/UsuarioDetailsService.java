package com.api.Agro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.Agro.model.Usuario;
import com.api.Agro.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username)
            .map(usuario -> User.builder()
                .username(usuario.getUsername())
                .password(usuario.getSenha()) // cuidado: é senha, não password
                .roles("USER") // atribui papel fixo
                .build()
            ).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado no banco"));
    }
}