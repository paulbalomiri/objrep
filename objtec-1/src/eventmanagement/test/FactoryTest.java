package eventmanagement.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import eventmanagement.AccountingPosition;
import eventmanagement.Artist;
import eventmanagement.BalanceSheet;
import eventmanagement.Band;
import eventmanagement.BandMember;
import eventmanagement.Concert;
import eventmanagement.Event;
import eventmanagement.EventSchedule;
import eventmanagement.Factory;
import eventmanagement.FactoryException;
import eventmanagement.Rehearsal;
import eventmanagement.Song;
import eventmanagement.DB.DB;
import eventmanagement.annotations.ConstructorArgumentRequirements;
import eventmanagement.time.Interval;

public class FactoryTest {
	Map<Class<?>, Class<?>> typeMap = new HashMap<Class<?>, Class<?>>();

	public FactoryTest() {
		typeMap.put(eventmanagement.AccountingPosition.class,
				eventmanagement.impl.AccountingPositionImpl.class);
		typeMap.put(eventmanagement.Artist.class,
				eventmanagement.impl.ArtistImpl.class);
		typeMap.put(eventmanagement.BalanceSheet.class,
				eventmanagement.impl.BalanceSheetImpl.class);
		typeMap.put(eventmanagement.Band.class,
				eventmanagement.impl.BandImpl.class);
		typeMap.put(eventmanagement.BandMember.class,
				eventmanagement.impl.BandMemberImpl.class);
		typeMap.put(eventmanagement.Concert.class,
				eventmanagement.impl.ConcertImpl.class);
		// The Event type is not beant to be constructed directly
		// typeMap.put(eventmanagement.Event.class,
		// eventmanagement.impl.EventImpl.class);
		typeMap.put(eventmanagement.EventSchedule.class,
				eventmanagement.impl.EventScheduleImpl.class);
		typeMap.put(eventmanagement.Rehearsal.class,
				eventmanagement.impl.RehearsalImpl.class);
		typeMap.put(eventmanagement.Song.class,
				eventmanagement.impl.SongImpl.class);
		// typeMap.put(eventmanagement..class, eventmanagement.impl..class);

	}

	public static String stringRepetition(String repetition, int times) {
		String subst = new String(new char[repetition.length()]);
		return new String(new char[40 * repetition.length()]).replace(subst,
				repetition);
	}

	private String stringMarker(String text) {
		return stringRepetition("#", 40) + "\n" + text + "\n"
				+ stringRepetition("#", 40);

	}

	public boolean test() {
		boolean ret = true;
		Factory.setDefaultFactory(new Factory(typeMap));
		System.out.println(stringMarker("Factory Test"));
		ret = ret && testFactoryMethodAvailability();
		if (ret) {
			System.out.println(stringMarker("Factory Test SUCCEEDED"));
		} else {
			System.out.println(stringMarker("Factory Test FAILED"));
		}

		System.out.println(stringMarker("Database population test"));
		ret = ret && testPopulateDB();
		if (ret) {
			System.out
					.println(stringMarker("Database population test SUCCEEDED"));
		} else {
			System.out.println(stringMarker("Database population test FAILED"));
		}

		return ret;

	}

	private boolean testPopulateDB() {
		boolean ret = testAddBand();
		ret = ret && testAddSchedule();
		ret = ret && testAddConcert();
		ret = ret && testAddRehearsals();
		ret = ret && testAddAccountingPositions();
		ret = ret && testAddBandMembers();
		ret = ret && testAddSongs();
		ret = ret && testBalanceSheet();
		ret = ret && testRepertoireByYear();
		ret = ret && testSchedule();

		return ret;
	}

	private boolean testSchedule() {
		boolean ret = true;
		Band band = DB.getBandByName("The Who");
		writeSuccess(band.getSchedule().getScheduleReport(Event.class, 
				new Interval(
						generateDate(1969,7),
						generateDate(1969,9)
						)));
		return ret;
	}

