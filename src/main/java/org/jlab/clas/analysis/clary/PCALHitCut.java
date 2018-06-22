package org.jlab.clas.analysis.clary;

import java.util.*;
import java.io.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import org.jlab.clas.analysis.clary.Detectors;

class PCALHitCut implements BICandidate {

    public boolean candidate( DataEvent event, int rec_i ){
	//System.out.println(" >> PCAL TEST " );
	ArrayList< Double > pcal_hit = new ArrayList();
	if( event.hasBank("REC::Calorimeter") ){
	    DataBank ecBank = event.getBank("REC::Calorimeter");	    
	    //System.out.println(" >> HAS PCAL HIT " );
	    //pcal_hit = Detectors.PCALHit(event, rec_i);
	    //CHANGE LATER
	    HashMap<Integer,Double> m_edep = Detectors.getEDepCal( event, rec_i );
	    double edep_tot = 0;

	    for( Map.Entry<Integer, Double> entry : m_edep.entrySet() ){
		int detector = entry.getKey();
		double edep = entry.getValue();
		//System.out.println(" >> MAP KEY " + detector + " MAP VALUE " + edep );
		if( detector == 1 ){ edep_tot = edep; }
		
	    }

	    if( edep_tot >= 0.07 ){
		//System.out.println(" >> PCAL ENERGY DEP IS " + edep_tot );
		return true;
	    }

	}
	return false;			      
    }
}
    
