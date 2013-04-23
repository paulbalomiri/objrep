package eventmanagement;

import eventmanagement.time.Interval;

public interface Factory {

	/**
	 * Create a Band
	 * @param name
	 * @throws FactoryException 
	 */
	public Band createBand(String name) throws FactoryException;

	public AccountingPosition createAccountingPosition(String chapter,
			Event event, String name, Integer value) throws FactoryException;

	public Artist createArtist(String name) throws FactoryException;

	public BalanceSheet createBalanceSheet(Band band, Interval interval)
			throws FactoryException;

	public BandMember createBandMember(Artist artist, Interval interval)
			throws FactoryException;

	public Concert createConcert(String name) throws FactoryException;

	public EventSchedule createEventSchedule(Band band) throws FactoryException;

	public Rehearsal createRehearsal(String name) throws FactoryException;

	public Song createSong(String name, Integer length) throws FactoryException;

}