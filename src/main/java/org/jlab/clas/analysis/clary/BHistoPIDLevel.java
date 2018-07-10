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

public class BHistoPIDLevel {

    public BHistoPIDLevel(){
	//normal constructor;
    }

    private int run_number = -1;
    BPIDHistograms h_bpid; 
    
    BPIDProtonHistograms h_bprotonpid;
    BPIDKaonPlusHistograms h_bkppid;

    public BHistoPIDLevel(int temp_run, String n_thread){
	//constructor
	run_number = temp_run;
	h_bpid = new BPIDHistograms(run_number, n_thread);
	h_bprotonpid = new BPIDProtonHistograms(run_number);
	h_bkppid = new BPIDKaonPlusHistograms(run_number);
	
    }
        
    Vector<H1F> v_el_pass = new Vector<H1F>();
    Vector<H2F> v2_el_pass = new Vector<H2F>();

    public void CreateHistograms(){
	
	//h_bpid.createElectronHistoToHipoOut(0);

	for( int i = 0; i <= 11; i++ ){ //THIS NUMBER SHOULD NOT BE HARD CODED :o // REPRESENTS NUMBER OF CUTS
	    h_bpid.createElectronHistograms(i);
	    //h_bprotonpid.createProtonHistograms(i);
	    //h_bkppid.createKaonPHistograms(i);
	}

	for( int j = 0; j <= 6; j++ ){
	    h_bpid.createElectronSectorHistograms(j,11);
	    //h_bprotonpid.createProtonSectorHistograms(j,6);
	    //h_bkppid.createKaonPSectorHistograms(j,6);
	}

	//h_bprotonpid.createProtonFTOFHistograms(6,50,6);	   		
    }

    public void fillElectronCutMonitor( Vector<Boolean> temp_pass, DataEvent event, int rec_i ){

	int j = 0;
	for( boolean pass : temp_pass ){
	    if( pass ){
		DataBank recBank = event.getBank("REC::Particle");		
		LorentzVector lv_el = Calculator.lv_particle(recBank, rec_i, PhysicalConstants.electronID);
		double p = lv_el.p();
		
		//h_bpid.h_el_p_cutrates.get(j).fill(p);
		//int bin = h_bpid.h_el_p_cutrates.get(j).getXaxis().getBin(p);
		//if( bin < 0 ){ System.out.println(" >> bin " + bin + " " + p ) ;}
	    }
	    j++;
	}
    }

    public void fillElectronCutMonitor2( Vector<Boolean> temp_pass ){

	//int g = 0;
	boolean neg_track = temp_pass.get(0);
	for( int g = 0; g < temp_pass.size(); g++ ){
	    boolean pass = temp_pass.get(g);
	    if( pass  && neg_track ){
		//h_bpid.h_el_p_cutrates.get(g).fill(1.73);
		int num = h_bpid.v_cutrates.get(g);
		num++;
		h_bpid.v_cutrates.set(g,num);
	    }
	    //g++;
	}
    }


    public void fillComparisonPID( DataEvent event, int rec_i ){

	//if( rec_i == 0 ){
	h_bpid.h_el_comparison.fill(rec_i);

	DataBank recBank = event.getBank("REC::Particle");
	DataBank eventBank = event.getBank("REC::Event");
	
	LorentzVector lv_el = Calculator.lv_particle(recBank, rec_i, PhysicalConstants.electronID);
	
	double p = lv_el.p();
	double theta = Math.toDegrees(lv_el.theta());
	double phi = Math.toDegrees(lv_el.phi());
	
	double w = Calculator.W(lv_el);
	double q2 = Calculator.Q2(lv_el);
	double xb = Calculator.Xb(lv_el);

	h_bpid.h_el_p_comp.fill(p);
	h_bpid.h_el_theta_comp.fill(theta);
	h_bpid.h_el_phi_comp.fill(phi);
	h_bpid.h_el_w_comp.fill(w);
	h_bpid.h_el_q2_comp.fill(q2);
	h_bpid.h_el_q2x_comp.fill(xb,q2);
      
    }

