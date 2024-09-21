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
@Table(name = "TB_ENDERECO")
public class EnderecoVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long idEvento;
	private String nomeLogradouro;
	private String tipoLogradouro;
	private Long numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String uF;
	private String nomeEspaco;
	
	@Id
	@SequenceGenerator(name="TB_ENDERECO_SEQ", sequenceName="TB_ENDERECO_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="TB_ENDERECO_SEQ")
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
	
	@Column(name="NOME_LOGRADOURO")
	public String getNomeLogradouro() {
		return nomeLogradouro;
	}
	public void setNomeLogradouro(String nomeLogradouro) {
		this.nomeLogradouro = nomeLogradouro;
	}
	
	@Column(name="TIPO_LOGRADOURO")
	public String getTipoLogradouro() {
		return tipoLogradouro;
	}
	public void setTipoLogradouro(String tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}
	
	@Column(name="NUMERO")
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	
	@Column(name="COMPLEMENTO")
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	@Column(name="BAIRRO")
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	@Column(name="CIDADE")
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	@Column(name="UF")
	public String getuF() {
		return uF;
	}
	public void setuF(String uF) {
		this.uF = uF;
	}
	
	@Column(name="NOME_ESPACO")
	public String getNomeEspaco() {
		return nomeEspaco;
	}
	public void setNomeEspaco(String nomeEspaco) {
		this.nomeEspaco = nomeEspaco;
	}
	
	

}
