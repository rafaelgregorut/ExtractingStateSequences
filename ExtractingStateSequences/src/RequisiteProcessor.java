import java.util.ArrayList;
import java.util.Hashtable;

import mef.basics.State;


public class RequisiteProcessor {

	Hashtable<String,State> stateHash;
	
	private String rawStates;
	
	private ArrayList<State> stateList = null;
	
	RequisiteProcessor() {
		super();
		stateList = new ArrayList<State>();
	}
	
	public ArrayList<State> processStates() {
		
		String finalStates = rawStates;
		finalStates = finalStates.replaceAll("[( ,\\.\\n)]", " ");
		String estado[] = finalStates.split(" +");
		for (int i = 1; i < estado.length; i++) {
			if(estado[i] != "") {
				State st = new State(estado[i]);
				stateList.add(st);
			}
		}
		return stateList;
	}
	
	public void setRawStates(String s) {
		rawStates = s;
	}
	
	public ArrayList<State> getStateList() {
		return stateList;
	}
	
}