    public void FillElectronPID( Vector<Boolean> temp_pass, DataEvent event, int rec_i ){

	fillElectronCutMonitor2( temp_pass );//, event, rec_i );
	//System.out.println(" >> FILLING ELECTRON HISTOGRAMS ");
	
	//int j = 0;	
	//for( boolean pass : temp_pass ){
	for( int j = 0; j < temp_pass.size(); j++ ){
	    boolean pass = temp_pass.get(j);
	    if( pass ){		

		DataBank recBank = event.getBank("REC::Particle");
		DataBank eventBank = event.getBank("REC::Event");
		
		LorentzVector lv_el = Calculator.lv_particle(recBank, rec_i, PhysicalConstants.electronID);
		
		double p = lv_el.p();
		double theta = Math.toDegrees(lv_el.theta());
		double phi = Math.toDegrees(lv_el.phi());
		
		double w = Calculator.W(lv_el);
		double q2 = Calculator.Q2(lv_el);

		float vz = recBank.getFloat("vz",rec_i);
		float clas12_t_start = eventBank.getFloat("STTime",0); //USING TIME PROVIDED BY EB		
	
		double nphe = Detectors.getCherenkovNPHE( event, rec_i );
		Map<Integer, Double> m_edep = Detectors.getEDepCal( event, rec_i );
		double ecdep_tot = 0; //ENERGY DEPOSITED WITHIN ALL LAYERS
	
		int sector_ec = Detectors.getSectorECAL( event, rec_i ) - 1; 
		int sector_dc = Detectors.getDCTrajSect(event, rec_i, 12) - 1; // PROBS NOT SAME AS SD
		int sector_pcal = Detectors.getSectorPCAL( event, rec_i ) - 1;

		double e_pcal = 0.0;
		double e_ecal_ei = 0.0;
		double e_ecal_eo = 0.0;

		for( Map.Entry<Integer,Double> entry : m_edep.entrySet() ){
		    int layer = entry.getKey();
		    double edep = entry.getValue();		    
		    if( layer == Detectors.pcal ){
			h_bpid.h_el_pcaltot.get(j).fill(edep);	
			if( sector_ec >= 0 ){ h_bpid.h_el_sect_pcal.get(sector_ec).get(j).fill(edep); e_pcal = edep; }
		    }
		    if( layer == Detectors.ec_ei ){
			h_bpid.h_el_ecei.get(j).fill(edep);		
			if( sector_ec >= 0 ) {h_bpid.h_el_sect_ecei.get(sector_ec).get(j).fill(edep); e_ecal_ei = edep; }
		    }
		    if( layer == Detectors.ec_eo ){
			h_bpid.h_el_eceo.get(j).fill(edep);		
			if( sector_ec >= 0 ){h_bpid.h_el_sect_eceo.get(sector_ec).get(j).fill(edep); e_ecal_eo = edep;}
		    }		    
		    ecdep_tot = ecdep_tot + edep;
		}
		
		double e_ecal = e_ecal_ei + e_ecal_eo;
		h_bpid.h2_el_pcalecal.get(j).fill(e_pcal, e_ecal ); 

		h_bpid.h_el_p.get(j).fill( p );

		h_bpid.h_el_p_test.get(j).fill(p);

		h_bpid.h_el_theta.get(j).fill( theta );
		h_bpid.h_el_phi.get(j).fill( phi );
		h_bpid.h_el_vz.get(j).fill( vz );
		h_bpid.h_el_timing.get(j).fill( clas12_t_start );
		h_bpid.h_el_nphe.get(j).fill(nphe);
		h_bpid.h_el_ectot.get(j).fill(ecdep_tot);	      
		h_bpid.h_el_edepdiff.get(j).fill(e_ecal - e_pcal);

		h_bpid.h_el_w.get(j).fill(w);
		h_bpid.h2_el_wq2.get(j).fill( w, q2 );
		h_bpid.h2_el_wphi.get(j).fill(phi, w);
		
		h_bpid.h2_el_etotnphe.get(j).fill(nphe, ecdep_tot);
		h_bpid.h2_el_ectotp.get(j).fill(p, ecdep_tot/p);
		h_bpid.h2_el_ectotp2.get(j).fill(ecdep_tot, ecdep_tot/p);

		h_bpid.h2_el_pcalp.get(j).fill(p, e_pcal/p);
		h_bpid.h2_el_eieo.get(j).fill(e_ecal_ei, e_ecal_eo);

		//System.out.println(" >> " + j + " " + p + " " + theta );
		h_bpid.h2_el_thetap.get(j).fill(p, theta);
		h_bpid.h2_el_phip.get(j).fill(p, phi);
		h_bpid.h2_el_edepdiff.get(j).fill( (e_ecal - e_pcal)/(e_ecal + e_pcal), e_ecal + e_pcal );
		h_bpid.h2_el_phivz.get(j).fill(vz, phi);
		
		  		
		if( event.hasBank("REC::Track") && event.hasBank("TimeBasedTrkg::TBCrosses") && event.hasBank("TimeBasedTrkg::TBTracks") ){
		    int dc_sector_r1 = Detectors.getDCSectorR1(event, rec_i) - 1;
		    double dc_x1 = Detectors.getDCCrossX1(event, rec_i);
		    double dc_y1 = Detectors.getDCCrossY1(event, rec_i);

		    int dc_sector_r3 = Detectors.getDCSectorR3(event, rec_i) - 1;
		    double dc_x3 = Detectors.getDCCrossX3(event, rec_i);
		    double dc_y3 = Detectors.getDCCrossY3(event, rec_i);
 		    //System.out.println(" >> DC HIT X Y " + dc_x1 + " " + dc_y1);

		    Vector<Double> dc_r1_rotxy = Calculator.getRotatedCoordinates(dc_x1,dc_y1,dc_sector_r1);
		    Vector<Double> dc_r3_rotxy = Calculator.getRotatedCoordinates(dc_x3,dc_y3,dc_sector_r3);
		    		   
		    if( dc_x1 > -1000 && dc_y1 > -1000 && dc_sector_r1 >= 0){
			Vector<Double> dc_r1_locxy = Detectors.getDCCrossLocalR1(event, rec_i);
			if( dc_r1_locxy.size() > 1 ){
			    h_bpid.h2_el_sect_dc_R1_lxy.get(dc_sector_r1).get(j).fill(dc_r1_locxy.get(0), dc_r1_locxy.get(1) );
			    h_bpid.h2_el_dchit_R1_lxy.get(j).fill(dc_r1_locxy.get(0), dc_r1_locxy.get(1));
			}
			if( dc_r1_rotxy.size() > 1 ) {
			    h_bpid.h2_el_dchitxy.get(j).fill(dc_r1_rotxy.get(0), dc_r1_rotxy.get(1));
			    h_bpid.h2_el_sect_dc_R1_xy.get(dc_sector_r1).get(j).fill(dc_r1_rotxy.get(0), dc_r1_rotxy.get(1));			
			}
		    }
		    
		    if( dc_x3 > -1000 && dc_y3 > -1000 && dc_sector_r3 >= 0){
			Vector<Double> dc_r3_locxy = Detectors.getDCCrossLocalR3(event, rec_i);

			if( dc_r3_rotxy.size() > 1 ){
			    h_bpid.h2_el_dchit_R3_xy.get(j).fill(dc_r3_rotxy.get(0), dc_r3_rotxy.get(1));
			    h_bpid.h2_el_sect_dc_R3_xy.get(dc_sector_r3).get(j).fill(dc_r3_rotxy.get(0), dc_r3_rotxy.get(1) );
			}
			if(dc_r3_locxy.size() > 1 ){
			    h_bpid.h2_el_sect_dc_R3_lxy.get(dc_sector_r3).get(j).fill(dc_r3_locxy.get(0), dc_r3_locxy.get(1) );
			    h_bpid.h2_el_dchit_R3_lxy.get(j).fill(dc_r3_locxy.get(0), dc_r3_locxy.get(1));
			}
			
		    }		   
		}
						
		Map<Integer,ArrayList<Double>> m_ecal_uvw = Calculator.xyzToUVW(event, rec_i);
		for( Map.Entry<Integer,ArrayList<Double> > entry : m_ecal_uvw.entrySet() ){
		    int detector = entry.getKey();
		    ArrayList<Double> hit_uvw = entry.getValue();
		    double u_cord = hit_uvw.get(0);
		    double v_cord = hit_uvw.get(1);
		    double w_cord = hit_uvw.get(2);
		    //System.out.println(" >> UVW HIT IN HISTO DETECTOR " + detector + " "  + hit_uvw.get(0) + " " + hit_uvw.get(1) + " " + hit_uvw.get(2) );
		    if( detector == 7 ){
			h_bpid.h_el_ecu.get(j).fill(u_cord);
			h_bpid.h_el_ecv.get(j).fill(v_cord);
			h_bpid.h_el_ecw.get(j).fill(w_cord);
		    }
		    
		}

		if( event.hasBank("REC::Traj") ){
 		    Vector<Double> dc_hit_R1 = Detectors.getDCTrajR1(event,rec_i);
 		    Vector<Double> dc_hit_R2 = Detectors.getDCTrajR2(event,rec_i);
 		    Vector<Double> dc_hit_R3 = Detectors.getDCTrajR3(event,rec_i);

		    int dc_sect_traj1 = Detectors.getDCTrajSect(event, rec_i, 12) - 1; // 12, 24, 36 for R1, R2, R3
		    int dc_sect_traj2 = Detectors.getDCTrajSect(event, rec_i, 24) - 1; // 12, 24, 36 for R1, R2, R3
		    int dc_sect_traj3 = Detectors.getDCTrajSect(event, rec_i, 36) - 1; // 12, 24, 36 for R1, R2, R3
		    
		    Vector<Double> dc_hit_R1_rot = Calculator.getTrajRotatedCoordindate(dc_hit_R1.get(0),dc_hit_R1.get(1),dc_sect_traj1);
		    Vector<Double> dc_hit_R2_rot = Calculator.getTrajRotatedCoordindate(dc_hit_R2.get(0),dc_hit_R2.get(1),dc_sect_traj2);
		    Vector<Double> dc_hit_R3_rot = Calculator.getTrajRotatedCoordindate(dc_hit_R3.get(0),dc_hit_R3.get(1),dc_sect_traj3);
		    

		    h_bpid.h2_el_dchit_R1_traj.get(j).fill( dc_hit_R1.get(0), dc_hit_R1.get(1) );
		    h_bpid.h2_el_dchit_R2_traj.get(j).fill( dc_hit_R2.get(0), dc_hit_R2.get(1) );
		    h_bpid.h2_el_dchit_R3_traj.get(j).fill( dc_hit_R3.get(0), dc_hit_R3.get(1) );

		    		    
		    h_bpid.h2_el_dchit_R1_traj_rot.get(j).fill( dc_hit_R1_rot.get(0), dc_hit_R1_rot.get(1) );
		    h_bpid.h2_el_dchit_R2_traj_rot.get(j).fill( dc_hit_R2_rot.get(0), dc_hit_R2_rot.get(1) );
		    h_bpid.h2_el_dchit_R3_traj_rot.get(j).fill( dc_hit_R3_rot.get(0), dc_hit_R3_rot.get(1) );


		    //System.out.println( ">> CUT " + j + " "  + dc_sect_traj1 + " " + dc_sect_traj2 + " " + dc_sect_traj3 );

		    if( dc_sect_traj1 >= 0 ){
			h_bpid.h2_el_sect_dc_R1_traj.get(dc_sect_traj1).get(j).fill( dc_hit_R1.get(0), dc_hit_R1.get(1) );
		    }
		    if( dc_sect_traj2 >= 0 ){
			h_bpid.h2_el_sect_dc_R2_traj.get(dc_sect_traj2).get(j).fill( dc_hit_R2.get(0), dc_hit_R2.get(1) );
		    }
		    if( dc_sect_traj3 >= 0 ){
			h_bpid.h2_el_sect_dc_R3_traj.get(dc_sect_traj3).get(j).fill( dc_hit_R3.get(0), dc_hit_R3.get(1) );
		    }

		}
			
		if( sector_dc >= 0 ){
		    h_bpid.h_el_sect_p.get(sector_dc).get(j).fill(p);
		    h_bpid.h_el_sect_theta.get(sector_dc).get(j).fill(theta);
		    h_bpid.h_el_sect_phi.get(sector_dc).get(j).fill(phi);
		    h_bpid.h_el_sect_vz.get(sector_dc).get(j).fill(vz);
		    h_bpid.h_el_sect_timing.get(sector_dc).get(j).fill(clas12_t_start);
		    h_bpid.h_el_sect_nphe.get(sector_dc).get(j).fill(nphe);
		    h_bpid.h_el_sect_ectot.get(sector_dc).get(j).fill(ecdep_tot);

		    h_bpid.h2_el_sect_thetap.get(sector_dc).get(j).fill(p, theta);
		    h_bpid.h2_el_sect_phip.get(sector_dc).get(j).fill(p, phi);
		    h_bpid.h2_el_sect_thetavz.get(sector_dc).get(j).fill(vz, theta);
		    h_bpid.h2_el_sect_phivz.get(sector_dc).get(j).fill(vz, phi);
		    h_bpid.h2_el_sect_pvz.get(sector_dc).get(j).fill(vz,p);		  
		    h_bpid.h2_el_sect_q2w.get(sector_dc).get(j).fill(w, q2);
		}

		if( sector_pcal >= 0 ){
		    h_bpid.h2_el_sect_pcalp.get(sector_pcal).get(j).fill(p, e_pcal/p);

		    ArrayList<Double> al_pcalhit = Detectors.PCALHit(event, rec_i);		    
		    Vector<Double> pcal_rot_hit = Calculator.getRotatedCoordinates(al_pcalhit.get(0), al_pcalhit.get(1), sector_pcal);

		    h_bpid.h2_el_pcalhitxy.get(j).fill(pcal_rot_hit.get(0),pcal_rot_hit.get(1));

		    h_bpid.h2_el_sect_pcal_sfxy.get(sector_pcal).get(j).fill(pcal_rot_hit.get(0),pcal_rot_hit.get(1), ecdep_tot/p);
 		    h_bpid.h2_el_sect_pcalhitxy.get(sector_pcal).get(j).fill(pcal_rot_hit.get(0),pcal_rot_hit.get(1));
		    
		}
		if( sector_ec >= 0 ){
		    h_bpid.h_el_sect_ectot.get(sector_ec).get(j).fill(ecdep_tot);
		    
		    /*Map<Integer,ArrayList<Double>> m_echit = Detectors.ECHit(event, rec_i);
		    for( Map.Entry<Integer, ArrayList<Double> > entry : m_echit.entrySet() ){ 
			int detector = entry.getKey(); 
			ArrayList<Double> hit_pos = entry.getValue(); 
			double x = hit_pos.get(0);
			double y = hit_pos.get(1);
			if( detector == 7 ){
			    Vector<Double> ec_hit_rot = Calculator.getRotatedCoordinates(x,y,sector_ec);
			}
		    }
		    */

		    Map<Integer,ArrayList<Double>> m_echit = Detectors.ECHit(event, rec_i);

		    for( Map.Entry<Integer, ArrayList<Double> > entry : m_echit.entrySet() ){ 
			int detector = entry.getKey(); 
			ArrayList<Double> hit_pos = entry.getValue(); 
			double x = hit_pos.get(0);
			double y = hit_pos.get(1);
			if( detector == Detectors.ec_ei ){
			    Vector<Double> ec_hit_rot = Calculator.getRotatedCoordinates(x,y,sector_ec);			    
			    h_bpid.h2_el_sect_ecalinhitxy.get(sector_ec).get(j).fill(ec_hit_rot.get(0), ec_hit_rot.get(1));
			    h_bpid.h2_el_sect_ecalin_sfxy.get(sector_ec).get(j).fill(ec_hit_rot.get(0), ec_hit_rot.get(1), ecdep_tot/p);
			}
			if( detector == Detectors.ec_eo ){
			    Vector<Double> ec_hit_rot = Calculator.getRotatedCoordinates(x,y,sector_ec);			   
			    h_bpid.h2_el_sect_ecalouthitxy.get(sector_ec).get(j).fill(ec_hit_rot.get(0), ec_hit_rot.get(1));
			    h_bpid.h2_el_sect_ecalout_sfxy.get(sector_ec).get(j).fill( ec_hit_rot.get(0), ec_hit_rot.get(1), ecdep_tot/p);
			}
		    }
		   
		    h_bpid.h2_el_sect_etotnphe.get(sector_ec).get(j).fill(nphe,ecdep_tot);
		    h_bpid.h2_el_sect_ectotp.get(sector_ec).get(j).fill(p, ecdep_tot/p);
		    h_bpid.h2_el_sect_ectotp2.get(sector_ec).get(j).fill(ecdep_tot, ecdep_tot/p);

		    h_bpid.h2_el_sect_wp.get(sector_ec).get(j).fill(p, w);		  

		    //h_bpid.h2_el_sect_pcalp.get(sector_ec).get(j).fill(p, e_pcal/p);
		    h_bpid.h2_el_sect_eieo.get(sector_ec).get(j).fill(e_ecal_ei, e_ecal_eo);

 		    h_bpid.h2_el_sect_pcalecal.get(sector_ec).get(j).fill(e_pcal, e_ecal);  
		    //System.out.println(" >> here " + w + " " + sector_ec);
		    h_bpid.h_el_sect_w.get(sector_ec).get(j).fill(w);
		    
		}
		
		if( sector_pcal >= 0 ){

		}
	
	    }
	    else if( !pass ){
	    	break;
	    }		    
	}
    }


