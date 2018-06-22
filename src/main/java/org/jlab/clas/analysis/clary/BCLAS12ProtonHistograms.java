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

public class BCLAS12ProtonHistograms {

    int run_number = -1;
    String s_run_number = " ";
    public BCLAS12ProtonHistograms( int temp_run ){
	run_number = temp_run;
	s_run_number = Integer.toString(run_number);
    }

    TDirectory dir = new TDirectory();

    double min_p = 0.0; double max_p = 6.5;
    double min_theta = 0.0; double max_theta = 60.5;
    double min_phi = -180.0; double max_phi = 180.0;
    double min_vz = -10.0; double max_vz = 10.;
    double min_b = 0.50; double max_b = 1.2;
    double min_delt = -10.0; double max_delt = 10.0;
    
    H2F h2_pr_betap;
    H2F h2_pr_deltat;
    H1F h_pr_rf_time;
    Vector<H2F> h2_pr_sect_betap = new Vector< H2F >();
    Vector<H1F> h_pr_sect_vz = new Vector<H1F >();
    Vector<H2F> h2_pr_sect_pvz = new Vector<H2F>();
    Vector<H2F> h2_pr_sect_deltat = new Vector<H2F>();
    

    HashMap<Integer, Vector<H2F> > m_pr_sect_panel_deltp = new HashMap<Integer, Vector< H2F>  >(); 

    public void createCLAS12ProtonHistograms( int i ){

	h2_pr_betap = new H2F("h2_"+s_run_number+"_clas12pr_betap","h2_clas12pr_betap",350, min_p, max_p, 350, min_b, max_b);
	h2_pr_deltat = new H2F("h2_"+s_run_number+"_clas12pr_deltat","h2_clas12pr_deltat",250, min_p, max_p, 250, min_delt, max_delt);
	h_pr_rf_time = new H1F("h2_"+s_run_number+"_clas12pr_rftime","h2_clas12pr_rftime", 200, 0.0, 200.0);


    }

    public void createCLAS12ProtonSectorHistograms( int sector ){

	    h2_pr_sect_betap.add( new H2F("h2_"+s_run_number+"_clas12pr_"+Integer.toString(sector)+"_betap","h2_"+s_run_number+"_clas12pr_"+Integer.toString(sector)+"_betap", 350, min_p, max_p, 350, min_b, max_b));	    
	    h_pr_sect_vz.add( new H1F("h_"+s_run_number+"_clas12pr_"+Integer.toString(sector)+"_vz","h_"+s_run_number+"_clas12pr_"+Integer.toString(sector)+"_vz", 200, min_vz, max_vz));	    
	    h2_pr_sect_pvz.add( new H2F("h_"+s_run_number+"_clas12pr_"+Integer.toString(sector)+"_pvz","h_"+s_run_number+"_clas12pr_"+Integer.toString(sector)+"_pvz", 200, min_vz, max_vz, 200, min_p, max_p));
	    h2_pr_sect_deltat.add( new H2F("h_"+s_run_number+"_clas12pr_"+Integer.toString(sector)+"_deltat","h_"+s_run_number+"_clas12pr_"+Integer.toString(sector)+"_deltat", 200, min_p, max_p, 200, min_delt, max_delt));  
    }

    public void createProtonFTOFHistograms( int sector, int panel ){

	for( int s = 0; s < sector; s++ ){
	    Vector< H2F  > p_temp = new Vector<H2F>();
	    for( int p = 0; p < panel; p++ ){
		p_temp.add( new H2F("h_"+s_run_number+"_clas12pr_deltimep_"+Integer.toString(s)+"_"+Integer.toString(p),"h_"+s_run_number+"_clas12pr_deltimep_"+Integer.toString(s)+"_"+Integer.toString(p), 200, 0.0, 10.5, 200, -10.0, 10.0) );
	    }
	    m_pr_sect_panel_deltp.put(s, p_temp); 
	}

    }

