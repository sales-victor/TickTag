package br.com.ticktag.controller;

import br.com.ticktag.model.RoleVO;
import br.com.ticktag.model.UsuarioVO;
import br.com.ticktag.repository.RoleRepository;
import br.com.ticktag.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
public class UsuarioRestCtr {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public ResponseEntity<List<UsuarioVO>> listarUsuarios() {
        List<UsuarioVO> usuarios = usuarioService.listarUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioVO> criarUsuario(@Valid @RequestBody UsuarioVO usuario) {
        // Validações de dados obrigatórios
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Atribuir uma role padrão (ADMIN por exemplo)
        Set<RoleVO> roles = new HashSet<>();
        RoleVO adminRole = roleRepository.findByNome("ADMIN");
        if (adminRole != null) {
            roles.add(adminRole);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Falha na role
        }
        usuario.setRoles(roles);

        UsuarioVO usuarioSalvo = usuarioService.salvarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        Optional<UsuarioVO> usuario = usuarioService.buscarPorId(id);
        if (usuario.isPresent()) {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioVO> buscarUsuarioPorEmail(@PathVariable String email) {
        UsuarioVO usuario = usuarioService.buscarPorEmail(email);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioVO> buscarUsuarioPorId(@PathVariable Long id) {
        Optional<UsuarioVO> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
