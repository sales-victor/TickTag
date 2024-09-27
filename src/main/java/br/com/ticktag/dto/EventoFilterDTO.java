package br.com.ticktag.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class EventoFilterDTO {
	
	private String nomeEvento;
	private String statusEvento;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date dataEvento;
	private Long classificacaoIdade;
	private Long lotacaoMaxima;
	
	public String getNomeEvento() {
		return nomeEvento;
	}
	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}
	public String getStatusEvento() {
		return statusEvento;
	}
	public void setStatusEvento(String statusEvento) {
		this.statusEvento = statusEvento;
	}
	public Date getDataEvento() {
		return dataEvento;
	}
	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}
	public Long getClassificacaoIdade() {
		return classificacaoIdade;
	}
	public void setClassificacaoIdade(Long classificacaoIdade) {
		this.classificacaoIdade = classificacaoIdade;
	}
	public Long getLotacaoMaxima() {
		return lotacaoMaxima;
	}
	public void setLotacaoMaxima(Long lotacaoMaxima) {
		this.lotacaoMaxima = lotacaoMaxima;
	}
	
	
}
