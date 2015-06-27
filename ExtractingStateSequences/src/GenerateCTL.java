import java.util.ArrayList;
import java.util.Iterator;


public class GenerateCTL implements PropertyGenerator {

	@Override
	public ArrayList<Property> generateProperties(EventList eventSeq) {
		
		//Preciso saber o tipo da sequencia de eventos
		String type = eventSeq.getType();
		switch (type) {
		case "DS":
				generatePropDS(eventSeq);
			break;
			default:
				System.out.println("Tipo de sequencia nao e valido");
		}
		return null;
	}

	
	private ArrayList<Property> generatePropDS(EventList el) {
		
		ArrayList<Property> properties = new ArrayList<Property>();
		
		Event first = el.get(0);
		Event last = el.get(el.size()-1);
		
		Property existFirst = new Property();
		existFirst.setType(Property.EXISTENCE);
		existFirst.setRepresentation("AF("+first.getName()+")");
		properties.add(existFirst);
		
		Property existLast = new Property();
		existLast.setType(Property.EXISTENCE);
		existLast.setRepresentation("AF("+last.getName()+")");
		properties.add(existLast);
		
		for (int i = 0; i < el.size()-1; i++) {
			Property precedence = new Property();
			precedence.setType(Property.PRECEDENCE);
			Event Q = first;
			Event R = last;
			Event S = el.get(i);
			Event P = el.get(i+1);
			precedence.setRepresentation("AG("+Q.getName()+" & !"+R.getName()+" -> A[(!"+P.getName()+" | AG(!"+R.getName()+")) W ("+S.getName()+" | "+R.getName()+")])");
			properties.add(precedence);
		}
		
		return properties;
	}
}
