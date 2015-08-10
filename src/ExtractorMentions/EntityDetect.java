package ExtractorMentions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;

public class EntityDetect {
	public static Map<String,String> getEntitiesFromPhrases(List<String> phrases){

		String serializedClassifier = "classifiers/english.conll.4class.distsim.crf.ser.gz";
		Map<String,String> annotationsNE = new HashMap<String, String>();
		try {
			AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
			for(String currentPhrase : phrases){
				System.out.println("FRASE: "+currentPhrase);
				List<Triple<String,Integer,Integer>> triples = classifier.classifyToCharacterOffsets(currentPhrase);
				for (Triple<String,Integer,Integer> trip : triples) {
					annotationsNE.put(currentPhrase.substring(trip.second(), trip.third()),trip.first);
					if (trip.first.equals("PERSON")){
						String[] personSplitted = currentPhrase.substring(trip.second(), trip.third()).split(" ");
						annotationsNE.put(personSplitted[personSplitted.length-1],trip.first);
					}
					//System.out.println("key: "+currentPhrase.substring(trip.second(), trip.third())+" value: "+trip.first);
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return annotationsNE;

	}

	/*public static Set<String> getEntitiesFromPhrases(List<String> phrases){

		String serializedClassifier = "classifiers/english.conll.4class.distsim.crf.ser.gz";
		Set<String> annotationsNE = new HashSet<String>();
		try {
			AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
				for(String currentPhrase : phrases){
				List<Triple<String,Integer,Integer>> triples = classifier.classifyToCharacterOffsets(currentPhrase);
				for (Triple<String,Integer,Integer> trip : triples) {
					annotationsNE.add(currentPhrase.substring(trip.second(), trip.third()));

				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return annotationsNE;

	}*/

	public static void main(String[] args) {
		//
		//String paragraph = "Back to the Future is a 1985 American comic science fiction film directed by Robert Zemeckis, written by Zemeckis and Bob Gale, produced by Gale and Neil Canton, and stars Michael J. Fox, Christopher Lloyd, Lea Thompson, Crispin Glover and Thomas F. Wilson. Steven Spielberg, Kathleen Kennedy, and Frank Marshall served as executive producers. In the film, teenager Marty McFly (Fox) is sent back in time to 1955, where he meets his future parents in high school and accidentally becomes his mother's romantic interest. Marty must repair the damage to history by causing his parents-to-be to fall in love, and with the help of eccentric scientist Dr. Emmett \"Doc\" Brown (Lloyd), he must find a way to return to 1985.";
		//String paragraph = "The story of ''Dracula'' has been the basis for numerous films and plays. Stoker himself wrote the first theatrical adaptation, which was presented at the Lyceum Theatre under the title ''Dracula, or The Undead'' shortly before the novel's publication and performed only once. Popular films include ''[[Dracula (1931 English-language film)|Dracula]]'' (1931), ''[[Dracula (1958 film)|Dracula]]'' (alternative title: ''The Horror of Dracula'') (1958), and ''[[Dracula (1992 film)|Dracula]]'' (also known as ''Bram Stoker's Dracula'') (1992). ''Dracula'' was also adapted as ''[[Nosferatu]]'' (1922), a film directed by the German director [[F. W. Murnau]], without permission from Stoker's widow; the filmmakers attempted to avoid copyright problems by altering many of the details, including changing the name of the villain to \"[[Count Orlok]]\". AC Milan";
		String paragraph = "ciao  Dracula e Bill Clinton (hello) . ciao christian.ciao  Dracula e Bill Clinton (hello)\"bella per te\"";
		System.out.println(paragraph);
		SentenceDetect sd = new SentenceDetect();
		EntityDetect ed = new EntityDetect();
		Map<String,String> namedEntities = ed.getEntitiesFromPhrases(sd.getSentences(paragraph));


		Iterator<String> keySetIterator = namedEntities.keySet().iterator();
		while(keySetIterator.hasNext()){
			String key = keySetIterator.next();
			System.out.println("key: " + key + " value: " + namedEntities.get(key));
		}




		/*for(String ne : namedEntities)
			System.out.println(ne);
		 */


	}

}
