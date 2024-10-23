package com.futebas.servicousuario.infrastructure.security;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.futebas.servicousuario.infrastructure.entities.Jogador;
import com.futebas.servicousuario.infrastructure.entities.Usuario;
import com.futebas.servicousuario.infrastructure.repositories.EmpresarioRepository;
import com.futebas.servicousuario.infrastructure.repositories.JogadorRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // Repositório para acessar dados de usuário no banco de dados

    private final JogadorRepository repoJog;
    private final EmpresarioRepository repoEmp;

    public UserDetailsServiceImpl(JogadorRepository repoJog, EmpresarioRepository repoEmp) {
        this.repoJog = repoJog;
        this.repoEmp = repoEmp;
    }

    // Implementação do método para carregar detalhes do usuário pelo e-mail
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repoEmp.findOptByEmail(email)
                .map(emp -> (Usuario) emp)
                .orElseGet(() -> repoJog.findOptByEmail(email)
                .map(jog -> (Usuario) jog)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email)));

        // Defina a role com base no tipo de usuário
        String role = (usuario instanceof Jogador) ? "ROLE_JOGADOR" : "ROLE_EMPRESARIO";

        // Cria e retorna um objeto UserDetails com base no usuário encontrado
        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities(role) // Adiciona a role às autoridades
                .build();
    }
}
