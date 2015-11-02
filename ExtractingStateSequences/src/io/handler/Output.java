package io.handler;

import javax.swing.JTextArea;

public class Output {

	JTextArea textArea;
	
	public Output(JTextArea textArea) {
		this.textArea = textArea;
	}
	
	public void println(String str) {
		textArea.append(str+"\n");
	}
	
	public void print(String str) {
		textArea.append(str);
	}
	
	public void clear() {
		textArea.setText(null);
	}
}
