package jus.aor.mobilagent.kernel;


import java.io.IOException;
import java.util.Map;
import java.util.jar.JarException;
import java.util.HashMap;

public class BAMAgentClassLoader extends ClassLoader {
	
	private Jar jar;
	HashMap<String,Class<?>> classMap;

	public BAMAgentClassLoader(){
		super();
		classMap = new HashMap<String,Class<?>>();
	}
	
	public BAMAgentClassLoader(ClassLoader cl) {
		super(cl);
		classMap = new HashMap<String,Class<?>>();
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
	
	protected Class<?> findClass(String className) throws ClassNotFoundException {
		String newClassName = className.replace(".","/")+".class";

		
		if(classMap.containsKey(newClassName)){
			return classMap.get(newClassName);
		}
		else throw new ClassNotFoundException(className);
	}
	
	protected Jar extractCode(){
		return this.jar;
	}
	
	public void extractCode(String codeBase) {
		// TODO Auto-generated method stub
		try {
			this.jar = new Jar(codeBase);
		} catch (JarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String toString(){
		return super.toString();
	}

}
