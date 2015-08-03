package test;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;



public class testCoreNLP2 {
	public static void main(String[] args) {
		// creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		// read some text in the text variable
		//String text = "As an anti-dogmatic philosophy, anarchism draws on many currents of thought and strategy. Anarchism does not offer a fixed body of doctrine from a single particular world view, instead fluxing and flowing as a philosophy.[19] There are many types and traditions of anarchism, not all of which are mutually exclusive.[20] Anarchist schools of thought can differ fundamentally, supporting anything from extreme individualism to complete collectivism.[10] Strains of anarchism have often been divided into the categories of social and individualist anarchism or similar dual classifications.[21][22] Anarchism is usually considered a radical left-wing ideology,[23][24] and much of anarchist economics and anarchist legal philosophy reflect anti-authoritarian interpretations of communism, collectivism, syndicalism, mutualism, or participatory economics. Bill Clinton was born in Italy.";
		String text = "The story of Dracula has been the basis for numerous films and plays. Stoker himself wrote the first theatrical adaptation, which was presented at the Lyceum Theatre under the title Dracula, or The Undead shortly before the novel's publication and performed only once. Popular films include Dracula (1931), Dracula (alternative title: The Horror of Dracula) (1958), and Dracula (also known as Bram Stoker's Dracula) (1992). Dracula was also adapted as Nosferatu (1922), a film directed by the German director F. W. Murnau, without permission from Stoker's widow; the filmmakers attempted to avoid copyright problems by altering many of the details, including changing the name of the villain to \"Count Orlok\".";
				// create an empty Annotation just with the given text
		Annotation document = new Annotation(text);

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
		        
		        //System.out.println("Word: "+word);
		        // this is the POS tag of the token
		        String pos = token.get(PartOfSpeechAnnotation.class);
		        // this is the NER label of the token
		        String ne = token.get(NamedEntityTagAnnotation.class);   
		        //System.out.println("WORD: "+word+" pos: "+pos+" NER: "+ne);
		      }
			cont++;
		}
		}
	
	
	}
