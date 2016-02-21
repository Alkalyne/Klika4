package src;

import java.util.HashMap;

public class Annuaire implements _Annuaire {

	HashMap<String, Numero> pageJaune = new HashMap<String, Numero>();
	
	public Annuaire (HashMap<String, Numero> l){
		this.pageJaune = l;
	}
	
	@Override
	public Numero get(String abonne) {
		return pageJaune.get(abonne);
	}

}
