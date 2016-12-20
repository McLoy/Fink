package ua.tifoha.fink.repositories;

import ua.tifoha.fink.entities.User;

public interface UserRepository extends NamedEntityRepository<User>{
	User findByEmail(String email);
}
