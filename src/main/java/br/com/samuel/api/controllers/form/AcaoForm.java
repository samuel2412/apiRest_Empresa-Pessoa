package br.com.samuel.api.controllers.form;

import br.com.samuel.api.models.Acao;
import br.com.samuel.api.models.Empresa;
import br.com.samuel.api.models.Usuario;
import br.com.samuel.api.repositorys.EmpresaRepository;
import br.com.samuel.api.repositorys.UsuarioRepository;

public class AcaoForm {

	
	
    private int quantidade;
    private Long empresa_id;
    private Long dono_id;
    
    
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public Long getEmpresa_id() {
		return empresa_id;
	}
	public void setEmpresa_id(Long empresa_id) {
		this.empresa_id = empresa_id;
	}
	public Long getDono_id() {
		return dono_id;
	}
	public void setDono_id(Long dono_id) {
		this.dono_id = dono_id;
	}
    
	public Acao converter(EmpresaRepository empresaRepository, UsuarioRepository usuarioRepository) {
		//System.out.println(toString());
		Empresa empresa = empresaRepository.getOne(getEmpresa_id());
		Usuario dono = usuarioRepository.getOne(getDono_id());
		
		
		return new Acao(getQuantidade(),empresa,dono);
	}
	@Override
	public String toString() {
		return "AcaoForm [quantidade=" + quantidade + ", empresa_id=" + empresa_id + ", dono_id=" + dono_id + "]";
	}
	
	
	

}
