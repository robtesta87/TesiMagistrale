package bean;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WikiArticle {
	//
	private String title;
	private String wikid;
	private Map<String,String> persors;
	private Set<String> mentions;
	private Map<String,String> redirectWikid;
	private String text;
	
	public WikiArticle(){
		this.persors = new TreeMap<String,String>();
		this.mentions = new HashSet<String>();
		this.redirectWikid = new TreeMap<String,String>();
	}
	
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
	
	public void addMention (String mention){
		this.mentions.add(mention);
	}

	public Map<String, String> getRedirectWikid() {
		return redirectWikid;
	}

	public void addRedirectEntry (String redirect,String wikid){
		this.redirectWikid.put(redirect, wikid);
	}
	
	public void addPerson (String person, String wikid){
		this.persors.put(person, wikid);
	}

}
