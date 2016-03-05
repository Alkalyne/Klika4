package src.Client;

import java.util.LinkedList;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		String host = "localhost"; /* on part sur le prédicat que tous les serveurs sont sur la même machine */
		String nomGeneriqueAnnuaire = "";
		String nomGeneriqueChaine = "";
		String localisation="Paris";
		LinkedList<String> ports = new LinkedList<String>();
		
		// récupération des arguments
		if (args.length!=9){
			System.out.println("Arguments number wrong ! \n Client <hostname> <nom générique des chaines> <nom générique de l'annuaire> <localisation> <ports x5>");
			System.exit(1);
		}
		try {
			host = args[0];
			nomGeneriqueChaine = args[1];
			nomGeneriqueAnnuaire = args[2];
			localisation = args[3];
			// récupération des 5 ports : on commence par celui de l'annuaire puis on donne celui des différents serveurs de Chaines
			for(int i=4; i<10; i++){
				ports.add(args[i]);
			}
		}catch(Exception e) {
			System.out.println("Client <hostname> <nom générique des chaines> <nom générique de l'annuaire> <localisation> <ports x5>");
			System.exit(1);
		}	
		// installation d'un securityManager 
		if(System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}
		// Démarrage de la recherche
		new LookForHotel(host, nomGeneriqueAnnuaire, nomGeneriqueChaine, localisation, ports).call();
	}
}
