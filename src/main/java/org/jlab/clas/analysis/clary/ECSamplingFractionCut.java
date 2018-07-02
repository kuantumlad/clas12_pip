package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;
import org.jlab.clas.analysis.clary.CutLoader;
import org.jlab.clas.analysis.clary.Detectors;

import java.util.*;
import java.io.*;

class ECSamplingFractionCut implements BICandidate {
        
    public boolean candidate( DataEvent event, int rec_i ){
	
	if( event.hasBank("REC::Calorimeter") ){
	    HashMap<Integer, Double> m_edep = Detectors.getEDepCal( event, rec_i );

	    double edep_tot = 0;// edep_1 + edep_2 + edep_3;
	    
	    DataBank recBank = event.getBank("REC::Particle");	    
	    double p = Calculator.lv_particle(recBank, rec_i, 11).p();
	    int sector = Detectors.getSectorECAL(event, rec_i) - 1;
	    //int sector = recBank.getByte("sector",rec_i);

	    for( Map.Entry<Integer, Double> entry : m_edep.entrySet() ){
		int detector = entry.getKey();
		double edep = entry.getValue();
		edep_tot = edep_tot + edep;
	    }

	    double ec_sf = edep_tot/p;
	    double fit_max_a = 0.0;
	    double fit_max_b = 0.0;
	    double fit_max_c = 0.0;
	    double fit_max_d = 0.0;
	    double fit_max_e = 0.0;
	    double fit_max_f = 0.0;

	    double fit_min_a = 0.0;
	    double fit_min_b = 0.0;
	    double fit_min_c = 0.0;
	    double fit_min_d = 0.0;
	    double fit_min_e = 0.0;
	    double fit_min_f = 0.0;


	    switch( sector ){
 	    case 0:
		//System.out.println(" >> EC SF SECTOR 1 " + CutLoader.getECMaxSector1Cut() );
		fit_max_a = CutLoader.getECMaxSector1Cut().get(0); //-0.000022744;
		fit_max_b = CutLoader.getECMaxSector1Cut().get(1);//0.0;
		fit_max_c = CutLoader.getECMaxSector1Cut().get(2);//0.00516;
		fit_max_d = CutLoader.getECMaxSector1Cut().get(3);//0.000987;
		fit_max_e = CutLoader.getECMaxSector1Cut().get(4);//-0.006289;
		//fit_max_f = CutLoader.getECMaxSector1Cut().get(5);//0.00909;

		fit_min_a = CutLoader.getECMinSector1Cut().get(0); //-0.000022744;
		fit_min_b = CutLoader.getECMinSector1Cut().get(1);//0.0;
		fit_min_c = CutLoader.getECMinSector1Cut().get(2);//0.00516;
		fit_min_d = CutLoader.getECMinSector1Cut().get(3);//0.000987;
		fit_min_e = CutLoader.getECMinSector1Cut().get(4);//-0.006289;
		//fit_min_f = CutLoader.getECMinSector1Cut().get(5);//0.00909;

		break;
	    case 1:
		//System.out.println(" SECTOR 2 " );
		/*		fit_a = CutLoader.getECMaxSector2Cut().get(0); //-0.000022744;
		fit_b = CutLoader.getECMaxSector2Cut().get(1);//0.0;
		fit_c = CutLoader.getECMaxSector2Cut().get(2);//0.00516;
		fit_d = CutLoader.getECMinSector2Cut().get(0);//0.000987;
		fit_e = CutLoader.getECMinSector2Cut().get(1);//-0.006289;
		fit_f = CutLoader.getECMinSector2Cut().get(2);//0.00909;
		*/
		fit_max_a = CutLoader.getECMaxSector2Cut().get(0); //-0.000022744;
		fit_max_b = CutLoader.getECMaxSector2Cut().get(1);//0.0;
		fit_max_c = CutLoader.getECMaxSector2Cut().get(2);//0.00516;
		fit_max_d = CutLoader.getECMaxSector2Cut().get(3);//0.000987;
		fit_max_e = CutLoader.getECMaxSector2Cut().get(4);//-0.006289;
		//fit_max_f = CutLoader.getECMaxSector2Cut().get(5);//0.00909;

		fit_min_a = CutLoader.getECMinSector2Cut().get(0); //-0.000022744;
		fit_min_b = CutLoader.getECMinSector2Cut().get(1);//0.0;
		fit_min_c = CutLoader.getECMinSector2Cut().get(2);//0.00516;
		fit_min_d = CutLoader.getECMinSector2Cut().get(3);//0.000987;
		fit_min_e = CutLoader.getECMinSector2Cut().get(4);//-0.006289;
		//fit_min_f = CutLoader.getECMinSector2Cut().get(5);//0.00909;

		break;
	    case 2:
		//System.out.println(" SECTOR 3 " );
		/*fit_a = CutLoader.getECMaxSector3Cut().get(0); //-0.000022744;
		fit_b = CutLoader.getECMaxSector3Cut().get(1);//0.0;
 		fit_c = CutLoader.getECMaxSector3Cut().get(2);//0.00516;
		fit_d = CutLoader.getECMinSector3Cut().get(0);//0.000987;
		fit_e = CutLoader.getECMinSector3Cut().get(1);//-0.006289;
		fit_f = CutLoader.getECMinSector3Cut().get(2);//0.00909;
		*/
		fit_max_a = CutLoader.getECMaxSector3Cut().get(0); //-0.000022744;
		fit_max_b = CutLoader.getECMaxSector3Cut().get(1);//0.0;
		fit_max_c = CutLoader.getECMaxSector3Cut().get(2);//0.00516;
		fit_max_d = CutLoader.getECMaxSector3Cut().get(3);//0.000987;
		fit_max_e = CutLoader.getECMaxSector3Cut().get(4);//-0.006289;
		//fit_max_f = CutLoader.getECMaxSector3Cut().get(5);//0.00909;

		fit_min_a = CutLoader.getECMinSector3Cut().get(0); //-0.000022744;
		fit_min_b = CutLoader.getECMinSector3Cut().get(1);//0.0;
		fit_min_c = CutLoader.getECMinSector3Cut().get(2);//0.00516;
		fit_min_d = CutLoader.getECMinSector3Cut().get(3);//0.000987;
		fit_min_e = CutLoader.getECMinSector3Cut().get(4);//-0.006289;
		//fit_min_f = CutLoader.getECMinSector3Cut().get(5);//0.00909;

		break;
	    case 3:
		/*fit_a = CutLoader.getECMaxSector4Cut().get(0); //-0.000022744;
		fit_b = CutLoader.getECMaxSector4Cut().get(1);//0.0;
		fit_c = CutLoader.getECMaxSector4Cut().get(2);//0.00516;
		fit_d = CutLoader.getECMinSector4Cut().get(0);//0.000987;
		fit_e = CutLoader.getECMinSector4Cut().get(1);//-0.006289;
		fit_f = CutLoader.getECMinSector4Cut().get(2);//0.00909;
		*/
		fit_max_a = CutLoader.getECMaxSector4Cut().get(0); //-0.000022744;
		fit_max_b = CutLoader.getECMaxSector4Cut().get(1);//0.0;
		fit_max_c = CutLoader.getECMaxSector4Cut().get(2);//0.00516;
		fit_max_d = CutLoader.getECMaxSector4Cut().get(3);//0.000987;
		fit_max_e = CutLoader.getECMaxSector4Cut().get(4);//-0.006289;
		//fit_max_f = CutLoader.getECMaxSector4Cut().get(5);//0.00909;

		fit_min_a = CutLoader.getECMinSector4Cut().get(0); //-0.000022744;
		fit_min_b = CutLoader.getECMinSector4Cut().get(1);//0.0;
		fit_min_c = CutLoader.getECMinSector4Cut().get(2);//0.00516;
		fit_min_d = CutLoader.getECMinSector4Cut().get(3);//0.000987;
		fit_min_e = CutLoader.getECMinSector4Cut().get(4);//-0.006289;
		//fit_min_f = CutLoader.getECMinSector4Cut().get(5);//0.00909;

		//System.out.println(" SECTOR 4 " );		
		break;
	    case 4:
		/*fit_a = CutLoader.getECMaxSector5Cut().get(0); //-0.000022744;
		fit_b = CutLoader.getECMaxSector5Cut().get(1);//0.0;
		fit_c = CutLoader.getECMaxSector5Cut().get(2);//0.00516;
		fit_d = CutLoader.getECMinSector5Cut().get(0);//0.000987;
		fit_e = CutLoader.getECMinSector5Cut().get(1);//-0.006289;
		fit_f = CutLoader.getECMinSector5Cut().get(2);//0.00909;
		*/
		fit_max_a = CutLoader.getECMaxSector5Cut().get(0); //-0.000022744;
		fit_max_b = CutLoader.getECMaxSector5Cut().get(1);//0.0;
		fit_max_c = CutLoader.getECMaxSector5Cut().get(2);//0.00516;
		fit_max_d = CutLoader.getECMaxSector5Cut().get(3);//0.000987;
		fit_max_e = CutLoader.getECMaxSector5Cut().get(4);//-0.006289;
		//fit_max_f = CutLoader.getECMaxSector5Cut().get(5);//0.00909;

		fit_min_a = CutLoader.getECMinSector5Cut().get(0); //-0.000022744;
		fit_min_b = CutLoader.getECMinSector5Cut().get(1);//0.0;
		fit_min_c = CutLoader.getECMinSector5Cut().get(2);//0.00516;
		fit_min_d = CutLoader.getECMinSector5Cut().get(3);//0.000987;
		fit_min_e = CutLoader.getECMinSector5Cut().get(4);//-0.006289;
		//fit_min_f = CutLoader.getECMinSector5Cut().get(5);//0.00909;
		//System.out.println(" SECTOR 5 " );		
		break;
	    case 5:
		/*fit_a = CutLoader.getECMaxSector6Cut().get(0); //-0.000022744;
		fit_b = CutLoader.getECMaxSector6Cut().get(1);//0.0;
		fit_c = CutLoader.getECMaxSector6Cut().get(2);//0.00516;
		fit_d = CutLoader.getECMinSector6Cut().get(0);//0.000987;
		fit_e = CutLoader.getECMinSector6Cut().get(1);//-0.006289;
		fit_f = CutLoader.getECMinSector6Cut().get(2);//0.00909;
		*/
		fit_max_a = CutLoader.getECMaxSector6Cut().get(0); //-0.000022744;
		fit_max_b = CutLoader.getECMaxSector6Cut().get(1);//0.0;
		fit_max_c = CutLoader.getECMaxSector6Cut().get(2);//0.00516;
		fit_max_d = CutLoader.getECMaxSector6Cut().get(3);//0.000987;
		fit_max_e = CutLoader.getECMaxSector6Cut().get(4);//-0.006289;
		//fit_max_f = CutLoader.getECMaxSector6Cut().get(5);//0.00909;

		fit_min_a = CutLoader.getECMinSector6Cut().get(0); //-0.000022744;
		fit_min_b = CutLoader.getECMinSector6Cut().get(1);//0.0;
		fit_min_c = CutLoader.getECMinSector6Cut().get(2);//0.00516;
		fit_min_d = CutLoader.getECMinSector6Cut().get(3);//0.000987;
		fit_min_e = CutLoader.getECMinSector6Cut().get(4);//-0.006289;
		//fit_min_f = CutLoader.getECMinSector6Cut().get(5);//0.00909;
		//System.out.println(" SECTOR 6 " );	       
		break;
	    default:
		fit_max_a = 0.0;
		fit_max_b = 0.0;
		fit_max_c = 0.0;
		fit_max_d = 0.0;
		fit_max_e = 0.0;
		fit_max_f = 0.0;

		fit_min_a = 0.0;
		fit_min_b = 0.0;
		fit_min_c = 0.0;
		fit_min_d = 0.0;
		fit_min_e = 0.0;
		fit_min_f = 0.0;
		//System.out.println("no sector?");
	    }

	    ///////////////////////////////////////////////////////////////////
	    ///CHANGE TO THE PHYSICALLY MOTIVATED CUTS	  
	    //double sf_limit_upper = fit_a + fit_b/p + fit_c/(p*p);
	    //double sf_limit_lower = fit_d + fit_e/p + fit_f/(p*p);

	    double sf_limit_upper = fit_max_a + fit_max_b/(p-0.1) + fit_max_c + fit_max_d/(Math.sqrt(p-0.1)) + fit_max_e/p;
	    double sf_limit_lower = fit_min_a + fit_min_b/(p-0.1) - fit_min_c - fit_min_d/(Math.sqrt(p-0.1)) - fit_min_e/p;

	    //	    System.out.println(" >> UPPER " + sf_limit_upper + " LOWER " + sf_limit_lower );

	    boolean sf_upper_pass = sf_limit_upper > ec_sf;
	    boolean sf_lower_pass = sf_limit_lower < ec_sf;
	    //System.out.println(" >> etot " + edep_tot + " p " + p );
	    //System.out.println(" >> SAMPLING FRACTION " + ec_sf );
	    //check here if ectot vs p is between max and min sigma cuts

	    if ( sf_upper_pass && sf_lower_pass ){
		return true;
	    }
	}
	return false;
    }   
}
