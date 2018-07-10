package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;

import org.jlab.analysis.plotting.TCanvasP;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.groot.graphics.GraphicsAxis;

import org.jlab.groot.fitter.*;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.math.Axis;

import org.jlab.groot.math.*;
import org.jlab.clas.physics.*;

import org.jlab.clas.analysis.clary.Calculator;
import org.jlab.clas.analysis.clary.PhysicalConstants;
import org.jlab.clas.analysis.clary.Detectors;
import org.jlab.clas.analysis.clary.BPIDHistograms;
import org.jlab.clas.analysis.clary.BPIDProtonHistograms;
import org.jlab.clas.analysis.clary.BPIDKaonPlusHistograms;

import java.util.*;
import java.io.*;

public class BHistoPhys {

    public BHistoPhys(){
	//normal constructor;
    }

    private int run_number = -1;
    BPhysHistograms h_phys;

    public BHistoPhys(int temp_run, String n_thread){
	//constructor
	run_number = temp_run;
	h_phys = new BPhysHistograms(run_number, n_thread);       
    }

    public void createHistograms(){

	h_phys.createPhysHistograms();

    }

    public void fillPhysicsEventHistograms(PhysicsEvent phyev){

	System.out.println(" >> FILLING PHYSICS EVENT HISTOGRAMS " );
	System.out.println(" >> Q2 " + -phyev.lv_q.mass2());
	System.out.println(" >> xb " + phyev.xB);
	System.out.println(" >> -t " + phyev.t);
	System.out.println(" >> KP Mass " + phyev.lv_kp.mass());
	System.out.println(" >> KM Mass " + phyev.lv_km.mass());
	
	if( phyev.topology == 4 ){
	    
	    h_phys.h_q2.fill( -phyev.lv_q.mass2() );
	    h_phys.h_xb.fill( phyev.xB );
	    h_phys.h_t.fill( phyev.t );
	    h_phys.h_w2.fill( phyev.lv_w.mass2() );
	    h_phys.h_w.fill( phyev.lv_w.mass() );
	    h_phys.h_cm_phi.fill( phyev.cm_phi);

	    h_phys.h2_q2x.fill(phyev.xB, -phyev.lv_q.mass2());
	    h_phys.h2_q2t.fill(phyev.t, -phyev.lv_q.mass2());
	    h_phys.h2_q2w.fill(  phyev.lv_w.mass(), -phyev.lv_q.mass2());
	    h_phys.h2_q2phi.fill( phyev.cm_phi, -phyev.lv_q.mass2()  );
	    
	    h_phys.h2_tw.fill(phyev.t,phyev.lv_w.mass() );
	    
	}


	fillMMeX(phyev);
	//	fillMMepX(phyev);
	fillMMepkX(phyev);
	fillMMekX(phyev);
	fillMMepkX_ekX(phyev);

	fillPhi(phyev);

    }

    public void fillMMeX( PhysicsEvent phyev ){

	if( phyev.topology == 0 ){
	    h_phys.h_mm_eX.fill( phyev.lv_w.mass() );
	}

    }

    public void fillMMepX( LorentzVector lv_beam, LorentzVector target, LorentzVector lv_el, LorentzVector lv_pr ){ //PhysicsEvent phyev ){

	//if( physev.topology == 1 ){
	    
	    LorentzVector lv_epX = new LorentzVector(0,0,0,0);
	    lv_epX.add(lv_beam);
	    lv_epX.add(target);
	    lv_epX.sub(lv_el);
	    lv_epX.sub(lv_pr);
	    
	    h_phys.h_mm_epX.fill( lv_epX.mass() );
	    //}

    }

    public void fillMMepkX( PhysicsEvent phyev ){
	
	if( phyev.topology == 2 ){

	LorentzVector lv_epkX = new LorentzVector(0,0,0,0);
	lv_epkX.add(phyev.lv_beam);
	lv_epkX.add(phyev.target);
	lv_epkX.sub(phyev.lv_el);
	lv_epkX.sub(phyev.lv_pr);
	lv_epkX.sub(phyev.lv_kp);

	System.out.println(" >> FILLING EPKX HISTO MASS IS: " + lv_epkX.mass());

	h_phys.h_mm_epKX.fill( lv_epkX.mass() );

	}

    }

    public void fillMMekX( PhysicsEvent phyev ){
	
	if( phyev.topology == 5 ){

	    LorentzVector lv_ekX = new LorentzVector(0,0,0,0);
	    lv_ekX.add(phyev.lv_beam);
	    lv_ekX.add(phyev.target);
	    lv_ekX.sub(phyev.lv_el);
	    lv_ekX.sub(phyev.lv_kp);
	    
	    
	    
	    
	    h_phys.h_mm_eKX.fill(lv_ekX.mass());

	}
    }

    public void fillMMepkX_ekX( PhysicsEvent phyev ){

	LorentzVector lv_epkX = new LorentzVector(0,0,0,0);
	lv_epkX.add(phyev.lv_beam);
	lv_epkX.add(phyev.target);
	lv_epkX.sub(phyev.lv_el);
	lv_epkX.sub(phyev.lv_pr);
	lv_epkX.sub(phyev.lv_kp);

	LorentzVector lv_phi = new LorentzVector(0,0,0,0);
	lv_phi.add( lv_epkX );
	lv_phi.add( phyev.lv_kp );

	LorentzVector lv_ekX = new LorentzVector(0,0,0,0);
	lv_ekX.add(phyev.lv_beam);
	lv_ekX.add(phyev.target);
	lv_ekX.sub(phyev.lv_el);
	lv_ekX.sub(phyev.lv_kp);

	System.out.println(" >> FILLING HISTO 2D PHI MASS IS: " + lv_phi.mass());

	h_phys.h2_mm_epKX_eKX.fill(lv_phi.mass(), lv_ekX.mass());

    }

    public void fillPhi( PhysicsEvent physev ){

	if( physev.topology == 4 ){
	    System.out.println(" >> PHI EVENT DETECTED " );
	    LorentzVector lv_phi = new LorentzVector(0,0,0,0);
	    lv_phi.add( physev.lv_kp );
	    lv_phi.add( physev.lv_km );

	    System.out.println(" >> FILLING PHI HISTO WITH " +  lv_phi.mass() );
	    h_phys.h_full_excl_phi_mass.fill( lv_phi.mass() );

	}
    }


    public void savePhysHistograms( boolean view ){


	h_phys.physicsHistoToHipo();
	System.out.println(">> DONE WITH PID LVL HISTOGRAMS ");


    }


    

}
