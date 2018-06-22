package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

class ProtonVertexCut implements BICandidate{

    public boolean candidate( DataEvent event, int rec_index ){

	DataBank recBank = event.getBank("REC::Particle");
	if( event.hasBank("REC::Particle")){
	    double vz = recBank.getFloat("vz",rec_index);	    
	    if( Math.abs(vz) <= 5.0 ){
		return true;
	    }
	}
	return false;
    }


}

