package jp.jast.spframework.config;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import jp.jast.spframework.auth.AbstractLogoutHandler;
import jp.jast.spframework.auth.AbstractLogoutSuccessHandler;
import jp.jast.spframework.auth.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

	/** ログイン成功時URLを常に使用する設定 */
	private static final String ALWAYS_USE_DEFAULT_SUCCESS_URL_FLG = "1";

	@Autowired SpFrameworkProperties properties;
	@Autowired UserDetailsServiceImpl userDetailsService;

	// パスワードの暗号化方式
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)/*.passwordEncoder(passwordEncoder())*/;
	}

    @Autowired AbstractLogoutHandler logoutHandler;
    @Autowired AbstractLogoutSuccessHandler logoutSuccessHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	// ログイン画面設定
    	configureLoginForm(http);

    	// 権限設定
    	configureAuthorizeRequests(http);

    	// CSRF無効設定
    	configureCsrf(http);

    	// ログアウト設定
    	configureLogout(http);
    }

    /**
     * ログイン画面設定
     * 
     * @param http
     * @throws Exception
     */
    private void configureLoginForm(HttpSecurity http) throws Exception {

    	// デフォルト設定
    	if (StringUtils.isEmpty(properties.getSecurity().getLoginPage())) {
    		super.configure(http);

        // カスタム設定（application.properties spframework.security.*）
    	} else {
    		boolean alwaysUseDefaultSuccessUrl = false;
			if (ALWAYS_USE_DEFAULT_SUCCESS_URL_FLG.equals(properties.getSecurity().getAlwaysUseDefaultSuccessUrl())) {
        		alwaysUseDefaultSuccessUrl = true;
    		}
        	http.formLogin()
	    		.loginPage(properties.getSecurity().getLoginPage())
	    		.usernameParameter(properties.getSecurity().getUsernameParameter())
	    		.passwordParameter(properties.getSecurity().getPasswordParameter())
	    		.loginProcessingUrl(properties.getSecurity().getLoginProcessingUrl())
	    		.defaultSuccessUrl(properties.getSecurity().getDefaultSuccessUrl(), alwaysUseDefaultSuccessUrl)
	    		.failureUrl(properties.getSecurity().getFailureUrl())
	    		.permitAll();
    	}
    }

    /**
     * 権限設定
     * 
     * @param http
     * @throws Exception
     */
    private void configureAuthorizeRequests(HttpSecurity http) throws Exception {
    	if (!StringUtils.isEmpty(properties.getSecurity().getPermitAllUrl())) {
    		String[] permitAllUrlArray = properties.getSecurity().getPermitAllUrl().split(",");
        	http.authorizeRequests()
        		.antMatchers(permitAllUrlArray).permitAll();
    	}
		http.authorizeRequests().antMatchers(
				"/custom/**",
				"/webjars/**",
				"/js/**",
				"/css/**",
				"/img/**",
				"/image/**",
				"/favicon.ico",
				"/authError").permitAll();
    	http.authorizeRequests().anyRequest().authenticated();
    }

    /**
     * CSRF無効設定
     * 
     * @param http
     * @throws Exception
     */
    private void configureCsrf(HttpSecurity http) throws Exception {
    	if (!StringUtils.isEmpty(properties.getSecurity().getDisableCsrfUrl())) {
    		String disableUri = properties.getSecurity().getDisableCsrfUrl().replaceAll(",", "|");
        	http.csrf()
        			.requireCsrfProtectionMatcher(new RequestMatcher() {
	            		private Pattern disableMethodPattern = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
	            		private Pattern disableUriPattern = Pattern.compile("^(" + disableUri + ")$");
	    				@Override
	    				public boolean matches(HttpServletRequest request) {
	    					if (request != null) {
	    						if (disableMethodPattern.matcher(request.getMethod()).matches()) {
	    							return false;
	    						}
	    						if (disableUriPattern.matcher(request.getRequestURI()).matches()) {
	    							return false;
	    						}
	    					}
	    					return true;
	    				}
	    			});
    	}
    }

    /**
     * ログアウト設定
     * 
     * @param http
     * @throws Exception
     */
    private void configureLogout(HttpSecurity http) throws Exception {
		http.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher(properties.getSecurity().getLogoutUrl()))
		.addLogoutHandler(logoutHandler)
		.logoutSuccessHandler(logoutSuccessHandler);
    }
}
