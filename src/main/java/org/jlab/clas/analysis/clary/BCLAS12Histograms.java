package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.math.*;
import org.jlab.groot.fitter.*;

import org.jlab.groot.group.*;
import org.jlab.groot.ui.TBrowser;
import org.jlab.groot.tree.*;
import org.jlab.groot.data.TDirectory;
import org.jlab.io.hipo.HipoDataSource;
import org.jlab.groot.graphics.EmbeddedCanvas;


//import org.jlab.groot.func.*;
//import org.jlab.groot.histogram.*;

import java.util.*;
import java.io.*;


public class BCLAS12Histograms {

    int run_number = -1;
    String s_run_number = " ";
    String n_thread = null;

    public BCLAS12Histograms( int temp_run, String temp_n_threads ){
	run_number = temp_run;
	s_run_number = Integer.toString(run_number);
	n_thread = temp_n_threads;
    }

    TDirectory dir = new TDirectory();
    String savepath = "/home/bclary/CLAS12/pics/pid_clary/";

    double min_p = 0.0; double max_p = 10.6;
    double min_theta = 0.0; double max_theta = 60.5;
    double min_phi = -180.0; double max_phi = 180.0;
    double min_vz = -30.0; double max_vz = 40.0;
    double min_timing = 75; double max_timing = 175;
    double min_nphe = 0; double max_nphe = 50;
    double min_ecei = 0.0; double max_ecei = 1.0;
    double min_eceo = 0.0; double max_eceo = 1.0;
    double min_ectot = 0.01; double max_ectot = 2.5;
    double min_pcal1 = 0.0; double max_pcal1 = 5.0;
    double min_pcal2 = 0.0; double max_pcal2 = 5.0;
    double min_pcaltot = 0.0; double max_pcaltot = 5.0;
    double min_beta =0.0; double max_beta = 1.2;
    double min_w = 0.90; double max_w = 4.0;
    double min_q = 0.00; double max_q = 10.0;
    double min_pcalx = -200.0; double max_pcalx = 200.0;
    double min_pcaly = -25.0; double max_pcaly = 350.0;
    double min_ecx = -350.0; double max_ecx = 350.0;
    double min_ecy = -390.0; double max_ecy = 390.0;

    /*
      COMPARISON PLOTS
    */
    H1F h_el_p;
    H1F h_el_theta;
    H1F h_el_phi;
    H1F h_el_w;
    H1F h_el_q;
    H1F h_el_nphe;
    H1F h_el_vz;
    H2F h_el_q2x;
    H1F h_el_comparison;

    H1F h_el_eb;
    H1F h_el_pip_Neb;
    H1F h_el_pip_eb;

    H2F h2_el_thetap;
    H2F h2_el_phip;
    H2F h2_el_ectotp;
    H2F h2_el_ectotp2;
    H2F h2_el_etotnphe;
    H2F h2_el_betap;
    H2F h2_el_eieo;
    H2F h2_el_pcalecal;
    H2F h2_el_pcalp;
    H2F h2_el_vzp;
    H2F h2_el_ecsfnph;
    H2F h2_el_vzphi;

    Vector<H2F> h2_el_sect_thetap = new Vector<H2F>();
    Vector<H2F> h2_el_sect_phip = new Vector<H2F>();
    Vector<H2F> h2_el_sect_ectotp = new Vector<H2F>();
    Vector<H2F> h2_el_sect_ectotp2 = new Vector<H2F>();
    Vector<H2F> h2_el_sect_etotnphe = new Vector<H2F>();
    Vector<H2F> h2_el_sect_betap = new Vector<H2F>();
    Vector<H2F> h2_el_sect_eieo = new Vector<H2F>();
    Vector<H2F> h2_el_sect_pcalecal = new Vector<H2F>();
    Vector<H2F> h2_el_sect_pcalp = new Vector<H2F>();
    Vector<H2F> h2_el_sect_vzp = new Vector<H2F>();
    Vector<H2F> h2_el_sect_ecsfnph = new Vector<H2F>();
    Vector<H1F> h_el_sect_nph = new Vector<H1F>();
    
    H2F h2_el_dchit_R1_traj;
    H2F h2_el_dchit_R2_traj;
    H2F h2_el_dchit_R3_traj;
   
