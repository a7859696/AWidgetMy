package cn.flyaudio.widgetswitch.alltools;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class FlyShortcutXMLParser extends DefaultHandler {

	// private List<String> list ;
	private Map<String, String> data;
	private String entity;

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		// list = new ArrayList<String>();
		data = new HashMap<String, String>();

	}

	public Map<String, String> getData() {
		return data;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		if (qName.equals("item")) {
			String b = "true";
			try {
				b = new String(attributes.getValue("show"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			entity = new String(attributes.getValue("classname"));
			data.put(entity, b);
		}

		super.startElement(uri, localName, qName, attributes);
	}

}