    public void fillProtonPID(Vector<Boolean> temp_pass, DataEvent event, int rec_i ){ 

	int j = 0;
	
	for( boolean pass : temp_pass ){
	   	    
	    if( pass ){		
		DataBank recBank = event.getBank("REC::Particle");
		DataBank eventBank = event.getBank("REC::Event");
 		DataBank scintBank = event.getBank("REC::Scintillator");

		LorentzVector lv_pr = Calculator.lv_particle(recBank,rec_i, PhysicalConstants.protonID);
		float pr_beta_clas12 = recBank.getFloat("beta",rec_i);

		double p = lv_pr.p();
		double theta = Math.toDegrees(lv_pr.theta());
		double phi = Math.toDegrees(lv_pr.phi());
 		double pr_beta_mntm = p/Math.sqrt(p*p + PhysicalConstants.mass_proton * PhysicalConstants.mass_proton);

 		h_bprotonpid.h_pr_beta_mntm.get(j).fill(pr_beta_mntm);
		h_bprotonpid.h2_pr_vzphi.get(j).fill(p, phi);

		Map<Integer, Double> m_edep = Detectors.getEDepCal( event, rec_i );
		
		//CALCULATE DELTA TIME FOR EACH HIT
		for( int i = 0; i < scintBank.rows(); i++){
			    
		    int pindex = scintBank.getShort("pindex",i);
		    if( pindex == rec_i ){
			
			int scint_sector = scintBank.getInt("sector",i) - 1;
			int scint_detector = scintBank.getByte("detector",i);
			int scint_layer = scintBank.getByte("layer",i);
			int scint_bar = scintBank.getInt("component",i) - 1  ;
			
			double ftof_e  = scintBank.getFloat("energy",i)/100.0;
			
			double start_time = eventBank.getFloat("STTime",0);
			double r_path = scintBank.getFloat("path",i);
			double t_ftof = scintBank.getFloat("time",i);
			double pr_tof = t_ftof - start_time;
			double pr_beta_time = r_path/pr_tof * (1.0/30.0); 
			double pr_tmeas = r_path/pr_beta_mntm * (1.0/30.0);
			double pr_deltime = -pr_tmeas + pr_tof;
			double pr_delbeta = pr_beta_time - pr_beta_mntm;
		
			double pr_masstime = p*Math.sqrt( 1/(pr_beta_time*pr_beta_time) - 1.0 );
			
 			if( scint_sector >= 0 && scint_detector == 12 ){ // && scint_layer == 1){
		
			    //////////////////////////

			    if( scint_layer == 1 && scint_bar >= 0 ){
				h_bprotonpid.m_pr_sect_panel_deltp.get(scint_sector).get(scint_bar).get(j).fill(p,pr_deltime);
			    }
			    
			    if(  scint_layer == 1 ){
				//System.out.println(" ftofe "  + ftof_e) ;
				h_bprotonpid.h_pr_masstime.get(j).fill(pr_masstime);
				h_bprotonpid.h2_pr_masstimep.get(j).fill(pr_masstime,p);
				
				h_bprotonpid.h_pr_rpath.get(j).fill(r_path);
				h_bprotonpid.h2_pr_tof.get(j).fill(p,pr_tof);		       
				
				h_bprotonpid.h2_pr_deltimep.get(j).fill(p,pr_deltime);			
				h_bprotonpid.h2_pr_betap.get(j).fill(p,pr_beta_time);		
				
				h_bprotonpid.h2_pr_deltabeta.get(j).fill(p, pr_delbeta);
				h_bprotonpid.h_pr_p.get(j).fill(p);			    
				
				h_bprotonpid.h2_pr_sect_betap.get(scint_sector).get(j).fill(p,pr_beta_time);
				h_bprotonpid.h2_pr_sect_deltabeta.get(scint_sector).get(j).fill(p, pr_delbeta);
				h_bprotonpid.h2_pr_sect_deltimep.get(scint_sector).get(j).fill(p, pr_deltime);			    
				h_bprotonpid.h_pr_beta_time.get(j).fill(pr_beta_time);
				h_bprotonpid.h_pr_sect_masstime.get(scint_sector).get(j).fill(pr_masstime);
				h_bprotonpid.h2_pr_sect_masstimep.get(scint_sector).get(j).fill(pr_masstime,p);
								
				h_bprotonpid.h_pr_ftof_l1_e.get(j).fill( ftof_e );
				h_bprotonpid.h_pr_sect_ftof_l1_e.get(scint_sector).get(j).fill( ftof_e );				
			    }
			    if(  scint_layer == 2 ){

				h_bprotonpid.h_pr_ftof_l2_masstime.get(j).fill(pr_masstime);
				h_bprotonpid.h2_pr_ftof_l2_masstimep.get(j).fill(pr_masstime,p);
				
				h_bprotonpid.h2_pr_ftof_l2_tof.get(j).fill(p,pr_tof);		       
				
				h_bprotonpid.h2_pr_ftof_l2_deltimep.get(j).fill(p,pr_deltime);			
				h_bprotonpid.h2_pr_ftof_l2_betap.get(j).fill(p,pr_beta_time);		
				
				h_bprotonpid.h2_pr_ftof_l2_deltabeta.get(j).fill(p, pr_delbeta);
				
				h_bprotonpid.h2_pr_sect_ftof_l2_betap.get(scint_sector).get(j).fill(p,pr_beta_time);
				h_bprotonpid.h2_pr_sect_ftof_l2_deltabeta.get(scint_sector).get(j).fill(p, pr_delbeta);
				h_bprotonpid.h2_pr_sect_ftof_l2_deltimep.get(scint_sector).get(j).fill(p, pr_deltime);			    
				h_bprotonpid.h_pr_ftof_l2_beta_time.get(j).fill(pr_beta_time);
				h_bprotonpid.h_pr_sect_ftof_l2_masstime.get(scint_sector).get(j).fill(pr_masstime);
				h_bprotonpid.h2_pr_sect_ftof_l2_masstimep.get(scint_sector).get(j).fill(pr_masstime,p);
				
				h_bprotonpid.h_pr_ftof_l2_e.get(j).fill( ftof_e );
				h_bprotonpid.h_pr_sect_ftof_l2_e.get(scint_sector).get(j).fill( ftof_e );
			    }
			}					       		
		    }
		}

		///////////////////////
		//DRIFT CHAMBERS
		if( event.hasBank("REC::Track") && event.hasBank("TimeBasedTrkg::TBCrosses") && event.hasBank("TimeBasedTrkg::TBTracks") ){
		    int dc_sector_r1 = Detectors.getDCSectorR1(event, rec_i) - 1;
		    double dc_x1 = Detectors.getDCCrossX1(event, rec_i);
		    double dc_y1 = Detectors.getDCCrossY1(event, rec_i);
		    
		    int dc_sector_r3 = Detectors.getDCSectorR3(event, rec_i) - 1;
		    double dc_x3 = Detectors.getDCCrossX3(event, rec_i);
		    double dc_y3 = Detectors.getDCCrossY3(event, rec_i);
		    
		    Vector<Double> dc_r1_rotxy = Calculator.getRotatedCoordinates(dc_x1,dc_y1,dc_sector_r1);
		    Vector<Double> dc_r3_rotxy = Calculator.getRotatedCoordinates(dc_x3,dc_y3,dc_sector_r3);
		    
		    if( dc_x1 > -1000 && dc_y1 > -1000 && dc_sector_r1 >= 0){
			Vector<Double> dc_r1_locxy = Detectors.getDCCrossLocalR1(event, rec_i);				
			h_bprotonpid.h2_pr_dchit_R1_xy.get(j).fill(dc_r1_locxy.get(0), dc_r1_locxy.get(1));
		    }			   
		    if( dc_x3 > -1000 && dc_y3 > -1000 && dc_sector_r3 >= 0){
			Vector<Double> dc_r3_locxy = Detectors.getDCCrossLocalR3(event, rec_i);				
			h_bprotonpid.h2_pr_dchit_R3_xy.get(j).fill(dc_r3_locxy.get(0), dc_r3_locxy.get(1));							
		    }			    
		}
				       												

		int sector_ec = Detectors.getSectorECAL( event, rec_i ) - 1; 
		int sector_pcal = Detectors.getSectorPCAL( event, rec_i ) - 1;

		double e_pcal = 0.0;
		double e_ecal_ei = 0.0;
		double e_ecal_eo = 0.0;

		double pcal_thickness = 67.5 + 6.16; //15 * 4.5 cm + 14 * 0.22 cm
		double ecal_thickness = 390.0 + 90.706; // 39 * 10 cm + 38 * 2.387;		
		double theta_edep = Math.toRadians( theta - 10.0 );
		double l = pcal_thickness * (1.0 / Math.cos( theta_edep ));
		//System.out.println(" >> l " + l );

		for( Map.Entry<Integer,Double> entry : m_edep.entrySet() ){
		    int layer = entry.getKey();
		    double edep = entry.getValue();		    
		    if( layer == Detectors.pcal ){
			h_bprotonpid.h_pr_pcal_e.get(j).fill(edep/l);
			if( sector_pcal >= 0 ){ h_bprotonpid.h_pr_sect_pcal_e.get(sector_pcal).get(j).fill(edep/l); e_pcal = edep; }
		    }
		    if( layer == Detectors.ec_ei ){
			h_bprotonpid.h_pr_eical_e.get(j).fill(edep);		
			if( sector_ec >= 0 ) { h_bprotonpid.h_pr_sect_eical_e.get(sector_ec).get(j).fill(edep); e_ecal_ei = edep; }
		    }
		    if( layer == Detectors.ec_eo ){
			h_bprotonpid.h_pr_eocal_e.get(j).fill(edep);		
			if( sector_ec >= 0 ){h_bprotonpid.h_pr_sect_eocal_e.get(sector_ec).get(j).fill(edep); e_ecal_eo = edep;}
		    }		    
		}
		
		double etot = e_ecal_ei + e_ecal_eo;
		h_bprotonpid.h2_pr_ectotp.get(j).fill(p, etot/p);

		if( sector_ec >= 0 ){
		    h_bprotonpid.h2_pr_sect_ectotp.get(sector_ec).get(j).fill(p, etot/p );		    
		}
		
		/*if( event.hasBank("CTOF::hits") && event.hasBank("REC::Scintillator")  ){
		    System.out.println(" CUT LVL " + j );
		    double ctof_path_length = Detectors.getFTOFPathLength(event, rec_i);
		    if( ctof_path_length > 0.0 ){
			DataBank scintBank2 = event.getBank("REC::Scintillator");
			DataBank ctofhits = event.getBank("CTOF::hits");
			
			scintBank2.show();
			ctofhits.show();
		    }
		    System.out.println(" FILLING HISTO WITH PL " + ctof_path_length );
		}
	    	*/		
	       
	    }	    	    	  	   
	    else if( !pass) {
		break;
	    }
	    j++;
	}
    }

