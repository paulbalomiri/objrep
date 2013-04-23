package eventmanagement;

import eventmanagement.annotations.ConstructorArgumentRequirements;
import eventmanagement.time.Interval;
@ConstructorArgumentRequirements({Artist.class,Interval.class})
public interface BandMember {
	Artist getArtist();
	Interval getMembershipInterval();
}
