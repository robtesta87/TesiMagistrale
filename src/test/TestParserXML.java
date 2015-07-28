package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class TestParserXML {
	
	public static void main(String[] args) {

	      try {	
	         File inputFile = new File("/home/roberto/Scrivania/TesiMagistrale/prova.xml");
	         
	         PrintWriter out=null;
	 		 out = new PrintWriter(new BufferedWriter(new FileWriter("/home/roberto/Scrivania/TesiMagistrale/output.txt", true)));
	         
	         
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         //doc.getDocumentElement().normalize();
	         NodeList nList = doc.getElementsByTagName("page");
	         System.out.println("----------------------------");
	         
	         String title = "";
	         String text = "";
	         
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            System.out.println("\nCurrent Element :" 
	               + nNode.getNodeName());
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element eElement = (Element) nNode;
	               
	               title = eElement.getElementsByTagName("title").item(0).getTextContent();
	               text = eElement.getElementsByTagName("text").item(0).getTextContent();
	               
	               out.println("---------------------");
	               out.println("title: "+title);
	               out.println("---------------------");
	               out.println(text);
	               
	            }
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
}
