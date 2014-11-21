package org.icarus.model.configs;

import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;

public class StackConfig {
	@Indexed
	String label;
	String description;
	String vpc;
	
	List<AutoScalingGroupConfig> asgs;
	List<DNS_NameConfig> dns;
	List<ServerConfig> servers;
	List<LoadBalancerConfig> loadbalancers;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVpc() {
		return vpc;
	}
	public void setVpc(String vpc) {
		this.vpc = vpc;
	}
	public List<AutoScalingGroupConfig> getAsgs() {
		return asgs;
	}
	public void setAsgs(List<AutoScalingGroupConfig> asgs) {
		this.asgs = asgs;
	}
	public List<DNS_NameConfig> getDns() {
		return dns;
	}
	public void setDns(List<DNS_NameConfig> dns) {
		this.dns = dns;
	}
	public List<ServerConfig> getServers() {
		return servers;
	}
	public void setServers(List<ServerConfig> servers) {
		this.servers = servers;
	}
	public List<LoadBalancerConfig> getLoadbalancers() {
		return loadbalancers;
	}
	public void setLoadbalancers(List<LoadBalancerConfig> loadbalancers) {
		this.loadbalancers = loadbalancers;
	}
}
