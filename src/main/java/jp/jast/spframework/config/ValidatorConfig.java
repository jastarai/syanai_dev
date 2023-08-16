package jp.jast.spframework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;

import jp.jast.spframework.thymeleaf.ExtDialect;


@Configuration
public class ValidatorConfig extends WebMvcConfigurerAdapter{

	// これでメッセージリソースを上書きできます。
	// Bean Validationがデフォルトで返す値はエラーメッセージですが、これをIDとして扱うようにし、
	// その後、別途与えられるメッセージIDで任意のエラーメッセージを出力するようにします
	
    @Autowired
    public TemplateEngine templateEngine;
	
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:ValidationID");
        messageSource.setDefaultEncoding("UTF-8");
        // キャッシュ -1: リロードしない、0: 常にリロード
        messageSource.setCacheSeconds(0);
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean localValidatorFactoryBean 
            = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource());
        return localValidatorFactoryBean;
    }

    @Override
    public Validator getValidator() {
        return validator();
    }
    
    @Bean
    public TemplateEngine addDialect() {
        templateEngine.addDialect(
                new ExtDialect(templateEngine.getConfiguration()));
        return templateEngine;
    }
	
}
