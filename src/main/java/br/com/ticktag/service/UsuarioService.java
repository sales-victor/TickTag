package br.com.ticktag.service;

import br.com.ticktag.domain.UsuarioVO;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public interface UsuarioService {
    List<UsuarioVO> listarUsuarios();

    UsuarioVO salvarUsuario(UsuarioVO usuario) throws ResponseStatusException;

    Optional<UsuarioVO> buscarPorId(Long id);

    void deletarUsuario(Long id);

    UsuarioVO buscarPorEmail(String email);
}
