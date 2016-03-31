package jus.aor.mobilagent.kernel;

public abstract class Agent implements _Agent {

	private static final long serialVersionUID = -3686255671996020363L;
	public Route feuilleDeRoute;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(feuilleDeRoute.hasNext()){
			Etape etapeCourante = feuilleDeRoute.next();
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
