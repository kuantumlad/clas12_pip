package org.jlab.clas.analysis.clary;

import java.io.*;

import org.jlab.groot.group.*;
import org.jlab.groot.ui.TBrowser;
import org.jlab.groot.tree.*;
import org.jlab.groot.data.TDirectory;

import org.jlab.clas.analysis.clary.Calculator;
import org.jlab.clas.analysis.clary.BResProton;
import org.jlab.clas.analysis.clary.BResPPion;
import org.jlab.clas.analysis.clary.BResKaonPlus;

import org.jlab.analysis.math.ClasMath;
import org.jlab.clas.physics.Vector3;
import org.jlab.clas.physics.LorentzVector;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.fitter.*;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.math.Axis;
import org.jlab.groot.graphics.GraphicsAxis;

import org.jlab.groot.math.Func1D;
import org.jlab.groot.math.F1D;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;


public class BHistoResolutions {
    ///////////////////////////////////////////////
    //USED FOR CREATING BETA LIKELIHOOD ESTIMATORS
    //

    BResProton h_res_pr;
    BResPPion h_res_pp;
    BResKaonPlus h_res_kp;

    int run_number = -1;
    String s_run = "";
    public BHistoResolutions( int temp_run ){
	run_number = temp_run;
	s_run = Integer.toString(temp_run);

	h_res_pr = new BResProton(temp_run);
	h_res_pp = new BResPPion(temp_run);
	h_res_kp = new BResKaonPlus(temp_run);

    }

    public void createHistograms(){
	
	h_res_pr.createResProtonHistograms();
	h_res_pp.createResPPionHistograms();
	h_res_kp.createResKaonPlusHistograms();

    }


    public void fillProtonResHistograms( DataEvent event, int rec_i ){
	//ADD BETA CALCULATION HERE FROM THE BPIDPROTONHIST CLASS AND FILL HISTOGRAMS
	//LIKEWISE FOR KAON PLUS
	//WILL NEED TO GET SLICE N FIT ROUTINE LATER TO GET FITS FOR MEAN AND SIGMAS
	//
	DataBank recBank = event.getBank("REC::Particle");
	LorentzVector lv_pr = Calculator.lv_particle(recBank,rec_i, PhysicalConstants.protonID);
	
	double pr_p  = lv_pr.p();
	
	double pr_beta_mntm = pr_p/Math.sqrt(pr_p*pr_p + PhysicalConstants.mass_proton * PhysicalConstants.mass_proton);

	if( event.hasBank("REC::Scintillator") && event.hasBank("REC::Event") && event.hasBank("MC::Particle") ){
	    DataBank scintBank = event.getBank("REC::Scintillator");
	    DataBank eventBank = event.getBank("REC:Event");
	    DataBank genBank = event.getBank("MC::Particle");
	    LorentzVector lv_mcpr = Calculator.lv_particle(genBank,0, PhysicalConstants.protonID);
	    double mc_p = lv_mcpr.p();

	    double mc_beta = mc_p/(Math.sqrt(mc_p*mc_p + PhysicalConstants.mass_proton*PhysicalConstants.mass_proton));


		for( int i = 0; i < scintBank.rows(); i++){		    
		    int pindex = scintBank.getShort("pindex",i);
		    if( pindex == rec_i ){
			
			int scint_sector = scintBank.getInt("sector",i) - 1;
			int scint_detector = scintBank.getByte("detector",i);
			int scint_layer = scintBank.getByte("layer",i);
			int scint_bar = scintBank.getInt("component",i) - 1  ;
			
			double ftof_e  = scintBank.getFloat("energy",i)/100.0;
			
			double start_time = genBank.getFloat("vt",0); //eventBank.getFloat("STTime",0);
			double r_path = scintBank.getFloat("path",i);
			double t_ftof = scintBank.getFloat("time",i);
			double pr_tof = t_ftof - start_time;
			double pr_beta_time = r_path/pr_tof * (1.0/30.0); 
			double pr_tmeas = r_path/pr_beta_mntm * (1.0/30.0);
			double pr_deltime = -pr_tmeas + pr_tof;
			double pr_delbeta = pr_beta_time - pr_beta_mntm;
		
			double pr_masstime = pr_p*Math.sqrt( 1/(pr_beta_time*pr_beta_time) - 1.0 );
						
 			if( scint_sector >= 0 && scint_detector == 12 ){
			    h_res_pr.h2_rc_pr_betap.fill( pr_p, pr_delbeta );
			    h_res_pr.h_rc_pr_beta_res.fill( pr_beta_time - mc_beta);
			}
		    }
		}       
	}
	////////////////////////////////////////////////////////////////////////////////////////////
    }

