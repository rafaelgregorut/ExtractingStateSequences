package main.pack;
import io.handler.SequencePatternsInputHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import ltl.extraction.ResponsePropertyExtract;
import ltl.extraction.UniversalPropertyExtract;
import mef.basics.EventList;
import ca.pfv.spmf.algorithms.sequentialpatterns.BIDE_and_prefixspan_with_strings.AlgoPrefixSpan_with_Strings;
import ca.pfv.spmf.input.sequence_database_list_strings.SequenceDatabase;

public class Main {
	
	public static void main(String[] args) throws IOException {
		String filePath = args[0];
		double minSupRelative = Double.parseDouble(args[1]);
		
		SequenceDatabase sequenceDatabase = new SequenceDatabase(); 
		String databaseInString = sequenceDatabase.loadFile(filePath);
		//System.out.println("AQUI: "+databaseInString);		
		
		AlgoPrefixSpan_with_Strings algo = new AlgoPrefixSpan_with_Strings(); 
				
		int absoluteMinSup = (int)Math.ceil((minSupRelative * sequenceDatabase.size()));
		
		algo.runAlgorithm(sequenceDatabase, null, absoluteMinSup);   
		String sequencePatt = algo.getFileContent();
		/*System.out.println("Padroes presentes em pelo menos "+minSupRelative+"% dos casos de teste");
		System.out.println("==============================================");
		System.out.print(sequencePatt);
		System.out.println("TOTAL DE PADROES: "+algo.getPatternCount());
		System.out.println("==============================================");*/

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
		
		/*
		 * Combinacao das propriedades de resposta
		 */
		System.out.println("Propriedades de resposta combinadas:");
		System.out.println("==============================================");
		respExtract.combineProperties();
		respExtract.printCombinedProperties();
		System.out.println("==============================================");
		
		AlgoPrefixSpan_with_Strings algo_uni = new AlgoPrefixSpan_with_Strings(); 
		
		algo_uni.runAlgorithm(sequenceDatabase, null, sequenceDatabase.size());
		String sequencePatt_Universal = algo_uni.getFileContent();

		/*System.out.println("Padroes presentes em todos os casos de teste");
		System.out.println("==============================================");
		System.out.print(sequencePatt_Universal);
		System.out.println("==============================================");*/
				
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
		
		/*
		 * Combinacao das propriedades universais
		 */
		
		System.out.println("Propriedades universais combinadas:");
		System.out.println("==============================================");
		uniExtract.combineProperties();
		uniExtract.printCombinedProperties();
		System.out.println("==============================================");
		
		/*
		 * Aqui preciso mostrar quais sequencias de teste nao foram usados para criacao de propriedades
		 * */
		Set<Integer> notUsed = sequenceDatabase.getSequenceIDs();
		notUsed.removeAll(sequenceDatabase.getUsedSequences());
		
		System.out.println("Sequencias de teste nao utilizadas: ");
		for(Integer i : notUsed) 
			System.out.print(i+" ");
		System.out.println("\n==============================================");

		/*Seria interessate mostrar os eventos nao usados tbm...*/
		
		/*
		 * Usuario pode passar eventos especificos para gerar as propriedades de resposta
		 */
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite evento para o qual deseja propriedades de resposta:");
		while (sc.hasNext()) {
			String eventSpec = sc.next();
			if(eventSpec.equals("Q"))
				break;
			
			SequenceDatabase databaseSpecific = new SequenceDatabase();
			databaseSpecific.loadFromStringWithSubstring(databaseInString, eventSpec);
			
			//databaseSpecific.printDatabase();
			
			AlgoPrefixSpan_with_Strings algoSpec = new AlgoPrefixSpan_with_Strings();
			algoSpec.runAlgorithm(databaseSpecific, null, 0);
			
			String sequencePatt_Spec = algoSpec.getFileContent(eventSpec);
			/*System.out.println("Padroes presentes em nas sequencias que contem "+eventSpec);
			System.out.println("==============================================");
			System.out.print(sequencePatt_Spec);
			//System.out.println("TOTAL DE PADROES: "+algoSpec.getPatternCount());
			System.out.println("==============================================");*/

			sequencePatt_Spec = seqPattIn.parseSequencePatternsString(sequencePatt_Spec);
			
			ArrayList<EventList> sequencias_spec = seqPattIn.seqPattStringsToManyEventLists(sequencePatt_Spec);
			ResponsePropertyExtract respSpecExtract = new ResponsePropertyExtract();
			
			for (Iterator<EventList> it = sequencias_spec.iterator(); it.hasNext(); ){
				respSpecExtract.extractSpecificReponseProperties(it.next(),eventSpec);
			}
			System.out.println("Todas as propriedades de resposta relacionadas ao evento "+eventSpec+":");
			System.out.println("==============================================");
			respSpecExtract.printAllResponseProperties(eventSpec);
			System.out.println("==============================================");
			
			/*
			 * Combinacao das propriedades de resposta
			 */
			System.out.println("Propriedades de resposta combinadas:");
			System.out.println("==============================================");
			respSpecExtract.combinePropertiesSpecific(eventSpec);
			respSpecExtract.printCombinedProperties();
			System.out.println("==============================================");
			
			System.out.println("Digite evento para o qual deseja propriedades de resposta:");

		}
		sc.close();
	}
}
