import java.util.ArrayList;
import java.util.Iterator;


public class EventList {

	private ArrayList<Event> list;
	
	private String type;
	
	EventList() {
		list = new ArrayList<Event>();
		type = "";
	}
	
	public void add(Event e) {
		list.add(e);
	}
	
	public Event get(int index) {
		return list.get(index);
	}
	
	public Iterator<Event> iterator() {
		return list.iterator();
	}
	
	public int size() {
		return list.size();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
