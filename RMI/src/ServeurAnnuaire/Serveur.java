package src.ServeurAnnuaire;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Serveur {
	/**
	 * @param args
	 */
	public static void main(final String args[]) {
		
		String nomFichier = "", nameGenerique = "";
		int port = 2000;
		// Récupération des arguments
		if (args.length != 3) {
			System.out.println("Server <nom du fichier de l'annuaire> <port du registry> <nom générique>");
			System.exit(1);
		}
		try {
			nomFichier = args[0];
			port = Integer.parseInt(args[1]);
			nameGenerique = args[2];
		} catch (Exception e) {
			System.out.println("Server <nom du fichier de l'annuaire> <port du registry> <nom générique>");
			System.exit(1);
		}
		
		// Installation d'un securityManager
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		// Mise en place du registry
		try {
			LocateRegistry.createRegistry(port);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			String name = "//localhost:"+ port +"/"+nameGenerique;
			Annuaire annu = new Annuaire(nomFichier);
	    	Naming.bind(name, annu);
	    	//System.out.println("Object registered : " + nameGenerique);
	    	//System.out.println("Taille : " + annu.size());
		} catch (Exception e) {
			System.out.println("Server err: " + e);
		}
		System.out.println("Server running...");
	}
}
