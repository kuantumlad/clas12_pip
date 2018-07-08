package org.jlab.clas.analysis.clary;

import java.util.*;
import java.io.*;
import org.apache.commons.math3.special.Erf;


class PionPlusPDF{ // SHOULD JUST BE CALLED PionPDF.java 

    public PionPlusPDF(){

    }

    public static double pionPlusBetaMLE( double beta_meas, double beta_true, double p, int sector ){
	///////meas is using tof and path length while beta_true is from the mass and mntm of the particle
 	List<Double> min_beta = null;
	List<Double> max_beta = null;

	if( sector == 0 ){
	    min_beta = CutLoader.getMinPIPBetaSector1Cut(); // CONTAINS MEAN VALUES HUGE TYPE 
	    max_beta = CutLoader.getMaxPIPBetaSector1Cut(); // CONTAINS SIGMA VALUES 
	}
	else if( sector == 1 ){
	    min_beta = CutLoader.getMinPIPBetaSector2Cut();
	    max_beta = CutLoader.getMaxPIPBetaSector2Cut();
	}
	else if( sector == 2 ){
	    min_beta = CutLoader.getMinPIPBetaSector3Cut();
	    max_beta = CutLoader.getMaxPIPBetaSector3Cut();
	}
	else if( sector == 3 ){
	    min_beta = CutLoader.getMinPIPBetaSector4Cut();
	    max_beta = CutLoader.getMaxPIPBetaSector4Cut();
	}
	else if( sector == 4 ){
	    min_beta = CutLoader.getMinPIPBetaSector5Cut();
	    max_beta = CutLoader.getMaxPIPBetaSector5Cut();
	}
	else if( sector == 5 ){
	    min_beta = CutLoader.getMinPIPBetaSector6Cut();
	    max_beta = CutLoader.getMaxPIPBetaSector6Cut();
	}

 	//double sigma = 0.013;
	//	double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p); //FORM [a] + [b]/p^(1/2)
	//double mean = 1.0/Math.sqrt(( 1.0 + Math.pow(min_beta.get(0)/p,2))) + min_beta.get(1) + min_beta.get(2)/p ; // FORM 1.0/( 1 + ([a]/x)^2 )^(1/2) + [b] + [c]/x
	
	//double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p); //FORM [a] + [b]/p^(1/2)
	double mean = min_beta.get(0)/Math.sqrt(( 1.0 + Math.pow(min_beta.get(1)/p,2))); //1.0/Math.sqrt(( 1.0 + Math.pow(min_beta.get(0)/p,2))) + min_beta.get(1) + min_beta.get(2)/p ; // FORM 1.0/( 1 + ([a]/x)^2 )^(1/2) + [b] + [c]/x
	double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p);


