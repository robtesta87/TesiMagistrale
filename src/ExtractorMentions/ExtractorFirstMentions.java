package ExtractorMentions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public  class ExtractorFirstMentions {
	
	final static String mentionRegex = "\\[\\[[\\w+\\s#\\|\\(\\)_-]*\\]\\]";	
	public  Map<String,String> extractMentions (String text){
		
		Map<String,String> wikiIdToText = new HashMap<String, String>();
		Pattern pattern = Pattern.compile(mentionRegex);
		Matcher matcher = pattern.matcher(text);
		
		while(matcher.find()){
			String mentionString = matcher.group();
			String stringCleaned = mentionString.substring(2, mentionString.length()-2);
			System.out.println("Mention string: "+mentionString);
			System.out.println("Cleaned Mention string: "+stringCleaned);
			
			if(stringCleaned.contains("|")){
				String[] splitted = stringCleaned.split("\\|");
				//primo campo: text secondo campo: wikiid
				wikiIdToText.put(splitted[1], splitted[0]);
				System.out.println("String 0:"+splitted[0]);
			}
			else{
				wikiIdToText.put(stringCleaned, stringCleaned);
			}
			
		}	
		
		return wikiIdToText;
	}
	
	
public Map<String,String> addAnnotations(Set<String> entities, Map<String,String> textToMention){
	for(String currentEntity : entities){
		if(!textToMention.containsKey(currentEntity)){
			//interrogare l'indice di redirect
			//aggiungere in caso positivo il risultato alla map
		}
	}
	
	
	
	return textToMention;
}
	
	public static void main(String[] args) {
		
		String mentionWiki = "This is  [[Hello world | sdkasdlas]] dskdasldlsa [[pippo]]hgsajhkgc[[A#sfhfdhws|dfhask of A]]";
		ExtractorFirstMentions extractor = new ExtractorFirstMentions();
		Map<String,String> wikidToText = extractor.extractMentions(mentionWiki);
		Set<String> wikidKeys = wikidToText.keySet();
		
		
		SentenceDetect sd = new SentenceDetect();
		EntityDetect ed = new EntityDetect();
//		Set<String> namedEntities = ed.getEntitiesFromPhrases(sd.getSentences(paragraph));
		
		for(String key: wikidKeys){
			System.out.println(key+"->"+wikidToText.get(key));
		}
		
		
	}
}
