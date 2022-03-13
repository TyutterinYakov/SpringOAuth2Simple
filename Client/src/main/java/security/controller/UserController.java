package security.controller;

import org.springframework.http.HttpStatus;
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
	public ResponseEntity<String> getAccess(){
		return new ResponseEntity<>(userProvider.get().isActive()?
				"Access granted":"Forbiden", HttpStatus.ACCEPTED);
	}
	
}
