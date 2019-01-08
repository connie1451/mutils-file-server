package cn.minsin.file.model;

public class FileSaveResult {

	private boolean isPreview = false;
	
	private String url;

	public boolean isPreview() {
		return isPreview;
	}

	public void setPreview(boolean isPreview) {
		this.isPreview = isPreview;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
