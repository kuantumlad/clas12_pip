package org.jlab.clas.analysis.clary;

import org.jlab.clas.analysis.clary.PhysicalConstants;

import java.io.*;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

class MatchElectronPhi implements BICandidate {

    public boolean candidate( DataEvent tempdevent, int rec_i ){

	DataBank recdbank = tempdevent.getBank("REC::Particle");
	DataBank gendbank = tempdevent.getBank("MC::Particle");
	int index = PhysicalConstants.el_index;

	float gen_px = gendbank.getFloat("px",index);
	float gen_py = gendbank.getFloat("py",index);
	float gen_pz = gendbank.getFloat("pz",index);
	float gen_vz = gendbank.getFloat("vz",index);

	float rec_px = recdbank.getFloat("px",rec_i);
	float rec_py = recdbank.getFloat("py",rec_i);
	float rec_pz = recdbank.getFloat("pz",rec_i);
	float rec_vz = recdbank.getFloat("vz",rec_i);

	double rec_p = Math.sqrt( rec_px*rec_px + rec_py*rec_py + rec_pz*rec_pz);
	double gen_p = Math.sqrt( gen_px*gen_px + gen_py*gen_py + gen_pz*gen_pz);

	double rec_phi = Math.atan2(rec_py,rec_px) * 180.0/Math.PI;
	double gen_phi = Math.atan2(gen_py,gen_px) * 180.0/Math.PI;

	double phi_accuracy = (rec_phi - gen_phi);

	if(  Math.abs(phi_accuracy) < 2.0 ){
	    return true;
	}
	return false;
    }
}
