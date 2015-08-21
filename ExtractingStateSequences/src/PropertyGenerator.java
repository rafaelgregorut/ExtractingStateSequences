import java.util.ArrayList;

import ltl.extraction.Property;
import mef.basics.EventList;

public interface PropertyGenerator {
	
	public ArrayList<Property> generateProperties(EventList el);
}
