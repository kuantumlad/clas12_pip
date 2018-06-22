package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;

import org.jlab.clas.analysis.clary.Detectors;

class PhotoElectronCut implements BICandidate {

    public boolean candidate( DataEvent event, int rec_i ){

	float nphe = 0;
	//System.out.println(" >> NPHE CUT " );
	if( event.hasBank("REC::Cherenkov") ){
	    //System.out.println(" >> HAS CHERENKOV " );
	    DataBank chkovBank = event.getBank("REC::Cherenkov");
	    for( int i = 0; i < chkovBank.rows(); i++ ){
		int pindex =  chkovBank.getShort("pindex",i); 
		// 15 - HTCC
		//16 - LTCC
		int detector =  chkovBank.getByte("detector",i);  
		
		//System.out.println(" >>  REC::CHERENKOV PINDEX " + pindex );
		if( pindex == rec_i && detector == 15 ){		    
		    nphe = chkovBank.getFloat("nphe",i);
		    //System.out.println(" >> NPHE " + nphe );		    
		}
	    }
	    if( nphe >= 2 ){
		return true;
	    }
	}
	return false;
    }
}


