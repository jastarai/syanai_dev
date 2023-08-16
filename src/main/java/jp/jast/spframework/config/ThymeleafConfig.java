package jp.jast.spframework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;

import jp.jast.spframework.thymeleaf.ExtDialect;

@Configuration
public class ThymeleafConfig {

    @Autowired
    public TemplateEngine templateEngine;

    @Bean
    public TemplateEngine addDialect() {
        templateEngine.addDialect(
                new ExtDialect(templateEngine.getConfiguration()));
        return templateEngine;
    }
    
}