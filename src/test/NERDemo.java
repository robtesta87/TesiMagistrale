package test;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;
import edu.stanford.nlp.util.Triple;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/** This is a demo of calling CRFClassifier programmatically.
 *  <p>
 *  Usage: {@code java -mx400m -cp "*" NERDemo [serializedClassifier [fileName]] }
 *  <p>
 *  If arguments aren't specified, they default to
 *  classifiers/english.all.3class.distsim.crf.ser.gz and some hardcoded sample text.
 *  If run with arguments, it shows some of the ways to get k-best labelings and
 *  probabilities out with CRFClassifier. If run without arguments, it shows some of
 *  the alternative output formats that you can get.
 *  <p>
 *  To use CRFClassifier from the command line:
 *  </p><blockquote>
 *  {@code java -mx400m edu.stanford.nlp.ie.crf.CRFClassifier -loadClassifier [classifier] -textFile [file] }
 *  </blockquote><p>
 *  Or if the file is already tokenized and one word per line, perhaps in
 *  a tab-separated value format with extra columns for part-of-speech tag,
 *  etc., use the version below (note the 's' instead of the 'x'):
 *  </p><blockquote>
 *  {@code java -mx400m edu.stanford.nlp.ie.crf.CRFClassifier -loadClassifier [classifier] -testFile [file] }
 *  </blockquote>
 *
 *  @author Jenny Finkel
 *  @author Christopher Manning
 */

public class NERDemo {

	public static Set<String> analyzeThisFile(String path){

		String serializedClassifier = "classifiers/english.conll.4class.distsim.crf.ser.gz";


		Set<String> annotationsNE = new HashSet<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
			String line = "";
			while((line = br.readLine())!=null){
				List<Triple<String,Integer,Integer>> triples = classifier.classifyToCharacterOffsets(line);
				for (Triple<String,Integer,Integer> trip : triples) {
					annotationsNE.add(line.substring(trip.second(), trip.third()));
				}
			}
		
			System.out.println("Named Entity TROVATE");
			for(String ne : annotationsNE)
				System.out.println(ne);

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






	public static void main(String[] args) throws Exception {
		String path = "/home/chris88/Scrivania/Alberta_cleaned.txt";
		analyzeThisFile(path);
		
		
	}
		/*

		String serializedClassifier = "classifiers/english.conll.4class.distsim.crf.ser.gz";

		AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);

		String[]example ={"Alberta (/ælˈbɜrtə/) is a western province of Canada. With a population of 3,645,257 in 2011 and an estimated population of 4,145,992 as of October 1, 2014, it is Canada's fourth-most populous province and most populous of Canada's three prairie provinces. Alberta and its neighbour, Saskatchewan, were established as provinces on September 1, 1905.The current premier of the province is Rachel Notley."};
		for (String str : example) {
			System.out.println(classifier.classifyToString(str));
		}
		System.out.println("---");

		for (String str : example) {
			// This one puts in spaces and newlines between tokens, so just print not println.
			System.out.print(classifier.classifyToString(str, "slashTags", false));
		}
		System.out.println("---");

		for (String str : example) {
			// This one is best for dealing with the output as a TSV (tab-separated column) file.
			// The first column gives entities, the second their classes, and the third the remaining text in a document
			System.out.print(classifier.classifyToString(str, "tabbedEntities", false));
		}
		System.out.println("---");

		for (String str : example) {
			System.out.println(classifier.classifyWithInlineXML(str));
		}
		System.out.println("---");

		for (String str : example) {
			System.out.println(classifier.classifyToString(str, "xml", true));
		}
		System.out.println("---");

		for (String str : example) {
			System.out.print(classifier.classifyToString(str, "tsv", false));
		}
		System.out.println("---");

		// This gets out entities with character offsets
		int j = 0;
		for (String str : example) {
			j++;
			List<Triple<String,Integer,Integer>> triples = classifier.classifyToCharacterOffsets(str);
			for (Triple<String,Integer,Integer> trip : triples) {
				System.out.printf("String %s is type of %s over character offsets [%d, %d) in sentence %d.%n",str.substring(trip.second(), trip.third()),trip.first(), trip.second(), trip.third, j);
			}
		}
		System.out.println("---");

		// This prints out all the details of what is stored for each token
		int i=0;
		for (String str : example) {
			for (List<CoreLabel> lcl : classifier.classify(str)) {
				for (CoreLabel cl : lcl) {
					System.out.print(i++ + ": ");
					System.out.println(cl.toShorterString());
				}
			}
		}

		System.out.println("---");

	}
	*/



}
