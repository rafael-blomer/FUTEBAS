package com.futebas.servicousuario.infrastructure.entities;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.futebas.servicousuario.infrastructure.enums.QualidadeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Jogador extends Usuario implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private String cpf;
	private Boolean jogaDeGoleiro;
	private QualidadeEnum qualidade;
	private LocalDate dataNascimento;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPassword() {
		return getSenha();
	}
	@Override
	public String getUsername() {
		return getEmail();
	}
}
