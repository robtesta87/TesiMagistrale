package index.kb_table;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearcherEntryKB {

	static final String IndexPath = "util/index_KB/";

	private static final float SCORE_THRESHOLD = 0.5f;
	private static final String Field = "mid1";

	private static Analyzer analyzer;
	private static IndexReader reader;
	private static IndexSearcher searcher;
	private static QueryParser parser;

	public SearcherEntryKB() throws IOException {
		reader = DirectoryReader.open(FSDirectory.open(new File(IndexPath)));
		searcher = new IndexSearcher(reader);
//		analyzer = new StandardAnalyzer(Version.LUCENE_47);
		analyzer = new KeywordAnalyzer();
	}
	
	
	
	public static void main(String[] args) {
		try {
			executeQuery();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private static void executeQuery() throws IOException, UnsupportedEncodingException {
		String queries = null;
		String queryString = null;
		int hitsPerPage = 10;

		reader = DirectoryReader.open(FSDirectory.open(new File(IndexPath)));
		searcher = new IndexSearcher(reader);
//		analyzer = new StandardAnalyzer(Version.LUCENE_47);
		analyzer = new KeywordAnalyzer();
		parser = new QueryParser(Version.LUCENE_47, Field, analyzer);

		BufferedReader in = null;
		in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

		while (true) {
			if (queries == null && queryString == null) { // prompt the user
				System.out.println("Enter query: ");
			}

			String line = queryString != null ? queryString : in.readLine();

			if (line == null || line.length() == -1) {
				break;
			}

			
			
			/*
			String mid1 = null;
			String mid2 = null;
			String[] lineSplitted = line.split(" ");
			mid1 = lineSplitted[0];
			mid2 = lineSplitted[1];
			
			
			
			if (lineSplitted.length != 2) {
				break;
			}
			else{
				mid1 = lineSplitted[0];
				mid2 = lineSplitted[1];
			}
			*/
			
			
			
			

			try {
//				String coupleOfMid = "("+mid1+","+mid2+")";
				Query query = parser.parse(line);
//				String readableQuery = query.toString(Field);
				System.out.println("Searching...");

				TopDocs results = searcher.search(query, 5 * hitsPerPage);
				ScoreDoc[] hits = results.scoreDocs;

				/* print results */
				//System.out.println("Found " + hits.length + " hits.");
				for(int i=0;i<hits.length;++i) {
					int docId = hits[i].doc;
					Document d = searcher.doc(docId);
					System.out.println("mid1: " + d.get("mid1") +"mid2: "+d.get("mid2")+ " predicate: " + d.get("predicate"));
				}

				if (hits.length == 0)
					System.out.println("No results found for:\t"
							+ line);
				else
					System.out.println(hits.length + " results found for:\t"
							+ line);
				/*
				List<DocumentResult> dr = createDocumentsResulted(hits, query);
				for(DocumentResult d : dr){
					System.out.println("title"+" "+d.getTitle());
					System.out.println("mid"+" "+ d.getMid());
					System.out.println("-------------");
				}
				// resultsListerAndNavigator(in, hits, hitsPerPage,
				// highlighter);
				 */
			} catch (ParseException e) {
				System.err.println("Incorrect Query");
			}
		}
	}

	
	
	
	
	
	
}
