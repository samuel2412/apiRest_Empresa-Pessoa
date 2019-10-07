package br.com.samuel.api.repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.samuel.api.models.Acao;

public interface AcaoRepository extends JpaRepository<Acao, Long>{

	Page<Acao> findByDonoId(Long donoId, Pageable paginacao);

}
