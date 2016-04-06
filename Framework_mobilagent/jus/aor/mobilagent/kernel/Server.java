/**
 * J<i>ava</i> U<i>tilities</i> for S<i>tudents</i>
 */
package jus.aor.mobilagent.kernel;

import java.net.InetAddress;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jus.aor.mobilagent.kernel.BAMAgentClassLoader;
import jus.aor.mobilagent.kernel._Agent;

/**
 * Le serveur principal permettant le lancement d'un serveur d'agents mobiles 
 * et les fonctions permettant de déployer des services et des agents.
 * @author     Morat
 */
public final class Server implements _Server {
	/** le nom logique du serveur */
	protected String name;
	/** le port où sera ataché le service du bus à agents mobiles.
	 * Par défaut on prendra le port 10140 */
	protected int port=10140;
	/** le server d'agent démarré sur ce noeud */
	protected AgentServer agentServer;
	/** le nom du logger */
	protected String loggerName;
	/** le logger de ce serveur */
	protected Logger logger=null;
	/**
	 * Démarre un serveur de type mobilagent 
	 * @param port le port d'écoute du serveur d'agent 
	 * @param name le nom du serveur
	 */
	public Server(final int port, final String name){
		this.name=name;
		try {
			this.port=port;
			/* mise en place du logger pour tracer l'application */
			loggerName = "jus/aor/mobilagent/"+InetAddress.getLocalHost().getHostName()+"/"+this.name;
			logger=Logger.getLogger(loggerName);
			/* démarrage du server d'agents mobiles attaché à cette machine */
			this.agentServer = new AgentServer(name, port);
			/* temporisation de mise en place du server d'agents */
			Thread.sleep(1000);
			/* Lancement du serveur acceuillant les agents */
			this.agentServer.start();
		}catch(Exception ex){
			logger.log(Level.FINE," erreur durant le lancement du serveur"+this,ex);
			return;
		}
	}
	/**
	 * Ajoute le service caractérisé par les arguments
	 * @param name nom du service
	 * @param classeName classe du service
	 * @param codeBase codebase du service
	 * @param args arguments de construction du service
	 */
	public final void addService(String name, String classeName, String codeBase, Object... args) {
		try {
			BAMServerClassLoader agentServerLoader = new BAMServerClassLoader(new URL[]{},this.getClass().getClassLoader());
			agentServerLoader.addURL(new URL(codeBase));
			Class<?> classe = Class.forName(classeName,true,this.getClass().getClassLoader());
			_Service<?> service = (_Service<?>)classe.getConstructor(Object[].class).newInstance(new Object[]{args});
			agentServer.addService(name, service);
		}catch(Exception ex){
			logger.log(Level.FINE," erreur durant le lancement du serveur"+this,ex);
			return;
		}
	}
	/**
	 * deploie l'agent caractérisé par les arguments sur le serveur
	 * @param classeName classe du service
	 * @param args arguments de construction de l'agent
	 * @param codeBase codebase du service
	 * @param etapeAddress la liste des adresse des étapes
	 * @param etapeAction la liste des actions des étapes
	 */
	public final void deployAgent(String classeName, Object[] args, String codeBase, List<String> etapeAddress, List<String> etapeAction) {
		try {
			//A COMPLETER en terme de startAgent
			BAMAgentClassLoader agentLoader = new BAMAgentClassLoader(new URL{}[],this.getClass().getClassLoader());
			//on ajout
			agentLoader.addURL(codeBase.getPath);
			//on creer la classe
			Class<?> classe = (Class<?>)Class.forName(classeName,true,agentLoader);
			//le constructeur
			Constructor<?> constructor = classe.getConstructor(Object[].class);

			//on creer une nouvelle instance d'un agent
			Agent agentCourant = (Agent)constructor.newInstance(new Object[]{args});

			agentCourant.init(agentLoader, this.agentServer, this.name);

			agentCourant.addEtape(new Etape(new URI(this.site),_Action.NIHIL));
			//System.out.println(agentCourant.route.get());
			//On lui creer une route
			//si il y a autant d'actions que d'adresses
			if (etapeAddress.size() == etapeAction.size()){
				//on lui ajoute toutes les actions
				for (int i=0;i<etapeAddress.size();i++){
					Field f = agentCourant.getClass().getDeclaredField(etapeAction.get(i));
					f.setAccessible(true);
					_Action action = (_Action) f.get(agentCourant);
					agentCourant.addEtape(new Etape(new URI(etapeAddress.get(i)), action));
					//System.out.println(new Etape(new URI(etapeAddress.get(i)), action));
		}catch(Exception ex){
			logger.log(Level.FINE," erreur durant le lancement du serveur"+this,ex);
			return;
		}
	}
	/**
	 * Primitive permettant de "mover" un agent sur ce serveur en vue de son exécution
	 * immédiate.
	 * @param agent l'agent devant être exécuté
	 * @param loader le loader à utiliser pour charger les classes.
	 * @throws Exception
	 */
	protected void startAgent(_Agent agent, BAMAgentClassLoader loader) throws Exception {
		agent.init(loader, agentServer, name);
		agent.run();
	}
	
	public String toString() {
		return null;
	}
	
	public final void deployAgent(String classeName, Object[] args, String codeBase,List <ServiceDescriptor> descriptorList) {

	}
	
}
