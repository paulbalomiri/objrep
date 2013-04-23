package eventmanagement.impl;

import eventmanagement.Artist;

public class ArtistImpl implements Artist {
	
	private String name;
	private String phone;
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	private String instrument;
	
	@SuppressWarnings("unused")
	private ArtistImpl(){};
	/**
	 * The Constructor is used directly, but could be later factored into a factory
	 * Also the object is immutable
	 */
	
	public ArtistImpl(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

	

	@Override
	public String getPhone() {
		
		return phone;
	}

	@Override
	public String getInstrument() {
		return instrument;
	}

}
