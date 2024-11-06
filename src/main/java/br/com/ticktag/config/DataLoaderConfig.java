package br.com.ticktag.config;

import br.com.ticktag.controller.Carrinho;
import br.com.ticktag.domain.*;
import br.com.ticktag.repository.RepositoryFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DataLoaderConfig {

    private final RepositoryFacade facade;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner loadRolesAndUsers() {
        return args -> {
            // Verifica se as roles já existem no banco de dados
            if (facade.roleRepository.count() == 0) {
                // Criar as roles
                RoleVO admin = new RoleVO();
                admin.setId(1L);
                admin.setNome("ADMIN");

                RoleVO analista = new RoleVO();
                analista.setId(2L);
                analista.setNome("ANALISTA");

                RoleVO comercial = new RoleVO();
                comercial.setId(3L);
                comercial.setNome("COMERCIAL");

                facade.roleRepository.save(admin);
                facade.roleRepository.save(analista);
                facade.roleRepository.save(comercial);

                log.info("Roles inseridas no banco de dados.");
            } else {
                log.info("Roles já existem no banco de dados.");
            }

            // Verifica se o usuário padrão já existe
            if (facade.usuarioRepository.count() == 0) {
                // Buscar as roles criadas
                RoleVO adminRole = facade.roleRepository.findByNome("ADMIN");
                RoleVO analistaRole = facade.roleRepository.findByNome("ANALISTA");
                RoleVO comercialRole = facade.roleRepository.findByNome("COMERCIAL");

                // Criação do conjunto de roles para o usuário padrão
                Set<RoleVO> rolesAdmin = new HashSet<>();
                rolesAdmin.add(adminRole);

                Set<RoleVO> rolesAnalista = new HashSet<>();
                rolesAnalista.add(analistaRole);

                Set<RoleVO> rolesComercial = new HashSet<>();
                rolesComercial.add(comercialRole);

                Set<TicketVO> tickets = new HashSet<>();

                // Criar usuário com role de ADMIN
                UsuarioVO adminUser = new UsuarioVO();
                CarrinhoVO carrinhoAdmin = new CarrinhoVO();
                adminUser.setTickets(tickets);
                adminUser.setNome("Administrador");
                adminUser.setEmail("admin@ticktag.com");
                adminUser.setPassword(passwordEncoder.encode("admin123")); // Senha criptografada
                adminUser.setCpf("00000000000");
                adminUser.setDataNascimento(LocalDate.of(1980, 1, 1)); // Data de nascimento padrão
                adminUser.setRoles(rolesAdmin); // Associa a role de ADMIN
                carrinhoAdmin.setUsuario(adminUser);
                adminUser.setCarrinho(carrinhoAdmin);
                facade.usuarioRepository.save(adminUser);

                // Criar usuário com role de ANALISTA
                UsuarioVO analistaUser = new UsuarioVO();
                CarrinhoVO carrinhoAnalista = new CarrinhoVO();
                analistaUser.setTickets(tickets);
                analistaUser.setNome("Analista");
                analistaUser.setEmail("analista@ticktag.com");
                analistaUser.setPassword(passwordEncoder.encode("analista123")); // Senha criptografada
                analistaUser.setCpf("11111111111");
                analistaUser.setDataNascimento(LocalDate.of(1990, 5, 10)); // Data de nascimento padrão
                analistaUser.setRoles(rolesAnalista); // Associa a role de ANALISTA
                carrinhoAnalista.setUsuario(analistaUser);
                analistaUser.setCarrinho(carrinhoAnalista);
                facade.usuarioRepository.save(analistaUser);

                // Criar usuário com role de COMERCIAL
                UsuarioVO comercialUser = new UsuarioVO();
                CarrinhoVO carrinhoComercial = new CarrinhoVO();
                comercialUser.setTickets(tickets);
                comercialUser.setNome("Comercial");
                comercialUser.setEmail("comercial@ticktag.com");
                comercialUser.setPassword(passwordEncoder.encode("comercial123")); // Senha criptografada
                comercialUser.setCpf("22222222222");
                comercialUser.setDataNascimento(LocalDate.of(1995, 7, 15)); // Data de nascimento padrão
                comercialUser.setRoles(rolesComercial); // Associa a role de COMERCIAL
                carrinhoComercial.setUsuario(comercialUser);
                comercialUser.setCarrinho(carrinhoComercial);
                facade.usuarioRepository.save(comercialUser);

                log.info("Usuários padrão criados com sucesso.");
            } else {
                log.info("Usuários já existem no banco de dados.");
            }
        };
    }
}