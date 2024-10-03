package br.com.ticktag.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "TB_USUARIO")
public class UsuarioVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private String cpf;
    private String password;
    private Set<RoleVO> roles; // Grupos de acesso

    @Id
    @SequenceGenerator(name = "TB_USUARIO_SEQ", sequenceName = "TB_USUARIO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_USUARIO_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NOME", nullable = false)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "DATA_NASCIMENTO", nullable = true)
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Column(name = "EMAIL", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "TELEFONE", nullable = true)
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Column(name = "CPF", nullable = false, unique = true)
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "PASSWORD", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany
    @JoinTable(
            name = "TB_USUARIO_ROLE",
            joinColumns = @JoinColumn(name = "ID_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROLE")
    )
    public Set<RoleVO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleVO> roles) {
        this.roles = roles;
    }
}
