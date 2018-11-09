/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uniformrandomization;

/**
 *
 * @author oscareduardo937
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

import weka.core.Instances;
import glowworms.GSO;


public class UniformRandomization {
    
    public UniformRandomization(){
        super();
    }
    
    //Loading the data from the filename file to the program. It can be .arff or .csv
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        BufferedReader datafile1 = readDataFile("src/data/iris.arff");
        Instances data = new Instances(datafile1);
		
	GSO gso = new GSO();
        gso.instancesToSamples(data);
        gso.initializeSwarm(data);
	
        System.out.println("Fin...");
    }
    
}
