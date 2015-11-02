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
	
	//chave -> primeiro evento
	//valor -> eventos de resposta
	private Hashtable<String, String> combinedProps;
	
	public ResponsePropertyExtract() {
		propertyHash = new Hashtable<String,Property>();
		combinedProps = new Hashtable<String, String>();
		out = Main.out;
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
					//System.out.println("Entrei aqui de novo "+prop.getRepresentation());
					propertyHash.put(prop.getRepresentation(), prop);
				}
				
			}
		}
	}
	
	public void printAllResponseProperties() {
		Collection<Property> allResp = propertyHash.values();
		
		for (Property it : allResp) {
			out.println(it.getMeaning()+":");
			out.println(it.getRepresentation());
		}
		out.println("TOTAL DE PROPRIEDADES: "+allResp.size());
	}
	
	public void printAllResponseProperties(String e) {
		List<Property> allResp = new ArrayList<Property>(propertyHash.values());
		
		Comparator<Property> comparator = new Comparator<Property>() {
		    public int compare(Property c1, Property c2) {
		        return c2.freq - c1.freq;
		    }
		};

		Collections.sort(allResp, comparator);
		
		for (Property it : allResp) {
			out.println(it.getMeaning()+":");
			out.println(it.getRepresentation()+" #"+it.freq);
		}
		out.println("TOTAL DE PROPRIEDADES: "+allResp.size());
	}
	
	public void combineProperties() {
		
		Enumeration<String> propReps = propertyHash.keys();
		Pattern pattern1 = Pattern.compile("\\[\\]\\((.*?) \\-\\>");
		Pattern pattern2 = Pattern.compile("\\<\\>(.*?)\\)");
		Matcher matcher;
		
		while (propReps.hasMoreElements()) {
			String prop = propReps.nextElement();
			//System.out.println(prop);
			matcher = pattern1.matcher(prop);
			if (matcher.find()) {
				String firstEvent = matcher.group(1);
				//System.out.println(firstEvent);
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
	
	public void printCombinedProperties() {
		Enumeration<String> firstEvents = combinedProps.keys();
		Matcher matcher;

		while(firstEvents.hasMoreElements()) {
			String next = firstEvents.nextElement();
			if (!next.equals(""))
				out.println("[]("+next+" -> <>("+combinedProps.get(next)+"))");
		}
	}
	
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
}
