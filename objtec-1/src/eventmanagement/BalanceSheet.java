package eventmanagement;

import java.util.List;

import eventmanagement.annotations.ConstructorArgumentRequirements;
import eventmanagement.time.Interval;
/**
 * Represents a compiled balance sheet. Use Construction methods of implementations
 * to specify what should be included in a balance sheet
 * @author paul
 *
 */
@ConstructorArgumentRequirements({Band.class, Interval.class})
public interface BalanceSheet {
	
	/**
	 * Band for which this BalanceSheet is created
	 * @return the Band
	 */
	Band getBand();
	
	/**
	 * The Interval of time this Band covers;
	 * @return
	 */
	Interval getIntervalCovered();
	/**
	 *  
	 * @return the Positions on this balance sheet
	 */
	List<AccountingPosition> getPositions();
	/**
	 * Create a Balance Sheet containing all Positions for the given event type
	 * @param type Which Event Type. Supply the Class object of the corresponding Event Subclass. Null is equated to Event.class
	 * @param from , to start and end date of the search window.
	 * @return The Balanceheet for the positions
	 */
	<T> Integer getFinalBalance(Class<T> eventType);

	public <T> String getReportForEventType(Class<T> eventType);
}
