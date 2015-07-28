package test;

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
           
    }
		public static void main(String[] args) throws IOException {
			Date start = new Date();
			// Example:
			// String bz2Filename = "c:\\temp\\<the dump file name>.xml.bz2";
			String bz2Filename = "/home/roberto/Scrivania/TesiMagistrale/enwiki-latest-pages-articles1.xml-p000000010p000010000.bz2";
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
