package HtmlGrabber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * provide map with all series of products and their configuration values
 * @author davinci
 *
 */
public class ParserHtmlSeriesOfProducts implements IHtmlParser {

	private String url; 
	private String idOfMain;
	
	
	public ParserHtmlSeriesOfProducts(String url,String idOfMain) {
		this.url = url;
		this.idOfMain = idOfMain;
	}
	
	
	public Map<String,String> getSocsProduct() throws Exception {
		return getProductsByType(url+"/socs");
	}
	
	public Map<String,String> getModulesProduct() throws Exception {
		return getProductsByType(url+"/modules");
	}
	
	public Map<String,String> getDevKitsProduct() throws Exception {
		return getProductsByType(url+"/devkits");
	}
	
	
	
	/**
	 * provide product
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private Map<String,String> getProductsByType(String url) throws Exception {
		Element main = getMainBody(url, idOfMain);
		
		Elements sheetCategoryTitles = main.getElementsByClass("sheet-category-title"); 
		Elements sheetsCategoryDescription = main.getElementsByClass("sheet-category-description"); 
		Map<String,String> products = new HashMap<String,String>();
		Map<Integer,String> names = new HashMap<Integer,String>();
		int i =0;
		for(Element el:sheetCategoryTitles){
			String name=el.text();
			names.put(i,name);
			i++;
		}
		int j=0;
		for(Element e: sheetsCategoryDescription) {
			String desc=e.text();
			if(names.containsKey(j)) {
				String name = names.get(j);
				products.put(name, desc);
			}
		j++;
		}
		return products;
		}
	
	@Override
	public Element getMainBody(String url, String idOfMain) throws Exception {
		Document doc =Jsoup.connect(url).get();
		Element main = doc.getElementById(idOfMain);
		return main;
	}


}
