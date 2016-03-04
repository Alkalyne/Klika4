package src.ServeurChaine;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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

import src.commun.Hotel;
import src.commun._Chaine;

public class Chaine extends UnicastRemoteObject implements _Chaine {
	
	private static final long serialVersionUID = 1L;
	private List<Hotel> possession = new LinkedList<Hotel>();
	
	public Chaine (String filename) throws RemoteException { 
		/* récupération des hôtels de la chaîne dans le fichier xml passé en 1er argument */
		DocumentBuilder docBuilder = null;
		Document doc=null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = docBuilder.parse(new File(filename));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public List<Hotel> getHotels (String localisation) throws RemoteException {
		Iterator<Hotel> i = possession.iterator();
		List<Hotel> region = new LinkedList<Hotel>();
		while(i.hasNext()){
			Hotel hCourant = i.next();
			if(hCourant.localisation.equals(localisation)){
				region.add(hCourant);
			}
		}
		return region;
	}

	public int size() throws RemoteException {
		// TODO Auto-generated method stub
		return possession.size();
	}

}
