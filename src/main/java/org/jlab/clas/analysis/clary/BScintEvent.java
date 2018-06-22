package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.io.hipo.HipoDataSource;

public class BScintEvent{

    private DataEvent event;
    private DataBank scintBank = null;
    private DataBank recBank = null;

    public BScintEvent( DataEvent temp_ev ){
	event = temp_ev;
    }

    public void loadScintBank( BEventInfo bev ){
	
	if( event.hasBank("REC::Scintillator") && event.hasBank("REC::Particle") ){
	    //System.out.println(" >> HAS SCINT BANK " );
	    scintBank = event.getBank("REC::Scintillator");
	    recBank = event.getBank("REC::Particle");
	    setBankMaps( bev.recScintBankMap, scintBank, recBank, "pindex");
	    bev.scintBank = getScintBank();

	}
    }
    
    public void setBankMaps( Map<Integer, List<Integer> > temp_map, DataBank fromBank, DataBank toBank, String idxVarName ){
	temp_map.clear();
	if( fromBank == null ) return;
	if( toBank == null ) return;
	for( int i = 0; i < fromBank.rows(); i++ ){
	    final int iTo = fromBank.getInt(idxVarName,i);
	    if( temp_map.containsKey(iTo) ){
		temp_map.get(iTo).add(i);
	    }
	    else{
		List<Integer> iFrom = new ArrayList<Integer>();
		temp_map.put(iTo,iFrom);
		temp_map.get(iTo).add(i);
	    }
	}
    }

    public DataBank getScintBank(){
	return scintBank;
    }

    public void setEventInfo(){


    }
    



}

