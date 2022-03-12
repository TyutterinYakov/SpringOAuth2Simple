package security.bean;

public class RegistrationRequest {
	private final String userName;
	private final String password;
	
	public RegistrationRequest(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	
	@Override
	public String toString() {
		return "RegistrationRequest [userName=" + userName + ", password=" + password + "]";
	}
	
	
	
	
}
