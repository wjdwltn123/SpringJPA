package kopo.poly.config;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.text.html.parser.Entity;

@RequiredArgsConstructor
@Configuration
public class QueryDSLConfig {

    private final EntityManager entityManager;

}