	private boolean testRepertoireByYear() {
		boolean ret = true;
		Band band = DB.getBandByName("The Who");
		StringBuilder str = new StringBuilder();
		for (int i=1960;  i<=1980; i++){
			str.append("Year " + i + "\"" + "\n");
			for(Song s: band.getRepertoire(generateDate(i))){
				str.append("  " + s.toString() + "\n");
			}
			
		}
		writeSuccess(str.toString());
		return ret;
	}

	private boolean testAddSongs() {
		boolean ret = true;
		Band band = DB.getBandByName("The Who");
		List<Song> songs = band.getRepertoire();
		Factory factory = new Factory(typeMap);
		Random rand = new Random(0);
		Set<String> names = new HashSet<>();
		try {
			for (String[] s : SongData.songData) {
				if (names.contains(s[1])) {
					continue; // duplicate name
				} else {
					names.add(s[1]);
				}
				int len = 0;
				len = Integer.parseInt(s[0].substring(0, s[0].indexOf(':'))) * 60;
				len += Integer.parseInt(s[0].substring(s[0].indexOf(':') + 1));
				String name = s[1];
				
				// Playable interval is randomized between 2 and 10 years in
				// length
				Interval playInterval = new Interval(
						generateDate(Integer.parseInt(s[2])),
						generateDate(Integer.parseInt(s[2]) + rand.nextInt(8)
								+ 2));

				Song song = factory.createSong(name, len);
				song.setPlayableInterval(playInterval);
				
				songs.add(song);
			}
			writeSuccess("Added " + songs.size() + " The Who Songs to database");
		} catch (FactoryException e) {
			ret = false;
			writeError("Could not instantiate Song (Factory Error)");
		}

		return ret;
	}

	private boolean testAddBandMembers() {
		boolean ret = true;
		Factory factory = new Factory(typeMap);
		Band band = DB.getBandByName("The Who");
		List<BandMember> members = band.getMembers();
		List<Artist> artists = DB.getArtists();
		try {
			for (int i = 0; i < 6; i++) {
				Artist artist = null;
				BandMember member = null;
				switch (i) {
				case 0:
					artist = factory.createArtist("John Entwistle");
					artist.setInstrument("Guitar");
					artist.setPhone("+441234567890");
					member = factory.createBandMember(artist, new Interval(
							generateDate(1944), generateDate(2002)));
					break;// TODO Auto-generated method stub
				case 1:
					artist = factory.createArtist("Doug Sandom");
					artist.setInstrument("Drums");
					artist.setPhone("+441234567891");
					member = factory.createBandMember(artist, new Interval(
							generateDate(1962), generateDate(1964)));
					break;
				case 2:
					artist = factory.createArtist("Keith Moon");
					artist.setInstrument("Drums");
					artist.setPhone("+441234567892");
					member = factory.createBandMember(artist, new Interval(
							generateDate(1946), generateDate(1978)));
					break;
				case 3:
					artist = factory.createArtist("Kenney Jones");
					artist.setInstrument("Drum");
					artist.setPhone("+441234567893");
					member = factory.createBandMember(artist, new Interval(
							generateDate(1965), generateDate(1982)));
					break;
				case 4:
					artist = factory.createArtist("Roger Daltrey");
					artist.setInstrument("Vocals");
					artist.setPhone("+441234567894");
					member = factory.createBandMember(artist, new Interval(
							generateDate(1965), null));
					break;
				case 5:
					artist = factory.createArtist("Pete Townshend");
					artist.setInstrument("Guitar");
					artist.setPhone("+441234567895");
					member = factory.createBandMember(artist, new Interval(
							generateDate(1962), null));
					break;

				default:
					break;
				}
				if (artist != null) {
					artists.add(artist);
				}
				if (member != null) {
					members.add(member);
				}
			}
			writeSuccess("Added 6 Bandmembers 2 active and 4 inactive");
		} catch (FactoryException e) {
			writeError("Could not create Artists");
			ret = false;
		}

		return ret;
	}

