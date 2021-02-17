package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

import controllers.Engine;
import helpers.Creator;
import helpers.Logger;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		Creator initializer = new Creator();
		
		try (BufferedReader br =  new BufferedReader(new FileReader("input.txt"))) {
			String line = br.readLine();
			
			while (line != null) {
				initializer.create(line);
				
				line = br.readLine();
			}
		}
		
		Engine engine = new Engine(initializer.getSetOfIds(), initializer.getMapOfEmployees());
		String result = engine.pairWithLongestTimeOnProject();
		
		try (BufferedWriter wr = new BufferedWriter(new FileWriter("result.txt"))) {
			wr.write(result);
		}
		
		try (BufferedWriter wr = new BufferedWriter(new FileWriter("logger.txt"))) {
			wr.write(Logger.logResult());
		}
	}

}
