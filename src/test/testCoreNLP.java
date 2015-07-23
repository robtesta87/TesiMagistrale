package test;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;

public class testCoreNLP {
	public static void main(String[] args) throws IOException {
			String path_file= "/home/roberto/Scrivania/prova.txt";
			// option #1: By sentence.
			DocumentPreprocessor dp = new DocumentPreprocessor(path_file);
			for (List<HasWord> sentence : dp) {
				System.out.println(sentence);
			}
			/*
			// option #2: By token
			PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<CoreLabel>(new FileReader(path_file),
					new CoreLabelTokenFactory(), "");
			while (ptbt.hasNext()) {
				CoreLabel label = ptbt.next();
				System.out.println(label);
			}*/
		
	}
}
