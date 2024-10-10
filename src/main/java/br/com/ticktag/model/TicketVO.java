package br.com.ticktag.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "TB_TICKET")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TB_TICKET_SEQ", sequenceName = "TB_TICKET_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_TICKET_SEQ")
    @Column(name = "ID_TICKET")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private UsuarioVO usuarioVO;

    @ManyToOne
    @JoinColumn(name = "ID_EVENTO")
    private EventoVO eventoVO;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_TICKET")
    private TipoTicketVO tipoTicketVO;

    @Column(name = "HASH_CODE")
    private String hashCode;
}
