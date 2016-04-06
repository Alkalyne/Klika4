package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;


public class AgentServer extends Thread {

	HashMap<String,_Service<?>> myService;
	String name;
	ServerSocket servListener;
	int port;

	
	public AgentServer(String name, int port) {
		this.name=name;
		this.port=port;
		myService= new HashMap<String,_Service<?>>();
	}
	
	public void run(){
		System.out.println("Run agent serveur");
		Jar jar;
		Socket socketClient;
		_Agent ag;
		
		try {
			servListener = new ServerSocket(port);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
		while(true){
			try {
				socketClient = servListener.accept();
				ObjectInputStream ois = new ObjectInputStream(socketClient.getInputStream());
				jar = (Jar) ois.readObject();
				BAMAgentClassLoader BAMAgent = new BAMAgentClassLoader(this.getClass().getClassLoader());
				BAMAgent.integrateCode(jar);
				AgentInputStream ais = new AgentInputStream(socketClient.getInputStream(),BAMAgent);
				ag = (_Agent) ais.readObject();
				ais.close();
				//ag.init(BAMAgent, this, this.name,jar);
				Thread t = new Thread(ag);
				t.start();

				
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private _Agent getAgent (Socket sock){
		return null;
	}
	
	
	protected void addService (String name,_Service<?> service){
		myService.put(name,service);
	}
	
	
	public String toString(){
		return name;
	}
	
	protected _Service<?> getService (String name){
		return myService.get(name);
	}
	
	public URI site() {
		URI uri=null;
		try {
			uri= new URI("mobilagent://localhost:"+port+"/");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uri;
		
	}

}
