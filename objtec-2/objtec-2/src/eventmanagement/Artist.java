package eventmanagement;

import eventmanagement.annotations.ConstructorArgumentRequirements;

@ConstructorArgumentRequirements({String.class})
public interface Artist {
	String getName();
	
	String getPhone();
	void setPhone(String phone );
	String getInstrument();
	void setInstrument(String instrument);
}
