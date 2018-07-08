package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;


class MinMomentumCut implements BICandidate {

    public boolean candidate( DataEvent event, int rec_i ){

	
	DataBank recbank = event.getBank("REC::Particle");
	if( event.hasBank("REC::Particle") ){
	    float rec_px = recbank.getFloat("px",rec_i);
	    float rec_py = recbank.getFloat("py",rec_i);
	    float rec_pz = recbank.getFloat("pz",rec_i);

	    double momentum = Math.sqrt( rec_px*rec_px + rec_py*rec_py + rec_pz*rec_pz );

	    double min_mntm = 0.5;
	    double eBeam = PhysicalConstants.eBeam;
	    if( eBeam < 4 ){
		min_mntm = 0.5;
	    }
	    else if( eBeam > 4 && eBeam < 7 ){
		min_mntm = 1.0;
	    }
	    else if( eBeam >= 7 ){
		min_mntm = 1.5;
	    }
	    
	    
	    if( momentum > min_mntm ){
		//System.out.println(" >> IN ELECTRON CHARGE CUT " + charge  );	    
		return true;
	    }
	}
	return false;	
    }

}
