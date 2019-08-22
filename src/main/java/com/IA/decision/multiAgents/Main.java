package com.IA.decision.multiAgents;

import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("C:\\multiAgents\\file.txt");
			  fileWriter.write("go");
			    fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
