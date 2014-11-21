package org.icarus.model.configs;

import java.util.List;

public class LoadBalancerConfig {
	 String label;
    String load_balancer_port;
    String protocol;
    String instance_port;
    String instance_protocol;
    List<String> security_groups;
    String subnet;
    
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLoad_balancer_port() {
		return load_balancer_port;
	}
	public void setLoad_balancer_port(String load_balancer_port) {
		this.load_balancer_port = load_balancer_port;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getInstance_port() {
		return instance_port;
	}
	public void setInstance_port(String instance_port) {
		this.instance_port = instance_port;
	}
	public String getInstance_protocol() {
		return instance_protocol;
	}
	public void setInstance_protocol(String instance_protocol) {
		this.instance_protocol = instance_protocol;
	}
	public List<String> getSecurity_groups() {
		return security_groups;
	}
	public void setSecurity_groups(List<String> security_groups) {
		this.security_groups = security_groups;
	}
	public String getSubnet() {
		return subnet;
	}
	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}
}
