package org.icarus.model.configs;

public class ServerConfig {
	String label;
    String quantity;
    String load_balancer;
    String instance_size;
    String subnet;
    String security_group;
    String auto_scaling;
    String base_ami;
	boolean shared;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getLoad_balancer() {
		return load_balancer;
	}
	public void setLoad_balancer(String load_balancer) {
		this.load_balancer = load_balancer;
	}
	public String getInstance_size() {
		return instance_size;
	}
	public void setInstance_size(String instance_size) {
		this.instance_size = instance_size;
	}
	public String getSubnet() {
		return subnet;
	}
	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}
	public String getSecurity_group() {
		return security_group;
	}
	public void setSecurity_group(String security_group) {
		this.security_group = security_group;
	}
	public String getAuto_scaling() {
		return auto_scaling;
	}
	public void setAuto_scaling(String auto_scaling) {
		this.auto_scaling = auto_scaling;
	}
	public String getBase_ami() {
		return base_ami;
	}
	public void setBase_ami(String base_ami) {
		this.base_ami = base_ami;
	}
	public boolean isShared() {
		return shared;
	}
	public void setShared(boolean shared) {
		this.shared = shared;
	}
}
