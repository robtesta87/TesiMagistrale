package ExtractorMentions;

import java.util.Map;
import java.util.TreeMap;

public class RedirectWikid {
	private Map<String,String> redirectWikid;
	
	public RedirectWikid(){
		this.redirectWikid = new TreeMap<String,String>();

	}
	
	
	
	public Map<String, String> getRedirectWikid() {
		return redirectWikid;
	}



	public void setRedirectWikid(Map<String, String> redirectWikid) {
		this.redirectWikid = redirectWikid;
	}



	public void addRedirectEntry (String redirect,String wikid){
		this.redirectWikid.put(redirect, wikid);
	}

}
