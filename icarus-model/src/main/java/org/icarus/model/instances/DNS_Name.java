package org.icarus.model.instances;

import org.icarus.model.configs.DNS_NameConfig;

public class DNS_Name extends DNS_NameConfig{

	private String provider_id;
	private String provider_name;
	
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
}
