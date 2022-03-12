package security.access;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import security.bean.CurrentUser;

@Component
public class CurrentUserProvider {
	
	private final HttpServletRequest httpServletRequest;
	private final String ATTR_CURRENT_USER = "CurrentUser";
	
	public CurrentUserProvider(HttpServletRequest httpServletRequest) {
		super();
		this.httpServletRequest = httpServletRequest;
	}

	public void set(CurrentUser currentUser) {
		httpServletRequest.setAttribute(ATTR_CURRENT_USER, currentUser);
	}
	
	public CurrentUser get() {
		CurrentUser currentUser = 
				(CurrentUser) httpServletRequest.getAttribute(ATTR_CURRENT_USER);
		if(currentUser==null) {
			return new CurrentUser();
		}
		return currentUser;
	}
	
	
	
	
	
}
