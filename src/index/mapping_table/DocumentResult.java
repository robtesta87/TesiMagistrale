package index.mapping_table;
import java.util.LinkedList;
import java.util.List;


public class DocumentResult {
	private String title,
				   mid;
	private List<DocumentResult> relatedDocument = new LinkedList<DocumentResult>();
	
	public DocumentResult(String title, String mid) {
		this.setTitle(title);
		this.setMid(mid);
	}
	
	public boolean hasSimilarDocs(){
		return relatedDocument.isEmpty();
	}
	
	public DocumentResult() {
		this(null, null);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public List<DocumentResult> getRelatedDocument() {
		return relatedDocument;
	}

	public void setRelatedDocument(List<DocumentResult> relatedDocument) {
		this.relatedDocument = relatedDocument;
	}
	
}