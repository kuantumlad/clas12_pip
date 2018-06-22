package org.jlab.clas.analysis.clary;

import org.jlab.clas.analysis.clary.CLAS12KaonPlusPIDCut;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.jnp.hipo.schema.*;

import java.util.*;
import java.io.*;

public class CLAS12KaonPlusPID implements IParticleIdentifier{

    Vector<BICandidate> v_cuts = new Vector<BICandidate>();
    boolean status = false;

    CLAS12KaonPlusPIDCut clas12_kp_cut = new CLAS12KaonPlusPIDCut();

    SchemaFactory factory;
    public CLAS12KaonPlusPID() {

	factory = new SchemaFactory();
	factory.initFromDirectory("CLAS12DIR","etc/bankdefs/hipo");

    }

    public void initializeCuts(){

	v_cuts.add(clas12_kp_cut);

    }

    public boolean processCuts( DataEvent tempevent, int rec_i ){

	boolean result = true;
	for( BICandidate cut : v_cuts ){
	    if( !cut.candidate( tempevent, rec_i ) ){
		result = false;
		break;
	    }
	}
	return result;
    }

    public HashMap getResult( DataEvent event ){
	HashMap<Boolean, Integer> m_kp_final = new HashMap<Boolean,Integer>();
	return m_kp_final;
    }

}
