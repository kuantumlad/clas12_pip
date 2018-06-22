package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import org.jlab.clas.analysis.clary.Detectors;

class ECALInnerOuterCut implements BICandidate {

    public boolean candidate( DataEvent event, int rec_i ){

	double edep_ei = 0.0;
	double edep_eo = 0.0;
	if(event.hasBank("REC::Calorimeter")){
	    
	    Map<Integer, Double> m_edep = Detectors.getEDepCal( event, rec_i );
	    for( Map.Entry<Integer,Double> entry : m_edep.entrySet() ){
		int layer = entry.getKey();
		double edep = entry.getValue();
		if( layer == Detectors.ec_ei ){
		    edep_ei = edep;
		}
		if( layer == Detectors.ec_eo ){
		    edep_eo = edep;
		}
	    }
	    double edep_tot = edep_ei + edep_eo;
	    if( edep_ei > 0.05 ){
		return true;
	    }	    
	}
	return false;
    }


}
