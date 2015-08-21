import io.handler.SequencePatternsInputHandler;

import java.util.ArrayList;
import java.util.Iterator;

import ltl.extraction.ResponsePropertyExtract;
import mef.basics.EventList;

public class Main {
	
	public static void main(String[] args) {
		String filePath = args[0];
		
		SequencePatternsInputHandler file = new SequencePatternsInputHandler();
		ArrayList<EventList> sequencias = file.fileToManyEventLists(filePath);
		//GenerateCTL genProp = new GenerateCTL();
		ResponsePropertyExtract respExtract = new ResponsePropertyExtract();
		
		for (Iterator<EventList> it = sequencias.iterator(); it.hasNext(); ){
			respExtract.printResponseProperties(it.next());
		}
		System.out.println("Todas as propriedades de resposta criadas:");
		respExtract.printAllResponseProperties();
	}

}
