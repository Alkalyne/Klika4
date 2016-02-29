package src.ServeurChaine;


import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import src.commun.Hotel;


public class Serveur {
	/**
	 * @param args
	 */
	public static void main(final String args[]) {
		
		String nom = "";
		int port = 1099;
		Registry registry = null;
		// Récupération des arguments
		if (args.length != 2) {
			System.out.println("Server <nom du fichier de chaînes> <port du registry> ");
			System.exit(1);
		}
		try {
			nom = args[0];
			port = Integer.parseInt(args[1]);
		} catch (Exception e) {
			System.out.println("Server <nom du fichier de chaînes> <port du registry> ");
			System.exit(1);
		}
		
		// Installation d'un securityManager
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		// Mise en place du registry
		try {
			registry = LocateRegistry.createRegistry(port);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Chaine chaine = new Chaine(nom);
	    	Naming.bind("//localhost:"+ port +"/"+nom, chaine);
		} catch (Exception e) {
			System.out.println("Server err: " + e);
		}
	}
}
