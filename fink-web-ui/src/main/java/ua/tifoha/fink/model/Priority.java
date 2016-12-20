package ua.tifoha.fink.model;

import org.apache.commons.lang3.StringUtils;

public enum Priority implements BaseEnum {
	HIGH(10), MEDIUM(5), LOW(0);

	private final int value;
	private final String representation;

	Priority(int value) {
		this.value = value;
		this.representation = BaseEnum.defaultRepresentation(this);
	}

	Priority(int value, String representation) {
		this.value = value;
		this.representation = representation;
	}

	@Override
	public int getValue() {
		return value;
	}

//	private String defaultRepresentation() {
//		return StringUtils.capitalize(name());
//	}

	@Override
	public String getRepresentation() {
		return representation;
	}
}
