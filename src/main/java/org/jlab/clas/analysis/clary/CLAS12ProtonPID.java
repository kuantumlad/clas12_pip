package org.jlab.clas.analysis.clary;

import org.jlab.clas.analysis.clary.CLAS12ProtonPIDCut;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.jnp.hipo.schema.*;

import java.util.*;
import java.io.*;

public class CLAS12ProtonPID implements IParticleIdentifier{

    Vector<BICandidate> v_cuts = new Vector<BICandidate>();
    boolean status = false;

    CLAS12ProtonPIDCut clas12_pr_cut = new CLAS12ProtonPIDCut();

    SchemaFactory factory;
    public CLAS12ProtonPID() {

	factory = new SchemaFactory();
	factory.initFromDirectory("CLAS12DIR","etc/bankdefs/hipo");

    }

    public void initializeCuts(){

	v_cuts.add(clas12_pr_cut);

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
	HashMap<Boolean,Integer> m_pr_final = new HashMap<Boolean,Integer>();
	return m_pr_final;

    }

}
