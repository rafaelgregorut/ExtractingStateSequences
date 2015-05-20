import java.util.ArrayList;
import java.util.Iterator;



public class Main {

	public static void main(String[] args) {
		char mode = args[0].charAt(0);
		String filePath = args[1];
		
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
				}
				break;
			case 'u':
				break;
			default:
				System.out.println("Modo não é válido");
		}
	}

}
