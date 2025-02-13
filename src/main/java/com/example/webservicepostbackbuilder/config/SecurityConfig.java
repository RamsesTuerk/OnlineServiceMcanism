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

@Configuration // Diese Annotation markiert die Klasse als eine Konfigurationsklasse für Spring
@EnableWebSecurity // Aktiviert die Web-Sicherheitskonfiguration für die Anwendung
public class SecurityConfig {

    // Bean für die Sicherheitskonfiguration der HTTP-Anfragen
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Hier wird festgelegt, wie HTTP-Anfragen autorisiert werden
                .authorizeHttpRequests(authorize -> authorize
                        // Zugriffsrechte für bestimmte Endpunkte festlegen
                        .requestMatchers("/template/generate", "/upload").permitAll() // Diese Endpunkte sind öffentlich zugänglich, d.h. ohne Authentifizierung
                        .requestMatchers("/template", "/templates").authenticated() // Diese Endpunkte erfordern eine Authentifizierung
                        .anyRequest().permitAll() // Alle anderen Anfragen werden ohne Authentifizierung zugelassen
                )
                // Falls CSRF nicht benötigt wird, wird es hier deaktiviert
                .formLogin(form -> form
                        .loginPage("/login") // Die Anmeldeseite wird unter "/login" erwartet
                        .permitAll() // Die Anmeldeseite ist öffentlich zugänglich, d.h. jeder kann sich einloggen
                )
                // Konfiguration für das Logout
                .logout(logout -> logout
                        .permitAll() // Der Logout ist für alle Benutzer zugänglich
                );

        return http.build(); // Gibt die konfigurierte Sicherheitsfilterkette zurück
    }

    // Bean für den UserDetailsService, der die Benutzerinformationen verwaltet
    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        Map<String, String> users = loadUsersFromFile(); // Lädt die Benutzer aus einer Datei
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(); // Der InMemoryUserDetailsManager verwaltet die Benutzer im Speicher

        // Für jeden Benutzer in der geladenen Map wird ein Benutzerobjekt erstellt
        for (Map.Entry<String, String> entry : users.entrySet()) {
            manager.createUser(org.springframework.security.core.userdetails.User.withUsername(entry.getKey()) // Benutzername setzen
                    .password("{noop}" + entry.getValue()) // Passwort setzen (kein Passwort-Encoder wird verwendet, daher {noop})
                    .roles("USER") // Der Benutzer bekommt die Rolle "USER"
                    .build()); // Der Benutzer wird erstellt und zum Manager hinzugefügt
        }

        return manager; // Gibt den UserDetailsService zurück
    }

    // Diese Methode lädt die Benutzerdaten aus einer Datei
    private Map<String, String> loadUsersFromFile() {
        Map<String, String> users = new HashMap<>(); // Eine Map, um Benutzername und Passwort zu speichern
        // Hier wird der Pfad zur Datei angegeben, die die Benutzerdaten enthält
        // Für lokale Tests: src/main/resources/users.txt
        // Für den Server: /opt/tomcat/webapps/ROOT/WEB-INF/classes/users.txt
        try (BufferedReader br = new BufferedReader(new FileReader("/opt/tomcat/webapps/ROOT/WEB-INF/classes/users.txt"))) {
            String line;
            // Jede Zeile in der Datei wird eingelesen
            while ((line = br.readLine()) != null) {
                // Die Zeile wird bei ":" getrennt, wobei der Teil vor dem ":" der Benutzername ist und der Teil nach dem ":" das Passwort
                String[] parts = line.split(":");
                if (parts.length == 2) { // Falls die Zeile korrekt formatiert ist
                    users.put(parts[0], parts[1]); // Benutzername und Passwort werden in die Map eingefügt
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Fehlerbehandlung im Falle eines IO-Problems beim Lesen der Datei
        }
        return users; // Gibt die Map mit den Benutzern zurück
    }
}
