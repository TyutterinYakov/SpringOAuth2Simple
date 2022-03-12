package security.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import security.bean.ErrorResponse;
import security.bean.RegistrationRequest;
import security.bean.UserResponse;
import security.enity.AppUser;
import security.service.RegistrationService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	private final RegistrationService registrationService;

	@Autowired
	public AuthController(RegistrationService registrationService) {
		super();
		this.registrationService = registrationService;
	}
	
	@PostMapping(path="/register")
	public ResponseEntity<?> registration(
			@RequestBody RegistrationRequest request, HttpServletResponse response){
		try {
			AppUser user = registrationService.register(request);
			return buildUserResponse(user);
		} catch(Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return buildErrorResponse(ex.getLocalizedMessage());
		}
		
	}
	
	
	
	private ResponseEntity<?>buildUserResponse(AppUser user){
		return ResponseEntity.ok(new UserResponse(user));
	}
	
	private ResponseEntity<?>buildErrorResponse(String message){
		return ResponseEntity.ok(new ErrorResponse(message));
	}

}
