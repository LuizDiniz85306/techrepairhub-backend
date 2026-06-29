package com.luiz.techrepairhub.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // Libera requisições de pré-verificação do navegador
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Rotas públicas
                        .requestMatchers("/", "/teste").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                        // Usuários
                        .requestMatchers("/usuarios/**")
                        .hasRole("ADMIN")

                        // Técnicos
                        .requestMatchers("/tecnicos/**")
                        .hasRole("ADMIN")

                        // Clientes
                        .requestMatchers("/clientes/**")
                        .hasAnyRole("ADMIN", "ATENDENTE")

                        // Categorias
                        .requestMatchers(HttpMethod.GET, "/categorias/**")
                        .permitAll()
                        .requestMatchers("/categorias/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE")

                        // Produtos
                        .requestMatchers(HttpMethod.GET, "/produtos/**")
                        .permitAll()
                        .requestMatchers("/produtos/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE")

                        // Imagens dos produtos
                        .requestMatchers(HttpMethod.GET, "/imagens-produto/**")
                        .permitAll()
                        .requestMatchers("/imagens-produto/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE")

                        // Especificações dos produtos
                        .requestMatchers(HttpMethod.GET, "/especificacoes-produto/**")
                        .permitAll()
                        .requestMatchers("/especificacoes-produto/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE")

                        // Estoques
                        .requestMatchers(HttpMethod.GET, "/estoques/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE")
                        .requestMatchers("/estoques/**")
                        .hasAnyRole("ADMIN", "GESTOR")

                        // Movimentações de estoque
                        .requestMatchers(HttpMethod.GET, "/movimentacoes-estoque/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE")
                        .requestMatchers("/movimentacoes-estoque/**")
                        .hasAnyRole("ADMIN", "GESTOR")

                        // Carrinhos
                        .requestMatchers("/carrinhos/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE", "CLIENTE")

                        // Pedidos
                        .requestMatchers("/pedidos/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE", "CLIENTE")

                        // Garantias
                        .requestMatchers("/garantias/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE", "CLIENTE")

                        // Equipamentos
                        .requestMatchers("/equipamentos/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE", "TECNICO", "CLIENTE")

                        // Ordens de serviço
                        .requestMatchers("/ordens-servico/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE", "TECNICO", "CLIENTE")

                        // Histórico das ordens de serviço
                        .requestMatchers("/historicos-ordem-servico/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE", "TECNICO", "CLIENTE")

                        // Orçamentos da OS
                        .requestMatchers("/orcamentos/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE", "TECNICO", "CLIENTE")

                        // Peças
                        .requestMatchers("/pecas/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE", "TECNICO")

                        // Peças utilizadas na OS
                        .requestMatchers("/pecas-utilizadas/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE", "TECNICO")

                        // Relatórios técnicos
                        .requestMatchers("/relatorios-tecnicos/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE", "TECNICO", "CLIENTE")

                        // Relatórios internos
                        .requestMatchers("/relatorios/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE")

                        // Área interna
                        .requestMatchers("/area-interna/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE", "TECNICO", "CLIENTE")

                        // Permissões por perfil
                        .requestMatchers("/permissoes/**")
                        .hasAnyRole("ADMIN", "GESTOR", "ATENDENTE", "TECNICO", "CLIENTE")

                        // Receitas
                        .requestMatchers("/receitas/**")
                        .hasAnyRole("ADMIN", "GESTOR")

                        // Despesas
                        .requestMatchers("/despesas/**")
                        .hasAnyRole("ADMIN", "GESTOR")

                        // Fluxo de caixa
                        .requestMatchers("/fluxo-caixa/**")
                        .hasAnyRole("ADMIN", "GESTOR")

                        // Dashboard administrativo
                        .requestMatchers("/dashboard/**")
                        .hasAnyRole("ADMIN", "GESTOR")

                        // Qualquer outra rota precisa estar autenticada
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}