package test;
import edu.jhu.nlp.wikipedia.*;
public class WikyXMLTest {
	public static void main(String[] args) {

		WikiXMLParser wxp = WikiXMLParserFactory.getDOMParser("/home/roberto/Scrivania/TesiMagistrale/enwiki-latest-pages-articles1.xml-p000000010p000010000");
		try {
			wxp.parse();
			WikiPageIterator it = wxp.getIterator();
			int cont=0;
			while(it.hasMorePages()) {
				WikiPage page = it.nextPage();
				System.out.println(page.getTitle());
				System.out.println(page.getText());
				cont++;
			}
			System.out.println(cont);
		}catch(Exception e) {
			e.printStackTrace();
		}
		/*WikiXMLParser wxsp = WikiXMLParserFactory.getSAXParser(args[0]);
        
        try {
                  
            wxsp.setPageCallback(new PageCallbackHandler() { 
                           public void process(WikiPage page) {
                                  System.out.println(page.getTitle());
                           }
            });
                
           wxsp.parse();
        }catch(Exception e) {
                e.printStackTrace();
        }*/
		
	}
}
