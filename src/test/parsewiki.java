package test;

import edu.jhu.nlp.wikipedia.PageCallbackHandler;
import edu.jhu.nlp.wikipedia.WikiPage;
import edu.jhu.nlp.wikipedia.WikiXMLParser;
import edu.jhu.nlp.wikipedia.WikiXMLParserFactory;



public class parsewiki {
	public static void main(String[] args) {
		 /*WikiXMLParser wxp = WikiXMLParserFactory.getDOMParser("/media/roberto/Elements/Tesi Magistrale/componenti/enwiki-latest-pages-articles.xml.bz2");
	        try {
	                wxp.parse();
	                WikiPageIterator it = wxp.getIterator();
	                while(it.hasMorePages()) {
	                        WikiPage page = it.nextPage();
	                        System.out.println(page.getTitle());
	                }

	        }catch(Exception e) {
	                e.printStackTrace();
	        }*/
	        
	        WikiXMLParser wxsp = WikiXMLParserFactory.getSAXParser(args[0]);
            
	        try {
	           
	            wxsp.setPageCallback(new PageCallbackHandler() { 
	                           public void process(WikiPage page) {
	                                  System.out.println(page.getTitle());
	                           }
	            });
	                
	           wxsp.parse();
	        }catch(Exception e) {
	                e.printStackTrace();
	        }
	}
}
