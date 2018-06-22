package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
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


public class BPIDProtonHistograms {

    int run_number = -1;
    String s_run_number = " ";
    public BPIDProtonHistograms(int temp_run) {
	//constructor
	run_number = temp_run;
	s_run_number = Integer.toString(run_number);
    }

    TDirectory dir = new TDirectory();
    
    double min_p = 0.0; double max_p = 6.5;
    double min_theta = 0.0; double max_theta = 60.0;
    double min_phi = -180.0; double max_phi = 180.0;
    double min_vz = -5.0; double max_vz = 10.0;
    double min_timing = 0.0; double max_timing = 700.0;
    double min_delt = -5.0; double max_delt = 5.0;
    double min_delb = -1.0; double max_delb = 1.0;
    double min_b = 0.0; double max_b = 1.4;
    double min_rpath = 300.0; double max_rpath = 750.0; 
    double min_tof = 15.0; double max_tof = 35.0;

    double min_dc_minlx = -150.0; double max_dc_maxlx = 150.0;
    double min_dc_minly = -300.0; double max_dc_maxly = 300.0;
    double min_dc_mingx = -400.0; double max_dc_maxgx = 400.0;
    double min_dc_mingy = -400.0; double max_dc_maxgy = 400.0;

    Vector<H1F> h_pr_p = new Vector<H1F>(); 
    Vector<H1F> h_pr_theta = new Vector<H1F>(); 
    Vector<H1F> h_pr_phi = new Vector<H1F>(); 
    Vector<H1F> h_pr_vz = new Vector<H1F>(); 
    Vector<H1F> h_pr_timing = new Vector<H1F>(); 
    Vector<H1F> h_pr_masstime = new Vector<H1F>();

    Vector<H2F> h2_pr_masstimep = new Vector<H2F>();
    Vector<H2F> h2_pr_vzphi = new Vector< H2F >();
    Vector<H2F> h2_pr_deltimep = new Vector<H2F>(); 
    Vector<H2F> h2_pr_deltabeta = new Vector<H2F>();
    Vector<H2F> h2_pr_betap = new Vector<H2F>();

    Vector<H1F> h_pr_rpath = new Vector<H1F>();
    Vector<H2F> h2_pr_tof = new Vector<H2F>();
    Vector<H1F> h_pr_beta_time = new Vector<H1F>();
    Vector<H1F> h_pr_beta_mntm = new Vector<H1F>();

    Vector<H2F> h2_pr_dchit_R1_xy = new Vector<H2F>();
    Vector<H2F> h2_pr_dchit_R2_xy = new Vector<H2F>();
    Vector<H2F> h2_pr_dchit_R3_xy = new Vector<H2F>();

    Vector<H2F> h2_pr_dchit_R1_gxy = new Vector<H2F>();
    Vector<H2F> h2_pr_dchit_R2_gxy = new Vector<H2F>();
    Vector<H2F> h2_pr_dchit_R3_gxy = new Vector<H2F>();

    Vector<H2F> h2_pr_ectotp = new Vector<H2F>();
    Vector<H1F> h_pr_ftof_l1_e = new Vector<H1F>();
    Vector<H1F> h_pr_ftof_l2_e = new Vector<H1F>();
    Vector<H1F> h_pr_pcal_e = new Vector<H1F>();
    Vector<H1F> h_pr_eical_e = new Vector<H1F>();
    Vector<H1F> h_pr_eocal_e = new Vector<H1F>();
    
    Vector<Vector<H1F>> h_pr_sect_p = new Vector< Vector<H1F> >();
    Vector<Vector<H1F>> h_pr_sect_theta = new Vector< Vector<H1F> >();
    Vector<Vector<H1F>> h_pr_sect_phi = new Vector< Vector<H1F> >();
    Vector<Vector<H1F>> h_pr_sect_masstime = new Vector<Vector<H1F> >();
    Vector<Vector<H1F>> h_pr_sect_ftof_l2_masstime = new Vector<Vector<H1F> >();

    Vector<Vector<H2F>> h2_pr_sect_ectotp = new Vector<Vector<H2F> >();
    Vector<Vector<H1F>> h_pr_sect_pcal_e = new Vector<Vector<H1F> >();
    Vector<Vector<H1F>> h_pr_sect_ftof_l1_e = new Vector<Vector<H1F> >();
    Vector<Vector<H1F>> h_pr_sect_ftof_l2_e = new Vector<Vector<H1F> >();
    Vector<Vector<H1F>> h_pr_sect_eical_e = new Vector<Vector<H1F> >();
    Vector<Vector<H1F>> h_pr_sect_eocal_e = new Vector<Vector<H1F> >();

