package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.math.Axis;
import org.jlab.groot.graphics.GraphicsAxis;
import org.jlab.groot.fitter.*;
import org.jlab.groot.math.*;

import java.awt.*;
import org.jlab.groot.group.*;
import org.jlab.groot.ui.TBrowser;
import org.jlab.groot.tree.*;
import org.jlab.groot.data.TDirectory;
import org.jlab.jnp.hipo.io.*;
import org.jlab.groot.graphics.EmbeddedCanvas;

import java.util.*;
import java.io.*;

import org.jlab.clas.analysis.clary.RunPropertiesLoader;
import org.jlab.clas.analysis.clary.PhysicalConstants;

public class BPhysHistograms {

    private int run_number = -1;
    private String s_run_number = " ";
    private String n_thread = " ";
    public BPhysHistograms(int temp_run, String temp_n_threads) {
	//constructor
	run_number = temp_run;
	s_run_number = Integer.toString(run_number);
	n_thread = temp_n_threads;
    }

    //CREATE HISTOGRAMS FOR CUT BASED HISTO HERE
    TDirectory dir = new TDirectory();

    String savepath = "/home/bclary/CLAS12/pics/pid_clary/";
    
    H1F h_q2;
    H1F h_xb;
    H1F h_t;
    H1F h_w2;
    H1F h_w;
    H1F h_cm_phi;
    H1F h_cm_theta;
    
    H1F h_mm_eX;
    H1F h_mm_epX;
    H1F h_mm_epKX;
    H1F h_mm_eKX;
 
    H2F h2_q2x;
    H2F h2_q2t;
    H2F h2_q2w;
    H2F h2_q2phi;
 
    H2F h2_xbt;
    H2F h2_xbw;
    H2F h2_xbphi;
 
    H2F h2_tw;
 
    H2F h2_mm_epKX_eKX;
    

    double beam_energy = PhysicalConstants.eBeam;
    double min_p = 0.0; double max_p = beam_energy;


    public void createPhysHistograms(){

	h_q2 = new H1F("h_"+s_run_number+"_q2", 200, 0.0, beam_energy);
	h_xb = new H1F("h_"+s_run_number+"_xb",200, 0.0, 1.1);
	h_t = new H1F("h_"+s_run_number+"_t",200, 0.50, 4.5);
	h_w2 = new H1F("h_"+s_run_number+"_w2",200, 0.0, 8.0);
	h_w = new H1F("h_"+s_run_number+"_w",200, 0.0, 4.5);
	h_cm_phi = new H1F("h_"+s_run_number+"_cm_phi",200,-180.0,180.0);

	h_mm_eX = new H1F("h_"+s_run_number+"_mm_eX",200, 0.0, 3.0); 
	h_mm_epX = new H1F("h_"+s_run_number+"_mm_epX",200, 0.0, 3.0); 
	h_mm_epKX = new H1F("h_"+s_run_number+"_mm_epKX",200, 0.0, 3.0); 
	h_mm_eKX = new H1F("h_"+s_run_number+"_mm_eKX",200, 0.0, 3.0); 
	h2_mm_epKX_eKX = new H2F("h2_"+s_run_number+"_mm_epKX_eKX", 200, 0.5, 1.5, 300, 0.3, 1.8);

	h2_q2x = new H2F("h_"+s_run_number+"_q2x",300, 0.0, 1.1, 300, 0.0, beam_energy);
	h2_q2t = new H2F("h_"+s_run_number+"_q2t",300, 0.5, 4.5, 300, 0.0, beam_energy);
	h2_q2w = new H2F("h_"+s_run_number+"_q2w",300, 0.0, 4.5, 300, 0.0, beam_energy);
	h2_q2phi = new H2F("h_"+s_run_number+"_q2phi",300, -180.0, 180.0, 300, 0.0, beam_energy);
  
	h2_xbt = new H2F("h2_"+s_run_number+"_xbt",200, 0.0, 1.1, 200, 0.5, 4.5 );
	h2_xbw = new H2F("h2_"+s_run_number+"_xbw",200, 0.0, 1.1, 200, 0.5, 4.5 );
	h2_xbphi = new H2F("h2_"+s_run_number+"_xbphi",200, 0.0, 1.1, 200, 0.5, 4.5 );

	h2_tw = new H2F("h2_"+s_run_number+"_tw",200, 0.0, 1.1, 200, 0.5, 4.5 );
	
    }
    
    
    public void createPhysSectorHistograms(int s ){
	
	
    }
    
    public void physicsHistoToHipo(){
	
	System.out.println(" >> SAVING PHYSICS HISTOS NOW " );
	dir.mkdir("/physics_results/");
	dir.cd("/physics_results/");
	dir.addDataSet(h_q2);
	dir.addDataSet(h_xb);
	dir.addDataSet(h_t);
	dir.addDataSet(h_w2);
	dir.addDataSet(h_w);
	dir.addDataSet(h_cm_phi);

	dir.addDataSet(h_mm_eX);
	dir.addDataSet(h_mm_epX);
	dir.addDataSet(h_mm_epKX);
	dir.addDataSet(h_mm_eKX);

	dir.addDataSet(h2_q2x);
	dir.addDataSet(h2_q2t);
	dir.addDataSet(h2_q2w);
	dir.addDataSet(h2_q2phi);

	dir.addDataSet(h2_xbt);
	dir.addDataSet(h2_xbw);
	dir.addDataSet(h2_q2phi);

	dir.addDataSet(h2_tw);

	dir.addDataSet(h2_mm_epKX_eKX);

	saveHipoOut();
    }
    
    public void saveHipoOut(){

	System.out.println(" >> SAVING HIPO FILE NOW ");
	dir.writeFile(savepath+"h_"+s_run_number+"_"+n_thread+"_phys_clary.hipo");

    }

    public void printHistograms(){


    }


}
