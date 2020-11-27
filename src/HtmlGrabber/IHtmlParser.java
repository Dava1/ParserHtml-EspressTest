package HtmlGrabber;

import java.util.List;

import org.jsoup.nodes.Element;
/**
 * provide main body from html file
 * @author davinci
 *
 */
public interface IHtmlParser {
	public Element getMainBody(String Url,String idBody) throws Exception;
}
