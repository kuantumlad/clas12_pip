package org.jlab.clas.analysis.clary;

import org.jlab.clas.analysis.clary.MatchingElectronPID;
import org.jlab.clas.analysis.clary.MatchingProtonPID;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

public class MatchingProcessor{

    MatchingElectronPID match_el = new MatchingElectronPID();
    MatchingProtonPID match_pr = new MatchingProtonPID();

    public void initializeCuts(){

	match_el.initializeCuts();

    }

    public boolean processEvent(String particle, DataEvent event, int rec_i){
	boolean result = false;
	if( particle == "electron"){
	    if( match_el.processCuts(event, rec_i) ){ return true; }	    
	}

	if( particle == "proton"){
	    if( match_pr.processCuts(event, rec_i) ){ return true; }
	}
	return result;
    }

    public void processResult( DataEvent event, int rec_i ){
	System.out.println(" >> PROCESSING RESULT" );
	match_el.getResult( event );
	

    }


}
