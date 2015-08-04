package test;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.process.DocumentPreprocessor;

public class sentenceDetect {
	
	public static List<String> getSentences(String text){
		Reader reader = new StringReader(text);
		DocumentPreprocessor dp = new DocumentPreprocessor(reader);
		List<String> sentenceList = new ArrayList<String>();

		for (List<HasWord> sentence : dp) {
			String sentenceString = Sentence.listToString(sentence);
			sentenceList.add(sentenceString.toString());
		}
		
		return sentenceList;
	}

	public static void main(String[] args) {
		String paragraph = "The story of Dracula has been the basis for numerous films and plays. Stoker himself wrote the first theatrical adaptation, which was presented at the Lyceum Theatre under the title Dracula(1), or The Undead shortly before the novel's publication and performed only once. Popular films include Dracula (2) (1931), Dracula (alternative title: The Horror of Dracula) (1958), and Dracula  (also known as Bram Stoker's Dracula) (1992). Dracula was also adapted as Nosferatu (1922), a film directed by the German director F. W. Murnau, without permission from Stoker's widow; the filmmakers attempted to avoid copyright problems by altering many of the details, including changing the name of the villain to \"Count Orlok\".";
		List<String> sentenceList = getSentences(paragraph);
		int i=1;
		for (String sentence : sentenceList) {
			System.out.println(i+": "+sentence);
			i++;
		}
	}
}
