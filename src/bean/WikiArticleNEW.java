package bean;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import edu.stanford.nlp.util.Pair;

public class WikiArticleNEW{
	//
	private String title;
	private String wikid;

	private String text;
	TreeMap<String, Pair<String,String>> wikiEntities ;
	
	public WikiArticleNEW(){
		this.wikiEntities = new TreeMap<String, Pair<String,String>>();
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




	public TreeMap<String, Pair<String, String>> getWikiEntities() {
		return wikiEntities;
	}

	public void setWikiEntities(TreeMap<String, Pair<String, String>> wikiEntities) {
		this.wikiEntities = wikiEntities;
	}

	public void addWikiEntities (String text, String wikid, String mid){
		Pair<String, String> pair = new Pair<String,String>();
		pair.setFirst(wikid);
		pair.setSecond(mid);
		this.wikiEntities.put(text, pair);
	}

}
