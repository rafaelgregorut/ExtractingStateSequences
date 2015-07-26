public class DSSequenceProcessor extends FSMSequenceProcessor {
	
	DSSequenceProcessor() {
		super();
		setBeginPattern(" ####### SEQUÊNCIAS DE TESTES FINAL  ###### \n\n");
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
			el.setType("DS");
			eventSequence.add(el);
		}
	}

}
