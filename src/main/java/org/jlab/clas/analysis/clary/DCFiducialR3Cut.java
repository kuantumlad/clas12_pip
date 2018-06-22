package org.jlab.clas.analysis.clary;

import java.util.*;
import java.io.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.jnp.hipo.schema.*;

import org.jlab.clas.analysis.clary.Calculator;
import org.jlab.clas.analysis.clary.Detectors;

public class DCFiducialR3Cut implements BICandidate{

    public boolean candidate( DataEvent event, int rec_i ){

	/*boolean b_tbtrack = event.hasBank("TimeBasedTrkg::TBTracks");
	boolean b_tbcross = event.hasBank("TimeBasedTrkg::TBCrosses");
	boolean b_rectrack = event.hasBank("REC::Track");

	if( b_tbtrack && b_tbcross && b_rectrack ){
	    DataBank tbtrack = event.getBank("TimeBasedTrkg::TBTracks");
	    DataBank tbcross = event.getBank("TimeBasedTrkg::TBCrosses");
	    DataBank rectrack = event.getBank("REC::Track");
	    
	    int dc_sector_r3 = Detectors.getDCSectorR3(event, rec_i) - 1;
	    if( dc_sector_r3 >= 0 ){
		Vector<Double> dc_r3_locxy = Detectors.getDCCrossLocalR3(event, rec_i);
		//System.out.println(" >> Checking " + dc_r3_locxy.size() );
		if( dc_r3_locxy.size() < 2 ){ return false; }
		double x_local = dc_r3_locxy.get(0);
		double y_local = dc_r3_locxy.get(1);
	    
		boolean d_up = y_local < 0.443*x_local + 93.0;
		boolean d_bottom = y_local > -0.434*x_local - 89.9;
		boolean d_left = x_local > -180.0;
		boolean d_right = x_local < 150.0;
		
		if( d_up && d_bottom && d_left && d_right ){
		    return true;
		}
	    }	    
	}		       
	*/
	//System.out.println(" >> IN DCR3 FID CUT");
	if( event.hasBank("REC::Track") && event.hasBank("REC::Traj") ){
	    //System.out.println(" >> HAS ALL BANKS");
	    
	    double angle = 60;
	    double height = 48;
	    
	    Vector<Double> dc_r3 = Detectors.getDCTrajR3(event, rec_i);
	    //System.out.println(" >> Checking " + dc_r3.size() );
	    double cx = dc_r3.get(0);
	    double cy = dc_r3.get(1);
	    
	    int sec = Detectors.getDCTrajSect(event, rec_i, 36) - 1;
	    
	    if( sec >=0 ){
		double x1_rot = cy * Math.sin(sec*60.0*Math.PI/180) + cx * Math.cos(sec*60.0*Math.PI/180);
		double y1_rot = cy * Math.cos(sec*60.0*Math.PI/180) - cx * Math.sin(sec*60.0*Math.PI/180);
		
		double slope = 1/Math.tan(0.5*angle*Math.PI/180);
		double left  = (height - slope * y1_rot);
		double right = (height + slope * y1_rot);
		
		double radius2_DCr3 = Math.pow(54,2)-Math.pow(y1_rot,2);    // cut out the inner circle
		
		if( x1_rot > left && x1_rot > right &&  Math.pow(x1_rot,2) > radius2_DCr3 ){
		    return true;
		}
	    }
	}
	//System.out.println(">> in DC FIC BANKS DO NOT EXIST" );
	return false;
    }
    

}
