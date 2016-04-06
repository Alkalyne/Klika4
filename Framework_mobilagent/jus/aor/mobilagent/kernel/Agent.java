package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.NoSuchElementException;

//** Client mobile qui se déplace de serveur en serveur en emportant avec lui son contexte **/

public class Agent implements _Agent{

	private static final long serialVersionUID = 1L;
	private boolean first=false;
	protected transient BAMAgentClassLoader loader;
	protected transient AgentServer server;
	protected transient String serverName;
	protected transient Jar jar;
	protected Route route;
	

	@Override
	public void init(AgentServer agentServer, String serverName) {
		this.server = agentServer;
		this.serverName = serverName;
		if(route==null){
			route=new Route(new Etape(server.site(),_Action.NIHIL));
		}
	}

	
	@Override
	public void init(BAMAgentClassLoader loader, AgentServer server,String serverName) {
		this.server = server;
		this.serverName = serverName;
		this.loader=loader;
		if(route==null){
			route=new Route(new Etape(server.site(),_Action.NIHIL));
		}
		
	}
	

	
	@Override
	// On réinitialise k'agent quand il arrive sur le bus
	// > doit transiter avec son BAMAgentClassLoader !
	public void reInit( AgentServer agentServer, String serverName, BAMAgentClassLoader loader) {
		// On doit renseigner le serveur courant et le classloader
		this.server = agentServer;
		this.serverName = serverName;
		this.loader = loader;
		System.out.println("Reinit");
	}

	
	@Override
	public void addEtape(Etape etape) {
		// Ajout de l'étape à la feuille de route
		this.route.add(etape);
	}
	
	
	@Override
	/** Methode appelée lorsqu'un agent arrive sur un serveur **/
	public void run() {
		// On execute l'action a effectuer si on ne se trouve pas sur le serveur initial
				if(route.hasNext() && first){
					route.get().action.execute();
					route.next();
				}

				if(route.hasNext()){
					
					// Envoi de l'agent courant vers prochain serveur
						first=true;
						Socket socket;
						try {
							socket = new Socket(route.get().server.getHost(),route.get().server.getPort());
							OutputStream os=socket.getOutputStream();
							// Flux pour l'envoi de données
							ObjectOutputStream oosJar = new ObjectOutputStream(os);
							
							oosJar.writeObject(jar);
							

							ObjectOutputStream oosAg = new ObjectOutputStream(os);
							oosAg.writeObject(this);
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchElementException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
		
	}


	@Override
	public void init(List<ServiceDescriptor> serviceList) {
		// TODO Auto-generated method stub
		
	}



	
}