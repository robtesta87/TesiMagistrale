package testIndex;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.util.Version;
import org.apache.lucene.search.spell.Dictionary;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class invertedIndexTitleMid {
	static final String IndexPath = "util/index_lucene/";
	static final String DictionaryPath = "util/dictionar/";
	static final String TitleMidPath = "/media/roberto/Elements/TesiMagistrale/componenti/title_wkid_mid.txt";

	public static void main(String[] args) throws IOException {

		FileReader f = new FileReader(TitleMidPath);
		BufferedReader b = new BufferedReader(f);

		System.out.println("Creazione Indice inverso nella direcory: " +IndexPath + "'...");
		//Analyzer analyzer = new StopAnalyzer(Version.LUCENE_47);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);

		Directory index = FSDirectory.open(new File((IndexPath)));
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47,analyzer);
		config.setOpenMode(OpenMode.CREATE_OR_APPEND);

		IndexWriter writer = new IndexWriter(index, config);
		Date start = new Date();
		String [] fieldsText;
		String title = "";
		String mid = "";
		String line = "";
		int i=0;
		while(i<100){
			line=b.readLine();
			fieldsText = line.split(" ");
			title = fieldsText[0];
			mid = fieldsText[2];
			//System.out.println(title+" "+mid);
			
			//creazione del Document con i relativi campi d'interesse
			Document doc = new Document();
			
			Field titleField = new TextField("title", title, Field.Store.YES);
			titleField.setBoost(2f);
			
			Field midField = new TextField("mid", mid,Field.Store.YES);
			midField.setBoost(1.5f);
			
			doc.add(titleField);
			doc.add(midField);

			writer.addDocument(doc);
			i++;
		}

		
		
		
		
		
		

		writer.close();


		System.out.println("CONCLUSA. ");
		System.out.println("Creazione del dizionario in corso...");



		createDictionary(analyzer);
		Date end = new Date();
		System.out.println(end.getTime() - start.getTime() + " total milliseconds");
		System.out.println("CONCLUSA");

	}
	
	
	private static void createDictionary(Analyzer analyzer) throws IOException {
		Directory dictionaryDir = FSDirectory.open(new File(DictionaryPath)); 
		Directory indexDir = FSDirectory.open(new File(IndexPath));
		IndexReader reader = DirectoryReader.open(indexDir);
		
		Dictionary dictionary = new LuceneDictionary(reader, "content");
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47, analyzer);
		
		//StringDistance distance = new NGramDistance(3);
		//SpellChecker spellChecker = new SpellChecker(dictionaryDir,distance);
		
		SpellChecker spellChecker = new SpellChecker(dictionaryDir);
		spellChecker.indexDictionary(dictionary, iwc, false	);
		spellChecker.close();		
	}
}
