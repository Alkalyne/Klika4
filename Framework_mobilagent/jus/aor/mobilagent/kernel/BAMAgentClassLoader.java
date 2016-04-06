package jus.aor.mobilagent.kernel;


import java.io.IOException;
import java.util.Map;
import java.util.jar.JarException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

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
	
	@SuppressWarnings("deprecation")
	protected void integrateCode(Jar jar){
		Iterator<Entry<String,byte[]>> iterator =jar.classIterator().iterator();
		Class<?> c;
		
		while(iterator.hasNext()){
			Entry<String,byte[]> e = iterator.next();
			c = defineClass(e.getValue(),0,e.getValue().length);
			classMap.put(e.getKey(),c);
			
		}
	}
	
	protected Class<?> findClass(String className) throws ClassNotFoundException {
		String newClassName = className.replace(".","/")+".class";

		
		if(classMap.containsKey(newClassName)){
			return classMap.get(newClassName);
		}
		else throw new ClassNotFoundException(className);
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