	private boolean testBalanceSheet() {
		boolean ret = true;
		Factory factory = new Factory(typeMap);
		Band band = DB.getBandByName("The Who");

		try {
			BalanceSheet sheet = factory.createBalanceSheet(band, new Interval(
					generateDate(1969, 8), generateDate(1969, 9)));
			writeSuccess("Created monthly Balance Sheet for August 1969");

			writeSuccess("Balance Sheet for all Event Types");
			writeSuccess(sheet.getReportForEventType(Event.class));

			writeSuccess("Balance Sheet for Concerts Only");
			writeSuccess(sheet.getReportForEventType(Concert.class));

			writeSuccess("Balance Sheet for Rehearsals only");
			writeSuccess(sheet.getReportForEventType(Rehearsal.class));

		} catch (FactoryException e) {
			writeError("Could not Create Balancesheet");
		}

		return ret;
	}

	private boolean testAddRehearsals() {
		boolean ret = true;
		Factory factory = new Factory(typeMap);
		EventSchedule schedule = DB.getBandByName("The Who").getSchedule();
		Interval rehearsalInterval = new Interval(generateDate(1969, 1, 1, 17),
				generateDate(1969, 1, 1, 19));
		Date endDate = generateDate(1970);
		int weekduration = 1000 * 60 * 60 * 24 * 7;
		int i = 0;
		try {
			do {
				Rehearsal r;
				r = factory.createRehearsal("Rehearsal Week #" + (i + 1));
				r.setInterval(rehearsalInterval);
				schedule.getEvents().add(r);
				i++;
				rehearsalInterval = new Interval(new Date(rehearsalInterval
						.getStart().getTime() + weekduration), new Date(
						rehearsalInterval.getEnd().getTime() + weekduration));
			} while (rehearsalInterval.getStart().compareTo(endDate) < 0);
			writeSuccess("Scheduled " + i
					+ " weekly rehearsals throughout the year 1969");
		} catch (FactoryException e) {
			writeError("Could not create Rehearsal Objects");
			ret = false;
		}
		List<Rehearsal> result = schedule
				.getEventsByInterval(Rehearsal.class, new Interval(
						generateDate(1969, 1, 1), generateDate(1969, 2, 1)));
		if (result.size() != 5) {
			boolean first = true;
			String errStr = "";
			for (Rehearsal r : result) {
				if (first) {
					first = false;
				} else {
					errStr += ", ";
				}
				errStr += r.getName();
			}
			writeError("Expected 5 rehearsals for January  but got "
					+ result.size() + ": " + errStr);
			ret = false;
		} else {
			writeSuccess("Got 5 results in query for Rehearsals in january 1969: ");
			for (Rehearsal r : result) {
				writeSuccess(r.toString());
			}
		}
		result = schedule.getEventsByInterval(Rehearsal.class, new Interval(
				generateDate(1969, 1, 1), generateDate(1970)));
		if (result.size() != 52) {
			writeError("got "
					+ result.size()
					+ "weekly Rehearsals while expecting 52 for the year of 1969");
			ret = false;
		} else {
			writeSuccess("Expected and got 52 results in query for rehearsals for year 1969");
		}
		return ret;
	}

	private boolean testAddAccountingPositions() {
		boolean ret = true;
		Factory factory = new Factory(typeMap);
		Band band = DB.getBandByName("The Who");
		Concert c = band.getSchedule()
				.getEventsByName(Concert.class, "Woodstock").get(0);
		try {
			AccountingPosition acc = factory.createAccountingPosition("Income",
					c, "Performance fee", 10000000);
			band.getAccountingPositions().add(acc);
			acc = factory.createAccountingPosition("Expense", c, "Stage rent",
					-5000);
			band.getAccountingPositions().add(acc);
			c = band.getSchedule()
					.getEventsByName(Concert.class, "Isle Of Wight Festival")
					.get(0);
		} catch (FactoryException e) {
			writeError("Could not create Accounting Positions for Concerts");
			ret = false;
		}
		// Add Expenses for Rehearsal objects at randomized costs between 5000
		// and 6000
		List<Rehearsal> rehearsals = band.getSchedule().getEventsByInterval(
				Rehearsal.class,
				new Interval(generateDate(1969), generateDate(1970)));
		try {
			Random rand = new Random();
			for (Rehearsal r : rehearsals) {
				AccountingPosition acc = factory.createAccountingPosition(
						"Expense", r, "Stage Rent", -5000);
				band.getAccountingPositions().add(acc);
				acc = factory.createAccountingPosition("Income", r,
						"Entrance fee share", rand.nextInt(10000));
				band.getAccountingPositions().add(acc);
			}
		} catch (FactoryException e) {
			writeError("Could not create Accounting Positions for Rehearsals");
			ret = false;
		}

		return ret;
	}

