package jus.aor.mobilagent.kernel;

import java.util.Iterator;

public abstract class Agent implements _Agent {

	private static final long serialVersionUID = -3686255671996020363L;
	public Route feuilleDeRoute;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Iterator<Etape> i = feuilleDeRoute.iterator();
		while(i.hasNext()){
			Etape etapeCourante = i.next();
			// je dois aller sur le serveur
			// etapeCourante.server
			// pour exécuter l'action
			etapeCourante.action.execute();
		}
	}

	@Override
	public void addEtape(Etape etape) {
		// TODO Auto-generated method stub
		feuilleDeRoute.add(etape);
	}
}
