
public class Property {

	protected String type;
	
	protected EventList eventsInvolved;
	
	protected String representation;
	
	public final static String EXISTENCE = "EXISTENCE";

	public final static String PRECEDENCE = "PRECEDENCE";

	Property() {
		representation = null;
		eventsInvolved = null;
	}
	
	public void printEventsInvolved() {
		if (eventsInvolved != null) {
			for (int i = 0; i < eventsInvolved.size(); i++)
				System.out.println(eventsInvolved.get(i).getName());
		} else
			System.out.println("Lista de eventos involvidos esta vazia");
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EventList getEventsInvolved() {
		return eventsInvolved;
	}

	public void setEventsInvolved(EventList eventsInvolved) {
		this.eventsInvolved = eventsInvolved;
	}

	public String getRepresentation() {
		return representation;
	}
	
	public void setRepresentation(String prop) {
		this.representation = prop;
	}
}
