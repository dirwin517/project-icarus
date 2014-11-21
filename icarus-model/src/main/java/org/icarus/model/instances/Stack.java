package org.icarus.model.instances;

import org.icarus.model.configs.StackConfig;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.index.Indexed;

public class Stack extends StackConfig{

	private String provider_id;
	private String provider_name;
	
	private DateTime created;
	private DateTime timeOfDeath;
	
	@Indexed
	private String environment;

	public String getProvider_id() {
		return provider_id;
	}

	public void setProvider_id(String provider_id) {
		this.provider_id = provider_id;
	}

	public String getProvider_name() {
		return provider_name;
	}

	public void setProvider_name(String provider_name) {
		this.provider_name = provider_name;
	}

	public DateTime getCreated() {
		return created;
	}

	public void setCreated(DateTime created) {
		this.created = created;
	}

	public DateTime getTimeOfDeath() {
		return timeOfDeath;
	}

	public void setTimeOfDeath(DateTime timeOfDeath) {
		this.timeOfDeath = timeOfDeath;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
}
