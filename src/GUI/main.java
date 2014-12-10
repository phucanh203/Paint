package GUI;

import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import DataDAO.Application;
import DataDAO.Category;
import DataDAO.DataDao;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getAllGameCategory();
	}

	public static void getCategory() {
		Document doc;

		String url = "http://www.mobogenie.com/games/categories-0-downloads_1";
		try {
			doc = Jsoup.connect(url).get();
			Elements links = doc.select("div");
			for (Element link : links) {
				if (link.attr("class").equals("w-popular-keyword")) {
					// System.out.println(link.html());
					Elements cates = link.getElementsByTag("a");
					for (Element cate : cates) {
						if (!cate.text().equals("ALL")) {
							System.out.println("Category Name: " + cate.text());
							System.out.println("Link: http://www.mobogenie.com"
									+ cate.attr("href"));
							Category cateElement = new Category();
							cateElement.setCategoryName(cate.text());
							cateElement.setLink("http://www.mobogenie.com"
									+ cate.attr("href"));
							DataDao.AddNewCategory(cateElement);
						}
					}

					System.out.println("-----------------------");
				}
			}
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	public static void getAllApps() {
		Document doc;
		Document detail;
		String url = "http://www.mobogenie.com/apps/categories-0-downloads_1";
		try {
			doc = Jsoup.connect(url).timeout(1000).get();
			Elements links = doc.select("a");
			for (Element link : links) {
				Application app = new Application();
				if (link.attr("class").toString().equals("name te")) {
					app.setName(link.attr("title"));
					System.out.println("\nTitle: " + app.getName());
					String linkDetail = "http://www.mobogenie.com"
							+ link.attr("href");
					System.out.println("\nLink: " + linkDetail);
					detail = Jsoup.connect(linkDetail).get();
					Elements texts = detail.select("div");
					for (Element text : texts) {
						if (text.attr("class").toString().equals("info")) {
							app.setDescription(text.text().toString());
							System.out.println("\nDescription:"
									+ app.getDescription());
						}
						if (text.attr("class").toString().equals("details c-6")) {
							// app.setDescription(text.text().toString());
							String detailApp = text.text().toString();
							String[] detailArr = detailApp.split(" ");
							app.setVersion(detailArr[1].substring(7));
							System.out.println("\nDetail:" + app.getVersion());
						}

					}
					DataDao.AddNewApp(app);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getAllGameCategory() {
		String url = "";
		ArrayList<Category> cates = DataDao.getAllCategory();
		for (int i = 0; i < cates.size(); i++) {
			url = cates.get(i).getLink();
			for (int j = 1; j <= 10; j++) {
				String urlTemp = url.substring(0, url.length() - 1) + j;
				// System.out.println(urlTemp);
				getListAppByUrl(urlTemp, cates.get(i).getCategoryID());
			}
		}
		System.out.println("Finish!");
	}

	public static void getListAppByUrl(String url, String cateID) {
		Document doc;
		Document detail;
		try {
			doc = Jsoup.connect(url).timeout(10000).get();
			Elements links = doc.select("a");
			for (Element link : links) {
				Application app = new Application();
				app.setCategoryID(cateID);
				if (link.attr("class").toString().equals("name te")) {

					// Get Title
					app.setName(link.attr("title"));
					System.out.println("\nTitle: " + app.getName());

					// Get LinkDetail
					String linkDetail = "http://www.mobogenie.com"
							+ link.attr("href");
					System.out.println("\nLink: " + linkDetail);
					app.setLinkDetail(linkDetail);
					app.setNameIDByUrl(linkDetail);
					// Get Game Detail by linkdetail
					try {
						detail = Jsoup.connect(linkDetail).timeout(10000).get();
						Elements texts = detail.select("div");
						for (Element text : texts) {
							if (text.attr("class").toString().equals("info")) {
								app.setDescription(text.text().toString());
								System.out.println("\nDescription:"
										+ app.getDescription());
							}

							if (text.attr("class").toString()
									.equals("details c-6")) {
								app.setAppDetailByElement(text);

							}
						}
						Elements files = detail.getElementsByAttributeValue(
								"class", "g-btn g-btn1 p-download");
						for (Element file : files) {
							app.setLinkApk(file.attr("genie-url"));
							System.out.println("Link Apk: "
									+ file.attr("genie-url"));
						}
						DataDao.AddNewApp(app);
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
					
					System.out
							.println("--------------------------------------------");
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
