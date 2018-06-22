package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.clas.physics.Particle;
import org.jlab.clas.physics.LorentzVector;

import org.jlab.clas.analysis.clary.PhysicalConstants;
import org.jlab.clas.analysis.clary.ProtonPDF;

import java.io.*;

class MLEProtonBeta implements BIParticleCandidate{
    //make static 
    public Particle particleCandidate( BEventInfo bev, int rec_i ){

	Particle partcand = new Particle();
	double likelihood = -1000.0;
	double conf_lvl = 0.0;

       	if( bev.recScintBankMap.containsKey(rec_i) ){

	    //change to tof
	    double start_time = bev.start_time;

	    //System.out.println(" >> IN particleCandidate " + bev.recBank.rows() + " " + rec_i );

	    LorentzVector lv_pr = Calculator.lv_particle(bev.recBank, rec_i, PhysicalConstants.protonID);
	    double p_pr = lv_pr.p();
	    double beta_hypoth = p_pr/Math.sqrt(p_pr*p_pr + PhysicalConstants.mass_proton * PhysicalConstants.mass_proton);

	    //System.out.println(" >> HYPOTHESIS BETA " + beta_hypoth );
	    for( int iScint : bev.recScintBankMap.get(rec_i) ){
		
		int scint_detector = bev.scintBank.getByte("detector",iScint);
		//System.out.println(" SCINT DETECTOR " + scint_detector );
		if( scint_detector == 12 ){
		    int scint_layer = bev.scintBank.getByte("layer",iScint);
		    double r_path = bev.scintBank.getFloat("path",iScint);
		    double t_ftof = bev.scintBank.getFloat("time",iScint);
		    int sector = bev.scintBank.getInt("sector",iScint) - 1;
		    double pr_tof = t_ftof - start_time;
		    double beta  = r_path/pr_tof * (1.0/PhysicalConstants.speedOfLight);
		    if( scint_layer == 2 ){
			//System.out.println(" >> MEAS BETA " + beta );
			likelihood = ProtonPDF.protonBetaMLE(beta, beta_hypoth, p_pr, sector); //hypoth is the "true" beta
			conf_lvl = ProtonPDF.protonConfLevel(beta, beta_hypoth, p_pr, sector);
			//System.out.println(" >> LAYER 2 " + likelihood + " " + conf_lvl );
		    }
		    //else if( scint_layer == 1 ){
		    //likelihood = ProtonPDF.protonBetaMLE(beta, beta_hypoth, p_pr, sector); // same as above comment
		    //	conf_lvl = ProtonPDF.protonConfLevel(beta, beta_hypoth, p_pr, sector);
			//System.out.println(" >> LAYER 1 " + likelihood + " " + conf_lvl );
		    //}			    
		}
	    }	    
	    partcand.setProperty("likelihood",likelihood);	
	    partcand.setProperty("conflvl", conf_lvl);
	}   
	return partcand;	    
    }

}
