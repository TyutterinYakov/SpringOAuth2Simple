package security.access;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JwtTokenFilter extends GenericFilterBean {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
	
	private final JwtTokenProvider tokenProvider;
	
	@Autowired
	public JwtTokenFilter(JwtTokenProvider tokenProvider) {
		super();
		this.tokenProvider = tokenProvider;
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = tokenProvider.resolveToken((HttpServletRequest)request);
		try {
			if(token!=null && tokenProvider.validateToken(token)) { 
				Authentication auth = tokenProvider.getAuthentication(token);
					SecurityContextHolder.getContext().setAuthentication(auth);
			}
			
		} catch(Exception ex) {
			logger.error(ex.getLocalizedMessage());
			SecurityContextHolder.clearContext();
		}
		chain.doFilter(request, response);
	}

	
}
