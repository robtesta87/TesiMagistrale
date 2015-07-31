package ExtractorMentions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;

public class EntityDetect {
	public static Set<String> getEntitiesFromPhrases(List<String> phrases){

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

	}

	public static void main(String[] args) {
		
		String paragraph = "Back to the Future is a 1985 American comic science fiction film directed by Robert Zemeckis, written by Zemeckis and Bob Gale, produced by Gale and Neil Canton, and stars Michael J. Fox, Christopher Lloyd, Lea Thompson, Crispin Glover and Thomas F. Wilson. Steven Spielberg, Kathleen Kennedy, and Frank Marshall served as executive producers. In the film, teenager Marty McFly (Fox) is sent back in time to 1955, where he meets his future parents in high school and accidentally becomes his mother's romantic interest. Marty must repair the damage to history by causing his parents-to-be to fall in love, and with the help of eccentric scientist Dr. Emmett \"Doc\" Brown (Lloyd), he must find a way to return to 1985.";
		SentenceDetect sd = new SentenceDetect();
		EntityDetect ed = new EntityDetect();
		Set<String> namedEntities = ed.getEntitiesFromPhrases(sd.getSentences(paragraph));
		
		
		for(String ne : namedEntities)
			System.out.println(ne);
		
		System.out.println();
		
	}

}
