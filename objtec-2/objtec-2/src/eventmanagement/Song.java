package eventmanagement;

import eventmanagement.annotations.ConstructorArgumentRequirements;
import eventmanagement.time.Interval;
@ConstructorArgumentRequirements({String.class, Integer.class})
public interface Song {
	String getName();
	Integer getLength();
	/**
	 * The period this Song was included in repertoire
	 * @return
	 */
	Interval getPlayableInterval();
	
	void setPlayableInterval(Interval interval);
}
