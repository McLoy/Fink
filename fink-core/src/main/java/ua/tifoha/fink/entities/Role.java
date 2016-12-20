package ua.tifoha.fink.entities;

import org.springframework.security.core.GrantedAuthority;
import ua.tifoha.fink.entities.abstractions.BaseNamedEntity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@AttributeOverride(name = "name", column = @Column(nullable = false, unique = true))
public class Role extends BaseNamedEntity implements GrantedAuthority{

	@Override
	public String getAuthority() {
		return name;
	}
}
