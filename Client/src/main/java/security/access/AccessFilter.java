package security.access;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import security.bean.CurrentUser;
import security.provider.JwtSettingsProvider;

@Order(1)
@Component
public class AccessFilter implements Filter{

	private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);
	
	private final JwtSettingsProvider settingsProvider;
	private final CurrentUserProvider userProvider;
	private final Gson gson = new Gson();
	
	@Autowired
	public AccessFilter(JwtSettingsProvider settingsProvider, CurrentUserProvider userProvider) {
		super();
		this.settingsProvider = settingsProvider;
		this.userProvider = userProvider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Cookie[] cookies = httpServletRequest.getCookies();
		CurrentUser currentUser = null;
		String authToken = null;
		if(cookies!=null) {
			for(Cookie c: cookies) {
				if(c.getName().equals(settingsProvider.getCookieAuthTokenName())) {
					authToken=c.getValue();
					break;
				}
			}
		}
		if(authToken!=null && !authToken.isBlank()) {
			currentUser = fetchRemoteUser(httpServletRequest); 
		}
		
	}
	

	private CurrentUser fetchRemoteUser(HttpServletRequest httpServletRequest) {
		try {
			URL url = new URL("http:://localhost:8001/api/v1/auth/current");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(1000);
			connection.setRequestProperty("Cookie", httpServletRequest.getHeader("Cookie"));
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuilder content = new StringBuilder();
			while((inputLine=in.readLine())!=null) {
				content.append(inputLine);
			}
			in.close();
			connection.disconnect();
			return gson.fromJson(content.toString(), CurrentUser.class);
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}
		return new CurrentUser();
	}

}
