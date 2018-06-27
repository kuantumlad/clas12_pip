package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;


class HTCCCut implements BICandidate {

    public boolean candidate( DataEvent event, int rec_index ){

	double nphe = Detectors.getCherenkovNPHE( event, rec_index );


	if( nphe > 2 ){
	    //System.out.println(" >> IN ELECTRON CHARGE CUT " + charge  );	    
	    return true;
	}
	return false;
    }

}