    public void fillKaonPPID (Vector<Boolean> temp_pass, DataEvent event, int rec_i ){ 

	int j = 0;	
	for( boolean pass : temp_pass ){
	    if( pass ){		
		DataBank recBank = event.getBank("REC::Particle");
		DataBank eventBank = event.getBank("REC::Event");
		DataBank scintBank = event.getBank("REC::Scintillator");
		
		LorentzVector lv_kp = Calculator.lv_particle(recBank,rec_i, PhysicalConstants.kaonplusID);
		float kp_beta_clas12 = recBank.getFloat("beta",rec_i);
		//h_bkppid.h_kp_beta_time.get(j).fill(kp_beta_clas12);

		double p = lv_kp.p();
		double theta = Math.toDegrees(lv_kp.theta());
		double phi = Math.toDegrees(lv_kp.phi());
		double vz = recBank.getFloat("vz",rec_i);
		double kp_beta_mntm = p/Math.sqrt(p*p + PhysicalConstants.mass_kaon * PhysicalConstants.mass_kaon);

		h_bkppid.h_kp_p.get(j).fill(p);
		h_bkppid.h_kp_theta.get(j).fill(theta);
		h_bkppid.h_kp_phi.get(j).fill(phi);
		h_bkppid.h_kp_vz.get(j).fill(vz);
		h_bkppid.h2_kp_thetap.get(j).fill(p, theta);

		Map<Integer, Double> m_edep = Detectors.getEDepCal( event, rec_i );

		//CALCULATE DELTA TIME FOR EACH HIT
		for( int i = 0; i < scintBank.rows(); i++){
			    
		    int pindex = scintBank.getShort("pindex",i);
		    if( pindex == rec_i ){

			int scint_sector = scintBank.getInt("sector",i) - 1;
			int scint_detector = scintBank.getByte("detector",i);
			int scint_layer = scintBank.getByte("layer",i);
			int scint_bar = scintBank.getInt("component",i) - 1;

			double ftof_e  = scintBank.getFloat("energy",i)/100.0;

 			double start_time = eventBank.getFloat("STTime",0);
			double r_path = scintBank.getFloat("path",i);
			double t_ftof = scintBank.getFloat("time",i);
			double kp_tof = t_ftof - start_time;
			double kp_beta_time = r_path/kp_tof * (1.0/30.0); 
			double kp_tmeas = r_path/kp_beta_mntm * (1.0/30.0);
			double kp_deltime = -kp_tmeas + kp_tof;
			double kp_delbeta = kp_beta_time - kp_beta_mntm;

			double kp_masstime = p*Math.sqrt( 1/(kp_beta_time*kp_beta_time) - 1.0 );
			if( scint_sector >= 0 && scint_detector == 12 ){
			    
			    h_bkppid.h_kp_rpath.get(j).fill(r_path);
			    h_bkppid.h2_kp_tof.get(j).fill(p,kp_tof);		       
			    h_bkppid.h_kp_beta_mntm.get(j).fill(kp_beta_mntm);
			    
			    h_bkppid.h2_kp_deltimep.get(j).fill(p,kp_deltime);			
			    h_bkppid.h2_kp_betap.get(j).fill(p,kp_beta_clas12);		
			    
			    h_bkppid.h2_kp_deltabeta.get(j).fill(p, kp_delbeta);
			    h_bkppid.h_kp_p.get(j).fill(p);
			
			    h_bkppid.h2_kp_sect_betap.get(scint_sector).get(j).fill(p,kp_beta_clas12);
			    h_bkppid.h2_kp_sect_deltabeta.get(scint_sector).get(j).fill(p,kp_delbeta);
			    h_bkppid.h2_kp_sect_deltimep.get(scint_sector).get(j).fill(p,kp_deltime);		   			    			    
			    //////////////////////////
			    
			    if( scint_layer == 1 && scint_bar >= 0 ){
				//h_bkppid.m_kp_sect_panel_deltp.get(scint_sector).get(scint_bar).get(j).fill(p,kp_deltime);								
			    }
			    
			    if(  scint_layer == 1 ){
				//System.out.println(" ftofe "  + ftof_e) ;
				h_bkppid.h_kp_masstime.get(j).fill(kp_masstime);
				h_bkppid.h2_kp_masstimep.get(j).fill(kp_masstime,p);
				
				h_bkppid.h_kp_rpath.get(j).fill(r_path);
				h_bkppid.h2_kp_tof.get(j).fill(p,kp_tof);		       
				
				h_bkppid.h2_kp_deltimep.get(j).fill(p,kp_deltime);			
				h_bkppid.h2_kp_betap.get(j).fill(p,kp_beta_time);		
				
				h_bkppid.h2_kp_deltabeta.get(j).fill(p, kp_delbeta);
				h_bkppid.h_kp_p.get(j).fill(p);			    
				
				h_bkppid.h2_kp_sect_betap.get(scint_sector).get(j).fill(p,kp_beta_time);
				h_bkppid.h2_kp_sect_deltabeta.get(scint_sector).get(j).fill(p, kp_delbeta);
				h_bkppid.h2_kp_sect_deltimep.get(scint_sector).get(j).fill(p, kp_deltime);			    
				h_bkppid.h_kp_beta_time.get(j).fill(kp_beta_time);
				h_bkppid.h_kp_sect_masstime.get(scint_sector).get(j).fill(kp_masstime);
				h_bkppid.h2_kp_sect_masstimep.get(scint_sector).get(j).fill(kp_masstime,p);
				
				
				h_bkppid.h_kp_ftof_l1_e.get(j).fill( ftof_e );
				h_bkppid.h_kp_sect_ftof_l1_e.get(scint_sector).get(j).fill( ftof_e );				
			    }
			    if(  scint_layer == 2 ){
				
				h_bkppid.h_kp_ftof_l2_masstime.get(j).fill(kp_masstime);
				h_bkppid.h2_kp_ftof_l2_masstimep.get(j).fill(kp_masstime,p);
				
				h_bkppid.h2_kp_ftof_l2_tof.get(j).fill(p,kp_tof);		       
			       
				h_bkppid.h2_kp_ftof_l2_deltimep.get(j).fill(p,kp_deltime);			
				h_bkppid.h2_kp_ftof_l2_betap.get(j).fill(p,kp_beta_time);		
				
				h_bkppid.h2_kp_ftof_l2_deltabeta.get(j).fill(p,kp_delbeta);
				
				h_bkppid.h2_kp_sect_ftof_l2_betap.get(scint_sector).get(j).fill(p,kp_beta_time);
				h_bkppid.h2_kp_sect_ftof_l2_deltabeta.get(scint_sector).get(j).fill(p, kp_delbeta);
				h_bkppid.h2_kp_sect_ftof_l2_deltimep.get(scint_sector).get(j).fill(p, kp_deltime);			    
				h_bkppid.h_kp_ftof_l2_beta_time.get(j).fill(kp_beta_time);
				h_bkppid.h_kp_sect_ftof_l2_masstime.get(scint_sector).get(j).fill(kp_masstime);
				h_bkppid.h2_kp_sect_ftof_l2_masstimep.get(scint_sector).get(j).fill(kp_masstime,p);
				
				h_bkppid.h_kp_ftof_l2_e.get(j).fill( ftof_e );
				h_bkppid.h_kp_sect_ftof_l2_e.get(scint_sector).get(j).fill( ftof_e );
			    }
			}					       		
		    }		    		    		   
		}		
	    
	
	
		///////////////////////
		//DRIFT CHAMBERS
		if( event.hasBank("REC::Track") && event.hasBank("TimeBasedTrkg::TBCrosses") && event.hasBank("TimeBasedTrkg::TBTracks") ){
		    int dc_sector_r1 = Detectors.getDCSectorR1(event, rec_i) - 1;
		    double dc_x1 = Detectors.getDCCrossX1(event, rec_i);
		    double dc_y1 = Detectors.getDCCrossY1(event, rec_i);
		    
		    int dc_sector_r3 = Detectors.getDCSectorR3(event, rec_i) - 1;
		    double dc_x3 = Detectors.getDCCrossX3(event, rec_i);
		    double dc_y3 = Detectors.getDCCrossY3(event, rec_i);
		    
		    Vector<Double> dc_r1_rotxy = Calculator.getRotatedCoordinates(dc_x1,dc_y1,dc_sector_r1);
		    Vector<Double> dc_r3_rotxy = Calculator.getRotatedCoordinates(dc_x3,dc_y3,dc_sector_r3);
		    
		    if( dc_x1 > -1000 && dc_y1 > -1000 && dc_sector_r1 >= 0){
			Vector<Double> dc_r1_locxy = Detectors.getDCCrossLocalR1(event, rec_i);				
			h_bkppid.h2_kp_dchit_R1_xy.get(j).fill(dc_r1_locxy.get(0), dc_r1_locxy.get(1));
		    }			   
		    if( dc_x3 > -1000 && dc_y3 > -1000 && dc_sector_r3 >= 0){
			Vector<Double> dc_r3_locxy = Detectors.getDCCrossLocalR3(event, rec_i);				
			h_bkppid.h2_kp_dchit_R3_xy.get(j).fill(dc_r3_locxy.get(0), dc_r3_locxy.get(1));							
		    }			    
		}
				       												

		int sector_ec = Detectors.getSectorECAL( event, rec_i ) - 1; 
		int sector_pcal = Detectors.getSectorPCAL( event, rec_i ) - 1;

		double e_pcal = 0.0;
		double e_ecal_ei = 0.0;
		double e_ecal_eo = 0.0;

		double pcal_thickness = 67.5 + 6.16; //15 * 4.5 cm + 14 * 0.22 cm
		double ecal_thickness = 390.0 + 90.706; // 39 * 10 cm + 38 * 2.387;		
		double theta_edep = Math.toRadians( theta - 10.0 );
		double l = pcal_thickness * (1.0 / Math.cos( theta_edep ));
		//System.out.println(" >> l " + l );

		for( Map.Entry<Integer,Double> entry : m_edep.entrySet() ){
		    int layer = entry.getKey();
		    double edep = entry.getValue();		    
		    if( layer == Detectors.pcal ){
			h_bkppid.h_kp_pcal_e.get(j).fill(edep/l);
			if( sector_pcal >= 0 ){ h_bkppid.h_kp_sect_pcal_e.get(sector_pcal).get(j).fill(edep/l); e_pcal = edep; }
		    }
		    if( layer == Detectors.ec_ei ){
			h_bkppid.h_kp_eical_e.get(j).fill(edep);		
			if( sector_ec >= 0 ) { h_bkppid.h_kp_sect_eical_e.get(sector_ec).get(j).fill(edep); e_ecal_ei = edep; }
		    }
		    if( layer == Detectors.ec_eo ){
			h_bkppid.h_kp_eocal_e.get(j).fill(edep);		
			if( sector_ec >= 0 ){ h_bkppid.h_kp_sect_eocal_e.get(sector_ec).get(j).fill(edep); e_ecal_eo = edep;}
		    }		    
		}
		
		double etot = e_ecal_ei + e_ecal_eo;
		h_bkppid.h2_kp_ectotp.get(j).fill(p, etot/p);

		if( sector_ec >= 0 ){
		    h_bkppid.h2_kp_sect_ectotp.get(sector_ec).get(j).fill(p, etot/p );		    
		}	    	    
	    }
	    else if( !pass) {
		break;
	    }
	    j++;
	}
	
	
    }

