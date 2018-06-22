package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import java.util.*;
import java.io.*;

class CLAS12ElectronPIDCut implements BICandidate{

    public boolean candidate(DataEvent tempdevent, int rec_i){
	//System.out.println("Applying Charge Cuts" );
	
	DataBank recdbank = tempdevent.getBank("REC::Particle");
	
	int rec_pid = recdbank.getInt("pid",rec_i);
	
	if( rec_pid == 11 ){
	    return true;
	}
	return false;
    }
    


}
