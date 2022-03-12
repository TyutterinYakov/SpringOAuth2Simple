package security.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;

@Configuration
public class JwtTokenConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private final JwtTokenFilter tokenFilter;
	
	@Autowired
	public JwtTokenConfig(JwtTokenFilter tokenFilter) {
		super();
		this.tokenFilter = tokenFilter;
	}

	@Override
	public void configure(HttpSecurity builder) throws Exception {
		builder
			.addFilterBefore(tokenFilter, RequestCacheAwareFilter.class);
	}
	
	
	
	
	
}
