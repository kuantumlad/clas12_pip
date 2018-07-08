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


public class BMLEKaonHistograms {

    int run_number = -1;
    String s_run_number = " ";
    String n_thread = null;
    public BMLEKaonHistograms(int temp_run, String temp_thread) {
	//constructor
	run_number = temp_run;	
	s_run_number = Integer.toString(run_number);
	n_thread = temp_thread;
    }

    TDirectory dir = new TDirectory();
    String savepath = "/home/bclary/CLAS12/pics/pid_clary/";

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

    Vector<H1F> h_kp_p = new Vector<H1F>(); 
    Vector<H1F> h_kp_theta = new Vector<H1F>(); 
    Vector<H1F> h_kp_phi = new Vector<H1F>(); 
    Vector<H1F> h_kp_vz = new Vector<H1F>(); 
    Vector<H1F> h_kp_vz_mod = new Vector<H1F>(); 
    Vector<H1F> h_kp_timing = new Vector<H1F>(); 
    Vector<H1F> h_kp_masstime = new Vector<H1F>();
    Vector<H1F> h_kp_conflvl = new Vector<H1F>();

    Vector<H2F> h2_kp_masstimep = new Vector<H2F>();
    Vector<H2F> h2_kp_vzphi = new Vector< H2F >();
    Vector<H2F> h2_kp_ptheta = new Vector< H2F >();
    Vector<H2F> h2_kp_pphi = new Vector< H2F >();
    Vector<H2F> h2_kp_deltimep = new Vector<H2F>(); 
    Vector<H2F> h2_kp_deltabeta = new Vector<H2F>();
    Vector<H2F> h2_kp_betap = new Vector<H2F>();

    Vector<H1F> h_kp_rpath = new Vector<H1F>();
    Vector<H2F> h2_kp_tof = new Vector<H2F>();
    Vector<H1F> h_kp_beta_time = new Vector<H1F>();
    Vector<H1F> h_kp_beta_mntm = new Vector<H1F>();

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

    Vector<Vector<H2F>> h2_kp_sect_betap = new Vector< Vector<H2F> >();
    Vector<Vector<H2F>> h2_kp_el_sect_betap = new Vector< Vector<H2F> >();
    Vector<Vector<H2F>> h2_kp_sect_deltabeta = new Vector<Vector<H2F> >();
    Vector<Vector<H2F>> h2_kp_sect_deltimep = new Vector<Vector<H2F> >();    
    Vector<Vector<H2F>> h2_kp_sect_vztheta = new Vector< Vector<H2F> >();
    Vector<Vector<H2F>> h2_kp_sect_masstimep = new Vector<Vector<H2F> >();

    //////////////////////////////////////////////////////////////////////////
    //LAYERS FTOF 2 PLOTS


    H2F h_beta_all_pos;
    H1F h_test_phi;    
    
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

    HashMap<Integer, Vector< Vector<H2F> > > m_kp_sect_panel_deltp = new HashMap<Integer, Vector< Vector< H2F> > >();

    /* KAON MINUS HISTOGRAMS */
    Vector<H1F> h_km_p = new Vector<H1F>(); 
    Vector<H1F> h_km_theta = new Vector<H1F>(); 
    Vector<H1F> h_km_phi = new Vector<H1F>(); 
    Vector<H1F> h_km_vz = new Vector<H1F>(); 
    Vector<H1F> h_km_vz_mod = new Vector<H1F>(); 
    Vector<H1F> h_km_timing = new Vector<H1F>(); 
    Vector<H1F> h_km_masstime = new Vector<H1F>();
    Vector<H1F> h_km_conflvl = new Vector<H1F>();

    Vector<H2F> h2_km_betap = new Vector<H2F>();
    Vector<H2F> h2_km_vzphi = new Vector< H2F >();
    Vector<H2F> h2_km_ptheta = new Vector< H2F >();
    Vector<H2F> h2_km_pphi = new Vector< H2F >();

    Vector<H2F> h2_km_ftof_l2_betap = new Vector<H2F>();

    /* STUPIDLY MAKING THE PION CONF LEVEL PLOTS HERE " */
    Vector<H1F> h_pip_conflvl = new Vector<H1F>();
    Vector<H1F> h_pim_conflvl = new Vector<H1F>();


