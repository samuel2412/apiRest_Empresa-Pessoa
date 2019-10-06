package br.com.samuel.api.repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.samuel.api.models.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	Page<Empresa> findByNome(String nomeEmpresa, Pageable paginacao);

}