    public void fillPPionResHistograms( DataEvent event, int rec_i ){
	DataBank recBank = event.getBank("REC::Particle");
	LorentzVector lv_pp = Calculator.lv_particle(recBank,rec_i, PhysicalConstants.pionplusID);
	
	double p  = lv_pp.p();
	
	double pp_beta_mntm = p/Math.sqrt(p*p + PhysicalConstants.mass_pion * PhysicalConstants.mass_pion);

	if( event.hasBank("REC::Scintillator") && event.hasBank("REC::Event") && event.hasBank("MC::Particle") ){
	    DataBank scintBank = event.getBank("REC::Scintillator");
	    DataBank eventBank = event.getBank("REC:Event");
	    DataBank genBank = event.getBank("MC::Particle");
	    LorentzVector lv_mcpp = Calculator.lv_particle(genBank,0, PhysicalConstants.pionplusID);
	    double mc_p = lv_mcpp.p();

	    double mc_beta = mc_p/(Math.sqrt(mc_p*mc_p + PhysicalConstants.mass_pion*PhysicalConstants.mass_pion));


		for( int i = 0; i < scintBank.rows(); i++){		    
		    int pindex = scintBank.getShort("pindex",i);
		    if( pindex == rec_i ){
			
			int scint_sector = scintBank.getInt("sector",i) - 1;
			int scint_detector = scintBank.getByte("detector",i);
			int scint_layer = scintBank.getByte("layer",i);
			int scint_bar = scintBank.getInt("component",i) - 1  ;
			
			double ftof_e  = scintBank.getFloat("energy",i)/100.0;
			
			double start_time = genBank.getFloat("vt",0); //eventBank.getFloat("STTime",0);
			double r_path = scintBank.getFloat("path",i);
			double t_ftof = scintBank.getFloat("time",i);
			double pp_tof = t_ftof - start_time;
			double pp_beta_time = r_path/pp_tof * (1.0/30.0); 
			double pp_tmeas = r_path/pp_beta_mntm * (1.0/30.0);
			double pp_deltime = -pp_tmeas + pp_tof;
			double pp_delbeta = pp_beta_time - pp_beta_mntm;
		
			double pp_masstime = p*Math.sqrt( 1/(pp_beta_time*pp_beta_time) - 1.0 );
						
 			if( scint_sector >= 0 && scint_detector == 12 ){
			    h_res_pp.h2_rc_pp_betap.fill( p, pp_delbeta );
			    h_res_pp.h_rc_pp_beta_res.fill( pp_beta_time - mc_beta);
			}
		    }
		}       
	}
	////////////////////////////////////////////////////////////////////////////////////////////
    }

    public void fillKaonPlusResHistograms( DataEvent event, int rec_i ){
	DataBank recBank = event.getBank("REC::Particle");
	LorentzVector lv_kp = Calculator.lv_particle(recBank, rec_i, PhysicalConstants.kaonplusID);
	
	double p  = lv_kp.p();
	
	double kp_beta_mntm = p/Math.sqrt(p*p + PhysicalConstants.mass_kaon * PhysicalConstants.mass_kaon);

	if( event.hasBank("REC::Scintillator") && event.hasBank("REC::Event") && event.hasBank("MC::Particle") ){
	    DataBank scintBank = event.getBank("REC::Scintillator");
	    DataBank eventBank = event.getBank("REC:Event");
	    DataBank genBank = event.getBank("MC::Particle");
	    LorentzVector lv_mckp = Calculator.lv_particle(genBank,0, PhysicalConstants.kaonplusID);
	    double mc_p = lv_mckp.p();

	    double mc_beta = mc_p/(Math.sqrt(mc_p*mc_p + PhysicalConstants.mass_kaon*PhysicalConstants.mass_kaon));

		for( int i = 0; i < scintBank.rows(); i++){		    
		    int pindex = scintBank.getShort("pindex",i);
		    if( pindex == rec_i ){
			
			int scint_sector = scintBank.getInt("sector",i) - 1;
			int scint_detector = scintBank.getByte("detector",i);
			int scint_layer = scintBank.getByte("layer",i);
			int scint_bar = scintBank.getInt("component",i) - 1  ;
			
			double ftof_e  = scintBank.getFloat("energy",i)/100.0;
			
			double start_time = genBank.getFloat("vt",0);
			double r_path = scintBank.getFloat("path",i);
			double t_ftof = scintBank.getFloat("time",i);
			double kp_tof = t_ftof - start_time;
			double kp_beta_time = r_path/kp_tof * (1.0/30.0); 
			double kp_tmeas = r_path/kp_beta_mntm * (1.0/30.0);
			double kp_deltime = -kp_tmeas + kp_tof;
			double kp_delbeta = kp_beta_time - kp_beta_mntm;
		
			double kp_masstime = p*Math.sqrt( 1/(kp_beta_time*kp_beta_time) - 1.0 );
						
 			if( scint_sector >= 0 && scint_detector == 12 ){
			    h_res_kp.h2_rc_kp_betap.fill( p, kp_delbeta );
			    h_res_kp.h_rc_kp_beta_res.fill( kp_beta_time - mc_beta);
			}
		    }
		}       
	}
	////////////////////////////////////////////////////////////////////////////////////////////
    }

   
    public void saveResolutionsHistograms( boolean view ){

	h_res_pr.sliceNFitResProton();	
	h_res_pr.histoResProtonToHipo();

	h_res_pp.sliceNFitResPPion();	
	h_res_pp.histoResPPionToHipo();

	h_res_kp.sliceNFitResKaonPlus();	
	h_res_kp.histoResKaonPlusToHipo();


	if( view ){
	    h_res_pr.viewResProtonHipoOut();
	    h_res_pp.viewResPPionHipoOut();
	    h_res_kp.viewResKaonPlusHipoOut();
	}
    }
}
