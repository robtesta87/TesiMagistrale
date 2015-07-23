package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;

public class SentenceDetector {
	public static String[] sentenceDetect(String paragraph) throws InvalidFormatException,IOException {
		InputStream is = new FileInputStream("util/en-sent.bin");
		
//		FileInputStream text = new FileInputStream("util/testoProva");
//		StringBuilder builder = new StringBuilder();
//		int ch;
//		while((ch = text.read()) != -1){
//		    builder.append((char)ch);
//		}
//		String result = builder.toString();
		SentenceModel model = new SentenceModel(is);
		SentenceDetectorME sdetector = new SentenceDetectorME(model);
		String sentences[] = sdetector.sentDetect(paragraph);
		is.close();
		
		
		return sentences;
	}
}
