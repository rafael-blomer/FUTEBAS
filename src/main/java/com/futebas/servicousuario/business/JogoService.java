package com.futebas.servicousuario.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.futebas.servicousuario.business.exceptions.MaxPlayersReachedException;
import com.futebas.servicousuario.business.exceptions.ObjectNotFoundException;
import com.futebas.servicousuario.business.exceptions.PlayerInGameException;
import com.futebas.servicousuario.business.exceptions.TimeNotAllowedException;
import com.futebas.servicousuario.business.exceptions.WithoutPermissionException;
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

	// jogador
	public Jogo criarJogo(Jogo jogo, String token) {
		horarioValido(jogo);
		String email = jwt.extrairEmailToken(token);
		Jogador jogador = jogadorRepo.findByEmail(email);
		jogo.setCriador(jogador);
		return repo.save(jogo);
	}
	
	public Jogo jogoUpdate(Jogo novo, String token, String idJogo) {
		verificarPermissao(token, idJogo);
		Jogo velho = buscarDadosJogo(idJogo);
		updateData(velho, novo);
		horarioValido(velho);
		return repo.save(velho);
	}

	public Jogo buscarDadosJogo(String idJogo) {
		return repo.findById(idJogo).orElseThrow(() -> new ObjectNotFoundException("Jogo não encontrado."));
	}

	// jogador e emp
	public void cancelarJogo(String idJogo, String token) {
		verificarPermissao(token, idJogo);
		buscarDadosJogo(idJogo);
		repo.delete(buscarDadosJogo(idJogo));
	}

	// jogador
	public Jogo abrirInscricaoJogo(String idJogo, String token) {
		verificarPermissao(token, idJogo);
		Jogo jogo = buscarDadosJogo(idJogo);
		jogo.setAbertoParaJogadores(true);
		return repo.save(jogo);
	}

	// jogador/automatico
	public Jogo fecharInscricaoJogo(String idJogo, String token) {
		verificarPermissao(token, idJogo);
		Jogo jogo = buscarDadosJogo(idJogo);
		jogo.setAbertoParaJogadores(false);
		return repo.save(jogo);
	}

	// jogador
	public List<Jogo> jogosAbertos() {
		List<Jogo> todosOsJogos = repo.findAll();
		List<Jogo> jogosAbertos = new ArrayList<Jogo>();
		for (Jogo i : todosOsJogos) {
			if (i.getAbertoParaJogadores() == true)
				jogosAbertos.add(i);
		}
		return jogosAbertos;
	}

	// jogador
	public List<Jogo> jogosAbertosDia(LocalDate data) {
		LocalDateTime inicioDoDia = data.atStartOfDay();
		LocalDateTime fimDoDia = data.atTime(LocalTime.MAX);
		return repo.findByDataHoraBetween(inicioDoDia, fimDoDia);
	}

	// empresa
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

	// jogador
	public Jogo adicionarJogador(String cpfJogador, String idJogo, String token) {
		verificarPermissao(token, idJogo);
		Jogo jogo = buscarDadosJogo(idJogo);
		if (jogo.getJogadores().size() < jogo.getNumeroMaximoJogadores())
			jogo.getJogadores().add(jogadorRepo.findByCpf(cpfJogador));
		else
			throw new MaxPlayersReachedException("O número máximo de jogadores para este jogo foi atingido.");
		for (Jogador i : jogo.getJogadores()) {
			if (i.getCpf().equals(cpfJogador))
				throw new PlayerInGameException("O jogador já esta nesse jogo");
		}
		return repo.save(jogo);
	}

	// jogador
	public Jogo removerJogador(String cpfJogador, String idJogo, String token) {
		verificarPermissao(token, idJogo);
		Jogo jogo = buscarDadosJogo(idJogo);
		jogo.getJogadores().remove(jogadorRepo.findByCpf(cpfJogador));
		return repo.save(jogo);
	}

	private void horarioValido(Jogo jogo) {
		if (jogo.getCampo().getHoraAbrir().isAfter(jogo.getDataHora().toLocalTime()))
			throw new TimeNotAllowedException("O jogo só pode ser marcado depois do estabelecimento abrir.");
		if (jogo.getCampo().getHoraFechar().isBefore(jogo.getDataHora().toLocalTime()))
			throw new TimeNotAllowedException("O jogo só pode ser marcado antes do estabelecimento fechar.");
		List<Jogo> listJogosDia = jogosAbertosDia(jogo.getDataHora().toLocalDate());
		for (Jogo i : listJogosDia) {
			if (jogo.getDataHora().equals( i.getDataHora()))
				throw new TimeNotAllowedException("Já existe um jogo marcado nesse horário.");
		}
	}

	private void verificarPermissao(String token, String idJogo) {
		String email = jwt.extrairEmailToken(token);
		String role = jwt.extractRole(token);
		Jogador jogador = jogadorRepo.findByEmail(email);
		Jogo jogo = buscarDadosJogo(idJogo);
		if (!"EMPRESARIO".equals(role)) { 
			if (jogador == null || !jogo.getCriador().getId().equals(jogador.getId())) 
				throw new WithoutPermissionException("Você não tem permissão para fazer isso.");
		}
	}
	
	private Jogo updateData(Jogo antigo, Jogo novo) {
		antigo.setCriador(novo.getCriador() != null ? novo.getCriador() : antigo.getCriador());
		antigo.setDataHora(novo.getDataHora() != null ? novo.getDataHora() : antigo.getDataHora());
		antigo.setNumeroMaximoJogadores(novo.getNumeroMaximoJogadores() != null ? novo.getNumeroMaximoJogadores() : antigo.getNumeroMaximoJogadores());
		return antigo;
	}
}
