package processing.app.data;


import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import processing.app.Utils;

public class XMLHandler {

	public Document ReadXML(String file) {
		try {
			File fXmlFile = new File(file);
			if(!fXmlFile.exists()) {
				Utils.LogError("Couldn't laod the file " + file);
				return null;
			}
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			return doc;


		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	public Document saveXML(String masterNode, List<Data> data) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();


			Element rootElement = doc.createElement(masterNode);
			doc.appendChild(rootElement);

			for(int i = 0; i < data.size(); i++) {
				Element tmp = doc.createElement(data.get(i).getNode());
				tmp.appendChild(doc.createTextNode(data.get(i).getValue()));
				rootElement.appendChild(tmp);
			}

			return doc;
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		return null;
	}
}