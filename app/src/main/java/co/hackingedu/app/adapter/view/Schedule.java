package co.hackingedu.app.adapter.view;

public class Schedule {

	private String name;
	private String location;
	private String startTime;
	private String endTime;
	private int image;

	public Schedule() {
		name = "";
		location = "";
		startTime = "";
		endTime = "";
		image = 0;
	}

	public Schedule(String name, String location, String startTime, String endTime, int image) {
		this.name = name;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.image = image;
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
	public int getImage() {
		return image;
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

}
