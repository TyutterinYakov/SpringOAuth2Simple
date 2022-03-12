package security.bean;

import security.enity.AppUser;

public class UserResponse extends BaseResponse{

	private String username;
	private boolean enabled;
	
	public UserResponse(AppUser user) {
		this.username = user.getUsername();
		this.enabled = user.isActive();
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
