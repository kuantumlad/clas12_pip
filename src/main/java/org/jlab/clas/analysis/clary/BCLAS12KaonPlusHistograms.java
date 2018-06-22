package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.math.*;

import org.jlab.groot.group.*;
import org.jlab.groot.ui.TBrowser;
import org.jlab.groot.tree.*;
import org.jlab.groot.data.TDirectory;
import org.jlab.io.hipo.HipoDataSource;
import org.jlab.groot.graphics.EmbeddedCanvas;

public class BCLAS12KaonPlusHistograms {

    int run_number = -1;
    String s_run_number = " ";
    public BCLAS12KaonPlusHistograms( int temp_run ){
	run_number = temp_run;
	s_run_number = Integer.toString(run_number);
    }

    TDirectory dir = new TDirectory();

    double min_p = 0.0; double max_p = 6.5;
    double min_theta = 0.0; double max_theta = 60.5;
    double min_phi = -180.0; double max_phi = 180.0;
    double min_vz = -10.0; double max_vz = 10.0;
    double min_b = 0.5; double max_b = 1.2;
    double min_timing = 0.0; double max_timing = 700.0;
    double min_delt = -0.07; double max_delt = 0.07;
    double min_delb = -1.0; double max_delb = 1.0;
    double min_rpath = 300.0; double max_rpath = 750.0; 
    double min_tof = 15.0; double max_tof = 35.0;

    H1F h_kp_p;
    H1F h_kp_theta;
    H1F h_kp_phi;
    H1F h_kp_vz;
    H1F h_kp_timing;

    H2F h2_kp_thetap;
    H2F h2_kp_betap;
    H2F h2_kp_deltimep;
    H2F h2_kp_deltabeta;
    H2F h2_kp_tof;

    Vector<H2F> h2_kp_sect_betap = new Vector< H2F >();
    Vector<H1F> h_kp_sect_vz = new Vector<H1F >();
    Vector<H2F> h2_kp_sect_pvz = new Vector<H2F>();
    Vector<H2F> h2_kp_sect_deltat = new Vector<H2F>();


    public void createCLAS12KaonPHistograms( int i ){

	h2_kp_thetap = new H2F("h2_kp_thetap","h2_kp_thetp",100, min_p, max_p, 100, min_theta, max_theta);
	h2_kp_betap = new H2F("h2_kp_betap","h2_kp_betap",350, min_p, max_p, 350, min_b, max_b);
	h2_kp_deltimep = new H2F("h2_kp_deltimep","h2_kp_deltimep",100, min_p, max_p, 100, min_delt, max_delt);
	h2_kp_deltabeta = new H2F("h2_kp_deltabeta","h2_kp_deltabeta",350, min_p, max_p, 350, min_delb, max_delb);
	h2_kp_tof = new H2F("h2_kp_tof","h2_kp_tof",100, min_p, max_p, 100, min_tof, max_tof);

    }

    public void createCLAS12KaonPSectorHistograms( int sector ){

	    h2_kp_sect_betap.add( new H2F("h2_"+s_run_number+"_clas12kp_"+Integer.toString(sector)+"_betap","h2_"+s_run_number+"_clas12kp_"+Integer.toString(sector)+"_betap", 350, min_p, max_p, 350, min_b, max_b));	    
	    h_kp_sect_vz.add( new H1F("h_"+s_run_number+"_clas12kp_"+Integer.toString(sector)+"_vz","h_"+s_run_number+"_clas12kp_"+Integer.toString(sector)+"_vz", 200, min_vz, max_vz));	    
	    h2_kp_sect_pvz.add( new H2F("h_"+s_run_number+"_clas12kp_"+Integer.toString(sector)+"_pvz","h_"+s_run_number+"_clas12kp_"+Integer.toString(sector)+"_pvz", 200, min_vz, max_vz, 200, min_p, max_p));
	    h2_kp_sect_deltat.add( new H2F("h_"+s_run_number+"_clas12kp_"+Integer.toString(sector)+"_deltat","h_"+s_run_number+"_clas12kp_"+Integer.toString(sector)+"_deltat", 200, min_p, max_p, 200, min_delt, max_delt));  
    


    }

