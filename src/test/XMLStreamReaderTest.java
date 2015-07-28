package test;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XMLStreamReaderTest {
	private enum Element {PAGE, TITLE, TEXT, ID}
	private XMLInputFactory factory;
	private final Map<String, Element> nameToTypeMapping = new HashMap<>();

	private StringBuilder currentText;
	private Element currentElement;
	private Page page;
	
	public XMLStreamReaderTest(){
		factory = XMLInputFactory.newFactory();
        nameToTypeMapping.put("title", Element.TITLE);
        nameToTypeMapping.put("text", Element.TEXT);
        nameToTypeMapping.put("page", Element.PAGE);
        nameToTypeMapping.put("page", Element.ID);
	}
	
	
	public void readXml(InputStream input) throws XMLStreamException {

        // get an XML reader instance.
        XMLStreamReader xmlReader = factory.createXMLStreamReader(input);

        // before calling next() we can find out key things about the
        // document, because we would now be in XMLEvent.START_DOCUMENT
        // state.
        assert(xmlReader.getEventType() == XMLEvent.START_DOCUMENT);

        // iterate by calling hasNext in a loop until there are no more
        // elements left to process.
        while(xmlReader.hasNext()) {

            // get the next event and process it.
            int eventType = xmlReader.next();
            switch(eventType) {
                case XMLEvent.CDATA:
                case XMLEvent.SPACE:
                case XMLEvent.CHARACTERS:
                    processText(xmlReader.getText());
                    break;
                case XMLEvent.END_ELEMENT:
                    ended(xmlReader.getLocalName());
                    break;
                case XMLEvent.START_ELEMENT:
                    startElement(xmlReader.getLocalName());
                    int attributes = xmlReader.getAttributeCount();
                    for(int i=0;i<attributes;++i) {
                        attribute(xmlReader.getAttributeLocalName(i),
                                xmlReader.getAttributeValue(i));
                    }
                break;
            }
        }
    }
	
	
	private void startElement(String localName) {
        currentElement = nameToTypeMapping.get(localName);
        currentText = new StringBuilder(256);
        if(currentElement == Element.PAGE) {
            page = new Page();
        }
    }
	
	private void processText(String text) {
        if(currentElement != null && currentText != null) {
            currentText.append(text);
        }
    }
	
	 private void attribute(String localName, String value) {
	        if(currentElement == Element.PAGE && localName.equals("id")) {
	            page.setId(Integer.valueOf(value));
	        }
	    }
	
	private void ended(String localName) {
        // find the element type, and see if we can process it.
        currentElement = nameToTypeMapping.get(localName);
        if(currentElement != null) {

            // We can process the element, so perform the right function.
            // In a real world example, the "currentElement" type may be
            // more complex and have functionality to perform the action.
            switch (currentElement) {
                case TEXT:
                    page.setText(currentText.toString());
                    break;
                case TITLE:
                    page.setTitle(currentText.toString());
                    break;
                
                //case ANIMAL:
                 //   renderAnimal();
            }
            currentElement = null;
            currentText = null;
        }
    }

	 public static void main(String[] args) throws XMLStreamException {
	        InputStream xmlStream = XMLStreamReaderTest.class.getResourceAsStream("/home/roberto/Scrivania/TesiMagistrale/prova.xml");
	        XMLStreamReaderTest reader = new XMLStreamReaderTest();
	        reader.readXml(xmlStream);
	    }
	
}
