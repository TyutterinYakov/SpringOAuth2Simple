package security.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import security.enity.AppUser;

@Service
public class LoginService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class); 
	
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	
	@Autowired
	public LoginService(UserService userService, AuthenticationManager authenticationManager) {
		super();
		this.userService = userService;
		this.authenticationManager = authenticationManager;
	}

	
	public AppUser login(String username, String password) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));
		return userService.retrieveFromCache(username);
	}
	
	
	
	
	
}
