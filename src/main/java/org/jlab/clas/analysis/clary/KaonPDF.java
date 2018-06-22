package org.jlab.clas.analysis.clary;

import java.util.*;
import java.io.*;
import org.apache.commons.math3.special.Erf;

///////////////////////////////////
//WHERE ALL THE PROTON PDFS LIVE
//FOR MLE CALCULATIONS

class KaonPDF{

    public KaonPDF(){

    }

    public static double kaonBetaMLE( double beta_meas, double beta_true, double p, int sector ){
	///////meas is using tof and path length while beta_true is from the mass and mntm of the particle

 	List<Double> min_beta = null;
	List<Double> max_beta = null;

	if( sector == 0 ){
	    min_beta = CutLoader.getMinKPBetaSector1Cut(); // CONTAINS MEAN VALUES HUGE TYPE 
	    max_beta = CutLoader.getMaxKPBetaSector1Cut(); // CONTAINS SIGMA VALUES 
	}
	else if( sector == 1 ){
	    min_beta = CutLoader.getMinKPBetaSector2Cut();
	    max_beta = CutLoader.getMaxKPBetaSector2Cut();
	}
	else if( sector == 2 ){
	    min_beta = CutLoader.getMinKPBetaSector3Cut();
	    max_beta = CutLoader.getMaxKPBetaSector3Cut();
	}
	else if( sector == 3 ){
	    min_beta = CutLoader.getMinKPBetaSector4Cut();
	    max_beta = CutLoader.getMaxKPBetaSector4Cut();
	}
	else if( sector == 4 ){
	    min_beta = CutLoader.getMinKPBetaSector5Cut();
	    max_beta = CutLoader.getMaxKPBetaSector5Cut();
	}
	else if( sector == 5 ){
	    min_beta = CutLoader.getMinKPBetaSector6Cut();
	    max_beta = CutLoader.getMaxKPBetaSector6Cut();
	}


 	//double sigma = 0.013;

	//	System.out.println(">> SIG FIT VALUES " + max_beta.get(0) + " " + max_beta.get(1) );
	//System.out.println(">> MEAN FIT VALUES " + min_beta.get(0) + " " + min_beta.get(1) + " " + min_beta.get(2) );

	//double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p); //FORM [a] + [b]/p^(1/2)
	//double mean = 1.0/Math.sqrt(( 1.0 + Math.pow(min_beta.get(0)/p,2))) + min_beta.get(1) + min_beta.get(2)/p ; // FORM 1.0/( 1 + ([a]/x)^2 )^(1/2) + [b] + [c]/x

	//double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p); //FORM [a] + [b]/p^(1/2)
	double mean = min_beta.get(0)/Math.sqrt(( 1.0 + Math.pow(min_beta.get(1)/p,2))); //1.0/Math.sqrt(( 1.0 + Math.pow(min_beta.get(0)/p,2))) + min_beta.get(1) + min_beta.get(2)/p ; // FORM 1.0/( 1 + ([a]/x)^2 )^(1/2) + [b] + [c]/x
	double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p);


	//System.out.println(" >> " + mean + " SIGMA " + sigma );

	double likelihood = (1.0/ (Math.sqrt(2.0 * 3.14159265358 * sigma*sigma)) ) * Math.exp( -0.5 * Math.pow((beta_meas - mean)/sigma, 2 ));
	
	return likelihood;	
    }

    public static double kaonBetaConfLevel( double beta_meas, double beta_true, double p, int sector){

 	List<Double> min_beta = null;
	List<Double> max_beta = null;

	if( sector == 0 ){
	    min_beta = CutLoader.getMinKPBetaSector1Cut(); // CONTAINS MEAN VALUES HUGE TYPE 
	    max_beta = CutLoader.getMaxKPBetaSector1Cut(); // CONTAINS SIGMA VALUES 
	}
	else if( sector == 1 ){
	    min_beta = CutLoader.getMinKPBetaSector2Cut();
	    max_beta = CutLoader.getMaxKPBetaSector2Cut();
	}
	else if( sector == 2 ){
	    min_beta = CutLoader.getMinKPBetaSector3Cut();
	    max_beta = CutLoader.getMaxKPBetaSector3Cut();
	}
	else if( sector == 3 ){
	    min_beta = CutLoader.getMinKPBetaSector4Cut();
	    max_beta = CutLoader.getMaxKPBetaSector4Cut();
	}
	else if( sector == 4 ){
	    min_beta = CutLoader.getMinKPBetaSector5Cut();
	    max_beta = CutLoader.getMaxKPBetaSector5Cut();
	}
	else if( sector == 5 ){
	    min_beta = CutLoader.getMinKPBetaSector6Cut();
	    max_beta = CutLoader.getMaxKPBetaSector6Cut();
	}


 	//double sigma = 0.013;
	//double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p); //FORM [a] + [b]/p^(1/2)
	//double mean = 1.0/Math.sqrt(( 1.0 + Math.pow(min_beta.get(0)/p,2))) + min_beta.get(1) + min_beta.get(2)/p ; // FORM 1.0/( 1 + ([a]/x)^2 )^(1/2) + [b] + [c]/x

	//double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p); //FORM [a] + [b]/p^(1/2)
	double mean = min_beta.get(0)/Math.sqrt(( 1.0 + Math.pow(min_beta.get(1)/p,2))); //1.0/Math.sqrt(( 1.0 + Math.pow(min_beta.get(0)/p,2))) + min_beta.get(1) + min_beta.get(2)/p ; // FORM 1.0/( 1 + ([a]/x)^2 )^(1/2) + [b] + [c]/x
	double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p);

	//double sigma = 0.013; // 0.0005 BEFORE
	double conf = (1 - Erf.erf( Math.abs( beta_meas - mean)/sigma/Math.sqrt(2.0)));
	//System.out.println(" >> KAON CONF " + conf );
	return conf;
    }
    //public static double KaonVertexMLE( double beta ){
    ///////////////////////////////////////////
    //CHANGE THE PARAMETERS HERE FOR VERTEX MLE
    ///double likelihood = (1.0/ Math.sqrt(2.0*3.14159265358 * sigma*sigma) ) * Math.exp( -0.5 * Math.pow( (beta_pr - pr_beta_mntm )/sigma, 2 ));
    //return likelihood;	
    //}



}
