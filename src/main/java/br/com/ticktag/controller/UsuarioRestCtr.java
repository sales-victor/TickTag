package br.com.ticktag.controller;

import br.com.ticktag.model.RoleVO;
import br.com.ticktag.model.UsuarioVO;
import br.com.ticktag.repository.RoleRepository;
import br.com.ticktag.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
public class UsuarioRestCtr {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public List<UsuarioVO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @PostMapping
    public UsuarioVO criarUsuario(@RequestBody UsuarioVO usuario) {
        Set<RoleVO> roles = new HashSet<>();
        roles.add(roleRepository.findByNome("ADMIN"));

        usuario.setRoles(roles);
        return usuarioService.salvarUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
    }

    @GetMapping("/{email}")
    public UsuarioVO buscarUsuarioPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email);
    }
}