    H2F h2_el_dchit_R1_traj_rot;
    H2F h2_el_dchit_R2_traj_rot;
    H2F h2_el_dchit_R3_traj_rot;

    Vector<H2F> h2_el_sect_pcal_sfxy = new Vector<H2F>();
    Vector<H2F> h2_el_sect_ecei_sfxy = new Vector<H2F>();
    Vector<H2F> h2_el_sect_eceo_sfxy = new Vector<H2F>();

    Vector<H2F> h2_el_sect_pcal_xy = new Vector<H2F>();
    Vector<H2F> h2_el_sect_ecei_xy = new Vector<H2F>();
    Vector<H2F> h2_el_sect_eceo_xy = new Vector<H2F>();

    Vector<H1F> h_el_sect_w = new Vector<H1F>();

    public void createCLAS12ElectronHistograms( int i ){

	h_el_p = new H1F("h_"+s_run_number+"_el_p","h_"+s_run_number+"_el_p", 100, 0.0, 10.6 );
	h_el_w = new H1F("h_"+s_run_number+"_el_w","h_"+s_run_number+"_el_w", 100, 0.0, 6.0 );
	h_el_q = new H1F("h_"+s_run_number+"_el_q","h_"+s_run_number+"_el_q", 100, 0.0, 10.6 );

	h_el_theta = new H1F("h_"+s_run_number+"_el_theta","h_"+s_run_number+"_el_theta",100, min_theta, max_theta);
	h_el_phi = new H1F("h_"+s_run_number+"_el_phi","h_"+s_run_number+"_el_phi",200, min_phi, max_phi);
	h_el_q2x = new H2F("h_"+s_run_number+"_el_q2x","h_"+s_run_number+"_el_q2x",200, 0.0, 1.1, 200, 0.0, 10.6);

	h_el_vz = new H1F("h_"+s_run_number+"_el_vz","h_"+s_run_number+"_el_vz",200, -40.0, 40.0);

	h2_el_thetap = new H2F("h2_"+s_run_number+"_el_thetap","h2_"+s_run_number+"_el_thetap", 200, min_p, max_p, 200, min_theta, max_theta);
	h2_el_phip = new H2F("h2_"+s_run_number+"_el_phip","h2_"+s_run_number+"_el_phip", 200, min_p, max_p, 200, min_phi, max_phi);
 	h2_el_ectotp = new H2F("h2_"+s_run_number+"_el_ectotp","h2_"+s_run_number+"_el_ectotp", 200, min_p, max_p, 200, min_ectot, 0.40 );
	h2_el_ectotp2 = new H2F("h2_"+s_run_number+"_el_ectotp2","h2_"+s_run_number+"_el_ectotp2", 200, min_p, 2.0, 200, min_ectot, 0.40 );
	h2_el_etotnphe = new H2F("h2_"+s_run_number+"_el_etotnphe","h2_"+s_run_number+"_el_etotnphe", 50, min_nphe, max_nphe, 100, min_ectot, max_ectot );
	h2_el_betap = new H2F("h2_"+s_run_number+"_el_betap","h2_"+s_run_number+"_el_betap",100,min_p, max_p, 100, min_beta, max_beta);

	h2_el_eieo = new H2F("h2_"+s_run_number+"_el_eieo","h2_"+s_run_number+"_el_eieo",200, min_ecei, max_ecei, 200, min_eceo, max_eceo );
	h2_el_pcalecal = new H2F("h2_"+s_run_number+"_el_pcalecal","h2_"+s_run_number+"_el_pcalecal",100, min_ectot, 1.5, 100, min_pcal1, 1.5 );
	h2_el_pcalp = new H2F("h2_"+s_run_number+"_el_pcalp","h2_"+s_run_number+"_el_pcalp",200, min_p, max_p, 200,0.0, 0.4);// min_pcal1, max_pcal1 );
	h2_el_vzp = new H2F("h2_"+s_run_number+"_el_vzp","h2_"+s_run_number+"_el_vzp",200, min_vz, max_vz, 200, min_p, max_p); 
	h2_el_ecsfnph = new H2F("h2_"+s_run_number+"_el_ecsfnph","h2_"+s_run_number+"_el_ecsfnph", 200, 0.0, 50, 200, 0.0, 0.4 );
	h_el_nphe = new H1F("h_"+s_run_number+"_el_nphe","h_"+s_run_number+"_el_nphe", 50, min_nphe, 50 );

	h2_el_vzphi = new H2F("h_"+s_run_number+"_el_vzphi","h_"+s_run_number+"_el_vzphi", 200, min_vz, max_vz, 200, min_phi, max_phi);
		
	h2_el_dchit_R1_traj =  new H2F("h2_"+s_run_number+"_el_dchit_R1_traj","h2_"+s_run_number+"_el_dchit_R1_traj", 500, -200.0, 200.0, 500, -200.0, 200.0);
	h2_el_dchit_R2_traj =  new H2F("h2_"+s_run_number+"_el_dchit_R2_traj","h2_"+s_run_number+"_el_dchit_R2_traj", 500, -300.0, 300.0, 500, -300.0, 300.0);
	h2_el_dchit_R3_traj =  new H2F("h2_"+s_run_number+"_el_dchit_R3_traj","h2_"+s_run_number+"_el_dchit_R3_traj", 500, -400.0, 400.0, 500, -400.0, 400.0);

	h2_el_dchit_R1_traj_rot =  new H2F("h2_"+s_run_number+"_el_dchit_R1_traj_rot","h2_"+s_run_number+"_el_dchit_R1_traj_rot", 500, 0.0, 200.0, 500, -150.0, 150.0);
	h2_el_dchit_R2_traj_rot =  new H2F("h2_"+s_run_number+"_el_dchit_R2_traj_rot","h2_"+s_run_number+"_el_dchit_R2_traj_rot", 500, 0.0, 300.0, 500, -150.0, 150.0);
	h2_el_dchit_R3_traj_rot =  new H2F("h2_"+s_run_number+"_el_dchit_R3_traj_rot","h2_"+s_run_number+"_el_dchit_R3_traj_rot", 500, 0.0, 400.0, 500, -150.0, 150.0);

	h_el_comparison = new H1F("h_"+s_run_number+"_el_clas12_comparison","h_"+s_run_number+"_el_clas12_comparison",10, 0.0, 10.0);

	h_el_eb = new H1F("h_"+s_run_number+"_el_clas12_eb","h_"+s_run_number+"_el_clas12_eb",10, 0.0, 10.0);
	h_el_pip_Neb = new H1F("h_"+s_run_number+"_el_clas12_pip_Neb","h_"+s_run_number+"_el_clas12_pip_Neb",10, 0.0, 10.0);
	h_el_pip_eb = new H1F("h_"+s_run_number+"_el_clas12_pip_eb","h_"+s_run_number+"_el_clas12_pip_eb",10, 0.0, 10.0);

    }


