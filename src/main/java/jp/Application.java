package jp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Jsr330ScopeMetadataResolver;

//@ComponentScanを明示的に指定し、scopeResolverにJSR330の仕様となるよう指定することで、DIによるオブジェクトがシングルトンにならないようにしています。
@ComponentScan(scopeResolver = Jsr330ScopeMetadataResolver.class)
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}
}
