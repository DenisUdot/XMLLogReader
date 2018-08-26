package denisudot.gmail.com;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;

public class MyXMLReader implements Runnable {
	
	private List<List<InputLog>> daysList;
	private File fileName;
	private String writingFolder;
	
		public MyXMLReader(File filename, String writingFolder) {
		this.fileName = filename;
		this.writingFolder = writingFolder;
	}
		
	public void run() {
//		name for testing threads
//		Thread.currentThread().setName(fileName.getName());
//	    System.out.println("Thread work with file: "+Thread.currentThread().getName());
		daysList = new ArrayList<List<InputLog>>();
		daysList.add(new ArrayList<InputLog>());
	    readFile();
	    writeFile();
	    
	}
	
	private void readFile() {
		try{ 
		     DocumentBuilderFactory usersActivityFactory = DocumentBuilderFactory.newInstance();
		     DocumentBuilder usersActivityBuilder = usersActivityFactory.newDocumentBuilder();
		     Document inDoc = usersActivityBuilder.parse(fileName);	     
		     inDoc.getDocumentElement().normalize();
		     NodeList log = inDoc.getElementsByTagName("log");
		     int totalLogsNumbers = log.getLength();
		     
		     for (int i = 0; i < totalLogsNumbers; i++){
		        Node list = log.item(i);   
		        
		        if (list.getNodeType() == Node.ELEMENT_NODE){
		           Element information = (Element) list;
		           long timeStamp = 1000*Long.parseLong(information.getElementsByTagName("timestamp").item(0).getTextContent());
		           String userId = information.getElementsByTagName("userId").item(0).getTextContent();
		           String url = information.getElementsByTagName("url").item(0).getTextContent();
		           long seconds = Long.parseLong(information.getElementsByTagName("seconds").item(0).getTextContent());
		           long j = 86400-((timeStamp/1000)%86400);
		           
		           if(j < seconds) {
		        	   if(daysList.size() <= 1) {
		        		   daysList.add(new ArrayList<InputLog>());
		        	   }
		        	   addlogToList(timeStamp, userId, url, j, daysList.get(0));
		        	   addlogToList(timeStamp+(seconds*1000), userId, url, seconds-j, daysList.get(1));
		           }
		           else {
		        	   addlogToList(timeStamp, userId, url, seconds, daysList.get(0));
		           }
			       
		           }
		        }
		     }
		catch(Exception e){
			e.printStackTrace();
		}
		UserIdComparator userIdCompare = new UserIdComparator();

		for(int i = 0; i < daysList.size(); i++) {
			Collections.sort(daysList.get(i), userIdCompare);
//			System.out.println(daysList.get(i));
		}
	}
	
	public void addlogToList(long timeStamp, String userId, String url, long seconds, List<InputLog> list) {
		InputLog inputLog = new InputLog();
        inputLog.setTimeStamp(timeStamp);
        inputLog.setUserId(userId);
        inputLog.setUrl(url);
        inputLog.setSpentTime(seconds);
        list.add(inputLog);
	}
	
	private void writeFile() {
		if(!new File(writingFolder).exists()){
			new File(writingFolder).mkdir();
		}
		try {
			File userList = new File(writingFolder+"/awg_"+fileName.getName());
	        DocumentBuilderFactory dbFactory =  DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document docOut = dBuilder.newDocument();
	        docOut.setXmlStandalone(true);
	        
	        Element output = docOut.createElement("output");
	        docOut.appendChild(output);
	  
	        for(int i = 0; i < daysList.size(); i++) {
		        Element logday = docOut.createElement("logday");
		        output.appendChild(logday);
	
		        Element day = docOut.createElement("day");
		        logday.appendChild(day);
		        day.appendChild(docOut.createTextNode(daysList.get(i).get(0).getDate()));
		        Element users = docOut.createElement("users");
		        logday.appendChild(users);
		        
		        for(int j = 0; j < daysList.get(i).size(); j++) {
		        	Element user = docOut.createElement("user");
		        	users.appendChild(user);
		        	Element id = docOut.createElement("id");
		        	Element url = docOut.createElement("url");
		        	Element average = docOut.createElement("average");
		        	user.appendChild(id);
		        	user.appendChild(url);
		        	user.appendChild(average);
		        	id.appendChild(docOut.createTextNode(daysList.get(i).get(j).getUserId()));
		        	url.appendChild(docOut.createTextNode(daysList.get(i).get(j).getUrl()));
		        	average.appendChild(docOut.createTextNode(daysList.get(i).get(j).getAverage()));
		        }
	        }	
	      
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();		        Transformer transformer = transformerFactory.newTransformer();
		    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
		
		    DOMSource source = new DOMSource(docOut);
		    StreamResult result = new StreamResult(userList);
		    transformer.transform(source, result);
		        
// 			Output to console for testing
//		    StreamResult consoleResult = new StreamResult(System.out);
//		    transformer.transform(source, consoleResult);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		System.out.println("File "+fileName+" convertion complite");
		
	}
}
