package security.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.jaas.DefaultLoginExceptionResolver;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import security.access.JwtTokenProvider;
import security.bean.ErrorResponse;
import security.bean.LoginRequest;
import security.bean.RegistrationRequest;
import security.bean.UserResponse;
import security.enity.AppUser;
import security.service.LoginService;
import security.service.RegistrationService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	private final RegistrationService registrationService;
	private final JwtTokenProvider jwtTokenProvider;
	private final LoginService loginService;
	
	@Autowired
	public AuthController(RegistrationService registrationService, JwtTokenProvider jwtTokenProvider,
			LoginService loginService) {
		super();
		this.registrationService = registrationService;
		this.jwtTokenProvider = jwtTokenProvider;
		this.loginService = loginService;
	}

	@PostMapping(path="registration")
	public ResponseEntity<?> registration(
			@RequestBody RegistrationRequest request, HttpServletResponse response){
		try {
			AppUser user = registrationService.register(request);
			setRefreshToken(user, response);
			setAuthToken(user, response);
			return buildUserResponse(user);
		} catch(Exception ex) {
			logger.error(ex.getLocalizedMessage());
			clearTokens(response);
			return buildErrorResponse(ex.getLocalizedMessage());
		}
	}
	
	@PostMapping(path="login")
	public ResponseEntity<?> login(
			@RequestBody LoginRequest request, HttpServletResponse response){
		try {
			AppUser user = loginService.login(request.getUsername(), request.getPassword());
			setRefreshToken(user, response);
			setAuthToken(user, response);
			return buildUserResponse(user);
		} catch(Exception ex) {
			logger.error(ex.getLocalizedMessage());
			clearTokens(response);
			return buildErrorResponse(ex.getLocalizedMessage());
		}
	}
	
	@GetMapping(path="current")
	public ResponseEntity<?> current(){
		try {
			AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return buildUserResponse(appUser);
		} catch(NullPointerException ex) {
			return buildUserResponse(new AppUser());
		}
	}
	
	
	@GetMapping(path="logout")
	public ResponseEntity<?> logout(HttpServletResponse response, HttpServletRequest request){
		SecurityContextHolder.clearContext();
		clearTokens(response);
		return buildUserResponse(new AppUser());
	}
	
	private void clearTokens(HttpServletResponse response) {
		Cookie authCookie = new Cookie(jwtTokenProvider.getAuthCookieName(), "-");
		authCookie.setPath(jwtTokenProvider.getPathCookie());
		Cookie refreshCookie = new Cookie(jwtTokenProvider.getRefreshCookieName(), "-");
		refreshCookie.setPath(jwtTokenProvider.getPathCookie());
		response.addCookie(authCookie);
		response.addCookie(refreshCookie);
	}
	
	
	private void setAuthToken(AppUser user, HttpServletResponse response) {
		String token = jwtTokenProvider.createAuthToken(user.getUsername(), user.getRole().name());
		Cookie cookie = new Cookie(jwtTokenProvider.getAuthCookieName(), token);
		cookie.setPath(jwtTokenProvider.getPathCookie());
		cookie.setHttpOnly(true);
		cookie.setMaxAge(jwtTokenProvider.getAuthExpirationCookie());
		response.addCookie(cookie);
	}
	
	private void setRefreshToken(AppUser user, HttpServletResponse response) {
		String token = jwtTokenProvider.createRefreshToken(user.getUsername(), user.getRole().name());
		Cookie cookie = new Cookie(jwtTokenProvider.getRefreshCookieName(), token);
		cookie.setPath(jwtTokenProvider.getPathCookie());
		cookie.setHttpOnly(true);
		cookie.setMaxAge(jwtTokenProvider.getRefreshExpirationCookie());
		response.addCookie(cookie);
	}
	
	private ResponseEntity<?>buildUserResponse(AppUser user){
		return ResponseEntity.ok(new UserResponse(user));
	}
	
	private ResponseEntity<?>buildErrorResponse(String message){
		return ResponseEntity.ok(new ErrorResponse(message));
	}

}
