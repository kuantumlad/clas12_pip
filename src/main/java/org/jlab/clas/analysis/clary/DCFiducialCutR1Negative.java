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

public class DCFiducialCutR1Negative implements BIParticleCandidate{

    public Particle particleCandidate( BEventInfo bev, int rec_i ){

	Particle partcand = new Particle();
	double likelihood = 1.0;
	double conf_lvl = 0.0;

	double angle = 60;
	double height = 27;//31;
	int pow_val = 32;

	Vector<Double> dc_r1 = Detectors.getDCTrajR1(bev.bevEvent, rec_i);
	double cx = dc_r1.get(0);
	double cy = dc_r1.get(1);

	int sec = Detectors.getDCTrajSect(bev.bevEvent, rec_i, 12) - 1;
	if( sec >= 0 ){
	    ///System.out.println(">> " + sec );
	    double x1_rot = cy * Math.sin(sec*60.0*Math.PI/180) + cx * Math.cos(sec*60.0*Math.PI/180);
	    double y1_rot = cy * Math.cos(sec*60.0*Math.PI/180) - cx * Math.sin(sec*60.0*Math.PI/180);
	    
	    double slope = 1/Math.tan(0.5*angle*Math.PI/180);
	    double left  = (height - slope * y1_rot);
	    double right = (height + slope * y1_rot);
	    
	    double radius2_DCr1 = Math.pow(32,2) - Math.pow(y1_rot,2);    // cut out the inner circle //32 -> 31 temp mod
	    
	    if( x1_rot > left && x1_rot > right &&  Math.pow(x1_rot,2) > radius2_DCr1 ){

		//if( sec == 0 ) System.out.println(">> PASSED " + sec );
		//return true;
		conf_lvl = 1.0;
	    }
	}
	//System.out.println(">> in DC FIC BANKS DO NOT EXIST" );
	partcand.setProperty("likelihood",likelihood);	
	partcand.setProperty("conflvl", conf_lvl);
	
	return partcand;
	//return false;
    }
    

}
