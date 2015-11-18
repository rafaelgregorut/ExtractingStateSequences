package ltl.extraction;

import io.handler.Output;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.pack.Main;
import mef.basics.Event;
import mef.basics.EventList;

public class ResponsePropertyExtract {

	private Hashtable<String,Property> propertyHash;
	
	
	Output out;
	
	//key -> primeiro evento
	//value -> eventos de resposta
	private Hashtable<String, String> combinedProps;
	
	public ResponsePropertyExtract() {
		propertyHash = new Hashtable<String,Property>();
		combinedProps = new Hashtable<String, String>();
		out = Main.out;
	}
	
	//S responds to P
	//Globally: [](P -> <>S)
	//Finds the response properties contained in a list of events
	public void extractResponseProperties(EventList listaDeEventos) {
		for (int i = 0; i < listaDeEventos.size()-1; i++) {
			Event P = listaDeEventos.get(i);
			Event S = listaDeEventos.get(i+1);
			Property prop = new Property();
			prop.setRepresentation("[]("+P.getName()+" -> <>"+S.getName()+")");
			prop.setMeaning(S.getName()+" responds to "+P.getName());
			if (!propertyHash.containsKey(prop.getRepresentation()))
				propertyHash.put(prop.getRepresentation(), prop);
		}
	}
	
	//S responds to P
	//Globally: [](P -> <>S)
	//Finds the response properties of a given event contained in a list of events
	public void extractSpecificReponseProperties(EventList listaDeEventos, String eventSpec) {
		for (int i = 0; i < listaDeEventos.size()-1; i++) {
			Event P = listaDeEventos.get(i);
			Event S = listaDeEventos.get(i+1);
			if (P.getName().equals(eventSpec) || S.getName().equals(eventSpec)) {
				Property prop = new Property();
				prop.setRepresentation("[]("+P.getName()+" -> <>"+S.getName()+")");
				prop.setMeaning(S.getName()+" responds to "+P.getName());
				if (!propertyHash.containsKey(prop.getRepresentation()))
					propertyHash.put(prop.getRepresentation(), prop);
				else {
					Property needUpdate = propertyHash.remove(prop.getRepresentation());
					prop.freq += needUpdate.freq;
					propertyHash.put(prop.getRepresentation(), prop);
				}
				
			}
		}
	}
	
	//print all response properties in the property hash
	public void printAllResponseProperties() {
		Collection<Property> allResp = propertyHash.values();
		
		for (Property it : allResp) {
			out.printRowRespGen(it.getMeaning(), it.getRepresentation());
		}
	}
	
	//print all response properties in the property hash for a specific event
	public void printAllResponseProperties(String e) {
		List<Property> allResp = new ArrayList<Property>(propertyHash.values());
		
		Comparator<Property> comparator = new Comparator<Property>() {
		    public int compare(Property c1, Property c2) {
		        return c2.freq - c1.freq;
		    }
		};

		//sort based in the frequence that the property was generated
		Collections.sort(allResp, comparator);
		
		for (Property it : allResp) {
			out.printRowRespSpec(it.getMeaning(), it.getRepresentation());
		}
	}
	
	//combine the properties obtained from mining
	public void combineProperties() {
		
		Enumeration<String> propReps = propertyHash.keys();
		Pattern pattern1 = Pattern.compile("\\[\\]\\((.*?) \\-\\>");
		Pattern pattern2 = Pattern.compile("\\<\\>(.*?)\\)");
		Matcher matcher;
		
		while (propReps.hasMoreElements()) {
			String prop = propReps.nextElement();
			matcher = pattern1.matcher(prop);
			if (matcher.find()) {
				String firstEvent = matcher.group(1);
				matcher = pattern2.matcher(prop);
				if (matcher.find()) {
					String secEvent = matcher.group(1);
					if (!combinedProps.containsKey(firstEvent)) {
						combinedProps.put(firstEvent, secEvent);
					} else {
						String temp = combinedProps.remove(firstEvent);
						temp += " ^ "+secEvent;
						combinedProps.put(firstEvent, temp);
					}
				}
			}
		}
	}
	
	//print the combined properties
	public void printCombinedProperties() {
		Enumeration<String> firstEvents = combinedProps.keys();

		while(firstEvents.hasMoreElements()) {
			String next = firstEvents.nextElement();
			if (!next.equals(""))
				out.printRowRespGen("Combination of properties", "[]("+next+" -> <>("+combinedProps.get(next)+"))");
		}
	}
	
	//combines properties of a specific event
	public void combinePropertiesSpecific(String e) {
		combineProperties();
		Enumeration<String> propReps = propertyHash.keys();
		Pattern pattern1 = Pattern.compile("\\[\\]\\((.*?) \\-\\>");
		Matcher matcher;
		String disjunctionFirst = "";
		
		while (propReps.hasMoreElements()) {
			String next = propReps.nextElement();
			
			matcher = pattern1.matcher(next);
			if (matcher.find()) {
				String disjEvent = matcher.group(1);
				if (!disjEvent.equals(e)) {
					combinedProps.remove(disjEvent);
					if (disjunctionFirst == "") {
						disjunctionFirst = "("+disjEvent;
					} else {
						disjunctionFirst += " v "+disjEvent;
					}
				}
			}
		}
		
		if (disjunctionFirst != "")
			disjunctionFirst += ")";
		
		combinedProps.put(disjunctionFirst, e);
	}
	
	//prints properties of a specific event
	public void printCombinedProperties(String e) {
		Enumeration<String> firstEvents = combinedProps.keys();

		while(firstEvents.hasMoreElements()) {
			String next = firstEvents.nextElement();
			if (!next.equals(""))
				out.printRowRespSpec("Combination of properties", "[]("+next+" -> <>("+combinedProps.get(next)+"))");
		}
	}
}
