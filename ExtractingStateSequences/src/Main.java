import java.util.ArrayList;
import java.util.Iterator;



public class Main {

	public static void main(String[] args) {
		char mode = args[0].charAt(0);
		String filePath = args[1];
		boolean propDef = false;
		if (args.length == 3 && args[2].charAt(0) == 'p') {
			propDef = true;
		}
		
		FileHandler file = new FileHandler(filePath);
		String rawSequence = file.fileToString();
		//System.out.println(rawSequence);
		switch (mode) {
			case 'd':
				DSSequenceProcessor ds = new DSSequenceProcessor();
				ds.setRawSequence(rawSequence);
				ds.processSequence();
				ArrayList<EventList> l = ds.getEventSequence();
				for (int i = 0; i < l.size(); i++) {
					EventList el = l.get(i);
					for (Iterator<Event> it = el.iterator(); it.hasNext();) {
						Event e = it.next();
						System.out.print(e.getName()+"->");
					}
					System.out.println();
					System.out.println("Propriedades");
					GenerateCTL genProp = new GenerateCTL();
					ArrayList<Property> listProp = genProp.generateProperties(el);
					if (listProp != null) {
						for (int j = 0; j < listProp.size(); j++) {
							System.out.println(listProp.get(j).getRepresentation());
						}
					}
				}
				
				
				break;
			case 'u':
				UIOSequenceProcessor uio = new UIOSequenceProcessor();
				uio.setRawSequence(rawSequence);
				uio.processSequence();
				ArrayList<EventList> l2 = uio.getEventSequence();
				for (int i = 0; i < l2.size(); i++) {
					EventList el = l2.get(i);
					for (Iterator<Event> it = el.iterator(); it.hasNext();) {
						Event e = it.next();
						System.out.print(e.getName()+"->");
					}
					System.out.println();
					System.out.println("Propriedades");
					GenerateCTL genProp = new GenerateCTL();
					ArrayList<Property> listProp = genProp.generateProperties(el);
					if (listProp != null) {
						for (int j = 0; j < listProp.size(); j++) {
							System.out.println(listProp.get(j).getRepresentation());
						}
					} else {
						System.out.println("Lista de prop vazia");
					}
				}
				break;
			case 'h':
				HSwitchCoverSequenceProcessor hsc = new HSwitchCoverSequenceProcessor();
				hsc.setRawSequence(rawSequence);
				hsc.processSequence();
				ArrayList<EventList> l3 = hsc.getEventSequence();
				for (int i = 0; i < l3.size(); i++) {
					EventList el = l3.get(i);
					for (Iterator<Event> it = el.iterator(); it.hasNext();) {
						Event e = it.next();
						System.out.print(e.getName()+"/"+e.getOutput()+"->");
					}
					System.out.println();
					System.out.println("Propriedades");
					GenerateCTL genProp = new GenerateCTL();
					ArrayList<Property> listProp = genProp.generateProperties(el);
					if (listProp != null) {
						for (int j = 0; j < listProp.size(); j++) {
							System.out.println(listProp.get(j).getRepresentation());
						}
					} else {
						System.out.println("Lista de prop vazia");
					}
				}
				break;
			case 't':
				AllTransitionsSequenceProcessor at = new AllTransitionsSequenceProcessor();
				at.setRawSequence(rawSequence);
				at.processSequence();
				ArrayList<EventList> l4 = at.getEventSequence();
				for (int i = 0; i < l4.size(); i++) {
					EventList el = l4.get(i);
					for (Iterator<Event> it = el.iterator(); it.hasNext();) {
						Event e = it.next();
						System.out.print(e.getName()+"/"+e.getOutput()+"->");
					}
					System.out.println();
				}
				break;
			case 'm':
				//Processa sequencias UIO e DS
				FileHandler dsFileHand = new FileHandler(args[2]);
				String dsRawSequence = dsFileHand.fileToString();
				
				FileHandler uioFileHand = new FileHandler(args[3]);
				String uioRawSequence = uioFileHand.fileToString();
				
				DSSequenceProcessor dsMix = new DSSequenceProcessor();
				dsMix.setRawSequence(dsRawSequence);
				dsMix.processSequence();
				ArrayList<EventList> eventListDs = dsMix.getEventSequence();
				
				UIOSequenceProcessor uioMix = new UIOSequenceProcessor();
				uioMix.setRawSequence(uioRawSequence);
				uioMix.processSequence();
				ArrayList<EventList> eventListUio = uioMix.getEventSequence();
				
				ArrayList<EventList> eventListMix = new ArrayList<EventList>();
				eventListMix.addAll(eventListDs);
				eventListMix.addAll(eventListUio);
				
				
				break;
			default:
				System.out.println("Modo n�o � v�lido");
		}
	}

}
