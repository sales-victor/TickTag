package br.com.ticktag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ticktag.model.EnderecoVO;

public interface EnderecoRepository extends JpaRepository<EnderecoVO, Long> {

}
