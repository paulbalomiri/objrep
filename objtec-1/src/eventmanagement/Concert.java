package eventmanagement;

import eventmanagement.annotations.ConstructorArgumentRequirements;

/**
 * This is an event Type which can generate Income
 * @author paul
 *
 */
@ConstructorArgumentRequirements({String.class})
public interface Concert extends Event {
	
	
}
