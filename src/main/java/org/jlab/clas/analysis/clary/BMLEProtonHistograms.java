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


public class BMLEProtonHistograms {

    int run_number = -1;
    String s_run_number = " ";
    String n_thread = null;
    public BMLEProtonHistograms(int temp_run, String temp_thread) {
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
    double min_vz = -20.0; double max_vz = 20.0;
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
    Vector<H1F> h_pr_vz_mod = new Vector<H1F>(); 
    Vector<H1F> h_pr_timing = new Vector<H1F>(); 
    Vector<H1F> h_pr_masstime = new Vector<H1F>();
    Vector<H1F> h_pr_conflvl = new Vector<H1F>();

    Vector<H2F> h2_pr_masstimep = new Vector<H2F>();
    Vector<H2F> h2_pr_vzphi = new Vector< H2F >();
    Vector<H2F> h2_pr_ptheta = new Vector< H2F >();
    Vector<H2F> h2_pr_pphi = new Vector< H2F >();
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
    Vector<Vector<H1F>> h_pr_sect_deltime = new Vector<Vector<H1F> >();

    Vector<Vector<H2F>> h2_pr_sect_ectotp = new Vector<Vector<H2F> >();
    Vector<Vector<H1F>> h_pr_sect_pcal_e = new Vector<Vector<H1F> >();
    Vector<Vector<H1F>> h_pr_sect_ftof_l1_e = new Vector<Vector<H1F> >();
    Vector<Vector<H1F>> h_pr_sect_ftof_l2_e = new Vector<Vector<H1F> >();
    Vector<Vector<H1F>> h_pr_sect_eical_e = new Vector<Vector<H1F> >();
    Vector<Vector<H1F>> h_pr_sect_eocal_e = new Vector<Vector<H1F> >();

    Vector<Vector<H2F>> h2_pr_sect_betap = new Vector< Vector<H2F> >();
    Vector<Vector<H2F>> h2_pr_el_sect_betap = new Vector< Vector<H2F> >();
    Vector<Vector<H2F>> h2_pr_sect_deltabeta = new Vector<Vector<H2F> >();
    Vector<Vector<H2F>> h2_pr_sect_deltimep = new Vector<Vector<H2F> >();    
    Vector<Vector<H2F>> h2_pr_sect_vztheta = new Vector< Vector<H2F> >();
    Vector<Vector<H2F>> h2_pr_sect_masstimep = new Vector<Vector<H2F> >();
    //////////////////////////////////////////////////////////////////////////
    //LAYERS FTOF 2 PLOTS

    H2F h_beta_all_pos;
    H1F h_test_phi;
    
    Vector<H2F> h2_pr_ftof_l2_masstimep = new Vector<H2F>();
    Vector<H2F> h2_pr_ftof_l2_vzphi = new Vector< H2F >();
    Vector<H2F> h2_pr_ftof_l2_deltimep = new Vector<H2F>(); 
    Vector<H2F> h2_pr_ftof_l2_deltabeta = new Vector<H2F>();
    Vector<H2F> h2_pr_ftof_l2_betap = new Vector<H2F>();

    Vector<H2F> h2_pr_ftof_l2_tof = new Vector<H2F>();
    Vector<H1F> h_pr_ftof_l2_beta_time = new Vector<H1F>();
    Vector<H1F> h_pr_ftof_l2_masstime = new Vector<H1F>();

    Vector<H2F> h2_beta_all_pos_sect_ftof_l2 = new Vector<H2F>();                                                                                                                                            Vector<H2F> h2_beta_all_pos_sect_ftof_l1 = new Vector<H2F>();                                                                                                                                         
    Vector<H2F> h2_beta_all_neg_sect_ftof_l2 = new Vector<H2F>();                                                                                                                                            Vector<H2F> h2_beta_all_neg_sect_ftof_l1 = new Vector<H2F>();                                                                                                                                       
    
    Vector<H2F> h2_beta_all_pr_sect_ftof_l2 = new Vector<H2F>();                                                                                                                                         
    Vector<H2F> h2_beta_all_pr_sect_ftof_l1 = new Vector<H2F>();                                                                                                                                                                                                                                                                                                                                                  
    Vector<H2F> h2_beta_all_pip_sect_ftof_l2 = new Vector<H2F>();                                                                                                                                        
    Vector<H2F> h2_beta_all_pip_sect_ftof_l1 = new Vector<H2F>();                                                                                                                                                                                                                                                                                                                                                 
    Vector<H2F> h2_beta_all_kp_sect_ftof_l2 = new Vector<H2F>();                                                                                                                                         
    Vector<H2F> h2_beta_all_kp_sect_ftof_l1 = new Vector<H2F>();

    Vector<Vector<H2F>> h2_pr_sect_ftof_l2_betap = new Vector< Vector<H2F> >();
    Vector<Vector<H2F>> h2_pr_sect_ftof_l2_deltabeta = new Vector<Vector<H2F> >();
    Vector<Vector<H2F>> h2_pr_sect_ftof_l2_deltimep = new Vector<Vector<H2F> >();    
    Vector<Vector<H2F>> h2_pr_sect_ftof_l2_vztheta = new Vector< Vector<H2F> >();
    Vector<Vector<H2F>> h2_pr_sect_ftof_l2_masstimep = new Vector<Vector<H2F> >();

    Vector<Vector<H2F>> h_pr_sect_panel_deltp = new Vector< Vector<H2F> >();   
    Vector< Vector< Vector< H2F > > > h_pr_sect_panel_deltimep = new Vector< Vector< Vector<H2F> > >(); 

    HashMap<Integer, Vector< Vector<H2F> > > m_pr_sect_panel_deltp = new HashMap<Integer, Vector< Vector< H2F> > >();
    HashMap<Integer, Vector< Vector<H1F> > > m_pr_sect_panel_deltime = new HashMap<Integer, Vector< Vector< H1F> > >();


    H2F h2_poscharge_dc_R1_traj = new H2F("h2_"+s_run_number+"_poschrg_dchit_R1_traj_cutlvl0","h2_"+s_run_number+"_poschrg_dchit_R1_traj_cutlvl0", 500, -200.0, 200.0, 500, -200.0, 200.0);
    H2F h2_poscharge_dc_R2_traj = new H2F("h2_"+s_run_number+"_poschrg_dchit_R2_traj_cutlvl0","h2_"+s_run_number+"_poschrg_dchit_R2_traj_cutlvl0", 500, -300.0, 300.0, 500, -300.0, 300.0);
    H2F h2_poscharge_dc_R3_traj = new H2F("h2_"+s_run_number+"_poschrg_dchit_R3_traj_cutlvl0","h2_"+s_run_number+"_poschrg_dchit_R3_traj_cutlvl0", 500, -400.0, 400.0, 500, -400.0, 400.0);

    H2F h2_poscharge_dc_R1_traj_rot = new H2F("h2_"+s_run_number+"_poschrg_dchit_R1_traj_rot_cutlvl0","h2_"+s_run_number+"_poschrg_dchit_R1_traj_rot_cutlvl0", 500, -200.0, 200.0, 500, -200.0, 200.0);
    H2F h2_poscharge_dc_R2_traj_rot = new H2F("h2_"+s_run_number+"_poschrg_dchit_R2_traj_rot_cutlvl0","h2_"+s_run_number+"_poschrg_dchit_R2_traj_rot_cutlvl0", 500, -300.0, 300.0, 500, -300.0, 300.0);
    H2F h2_poscharge_dc_R3_traj_rot = new H2F("h2_"+s_run_number+"_poschrg_dchit_R3_traj_rot_cutlvl0","h2_"+s_run_number+"_poschrg_dchit_R3_traj_rot_cutlvl0", 500, -400.0, 400.0, 500, -400.0, 400.0);

    public void createProtonMLEHistograms(){

	int i = 0;
	h_beta_all_pos = new H2F("h_beta_all_pos","h_beta_all_pos",250,0.0,6.5, 250, 0.01, 1.2);
	h_test_phi = new H1F("h_test_phi","h_test_phi",100,0.5,1.3);

	h_pr_p.add( new H1F("h_"+s_run_number+"_pr_p_mle","h_"+s_run_number+"_pr_p_mle", 100, min_p, max_p ) );
	h_pr_theta.add( new H1F("h_"+s_run_number+"_pr_theta_mle","h_"+s_run_number+"_pr_theta_mle", 100, min_theta, max_theta) );
	h_pr_phi.add( new H1F("h_"+s_run_number+"_pr_phi_mle","h_"+s_run_number+"_pr_phi_mle", 100, min_phi, max_phi ) );
	h_pr_vz.add( new H1F("h_"+s_run_number+"_pr_vz_mle","h_"+s_run_number+"_pr_vz_mle", 100, min_vz, max_vz ) );
	h_pr_vz_mod.add( new H1F("h_"+s_run_number+"_pr_vzmod_mle","h_"+s_run_number+"_pr_vzmod_mle", 100, min_vz, max_vz ) );
	h_pr_timing.add( new H1F("h_"+s_run_number+"_pr_timing_mle","h_"+s_run_number+"_pr_timing_mle"+Integer.toString(i), 100, min_timing, max_timing ) );
	h_pr_conflvl.add( new H1F("h_"+s_run_number+"_pr_conflvl_mle","h_"+s_run_number+"_pr_conflvl_mle"+Integer.toString(i), 100,0.0,1.1));

	h_pr_rpath.add( new H1F("h_"+s_run_number+"_pr_rpath_mle","h_"+s_run_number+"_pr_rpath_mle"+Integer.toString(i), 300, min_rpath, max_rpath ) );
	h_pr_beta_time.add( new H1F("h_"+s_run_number+"_pr_betatime_mle","h_"+s_run_number+"_pr_betatime_mle"+Integer.toString(i), 200, min_b, max_b ) );
	h_pr_beta_mntm.add( new H1F("h_"+s_run_number+"_pr_betamntm_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_betamntm_mle"+Integer.toString(i), 200, min_b, max_b ) );
	h2_pr_tof.add( new H2F("h2_"+s_run_number+"_pr_tof_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_tof_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_tof, max_tof ) );
	
	h2_pr_deltimep.add( new H2F("h2_"+s_run_number+"_pr_deltimep_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_deltimep_mle"+Integer.toString(i),500, min_p, max_p, 500, min_delt, max_delt ));
	h2_pr_betap.add( new H2F("h2_"+s_run_number+"_pr_betap_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_betap_mle"+Integer.toString(i),500, min_p, max_p, 500, min_b, max_b ));
	h2_pr_deltabeta.add( new H2F("h2_"+s_run_number+"_pr_deltabeta_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_deltabeta_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_delb, max_delb ));

	h2_pr_vzphi.add( new H2F("h2_"+s_run_number+"_pr_vzphi_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_vzphi_mle"+Integer.toString(i), 200, min_vz, max_vz, 200, min_phi, max_phi ));
	h2_pr_ptheta.add( new H2F("h2_"+s_run_number+"_pr_ptheta_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_ptheta_mle"+Integer.toString(i), 200, min_p, max_p, 200, min_theta, max_theta ));
	h2_pr_pphi.add( new H2F("h2_"+s_run_number+"_pr_pphi_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_pphi_mle"+Integer.toString(i), 200, min_p, max_p, 200, min_phi, max_phi ));
	
 	h2_pr_dchit_R1_xy.add( new H2F("h2_"+s_run_number+"_pr_dchit_R1_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_dchit_R1_mle"+Integer.toString(i), 500, -95.0, 95.0, 500, -100.0, 100.0 ));
 	h2_pr_dchit_R2_xy.add( new H2F("h2_"+s_run_number+"_pr_dchit_R2_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_dchit_R2_mle"+Integer.toString(i), 500, -150.0, 150.0, 500, -150.0, 150.0 ));
 	h2_pr_dchit_R3_xy.add( new H2F("h2_"+s_run_number+"_pr_dchit_R3_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_dchit_R3_mle"+Integer.toString(i), 500, -200.0, 200.0, 500, -150.0, 150.0 ));
 	h2_pr_masstimep.add( new H2F("h2_"+s_run_number+"_pr_masstimep_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_masstimep_mle"+Integer.toString(i), 500, 0.0, 1.2, 500, min_p, max_p ));
 	h_pr_masstime.add( new H1F("h_"+s_run_number+"_pr_masstime_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_masstime_mle"+Integer.toString(i), 500, 0.0, 2.0 ));

 	h2_pr_ectotp.add( new H2F("h2_"+s_run_number+"_pr_ectotp_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_ectotp_mle"+Integer.toString(i), 400, 0.0, 4.0, 400, 0.0, 0.5 ));
 	h_pr_pcal_e.add( new H1F("h_"+s_run_number+"_pr_pcal_e_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_pcal_e_mle"+Integer.toString(i), 250, 0.0, 0.0070 ));
	h_pr_ftof_l1_e.add( new H1F("h_"+s_run_number+"_pr_ftof_l1_e_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_ftof_l1_e_mle"+Integer.toString(i), 250, 0.0, 0.50 ));
	h_pr_ftof_l2_e.add( new H1F("h_"+s_run_number+"_pr_ftof_l2_e_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_ftof_l2_e_mle"+Integer.toString(i), 250, 0.0, 0.50 ));	
 	h_pr_eical_e.add( new H1F("h_"+s_run_number+"_pr_eical_e_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_eical_e_mle"+Integer.toString(i), 250, 0.0, 0.50 ));
 	h_pr_eocal_e.add( new H1F("h_"+s_run_number+"_pr_eocal_e_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_eocal_e_mle"+Integer.toString(i), 250, 0.0, 0.50 ));

	h2_pr_ftof_l2_tof.add( new H2F("h2_"+s_run_number+"_pr_ftof_l2_tof_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_ftof_l2_tof_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_tof, max_tof ) );
	h_pr_ftof_l2_beta_time.add( new H1F("h_"+s_run_number+"_pr_ftof_l2_betatime_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_ftof_l2_betatime_mle"+Integer.toString(i), 400, min_b, max_b ) );
 	h2_pr_ftof_l2_masstimep.add( new H2F("h2_"+s_run_number+"_pr_ftof_l2_masstimep_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_ftof_l2_masstimep_mle"+Integer.toString(i), 500, 0.0, 1.2, 500, min_p, max_p ));
 	h_pr_ftof_l2_masstime.add( new H1F("h_"+s_run_number+"_pr_ftof_l2_masstime_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_ftof_l2_masstime_mle"+Integer.toString(i), 500, 0.0, 2.0 ));
	h2_pr_ftof_l2_deltabeta.add( new H2F("h2_"+s_run_number+"_pr_ftof_l2_deltabeta_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_ftof_l2_deltabeta_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_delb, max_delb ));
	h2_pr_ftof_l2_betap.add( new H2F("h2_"+s_run_number+"_pr_ftof_l2_betap_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_ftof_l2_betap_mle"+Integer.toString(i),500, min_p, max_p, 500, min_b, max_b ));
	h2_pr_ftof_l2_deltimep.add( new H2F("h2_"+s_run_number+"_pr_ftof_l2_deltimep_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_ftof_l2_deltimep_mle"+Integer.toString(i),500, min_p, max_p, 500, min_delt, max_delt ));

	for( int s = 0; s < 6; s++ ){                                                                                                                                                          
            String sect = Integer.toString(s);                                                                                                                                                 
            h2_beta_all_pos_sect_ftof_l2.add( new H2F("h2_"+s_run_number+"_beta_all_pos_"+sect+"_ftof_l2","h2_"+s_run_number+"_beta_all_pos_"+sect+"_ftof_l2",250, 0.0, 6.5, 250, 0.01, 1.2)); 
            h2_beta_all_pos_sect_ftof_l1.add( new H2F("h2_"+s_run_number+"_beta_all_pos_"+sect+"_ftof_l1","h2_"+s_run_number+"_beta_all_pos_"+sect+"_ftof_l1",250, 0.0, 6.5, 250, 0.01, 1.2)); 

            h2_beta_all_neg_sect_ftof_l2.add( new H2F("h2_"+s_run_number+"_beta_all_neg_"+sect+"_ftof_l2","h2_"+s_run_number+"_beta_all_neg_"+sect+"_ftof_l2",250, 0.0, 6.5, 250, 0.01, 1.2)); 
            h2_beta_all_neg_sect_ftof_l1.add( new H2F("h2_"+s_run_number+"_beta_all_neg_"+sect+"_ftof_l1","h2_"+s_run_number+"_beta_all_neg_"+sect+"_ftof_l1",250, 0.0, 6.5, 250, 0.01, 1.2)); 

            h2_beta_all_pr_sect_ftof_l2.add( new H2F("h2_"+s_run_number+"_beta_all_pr_"+sect+"_ftof_l2","h2_"+s_run_number+"_beta_all_pr_"+sect+"_ftof_l2",250, 0.0, 6.5, 250, 0.01, 1.2));
            h2_beta_all_pr_sect_ftof_l1.add( new H2F("h2_"+s_run_number+"_beta_all_pr_"+sect+"_ftof_l1","h2_"+s_run_number+"_beta_all_pr_"+sect+"_ftof_l1",250, 0.0, 6.5, 250, 0.01, 1.2)); 

            h2_beta_all_pip_sect_ftof_l2.add( new H2F("h2_"+s_run_number+"_beta_all_pip_"+sect+"_ftof_l2","h2_"+s_run_number+"_beta_all_pip_"+sect+"_ftof_l2",250, 0.0, 6.5, 250, 0.01, 1.2)); 
            h2_beta_all_pip_sect_ftof_l1.add( new H2F("h2_"+s_run_number+"_beta_all_pip_"+sect+"_ftof_l1","h2_"+s_run_number+"_beta_all_pip_"+sect+"_ftof_l1",250, 0.0, 6.5, 250, 0.01, 1.2));

            h2_beta_all_kp_sect_ftof_l2.add( new H2F("h2_"+s_run_number+"_beta_all_kp_"+sect+"_ftof_l2","h2_"+s_run_number+"_beta_all_kp_"+sect+"_ftof_l2",250, 0.0, 6.5, 250, 0.01, 1.2));
            h2_beta_all_kp_sect_ftof_l1.add( new H2F("h2_"+s_run_number+"_beta_all_kp_"+sect+"_ftof_l1","h2_"+s_run_number+"_beta_all_kp_"+sect+"_ftof_l1",250, 0.0, 6.5, 250, 0.01, 1.2)); 
        }     


    }

    public void createProtonSectorHistograms( int sector, int max_cuts ){
	h_pr_sect_p.add( new Vector<H1F>() );
	h_pr_sect_masstime.add( new Vector<H1F>() );
 	h_pr_sect_theta.add( new Vector<H1F>() );
	h_pr_sect_phi.add( new Vector<H1F>() );
	h2_pr_sect_betap.add( new Vector<H2F>() );
	h2_pr_el_sect_betap.add( new Vector<H2F>() );
	h2_pr_sect_deltabeta.add( new Vector<H2F>() );
	h2_pr_sect_deltimep.add( new Vector<H2F>() );
	h2_pr_sect_masstimep.add( new Vector<H2F>() );
	
	h2_pr_sect_ectotp.add( new Vector<H2F>() );
	h_pr_sect_pcal_e.add( new Vector<H1F>() );
	h_pr_sect_ftof_l1_e.add( new Vector<H1F>() );
	h_pr_sect_ftof_l2_e.add( new Vector<H1F>() );	
	h_pr_sect_eical_e.add( new Vector<H1F>() );
	h_pr_sect_eocal_e.add( new Vector<H1F>() );
	h_pr_sect_deltime.add( new Vector<H1F>() );


	h_pr_sect_ftof_l2_masstime.add( new Vector<H1F>() );
	h2_pr_sect_ftof_l2_betap.add( new Vector<H2F>() );
	h2_pr_sect_ftof_l2_deltabeta.add( new Vector<H2F>() );
	h2_pr_sect_ftof_l2_deltimep.add( new Vector<H2F>() );
	h2_pr_sect_ftof_l2_masstimep.add( new Vector<H2F>() );

	System.out.println(" >> " + h_pr_sect_eical_e.size() );
	for( int i = 0; i <= max_cuts; i++ ){
	    (h_pr_sect_p.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_p_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_p_mle"+Integer.toString(i), 200, min_p, max_p ) );  
	    (h_pr_sect_masstime.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_masstime_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_masstime_mle"+Integer.toString(i), 550, 0.0 , 2.0) );  
	    (h2_pr_sect_betap.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_betap_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_betap_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_b, max_b ) );  
	    (h2_pr_el_sect_betap.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_el_" + Integer.toString(sector) + "_betap_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_el_" + Integer.toString(sector)  + "_betap_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_b, max_b ) );  
	    (h2_pr_sect_deltabeta.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_deltabeta_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_deltabeta_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_delb, max_delb ) );  
	    (h2_pr_sect_deltimep.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_deltimep_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_deltimep_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_delt, max_delt ) );  
	    (h2_pr_sect_masstimep.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_masstimep_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_masstimep_mle"+Integer.toString(i), 500, 0.0, 1.2, 500, min_p, max_p ) );  

	    (h2_pr_sect_ectotp.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_ectotp_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_ectotp_mle"+Integer.toString(i), 400, 0.0, 4.0, 400, 0.0, 0.5 ) );  
	    (h_pr_sect_pcal_e.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_pcal_e_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_pcal_e_mle"+Integer.toString(i), 400, 0.0, 0.0080 ) );  
	    (h_pr_sect_ftof_l1_e.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_ftof_l1_e_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_ftof_l1_e_mle"+Integer.toString(i), 400, 0.0, 0.50 ) );  
	    (h_pr_sect_ftof_l2_e.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_ftof_l2_e_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_ftof_l2_e_mle"+Integer.toString(i), 400, 0.0, 0.50 ) );  
	    (h_pr_sect_eical_e.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_eical_e_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_eical_e_mle"+Integer.toString(i), 400, 0.0, 0.50 ) );  
	    (h_pr_sect_eocal_e.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_eocal_e_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_eocal_e_mle"+Integer.toString(i), 400, 0.0, 0.50 ) );  
	    (h_pr_sect_deltime.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_deltime_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_deltime_mle"+Integer.toString(i), 100, min_delt, max_delt ) );  


	    (h_pr_sect_ftof_l2_masstime.get(sector)).add( new H1F("h_"+s_run_number+"_pr_" + Integer.toString(sector) + "_ftof_l2_masstime_mle"+Integer.toString(i),"h_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_ftof_l2_masstime_mle"+Integer.toString(i), 500, 0.0 , 2.0) );  	    	    
	    (h2_pr_sect_ftof_l2_betap.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_ftof_l2_betap_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_ftof_l2_betap_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_b, max_b ) );  
	    (h2_pr_sect_ftof_l2_deltabeta.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_ftof_l2_deltabeta_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_ftof_l2_deltabeta_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_delb, max_delb ) );  
	    (h2_pr_sect_ftof_l2_deltimep.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_ftof_l2_deltimep_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_ftof_l2_deltimep_mle"+Integer.toString(i), 500, min_p, max_p, 500, min_delt, max_delt ) );  
	    (h2_pr_sect_ftof_l2_masstimep.get(sector)).add( new H2F("h2_"+s_run_number+"_pr_" + Integer.toString(sector) + "_ftof_l2_masstimep_mle"+Integer.toString(i),"h2_"+s_run_number+"_pr_" + Integer.toString(sector)  + "_ftof_l2_masstimep_mle"+Integer.toString(i), 500, 0.0, 1.2, 500, min_p, max_p ) );  

	}
    }

    public void createProtonFTOFHistograms( int sector, int panel, int max_cuts ){
	
	for( int s = 0; s < sector; s++ ){
	    Vector<Vector<H2F >> p_temp = new Vector<Vector<H2F >>();	    
	    Vector<Vector<H1F >> p_temp2 = new Vector<Vector<H1F >>();	    
	    for( int p = 0; p < panel; p++ ){
		p_temp.add(new Vector<H2F>() );
		p_temp2.add(new Vector<H1F>() );
	    }
	    m_pr_sect_panel_deltp.put( s, p_temp);
	    m_pr_sect_panel_deltime.put(s, p_temp2);
	}

	for( int s = 0; s < sector; s++ ){	    
	    for( int p = 0; p < panel; p++ ){
		for( int c = 0; c < max_cuts; c++ ){
		    Vector< Vector<H2F> > mh_temp = m_pr_sect_panel_deltp.get(s);
		    Vector< Vector<H1F> > mh_temp2 = m_pr_sect_panel_deltime.get(s);
		    mh_temp.get(p).add(new H2F("h_"+s_run_number+"_pr_"+Integer.toString(s)+"_"+Integer.toString(p)+"_cutlvl"+Integer.toString(c),"h_"+s_run_number+"_pr_"+Integer.toString(s)+"_"+Integer.toString(p)+"_cutlvl"+Integer.toString(c), 200, 0.0, 6.5, 200, -5.0, 5.0) );
		    mh_temp2.get(p).add(new H1F("h_"+s_run_number+"_pr_deltime_"+Integer.toString(s)+"_"+Integer.toString(p)+"_cutlvl"+Integer.toString(c),"h_"+s_run_number+"_pr_deltime_"+Integer.toString(s)+"_"+Integer.toString(p)+"_cutlvl"+Integer.toString(c), 50, -5.0, 5.0) );
		}
	    }
	}				   
    }


    public void protonHistoToHipo(){

	dir.mkdir("/proton/mle/h_pr_p/");
	dir.cd("/proton/mle/h_pr_p/");
	for( H1F h_temp : h_pr_p ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    //c_temp.draw(h_temp);
	    ////c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h2_pr_tof/");
	dir.cd("/proton/mle/h2_pr_tof/");
	for( H2F h_temp : h2_pr_tof ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("TOF [ns]");	    
	    //c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h_pr_rpath/");
	dir.cd("/proton/mle/h_pr_rpath/");
	for( H1F h_temp : h_pr_rpath ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h_pr_beta_time/");
	dir.cd("/proton/mle/h_pr_beta_time/");
	for( H1F h_temp : h_pr_beta_time ){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h_pr_masstime/");
	dir.cd("/proton/mle/h_pr_masstime/");
	for( H1F h_temp : h_pr_masstime ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Mass [GeV/c]");
	    c_temp.draw(h_temp);
	    //F1D f_mass = Calculator.fitHistogram(h_temp,0.6);
	    //f_mass.setLineColor(2);
	    //f_mass.setLineWidth(3);
	    //f_mass.setOptStat(111110);   
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h2_pr_masstimep/");
	dir.cd("/proton/mle/h2_pr_masstimep/");
	for( H2F h_temp : h2_pr_masstimep ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Mass [GeV/c]");
	    h_temp.setTitleY("p [GeV/c^2]");
	    //c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h2_pr_dchit_R1_xy/");
	dir.cd("/proton/mle/h2_pr_dchit_R1_xy/");
	for( H2F h_temp : h2_pr_dchit_R1_xy){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("DC X [cm]");
	    h_temp.setTitleX("DC Y [cm]"); 
	    //c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}
	dir.mkdir("/proton/mle/h2_pr_dchit_R2_xy/");
	dir.cd("/proton/mle/h2_pr_dchit_R2_xy/");
	for( H2F h_temp : h2_pr_dchit_R2_xy){
	    dir.addDataSet(h_temp);
	}
	dir.mkdir("/proton/mle/h2_pr_dchit_R3_xy/");
	dir.cd("/proton/mle/h2_pr_dchit_R3_xy/");
	for( H2F h_temp : h2_pr_dchit_R3_xy){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("DC X [cm]");
	    h_temp.setTitleX("DC Y [cm]"); 
	    //c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h2_pr_vzphi/");
	dir.cd("/proton/mle/h2_pr_vzphi/");
	for( H2F h_temp : h2_pr_vzphi){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h_pr_vz_mod/");
	dir.cd("/proton/mle/h_pr_vz_mod/");
	for( H1F h_temp : h_pr_vz_mod){
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h_pr_vz/");
	dir.cd("/proton/mle/h_pr_vz/");
	for( H1F h_temp : h_pr_vz){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    //F1D v_fit  = Calculator.fitHistogram(h_temp, 0.3);
	    //v_fit.setLineColor(2);
	    //v_fit.setLineWidth(3);
	    //c_temp.draw(h_temp);	    
	    //c_temp.draw(v_fit,"same");
	    dir.addDataSet(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	}
       
	for( int j = 0; j < h_pr_vz_mod.size(); j++ ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_pr_vz.get(j).setLineColor(2);
	    h_pr_vz.get(j).setLineWidth(3);
	    h_pr_vz_mod.get(j).setLineColor(2);
	    h_pr_vz_mod.get(j).setLineWidth(3);
	    //c_temp.draw(h_pr_vz.get(j));
	    //c_temp.draw(h_pr_vz_mod.get(j));
	    //c_temp.save(savepath+"pr_vzMOD"+".png");
	}


	dir.mkdir("/proton/mle/h2_pr_deltabeta/");
	dir.cd("/proton/mle/h2_pr_deltabeta/");
	for( H2F h_temp : h2_pr_deltabeta ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#delta #beta");	    
	    //c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}
    
	dir.mkdir("/proton/mle/h2_pr_ectotp/");
	dir.cd("/proton/mle/h2_pr_ectotp/");
	for( H2F h_temp : h2_pr_ectotp){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("EC SF"); 
	    //c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h_pr_pcal_e/");
	dir.cd("/proton/mle/h_pr_pcal_e/");
	for( H1F h_temp : h_pr_pcal_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("PCAL Energy [GeV]");
	    //c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h_pr_ftof_l1_e/");
	dir.cd("/proton/mle/h_pr_ftof_l1_e/");
	for( H1F h_temp : h_pr_ftof_l1_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("FTOF L1 Energy [GeV]");
	    //c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}


	dir.mkdir("/proton/mle/h_pr_ftof_l2_e/");
	dir.cd("/proton/mle/h_pr_ftof_l2_e/");
	for( H1F h_temp : h_pr_ftof_l2_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("FTOF L2 Energy [GeV]");
	    //c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h_pr_eical_e/");
	dir.cd("/proton/mle/h_pr_eical_e/");
	for( H1F h_temp : h_pr_eical_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("ECAL EI [GeV]");
	    //c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h_pr_eocal_e/");
	dir.cd("/proton/mle/h_pr_eocal_e/");
	for( H1F h_temp : h_pr_eocal_e){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("ECAL EO [GeV]");
	    //c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}


	dir.mkdir("/proton/mle/h_pr_sect_panel_deltimep/");
	dir.cd("/proton/mle/h_pr_sect_panel_deltimep/");
	for( int s = 0; s < 6; s++){
	    //EmbeddedCanvas c_sp_deltp = new EmbeddedCanvas();
	    //c_sp_deltp.setSize(1600,800);
 	    //c_sp_deltp.divide(9,6);
	    for( int p = 0; p < 55; p++ ){
		//c_sp_deltp.cd(p);
		//m_pr_sect_panel_deltp.get(s).get(p).get(0).setTitleX("S" + Integer.toString(s) + " P" + Integer.toString(p));
		//c_sp_deltp.draw(m_pr_sect_panel_deltp.get(s).get(p).get(0),"colz");
		//System.out.println(" >> S " + s + " P " + p + " " + m_pr_sect_panel_deltp.get(s).get(p).get(0).getEntries() );
		//for(int c = 0; c < 3; c++){
		dir.addDataSet(m_pr_sect_panel_deltp.get(s).get(p).get(0));
		//}
	    }
	    //c_sp_deltp.save(savepath+"h2_"+s_run_number+"_pr_"+Integer.toString(s)+"_panel_mle0.png");
	}


	BufferedWriter writer = null;
	try{
	    String deltime_text = "pr_delta_time_sector_panel_values.txt";
	    writer = new BufferedWriter( new FileWriter(deltime_text) );


	dir.mkdir("/proton/mle/h_pr_sect_panel_deltime/");
	dir.cd("/proton/mle/h_pr_sect_panel_deltime/");
	for( int s = 0; s < 6; s++){
	    EmbeddedCanvas c_sp_deltime = new EmbeddedCanvas();
	    c_sp_deltime.setSize(1600,800);
 	    c_sp_deltime.divide(9,8);
	    for( int p = 0; p < 72; p++ ){
		c_sp_deltime.cd(p);
		m_pr_sect_panel_deltime.get(s).get(p).get(0).setTitleX("S" + Integer.toString(s) + " P" + Integer.toString(p));
		c_sp_deltime.draw(m_pr_sect_panel_deltime.get(s).get(p).get(0),"colz");
		//System.out.println(" >> S " + s + " P " + p + " " + m_pr_sect_panel_deltime.get(s).get(p).get(0).getEntries() );
		//for(int c = 0; c < 3; c++){
		dir.addDataSet(m_pr_sect_panel_deltime.get(s).get(p).get(0));

		////////////////////////////////////////////////////////////
		//FIT PANEL DELTA TIME WITH GAUS TO EXTRACT MEAN
		F1D f_deltime = Calculator.fitHistogram(m_pr_sect_panel_deltime.get(s).get(p).get(0));
		double time_mean = f_deltime.getParameter(1);
		double time_sigma = f_deltime.getParameter(2);
		writer.write(s + " " + p + " " + time_mean + " " + time_sigma + "\n");

	    }
	    //c_sp_deltime.save(savepath+"h2_"+s_run_number+"_pr_deltime_"+Integer.toString(s)+"_panel_mle0.png");
	}

	}
	catch ( IOException e)
 	    {
		System.out.println(">> ERROR WRITING TO FILE ");
	    }	
	finally
	    { 
		try
		    {
			if ( writer != null)
			    writer.close( );
		    }
		catch ( IOException e)
		    {
			System.out.println(">> ANOTHER ERROR");
		    }
	    }    
    
	
	dir.mkdir("/proton/mle/h2_pr_deltimep/");
	dir.cd("/proton/mle/h2_pr_deltimep/");
	for( H2F h_temp : h2_pr_deltimep ){
	    //EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    //c_temp.setSize(800,800);
	    //h_temp.setTitleX("p [GeV]");
	    //h_temp.setTitleY("#delta time of flight");	    
	    //c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}


	F1D f_beta_cuttop = new F1D("f_betacut_top","x/sqrt(x*x + [a]*[a])",0.1,5.5);
	f_beta_cuttop.setParameter(0,PhysicalConstants.mass_proton*PhysicalConstants.mass_proton);	
	f_beta_cuttop.setLineColor(2);
	f_beta_cuttop.setLineWidth(3);
				
	dir.mkdir("/proton/mle/h2_pr_betap/");
	dir.cd("/proton/mle/h2_pr_betap/");
	for( H2F h_temp : h2_pr_betap ){
	    //EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    //c_temp.setSize(800,800);
	    //h_temp.setTitleX("p [GeV]");
	    //h_temp.setTitleY("#beta");
	    //c_temp.draw(h_temp,"colz");
	    //c_temp.draw(f_beta_cuttop,"same");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///SECTOR BASED PLOTS
	F1D f_beta_pr = new F1D("f_betacut_pr","x/sqrt(x*x + [a]*[a])",0.1,5.5);
	f_beta_pr.setParameter(0,PhysicalConstants.mass_proton*PhysicalConstants.mass_proton);	
	f_beta_pr.setLineColor(2);

	dir.mkdir("/proton/mle/h2_pr_sect_betap/");
	dir.cd("/proton/mle/h2_pr_sect_betap/");
	for( int i = 0; i < h2_pr_sect_betap.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_betap.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
		//EmbeddedCanvas c_temp = new EmbeddedCanvas();
		//c_temp.setSize(800,800);
		//v_temp.get(j).setTitleX("p [GeV]");
		//v_temp.get(j).setTitleY("Total Energy (PCAL + ECAL ) [GeV]");		
		//c_temp.draw(v_temp.get(j),"colz");
		//c_temp.draw(f_beta_pr,"same");
		//c_temp.save(savepath+v_temp.get(j).getTitle()+".png");
		
	    }
	}

	dir.mkdir("/proton/mle/h2_pr_el_sect_betap/");
	dir.cd("/proton/mle/h2_pr_el_sect_betap/");
	for( int i = 0; i < h2_pr_el_sect_betap.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_el_sect_betap.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
		//EmbeddedCanvas c_temp = new EmbeddedCanvas();
		//c_temp.setSize(800,800);
		//v_temp.get(j).setTitleX("p [GeV]");
		//v_temp.get(j).setTitleY("Total Energy (PCAL + ECAL ) [GeV]");
		//c_temp.draw(v_temp.get(j),"colz");
		//c_temp.draw(f_beta_pr,"same");
		//c_temp.save(savepath+v_temp.get(j).getTitle()+".png");
	    }
	}

	dir.mkdir("/proton/mle/h2_pr_sect_deltabeta/");
	dir.cd("/proton/mle/h2_pr_sect_deltabeta/");
	for( int i = 0; i < h2_pr_sect_deltabeta.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_deltabeta.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));

	    }
	}

	dir.mkdir("/proton/mle/h2_pr_sect_deltimep/");
	dir.cd("/proton/mle/h2_pr_sect_deltimep/");
	for( int i = 0; i < h2_pr_sect_deltimep.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_deltimep.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
	    }
	}	
    
	dir.mkdir("/proton/mle/h_pr_sect_masstime/");
 	dir.cd("/proton/mle/h_pr_sect_masstime/");
	for( int i = 0; i < h_pr_sect_masstime.size(); i++ ){
 	   Vector<H1F> v_temp = h_pr_sect_masstime.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("Mass [GeV]");
		//c_temp.draw(v_temp.get(j));
		//F1D f_mass = Calculator.fitHistogram(v_temp.get(j), 0.3);
		//f_mass.setLineColor(2);
		//f_mass.setLineWidth(3);
		//f_mass.setOptStat(111110);   
		//c_temp.draw(f_mass,"same");
		//c_temp.save(savepath+v_temp.get(j).getTitle()+".png");		
	    }
	}

	dir.mkdir("/proton/mle/h2_pr_sect_masstimep/");
	dir.cd("/proton/mle/h2_pr_sect_masstimep/");
	for( int i = 0; i < h2_pr_sect_masstimep.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_masstimep.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("Mass [GeV/c]");
		v_temp.get(j).setTitleY("p [GeV/c^2]"); 
		//c_temp.draw(v_temp.get(j),"colz");
		//c_temp.save(savepath+v_temp.get(j).getTitle()+".png");	       
		dir.addDataSet(v_temp.get(j));
	    }
	}


	dir.mkdir("/proton/mle/h2_pr_sect_ectotp/");
	dir.cd("/proton/mle/h2_pr_sect_ectotp/");
	for( int i = 0; i < h2_pr_sect_ectotp.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_ectotp.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("p [GeV]");
		v_temp.get(j).setTitleY("EC SF"); 
		//c_temp.draw(v_temp.get(j),"colz");
		//c_temp.save(savepath+v_temp.get(j).getTitle()+".png");	       
		dir.addDataSet(v_temp.get(j));
	    }
	}

	dir.mkdir("/proton/mle/h_pr_sect_pcal_e/");
	dir.cd("/proton/mle/h_pr_sect_pcal_e/");
	for( int i = 0; i < h_pr_sect_pcal_e.size(); i++ ){
 	   Vector<H1F> v_temp = h_pr_sect_pcal_e.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("PCAL [GeV]");
		//c_temp.draw(v_temp.get(j));
		//c_temp.save(savepath+v_temp.get(j).getTitle()+".png");	       
		dir.addDataSet(v_temp.get(j));
	    }
	}


	dir.mkdir("/proton/mle/h_pr_sect_ei_e/");
	dir.cd("/proton/mle/h_pr_sect_ei_e/");
	for( int i = 0; i < h_pr_sect_eical_e.size(); i++ ){
 	   Vector<H1F> v_temp = h_pr_sect_eical_e.get(i);       
	   for( int j = 0; j < v_temp.size(); j++){		
	       EmbeddedCanvas c_temp = new EmbeddedCanvas();
	       c_temp.setSize(800,800);
	       v_temp.get(j).setTitleX("EC EI [GeV]");
	       c_temp.draw(v_temp.get(j));
	       //c_temp.save(savepath+v_temp.get(j).getTitle()+".png");	       
	       dir.addDataSet(v_temp.get(j));
	    }
	}

 
	dir.mkdir("/proton/mle/h_pr_sect_eo_e/");
	dir.cd("/proton/mle/h_pr_sect_eo_e/");
	for( int i = 0; i < h_pr_sect_eocal_e.size(); i++ ){
 	   Vector<H1F> v_temp = h_pr_sect_eocal_e.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
	       EmbeddedCanvas c_temp = new EmbeddedCanvas();
	       c_temp.setSize(800,800);
	       v_temp.get(j).setTitleX("EC EO [GeV]");
	       //c_temp.draw(v_temp.get(j));
	       //c_temp.save(savepath+v_temp.get(j).getTitle()+".png");	       
	       dir.addDataSet(v_temp.get(j));
	    }
	}

	dir.mkdir("/proton/mle/h_pr_ftof_l2_masstime/");
	dir.cd("/proton/mle/h_pr_ftof_l2_masstime/");
	for( H1F h_temp : h_pr_ftof_l2_masstime ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Mass [GeV/c]");
	    //c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h2_pr_ftof_l2_masstimep/");
	dir.cd("/proton/mle/h2_pr_ftof_l2_masstimep/");
	for( H2F h_temp : h2_pr_ftof_l2_masstimep ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Mass [GeV/c]");
	    h_temp.setTitleY("p [GeV/c^2]");
	    //c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h2_pr_ftof_l2_deltimep/");
	dir.cd("/proton/mle/h2_pr_ftof_l2_deltimep/");
	for( H2F h_temp : h2_pr_ftof_l2_deltimep ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#delta time of flight");	    
	    //c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h2_pr_ftof_l2_deltabeta/");
	dir.cd("/proton/mle/h2_pr_ftof_l2_deltabeta/");
	for( H2F h_temp : h2_pr_ftof_l2_deltabeta ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#delta #beta");	    
	    //c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h2_pr_ftof_l2_betap/");
	dir.cd("/proton/mle/h2_pr_ftof_l2_betap/");
	for( H2F h_temp : h2_pr_ftof_l2_betap ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#beta");
	    //c_temp.draw(h_temp,"colz");
	    //c_temp.draw(f_beta_cuttop,"same");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h2_pr_ftof_l2_tof/");
	dir.cd("/proton/mle/h2_pr_ftof_l2_tof/");
	for( H2F h_temp : h2_pr_ftof_l2_tof ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("TOF [ns]");	    
	    //c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}

	dir.mkdir("/proton/mle/h_pr_ftof_l2_beta_time/");
	dir.cd("/proton/mle/h_pr_ftof_l2_beta_time/");
	for( H1F h_temp : h_pr_ftof_l2_beta_time ){
	    dir.addDataSet(h_temp);
	}


	dir.mkdir("/proton/mle/h_pr_sect_ftof_l2_masstime/");
	dir.cd("/proton/mle/h_pr_sect_ftof_l2_masstime/");
	for( int i = 0; i < h_pr_sect_ftof_l2_masstime.size(); i++ ){
 	   Vector<H1F> v_temp = h_pr_sect_ftof_l2_masstime.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
	    }
	}

	dir.mkdir("/proton/mle/h2_pr_sect_ftof_l2_betap/");
	dir.cd("/proton/mle/h2_pr_sect_ftof_l2_betap/");
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

	dir.mkdir("/proton/mle/h2_pr_sect_ftof_l2_deltabeta/");
	dir.cd("/proton/mle/h2_pr_sect_ftof_l2_deltabeta/");
	for( int i = 0; i < h2_pr_sect_ftof_l2_deltabeta.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_ftof_l2_deltabeta.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));

	    }
	}

	dir.mkdir("/proton/mle/h2_pr_sect_ftof_l2_deltimep/");
	dir.cd("/proton/mle/h2_pr_sect_ftof_l2_deltimep/");
	for( int i = 0; i < h2_pr_sect_ftof_l2_deltimep.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_ftof_l2_deltimep.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		dir.addDataSet(v_temp.get(j));
	    }
	}	

	dir.mkdir("/proton/mle/h2_pr_sect_ftof_l2_masstimep/");
	dir.cd("/proton/mle/h2_pr_sect_ftof_l2_masstimep/");
	for( int i = 0; i < h2_pr_sect_ftof_l2_masstimep.size(); i++ ){
 	   Vector<H2F> v_temp = h2_pr_sect_ftof_l2_masstimep.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("Mass [GeV/c]");
		v_temp.get(j).setTitleY("p [GeV/c^2]"); 
		//c_temp.draw(v_temp.get(j),"colz");
		//c_temp.save(savepath+v_temp.get(j).getTitle()+".png");	       
		dir.addDataSet(v_temp.get(j));
	    }
	}
	
	dir.mkdir("/proton/mle/h2_pr_conflvl/");
	dir.cd("/proton/mle/h2_pr_conflvl/");
	for( H1F h_temp :  h_pr_conflvl ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("Confidence Level");
	    //c_temp.draw(h_temp);
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}	

	dir.mkdir("/proton/mle/h2_poscharge_dc_traj/");
	dir.cd("/proton/mle/h2_poscharge_dc_traj/");
	dir.addDataSet(h2_poscharge_dc_R1_traj);
	dir.addDataSet(h2_poscharge_dc_R2_traj);
	dir.addDataSet(h2_poscharge_dc_R3_traj);
	dir.addDataSet(h2_poscharge_dc_R1_traj_rot);
	dir.addDataSet(h2_poscharge_dc_R2_traj_rot);
	dir.addDataSet(h2_poscharge_dc_R3_traj_rot);
	

	dir.mkdir("/proton/mle/h2_pr_ptheta/");
	dir.cd("/proton/mle/h2_pr_ptheta/");
	for( H2F h_temp :  h2_pr_ptheta ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#theta [deg]");
	    //c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}	

	dir.mkdir("/proton/mle/h2_pr_pphi/");
	dir.cd("/proton/mle/h2_pr_pphi/");
	for( H2F h_temp :  h2_pr_pphi ){
	    EmbeddedCanvas c_temp = new EmbeddedCanvas();
	    c_temp.setSize(800,800);
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("#phi [deg]");
	    //c_temp.draw(h_temp,"colz");
	    //c_temp.save(savepath+h_temp.getTitle()+".png");
	    dir.addDataSet(h_temp);
	}	

	dir.mkdir("/proton/mle/h_pr_sect_deltime/");
	dir.cd("/proton/mle/h_pr_sect_deltime/");
	for( int i = 0; i < h_pr_sect_deltime.size(); i++ ){
 	   Vector<H1F> v_temp = h_pr_sect_deltime.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("#delta t [ns]");
		//F1D f_deltime = Calculator.fitHistogram(v_temp.get(j),0.6);
		//f_deltime.setLineWidth(3);
		//f_deltime.setLineColor(2);
		//f_deltime.setOptStat(111110);
		//c_temp.draw(v_temp.get(j));
		//c_temp.draw(f_deltime,"same");
		//c_temp.save(savepath+v_temp.get(j).getTitle()+".png");	       
		dir.addDataSet(v_temp.get(j));
	    }
	}

	dir.mkdir("/proton/mle/h2_beta_all_pos/");
	dir.cd("/proton/mle/h2_beta_all_pos/");
	dir.addDataSet(h_beta_all_pos);


 	F1D f_pr = new F1D("f_pr","x/sqrt(x*x + [a]*[a])",0.1,5.5);
 	F1D f_kp = new F1D("f_kp","x/sqrt(x*x + [a]*[a])",0.1,5.5);
 	F1D f_pp = new F1D("f_pp","x/sqrt(x*x + [a]*[a])",0.1,5.5);
 	F1D f_d = new F1D("f_d","x/sqrt(x*x + [a]*[a])",0.1,5.5);
	f_pr.setParameter(0,PhysicalConstants.mass_proton*PhysicalConstants.mass_proton);	
	f_pr.setLineColor(2);
	f_pr.setLineWidth(3);
	f_kp.setParameter(0,PhysicalConstants.mass_kaon*PhysicalConstants.mass_kaon);	
	f_kp.setLineColor(2);
	f_kp.setLineWidth(3);
	f_pp.setParameter(0,PhysicalConstants.mass_pion*PhysicalConstants.mass_pion);	
	f_pp.setLineColor(2);
	f_pp.setLineWidth(3);
	f_d.setParameter(0,PhysicalConstants.mass_deut*PhysicalConstants.mass_deut);	
	f_d.setLineColor(2);
	f_d.setLineWidth(3);
	
	EmbeddedCanvas c_beta_all = new EmbeddedCanvas();
	c_beta_all.setSize(800,800);
	h_beta_all_pos.setTitleX("p [GeV]");
	h_beta_all_pos.setTitleY("#beta ");
	c_beta_all.draw(h_beta_all_pos,"colz");
	c_beta_all.draw(f_pr,"same");
	c_beta_all.draw(f_kp,"same");
	c_beta_all.draw(f_pp,"same");
	c_beta_all.draw(f_d,"same");
	//c_beta_all.save(savepath+h_beta_all_pos.getTitle()+".png");

	dir.mkdir("/proton/mle/h2_beta_all_pos_sect_ftof_l2/");
	dir.cd("/proton/mle/h2_beta_all_pos_sect_ftof_l2/");
	for( int s = 0; s < h2_beta_all_pos_sect_ftof_l2.size(); s++ ){
	    dir.addDataSet(h2_beta_all_pos_sect_ftof_l2.get(s));
	}

	dir.mkdir("/proton/mle/h2_beta_all_pos_sect_ftof_l1/");
	dir.cd("/proton/mle/h2_beta_all_pos_sect_ftof_l1/");
	for( int s = 0; s < h2_beta_all_pos_sect_ftof_l1.size(); s++ ){
	    dir.addDataSet(h2_beta_all_pos_sect_ftof_l1.get(s));
	}

	dir.mkdir("/proton/mle/h2_test/");
	dir.cd("/proton/mle/h2_test/");
	dir.addDataSet(h_test_phi);
	EmbeddedCanvas ctest = new EmbeddedCanvas();
	ctest.setSize(800,800);
	h_test_phi.setTitleX("Mass [GeV]");
	ctest.draw(h_test_phi);
	//ctest.save(savepath+h_test_phi.getTitle()+".png");

	dir.mkdir("/proton/mle/h2_betap_sect_proton_ftof_l1/");                                                                                                                                          
        dir.cd("/proton/mle/h2_betap_sect_proton_ftof_l1/");                                                                                                                                             
        for(H2F h2_temp : h2_beta_all_pr_sect_ftof_l1 ){                                                                                                                                                 
            dir.addDataSet(h2_temp);                                                                                                                                                                     
        }                                                                                                                                                                                                                                                                                                                                                                                                         
        dir.mkdir("/proton/mle/h2_betap_sect_proton_ftof_l2/");                                                                                                                                          
        dir.cd("/proton/mle/h2_betap_sect_proton_ftof_l2/");                                                                                                                                             
        for(H2F h2_temp : h2_beta_all_pr_sect_ftof_l2 ){                                                                                                                                                 
            dir.addDataSet(h2_temp);                                                                                                                                                                     
        }                                                                                                                                                                                                                                                                                                                                                                                                         
        dir.mkdir("/proton/mle/h2_betap_sect_pip_ftof_l1/");                                                                                                                                             
        dir.cd("/proton/mle/h2_betap_sect_pip_ftof_l1/");                                                                                                                                                
        for(H2F h2_temp : h2_beta_all_pip_sect_ftof_l1 ){                                                                                                                                                
            dir.addDataSet(h2_temp);                                                                                                                                                                     
        }                                                                                                                                                                                                
                                                                                                                                                                                                         
        dir.mkdir("/proton/mle/h2_betap_sect_pip_ftof_l2/");                                                                                                                                             
        dir.cd("/proton/mle/h2_betap_sect_pip_ftof_l2/");                                                                                                                                                
        for(H2F h2_temp : h2_beta_all_pip_sect_ftof_l2 ){                                                                                                                                                
            dir.addDataSet(h2_temp);                                                                                                                                                                     
        }                                               

	dir.mkdir("/proton/mle/h2_betap_sect_kp_ftof_l1/");                                                                                                                                              
        dir.cd("/proton/mle/h2_betap_sect_kp_ftof_l1/");                                                                                                                                                 
        for(H2F h2_temp : h2_beta_all_kp_sect_ftof_l1 ){                                                                                                                                                 
            dir.addDataSet(h2_temp);                                                                                                                                                                     
        }                                                                                                                                                                                                                                                                                                                                                                                                         
        dir.mkdir("/proton/mle/h2_betap_sect_kp_ftof_l2/");                                                                                                                                              
        dir.cd("/proton/mle/h2_betap_sect_kp_ftof_l2/");                                                                                                                                                 
        for(H2F h2_temp : h2_beta_all_kp_sect_ftof_l2 ){                                                                                                                                                 
           dir.addDataSet(h2_temp);                                                                                                                                                                              }                              

	saveHipoOut();

    }

    public void saveHipoOut(){
	dir.writeFile(savepath+"h_"+s_run_number+"_"+n_thread+"_proton_pid_clary.hipo");
    }


    public void viewHipoOut(){
	////////////////////////////////////////////////
	//TO READ DO THE FOLLOWING (ACCORDING TO NATHAN)
	//TDirector d = new TDirectory();
	//d.readFile(savepath+"h_proton.hipo");
	//TBrowser b = new TBrowser();
	//b.setDirectory(d);

	//dir.readFile("/u/home/bclary/CLAS12/phi_analysis/v2/v1/testing_cutlvl.hipo");
	TBrowser browser = new TBrowser(dir);

	//dir.ls();
	
    }



}
