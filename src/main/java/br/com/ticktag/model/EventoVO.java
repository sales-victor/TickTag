package br.com.ticktag.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
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
	@Column(name="ID_EVENTO")
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
	
	@OneToOne
	@JoinColumn(name = "ID_EVENTO", referencedColumnName = "ID_EVENTO", insertable=false, updatable=false)
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
	
	@OneToMany
	@JoinColumn(name = "ID_EVENTO", referencedColumnName = "ID_EVENTO", insertable=false, updatable=false)
	public Set<TipoTicketVO> getTickets() {
		return tickets;
	}
	public void setTickets(Set<TipoTicketVO> tickets) {
		this.tickets = tickets;
	}
	
		
	public EventoVO() {
		super();
	}
	
	public EventoVO(String nomeEvento, String statusEvento, Date dataEvento,
			 Long lotacaoMaxima, Long classificacaoIdade) {
		super();
		this.nomeEvento = nomeEvento;
		this.statusEvento = statusEvento;
		this.dataEvento = dataEvento;
		this.lotacaoMaxima = lotacaoMaxima;
		this.classificacaoIdade = classificacaoIdade;
	}
	public EventoVO(Long id, String nomeEvento, String statusEvento, Date dataEvento,
			Long lotacaoMaxima, Long classificacaoIdade) {
		super();
		this.id = id;
		this.nomeEvento = nomeEvento;
		this.statusEvento = statusEvento;
		this.dataEvento = dataEvento;
		this.lotacaoMaxima = lotacaoMaxima;
		this.classificacaoIdade = classificacaoIdade;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(classificacaoIdade, dataEvento, enderecoVO, id, lotacaoMaxima, nomeEvento, statusEvento,
				tickets);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventoVO other = (EventoVO) obj;
		return Objects.equals(classificacaoIdade, other.classificacaoIdade)
				&& Objects.equals(dataEvento, other.dataEvento) && Objects.equals(enderecoVO, other.enderecoVO)
				&& Objects.equals(id, other.id) && Objects.equals(lotacaoMaxima, other.lotacaoMaxima)
				&& Objects.equals(nomeEvento, other.nomeEvento) && Objects.equals(statusEvento, other.statusEvento)
				&& Objects.equals(tickets, other.tickets);
	}
	
	
	
	
	
	

}
