package org.jlab.clas.analysis.clary;

import java.util.*;
import java.io.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.clas.analysis.clary.Detectors;

class CalorimeterCut implements BICandidate {
    
    public boolean candidate( DataEvent event, int rec_i ){
	
	ArrayList< Double > pcal_hit = new ArrayList();
	HashMap<Integer,ArrayList< Double >> m_ec_hit = new HashMap<Integer, ArrayList<Double> >();
	//System.out.println(" >> cal test " );
	if( event.hasBank("REC::Calorimeter") ){
	    DataBank ecBank = event.getBank("REC::Calorimeter");	    
	    pcal_hit = Detectors.PCALHit(event, rec_i);
	    m_ec_hit = Detectors.ECHit(event, rec_i);
	    
	    //if( pcal_hit.size() > 0  || m_ec_hit.size() > 0 ){
	    return true;
		//}
	}
	return false;			      
    }
}
