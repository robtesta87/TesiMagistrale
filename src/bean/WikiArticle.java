package bean;

import java.util.Map;

public class WikiArticle {
	//
	private String title;
	private String wikid;
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
	
	public String getWikid() {
		return wikid;
	}
	public void setWikid(String wikid) {
		this.wikid = wikid;
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
