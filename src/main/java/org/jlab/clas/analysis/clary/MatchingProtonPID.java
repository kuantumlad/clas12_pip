package org.jlab.clas.analysis.clary;

import org.jlab.clas.analysis.clary.MatchProtonPID;
import org.jlab.clas.analysis.clary.MatchProtonMntm;
import org.jlab.clas.analysis.clary.MatchProtonTheta;
import org.jlab.clas.analysis.clary.MatchProtonPhi;
import org.jlab.clas.analysis.clary.IParticleIdentifier;

import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.jnp.hipo.schema.*;

import java.util.*;
import java.io.*;

public class MatchingProtonPID implements IParticleIdentifier {

    MatchProtonPID pr_pid_cut = new MatchProtonPID();
    MatchProtonMntm pr_mntm_cut = new MatchProtonMntm();
    MatchProtonTheta pr_theta_cut = new MatchProtonTheta();
    MatchProtonPhi pr_phi_cut = new MatchProtonPhi();
    
    Vector<BICandidate> match_cuts = new Vector<BICandidate>();
    boolean status = false;

    SchemaFactory factory;
    public MatchingProtonPID() {
	factory = new SchemaFactory();
	factory.initFromDirectory("CLAS12DIR", "etc/bankdefs/hipo");
    }

    public void initializeCuts(){

	//match_cuts.add(pr_pid_cut);
	match_cuts.add(pr_mntm_cut);
	match_cuts.add(pr_theta_cut);
	match_cuts.add(pr_phi_cut);

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
	HashMap<Boolean, Integer> m_pr_final = new HashMap<Boolean, Integer>();



	return m_pr_final;
    }



}
