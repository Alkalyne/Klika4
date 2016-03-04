package src.ServeurAnnuaire;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import src.commun.Numero;
import src.commun._Annuaire;

public class Annuaire extends UnicastRemoteObject implements _Annuaire {

	private static final long serialVersionUID = 1L;
	HashMap<String, Numero> pageJaune = new HashMap<String, Numero>();
	
	public Annuaire (String file) throws RemoteException{
		/* Récupération de l'annuaire dans le fichier xml */
		DocumentBuilder docBuilder = null;
		Document doc = null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = docBuilder.parse(file);
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

		String name, numero;
		NodeList list = doc.getElementsByTagName("Telephone");
		NamedNodeMap attrs;
		/* acquisition de toutes les entrées de l'annuaire */
		for(int i =0; i<list.getLength(); i++) {
			attrs = list.item(i).getAttributes();
			name = attrs.getNamedItem("name").getNodeValue();
			numero = attrs.getNamedItem("numero").getNodeValue();
			pageJaune.put(name, new Numero(numero));
		}
	}
	
	@Override
	public Numero get(String abonne) throws RemoteException{
		return pageJaune.get(abonne);
	}

	@Override
	public int size() throws RemoteException {
		return pageJaune.size();
	}
}
