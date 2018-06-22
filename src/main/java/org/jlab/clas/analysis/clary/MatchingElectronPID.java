package org.jlab.clas.analysis.clary;
import org.jlab.clas.analysis.clary.MatchElectronPID;
import org.jlab.clas.analysis.clary.MatchElectronMntm;
import org.jlab.clas.analysis.clary.MatchElectronPhi;
import org.jlab.clas.analysis.clary.MatchElectronTheta;
import org.jlab.clas.analysis.clary.IParticleIdentifier;
import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.jnp.hipo.schema.*;

import java.util.*;
import java.io.*;

public class MatchingElectronPID implements IParticleIdentifier {

    //    MatchElectronPID el_pid_cut = new MatchElectronPID();
    MatchElectronMntm el_mntm_cut = new MatchElectronMntm();
    MatchElectronTheta el_theta_cut = new MatchElectronTheta();
    MatchElectronPhi el_phi_cut = new MatchElectronPhi();	

    Vector<BICandidate> match_cuts = new Vector<BICandidate>();
    boolean status = false;

    SchemaFactory factory;
    public MatchingElectronPID( ){
	//constructor
	factory  = new SchemaFactory();
	factory.initFromDirectory("CLAS12DIR", "etc/bankdefs/hipo");

	
    }

    public void initializeCuts(){

	//match_cuts.add(el_pid_cut);
	match_cuts.add(el_mntm_cut);
	match_cuts.add(el_theta_cut);
	match_cuts.add(el_phi_cut);

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
	
	HashMap<Boolean, Integer> m_el_final = new HashMap<Boolean, Integer>();
	return m_el_final;

    }


    //public 
      

}
