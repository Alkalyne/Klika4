package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarException;

public class BAMServerClassLoader extends URLClassLoader{

	HashMap<String,Class<?>> classMap;
	Jar jar;

	public BAMServerClassLoader(URL[] urls, ClassLoader classLoader) {
		// TODO Auto-generated constructor stub
		super(new URL[]{},classLoader);
		classMap = new HashMap<String,Class<?>>();
	}
	
	public String toString(){
		return super.toString();
	}
	
	public void addURL(URL url){
		try {
			jar = new Jar(url.getPath());
		} catch (JarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		integrateCode(jar);
	}
	
	public void integrateCode(Jar jar) {
		this.jar = jar;
 		for(Map.Entry<String, byte[]> c : jar){
 			this.defineClass(c.getKey(), c.getValue(), 0, c.getValue().length);
 		}
	}

}
