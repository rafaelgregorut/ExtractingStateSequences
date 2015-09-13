package ltl.extraction;

import java.util.Collection;
import java.util.Hashtable;

import mef.basics.Event;
import mef.basics.EventList;

public class ResponsePropertyExtract {

	private Hashtable<String,Property> propertyHash;
	
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
			prop.setMeaning(S.getName()+" responds to "+P.getName());
			if (!propertyHash.containsKey(prop.getRepresentation()))
				propertyHash.put(prop.getRepresentation(), prop);
			//System.out.println(prop.getRepresentation());
		}
	}
	
	public void printAllResponseProperties() {
		Collection<Property> allResp = propertyHash.values();
		for (Property it : allResp) {
			System.out.println(it.getMeaning()+":");
			System.out.println(it.getRepresentation());
		}
		System.out.println("TOTAL DE PROPRIEDADES: "+allResp.size());
	}
}
