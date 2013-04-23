package eventmanagement.impl;

import eventmanagement.Artist;
import eventmanagement.BandMember;
import eventmanagement.time.Interval;

public class BandMemberImpl implements BandMember {

	

	private Artist artist;
	private Interval  membershipInterval;
	public BandMemberImpl(Artist artist, Interval interval) {
		this.artist=artist;
		this.membershipInterval =interval;
	}
	@Override
	public Interval getMembershipInterval() {
		// TODO Auto-generated method stub
		return membershipInterval;
	}
	@Override
	public Artist getArtist() {		
		return artist;
	}

}
