import java.util.ArrayList;
import java.util.Iterator;

public class Main {
	
	public static void main(String[] args) {
		char mode = args[0].charAt(0);
		String filePath = args[1];
		
		FileHandler file = new FileHandler(filePath);
		String rawSequence = file.fileToString();
		GenerateCTL genProp = new GenerateCTL();
		switch (mode) {		
			case 'm':
				
				DS_UIOSequenceProcessor ds_uio = new DS_UIOSequenceProcessor();
				ds_uio.setRawSequence(rawSequence);
				ds_uio.processSequence();
				ArrayList<EventList> eventsSeqsLists = ds_uio.getEventSequence();
				System.out.println("Sequencias de testes");
				for (int i = 0; i < eventsSeqsLists.size(); i++) {
					EventList el = eventsSeqsLists.get(i);
					for (Iterator<Event> it = el.iterator(); it.hasNext();) {
						Event e = it.next();
						System.out.print(e.getName()+"->");
					}
					System.out.println();
					System.out.println("Propriedades");
					
					ArrayList<Property> listProp = genProp.generateProperties(el);
					if (listProp != null) {
						for (int j = 0; j < listProp.size(); j++) {
							System.out.println(listProp.get(j).getRepresentation());
						}
					} else {
						System.out.println("Lista de prop vazia");
					}
				}
				
				PrefixTreeAcceptor pta = new PrefixTreeAcceptor(eventsSeqsLists);
				pta.printTree();
				
				EventList pref = pta.maxCommonPrefix();
				System.out.println("Max pref:");
				for (Iterator<Event> i =pref.iterator(); i.hasNext();) {
					System.out.print(i.next().getName()+" ");
				}
				System.out.println();
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
			case 'r':
				RequisiteProcessor req = new RequisiteProcessor();
				req.setRawStates(rawSequence);
				req.processStates();
				ArrayList<State> lState = req.getStateList();
				System.out.println("Estados:");
				for (int i = 0; i < lState.size(); i++) {
					System.out.println(lState.get(i).getName());
				}
				ArrayList<Property> props = genProp.generateProperties(lState);
				for (Iterator<Property> i = props.iterator(); i.hasNext();) {
					System.out.println(i.next().getRepresentation());
				}
				break;
			default:
				System.out.println("Modo invalido");
		}
	}

}
