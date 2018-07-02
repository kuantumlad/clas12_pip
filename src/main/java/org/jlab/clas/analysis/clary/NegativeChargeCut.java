package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;


class NegativeChargeCut implements BICandidate {

    public boolean candidate( DataEvent event, int rec_index ){

	DataBank recBank = event.getBank("REC::Particle");
	int charge = (int)recBank.getByte("charge",rec_index);
	//System.out.println(" >> IN ELECTRON CHARGE CUT " + charge  );	    
	if( charge == -1 ){
	    //System.out.println(" >> PASSED ELECTRON CHARGE CUT " + charge  );
	    
	    return true;
	}
	return false;
    }

}