    Vector<Vector<H2F>> h2_pr_sect_betap = new Vector< Vector<H2F> >();
    Vector<Vector<H2F>> h2_pr_sect_deltabeta = new Vector<Vector<H2F> >();
    Vector<Vector<H2F>> h2_pr_sect_deltimep = new Vector<Vector<H2F> >();    
    Vector<Vector<H2F>> h2_pr_sect_vztheta = new Vector< Vector<H2F> >();
    Vector<Vector<H2F>> h2_pr_sect_masstimep = new Vector<Vector<H2F> >();
    //////////////////////////////////////////////////////////////////////////
    //LAYERS FTOF 2 PLOTS
    
    Vector<H2F> h2_pr_ftof_l2_masstimep = new Vector<H2F>();
    Vector<H2F> h2_pr_ftof_l2_vzphi = new Vector< H2F >();
    Vector<H2F> h2_pr_ftof_l2_deltimep = new Vector<H2F>(); 
    Vector<H2F> h2_pr_ftof_l2_deltabeta = new Vector<H2F>();
    Vector<H2F> h2_pr_ftof_l2_betap = new Vector<H2F>();

    Vector<H2F> h2_pr_ftof_l2_tof = new Vector<H2F>();
    Vector<H1F> h_pr_ftof_l2_beta_time = new Vector<H1F>();
    Vector<H1F> h_pr_ftof_l2_masstime = new Vector<H1F>();

    Vector<Vector<H2F>> h2_pr_sect_ftof_l2_betap = new Vector< Vector<H2F> >();
    Vector<Vector<H2F>> h2_pr_sect_ftof_l2_deltabeta = new Vector<Vector<H2F> >();
    Vector<Vector<H2F>> h2_pr_sect_ftof_l2_deltimep = new Vector<Vector<H2F> >();    
    Vector<Vector<H2F>> h2_pr_sect_ftof_l2_vztheta = new Vector< Vector<H2F> >();
    Vector<Vector<H2F>> h2_pr_sect_ftof_l2_masstimep = new Vector<Vector<H2F> >();

    
    Vector<Vector<H2F>> h_pr_sect_panel_deltp = new Vector< Vector<H2F> >();   
    Vector< Vector< Vector< H2F > > > h_pr_sect_panel_deltimep = new Vector< Vector< Vector<H2F> > >(); 

    HashMap<Integer, Vector< Vector<H2F> > > m_pr_sect_panel_deltp = new HashMap<Integer, Vector< Vector< H2F> > >();

