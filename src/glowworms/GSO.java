/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glowworms;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;
import weka.core.AttributeStats;

import weka.core.Instances;

/**
 *
 * @author oscareduardo937
 */

public class GSO {
    
    /* ************ Initializing parameters of CGSO algorithm ******************** */
    int swarmSize = 1000; // Swarm size m
    int maxIte = 200;
    double stepSize = 0.03; // Step size for the movements
    double luciferin = 5.0; // Initial luciferin level
    double rho = 0.4; // Luciferin decay parameter
    double gamma = 0.6; // Luciferin reinforcement parameter

    double rs = 0.38; // Initial radial sensor range. This parameter depends on the data set and needs to be found by running experiments

    double gworms[][] = null; // Glowworms of the swarm. 

    /* ************ Initializing parameters of clustering problem and data set ******************** */
    int numAtt; // Dimension of the position vector
    int numClasses; // Number of classes
    int total_data; //Number of instances
    int threshold = 5;
    int runtime = 1;
    /*Algorithm can be run many times in order to see its robustness*/
    
    double minValuesAtts[] = new double[this.numAtt]; // Minimum values for all attributes
    double maxValuesAtts[] = new double[this.numAtt]; // Maximum values for all attributes

    double samples[][] = new double[this.total_data][this.numAtt]; //Samples of the selected dataset.
    
    ArrayList<Integer> candidateList;
    double r;

    /*a random number in the range [0,1)*/
    
    /* *********** Method to put the instances in a matrix and get max and min values for attributes ******************* */

    public void instancesToSamples(Instances data) {
        this.numAtt = data.numAttributes();
        System.out.println("********* NumAttributes: " + this.numAtt);
        AttributeStats attStats = new AttributeStats();
        if (data.classIndex() == -1) {
          //System.out.println("reset index...");
          data.setClassIndex(data.numAttributes() - 1);
        }
        
        this.numClasses = data.numClasses();
        this.minValuesAtts = new double[this.numAtt];
        this.maxValuesAtts = new double[this.numAtt];
        
        System.out.println("********* NumClasses: " + this.numClasses);
        this.total_data = data.numInstances();
        samples = new double[this.total_data][this.numAtt];
        
        double[] values = new double[this.total_data];

        for (int j = 0; j < this.numAtt; j++) {
            values = data.attributeToDoubleArray(j);
            
            for (int i = 0; i < this.total_data; i++) {
                samples[i][j] = values[i];
            }
            
        }
        
        for(int j=0; j<this.numAtt-1; j++){
            
            attStats = data.attributeStats(j);
            
            this.maxValuesAtts[j] = attStats.numericStats.max;
            this.minValuesAtts[j] = attStats.numericStats.min;
            
            //System.out.println("** Min Value Attribute " + j + ": " + this.minValuesAtts[j]);
            //System.out.println("** Max Value Attribute " + j + ": " + this.maxValuesAtts[j]);
        }
        
        //Checking
        /*for(int i=0; i<this.total_data; i++){
		for(int j=0; j<this.numAtt; j++){
		    System.out.print(samples[i][j] + "** ");
		  }
                System.out.println();
	 }*/ 
        
    } // End of method InstancesToSamples
    
    
    public void initializeSwarm(Instances data) {
        this.gworms = new double[this.swarmSize][this.numAtt + 2]; // D-dimensional vector plus luciferin, fitness and intradistance.
        double intraDistance = 0;
        Random r = new Random(); //Random r;

        for (int i = 0; i < this.swarmSize; i++) {

            for (int j = 0; j < this.numAtt - 1; j++) {
                //Uniform randomization of d-dimensional position vector
                //this.gworms[i][j] = this.minValuesAtts[j] + (this.maxValuesAtts[j] - this.minValuesAtts[j]) * r.nextDouble();	
                this.gworms[i][j] = Math.floor(Math.random() * this.maxValuesAtts[j] + this.minValuesAtts[j]);
            }

            this.gworms[i][this.numAtt - 1] = this.luciferin; // Initial luciferin level for all swarm
            this.gworms[i][this.numAtt] = 0; // Initial fitness for all swarm
            this.gworms[i][this.numAtt + 1] = intraDistance; // Intra-distance for gworm i
        }
        
        
        //Checking gworms
        /*for(int i=0; i<this.swarmSize; i++){
		for(int j=0; j<this.numAtt+2; j++){
		    System.out.print(gworms[i][j] + "** ");
		  }
                System.out.println();
	 }*/
        
    } // End of method initializeSwarm
}
