package security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import security.access.CurrentUserProvider;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/client")
public class UserController {

	
	private final CurrentUserProvider userProvider;

	public UserController(CurrentUserProvider userProvider) {
		super();
		this.userProvider = userProvider;
	}
	
	
	
	@GetMapping(path = "access")
	public ResponseEntity<?> getAccess(){
		return ResponseEntity.ok(userProvider.get().isEnabled()?
				"Access granted":"Forbiden");
	}
	
}
