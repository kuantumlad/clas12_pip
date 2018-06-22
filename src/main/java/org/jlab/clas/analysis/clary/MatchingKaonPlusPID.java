package org.jlab.clas.analysis.clary;

import org.jlab.clas.analysis.clary.MatchKaonPlusPID;
import org.jlab.clas.analysis.clary.MatchKaonPlusMntm;
import org.jlab.clas.analysis.clary.MatchKaonPlusTheta;
import org.jlab.clas.analysis.clary.MatchKaonPlusPhi;
import org.jlab.clas.analysis.clary.IParticleIdentifier;
import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.jnp.hipo.schema.*;

import java.util.*;
import java.io.*;


public class MatchingKaonPlusPID implements IParticleIdentifier {

    MatchKaonPlusMntm kp_mntm_cut = new MatchKaonPlusMntm();
    MatchKaonPlusTheta kp_theta_cut = new MatchKaonPlusTheta();
    MatchKaonPlusPhi kp_phi_cut = new MatchKaonPlusPhi();

    Vector<BICandidate> match_cuts = new Vector<BICandidate>();
    boolean status = false;

    SchemaFactory factory;
    public MatchingKaonPlusPID( ){
	//constructor
	factory  = new SchemaFactory();
	factory.initFromDirectory("CLAS12DIR", "etc/bankdefs/hipo");
	
    }

    public void initializeCuts(){

	match_cuts.add(kp_mntm_cut);
	match_cuts.add(kp_theta_cut);
	match_cuts.add(kp_phi_cut);

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
	
	HashMap<Boolean, Integer>  m_kp_final = new HashMap<Boolean,Integer>();
	return m_kp_final;
	
	
    }




}
