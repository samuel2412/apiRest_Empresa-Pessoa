package br.com.samuel.api.controllers.form;

import br.com.samuel.api.models.Acao;
import br.com.samuel.api.models.Empresa;
import br.com.samuel.api.models.Usuario;
import br.com.samuel.api.repositorys.AcaoRepository;
import br.com.samuel.api.repositorys.EmpresaRepository;
import br.com.samuel.api.repositorys.UsuarioRepository;

public class AcaoAtualizarForm {
	
	private int quantidade;
	private Long dono_id;
	private Long empresa_id;

	

	public int getQuantidade() {
		return quantidade;
	}





	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}





	public Long getDono_id() {
		return dono_id;
	}





	public void setDono_id(Long dono_id) {
		this.dono_id = dono_id;
	}





	public Long getEmpresa_id() {
		return empresa_id;
	}





	public void setEmpresa_id(Long empresa_id) {
		this.empresa_id = empresa_id;
	}



	public Acao atualizar(Long id, AcaoRepository acaoRepository,EmpresaRepository empresaRepository, UsuarioRepository usuarioRepository) {
		Acao acao = acaoRepository.getOne(id);
		Empresa empresa = empresaRepository.getOne(getEmpresa_id());
		Usuario dono = usuarioRepository.getOne(getDono_id());
		
		acao.setDono(dono);
		acao.setEmpresa(empresa);
		acao.setQuantidade(getQuantidade());
		
		
		return acao;
	}

}
