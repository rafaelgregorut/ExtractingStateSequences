import java.util.ArrayList;


public class DSSequenceProcessor implements TestSequenceProcessor {

	private String rawSequence;
	
	private ArrayList<EventList> eventSequence;
	
	String beginPattern = " ####### SEQUÊNCIAS DE TESTES FINAL  ###### \n\n";
	
	DSSequenceProcessor() {
		eventSequence = new ArrayList<EventList>();
	}
	
	public String getRawSequence() {
		return rawSequence;
	}

	@Override
	public void setRawSequence(String s) {
		rawSequence = s;
		
	}

	@Override
	public void processSequence() {
		
		int indexFinalSequences = rawSequence.indexOf(beginPattern);
		String finalSequences = rawSequence.substring(indexFinalSequences+beginPattern.length());
		//System.out.println(finalSequences);
		String sequencias[] = finalSequences.split("\n");
		for (int i = 0; i < sequencias.length; i++) {
			System.out.println(sequencias[i]);
			EventList el = new EventList();
			String events[] = sequencias[i].split(" ");
			for (int j = 0; j < events.length; j++) {
				Event e = new Event(events[j],null);
				el.add(e);
			}
			eventSequence.add(el);
		}
	}

	@Override
	public ArrayList<EventList> getEventSequence() {
		// TODO Auto-generated method stub
		return eventSequence;
	}
	
}
