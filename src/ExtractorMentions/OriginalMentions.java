package ExtractorMentions;

import java.util.HashSet;
import java.util.Set;

public class OriginalMentions {
	private Set<String> mentions;
	
	public OriginalMentions(){
		this.mentions = new HashSet<String>();
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
}