    public void createCLAS12ElectronSectorHistograms( int sector ){

	h_el_sect_w.add( new H1F("h_"+s_run_number+"_el_"+Integer.toString(sector)+"_w", "h_el_"+Integer.toString(sector)+"_w", 200, min_w, max_w ));

	h2_el_sect_thetap.add( new H2F("h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_thetap","h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_thetap", 200, min_p, max_p, 200, min_theta, max_theta));
	h2_el_sect_phip.add( new H2F("h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_phip","h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_phip", 200, min_p, max_p, 200, min_phi, max_phi));
	h2_el_sect_ectotp.add( new H2F("h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_ectotp","h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_ectotp", 200, min_p, max_p, 200, 0.01, 0.4));
	h2_el_sect_ectotp2.add( new H2F("h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_ectotp2","h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_ectotp2", 100, min_p, 2.0, 100, 0.01, 0.4));
	h2_el_sect_etotnphe.add( new H2F("h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_etotnphe","h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_etotnphe", 100, min_p, max_p, 50 ,min_nphe, 50));
	h2_el_sect_betap.add( new H2F("h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_betap","h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_betap", 100, min_p, max_p, 100, min_beta, max_beta));
	h2_el_sect_eieo.add( new H2F("h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_eieo","h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_eieo", 100, 0.01, max_ecei, 100, 0.01, max_eceo));
	h2_el_sect_pcalecal.add( new H2F("h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_pcalecal","h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_pcalecal", 100, 0.0, 1.5, 100, 0.0, 1.5));
	h2_el_sect_pcalp.add( new H2F("h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_pcalp","h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_pcalp", 100, min_p, max_p, 100, 0.01, 0.35));
	h2_el_sect_vzp.add( new H2F("h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_vzp","h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_vzp", 200, min_vz, max_vz, 200, min_p, max_p));
	h2_el_sect_ecsfnph.add( new H2F("h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_ecsfnph","h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_ecsfnph", 50, 0.0, 50.0, 200, 0.0, 0.3));
	h_el_sect_nph.add( new H1F("h1_"+s_run_number+"_el_"+Integer.toString(sector)+"_nph","h2_"+s_run_number+"_el_"+Integer.toString(sector)+"_nph", 50, 0.0, 50));

	h2_el_sect_pcal_sfxy.add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_pcal_sfxy", "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_pcal_sfxy", 600, min_pcalx, max_pcalx, 600, min_pcaly, max_pcaly));
	h2_el_sect_ecei_sfxy.add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_ecei_sfxy", "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_ecei_sfxy", 600, min_ecx, max_ecx, 600, 0.0, max_ecy));
	h2_el_sect_eceo_sfxy.add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_eceo_sfxy", "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_eceo_sfxy", 600, min_ecx, max_ecx, 600, 0.0, max_ecy));

	h2_el_sect_pcal_xy.add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_pcal_xy", "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_pcal_xy", 600, min_pcalx, max_pcalx, 600, min_pcaly, max_pcaly));
	h2_el_sect_ecei_xy.add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_ecei_xy", "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_ecei_xy", 600, min_ecx, max_ecx, 600, 0.0, max_ecy));
	h2_el_sect_eceo_xy.add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_eceo_xy", "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_eceo_xy", 600, min_ecx, max_ecx, 600, 0.0, max_ecy));



		
    }

