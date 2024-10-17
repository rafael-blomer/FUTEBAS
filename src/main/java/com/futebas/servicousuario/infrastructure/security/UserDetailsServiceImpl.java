package com.futebas.servicousuario.infrastructure.security;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        // Busca o usuário no banco de dados pelo e-mail
    	Usuario usuario = repoEmp.findOptByEmail(email)
    	        .map(emp -> (Usuario) emp) // Faz o cast para Usuario
    	        .orElseGet(() -> repoJog.findOptByEmail(email)
    	        .map(jog -> (Usuario) jog) // Faz o cast para Usuario
    	        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email)));

        // Cria e retorna um objeto UserDetails com base no usuário encontrado
        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getEmail()) // Define o nome de usuário como o e-mail
                .password(usuario.getSenha()) // Define a senha do usuário
                .build(); // Constrói o objeto UserDetails
    }
}
