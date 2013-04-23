package eventmanagement.impl;

import eventmanagement.AccountingPosition;
import eventmanagement.Event;

public class AccountingPositionImpl implements AccountingPosition {

	private String chapter;
	private String name;
	private Integer value;
	private Event event;

	@SuppressWarnings("unused")
	private AccountingPositionImpl(){};
	/**
	 * The Constructor is used directly, but could be later factored into a factory
	 * Also the object is immutable
	 */
	public AccountingPositionImpl(String chapter, Event event, String name, Integer value) {
		super();
		this.chapter = chapter;
		this.name = name;
		this.value = value;
		this.event=event;
	}

	@Override
	public String getChapter() {
		return chapter;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public Integer getValue() {
		return value;
	}
	@Override
	public Event getEvent() {
	
		return event;
	}

}
