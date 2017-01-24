package ua.tifoha.fink.repositories;

import ua.tifoha.fink.entities.User;

import java.util.List;

public interface UserRepository extends NamedEntityRepository<User>{
	User findByEmail(String email);
}
