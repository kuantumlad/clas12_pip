package org.jlab.clas.analysis.clary;


import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import java.io.*; 
import java.util.*;

class FTOFHitCut implements BICandidate {

    public boolean candidate( DataEvent event, int rec_index ){
	//System.out.println(">>FTOFCUT - index " + rec_index );
	DataBank recBank = event.getBank("REC::Particle");
	boolean scintBank_present = event.hasBank("REC::Scintillator");
	if( scintBank_present ){
	    //System.out.println(" >> SCINT bank present " );
	    DataBank scintBank = event.getBank("REC::Scintillator");
	    for( int i = 0; i < scintBank.rows(); i++ ){
		int pindex = scintBank.getShort("pindex",i);
		int scint_detector = scintBank.getInt("detector",i);
		int layer = scintBank.getInt("layer",i);
		if( pindex == rec_index ){
		    //System.out.println(" >> element " + i + " pindex " + pindex + " detector " + scint_detector );		
		    //return true;		    
		    if( (scint_detector == 12) ){
			//System.out.println( " >>> PASSING element " + i + " pindex " + pindex + " detector " + scint_detector ); 
			if( layer == 1 || layer == 2 ){
			    //System.out.println(" >> SCINT HIT LAYER " + layer );
			    return true;
		    	}
		    }
		}
	    }
	}
	return false;
    }
    
}

