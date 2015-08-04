package bean;

import java.util.Map;

public class WikiArticle {
	
	private String title;
	private Map<String,String> mantions;
	private String text;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Map<String, String> getMantions() {
		return mantions;
	}
	public void setMantions(Map<String, String> mantions) {
		this.mantions = mantions;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
