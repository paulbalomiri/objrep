package eventmanagement;

import eventmanagement.time.Interval;

/**
 * An {@link Event} is a scheduled occurence with a name involving
 * Costs and possibly income {@link AccountingPosition}
 * 
 * Implementations must ensure that events have an immutable name ( {@link Event.getName() })
 * @author paul
 *
 */
public interface Event {
	/**
	 * Immutable Event Name. Implementations shall use the Name as mandatory
	 * constructor parameter;
	 * @return name
	 */
	String getName();
	/**
	 * The start and end Date of the Event represented as an {@link Interval} object
	 * @return
	 */
	Interval getInterval();
	/**
	 * Set Interval method provided for possible rescheduling
	 * @param interval
	 */
	void setInterval( Interval interval);
	/**
	 * Location information
	 * @return The place description where the Event takes place
	 */
	String getPlace();
	/**
	 * Location setter
	 */
	void setPlace(String newPlace);
	/**
	 * Describes the type of Event subclasses implement in a Word
	 * @return type descriptor
	 */
	String getShortTypeDescriptor();
}
