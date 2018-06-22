package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.clas.physics.*;
import org.jlab.clas.analysis.clary.PhysicalConstants;

//SHOULD REALLY BE N SIGMA CUT AWAY FROM THE MEAN OF THE PROTON DISTRIBUTION

class ProtonBetaCut implements BICandidate {


    public boolean candidate(DataEvent event, int rec_index ){

	DataBank recBank = event.getBank("REC::Particle");
	
	LorentzVector lv_pr = Calculator.lv_particle(recBank, rec_index, PhysicalConstants.protonID);
	double p = lv_pr.p();
	double pr_mass = PhysicalConstants.mass_proton;
	double p_beta = p/Math.sqrt( p*p + pr_mass*pr_mass);
	double clas12_beta = recBank.getFloat("beta",rec_index);
	double delta_beta = p_beta - clas12_beta;
	
	if( Math.abs(delta_beta) < 0.034 ){
	    return true;
	}
	return false;
    }
    

}
