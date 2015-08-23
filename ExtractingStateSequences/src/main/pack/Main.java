package main.pack;
import io.handler.SequencePatternsInputHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import ltl.extraction.ResponsePropertyExtract;
import mef.basics.EventList;
import ca.pfv.spmf.algorithms.sequentialpatterns.BIDE_and_prefixspan_with_strings.AlgoPrefixSpan_with_Strings;
import ca.pfv.spmf.input.sequence_database_list_strings.SequenceDatabase;

public class Main {
	
	public static void main(String[] args) throws IOException {
		String filePath = args[0];
		double minSupRelative = Double.parseDouble(args[1]);
		//System.out.println("min relative "+minSupRelative);
		
		SequenceDatabase sequenceDatabase = new SequenceDatabase(); 
		sequenceDatabase.loadFile(filePath);
		// print the database to console
		//sequenceDatabase.printDatabase();
		
		AlgoPrefixSpan_with_Strings algo = new AlgoPrefixSpan_with_Strings(); 
				
		int absoluteMinSup = (int)Math.ceil((minSupRelative * sequenceDatabase.size()));
		
		////System.out.println("absolute min sup: "+absoluteMinSup);
		// execute the algorithm
		algo.runAlgorithm(sequenceDatabase, null, absoluteMinSup);   
		String sequencePatt = algo.getFileContent();
		System.out.println("Padroes presentes em pelo menos "+minSupRelative+"% dos casos de teste");
		System.out.println("==============================================");
		System.out.print(sequencePatt);
		System.out.println("==============================================");

		SequencePatternsInputHandler seqPattIn = new SequencePatternsInputHandler();
		sequencePatt = seqPattIn.parseSequencePatternsString(sequencePatt);
		//System.out.print(sequencePatt);
		
		ArrayList<EventList> sequencias = seqPattIn.seqPattStringsToManyEventLists(sequencePatt);
		ResponsePropertyExtract respExtract = new ResponsePropertyExtract();
		
		for (Iterator<EventList> it = sequencias.iterator(); it.hasNext(); ){
			respExtract.extractResponseProperties(it.next());
		}
		System.out.println("Todas as propriedades de resposta criadas:");
		System.out.println("==============================================");
		respExtract.printAllResponseProperties();
		System.out.println("==============================================");

	}

}
