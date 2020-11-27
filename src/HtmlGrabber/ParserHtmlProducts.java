package HtmlGrabber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * provide all products from site
 * @author davinci
 *
 */
public class ParserHtmlProducts implements IHtmlParser {
	private String url;
	private String idOfMain;
	
	
	public ParserHtmlProducts(String url,String idOfMain) {
	this.url = url;
	this.idOfMain = idOfMain;
	}
	
	public Map<String,List<String>> getSocsProduct() throws Exception{
		return getElements(url+"/socs");
	}

	public Map<String,List<String>> getModulesProduct() throws Exception{
		return getElements(url+"/modules");
	}
	
	public Map<String,List<String>> getDevKitsProduct() throws Exception{
		return getElements(url+"/devkits");
	}
	
	/**
	 * engine of class
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private Map<String,List<String>> getElements(String url) throws Exception {
		Element main = getMainBody(url, idOfMain);
		Elements tables = main.getElementsByTag("td");
		List<String> values = new ArrayList<String>();
		List<Element> l= new ArrayList<Element>();
		l.addAll(getNameOfProduct(url));
		for(Element e: tables) {
			String s = e.attr("class");
			if(s.equals("sheet-figure")) {
			continue;
				}
				values.addAll(parseNamesOfParts(e, "td"));
		}
		Map<String,List<String>> product = new HashMap<String, List<String>>();
		List<String> names	=new ArrayList<String>();
		int i = 0;
		for(Element e: l) {
			names.addAll(parseNameofProduct(e, "a"));
		}
		int k = sizeOfValues(url);
		int goal=0;
		for(String s: names) {
		List<String> temp = new ArrayList<String>();
			while(i < k-1) {
			temp.add(cutExces(values.get(i)));
			i++;
			};i=0;
		product.put(s, temp);	
		}
		return product;
	}


	
	@Override
	public Element getMainBody(String url, String idOfMain) throws Exception {
		Document doc = Jsoup.connect(url).get();
		Element d =doc.getElementById(idOfMain);
		return d;
	}
	/**
	 * size need for list of values
	 * @return
	 * @throws Exception
	 */
	private int sizeOfValues(String url) throws Exception{
		Set<String> s = new HashSet<>();
		Element e = getMainBody(url, idOfMain);
		Elements el =e.getElementsByTag("td");
		for(Element a:el) {
			String q = a.attr("class");
			s.add(q);	
		}
	return s.size();
	}
	
	/**
	 * help to get all name of products
	 * @return
	 * @throws Exception
	 */
	private List<Element> getNameOfProduct(String url) throws Exception {
		Element main = getMainBody(url, idOfMain);
		Elements el = main.getElementsByTag("a");
		List <Element> list = new ArrayList<Element>();
		for(Element e :el) {
			String s = e.attr("class");
			if(s.contains("chip-name") || s.contains("module-name") ||s.contains("devbd-name"))
			list.add(e);
		}
	return list;
	}
	
	
	
	public List<String> parseNameofProduct(Element e,String tag) {
		List<String> res = new ArrayList<String>();
		Elements es=e.getElementsByTag(tag);
		for (Element r:es) {
			String k0 = "<"+tag+" ";
			String k1 = "href=\""+r.attr("href")+"\"";
			String k2 ="class=\""+r.attr("class")+"\">";
			String k3 = "</"+tag+">";
			String value = r.toString().replaceAll(k0,"");
			value=value.replaceAll(k1, "");
			value=value.replaceAll(k2, "");
			res.add(value.replace(k3, ""));
		}
	return res;
	}
	/**
	 * cut excess products characteristics names
	 * @param e
	 * @param tag
	 * @return
	 */
	private List<String> parseNamesOfParts(Element e,String tag) {
		List<String> list = new ArrayList<String>(); 
		Elements es = e.getElementsByTag(tag);
		for (Element r:es) {
			String k0 = "<"+tag+" ";
			String name = r.attr("class");
			String k1 = " href=\""+r.attr("href")+"\"";
			String k2 ="class=\""+r.attr("class")+"\">";
			String k3 = "</"+tag+">";
			String value = r.toString().replace(k0+k2,"");
			list.add(name.replace("sheet-field ", "")+":"+value.replace(k3, ""));
		}
	return list;
	}
	/**
	 * Trim excess
	 * @param s
	 * @return
	 */
	private String cutExces(String s) {
	String replacement = "";
	String d=s.replaceAll("<div class=\"taxonomy-term-description\">\n", "");
	d=d.replaceAll("<p>", replacement);
	d=d.replaceAll("</p>",replacement);
	d=d.replaceAll("</div>", "");
	d=d.replaceAll("</ul>", "");
	d=d.replaceAll("<ul>", "");
	d=d.replaceAll("</li>", "");
	d=d.replaceAll("<li>", "");
	d=d.replaceAll("<a ", "");
	d=d.replaceAll("</a>", "");
	d=d.replaceAll("\n", "");
	d =d.replaceAll("<br>", replacement);
	d=d.replaceAll("<li>Header connecters</li>", "");
	return d.trim();
	}
}
