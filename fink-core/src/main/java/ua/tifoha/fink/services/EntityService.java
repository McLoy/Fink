package ua.tifoha.fink.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface EntityService<T, ID> {
	List<T> findAll();

	@Transactional(readOnly = false)
	<S extends T> S save(S entity);

	T findOne(Long id);

	boolean exists(Long id);

	@Transactional(readOnly = false)
	void delete(Long id);

	@Transactional(readOnly = false)
	void delete(T entity);
}
