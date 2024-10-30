package br.com.ticktag.repository;

import br.com.ticktag.domain.UsuarioVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioVO, Long> {
    UsuarioVO findByEmail(String email);
}

