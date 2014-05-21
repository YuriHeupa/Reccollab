package processing.app.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DAO implements DataDao {


	private XMLHandler xml = new XMLHandler();
	private Document docLoad;
	private String basePath;
	private String rootNode;
	Random randomGenerator;
	
	List<Data> buffer = new ArrayList<Data>();
	private boolean unsavedChanges = false;


	public DAO(String name) {
		rootNode = name;
		randomGenerator = new Random();
	}


	@Override
	public void deleteData(String tag) {
		int index = findIndexByTag(tag);
		if(index != -1) {
			buffer.remove(index);
			unsavedChanges  = true;
		}
	}

	@Override
	public List<Data> getAllData() {
		return buffer;
	}

	@Override
	public String getStringData(String Tag, String Default) {
		int index = findIndexByTag(Tag);
		if(index != -1)
			return buffer.get(index).getValue();
		return Default;
	}

	@Override
	public int getIntData(String Tag, int Default) {
		int index = findIndexByTag(Tag);
		if(index != -1)
			return Integer.parseInt(buffer.get(index).getValue());
		return Default;
	}

	@Override
	public boolean getBooleanData(String Tag, Boolean Default) {
		int index = findIndexByTag(Tag);
		if(index != -1)
			return Boolean.parseBoolean(buffer.get(index).getValue());
		return Default;
	}

	@Override
	public float getFloatData(String Tag, float Default) {
		int index = findIndexByTag(Tag);
		if(index != -1)
			return Float.parseFloat(buffer.get(index).getValue());
		return Default;
	}

	private int findIndexByTag(String tag) {
		for(int i = 0; i < buffer.size(); i++)
			if(buffer.get(i).getNode() == tag)
				return i;
		return -1;

	}

	@Override
	public void updateData(String tag, String value) {
		updateDataAtList(tag, value, buffer);
	}

	private void updateDataAtList(String tag, String value, List<Data> list) {
		int index = findIndexByTag(tag);
		if(index != -1) {
			list.get(index).setValue(value);
			unsavedChanges = true;
		}
	}

	@Override
	public void insertData(Data data) {
		insertDataAtList(data, buffer);
	}

	private void insertDataAtList(Data newData, List<Data> list) {
		if(validateData(newData)) {
			if(alreadyExists(newData)) {// se ja existe na lista de dados esse elemento apenas atualiza 
				if(newData.getNode().equals("Warning")) {
					insertDataAtList(new Data(newData.getNode()+
							String.valueOf(randomGenerator.nextInt(999999)), 
							newData.getValue()), list);
				} else {
					updateDataAtList(newData.getNode(), newData.getValue(), list);
				}
			} else {// se nao existe, adiciona
				list.add(newData);
			}
		}
	}

	private boolean alreadyExists(Data verifyData) {
		for (int i = 0; i < buffer.size(); i++) {
			if(buffer.get(i).getNode().equals(verifyData.getNode()))
				return true;
		}
		return false;
	}

	private boolean validateData(Data validData) {
		if(!validData.getNode().isEmpty() && !validData.getNode().equals(" "))
			return true;
		return false;
	}

	@Override
	public void loadData(String file) {
		basePath = file;
		docLoad = xml.ReadXML(basePath);

		if(docLoad != null) {
			if(docLoad.getElementsByTagName(rootNode).item(0) != null) {
				NodeList elements = docLoad.getElementsByTagName(rootNode).item(0).getChildNodes();

				for (int i = 0; i < elements.getLength(); i++) {

					Node nNode = elements.item(i);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						insertDataAtList(new Data(eElement.getTagName(), 
								eElement.getTextContent()), buffer);
					}
				}
			}
		}

	}

	@Override
	public void saveData() {
		Document doc = xml.saveXML(rootNode, buffer);
		if(doc != null) {
			try {
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(basePath));


				transformer.transform(source, result);

				unsavedChanges = false;
			} catch (TransformerException e) {

			}
		}
	}

	public boolean isUnsavedChanges() {
		return unsavedChanges;
	}






}
