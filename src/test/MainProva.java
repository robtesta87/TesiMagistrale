package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class MainProva {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		String testo ="As they rounded a bend in the path that ran beside the river, Lara recognized the silhouette of a fig tree atop a nearby hill. The weather was hot and the days were long. The fig tree was in full leaf, but not yet bearing fruit.Soon Lara spotted other landmarks—an outcropping of limestone beside the path that had a silhouette like a man’s face, a marshy spot beside the river where the waterfowl were easily startled, a tall tree that looked like a man with his arms upraised. They were drawing near to the place where there was an island in the river. The island was a good spot to make camp. They would sleep on the island tonight.";
		String paragraph = "As an anti-dogmatic philosophy, anarchism draws on many currents of thought and strategy. Anarchism does not offer a fixed body of doctrine from a single particular world view, instead fluxing and flowing as a philosophy.[19] There are many types and traditions of anarchism, not all of which are mutually exclusive.[20] Anarchist schools of thought can differ fundamentally, supporting anything from extreme individualism to complete collectivism.[10] Strains of anarchism have often been divided into the categories of social and individualist anarchism or similar dual classifications.[21][22] Anarchism is usually considered a radical left-wing ideology,[23][24] and much of anarchist economics and anarchist legal philosophy reflect anti-authoritarian interpretations of communism, collectivism, syndicalism, mutualism, or participatory economics.";
		
		String [] result = SentenceDetector.sentenceDetect(paragraph);
//		1NumberDetector.findNumber();
		for(String s : result){
			System.out.println(s);
		}
	}
}
