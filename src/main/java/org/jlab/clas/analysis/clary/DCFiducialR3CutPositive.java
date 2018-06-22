package org.jlab.clas.analysis.clary;

import java.util.*;
import java.io.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.jnp.hipo.schema.*;
import org.jlab.clas.physics.Particle;

import org.jlab.clas.analysis.clary.Calculator;
import org.jlab.clas.analysis.clary.Detectors;

public class DCFiducialR3CutPositive implements BIParticleCandidate{

    public Particle particleCandidate( BEventInfo bev, int rec_i ){

	Particle partcand = new Particle();
	double likelihood = 1.0;
	double conf_lvl = 0.0;

	double angle = 60;
	double height = 69;//31;

	Vector<Double> dc_r3 = Detectors.getDCTrajR3(bev.bevEvent, rec_i);
	double cx = dc_r3.get(0);
	double cy = dc_r3.get(1);

	int sec = Detectors.getDCTrajSect(bev.bevEvent, rec_i, 36) - 1;
	if( sec >= 0 ){
	    ///System.out.println(">> " + sec );
	    double x3_rot = cy * Math.sin(sec*60.0*Math.PI/180) + cx * Math.cos(sec*60.0*Math.PI/180);
	    double y3_rot = cy * Math.cos(sec*60.0*Math.PI/180) - cx * Math.sin(sec*60.0*Math.PI/180);
	    
	    double slope = 1/Math.tan(0.5*angle*Math.PI/180);
	    double left  = (height - slope * y3_rot);
	    double right = (height + slope * y3_rot);
	    
	    double radius2_DCr3 = Math.pow(78,2) - Math.pow(y3_rot,2);    // cut out the inner circle //32 -> 31 temp mod
	    
	    if( x3_rot > left && x3_rot > right &&  Math.pow(x3_rot,2) > radius2_DCr3 ){

		//System.out.println(">>DC R3 PASSED " + sec );
		conf_lvl = 1.0;
		//return true;
	    }
	}
	//System.out.println(">> in DC FIC BANKS DO NOT EXIST" );
	partcand.setProperty("likelihood",likelihood);	
	partcand.setProperty("conflvl", conf_lvl);
	
	return partcand;
	//return false;
    }
    

}
