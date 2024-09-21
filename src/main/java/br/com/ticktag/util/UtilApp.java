package br.com.ticktag.util;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilApp implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String CONFIG_DATA_HORA = "dd/MM/yyyy - hh:mm";
	public static final String CONFIG_DATA = "dd/MM/yyyy";
	public static String obterDataAtualFusoFormatadaDDMMYYYYHHMM() {

		String dataFormatada = "";
		// bahia por conta governo nao ter adotado horario de verao
		ZonedDateTime zd = java.time.ZonedDateTime.now(ZoneId.of("America/Bahia"));

		Date data = Date.from(zd.toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant());
		// String dateToStr = DataHelper.converteData(data,

		dataFormatada = new SimpleDateFormat(CONFIG_DATA_HORA).format(data);

		return dataFormatada;
	}
	
	public static String obtemMensagemCompletaException(Exception e) {
		StringBuilder mensagemFinal = new StringBuilder();
		mensagemFinal.append("Erro ao processar a transação! ");
		mensagemFinal.append(e.getMessage());
		if (e.getCause() != null && e.getCause().getCause() != null && e.getCause().getCause().getMessage() != null) {
			mensagemFinal.append(" - ");
			mensagemFinal.append(e.getCause().getCause().getMessage());
		}
		if (e.getStackTrace() != null && e.getStackTrace().length > 0) {
			mensagemFinal.append(" - ");
			String erro = "";
			if (e.getStackTrace() != null) {
				for (StackTraceElement element : e.getStackTrace()) {
					if (element.toString().contains("gasc")) {
						erro = element.toString();
						break;
					}
				}
			}

			String regexObterDentroParenteseClasse = "\\((.*?)\\)";
			Pattern p = Pattern.compile(regexObterDentroParenteseClasse);
			Matcher m = p.matcher(erro);

			while (m.find()) {
				if (m.group() != null) {
					mensagemFinal.append("'");
					mensagemFinal.append(m.group().replace(" ", "").replace("(", "").replace(")", ""));
					mensagemFinal.append("'");
				}

			}

		}

		return mensagemFinal.toString();

	}
}
