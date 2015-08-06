package util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class ProvaComparator {
	public static void main(String[] args) {
		Comparator<String> comp = new Comparator<String>() {
			
			@Override
			public int compare(String o1, String o2) {
				return o2.length() - o1.length();
			}
		};
		
		Map<String,String> map = new TreeMap<String,String>(comp);
		
		
		map.put("Dracula_Bram_Stoker", "Dracula_Bram_Stoker");
		map.put("Dracula", "Dracula");
		map.put("Dracula Vampire","Dracula_Vampire");
		map.put("Pippo","Goofy");
		map.put("\"Million_Dollar_Man\"_Ted_DiBiase","Ted_DiBiase");
		//fjkdsjflkjsdlfks
		
			
		for(String key : map.keySet()){
			System.out.println(key+" --> "+map.get(key));
		}
		
	}
	

}
