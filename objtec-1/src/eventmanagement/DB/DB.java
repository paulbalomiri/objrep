package eventmanagement.DB;

import java.util.ArrayList;
import java.util.List;

import eventmanagement.Artist;
import eventmanagement.Band;

public class DB {
	private static DB instance;
	private static List<Artist> artists;
	private List<Band> bands;
	private DB(){
		instance = this;
		bands=new ArrayList<Band>();
		artists=new ArrayList<>();
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

}
