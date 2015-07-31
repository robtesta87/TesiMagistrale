package index.redirect;

import index.kb_table.SearcherEntryKB;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import bean.EntryKB;
import bean.EntryMappedBean;
import bean.EntryRedirect;

public class SearcherRedirect {
	static final String IndexPath = "util/index_redirect/";

	private static final float SCORE_THRESHOLD = 0.5f;
	private static final String Field = "redirect";

	private static Analyzer analyzer;
	private static IndexReader reader;
	private static IndexSearcher searcher;
	private static QueryParser parser;



	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		//executeQuery();
		List<EntryRedirect> mappingResults = searchRecordFor("The Endless Dark Nothingness");
		for (EntryRedirect er : mappingResults){
			System.out.println(er.toString());
		}
		
	}

	public SearcherRedirect() throws IOException {
		reader = DirectoryReader.open(FSDirectory.open(new File(IndexPath)));
		searcher = new IndexSearcher(reader);
		analyzer = new KeywordAnalyzer();
	}

	private static void executeQuery() throws IOException, UnsupportedEncodingException {
		String queries = null;
		String queryString = null;
		int hitsPerPage = 10;

		reader = DirectoryReader.open(FSDirectory.open(new File(IndexPath)));
		searcher = new IndexSearcher(reader);
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

			line = line.trim();
			if (line.length() == 0) {
				break;
			}

			try {
				Query query = parser.parse(line);
				String readableQuery = query.toString(Field);
				System.out.println("Searching...");

				TopDocs results = searcher.search(query, 5 * hitsPerPage);
				ScoreDoc[] hits = results.scoreDocs;

				/* print results */
				//System.out.println("Found " + hits.length + " hits.");
				for(int i=0;i<hits.length;++i) {
					int docId = hits[i].doc;
					Document d = searcher.doc(docId);
					System.out.println("redirect: " + d.get("redirect") + " wikID: " + d.get("wikID"));
				}

				if (hits.length == 0)
					System.out.println("No results found for:\t"
							+ readableQuery);
				else
					System.out.println(hits.length + " results found for:\t"
							+ readableQuery);
				
			} catch (ParseException e) {
				System.err.println("Incorrect Query");
			}
		}
	}
	
	
	public static List<EntryRedirect> searchRecordFor(String redirect) throws IOException, UnsupportedEncodingException {
		redirect=redirect.replaceAll(" ", "_");
		List<EntryRedirect> mappingResults = new ArrayList<EntryRedirect>();
		int maxHits = 10;

		reader = DirectoryReader.open(FSDirectory.open(new File(IndexPath)));
		searcher = new IndexSearcher(reader);
		analyzer = new KeywordAnalyzer();
		parser = new QueryParser(Version.LUCENE_47, Field, analyzer);


		try {
			Query query = parser.parse(redirect);
			System.out.println("Searching...");

			TopDocs results = searcher.search(query, 5 * maxHits);
			ScoreDoc[] hits = results.scoreDocs;

			for(int i=0;i<hits.length;++i) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				mappingResults.add(new EntryRedirect(d.get("redirect"),  d.get("wikID")));
			}

		} catch (ParseException e) {
			System.err.println("Incorrect Query");
		}
		return mappingResults;
	}

}
