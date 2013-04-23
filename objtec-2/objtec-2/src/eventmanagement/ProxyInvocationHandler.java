package eventmanagement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProxyInvocationHandler implements InvocationHandler {
	
	private Map<Class<?>, Class<?>> typeMap;
	private Map<String, Map.Entry<Class<?>, Class<?>>> nameMap;
	
	public ProxyInvocationHandler( Map<Class<?>, Class<?>> typeMap) {
		this.typeMap=typeMap;
		this.nameMap= new HashMap<>();
		for(Map.Entry<Class<?>, Class<?>> iter: typeMap.entrySet()){
			nameMap.put(getSimplifiedClassName(iter.getKey()), iter);
		}
	}
	private String getSimplifiedClassName(Class<?> clazz){
		int idx = clazz.getName().lastIndexOf('.');
		if(idx < 0) {
			return clazz.getName();
		} else {
			return clazz.getName().substring(idx+1);
		}
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if(!method.getName().substring(0,"create".length()).equals("create")){
			throw new FactoryException("Factory Interfaces must only have methods called createClassName");
		}		
		String expectedName=
					method.getName().substring("create".length());
			Map.Entry<Class<?>, Class<?>>  typeMapping = nameMap.get(expectedName);
		if(typeMapping == null) {
			throw new FactoryException("No Class named \"" + expectedName+"\" in typemap");
		}
			
		return create(typeMapping.getKey(), args);
	}
	
	/**
	 * Finds a compatible Constructor which shows arguments which can be substituted for the provided parameters
	 * @param clazz
	 * @param args
	 * @return
	 * @throws NoSuchMethodException
	 */
	private Constructor<?> getCompatibleConstructor(Class<?>clazz, Class<?>[] args ) throws NoSuchMethodException{
		for (Constructor<?> c: clazz.getConstructors()) {
			Class<?> parms[]=c.getParameterTypes();
			if(parms.length != args.length){
				continue;
			}
			for (int i=0; i<args.length; i++){
				if(!parms[i].isAssignableFrom(args[i])){
					continue;
				}				
				return c;
			}
			return null;
		}
		throw new NoSuchMethodException();
	}
	
	private Object create(Class<? extends Object> clazz, Object ... params) throws FactoryException {
	try{
		List<Class<? extends Object>> paramTypes  = new ArrayList<Class<? extends Object>>();
		for(Object o :params){
			paramTypes.add(o.getClass());
		}
		return getCompatibleConstructor(typeMap.get(clazz) ,paramTypes.toArray(new Class<?>[]{})).newInstance(params);
	
	} catch (NoSuchMethodException | 
			 SecurityException |
			 IllegalAccessException| 
			 IllegalArgumentException| 
			 InvocationTargetException|
			 InstantiationException e) {
		e.printStackTrace();
		throw new FactoryException("No Band Type injector or faulty constructor");
	}
	}

}
