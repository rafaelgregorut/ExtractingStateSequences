import java.util.ArrayList;


public interface TestSequenceProcessor {

	public String getRawSequence();
	
	public void setRawSequence(String s);
	
	public void processSequence();
	
	public ArrayList<EventList> getEventSequence();
	
}
