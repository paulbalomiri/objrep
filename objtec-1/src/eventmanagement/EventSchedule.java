package eventmanagement;

import java.util.List;

import eventmanagement.annotations.ConstructorArgumentRequirements;
import eventmanagement.time.Interval;
/**
 * Holds the scheduled events for the band 
 * @author paul
 *
 */
@ConstructorArgumentRequirements(Band.class)
public interface EventSchedule {
	Band getBand();
	/**
	 * Queries Events for a specified interval
	 * @param type The type (Subtype of Event) of wvent to return within the List
	 * @param interval The time interval for chich qu query
	 * @return unmodifiable list of scheduled events in specified interval
	 */
	<T extends Event> List<T> getEventsByInterval(Class<T> type , Interval interval);
	
	<T extends Event> List<T> getEventsByName(Class<T> type, String name);
	/**
	 * Returs the List of all Events scheduled by the scheduler.
	 * The list can be modified to schedule new events or delete events. 
	 * @return
	 */
	
	List<Event> getEvents();
	
	/**
	 * Prints a report of scheduled events (Concerts, Rehearsals or both)
	 * 
	 * 
	 * @param t Type of Event to include in report
	 * @param interval Interval which shall be included in the report
	 * @return A string containing the report
	 */
	//TODO: This is a view compilation rather than a model functionality
	<T extends Event> String getScheduleReport(Class<T> eventType, Interval interval);
	
}
