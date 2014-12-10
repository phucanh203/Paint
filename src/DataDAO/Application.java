package DataDAO;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Application {
	private String AppID;
	private String Name;
	private String CategoryID;
	private String Description;
	private String Version;
	private String Size;
	private Date UpdatedDate;
	private String LinkDetail;
	private String LinkApk;

	public String getAppID() {
		return AppID;
	}

	public void setAppID(String appID) {
		AppID = appID;
	}

	public Application() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public String getCategoryID() {
		return CategoryID;
	}

	public void setCategoryID(String categoryID) {
		CategoryID = categoryID;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		this.Description = description;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getSize() {
		return Size;
	}

	public void setSize(String size) {
		Size = size;
	}

	public Date getUpdatedDate() {
		return UpdatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		UpdatedDate = updatedDate;
	}

	public String getLinkDetail() {
		return LinkDetail;
	}

	public void setLinkDetail(String linkDetail) {
		LinkDetail = linkDetail;
	}

	public String getLinkApk() {
		return LinkApk;
	}

	public void setLinkApk(String linkApk) {
		LinkApk = linkApk;
	}

	public void setNameIDByUrl(String url) {
		try {
			String[] arr = url.split("-");
			String id = arr[arr.length - 1].substring(0,
					arr[arr.length - 1].length() - 5);
			setAppID(id);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public boolean checkNullData()
	{
		if(getAppID().isEmpty())
			return false;
		if(getName().isEmpty())
			return false;
		if(getCategoryID().isEmpty())
			return false;
		if(getDescription().isEmpty())
			return false;
		if(getLinkApk().isEmpty())
			return false;
		if(getLinkDetail().isEmpty())
			return false;
		if(getSize().isEmpty())
			return false;
		if(getUpdatedDate().toString().isEmpty())
			return false;
		
		return true;
	}
	
	public void setAppDetailByElement(Element elment) {
		try {
			Elements detailApps = elment.getElementsByTag("p");
			for (Element detailApp : detailApps) {
				String AttrName = detailApp.getElementsByTag("span").text();
				switch (AttrName) {
				case "Version":
					setVersion(detailApp.text().substring(7));
					System.out.println("Version: "
							+ detailApp.text().substring(7));
					break;
				case "Size":
					setSize(detailApp.text().substring(4));
					System.out
							.println("Size: " + detailApp.text().substring(4));
					break;
				case "Updated":
					
					String time = detailApp.text().substring(7);
					Date updateTime = new Date();
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

					updateTime = ft.parse(time);

					setUpdatedDate(updateTime);
					System.out.println("Updated: " + updateTime);
					break;

				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
