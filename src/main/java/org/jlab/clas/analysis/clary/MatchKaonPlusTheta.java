package org.jlab.clas.analysis.clary;

import org.jlab.clas.analysis.clary.PhysicalConstants;

import java.io.*;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;


class MatchKaonPlusTheta implements BICandidate {

    public boolean candidate( DataEvent tempdevent, int rec_i ){

	DataBank recbank = tempdevent.getBank("REC::Particle");
	DataBank genbank = tempdevent.getBank("MC::Particle");

	int index = PhysicalConstants.kp_index;
	float gen_px = genbank.getFloat("px",index);
	float gen_py = genbank.getFloat("py",index);
	float gen_pz = genbank.getFloat("pz",index);
	float gen_vz = genbank.getFloat("vz",index);

	float rec_px = recbank.getFloat("px",rec_i);
	float rec_py = recbank.getFloat("py",rec_i);
	float rec_pz = recbank.getFloat("pz",rec_i);
	float rec_vz = recbank.getFloat("vz",rec_i);

	double rec_p = Math.sqrt( rec_px*rec_px + rec_py*rec_py + rec_pz*rec_pz);// + PhysicalConstants.mass_kaon*PhysicalConstants.mass_kaon );
	double gen_p = Math.sqrt( gen_px*gen_px + gen_py*gen_py + gen_pz*gen_pz);// + PhysicalConstants.mass_kaon*PhysicalConstants.mass_kaon );

	double rec_theta = Math.acos(rec_pz/rec_p) * 180.0/Math.PI;
	double gen_theta = Math.acos(gen_pz/gen_p) * 180.0/Math.PI;
	
	//System.out.println(" >> KAON THETA " + rec_theta + " " + gen_theta );

	double theta_accuracy = (rec_theta - gen_theta);// / gen_theta;
	
	if(  Math.abs(theta_accuracy) < 0.75 ){
	    return true;
	}
	return false;
    }
}
