package main.pack;


import io.handler.Output;
import io.handler.SequencePatternsInputHandler;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import ltl.extraction.ExistencePropertyExtract;
import ltl.extraction.ResponsePropertyExtract;
import mef.basics.EventList;
import ca.pfv.spmf.algorithms.sequentialpatterns.BIDE_and_prefixspan_with_strings.AlgoPrefixSpan_with_Strings;
import ca.pfv.spmf.input.sequence_database_list_strings.SequenceDatabase;

public class Main {
	
	

	private JFrame frame;
	private JTextField textFieldFilePath;
	private JTextField textFieldMinSup;
	private JTextField textFieldSpecEvent;
	private JTable tableRespGen;
	private JTable tableExistGen;
	private JTable tableRespSpec;
	private JTextArea textFieldNotUsed;
	
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
		out = new Output(tableRespGen,tableExistGen,tableRespSpec,textFieldNotUsed);

	}
	
	//method to specify and print on the screen the response properties based on mining
	private void specifyResponseProperties(String sequencePatt, SequencePatternsInputHandler seqPattIn) {
		//parse the sequence in the SPMF format
		sequencePatt = seqPattIn.parseSequencePatternsString(sequencePatt);
		
		//Convert the string in event lists
		ArrayList<EventList> sequencias = seqPattIn.seqPattStringsToManyEventLists(sequencePatt);
		
		ResponsePropertyExtract respExtract = new ResponsePropertyExtract();
		
		//Extract response properties from each event list
		for (Iterator<EventList> it = sequencias.iterator(); it.hasNext(); ){
			respExtract.extractResponseProperties(it.next());
		}
		respExtract.printAllResponseProperties();
		
		//combine properties that can be combined
		respExtract.combineProperties();
		respExtract.printCombinedProperties();
	}
	
	//method to specify and print on the screen the existence properties based on mining
	private void specifyExistenceProperties(String sequencePattExist, SequencePatternsInputHandler seqPattIn) {
		//parse the sequence in the SPMF format
		sequencePattExist = seqPattIn.parseSequencePatternsString(sequencePattExist);
		
		//Convert the string in event lists
		ArrayList<EventList> sequencesExistence = seqPattIn.seqPattStringsToManyEventLists(sequencePattExist);
		ExistencePropertyExtract existExtract = new ExistencePropertyExtract();
		
		//Extract existence properties from each event list
		for (Iterator<EventList> it = sequencesExistence.iterator(); it.hasNext(); ){
			existExtract.extractExistenceProperties(it.next());
		}
		existExtract.printAllExistenceProperties();
		
		//combine properties that can be combined
		existExtract.combineProperties();
		existExtract.printCombinedProperties();
		
	}
	
	//print the sequences' ID that were not used in mining
	private void printNotUsed(SequenceDatabase sequenceDatabase) {
		Set<Integer> notUsed = sequenceDatabase.getSequenceIDs();
		notUsed.removeAll(sequenceDatabase.getUsedSequences());
		
		for(Integer i : notUsed) 
			out.printNotUsed(i+1+" ");

	}
	
	//method triggered by button Specify Properties in the mining tab
	private void specifyPropertiesFromMining() {
		try {
			//get the file path
			String filePath = textFieldFilePath.getText();
			
			//get the minimum support
			double minSupRelative = Double.parseDouble(textFieldMinSup.getText());
			
			SequenceDatabase sequenceDatabase = new SequenceDatabase(); 
			//Load the file into the database
			sequenceDatabase.loadFile(filePath);
			
			AlgoPrefixSpan_with_Strings algo = new AlgoPrefixSpan_with_Strings(); 
				
			//calculate the minimum support
			int absoluteMinSup = (int)Math.ceil((minSupRelative * sequenceDatabase.size()));
			
			//run the algorithm
			algo.runAlgorithm(sequenceDatabase, null, absoluteMinSup);   
			
			//get the results (most frequent patterns)
			String sequencePatt = algo.getFileContent();
			
			//Object to handle the results format
			SequencePatternsInputHandler seqPattIn = new SequencePatternsInputHandler();
			
			//Specify the response properties based on the mining results
			specifyResponseProperties(sequencePatt,seqPattIn);
	
			//It is necessary to create another object to run the algorithm
			AlgoPrefixSpan_with_Strings algoExist = new AlgoPrefixSpan_with_Strings(); 
			
			//run the algorithm to get the patterns in all sequences
			algoExist.runAlgorithm(sequenceDatabase, null, sequenceDatabase.size());
			
			//get the results
			String sequencePattExist = algoExist.getFileContent();
	
			//specify the existence properties
			specifyExistenceProperties(sequencePattExist,seqPattIn);
			
			//print the sequences that were not used
			printNotUsed(sequenceDatabase);
			
			/*Seria interessate mostrar os eventos nao usados tbm...*/
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	//specify and prints on the screen response properties for a specific event
	private void specifyResponsePropertiesForSpecificEvent(String sequencePatt_Spec, SequencePatternsInputHandler seqPattIn, String eventSpec) {
		//parse the sequence in the SPMF format
		sequencePatt_Spec = seqPattIn.parseSequencePatternsString(sequencePatt_Spec);
		
		//Convert the string in event lists
		ArrayList<EventList> sequencias_spec = seqPattIn.seqPattStringsToManyEventLists(sequencePatt_Spec);
		ResponsePropertyExtract respSpecExtract = new ResponsePropertyExtract();
		
		//Extract existence properties from each event list
		for (Iterator<EventList> it = sequencias_spec.iterator(); it.hasNext(); ){
			respSpecExtract.extractSpecificReponseProperties(it.next(),eventSpec);
		}
		respSpecExtract.printAllResponseProperties(eventSpec);
		
		//combine properties that can be combined
		respSpecExtract.combinePropertiesSpecific(eventSpec);
		respSpecExtract.printCombinedProperties(eventSpec);
	}
	
	//Methos triggered by button Specify properties in tab for specific event
	private void specifyPropertySpecific() {
		try {
			//get the file path
			String filePath = textFieldFilePath.getText();
			
			//load the whole database
			SequenceDatabase sequenceDatabase = new SequenceDatabase(); 
			String databaseInString = sequenceDatabase.loadFile(filePath);
			
			//get the specific event
			String eventSpec = textFieldSpecEvent.getText();
			
			//create the specific database
			SequenceDatabase databaseSpecific = new SequenceDatabase();
			databaseSpecific.loadFromStringWithSubstring(databaseInString, eventSpec);
			
			AlgoPrefixSpan_with_Strings algoSpec = new AlgoPrefixSpan_with_Strings();
			//run the algorithm
			algoSpec.runAlgorithm(databaseSpecific, null, 0);
			
			//get the results
			String sequencePatt_Spec = algoSpec.getFileContent(eventSpec);

			SequencePatternsInputHandler seqPattIn = new SequencePatternsInputHandler();
			
			//specify and prints on the screen the specific response properties
			specifyResponsePropertiesForSpecificEvent(sequencePatt_Spec,seqPattIn,eventSpec);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	//Action for the button to open the test paths file
	private void actionOpenButton() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    textFieldFilePath.setText(selectedFile.getAbsolutePath());
		}
	}
	
	//Initializes and places the elements on the frame
	private void initialize() {		
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 808, 669);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textFieldFilePath = new JTextField();
		textFieldFilePath.setBounds(200, 11, 451, 28);
		frame.getContentPane().add(textFieldFilePath);
		textFieldFilePath.setColumns(10);
		
		JLabel lblPathToThe = new JLabel("Test cases sequences (SPMF) :");
		lblPathToThe.setBounds(6, 6, 200, 38);
		frame.getContentPane().add(lblPathToThe);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 50, 796, 591);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panelGeneralProps = new JPanel();
		tabbedPane.addTab("Properties based on test case mining", null, panelGeneralProps, null);
		panelGeneralProps.setLayout(null);
		
		JLabel lblMinimumSupport = new JLabel("Minimum support (e.g. 0.5)");
		lblMinimumSupport.setBounds(6, 6, 200, 28);
		panelGeneralProps.add(lblMinimumSupport);
		
		textFieldMinSup = new JTextField();
		textFieldMinSup.setBounds(192, 6, 58, 28);
		panelGeneralProps.add(textFieldMinSup);
		textFieldMinSup.setColumns(10);
		
		JButton btnCreateGenProps = new JButton("Specify properties");
		btnCreateGenProps.setBounds(262, 7, 141, 29);
		panelGeneralProps.add(btnCreateGenProps);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Responsive Properties", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(0, 39, 775, 222);
		panelGeneralProps.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 18, 763, 198);
		panel_1.add(scrollPane_1);
		
		String[] columnsNames = {"Informal Description", "Formal property"};
		DefaultTableModel modelRespGen = new DefaultTableModel(null,columnsNames);
		
		tableRespGen = new JTable(modelRespGen);
		scrollPane_1.setViewportView(tableRespGen);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Existence Properties", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(0, 273, 775, 220);
		panelGeneralProps.add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(6, 18, 763, 196);
		panel_2.add(scrollPane_2);
		
		DefaultTableModel modelExistGen = new DefaultTableModel(null,columnsNames);
		tableExistGen = new JTable(modelExistGen);
		scrollPane_2.setViewportView(tableExistGen);
		
		JLabel lblNotUsedSequences = new JLabel("Not used sequences:");
		lblNotUsedSequences.setBounds(10, 505, 146, 16);
		panelGeneralProps.add(lblNotUsedSequences);
		
		textFieldNotUsed = new JTextArea();
		textFieldNotUsed.setBounds(153, 499, 616, 28);
		panelGeneralProps.add(textFieldNotUsed);
		textFieldNotUsed.setColumns(10);
		btnCreateGenProps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				specifyPropertiesFromMining();
			}
		});
		
		JPanel panelSpecificProps = new JPanel();
		tabbedPane.addTab("Properties for specific events", null, panelSpecificProps, null);
		panelSpecificProps.setLayout(null);
		
		JButton btnCreateSpecifcProperties = new JButton("Specify properties");
		btnCreateSpecifcProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				specifyPropertySpecific();
			}
		});
		btnCreateSpecifcProperties.setBounds(291, 6, 157, 29);
		panelSpecificProps.add(btnCreateSpecifcProperties);
		
		textFieldSpecEvent = new JTextField();
		textFieldSpecEvent.setBounds(100, 5, 189, 28);
		panelSpecificProps.add(textFieldSpecEvent);
		textFieldSpecEvent.setColumns(10);
		
		JLabel lblSpecificEvent = new JLabel("Specific event");
		lblSpecificEvent.setBounds(6, 8, 200, 22);
		panelSpecificProps.add(lblSpecificEvent);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Specific Response Properties", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(6, 36, 763, 503);
		panelSpecificProps.add(panel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 18, 751, 479);
		panel.add(scrollPane);
		
		DefaultTableModel modelRespSpec = new DefaultTableModel(null,columnsNames);

		tableRespSpec = new JTable(modelRespSpec);
		scrollPane.setViewportView(tableRespSpec);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionOpenButton();
			}
		});
		btnOpen.setBounds(663, 12, 117, 29);
		frame.getContentPane().add(btnOpen);
	}
}
