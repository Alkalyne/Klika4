package jus.aor.mobilagent.hello;

import java.util.logging.Level;

import jus.aor.mobilagent.kernel.Starter;
import jus.aor.mobilagent.kernel._Action;
import jus.aor.mobilagent.kernel.Agent;

/**
 * Classe de test élémentaire pour le bus à agents mobiles
 * @author  Morat
 */
public class Hello extends Agent{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	  * construction d'un agent de type hello.
	  * @param args aucun argument n'est requis
	  */
	 public Hello(Object... args) {
		 // ....
	 }
	 /**
	 * l'action à entreprendre sur les serveurs visités  
	 */
	protected _Action doIt = new _Action(){
		private static final long serialVersionUID = 1L;
		public void execute() {
			Starter.get_logger().log(Level.FINE,"Action -> Hello ~");
		}
	};
	
	protected _Action retour(){
		return doIt;
	}
}
