package ExtractorMentions;


import java.util.Map;
import java.util.TreeMap;

public class Persons {
	private Map<String,String> persors;

	public Persons(){
		this.persors = new TreeMap<String,String>();
	}

	
	public Map<String, String> getPersors() {
		return persors;
	}


	public void setPersors(Map<String, String> persors) {
		this.persors = persors;
	}


	public void addPerson (String person, String wikid){
		this.persors.put(person, wikid);
	}
	
}
