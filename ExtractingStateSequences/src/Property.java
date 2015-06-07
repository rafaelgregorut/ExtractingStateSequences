
public abstract class Property {

	protected String type;
	
	protected EventList eventsInvolved;
	
	protected String representation;
	
	Property() {
		representation = null;
	}
	
	public void printEventsInvolved() {
		for (int i = 0; i < eventsInvolved.size(); i++)
			System.out.println(eventsInvolved.get(i).getName());
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
}
