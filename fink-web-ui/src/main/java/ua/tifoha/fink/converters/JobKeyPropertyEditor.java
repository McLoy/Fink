package ua.tifoha.fink.converters;

import java.beans.PropertyEditorSupport;
import java.util.Objects;

import org.quartz.JobKey;

public class JobKeyPropertyEditor extends PropertyEditorSupport {
	@Override
	public String getAsText() {
		return Objects.toString(getValue());
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		String[] strings = text.split("\\.", 2);
		JobKey key = null;

		if (strings.length == 2) {
			key = new JobKey(strings[1], strings[0]);
		}

		setValue(key);

	}
}
