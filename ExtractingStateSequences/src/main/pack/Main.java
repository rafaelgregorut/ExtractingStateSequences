package main.pack;


import io.handler.Output;
import io.handler.SequencePatternsInputHandler;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ltl.extraction.ResponsePropertyExtract;
import ltl.extraction.UniversalPropertyExtract;
import mef.basics.EventList;
import ca.pfv.spmf.algorithms.sequentialpatterns.BIDE_and_prefixspan_with_strings.AlgoPrefixSpan_with_Strings;
import ca.pfv.spmf.input.sequence_database_list_strings.SequenceDatabase;

public class Main {
	
	
	private JFrame frame;
	private JTextField textFieldFilePath;
	private JTextField textFieldMinSup;
	private JTextField textFieldSpecEvent;
	public JTextArea textArea;
	
	public static Output out;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 568, 581);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textFieldFilePath = new JTextField();
		textFieldFilePath.setBounds(200, 11, 346, 28);
		frame.getContentPane().add(textFieldFilePath);
		textFieldFilePath.setColumns(10);
		
		JButton btnNewButton = new JButton("Create general properties");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					out.clear();
					
					String filePath = textFieldFilePath.getText();
					double minSupRelative = Double.parseDouble(textFieldMinSup.getText());
					
					SequenceDatabase sequenceDatabase = new SequenceDatabase(); 
					String databaseInString = sequenceDatabase.loadFile(filePath);
					
					AlgoPrefixSpan_with_Strings algo = new AlgoPrefixSpan_with_Strings(); 
							
					int absoluteMinSup = (int)Math.ceil((minSupRelative * sequenceDatabase.size()));
					
					algo.runAlgorithm(sequenceDatabase, null, absoluteMinSup);   
					String sequencePatt = algo.getFileContent();
					
					SequencePatternsInputHandler seqPattIn = new SequencePatternsInputHandler();
					sequencePatt = seqPattIn.parseSequencePatternsString(sequencePatt);
					
					ArrayList<EventList> sequencias = seqPattIn.seqPattStringsToManyEventLists(sequencePatt);
					ResponsePropertyExtract respExtract = new ResponsePropertyExtract();
					
					for (Iterator<EventList> it = sequencias.iterator(); it.hasNext(); ){
						respExtract.extractResponseProperties(it.next());
					}
					out.println("Todas as propriedades de resposta criadas:");
					out.println("==============================================");
					respExtract.printAllResponseProperties();
					System.out.println("==============================================");
					
					/*
					 * Combinacao das propriedades de resposta
					 */
					out.println("Propriedades de resposta combinadas:");
					out.println("==============================================");
					respExtract.combineProperties();
					respExtract.printCombinedProperties();
					out.println("==============================================");
					
					AlgoPrefixSpan_with_Strings algo_uni = new AlgoPrefixSpan_with_Strings(); 
					
					algo_uni.runAlgorithm(sequenceDatabase, null, sequenceDatabase.size());
					String sequencePatt_Universal = algo_uni.getFileContent();
	
						sequencePatt_Universal = seqPattIn.parseSequencePatternsString(sequencePatt_Universal);
					
					ArrayList<EventList> sequencias_universais = seqPattIn.seqPattStringsToManyEventLists(sequencePatt_Universal);
					UniversalPropertyExtract uniExtract = new UniversalPropertyExtract();
					
					for (Iterator<EventList> it = sequencias_universais.iterator(); it.hasNext(); ){
						uniExtract.extractUniversalProperties(it.next());
					}
					out.println("Todas as propriedades universais criadas:");
					out.println("==============================================");
					uniExtract.printAllUniversalProperties();
					out.println("==============================================");
					
					/*
					 * Combinacao das propriedades universais
					 */
					
					out.println("Propriedades universais combinadas:");
					out.println("==============================================");
					uniExtract.combineProperties();
					uniExtract.printCombinedProperties();
					out.println("==============================================");
					
					/*
					 * Aqui preciso mostrar quais sequencias de teste nao foram usados para criacao de propriedades
					 * */
					Set<Integer> notUsed = sequenceDatabase.getSequenceIDs();
					notUsed.removeAll(sequenceDatabase.getUsedSequences());
					
					out.println("Sequencias de teste nao utilizadas: ");
					for(Integer i : notUsed) 
						out.print(i+" ");
					out.println("\n==============================================");
	
					/*Seria interessate mostrar os eventos nao usados tbm...*/
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				

			}
		});
		btnNewButton.setBounds(346, 98, 200, 29);
		frame.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 228, 557, 325);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		out = new Output(textArea);

		
		JLabel lblPathToThe = new JLabel("Path to the sequence file");
		lblPathToThe.setBounds(19, 6, 200, 38);
		frame.getContentPane().add(lblPathToThe);
		
		JLabel lblMinimumSupport = new JLabel("Minimum support");
		lblMinimumSupport.setBounds(19, 86, 200, 50);
		frame.getContentPane().add(lblMinimumSupport);
		
		textFieldMinSup = new JTextField();
		textFieldMinSup.setBounds(200, 97, 134, 28);
		frame.getContentPane().add(textFieldMinSup);
		textFieldMinSup.setColumns(10);
		
		JLabel lblCreatePropertiesFor = new JLabel("Create properties for specific event");
		lblCreatePropertiesFor.setBounds(19, 137, 261, 50);
		frame.getContentPane().add(lblCreatePropertiesFor);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(16, 135, 530, 12);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(19, 51, 527, 12);
		frame.getContentPane().add(separator_1);
		
		JLabel lblCreateGeneralProperties = new JLabel("Create general properties");
		lblCreateGeneralProperties.setBounds(19, 51, 200, 50);
		frame.getContentPane().add(lblCreateGeneralProperties);
		
		JLabel lblSpecificEvent = new JLabel("Specific event");
		lblSpecificEvent.setBounds(19, 175, 200, 50);
		frame.getContentPane().add(lblSpecificEvent);
		
		textFieldSpecEvent = new JTextField();
		textFieldSpecEvent.setBounds(200, 186, 134, 28);
		frame.getContentPane().add(textFieldSpecEvent);
		textFieldSpecEvent.setColumns(10);
		
		JButton btnCreateSpecifcProperties = new JButton("Create specifc properties");
		btnCreateSpecifcProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String filePath = textFieldFilePath.getText();
					
					SequenceDatabase sequenceDatabase = new SequenceDatabase(); 
					String databaseInString = sequenceDatabase.loadFile(filePath);
					String eventSpec = textFieldSpecEvent.getText();
					
					SequenceDatabase databaseSpecific = new SequenceDatabase();
					databaseSpecific.loadFromStringWithSubstring(databaseInString, eventSpec);
					
					
					AlgoPrefixSpan_with_Strings algoSpec = new AlgoPrefixSpan_with_Strings();
					algoSpec.runAlgorithm(databaseSpecific, null, 0);
					
					String sequencePatt_Spec = algoSpec.getFileContent(eventSpec);
	
					SequencePatternsInputHandler seqPattIn = new SequencePatternsInputHandler();
					sequencePatt_Spec = seqPattIn.parseSequencePatternsString(sequencePatt_Spec);
					
					ArrayList<EventList> sequencias_spec = seqPattIn.seqPattStringsToManyEventLists(sequencePatt_Spec);
					ResponsePropertyExtract respSpecExtract = new ResponsePropertyExtract();
					
					for (Iterator<EventList> it = sequencias_spec.iterator(); it.hasNext(); ){
						respSpecExtract.extractSpecificReponseProperties(it.next(),eventSpec);
					}
					out.println("Todas as propriedades de resposta relacionadas ao evento "+eventSpec+":");
					out.println("==============================================");
					respSpecExtract.printAllResponseProperties(eventSpec);
					out.println("==============================================");
					
					/*
					 * Combinacao das propriedades de resposta
					 */
					out.println("Propriedades de resposta combinadas:");
					out.println("==============================================");
					respSpecExtract.combinePropertiesSpecific(eventSpec);
					respSpecExtract.printCombinedProperties();
					out.println("==============================================");
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});
		btnCreateSpecifcProperties.setBounds(346, 187, 200, 29);
		frame.getContentPane().add(btnCreateSpecifcProperties);
	}


	
	public static void main2(String[] args) throws IOException {
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
		
		//System.out.println("Padroes:");
		//System.out.println(sequencePatt);
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
				//EventList elIt = it.next();
				//System.out.println("Vou gerar prop spec para sequencia ");
				//elIt.print();
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
