package br.com.ticktag.repository;

import br.com.ticktag.model.UsuarioVO;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioVO, Long> {
    UsuarioVO findByEmail(String email);

}

