package persistence.dao;

import java.util.List;

import bean.EntryKB;

public interface EntryKBDAO {
	public List<EntryKB> getRelationshipsBetween(String mid1,String mid2);

}
