package security.bean;

public class CurrentUser {

	private boolean ok;
	private String username;
	private boolean active;
	
	public CurrentUser() {
		super();
	}
	public CurrentUser(String username, boolean enabled) {
		super();
		this.username = username;
		this.active = enabled;
	}
	
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
	
	
	
	
}
