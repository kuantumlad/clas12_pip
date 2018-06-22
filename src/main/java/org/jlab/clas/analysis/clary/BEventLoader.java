package org.jlab.clas.analysis.clary;                                                                                                                                                                                                                                           
import org.jlab.io.base.DataEvent;
import java.io.*;
import java.util.*;


public class BEventLoader{

    public BEventInfo bev = new BEventInfo();

    private BScintEvent scintevent = null;
    DataEvent event = null;
    
    public BEventLoader( DataEvent temp_event ){
	//System.out.println(" >> LOAD EVENT INFORMATION FROM BANKS ");
	event = temp_event;
	bev.bevEvent = temp_event;
	scintevent = new BScintEvent(temp_event);

    }

    public void setBEventInfo(){

	setEventParameters();
	scintevent.loadScintBank(bev);	
	setRECBankInfo();

    }

    public BEventInfo getBEventInfo(){

	return bev;

    }

    public void setRECBankInfo(){
	bev.recBank = event.getBank("REC::Particle");
    }


    public void setEventParameters(){

	if( event.hasBank("REC::Event") ){
	    bev.start_time = event.getBank("REC::Event").getFloat("STTime",0);
	}

    }


}
