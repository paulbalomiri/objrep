package eventmanagement;

import eventmanagement.annotations.ConstructorArgumentRequirements;

/**
 * Cost or income position for a balance sheet
 * @author paul
 *
 */
@ConstructorArgumentRequirements({
	String.class, Event.class, String.class, Integer.class
	}
		
		)
public interface AccountingPosition {
	/**
	 * The chapter on the balance sheet where this Accounting position belongs to
	 * In a simple balance sheet only income and expenses are used as chapters
	 * @return
	 */
	String getChapter();	
	Event getEvent();
	String getName();
	Integer getValue();
}
