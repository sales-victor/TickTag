package br.com.ticktag.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "TB_ITEM_CARRINHO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemCarrinhoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TB_ITEM_CARRINHO_SEQ", sequenceName = "TB_ITEM_CARRINHO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_ITEM_CARRINHO_SEQ")
    @Column(name = "ID_ITEM_CARRINHO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_CARRINHO", nullable = false)
    private CarrinhoVO carrinho;

    @Column(name = "ID_EVENTO")
    private Long idEvento;

    @Column(name = "NOME_EVENTO")
    private String nomeEvento;

    @Column(name = "ID_TIPO_TICKET")
    private Long idTipoTicket;

    @Column(name = "NOME_TIPO_TICKET")
    private String nomeTipoTicket;

    @Column(name = "QUANTIDADE")
    private Long quantitidade;

}
