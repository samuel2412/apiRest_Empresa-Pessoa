package br.com.samuel.api.repositorys;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.samuel.api.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Page<Usuario> findByNome(String nomeUsuario, Pageable paginacao);

	Optional<Usuario> findByEmail(String username);

}
