package eventmanagement.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import eventmanagement.AccountingPosition;
import eventmanagement.BalanceSheet;
import eventmanagement.Band;
import eventmanagement.time.Interval;

public class BalanceSheetImpl implements BalanceSheet {
	private Band band;
	private Interval intervalCovered;
	
	public BalanceSheetImpl(Band band, Interval intervalCovered) {
		super();
		this.band = band;
		this.intervalCovered = intervalCovered;
		this.setLineWidth(80);
	}

	@Override
	public List<AccountingPosition> getPositions() {		
		List<AccountingPosition> ret = new ArrayList<AccountingPosition>(); 
		for(AccountingPosition pos: band.getAccountingPositions()){
			if (intervalCovered.overlaps(pos.getEvent().getInterval())){
				ret.add(pos);
			}
		}
		return ret;
	}

	@Override
	public <T> Integer getFinalBalance(Class<T> eventType) {
		Integer sum = 0;
		for (AccountingPosition pos : getPositions()) {
			if(! eventType.isAssignableFrom(pos.getEvent().getClass())) {
				continue; // exclude Events that are not a subtype of EventType 
			}
			sum += pos.getValue();
		}
		return sum;
	}

	@Override
	public Band getBand() {
		return band;
	}

	@Override
	public Interval getIntervalCovered() {
		return intervalCovered;
	}
	private int lineWidth;
	public int getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	@Override
	public <T> String getReportForEventType(Class<T> eventType) {
		StringBuilder report = new StringBuilder();
		SortedMap<String,StringBuilder> events = new TreeMap<String, StringBuilder>(); 
		
		for (AccountingPosition p : band.getAccountingPositionsByInterval(AccountingPosition.class, 
				getIntervalCovered() )){
			if(!eventType.isAssignableFrom(p.getEvent().getClass())){
				continue; // ignore All event type we are not interested in
			}
			StringBuilder eventBuilder = events.get(p.getEvent().getName());
			if (eventBuilder ==null){
				eventBuilder = new StringBuilder(p.getEvent().getName() +":\n");
				events.put(p.getEvent().getName(),eventBuilder);
			}
			
			
			eventBuilder.append( createAccountingLineString( p.getName(), p.getValue()/100.0));
		}
		
		report.append("\nBalance Sheet for period : ").append(getIntervalCovered()). append("\n");
		for (StringBuilder ch : events.values()){
			report.append(ch.toString());
		}
		report.append(new String(new char[getLineWidth()]).replace('\0', '#') +"\n");
		
		report.append(createAccountingLineString("SUM: ", getFinalBalance(eventType)/100.0));
		return report.toString();
	}
	private String createAccountingLineString(String text, double value){
		
		DecimalFormat format = new DecimalFormat("###.00");
		String valueStr = format.format(value);
		if(text.length() + valueStr.length() + 2 < getLineWidth()){
			valueStr = new String(new char[getLineWidth()- 2 - text.length() - valueStr.length()]).replace('\0', ' ') + valueStr;
		}
		return "  " + text + valueStr + '\n';
		
	}

}