    public void createProtonHistograms(int i){

	h_pr_p.add( new H1F("h_"+s_run_number+"_pr_p_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_p_cutlvl"+Integer.toString(i), 100, min_p, max_p ) );
	h_pr_theta.add( new H1F("h_"+s_run_number+"_pr_theta_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_theta_cutlvl"+Integer.toString(i), 100, min_theta, max_theta) );
	h_pr_phi.add( new H1F("h_"+s_run_number+"_pr_phi_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_phi_cutlvl"+Integer.toString(i), 100, min_phi, max_phi ) );
	h_pr_vz.add( new H1F("h_"+s_run_number+"_pr_vz_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_vz_cutlvl"+Integer.toString(i), 100, min_vz, max_vz ) );
	h_pr_timing.add( new H1F("h_"+s_run_number+"_pr_timing_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_timing_cutlvl"+Integer.toString(i), 100, min_timing, max_timing ) );
	h_pr_rpath.add( new H1F("h_"+s_run_number+"_pr_rpath_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_rpath_cutlvl"+Integer.toString(i), 300, min_rpath, max_rpath ) );
	h_pr_beta_time.add( new H1F("h_"+s_run_number+"_pr_betatime_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_betatime_cutlvl"+Integer.toString(i), 200, min_b, max_b ) );
	h_pr_beta_mntm.add( new H1F("h_"+s_run_number+"_pr_betamntm_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_betamntm_cutlvl"+Integer.toString(i), 200, min_b, max_b ) );
	h2_pr_tof.add( new H2F("h2_"+s_run_number+"_pr_tof_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_tof_cutlvl"+Integer.toString(i), 500, min_p, max_p, 500, min_tof, max_tof ) );
	
	h2_pr_deltimep.add( new H2F("h2_"+s_run_number+"_pr_deltimep_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_deltimep_cutlvl"+Integer.toString(i),500, min_p, max_p, 500, min_delt, max_delt ));
	h2_pr_betap.add( new H2F("h2_"+s_run_number+"_pr_betap_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_betap_cutlvl"+Integer.toString(i),500, min_p, max_p, 500, min_b, max_b ));
	h2_pr_deltabeta.add( new H2F("h2_"+s_run_number+"_pr_deltabeta_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_deltabeta_cutlvl"+Integer.toString(i), 500, min_p, max_p, 500, min_delb, max_delb ));

	h2_pr_vzphi.add( new H2F("h2_"+s_run_number+"_pr_vzphi_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_vzphi_cutlvl"+Integer.toString(i), 200, min_vz, max_vz, 200, min_phi, max_phi ));
 	h2_pr_dchit_R1_xy.add( new H2F("h2_"+s_run_number+"_pr_dchit_R1_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_dchit_R1_cutlvl"+Integer.toString(i), 500, -95.0, 95.0, 500, -100.0, 100.0 ));
 	h2_pr_dchit_R2_xy.add( new H2F("h2_"+s_run_number+"_pr_dchit_R2_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_dchit_R2_cutlvl"+Integer.toString(i), 500, -150.0, 150.0, 500, -150.0, 150.0 ));
 	h2_pr_dchit_R3_xy.add( new H2F("h2_"+s_run_number+"_pr_dchit_R3_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_dchit_R3_cutlvl"+Integer.toString(i), 500, -200.0, 200.0, 500, -150.0, 150.0 ));
 	h2_pr_masstimep.add( new H2F("h2_"+s_run_number+"_pr_masstimep_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_masstimep_cutlvl"+Integer.toString(i), 500, 0.0, 1.2, 500, min_p, max_p ));
 	h_pr_masstime.add( new H1F("h_"+s_run_number+"_pr_masstime_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_masstime_cutlvl"+Integer.toString(i), 500, 0.0, 2.0 ));


 	h2_pr_ectotp.add( new H2F("h2_"+s_run_number+"_pr_ectotp_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_ectotp_cutlvl"+Integer.toString(i), 400, 0.0, 4.0, 400, 0.0, 0.5 ));
 	h_pr_pcal_e.add( new H1F("h_"+s_run_number+"_pr_pcal_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_pcal_e_cutlvl"+Integer.toString(i), 250, 0.0, 0.0070 ));
	h_pr_ftof_l1_e.add( new H1F("h_"+s_run_number+"_pr_ftof_l1_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_ftof_l1_e_cutlvl"+Integer.toString(i), 250, 0.0, 0.50 ));
	h_pr_ftof_l2_e.add( new H1F("h_"+s_run_number+"_pr_ftof_l2_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_ftof_l2_e_cutlvl"+Integer.toString(i), 250, 0.0, 0.50 ));	
 	h_pr_eical_e.add( new H1F("h_"+s_run_number+"_pr_eical_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_eical_e_cutlvl"+Integer.toString(i), 250, 0.0, 0.50 ));
 	h_pr_eocal_e.add( new H1F("h_"+s_run_number+"_pr_eocal_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_eocal_e_cutlvl"+Integer.toString(i), 250, 0.0, 0.50 ));

	h2_pr_ftof_l2_tof.add( new H2F("h2_"+s_run_number+"_pr_ftof_l2_tof_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_ftof_l2_tof_cutlvl"+Integer.toString(i), 500, min_p, max_p, 500, min_tof, max_tof ) );
	h_pr_ftof_l2_beta_time.add( new H1F("h_"+s_run_number+"_pr_ftof_l2_betatime_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_ftof_l2_betatime_cutlvl"+Integer.toString(i), 400, min_b, max_b ) );
 	h2_pr_ftof_l2_masstimep.add( new H2F("h2_"+s_run_number+"_pr_ftof_l2_masstimep_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_ftof_l2_masstimep_cutlvl"+Integer.toString(i), 500, 0.0, 1.2, 500, min_p, max_p ));
 	h_pr_ftof_l2_masstime.add( new H1F("h_"+s_run_number+"_pr_ftof_l2_masstime_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_ftof_l2_masstime_cutlvl"+Integer.toString(i), 500, 0.0, 2.0 ));
	h2_pr_ftof_l2_deltabeta.add( new H2F("h2_"+s_run_number+"_pr_ftof_l2_deltabeta_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_ftof_l2_deltabeta_cutlvl"+Integer.toString(i), 500, min_p, max_p, 500, min_delb, max_delb ));
	h2_pr_ftof_l2_betap.add( new H2F("h2_"+s_run_number+"_pr_ftof_l2_betap_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_ftof_l2_betap_cutlvl"+Integer.toString(i),500, min_p, max_p, 500, min_b, max_b ));
	h2_pr_ftof_l2_deltimep.add( new H2F("h2_"+s_run_number+"_pr_ftof_l2_deltimep_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_ftof_l2_deltimep_cutlvl"+Integer.toString(i),500, min_p, max_p, 500, min_delt, max_delt ));

    }

