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
import org.jlab.clas.analysis.clary.BMLEProtonHistograms;
import org.jlab.clas.analysis.clary.BMLEKaonHistograms;


import java.util.*;
import java.io.*;

public class BHistoMLE {

    public BHistoMLE(){

    }

    private int run_number = -1;
    private String n_thread = null;
    BMLEProtonHistograms h_prmle;
    BMLEKaonHistograms h_kpmle;
    //BMLEPionHisto h_ppmle;

    public BHistoMLE( int temp_run, String thread ){

	run_number = temp_run;
	n_thread = thread;
	h_prmle = new BMLEProtonHistograms(run_number, thread);
	h_kpmle = new BMLEKaonHistograms(run_number, thread);
	//h_ppmle = new BMLEPionHisto();

	createHistograms();

    }
    
    public void createHistograms(){

	h_prmle.createProtonMLEHistograms();		
	for( int s  = 0; s <= 6; s++ ){
	    h_prmle.createProtonSectorHistograms(s,0);
	}

	h_kpmle.createKaonMLEHistograms();	
	for( int s  = 0; s <= 6; s++ ){
	    h_kpmle.createKaonSectorHistograms(s,0);
	}
	//h_ppmle.createPionMLEHistograms();
	h_prmle.createProtonFTOFHistograms(6,74,1);	   		

    }

    public void fillProtonConfidenceLevel(double conf_lvl ){
	
	h_prmle.h_pr_conflvl.get(0).fill(conf_lvl);
	
    }

    public void fillDCTraj(DataEvent event, int rec_i ){

	if( event.hasBank("REC::Traj") ){
	    Vector<Double> dc_hit_R1 = Detectors.getDCTrajR1(event,rec_i);
	    Vector<Double> dc_hit_R2 = Detectors.getDCTrajR2(event,rec_i);
	    Vector<Double> dc_hit_R3 = Detectors.getDCTrajR3(event,rec_i);
	    
	    if( dc_hit_R1.get(0) > -1000 && dc_hit_R1.get(1) > -1000 ){
		
		int dc_sect_traj1 = Detectors.getDCTrajSect(event, rec_i, 12) - 1; // 12, 24, 36 for R1, R2, R3
		int dc_sect_traj2 = Detectors.getDCTrajSect(event, rec_i, 24) - 1; // 12, 24, 36 for R1, R2, R3
		int dc_sect_traj3 = Detectors.getDCTrajSect(event, rec_i, 36) - 1; // 12, 24, 36 for R1, R2, R3
		
		Vector<Double> dc_hit_R1_rot = Calculator.getTrajRotatedCoordindate(dc_hit_R1.get(0),dc_hit_R1.get(1),dc_sect_traj1);
		Vector<Double> dc_hit_R2_rot = Calculator.getTrajRotatedCoordindate(dc_hit_R2.get(0),dc_hit_R2.get(1),dc_sect_traj2);
		Vector<Double> dc_hit_R3_rot = Calculator.getTrajRotatedCoordindate(dc_hit_R3.get(0),dc_hit_R3.get(1),dc_sect_traj3);
		
		h_prmle.h2_poscharge_dc_R1_traj.fill(dc_hit_R1.get(0),dc_hit_R1.get(1));
		h_prmle.h2_poscharge_dc_R2_traj.fill(dc_hit_R2.get(0),dc_hit_R2.get(1));
		h_prmle.h2_poscharge_dc_R3_traj.fill(dc_hit_R3.get(0),dc_hit_R3.get(1));
		
		h_prmle.h2_poscharge_dc_R1_traj_rot.fill(dc_hit_R1_rot.get(0),dc_hit_R1_rot.get(1));
		h_prmle.h2_poscharge_dc_R2_traj_rot.fill(dc_hit_R2_rot.get(0),dc_hit_R2_rot.get(1));
		h_prmle.h2_poscharge_dc_R3_traj_rot.fill(dc_hit_R3_rot.get(0), dc_hit_R3_rot.get(1));

	    }
	}

	    
    }

