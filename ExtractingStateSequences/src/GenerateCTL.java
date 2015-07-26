import java.util.ArrayList;
import java.util.Iterator;

public class GenerateCTL implements PropertyGenerator {

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
		//System.out.println("first "+first.getName());
		Event last = el.get(el.size()-1);
		//System.out.println("last "+last.getName());
		
		Property existFirst = new Property();
		existFirst.setType(Property.EXISTENCE);
		existFirst.setRepresentation("AF("+first.getName()+")");
		properties.add(existFirst);
		
		Property existLast = new Property();
		existLast.setType(Property.EXISTENCE);
		existLast.setRepresentation("AF("+last.getName()+")");
		properties.add(existLast);
		
		//System.out.println(properties.get(0).getRepresentation()+" "+properties.get(1).getRepresentation());
		
		for (int i = 0; i < el.size()-1; i++) {
			Property precedence = new Property();
			precedence.setType(Property.PRECEDENCE);
			//S precedes P between Q and R
			Event Q = first;
			Event R = last;
			Event S = el.get(i);
			Event P = el.get(i+1);
			precedence.setRepresentation("AG("+Q.getName()+" & !"+R.getName()+" -> A[(!"+P.getName()+" | AG(!"+R.getName()+")) W ("+S.getName()+" | "+R.getName()+")])");
			properties.add(precedence);
		}
		
		return properties;
	}
	
	private ArrayList<Property> generatePropHSwitch(EventList el) {
		ArrayList<Property> properties = new ArrayList<Property>();
			
		Event first = el.get(0);
		//System.out.println("first "+first.getName());
		Event last = el.get(el.size()-1);
		//System.out.println("last "+last.getName());
			
		Property existFirst = new Property();
		existFirst.setType(Property.EXISTENCE);
		existFirst.setRepresentation("AF("+first.getName()+")");
		properties.add(existFirst);
			
		Property existLast = new Property();
		existLast.setType(Property.EXISTENCE);
		existLast.setRepresentation("AF("+last.getName()+")");
		properties.add(existLast);
		
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
				properties.add(precedence);
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
			resp.add(existState);
		}
		return resp;
	}
}