    public void createProtonSectorHistograms( int sector, int max_cuts ){
	h_pr_sect_p.add( new Vector<H1F>() );
	h_pr_sect_masstime.add( new Vector<H1F>() );
 	h_pr_sect_theta.add( new Vector<H1F>() );
	h_pr_sect_phi.add( new Vector<H1F>() );
	h2_pr_sect_betap.add( new Vector<H2F>() );
	h2_pr_sect_deltabeta.add( new Vector<H2F>() );
	h2_pr_sect_deltimep.add( new Vector<H2F>() );
	h2_pr_sect_masstimep.add( new Vector<H2F>() );
	
	h2_pr_sect_ectotp.add( new Vector<H2F>() );
	h_pr_sect_pcal_e.add( new Vector<H1F>() );
	h_pr_sect_ftof_l1_e.add( new Vector<H1F>() );
	h_pr_sect_ftof_l2_e.add( new Vector<H1F>() );	
	h_pr_sect_eical_e.add( new Vector<H1F>() );
	h_pr_sect_eocal_e.add( new Vector<H1F>() );


	h_pr_sect_ftof_l2_masstime.add( new Vector<H1F>() );
	h2_pr_sect_ftof_l2_betap.add( new Vector<H2F>() );
	h2_pr_sect_ftof_l2_deltabeta.add( new Vector<H2F>() );
	h2_pr_sect_ftof_l2_deltimep.add( new Vector<H2F>() );
	h2_pr_sect_ftof_l2_masstimep.add( new Vector<H2F>() );

	
	for( int i = 0; i <= max_cuts; i++ ){
	    (h_pr_sect_p.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_p_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_p_cutlvl"+Integer.toString(i), 200, min_p, max_p ) );  
	    (h_pr_sect_masstime.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_masstime_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_masstime_cutlvl"+Integer.toString(i), 550, 0.0 , 2.0) );  
	    (h2_pr_sect_betap.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_betap_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_betap_cutlvl"+Integer.toString(i), 500, min_p, max_p, 500, min_b, max_b ) );  
	    (h2_pr_sect_deltabeta.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_deltabeta_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_deltabeta_cutlvl"+Integer.toString(i), 500, min_p, max_p, 500, min_delb, max_delb ) );  
	    (h2_pr_sect_deltimep.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_deltimep_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_deltimep_cutlvl"+Integer.toString(i), 500, min_p, max_p, 500, min_delt, max_delt ) );  
	    (h2_pr_sect_masstimep.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_masstimep_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_masstimep_cutlvl"+Integer.toString(i), 500, 0.0, 1.2, 500, min_p, max_p ) );  

	    (h2_pr_sect_ectotp.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_ectotp_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_ectotp_cutlvl"+Integer.toString(i), 400, 0.0, 4.0, 400, 0.0, 0.5 ) );  
	    (h_pr_sect_pcal_e.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_pcal_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_pcal_e_cutlvl"+Integer.toString(i), 400, 0.0, 0.0080 ) );  
	    (h_pr_sect_ftof_l1_e.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_ftof_l1_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_ftof_l1_e_cutlvl"+Integer.toString(i), 400, 0.0, 0.50 ) );  
	    (h_pr_sect_ftof_l2_e.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_ftof_l2_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_ftof_l2_e_cutlvl"+Integer.toString(i), 400, 0.0, 0.50 ) );  
	    (h_pr_sect_eical_e.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_eical_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_eical_e_cutlvl"+Integer.toString(i), 400, 0.0, 0.50 ) );  
	    (h_pr_sect_eocal_e.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_eocal_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_eocal_e_cutlvl"+Integer.toString(i), 400, 0.0, 0.50 ) );  


	    (h_pr_sect_ftof_l2_masstime.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_ftof_l2_masstime_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_ftof_l2_masstime_cutlvl"+Integer.toString(i), 500, 0.0 , 2.0) );  	    	    
	    (h2_pr_sect_ftof_l2_betap.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_ftof_l2_betap_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_ftof_l2_betap_cutlvl"+Integer.toString(i), 500, min_p, max_p, 500, min_b, max_b ) );  
	    (h2_pr_sect_ftof_l2_deltabeta.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_ftof_l2_deltabeta_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_ftof_l2_deltabeta_cutlvl"+Integer.toString(i), 500, min_p, max_p, 500, min_delb, max_delb ) );  
	    (h2_pr_sect_ftof_l2_deltimep.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_ftof_l2_deltimep_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_ftof_l2_deltimep_cutlvl"+Integer.toString(i), 500, min_p, max_p, 500, min_delt, max_delt ) );  
	    (h2_pr_sect_ftof_l2_masstimep.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_ftof_l2_masstimep_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_ftof_l2_masstimep_cutlvl"+Integer.toString(i), 500, 0.0, 1.2, 500, min_p, max_p ) );  

	}
    }

    public void createProtonFTOFHistograms( int sector, int panel, int max_cuts ){
	
	for( int s = 0; s < sector; s++ ){
	    Vector<Vector<H2F >> p_temp = new Vector<Vector<H2F >>();	    
	    for( int p = 0; p < panel; p++ ){
		p_temp.add(new Vector<H2F>() );
	    }
	    m_pr_sect_panel_deltp.put( s, p_temp);
	}

	for( int s = 0; s < sector; s++ ){	    
	    for( int p = 0; p < panel; p++ ){
		for( int c = 0; c < max_cuts; c++ ){
		    Vector< Vector<H2F> > mh_temp = m_pr_sect_panel_deltp.get(s);
		    mh_temp.get(p).add(new H2F("h_"+s_run_number+"_pr_"+Integer.toString(s)+"_"+Integer.toString(p)+"_cutlvl"+Integer.toString(c),"h_"+s_run_number+"_pr_"+Integer.toString(s)+"_"+Integer.toString(p)+"_cutlvl"+Integer.toString(c), 400, 0.0, 10.5, 400, -10.0, 10.0) );
		}
	    }
	}				   
    }

   
    public void protonHistoToHipo(){

	dir.mkdir("/proton/cutlvls/h_pr_p/");
	dir.cd("/proton/cutlvls/h_pr_p/");
	for( H1F h_temp : h_pr_p ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h2_pr_tof/");
	dir.cd("/proton/cutlvls/h2_pr_tof/");
	for( H2F h_temp : h2_pr_tof ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("TOF [ns]");	    
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h_pr_rpath/");
	dir.cd("/proton/cutlvls/h_pr_rpath/");
	for( H1F h_temp : h_pr_rpath ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h_pr_beta_time/");
	dir.cd("/proton/cutlvls/h_pr_beta_time/");
	for( H1F h_temp : h_pr_beta_time ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h_pr_masstime/");
	dir.cd("/proton/cutlvls/h_pr_masstime/");
	for( H1F h_temp : h_pr_masstime ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Mass [GeV/c]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h2_pr_masstimep/");
	dir.cd("/proton/cutlvls/h2_pr_masstimep/");
	for( H2F h_temp : h2_pr_masstimep ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Mass [GeV/c]");
	    h_temp.setTitleY("p [GeV/c^2]");
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h2_pr_dchit_R1_xy/");
	dir.cd("/proton/cutlvls/h2_pr_dchit_R1_xy/");
	for( H2F h_temp : h2_pr_dchit_R1_xy){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("DC X [cm]");
	    h_temp.setTitleX("DC Y [cm]"); 
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}
	dir.mkdir("/proton/cutlvls/h2_pr_dchit_R2_xy/");
	dir.cd("/proton/cutlvls/h2_pr_dchit_R2_xy/");
	for( H2F h_temp : h2_pr_dchit_R2_xy){
	    dir.addDataSet(h_temp);
	}
	dir.mkdir("/proton/cutlvls/h2_pr_dchit_R3_xy/");
	dir.cd("/proton/cutlvls/h2_pr_dchit_R3_xy/");
	for( H2F h_temp : h2_pr_dchit_R3_xy){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("DC X [cm]");
	    h_temp.setTitleX("DC Y [cm]"); 
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h2_pr_vzphi/");
	dir.cd("/proton/cutlvls/h2_pr_vzphi/");
	for( H2F h_temp : h2_pr_vzphi){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h2_pr_deltabeta/");
	dir.cd("/proton/cutlvls/h2_pr_deltabeta/");
	for( H2F h_temp : h2_pr_deltabeta ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#delta #beta");	    
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}
    
	dir.mkdir("/proton/cutlvls/h2_pr_ectotp/");
	dir.cd("/proton/cutlvls/h2_pr_ectotp/");
	for( H2F h_temp : h2_pr_ectotp){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("EC SF"); 
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h_pr_pcal_e/");
	dir.cd("/proton/cutlvls/h_pr_pcal_e/");
	for( H1F h_temp : h_pr_pcal_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("PCAL Energy [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h_pr_ftof_l1_e/");
	dir.cd("/proton/cutlvls/h_pr_ftof_l1_e/");
	for( H1F h_temp : h_pr_ftof_l1_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("FTOF L1 Energy [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}


	dir.mkdir("/proton/cutlvls/h_pr_ftof_l2_e/");
	dir.cd("/proton/cutlvls/h_pr_ftof_l2_e/");
	for( H1F h_temp : h_pr_ftof_l2_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("FTOF L2 Energy [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h_pr_eical_e/");
	dir.cd("/proton/cutlvls/h_pr_eical_e/");
	for( H1F h_temp : h_pr_eical_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("ECAL EI [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h_pr_eocal_e/");
	dir.cd("/proton/cutlvls/h_pr_eocal_e/");
	for( H1F h_temp : h_pr_eocal_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("ECAL EO [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}


	dir.mkdir("/proton/cutlvls/h_pr_sect_panel_deltimep/");
	dir.cd("/proton/cutlvls/h_pr_sect_panel_deltimep/");
	for( int s = 0; s < 6; s++){
	    EmbeddedCanvas c_sp_deltp = new EmbeddedCanvas();
	    c_sp_deltp.setSize(1600,800);
 	    c_sp_deltp.divide(9,6);
	    for( int p = 0; p < 48; p++ ){
		c_sp_deltp.cd(p);
		m_pr_sect_panel_deltp.get(s).get(p).get(0).setTitleX("S" + Integer.toString(s) + " P" + Integer.toString(p));
		c_sp_deltp.draw(m_pr_sect_panel_deltp.get(s).get(p).get(0),"colz");
		for(int c = 0; c < 3; c++){
		    dir.addDataSet(m_pr_sect_panel_deltp.get(s).get(p).get(c));
		}
	    }
	    c_sp_deltp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/h_"+s_run_number+"_pr_"+Integer.toString(s)+"_panel_cutlvl0.png");

	}
	
	dir.mkdir("/proton/cutlvls/h2_pr_deltimep/");
	dir.cd("/proton/cutlvls/h2_pr_deltimep/");
	for( H2F h_temp : h2_pr_deltimep ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#delta time of flight");	    
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}


	F1D f_beta_cuttop = new F1D("f_betacut_top","x/sqrt(x*x + [a]*[a])",0.1,5.5);
	f_beta_cuttop.setParameter(0,PhysicalConstants.mass_proton*PhysicalConstants.mass_proton);	
	f_beta_cuttop.setLineColor(2);
				
	dir.mkdir("/proton/cutlvls/h2_pr_betap/");
	dir.cd("/proton/cutlvls/h2_pr_betap/");
	for( H2F h_temp : h2_pr_betap ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#beta");
	    c_temp.draw(h_temp,"colz");
	    c_temp.draw(f_beta_cuttop,"same");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///SECTOR BASED PLOTS

	dir.mkdir("/proton/cutlvls/h2_pr_sect_betap/");
	dir.cd("/proton/cutlvls/h2_pr_sect_betap/");
	for( int i = 0; i < h2_pr_sect_betap.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_betap.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
		//EmbeddedCanvas c_temp = new EmbeddedCanvas();
		//c_temp.setSize(800,800);
		//v_temp.get(j).setTitleX("p [GeV]");
		//v_temp.get(j).setTitleY("Total Energy (PCAL + ECAL ) [GeV]");
		//c_temp.draw(v_temp.get(j),"colz");
		//c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+v_temp.get(j).getTitle()+".png");
	    }
	}

	dir.mkdir("/proton/cutlvls/h2_pr_sect_deltabeta/");
	dir.cd("/proton/cutlvls/h2_pr_sect_deltabeta/");
	for( int i = 0; i < h2_pr_sect_deltabeta.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_deltabeta.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));

	    }
	}

	dir.mkdir("/proton/cutlvls/h2_pr_sect_deltimep/");
	dir.cd("/proton/cutlvls/h2_pr_sect_deltimep/");
	for( int i = 0; i < h2_pr_sect_deltimep.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_deltimep.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
	    }
	}	
    
	dir.mkdir("/proton/cutlvls/h_pr_sect_masstime/");
 	dir.cd("/proton/cutlvls/h_pr_sect_masstime/");
	for( int i = 0; i < h_pr_sect_masstime.size(); i++ ){
 	   Vector<H1F> v_temp = h_pr_sect_masstime.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
	    }
	}

	dir.mkdir("/proton/cutlvls/h2_pr_sect_masstimep/");
	dir.cd("/proton/cutlvls/h2_pr_sect_masstimep/");
	for( int i = 0; i < h2_pr_sect_masstimep.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_masstimep.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("Mass [GeV/c]");
		v_temp.get(j).setTitleY("p [GeV/c^2]"); 
		c_temp.draw(v_temp.get(j),"colz");
		c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+v_temp.get(j).getTitle()+".png");	       
		dir.addDataSet(v_temp.get(j));
	    }
	}


	dir.mkdir("/proton/cutlvls/h2_pr_sect_ectotp/");
	dir.cd("/proton/cutlvls/h2_pr_sect_ectotp/");
	for( int i = 0; i < h2_pr_sect_ectotp.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_ectotp.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("p [GeV]");
		v_temp.get(j).setTitleY("EC SF"); 
		c_temp.draw(v_temp.get(j),"colz");
		c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+v_temp.get(j).getTitle()+".png");	       
		dir.addDataSet(v_temp.get(j));
	    }
	}

	dir.mkdir("/proton/cutlvls/h_pr_sect_pcal_e/");
	dir.cd("/proton/cutlvls/h_pr_sect_pcal_e/");
	for( int i = 0; i < h_pr_sect_pcal_e.size(); i++ ){
 	   Vector<H1F> v_temp = h_pr_sect_pcal_e.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("PCAL [GeV]");
		c_temp.draw(v_temp.get(j));
		c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+v_temp.get(j).getTitle()+".png");	       
		dir.addDataSet(v_temp.get(j));
	    }
	}


	dir.mkdir("/proton/cutlvls/h_pr_sect_ei_e/");
	dir.cd("/proton/cutlvls/h_pr_sect_ei_e/");
	for( int i = 0; i < h_pr_sect_eical_e.size(); i++ ){
 	   Vector<H1F> v_temp = h_pr_sect_eical_e.get(i);       
	   for( int j = 0; j < v_temp.size(); j++){		
	       EmbeddedCanvas c_temp = new EmbeddedCanvas();
	       c_temp.setSize(800,800);
	       v_temp.get(j).setTitleX("EC EI [GeV]");
	       c_temp.draw(v_temp.get(j));
	       c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+v_temp.get(j).getTitle()+".png");	       
	       dir.addDataSet(v_temp.get(j));
	    }
	}

 
	dir.mkdir("/proton/cutlvls/h_pr_sect_eo_e/");
	dir.cd("/proton/cutlvls/h_pr_sect_eo_e/");
	for( int i = 0; i < h_pr_sect_eocal_e.size(); i++ ){
 	   Vector<H1F> v_temp = h_pr_sect_eocal_e.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
	       EmbeddedCanvas c_temp = new EmbeddedCanvas();
	       c_temp.setSize(800,800);
	       v_temp.get(j).setTitleX("EC EO [GeV]");
	       c_temp.draw(v_temp.get(j));
	       c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+v_temp.get(j).getTitle()+".png");	       
	       dir.addDataSet(v_temp.get(j));
	    }
	}

	dir.mkdir("/proton/cutlvls/h_pr_ftof_l2_masstime/");
	dir.cd("/proton/cutlvls/h_pr_ftof_l2_masstime/");
	for( H1F h_temp : h_pr_ftof_l2_masstime ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Mass [GeV/c]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h2_pr_ftof_l2_masstimep/");
	dir.cd("/proton/cutlvls/h2_pr_ftof_l2_masstimep/");
	for( H2F h_temp : h2_pr_ftof_l2_masstimep ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Mass [GeV/c]");
	    h_temp.setTitleY("p [GeV/c^2]");
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h2_pr_ftof_l2_deltimep/");
	dir.cd("/proton/cutlvls/h2_pr_ftof_l2_deltimep/");
	for( H2F h_temp : h2_pr_ftof_l2_deltimep ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#delta time of flight");	    
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h2_pr_ftof_l2_deltabeta/");
	dir.cd("/proton/cutlvls/h2_pr_ftof_l2_deltabeta/");
	for( H2F h_temp : h2_pr_ftof_l2_deltabeta ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#delta #beta");	    
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h2_pr_ftof_l2_betap/");
	dir.cd("/proton/cutlvls/h2_pr_ftof_l2_betap/");
	for( H2F h_temp : h2_pr_ftof_l2_betap ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#beta");
	    c_temp.draw(h_temp,"colz");
	    c_temp.draw(f_beta_cuttop,"same");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h2_pr_ftof_l2_tof/");
	dir.cd("/proton/cutlvls/h2_pr_ftof_l2_tof/");
	for( H2F h_temp : h2_pr_ftof_l2_tof ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("TOF [ns]");	    
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/cutlvls/h_pr_ftof_l2_beta_time/");
	dir.cd("/proton/cutlvls/h_pr_ftof_l2_beta_time/");
	for( H1F h_temp : h_pr_ftof_l2_beta_time ){
	    dir.addDataSet(h_temp);
	}


	dir.mkdir("/proton/cutlvls/h_pr_sect_ftof_l2_masstime/");
	dir.cd("/proton/cutlvls/h_pr_sect_ftof_l2_masstime/");
	for( int i = 0; i < h_pr_sect_ftof_l2_masstime.size(); i++ ){
 	   Vector<H1F> v_temp = h_pr_sect_ftof_l2_masstime.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
	    }
	}

	dir.mkdir("/proton/cutlvls/h2_pr_sect_ftof_l2_betap/");
	dir.cd("/proton/cutlvls/h2_pr_sect_ftof_l2_betap/");
	for( int i = 0; i < h2_pr_sect_ftof_l2_betap.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_ftof_l2_betap.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
		//EmbeddedCanvas c_temp = new EmbeddedCanvas();
		//c_temp.setSize(800,800);
		//v_temp.get(j).setTitleX("p [GeV]");
		//v_temp.get(j).setTitleY("Total Energy (PCAL + ECAL ) [GeV]");
		//c_temp.draw(v_temp.get(j),"colz");
		//c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+v_temp.get(j).getTitle()+".png");
	    }
	}

	dir.mkdir("/proton/cutlvls/h2_pr_sect_ftof_l2_deltabeta/");
	dir.cd("/proton/cutlvls/h2_pr_sect_ftof_l2_deltabeta/");
	for( int i = 0; i < h2_pr_sect_ftof_l2_deltabeta.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_ftof_l2_deltabeta.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));

	    }
	}

	dir.mkdir("/proton/cutlvls/h2_pr_sect_ftof_l2_deltimep/");
	dir.cd("/proton/cutlvls/h2_pr_sect_ftof_l2_deltimep/");
	for( int i = 0; i < h2_pr_sect_ftof_l2_deltimep.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_ftof_l2_deltimep.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
	    }
	}	

	dir.mkdir("/proton/cutlvls/h2_pr_sect_ftof_l2_masstimep/");
	dir.cd("/proton/cutlvls/h2_pr_sect_ftof_l2_masstimep/");
	for( int i = 0; i < h2_pr_sect_ftof_l2_masstimep.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_ftof_l2_masstimep.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("Mass [GeV/c]");
		v_temp.get(j).setTitleY("p [GeV/c^2]"); 
		c_temp.draw(v_temp.get(j),"colz");
		c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+v_temp.get(j).getTitle()+".png");	       
		dir.addDataSet(v_temp.get(j));
	    }
	}


    }

    public void viewHipoOut(){

	//dir.readFile("/u/home/bclary/CLAS12/phi_analysis/v2/v1/testing_cutlvl.hipo");
	TBrowser browser = new TBrowser(dir);

	//dir.ls();
	
    }

    
}
