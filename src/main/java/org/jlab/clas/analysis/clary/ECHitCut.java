package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;

import java.util.*;
import java.io.*;
import org.jlab.clas.analysis.clary.Detectors;

class ECHitCut implements BICandidate {
    
    public boolean candidate( DataEvent event, int rec_i ){
	
	HashMap<Integer,ArrayList< Double >> m_ec_hit = new HashMap<Integer, ArrayList<Double> >();
	//System.out.println(" >> TESTING EC HIT POS " );
	if( event.hasBank("REC::Calorimeter") ){
	    DataBank ecBank = event.getBank("REC::Calorimeter");
	    //System.out.println(" >> HAS CAL BANK " );
	    m_ec_hit = Detectors.ECHit(event, rec_i);
	    if( m_ec_hit.size() > 0 ){
		//System.out.println(" HIT EI OR EO " );
		for(Map.Entry<Integer, ArrayList<Double> > entry : m_ec_hit.entrySet() ){
		    ArrayList<Double> hit_pos = new ArrayList();
		    int detector = entry.getKey();
		    hit_pos = entry.getValue();
		    if( hit_pos.size() > 0 ){
			//System.out.println(" >> EC HIT  " + detector + " " + hit_pos.get(0) + " " +  hit_pos.get(1) + " " +  hit_pos.get(2) );		    
		    }
		}
		return true;
	    }
	}
	return false;			          	
    }    
}
