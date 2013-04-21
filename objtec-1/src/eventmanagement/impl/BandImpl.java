package eventmanagement.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import eventmanagement.AccountingPosition;
import eventmanagement.Band;
import eventmanagement.BandMember;
import eventmanagement.EventSchedule;
import eventmanagement.Factory;
import eventmanagement.FactoryException;
import eventmanagement.Song;
import eventmanagement.time.Interval;

public class BandImpl implements Band {
	private List<BandMember> bandMembers;
	private List<Song> repertoire;
	private String name;
	private List<AccountingPosition> accountingPositions;
	private EventSchedule schedule;
	private List<BandMember> members;

	public BandImpl(String name) throws FactoryException{
		this.name=name;
		this.schedule=Factory.getDefaultFactory().createEventSchedule(this);
		this.accountingPositions = new ArrayList<>();
		this.members = new ArrayList<BandMember>();
		this.repertoire = new ArrayList<>();
	}
	@Override
	public List<BandMember> getBandMembersAtDate(Date atTime) {
		List<BandMember> ret = new ArrayList<>();
		for (BandMember b : bandMembers){
			if(atTime == null) {
				ret.add(b);
			}else{
				if( b.getMembershipInterval().includes(atTime))
				 {
					ret.add(b);
				}
			}			
		}
		return ret;
	}
	
	@Override
	public List<Song> getRepertoire(Date atTime) {
		if (atTime == null) {
			return repertoire;
		}
		List<Song> ret= new ArrayList<Song> ();
		for(Song s: repertoire){
			if (s.getPlayableInterval().includes(atTime)){
			ret.add(s);
			}
		}
		return Collections.unmodifiableList(ret);
	}
	@Override
	public List<Song> getRepertoire() {
		return repertoire;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<AccountingPosition> getAccountingPositions() {
		return accountingPositions;
	}
	@Override
	public void setSchedule(EventSchedule schedule) {
		this.schedule=schedule;
	}
	@Override
	public EventSchedule getSchedule() {
		return schedule;
	}
	@Override
	public <T extends AccountingPosition> List<T> getAccountingPositionsByInterval(Class<T> type,
			Interval interval) {
		List<T> result = new ArrayList<T>();
		@SuppressWarnings("unchecked")
		Class<T> mytype=(Class<T>) AccountingPosition.class;
		if(type != null) {
			mytype = type;
		}
		for(AccountingPosition a : getAccountingPositions()){
			if(a.getEvent().getInterval().overlaps(interval) &&
				mytype.isAssignableFrom(a.getClass())	) {
				result.add(mytype.cast(a));
			}
		}
		
		return result;
		
	}	
	@Override
	public List<BandMember> getMembers() {
		return members;
	}
	

}
