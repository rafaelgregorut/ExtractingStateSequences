import java.util.ArrayList;


public abstract class PropertyDefiner {

	protected ArrayList<EventList> eventSequence;
	
	protected Property property;
	
	PropertyDefiner(ArrayList<EventList> seq) {
		eventSequence = seq;
	}
	
	abstract protected void defineProperty();
}
