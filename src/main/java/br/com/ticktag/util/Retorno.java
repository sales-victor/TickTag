package br.com.ticktag.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Retorno {

	private Integer codigoMensagem;
	private String mensagem;
	private Object objeto;
	private String dataServidor;
	
	public Retorno(){
		
	}

	public Retorno(Integer codigoMensagem, String mensagem, Object objeto) {
		this.codigoMensagem = codigoMensagem;
		this.mensagem = mensagem;
		this.objeto = objeto;
	}

	public Integer getCodigoMensagem() {
		return codigoMensagem;
	}

	public void setCodigoMensagem(Integer codigoMensagem) {
		this.codigoMensagem = codigoMensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Object getObjeto() {
		return objeto;
	}

	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}


	public String getDataServidor() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		dataServidor = format.format(new Date());
		return dataServidor;
	}


	public void setDataServidor(String dataServidor) {
		this.dataServidor = dataServidor;
	}		

}
