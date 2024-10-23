package com.futebas.servicousuario.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.futebas.servicousuario.business.exceptions.ObjectNotFoundException;
import com.futebas.servicousuario.business.exceptions.TimeNotAllowedException;
import com.futebas.servicousuario.infrastructure.entities.Campo;
import com.futebas.servicousuario.infrastructure.entities.Empresario;
import com.futebas.servicousuario.infrastructure.entities.Jogador;
import com.futebas.servicousuario.infrastructure.entities.Jogo;
import com.futebas.servicousuario.infrastructure.repositories.EmpresarioRepository;
import com.futebas.servicousuario.infrastructure.repositories.JogadorRepository;
import com.futebas.servicousuario.infrastructure.repositories.JogoRepository;
import com.futebas.servicousuario.infrastructure.security.JwtUtil;

@Service
public class JogoService {

	@Autowired
	private JogoRepository repo;
	@Autowired
	private EmpresarioRepository empRepo;
	@Autowired
	private JwtUtil jwt;
	@Autowired
	private JogadorRepository jogadorRepo;

	public Jogo criarJogo(Jogo jogo, String token) {
		horarioValido(jogo);
		String email = jwt.extrairEmailToken(token);
		Jogador jogador = jogadorRepo.findByEmail(email);
		jogo.setCriador(jogador);
		return repo.save(jogo);
	}

	private void horarioValido(Jogo jogo) {
		if (jogo.getCampo().getHoraAbrir().isAfter(jogo.getDataHora().toLocalTime()))
			throw new TimeNotAllowedException("O jogo só pode ser marcado depois do estabelecimento abrir.");
		if (jogo.getCampo().getHoraFechar().isBefore(jogo.getDataHora().toLocalTime()))
			throw new TimeNotAllowedException("O jogo só pode ser marcado antes do estabelecimento fechar.");
		List<Jogo> listJogosDia = jogosAbertosDia(jogo.getDataHora());
		for (Jogo i : listJogosDia) {
			if (jogo.getDataHora() == i.getDataHora())
				throw new TimeNotAllowedException("Já existe um jogo marcado nesse horário.");
		}
	}

	public Jogo buscarDadosJogo(String id) {
		return repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Jogo não encontrado."));
	}

	public void CancelarJogo(String id, String token) {
		buscarDadosJogo(id);
		repo.delete(buscarDadosJogo(id));
	}

	public Jogo abrirJogo(String id, String token) {
		Jogo jogo = buscarDadosJogo(id);
		jogo.setAbertoParaJogadores(true);
		return repo.save(jogo);
	}

	//
	public Jogo fecharJogo(String id, String token) {
		Jogo jogo = buscarDadosJogo(id);
		jogo.setAbertoParaJogadores(false);
		return repo.save(jogo);
	}

	//jogador
	public List<Jogo> jogosAbertos() {
		List<Jogo> todosOsJogos = repo.findAll();
		List<Jogo> jogosAbertos = new ArrayList<Jogo>();
		for (Jogo i : todosOsJogos) {
			if (i.getAbertoParaJogadores() == true)
				jogosAbertos.add(i);
		}
		return jogosAbertos;
	}

	//jogador
	public List<Jogo> jogosAbertosDia(LocalDateTime dataHora) {
		LocalDate dia = dataHora.toLocalDate();
		LocalDateTime inicioDoDia = dia.atStartOfDay();
		LocalDateTime fimDoDia = dia.atTime(LocalTime.MAX);
		return repo.findByDataHoraBetween(inicioDoDia, fimDoDia);
	}

	//empresa
	public List<Jogo> jogosMarcadosPorEmpresa(String token) {
		String email = jwt.extrairEmailToken(token);
		Empresario emp = empRepo.findByEmail(email);
		List<Jogo> todosOsJogos = repo.findAll();
		List<Jogo> jogosPorEmpresa = new ArrayList<Jogo>();
		for (Jogo i : todosOsJogos) {
			for (Campo a : emp.getCampos()) {
				if (i.getCampo().getId().equals(a.getId()))
					jogosPorEmpresa.add(i);
			}
		}
		return jogosPorEmpresa;
	}

	//jogador
	public Jogo adicionarJogador(Jogador jogador, String id) {
		Jogo jogo = buscarDadosJogo(id);
		jogo.getJogadores().add(jogador);
		return repo.save(jogo);
	}

	//jogador
	public Jogo removerJogador(Jogador jogador, String id) {
		Jogo jogo = buscarDadosJogo(id);
		jogo.getJogadores().remove(jogador);
		return repo.save(jogo);
	}
}
