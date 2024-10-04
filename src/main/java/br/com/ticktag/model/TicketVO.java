package br.com.ticktag.model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_TICKET")
public class TicketVO implements Serializable {

    @Id
    private Long id;

    @ManyToMany(mappedBy = "USUARIOS")
    private UsuarioVO usuarioVO;

    @ManyToOne
    @JoinColumn(name = "ID_EVENTO")
    private EventoVO eventoVO;

    @OneToOne
    @JoinColumn(name = "ID_TIPO_TICKET")
    private TipoTicketVO tipoTicketVO;

    @Column(name = "HASH_CODE")
    private String hashCode;

    public TicketVO() {
        super();
    }

    public TicketVO(UsuarioVO usuario, EventoVO evento, TipoTicketVO tipoTicket, String hashCode) {
        super();
        this.usuarioVO = usuario;
        this.eventoVO = evento;
        this.tipoTicketVO = tipoTicket;
        this.hashCode = hashCode;
    }

    @SequenceGenerator(name = "TB_TICKET_SEQ", sequenceName = "TB_TICKET_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TB_TICKET_SEQ")
    @Column(name = "ID_TICKET")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioVO getUsuario() {
        return this.usuarioVO;
    }

    public void setUsuario(UsuarioVO usuario) {
        this.usuarioVO = usuario;
    }

    public EventoVO getEvento() {
        return this.eventoVO;
    }

    public void setEvento(EventoVO evento) {
        this.eventoVO = evento;
    }

    public TipoTicketVO getTipoTicket() {
        return this.tipoTicketVO;
    }

    public void setTipoTicket(TipoTicketVO tipoTicket) {
        this.tipoTicketVO = tipoTicket;
    }

    public String getHashCode() {
        return this.hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

}