    public void clas12KaonPHistoToHipo(){

	EmbeddedCanvas c_kpbetap = new EmbeddedCanvas();
	c_kpbetap.setSize(800,800);
	h2_kp_betap.setTitleX("p [GeV]");
	h2_kp_betap.setTitleY("#beta");
	c_kpbetap.draw(h2_kp_betap);
	c_kpbetap.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h2_"+s_run_number+"clas12kp_betap.png");

	EmbeddedCanvas c_kpthetap = new EmbeddedCanvas();
	c_kpthetap.setSize(800,800);	
	h2_kp_thetap.setTitleX("p [GeV]");
	h2_kp_thetap.setTitleY("#theta [deg]");
	c_kpthetap.draw(h2_kp_thetap);
	c_kpthetap.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h2_"+s_run_number+"clas12kp_thetap.png");

	EmbeddedCanvas c_kpdeltimep = new EmbeddedCanvas();
	c_kpdeltimep.setSize(800,800);	
	h2_kp_deltimep.setTitleX("p [GeV]");
	h2_kp_deltimep.setTitleY("#delta tof [ns]");
	c_kpdeltimep.draw(h2_kp_deltimep);
	c_kpdeltimep.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h2_"+s_run_number+"clas12kp_deltimep.png");

	EmbeddedCanvas c_kpdeltabeta = new EmbeddedCanvas();
	c_kpdeltabeta.setSize(800,800);	
	h2_kp_deltabeta.setTitleX("p [GeV]");
	h2_kp_deltabeta.setTitleY("#delta #beta");
	c_kpdeltabeta.draw(h2_kp_deltabeta);
	c_kpdeltabeta.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h2_"+s_run_number+"clas12kp_deltabeta.png");


	EmbeddedCanvas c_kptof = new EmbeddedCanvas();
	c_kptof.setSize(800,800);	
	h2_kp_tof.setTitleX("p [GeV]");
	h2_kp_tof.setTitleY("tof [ns]");
	c_kptof.draw(h2_kp_tof);
	c_kptof.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h2_"+s_run_number+"clas12kp_tof.png");


  	dir.mkdir("/clas12kaonppid/");
	dir.cd("/clas12kaonppid/");
	dir.addDataSet(h2_kp_betap);
	dir.addDataSet(h2_kp_thetap);
	dir.addDataSet(h2_kp_deltimep);
	dir.addDataSet(h2_kp_deltabeta);
	dir.addDataSet(h2_kp_tof);

 	dir.mkdir("/clas12kaonpid/h_kp_sect_betap/");
	dir.cd("/clas12kaonpid/h_kp_sect_betap/");	
	EmbeddedCanvas c_betap = new EmbeddedCanvas();
	c_betap.setSize(1600,800);
	c_betap.divide(3,2);
	for( int s = 0; s < 6; s++ ){	   
	    c_betap.cd(s);
	    h2_kp_sect_betap.get(s).setTitleX(Integer.toString(s));
	    c_betap.draw(h2_kp_sect_betap.get(s),"colz");
	    dir.addDataSet(h2_kp_sect_betap.get(s));
	}
	c_betap.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h_"+s_run_number+"_clas12kp_betap_allsect.png"); 

 	dir.mkdir("/clas12kaonpid/h_kp_sect_vz/");
	dir.cd("/clas12kaonpid/h_kp_sect_vz/");	
	EmbeddedCanvas c_vz = new EmbeddedCanvas();
	c_vz.setSize(1600,800);
	c_vz.divide(3,2);
	for( int s = 0; s < 6; s++ ){	   
	    c_vz.cd(0);
	    h_kp_sect_vz.get(s).setTitleX(Integer.toString(s));
	    c_vz.draw(h_kp_sect_vz.get(s));
	    dir.addDataSet(h_kp_sect_vz.get(s));
	}
	c_vz.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h_"+s_run_number+"_clas12kp_vz_allsect.png"); 

 	dir.mkdir("/clas12kaonpid/h_kp_sect_pvz/");
	dir.cd("/clas12kaonpid/h_kp_sect_pvz/");	
	EmbeddedCanvas c_pvz = new EmbeddedCanvas();
	c_pvz.setSize(1600,800);
	c_pvz.divide(3,2);
	for( int s = 0; s < 6; s++ ){	   
	    c_pvz.cd(0);
	    h2_kp_sect_pvz.get(s).setTitleX(Integer.toString(s));
	    c_pvz.draw(h2_kp_sect_pvz.get(s));
	    dir.addDataSet(h2_kp_sect_pvz.get(s));
	}
	c_pvz.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h_"+s_run_number+"_clas12kp_pvz_allsect.png"); 

 	dir.mkdir("/clas12kaonpid/h_kp_sect_deltat/");
	dir.cd("/clas12kaonpid/h_kp_sect_deltat/");	
	EmbeddedCanvas c_delt = new EmbeddedCanvas();
	c_delt.setSize(1600,800);
	c_delt.divide(3,2);
	for( int s = 0; s < 6; s++ ){	   
	    c_delt.cd(0);
	    h2_kp_sect_deltat.get(s).setTitleX(Integer.toString(s));
	    c_delt.draw(h2_kp_sect_deltat.get(s));
	    dir.addDataSet(h2_kp_sect_deltat.get(s));
	}
	c_delt.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h_"+s_run_number+"_clas12kp_delt_allsect.png");	       
    }

    public void viewHipoOut(){

	TBrowser b = new TBrowser(dir);

    }

}
