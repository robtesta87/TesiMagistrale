package persistence.dao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import bean.EntryKB;
import index.kb_table.SearcherEntryKB;

public class EntryKBDAOImpl implements EntryKBDAO {
	private SearcherEntryKB searcher = null;
	
	public EntryKBDAOImpl() {
		try {
			searcher = new SearcherEntryKB();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<EntryKB> getRelationshipsBetween(String mid1,String mid2){
		List<EntryKB> results = new ArrayList<EntryKB>();
		try {
			results = searcher.executeQuery(mid1, mid2);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}

}
