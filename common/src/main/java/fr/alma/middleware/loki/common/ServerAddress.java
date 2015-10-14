package fr.alma.middleware.loki.common;

public class ServerAddress {
	private String ip;
	private int port;
	
	public ServerAddress(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	public ServerAddress() {
		this("127.0.0.1",9999);
	}
	
	public String getIp() {
		return this.ip;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String toString() {
		return this.ip+":"+this.port;
	}
}
