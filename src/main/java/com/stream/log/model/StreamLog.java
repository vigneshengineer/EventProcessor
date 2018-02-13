package com.stream.log.model;

public class StreamLog {

	private String id;
	private String state;
	private String type;
	private String host;
	private long duration;
	private long timestamp;
	private boolean alert;
	

	
	@Override
	public String toString() {
		return "StreamLog [id=" + id + ", state=" + state + ", type=" + type + ", host=" + host + ", duration="
				+ duration + ", timestamp=" + timestamp + ", alert=" + alert + "]";
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
	public boolean isAlert() {
		return alert;
	}
	public void setAlert(boolean alert) {
		this.alert = alert;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public StreamLog(String id, String state, String type, String host, long timestamp, boolean alert) {
		super();
		this.id = id;
		this.state = state;
		this.type = type;
		this.host = host;
		this.timestamp = timestamp;
		this.alert = alert;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