    public void clas12ElectronHistoToHipo(){

 	dir.mkdir("/clas12pid/");
	dir.cd("/clas12pid/");
	dir.addDataSet(h_el_p);      
	dir.addDataSet(h_el_theta);
	dir.addDataSet(h_el_phi);
	dir.addDataSet(h_el_vz);
	dir.addDataSet(h_el_w);
	dir.addDataSet(h_el_q);
	dir.addDataSet(h_el_q2x);
	dir.addDataSet(h_el_comparison);
	dir.addDataSet(h_el_eb);
	dir.addDataSet(h_el_pip_Neb);
	dir.addDataSet(h_el_pip_eb);

	dir.addDataSet(h_el_nphe);
	dir.addDataSet(h2_el_thetap);      
	dir.addDataSet(h2_el_phip);      
	dir.addDataSet(h2_el_ectotp);
	dir.addDataSet(h2_el_ectotp2);
	dir.addDataSet(h2_el_etotnphe);
	dir.addDataSet(h2_el_betap);
	dir.addDataSet(h2_el_eieo);
	dir.addDataSet(h2_el_pcalecal);
	dir.addDataSet(h2_el_pcalp);
	dir.addDataSet(h2_el_vzp);
	dir.addDataSet(h2_el_ecsfnph);
	dir.addDataSet(h2_el_vzphi);

	dir.addDataSet(h2_el_dchit_R1_traj);
	dir.addDataSet(h2_el_dchit_R2_traj);
	dir.addDataSet(h2_el_dchit_R3_traj);

	dir.addDataSet(h2_el_dchit_R1_traj_rot);
	dir.addDataSet(h2_el_dchit_R2_traj_rot);
	dir.addDataSet(h2_el_dchit_R3_traj_rot);

	dir.mkdir("/clas12pid_sector_pcal_sfxy/");
	dir.cd("/clas12pid_sector_pcal_sfxy/");
	int j = 0;
	for( H2F h2_temp : h2_el_sect_pcal_sfxy ){
	    
	    H2F h2_temp2 = h2_el_sect_pcal_xy.get(j);
	    h2_temp.divide(h2_temp2);
	    dir.addDataSet(h2_temp);
	    j++;
	}

	j = 0;
	dir.mkdir("/clas12pid_sector_ecei_sfxy/");
	dir.cd("/clas12pid_sector_ecei_sfxy/");
	for( H2F h2_temp : h2_el_sect_ecei_sfxy ){
	    H2F h2_temp2 = h2_el_sect_ecei_xy.get(j);
	    h2_temp.divide(h2_temp2);
	    dir.addDataSet(h2_temp);
	    j++;
	}

	j = 0;
	dir.mkdir("/clas12pid_sector_eceo_sfxy/");
	dir.cd("/clas12pid_sector_eceo_sfxy/");
	for( H2F h2_temp : h2_el_sect_eceo_sfxy ){
	    H2F h2_temp2 = h2_el_sect_eceo_xy.get(j);
	    h2_temp.divide(h2_temp2);
	    dir.addDataSet(h2_temp);
 	    j++;
	}

	dir.mkdir("/clas12pid_sector_w/");
	dir.cd("/clas12pid_sector_w/");
	for( H1F h_temp : h_el_sect_w ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/clas12pid_sector_thetap/");
	dir.cd("/clas12pid_sector_thetap/");
	for( H2F h_temp : h2_el_sect_thetap ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/clas12pid_sector_phip/");
	dir.cd("/clas12pid_sector_phip/");
 	for( H2F h_temp : h2_el_sect_phip ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/clas12pid_sector_ectotp/");
	dir.cd("/clas12pid_sector_ectotp/");
	for( H2F h_temp : h2_el_sect_ectotp ){
	    dir.addDataSet(h_temp);
	}
	dir.mkdir("/clas12pid_sector_ectotp2/");
	dir.cd("/clas12pid_sector_ectotp2/");
	for( H2F h_temp : h2_el_sect_ectotp2 ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/clas12pid_sector_etotnphe/");
	dir.cd("/clas12pid_sector_etotnphe/");
	for( H2F h_temp : h2_el_sect_etotnphe ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/clas12pid_sector_betap/");
	dir.cd("/clas12pid_sector_betap/");
	for(H2F h_temp : h2_el_sect_betap ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/clas12pid_sector_eieo/");
	dir.cd("/clas12pid_sector_eieo/");
	for(H2F h_temp : h2_el_sect_eieo ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/clas12pid_sector_pcalecal/");
	dir.cd("/clas12pid_sector_pcalecal/");
	for(H2F h_temp : h2_el_sect_pcalecal ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/clas12pid_sector_pcalp/");
	dir.cd("/clas12pid_sector_pcalp/");
	for(H2F h_temp : h2_el_sect_pcalp ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/clas12pid_sector_vzp/");
	dir.cd("/clas12pid_sector_vzp/");
	for(H2F h_temp : h2_el_sect_vzp ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/clas12pid_sector_nph/");
	dir.cd("/clas12pid_sector_nph/");
	for(H1F h_temp : h_el_sect_nph ){
	    dir.addDataSet(h_temp);
	}
       
	//	dir.writeFile("clas12_pid.hipo");

	saveHipoOut();
	

    }


    public void printHistograms(){


	for( H1F h_temp : h_el_sect_w ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("W [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/"+h_temp.getTitle()+".png");
	}       

	for(H2F h_temp : h2_el_sect_thetap ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#theta [deg]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/"+h_temp.getTitle()+".png");
	}

	for(H2F h_temp : h2_el_sect_phip ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#phi [deg]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/"+h_temp.getTitle()+".png");
	}

	for( H2F h_temp : h2_el_sect_ectotp ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("Total Energy / p");
	    c_temp.draw(h_temp,"colz");

	    F1D f_sf_max = new F1D("f_sf_avg","[a]*([b] - [c]/x + [d]/(x*x)) + 0.0166*5.0", 0.6, 10.6);
	    F1D f_sf_avg = new F1D("f_sf_avg","[a]*([b] - [c]/x + [d]/(x*x))", 0.6, 10.6);
	    F1D f_sf_min = new F1D("f_sf_avg","[a]*([b] - [c]/x + [d]/(x*x)) - 0.0166*5.0", 0.6, 10.6);
	    f_sf_max.setParameter(0, 0.263);
	    f_sf_max.setParameter(1, 0.985);
	    f_sf_max.setParameter(2, 0.036);
	    f_sf_max.setParameter(3, 0.002);
	    f_sf_max.setLineColor(3);
	    f_sf_max.setLineWidth(4);

	    f_sf_avg.setParameter(0, 0.263);
	    f_sf_avg.setParameter(1, 0.985);
	    f_sf_avg.setParameter(2, 0.036);
	    f_sf_avg.setParameter(3, 0.002);
	    f_sf_avg.setLineColor(2);
	    f_sf_avg.setLineWidth(3);

	    f_sf_min.setParameter(0, 0.263);
	    f_sf_min.setParameter(1, 0.985);
	    f_sf_min.setParameter(2, 0.036);
	    f_sf_min.setParameter(3, 0.002);
	    f_sf_min.setLineColor(3);
	    f_sf_min.setLineWidth(4);

	    c_temp.draw(f_sf_min,"same");
	    c_temp.draw(f_sf_avg,"same");
	    c_temp.draw(f_sf_max,"same");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/"+h_temp.getTitle()+".png");
	}

	for( H2F h_temp : h2_el_sect_ectotp2 ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Total Energy [GeV]");
	    h_temp.setTitleY("Total Energy / p");
	    c_temp.draw(h_temp,"colz");

	    F1D f_sf_max = new F1D("f_sf_avg","[a]*([b] - [c]/x + [d]/(x*x)) + 0.0166*5.0", 0.05, 2.0);
	    F1D f_sf_avg = new F1D("f_sf_avg","[a]*([b] - [c]/x + [d]/(x*x))", 0.05, 2.0);
	    F1D f_sf_min = new F1D("f_sf_avg","[a]*([b] - [c]/x + [d]/(x*x)) - 0.0166*5.0", 0.05, 2.0);
	    f_sf_max.setParameter(0, 0.263);
	    f_sf_max.setParameter(1, 0.985);
	    f_sf_max.setParameter(2, 0.036);
	    f_sf_max.setParameter(3, 0.002);
	    f_sf_max.setLineColor(3);
	    f_sf_max.setLineWidth(4);

	    f_sf_avg.setParameter(0, 0.263);
	    f_sf_avg.setParameter(1, 0.985);
	    f_sf_avg.setParameter(2, 0.036);
	    f_sf_avg.setParameter(3, 0.002);
	    f_sf_avg.setLineColor(2);
	    f_sf_avg.setLineWidth(3);

	    f_sf_min.setParameter(0, 0.263);
	    f_sf_min.setParameter(1, 0.985);
	    f_sf_min.setParameter(2, 0.036);
	    f_sf_min.setParameter(3, 0.002);
	    f_sf_min.setLineColor(3);
	    f_sf_min.setLineWidth(4);

	    c_temp.draw(f_sf_min,"same");
	    c_temp.draw(f_sf_avg,"same");
	    c_temp.draw(f_sf_max,"same");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/"+h_temp.getTitle()+".png");
	}

	for( H2F h_temp : h2_el_sect_etotnphe  ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Nphe");
	    h_temp.setTitleY("Total Energy [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/"+h_temp.getTitle()+".png");
	}

	for( H2F h_temp : h2_el_sect_betap ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#beta");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/"+h_temp.getTitle()+".png");
	}

	for( H2F h_temp : h2_el_sect_eieo ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("EC inner [GeV]");
	    h_temp.setTitleY("EC outer [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/"+h_temp.getTitle()+".png");
	}
	for( H2F h_temp : h2_el_sect_pcalecal ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("ECAL [GeV]");
	    h_temp.setTitleY("PCAL [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/"+h_temp.getTitle()+".png");
	}
	for( H2F h_temp : h2_el_sect_pcalp ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("PCAL [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/"+h_temp.getTitle()+".png");
	}

	for( H2F h_temp : h2_el_sect_vzp ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("vz [cm]");
	    h_temp.setTitleY("p [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/"+h_temp.getTitle()+".png");
	}

	for( H2F h_temp : h2_el_sect_ecsfnph ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Nphe [cm]");
	    h_temp.setTitleY("EC SF");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/"+h_temp.getTitle()+".png");
	}   


	for( H1F h_temp : h_el_sect_nph ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Nphe [cm]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/"+h_temp.getTitle()+".png");
	}   

    }

    public void saveHipoOut(){
	System.out.println(" >> SAVING CLAS12 HIPO FILE NOW ");
	dir.writeFile(savepath+"h_"+s_run_number+"_"+n_thread+"_el_pid_clas12.hipo");
    }


    public void viewHipoOut(){
	//dir.readFile("clas12_pid.hipo");
	//dir.ls();
	TBrowser b = new TBrowser(dir);

    }
    

}

