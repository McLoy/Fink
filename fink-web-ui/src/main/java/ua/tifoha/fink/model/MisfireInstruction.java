package ua.tifoha.fink.model;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

public enum MisfireInstruction implements BaseEnum {
	FIRE_NOW(1),
	RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT(2),
	RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT(3),
	RESCHEDULE_NEXT_WITH_REMAINING_COUNT(4),
	RESCHEDULE_NEXT_WITH_EXISTING_COUNT(5);


	private final int value;
	private final String representation;

	MisfireInstruction(int value) {
		this.value = value;
		this.representation = BaseEnum.defaultRepresentation(this);
	}

	MisfireInstruction(int value, String representation) {
		this.value = value;
		this.representation = representation;
	}

	@Override
	public int getValue() {
		return value;
	}



	@Override
	public String getRepresentation() {
		return representation;
	}

	public static MisfireInstruction getByValue(int value) {
		return Stream.of(values()).filter(e -> e.value == value).findAny().orElse(null);
	}
}
