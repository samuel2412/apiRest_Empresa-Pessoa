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

import br.com.samuel.api.models.Usuario;
import br.com.samuel.api.repositorys.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@GetMapping
	public Page<Usuario> usuarios(@RequestParam(required = false) String nomeUsuario,
			@PageableDefault(sort="id", direction = Direction.ASC, page=0,size=10) Pageable paginacao) {
			
		
		if (nomeUsuario == null) {
			Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
			return usuarios;
		} else {
			Page<Usuario> usuarios = usuarioRepository.findByNome(nomeUsuario,paginacao);
			return usuarios;
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> detalheUsuario(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		}
		return ResponseEntity.notFound().build();

	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody @Valid Usuario form) {
		Optional<Usuario> optional = usuarioRepository.findById(id);
		if (optional.isPresent()) {
			Usuario usuario = form.atualizar(id, usuarioRepository);
			usuario.criptrografaSenha();
			return ResponseEntity.ok(usuario);
		}
		return ResponseEntity.notFound().build();	
		
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Usuario> excluir(@PathVariable Long id) {
		Optional<Usuario> optional = usuarioRepository.findById(id);
		if (optional.isPresent()) {
			usuarioRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();	
		
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Usuario> cadastrar(@RequestBody @Valid Usuario usuario, UriComponentsBuilder uriBuilder) {
		usuario.criptrografaSenha();
		
		usuarioRepository.save(usuario);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(usuario.getId()).toUri();

		return ResponseEntity.created(uri).body(usuario);
	}
	
	@PostMapping("/data")
	@Transactional
	public ResponseEntity<Usuario> cadastrarVarios(@RequestBody List<Usuario> usuarios, ModelMap map) {
	    for(Usuario s : usuarios) {
	    	s.criptrografaSenha();
			usuarioRepository.save(s);
	    }
	    return ResponseEntity.ok().build();
	   
	}
	

	
}
