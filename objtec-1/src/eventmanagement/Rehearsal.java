package eventmanagement;

import eventmanagement.annotations.ConstructorArgumentRequirements;

/** A rehearsal is an {@link Event} with only costs ({@link Event.getCosts()}),
 *  and no income
 * 
 * @author paul
 *
 */
@ConstructorArgumentRequirements({String.class})
public interface Rehearsal extends Event {
	
}
