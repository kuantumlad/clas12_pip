package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.jnp.hipo.schema.*;


import java.io.*;
import java.util.*;

class ECFiducialCut implements BICandidate {

    public boolean candidate( DataEvent event, int rec_i ){
	
	if( event.hasBank("REC::Calorimeter") ){
	    DataBank ecBank = event.getBank("REC::Calorimeter");
	    int sector_ec = Detectors.getSectorECAL( event, rec_i ) - 1; 

	    for( int i = 0; i < ecBank.rows(); i++ ){
		int pindex = ecBank.getShort("pindex",i);
		
		int layer = ecBank.getByte("layer",i);		
		if( rec_i == pindex ){		    
		    Map<Integer,ArrayList<Double>> m_echit = Detectors.ECHit(event, rec_i);
		    for( Map.Entry<Integer, ArrayList<Double> > entry : m_echit.entrySet() ){
			
			int detector = entry.getKey();
			ArrayList<Double> hit_pos = entry.getValue();
			double x = hit_pos.get(0);
			double y = hit_pos.get(1);
 			double rot_angle = 0.0;
			switch (sector_ec ){
			case 0: 
			    rot_angle = Math.PI/2.0;
			    break;
			case 1:
			    rot_angle = Math.PI/6.0;
			    break;
			case 2:
			    rot_angle = -Math.PI/6.0;
			    break;
			case 3:
			    rot_angle = -Math.PI/2.0;
			    break;
			case 4:
			    rot_angle = -(5.0/6.0)*Math.PI;
			    break;
			case 5:
			    rot_angle = (5.0/6.0)*Math.PI;
			    break;
			}
			
			double x_rot = -y*Math.sin(rot_angle) + x*Math.cos(rot_angle);
			double y_rot = x*Math.sin(rot_angle) + y*Math.cos(rot_angle) - 65.0;
			
			double m1 = 389.0/210.0;//FROM EC SPECS - length/2 and height
			double m2 = 389.0/210.0; //FOR THE LEFT SIDE
			
			//double d_right = (-m1*x_rot + 1.0*y_rot)*(1.0/Math.sqrt(m1*m1 + 1));
			//double d_left = (m2*x_rot + 1.0*y_rot)*(1.0/Math.sqrt(m2*m2 + 1));
			
			//if( d_right <= 20.0 || d_left <= 20.0  || y_rot >= 200.0 ){
			//System.out.println(" >> DR " + d_right + " DL " + d_left );
			//}
			
			//if( d_right <= 10.0 ){ System.out.println(" >> " + d_right ); }
			double opening_angle = Math.atan(210.0/389.0); //should be 30 deg but is actually 28.36 deg
			double b = 10.0/Math.cos(opening_angle*Math.PI / 180.0 );
			boolean d_left = y_rot > m1*x_rot + b ;
			boolean d_right = y_rot > -m2*x_rot + b;
			boolean d_up = y_rot < 379.0;
			boolean d_down = y_rot > 0.0;
			
			if( detector == 4 && d_left && d_right && d_up && d_down){
			    return true;
			}		    
		    }
		}
	    }
	}
	return false;
    }
    
}
