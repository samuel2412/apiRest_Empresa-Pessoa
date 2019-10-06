package br.com.samuel.api.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.samuel.api.models.Usuario;
import br.com.samuel.api.repositorys.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService{

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> optional = usuarioRepository.findByEmail(username);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		throw new UsernameNotFoundException("Dados Inválidos");
	}

}
