package security.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtSettingsProvider {

	
	@Value("${auth.cookie.auth}")
	private String cookieAuthTokenName;

	public String getCookieAuthTokenName() {
		return cookieAuthTokenName;
	}
	
	
}
