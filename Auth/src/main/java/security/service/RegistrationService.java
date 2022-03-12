package security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import security.bean.RegistrationRequest;
import security.enity.AppUser;

@Service
public class RegistrationService {
	
	private final UserService userService;

	@Autowired
	public RegistrationService(UserService userService) {
		super();
		this.userService = userService;
	}
	
	
	public AppUser register(RegistrationRequest request) {
		return userService.signUpUser(new AppUser(request));
	}
	
	
	
	
	

}
