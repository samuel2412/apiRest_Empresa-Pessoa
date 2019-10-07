package br.com.samuel.api.controllers.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import br.com.samuel.api.models.Acao;

public class AcaoDto {
	
	
	private Long id;
    private int quantidade;
    private Long empresa_id;
    private Long dono_id;
	private LocalDateTime dataCriacao;
	
	
	public AcaoDto(Acao acao) {
		this.id = acao.getId();
		this.quantidade = acao.getQuantidade();
		this.empresa_id = acao.getEmpresa().getId();
		this.dono_id = acao.getDono().getId();
		this.dataCriacao = acao.getDataCriacao();
	}


	public Long getId() {
		return id;
	}


	public int getQuantidade() {
		return quantidade;
	}


	public Long getEmpresa_id() {
		return empresa_id;
	}


	public Long getDono_id() {
		return dono_id;
	}


	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}


	public static Page<AcaoDto> converter(Page<Acao> acoes){
		return acoes.map(AcaoDto::new);
	}

	
	
	

}
