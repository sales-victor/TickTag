package br.com.ticktag.config;

import br.com.ticktag.model.RoleVO;
import br.com.ticktag.model.TicketVO;
import br.com.ticktag.model.UsuarioVO;
import br.com.ticktag.repository.RoleRepository;
import br.com.ticktag.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataLoaderConfig {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner loadRolesAndUsers() {
        return args -> {
            // Verifica se as roles já existem no banco de dados
            if (roleRepository.count() == 0) {
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

                roleRepository.save(admin);
                roleRepository.save(analista);
                roleRepository.save(comercial);

                System.out.println("Roles inseridas no banco de dados.");
            } else {
                System.out.println("Roles já existem no banco de dados.");
            }

            // Verifica se o usuário padrão já existe
            if (usuarioRepository.count() == 0) {
                // Buscar as roles criadas
                RoleVO adminRole = roleRepository.findByNome("ADMIN");
                RoleVO analistaRole = roleRepository.findByNome("ANALISTA");
                RoleVO comercialRole = roleRepository.findByNome("COMERCIAL");

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
                adminUser.setTickets(tickets);
                adminUser.setNome("Administrador");
                adminUser.setEmail("admin@ticktag.com");
                adminUser.setPassword(passwordEncoder.encode("admin123")); // Senha criptografada
                adminUser.setCpf("00000000000");
                adminUser.setDataNascimento(LocalDate.of(1980, 1, 1)); // Data de nascimento padrão
                adminUser.setRoles(rolesAdmin); // Associa a role de ADMIN
                usuarioRepository.save(adminUser);

                // Criar usuário com role de ANALISTA
                UsuarioVO analistaUser = new UsuarioVO();
                analistaUser.setTickets(tickets);
                analistaUser.setNome("Analista");
                analistaUser.setEmail("analista@ticktag.com");
                analistaUser.setPassword(passwordEncoder.encode("analista123")); // Senha criptografada
                analistaUser.setCpf("11111111111");
                analistaUser.setDataNascimento(LocalDate.of(1990, 5, 10)); // Data de nascimento padrão
                analistaUser.setRoles(rolesAnalista); // Associa a role de ANALISTA
                usuarioRepository.save(analistaUser);

                // Criar usuário com role de COMERCIAL
                UsuarioVO comercialUser = new UsuarioVO();
                comercialUser.setTickets(tickets);
                comercialUser.setNome("Comercial");
                comercialUser.setEmail("comercial@ticktag.com");
                comercialUser.setPassword(passwordEncoder.encode("comercial123")); // Senha criptografada
                comercialUser.setCpf("22222222222");
                comercialUser.setDataNascimento(LocalDate.of(1995, 7, 15)); // Data de nascimento padrão
                comercialUser.setRoles(rolesComercial); // Associa a role de COMERCIAL
                usuarioRepository.save(comercialUser);

                System.out.println("Usuários padrão criados com sucesso.");
            } else {
                System.out.println("Usuários já existem no banco de dados.");
            }
        };
    }
}
