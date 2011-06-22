package org.ecega;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.jscience.mathematics.number.Float64;

public class CSV {

	final public EnergyDataSet _dataSet = new EnergyDataSet();
	final private String PATH = "energyConsumption.csv";

	public static void main(String[] args) {
		CSV c = new CSV();
		c.readData("energyConsumption.csv");
		c.printData();
	}

	public void readData(String path) {
		if (path == null) {
			path = PATH;
		}
		String zeile;
		String[] split;
		try {
			FileReader file = new FileReader(path);
			BufferedReader data = new BufferedReader(file);
			while ((zeile = data.readLine()) != null) {
				split = zeile.split(";");
				split[1] = split[1].replace(",", ".");
				_dataSet.put(Integer.parseInt(split[0]),
						Float64.valueOf(Double.parseDouble(split[1])));
			}
		} catch (FileNotFoundException e) {
			System.out.println("Datei nicht gefunden");
		} catch (IOException e) {
			System.out.println("E/A-Fehler");
		}
	}

	public EnergyDataSet getDataSet() {
		return this._dataSet;
	}

	public void printData() {
		for (Integer i : _dataSet.keySet()) {
			System.out.println(i + "   " + _dataSet.get(i));
		}
	}
}