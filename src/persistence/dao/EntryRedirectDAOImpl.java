package persistence.dao;



import index.redirect.SearcherRedirect;

import java.io.IOException;
import java.util.List;

import bean.EntryMappedBean;
import bean.EntryRedirect;

public class EntryRedirectDAOImpl implements EntryRedirectDAO{
	private SearcherRedirect searcher;


	public EntryRedirectDAOImpl() {
		try {
			searcher = new SearcherRedirect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public EntryRedirect getwikIDFromRedirect(String redirect) {
		EntryRedirect mappingResult = null;

		try {
			List<EntryRedirect>listOfresults = searcher.searchRecordFor(redirect);
			for(EntryRedirect mb: listOfresults){
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
		String redirect = "AC Milan";
//		String wikid = "dlskfaòld";
		EntryRedirectDAO dao = new EntryRedirectDAOImpl();

		EntryRedirect mappingBean = dao.getwikIDFromRedirect(redirect);
		if(mappingBean!=null){
			System.out.println("Ricerca per :"+redirect);
			System.out.println("Risultato: "+mappingBean.getWikid());
		}
		else{
			System.out.println("Nessun risultato per "+redirect+"!");
		}
	}
}