    public void clas12ProtonHistoToHipo(){

 	dir.mkdir("/clas12Protonpid/betap/");
	dir.cd("/clas12Protonpid/betap/");
	dir.addDataSet(h2_pr_betap);      
	dir.addDataSet(h2_pr_deltat);
	dir.addDataSet(h_pr_rf_time);
 
	EmbeddedCanvas c_prbetap  = new EmbeddedCanvas();
	c_prbetap.setSize(800,800);
	c_prbetap.draw(h2_pr_betap);
	c_prbetap.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h2_"+s_run_number+"pr_betap.png");

	EmbeddedCanvas c_prdeltat  = new EmbeddedCanvas();
	c_prdeltat.setSize(800,800);
	c_prdeltat.draw(h2_pr_deltat);
	c_prdeltat.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h2_"+s_run_number+"pr_deltat.png");

	EmbeddedCanvas c_prrftime  = new EmbeddedCanvas();
	c_prrftime.setSize(800,800);
	c_prrftime.draw(h_pr_rf_time);
	c_prrftime.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h2_"+s_run_number+"pr_rftime.png");


 	dir.mkdir("/clas12Protonpid/deltimep/");
	dir.cd("/clas12Protonpid/deltimep/");
	for( int s = 0; s < 6; s++){
	    EmbeddedCanvas c_sp_deltp = new EmbeddedCanvas();
	    c_sp_deltp.setSize(1600,800);
 	    c_sp_deltp.divide(9,6);
	    for( int p = 0; p < 48; p++ ){
		c_sp_deltp.cd(p);
		m_pr_sect_panel_deltp.get(s).get(p).setTitleX("S" + Integer.toString(s) + " P" + Integer.toString(p));
		c_sp_deltp.draw(m_pr_sect_panel_deltp.get(s).get(p),"colz");
		dir.addDataSet(m_pr_sect_panel_deltp.get(s).get(p));
	    }
	    c_sp_deltp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h_"+s_run_number+"_clas12pr_deltimep_"+Integer.toString(s)+"_panel.png");

	}

 	dir.mkdir("/clas12Protonpid/h_pr_sect_betap/");
	dir.cd("/clas12Protonpid/h_pr_sect_betap/");	
	EmbeddedCanvas c_betap = new EmbeddedCanvas();
	c_betap.setSize(1600,800);
	c_betap.divide(3,2);
	for( int s = 0; s < 6; s++ ){	   
	    c_betap.cd(s);
	    h2_pr_sect_betap.get(s).setTitleX(Integer.toString(s));
	    c_betap.draw(h2_pr_sect_betap.get(s),"colz");
	    dir.addDataSet(h2_pr_sect_betap.get(s));
	}
	c_betap.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h_"+s_run_number+"_clas12pr_betap_allsect.png"); 

 	dir.mkdir("/clas12Protonpid/h_pr_sect_vz/");
	dir.cd("/clas12Protonpid/h_pr_sect_vz/");	
	EmbeddedCanvas c_vz = new EmbeddedCanvas();
	c_vz.setSize(1600,800);
	c_vz.divide(3,2);
	for( int s = 0; s < 6; s++ ){	   
	    c_vz.cd(0);
	    h_pr_sect_vz.get(s).setTitleX(Integer.toString(s));
	    c_vz.draw(h_pr_sect_vz.get(s));
	    dir.addDataSet(h_pr_sect_vz.get(s));
	}
	c_vz.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h_"+s_run_number+"_clas12pr_vz_allsect.png"); 

 	dir.mkdir("/clas12Protonpid/h_pr_sect_pvz/");
	dir.cd("/clas12Protonpid/h_pr_sect_pvz/");	
	EmbeddedCanvas c_pvz = new EmbeddedCanvas();
	c_pvz.setSize(1600,800);
	c_pvz.divide(3,2);
	for( int s = 0; s < 6; s++ ){	   
	    c_pvz.cd(0);
	    h2_pr_sect_pvz.get(s).setTitleX(Integer.toString(s));
	    c_pvz.draw(h2_pr_sect_pvz.get(s));
	    dir.addDataSet(h2_pr_sect_pvz.get(s));
	}
	c_pvz.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h_"+s_run_number+"_clas12pr_pvz_allsect.png"); 

 	dir.mkdir("/clas12Protonpid/h_pr_sect_deltat/");
	dir.cd("/clas12Protonpid/h_pr_sect_deltat/");	
	EmbeddedCanvas c_delt = new EmbeddedCanvas();
	c_delt.setSize(1600,800);
	c_delt.divide(3,2);
	for( int s = 0; s < 6; s++ ){	   
	    c_delt.cd(0);
	    h2_pr_sect_deltat.get(s).setTitleX(Integer.toString(s));
	    c_delt.draw(h2_pr_sect_deltat.get(s));
	    dir.addDataSet(h2_pr_sect_deltat.get(s));
	}
	c_delt.save("/lustre/expphy/work/hallb/clas12/bclary/pics/h_"+s_run_number+"_clas12pr_delt_allsect.png"); 


	//dir.addDataSet(h_el_w);
	//dir.addDataSet(h2_el_thetap);      

	/*dir.mkdir("/clas12pid_sector_w/");
	dir.cd("/clas12pid_sector_w/");
	for( H1F h_temp : h_el_sect_w ){
	dir.addDataSet(h_temp);
	}*/

	//	dir.writeFile("clas12_protonpid.hipo");

    }


    public void viewHipoOut(){
	//dir.readFile("clas12_pid.hipo");
	//dir.ls();
    	TBrowser b = new TBrowser(dir);
    }

}
