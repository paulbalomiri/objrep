package eventmanagement.impl;

import eventmanagement.Song;
import eventmanagement.time.Interval;

public class SongImpl implements Song {

	private String name;
	private Integer length;
	private Interval playabaleInterval;

	public SongImpl(String name, Integer length){
		this.name=name;
		this.length=length;
		this.playabaleInterval = new Interval(null, null);
	}
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getLength() {
		return length;
	}

	@Override
	public Interval getPlayableInterval() {
		return playabaleInterval;
	}

	@Override
	public void setPlayableInterval(Interval interval) {
		playabaleInterval=interval;
		
	}

	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder("Song[")
		.append("\"" +this.getName()+"\",")
		.append(" Length: "+Integer.toString(this.getLength()/60) + ":" + Integer.toString(this.getLength()%60))
		.append(" playable:" + getPlayableInterval().toString());
		
		return str.toString();
	}
}
