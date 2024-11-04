package br.com.ticktag.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TB_CARRINHO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarrinhoVO implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TB_CARRINHO_SEQ", sequenceName = "TB_CARRINHO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_CARRINHO_SEQ")
    @Column(name = "ID_CARRINHO")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_USUARIO")
    private UsuarioVO usuario;

    @OneToMany(mappedBy = "carrinho")
    private Set<ItemCarrinhoVO> itensCarrinho = new HashSet<>();
}
