package security.bean;

public class CurrentUser {

	private String username;
	private boolean enabled;
	
	public CurrentUser() {
		super();
	}
	public CurrentUser(String username, boolean enabled) {
		super();
		this.username = username;
		this.enabled = enabled;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
	
	
	
	
}
