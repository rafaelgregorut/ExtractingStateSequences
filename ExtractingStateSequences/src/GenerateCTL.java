import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class GenerateCTL implements PropertyGenerator {
	
	Hashtable<String,Property> propertyHash;
	
	GenerateCTL() {
		propertyHash = new Hashtable<String,Property>();
	}
	
	@Override
	public ArrayList<Property> generateProperties(EventList eventSeq) {
		
		//Preciso saber o tipo da sequencia de eventos
		String type = eventSeq.getType();
		if (type == "DS_UIO") {
			return generatePropDS_UIO(eventSeq);
		} else if (type == "HSwitch") {
			return generatePropHSwitch(eventSeq);
		} else {
			System.out.println("Tipo de sequencia nao e valido");
		}
		return null;
	}
	
	private ArrayList<Property> generatePropDS_UIO(EventList el) {
		
		ArrayList<Property> properties = new ArrayList<Property>();
		
		Event first = el.get(0);
		Event last = el.get(el.size()-1);
		
		Property existFirst = new Property();
		existFirst.setType(Property.EXISTENCE);
		existFirst.setRepresentation("AF("+first.getName()+")");
		if (!propertyHash.containsKey(existFirst.getRepresentation())) {
			properties.add(existFirst);
			propertyHash.put(existFirst.getRepresentation(), existFirst);
		}
		
		Property existLast = new Property();
		existLast.setType(Property.EXISTENCE);
		existLast.setRepresentation("AF("+last.getName()+")");
		if (!propertyHash.containsKey(existLast.getRepresentation())) {
			properties.add(existLast);
			propertyHash.put(existLast.getRepresentation(), existLast);

		}
				
		for (int i = 0; i < el.size()-1; i++) {
			Property precedence = new Property();
			precedence.setType(Property.PRECEDENCE);
			//S precedes P between Q and R
			Event Q = first;
			Event R = last;
			Event S = el.get(i);
			Event P = el.get(i+1);
			precedence.setRepresentation("AG("+Q.getName()+" & !"+R.getName()+" -> A[(!"+P.getName()+" | AG(!"+R.getName()+")) W ("+S.getName()+" | "+R.getName()+")])");
			if (!propertyHash.containsKey(precedence.getRepresentation())) {
				properties.add(precedence);
				propertyHash.put(precedence.getRepresentation(), precedence);
			}
		}
		return properties;
	}
	
	private ArrayList<Property> generatePropHSwitch(EventList el) {
		ArrayList<Property> properties = new ArrayList<Property>();
			
		Event first = el.get(0);
		Event last = el.get(el.size()-1);
			
		Property existFirst = new Property();
		existFirst.setType(Property.EXISTENCE);
		existFirst.setRepresentation("AF("+first.getName()+")");
		if (!propertyHash.containsKey(existFirst.getRepresentation())) {
			properties.add(existFirst);
			propertyHash.put(existFirst.getRepresentation(), existFirst);
		}
			
		Property existLast = new Property();
		existLast.setType(Property.EXISTENCE);
		existLast.setRepresentation("AF("+last.getName()+")");
		if (!propertyHash.containsKey(existLast.getRepresentation())) {
			properties.add(existLast);
			propertyHash.put(existLast.getRepresentation(), existLast);
		}
		
		//Nesse tipo de sequencia pode ver o output dos eventos
		for (int i = 0; i < el.size(); i++) {
			if (el.get(i).getOutput() != null) {
				Property precedence = new Property();
				precedence.setType(Property.PRECEDENCE);
				//S precedes P between Q and R
				Event Q = first;
				Event R = last;
				Event S = el.get(i);
				String P_name = el.get(i).getOutput();
				precedence.setRepresentation("AG("+Q.getName()+" & !"+R.getName()+" -> A[(!"+P_name+" | AG(!"+R.getName()+")) W ("+S.getName()+" | "+R.getName()+")])");
				if (!propertyHash.containsKey(precedence.getRepresentation())) {
					properties.add(precedence);
					propertyHash.put(precedence.getRepresentation(), precedence);
				}
			}
		}
		return properties;
	}
	
	public ArrayList<Property> generateProperties(ArrayList<State> stateList) {
		ArrayList<Property> resp = new ArrayList<Property>();
		for (Iterator<State> i = stateList.iterator(); i.hasNext();) {
			Property existState = new Property();
			existState.setType(Property.EXISTENCE);
			existState.setRepresentation("AF("+i.next().getName()+")");
			if (!propertyHash.containsKey(existState.getRepresentation())) {
				resp.add(existState);
				propertyHash.put(existState.getRepresentation(), existState);
			}
			
		}
		return resp;
	}
}