    public void fillPNDriftChamber( DataEvent event, int rec_i ){

	if( event.hasBank("REC::Track") && event.hasBank("TimeBasedTrkg::TBCrosses") && event.hasBank("TimeBasedTrkg::TBTracks") ){
	    int dc_sector_r1 = Detectors.getDCSectorR1(event, rec_i) - 1;
	    double dc_x1 = Detectors.getDCCrossX1(event, rec_i);
	    double dc_y1 = Detectors.getDCCrossY1(event, rec_i);
	

	    int dc_sector_r2 = Detectors.getDCSectorR2(event, rec_i) - 1;
    
	    int dc_sector_r3 = Detectors.getDCSectorR3(event, rec_i) - 1;
	    double dc_x3 = Detectors.getDCCrossX3(event, rec_i);
	    double dc_y3 = Detectors.getDCCrossY3(event, rec_i);
	    
	    Vector<Double> dc_r1_rotxy = Calculator.getRotatedCoordinates(dc_x1,dc_y1,dc_sector_r1);
	    Vector<Double> dc_r3_rotxy = Calculator.getRotatedCoordinates(dc_x3,dc_y3,dc_sector_r3);
	    
	    if( dc_x1 > -1000 && dc_y1 > -1000 && dc_sector_r1 >= 0){
		Vector<Double> dc_r1_locxy = Detectors.getDCCrossLocalR1(event, rec_i);
		if( dc_r1_locxy.size() > 1 ){
		    h_bpid.h2_el_dc_R1_allcharge.fill(dc_r1_locxy.get(0), dc_r1_locxy.get(1));
		}
 	    }

	    if( dc_sector_r2 >= 0){
		Vector<Double> dc_r2_locxy = Detectors.getDCCrossLocalR2(event, rec_i);
		if( dc_r2_locxy.size() > 1 ){
		    h_bpid.h2_el_dc_R2_allcharge.fill(dc_r2_locxy.get(0), dc_r2_locxy.get(1));
		}
	    }

	    if( dc_x3 > -1000 && dc_y3 > -1000 && dc_sector_r3 >= 0){
		Vector<Double> dc_r3_locxy = Detectors.getDCCrossLocalR3(event, rec_i);
		if( dc_r3_locxy.size() > 1 ){
		    h_bpid.h2_el_dc_R3_allcharge.fill(dc_r3_locxy.get(0), dc_r3_locxy.get(1));
		}
	    }
	    
	}
	
       
    }


