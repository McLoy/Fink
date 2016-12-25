package ua.tifoha.fink.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ua.tifoha.fink.entities.User;

import java.util.Collection;
import java.util.List;

public interface UserService extends EntityService<User, Long>, UserDetailsService{

	/**
	 * Find user by username or email
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	@Transactional
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
