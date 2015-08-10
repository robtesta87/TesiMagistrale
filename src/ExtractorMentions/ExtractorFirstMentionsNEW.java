package ExtractorMentions;

import info.bliki.wiki.filter.PlainTextConverter;
import info.bliki.wiki.model.WikiModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.util.Pair;
import persistence.dao.EntryMappedDAO;
import persistence.dao.EntryMappedDAOImpl;
import persistence.dao.EntryRedirectDAO;
import persistence.dao.EntryRedirectDAOImpl;
import bean.EntryMappedBean;
import bean.EntryRedirect;
import bean.WikiArticleNEW;



public  class ExtractorFirstMentionsNEW {

	final static String mentionRegex = "\\[\\[[\\w+\\s#\\|\\(\\)_-]*\\]\\]";


	public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
		Comparator<K> valueComparator = 
				new Comparator<K>() {
			public int compare(K k1, K k2) {
				//int compare = map.get(k1).compareTo(map.get(k2));
				int compare = k1.toString().length()-k2.toString().length();
				if (compare > 0) 
					return -1;
				else 
					return 1;
			}
		};

		Map<K, V> sortedByValues = 
				new TreeMap<K, V>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}


	
	public  OriginalMentions extractMentions (String text, WikiArticleNEW wikiArticle){

		OriginalMentions om = new OriginalMentions();


		Pattern pattern = Pattern.compile(mentionRegex);
		Matcher matcher = pattern.matcher(text);

		while(matcher.find()){
			String mentionString = matcher.group();
			String stringCleaned = mentionString.substring(2, mentionString.length()-2);
			System.out.println(stringCleaned);


			if(stringCleaned.contains("|")){
				String[] splitted = stringCleaned.split("\\|");
				//primo campo: text secondo campo: wikiid
				om.addMention(splitted[0]);

				text = text.replace(mentionString, "[["+splitted[0]+"]]");
			}
			else{
				om.addMention(stringCleaned);
			}

		}	

		wikiArticle.setText(text);
		return om;
	}

	public void addAnnotations(Map<String,String> entities, Set<String> wikidKeys, Persons persons, RedirectWikid redirectWikid){

		Iterator<String> keySetIterator = entities.keySet().iterator();
		while(keySetIterator.hasNext()){
			String currentEntity = keySetIterator.next();
			String type = entities.get(currentEntity);
			if (type.equals("PERSON")){
				String[] personSpitted = currentEntity.split(" ");

				for(String wikid: wikidKeys){
					String[] wikidSplitted = wikid.split(" ");
					if ((wikid.contains(currentEntity))&&(wikidSplitted[wikidSplitted.length-1].equals(personSpitted[personSpitted.length-1]))){

						persons.addPerson(currentEntity, wikid);
						persons.addPerson(personSpitted[personSpitted.length-1], wikid);

					}

				}
			}
			if(!wikidKeys.contains(currentEntity)){
				//interrogare l'indice di redirect
				//aggiungere in caso positivo il risultato alla map
				EntryRedirectDAO dao = new EntryRedirectDAOImpl();
				EntryRedirect mappingBean = dao.getwikIDFromRedirect(currentEntity);
				if (mappingBean!=null){
					System.out.println("redirect"+currentEntity+" "+mappingBean.getWikid());
					redirectWikid.addRedirectEntry(currentEntity, mappingBean.getWikid() );
				}
			}
		}

	}

	public static void UpdateOriginalMentions (WikiArticleNEW wikiArticle, Set<String> originalMentions){

		for (String originalMention:originalMentions){
			String wikid = originalMention.replaceAll(" ", "_");
			EntryMappedDAO dao = new EntryMappedDAOImpl();
			EntryMappedBean mappingBean = dao.getMidFromWikID(wikid);
			String mid="";
			if (mappingBean!=null){
				mid= mappingBean.getMid();
			}
			wikiArticle.addWikiEntities(originalMention, wikid, mid);
			System.out.println("text: "+originalMention+" wikid: "+wikid+" mid: "+mid);
			
		}
	}
	
	public static void UpdateMentionsRedirect (WikiArticleNEW wikiArticle, Map<String,String> mapRedirect){
		Iterator<String> keyRedirectIterator = mapRedirect.keySet().iterator();
		while(keyRedirectIterator.hasNext()){
			String currentEntity = keyRedirectIterator.next();
			String wikid = mapRedirect.get(currentEntity).replaceAll(" ", "_");
			Pair<String, String> pair = new Pair<String,String>();
			pair.setFirst(wikid);
			EntryMappedDAO dao = new EntryMappedDAOImpl();
			EntryMappedBean mappingBean = dao.getMidFromWikID(wikid);
			String mid="";
			if (mappingBean!=null){
				mid= mappingBean.getMid();
			}
			pair.setSecond(mid);
			//treemap.put(currentEntity,pair);
			wikiArticle.addWikiEntities(currentEntity, wikid, mid);
			System.out.println("redirect:"+currentEntity+" id "+wikid);
		}
	}
	
	public static void UpdateMentionsPerson (WikiArticleNEW wikiArticle, Map<String,String> mapPersons){
		Iterator<String> keyPersonIterator = mapPersons.keySet().iterator();
		while(keyPersonIterator.hasNext()){
			String currentEntity = keyPersonIterator.next();

			String wikidPerson = mapPersons.get(currentEntity).replaceAll(" ", "_");
			Pair<String, String> pair = new Pair<String,String>();
			pair.setFirst(wikidPerson);
			EntryMappedDAO dao = new EntryMappedDAOImpl();
			EntryMappedBean mappingBean = dao.getMidFromWikID(wikidPerson);
			String mid="";
			if (mappingBean!=null){
				mid= mappingBean.getMid();
			}
			pair.setSecond(mid);
			//treemap.put(currentEntity, pair);
			wikiArticle.addWikiEntities(currentEntity, wikidPerson, mid);
			System.out.println("person:"+currentEntity+" id "+wikidPerson);
		}
	}
	
	public static TreeMap<String, Pair<String,String>> getMid (String text, String title){
		System.out.println("Mention riconosciute dal testo 'sporco':");
		ExtractorFirstMentionsNEW extractor = new ExtractorFirstMentionsNEW();
		
		WikiArticleNEW wikiArticle = new WikiArticleNEW();
		wikiArticle.setTitle(title);
		OriginalMentions om = extractor.extractMentions(text,wikiArticle);
		Set<String> originalMentions = om.getMentions();
		UpdateOriginalMentions(wikiArticle, originalMentions);
		

		text= wikiArticle.getText();
		System.out.println("Pulizia testo...");
		//pulizia testo
		WikiModel wikiModel = new WikiModel("http://www.mywiki.com/wiki/${image}", "http://www.mywiki.com/wiki/${title}");
		text = wikiModel.render(new PlainTextConverter(), text);
		wikiArticle.setText(text);
		System.out.println("Testo 'pulito: "+text);
		System.out.println("Riconoscimento entità attraverso il NER");
		EntityDetect ed = new EntityDetect();
		SentenceDetect sd = new SentenceDetect();
		List<String> phrases = sd.getSentences(text);
		Map<String,String> namedEntities = ed.getEntitiesFromPhrases(phrases);

		Persons persons = new Persons();
		RedirectWikid redirectWikid = new RedirectWikid();
		
		
		extractor.addAnnotations(namedEntities,originalMentions,persons, redirectWikid);
		TreeMap<String, Pair<String,String>> treemap = new TreeMap<String, Pair<String,String>>();
/*
		//visualizzazione Set mention iniziali 
		for(String key: originalMentions){
			//System.out.println(key+"->"+wikidToText.get(key));
			String wikid = key.replaceAll(" ", "_");
			Pair<String, String> pair = new Pair<String,String>();
			pair.setFirst(wikid);
			EntryMappedDAO dao = new EntryMappedDAOImpl();
			EntryMappedBean mappingBean = dao.getMidFromWikID(wikid);
			String mid="";
			if (mappingBean!=null){
				mid= mappingBean.getMid();
			}
			pair.setSecond(mid);
			treemap.put(key, pair );
			System.out.println(key);
		}*/
		//visualizzazione e aggiornamento Map PERSON.
		Map<String,String> mapPersons = persons.getPersors();
		UpdateMentionsPerson(wikiArticle, mapPersons);
				
		//visualizzazione e aggiornamento Map redirect 
		Map<String,String> mapRedirect = redirectWikid.getRedirectWikid();
		UpdateMentionsRedirect(wikiArticle, mapRedirect);
		

		Map sortedMap = sortByValues(wikiArticle.getWikiEntities());

		// Get a set of the entries on the sorted map
		Set set = sortedMap.entrySet();
		// Get an iterator
		Iterator i = set.iterator();

		// Display elements
		while(i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next();
			System.out.print(me.getKey() + "---> ");

			System.out.println(me.getValue().toString());
		}

		for (String phrase:phrases){
			Iterator i2 = set.iterator();


			System.out.println(phrase);
			// Display elements
			while((i2.hasNext())) {
				Map.Entry me = (Map.Entry)i2.next();
				String key = me.getKey().toString().replaceAll("\\(","-LRB- ");
				key = key.replaceAll( "\\)"," -RRB-");
				//String key = "Dracula -LRB- 1931 English-language film -RRB-";
				String mid = ((Pair<String,String>) me.getValue()).second();
				phrase = phrase.replaceAll(key, "[["+mid+"]]");
			}
			System.out.println(phrase);
		}
		return wikiArticle.getWikiEntities();



	}

	public static void main(String[] args) {

		String title = "Dracula";
		//String mentionWiki = "This is  [[Hello world | Hello world]] hgjkgy [[ciao mondo | Hello world]]dskdasldlsa [[pippo]]hgsajhkgc[[A#sfhfdhws|dfhask of A]] AC Milan";
		String mentionWiki = "The story of [[Dracula]] [[Bram Stoker]] has been the basis for numerous films and plays. Stoker himself wrote the first theatrical adaptation, which was presented at the Lyceum Theatre under the title ''Dracula, or The Undead'' shortly before the novel's publication and performed only once. Popular films include ''[[Dracula (1931 English-language film)|Dracula]]'' (1931), ''[[Dracula (1958 film)|Dracula]]'' (alternative title: ''The Horror of Dracula'') (1958), and ''[[Dracula (1992 film)|Dracula]]'' (also known as ''Bram Stoker's Dracula'') (1992). ''Dracula'' was also adapted as ''[[Nosferatu]]'' (1922), a film directed by the German director [[F. W. Murnau]], without permission from Stoker's widow; the filmmakers attempted to avoid copyright problems by altering many of the details, including changing the name of the villain to \"[[Count Orlok]]\". AC Milan [[Bill Clinton]]. Clinton was born in Italy.";
		//String mentionWiki ="Popular films include Dracula (1931 English-language film) (1931) , Dracula (1958 film) (alternative title : The Horror of Dracula) (1958) , and Dracula (1992 film) (also known as Bram Stoker 's Dracula) (1992) .";
		//List<String> phrases = getPhrasesWithMid(mentionWiki,title);
		TreeMap<String, Pair<String,String>> treemap = getMid(mentionWiki, title);
		
		
		
		Map sortedMap = sortByValues(treemap);

		// Get a set of the entries on the sorted map
		Set set = sortedMap.entrySet();
		// Get an iterator
		Iterator i = set.iterator();

		// Display elements
		while(i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next();
			System.out.print(me.getKey() + "---> ");

			System.out.println(me.getValue().toString());
		}
		
	}
}
