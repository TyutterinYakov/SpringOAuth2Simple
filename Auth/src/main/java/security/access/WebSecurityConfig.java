package security.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity(debug=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	private final JwtTokenConfig tokenConfig;

	@Autowired
	public WebSecurityConfig(JwtTokenConfig tokenConfig) {
		super();
		this.tokenConfig = tokenConfig;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors(Customizer.withDefaults())
			.csrf().disable()
//			.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
			.authorizeRequests()
			.antMatchers("/api/v1/auth/login", "/api/v1/auth/registration", "/api/v1/auth/current").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.apply(tokenConfig);
	}
	
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	public WebMvcConfigurer corsConfig() {
		return new WebMvcConfigurer(){
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins("http://localhost:3000", "http://localhost:8080");
			}
		};
	}
	
	
	
	
	
	
}
