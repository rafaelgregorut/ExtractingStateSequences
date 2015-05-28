public class HSwitchCoverSequenceProcessor extends FSMSequenceProcessor {

	HSwitchCoverSequenceProcessor() {
		super();
		setBeginPattern(" ###### SEQUENCE H-SWITCH COVER ###### \n\n");
	}
	
	@Override
	public void processSequence() {
		
		int indexFinalSequences = super.rawSequence.indexOf(beginPattern);
		String finalSequences = rawSequence.substring(indexFinalSequences+beginPattern.length()).replaceAll("\n\n", "\n");
		String sequencias[] = finalSequences.split("\n");
		//System.out.println(sequencias.length);
		for (int i = 0; i < sequencias.length; i++) {
			//System.out.println(sequencias[i]);
			if (sequencias[i] != "\n") {
				//System.out.println(sequencias[i]);
				EventList el = new EventList();
				String events[] = sequencias[i].split(" ");
				for (int j = 3; j < events.length; j++) {
					String actionAndEffect[] = events[j].split("/");
					Event e;
					if (actionAndEffect.length == 2) {
						e = new Event(actionAndEffect[0],actionAndEffect[1]);
					} else {
						e = new Event(actionAndEffect[0],null);
					}
					el.add(e);
				}
				eventSequence.add(el);
			}
		}
	}
	
}
