package br.com.ticktag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.ticktag.model.UsuarioVO;
import br.com.ticktag.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioVO> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public UsuarioVO salvarUsuario(UsuarioVO usuario) {
        // Adicionar lógica de validação de CPF, email, etc.
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioVO buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<UsuarioVO> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
}
