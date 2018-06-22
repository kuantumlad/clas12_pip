package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.clas.physics.Particle;
import org.jlab.clas.physics.LorentzVector;
import org.jlab.clas.analysis.clary.PhysicalConstants;

import java.io.*;

class MLEPionPlusBeta implements BIParticleCandidate{
    
    public Particle particleCandidate( BEventInfo bev, int rec_i ){

	Particle partcand = new Particle();
	double likelihood = -1000.0;
	double conf_lvl = 0.0;

       	if( bev.recScintBankMap.containsKey(rec_i) ){

	    double start_time = bev.start_time;
	    LorentzVector lv_pp = Calculator.lv_particle(bev.recBank, rec_i, PhysicalConstants.pionplusID);
	    double p_pp = lv_pp.p();
	    double beta_hypoth = p_pp/Math.sqrt(p_pp*p_pp + PhysicalConstants.mass_pion * PhysicalConstants.mass_pion);

	    for( int iScint : bev.recScintBankMap.get(rec_i) ){
		
		int scint_detector = bev.scintBank.getByte("detector",iScint);
		int scint_layer = bev.scintBank.getByte("layer",iScint);

		if( scint_detector == 12 ){
		    double r_path = bev.scintBank.getFloat("path",iScint);
		    double t_ftof = bev.scintBank.getFloat("time",iScint);
		    int sector = bev.scintBank.getInt("sector",iScint) - 1;
		    double pp_tof = t_ftof - start_time;		   
		    double beta  = r_path/pp_tof * (1.0/PhysicalConstants.speedOfLight);
		    if( scint_layer == 2 ){
			likelihood = PionPlusPDF.pionPlusBetaMLE(beta, beta_hypoth, p_pp, sector); //hypoth is the "true" beta
			conf_lvl = PionPlusPDF.pionPlusBetaConfLevel(beta, beta_hypoth, p_pp, sector); //hypoth is the "true" beta
		    }
		    else if( scint_layer == 1 ){
			likelihood = PionPlusPDF.pionPlusBetaMLE(beta, beta_hypoth, p_pp, sector); // same as above comment
			conf_lvl = PionPlusPDF.pionPlusBetaConfLevel(beta, beta_hypoth, p_pp, sector); //hypoth is the "true" beta
		    }			    
		}
	    }	    
	    partcand.setProperty("likelihood",likelihood);	
	    partcand.setProperty("conflvl",conf_lvl);	
	}   
	return partcand;	    
    }

}
