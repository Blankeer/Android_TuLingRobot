package com.example.myrobots;

import java.sql.Timestamp;

public class Messagee {

	private String content, timeInfo;
	private Timestamp time;
	private boolean isSend = false;

	public Messagee(String content, Timestamp time) {
		super();
		this.content = content;
		this.time = time;
	}

	public Messagee(String content, Timestamp time, boolean isSend) {
		super();
		this.content = content;
		this.time = time;
		this.isSend = isSend;
	}

	@Override
	public String toString() {
		return "Message [content=" + content + ", time=" + time + ", isSend="
				+ isSend + "]";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getTime() {
		return time;
	}

	public String getTimeInfo() {
		return timeInfo;
	}

	public void setTimeInfo(String timeInfo) {
		this.timeInfo = timeInfo;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public boolean isSend() {
		return isSend;
	}

	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}

}
