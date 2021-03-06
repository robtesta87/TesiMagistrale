/*
 * Copyright 2011 Carnegie Mellon University
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package edu.cmu.lti.wikipedia_redirect;

import java.io.BufferedReader;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extracts wikipedia redirect information and serializes the data.
 * 
 * @author Hideki Shima
 *
 */
public class WikipediaRedirectExtractor {

	private static String titlePattern    = "    <title>";
	private static String redirectPattern = "    <redirect";
	private static String textPattern     = "      <text xml";
	private static Pattern pRedirect = Pattern.compile("#[ ]?[^ ]+[ ]?\\[\\[(.+?)\\]\\]", Pattern.CASE_INSENSITIVE);
	
//	private static final String directoryDump = "/home/roberto/Scrivania/TesiMagistrale/dump/";
	private static final String directoryDump = "/home/chris88/Documenti/componenti/dump-wiki-pages/";
	
	
	public void run() throws Exception {
		int invalidCount = 0;
		long t0 = System.nanoTime();
		long startTime = System.currentTimeMillis();
		
		 File dir = new File(directoryDump);
		  File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
			  int i = 1;
		    for (File f : directoryListing) {
		    	System.out.println("Analisi del file:"+ f.getAbsolutePath());
		    	
		    	/*
				if (!f.exists()) {
					System.err.println("ERROR: File not found at "+f.getAbsolutePath());
					return;
				}
				*/
		    	
				FileInputStream fis = new FileInputStream(f);
				Map<String,String> redirectData = new HashMap<String,String>();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(
								new BZip2CompressorInputStream(
										new BufferedInputStream(
												new FileInputStream(f)))));
				String title = null;
				String text = null;
				String line = null;
				boolean isRedirect = false;
				boolean inText = false;
				
				while ((line=br.readLine())!=null) {
					if (line.startsWith(titlePattern)) {
						title = line;
						text = null;
						isRedirect = false;  
					}
					if (line.startsWith(redirectPattern)) {
						isRedirect = true;
					}
					if (isRedirect && (line.startsWith(textPattern) || inText)) {
						Matcher m = pRedirect.matcher(line); // slow regex shouldn't be used until here.
						if (m.find()) { // make sure the current text field contains [[...]]
							text  = line;
							try {
								title = cleanupTitle(title);
								String redirectedTitle = m.group(1);
								if ( isValidAlias(title, redirectedTitle) ) {
									redirectData.put(title, redirectedTitle);
								} else {
									invalidCount++;
								}
							} catch ( StringIndexOutOfBoundsException e ) {
								System.out.println("ERROR: cannot extract redirection from title = "+title+", text = "+text);
								e.printStackTrace();
							}
						} else { // Very rare case 
							inText = true;
						}
					}
				}
				br.close();
//				isr.close();
				fis.close();
				System.out.println("---- Wikipedia redirect extraction done ----");
				long t1 = System.nanoTime();
				IOUtil.save(redirectData,i);
				System.out.println("Fine analisi del file:"+f.getName());
				System.out.println("Discarded "+invalidCount+" redirects to wikipedia meta articles.");
				System.out.println("Extracted "+redirectData.size()+" redirects.");
				System.out.println("Done file in "+((double)(t1-t0)/(double)1000000000)+" [sec]");
		    	i++;
		    }
		  } 
	
		  long endTime = System.currentTimeMillis();
		  double totalTime = (double)(endTime - startTime) / 1000 ;
		  System.out.println("ANALISI COMPLETATA IN:"+totalTime+" sec (~"+(totalTime/60)+" min)");
	}

	private String cleanupTitle( String title ) {
		int end = title.indexOf("</title>");
		return end!=-1?title.substring(titlePattern.length(), end):title;
	}

	/**
	 * Identifies if the redirection is valid.
	 * Currently, we only check if the redirection is related to
	 * a special Wikipedia page or not.
	 * 
	 * TODO: write more rules to discard more invalid redirects.
	 *  
	 * @param title source title
	 * @param redirectedTitle target title
	 * @return validity
	 */
	private boolean isValidAlias( String title, String redirectedTitle ) {
		if ( title.indexOf("Wikipedia:")!=-1 ) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws Exception {
		 
		new WikipediaRedirectExtractor().run();
//		new WikipediaRedirectExtractor().run("/home/chris88/Documenti/componenti/dump-wiki-pages/enwiki-20150702-pages-articles1.xml-p000000010p000010000.bz2");
//		new WikipediaRedirectExtractor().run("/home/chris88/Documenti/componenti/dump-wiki-pages/enwiki-latest-pages-articles1.xml");

	}
}
