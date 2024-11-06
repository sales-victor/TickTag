package br.com.ticktag.service.impl;

import br.com.ticktag.domain.CarrinhoVO;
import br.com.ticktag.domain.RoleVO;
import br.com.ticktag.domain.UsuarioVO;
import br.com.ticktag.repository.RepositoryFacade;
import br.com.ticktag.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
class UsuarioServiceImpl implements UsuarioService {

    private final RepositoryFacade facade;

    public List<UsuarioVO> listarUsuarios() {
        return facade.usuarioRepository.findAll();
    }

    public UsuarioVO salvarUsuario(UsuarioVO usuario) throws ResponseStatusException {

        // Atribuir uma role padrão (ADMIN por exemplo)
        Set<RoleVO> roles = new HashSet<>();
        RoleVO adminRole = facade.roleRepository.findByNome("ADMIN");
        if (adminRole != null) {
            roles.add(adminRole);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        usuario.setRoles(roles);

        // Adiciona um novo carrinho para o usuário
        CarrinhoVO carrinho = CarrinhoVO.builder().usuario(usuario).build();
        carrinho.setUsuario(usuario);
        usuario.setCarrinho(carrinho);

        // Adicionar lógica de validação de CPF, email, etc.
        return facade.usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        facade.usuarioRepository.deleteById(id);
    }

    public UsuarioVO buscarPorEmail(String email) {
        return facade.usuarioRepository.findByEmail(email);
    }

    public Optional<UsuarioVO> buscarPorId(Long id) {
        return facade.usuarioRepository.findById(id);
    }
}
