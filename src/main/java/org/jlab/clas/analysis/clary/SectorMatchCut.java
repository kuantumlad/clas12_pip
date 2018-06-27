package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;


class SectorMatchCut implements BICandidate {

    public boolean candidate( DataEvent event, int rec_index ){

	DataBank recBank = event.getBank("REC::Particle");
	int sector_pcal = Detectors.getSectorPCAL( event, rec_index );
	int sector_dc = Detectors.getDCTrajSect(event, rec_index, 12) - 1; // PROBS NOT SAME AS SD
	int sector_htcc = Detectors.getSectorHTCC(event,rec_index);
	//System.out.println(" >> SECTOR MMATCH CUT " );
	if( sector_dc >= 0 && sector_pcal >= 0 && sector_htcc >= 0 ){	    
	    //System.out.println(" >> SECTOR ERROR DO NOT MATCH: DC " + sector_dc + " EC " + sector_ec );
	    return true;
	}
	else{
	    //System.out.println(" >> SECTOR ERROR DO NOT MATCH: DC " + sector_dc + " EC " + sector_ec );
	}  
	return false;
    }

}
