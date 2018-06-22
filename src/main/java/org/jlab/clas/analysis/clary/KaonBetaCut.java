package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.clas.physics.*;
import org.jlab.clas.analysis.clary.PhysicalConstants;

//SHOULD REALLY BE N SIGMA CUT AWAY FROM THE MEAN OF THE KAON DISTRIBUTION

class KaonBetaCut implements BICandidate {

    public boolean candidate(DataEvent event, int rec_index ){

	DataBank recBank = event.getBank("REC::Particle");
	
	LorentzVector lv_kp = Calculator.lv_particle(recBank, rec_index, PhysicalConstants.kaonplusID);
	double p = lv_kp.p();
	double kp_mass = PhysicalConstants.mass_kaon;
	double p_beta = p/Math.sqrt( p*p + kp_mass*kp_mass);
	double clas12_beta = recBank.getFloat("beta",rec_index);
	double delta_beta = p_beta - clas12_beta;
	
	if( Math.abs(delta_beta) < 0.05 ){
	    return true;
	}
	return false;
    }
    

}

