package org.icarus.model.configs;

import org.springframework.data.annotation.Id;

public class AutoScalingGroupConfig {
	@Id
	String id;
	String label;
    String group;
    String launch_config;
    String min_size;
    String max_size;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getLaunch_config() {
		return launch_config;
	}
	public void setLaunch_config(String launch_config) {
		this.launch_config = launch_config;
	}
	public String getMin_size() {
		return min_size;
	}
	public void setMin_size(String min_size) {
		this.min_size = min_size;
	}
	public String getMax_size() {
		return max_size;
	}
	public void setMax_size(String max_size) {
		this.max_size = max_size;
	}
}
