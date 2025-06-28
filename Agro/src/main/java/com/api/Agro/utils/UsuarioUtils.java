package com.api.Agro.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.api.Agro.model.Usuario;
import com.api.Agro.repository.UsuarioRepository;

@Component
public class UsuarioUtils {

    @Bean
    public CommandLineRunner criarAdminSeNaoExistir(UsuarioRepository repository, PasswordEncoder encoder) {
        return args -> {
            if (repository.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setSenha(encoder.encode("1234"));
                repository.save(admin);
                System.out.println("✅ Usuário admin criado com sucesso.");
            }
        };
    }
}
