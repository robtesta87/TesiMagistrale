package ExtractorMentions;

import info.bliki.wiki.filter.PlainTextConverter;
import info.bliki.wiki.model.WikiModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import persistence.dao.EntryRedirectDAO;
import persistence.dao.EntryRedirectDAOImpl;
import bean.EntryRedirect;
import bean.WikiArticle;



public  class ExtractorFirstMentions {

	final static String mentionRegex = "\\[\\[[\\w+\\s#\\|\\(\\)_-]*\\]\\]";	


	public  WikiArticle extractMentions (String text, String title){
		
		WikiArticle wikiArticle = new WikiArticle();

		//Set<String> setWikid = new HashSet<String>();
		Pattern pattern = Pattern.compile(mentionRegex);
		Matcher matcher = pattern.matcher(text);

		while(matcher.find()){
			String mentionString = matcher.group();
			String stringCleaned = mentionString.substring(2, mentionString.length()-2);
			System.out.println(stringCleaned);


			if(stringCleaned.contains("|")){
				String[] splitted = stringCleaned.split("\\|");
				//primo campo: text secondo campo: wikiid
				wikiArticle.addMention(splitted[0]);
				System.out.println("String 0:"+splitted[0]);
				//matcher.group().replace(mentionString, "[["+splitted[0]+"]]");
				text = text.replace(mentionString, "[["+splitted[0]+"]]");
			}
			else{
				wikiArticle.addMention(stringCleaned);
			}

		}	
		//System.out.println("TEXT: "+text);
		wikiArticle.setText(text);
		wikiArticle.setTitle(title);
		return wikiArticle;
	}
	/*
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
				matcher.group().replace(mentionString, "[["+splitted[0]+"]]");
				System.out.println();
			}
			else{
				textToWikID.put(stringCleaned, stringCleaned);
			}

		}	

		return textToWikID;
	}*/


	/*public Map<String,String> addAnnotations(Set<String> entities, Map<String,String> textToMention){
		for(String currentEntity : entities){
			if(!textToMention.containsKey(currentEntity)){
				//interrogare l'indice di redirect
				//aggiungere in caso positivo il risultato alla map
				EntryRedirectDAO dao = new EntryRedirectDAOImpl();
				EntryRedirect mappingBean = dao.getwikIDFromRedirect(currentEntity);
				if (mappingBean!=null){
					textToMention.put(mappingBean.getWikid(), mappingBean.getRedirect() );
				}
			}
		}



		return textToMention;
	}*/
	/*
	public Set<String> addAnnotations(Map<String,String> entities, Set<String> textToMention){
		Iterator<String> keySetIterator = entities.keySet().iterator();
		while(keySetIterator.hasNext()){
			String currentEntity = keySetIterator.next();
			if(!textToMention.contains(currentEntity)){
				//interrogare l'indice di redirect
				//aggiungere in caso positivo il risultato alla map
				EntryRedirectDAO dao = new EntryRedirectDAOImpl();
				EntryRedirect mappingBean = dao.getwikIDFromRedirect(currentEntity);
				if (mappingBean!=null){
					textToMention.put(mappingBean.getWikid(), mappingBean.getRedirect() );
				}
			}
		}



		return textToMention;
	}
	 */
	public WikiArticle addAnnotations(Map<String,String> entities, WikiArticle wikiArticle){
		Set<String> textToMention = wikiArticle.getMentions();
		Iterator<String> keySetIterator = entities.keySet().iterator();
		while(keySetIterator.hasNext()){
			String currentEntity = keySetIterator.next();
			if(!textToMention.contains(currentEntity)){
				//interrogare l'indice di redirect
				//aggiungere in caso positivo il risultato alla map
				EntryRedirectDAO dao = new EntryRedirectDAOImpl();
				EntryRedirect mappingBean = dao.getwikIDFromRedirect(currentEntity);
				if (mappingBean!=null){
					System.out.println("redirect"+mappingBean.getRedirect()+" "+mappingBean.getWikid());
					wikiArticle.addRedirectEntry(mappingBean.getRedirect(), mappingBean.getWikid() );
				}
			}
		}



		return wikiArticle;
	}

