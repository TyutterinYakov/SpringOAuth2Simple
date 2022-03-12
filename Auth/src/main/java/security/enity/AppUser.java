package security.enity;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import security.bean.RegistrationRequest;

@Entity
@Table(name="app_user")
public class AppUser implements UserDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userId;
	private String username;
	private String password;
	private boolean active=true;
	@Enumerated(value = EnumType.STRING)
	@Column(name="role")
	private AppUserRole role = AppUserRole.USER;
	
	public AppUser() {
		super();
	}

	public AppUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Long getUserId() {
		return userId;
	}
	
	

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public AppUserRole getRole() {
		return role;
	}

	public void setRole(AppUserRole role) {
		this.role = role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return active;
	}

	@Override
	public boolean isAccountNonLocked() {
		return active;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return active;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

	public AppUser(RegistrationRequest request) {
		this.username = request.getUserName();
		this.password = request.getPassword();
	}
	
	

}
