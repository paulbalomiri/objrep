package eventmanagement.DB;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eventmanagement.Artist;
import eventmanagement.Band;
import eventmanagement.Factory;
import eventmanagement.ProxyInvocationHandler;

public class DB {
	private static DB instance;
	private static List<Artist> artists;
	private static Factory factory;
	private List<Band> bands;
	private DB(){
		instance = this;
		bands=new ArrayList<Band>();
		artists=new ArrayList<>();
	}
	private static void init(){
		if (factory==null){
			if (typeMap == null) {
				fillTypeMap();
			}
			factory = (Factory) Proxy.newProxyInstance(DB.class.getClassLoader(), new Class<?>[]{Factory.class}, new ProxyInvocationHandler(DB.typeMap));
		}
	}
	public static Factory getFactory() {
		
		return factory;
	}
		
	public static DB getInstance(){
		
		if (instance == null){
			instance = new DB();
		}
		return instance;
	}

	
	public static Band getBandByName(String name) {
		for (Band b : getInstance().bands){
			if (b.getName().equals(name)) {
				return b;
			}
		}
	
		return null;
	}
	public static List<Band> getBands(){
		return getInstance().bands;
	}
	public static List<Artist> getArtists() {
		return artists;
	}
	public static Artist getArtistByName(String name){
		getInstance();
		for(Artist a : DB.getArtists()){
			if(a.getName() == name) {
				return a;
			}
		}
		return null;
	}
	private static Map<Class<?>, Class<?>>  typeMap = null;
	public static Map<Class<?>, Class<?>>  getTypeMap(){
		DB.init();
		return Collections.unmodifiableMap(typeMap);
	}
	private static Map<Class<?>, Class<?>> fillTypeMap(){
		DB.typeMap = new HashMap<Class<?>, Class<?>>();
		DB.typeMap.put(eventmanagement.AccountingPosition.class,
				eventmanagement.impl.AccountingPositionImpl.class);
		DB.typeMap.put(eventmanagement.Artist.class,
				eventmanagement.impl.ArtistImpl.class);
		DB.typeMap.put(eventmanagement.BalanceSheet.class,
				eventmanagement.impl.BalanceSheetImpl.class);
		DB.typeMap.put(eventmanagement.Band.class,
				eventmanagement.impl.BandImpl.class);
		DB.typeMap.put(eventmanagement.BandMember.class,
				eventmanagement.impl.BandMemberImpl.class);
		DB.typeMap.put(eventmanagement.Concert.class,
				eventmanagement.impl.ConcertImpl.class);
		// The Event type is not beant to be constructed directly
		// DB.typeMap.put(eventmanagement.Event.class,
		// eventmanagement.impl.EventImpl.class);
		DB.typeMap.put(eventmanagement.EventSchedule.class,
				eventmanagement.impl.EventScheduleImpl.class);
		DB.typeMap.put(eventmanagement.Rehearsal.class,
				eventmanagement.impl.RehearsalImpl.class);
		DB.typeMap.put(eventmanagement.Song.class,
				eventmanagement.impl.SongImpl.class);
		// DB.typeMap.put(eventmanagement..class, eventmanagement.impl..class);
		return DB.typeMap;
	}

}
