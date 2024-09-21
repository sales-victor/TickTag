package br.com.ticktag.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_EVENTO")
public class EventoVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nomeEvento;
	private String statusEvento;
	private Date dataEvento;
	private EnderecoVO enderecoVO;
	private Set<TipoTicketVO> tickets = new HashSet<>();;
	private Long lotacaoMaxima;
	private Long classificacaoIdade;
	
	@Id
	@SequenceGenerator(name="TB_EVENTO_SEQ", sequenceName="TB_EVENTO_SEQ", allocationSize=1)
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
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="ID_EVENTO", referencedColumnName = "ID", insertable = false, updatable=false)
	public EnderecoVO getEnderecoVO() {
		return enderecoVO;
	}
	public void setEnderecoVO(EnderecoVO enderecoVO) {
		this.enderecoVO = enderecoVO;
	}
	
	@Column(name="STATUS_EVENTO")
	public String getStatusEvento() {
		return statusEvento;
	}
	public void setStatusEvento(String statusEvento) {
		this.statusEvento = statusEvento;
	}
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="ID_EVENTO", referencedColumnName = "ID", insertable = false, updatable=false)
	public Set<TipoTicketVO> getTickets() {
		return tickets;
	}
	public void setTickets(Set<TipoTicketVO> tickets) {
		this.tickets = tickets;
	}
	
	

}
