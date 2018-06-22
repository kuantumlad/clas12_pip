package org.jlab.clas.analysis.clary;

import java.util.*;
import java.io.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.jnp.hipo.schema.*;

import org.jlab.clas.analysis.clary.Calculator;
import org.jlab.clas.analysis.clary.Detectors;

public class DCFiducialR2Cut implements BICandidate{

    public boolean candidate( DataEvent event, int rec_i ){

	/*boolean b_tbtrack = event.hasBank("TimeBasedTrkg::TBTracks");
	boolean b_tbcross = event.hasBank("TimeBasedTrkg::TBCrosses");
	boolean b_rectrack = event.hasBank("REC::Track");

	if( b_tbtrack && b_tbcross && b_rectrack ){
	    DataBank tbtrack = event.getBank("TimeBasedTrkg::TBTracks");
	    DataBank tbcross = event.getBank("TimeBasedTrkg::TBCrosses");
	    DataBank rectrack = event.getBank("REC::Track");
	    
	    int dc_sector_r2 = Detectors.getDCSectorR2(event, rec_i) - 1;
	    if( dc_sector_r2 >= 0 ){
		Vector<Double> dc_r2_locxy = Detectors.getDCCrossLocalR2(event, rec_i);
		if( dc_r2_locxy.size() < 2 ){ return false; }
		
		double x_local = dc_r2_locxy.get(0);
		double y_local = dc_r2_locxy.get(1);
	    
		boolean d_up = y_local < 0.459*x_local + 69.0;
		boolean d_bottom = y_local > -0.460*x_local - 68.0;
		boolean d_left = x_local > -112.0;
		boolean d_right = x_local < 80.0;
		
		if( d_up && d_bottom && d_left && d_right ){
		    return true;
		}
	    }	    
	}
	*/

	double angle = 60;
	double height = 45;

	Vector<Double> dc_r1 = Detectors.getDCTrajR2(event, rec_i);
	double cx = dc_r1.get(0);
	double cy = dc_r1.get(1);

	int sec = Detectors.getDCTrajSect(event, rec_i, 24) - 1;
	if( sec>=0){
	    double x1_rot = cy * Math.sin(sec*60.0*Math.PI/180) + cx * Math.cos(sec*60.0*Math.PI/180);
	    double y1_rot = cy * Math.cos(sec*60.0*Math.PI/180) - cx * Math.sin(sec*60.0*Math.PI/180);

	    double slope = 1/Math.tan(0.5*angle*Math.PI/180);
	    double left  = (height - slope * y1_rot);
	    double right = (height + slope * y1_rot);
	    
	    double radius2_DCr1 = Math.pow(49,2) - Math.pow(y1_rot,2);    // cut out the inner circle
	    
	    if( x1_rot > left && x1_rot > right &&  Math.pow(x1_rot,2) > radius2_DCr1 ){
		return true;
	    }
	}
	//System.out.println(">> in DC FIC BANKS DO NOT EXIST" );
	return false;
    }
		           

}
