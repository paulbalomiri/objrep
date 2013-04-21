package eventmanagement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eventmanagement.time.Interval;

public class Factory {
	private static Factory defaultFactory = null;
	public static Factory getDefaultFactory() throws FactoryException  {
		if (defaultFactory == null) {
			throw new FactoryException("No default factory set");
		}
		return defaultFactory;
	}
	public static void setDefaultFactory(Factory f){
		defaultFactory= f;
	}
	private Map<Class<?>,Class<?>> typeInjector;
	@SuppressWarnings("unused")
	private Factory(){
		if (null==Factory.defaultFactory) {
		setDefaultFactory(this);
		}
	}
	public Factory(Map<Class<?>,Class<?>> typeInjector) {
		this.typeInjector=typeInjector;
	}
	/**
	 * Create a Band
	 * @param name
	 * @throws FactoryException 
	 */
	public Band createBand(String name) throws FactoryException{
			return (Band) create(Band.class,  name);
		
	}
	public AccountingPosition createAccountingPosition(String chapter,Event event, String name, Integer value ) throws FactoryException{
		return (AccountingPosition) create(AccountingPosition.class , chapter,event,name,value);  
		
	}
	public Artist createArtist(String name) throws FactoryException{
		return (Artist)create(Artist.class,name);
	}
	public BalanceSheet createBalanceSheet(Band band, Interval interval) throws FactoryException{
		return (BalanceSheet) create(BalanceSheet.class, band, interval);
	}
	public BandMember createBandMember(Artist artist, Interval interval) throws FactoryException {
		return (BandMember) create(BandMember.class, artist,interval);
	}
	public Concert createConcert(String name) throws FactoryException {
		return (Concert) create(Concert.class , name);
	}
	public EventSchedule createEventSchedule(Band band) throws FactoryException{
		return (EventSchedule) create(EventSchedule.class,band);
	}
	public Rehearsal createRehearsal(String name) throws FactoryException{
		return (Rehearsal) create(Rehearsal.class, name);
	}
	public Song createSong(String name, Integer length) throws  FactoryException{
		return (Song) create(Song.class, name,length );
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
			}
			return c;
		}
		throw new NoSuchMethodException();
	}
	
	private Object create(Class<? extends Object> clazz, Object ... params) throws FactoryException {
	try{
		List<Class<? extends Object>> paramTypes  = new ArrayList<Class<? extends Object>>();
		for(Object o :params){
			paramTypes.add(o.getClass());
		}
		return getCompatibleConstructor(typeInjector.get(clazz) ,paramTypes.toArray(new Class<?>[]{})).newInstance(params);
	
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
