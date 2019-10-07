package br.com.samuel.api.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.samuel.api.controllers.dto.AcaoDto;
import br.com.samuel.api.controllers.form.AcaoAtualizarForm;
import br.com.samuel.api.controllers.form.AcaoForm;
import br.com.samuel.api.models.Acao;
import br.com.samuel.api.repositorys.AcaoRepository;
import br.com.samuel.api.repositorys.EmpresaRepository;
import br.com.samuel.api.repositorys.UsuarioRepository;

@RestController
@RequestMapping("/acao")
public class AcaoController {
	
	@Autowired
	AcaoRepository acaoRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	
	
	@GetMapping
	@Transactional
	public Page<AcaoDto> acoes(@RequestParam(required = false) Long donoId,
			@PageableDefault(sort="id", direction = Direction.ASC, page=0,size=10) Pageable paginacao) {
		
		if (donoId == null) {
			Page<Acao> acoes = acaoRepository.findAll(paginacao);
			return AcaoDto.converter(acoes);
		} else {
			Page<Acao> acoes = acaoRepository.findByDonoId(donoId,paginacao);
			return AcaoDto.converter(acoes);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AcaoDto> detalheAcao(@PathVariable Long id) {
		Optional<Acao> acao = acaoRepository.findById(id);
		if (acao.isPresent()) {
			return ResponseEntity.ok(new AcaoDto(acao.get()));
		}
		return ResponseEntity.notFound().build();

	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<AcaoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AcaoAtualizarForm form) {
		Optional<Acao> optional = acaoRepository.findById(id);
		if (optional.isPresent()) {
			Acao acao = form.atualizar(id, acaoRepository,empresaRepository,usuarioRepository);
			return ResponseEntity.ok(new AcaoDto(acao));
		}
		return ResponseEntity.notFound().build();	
		
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<AcaoDto> excluir(@PathVariable Long id) {
		Optional<Acao> optional = acaoRepository.findById(id);
		if (optional.isPresent()) {
			acaoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();	
		
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<AcaoDto> cadastrar(@RequestBody @Valid AcaoForm form, UriComponentsBuilder uriBuilder) {
		
		Acao acao = form.converter(empresaRepository,usuarioRepository);
		//System.out.print(acao.toString());
		acaoRepository.save(acao);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(acao.getId()).toUri();

		return ResponseEntity.created(uri).body(new AcaoDto(acao));
	}
	

	@PostMapping("/data")
	@Transactional
	public ResponseEntity<AcaoDto> cadastrarVarios(@RequestBody List<AcaoForm> acoes, ModelMap map) {
	    for(AcaoForm form : acoes) {
	    	Acao acao = form.converter(empresaRepository,usuarioRepository);
	    	acaoRepository.save(acao);
			
	    }
	    return ResponseEntity.ok().build();
	   
	}
	
	
	

}
