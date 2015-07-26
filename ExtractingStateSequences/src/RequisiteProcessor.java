import java.util.ArrayList;
import java.util.Hashtable;


public class RequisiteProcessor {

	Hashtable<String,State> stateHash;
	
	private String rawStates;
	
	private ArrayList<State> stateList = null;
	
	RequisiteProcessor() {
		super();
		stateHash = new Hashtable<String,State>();
	}
	
	public ArrayList<State> processStates() {
		
		String finalStates = rawStates;
		finalStates = finalStates.replaceAll("[( ,\\.\\n)]", " ");
		String estado[] = finalStates.split(" +");
		for (int i = 1; i < estado.length; i++) {
			if(estado[i] != "" && !(stateHash.containsKey(estado[i]))) {
				//System.out.println(estado[i]+";");
				State st = new State(estado[i]);
				stateHash.put(estado[i], st);
			}
		}
		stateList = new ArrayList<State>(stateHash.values());
		return stateList;
	}
	
	public void setRawStates(String s) {
		rawStates = s;
	}
	
	public ArrayList<State> getStateList() {
		return stateList;
	}
	
}
