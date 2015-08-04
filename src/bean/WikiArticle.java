package bean;

import java.util.Map;
import java.util.Set;

public class WikiArticle {
	//
	private String title;
	private String wikid;
	private Map<String,String> persors;
	private Set<String> mentions;
	private String text;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
		
	public String getWikid() {
		return wikid;
	}
	public void setWikid(String wikid) {
		this.wikid = wikid;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Map<String, String> getPersors() {
		return persors;
	}
	public void setPersors(Map<String, String> persors) {
		this.persors = persors;
	}
	public Set<String> getMentions() {
		return mentions;
	}
	public void setMentions(Set<String> mentions) {
		this.mentions = mentions;
	}
	
	
	
}
