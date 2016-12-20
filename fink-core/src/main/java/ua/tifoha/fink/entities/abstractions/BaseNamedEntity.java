package ua.tifoha.fink.entities.abstractions;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseNamedEntity extends BaseEntity implements NamedEntity {
	@Column
	protected String name;

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append("[id=").append(id);
		sb.append(" name=").append(name);
		sb.append(']');
		return sb.toString();
	}
}
