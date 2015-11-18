package ltl.extraction;

import io.handler.Output;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.pack.Main;
import mef.basics.Event;
import mef.basics.EventList;

public class ExistencePropertyExtract {
	
	Output out;
	
	private Hashtable<String,Property> propertyHash;
	
	String combinedProps;

	public ExistencePropertyExtract() {
		propertyHash = new Hashtable<String,Property>();
		combinedProps = "";
		out = Main.out;
	}

	//Create the existence properties, given a list of events that occur in all test paths
	public void extractExistenceProperties(EventList listaDeEventos) {
		for (int i = 0; i < listaDeEventos.size(); i++) {
			Event P = listaDeEventos.get(i);
			Property prop = new Property();
			prop.setRepresentation("<>("+P.getName()+")");
			prop.setMeaning(P.getName()+" must occur");
			if (!propertyHash.containsKey(prop.getRepresentation()))
				propertyHash.put(prop.getRepresentation(), prop);
		}
	}
	
	//print all existence properties in the hash
	public void printAllExistenceProperties() {
		Enumeration<String> allRep = propertyHash.keys();
		while(allRep.hasMoreElements()) {
			String representationKey = allRep.nextElement();
			out.printRowExistGen(propertyHash.get(representationKey).meaning,representationKey);
		}
	}

	//combine the existence propeties in a single one
	public void combineProperties() {
		Enumeration<String> propReps = propertyHash.keys();
		Pattern pattern = Pattern.compile("\\<\\>\\((.*?)\\)");
		Matcher matcher;
		
		if (propReps.hasMoreElements()) {
			String prop = propReps.nextElement();
			matcher = pattern.matcher(prop);
			if (matcher.find()) {
				combinedProps = "<>("+matcher.group(1);
			}
		}

		while (propReps.hasMoreElements()) {
			String prop = propReps.nextElement();
			matcher = pattern.matcher(prop);
			if (matcher.find()) {
				combinedProps += " ^ "+matcher.group(1);
			}
		}
		
		if (combinedProps != "")
			combinedProps += ")";
	}
	
	//print the combined properties
	public void printCombinedProperties() {
		if(!combinedProps.equals(""))
			out.printRowExistGen("Combination of properties", combinedProps);
	}
}
