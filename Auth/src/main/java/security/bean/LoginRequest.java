package security.bean;

public class LoginRequest {

	private final String username;
	private final String password;
	
	public LoginRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	
	
	
	
	
}
