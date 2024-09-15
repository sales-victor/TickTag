package br.com.ticktag.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_EVENTO")
public class EventoVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nomeEvento;
	private Date dataEvento;
	private Long idEndereco;
	private Long lotacaoMaxima;
	private Long classificacaoIdade;
	
	@Id
	@SequenceGenerator(name="TB_EVENTO SEQ", sequenceName="TB_EVENTO_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="TB_EVENTO_SEQ")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="NOME_EVENTO")
	public String getNomeEvento() {
		return nomeEvento;
	}
	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}
	
	@Column(name="DATA_EVENTO")
	public Date getDataEvento() {
		return dataEvento;
	}
	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}
	
	@Column(name="ID_ENDERECO")
	public Long getIdEndereco() {
		return idEndereco;
	}
	public void setIdEndereco(Long idEndereco) {
		this.idEndereco = idEndereco;
	}
	
	@Column(name="LOTACAO_MAX")
	public Long getLotacaoMaxima() {
		return lotacaoMaxima;
	}
	public void setLotacaoMaxima(Long lotacaoMaxima) {
		this.lotacaoMaxima = lotacaoMaxima;
	}
	
	@Column(name="CLASSIFICACAO_IDADE")
	public Long getClassificacaoIdade() {
		return classificacaoIdade;
	}
	public void setClassificacaoIdade(Long classificacaoIdade) {
		this.classificacaoIdade = classificacaoIdade;
	}
	
	

}