	public static void main(String[] args) {
		//
		String title = "Dracula";
		//String mentionWiki = "This is  [[Hello world | Hello world]] hgjkgy [[ciao mondo | Hello world]]dskdasldlsa [[pippo]]hgsajhkgc[[A#sfhfdhws|dfhask of A]] AC Milan";
		String mentionWiki = "The story of [[Dracula]] [[Bram Stoker]] has been the basis for numerous films and plays. Stoker himself wrote the first theatrical adaptation, which was presented at the Lyceum Theatre under the title ''Dracula, or The Undead'' shortly before the novel's publication and performed only once. Popular films include ''[[Dracula (1931 English-language film)|Dracula]]'' (1931), ''[[Dracula (1958 film)|Dracula]]'' (alternative title: ''The Horror of Dracula'') (1958), and ''[[Dracula (1992 film)|Dracula]]'' (also known as ''Bram Stoker's Dracula'') (1992). ''Dracula'' was also adapted as ''[[Nosferatu]]'' (1922), a film directed by the German director [[F. W. Murnau]], without permission from Stoker's widow; the filmmakers attempted to avoid copyright problems by altering many of the details, including changing the name of the villain to \"[[Count Orlok]]\". AC Milan [[Bill Clinton]]. Clinton was born in Italy.";
		System.out.println("Mention riconosciute dal testo 'sporco':");
		ExtractorFirstMentions extractor = new ExtractorFirstMentions();
		WikiArticle wikiArticle = extractor.extractMentions(mentionWiki,title);
		//Set<String> setWikid = wikiArticle.getMentions();
		//setWikid.add(title);
		mentionWiki= wikiArticle.getText();
		System.out.println("Pulizia testo...");
		//pulizia testo
		WikiModel wikiModel = new WikiModel("http://www.mywiki.com/wiki/${image}", "http://www.mywiki.com/wiki/${title}");
		mentionWiki = wikiModel.render(new PlainTextConverter(), mentionWiki);
		wikiArticle.setText(mentionWiki);
		System.out.println("Testo 'pulito: "+mentionWiki);
		System.out.println("Riconoscimento entità attraverso il NER");
		EntityDetect ed = new EntityDetect();
		SentenceDetect sd = new SentenceDetect();
		//Set<String> namedEntities = ed.getEntitiesFromPhrases(sd.getSentences(mentionWiki));
		Map<String,String> namedEntities = ed.getEntitiesFromPhrases(sd.getSentences(mentionWiki));

		//estrazione entità riconosciute come PERSON
		List<String> persons = new ArrayList<String>();
		Iterator<String> keySetIterator = namedEntities.keySet().iterator();
		while(keySetIterator.hasNext()){
			String currentEntity = keySetIterator.next();
			System.out.println("entità: "+currentEntity+" tipo: "+namedEntities.get(currentEntity));
			if ((namedEntities.get(currentEntity)).equals("PERSON")){
				persons.add(currentEntity);
				//System.out.println("person:"+currentEntity);
			}
		}
		Set<String> wikidKeys = wikiArticle.getMentions();

		for (String person:persons){
			String[] personSpitted = person.split(" ");

			for(String wikid: wikidKeys){
				String[] wikidSplitted = wikid.split(" ");
				if ((wikid.contains(person))&&(wikidSplitted[wikidSplitted.length-1].equals(personSpitted[personSpitted.length-1]))){

					wikiArticle.addPerson(person, wikid);
					wikiArticle.addPerson(personSpitted[personSpitted.length-1], wikid);

				}

			}

		}

		wikiArticle = extractor.addAnnotations(namedEntities, wikiArticle);


		//Set<String> namedEntities = ed.getEntitiesFromPhrases(sd.getSentences(paragraph));

		//visualizzazione Set mention iniziali 
		for(String key: wikidKeys){
			//System.out.println(key+"->"+wikidToText.get(key));
			System.out.println(key);
		}
		//visualizzazione Map PERSON 
		Map<String,String> mapPersons = wikiArticle.getPersors();
		Iterator<String> keyPersonIterator = mapPersons.keySet().iterator();
		while(keyPersonIterator.hasNext()){
			String currentEntity = keyPersonIterator.next();
			System.out.println("person:"+currentEntity+" id "+mapPersons.get(currentEntity));
		}
		//visualizzazione Map redirect 
		Map<String,String> mapRedirect = wikiArticle.getRedirectWikid();
		Iterator<String> keyRedirectIterator = mapRedirect.keySet().iterator();
		while(keyRedirectIterator.hasNext()){
			String currentEntity = keyRedirectIterator.next();
			System.out.println("redirect:"+currentEntity+" id "+mapRedirect.get(currentEntity));
		}


	}
}
