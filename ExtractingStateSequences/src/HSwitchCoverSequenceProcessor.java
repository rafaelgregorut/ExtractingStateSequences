import mef.basics.Event;
import mef.basics.EventList;

public class HSwitchCoverSequenceProcessor extends FSMSequenceProcessor {

	HSwitchCoverSequenceProcessor() {
		super();
	}
	
	@Override
	public void processSequence() {
		
		String finalSequences = rawSequence;
		String sequencias[] = finalSequences.split("\n");
		for (int i = 0; i < sequencias.length; i++) {
			System.out.println(sequencias[i]);
			if (sequencias[i] != "\n") {
				EventList el = new EventList();
				String events[] = sequencias[i].split(" ");
				for (int j = 0; j < events.length; j++) {
					String actionAndEffect[] = events[j].split("/");
					Event e;
					if (actionAndEffect.length == 2) {
						e = new Event(actionAndEffect[0],actionAndEffect[1]);
					} else {
						e = new Event(actionAndEffect[0],null);
					}
					el.add(e);
				}
				el.setType("HSwitch");
				eventSequence.add(el);
			}
		}
	}
	
}
