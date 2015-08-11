package co.hackingedu.app.adapter.view;

import android.graphics.drawable.Drawable;

public class Schedule {

	private String name;
	private String location;
	private String startTime;
	private String endTime;
	private String speaker;
	private String description;
	private int image;

	public Schedule() {
		name = "";
		location = "";
		startTime = "";
		endTime = "";
		image = 0;
		speaker = "";
		description = "";
	}

	public Schedule(String name, String location, String startTime, String endTime, int image, String speaker, String description) {
		this.name = name;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.image = image;
		this.speaker = speaker;
		this.description = description;
	}

	public String getName() {
		return name;
	}
	public String getLocation() {
		return location;
	}
	public String getStartTime() {
		return startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public String getDetails() {
		return startTime + " - " + endTime + " / " + location;
	}
	public int getImage() {
		return image;
	}
	public String getSpeaker() {
		return speaker;
	}
	public String getDescription() {
		return description;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
