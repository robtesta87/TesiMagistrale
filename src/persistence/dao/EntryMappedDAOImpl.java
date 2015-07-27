package persistence.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bean.EntryMappedBean;
import index.mapping_table.SearcherEntryMapped;

public class EntryMappedDAOImpl implements EntryMappedDAO {


	private SearcherEntryMapped searcher;


	public EntryMappedDAOImpl() {
		try {
			searcher = new SearcherEntryMapped();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public EntryMappedBean getMidFromWikID(String wikid) {
		EntryMappedBean mappingResult = null;

		try {
			List<EntryMappedBean>listOfresults = searcher.searchRecordFor(wikid);
			for(EntryMappedBean mb: listOfresults){
				System.out.println(mb.toString());
			}
			if(listOfresults.size()>0)
				mappingResult = listOfresults.get(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mappingResult;
	}


	public static void main(String[] args) {
		String wikid = "Silvio_Berlusconi";
//		String wikid = "dlskfaòld";
		EntryMappedDAO dao = new EntryMappedDAOImpl();

		EntryMappedBean mappingBean = dao.getMidFromWikID(wikid);
		if(mappingBean!=null){
			System.out.println("Ricerca per :"+wikid);
			System.out.println("Risultato: "+mappingBean.toString());
		}
		else{
			System.out.println("Nessun risultato per "+wikid+"!");
		}
	}
}
