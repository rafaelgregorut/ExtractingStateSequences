import java.util.ArrayList;


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
				System.out.println("Tipo de sequência não é válido");
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
		
		return properties;
	}
}
