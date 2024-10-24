package com.example.webservicepostbackbuilder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/template/generate").permitAll() // Zugang ohne Authentifizierung
                        .requestMatchers("/template", "/templates").authenticated() // Zugang mit Authentifizierung
                        .anyRequest().permitAll() // Alle anderen Anfragen erlauben
                )
                .formLogin(form -> form
                        .loginPage("/login") // Anmeldeseite
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        Map<String, String> users = loadUsersFromFile();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        for (Map.Entry<String, String> entry : users.entrySet()) {
            manager.createUser(org.springframework.security.core.userdetails.User.withUsername(entry.getKey())
                    .password("{noop}" + entry.getValue()) // {noop} bedeutet kein Passwort-Encoder
                    .roles("USER")
                    .build());
        }

        return manager;
    }

    private Map<String, String> loadUsersFromFile() {
        Map<String, String> users = new HashMap<>();
        //lokales Testen: src/main/resources/users.txt
        //server pfad: /opt/tomcat/webapps/ROOT/WEB-INF/classes/users.txt
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":"); // Annahme: Benutzername und Passwort sind durch ":" getrennt
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
