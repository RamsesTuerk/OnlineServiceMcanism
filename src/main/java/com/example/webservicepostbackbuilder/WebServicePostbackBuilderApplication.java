package com.example.webservicepostbackbuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.webservicepostbackbuilder.repository") // oder zum Paket, das die Entitäten enthält
public class WebServicePostbackBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServicePostbackBuilderApplication.class, args);
    }

}