    public void sliceNFitHistograms(){

	int counter = 0;
	int cutlvl = 4;
	HashMap<Integer, Vector<Double> > h2_temp_binX = new HashMap<Integer, Vector<Double> >();

	
	for( Vector<H2F> h2_temp : h_bpid.h2_el_sect_ectotp ){
	    System.out.println(">> PREPARING TO SLICE 'N' FIT " + h2_temp.get(cutlvl).getTitle() );
	    H2F h2_temp_rebinX = h2_temp.get(cutlvl).rebinX(5); //was 5
	    H2F h2_temp_rebinXY = h2_temp_rebinX.rebinY(5);

	    ArrayList <H1F> h_temp_rebinXY_sliceX = new ArrayList<H1F>();
	    h_temp_rebinXY_sliceX = h2_temp_rebinXY.getSlicesX();

 	    Vector<Double> bin_center_temp = new Vector<Double>();
	    for( int bin = 1; bin < h2_temp_rebinXY.getXAxis().getNBins(); bin++ ){ /// was bin = 1 to start 
		double bin_center = h2_temp_rebinXY.getXAxis().getBinCenter(bin);
		//System.out.println(" >> SECTOR " + counter + " BIN CENTER " + bin_center ); 
		bin_center_temp.add(bin_center);
		h2_temp_binX.put(counter,bin_center_temp);
		
	    }
		
	    h_bpid.m_el_sect_slices_ectotp.put(counter, h_temp_rebinXY_sliceX);
	    
	    //ParallelSliceFitter fit_temp = new ParallelSliceFitter(h2_temp_rebinXY);
	    //fit_temp.setMinBin(1);
	    //fit_temp.setMaxBin(18);
 	    //fit_temp.fitSlicesX();
	    //GraphErrors temp_mean = fit_temp.getMeanSlices();
	    //GraphErrors temp_sigma = fit_temp.getSigmaSlices();	   
	    //h_bpid.g_sf_sect_meansfits.add(temp_mean);
	    //h_bpid.g_sf_sect_sigmasfits.add(temp_sigma);
	    
	    counter=counter+1;
	}
	
	//System.out.println(" >> FITTING HISTOGRAMS ACROSS ALL SECTORS ");
	for( int sector = 0; sector < 6; sector++ ){
	    //System.out.println(" FITTING SECTOR " + sector );
	    ArrayList<H1F> al_h_ectotp = h_bpid.m_el_sect_slices_ectotp.get(sector);
	    //System.out.println( " >> NUMBER OF SLICES " + al_h_ectotp.size());

	    GraphErrors g_temp = h_bpid.g_sf_sect_means_bc.get(sector);
	    GraphErrors g_temp_sigmas = h_bpid.g_sf_sect_sigmas_bc.get(sector);
	    g_temp.setTitle("Mean Sector " + Integer.toString(sector));
	    g_temp_sigmas.setTitle(" SIGMAS SECTOR " + Integer.toString(sector));
	    
	    for( int n_htemp = 1; n_htemp < al_h_ectotp.size() -1; n_htemp++){
		System.out.println(" >> n_htemp " + n_htemp );
		H1F h_temp = al_h_ectotp.get(n_htemp);
		F1D fit_temp = Calculator.fitHistogram(h_temp);
		double fit_mean = fit_temp.getParameter(1);
		double fit_sigma = fit_temp.getParameter(2);
		double fit_mean_err = fit_temp.parameter(1).error();
		double fit_sigma_err = fit_temp.parameter(2).error();
		//System.out.println(" >> size of bin vector " + h2_temp_binX.get(sector).size() );
		//System.out.println(" >> ADDING POINT " + h2_temp_binX.get(sector).get(n_htemp) +  " " + fit_mean + " ERROR " + fit_mean_err + " SIG ERROR " + fit_sigma_err);
		if( fit_mean > 1.0 || fit_mean < 0.0 || Math.abs(fit_sigma) > 1.0 ){ continue; }
		g_temp.addPoint(h2_temp_binX.get(sector).get(n_htemp), fit_mean, fit_mean_err, fit_sigma_err);
		g_temp_sigmas.addPoint(h2_temp_binX.get(sector).get(n_htemp), fit_sigma, fit_mean_err, fit_sigma_err);
	    }

	}

    }

