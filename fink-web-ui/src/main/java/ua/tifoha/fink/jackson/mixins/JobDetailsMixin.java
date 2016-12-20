package ua.tifoha.fink.jackson.mixins;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;

public interface JobDetailsMixin {
	@JsonIgnore
	JobBuilder getJobBuilder();

	@JsonIgnore
	String getName();

	@JsonIgnore
	String getGroup();

//	@JsonIgnore
//	JobKey getKey();
//
	@JsonProperty (value = "id", access = JsonProperty.Access.READ_ONLY)
	default String getKeyString() {
		return getGroup() + "." + getName();
	}
}