    public void fillProtonMLE( DataEvent event, int rec_i ){
	
	int j = 0;

	DataBank recBank = event.getBank("REC::Particle");
	DataBank eventBank = event.getBank("REC::Event");
	DataBank scintBank = event.getBank("REC::Scintillator");

	LorentzVector lv_pr = Calculator.lv_particle(recBank,rec_i, PhysicalConstants.protonID);
	float pr_beta_clas12 = recBank.getFloat("beta",rec_i);

	double p = lv_pr.p();
	double theta = Math.toDegrees(lv_pr.theta());
	double phi = Math.toDegrees(lv_pr.phi());
	double vz = recBank.getFloat("vz",rec_i);
	double vz_mod = (recBank.getFloat("vz",0) - vz);///2.0;
	double pr_beta_mntm = p/Math.sqrt(p*p + PhysicalConstants.mass_pion * PhysicalConstants.mass_pion);

	h_prmle.h_pr_beta_mntm.get(j).fill(pr_beta_mntm);
	h_prmle.h2_pr_vzphi.get(j).fill(vz, phi);
	h_prmle.h2_pr_ptheta.get(j).fill(p, theta);
	h_prmle.h2_pr_pphi.get(j).fill(p, phi);
	h_prmle.h_pr_vz.get(j).fill(vz);
	h_prmle.h_pr_vz_mod.get(j).fill(vz_mod);
	
	//Map<Integer, Double> m_edep = Detectors.getEDepCal( event, rec_i );
		
	////////////////////////////////
	//ELECTRON SECTOR
	int el_sect = Detectors.getSectorECAL( event, 0 ) - 1; // index 0 is trigger electron

	//CALCULATE DELTA TIME FOR EACH HIT
	for( int i = 0; i < scintBank.rows(); i++){
			    
	    int pindex = scintBank.getShort("pindex",i);
	    if( pindex == rec_i ){
			
		int scint_sector = scintBank.getInt("sector",i) - 1;
		int scint_detector = scintBank.getByte("detector",i);
		int scint_layer = scintBank.getByte("layer",i);
		int scint_bar = scintBank.getInt("component",i) - 1  ;
			
		double ftof_e  = scintBank.getFloat("energy",i)/100.0;
			
		double start_time = eventBank.getFloat("STTime",0); //USE WHEN USING CLAS12 ELECTRON PID ELSE CHANGE FOR MY PID
		double r_path = scintBank.getFloat("path",i);
		double t_ftof = scintBank.getFloat("time",i);
		double pr_tof = t_ftof - start_time;
		double pr_beta_time = r_path/pr_tof * (1.0/30.0); 
		double pr_tmeas = r_path/pr_beta_mntm * (1.0/30.0);
		double pr_deltime = -pr_tmeas + pr_tof;
		double pr_delbeta = pr_beta_time - pr_beta_mntm;
		//double beta_time = r_path/tof * (1.0/PhysicalConstants.speedOfLight);  
		
		double pr_masstime = p*Math.sqrt( 1/(pr_beta_time*pr_beta_time) - 1.0 );
			
		if( scint_sector >= 0 && scint_detector == 12 ){ // && scint_layer == 1){
		    //System.out.println(">> FILLING SCINT INFO HERE " );
		    //////////////////////////


		    if( scint_layer == 2 && scint_bar >= 0 ){
			h_prmle.m_pr_sect_panel_deltp.get(scint_sector).get(scint_bar).get(j).fill(p,pr_deltime);
			h_prmle.m_pr_sect_panel_deltime.get(scint_sector).get(scint_bar).get(j).fill(pr_deltime);
		    }
			    
		    h_prmle.h_pr_sect_deltime.get(scint_sector).get(j).fill(pr_deltime);

		    if(  scint_layer == 2 ){

			h_prmle.h_pr_ftof_l2_masstime.get(j).fill(pr_masstime);
			h_prmle.h2_pr_ftof_l2_masstimep.get(j).fill(pr_masstime,p);
				
			h_prmle.h2_pr_ftof_l2_tof.get(j).fill(p,pr_tof);		       
				
			h_prmle.h2_pr_ftof_l2_deltimep.get(j).fill(p,pr_deltime);			
			h_prmle.h2_pr_ftof_l2_betap.get(j).fill(p,pr_beta_time);		
				
			h_prmle.h2_pr_ftof_l2_deltabeta.get(j).fill(p, pr_delbeta);
			
			if( el_sect >= 0 ){
			    h_prmle.h2_pr_el_sect_betap.get(el_sect).get(j).fill(p, pr_beta_time);
			}

			h_prmle.h2_pr_sect_ftof_l2_betap.get(scint_sector).get(j).fill(p,pr_beta_time);
			h_prmle.h2_pr_sect_ftof_l2_deltabeta.get(scint_sector).get(j).fill(p, pr_delbeta);
			h_prmle.h2_pr_sect_ftof_l2_deltimep.get(scint_sector).get(j).fill(p, pr_deltime);			    
			h_prmle.h_pr_ftof_l2_beta_time.get(j).fill(pr_beta_time);
			h_prmle.h_pr_sect_ftof_l2_masstime.get(scint_sector).get(j).fill(pr_masstime);
			h_prmle.h2_pr_sect_ftof_l2_masstimep.get(scint_sector).get(j).fill(pr_masstime,p);
				
			h_prmle.h_pr_ftof_l2_e.get(j).fill( ftof_e );
			h_prmle.h_pr_sect_ftof_l2_e.get(scint_sector).get(j).fill( ftof_e );
		    }
		    if(  scint_layer == 1 ){
			//System.out.println(" ftofe "  + ftof_e) ;
			h_prmle.h_pr_masstime.get(j).fill(pr_masstime);
			h_prmle.h2_pr_masstimep.get(j).fill(pr_masstime,p);
				
			h_prmle.h_pr_rpath.get(j).fill(r_path);
			h_prmle.h2_pr_tof.get(j).fill(p,pr_tof);		       
				
			h_prmle.h2_pr_deltimep.get(j).fill(p,pr_deltime);			
			h_prmle.h2_pr_betap.get(j).fill(p,pr_beta_time);		
				
			h_prmle.h2_pr_deltabeta.get(j).fill(p, pr_delbeta);
			h_prmle.h_pr_p.get(j).fill(p);			    
				
			h_prmle.h2_pr_sect_betap.get(scint_sector).get(j).fill(p,pr_beta_time);
			h_prmle.h2_pr_sect_deltabeta.get(scint_sector).get(j).fill(p, pr_delbeta);
			h_prmle.h2_pr_sect_deltimep.get(scint_sector).get(j).fill(p, pr_deltime);			    
			h_prmle.h_pr_beta_time.get(j).fill(pr_beta_time);
			h_prmle.h_pr_sect_masstime.get(scint_sector).get(j).fill(pr_masstime);
			h_prmle.h2_pr_sect_masstimep.get(scint_sector).get(j).fill(pr_masstime,p);
						       	
			h_prmle.h_pr_ftof_l1_e.get(j).fill( ftof_e );
			h_prmle.h_pr_sect_ftof_l1_e.get(scint_sector).get(j).fill( ftof_e );				
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
		    
	    if( dc_x1 > -900 && dc_y1 > -900 && dc_sector_r1 >= 0){
		Vector<Double> dc_r1_locxy = Detectors.getDCCrossLocalR1(event, rec_i);				
		//h_prmle.h2_pr_dchit_R1_xy.get(j).fill(dc_r1_locxy.get(0), dc_r1_locxy.get(1));
	    }			   
	    if( dc_x3 > -900 && dc_y3 > -900 && dc_sector_r3 >= 0){
		Vector<Double> dc_r3_locxy = Detectors.getDCCrossLocalR3(event, rec_i);				
		//System.out.println(" >> " + dc_r3_locxy.size() );
		//h_prmle.h2_pr_dchit_R3_xy.get(j).fill(dc_r3_locxy.get(0), dc_r3_locxy.get(1));							
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

	/*	for( Map.Entry<Integer,Double> entry : m_edep.entrySet() ){
	    int layer = entry.getKey();
	    double edep = entry.getValue();		    
	    if( layer == Detectors.pcal ){
		h_prmle.h_pr_pcal_e.get(j).fill(edep/l);
		if( sector_pcal >= 0 ){ h_prmle.h_pr_sect_pcal_e.get(sector_pcal).get(j).fill(edep/l); e_pcal = edep; }
	    }
	    if( layer == Detectors.ec_ei ){
		h_prmle.h_pr_eical_e.get(j).fill(edep);		
		if( sector_ec >= 0 ) { h_prmle.h_pr_sect_eical_e.get(sector_ec).get(j).fill(edep); e_ecal_ei = edep; }
	    }
	    if( layer == Detectors.ec_eo ){
		h_prmle.h_pr_eocal_e.get(j).fill(edep);		
		if( sector_ec >= 0 ){h_prmle.h_pr_sect_eocal_e.get(sector_ec).get(j).fill(edep); e_ecal_eo = edep;}
	    }		    
	}
		
	double etot = e_ecal_ei + e_ecal_eo;
	h_prmle.h2_pr_ectotp.get(j).fill(p, etot/p);

	if( sector_ec >= 0 ){
	    h_prmle.h2_pr_sect_ectotp.get(sector_ec).get(j).fill(p, etot/p );		    
	}
	*/	
    }


    public void fillKaonConfidenceLevel(double conf_lvl ){	
	h_kpmle.h_kp_conflvl.get(0).fill(conf_lvl);	
    }	    

    public void fillKaonMLE( DataEvent event, int rec_i ){

	int j = 0;
	
	DataBank recBank = event.getBank("REC::Particle");
	DataBank eventBank = event.getBank("REC::Event");
	DataBank scintBank = event.getBank("REC::Scintillator");
	
	LorentzVector lv_kp = Calculator.lv_particle(recBank,rec_i, PhysicalConstants.kaonplusID);
	float kp_beta_clas12 = recBank.getFloat("beta",rec_i);

	double p = lv_kp.p();
	double theta = Math.toDegrees(lv_kp.theta());
	double phi = Math.toDegrees(lv_kp.phi());
	double vz = recBank.getFloat("vz",rec_i);
	double vz_mod = (recBank.getFloat("vz",0) - vz);///2.0;
	double kp_beta_mntm = p/Math.sqrt(p*p + PhysicalConstants.mass_kaon*PhysicalConstants.mass_kaon);

	h_kpmle.h_kp_beta_mntm.get(j).fill(kp_beta_mntm);
	h_kpmle.h2_kp_vzphi.get(j).fill(vz, phi);
	h_kpmle.h2_kp_ptheta.get(j).fill(p, theta);
	h_kpmle.h2_kp_pphi.get(j).fill(p, phi);
	h_kpmle.h_kp_vz.get(j).fill(vz);
	h_kpmle.h_kp_vz_mod.get(j).fill(vz_mod);

	//Map<Integer, Double> m_edep = Detectors.getEDepCal( event, rec_i );
		
	////////////////////////////////
	//ELECTRON SECTOR
	int el_sect = Detectors.getSectorECAL( event, 0 ) - 1; // index 0 is trigger electron

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
		double kp_tof = t_ftof - start_time;
		double kp_beta_time = r_path/kp_tof * (1.0/30.0); 
		double kp_tmeas = r_path/kp_beta_mntm * (1.0/30.0);
		double kp_deltime = -kp_tmeas + kp_tof;
		double kp_delbeta = kp_beta_time - kp_beta_mntm;
		
		double kp_masstime = p*Math.sqrt( 1/(kp_beta_time*kp_beta_time) - 1.0 );
			
		if( scint_sector >= 0 && scint_detector == 12 ){ // && scint_layer == 1){
		
		    //////////////////////////


		    if( scint_layer == 1 && scint_bar >= 0 ){
			//h_kpmle.m_kp_sect_panel_deltp.get(scint_sector).get(scint_bar).get(j).fill(p,kp_deltime);
		    }
			    
		    if(  scint_layer == 2 ){

			h_kpmle.h_kp_ftof_l2_masstime.get(j).fill(kp_masstime);
			h_kpmle.h2_kp_ftof_l2_masstimep.get(j).fill(kp_masstime,p);
				
			h_kpmle.h2_kp_ftof_l2_tof.get(j).fill(p,kp_tof);		       
				
			h_kpmle.h2_kp_ftof_l2_deltimep.get(j).fill(p,kp_deltime);			
			h_kpmle.h2_kp_ftof_l2_betap.get(j).fill(p,kp_beta_time);		
				
			h_kpmle.h2_kp_ftof_l2_deltabeta.get(j).fill(p, kp_delbeta);
			
			if( el_sect >= 0 ){
			    h_kpmle.h2_kp_el_sect_betap.get(el_sect).get(j).fill(p, kp_beta_time);
			}

			h_kpmle.h2_kp_sect_ftof_l2_betap.get(scint_sector).get(j).fill(p,kp_beta_time);
			h_kpmle.h2_kp_sect_ftof_l2_deltabeta.get(scint_sector).get(j).fill(p, kp_delbeta);
			h_kpmle.h2_kp_sect_ftof_l2_deltimep.get(scint_sector).get(j).fill(p, kp_deltime);			    
			h_kpmle.h_kp_ftof_l2_beta_time.get(j).fill(kp_beta_time);
			h_kpmle.h_kp_sect_ftof_l2_masstime.get(scint_sector).get(j).fill(kp_masstime);
			h_kpmle.h2_kp_sect_ftof_l2_masstimep.get(scint_sector).get(j).fill(kp_masstime,p);
				
			h_kpmle.h_kp_ftof_l2_e.get(j).fill( ftof_e );
			h_kpmle.h_kp_sect_ftof_l2_e.get(scint_sector).get(j).fill( ftof_e );
		    }
		    if(  scint_layer == 1 ){
			//System.out.println(" ftofe "  + ftof_e) ;
			h_kpmle.h_kp_masstime.get(j).fill(kp_masstime);
			h_kpmle.h2_kp_masstimep.get(j).fill(kp_masstime,p);
				
			h_kpmle.h_kp_rpath.get(j).fill(r_path);
			h_kpmle.h2_kp_tof.get(j).fill(p,kp_tof);		       
				
			h_kpmle.h2_kp_deltimep.get(j).fill(p,kp_deltime);			
			h_kpmle.h2_kp_betap.get(j).fill(p,kp_beta_time);		
				
			h_kpmle.h2_kp_deltabeta.get(j).fill(p, kp_delbeta);
			h_kpmle.h_kp_p.get(j).fill(p);			    
				
			h_kpmle.h2_kp_sect_betap.get(scint_sector).get(j).fill(p,kp_beta_time);
			h_kpmle.h2_kp_sect_deltabeta.get(scint_sector).get(j).fill(p, kp_delbeta);
			h_kpmle.h2_kp_sect_deltimep.get(scint_sector).get(j).fill(p, kp_deltime);			    
			h_kpmle.h_kp_beta_time.get(j).fill(kp_beta_time);
			h_kpmle.h_kp_sect_masstime.get(scint_sector).get(j).fill(kp_masstime);
			h_kpmle.h2_kp_sect_masstimep.get(scint_sector).get(j).fill(kp_masstime,p);
						       	
			h_kpmle.h_kp_ftof_l1_e.get(j).fill( ftof_e );
			h_kpmle.h_kp_sect_ftof_l1_e.get(scint_sector).get(j).fill( ftof_e );				
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
		    
	    if( dc_x1 > -900 && dc_y1 > -900 && dc_sector_r1 >= 0){
		Vector<Double> dc_r1_locxy = Detectors.getDCCrossLocalR1(event, rec_i);				
		//h_kpmle.h2_kp_dchit_R1_xy.get(j).fill(dc_r1_locxy.get(0), dc_r1_locxy.get(1));
	    }			   
	    if( dc_x3 > -900 && dc_y3 > -900 && dc_sector_r3 >= 0){
		Vector<Double> dc_r3_locxy = Detectors.getDCCrossLocalR3(event, rec_i);				
		//System.out.println(" >> " + dc_r3_locxy.size() );
		//h_kpmle.h2_kp_dchit_R3_xy.get(j).fill(dc_r3_locxy.get(0), dc_r3_locxy.get(1));							
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

	/*	for( Map.Entry<Integer,Double> entry : m_edep.entrySet() ){
	    int layer = entry.getKey();
	    double edep = entry.getValue();		    
	    if( layer == Detectors.pcal ){
		h_kpmle.h_pr_pcal_e.get(j).fill(edep/l);
		if( sector_pcal >= 0 ){ h_prmle.h_pr_sect_pcal_e.get(sector_pcal).get(j).fill(edep/l); e_pcal = edep; }
	    }
	    if( layer == Detectors.ec_ei ){
		h_kpmle.h_pr_eical_e.get(j).fill(edep);		
		if( sector_ec >= 0 ) { h_prmle.h_pr_sect_eical_e.get(sector_ec).get(j).fill(edep); e_ecal_ei = edep; }
	    }
	    if( layer == Detectors.ec_eo ){
		h_kpmle.h_pr_eocal_e.get(j).fill(edep);		
		if( sector_ec >= 0 ){h_prmle.h_pr_sect_eocal_e.get(sector_ec).get(j).fill(edep); e_ecal_eo = edep;}
	    }		    
	}
		
	double etot = e_ecal_ei + e_ecal_eo;
	h_prmle.h2_pr_ectotp.get(j).fill(p, etot/p);

	if( sector_ec >= 0 ){
	    h_prmle.h2_pr_sect_ectotp.get(sector_ec).get(j).fill(p, etot/p );		    
	}
	*/	
  
    }



    public void fillBetaAll(DataEvent event, int rec_i ){

	int j = 0;

	DataBank recBank = event.getBank("REC::Particle");
	DataBank eventBank = event.getBank("REC::Event");
	DataBank scintBank = event.getBank("REC::Scintillator");

	LorentzVector lv_pr = Calculator.lv_particle(recBank,rec_i);// PhysicalConstants.kaonplusID);
	float pr_beta_clas12 = recBank.getFloat("beta",rec_i);
	int pid_clas12 = recBank.getInt("pid",rec_i);

	double p = lv_pr.p();
	double theta = Math.toDegrees(lv_pr.theta());
	double phi = Math.toDegrees(lv_pr.phi());
	double pr_beta_mntm = p/Math.sqrt(p*p + PhysicalConstants.mass_proton * PhysicalConstants.mass_proton);

	//h_prmle.h_pr_beta_mntm.get(j).fill(pr_beta_mntm);
	//h_prmle.h2_pr_vzphi.get(j).fill(p, phi);

	//Map<Integer, Double> m_edep = Detectors.getEDepCal( event, rec_i );
		
	////////////////////////////////
	//ELECTRON SECTOR
	int el_sect = Detectors.getSectorECAL( event, 0 ) - 1; // index 0 is trigger electron

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
		double tof = t_ftof - start_time;
		double beta_time = r_path/tof * (1.0/PhysicalConstants.speedOfLight);  

		double pr_masstime = p*Math.sqrt( 1/(pr_beta_time*pr_beta_time) - 1.0 );
			
		if( scint_sector >= 0 && scint_detector == 12 && scint_layer == 2){
		    h_prmle.h_beta_all_pos.fill(p,pr_beta_time);

		    h_prmle.h2_beta_all_pos_sect_ftof_l2.get(scint_sector).fill(p,pr_beta_time);                                                                                                                         if( pid_clas12 == 211 ){                                                                                                                                                                
			h_prmle.h2_beta_all_pip_sect_ftof_l2.get(scint_sector).fill(p,beta_time);
		    }
		    else if( pid_clas12 == 2212 ){                                                                                                                                                                               h_prmle.h2_beta_all_pr_sect_ftof_l2.get(scint_sector).fill(p,beta_time);                                                                                                                             }                                                                                                                                                                                                        else if( pid_clas12 == 321 ){                                                                                                                                                                              h_prmle.h2_beta_all_kp_sect_ftof_l2.get(scint_sector).fill(p,beta_time);                                                                                                                             }
                }                                                                                                                                                                                        
                if( scint_sector >= 0 && scint_detector == 12 && scint_layer == 1){                                                                                                                      
                    h_prmle.h2_beta_all_pos_sect_ftof_l1.get(scint_sector).fill(p,pr_beta_time);
		                                                                                                                                   
                    if( pid_clas12 == 211 ){                                                                                                                                                                                     h_prmle.h2_beta_all_pip_sect_ftof_l1.get(scint_sector).fill(p,beta_time);                                                                                                                            }                                                                                                                                                                                                        else if( pid_clas12 == 2212 ){                                                                                                                                                                               h_prmle.h2_beta_all_pr_sect_ftof_l1.get(scint_sector).fill(p,beta_time);                                                                                                                             }                                                                                                                                                                                                        else if( pid_clas12 == 321 ){                                                                                                                                                                                h_prmle.h2_beta_all_kp_sect_ftof_l1.get(scint_sector).fill(p,beta_time);                                                                                                                             }                                                                                                                                                                                                                                                                                                                                                                              
                } 
	    }
	    
	}
    }
    
    public void fillPhiTest( DataEvent event, int el_i, int pr_i, int kp_i ){

	DataBank recBank = event.getBank("REC::Particle");   
	LorentzVector lv_el = Calculator.lv_particle(recBank,el_i,PhysicalConstants.electronID);
	LorentzVector lv_pr = Calculator.lv_particle(recBank,pr_i,PhysicalConstants.protonID);
	LorentzVector lv_kp = Calculator.lv_particle(recBank,kp_i,PhysicalConstants.kaonplusID);

	LorentzVector lv_beam = new LorentzVector(0,0,6.4,6.4);
	LorentzVector lv_tar = new LorentzVector(0,0,0,0.938);

	LorentzVector lv_epkpX = new LorentzVector(0,0,0,0);
	lv_epkpX.add(lv_beam);
	lv_epkpX.add(lv_tar);
	lv_epkpX.sub(lv_el);
	lv_epkpX.sub(lv_pr);
	lv_epkpX.sub(lv_kp);
	LorentzVector lv_phi = new LorentzVector(0,0,0,0);
	lv_phi.add(lv_kp);
	lv_phi.add(lv_epkpX);

	double phi_mass = lv_phi.mass();

	if( (lv_kp.mass() <= 0.53 && lv_kp.mass() >= 0.45 )
	    && (lv_epkpX.mass() <= 0.53 && lv_epkpX.mass() >= 0.45 ))
	    {
		h_prmle.h_test_phi.fill( phi_mass );
	    }

    }

    public void fillPionMLE( DataEvent event, int rec_i ){


    }

    public void saveMLEHistograms( boolean view ){

	h_prmle.protonHistoToHipo();
	h_kpmle.kaonHistoToHipo();
	if( view ){
	    h_prmle.viewHipoOut();
	    h_kpmle.viewHipoOut();
	}
    }

}
