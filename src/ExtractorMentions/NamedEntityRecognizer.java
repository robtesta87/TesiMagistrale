package ExtractorMentions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;




public class NamedEntityRecognizer {
	
	public static Map<String,String> annotationNE(String cleanedText){
		
		Map<String,String> annotations  = new HashMap<String,String>();
		
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		
		// create an empty Annotation just with the given text
		Annotation document = new Annotation(cleanedText);

		// run all Annotators on this text
		pipeline.annotate(document);

		// these are all the sentences in this document
		// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		int cont=1;
		for(CoreMap sentence: sentences) {
			System.out.println("sentence "+cont+":"+sentence);
			for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
		        // this is the text of the token
		        String word = token.get(TextAnnotation.class);
		        // this is the POS tag of the token
		        String pos = token.get(PartOfSpeechAnnotation.class);
		        // this is the NER label of the token
		        String ne = token.get(NamedEntityTagAnnotation.class);   
		        System.out.println("WORD: "+word+" pos: "+pos+" NER: "+ne);
		        annotations.put(word, ne);
		      }
			cont++;
		}
		
		return annotations;
	}
	
	public static void main(String[] args) {
		String text ="Back to the Future is a 1985 American comic science fiction film directed by Robert Zemeckis, written by Zemeckis and Bob Gale, produced by Gale and Neil Canton, and stars Michael J. Fox, Christopher Lloyd, Lea Thompson, Crispin Glover and Thomas F. Wilson. Steven Spielberg, Kathleen Kennedy, and Frank Marshall served as executive producers. In the film, teenager Marty McFly (Fox) is sent back in time to 1955, where he meets his future parents in high school and accidentally becomes his mother's romantic interest. Marty must repair the damage to history by causing his parents-to-be to fall in love, and with the help of eccentric scientist Dr. Emmett \"Doc\" Brown (Lloyd), he must find a way to return to 1985.";
		Map<String,String> annotations = annotationNE(text);
		Set<String> anns = annotations.keySet();
		
		for(String key: anns){
			System.out.println(key+"->"+annotations.get(key));
		}
	}
}
