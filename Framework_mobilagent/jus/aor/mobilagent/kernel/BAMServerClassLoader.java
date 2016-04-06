package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
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
	
@SuppressWarnings("deprecation")
public void integrateCode(Jar jar) {
		
		Iterator<Entry<String,byte[]>> iterator =jar.classIterator().iterator();
		Class<?> c;
		
		while(iterator.hasNext()){
			Entry<String,byte[]> e = iterator.next();
			c = defineClass(e.getValue(),0,e.getValue().length);
			classMap.put(e.getKey(),c);
			
		}
	}

}
