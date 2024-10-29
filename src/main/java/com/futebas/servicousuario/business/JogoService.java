package com.futebas.servicousuario.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.futebas.servicousuario.business.converter.Converter;
import com.futebas.servicousuario.business.dtos.in.JogoDtoRequest;
import com.futebas.servicousuario.business.dtos.out.CampoDtoResponse;
import com.futebas.servicousuario.business.dtos.out.JogoDtoResponse;
import com.futebas.servicousuario.business.exceptions.MaxPlayersReachedException;
import com.futebas.servicousuario.business.exceptions.ObjectNotFoundException;
import com.futebas.servicousuario.business.exceptions.PlayerInGameException;
import com.futebas.servicousuario.business.exceptions.TimeNotAllowedException;
import com.futebas.servicousuario.business.exceptions.WithoutPermissionException;
import com.futebas.servicousuario.infrastructure.entities.Campo;
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
	@Autowired
	private Converter converter;
	@Autowired
	private EmpresarioService empService;

	public JogoDtoResponse criarJogo(JogoDtoRequest jogo, String token) {
		String email = jwt.extrairEmailToken(token.substring(7));
		Jogador jogador = jogadorRepo.findByEmail(email);
		Jogo entity = converter.requestParaJogoEntity(jogo);
		entity.setCriador(jogador);
		entity.getJogadores().add(jogador);
		horarioValido(entity);
		return converter.paraJogoDtoResponse(repo.save(entity), empRepo.findByCampos_Id(entity.getCampo().getId()));
	}

	public JogoDtoResponse jogoUpdate(JogoDtoRequest novoDto, String token, String idJogo) {
		verificarPermissao(token, idJogo);
		Jogo velho = buscarDadosJogoEntity(idJogo);
		LocalDateTime horarioOriginal = velho.getDataHora();
		updateData(velho, converter.requestParaJogoEntity(novoDto));
		if (!velho.getDataHora().equals(horarioOriginal)) {
			horarioValido(velho);
		}
		return converter.paraJogoDtoResponse(repo.save(velho), empRepo.findByCampos_Id(velho.getCampo().getId()));
	}

	public JogoDtoResponse buscarDadosJogoDTO(String idJogo) {
		Jogo jogo = repo.findById(idJogo).orElseThrow(() -> new ObjectNotFoundException("Jogo não encontrado."));
		return converter.paraJogoDtoResponse(jogo, empRepo.findByCampos_Id(jogo.getCampo().getId()));
	}

	public void cancelarJogo(String idJogo, String token) {
		verificarPermissao(token, idJogo);
		repo.delete(buscarDadosJogoEntity(idJogo));
	}

	public JogoDtoResponse abrirInscricaoJogo(String idJogo, String token) {
		verificarPermissao(token, idJogo);
		Jogo jogo = buscarDadosJogoEntity(idJogo);
		jogo.setAbertoParaJogadores(true);
		return converter.paraJogoDtoResponse(repo.save(jogo), empRepo.findByCampos_Id(jogo.getCampo().getId()));
	}

	public JogoDtoResponse fecharInscricaoJogo(String idJogo, String token) {
		verificarPermissao(token, idJogo);
		Jogo jogo = buscarDadosJogoEntity(idJogo);
		jogo.setAbertoParaJogadores(false);
		return converter.paraJogoDtoResponse(repo.save(jogo), empRepo.findByCampos_Id(jogo.getCampo().getId()));
	}

	public List<JogoDtoResponse> jogosAbertos() {
		List<Jogo> todosOsJogos = repo.findAll();
		List<JogoDtoResponse> jogosAbertos = new ArrayList<JogoDtoResponse>();
		for (Jogo i : todosOsJogos) {
			if (i.getAbertoParaJogadores() == true)
				jogosAbertos.add(converter.paraJogoDtoResponse(i, empRepo.findByCampos_Id(i.getCampo().getId())));
		}
		return jogosAbertos;
	}

	public List<JogoDtoResponse> jogosAbertosDia(LocalDate data) {
		LocalDateTime inicioDoDia = data.atStartOfDay();
		LocalDateTime fimDoDia = data.atTime(LocalTime.MAX);
		List<JogoDtoResponse> listJogosDto = new ArrayList<JogoDtoResponse>();
		List<Jogo> listJogosEntity = repo.findByDataHoraBetween(inicioDoDia, fimDoDia);
		for (Jogo i : listJogosEntity) {
			listJogosDto.add(converter.paraJogoDtoResponse(i, empRepo.findByCampos_Id(i.getCampo().getId())));
		}
		return listJogosDto;
	}

	public List<JogoDtoResponse> jogosMarcadosPorEmpresa(String token) {
		List<Jogo> todosOsJogos = repo.findAll();
		List<JogoDtoResponse> jogosPorEmpresa = new ArrayList<>();
		List<CampoDtoResponse> camposPorEmpresa = empService.getTodosCamposEmp(token);
		List<Campo> listCamposEntity = new ArrayList<>();
		for(CampoDtoResponse i : camposPorEmpresa) {
			listCamposEntity.add(converter.paraCampoEntity(i));
		}
		for(Jogo j : todosOsJogos) {
			for (Campo h : listCamposEntity) {
				if (h.equals(j.getCampo()))
					jogosPorEmpresa.add(converter.paraJogoDtoResponse(j, empRepo.findByCampos_Id(j.getCampo().getId())));
			}
		}
		return jogosPorEmpresa;
	}

	public JogoDtoResponse adicionarJogador(String cpfJogador, String idJogo, String token) {
		verificarPermissao(token, idJogo);
		Jogo jogo = buscarDadosJogoEntity(idJogo);
		for (Jogador i : jogo.getJogadores()) {
			if (i.getCpf().equals(cpfJogador))
				throw new PlayerInGameException("O jogador já esta nesse jogo");
		}
		if (jogo.getJogadores().size() < jogo.getNumeroMaximoJogadores())
			jogo.getJogadores().add(jogadorRepo.findByCpf(cpfJogador));
		else
			throw new MaxPlayersReachedException("O número máximo de jogadores para este jogo foi atingido.");
		return converter.paraJogoDtoResponse(repo.save(jogo), empRepo.findByCampos_Id(jogo.getCampo().getId()));
	}

	public JogoDtoResponse removerJogador(String cpfJogador, String idJogo, String token) {
		verificarPermissao(token, idJogo);
		Jogo jogo = buscarDadosJogoEntity(idJogo);
		Jogador jogadorRemovido = jogadorRepo.findByCpf(cpfJogador);
		if (jogadorRemovido == null)
			throw new ObjectNotFoundException("Jogador não encontrado.");
		Boolean jogadorNaLista = false;
		for (Jogador i : jogo.getJogadores()) {
			if (i.equals(jogadorRemovido)) {
				jogadorNaLista = true;
				break;
			}
		}
		if (!jogadorNaLista)
			throw new ObjectNotFoundException("Jogador não está na lista.");
		jogo.getJogadores().remove(jogadorRemovido);
		return converter.paraJogoDtoResponse(repo.save(jogo), empRepo.findByCampos_Id(jogo.getCampo().getId()));
	}
	
	public List<JogoDtoResponse> jogosMarcadosPorJogador(String token) {
		String email = jwt.extrairEmailToken(token.substring(7));
		Jogador jogador = jogadorRepo.findByEmail(email);
		List<JogoDtoResponse> listJogosDto = new ArrayList<JogoDtoResponse>();
		List<Jogo> listJogosEntity = repo.findByCriador(jogador);
		for (Jogo i : listJogosEntity) {
			listJogosDto.add(converter.paraJogoDtoResponse(i, empRepo.findByCampos_Id(i.getCampo().getId())));
		}
		return listJogosDto;
	}

	private void horarioValido(Jogo jogo) {
		if (jogo.getCampo().getHoraAbrir().isAfter(jogo.getDataHora().toLocalTime()))
			throw new TimeNotAllowedException("O jogo só pode ser marcado depois do estabelecimento abrir.");
		if (jogo.getCampo().getHoraFechar().isBefore(jogo.getDataHora().toLocalTime()))
			throw new TimeNotAllowedException("O jogo só pode ser marcado antes do estabelecimento fechar.");
		List<JogoDtoResponse> listJogosDia = jogosAbertosDia(jogo.getDataHora().toLocalDate());
		for (JogoDtoResponse i : listJogosDia) {
			if (jogo.getDataHora().equals(i.getDataHora()))
				throw new TimeNotAllowedException("Já existe um jogo marcado nesse horário.");
		}
	}

	private void verificarPermissao(String token, String idJogo) {
		String email = jwt.extrairEmailToken(token.substring(7));
		String role = jwt.extractRole(token.substring(7));
		Jogador jogador = jogadorRepo.findByEmail(email);
		Jogo jogo = buscarDadosJogoEntity(idJogo);
		if (!"EMPRESARIO".equals(role)) {
			if (jogador == null || !jogo.getCriador().getId().equals(jogador.getId()))
				throw new WithoutPermissionException("Você não tem permissão para fazer isso.");
		}
	}

	private Jogo updateData(Jogo antigo, Jogo novo) {
		if (novo.getDataHora() != null && !novo.getDataHora().equals(antigo.getDataHora())) {
			antigo.setDataHora(novo.getDataHora());
		}
		antigo.setNumeroMaximoJogadores(novo.getNumeroMaximoJogadores() != null ? novo.getNumeroMaximoJogadores()
				: antigo.getNumeroMaximoJogadores());
		return antigo;
	}

	private Jogo buscarDadosJogoEntity(String idJogo) {
		return repo.findById(idJogo).orElseThrow(() -> new ObjectNotFoundException("Jogo não encontrado."));
	}
}
