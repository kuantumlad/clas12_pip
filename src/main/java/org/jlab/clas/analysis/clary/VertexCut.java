package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;


class VertexCut implements BICandidate {

    public boolean candidate( DataEvent event, int rec_index ){

	DataBank recBank = event.getBank("REC::Particle");
	double vz = recBank.getFloat("vz",rec_index);
	//System.out.println(" >> IN ELECTRON CHARGE CUT " + charge  );	    
	if( vz <= 11.0 && vz >= -7.0 ){
	    //System.out.println(" >> PASSED ELECTRON CHARGE CUT " + charge  );	   
	    return true;
	}
	return false;
    }

}

