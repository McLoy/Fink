package ua.tifoha.fink.services;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.tifoha.fink.entities.User;
import ua.tifoha.fink.repositories.UserRepository;

import static org.quartz.impl.matchers.GroupMatcher.anyGroup;

@Service
public class UserServiceImpl extends BaseEntityService<User, UserRepository> implements UserService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Stream<Function<String, User>> search;
		if (username.contains("@")) {
			search = Stream.of(repository::findByEmail, repository::findByName);
		} else {
			search = Stream.of(repository::findByName, repository::findByEmail);
		}
		User user = search.map(f -> f.apply(username))
						  .filter(Objects::nonNull)
						  .findFirst()
						  .orElseThrow(() -> {
							  this.logger.debug("Query returned no results for user '" + username + "'");

							  return new UsernameNotFoundException(
									  "Username '" + username + " not found");
						  });
		user.getRoles().size();
		return user;
	}

}
