package src;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class Chaine implements _Chaine {
	
	private List<Hotel> possession = new LinkedList<Hotel>();
	
	public Chaine (List<Hotel> l,String filename) {
		this.possession = l;
		
		/* récupération des hôtels de la chaîne dans le fichier xml passé en 1er argument */
		DocumentBuilder docBuilder = null;
		Document doc=null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			doc = docBuilder.parse(new File(filename));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String name, localisation;
		NodeList list = doc.getElementsByTagName("Hotel");
		NamedNodeMap attrs;
		/* acquisition de toutes les entrées de la base d'hôtels */
		for(int i =0; i<list.getLength();i++) {
			attrs = list.item(i).getAttributes();
			name=attrs.getNamedItem("name").getNodeValue();
			localisation=attrs.getNamedItem("localisation").getNodeValue();
			possession.add(new Hotel(name,localisation));
		}
	}
	
	@Override
	public List<Hotel> get(String localisation) {
		Iterator<Hotel> i = possession.iterator();
		List<Hotel> region = new LinkedList<Hotel>();
		while(i.hasNext()){
			Hotel hCourant = i.next();
			if(hCourant.localisation == localisation){
				region.add(hCourant);
			};	
		}
		return region;
	}


}
