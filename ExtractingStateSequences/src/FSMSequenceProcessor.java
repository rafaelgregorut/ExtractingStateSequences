import java.util.ArrayList;

import mef.basics.EventList;


public abstract class FSMSequenceProcessor {

	protected String rawSequence;
	
	protected ArrayList<EventList> eventSequence;
	
	protected String beginPattern;
	
	FSMSequenceProcessor() {
		eventSequence = new ArrayList<EventList>();
	}
	
	public void setBeginPattern(String pattern) {
		beginPattern = pattern;
	}
	
	public String getRawSequence() {
		return rawSequence;
	}

	
	public void setRawSequence(String s) {
		rawSequence = s;
		
	}

	
	abstract public void processSequence();
	
	public ArrayList<EventList> getEventSequence() {
		return eventSequence;
	}
}
