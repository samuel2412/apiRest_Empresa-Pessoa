package br.com.samuel.api.controllers;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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

import br.com.samuel.api.models.Empresa;
import br.com.samuel.api.repositorys.EmpresaRepository;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	@GetMapping
	@Cacheable("listaEmpresas")
	public Page<Empresa> empresas(@RequestParam(required = false) String nomeEmpresa,
			@PageableDefault(sort="id", direction = Direction.ASC, page=0,size=10) Pageable paginacao) {
			
		
		if (nomeEmpresa == null) {
			Page<Empresa> empresas = empresaRepository.findAll(paginacao);
			return empresas;
		} else {
			Page<Empresa> empresas = empresaRepository.findByNome(nomeEmpresa,paginacao);
			return empresas;
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Empresa> detalheEmpresa(@PathVariable Long id) {
		Optional<Empresa> empresa = empresaRepository.findById(id);
		if (empresa.isPresent()) {
			return ResponseEntity.ok(empresa.get());
		}
		return ResponseEntity.notFound().build();

	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value="listaEmpresas", allEntries = true)
	public ResponseEntity<Empresa> atualizar(@PathVariable Long id, @RequestBody @Valid Empresa form) {
		Optional<Empresa> optional = empresaRepository.findById(id);
		if (optional.isPresent()) {
			Empresa empresa = form.atualizar(id, empresaRepository);
			return ResponseEntity.ok(empresa);
		}
		return ResponseEntity.notFound().build();	
		
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value="listaEmpresas", allEntries = true)
	public ResponseEntity<Empresa> excluir(@PathVariable Long id) {
		Optional<Empresa> optional = empresaRepository.findById(id);
		if (optional.isPresent()) {
			empresaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();	
		
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value="listaEmpresas", allEntries = true)
	public ResponseEntity<Empresa> cadastrar(@RequestBody @Valid Empresa empresa, UriComponentsBuilder uriBuilder) {

		empresaRepository.save(empresa);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(empresa.getId()).toUri();

		return ResponseEntity.created(uri).body(empresa);

	}

	
}
