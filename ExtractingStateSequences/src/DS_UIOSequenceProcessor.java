public class DS_UIOSequenceProcessor extends FSMSequenceProcessor {
	
	DS_UIOSequenceProcessor() {
		super();
	}

	@Override
	public void processSequence() {

		String finalSequences = rawSequence;
		String sequencias[] = finalSequences.split("\n");
		for (int i = 0; i < sequencias.length; i++) {
			EventList el = new EventList();
			String events[] = sequencias[i].split(" ");
			for (int j = 0; j < events.length; j++) {
				Event e = new Event(events[j],null);
				el.add(e);
			}
			el.setType("DS_UIO");
			eventSequence.add(el);
		}
	}

}
