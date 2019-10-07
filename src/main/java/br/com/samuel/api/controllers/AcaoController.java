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

import br.com.samuel.api.models.Acao;
import br.com.samuel.api.models.Empresa;
import br.com.samuel.api.models.Usuario;
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
	public Page<Acao> acoes(@RequestParam(required = false) Long donoId,
			@PageableDefault(sort="id", direction = Direction.ASC, page=0,size=10) Pageable paginacao) {
		
		if (donoId == null) {
			Page<Acao> acoes = acaoRepository.findAll(paginacao);
			return acoes;
		} else {
			Page<Acao> acoes = acaoRepository.findByDonoId(donoId,paginacao);
			return acoes;
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Acao> detalheAcao(@PathVariable Long id) {
		Optional<Acao> acao = acaoRepository.findById(id);
		if (acao.isPresent()) {
			return ResponseEntity.ok(acao.get());
		}
		return ResponseEntity.notFound().build();

	}
	
//	@PutMapping("/{id}")
//	@Transactional
//	public ResponseEntity<Acao> atualizar(@PathVariable Long id, @RequestBody @Valid Acao form) {
//		Optional<Acao> optional = acaoRepository.findById(id);
//		if (optional.isPresent()) {
//			Acao acao = form.atualizar(id, acaoRepository);
//			return ResponseEntity.ok(acao);
//		}
//		return ResponseEntity.notFound().build();	
//		
//	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Acao> excluir(@PathVariable Long id) {
		Optional<Acao> optional = acaoRepository.findById(id);
		if (optional.isPresent()) {
			acaoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();	
		
	}
	
//	@PostMapping
//	@Transactional
//	public ResponseEntity<Acao> cadastrar(@RequestBody @Valid Acao acao, UriComponentsBuilder uriBuilder) {
//		Optional<Empresa> empresa = empresaRepository.findById(acao.getEmpresa().getId());
//		Optional<Usuario> dono = usuarioRepository.findById(acao.getDono().getId());
//		
//		if(empresa.isPresent() && dono.isPresent()) {
//			acaoRepository.save(acao);
//
//			URI uri = uriBuilder.path("/acao/{id}").buildAndExpand(acao.getId()).toUri();
//
//			return ResponseEntity.created(uri).body(acao);
//		}
//		return ResponseEntity.notFound().build();
//	}
//	
//	@PostMapping("/data")
//	@Transactional
//	public ResponseEntity<Usuario> cadastrarVarios(@RequestBody List<Acao> acoes, ModelMap map) {
//	    for(Acao acao : acoes) {
//	    	Optional<Empresa> empresa = empresaRepository.findById(acao.getEmpresa().getId());
//			Optional<Usuario> dono = usuarioRepository.findById(acao.getDono().getId());
//			
//			if(empresa.isPresent() && dono.isPresent()) {
//				acaoRepository.save(acao);
//			}
//	    }
//	    return ResponseEntity.ok().build();
//	   
//	}
//	
	
	

}
