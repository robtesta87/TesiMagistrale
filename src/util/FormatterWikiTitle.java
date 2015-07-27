package util;

public class FormatterWikiTitle {

	public static String formattingThisTitle(String title){
		String titleFormatted = "";
		String[] titleSplitted = title.split(" ");


		if(titleSplitted.length>0){
			int i = 0;
			while(i<titleSplitted.length-1)
				titleFormatted += titleSplitted[i] +"_";
			titleFormatted += titleSplitted[i];
		}
		
		return titleFormatted;
	}
}
