package ua.tifoha.fink.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface NamedEntityRepository<T> extends JpaRepository<T, Long> {
    T findByName(String name);

    List<T> findAllByName(String name);
}
