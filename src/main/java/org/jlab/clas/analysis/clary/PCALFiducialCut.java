package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.jnp.hipo.schema.*;

import org.jlab.clas.analysis.clary.Detectors;

import java.io.*;
import java.util.*;

class PCALFiducialCut implements BICandidate {

    public boolean candidate( DataEvent event, int rec_i ){

	if( event.hasBank("REC::Calorimeter") ){
	    DataBank pcalBank = event.getBank("REC::Calorimeter");

	    for( int i = 0; i < pcalBank.rows(); i++ ){
		int pindex  = pcalBank.getShort("pindex",i);
		
		if( pindex == rec_i ){
		    int layer = pcalBank.getInt("layer",i);
		    int sector_pcal = Detectors.getSectorPCAL( event, rec_i ) - 1; 
		    
		    if( layer == 1){
			ArrayList<Double> v_pcal_hit = Detectors.PCALHit(event, rec_i);
			/*
			  Vector<Double> pcal_rotxy = Calculator.getRotatedCoordinates(v_pcal_hit.get(0), v_pcal_hit.get(1), sector_pcal);

			double x_rot = pcal_rotxy.get(0);
			double y_rot = pcal_rotxy.get(1);

			boolean d_left = y_rot > 1.86*x_rot + 51.0;
			boolean d_right = y_rot > -1.876*x_rot + 49.0;
			boolean d_up = y_rot < 372.0;
			boolean d_down = y_rot > 52.0;
			if( d_left && d_right && d_up && d_down ){
			    return true;
			}
			
			*/

			  ///USE THIS FOR FIDUCIAL CUT ON PCAL SURFACE
			  //WHEN COMPARING TO STEFANS SHIT CODE

			int sec_PCAL = sector_pcal;
			double x_PCAL = v_pcal_hit.get(0);
			double y_PCAL = v_pcal_hit.get(1);

			double x_PCAL_rot = y_PCAL * Math.sin(sec_PCAL*60.0*Math.PI/180) + x_PCAL * Math.cos(sec_PCAL*60.0*Math.PI/180);
			double y_PCAL_rot = y_PCAL * Math.cos(sec_PCAL*60.0*Math.PI/180) - x_PCAL * Math.sin(sec_PCAL*60.0*Math.PI/180);

			double angle_PCAL = 60;
			double height_PCAL = 47;   // PCAL starts at a hight of 39 + 1.5 PCAL
			//scintillator bars (each is 4.5 cm)

			double slope_PCAL = 1/Math.tan(0.5*angle_PCAL*Math.PI/180);
			double left_PCAL  = (height_PCAL - slope_PCAL * y_PCAL_rot);
			double right_PCAL = (height_PCAL + slope_PCAL * y_PCAL_rot);

			double radius2_PCAL = Math.pow(height_PCAL+6,2) - Math.pow(y_PCAL_rot,2);    // cut
			//another 6 cm circle in the inner triangle tip to reject particles
			//influenced by dead metarial

			if(x_PCAL_rot > left_PCAL && x_PCAL_rot > right_PCAL &&	Math.pow(x_PCAL_rot,2) > radius2_PCAL && x_PCAL_rot < 371 )
			    {
				return true;
			    }			
			else return false;			
		    }
		}
	    
	    }
	}
	return false;
    }

}
