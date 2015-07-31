package index.mapping_table;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TextFragment;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import bean.EntryMappedBean;
import bean.EntryRedirect;

public class SearcherEntryMapped {

	static final String IndexPath = "util/index_lucene/";

	private static final float SCORE_THRESHOLD = 0.5f;
	private static final String Field = "title";

	private static StandardAnalyzer analyzer;
	private static IndexReader reader;
	private static IndexSearcher searcher;
	private static QueryParser parser;

	public SearcherEntryMapped() throws IOException {
		reader = DirectoryReader.open(FSDirectory.open(new File(IndexPath)));
		searcher = new IndexSearcher(reader);
		analyzer = new StandardAnalyzer(Version.LUCENE_47);
	}

	public static void main(String[] args) throws Exception {
		executeQuery();
	}
	/*
	private static List<DocumentResult> createDocumentsResulted(ScoreDoc[] hits, Query query) throws IOException, InvalidTokenOffsetsException {
		SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter();
		Highlighter highlighter = new Highlighter(htmlFormatter,
				new QueryScorer(query));
		//DocumentResult[] docResArray = new DocumentResult[hits.length];
		List<DocumentResult> docResArray = new ArrayList<DocumentResult>();

		for (int i = 0; i < hits.length; i++) {
			DocumentResult docRes = new DocumentResult();
			int docId = hits[i].doc;
			Document doc = searcher.doc(docId);
			//docRes.setRelatedDocument(moreLikeThis(3, docId));
			String url = doc.get("url");
			if (url != null) {
				docRes.setUrl(url);
				String title = doc.get("title");
				if (title != null) {
					docRes.setTitle(title);
				}

				StringBuilder builder = new StringBuilder();
				for (TextFragment frag : getHighlights(docId, highlighter))
					if ((frag != null) && (frag.getScore() > 0)) {
						String snippet = frag.toString().replaceAll("\\s+", " ");
						builder.append(snippet).append("...");
					}
				if (builder.length() != 0)
					docRes.setHighlights(builder.toString());

			}

			docResArray.add(docRes);
		}

		return docResArray;

	}
	 */

	private static void executeQuery() throws IOException, UnsupportedEncodingException {
		String queries = null;
		String queryString = null;
		int hitsPerPage = 10;

		reader = DirectoryReader.open(FSDirectory.open(new File(IndexPath)));
		searcher = new IndexSearcher(reader);
		analyzer = new StandardAnalyzer(Version.LUCENE_47);
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
					System.out.println("title: " + d.get("title") + " mid: " + d.get("mid"));
				}

				if (hits.length == 0)
					System.out.println("No results found for:\t"
							+ readableQuery);
				else
					System.out.println(hits.length + " results found for:\t"
							+ readableQuery);
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


	public List<EntryMappedBean> searchRecordFor(String wikid) throws IOException, UnsupportedEncodingException {

		List<EntryMappedBean> mappingResults = new ArrayList<EntryMappedBean>();
		int maxHits = 10;

		reader = DirectoryReader.open(FSDirectory.open(new File(IndexPath)));
		searcher = new IndexSearcher(reader);
		analyzer = new StandardAnalyzer(Version.LUCENE_47);
		parser = new QueryParser(Version.LUCENE_47, Field, analyzer);


		try {
			Query query = parser.parse(wikid);
			System.out.println("Searching...");

			TopDocs results = searcher.search(query, 5 * maxHits);
			ScoreDoc[] hits = results.scoreDocs;

			for(int i=0;i<hits.length;++i) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				mappingResults.add(new EntryMappedBean(d.get("title"),  d.get("mid")));
			}

		} catch (ParseException e) {
			System.err.println("Incorrect Query");
		}
		return mappingResults;
	}



}