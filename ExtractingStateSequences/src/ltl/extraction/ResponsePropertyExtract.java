package ltl.extraction;

import java.util.Enumeration;
import java.util.Hashtable;

import mef.basics.Event;
import mef.basics.EventList;

public class ResponsePropertyExtract {

	Hashtable<String,Property> propertyHash;
	
	public ResponsePropertyExtract() {
		propertyHash = new Hashtable<String,Property>();
	}
	
	//S responds to P
	//Globally: [](P -> <>S)
	
	public void extractResponseProperties(EventList listaDeEventos) {
		//listaDeEventos.print();

		for (int i = 0; i < listaDeEventos.size()-1; i++) {
			Event P = listaDeEventos.get(i);
			Event S = listaDeEventos.get(i+1);
			Property prop = new Property();
			prop.setRepresentation("[]("+P.getName()+" -> <>"+S.getName()+")");
			if (!propertyHash.containsKey(prop.getRepresentation()))
				propertyHash.put(prop.getRepresentation(), prop);
			//System.out.println(prop.getRepresentation());
		}
	}
	
	public void printAllResponseProperties() {
		Enumeration<String> allRep = propertyHash.keys();
		while(allRep.hasMoreElements())
			System.out.println(allRep.nextElement());
	}
}
