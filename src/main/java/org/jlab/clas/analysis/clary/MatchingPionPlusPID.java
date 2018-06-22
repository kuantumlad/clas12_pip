package org.jlab.clas.analysis.clary;

import org.jlab.clas.analysis.clary.MatchPPionMntm;
import org.jlab.clas.analysis.clary.MatchPPionPhi;
import org.jlab.clas.analysis.clary.MatchPPionTheta;
import org.jlab.clas.analysis.clary.IParticleIdentifier;
import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.jnp.hipo.schema.*;

import java.util.*;
import java.io.*;

public class MatchingPionPlusPID implements IParticleIdentifier {

    MatchPPionMntm pp_mntm_cut = new MatchPPionMntm();
    MatchPPionTheta pp_theta_cut = new MatchPPionTheta();
    MatchPPionPhi pp_phi_cut = new MatchPPionPhi();	

    Vector<BICandidate> match_cuts = new Vector<BICandidate>();
    boolean status = false;

    public void initializeCuts(){

	
	match_cuts.add(pp_mntm_cut);
	match_cuts.add(pp_theta_cut);
	match_cuts.add(pp_phi_cut);

    }

    public boolean processCuts( DataEvent tempevent, int rec_i ){

	boolean result = true;
	for( BICandidate cut : match_cuts ){
	    //System.out.println(" >> LOOPING OVER CUTS " );
	    if( !cut.candidate( tempevent, rec_i ) ){
		//System.out.println(" >> PASSED CUT " );
		result = false;
		break;		
	    }
	}
	return result;
    }


    public HashMap getResult( DataEvent event ){
	HashMap<Boolean, Integer> m_pp_final = new HashMap<Boolean, Integer>();
	return m_pp_final;
    }

}
