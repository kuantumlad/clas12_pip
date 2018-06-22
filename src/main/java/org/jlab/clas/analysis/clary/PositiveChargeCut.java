package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

class PositiveChargeCut implements BICandidate{


    public boolean candidate( DataEvent event, int rec_index ){

	DataBank recBank = event.getBank("REC::Particle");
	if( event.hasBank("REC::Particle")){
	    int charge = recBank.getInt("charge",rec_index);
	    if( charge > 0){
		return true;
	    }
	}
	return false;
    }


}
