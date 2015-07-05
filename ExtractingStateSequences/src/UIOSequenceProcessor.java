public class UIOSequenceProcessor extends FSMSequenceProcessor {
	
	UIOSequenceProcessor() {
		super();
		setBeginPattern(" ####### FINAL TEST SEQUENCE  ###### \n\n");
	}
	
	@Override
	public void processSequence() {
		
		int indexFinalSequences = rawSequence.indexOf(beginPattern);
		String finalSequences = rawSequence.substring(indexFinalSequences+beginPattern.length());
		//System.out.println(finalSequences);
		String sequencias[] = finalSequences.split("\n");
		for (int i = 0; i < sequencias.length; i++) {
			//System.out.println(sequencias[i]);
			EventList el = new EventList();
			String events[] = sequencias[i].split(" ");
			for (int j = 0; j < events.length; j++) { 
				Event e = new Event(events[j],null);
				el.add(e);
			}
			el.setType("UIO");
			eventSequence.add(el);
		}
	}
	
}
