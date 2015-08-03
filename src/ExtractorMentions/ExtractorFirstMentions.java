package ExtractorMentions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import persistence.dao.EntryRedirectDAO;
import persistence.dao.EntryRedirectDAOImpl;
import bean.EntryRedirect;



public  class ExtractorFirstMentions {
	
	final static String mentionRegex = "\\[\\[[\\w+\\s#\\|\\(\\)_-]*\\]\\]";	
	public  Map<String,String> extractMentions (String text){
		
		Map<String,String> textToWikID = new HashMap<String, String>();
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
				textToWikID.put(splitted[0], splitted[1]);
				System.out.println("String 0:"+splitted[0]);
			}
			else{
				textToWikID.put(stringCleaned, stringCleaned);
			}
			
		}	
		
		return textToWikID;
	}
	
	
public Map<String,String> addAnnotations(Set<String> entities, Map<String,String> textToMention){
	for(String currentEntity : entities){
		if(!textToMention.containsKey(currentEntity)){
			//interrogare l'indice di redirect
			//aggiungere in caso positivo il risultato alla map
			EntryRedirectDAO dao = new EntryRedirectDAOImpl();
			EntryRedirect mappingBean = dao.getwikIDFromRedirect(currentEntity);
			if (mappingBean!=null){
				textToMention.put(mappingBean.getRedirect(),mappingBean.getWikid() );
			}
		}
	}
	
	
	
	return textToMention;
}
	
	public static void main(String[] args) {
		
		String mentionWiki = "This is  [[Hello world | Hello world]] hgjkgy [[ciao mondo | Hello world]]dskdasldlsa [[pippo]]hgsajhkgc[[A#sfhfdhws|dfhask of A]] AC Milan";
		ExtractorFirstMentions extractor = new ExtractorFirstMentions();
		Map<String,String> wikidToText = extractor.extractMentions(mentionWiki);
		
		EntityDetect ed = new EntityDetect();
		SentenceDetect sd = new SentenceDetect();
		Set<String> namedEntities = ed.getEntitiesFromPhrases(sd.getSentences(mentionWiki));
	
		wikidToText = extractor.addAnnotations(namedEntities, wikidToText);
		Set<String> wikidKeys = wikidToText.keySet();
		
		
//		Set<String> namedEntities = ed.getEntitiesFromPhrases(sd.getSentences(paragraph));
		
		for(String key: wikidKeys){
			System.out.println(key+"->"+wikidToText.get(key));
		}
		
		
	}
}
