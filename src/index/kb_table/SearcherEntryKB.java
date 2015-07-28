package index.kb_table;

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
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import bean.EntryKB;

public class SearcherEntryKB {

	static final String IndexPath = "util/index_KB/";

	private static final float SCORE_THRESHOLD = 0.5f;
	private static final String Field = "mid1";

	private static Analyzer analyzer;
	private static IndexReader reader;
	private static IndexSearcher searcher;
	private static QueryParser parser;



	public static void main(String[] args) {
		
		SearcherEntryKB searcher;
		try {
			searcher = new SearcherEntryKB();
			searcher.executeQuery("m.0k_267p","m.010012l");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SearcherEntryKB() throws IOException {
		reader = DirectoryReader.open(FSDirectory.open(new File(IndexPath)));
		searcher = new IndexSearcher(reader);
		analyzer = new KeywordAnalyzer();
	}


	public List<EntryKB> executeQuery(String mid1,String mid2) throws IOException, UnsupportedEncodingException {
		int hitsPerPage = 10;

		reader = DirectoryReader.open(FSDirectory.open(new File(IndexPath)));
		searcher = new IndexSearcher(reader);
		analyzer = new KeywordAnalyzer();
		parser = new QueryParser(Version.LUCENE_47, Field, analyzer);

		Query query1 = new TermQuery(new Term("mid1", mid1));
		Query query2 = new TermQuery(new Term("mid2",mid2));
		
		BooleanQuery bq = new BooleanQuery();
		bq.add(query1, Occur.MUST);
		bq.add(query2, Occur.MUST);

	
		List<EntryKB> resultEntries = new ArrayList<EntryKB>();

		TopDocs results = searcher.search(bq, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;

		
		if(hits.length==0){
			//inverto i due parametri sulle colonne di riferimento
			query1 = new TermQuery(new Term("mid1",mid2));
			query2 = new TermQuery(new Term("mid2",mid1));
			bq = new BooleanQuery();
			bq.add(query1, Occur.MUST);
			bq.add(query2, Occur.MUST);
			
			results = searcher.search(bq, 5 * hitsPerPage);
			hits = results.scoreDocs;
		}
		
			
		for(int i=0;i<hits.length;++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			EntryKB entryKB = new EntryKB();
			entryKB.setMid1(d.get("mid1"));
			entryKB.setMid2(d.get("mid2"));
			entryKB.setPredicate(d.get("predicate"));
			entryKB.setTitle1(d.get("title1"));
			entryKB.setTitle2(d.get("title2"));
			entryKB.setTypes1(d.get("types1"));
			entryKB.setTypes2(d.get("types2"));
			
			resultEntries.add(entryKB);

		}

		return resultEntries;
	}
}


