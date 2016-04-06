package jus.aor.mobilagent.kernel;

import java.net.URI;

public abstract class Agent implements _Agent {

	private static final long serialVersionUID = -3686255671996020363L;
	public Route feuilleDeRoute;
	protected transient AgentServer agentServ;
	protected transient String name;
	protected long tpsInit;

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(feuilleDeRoute.hasNext()){
			Etape etapeCourante = feuilleDeRoute.next();
			// je dois aller sur le serveur
			// etapeCourante.agentServ
			// pour exécuter l'action
			etapeCourante.action.execute();
			move();
		}
	}
	
	public void init(AgentServer agentServ, String name) {
		this.agentServ=agentServ;
		this.agentServ.setName(name);
		this.name = name;
		tpsInit = System.currentTimeMillis();
		if(feuilleDeRoute == null){
			feuilleDeRoute = new Route(new Etape(agentServ, _Action.NIHIL));
		}
	}


	public void reInit(AgentServer agentServ, String name) {
		// TODO Auto-generated method stub
		this.init(agentServ, name);
	}

	@Override
	public void addEtape(Etape etape) {
		// TODO Auto-generated method stub
		feuilleDeRoute.add(etape);
	}
	
	protected _Action retour(){
		return _Action.NIHIL;
	}
	
	protected _Service<?> getService(String name){
		return null;
	}
	
	public Agent (){
		this.init(agentServ, name);
	}
	
	private void move(){
		
	}
	
	protected void move(URI uri){
		
	}
	
	public String toString(){
		return feuilleDeRoute.toString();
		
	}
	
	protected String route(){
		return null;
		
	}
}
