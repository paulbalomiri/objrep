package eventmanagement.impl;

import eventmanagement.Event;
import eventmanagement.time.Interval;
/**
 * Class implementing common functionality of all Events
 * Subclasses shall be used which implement different types
 * of Events.
 * @author paul
 *
 */
public abstract class EventImpl implements Event {
	// Disable standard constructor
	@SuppressWarnings("unused")
	private EventImpl(){
		
	}
	private  Interval interval;
	private String name;
	private String place;
	public EventImpl(String name) {
		this.name = name;
	}
	@Override
	public Interval getInterval() {
		return interval;
	}
	@Override
	public void setInterval(Interval interval) {
		this.interval=interval;
		
	}

	@Override
	public String getPlace() {
	
		return place;
	}
	@Override
	public void setPlace(String newPlace) {
		this.place = newPlace;
		
	}

	
	@Override
	public String getName() {
		return name;
	}
	 @Override
	public String toString() {
		return "Event[\""+this.getName()+"\" scheduled for:"+interval+" ]";
	}

}
