package br.com.ticktag.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_TIPO_TICKET")
public class TipoTicketVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long idEvento;
	private Long lote;
	private Long qtdLote;
	private Double valorTicket;
	private Double valorMeiaTicket;
	private String tipoTicket;
	private String statusTicket;
	
	@Id
	@SequenceGenerator(name="TB_TIPO_TICKET_SEQ", sequenceName="TB_TIPO_TICKET_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="TB_TIPO_TICKET_SEQ")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="ID_EVENTO")
	public Long getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}
	
	@Column(name="LOTE")
	public Long getLote() {
		return lote;
	}
	public void setLote(Long lote) {
		this.lote = lote;
	}
	
	@Column(name="QTD_LOTE")
	public Long getQtdLote() {
		return qtdLote;
	}
	public void setQtdLote(Long qtdLote) {
		this.qtdLote = qtdLote;
	}
	
	@Column(name="VALOR_TICKET")
	public Double getValorTicket() {
		return valorTicket;
	}
	public void setValorTicket(Double valorTicket) {
		this.valorTicket = valorTicket;
	}
	
	@Column(name="VALOR_MEIA_TICKET")
	public Double getValorMeiaTicket() {
		return valorMeiaTicket;
	}
	public void setValorMeiaTicket(Double valorMeiaTicket) {
		this.valorMeiaTicket = valorMeiaTicket;
	}
	
	@Column(name="TIPO_TICKET")
	public String getTipoTicket() {
		return tipoTicket;
	}
	public void setTipoTicket(String tipoTicket) {
		this.tipoTicket = tipoTicket;
	}
	
	@Column(name="STATUS_TICKET")
	public String getStatusTicket() {
		return statusTicket;
	}
	public void setStatusTicket(String statusTicket) {
		this.statusTicket = statusTicket;
	}
	
	

}
