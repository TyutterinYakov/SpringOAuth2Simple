package security.access;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import security.enity.AppUser;
import security.service.UserService;

@Component
public class JwtTokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	@Value("${auth.cookie.secret}")
	private String secretKey;
	@Value("${auth.cookie.auth}")
	private String authCookieName;
	@Value("${auth.cookie.refresh}")
	private String refreshCookieName;
	@Value("${auth.cookie.expiration-auth}")
	private Integer authExpirationCookie;
	@Value("${auth.cookie.expiration-refresh}")
	private Integer refreshExpirationCookie;
	@Value("${auth.cookie.path}")
	private String pathCookie;
	
	private final UserService userService;
	
	@Autowired
	public JwtTokenProvider(UserService userService) {
		super();
		this.userService = userService;
	}


	@PostConstruct
	public void init() {
		this.secretKey=Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	
	public String createAuthToken(String username, String role) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("role", role);
		Date now = new Date();
		Date valid = new Date(now.getTime()+getAuthExpirationCookie());
		return Jwts
				.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(valid)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	public String createRefreshToken(String username, String role) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("role", role);
		Date now = new Date();
		Date valid = new Date(now.getTime()+getRefreshExpirationCookie());
		return Jwts
				.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(valid)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	public boolean validateToken(String token) {
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return claimsJws.getBody().getExpiration().after(new Date());
		} catch(ExpiredJwtException ex) {
			logger.error(ex.getLocalizedMessage());
		}
		return false;
	}
	
	public Authentication getAuthentication(String token) {
		AppUser appUser = userService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(appUser, appUser.getPassword(), appUser.getAuthorities());
	}
	
	private String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey)
				.parseClaimsJws(token).getBody().getSubject();
	}
	
	
	public String resolveToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String res = null;
		if(cookies!=null) {
			for(Cookie cookie: cookies) {
				if(cookie.getName().equals(getAuthCookieName())) {
					res = cookie.getValue();
					break;
				}
			}
		}
		return res;
	}


	public String getAuthCookieName() {
		return authCookieName;
	}
	public String getRefreshCookieName() {
		return refreshCookieName;
	}
	public Integer getAuthExpirationCookie() {
		return authExpirationCookie;
	}
	public Integer getRefreshExpirationCookie() {
		return refreshExpirationCookie;
	}
	public String getPathCookie() {
		return pathCookie;
	}
	
	
	
	
	
	
	
}