	private Date generateDate(int... params) {
		if (params.length > 6)
			throw new IllegalArgumentException(
					"generateDate only accepts 6 parameters for: YMDhms");
		Calendar date = new GregorianCalendar();
		int[] completeparams = new int[6];
		// completeparams[1] = 1; // Month index for 1st

		completeparams[1] = 1; // Month index for January

		for (int i = 0; i < params.length; i++) {
			completeparams[i] = params[i];
		}
		for (int i = 0; i < completeparams.length; i++) {
			switch (i) {
			case 0:
				date.set(Calendar.YEAR, completeparams[i]);
				break;
			case 1:
				date.set(Calendar.MONTH, completeparams[i] - 1);
				break;
			case 2:
				date.set(Calendar.DAY_OF_MONTH, completeparams[i] - 1);
				break;
			case 3:
				date.set(Calendar.HOUR, completeparams[i]);
				break;
			case 4:
				date.set(Calendar.MINUTE, completeparams[i]);
				break;
			case 5:
				date.set(Calendar.SECOND, completeparams[i]);
				break;
			default:
				break;
			}
		}
		return (Date) date.getTime().clone();

	}

	private boolean testAddConcert() {
		boolean ret = true;
		Factory factory = new Factory(typeMap);
		try {
			Concert concert = factory.createConcert("Woodstock");
			concert.setInterval(new Interval(generateDate(1969, 8, 15, 17, 7),
					generateDate(1969, 8, 18, 11, 10)));
			EventSchedule schedule = DB.getBandByName("The Who").getSchedule();
			schedule.getEvents().add(concert);
			concert = factory.createConcert("Isle Of Wight Festival");
			concert.setInterval(new Interval(generateDate(1969, 8, 30, 17),
					generateDate(1969, 8, 30, 19)));
			schedule.getEvents().add(concert);
			writeSuccess("Added 2 Concerts");
			Interval queryI = new Interval(generateDate(1969),
					generateDate(1970));
			List<Event> query = schedule.getEventsByInterval(Event.class,
					queryI);
			if (query == null || query.size() != 2) {
				writeError("Query by time frame for the two scheduled concerts did not return 2 Events");
				ret = false;
			} else {
				String successReport = "Found the following Entries: ";
				for (String name : new String[] { "Woodstock",
						"Isle Of Wight Festival" }) {
					boolean found = false;
					for (Event e : query) {
						if (e.getName().equals(name)) {
							found = true;
							successReport += e + " ";
							break;
						}
					}
					if (!found) {
						writeError("Could not find previously inserted concert \""
								+ name + "\" in query by time frame");
						ret = false;
					}
				}
				if (ret) {
					writeSuccess(successReport + ".");
				}
			}
			query = schedule.getEventsByInterval(Event.class, new Interval(
					generateDate(1969), generateDate(1969, 8, 20)));
			if (query.size() != 1) {
				writeError("got " + query.size()
						+ " results in resultset while expecting 1");
				ret = false;
			} else if (query.get(0).getName() != "Woodstock") {
				writeError("Expected Woodstock event, but got " + query.get(0)
						+ " instead");
				ret = false;
			} else {
				writeSuccess("Retrieved Woodstock as single result by it's timeframe");
				query = schedule.getEventsByInterval(Event.class, new Interval(
						generateDate(1969, 8, 20), generateDate(1970)));
				if (query.size() != 1) {
					writeError("got " + query.size()
							+ " results in resultset while expecting 1");
					ret = false;
				} else if (query.get(0).getName() != "Isle Of Wight Festival") {
					writeError("Expected Isle Of Wight Festival event, but got "
							+ query.get(0) + " instead");
					ret = false;
				} else {
					writeSuccess("Retrieved Isle Of Wight Festival as single result by it's timeframe");
				}
			}
		} catch (FactoryException e) {
			writeError("Failed to create concert");
		}
		return ret;
	}

