package co.hackingedu.app.map;

class Map {

	private String title;
	private int image;
	private int imageThumb;

	public Map() {
		title = "";
		image = 0;
		imageThumb = 0;
	}

	public Map(String title, int image, int imageThumb) {
		this.title = title;
		this.image = image;
		this.imageThumb = imageThumb;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public void setImageThumb(int imageThumb) {
		this.imageThumb = imageThumb;
	}

	public String getTitle() {
		return title;
	}

	public int getImage() {
		return image;
	}

	public int getImageThumb() {
		return imageThumb;
	}

}
