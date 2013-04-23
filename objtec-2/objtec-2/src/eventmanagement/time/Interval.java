package eventmanagement.time;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Holds a start  and end date and represents the time in between.
 * The timespan includes time from the start time (inclusive) and excludes the end time, and time after.  
 * @author paul
 *
 */
public class Interval implements Comparable<Interval>  {
	private Date start;
	public Interval(Date from, Date to) {
		start = from;
		end=to;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	private Date end;
	
	@Override
	public int compareTo(Interval o) {
		int st = start.compareTo(o.start);
		if( st ==0){
			return end.compareTo(o.end);
		} else {
			return st;
		}
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Interval ) {
			return this.compareTo((Interval) obj) == 0;
		}
		return false;
	}
	public boolean includes(Date d){
		if ((start == null || start.compareTo( d) <=0)
				&&(end==null || end.compareTo(d) >0) ){
			return true;
		}
		else return false;
	}
	public boolean contains(Interval i){
		if(this.start.compareTo(i.start)  <0 && this.end.compareTo(i.end) >=0) {
			return true;
		}
		return false;
	}
	public boolean overlaps(Interval i){
		if(this.includes(i.start) || this.includes(end) || 
				this.contains(i) || i.contains(this) ) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * difference between start and end time in miliseconds	 
	 **/
	public long len(){
		return end.getTime() -start.getTime();
	}
	@Override
	public String toString() {
		SimpleDateFormat df =  new SimpleDateFormat(
	            "d. MMMM yyyy HH:mm");
		return df.format(start) + "-" + df.format(end);
	}
}
