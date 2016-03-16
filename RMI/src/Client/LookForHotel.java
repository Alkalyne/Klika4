/**
 * J<i>ava</i> U<i>tilities</i> for S<i>tudents</i>
 */

package src.Client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedList;

import src.commun.Hotel;
import src.commun.Numero;
import src.commun._Annuaire;
import src.commun._Chaine;

/**
 * Représente un client effectuant une requête lui permettant d'obtenir les numéros de téléphone des hôtels répondant à son critère de choix.
 * @author  Morat
 */
public class LookForHotel{
	/** on part sur le prédicat que tous les serveurs sont sur la même machine **/
	String host = "localhost"; 
	String nomGeneriqueAnnuaire = "";
	String nomGeneriqueChaine = "";
	String localisation="Paris";
	/** ports : on commence par celui de l'annuaire puis on donne celui des différents serveurs de Chaines **/
	LinkedList<String> ports = new LinkedList<String>();
	
	private _Chaine[] tabChaines = new _Chaine[4];
	private _Annuaire pageJaune;
	
	/**
	 * Définition de l'objet représentant l'interrogation.
	 * @param args les arguments n'en comportant qu'un seul qui indique le critère
	 *          de localisation
	 */
	public LookForHotel(String host, String nomGeneriqueAnnuaire,
			String nomGeneriqueChaine, String localisation,
			LinkedList<String> ports) {
		this.host = host;
		this.nomGeneriqueAnnuaire = nomGeneriqueAnnuaire;
		this.nomGeneriqueChaine = nomGeneriqueChaine;
		this.localisation = localisation;
		this.ports = ports;
	}
	/**
	 * réalise une intérrogation
	 * @return la durée de l'interrogation
	 * @throws RemoteException
	 */
	public long call() {
		try {
			// récupération de l'annuaire :
			String nameAnnuaire = "//" + host + ":" + ports.get(0) + "/" + nomGeneriqueAnnuaire;
			pageJaune = (_Annuaire) Naming.lookup(nameAnnuaire);
			// liste des chaines d'hotels :
			LinkedList<Hotel> listChaine = new LinkedList<Hotel>();
			for(int i=1; i<5; i++){
				// récupération des chaines :
				String nameChaine = "//" + host + ":" + ports.get(i) + "/" + nomGeneriqueChaine;
				tabChaines[i-1] = (_Chaine) Naming.lookup(nameChaine);
				listChaine.addAll(tabChaines[i-1].getHotels(localisation));
			}
			// recherche des numéros :
			LinkedList<Numero> listNumero = new LinkedList<Numero>();
			Iterator<Hotel> i = listChaine.iterator();
			while(i.hasNext()){
				Hotel hCourant = i.next();
				listNumero.add(pageJaune.get(hCourant.name));
			}
			System.out.println("Voici la liste des numéros d'hotels à " + localisation);
			System.out.println(listNumero);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
