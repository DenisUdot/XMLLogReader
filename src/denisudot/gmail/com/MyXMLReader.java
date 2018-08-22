package denisudot.gmail.com;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;

public class MyXMLReader {
	
	public static void main(String[] args) {
		try{ 
		     File userList = new File("src/file1.xml");
		     DocumentBuilderFactory usersActivityFactory = DocumentBuilderFactory.newInstance();
		     DocumentBuilder usersActivityBuilder = usersActivityFactory.newDocumentBuilder();
		     Document user = usersActivityBuilder.parse(userList);
		     
		     user.getDocumentElement().normalize();
		     NodeList logList = user.getElementsByTagName("log");
		     int totalLogsNumbers = logList.getLength();
		     
		     for (int i = 0; i < totalLogsNumbers; i++)
		     {
		        Node list = logList.item(i);   
		        if (list.getNodeType() == Node.ELEMENT_NODE)
		        {
		           Element information = (Element) list;
		           String timeStamp = information.getElementsByTagName("timestamp").item(0).getTextContent();
		           String userId = information.getElementsByTagName("userId").item(0).getTextContent();
		           String url = information.getElementsByTagName("url").item(0).getTextContent();
		           String seconds = information.getElementsByTagName("seconds").item(0).getTextContent();
		           System.out.println("timeStamp: " + timeStamp );
		           System.out.println("userId: " + userId);
		           System.out.println("url: " + url);
		           System.out.println("seconds: " + seconds);
		           System.out.println();
		           }
		        }
		        }
		     catch(Exception e)
		     {
		        e.printStackTrace();
		     }
	}
}
