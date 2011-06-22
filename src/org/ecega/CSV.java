package org.ecega;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

public class CSV {

	final public SortedMap <Integer, Double> dataList = new TreeMap<Integer, Double>();
		
	public static void main(String[] args) {
		CSV c = new CSV();
		c.readData("energyConsumption.csv");
		c.printData();
	}
	
	public void readData(String path) {
		
		String zeile;
		String[] split;
		try {
				FileReader file = new FileReader(path);
				BufferedReader data = new BufferedReader(file);
				while ((zeile = data.readLine()) != null) {
					split = zeile.split(";");
					split[1] = split[1].replace(",", ".");
					dataList.put(Integer.parseInt(split[0]), Double.parseDouble(split[1]));
				}
		} catch (FileNotFoundException e) {
			System.out.println("Datei nicht gefunden");
		} catch (IOException e) {
			System.out.println("E/A-Fehler");
		}
	}
	
	public void printData() {
		for (Integer i : dataList.keySet()){
			System.out.println(i + "   " + dataList.get(i));
		}
	}
}