    public void createKaonMLEHistograms(){

	int i = 0;
	h_beta_all_pos = new H2F("h_beta_all_pos","h_beta_all_pos",250,0.0,6.5, 250, 0.01, 1.2);
	h_test_phi = new H1F("h_test_phi","h_test_phi",100,0.5,1.3);

	h_kp_p.add( new H1F("h_"+s_run_number+"_kp_p_mle","h_"+s_run_number+"_kp_p_mle", 100, min_p, max_p ) );
	h_kp_theta.add( new H1F("h_"+s_run_number+"_kp_theta_mle","h_"+s_run_number+"_kp_theta_mle", 100, min_theta, max_theta) );
	h_kp_phi.add( new H1F("h_"+s_run_number+"_kp_phi_mle","h_"+s_run_number+"_kp_phi_mle", 100, min_phi, max_phi ) );
	h_kp_vz.add( new H1F("h_"+s_run_number+"_kp_vz_mle","h_"+s_run_number+"_kp_vz_mle", 100, min_vz, max_vz ) );
	h_kp_vz_mod.add( new H1F("h_"+s_run_number+"_kp_vzmod_mle","h_"+s_run_number+"_kp_vzmod_mle", 100, min_vz, max_vz ) );
	h_kp_timing.add( new H1F("h_"+s_run_number+"_kp_timing_mle","h_"+s_run_number+"_kp_timing_mle"+Integer.toString(i), 100, min_timing, max_timing ) );

	h_km_p.add( new H1F("h_"+s_run_number+"_km_p_mle","h_"+s_run_number+"_km_p_mle", 100, min_p, max_p ) );
	h_km_theta.add( new H1F("h_"+s_run_number+"_km_theta_mle","h_"+s_run_number+"_km_theta_mle", 100, min_theta, max_theta) );
	h_km_phi.add( new H1F("h_"+s_run_number+"_km_phi_mle","h_"+s_run_number+"_km_phi_mle", 100, min_phi, max_phi ) );
	h_km_vz.add( new H1F("h_"+s_run_number+"_km_vz_mle","h_"+s_run_number+"_km_vz_mle", 100, min_vz, max_vz ) );
	h_km_vz_mod.add( new H1F("h_"+s_run_number+"_km_vzmod_mle","h_"+s_run_number+"_km_vzmod_mle", 100, min_vz, max_vz ) );
	h_km_timing.add( new H1F("h_"+s_run_number+"_km_timing_mle","h_"+s_run_number+"_km_timing_mle"+Integer.toString(i), 100, min_timing, max_timing ) );
	h2_km_betap.add( new H2F("h2_"+s_run_number+"_km_betap_mle"+Integer.toString(i),"h2_"+s_run_number+"_km_betap_mle"+Integer.toString(i),500, min_p, max_p, 500, min_b, max_b ));

	h_kp_conflvl.add( new H1F("h_"+s_run_number+"_kp_conflvl_mle","h_"+s_run_number+"_kp_conflvl_mle"+Integer.toString(i), 100,0.0,1.1));
	h_km_conflvl.add( new H1F("h_"+s_run_number+"_km_conflvl_mle","h_"+s_run_number+"_km_conflvl_mle"+Integer.toString(i), 100,0.0,1.1));

	h_kp_rpath.add( new H1F("h_"+s_run_number+"_kp_rpath_mle","h_"+s_run_number+"_kp_rpath_mle"+Integer.toString(i), 300, min_rpath, max_rpath ) );
	h_kp_beta_time.add( new H1F("h_"+s_run_number+"_kp_betatime_mle","h_"+s_run_number+"_kp_betatime_mle"+Integer.toString(i), 200, min_b, max_b ) );
	h_kp_beta_mntm.add( new H1F("h_"+s_run_number+"_kp_betamntm_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_betamntm_mle"+Integer.toString(i), 200, min_b, max_b ) );
	h2_kp_tof.add( new H2F("h2_"+s_run_number+"_kp_tof_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_tof_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_tof, max_tof ) );
	
	h2_kp_deltimep.add( new H2F("h2_"+s_run_number+"_kp_deltimep_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_deltimep_mle"+Integer.toString(i),500, min_p, max_p, 500, min_delt, max_delt ));
	h2_kp_betap.add( new H2F("h2_"+s_run_number+"_kp_betap_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_betap_mle"+Integer.toString(i),500, min_p, max_p, 500, min_b, max_b ));
	h2_kp_deltabeta.add( new H2F("h2_"+s_run_number+"_kp_deltabeta_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_deltabeta_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_delb, max_delb ));

	h2_kp_vzphi.add( new H2F("h2_"+s_run_number+"_kp_vzphi_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_vzphi_mle"+Integer.toString(i), 200, min_vz, max_vz, 200, min_phi, max_phi ));
 	h2_kp_ptheta.add( new H2F("h2_"+s_run_number+"_kp_ptheta_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_ptheta_mle"+Integer.toString(i), 200, min_p, max_p, 200, min_theta, max_theta ));
	h2_kp_pphi.add( new H2F("h2_"+s_run_number+"_kp_pphi_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_pphi_mle"+Integer.toString(i), 200, min_p, max_p, 200, min_phi, max_phi ));

	h2_km_vzphi.add( new H2F("h2_"+s_run_number+"_km_vzphi_mle"+Integer.toString(i),"h2_"+s_run_number+"_km_vzphi_mle"+Integer.toString(i), 200, min_vz, max_vz, 200, min_phi, max_phi ));
 	h2_km_ptheta.add( new H2F("h2_"+s_run_number+"_km_ptheta_mle"+Integer.toString(i),"h2_"+s_run_number+"_km_ptheta_mle"+Integer.toString(i), 200, min_p, max_p, 200, min_theta, max_theta ));
	h2_km_pphi.add( new H2F("h2_"+s_run_number+"_km_pphi_mle"+Integer.toString(i),"h2_"+s_run_number+"_km_pphi_mle"+Integer.toString(i), 200, min_p, max_p, 200, min_phi, max_phi ));

 	h2_kp_dchit_R1_xy.add( new H2F("h2_"+s_run_number+"_kp_dchit_R1_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_dchit_R1_mle"+Integer.toString(i), 500, -95.0, 95.0, 500, -100.0, 100.0 ));
 	h2_kp_dchit_R2_xy.add( new H2F("h2_"+s_run_number+"_kp_dchit_R2_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_dchit_R2_mle"+Integer.toString(i), 500, -150.0, 150.0, 500, -150.0, 150.0 ));
 	h2_kp_dchit_R3_xy.add( new H2F("h2_"+s_run_number+"_kp_dchit_R3_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_dchit_R3_mle"+Integer.toString(i), 500, -200.0, 200.0, 500, -150.0, 150.0 ));
 	h2_kp_masstimep.add( new H2F("h2_"+s_run_number+"_kp_masstimep_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_masstimep_mle"+Integer.toString(i), 500, 0.0, 1.2, 500, min_p, max_p ));
 	h_kp_masstime.add( new H1F("h_"+s_run_number+"_kp_masstime_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_masstime_mle"+Integer.toString(i), 500, 0.0, 2.0 ));


 	h2_kp_ectotp.add( new H2F("h2_"+s_run_number+"_kp_ectotp_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_ectotp_mle"+Integer.toString(i), 400, 0.0, 4.0, 400, 0.0, 0.5 ));
 	h_kp_pcal_e.add( new H1F("h_"+s_run_number+"_kp_pcal_e_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_pcal_e_mle"+Integer.toString(i), 250, 0.0, 0.0070 ));
	h_kp_ftof_l1_e.add( new H1F("h_"+s_run_number+"_kp_ftof_l1_e_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_ftof_l1_e_mle"+Integer.toString(i), 250, 0.0, 0.50 ));
	h_kp_ftof_l2_e.add( new H1F("h_"+s_run_number+"_kp_ftof_l2_e_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_ftof_l2_e_mle"+Integer.toString(i), 250, 0.0, 0.50 ));	
 	h_kp_eical_e.add( new H1F("h_"+s_run_number+"_kp_eical_e_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_eical_e_mle"+Integer.toString(i), 250, 0.0, 0.50 ));
 	h_kp_eocal_e.add( new H1F("h_"+s_run_number+"_kp_eocal_e_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_eocal_e_mle"+Integer.toString(i), 250, 0.0, 0.50 ));

	h2_kp_ftof_l2_tof.add( new H2F("h2_"+s_run_number+"_kp_ftof_l2_tof_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_ftof_l2_tof_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_tof, max_tof ) );
	h_kp_ftof_l2_beta_time.add( new H1F("h_"+s_run_number+"_kp_ftof_l2_betatime_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_ftof_l2_betatime_mle"+Integer.toString(i), 400, min_b, max_b ) );
 	h2_kp_ftof_l2_masstimep.add( new H2F("h2_"+s_run_number+"_kp_ftof_l2_masstimep_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_ftof_l2_masstimep_mle"+Integer.toString(i), 500, 0.0, 1.2, 500, min_p, max_p ));
 	h_kp_ftof_l2_masstime.add( new H1F("h_"+s_run_number+"_kp_ftof_l2_masstime_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_ftof_l2_masstime_mle"+Integer.toString(i), 500, 0.0, 2.0 ));
	h2_kp_ftof_l2_deltabeta.add( new H2F("h2_"+s_run_number+"_kp_ftof_l2_deltabeta_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_ftof_l2_deltabeta_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_delb, max_delb ));
	h2_kp_ftof_l2_betap.add( new H2F("h2_"+s_run_number+"_kp_ftof_l2_betap_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_ftof_l2_betap_mle"+Integer.toString(i),500, min_p, max_p, 500, min_b, max_b ));
	h2_kp_ftof_l2_deltimep.add( new H2F("h2_"+s_run_number+"_kp_ftof_l2_deltimep_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_ftof_l2_deltimep_mle"+Integer.toString(i),500, min_p, max_p, 500, min_delt, max_delt ));

	h2_km_ftof_l2_betap.add( new H2F("h2_"+s_run_number+"_km_ftof_l2_betap_mle"+Integer.toString(i),"h2_"+s_run_number+"_km_ftof_l2_betap_mle"+Integer.toString(i),500, min_p, max_p, 500, min_b, max_b ));


	h_pip_conflvl.add( new H1F("h_"+s_run_number+"_pip_conflvl_mle","h_"+s_run_number+"_pip_conflvl_mle"+Integer.toString(i), 100,0.0,1.1));
	h_pim_conflvl.add( new H1F("h_"+s_run_number+"_pim_conflvl_mle","h_"+s_run_number+"_pim_conflvl_mle"+Integer.toString(i), 100,0.0,1.1));



    }


    public void createKaonSectorHistograms( int sector, int max_cuts ){
	h_kp_sect_p.add( new Vector<H1F>() );
	h_kp_sect_masstime.add( new Vector<H1F>() );
 	h_kp_sect_theta.add( new Vector<H1F>() );
	h_kp_sect_phi.add( new Vector<H1F>() );
	h2_kp_sect_betap.add( new Vector<H2F>() );
	h2_kp_el_sect_betap.add( new Vector<H2F>() );
	h2_kp_sect_deltabeta.add( new Vector<H2F>() );
	h2_kp_sect_deltimep.add( new Vector<H2F>() );
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

	//System.out.println(" >> " + h__sect_eical_e.size() );
	for( int i = 0; i <= max_cuts; i++ ){
	    (h_kp_sect_p.get(sector)).add( new H1F("h_"+s_run_number+"_kp_" + Integer.toString(sector) + "_p_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_p_mle"+Integer.toString(i), 200, min_p, max_p ) );  
	    (h_kp_sect_masstime.get(sector)).add( new H1F("h_"+s_run_number+"_kp_" + Integer.toString(sector) + "_masstime_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_masstime_mle"+Integer.toString(i), 550, 0.0 , 2.0) );  
	    (h2_kp_sect_betap.get(sector)).add( new H2F("h2_"+s_run_number+"_kp_" + Integer.toString(sector) + "_betap_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_betap_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_b, max_b ) );  
	    (h2_kp_el_sect_betap.get(sector)).add( new H2F("h2_"+s_run_number+"_kp_el_" + Integer.toString(sector) + "_betap_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_el_" + Integer.toString(sector)  + "_betap_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_b, max_b ) );  
	    (h2_kp_sect_deltabeta.get(sector)).add( new H2F("h2_"+s_run_number+"_kp_" + Integer.toString(sector) + "_deltabeta_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_deltabeta_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_delb, max_delb ) );  
	    (h2_kp_sect_deltimep.get(sector)).add( new H2F("h2_"+s_run_number+"_kp_" + Integer.toString(sector) + "_deltimep_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_deltimep_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_delt, max_delt ) );  
	    (h2_kp_sect_masstimep.get(sector)).add( new H2F("h2_"+s_run_number+"_kp_" + Integer.toString(sector) + "_masstimep_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_masstimep_mle"+Integer.toString(i), 500, 0.0, 1.2, 500, min_p, max_p ) );  

	    (h2_kp_sect_ectotp.get(sector)).add( new H2F("h2_"+s_run_number+"_kp_" + Integer.toString(sector) + "_ectotp_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_ectotp_mle"+Integer.toString(i), 400, 0.0, 4.0, 400, 0.0, 0.5 ) );  
	    (h_kp_sect_pcal_e.get(sector)).add( new H1F("h_"+s_run_number+"_kp_" + Integer.toString(sector) + "_pcal_e_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_pcal_e_mle"+Integer.toString(i), 400, 0.0, 0.0080 ) );  
	    (h_kp_sect_ftof_l1_e.get(sector)).add( new H1F("h_"+s_run_number+"_kp_" + Integer.toString(sector) + "_ftof_l1_e_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_ftof_l1_e_mle"+Integer.toString(i), 400, 0.0, 0.50 ) );  
	    (h_kp_sect_ftof_l2_e.get(sector)).add( new H1F("h_"+s_run_number+"_kp_" + Integer.toString(sector) + "_ftof_l2_e_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_ftof_l2_e_mle"+Integer.toString(i), 400, 0.0, 0.50 ) );  
	    (h_kp_sect_eical_e.get(sector)).add( new H1F("h_"+s_run_number+"_kp_" + Integer.toString(sector) + "_eical_e_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_eical_e_mle"+Integer.toString(i), 400, 0.0, 0.50 ) );  
	    (h_kp_sect_eocal_e.get(sector)).add( new H1F("h_"+s_run_number+"_kp_" + Integer.toString(sector) + "_eocal_e_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_eocal_e_mle"+Integer.toString(i), 400, 0.0, 0.50 ) );  


	    (h_kp_sect_ftof_l2_masstime.get(sector)).add( new H1F("h_"+s_run_number+"_kp_" + Integer.toString(sector) + "_ftof_l2_masstime_mle"+Integer.toString(i),"h_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_ftof_l2_masstime_mle"+Integer.toString(i), 500, 0.0 , 2.0) );  	    	     
	    (h2_kp_sect_ftof_l2_betap.get(sector)).add( new H2F("h2_"+s_run_number+"_kp_" + Integer.toString(sector) + "_ftof_l2_betap_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_ftof_l2_betap_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_b, max_b ) );  
	    (h2_kp_sect_ftof_l2_deltabeta.get(sector)).add( new H2F("h2_"+s_run_number+"_kp_" + Integer.toString(sector) + "_ftof_l2_deltabeta_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_ftof_l2_deltabeta_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_delb, max_delb ) );  
	    (h2_kp_sect_ftof_l2_deltimep.get(sector)).add( new H2F("h2_"+s_run_number+"_kp_" + Integer.toString(sector) + "_ftof_l2_deltimep_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_ftof_l2_deltimep_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_delt, max_delt ) );  
	    (h2_kp_sect_ftof_l2_masstimep.get(sector)).add( new H2F("h2_"+s_run_number+"_kp_" + Integer.toString(sector) + "_ftof_l2_masstimep_mle"+Integer.toString(i),"h2_"+s_run_number+"_kp_" + Integer.toString(sector)  + "_ftof_l2_masstimep_mle"+Integer.toString(i), 500, 0.0, 1.2, 500, min_p, max_p ) );  

	}
    }

    public void kaonHistoToHipo(){

	dir.mkdir("/kaon/mle/h_kp_p/");
	dir.cd("/kaon/mle/h_kp_p/");
	for( H1F h_temp : h_kp_p ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h2_kp_tof/");
	dir.cd("/kaon/mle/h2_kp_tof/");
	for( H2F h_temp : h2_kp_tof ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("TOF [ns]");	    
	    c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h_kp_rpath/");
	dir.cd("/kaon/mle/h_kp_rpath/");
	for( H1F h_temp : h_kp_rpath ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h_kp_beta_time/");
	dir.cd("/kaon/mle/h_kp_beta_time/");
	for( H1F h_temp : h_kp_beta_time ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h_kp_masstime/");
	dir.cd("/kaon/mle/h_kp_masstime/");
	for( H1F h_temp : h_kp_masstime ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Mass [GeV/c]");
	    c_temp.draw(h_temp);
	    //F1D f_mass = Calculator.fitHistogram(h_temp,0.6);
	    //f_mass.setLineColor(2);
	    //f_mass.setLineWidth(3);
	    //f_mass.setOptStat(1101);   
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h2_kp_masstimep/");
	dir.cd("/kaon/mle/h2_kp_masstimep/");
	for( H2F h_temp : h2_kp_masstimep ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Mass [GeV/c]");
	    h_temp.setTitleY("p [GeV/c^2]");
	    c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h2_kp_dchit_R1_xy/");
	dir.cd("/kaon/mle/h2_kp_dchit_R1_xy/");
	for( H2F h_temp : h2_kp_dchit_R1_xy){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("DC X [cm]");
	    h_temp.setTitleX("DC Y [cm]"); 
	    c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}
	dir.mkdir("/kaon/mle/h2_kp_dchit_R2_xy/");
	dir.cd("/kaon/mle/h2_kp_dchit_R2_xy/");
	for( H2F h_temp : h2_kp_dchit_R2_xy){
	    dir.addDataSet(h_temp);
	}
	dir.mkdir("/kaon/mle/h2_kp_dchit_R3_xy/");
	dir.cd("/kaon/mle/h2_kp_dchit_R3_xy/");
	for( H2F h_temp : h2_kp_dchit_R3_xy){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("DC X [cm]");
	    h_temp.setTitleX("DC Y [cm]"); 
	    c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h2_kp_vzphi/");
	dir.cd("/kaon/mle/h2_kp_vzphi/");
	for( H2F h_temp : h2_kp_vzphi){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h_kp_vz_mod/");
	dir.cd("/kaon/mle/h_kp_vz_mod/");
	for( H1F h_temp : h_kp_vz_mod){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h_kp_vz/");
	dir.cd("/kaon/mle/h_kp_vz/");
	for( H1F h_temp : h_kp_vz){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    //F1D v_fit  = Calculator.fitHistogram(h_temp, 0.3);
	    //v_fit.setLineColor(2);
	    //v_fit.setLineWidth(3);
	    c_temp.draw(h_temp);	    
	    //c_temp.draw(v_fit,"same");
	    dir.addDataSet(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	}
       
	for( int j = 0; j < h_kp_vz_mod.size(); j++ ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_kp_vz.get(j).setLineColor(2);
	    h_kp_vz.get(j).setLineWidth(3);
	    h_kp_vz_mod.get(j).setLineColor(2);
	    h_kp_vz_mod.get(j).setLineWidth(3);
	    c_temp.draw(h_kp_vz.get(j));
	    c_temp.draw(h_kp_vz_mod.get(j));
	    //c_temp.save(savepath+"kp_vzMOD"+".png");
	    dir.addDataSet(h_kp_vz_mod.get(j));
	}



	dir.mkdir("/kaon/mle/h2_kp_deltabeta/");
	dir.cd("/kaon/mle/h2_kp_deltabeta/");
	for( H2F h_temp : h2_kp_deltabeta ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#delta #beta");	    
	    c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}
    
	dir.mkdir("/kaon/mle/h2_kp_ectotp/");
	dir.cd("/kaon/mle/h2_kp_ectotp/");
	for( H2F h_temp : h2_kp_ectotp){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("EC SF"); 
	    c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h_kp_pcal_e/");
	dir.cd("/kaon/mle/h_kp_pcal_e/");
	for( H1F h_temp : h_kp_pcal_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("PCAL Energy [GeV]");
	    c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h_kp_ftof_l1_e/");
	dir.cd("/kaon/mle/h_kp_ftof_l1_e/");
	for( H1F h_temp : h_kp_ftof_l1_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("FTOF L1 Energy [GeV]");
	    c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}


	dir.mkdir("/kaon/mle/h_kp_ftof_l2_e/");
	dir.cd("/kaon/mle/h_kp_ftof_l2_e/");
	for( H1F h_temp : h_kp_ftof_l2_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("FTOF L2 Energy [GeV]");
	    c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h_kp_eical_e/");
	dir.cd("/kaon/mle/h_kp_eical_e/");
	for( H1F h_temp : h_kp_eical_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("ECAL EI [GeV]");
	    c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h_kp_eocal_e/");
	dir.cd("/kaon/mle/h_kp_eocal_e/");
	for( H1F h_temp : h_kp_eocal_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("ECAL EO [GeV]");
	    c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}


	/*dir.mkdir("/proton/mle/h_pr_sect_panel_deltimep/");
	dir.cd("/proton/mle/h_pr_sect_panel_deltimep/");
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
	*/
	dir.mkdir("/kaon/mle/h2_kp_deltimep/");
	dir.cd("/kaon/mle/h2_kp_deltimep/");
	for( H2F h_temp : h2_kp_deltimep ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#delta time of flight");	    
	    c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}


	F1D f_beta_cuttop = new F1D("f_betacut_top","x/sqrt(x*x + [a]*[a])",0.1,5.5);
	f_beta_cuttop.setParameter(0,PhysicalConstants.mass_kaon*PhysicalConstants.mass_kaon);	
	f_beta_cuttop.setLineColor(2);
	f_beta_cuttop.setLineWidth(3);
				
	dir.mkdir("/kaon/mle/h2_kp_betap/");
	dir.cd("/kaon/mle/h2_kp_betap/");
	for( H2F h_temp : h2_kp_betap ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#beta");
	    c_temp.draw(h_temp,"colz");
	    c_temp.draw(f_beta_cuttop,"same");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h2_km_betap/");
	dir.cd("/kaon/mle/h2_km_betap/");
	for( H2F h_temp : h2_km_betap ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#beta");
	    c_temp.draw(h_temp,"colz");
	    c_temp.draw(f_beta_cuttop,"same");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///SECTOR BASED PLOTS
	F1D f_beta_kp = new F1D("f_betacut_kp","x/sqrt(x*x + [a]*[a])",0.1,5.5);
	f_beta_kp.setParameter(0,PhysicalConstants.mass_kaon*PhysicalConstants.mass_kaon);	
	f_beta_kp.setLineColor(2);

	dir.mkdir("/kaon/mle/h2_kp_sect_betap/");
	dir.cd("/kaon/mle/h2_kp_sect_betap/");
	for( int i = 0; i < h2_kp_sect_betap.size(); i++ ){
 	   Vector<H2F> v_temp = h2_kp_sect_betap.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("p [GeV]");
		v_temp.get(j).setTitleY("Total Energy (PCAL + ECAL ) [GeV]");		
		c_temp.draw(v_temp.get(j),"colz");
		c_temp.draw(f_beta_kp,"same");
		//c_temp.save(savepath+v_temp.get(j).getTitle()+".png");
		
	    }
	}

	dir.mkdir("/kaon/mle/h2_kp_el_sect_betap/");
	dir.cd("/kaon/mle/h2_kp_el_sect_betap/");
	for( int i = 0; i < h2_kp_el_sect_betap.size(); i++ ){
 	   Vector<H2F> v_temp = h2_kp_el_sect_betap.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		//v_temp.get(j).setTitleX("p [GeV]");
		//v_temp.get(j).setTitleY("Total Energy (PCAL + ECAL ) [GeV]");
		c_temp.draw(v_temp.get(j),"colz");
		c_temp.draw(f_beta_kp,"same");
		//dir.addDataSet(v_temp.get(j));
		//c_temp.save(savepath+v_temp.get(j).getTitle()+".png");
	    }
	}

	dir.mkdir("/kaon/mle/h2_kp_sect_deltabeta/");
	dir.cd("/kaon/mle/h2_kp_sect_deltabeta/");
	for( int i = 0; i < h2_kp_sect_deltabeta.size(); i++ ){
 	   Vector<H2F> v_temp = h2_kp_sect_deltabeta.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));

	    }
	}

	dir.mkdir("/kaon/mle/h2_kp_sect_deltimep/");
	dir.cd("/kaon/mle/h2_kp_sect_deltimep/");
	for( int i = 0; i < h2_kp_sect_deltimep.size(); i++ ){
 	   Vector<H2F> v_temp = h2_kp_sect_deltimep.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
	    }
	}	
    
	dir.mkdir("/kaon/mle/h_kp_sect_masstime/");
 	dir.cd("/kaon/mle/h_kp_sect_masstime/");
	for( int i = 0; i < h_kp_sect_masstime.size(); i++ ){
 	   Vector<H1F> v_temp = h_kp_sect_masstime.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("Mass [GeV]");
		c_temp.draw(v_temp.get(j));
		//F1D f_mass = Calculator.fitHistogram(v_temp.get(j), 0.3);
		//f_mass.setLineColor(2);
		//f_mass.setLineWidth(3);
		//f_mass.setOptStat(1101);
		//c_temp.draw(f_mass,"same");
		//c_temp.save(savepath+v_temp.get(j).getTitle()+".png");		
	    }
	}

	dir.mkdir("/kaon/mle/h2_kp_sect_masstimep/");
	dir.cd("/kaon/mle/h2_kp_sect_masstimep/");
	for( int i = 0; i < h2_kp_sect_masstimep.size(); i++ ){
 	   Vector<H2F> v_temp = h2_kp_sect_masstimep.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("Mass [GeV/c]");
		v_temp.get(j).setTitleY("p [GeV/c^2]"); 
		c_temp.draw(v_temp.get(j),"colz");
		//c_temp.save(savepath+v_temp.get(j).getTitle()+".png");	       
		dir.addDataSet(v_temp.get(j));
	    }
	}


	dir.mkdir("/kaon/mle/h2_kp_sect_ectotp/");
	dir.cd("/kaon/mle/h2_kp_sect_ectotp/");
	for( int i = 0; i < h2_kp_sect_ectotp.size(); i++ ){
 	   Vector<H2F> v_temp = h2_kp_sect_ectotp.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("p [GeV]");
		v_temp.get(j).setTitleY("EC SF"); 
		c_temp.draw(v_temp.get(j),"colz");
		c_temp.save(savepath+v_temp.get(j).getTitle()+".png");	       
		dir.addDataSet(v_temp.get(j));
	    }
	}

	dir.mkdir("/kaon/mle/h_kp_sect_pcal_e/");
	dir.cd("/kaon/mle/h_kp_sect_pcal_e/");
	for( int i = 0; i < h_kp_sect_pcal_e.size(); i++ ){
 	   Vector<H1F> v_temp = h_kp_sect_pcal_e.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("PCAL [GeV]");
		c_temp.draw(v_temp.get(j));
		//c_temp.save(savepath+v_temp.get(j).getTitle()+".png");	       
		dir.addDataSet(v_temp.get(j));
	    }
	}


	dir.mkdir("/kaon/mle/h_kp_sect_ei_e/");
	dir.cd("/kaon/mle/h_kp_sect_ei_e/");
	for( int i = 0; i < h_kp_sect_eical_e.size(); i++ ){
 	   Vector<H1F> v_temp = h_kp_sect_eical_e.get(i);       
	   for( int j = 0; j < v_temp.size(); j++){		
	       EmbeddedCanvas c_temp = new EmbeddedCanvas();
	       c_temp.setSize(800,800);
	       v_temp.get(j).setTitleX("EC EI [GeV]");
	       c_temp.draw(v_temp.get(j));
	       //c_temp.save(savepath+v_temp.get(j).getTitle()+".png");	       
	       dir.addDataSet(v_temp.get(j));
	    }
	}

 
	dir.mkdir("/kaon/mle/h_kp_sect_eo_e/");
	dir.cd("/kaon/mle/h_kp_sect_eo_e/");
	for( int i = 0; i < h_kp_sect_eocal_e.size(); i++ ){
 	   Vector<H1F> v_temp = h_kp_sect_eocal_e.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
	       EmbeddedCanvas c_temp = new EmbeddedCanvas();
	       c_temp.setSize(800,800);
	       v_temp.get(j).setTitleX("EC EO [GeV]");
	       c_temp.draw(v_temp.get(j));
	       //c_temp.save(savepath+v_temp.get(j).getTitle()+".png");	       
	       dir.addDataSet(v_temp.get(j));
	    }
	}

	dir.mkdir("/kaon/mle/h_kp_ftof_l2_masstime/");
	dir.cd("/kaon/mle/h_kp_ftof_l2_masstime/");
	for( H1F h_temp : h_kp_ftof_l2_masstime ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Mass [GeV/c]");
	    //F1D f_mass = Calculator.fitHistogram(h_temp, 0.3); 
	    //f_mass.setLineColor(2);
	    //f_mass.setLineWidth(3);
	    //c_temp.draw(h_temp);
	    //c_temp.draw(f_mass,"same");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h2_kp_ftof_l2_masstimep/");
	dir.cd("/kaon/mle/h2_kp_ftof_l2_masstimep/");
	for( H2F h_temp : h2_kp_ftof_l2_masstimep ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Mass [GeV/c]");
	    h_temp.setTitleY("p [GeV/c^2]");
	    c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h2_kp_ftof_l2_deltimep/");
	dir.cd("/kaon/mle/h2_kp_ftof_l2_deltimep/");
	for( H2F h_temp : h2_kp_ftof_l2_deltimep ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#delta time of flight");	    
	    c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h2_kp_ftof_l2_deltabeta/");
	dir.cd("/kaon/mle/h2_kp_ftof_l2_deltabeta/");
	for( H2F h_temp : h2_kp_ftof_l2_deltabeta ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#delta #beta");	    
	    c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h2_kp_ftof_l2_betap/");
	dir.cd("/kaon/mle/h2_kp_ftof_l2_betap/");
	for( H2F h_temp : h2_kp_ftof_l2_betap ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#beta");
	    c_temp.draw(h_temp,"colz");
	    c_temp.draw(f_beta_cuttop,"same");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h2_kp_ftof_l2_tof/");
	dir.cd("/kaon/mle/h2_kp_ftof_l2_tof/");
	for( H2F h_temp : h2_kp_ftof_l2_tof ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("TOF [ns]");	    
	    c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h_kp_ftof_l2_beta_time/");
	dir.cd("/kaon/mle/h_kp_ftof_l2_beta_time/");
	for( H1F h_temp : h_kp_ftof_l2_beta_time ){
	    dir.addDataSet(h_temp);
	}


	dir.mkdir("/kaon/mle/h_kp_sect_ftof_l2_masstime/");
	dir.cd("/kaon/mle/h_kp_sect_ftof_l2_masstime/");
	for( int i = 0; i < h_kp_sect_ftof_l2_masstime.size(); i++ ){
 	   Vector<H1F> v_temp = h_kp_sect_ftof_l2_masstime.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
	    }
	}

	dir.mkdir("/kaon/mle/h2_kp_sect_ftof_l2_betap/");
	dir.cd("/kaon/mle/h2_kp_sect_ftof_l2_betap/");
	for( int i = 0; i < h2_kp_sect_ftof_l2_betap.size(); i++ ){
 	   Vector<H2F> v_temp = h2_kp_sect_ftof_l2_betap.get(i);       
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

	dir.mkdir("/kaon/mle/h2_kp_sect_ftof_l2_deltabeta/");
	dir.cd("/kaon/mle/h2_kp_sect_ftof_l2_deltabeta/");
	for( int i = 0; i < h2_kp_sect_ftof_l2_deltabeta.size(); i++ ){
 	   Vector<H2F> v_temp = h2_kp_sect_ftof_l2_deltabeta.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));

	    }
	}

	dir.mkdir("/kaon/mle/h2_kp_sect_ftof_l2_deltimep/");
	dir.cd("/kaon/mle/h2_kp_sect_ftof_l2_deltimep/");
	for( int i = 0; i < h2_kp_sect_ftof_l2_deltimep.size(); i++ ){
 	   Vector<H2F> v_temp = h2_kp_sect_ftof_l2_deltimep.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
	    }
	}	

	dir.mkdir("/kaon/mle/h2_kp_sect_ftof_l2_masstimep/");
	dir.cd("/kaon/mle/h2_kp_sect_ftof_l2_masstimep/");
	for( int i = 0; i < h2_kp_sect_ftof_l2_masstimep.size(); i++ ){
 	   Vector<H2F> v_temp = h2_kp_sect_ftof_l2_masstimep.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("Mass [GeV/c]");
		v_temp.get(j).setTitleY("p [GeV/c^2]"); 
		c_temp.draw(v_temp.get(j),"colz");
		//c_temp.save(savepath+v_temp.get(j).getTitle()+".png");	       
		dir.addDataSet(v_temp.get(j));
	    }
	}

	dir.mkdir("/kaon/mle/h_kp_p/");
	dir.cd("/kaon/mle/h_kp_p/");
	for( H1F h_temp : h_kp_p ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h2_kp_conflvl/");
	dir.cd("/kaon/mle/h2_kp_conflvl/");
	for( H1F h_temp :  h_kp_conflvl ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Confidence Level");
	    c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}


	dir.mkdir("/kaon/mle/h2_kaon_ptheta/");
	dir.cd("/kaon/mle/h2_kaon_ptheta/");
	for( H2F h_temp :  h2_kp_ptheta ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#theta [deg]");
	    c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}	

	dir.mkdir("/kaon/mle/h2_kp_pphi/");
	dir.cd("/kaon/mle/h2_kp_pphi/");
	for( H2F h_temp :  h2_kp_pphi ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#phi [deg]");
	    c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}	
	

	dir.mkdir("/kaon/mle/h_km_p/");
	dir.cd("/kaon/mle/h_km_p/");
	for( H1F h_temp : h_km_p ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h2_km_conflvl/");
	dir.cd("/kaon/mle/h2_km_conflvl/");
	for( H1F h_temp :  h_km_conflvl ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Confidence Level");
	    c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h_pim_conflvl/");
	dir.cd("/kaon/mle/h_pim_conflvl/");
	for( H1F h_temp : h_pim_conflvl ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Confidence Level");
	    c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/kaon/mle/h_km_conflvl/");
	dir.cd("/kaon/mle/h_km_conflvl/");
	for( H1F h_temp : h_km_conflvl ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Confidence Level");
	    c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}


	dir.mkdir("/kaon/mle/h2_km_ptheta/");
	dir.cd("/kaon/mle/h2_km_ptheta/");
	for( H2F h_temp :  h2_km_ptheta ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#theta [deg]");
	    c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}	

	dir.mkdir("/kaon/mle/h2_km_pphi/");
	dir.cd("/kaon/mle/h2_km_pphi/");
	for( H2F h_temp :  h2_km_pphi ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#phi [deg]");
	    c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}	
	
	dir.mkdir("/kaon/mle/h2_km_ftof_l2_betap/");
	dir.cd("/kaon/mle/h2_km_ftof_l2_betap/");
	for( H2F h_temp : h2_km_ftof_l2_betap ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#beta");
	    c_temp.draw(h_temp,"colz");
	    c_temp.draw(f_beta_cuttop,"same");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}


	dir.mkdir("/kaon/mle/h2_test/");
	dir.cd("/kaon/mle/h2_test/");
	dir.addDataSet(h_test_phi);
	EmbeddedCanvas ctest = new EmbeddedCanvas();
	ctest.setSize(800,800);
	h_test_phi.setTitleX("Mass [GeV]");
	ctest.draw(h_test_phi);
	//ctest.save(savepath+h_test_phi.getTitle()+".png");

	saveHipoOut();

    }

    public void saveHipoOut(){
	dir.writeFile(savepath+"h_"+s_run_number+"_"+n_thread+"_kaonP_pid_clary.hipo");
    }

    public void viewHipoOut(){

	//dir.readFile("/u/home/bclary/CLAS12/phi_analysis/v2/v1/testing_cutlvl.hipo");
	TBrowser browser = new TBrowser(dir);

	//dir.ls();
	
    }


}
