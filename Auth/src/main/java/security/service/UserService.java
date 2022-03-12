package security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import security.access.PasswordConfig;
import security.enity.AppUser;
import security.repository.AppUserRepository;

@Service
public class UserService implements UserDetailsService {

	private final AppUserRepository userDao;
	private final PasswordConfig passwordConfig;

	@Autowired
	public UserService(AppUserRepository userDao, PasswordConfig passwordConfig) {
		super();
		this.userDao = userDao;
		this.passwordConfig = passwordConfig;
	}

	@Override
	public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
		return userDao.findByUsername(username).orElseThrow(()->
				new UsernameNotFoundException("Username not found exception "+username));
	}

	public AppUser signUpUser(AppUser user) {
		userDao.findByUsername(user.getUsername()).ifPresent((u)->{
			throw new IllegalStateException("Username is already taken");
		}); 
		user.setActive(true);
		user.setPassword(encodeString(user.getPassword()));
		return userDao.saveAndFlush(user);
	}

	private String encodeString(String password) {
		return passwordConfig
				.passwordEncoder()
				.encode(password);
	}
	
	public AppUser retrieveFromCache(String username) {
		return (AppUser) new CachingUserDetailsService(this).loadUserByUsername(username);
	}
	
	
}
