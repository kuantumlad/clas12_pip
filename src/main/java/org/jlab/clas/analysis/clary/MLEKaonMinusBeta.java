package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.clas.physics.Particle;
import org.jlab.clas.physics.LorentzVector;

import org.jlab.clas.analysis.clary.KaonPDF;
import org.jlab.clas.analysis.clary.PhysicalConstants;

import java.io.*;

class MLEKaonMinusBeta implements BIParticleCandidate{
    
    public Particle particleCandidate( BEventInfo bev, int rec_i ){

	Particle partcand = new Particle();
	double likelihood = -1000.0;
	double conf = 0.0;
       	if( bev.recScintBankMap.containsKey(rec_i) ){

	    double start_time = bev.start_time;
	    LorentzVector lv_kp = Calculator.lv_particle(bev.recBank, rec_i, PhysicalConstants.kaonplusID);
	    double p_kp = lv_kp.p();
	    double beta_hypoth = p_kp/Math.sqrt(p_kp*p_kp + PhysicalConstants.mass_kaon * PhysicalConstants.mass_kaon);

	    for( int iScint : bev.recScintBankMap.get(rec_i) ){
		
		int scint_detector = bev.scintBank.getByte("detector",iScint);

		if( scint_detector == 12 ){
		    int scint_layer = bev.scintBank.getByte("layer",iScint);	       
		    double r_path = bev.scintBank.getFloat("path",iScint);
		    double t_ftof = bev.scintBank.getFloat("time",iScint);
		    int sector = bev.scintBank.getInt("sector",iScint) - 1;
		    double kp_tof = t_ftof - start_time;
		    double beta  = r_path/kp_tof * (1.0/PhysicalConstants.speedOfLight);
		    if( scint_layer == 2 ){
			likelihood = KaonPDF.kaonMinusBetaMLE(beta, beta_hypoth, p_kp, sector); //hypoth is the "true" beta
			conf = KaonPDF.kaonMinusBetaConfLevel(beta, beta_hypoth, p_kp, sector); //hypoth is the "true" beta
		    }
		    //else if( scint_layer == 1 ){
		    //likelihood = KaonPDF.kaonBetaMLE(beta, beta_hypoth, p_kp, sector); // same as above comment
		    //conf = KaonPDF.kaonBetaConfLevel(beta, beta_hypoth, p_kp, sector); //hypoth is the "true" beta

		    //}			    
		}
	    }	    
	    partcand.setProperty("likelihood",likelihood);	
	    partcand.setProperty("conflvl",conf);	
	}   
	return partcand;	    
    }

}
