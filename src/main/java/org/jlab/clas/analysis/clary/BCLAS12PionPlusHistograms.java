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

public class BCLAS12PionPlusHistograms {

    int run_number = -1;
    String s_run_number = " ";
    public BCLAS12PionPlusHistograms( int temp_run ){
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

    H1F h_pp_p;
    H1F h_pp_theta;
    H1F h_pp_phi;
    H1F h_pp_vz;
    H1F h_pp_timing;

    H2F h2_pp_thetap;
    H2F h2_pp_betap;
    H2F h2_pp_deltimep;
    H2F h2_pp_deltabeta;
    H2F h2_pp_tof;

    Vector<H2F> h2_pp_sect_betap = new Vector< H2F >();
    Vector<H1F> h_pp_sect_vz = new Vector<H1F >();
    Vector<H2F> h2_pp_sect_pvz = new Vector<H2F>();
    Vector<H2F> h2_pp_sect_deltat = new Vector<H2F>();

    public void createCLAS12PionPHistograms( int i ){

	h2_pp_thetap = new H2F("h2_pp_thetap","h2_pp_thetp",100, min_p, max_p, 100, min_theta, max_theta);
	h2_pp_betap = new H2F("h2_pp_betap","h2_pp_betap",350, min_p, max_p, 350, min_b, max_b);
	h2_pp_deltimep = new H2F("h2_pp_deltimep","h2_pp_deltimep",350, min_p, max_p, 350, min_delt, max_delt);
	h2_pp_deltabeta = new H2F("h2_pp_deltabeta","h2_pp_deltabeta",350, min_p, max_p, 350, min_delb, max_delb);
	h2_pp_tof = new H2F("h2_pp_tof","h2_pp_tof",350, min_p, max_p, 350, min_tof, max_tof);

    }

    public void createCLAS12PionPSectorHistograms( int sector ){

	    h2_pp_sect_betap.add( new H2F("h2_"+s_run_number+"_clas12pp_"+Integer.toString(sector)+"_betap","h2_"+s_run_number+"_clas12pp_"+Integer.toString(sector)+"_betap", 350, min_p, max_p, 350, min_b, max_b));	    
	    h_pp_sect_vz.add( new H1F("h_"+s_run_number+"_clas12pp_"+Integer.toString(sector)+"_vz","h_"+s_run_number+"_clas12pp_"+Integer.toString(sector)+"_vz", 200, min_vz, max_vz));	    
	    h2_pp_sect_pvz.add( new H2F("h_"+s_run_number+"_clas12pp_"+Integer.toString(sector)+"_pvz","h_"+s_run_number+"_clas12pp_"+Integer.toString(sector)+"_pvz", 200, min_vz, max_vz, 200, min_p, max_p));
	    h2_pp_sect_deltat.add( new H2F("h_"+s_run_number+"_clas12pp_"+Integer.toString(sector)+"_deltat","h_"+s_run_number+"_clas12pp_"+Integer.toString(sector)+"_deltat", 350, min_p, max_p, 350, min_delt, max_delt));  
    
    }

    public void clas12PionPHistoToHipo(){

	h2_pp_betap.setTitleX("p [GeV]");
	h2_pp_betap.setTitleY("#beta");
	
	h2_pp_thetap.setTitleX("p [GeV]");
	h2_pp_thetap.setTitleY("#theta [deg]");

	h2_pp_deltimep.setTitleX("p [GeV]");
	h2_pp_deltimep.setTitleY("#delta tof [ns]");

	h2_pp_deltabeta.setTitleX("p [GeV]");
	h2_pp_deltabeta.setTitleY("#delta #beta");

	h2_pp_tof.setTitleX("p [GeV]");
	h2_pp_tof.setTitleY("tof [ns]");

  	dir.mkdir("/clas12pionppid/");
	dir.cd("/clas12pionppid/");
	dir.addDataSet(h2_pp_betap);
	dir.addDataSet(h2_pp_thetap);
	dir.addDataSet(h2_pp_deltimep);
	dir.addDataSet(h2_pp_deltabeta);
	dir.addDataSet(h2_pp_tof);

	dir.mkdir("/clas12pionpid/h_pp_sect_betap/");
	dir.cd("/clas12pionpid/h_pp_sect_betap/");	
	EmbeddedCanvas c_betap = new EmbeddedCanvas();
	c_betap.setSize(1600,800);
	c_betap.divide(3,2);
	for( int s = 0; s < 6; s++ ){	   
	    c_betap.cd(s);
	    h2_pp_sect_betap.get(s).setTitleX(Integer.toString(s));
	    c_betap.draw(h2_pp_sect_betap.get(s),"colz");
	    dir.addDataSet(h2_pp_sect_betap.get(s));
	}
	c_betap.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h_"+s_run_number+"_clas12pp_betap_allsect.png"); 

 	dir.mkdir("/clas12pionpid/h_pp_sect_vz/");
	dir.cd("/clas12pionpid/h_pp_sect_vz/");	
	EmbeddedCanvas c_vz = new EmbeddedCanvas();
	c_vz.setSize(1600,800);
	c_vz.divide(3,2);
	for( int s = 0; s < 6; s++ ){	   
	    c_vz.cd(0);
	    h_pp_sect_vz.get(s).setTitleX(Integer.toString(s));
	    c_vz.draw(h_pp_sect_vz.get(s));
	    dir.addDataSet(h_pp_sect_vz.get(s));
	}
	c_vz.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h_"+s_run_number+"_clas12pp_vz_allsect.png"); 

 	dir.mkdir("/clas12pionpid/h_pp_sect_pvz/");
	dir.cd("/clas12pionpid/h_pp_sect_pvz/");	
	EmbeddedCanvas c_pvz = new EmbeddedCanvas();
	c_pvz.setSize(1600,800);
	c_pvz.divide(3,2);
	for( int s = 0; s < 6; s++ ){	   
	    c_pvz.cd(0);
	    h2_pp_sect_pvz.get(s).setTitleX(Integer.toString(s));
	    c_pvz.draw(h2_pp_sect_pvz.get(s));
	    dir.addDataSet(h2_pp_sect_pvz.get(s));
	}
	c_pvz.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h_"+s_run_number+"_clas12pp_pvz_allsect.png"); 

 	dir.mkdir("/clas12pionpid/h_pp_sect_deltat/");
	dir.cd("/clas12pionpid/h_pp_sect_deltat/");	
	EmbeddedCanvas c_delt = new EmbeddedCanvas();
	c_delt.setSize(1600,800);
	c_delt.divide(3,2);
	for( int s = 0; s < 6; s++ ){	   
	    c_delt.cd(0);
	    h2_pp_sect_deltat.get(s).setTitleX(Integer.toString(s));
	    c_delt.draw(h2_pp_sect_deltat.get(s));
	    dir.addDataSet(h2_pp_sect_deltat.get(s));
	}
	c_delt.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h_"+s_run_number+"_clas12pp_delt_allsect.png");	       
 
	       
    }

    public void viewHipoOut(){

	TBrowser b = new TBrowser(dir);

    }

}
