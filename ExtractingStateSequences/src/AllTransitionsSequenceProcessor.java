
public class AllTransitionsSequenceProcessor extends FSMSequenceProcessor {

	AllTransitionsSequenceProcessor() {
		super();
		setBeginPattern("{(");
	}
	public void processSequence() {
		int indexFinalSequences = super.rawSequence.indexOf(beginPattern);
		String finalSequences = rawSequence.substring(indexFinalSequences+beginPattern.length()).replaceAll("\\)}\n*", "");
		String sequencias[] = finalSequences.split("\\)\\(");
		//System.out.println(sequencias.length);
		for (int i = 0; i < sequencias.length; i++) {
			//System.out.println("seq["+i+"]="+sequencias[i]);
			if(sequencias[i] != "\n") {
				
				EventList el = new EventList();
				String events[] = sequencias[i].split(",");
				for (int j = 0; j < events.length; j++) {
					System.out.println(events[j]);
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
				//System.out.println(sequencias[i]);
			}
		}
	}
}
