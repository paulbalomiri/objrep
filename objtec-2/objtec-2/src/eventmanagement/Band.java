package eventmanagement;

import java.util.Date;
import java.util.List;

import eventmanagement.annotations.ConstructorArgumentRequirements;
import eventmanagement.time.Interval;

@ConstructorArgumentRequirements({String.class})
public interface Band {
	List<BandMember> getBandMembersAtDate(Date atTime);
	List<Song> getRepertoire(Date atTime);
	String getName();
	List<AccountingPosition> getAccountingPositions();
	void setSchedule(EventSchedule schedule);
	EventSchedule getSchedule();
	<T extends AccountingPosition> List<T> getAccountingPositionsByInterval( Class<T> type, Interval interval);
	List<BandMember> getMembers();
	List<Song> getRepertoire();
}
