/**
 * J<i>ava</i> U<i>tilities</i> for S<i>tudents</i>
 */

package src.Client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import src.commun._Chaine;

/**
 * Représente un client effectuant une requête lui permettant d'obtenir les numéros de téléphone des hôtels répondant à son critère de choix.
 * @author  Morat
 */
public class LookForHotel{
	/** le critère de localisaton choisi */
	private String localisation;
	private String ou;
	private String qui;
	
	private _Chaine obj;
	
	/**
	 * Définition de l'objet représentant l'interrogation.
	 * @param args les arguments n'en comportant qu'un seul qui indique le critère
	 *          de localisation
	 */
	public LookForHotel(String... args){
		ou = args[0];
		qui = args[1];
		localisation = args[2];
	}
	/**
	 * réalise une intérrogation
	 * @return la durée de l'interrogation
	 * @throws RemoteException
	 */
	public long call() {
		try {
			String name = "//" + ou + "/" + qui;
			System.out.println(this + " -> " + name);
			obj = (_Chaine) Naming.lookup(name);
			System.out.println("Liste des hotels à " + localisation + " : " + obj.getHotels(localisation));
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
