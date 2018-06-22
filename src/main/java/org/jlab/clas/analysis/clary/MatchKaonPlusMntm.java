package org.jlab.clas.analysis.clary;

import org.jlab.clas.analysis.clary.PhysicalConstants;

import java.io.*;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;


class MatchKaonPlusMntm implements BICandidate {

    public boolean candidate( DataEvent tempdevent,int rec_i ){

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

	double rec_p = Math.sqrt( rec_px*rec_px + rec_py*rec_py + rec_pz*rec_pz);
	double gen_p = Math.sqrt( gen_px*gen_px + gen_py*gen_py + gen_pz*gen_pz);

	double p_accuracy = (rec_p - gen_p) / gen_p;
	if(  Math.abs(p_accuracy) < 0.025 ){
	    return true;
	}
	else {
	    return false;
	}
    }
}
