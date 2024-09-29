package br.com.ticktag.model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.UUID;
import java.security.MessageDigest;

@Entity
@Table(name = "TB_TICKET")
public class TicketVO implements Serializable {

    private Long id;
    private UsuarioVO usuarioVO;
    private EventoVO eventoVo;
    private String hashCode;

    public TicketVO(Long id, UsuarioVO usuario, EventoVO evento, String hashCode) {
        super();
        this.id = id;
        this.usuarioVO = usuario;
        this.eventoVo = evento;
        this.hashCode = hashCode;
    }

    public TicketVO(UsuarioVO usuario, EventoVO evento, String hashCode) {
        super();
        this.usuarioVO = usuario;
        this.eventoVo = evento;
        this.hashCode = hashCode;
    }

    @Id
    @SequenceGenerator(name = "TB_TICKET_SEQ", sequenceName = "TB_TICKET_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_TICKET_SEQ")
    @Column(name = "ID_TICKET")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "USUARIO")
    @JoinColumn(name = "ID_TICKET", referencedColumnName = "ID_TICKET", insertable = false, updatable = false)
    public UsuarioVO getUsuario() {
        return this.usuarioVO;
    }

    public void setUsuario(UsuarioVO usuario) {
        this.usuarioVO = usuario;
    }

    @Column(name = "EVENTO")
    @JoinColumn(name = "ID_TICKET", referencedColumnName = "ID_TICKET", insertable = false, updatable = false)
    public EventoVO getEvento() {
        return this.eventoVo;
    }

    public void setEvento(EventoVO evento) {
        this.eventoVo = evento;
    }

    @Column(name = "HASH_CODE")
    public String getHashCode() {
        return this.hashCode;
    }

    @PrePersist
    public String setHashCode() {
        String usuario = this.getUsuario().getNome();
        String evento = this.getEvento().getNomeEvento();
        String input = usuario + evento + UUID.randomUUID().toString();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException("Erro ao gerar hash: " + e.getMessage());

        }
    }
}
