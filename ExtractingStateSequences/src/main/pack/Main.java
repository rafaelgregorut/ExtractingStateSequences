package main.pack;


import io.handler.Output;
import io.handler.SequencePatternsInputHandler;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import ltl.extraction.ResponsePropertyExtract;
import ltl.extraction.ExistencePropertyExtract;
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
				try {
					
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
					respExtract.printAllResponseProperties();
					
					respExtract.combineProperties();
					respExtract.printCombinedProperties();

					AlgoPrefixSpan_with_Strings algo_uni = new AlgoPrefixSpan_with_Strings(); 
					
					algo_uni.runAlgorithm(sequenceDatabase, null, sequenceDatabase.size());
					String sequencePatt_Universal = algo_uni.getFileContent();
	
						sequencePatt_Universal = seqPattIn.parseSequencePatternsString(sequencePatt_Universal);
					
					ArrayList<EventList> sequencias_universais = seqPattIn.seqPattStringsToManyEventLists(sequencePatt_Universal);
					ExistencePropertyExtract uniExtract = new ExistencePropertyExtract();
					
					for (Iterator<EventList> it = sequencias_universais.iterator(); it.hasNext(); ){
						uniExtract.extractExistenceProperties(it.next());
					}
					uniExtract.printAllExistenceProperties();
					
					uniExtract.combineProperties();
					uniExtract.printCombinedProperties();
					
					Set<Integer> notUsed = sequenceDatabase.getSequenceIDs();
					notUsed.removeAll(sequenceDatabase.getUsedSequences());
					
					for(Integer i : notUsed) 
						out.printNotUsed(i+1+" ");
	
					/*Seria interessate mostrar os eventos nao usados tbm...*/
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
		
		JPanel panelSpecificProps = new JPanel();
		tabbedPane.addTab("Properties for specific events", null, panelSpecificProps, null);
		panelSpecificProps.setLayout(null);
		
		JButton btnCreateSpecifcProperties = new JButton("Specify properties");
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
					respSpecExtract.printAllResponseProperties(eventSpec);
					
					respSpecExtract.combinePropertiesSpecific(eventSpec);
					respSpecExtract.printCombinedProperties(eventSpec);
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}

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
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    textFieldFilePath.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		btnOpen.setBounds(663, 12, 117, 29);
		frame.getContentPane().add(btnOpen);
	}
}