	//double sigma = 0.012;
	double likelihood = (1.0/ (sigma*Math.sqrt(2.0*3.14159265358)) ) * Math.exp( -(1.0/2.0) * Math.pow((beta_meas - mean)/sigma, 2 ));
	return likelihood;
	
    }

    public static double pionPlusBetaConfLevel( double beta_meas, double beta_true, double p, int sector ){

 	List<Double> min_beta = null;
	List<Double> max_beta = null;

	if( sector == 0 ){
	    min_beta = CutLoader.getMinPIPBetaSector1Cut(); // CONTAINS MEAN VALUES HUGE TYPE 
	    max_beta = CutLoader.getMaxPIPBetaSector1Cut(); // CONTAINS SIGMA VALUES 
	}
	else if( sector == 1 ){
	    min_beta = CutLoader.getMinPIPBetaSector2Cut();
	    max_beta = CutLoader.getMaxPIPBetaSector2Cut();
	}
	else if( sector == 2 ){
	    min_beta = CutLoader.getMinPIPBetaSector3Cut();
	    max_beta = CutLoader.getMaxPIPBetaSector3Cut();
	}
	else if( sector == 3 ){
	    min_beta = CutLoader.getMinPIPBetaSector4Cut();
	    max_beta = CutLoader.getMaxPIPBetaSector4Cut();
	}
	else if( sector == 4 ){
	    min_beta = CutLoader.getMinPIPBetaSector5Cut();
	    max_beta = CutLoader.getMaxPIPBetaSector5Cut();
	}
	else if( sector == 5 ){
	    min_beta = CutLoader.getMinPIPBetaSector6Cut();
	    max_beta = CutLoader.getMaxPIPBetaSector6Cut();
	}


 	//double sigma = 0.013;
	//	double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p); //FORM [a] + [b]/p^(1/2)
	//	double mean = 1.0/Math.sqrt(( 1.0 + Math.pow(min_beta.get(0)/p,2))) + min_beta.get(1) + min_beta.get(2)/p ; // FORM 1.0/( 1 + ([a]/x)^2 )^(1/2) + [b] + [c]/x

	//double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p); //FORM [a] + [b]/p^(1/2)
	double mean = min_beta.get(0)/Math.sqrt(( 1.0 + Math.pow(min_beta.get(1)/p,2))); //1.0/Math.sqrt(( 1.0 + Math.pow(min_beta.get(0)/p,2))) + min_beta.get(1) + min_beta.get(2)/p ; // FORM 1.0/( 1 + ([a]/x)^2 )^(1/2) + [b] + [c]/x
	double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p);

	//double sigma = 0.012; //0.004 BEFORE
	double conf = (1 - Erf.erf( Math.abs( beta_meas - mean )/sigma/Math.sqrt(2.0)));
	//System.out.println(" >> PION PLUS CONF " + conf );
	return conf;
    }


    //public static double KaonVertexMLE( double beta ){
    ///////////////////////////////////////////
    //CHANGE THE PARAMETERS HERE FOR VERTEX MLE
    ///double likelihood = (1.0/ Math.sqrt(2.0*3.14159265358 * sigma*sigma) ) * Math.exp( -0.5 * Math.pow( (beta_pr - pr_beta_mntm )/sigma, 2 ));
    //return likelihood;	
    //}


    public static double pionMinusBetaMLE( double beta_meas, double beta_true, double p, int sector ){
	///////meas is using tof and path length while beta_true is from the mass and mntm of the particle
 	List<Double> min_beta = null;
	List<Double> max_beta = null;

	if( sector == 0 ){
	    min_beta = CutLoader.getMinPIMBetaSector1Cut(); // CONTAINS MEAN VALUES HUGE TYPE 
	    max_beta = CutLoader.getMaxPIMBetaSector1Cut(); // CONTAINS SIGMA VALUES 
	}
	else if( sector == 1 ){
	    min_beta = CutLoader.getMinPIMBetaSector2Cut();
	    max_beta = CutLoader.getMaxPIMBetaSector2Cut();
	}
	else if( sector == 2 ){
	    min_beta = CutLoader.getMinPIMBetaSector3Cut();
	    max_beta = CutLoader.getMaxPIMBetaSector3Cut();
	}
	else if( sector == 3 ){
	    min_beta = CutLoader.getMinPIMBetaSector4Cut();
	    max_beta = CutLoader.getMaxPIMBetaSector4Cut();
	}
	else if( sector == 4 ){
	    min_beta = CutLoader.getMinPIMBetaSector5Cut();
	    max_beta = CutLoader.getMaxPIMBetaSector5Cut();
	}
	else if( sector == 5 ){
	    min_beta = CutLoader.getMinPIMBetaSector6Cut();
	    max_beta = CutLoader.getMaxPIMBetaSector6Cut();
	}

 	//double sigma = 0.013;
	//	double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p); //FORM [a] + [b]/p^(1/2)
	//double mean = 1.0/Math.sqrt(( 1.0 + Math.pow(min_beta.get(0)/p,2))) + min_beta.get(1) + min_beta.get(2)/p ; // FORM 1.0/( 1 + ([a]/x)^2 )^(1/2) + [b] + [c]/x
	
	//double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p); //FORM [a] + [b]/p^(1/2)
	double mean = min_beta.get(0)/Math.sqrt(( 1.0 + Math.pow(min_beta.get(1)/p,2))); //1.0/Math.sqrt(( 1.0 + Math.pow(min_beta.get(0)/p,2))) + min_beta.get(1) + min_beta.get(2)/p ; // FORM 1.0/( 1 + ([a]/x)^2 )^(1/2) + [b] + [c]/x
	double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p);


	//double sigma = 0.012;
	double likelihood = (1.0/ (sigma*Math.sqrt(2.0*3.14159265358)) ) * Math.exp( -(1.0/2.0) * Math.pow((beta_meas - mean)/sigma, 2 ));
	return likelihood;
	
    }

    public static double pionMinusBetaConfLevel( double beta_meas, double beta_true, double p, int sector ){

 	List<Double> min_beta = null;
	List<Double> max_beta = null;

	if( sector == 0 ){
	    min_beta = CutLoader.getMinPIMBetaSector1Cut(); // CONTAINS MEAN VALUES HUGE TYPE 
	    max_beta = CutLoader.getMaxPIMBetaSector1Cut(); // CONTAINS SIGMA VALUES 
	}
	else if( sector == 1 ){
	    min_beta = CutLoader.getMinPIMBetaSector2Cut();
	    max_beta = CutLoader.getMaxPIMBetaSector2Cut();
	}
	else if( sector == 2 ){
	    min_beta = CutLoader.getMinPIMBetaSector3Cut();
	    max_beta = CutLoader.getMaxPIMBetaSector3Cut();
	}
	else if( sector == 3 ){
	    min_beta = CutLoader.getMinPIMBetaSector4Cut();
	    max_beta = CutLoader.getMaxPIMBetaSector4Cut();
	}
	else if( sector == 4 ){
	    min_beta = CutLoader.getMinPIMBetaSector5Cut();
	    max_beta = CutLoader.getMaxPIMBetaSector5Cut();
	}
	else if( sector == 5 ){
	    min_beta = CutLoader.getMinPIMBetaSector6Cut();
	    max_beta = CutLoader.getMaxPIMBetaSector6Cut();
	}


 	//double sigma = 0.013;
	//	double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p); //FORM [a] + [b]/p^(1/2)
	//	double mean = 1.0/Math.sqrt(( 1.0 + Math.pow(min_beta.get(0)/p,2))) + min_beta.get(1) + min_beta.get(2)/p ; // FORM 1.0/( 1 + ([a]/x)^2 )^(1/2) + [b] + [c]/x

	//double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p); //FORM [a] + [b]/p^(1/2)
	double mean = min_beta.get(0)/Math.sqrt(( 1.0 + Math.pow(min_beta.get(1)/p,2))); //1.0/Math.sqrt(( 1.0 + Math.pow(min_beta.get(0)/p,2))) + min_beta.get(1) + min_beta.get(2)/p ; // FORM 1.0/( 1 + ([a]/x)^2 )^(1/2) + [b] + [c]/x
	double sigma = max_beta.get(0) + max_beta.get(1)/Math.sqrt(p);

	//double sigma = 0.012; //0.004 BEFORE
	double conf = (1 - Erf.erf( Math.abs( beta_meas - mean )/sigma/Math.sqrt(2.0)));
	//System.out.println(" >> PION PLUS CONF " + conf );
	return conf;
    }


    //public static double pionPlusVertexMLE( double beta ){
    ///////////////////////////////////////////
    //CHANGE THE PARAMETERS HERE FOR VERTEX MLE
    ///double likelihood = (1.0/ Math.sqrt(2.0*3.14159265358 * sigma*sigma) ) * Math.exp( -0.5 * Math.pow( (beta_pr - pr_beta_mntm )/sigma, 2 ));
    //return likelihood;	
    //}



}

