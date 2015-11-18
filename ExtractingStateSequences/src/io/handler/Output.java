package io.handler;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class Output {

	JTextArea textAreaNotUsed;
	JTable tableRespGen;
	JTable tableRespSpec;
	JTable tableExistGen;
	
	
	
	public Output(JTable respGen, JTable existGen, JTable respSpec, JTextArea ta) {
		tableRespGen = respGen;
		tableExistGen = existGen;
		tableRespSpec = respSpec;
		textAreaNotUsed = ta;
	}
	
	
	
	public void printRowRespSpec(String informal, String prop) {
		DefaultTableModel myModel = (DefaultTableModel) tableRespSpec.getModel();
		myModel.addRow(new Object[]{informal,prop});
	}
	
	public void printRowRespGen(String informal, String prop) {
		DefaultTableModel myModel = (DefaultTableModel) tableRespGen.getModel();
		myModel.addRow(new Object[]{informal,prop});
	}
	
	public void printRowExistGen(String informal, String prop) {
		DefaultTableModel myModel = (DefaultTableModel) tableExistGen.getModel();
		myModel.addRow(new Object[]{informal,prop});
	}
	
	public void printNotUsed(String str) {
		if(textAreaNotUsed.getText().equals(""))
			textAreaNotUsed.append(str);
		else
			textAreaNotUsed.append(", "+str);
	}
}
