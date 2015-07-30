package test;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class OrderedMap {

	public static void main(String[] args) {
		Comparator<String> myComparator = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o2.length() - o1.length();

			}
		};


		Map<String,String> map = new TreeMap<String,String>(myComparator);
		map.put("universal affirmative", "Categorical proposition");
		map.put("English orthography", "English orthography");
		map.put("near-open front unrounded vowel", "near-open");
		map.put("English", "English language");


		Set<String> keySet = map.keySet();

		for(String key : keySet){
			System.out.println(key+" -> "+map.get(key));
		}




	}

}
