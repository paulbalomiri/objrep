package eventmanagement.impl;

import eventmanagement.Concert;

public class ConcertImpl extends EventImpl implements Concert {

	
	public ConcertImpl(String name) {
		super(name);
	}	
	
	@Override
	public String getShortTypeDescriptor() {
		return "Concert";
	}

	

}
