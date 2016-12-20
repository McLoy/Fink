package ua.tifoha.fink.model;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractModel {
	protected String name = this.getClass().getSimpleName().toLowerCase();
	protected String title = this.getClass().getSimpleName().toLowerCase();

	public AbstractModel() {
		name = this.getClass().getSimpleName().toLowerCase();
		title = StringUtils.capitalize(name);
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
