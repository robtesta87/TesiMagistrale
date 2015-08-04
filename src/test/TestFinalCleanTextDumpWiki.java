package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import info.bliki.wiki.filter.PlainTextConverter;
import info.bliki.wiki.model.WikiModel;

public class TestFinalCleanTextDumpWiki {


	public static final String pathFileInputFile = "/home/roberto/Scrivania/Alberta.txt";

	public static final String TEST = "This is a [[Hello World]]\"\"1\"\" '''example''' [[Silvio Berlusconi|Berlusca]] dagajshgf {{my template}}";

    public static void main(String[] args) {
    	
    	
        WikiModel wikiModel = new WikiModel("http://www.mywiki.com/wiki/${image}", "http://www.mywiki.com/wiki/${title}");
        String plainStr = wikiModel.render(new PlainTextConverter(), TEST);
        System.out.print(plainStr);
    	
    	//cleanedDumpArticle(pathFileInputFile);
    }
    
    
    public static void cleanedDumpArticle(String pathInputFile){
    	
    	final String pathFileOutputFile = "/home/roberto/Scrivania/Alberta_cleaned.txt";

    	WikiModel wikiModel = new WikiModel("http://www.mywiki.com/wiki/${image}", "http://www.mywiki.com/wiki/${title}");

    	String line = "";
    	try {
    		BufferedReader br = new BufferedReader(new FileReader(pathInputFile));
    		BufferedWriter bw = new BufferedWriter(new FileWriter(pathFileOutputFile));
			while((line = br.readLine())!=null){
				String plainStr = wikiModel.render(new PlainTextConverter(), line);
				System.out.println(plainStr);
				
				bw.append(plainStr);
				bw.write(plainStr);
			}
			br.close();
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
}
