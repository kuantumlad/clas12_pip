package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;


class VertexCut implements BICandidate {

    public boolean candidate( DataEvent event, int rec_index ){

	DataBank recBank = event.getBank("REC::Particle");
	double vz = recBank.getFloat("vz",rec_index);
	//System.out.println(" >> IN ELECTRON CHARGE CUT " + charge  );	    
	int sector = Detectors.getSectorPCAL(event, rec_index) - 1; // PROBS NOT SAME AS SD
 //USE DC SECTOR TRACK REC IN BECAUSE STEFAN DOES IT. SHOULD USE RECTRACK
	
	double min_vz = 0.0;
	double max_vz = 0.0;
	
	switch(sector){
	case 0:
	    min_vz = CutLoader.getVertexCutSector1().get(0);
	    max_vz = CutLoader.getVertexCutSector1().get(1);
	    break;
	case 1:
	    min_vz = CutLoader.getVertexCutSector2().get(0);
	    max_vz = CutLoader.getVertexCutSector2().get(1);
	    break;
	case 2:
	    min_vz = CutLoader.getVertexCutSector3().get(0);
	    max_vz = CutLoader.getVertexCutSector3().get(1);
	    break;
	case 3:
	    min_vz = CutLoader.getVertexCutSector4().get(0);
	    max_vz = CutLoader.getVertexCutSector4().get(1);
	    break;
	case 4:
	    min_vz = CutLoader.getVertexCutSector5().get(0);
	    max_vz = CutLoader.getVertexCutSector5().get(1);
	    break;
	case 5:
	    min_vz = CutLoader.getVertexCutSector6().get(0);
	    max_vz = CutLoader.getVertexCutSector6().get(1);
	    break;
	    
	default:
	    min_vz=0.0;
	    max_vz=0.0;
	}
	System.out.println(" >> " + min_vz + " " + max_vz );
	if( vz <= max_vz && vz >= min_vz ){
	    //System.out.println(" >> PASSED ELECTRON CHARGE CUT " + charge  );	   
	    return true;
	}
	return false;
    }

}

