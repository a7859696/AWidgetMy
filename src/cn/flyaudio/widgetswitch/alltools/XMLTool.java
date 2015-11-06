package cn.flyaudio.widgetswitch.alltools;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLTool {
	
	private static SAXParser getSAXParser() throws ParserConfigurationException, SAXException{
		
		// 1.构建一个工厂SAXParserFactory
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        // 2.构建并实例化SAXPraser对象
        return parserFactory.newSAXParser();
	}
	
	public static DefaultHandler parse(InputStream inStream,DefaultHandler handler){
		if(inStream!=null){//     DefaultHandler这是安卓中内置的用于SAX处理XML的类，
			                   //但是大多情况下我们都需要继承该类重写部分方法，才能达到处理XML数据的功能。
			try {
				SAXParser parser = getSAXParser();
				parser.parse(inStream, handler);        	//通过解析saxParser的parse()方法设定解析的文件和自己定义的事件处理器对象
				return handler;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(inStream!=null){
					try {
						inStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
}
