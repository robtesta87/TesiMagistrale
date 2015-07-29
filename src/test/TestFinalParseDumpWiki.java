package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.xml.sax.SAXException;

import info.bliki.wiki.dump.IArticleFilter;
import info.bliki.wiki.dump.Siteinfo;
import info.bliki.wiki.dump.WikiArticle;
import info.bliki.wiki.dump.WikiXMLParser;

public class TestFinalParseDumpWiki {
	static  class DemoArticleFilter implements IArticleFilter {

		public void process(WikiArticle page, Siteinfo siteinfo) {
			
			
            System.out.println("----------------------------------------");
            System.out.println(page.getTitle());
            
            System.out.println("----------------------------------------");
            System.out.println(page.getText());
           
            File file = new File("output/"+page.getTitle()+".txt");
        	try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write(page.getText());
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
		public static void main(String[] args) throws IOException {
			Date start = new Date();
			
			String bz2Filename = "/home/chris88/Documenti/componenti/dump-wiki-pages/enwiki-latest-pages-articles1.xml-p000000010p000010000.bz2";
			//String bz2Filename = "/media/roberto/Elements/TesiMagistrale/componenti/enwiki-latest-pages-articles.xml.bz2";

			File filename = new File(bz2Filename);
			try {
				IArticleFilter handler = new DemoArticleFilter();
				WikiXMLParser wxp = new WikiXMLParser(filename, handler);
				wxp.parse();
				
			} catch (Exception e) {
				e.printStackTrace();
			}	

			Date end = new Date();
			System.out.println("Tempo di esecuzione in ms: "+(end.getTime()-start.getTime()));
			
		}
		
	}
}
