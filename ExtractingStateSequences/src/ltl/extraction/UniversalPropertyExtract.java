package ltl.extraction;

import java.util.Enumeration;
import java.util.Hashtable;

import mef.basics.Event;
import mef.basics.EventList;

public class UniversalPropertyExtract {
	
	private Hashtable<String,Property> propertyHash;
	
	public UniversalPropertyExtract() {
		propertyHash = new Hashtable<String,Property>();
	}

	public void extractUniversalProperties(EventList listaDeEventos) {
		//listaDeEventos.print();

		for (int i = 0; i < listaDeEventos.size(); i++) {
			Event P = listaDeEventos.get(i);
			Property prop = new Property();
			prop.setRepresentation("[]("+P.getName()+")");
			if (!propertyHash.containsKey(prop.getRepresentation()))
				propertyHash.put(prop.getRepresentation(), prop);
			//System.out.println(prop.getRepresentation());
		}
	}
	
	public void printAllUniversalProperties() {
		Enumeration<String> allRep = propertyHash.keys();
		while(allRep.hasMoreElements())
			System.out.println(allRep.nextElement());
	}


}
