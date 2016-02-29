package src.Client;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		String nom="", host="localhost", localisation="Paris";
		// récupération des arguments
		if (args.length!=2){
			System.out.println("Client <hostname> <nom générique des objets distants> <localisation>");
			System.exit(1);
		}
		try {
			host = args[0];
			nom = args[1];
			localisation = args[2];
		}catch(Exception e) {
			System.out.println("Client <hostname> <nom générique des objets distants> <localisation>");
			System.exit(1);
		}	
		// installation d'un securityManager 
		// A COMPLETER : INSTALLATION DU SECURITYMANAGER
		/*if(System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}*/
		// Démarrage des consommateurs
		new LookForHotel(host, nom, localisation).call();
	}
}
