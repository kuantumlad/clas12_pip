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

public class BPIDKaonPlusHistograms {

    int run_number = -1;
    String s_run_number = " ";
    public BPIDKaonPlusHistograms(int temp_run) {
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
    double min_delt = -1.57; double max_delt = 1.57;
    double min_delb = -1.0; double max_delb = 1.0;
    double min_b = 0.0; double max_b = 1.4;
    double min_rpath = 300.0; double max_rpath = 750.0; 
    double min_tof = 15.0; double max_tof = 35.0;

    Vector<H1F> h_kp_p = new Vector<H1F>(); 
    Vector<H1F> h_kp_theta = new Vector<H1F>(); 
    Vector<H1F> h_kp_phi = new Vector<H1F>(); 
    Vector<H1F> h_kp_vz = new Vector<H1F>(); 
    Vector<H1F> h_kp_timing = new Vector<H1F>(); 
    Vector<H1F> h_kp_masstime = new Vector<H1F>();

    Vector<H2F> h2_kp_deltimep = new Vector<H2F>(); 
    Vector<H2F> h2_kp_deltabeta = new Vector<H2F>();
    Vector<H2F> h2_kp_betap = new Vector<H2F>();
    Vector<H2F> h2_kp_thetap = new Vector<H2F>();
    Vector<H2F> h2_kp_masstimep = new Vector<H2F>();

    Vector<H1F> h_kp_rpath = new Vector<H1F>();
    Vector<H2F> h2_kp_tof = new Vector<H2F>();
    Vector<H1F> h_kp_beta_time = new Vector<H1F>();
    Vector<H1F> h_kp_beta_mntm = new Vector<H1F>();


    Vector<Vector<H2F>> h2_kp_sect_betap = new Vector< Vector<H2F> >();
    Vector<Vector<H2F>> h2_kp_sect_deltabeta = new Vector<Vector<H2F> >();
    Vector<Vector<H2F>> h2_kp_sect_deltimep = new Vector<Vector<H2F> >();
    
    Vector<H2F> h2_kp_dchit_R1_xy = new Vector<H2F>();
    Vector<H2F> h2_kp_dchit_R2_xy = new Vector<H2F>();
    Vector<H2F> h2_kp_dchit_R3_xy = new Vector<H2F>();

    Vector<H2F> h2_kp_dchit_R1_gxy = new Vector<H2F>();
    Vector<H2F> h2_kp_dchit_R2_gxy = new Vector<H2F>();
    Vector<H2F> h2_kp_dchit_R3_gxy = new Vector<H2F>();

    Vector<H2F> h2_kp_ectotp = new Vector<H2F>();
    Vector<H1F> h_kp_ftof_l1_e = new Vector<H1F>();
    Vector<H1F> h_kp_ftof_l2_e = new Vector<H1F>();
    Vector<H1F> h_kp_pcal_e = new Vector<H1F>();
    Vector<H1F> h_kp_eical_e = new Vector<H1F>();
    Vector<H1F> h_kp_eocal_e = new Vector<H1F>();

    Vector<Vector<H1F>> h_kp_sect_p = new Vector< Vector<H1F> >();
    Vector<Vector<H1F>> h_kp_sect_theta = new Vector< Vector<H1F> >();
    Vector<Vector<H1F>> h_kp_sect_phi = new Vector< Vector<H1F> >();
    Vector<Vector<H1F>> h_kp_sect_masstime = new Vector<Vector<H1F> >();
    Vector<Vector<H1F>> h_kp_sect_ftof_l2_masstime = new Vector<Vector<H1F> >();

    Vector<Vector<H2F>> h2_kp_sect_ectotp = new Vector<Vector<H2F> >();
    Vector<Vector<H1F>> h_kp_sect_pcal_e = new Vector<Vector<H1F> >();
    Vector<Vector<H1F>> h_kp_sect_ftof_l1_e = new Vector<Vector<H1F> >();
    Vector<Vector<H1F>> h_kp_sect_ftof_l2_e = new Vector<Vector<H1F> >();
    Vector<Vector<H1F>> h_kp_sect_eical_e = new Vector<Vector<H1F> >();
    Vector<Vector<H1F>> h_kp_sect_eocal_e = new Vector<Vector<H1F> >();

    Vector<Vector<H2F>> h2_kp_sect_vztheta = new Vector< Vector<H2F> >();
    Vector<Vector<H2F>> h2_kp_sect_masstimep = new Vector<Vector<H2F> >();

    //////////////////////////////////////////////////////////////////////////
    //LAYERS FTOF 2 PLOTS
    
    Vector<H2F> h2_kp_ftof_l2_masstimep = new Vector<H2F>();
    Vector<H2F> h2_kp_ftof_l2_vzphi = new Vector< H2F >();
    Vector<H2F> h2_kp_ftof_l2_deltimep = new Vector<H2F>(); 
    Vector<H2F> h2_kp_ftof_l2_deltabeta = new Vector<H2F>();
    Vector<H2F> h2_kp_ftof_l2_betap = new Vector<H2F>();

    Vector<H2F> h2_kp_ftof_l2_tof = new Vector<H2F>();
    Vector<H1F> h_kp_ftof_l2_beta_time = new Vector<H1F>();
    Vector<H1F> h_kp_ftof_l2_masstime = new Vector<H1F>();

    Vector<Vector<H2F>> h2_kp_sect_ftof_l2_betap = new Vector< Vector<H2F> >();
    Vector<Vector<H2F>> h2_kp_sect_ftof_l2_deltabeta = new Vector<Vector<H2F> >();
    Vector<Vector<H2F>> h2_kp_sect_ftof_l2_deltimep = new Vector<Vector<H2F> >();    
    Vector<Vector<H2F>> h2_kp_sect_ftof_l2_vztheta = new Vector< Vector<H2F> >();
    Vector<Vector<H2F>> h2_kp_sect_ftof_l2_masstimep = new Vector<Vector<H2F> >();


    Vector<Vector<H2F>> h_kp_sect_panel_deltp = new Vector< Vector<H2F> >();   
    Vector< Vector< Vector< H2F > > > h_kp_sect_panel_deltimep = new Vector< Vector< Vector<H2F> > >(); 

    public BPIDKaonPlusHistograms() {
	//constructor
    }

    public void createKaonPHistograms(int i){

	h_kp_p.add( new H1F("h_"+s_run_number+"_kp_p_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_p_cutlvl"+Integer.toString(i), 100, min_p, max_p ) );
	h_kp_theta.add( new H1F("h_"+s_run_number+"_kp_theta_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_theta_cutlvl"+Integer.toString(i), 100, min_theta, max_theta) );
	h_kp_phi.add( new H1F("h_"+s_run_number+"_kp_phi_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_phi_cutlvl"+Integer.toString(i), 100, min_phi, max_phi ) );
	h_kp_vz.add( new H1F("h_"+s_run_number+"_kp_vz_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_vz_cutlvl"+Integer.toString(i), 100, min_vz, max_vz ) );
	h_kp_timing.add( new H1F("h_"+s_run_number+"_kp_timing_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_timing_cutlvl"+Integer.toString(i), 100, min_timing, max_timing ) );
	h_kp_rpath.add( new H1F("h_"+s_run_number+"_kp_rpath_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_rpath_cutlvl"+Integer.toString(i), 300, min_rpath, max_rpath ) );
	h_kp_beta_time.add( new H1F("h_"+s_run_number+"_kp_betatime_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_betatime_cutlvl"+Integer.toString(i), 100, min_b, max_b ) );
	h_kp_beta_mntm.add( new H1F("h_"+s_run_number+"_kp_betamntm_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_betamntm_cutlvl"+Integer.toString(i), 100, min_b, max_b ) );
	h2_kp_tof.add( new H2F("h2_"+s_run_number+"_kp_tof_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_tof_cutlvl"+Integer.toString(i), 200, min_p, max_p, 200, min_tof, max_tof ) );
	
	h2_kp_deltimep.add( new H2F("h2_"+s_run_number+"_kp_deltimep_cutlvl"+Integer.toString(i),"h2"+s_run_number+"__kp_deltimep_cutlvl"+Integer.toString(i),200, min_p, max_p, 200, min_delt, max_delt ));
	h2_kp_betap.add( new H2F("h2_"+s_run_number+"_kp_betap_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_betap_cutlvl"+Integer.toString(i),200, min_p, max_p, 200, min_b, max_b ));
	h2_kp_deltabeta.add( new H2F("h2_"+s_run_number+"_kp_deltabeta_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_deltabeta_cutlvl"+Integer.toString(i),200, min_p, max_p, 200, min_delb, max_delb ));

	h2_kp_thetap.add( new H2F("h2_"+s_run_number+"_kp_thetap_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_thetap_cutlvl"+Integer.toString(i),200, min_p, max_p, 200, min_theta, max_theta));


 	h2_kp_dchit_R1_xy.add( new H2F("h2_"+s_run_number+"_kp_dchit_R1_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_dchit_R1_cutlvl"+Integer.toString(i), 500, -95.0, 95.0, 500, -100.0, 100.0 ));
 	h2_kp_dchit_R2_xy.add( new H2F("h2_"+s_run_number+"_kp_dchit_R2_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_dchit_R2_cutlvl"+Integer.toString(i), 500, -150.0, 150.0, 500, -150.0, 150.0 ));
 	h2_kp_dchit_R3_xy.add( new H2F("h2_"+s_run_number+"_kp_dchit_R3_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_dchit_R3_cutlvl"+Integer.toString(i), 500, -200.0, 200.0, 500, -150.0, 150.0 ));
 	h2_kp_masstimep.add( new H2F("h2_"+s_run_number+"_kp_masstimep_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_masstimep_cutlvl"+Integer.toString(i), 500, 0.0, 1.2, 500, min_p, max_p ));
 	h_kp_masstime.add( new H1F("h_"+s_run_number+"_kp_masstime_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_masstime_cutlvl"+Integer.toString(i), 500, 0.0, 2.0 ));


 	h2_kp_ectotp.add( new H2F("h2_"+s_run_number+"_kp_ectotp_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_ectotp_cutlvl"+Integer.toString(i), 400, 0.0, 4.0, 400, 0.0, 0.5 ));
 	h_kp_pcal_e.add( new H1F("h_"+s_run_number+"_kp_pcal_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_pcal_e_cutlvl"+Integer.toString(i), 250, 0.0, 0.0070 ));
	h_kp_ftof_l1_e.add( new H1F("h_"+s_run_number+"_kp_ftof_l1_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_ftof_l1_e_cutlvl"+Integer.toString(i), 250, 0.0, 0.50 ));
	h_kp_ftof_l2_e.add( new H1F("h_"+s_run_number+"_kp_ftof_l2_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_ftof_l2_e_cutlvl"+Integer.toString(i), 250, 0.0, 0.50 ));	
 	h_kp_eical_e.add( new H1F("h_"+s_run_number+"_kp_eical_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_eical_e_cutlvl"+Integer.toString(i), 250, 0.0, 0.50 ));
 	h_kp_eocal_e.add( new H1F("h_"+s_run_number+"_kp_eocal_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_eocal_e_cutlvl"+Integer.toString(i), 250, 0.0, 0.50 ));

	h2_kp_ftof_l2_tof.add( new H2F("h2_"+s_run_number+"_kp_ftof_l2_tof_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_ftof_l2_tof_cutlvl"+Integer.toString(i), 500, min_p, max_p, 500, min_tof, max_tof ) );
	h_kp_ftof_l2_beta_time.add( new H1F("h_"+s_run_number+"_kp_ftof_l2_betatime_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_ftof_l2_betatime_cutlvl"+Integer.toString(i), 400, min_b, max_b ) );
 	h2_kp_ftof_l2_masstimep.add( new H2F("h2_"+s_run_number+"_kp_ftof_l2_masstimep_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_ftof_l2_masstimep_cutlvl"+Integer.toString(i), 500, 0.0, 1.2, 500, min_p, max_p ));
 	h_kp_ftof_l2_masstime.add( new H1F("h_"+s_run_number+"_kp_ftof_l2_masstime_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_ftof_l2_masstime_cutlvl"+Integer.toString(i), 500, 0.0, 2.0 ));
	h2_kp_ftof_l2_deltabeta.add( new H2F("h2_"+s_run_number+"_kp_ftof_l2_deltabeta_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_ftof_l2_deltabeta_cutlvl"+Integer.toString(i), 500, min_p, max_p, 500, min_delb, max_delb ));
	h2_kp_ftof_l2_betap.add( new H2F("h2_"+s_run_number+"_kp_ftof_l2_betap_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_ftof_l2_betap_cutlvl"+Integer.toString(i),500, min_p, max_p, 500, min_b, max_b ));
	h2_kp_ftof_l2_deltimep.add( new H2F("h2_"+s_run_number+"_kp_ftof_l2_deltimep_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_ftof_l2_deltimep_cutlvl"+Integer.toString(i),500, min_p, max_p, 500, min_delt, max_delt ));


    }

    public void createKaonPSectorHistograms( int sector, int max_cuts ){
	h_kp_sect_p.add( new Vector<H1F>() );
 	h_kp_sect_theta.add( new Vector<H1F>() );
	h_kp_sect_phi.add( new Vector<H1F>() );
	h2_kp_sect_betap.add( new Vector<H2F>() );
	h2_kp_sect_deltabeta.add( new Vector<H2F>() );
	h2_kp_sect_deltimep.add( new Vector<H2F>() );

	h_kp_sect_masstime.add( new Vector<H1F>() );
	h2_kp_sect_masstimep.add( new Vector<H2F>() );

	h2_kp_sect_ectotp.add( new Vector<H2F>() );
	h_kp_sect_pcal_e.add( new Vector<H1F>() );
	h_kp_sect_ftof_l1_e.add( new Vector<H1F>() );
	h_kp_sect_ftof_l2_e.add( new Vector<H1F>() );	
	h_kp_sect_eical_e.add( new Vector<H1F>() );
	h_kp_sect_eocal_e.add( new Vector<H1F>() );

	h_kp_sect_ftof_l2_masstime.add( new Vector<H1F>() );
	h2_kp_sect_ftof_l2_betap.add( new Vector<H2F>() );
	h2_kp_sect_ftof_l2_deltabeta.add( new Vector<H2F>() );
	h2_kp_sect_ftof_l2_deltimep.add( new Vector<H2F>() );
	h2_kp_sect_ftof_l2_masstimep.add( new Vector<H2F>() );
	
	for( int i = 0; i <= max_cuts; i++ ){
	    (h_kp_sect_p.get(sector)).add( new H1F("h_kp_" + Integer.toString(sector) + "_p_cutlvl"+Integer.toString(i),"h_kp_" + Integer.toString(sector)  + "_p_cutlvl"+Integer.toString(i), 300, min_p, max_p ) );  
	    (h2_kp_sect_betap.get(sector)).add( new H2F("h2_kp_" + Integer.toString(sector) + "_betap_cutlvl"+Integer.toString(i),"h2_kp_" + Integer.toString(sector)  + "_betap_cutlvl"+Integer.toString(i), 200, min_p, max_p, 200, min_b, max_b ) );  
	    (h2_kp_sect_deltabeta.get(sector)).add( new H2F("h2_kp_" + Integer.toString(sector) + "_deltabeta_cutlvl"+Integer.toString(i),"h2_kp_" + Integer.toString(sector)  + "_deltabeta_cutlvl"+Integer.toString(i), 200, min_p, max_p, 200, min_delb, max_delb ) );  
	    (h2_kp_sect_deltimep.get(sector)).add( new H2F("h2_kp_" + Integer.toString(sector) + "_deltimep_cutlvl"+Integer.toString(i),"h2_kp_" + Integer.toString(sector)  + "_deltimep_cutlvl"+Integer.toString(i), 200, min_p, max_p, 200, min_delt, max_delt ) );  


	    (h_kp_sect_masstime.get(sector)).add( new H1F("h_"+s_run_number+"_kp_" + Integer.toString(sector) + "_masstime_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_masstime_cutlvl"+Integer.toString(i), 550, 0.0 , 2.0) );  
	    (h2_kp_sect_masstimep.get(sector)).add( new H2F("h2_"+s_run_number+"_kp_" + Integer.toString(sector) + "_masstimep_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_masstimep_cutlvl"+Integer.toString(i), 500, 0.0, 1.2, 500, min_p, max_p ) );  

	    (h2_kp_sect_ectotp.get(sector)).add( new H2F("h2_"+s_run_number+"_kp_" + Integer.toString(sector) + "_ectotp_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_ectotp_cutlvl"+Integer.toString(i), 400, 0.0, 4.0, 400, 0.0, 0.5 ) );  
	    (h_kp_sect_pcal_e.get(sector)).add( new H1F("h_"+s_run_number+"_kp_" + Integer.toString(sector) + "_pcal_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_pcal_e_cutlvl"+Integer.toString(i), 400, 0.0, 0.0080 ) );  
	    (h_kp_sect_ftof_l1_e.get(sector)).add( new H1F("h_"+s_run_number+"_kp_" + Integer.toString(sector) + "_ftof_l1_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_ftof_l1_e_cutlvl"+Integer.toString(i), 400, 0.0, 0.50 ) );  
	    (h_kp_sect_ftof_l2_e.get(sector)).add( new H1F("h_"+s_run_number+"_kp_" + Integer.toString(sector) + "_ftof_l2_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_ftof_l2_e_cutlvl"+Integer.toString(i), 400, 0.0, 0.50 ) );  
	    (h_kp_sect_eical_e.get(sector)).add( new H1F("h_"+s_run_number+"_kp_" + Integer.toString(sector) + "_eical_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_eical_e_cutlvl"+Integer.toString(i), 400, 0.0, 0.50 ) );  
	    (h_kp_sect_eocal_e.get(sector)).add( new H1F("h_"+s_run_number+"_kp_" + Integer.toString(sector) + "_eocal_e_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_eocal_e_cutlvl"+Integer.toString(i), 400, 0.0, 0.50 ) );  


	    (h_kp_sect_ftof_l2_masstime.get(sector)).add( new H1F("h_"+s_run_number+"_kp_" + Integer.toString(sector) + "_ftof_l2_masstime_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_ftof_l2_masstime_cutlvl"+Integer.toString(i), 500, 0.0 , 2.0) );  	    	    
	    (h2_kp_sect_ftof_l2_betap.get(sector)).add( new H2F("h2_"+s_run_number+"_kp_" + Integer.toString(sector) + "_ftof_l2_betap_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_ftof_l2_betap_cutlvl"+Integer.toString(i), 500, min_p, max_p, 500, min_b, max_b ) );
	    (h2_kp_sect_ftof_l2_deltabeta.get(sector)).add( new H2F("h2_"+s_run_number+"_kp_" + Integer.toString(sector) + "_ftof_l2_deltabeta_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_ftof_l2_deltabeta_cutlvl"+Integer.toString(i), 500, min_p, max_p, 500, min_delb, max_delb ) );  
	    (h2_kp_sect_ftof_l2_deltimep.get(sector)).add( new H2F("h2_"+s_run_number+"_kp_" + Integer.toString(sector) + "_ftof_l2_deltimep_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_ftof_l2_deltimep_cutlvl"+Integer.toString(i), 500, min_p, max_p, 500, min_delt, max_delt ) );  
	    (h2_kp_sect_ftof_l2_masstimep.get(sector)).add( new H2F("h2_"+s_run_number+"_kp_" + Integer.toString(sector) + "_ftof_l2_masstimep_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_ftof_l2_masstimep_cutlvl"+Integer.toString(i), 500, 0.0, 1.2, 500, min_p, max_p ) );  


	}
    }


    public void kaonpHistoToHipo(){

	dir.mkdir("/kp/cutlvls/h_kp_p/");
	dir.cd("/kp/cutlvls/h_kp_p/");
	for( H1F h_temp : h_kp_p ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kp/cutlvls/h_kp_thetap/");
	dir.cd("/kp/cutlvls/h_kp_thetap/");
	for( H2F h_temp : h2_kp_thetap ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("TOF [ns]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kp/cutlvls/h2_kp_tof/");
	dir.cd("/kp/cutlvls/h2_kp_tof/");
	for( H2F h_temp : h2_kp_tof ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("TOF [ns]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kp/cutlvls/h_kp_rpath/");
	dir.cd("/kp/cutlvls/h_kp_rpath/");
	for( H1F h_temp : h_kp_rpath ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kp/cutlvls/h_kp_beta_time/");
	dir.cd("/kp/cutlvls/h_kp_beta_time/");
	for( H1F h_temp : h_kp_beta_time ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("#beta_{clas12}");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kp/cutlvls/h_kp_beta_mntm/");
	dir.cd("/kp/cutlvls/h_kp_beta_mntm/");
	for( H1F h_temp : h_kp_beta_mntm ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kp/cutlvls/h2_kp_deltabeta/");
	dir.cd("/kp/cutlvls/h2_kp_deltabeta/");
	for( H2F h_temp : h2_kp_deltabeta ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#delta #beta");	    
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}
    	
	dir.mkdir("/kp/cutlvls/h2_kp_deltimep/");
	dir.cd("/kp/cutlvls/h2_kp_deltimep/");
	for( H2F h_temp : h2_kp_deltimep ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#delta time of flight");	    
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}



	dir.mkdir("/kp/cutlvls/h2_kp_sect_betap/");
	dir.cd("/kp/cutlvls/h2_kp_sect_betap/");
	for( int i = 0; i < h2_kp_sect_betap.size(); i++ ){
 	   Vector<H2F> v_temp = h2_kp_sect_betap.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("p [GeV]");
		v_temp.get(j).setTitleY("#beta");	    
		c_temp.draw(v_temp.get(j),"colz");
		c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+v_temp.get(j).getTitle()+".png");		
	    }
	}

	dir.mkdir("/kp/cutlvls/h2_kp_sect_deltabeta/");
	dir.cd("/kp/cutlvls/h2_kp_sect_deltabeta/");
	for( int i = 0; i < h2_kp_sect_deltabeta.size(); i++ ){
 	   Vector<H2F> v_temp = h2_kp_sect_deltabeta.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("p [GeV]");
		v_temp.get(j).setTitleY("#Delta #beta");	    
		c_temp.draw(v_temp.get(j),"colz");
		c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+v_temp.get(j).getTitle()+".png");		
	    }
	}

	dir.mkdir("/kp/cutlvls/h_kp_masstime/");
	dir.cd("/kp/cutlvls/h_kp_masstime/");
	for( H1F h_temp : h_kp_masstime ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Mass [GeV/c]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kp/cutlvls/h2_kp_masstimep/");
	dir.cd("/kp/cutlvls/h2_kp_masstimep/");
	for( H2F h_temp : h2_kp_masstimep ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Mass [GeV/c]");
	    h_temp.setTitleY("p [GeV/c^2]");
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kp/cutlvls/h2_kp_dchit_R1_xy/");
	dir.cd("/kp/cutlvls/h2_kp_dchit_R1_xy/");
	for( H2F h_temp : h2_kp_dchit_R1_xy){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("DC X [cm]");
	    h_temp.setTitleX("DC Y [cm]"); 
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}
	dir.mkdir("/kp/cutlvls/h2_kp_dchit_R2_xy/");
	dir.cd("/kp/cutlvls/h2_kp_dchit_R2_xy/");
	for( H2F h_temp : h2_kp_dchit_R2_xy){
	    dir.addDataSet(h_temp);
	}
	dir.mkdir("/kp/cutlvls/h2_kp_dchit_R3_xy/");
	dir.cd("/kp/cutlvls/h2_kp_dchit_R3_xy/");
	for( H2F h_temp : h2_kp_dchit_R3_xy){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("DC X [cm]");
	    h_temp.setTitleX("DC Y [cm]"); 
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kp/cutlvls/h2_kp_ectotp/");
	dir.cd("/kp/cutlvls/h2_kp_ectotp/");
	for( H2F h_temp : h2_kp_ectotp){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("EC SF"); 
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kp/cutlvls/h_kp_pcal_e/");
	dir.cd("/kp/cutlvls/h_kp_pcal_e/");
	for( H1F h_temp : h_kp_pcal_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("PCAL Energy [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kp/cutlvls/h_kp_ftof_l1_e/");
	dir.cd("/kp/cutlvls/h_kp_ftof_l1_e/");
	for( H1F h_temp : h_kp_ftof_l1_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("FTOF L1 Energy [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}


	dir.mkdir("/kp/cutlvls/h_kp_ftof_l2_e/");
	dir.cd("/kp/cutlvls/h_kp_ftof_l2_e/");
	for( H1F h_temp : h_kp_ftof_l2_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("FTOF L2 Energy [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kp/cutlvls/h_kp_eical_e/");
	dir.cd("/kp/cutlvls/h_kp_eical_e/");
	for( H1F h_temp : h_kp_eical_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("ECAL EI [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kp/cutlvls/h_kp_eocal_e/");
	dir.cd("/kp/cutlvls/h_kp_eocal_e/");
	for( H1F h_temp : h_kp_eocal_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("ECAL EO [GeV]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	F1D f_beta_cuttop = new F1D("f_betacut_top","x/sqrt(x*x + [a]*[a])",0.1,4.5);
	f_beta_cuttop.setParameter(0,PhysicalConstants.mass_kaon*PhysicalConstants.mass_kaon);	
	f_beta_cuttop.setLineColor(2);
				
 	dir.mkdir("/kp/cutlvls/h2_kp_betap/");
	dir.cd("/kp/cutlvls/h2_kp_betap/");
	for( H2F h_temp : h2_kp_betap ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#beta");
	    c_temp.draw(h_temp,"colz");
	    c_temp.draw(f_beta_cuttop,"same");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kp/cutlvls/h_kp_sect_masstime/");
 	dir.cd("/kp/cutlvls/h_kp_sect_masstime/");
	for( int i = 0; i < h_kp_sect_masstime.size(); i++ ){
 	   Vector<H1F> v_temp = h_kp_sect_masstime.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
	    }
	}

	dir.mkdir("/kp/cutlvls/h2_kp_sect_masstimep/");
	dir.cd("/kp/cutlvls/h2_kp_sect_masstimep/");
	for( int i = 0; i < h2_kp_sect_masstimep.size(); i++ ){
 	   Vector<H2F> v_temp = h2_kp_sect_masstimep.get(i);       
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


	dir.mkdir("/kp/cutlvls/h2_kp_sect_ectotp/");
	dir.cd("/kp/cutlvls/h2_kp_sect_ectotp/");
	for( int i = 0; i < h2_kp_sect_ectotp.size(); i++ ){
 	   Vector<H2F> v_temp = h2_kp_sect_ectotp.get(i);       
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

	dir.mkdir("/kp/cutlvls/h_kp_sect_pcal_e/");
	dir.cd("/kp/cutlvls/h_kp_sect_pcal_e/");
	for( int i = 0; i < h_kp_sect_pcal_e.size(); i++ ){
 	   Vector<H1F> v_temp = h_kp_sect_pcal_e.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("PCAL [GeV]");
		c_temp.draw(v_temp.get(j));
		c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+v_temp.get(j).getTitle()+".png");	       
		dir.addDataSet(v_temp.get(j));
	    }
	}


	dir.mkdir("/kp/cutlvls/h_kp_sect_ei_e/");
	dir.cd("/kp/cutlvls/h_kp_sect_ei_e/");
	for( int i = 0; i < h_kp_sect_eical_e.size(); i++ ){
 	   Vector<H1F> v_temp = h_kp_sect_eical_e.get(i);       
	   for( int j = 0; j < v_temp.size(); j++){		
	       EmbeddedCanvas c_temp = new EmbeddedCanvas();
	       c_temp.setSize(800,800);
	       v_temp.get(j).setTitleX("EC EI [GeV]");
	       c_temp.draw(v_temp.get(j));
	       c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+v_temp.get(j).getTitle()+".png");	       
	       dir.addDataSet(v_temp.get(j));
	    }
	}

 
	dir.mkdir("/kp/cutlvls/h_kp_sect_eo_e/");
	dir.cd("/kp/cutlvls/h_kp_sect_eo_e/");
	for( int i = 0; i < h_kp_sect_eocal_e.size(); i++ ){
 	   Vector<H1F> v_temp = h_kp_sect_eocal_e.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
	       EmbeddedCanvas c_temp = new EmbeddedCanvas();
	       c_temp.setSize(800,800);
	       v_temp.get(j).setTitleX("EC EO [GeV]");
	       c_temp.draw(v_temp.get(j));
	       c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+v_temp.get(j).getTitle()+".png");	       
	       dir.addDataSet(v_temp.get(j));
	    }
	}

	dir.mkdir("/kp/cutlvls/h_kp_ftof_l2_masstime/");
	dir.cd("/kp/cutlvls/h_kp_ftof_l2_masstime/");
	for( H1F h_temp : h_kp_ftof_l2_masstime ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Mass [GeV/c]");
	    c_temp.draw(h_temp);
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kp/cutlvls/h2_kp_ftof_l2_masstimep/");
	dir.cd("/kp/cutlvls/h2_kp_ftof_l2_masstimep/");
	for( H2F h_temp : h2_kp_ftof_l2_masstimep ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Mass [GeV/c]");
	    h_temp.setTitleY("p [GeV/c^2]");
	    c_temp.draw(h_temp,"colz");
	    c_temp.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/"+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}


    }

    public void viewHipoOut(){

	TBrowser browser = new TBrowser(dir);

    }

}
