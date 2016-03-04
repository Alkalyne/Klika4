/**
 * J<i>ava</i> U<i>tilities</i> for S<i>tudents</i>
 */

package src.Client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import src.commun._Annuaire;
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
	private String quiAnnuaire;
	private String ouAnnuaire;
	
	private _Chaine obj;
	private _Annuaire obj2;
	
	/**
	 * Définition de l'objet représentant l'interrogation.
	 * @param args les arguments n'en comportant qu'un seul qui indique le critère
	 *          de localisation
	 */
	public LookForHotel(String... args){
		ou = args[0];
		qui = args[1];
		localisation = args[2];
		ouAnnuaire=args[3];
		quiAnnuaire=args[4];
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
			String nameAnnuaire = "//" + ouAnnuaire + "/" + quiAnnuaire;
			obj2 = (_Annuaire) Naming.lookup(nameAnnuaire);

			System.out.println("Numéro de l'hôtel 0 " + localisation + " : " + obj2.get(obj.getHotels(localisation).get(1).name));
			

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
