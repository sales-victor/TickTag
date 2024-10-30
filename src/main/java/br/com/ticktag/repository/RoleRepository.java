package br.com.ticktag.repository;

import br.com.ticktag.domain.RoleVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleVO, Long> {
    RoleVO findByNome(String nome);
}

