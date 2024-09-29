package br.com.ticktag.controller;

import br.com.ticktag.model.UsuarioVO;
import br.com.ticktag.repository.UsuarioRepository;
import br.com.ticktag.service.CustomUserDetailsService;
import br.com.ticktag.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    public void setup() {
        // Limpa o repositório antes de cada teste
        usuarioRepository.deleteAll();

        // Cria um usuário antes do teste
        UsuarioVO testUser = new UsuarioVO();
        testUser.setEmail("testuser@example.com");
        testUser.setPassword(passwordEncoder.encode("password123"));
        testUser.setCpf("00000000000");
        testUser.setNome("Test User");
        testUser.setDataNascimento(LocalDate.of(1990, 1, 1));

        usuarioRepository.save(testUser);
    }

    @Test
    public void testLoginWithExistingUser() throws Exception {
        // Simula o login com um usuário já existente
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"email\": \"testuser@example.com\", \"password\": \"password123\" }"))
                .andExpect(status().isOk());  // Verifica se a requisição foi bem-sucedida
    }

    @Test
    public void testLoginWithInvalidCredentials() throws Exception {
        // Tenta fazer login com credenciais inválidas
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"email\": \"invalid@example.com\", \"password\": \"wrongpassword\" }"))
                .andExpect(status().isUnauthorized());  // Verifica se a resposta foi "Unauthorized"
    }
}
