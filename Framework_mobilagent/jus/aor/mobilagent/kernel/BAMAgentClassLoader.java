package jus.aor.mobilagent.kernel;


import java.io.IOException;
import java.util.Map;
import java.util.jar.JarException;

public class BAMAgentClassLoader extends ClassLoader {
	
	private Jar jar;

	public BAMAgentClassLoader(ClassLoader cl) {
		super(cl);
		// TODO Auto-generated constructor stub
	}
	
	public BAMAgentClassLoader(String name,ClassLoader cl) throws JarException, IOException {
		super(cl);
		jar = new Jar(name);
		integrateCode(jar);
		// TODO Auto-generated constructor stub
	}
	
	protected void integrateCode(Jar jar){
		this.jar = jar;
		for(Map.Entry<String, byte[]> c : jar){
			this.defineClass(c.getKey(), c.getValue(), 0, c.getValue().length);
		}
	}
	
	private String className(String name){
		return jar.getClass(name).toString();
	}
	
	protected Jar extractCode(){
		return this.jar;
	}
	
	public String toString(){
		return super.toString();
	}

}
