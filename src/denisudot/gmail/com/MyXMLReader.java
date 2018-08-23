package denisudot.gmail.com;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;

public class MyXMLReader {
	
	private List<InputLog> inputLogList = new ArrayList<InputLog>();
	
	public static void main(String[] args) {
		new MyXMLReader().go();
	}

	private void go() {
		try{ 
		     File userList = new File("src/file1.xml");
		     DocumentBuilderFactory usersActivityFactory = DocumentBuilderFactory.newInstance();
		     DocumentBuilder usersActivityBuilder = usersActivityFactory.newDocumentBuilder();
		     Document inDoc = usersActivityBuilder.parse(userList);
		     
		     inDoc.getDocumentElement().normalize();
		     NodeList logList = inDoc.getElementsByTagName("log");
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
		           InputLog inputLog = new InputLog();
		           inputLog.setTimeStamp(Long.parseLong(timeStamp));
		           inputLog.setUserId(userId);
		           inputLog.setUrl(url);
		           inputLog.setSpentTime(Long.parseLong(seconds));
		           inputLogList.add(inputLog);
		           System.out.println();
		           }
		        }
		     }
		     catch(Exception e){
		        e.printStackTrace();
		     }
	UserIdComparator userIdCompare = new UserIdComparator();
	Collections.sort(inputLogList, userIdCompare);
	System.out.println(inputLogList);
	
	
	//save new xml
	try {
		File userList = new File("src/awg_file1.xml");
        DocumentBuilderFactory dbFactory =  DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document docOut = dBuilder.newDocument();
        
        // root element
        Element rootElement = docOut.createElement("output");
        docOut.appendChild(rootElement);

        // logday element
        Element logDay = docOut.createElement("logday");
        rootElement.appendChild(logDay);

        // setting attribute to element
        Element day = docOut.createElement("day");
        logDay.appendChild(day);
        day.appendChild(docOut.createTextNode(inputLogList.get(0).getData()));
 
        Element users = docOut.createElement("users");
        day.appendChild(users);
        
        for(int i = 0; i < inputLogList.size(); i++) {
        	Element user = docOut.createElement("user");
        	users.appendChild(user);
        	Element id = docOut.createElement("id");
        	Element url = docOut.createElement("url");
        	Element average = docOut.createElement("average");
        	user.appendChild(id);
        	user.appendChild(url);
        	user.appendChild(average);
        	id.appendChild(docOut.createTextNode(inputLogList.get(i).getUserId()));
        	url.appendChild(docOut.createTextNode(inputLogList.get(i).getUrl()));
        	average.appendChild(docOut.createTextNode(inputLogList.get(i).getAverage()));
        }

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(docOut);
        StreamResult result = new StreamResult(userList);
        transformer.transform(source, result);
        
        // Output to console for testing
        StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(source, consoleResult);
     } catch (Exception e) {
        e.printStackTrace();
     }
	}
}
