package ua.tifoha.fink.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.tifoha.fink.entities.abstractions.BaseNamedEntity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@AttributeOverride(name = "name", column = @Column(name = "username", nullable = false, unique = true))
public class User extends BaseNamedEntity implements UserDetails{
	@Column (nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Basic
	@Column(columnDefinition="tinyint(1) default 0")
	private boolean enabled;

	@OneToMany
	private Collection<Role> roles = new LinkedHashSet<>();

//	private String newPassword;
//
//	private String confirmNewPassword;
//
//	public String getNewPassword() {
//		return newPassword;
//	}
//
//	public String getConfirmNewPassword() {
//		return confirmNewPassword;
//	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public void addRoles(Role... roles) {
		this.roles.addAll(Arrays.asList(roles));
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
