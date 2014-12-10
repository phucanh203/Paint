package DataDAO;

public class Category {
	private String CategoryID;
	private String CategoryName;
	private String Link;
	
	public String getCategoryID() {
		return CategoryID;
	}
	public void setCategoryID(String categoryID) {
		CategoryID = categoryID;
	}
	
	public String getCategoryName() {
		return CategoryName;
	}
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}
	
	public String getLink() {
		return Link;
	}
	public void setLink(String link) {
		Link = link;
	}
}
