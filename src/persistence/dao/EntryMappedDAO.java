package persistence.dao;

import java.util.List;

import bean.EntryMappedBean;

public interface EntryMappedDAO {
	public EntryMappedBean getMidFromWikID(String wikid);

}