	private boolean testAddSchedule() {
		boolean ret = true;
		Factory factory = new Factory(typeMap);
		EventSchedule schedule = null;
		try {
			Band band = DB.getBandByName("The Who");
			schedule = factory.createEventSchedule(band);
			if (schedule == null) {
				writeError("Band Creation did not automatically set s new Eventschedule");
			}
			band.setSchedule(schedule);
		} catch (FactoryException e) {
			writeError("Could not instantiate or set a new schedule for Band \"The Who\"");
			ret = false;
		}
		if (ret) {
			writeSuccess("Added a Schedule for Band The Who");
		}
		return ret;
	}

	private boolean testAddBand() {
		boolean ret = true;
		Factory factory;
		try {
			factory = Factory.getDefaultFactory();
		} catch (FactoryException e1) {
			writeError("Default factory has not been set!");
			return false;
		}

		try {
			DB.getBands().add(factory.createBand("The Who"));
			writeSuccess("Created Band and inserted into db");
			if (DB.getBands().size() != 1) {
				writeError("Bands should contain 1 entry but contains "
						+ DB.getBands().size());
				ret = false;
			}
			Band retrievedBand = DB.getBandByName("The Who");
			if (retrievedBand == null
					|| !retrievedBand.getName().equals("The Who")) {
				writeError("Could not retrieve previously entered Band \"The Who\" ");
				ret = false;
			}
		} catch (FactoryException e) {
			writeError("Could not Instantiate, insert or reread Band from DB");
			ret = false;
		}
		if (ret) {
			writeSuccess("Successfully added and retrieved Band The Who");
		}

		return ret;
	}

	private boolean testFactoryMethodAvailability() {
		boolean ret = true;
		for (Class<?> c : typeMap.keySet()) {
			ConstructorArgumentRequirements req = c
					.getAnnotation(ConstructorArgumentRequirements.class);
			if (req == null) {
				writeError("Missing "
						+ simpleName(ConstructorArgumentRequirements.class)
						+ " on interface " + simpleName(c));
				ret = false;
			} else {
				Class<?>[] constructorArgs = req.value();
				String creatorFunctionName = "create" + simpleName(c);
				try {
					Factory.class.getMethod(creatorFunctionName,
							constructorArgs);
					writeSuccess("Creator method found: "
							+ methodSignatureString(Factory.class,
									creatorFunctionName, constructorArgs));
				} catch (NoSuchMethodException | SecurityException e) {
					String errstr = "Could not locate creator method "
							+ methodSignatureString(Factory.class,
									creatorFunctionName, constructorArgs);

					writeError(errstr);
					ret = false;
				}
				Class<?> implClass = typeMap.get(c);
				if (!c.isAssignableFrom(implClass)) {
					writeError(simpleName(implClass)
							+ "is not an Implementation of " + simpleName(c));
				}
				try {
					implClass.getConstructor(constructorArgs);
					writeSuccess("Constructor found: "
							+ methodSignatureString(implClass,
									simpleName(implClass), constructorArgs));
				} catch (NoSuchMethodException | SecurityException e) {
					writeError("Cannot find any constructor with Signature: "
							+ methodSignatureString(implClass,
									simpleName(implClass), constructorArgs));
					ret = false;
				}
			}
		}

		return ret;
	}

	private String methodSignatureString(Class<?> clazz, String methodName,
			Class<?>[] expectedParams) {
		String ret = simpleName(clazz) + "." + methodName + "(";
		boolean first = true;
		for (Class<?> param : expectedParams) {
			if (first) {
				first = false;
			} else {
				ret += ", ";
			}
			ret += simpleName(param);
		}
		ret += ")";
		return ret;
	}

	private String simpleName(Class<?> clazz) {
		String name = clazz.getName();
		for (int i = name.length() - 1; i >= 0; i--) {
			if (name.charAt(i) == '.') {
				return name.substring(i + 1);
			}
		}
		return name;
	}

	private void writeError(String string) {
		System.err.println("ERROR: " + string);
	}

	private void writeSuccess(String string) {
		System.out.println("SUCCESS: " + string);
	}
}
