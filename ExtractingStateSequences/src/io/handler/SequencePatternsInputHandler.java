package io.handler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import mef.basics.Event;
import mef.basics.EventList;

public class SequencePatternsInputHandler {
	
	public String fileToString(String filePath) {
		String fileContent = "";
		try {
				BufferedReader reader = new BufferedReader(new FileReader(filePath));
				String line;
				while ((line = reader.readLine()) != null) {
					line = line.replaceAll("  #SUP: [0-9]*","");
					line = line.replaceAll(" -1 ", ",");
					line = line.replaceAll(" -1", "");
					fileContent += line + "\n";
				}
				reader.close();
				return fileContent;
		} catch (Exception e) {
		    System.err.format("Exception occurred trying to read '%s'.", filePath);
		    e.printStackTrace();
		    return null;
		}
	}
	
	public String parseSequencePatternsString(String str) {
		str = str.replaceAll("  #SUP: [0-9]*","");
		str = str.replaceAll(" -1 ", ",");
		str = str.replaceAll(" -1", "");
		return str;
	}
	
	public EventList lineToEventList(String line) {
		EventList list = new EventList();
		String eventosInOut[] = line.split(",");
		for (int i = 0; i < eventosInOut.length; i++) {
			String evento[] = eventosInOut[i].split("/");
			Event e = new Event(evento[0],evento[1]);
			list.add(e);
		}
		return list;
	}
	
	public ArrayList<EventList> fileToManyEventLists(String filePath) {
		String raw = fileToString(filePath);
		String lines[] = raw.split("\n");
		ArrayList<EventList> sequencias = new ArrayList<EventList>();
		for (int i = 0; i < lines.length; i++) {
			sequencias.add(lineToEventList(lines[i]));
		}
		return sequencias;
	}
	
	public ArrayList<EventList> seqPattStringsToManyEventLists(String str) {
		String lines[] = str.split("\n");
		ArrayList<EventList> sequencias = new ArrayList<EventList>();
		for (int i = 0; i < lines.length; i++) {
			sequencias.add(lineToEventList(lines[i]));
		}
		return sequencias;
	}
}
