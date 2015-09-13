package main.pack;
import io.handler.SequencePatternsInputHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import ltl.extraction.ResponsePropertyExtract;
import ltl.extraction.UniversalPropertyExtract;
import mef.basics.EventList;
import ca.pfv.spmf.algorithms.sequentialpatterns.BIDE_and_prefixspan_with_strings.AlgoPrefixSpan_with_Strings;
import ca.pfv.spmf.algorithms.sequentialpatterns.BIDE_and_prefixspan_with_strings.SequentialPatterns;
import ca.pfv.spmf.input.sequence_database_list_strings.SequenceDatabase;

public class Main {
	
	public static void main(String[] args) throws IOException {
		String filePath = args[0];
		double minSupRelative = Double.parseDouble(args[1]);
		
		SequenceDatabase sequenceDatabase = new SequenceDatabase(); 
		sequenceDatabase.loadFile(filePath);
		
		AlgoPrefixSpan_with_Strings algo = new AlgoPrefixSpan_with_Strings(); 
				
		int absoluteMinSup = (int)Math.ceil((minSupRelative * sequenceDatabase.size()));
		
		algo.runAlgorithm(sequenceDatabase, null, absoluteMinSup);   
		String sequencePatt = algo.getFileContent();
		System.out.println("Padroes presentes em pelo menos "+minSupRelative+"% dos casos de teste");
		System.out.println("==============================================");
		System.out.print(sequencePatt);
		System.out.println("TOTAL DE PADROES: "+algo.getPatternCount());
		System.out.println("==============================================");

		SequencePatternsInputHandler seqPattIn = new SequencePatternsInputHandler();
		sequencePatt = seqPattIn.parseSequencePatternsString(sequencePatt);
		
		ArrayList<EventList> sequencias = seqPattIn.seqPattStringsToManyEventLists(sequencePatt);
		ResponsePropertyExtract respExtract = new ResponsePropertyExtract();
		
		for (Iterator<EventList> it = sequencias.iterator(); it.hasNext(); ){
			respExtract.extractResponseProperties(it.next());
		}
		System.out.println("Todas as propriedades de resposta criadas:");
		System.out.println("==============================================");
		respExtract.printAllResponseProperties();
		System.out.println("==============================================");
		
		AlgoPrefixSpan_with_Strings algo_uni = new AlgoPrefixSpan_with_Strings(); 
		
		algo_uni.runAlgorithm(sequenceDatabase, null, sequenceDatabase.size());
		String sequencePatt_Universal = algo_uni.getFileContent();

		System.out.println("Padroes presentes em todos os casos de teste");
		System.out.println("==============================================");
		System.out.print(sequencePatt_Universal);
		System.out.println("==============================================");
				
		sequencePatt_Universal = seqPattIn.parseSequencePatternsString(sequencePatt_Universal);
		
		ArrayList<EventList> sequencias_universais = seqPattIn.seqPattStringsToManyEventLists(sequencePatt_Universal);
		UniversalPropertyExtract uniExtract = new UniversalPropertyExtract();
		
		for (Iterator<EventList> it = sequencias_universais.iterator(); it.hasNext(); ){
			uniExtract.extractUniversalProperties(it.next());
		}
		System.out.println("Todas as propriedades universais criadas:");
		System.out.println("==============================================");
		uniExtract.printAllUniversalProperties();
		System.out.println("==============================================");
	}

}
