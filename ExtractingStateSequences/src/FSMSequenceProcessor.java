import java.util.ArrayList;


public abstract class FSMSequenceProcessor implements TestSequenceProcessor {

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

	@Override
	public void setRawSequence(String s) {
		rawSequence = s;
		
	}

	@Override
	abstract public void processSequence();
	
	public ArrayList<EventList> getEventSequence() {
		return eventSequence;
	}
}
