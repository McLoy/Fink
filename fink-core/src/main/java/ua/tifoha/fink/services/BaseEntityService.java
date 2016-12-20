package ua.tifoha.fink.services;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public class BaseEntityService<T, R extends JpaRepository<T, Long>> implements EntityService<T, Long>{
	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired 
	protected R repository;

	@Override
	public List<T> findAll() {
		return repository.findAll();
	}

	@Override
	public <S extends T> S save(S entity) {
		return repository.save(entity);
	}

	@Override
	public T findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public boolean exists(Long id) {
		return repository.exists(id);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public void delete(T entity) {
		repository.delete(entity);
	}
}
