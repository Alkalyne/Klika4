package jus.aor.mobilagent.kernel;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

public class BAMAgentClassLoader extends URLClassLoader {
	
	private Jar jar;

	public BAMAgentClassLoader(URL[] urls) {
		super(urls);
		// TODO Auto-generated constructor stub
	}
	
	protected void integrateCode(Jar jar){
		this.jar = jar;
		for(Map.Entry<String, byte[]> c : jar){
			this.defineClass(c.getKey(), c.getValue(), 0, c.getValue().length);
		}
	}
	
	private String className(String name){
		return null;
	}
	
	protected Jar extractCode(){
		return this.jar;
	}
	
	public String toString(){
		return super.toString();
	}

}
