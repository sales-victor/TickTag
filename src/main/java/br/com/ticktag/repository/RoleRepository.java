package br.com.ticktag.repository;

import br.com.ticktag.model.RoleVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleVO, Long> {
    RoleVO findByNome(String nome);
}

