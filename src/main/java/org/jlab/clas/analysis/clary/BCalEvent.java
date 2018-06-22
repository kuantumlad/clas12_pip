package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.io.hipo.HipoDataSource;

public class BCalEvent{

    private DataEvent event;
    private DataBank calBank = null;
    private DataBank recBank = null;

    public BCalEvent( DataEvent temp_ev ){
	event = temp_ev;
    }

    public void loadCalBank( BEventInfo bev ){
	
	if( event.hasBank("REC::Calorimeter") && event.hasBank("REC::Particle") ){
	    System.out.println(" >> HAS CAL BANK " );
	    calBank = event.getBank("REC::Calorimeter");
	    recBank = event.getBank("REC::Particle");
	    setBankMaps( bev.recCalBankMap, calBank, recBank, "pindex");
	    bev.calBank = getCalBank();

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

    public DataBank getCalBank(){
	return calBank;
    }

    public void setEventInfo(){


    }
    



}
