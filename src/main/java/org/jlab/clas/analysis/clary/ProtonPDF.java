package org.jlab.clas.analysis.clary;

import java.util.*;
import java.io.*;
import java.math.*;
import org.apache.commons.math3.special.Erf;

///////////////////////////////////
//WHERE ALL THE PROTON PDFS LIVE
//FOR MLE CALCULATIONS

public class ProtonPDF{
   
    
    public static double protonBetaMLE( double beta_meas, double beta_true, double p, int sector ){
	//SECTOR DEPENDENCE HERE
 	List<Double> min_beta = null;
	List<Double> max_beta = null;

	if( sector == 0 ){
	    min_beta = CutLoader.getMinProtonBetaSector1Cut(); // CONTAINS MEAN VALUES HUGE TYPE 
	    max_beta = CutLoader.getMaxProtonBetaSector1Cut(); // CONTAINS SIGMA VALUES 
	}
	else if( sector == 1 ){
	    min_beta = CutLoader.getMinProtonBetaSector2Cut();
	    max_beta = CutLoader.getMaxProtonBetaSector2Cut();
	}
	else if( sector == 2 ){
	    min_beta = CutLoader.getMinProtonBetaSector3Cut();
	    max_beta = CutLoader.getMaxProtonBetaSector3Cut();
	}
	else if( sector == 3 ){
	    min_beta = CutLoader.getMinProtonBetaSector4Cut();
	    max_beta = CutLoader.getMaxProtonBetaSector4Cut();
	}
	else if( sector == 4 ){
	    min_beta = CutLoader.getMinProtonBetaSector5Cut();
	    max_beta = CutLoader.getMaxProtonBetaSector5Cut();
	}
	else if( sector == 5 ){
	    min_beta = CutLoader.getMinProtonBetaSector6Cut();
	    max_beta = CutLoader.getMaxProtonBetaSector6Cut();
	}

	/*
	  SIGMA FUNCTIONAL FORM HERE
	  DOESNT MATTER IF I USE MIN OR MAX HERE, I JUST NEED ONE SIGMA PARAMETERIZATION
	 */
	//System.out.println(">> SIG FIT VALUES " + max_beta.get(0) + " " + max_beta.get(1) );
	//System.out.println(">> MEAN FIT VALUES " + min_beta.get(0) + " " + min_beta.get(1) + " " + min_beta.get(2) );


	//double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p); //FORM [a] + [b]/p^(1/2)
	double mean = min_beta.get(0)/Math.sqrt(( 1.0 + Math.pow(min_beta.get(1)/p,2))); //1.0/Math.sqrt(( 1.0 + Math.pow(min_beta.get(0)/p,2))) + min_beta.get(1) + min_beta.get(2)/p ; // FORM 1.0/( 1 + ([a]/x)^2 )^(1/2) + [b] + [c]/x
	double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p);
	
	//System.out.println(" >> " + mean + " SIGMA " + sigma );
	

	//System.out.println(" IN PROTON BETA MLE " + beta_meas +  " " + beta_true + " " + p + " " + sector );
	double residu = (beta_meas - mean);//beta_true );
	double residsig = (beta_meas - mean)/sigma ;// beta_true )/sigma;
	double expon = Math.exp( -0.5 * Math.pow( (beta_meas - mean)/sigma, 2 ));
	//System.out.println(" EXPON " + expon + " RES " + residu + " RESSIG" + residsig );

	double likelihood = (1.0/ Math.sqrt(2.0 * 3.14159265358 * sigma*sigma) ) * Math.exp( -0.5 * Math.pow( (beta_meas - mean )/sigma, 2 )); //beta_true CHANGED TO meas
	//System.out.println(" PROTON LIKELIHOOD " + likelihood );
	return likelihood;
	
    }

    public static double protonVertexMLE( double vz_meas, double vz_true ){
	///////////////////////////////////////////
	//CHANGE THE PARAMETERS HERE FOR VERTEX MLE
	double sigma = 0.02;
	//System.out.println(" IN PROTON BETA MLE " + beta_meas +  " " + beta_true );
	double residu = (vz_meas - vz_true );
	double residsig = (vz_meas - vz_true )/sigma;
	double expon = Math.exp( -0.5 * Math.pow( (vz_meas - vz_true )/sigma, 2 ));
	//System.out.println(" EXPON " + expon + " " + residu + " " + residsig );
				 
	double likelihood = (1.0/ Math.sqrt(2.0 * 3.14159265358 * sigma*sigma) ) * Math.exp( -0.5 * Math.pow( (vz_meas - vz_true )/sigma, 2 ));
	//System.out.println(" PROTON LIKELIHOOD " + likelihood );
	return likelihood;

	
    }

    public static double protonConfLevel( double beta_meas, double beta_true, double p, int sector ){
	//double sigma = 0.02; 


 	List<Double> min_beta = null;
	List<Double> max_beta = null;

	if( sector == 0 ){
	    min_beta = CutLoader.getMinProtonBetaSector1Cut(); // CONTAINS MEAN VALUES HUGE TYP0
	    max_beta = CutLoader.getMaxProtonBetaSector1Cut(); // CONTAINS SIGMA VALUES 
	}
	else if( sector == 1 ){
	    min_beta = CutLoader.getMinProtonBetaSector2Cut();
	    max_beta = CutLoader.getMaxProtonBetaSector2Cut();
	}
	else if( sector == 2 ){
	    min_beta = CutLoader.getMinProtonBetaSector3Cut();
	    max_beta = CutLoader.getMaxProtonBetaSector3Cut();
	}
	else if( sector == 3 ){
	    min_beta = CutLoader.getMinProtonBetaSector4Cut();
	    max_beta = CutLoader.getMaxProtonBetaSector4Cut();
	}
	else if( sector == 4 ){
	    min_beta = CutLoader.getMinProtonBetaSector5Cut();
	    max_beta = CutLoader.getMaxProtonBetaSector5Cut();
	}
	else if( sector == 5 ){
	    min_beta = CutLoader.getMinProtonBetaSector6Cut();
	    max_beta = CutLoader.getMaxProtonBetaSector6Cut();
	}

 	//double sigma = 0.013;
	//double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p); //FORM [a] + [b]/p^(1/2)
	double mean = min_beta.get(0)/Math.sqrt(( 1.0 + Math.pow(min_beta.get(1)/p,2))); //1.0/Math.sqrt(( 1.0 + Math.pow(min_beta.get(0)/p,2))) + min_beta.get(1) + min_beta.get(2)/p ; // FORM 1.0/( 1 + ([a]/x)^2 )^(1/2) + [b] + [c]/x
	double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p);



	double cl = (1 - Erf.erf( Math.abs( beta_meas - mean )/sigma/Math.sqrt(2.0)));///2.0; 
	return cl;
    }

}