    public void savePIDHistograms( boolean view ){

	//sliceNFitHistograms();
	
	h_bpid.electronHistoToHipo();
	//h_bprotonpid.protonHistoToHipo();
	//h_bkppid.kaonpHistoToHipo();
	if( view ){
	    System.out.println(">> VIEWING HIPO " );
	    //h_bpid.viewHipoOut();
	    //h_bprotonpid.viewHipoOut();
	    //h_bkppid.viewHipoOut();
	    h_bpid.printHistograms();
	    
	}
	h_bpid.printCutRatesToText();

	System.out.println(">> DONE WITH PID LVL HISTOGRAMS ");

    }


    public void mergeHistograms(){

	/*System.out.println(">> MERGING HISTOGRAMS FILES NOW ");
	String file_path = "/w/hallb-scifs17exp/clas12/bclary/pics/pid_clary/";
 	String[] merge_command = new String[] {"/bin/bash ","/w/hallb-scifs17exp/clas12/bclary/extras/coatjava_5b.3.3/bin/hadd " + file_path+"h_"+Integer.toString(run_number)+"_el_all.hipo " + file_path+"h_"+Integer.toString(run_number)+"_thread-*_el_pid_clary.hipo"};
	try{
	    System.out.println(">> ISSUING COMMAND TO PROCESSBUILDER " + merge_command[0] + merge_command[1] );
	    ProcessBuilder pb = new ProcessBuilder(merge_command);
	    pb.inheritIO();
	    
	    try{
		Process process = pb.start();
	    
		process.waitFor();
	    }
	    catch(IOException e){
		System.out.println(">>> PROCESS ERROR " );
	    }
	}
	catch (InterruptedException e) {
	    System.out.println(">>> PROCESS BUILDER ERROR  interrupted.");
	}
	System.out.println(">> FINISHED MERGING ");
	*/
    }

}
