package org.icarus.model.instances;

import org.icarus.model.configs.AutoScalingGroupConfig;

public class AutoScalingGroup extends AutoScalingGroupConfig{

	private String provider_id;
	private String provider_name;
	
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

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
}
