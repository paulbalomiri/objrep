package eventmanagement.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eventmanagement.Band;
import eventmanagement.Event;
import eventmanagement.EventSchedule;
import eventmanagement.time.Interval;

public class EventScheduleImpl implements EventSchedule{

	
	private List<Event> events;
	private Band band;
	public EventScheduleImpl(Band band) {
		this.band=band;
		events=new ArrayList<Event>();
	}
	@Override
	public <T extends Event> List<T>  getEventsByInterval(Class<T> type, Interval query) {
		@SuppressWarnings("unchecked") Class<T> mytype=((Class<T>) Event.class);
		if (type!=null) {
				mytype = type;
		} 
		List<T> resultSet = new ArrayList<>();
		for (Event i : events) {
			if (mytype.isAssignableFrom(i.getClass()) &&i.getInterval().overlaps(query)) {
				resultSet.add(mytype.cast(i));
			}
		}
		return Collections.unmodifiableList(resultSet);
	}
	@Override
	public Band getBand() {
		return band;
	}
	@Override
	public List<Event> getEvents() {		
		return events;
	}
	
	@Override
	public <T extends Event>List<T> getEventsByName(Class<T> type, String name) {
		@SuppressWarnings("unchecked") Class<T> mytype=((Class<T>) Event.class);
		if (type!=null) {
				mytype = type;
		} 
		List<T> resultSet = new ArrayList<>();
		for(Event e : events){
			if(mytype.isAssignableFrom(e.getClass()) && e.getName().equals(name)){
				resultSet.add(mytype.cast(e));
			}
		}
		return Collections.unmodifiableList(resultSet);
	}
	@Override
	public <T extends Event> String getScheduleReport(Class<T> eventType,
			Interval interval) {
		StringBuilder str = new StringBuilder(
				"Schedule for time period: " + interval.toString() + "\n"
				);
		List<T> list = getEventsByInterval(eventType, interval);
		for (Event t: list){
			str.append(t.getShortTypeDescriptor() + " " + t.toString() +"\n");
		}
		return str.toString();
	}

}
