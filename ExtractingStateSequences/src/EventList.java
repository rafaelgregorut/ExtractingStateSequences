import java.util.ArrayList;
import java.util.Iterator;


public class EventList {

	private ArrayList<Event> list;
	
	EventList() {
		list = new ArrayList<Event>();
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
}
