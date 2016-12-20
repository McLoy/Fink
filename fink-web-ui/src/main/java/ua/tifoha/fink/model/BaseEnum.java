package ua.tifoha.fink.model;

import static org.apache.commons.lang3.StringUtils.capitalize;

import org.apache.commons.lang3.StringUtils;

public interface BaseEnum {
	int getValue();

	String getRepresentation();
	static String defaultRepresentation(Enum<?> baseEnum) {
		return StringUtils.capitalize(baseEnum.name().replace("_", " ").toLowerCase());
	}
}
