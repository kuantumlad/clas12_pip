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

public class BPIDHistograms {

    private int run_number = -1;
    private String s_run_number = " ";
    private String n_thread = " ";

    private TDirectory dir;

    public BPIDHistograms(int temp_run, String temp_n_threads) {
	//constructor
	run_number = temp_run;
	s_run_number = Integer.toString(run_number);
	n_thread = temp_n_threads;
	dir = new TDirectory();

    }

    //CREATE HISTOGRAMS FOR CUT BASED HISTO HERE

    String savepath = "/home/bclary/CLAS12/pics/pid_clary/";

    H2F h2_el_dc_R1_allcharge = new H2F("h2_el_dc_R1_allcharge","h2_el_dc_R1_allcharge", 500, -95.0, 95.0, 500, -100.0, 100.0);
    H2F h2_el_dc_R2_allcharge = new H2F("h2_el_dc_R2_allcharge","h2_el_dc_R2_allcharge", 500, -150.0, 150.0, 500, -170.0, 170.0);
    H2F h2_el_dc_R3_allcharge = new H2F("h2_el_dc_R3_allcharge","h2_el_dc_R3_allcharge", 500, -200.0, 200.0, 500, -170.0, 170.0 );
    H1F h_el_cutrates = new H1F("h_"+s_run_number+"_el_cutrates","h_"+s_run_number+"_el_cutrates", 11, 0.0, 11.0);

   
    Vector<H1F> h_el_p_cutrates = new Vector<H1F>();

    Vector<H1F> h_el_p = new Vector<H1F>();

    Vector<H1F> h_el_p_test = new Vector<H1F>();

    Vector<H1F> h_el_theta = new Vector<H1F>();
    Vector<H1F> h_el_phi = new Vector<H1F>();
    Vector<H1F> h_el_vz = new Vector<H1F>();
    Vector<H1F> h_el_timing = new Vector<H1F>();
    Vector<H1F> h_el_nphe = new Vector<H1F>();
    Vector<H1F> h_el_ecei = new Vector<H1F>();
    Vector<H1F> h_el_eceo = new Vector<H1F>();
    Vector<H1F> h_el_ectot = new Vector<H1F>();
    Vector<H1F> h_el_pcal1 = new Vector<H1F>();
    Vector<H1F> h_el_pcal2 = new Vector<H1F>();
    Vector<H1F> h_el_pcaltot = new Vector<H1F>();
    Vector<H1F> h_el_edepdiff = new Vector<H1F>();
    Vector<H1F> h_el_ecu = new Vector<H1F>();
    Vector<H1F> h_el_ecv = new Vector<H1F>();
    Vector<H1F> h_el_ecw = new Vector<H1F>();

    Vector<H2F> h2_el_etotnphe = new Vector<H2F>();
    Vector<H2F> h2_el_ectotp = new Vector<H2F>();
    Vector<H2F> h2_el_ectotp2 = new Vector<H2F>();

    Vector<H2F> h2_el_pcalp = new Vector<H2F>();
    Vector<H2F> h2_el_eieo = new Vector<H2F>();

    Vector<H2F> h2_el_thetap = new Vector<H2F>();
    Vector<H2F> h2_el_phip = new Vector<H2F>();
    Vector<H2F> h2_el_pvz = new Vector<H2F>();
    Vector<H2F> h2_el_thetavz = new Vector<H2F>();
    Vector<H2F> h2_el_phivz = new Vector<H2F>();
    Vector<H2F> h2_el_pcalecal = new Vector<H2F>();
    Vector<H2F> h2_el_edepdiff = new Vector<H2F>();
    Vector<H2F> h2_el_ptime = new Vector<H2F>();    
    
    Vector<H2F> h2_el_echitxy = new Vector<H2F>();
    Vector<H2F> h2_el_pcalhitxy = new Vector<H2F>();
    Vector<H2F> h2_el_dchitxy = new Vector<H2F>();
    Vector<H2F> h2_el_dchitgxy = new Vector<H2F>();
    Vector<H2F> h2_el_dchit_R3_xy = new Vector<H2F>();
    Vector<H2F> h2_el_dchit_R3_gxy = new Vector<H2F>();

    Vector<H2F> h2_el_dchit_R1_lxy = new Vector<H2F>();
    Vector<H2F> h2_el_dchit_R3_lxy = new Vector<H2F>();

    Vector<H2F> h2_el_dchit_R1_traj = new Vector<H2F>();
    Vector<H2F> h2_el_dchit_R2_traj = new Vector<H2F>();
    Vector<H2F> h2_el_dchit_R3_traj = new Vector<H2F>();
   
    Vector<H2F> h2_el_dchit_R1_traj_rot = new Vector<H2F>();
    Vector<H2F> h2_el_dchit_R2_traj_rot = new Vector<H2F>();
    Vector<H2F> h2_el_dchit_R3_traj_rot = new Vector<H2F>();
       
    Vector<H1F> h_el_w = new Vector<H1F>();
    Vector<H1F> h_el_q2 = new Vector<H1F>();

    Vector<H2F> h2_el_wphi = new Vector<H2F>();
    Vector<H2F> h2_el_wq2 = new Vector<H2F>();

    Vector<Vector<H1F> > h_el_sect_p = new Vector< Vector<H1F> >();
    Vector<Vector<H1F> > h_el_sect_theta = new Vector< Vector<H1F> >();
    Vector<Vector<H1F> > h_el_sect_phi = new Vector< Vector<H1F> >();
    Vector<Vector<H1F> > h_el_sect_vz = new Vector< Vector<H1F> >();
    Vector<Vector<H1F> > h_el_sect_timing = new Vector< Vector<H1F> >();
    Vector<Vector<H1F> > h_el_sect_nphe = new Vector< Vector<H1F> >();
    Vector<Vector<H1F> > h_el_sect_pcal = new Vector< Vector<H1F> >();
    Vector<Vector<H1F> > h_el_sect_ecei = new Vector< Vector<H1F> >();
    Vector<Vector<H1F> > h_el_sect_eceo = new Vector< Vector<H1F> >();
    Vector<Vector<H1F> > h_el_sect_ectot = new Vector< Vector<H1F> >();
    Vector<Vector<H1F> > h_el_sect_w = new Vector< Vector<H1F> >();   
    Vector<Vector<H1F> > h_el_sect_q2 = new Vector< Vector<H1F> >();
    Vector<Vector<H2F> > h2_el_sect_etotnphe = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_ectotp = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_ectotp2 = new Vector< Vector<H2F> >();

    Vector<Vector<H2F> > h2_el_sect_pcalp = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_eieo = new Vector< Vector<H2F> >();

    Vector<Vector<H2F> > h2_el_sect_thetap = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_phip = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_thetavz = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_phivz = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_pvz = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_wp = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_pcalecal = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_ptime = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_q2w = new Vector< Vector<H2F> >();
    
    Vector<Vector<H2F> > h2_el_sect_pcalhitxy = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_ecalhitxy = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_ecalinhitxy = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_ecalouthitxy = new Vector< Vector<H2F> >();

    Vector<Vector<H2F> > h2_el_sect_pcal_sfxy = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_ecalin_sfxy = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_ecalout_sfxy = new Vector< Vector<H2F> >();
   
    Vector<Vector<H2F> > h2_el_sect_dc_R1_xy = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_dc_R3_xy = new Vector< Vector<H2F> >();

    Vector<Vector<H2F> > h2_el_sect_dc_R1_lxy = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_dc_R3_lxy = new Vector< Vector<H2F> >();

    Vector<Vector<H2F> > h2_el_sect_dc_R1_traj = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_dc_R2_traj = new Vector< Vector<H2F> >();
    Vector<Vector<H2F> > h2_el_sect_dc_R3_traj = new Vector< Vector<H2F> >();

    Vector<GraphErrors> g_sf_sect_meansfits = new Vector<GraphErrors>();
    Vector<GraphErrors> g_sf_sect_sigmasfits = new Vector<GraphErrors>();
    
    HashMap<Integer, ArrayList<H1F> > m_el_sect_slices_ectotp = new HashMap<Integer, ArrayList<H1F> >();
    Vector<Double> v_fitmeans = new Vector<Double>();
    Vector<Double> v_fitsigmas = new Vector<Double>();
    Vector<GraphErrors> g_sf_sect_means_bc = new Vector<GraphErrors>(); 
    Vector<GraphErrors> g_sf_sect_sigmas_bc = new Vector<GraphErrors>(); 
    Vector<Double> v_sf_fit_top = new Vector<Double>();
    Vector<Double> v_sf_fit_bot = new Vector<Double>();

    Vector<Integer> v_cutrates = new Vector<Integer>();
    Vector<Integer> v_cutrates2 = new Vector<Integer>();

    double beam_energy = PhysicalConstants.eBeam;

    double min_p = 0.0; double max_p = beam_energy;
    double min_theta = 0.0; double max_theta = 60.5;
    double min_phi = -180.0; double max_phi = 180.0;
    double min_vz = -30.0; double max_vz = 40.0;
    double min_timing = 0; double max_timing = 800;
    double min_nphe = 0; double max_nphe = 50;
    double min_ecei = 0.0; double max_ecei = 0.80;
    double min_eceo = 0.01; double max_eceo = 0.80;
    double min_ectot = 0.01; double max_ectot = 10.30;
    double min_pcal1 = 0.0; double max_pcal1 = 0.35;
    double min_pcal2 = 0.0; double max_pcal2 = 0.35;
    double min_pcaltot = 0.0; double max_pcaltot = 10.0;
    double min_ecx = -350.0; double max_ecx = 350.0;
    double min_ecy = -390.0; double max_ecy = 390.0;
    double min_pcalx = -200.0; double max_pcalx = 200.0;
    double min_pcaly = -25.0; double max_pcaly = 350.0;
    double min_ecal = 0.0; double max_ecal =  0.7; 
    double min_pcal = 0.0; double max_pcal = 0.7;
    double min_u = -500.0; double max_u = 500.0;
    double min_v = -500.0; double max_v = 500.0;
    double min_W = -500.0; double max_W = 500.0;
    double min_dcx = -200.0; double max_dcx = 200.0;
    double min_dcy = -10.0; double max_dcy = 350.0;
    double min_dcgx = -300.0; double max_dcgx = 300.0;
    double min_dcgy = -300.0; double max_dcgy = 300.0;
   
    double min_w = 0.75; double max_w = 2.0;
    double min_q2 = 0.0; double max_q2 = PhysicalConstants.eBeam;

    ////////////////////////////////
    //REBINNING PERAMETERS FOR FITS
    int n_rebinx = 5;
    int n_rebiny = 5;

    H1F h_el_comparison = new H1F("h_"+s_run_number+"_el_comparison","h_"+s_run_number+"_el_cutrates", 10, 0.0, 10.0 );

    //

    H1F h_el_p_comp = new H1F("h_"+s_run_number+"_el_p_comp","h_"+s_run_number+"_el_p_comp",100,0.0,beam_energy );
    H1F h_el_theta_comp = new H1F("h_"+s_run_number+"_el_theta_comp","h_"+s_run_number+"_el_theta_comp",100,min_theta, max_theta );
    H1F h_el_phi_comp = new H1F("h_"+s_run_number+"_el_phi_comp","h_"+s_run_number+"_el_phi_comp",200, min_phi, max_phi );

    H1F h_el_q2_comp = new H1F("h_"+s_run_number+"_el_q2_comp","h_"+s_run_number+"_el_q2_comp", 100, 0.0, beam_energy);

    H1F h_el_w_comp = new H1F("h_"+s_run_number+"_el_w_comp","h_"+s_run_number+"_el_w_comp",100, 0.0, 6.0);

    H2F h_el_q2x_comp = new H2F("h_"+s_run_number+"_el_q2x_comp","h_"+s_run_number+"_el_q2x_comp", 200, 0.0, 1.1, 200, 0.0, beam_energy);

    public void createElectronHistoToHipoOut( int i ){
		
    }
           
    public void createElectronHistograms(int i){

	v_cutrates.add(0);
	v_cutrates2.add(0);
	h_el_p_cutrates.add( new H1F("h_"+s_run_number+"_el_prates_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_prates_cutlvl"+Integer.toString(i), 100, min_p, max_p ) );

	h_el_p.add( new H1F("h_"+s_run_number+"_el_p_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_p_cutlvl"+Integer.toString(i), 100, min_p, max_p ) );

	h_el_p_test.add( new H1F("h_"+s_run_number+"_el_p_test_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_p_test_cutlvl"+Integer.toString(i), 100, min_p, max_p ) );

	h_el_theta.add( new H1F("h_"+s_run_number+"_el_theta_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_theta_cutlvl"+Integer.toString(i), 100, min_theta, max_theta) );
	h_el_phi.add( new H1F("h_"+s_run_number+"_el_phi_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_phi_cutlvl"+Integer.toString(i), 100, min_phi, max_phi ) );
	h_el_vz.add( new H1F("h_"+s_run_number+"_el_vz_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_vz_cutlvl"+Integer.toString(i), 100, min_vz, max_vz ) );
	h_el_timing.add( new H1F("h_"+s_run_number+"_el_timing_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_timing_cutlvl"+Integer.toString(i), 100, min_timing, max_timing ) );
	h_el_nphe.add( new H1F("h_"+s_run_number+"_el_nphe_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_nphe_cutlvl"+Integer.toString(i), 50, min_nphe, max_nphe) );
	h_el_ecei.add( new H1F("h_"+s_run_number+"_el_ecei_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_ecei_cutlvl"+Integer.toString(i), 100, min_ecei, max_ecei ) );
	h_el_eceo.add( new H1F("h_"+s_run_number+"_el_eceo_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_eceo_cutlvl"+Integer.toString(i), 100, min_eceo, max_eceo ) );
	h_el_ectot.add( new H1F("h_"+s_run_number+"_el_ectot_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_ectot_cutlvl"+Integer.toString(i), 100, min_ectot, max_ectot) );
	h_el_pcal1.add( new H1F("h_"+s_run_number+"_el_pcal1_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_pcal1_cutlvl"+Integer.toString(i), 100, min_pcal1, max_pcal1 ) );
	h_el_pcal2.add( new H1F("h_"+s_run_number+"_el_pcal2_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_pcal2_cutlvl"+Integer.toString(i), 100, min_pcal2, max_pcal2) );
	h_el_pcaltot.add( new H1F("h_"+s_run_number+"_el_pcaltot_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_pcaltot_cutlvl"+Integer.toString(i), 100, min_pcaltot, max_pcaltot) );
 	h_el_edepdiff.add( new H1F("h_"+s_run_number+"_el_edepdiff_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_edepdiff_cutlvl"+Integer.toString(i), 100, -1.5, 1.5));
	h_el_ecu.add( new H1F("h_"+s_run_number+"_el_ecu_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_ecu_cutlvl"+Integer.toString(i), 300, min_u, max_u));
	h_el_ecv.add( new H1F("h_"+s_run_number+"_el_ecv_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_ecv_cutlvl"+Integer.toString(i), 300, min_v, max_v));
	h_el_ecw.add( new H1F("h_"+s_run_number+"_el_ecw_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_ecw_cutlvl"+Integer.toString(i), 300, min_W, max_W));

	h2_el_etotnphe.add( new H2F("h_"+s_run_number+"el_etotnphe_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_etotnphe_cutlvl"+Integer.toString(i), 50, min_nphe, max_nphe, 100, min_ectot, 3.0) );
	h2_el_ectotp.add( new H2F("h_"+s_run_number+"el_ectotp_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_ectotp_cutlvl"+Integer.toString(i), 200, min_p, max_p, 200, min_ectot, 0.4) );
	h2_el_ectotp2.add( new H2F("h_"+s_run_number+"el_ectotp2_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_ectotp2_cutlvl"+Integer.toString(i), 200, min_p, 2.2, 200, min_ectot, 0.4) );

	h2_el_pcalp.add( new H2F("h2_"+s_run_number+"_el_pcalp_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_pcalp_cutlvl"+Integer.toString(i), 200, min_p, max_p, 200, min_pcal1, max_pcal1) );
	h2_el_eieo.add( new H2F("h2_"+s_run_number+"_el_eieo_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_eieo_cutlvl"+Integer.toString(i), 250, min_ecei, max_ecei, 250, min_eceo, max_eceo) );

	h2_el_thetap.add( new H2F("h_"+s_run_number+"_el_thetap_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_thetap_cutlvl"+Integer.toString(i), 300, min_p, max_p, 300, min_theta, max_theta) );
	h2_el_phip.add( new H2F("h_"+s_run_number+"_el_phip_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_phip_cutlvl"+Integer.toString(i), 350, min_p, max_p, 350, min_phi, max_phi) );
	h2_el_pvz.add(  new H2F("h_"+s_run_number+"_el_pvz_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_pvz_cutlvl"+Integer.toString(i), 350, min_vz, max_vz, 350, min_p, max_p) );
	h2_el_thetavz.add( new H2F("h_"+s_run_number+"_el_thetavz_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_thetavz_cutlvl"+Integer.toString(i), 300, min_theta, max_theta, 300, min_vz, max_vz) );
	h2_el_phivz.add( new H2F("h_"+s_run_number+"_el_phivz_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_phivz_cutlvl"+Integer.toString(i), 300, min_vz, max_vz, 300, min_phi, max_phi) );

	h2_el_echitxy.add( new H2F("h2_"+s_run_number+"_el_echitxy_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_echitxy_cutlvl"+Integer.toString(i), 500, min_ecx, max_ecx, 500, min_ecy, max_ecy) ); 
	h2_el_pcalhitxy.add( new H2F("h2_"+s_run_number+"_el_pcalhitxy_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_pcalhitxy_cutlvl"+Integer.toString(i), 500, min_pcalx, max_pcalx, 500, min_pcaly, max_pcaly) ); 

	h2_el_dchitxy.add( new H2F("h2_"+s_run_number+"_el_dchitxy_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_dchitxy_cutlvl"+Integer.toString(i), 500, min_dcx, max_dcx, 500, min_dcy, max_dcy));
	h2_el_dchitgxy.add( new H2F("h2_"+s_run_number+"_el_dchitgxy_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_dchitgxy_cutlvl"+Integer.toString(i), 500, min_dcgx, max_dcgx, 500, min_dcgy, max_dcgy));

	h2_el_dchit_R3_xy.add( new H2F("h2_"+s_run_number+"_el_dchit_R3_xy_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_dchit_R3_xy_cutlvl"+Integer.toString(i), 500, min_dcx, max_dcx, 500, min_dcy, max_dcy));
	h2_el_dchit_R3_gxy.add( new H2F("h2_"+s_run_number+"_el_dchit_R3_gxy_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_dchit_R3_gxy_cutlvl"+Integer.toString(i), 500, min_dcx, max_dcgx, 500, min_dcy, max_dcy));
	
	h2_el_dchit_R1_lxy.add( new H2F("h2_"+s_run_number+"_el_dchit_R1_lxy_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_dchit_R1_lxy_cutlvl"+Integer.toString(i), 500, -95.0, 95.0, 500, -100.0, 100.0));
	
	h2_el_dchit_R3_lxy.add( new H2F("h2_"+s_run_number+"_el_dchit_R3_lxy_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_dchit_R3_lxy_cutlvl"+Integer.toString(i), 500, -200.0, 200.0, 500, -150.0, 150.0));

	h2_el_dchit_R1_traj.add( new H2F("h2_"+s_run_number+"_el_dchit_R1_traj_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_dchit_R1_traj_cutlvl"+Integer.toString(i), 500, -200.0, 200.0, 500, -200.0, 200.0));
	h2_el_dchit_R2_traj.add( new H2F("h2_"+s_run_number+"_el_dchit_R2_traj_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_dchit_R2_traj_cutlvl"+Integer.toString(i), 500, -300.0, 300.0, 500, -300.0, 300.0));
	h2_el_dchit_R3_traj.add( new H2F("h2_"+s_run_number+"_el_dchit_R3_traj_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_dchit_R3_traj_cutlvl"+Integer.toString(i), 500, -400.0, 400.0, 500, -400.0, 400.0));

	h2_el_dchit_R1_traj_rot.add( new H2F("h2_"+s_run_number+"_el_dchit_R1_traj_rot_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_dchit_R1_traj_rot_cutlvl"+Integer.toString(i), 500, 0.0, 200.0, 500, -150.0, 150.0));
	h2_el_dchit_R2_traj_rot.add( new H2F("h2_"+s_run_number+"_el_dchit_R2_traj_rot_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_dchit_R2_traj_rot_cutlvl"+Integer.toString(i), 500, 0.0, 300.0, 500, -150.0, 150.0));
	h2_el_dchit_R3_traj_rot.add( new H2F("h2_"+s_run_number+"_el_dchit_R3_traj_rot_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_dchit_R3_traj_rot_cutlvl"+Integer.toString(i), 500, 0.0, 400.0, 500, -150.0, 150.0));
		
	h2_el_pcalecal.add( new H2F("h2_"+s_run_number+"_el_pcalecal_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_pcalecal_cutlvl"+Integer.toString(i), 200, 0.0, 1.6, 200, 0.0, 1.6) ); 
 	h2_el_edepdiff.add( new H2F("h2_"+s_run_number+"_el_edepdiff_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_edepdiff_cutlvl"+Integer.toString(i), 100, 0.0, 3.0, 100, -1.5, 1.5));
 	h2_el_ptime.add( new H2F("h2_"+s_run_number+"_el_ptime_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_ptime_cutlvl"+Integer.toString(i), 200, min_p, max_p, 200, min_timing, max_timing));
	
	h_el_w.add( new H1F("h_"+s_run_number+"_el_w_cutlvl_"+Integer.toString(i),"h_"+s_run_number+"_el_w_cutlvl_"+Integer.toString(i),100, min_w, max_w) );
	h2_el_wphi.add( new H2F("h_"+s_run_number+"_el_wphi_cutlvl_"+Integer.toString(i),"h_"+s_run_number+"_el_wphi_cutlvl_"+Integer.toString(i),200, -180.0, 180.0, 200, min_w, max_w) );
	h2_el_wq2.add( new H2F("h_"+s_run_number+"_el_wq2_cutlvl_"+Integer.toString(i),"h_"+s_run_number+"_el_wq2_cutlvl_"+Integer.toString(i), 100, min_w, max_w, 100, 0.0, beam_energy) );

	g_sf_sect_means_bc.add( new GraphErrors() );
	g_sf_sect_sigmas_bc.add( new GraphErrors() );
    }

    
    public void createElectronSectorHistograms( int sector, int max_cuts ){
	h_el_sect_p.add( new Vector<H1F>() );
	h_el_sect_theta.add( new Vector<H1F>() );
	h_el_sect_phi.add( new Vector<H1F>() );
	h_el_sect_vz.add( new Vector<H1F>() );
	h_el_sect_timing.add( new Vector<H1F>() );
	h_el_sect_nphe.add( new Vector<H1F>() );
	h_el_sect_pcal.add( new Vector<H1F>() );
	h_el_sect_ecei.add( new Vector<H1F>() );
	h_el_sect_eceo.add( new Vector<H1F>() );
	h_el_sect_ectot.add( new Vector<H1F>() );
	h_el_sect_w.add( new Vector<H1F>() );
	h2_el_sect_etotnphe.add( new Vector<H2F>() );
	h2_el_sect_ectotp.add( new Vector<H2F>() );
	h2_el_sect_ectotp2.add( new Vector<H2F>() );

	h2_el_sect_pcalp.add( new Vector<H2F>() );
	h2_el_sect_eieo.add( new Vector<H2F>() );

	h2_el_sect_thetap.add( new Vector<H2F>() );
	h2_el_sect_phip.add( new Vector<H2F>() );
	h2_el_sect_thetavz.add( new Vector<H2F>() );
	h2_el_sect_phivz.add( new Vector<H2F>() );
	h2_el_sect_pvz.add( new Vector<H2F>() );
       	h2_el_sect_pcalecal.add( new Vector<H2F>() );
	h2_el_sect_ptime.add( new Vector<H2F>() );

	h2_el_sect_wp.add( new Vector<H2F>() );
	h2_el_sect_q2w.add( new Vector<H2F>() );

	h2_el_sect_pcalhitxy.add( new Vector<H2F>() );
	h2_el_sect_ecalhitxy.add( new Vector<H2F>() );
	h2_el_sect_ecalinhitxy.add( new Vector<H2F>() );
	h2_el_sect_ecalouthitxy.add( new Vector<H2F>() );

	h2_el_sect_pcal_sfxy.add( new Vector<H2F>() );
	h2_el_sect_ecalin_sfxy.add( new Vector<H2F>() );
	h2_el_sect_ecalout_sfxy.add( new Vector<H2F>() );

	h2_el_sect_dc_R1_xy.add( new Vector<H2F>() );
	h2_el_sect_dc_R3_xy.add( new Vector<H2F>() );

	h2_el_sect_dc_R1_lxy.add( new Vector<H2F>() );
	h2_el_sect_dc_R3_lxy.add( new Vector<H2F>() );

	h2_el_sect_dc_R1_traj.add( new Vector<H2F>() );
	h2_el_sect_dc_R2_traj.add( new Vector<H2F>() );
	h2_el_sect_dc_R3_traj.add( new Vector<H2F>() );
	
	///////////////////////////////////////////////////////////////////////////////////
	//FOR THE SF FITTING PROCEDURE (BC THE GROOT VERSION SUCK A**)
	//m_el_sect_slices_ectotp.put(sector, new ArrayList<H1F>() );
	
	for( int i = 0; i <= max_cuts; i++ ){
	    (h_el_sect_p.get(sector)).add( new H1F("h_"+s_run_number+"_el_" + Integer.toString(sector) + "_p_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_" + Integer.toString(sector)  + "_p_cutlvl"+Integer.toString(i), 300, min_p, max_p ) );  
	    (h_el_sect_theta.get(sector)).add( new H1F("h_"+s_run_number+"_el_" + Integer.toString(sector) + "_theta_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_" + Integer.toString(sector)  + "_theta_cutlvl"+Integer.toString(i), 100, min_theta, max_theta ) );  
	    (h_el_sect_phi.get(sector)).add( new H1F("h_"+s_run_number+"_el_" + Integer.toString(sector) + "_phi_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_" + Integer.toString(sector)  + "_phi_cutlvl"+Integer.toString(i), 100, min_phi, max_phi ) );  
	    (h_el_sect_vz.get(sector)).add( new H1F("h_"+s_run_number+"_el_" + Integer.toString(sector) + "_vz_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_" + Integer.toString(sector)  + "_vz_cutlvl"+Integer.toString(i), 100, min_vz, max_vz ) );  
	    (h_el_sect_timing.get(sector)).add( new H1F("h_"+s_run_number+"_el_" + Integer.toString(sector) + "_timing_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_" + Integer.toString(sector)  + "_timing_cutlvl"+Integer.toString(i), 500, min_timing, max_timing ) );  
	    (h_el_sect_nphe.get(sector)).add( new H1F("h_"+s_run_number+"_el_" + Integer.toString(sector) + "_nphe_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_" + Integer.toString(sector)  + "_nphe_cutlvl"+Integer.toString(i), 50, min_nphe, max_nphe ) );  
	    (h_el_sect_pcal.get(sector)).add( new H1F("h_"+s_run_number+"_el_" + Integer.toString(sector) + "_pcal_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_" + Integer.toString(sector)  + "_pcal_cutlvl"+Integer.toString(i), 100, min_pcaltot, max_pcaltot ) );  
	    (h_el_sect_ecei.get(sector)).add( new H1F("h_"+s_run_number+"_el_" + Integer.toString(sector) + "_ecei_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_" + Integer.toString(sector)  + "_ecei_cutlvl"+Integer.toString(i), 100, min_ecei, max_ecei ) );  
	    (h_el_sect_eceo.get(sector)).add( new H1F("h_"+s_run_number+"_el_" + Integer.toString(sector) + "_eceo_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_" + Integer.toString(sector)  + "_eceo_cutlvl"+Integer.toString(i), 100, min_eceo, max_eceo ) );  
	    (h_el_sect_ectot.get(sector)).add( new H1F("h_"+s_run_number+"_el_" + Integer.toString(sector) + "_ectot_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_" + Integer.toString(sector)  + "_ectot_cutlvl"+Integer.toString(i), 100, min_ectot, max_ectot ) );  
	    (h_el_sect_w.get(sector)).add( new H1F("h_"+s_run_number+"_el_" + Integer.toString(sector) + "_w_cutlvl"+Integer.toString(i),"h_"+s_run_number+"_el_" + Integer.toString(sector)  + "_w_cutlvl"+Integer.toString(i), 100, 0.75, 2.3 ) );  

	    (h2_el_sect_etotnphe.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_etotnphe_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_" + Integer.toString(sector)  + "_etotnphe_cutlvl"+Integer.toString(i), 50, min_nphe, max_nphe, 200, min_ectot, 3.0 ) );  
	    (h2_el_sect_ectotp.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_ectotp_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_" + Integer.toString(sector)  + "_ectotp_cutlvl"+Integer.toString(i), 350, min_p, max_p, 350, min_ectot, 0.45 ) );  
	    (h2_el_sect_ectotp2.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_ectotp2_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_" + Integer.toString(sector)  + "_ectotp2_cutlvl"+Integer.toString(i), 350, min_p, 2.2, 350, min_ectot, 0.45 ) );  

	    (h2_el_sect_pcalp.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_pcalp_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_" + Integer.toString(sector)  + "_pcalp_cutlvl"+Integer.toString(i), 100, min_p, max_p, 100, min_pcal1, max_pcal1 ) );  
	    (h2_el_sect_eieo.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_eieo_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_" + Integer.toString(sector)  + "_eieo_cutlvl"+Integer.toString(i), 200, min_ecei, max_ecei, 200, min_eceo, max_eceo  ) );

	    (h2_el_sect_thetap.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_thetap_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_" + Integer.toString(sector)  + "_thetap_cutlvl"+Integer.toString(i), 300, min_p, max_p, 300, min_theta, max_theta ) );  
	    (h2_el_sect_phip.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_phip_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_" + Integer.toString(sector)  + "_phip_cutlvl"+Integer.toString(i), 100, min_p, max_p, 100, min_phi, max_phi ) );  
	    (h2_el_sect_thetavz.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_thetavz_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_" + Integer.toString(sector)  + "_thetavz_cutlvl"+Integer.toString(i), 100, min_vz, max_vz, 100, min_theta, max_theta ) );  
	    (h2_el_sect_phivz.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_phivz_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_" + Integer.toString(sector)  + "_phivz_cutlvl"+Integer.toString(i), 100, min_vz, max_vz, 100, min_phi, max_phi ) );  
	    (h2_el_sect_pvz.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_pvz_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_" + Integer.toString(sector)  + "_pvz_cutlvl"+Integer.toString(i), 100, min_p, max_p, 100, min_vz, max_vz ) );  
	    (h2_el_sect_wp.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_wp_cutlvl"+Integer.toString(i),"h2_"+s_run_number+"_el_" + Integer.toString(sector)  + "_wp_cutlvl"+Integer.toString(i), 100, min_p, max_p, 100, min_w, max_w ) );  
	    (h2_el_sect_pcalecal.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_pcalecal_cutlvl" + Integer.toString(i), "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_pcalecal_cutlvl" + Integer.toString(i), 200, min_ecal, max_ecal, 200, min_pcal, max_pcal ));
	    (h2_el_sect_ptime.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_ptime_cutlvl" + Integer.toString(i), "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_ptime_cutlvl" + Integer.toString(i), 200, min_p, max_p, 200, min_timing, max_timing));
	    (h2_el_sect_q2w.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_q2w_cutlvl" + Integer.toString(i), "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_q2w_cutlvl" + Integer.toString(i), 200, min_w, max_w, 200, min_q2, max_q2));

	    (h2_el_sect_dc_R1_xy.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_dc_R1_xy_cutlvl" + Integer.toString(i), "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_dc_R1_xy_cutlvl" + Integer.toString(i), 600, -100.0, 100.0, 600, -10.0, 200.0));

	    (h2_el_sect_dc_R3_xy.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_dc_R3_xy_cutlvl" + Integer.toString(i), "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_dc_R3_xy_cutlvl" + Integer.toString(i), 600, -200.0, 200.0, 600, -10.0, 375.0));

	    (h2_el_sect_dc_R1_lxy.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_dc_R1_lxy_cutlvl" + Integer.toString(i), "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_dc_R1_lxy_cutlvl" + Integer.toString(i), 600, -100.0, 100.0, 600, -150.0, 150.0));

	    (h2_el_sect_dc_R3_lxy.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_dc_R3_lxy_cutlvl" + Integer.toString(i), "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_dc_R3_lxy_cutlvl" + Integer.toString(i), 600, -200.0, 200.0, 600, -150.0, 150.0));
	    ///////FOR PCAL SF INFORMATION
	    (h2_el_sect_pcalhitxy.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_pcalhitxy_cutlvl" + Integer.toString(i), "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_pcalhitxy_cutlvl" + Integer.toString(i), 600, min_pcalx, max_pcalx, 600, min_pcaly, max_pcaly));

	    (h2_el_sect_ecalinhitxy.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_ecalinhitxy_cutlvl" + Integer.toString(i), "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_ecalinhitxy_cutlvl" + Integer.toString(i), 600, -250.0, 250.0, 600, 0.0, max_ecy));

	    (h2_el_sect_ecalouthitxy.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_ecalouthitxy_cutlvl" + Integer.toString(i), "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_ecalouthitxy_cutlvl" + Integer.toString(i), 600, -250.0, 250.0, 600, 0.0, max_ecy));

	    (h2_el_sect_pcal_sfxy.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_pcal_sfxy_cutlvl" + Integer.toString(i), "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_pcal_sfxy_cutlvl" + Integer.toString(i), 600, min_pcalx, max_pcalx, 600, min_pcaly, max_pcaly));

	    (h2_el_sect_ecalin_sfxy.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_ecalin_sfxy_cutlvl" + Integer.toString(i), "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_ecalin_sfxy_cutlvl" + Integer.toString(i), 600, -250.0, 250.0, 600, 0.0, max_ecy));

	    (h2_el_sect_ecalout_sfxy.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_ecalout_sfxy_cutlvl" + Integer.toString(i), "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_ecalout_sfxy_cutlvl" + Integer.toString(i), 600, -250.0, 250.0, 600, 0.0, max_ecy));


	    (h2_el_sect_dc_R1_traj.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_dc_R1_traj_cutlvl" + Integer.toString(i), "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_dc_R1_traj_cutlvl" + Integer.toString(i), 600, -100.0, 100.0, 600, -10.0, 200.0));
	    (h2_el_sect_dc_R2_traj.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_dc_R2_traj_cutlvl" + Integer.toString(i), "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_dc_R2_traj_cutlvl" + Integer.toString(i), 600, -100.0, 100.0, 600, -10.0, 200.0));
	    (h2_el_sect_dc_R3_traj.get(sector)).add( new H2F("h2_"+s_run_number+"_el_" + Integer.toString(sector) + "_dc_R3_traj_cutlvl" + Integer.toString(i), "h2_"+s_run_number+"_el_"+Integer.toString(sector) + "_dc_R3_traj_cutlvl" + Integer.toString(i), 600, -100.0, 100.0, 600, -10.0, 200.0));

	}

    }

    public void electronHistoToHipo(){
	/////ALL NUMBERS ARE EYE BALLED
	System.out.println(" >> SAVING NOW " );
	F1D fid_dc1_cut_right = new F1D("fid_dc1_cut_right","[a]*x + [b]",-90.0, 90.0);
	F1D fid_dc1_cut_left = new F1D("fid_dc1_cut_left","[a]*x + [b]", -90.0, 90.0);
	F1D fid_dc1_cut_bottom = new F1D("fid_dc1_cut_bottom","[a]*x + [b]", -90.0, 90.0);
	F1D fid_dc1_cut_top = new F1D("fid_dc1_cut_top","[a]*x + [b]", -90.0, 90.0);

	F1D fid_dc2_cut_right = new F1D("fid_dc2_cut_right","[a]*x + [b]",-90.0, 90.0);
	F1D fid_dc2_cut_left = new F1D("fid_dc2_cut_left","[a]*x + [b]", -90.0, 90.0);
	F1D fid_dc2_cut_bottom = new F1D("fid_dc2_cut_bottom","[a]*x + [b]", -130.0, 90.0);
	F1D fid_dc2_cut_top = new F1D("fid_dc2_cut_top","[a]*x + [b]", -130.0, 90.0);

	F1D fid_dc3_cut_right = new F1D("fid_dc3_cut_right","[a]*x + [b]",-180.0, 130.0);
	F1D fid_dc3_cut_left = new F1D("fid_dc3_cut_left","[a]*x + [b]", -180.0, 130.0);
	F1D fid_dc3_cut_bottom = new F1D("fid_dc3_cut_bottom","[a]*x + [b]", -180.0, 149.0);
	F1D fid_dc3_cut_top = new F1D("fid_dc3_cut_top","[a]*x + [b]", -180.0, 149.0);

	fid_dc1_cut_top.setParameter(0, 0.428);
	fid_dc1_cut_top.setParameter(1, 44.0);//40.0);
	fid_dc1_cut_top.setLineColor(2);
	fid_dc1_cut_top.setLineWidth(3);

	fid_dc1_cut_bottom.setParameter(0, -0.456);
	fid_dc1_cut_bottom.setParameter(1, -47.0);//-43.0);
	fid_dc1_cut_bottom.setLineColor(2);
	fid_dc1_cut_bottom.setLineWidth(3);
	////////////////////////////////////////////////
	//R2	
	fid_dc2_cut_top.setParameter(0,0.459);
	fid_dc2_cut_top.setParameter(1,69);
	fid_dc2_cut_top.setLineColor(2);
	fid_dc2_cut_top.setLineWidth(3);

	fid_dc2_cut_bottom.setParameter(0,-0.460);
	fid_dc2_cut_bottom.setParameter(1,-68);
	fid_dc2_cut_bottom.setLineColor(2);
	fid_dc2_cut_bottom.setLineWidth(3);
	////////////////////////////////////////////////
	///R3
	fid_dc3_cut_top.setParameter(0,0.443);
	fid_dc3_cut_top.setParameter(1,93);
	fid_dc3_cut_top.setLineColor(2);
	fid_dc3_cut_top.setLineWidth(3);

	fid_dc3_cut_bottom.setParameter(0,-0.434);
	fid_dc3_cut_bottom.setParameter(1,-89.9);
	fid_dc3_cut_bottom.setLineColor(2);
	fid_dc3_cut_bottom.setLineWidth(3);

	EmbeddedCanvas c_dc_R1_allcharges = new EmbeddedCanvas();
	c_dc_R1_allcharges.setSize(800,800);
	c_dc_R1_allcharges.draw(h2_el_dc_R1_allcharge,"colz");
	c_dc_R1_allcharges.draw(fid_dc1_cut_top,"same");
	c_dc_R1_allcharges.draw(fid_dc1_cut_bottom,"same");
	c_dc_R1_allcharges.save(savepath + h2_el_dc_R1_allcharge.getTitle()+".png");

	EmbeddedCanvas c_dc_R2_allcharges = new EmbeddedCanvas();
	c_dc_R2_allcharges.setSize(800,800);
	c_dc_R2_allcharges.draw(h2_el_dc_R2_allcharge,"colz");
	c_dc_R2_allcharges.draw(fid_dc2_cut_top,"same");
	c_dc_R2_allcharges.draw(fid_dc2_cut_bottom,"same");
	c_dc_R2_allcharges.save(savepath + h2_el_dc_R2_allcharge.getTitle()+".png");

	EmbeddedCanvas c_dc_R3_allcharges = new EmbeddedCanvas();
	c_dc_R3_allcharges.setSize(800,800);
	c_dc_R3_allcharges.draw(h2_el_dc_R3_allcharge,"colz");
	c_dc_R3_allcharges.draw(fid_dc3_cut_top,"same");
	c_dc_R3_allcharges.draw(fid_dc3_cut_bottom,"same");
	c_dc_R3_allcharges.save(savepath + h2_el_dc_R3_allcharge.getTitle()+".png");


	dir.mkdir("/dc_all/");
	dir.cd("/dc_all/");
	dir.addDataSet(h2_el_dc_R1_allcharge);
	dir.addDataSet(h2_el_dc_R2_allcharge);
	dir.addDataSet(h2_el_dc_R3_allcharge);

	dir.mkdir("/cutlvls/h_el_comparison/");
	dir.cd("/cutlvls/h_el_comparison/");
	dir.addDataSet(h_el_comparison);
	dir.addDataSet(h_el_p_comp);
	dir.addDataSet(h_el_theta_comp);
	dir.addDataSet(h_el_phi_comp);
	dir.addDataSet(h_el_q2_comp);
	dir.addDataSet(h_el_w_comp);
	dir.addDataSet(h_el_q2x_comp);

	dir.mkdir("/cutlvls/h_el_rates/");
	dir.cd("/cutlvls/h_el_rates/");
	for( H1F h_temp : h_el_p_cutrates ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h_temp);
	    //ctemp.draw(h_temp);
	    //    ctemp.save(savepath + h_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h_el_p/");
	dir.cd("/cutlvls/h_el_p/");
	for( int i  = 0; i < h_el_p.size(); i++ ){
	    EmbeddedCanvas ctemp_p = new EmbeddedCanvas();
	    ctemp_p.setSize(800,800);
	    ctemp_p.divide(1,1);
	    ctemp_p.cd(0);
	    ctemp_p.draw(h_el_p.get(i));
	    H1F h_temp = h_el_p.get(i);
	    dir.addDataSet(h_temp);
	}


	dir.mkdir("/cutlvls/h_el_theta/");
	dir.cd("/cutlvls/h_el_theta/");
	for( H1F h_temp : h_el_theta ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h_temp);
	    ctemp.draw(h_temp);
	    //ctemp.save(savepath + h_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h_el_pt/");
	dir.cd("/cutlvls/h_el_pt/");
	for( H1F h_temp : h_el_p ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h_temp);
	    ctemp.draw(h_temp);
	    //ctemp.save(savepath + h_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h_el_phi/");
	dir.cd("/cutlvls/h_el_phi/");
	for( H1F h_temp : h_el_phi ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h_temp);
	    //ctemp.draw(h_temp);
	    //ctemp.save(savepath + h_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h_el_vz/");
	dir.cd("/cutlvls/h_el_vz/");
	for( H1F h_temp : h_el_vz ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h_temp);	
	    ctemp.draw(h_temp);
	    //ctemp.save(savepath + h_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h_el_timing/");
	dir.cd("/cutlvls/h_el_timing/");
	for( H1F h_temp : h_el_timing ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h_temp);
	    ctemp.draw(h_temp);
	    //ctemp.save(savepath + h_temp.getTitle() + ".png");
	}
      
	dir.mkdir("/cutlvls/h_el_nphe/");
	dir.cd("/cutlvls/h_el_nphe/");
	for( H1F h_temp : h_el_nphe ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h_temp);
	    ctemp.draw(h_temp);
	    //ctemp.save(savepath + h_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h_el_ecei/");
	dir.cd("/cutlvls/h_el_ecei/");
	for( H1F h_temp : h_el_ecei ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);	    
	    ctemp.draw(h_temp);
	    dir.addDataSet(h_temp);
	    //ctemp.save(savepath + h_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h_el_eceo/");
	dir.cd("/cutlvls/h_el_eceo/");
	for( H1F h_temp : h_el_eceo ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h_temp);
	    ctemp.draw(h_temp);
	    //ctemp.save(savepath + h_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h_el_ectot/");
	dir.cd("/cutlvls/h_el_ectot/");
	for( H1F h_temp : h_el_ectot ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h_temp);
	    ctemp.draw(h_temp);
	    //ctemp.save(savepath + h_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h_el_pcal1/");
	dir.cd("/cutlvls/h_el_pcal1/");
	for( H1F h_temp : h_el_pcal1 ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h_temp);
	    ctemp.draw(h_temp);
	    //ctemp.save(savepath + h_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h_el_pcal2/");
	dir.cd("/cutlvls/h_el_pcal2/");
	for( H1F h_temp : h_el_pcal2 ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h_temp);
	    ctemp.draw(h_temp);
	    //ctemp.save(savepath + h_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h_el_pcaltot/");
	dir.cd("/cutlvls/h_el_pcaltot/");
	for( H1F h_temp : h_el_pcaltot ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h_temp);
	    ctemp.draw(h_temp);
	    //ctemp.save(savepath + h_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h_el_edepdiff/");
	dir.cd("/cutlvls/h_el_edepdiff/");
	for( H1F h_temp : h_el_edepdiff ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h_temp);
	    ctemp.draw(h_temp);
	    //ctemp.save(savepath + h_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h_el_ecu/");
	dir.cd("/cutlvls/h_el_ecu/");
	for( H1F h_temp : h_el_ecu ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h_temp);
	    ctemp.draw(h_temp);
	    //ctemp.save(savepath + h_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h_el_ecv/");
	dir.cd("/cutlvls/h_el_ecv/");
	for( H1F h_temp : h_el_ecv ){
	    // EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    //ctemp.setSize(800,800);
	    dir.addDataSet(h_temp);
	    //ctemp.draw(h_temp);
	    //ctemp.save(savepath + h_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h_el_ecw/");
	dir.cd("/cutlvls/h_el_ecw/");
	for( H1F h_temp : h_el_ecw ){
	    //EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    //ctemp.setSize(800,800);
	    dir.addDataSet(h_temp);
	    //ctemp.save(savepath + h_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_etotnphe/");
	dir.cd("/cutlvls/h2_el_etotnphe/");
	for( H2F h2_temp : h2_el_etotnphe ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    ctemp.draw(h2_temp,"colz");
	    //ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_ectotp/");
 	dir.cd("/cutlvls/h2_el_ectotp/");
	for( H2F h2_temp : h2_el_ectotp ){
	    //H2F h2_temprbX = h2_temp.rebinX(n_rebinx);
	    //H2F h2_temprbXY = h2_temprbX.rebinY(n_rebiny);
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet((H2F)h2_temp);
	    ctemp.draw(h2_temp,"colz");
	    //ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_ectotp2/");
 	dir.cd("/cutlvls/h2_el_ectotp2/");
	for( H2F h2_temp : h2_el_ectotp2 ){
	    //H2F h2_temprbX = h2_temp.rebinX(n_rebinx);
	    //H2F h2_temprbXY = h2_temprbX.rebinY(n_rebiny);
	    //EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    //ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    //ctemp.draw(h2_temp,"colz");
	    //ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_pcalp/");
 	dir.cd("/cutlvls/h2_el_pcalp/");
	for( H2F h2_temp : h2_el_pcalp ){
	    //EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    //ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    // ctemp.draw(h2_temp,"colz");
	    //ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_eieo/");
 	dir.cd("/cutlvls/h2_el_eieo/");
	for( H2F h2_temp : h2_el_eieo ){
	    //EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    //ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    //ctemp.draw(h2_temp,"colz");
	    //ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_thetap/");
 	dir.cd("/cutlvls/h2_el_thetap/");
	System.out.println(" >> MAKING THETA P HISTO ");
	for( H2F h2_temp : h2_el_thetap ){
	    //EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    //ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    System.out.println(" >> " + h2_temp.getTitle() );
	    //ctemp.draw(h2_temp,"colz");
	    //ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_phip/");
 	dir.cd("/cutlvls/h2_el_phip/");
	for( H2F h2_temp : h2_el_phip ){
	    //EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    //ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    //ctemp.draw(h2_temp,"colz");
	    //ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_echitxy/");
 	dir.cd("/cutlvls/h2_el_echitxy/");
	for( H2F h2_temp : h2_el_echitxy ){
	    //EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    //ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    //ctemp.draw(h2_temp,"colz");
	    //ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}


	F1D fid_cut_right = new F1D("fid_cut_right","[a]*x + [b]",-50.0,150.0);
	F1D fid_cut_left = new F1D("fid_cut_left","[a]*x + [b]",-150.0, 25.0);
	F1D fid_cut_bottom = new F1D("fid_cut_bottom","[a]*x + [b]", -50.0, 50.0);
	F1D fid_cut_top = new F1D("fid_cut_top","[a]*x + [b]", -150.0, 150.0);

	fid_cut_right.setParameter(0,1.86);
	fid_cut_right.setParameter(1,51.0);
	fid_cut_right.setLineColor(2);
	fid_cut_right.setLineWidth(3);

	fid_cut_left.setParameter(0,-1.876);
	fid_cut_left.setParameter(1,49.0);
	fid_cut_left.setLineColor(2);
	fid_cut_left.setLineWidth(3);

	fid_cut_top.setParameter(0,0.0);
	fid_cut_top.setParameter(1,330.0);
	fid_cut_top.setLineColor(2);
	fid_cut_top.setLineWidth(3);

	fid_cut_bottom.setParameter(0,0.0);
	fid_cut_bottom.setParameter(1,52.0);
	fid_cut_bottom.setLineColor(2);
	fid_cut_bottom.setLineWidth(3);
	
	dir.mkdir("/cutlvls/h2_el_pcalhitxy/");
 	dir.cd("/cutlvls/h2_el_pcalhitxy/");
	for( H2F h2_temp : h2_el_pcalhitxy ){
	    EmbeddedCanvas c_fid_pcal = new EmbeddedCanvas();
	    c_fid_pcal.setSize(800,800);

	    c_fid_pcal.draw(h2_temp,"colz");	
	    c_fid_pcal.draw(fid_cut_bottom,"same");
	    c_fid_pcal.draw(fid_cut_top,"same");
	    c_fid_pcal.draw(fid_cut_left,"same");
	    c_fid_pcal.draw(fid_cut_right,"same");
	    //c_fid_pcal.save(savepath+h2_temp.getTitle()+".png");

	    dir.addDataSet(h2_temp);
	}
	


	dir.mkdir("/cutlvls/h2_el_pcalecal/");
 	dir.cd("/cutlvls/h2_el_pcalecal/");
	for( H2F h2_temp : h2_el_pcalecal ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    //ctemp.draw(h2_temp,"colz");
	    //ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_edepdiff/");
	dir.cd("/cutlvls/h2_el_edepdiff/");
	for( H2F h2_temp : h2_el_edepdiff ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    //ctemp.draw(h2_temp,"colz");
	    //ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_ptime/");
	dir.cd("/cutlvls/h2_el_ptime/");
	for( H2F h2_temp : h2_el_ptime ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    ctemp.draw(h2_temp,"colz");
	    ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_dchitxy/");
	dir.cd("/cutlvls/h2_el_dchitxy/");
	for( H2F h2_temp : h2_el_dchitxy ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    //ctemp.draw(h2_temp,"colz");
	    //ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_dchitgxy/");
	dir.cd("/cutlvls/h2_el_dchitgxy/");
	for( H2F h2_temp : h2_el_dchitgxy ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    //ctemp.draw(h2_temp,"colz");
	    //ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_dchit_R3_xy/");
	dir.cd("/cutlvls/h2_el_dchit_R3_xy/");
	for( H2F h2_temp : h2_el_dchit_R3_xy ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    ctemp.draw(h2_temp,"colz");
	    ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

 	dir.mkdir("/cutlvls/h2_el_dchit_R3_gxy/");
	dir.cd("/cutlvls/h2_el_dchit_R3_gxy/");
	for( H2F h2_temp : h2_el_dchitgxy ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    //ctemp.draw(h2_temp,"colz");
	    //ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_dchit_R1_lxy/");
	dir.cd("/cutlvls/h2_el_dchit_R1_lxy/");
	for( H2F h2_temp : h2_el_dchit_R1_lxy ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    //ctemp.draw(h2_temp,"colz");
	    //ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_dchit_R3_lxy/");
	dir.cd("/cutlvls/h2_el_dchit_R3_lxy/");
	for( H2F h2_temp : h2_el_dchit_R3_lxy ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    //ctemp.draw(h2_temp,"colz");
	    //ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_dchit_R1_traj/");
	dir.cd("/cutlvls/h2_el_dchit_R1_traj/");
	for( H2F h2_temp : h2_el_dchit_R1_traj ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    ctemp.draw(h2_temp,"colz");
	    ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_dchit_R2_traj/");
	dir.cd("/cutlvls/h2_el_dchit_R2_traj/");
	for( H2F h2_temp : h2_el_dchit_R2_traj ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    ctemp.draw(h2_temp,"colz");
	    ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_dchit_R3_traj/");
	dir.cd("/cutlvls/h2_el_dchit_R3_traj/");
	for( H2F h2_temp : h2_el_dchit_R3_traj ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    ctemp.draw(h2_temp,"colz");
	    ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_dchit_R1_traj_rot/");
	dir.cd("/cutlvls/h2_el_dchit_R1_traj_rot/");
	for( H2F h2_temp : h2_el_dchit_R1_traj_rot ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    ctemp.draw(h2_temp,"colz");
	    ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_dchit_R2_traj_rot/");
	dir.cd("/cutlvls/h2_el_dchit_R2_traj_rot/");
	for( H2F h2_temp : h2_el_dchit_R2_traj_rot ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    ctemp.draw(h2_temp,"colz");
	    ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}

	dir.mkdir("/cutlvls/h2_el_dchit_R3_traj_rot/");
	dir.cd("/cutlvls/h2_el_dchit_R3_traj_rot/");
	for( H2F h2_temp : h2_el_dchit_R3_traj_rot ){
	    EmbeddedCanvas ctemp = new EmbeddedCanvas();
	    ctemp.setSize(800,800);
	    dir.addDataSet(h2_temp);
	    ctemp.draw(h2_temp,"colz");
	    ctemp.save(savepath + h2_temp.getTitle() + ".png");
	}


	EmbeddedCanvas c_temp1 = new EmbeddedCanvas();
	c_temp1.setSize(800,800);
	c_temp1.divide(3,2);
	dir.mkdir("/cutlvls/h_el_w/");
 	dir.cd("/cutlvls/h_el_w/");
	int c_i = 0;
	for( H1F h_temp : h_el_w ){
	    dir.addDataSet(h_temp);
	    c_temp1.cd(c_i);
	    h_temp.setTitleX("W [GeV]");
	    //c_temp1.draw(h_temp);
	    c_i=c_i+1;
	}
	//c_temp1.save(savepath+"h_"+s_run_number+"_el_w_cutset.png");

	dir.mkdir("/cutlvls/h2_el_wphi/");
 	dir.cd("/cutlvls/h2_el_wphi/");
	int c_j = 0;
	for( H2F h_temp : h2_el_wphi ){
	    EmbeddedCanvas c_temp2 = new EmbeddedCanvas();
	    c_temp2.setSize(800,800);
	    dir.addDataSet(h_temp);
	    //c_temp.cd(c_i);
	    h_temp.setTitleY("W [GeV]");
	    h_temp.setTitleX("#phi [deg]");
	    //c_temp2.draw(h_temp,"colz");
	    //c_temp2.save(savepath+"h2_"+s_run_number+"_el_"+c_j+"_wphi_cutset.png");
	    c_j=c_j+1;
	}

	dir.mkdir("/cutlvls/h2_el_wq2/");
 	dir.cd("/cutlvls/h2_el_wq2/");
	int c_m = 0;
	for( H2F h_temp : h2_el_wq2 ){ 
	    EmbeddedCanvas c_temp3 = new EmbeddedCanvas();
	    c_temp3.setSize(800,800);
	    dir.addDataSet(h_temp);
	    //c_temp.cd(c_i);
	    h_temp.setTitleX("W [GeV]");
	    h_temp.setTitleY("#phi [deg]");
	    //c_temp3.draw(h_temp,"colz");
	    //c_temp3.save(savepath+"h2_"+s_run_number+"_el_"+c_m+"_wq2_cutset.png");
	    c_m=c_m+1;
	}


	dir.mkdir("/cutlvls/h2_el_phivz/");
 	dir.cd("/cutlvls/h2_el_phivz/");              		
	for( int i = 0; i < h2_el_phivz.size(); i++ ){
		H2F h2_temp = h2_el_phivz.get(i);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    
	}


	//////////////////////////////////////////////////////
	//SECTOR BASED HISTOGRAMS

	dir.mkdir("/cutlvls/h_el_sect_p/");
 	dir.cd("/cutlvls/h_el_sect_p/");              		
	for( int i = 0; i < h_el_sect_p.size(); i++ ){
	    Vector<H1F> v_temp = h_el_sect_p.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H1F h_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h_temp);
		//ctemp.draw(h_temp);
		//ctemp.save(savepath + h_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h_el_sect_theta/");
 	dir.cd("/cutlvls/h_el_sect_theta/");              		
	for( int i = 0; i < h_el_sect_theta.size(); i++ ){
	    Vector<H1F> v_temp = h_el_sect_theta.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H1F h_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h_temp);
		//ctemp.draw(h_temp);
		//ctemp.save(savepath + h_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h_el_sect_phi/");
 	dir.cd("/cutlvls/h_el_sect_phi/");              		
	for( int i = 0; i < h_el_sect_phi.size(); i++ ){
	    Vector<H1F> v_temp = h_el_sect_phi.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H1F h_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h_temp);
		//ctemp.draw(h_temp);
		//ctemp.save(savepath + h_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h_el_sect_vz/");
 	dir.cd("/cutlvls/h_el_sect_vz/");              		
	for( int i = 0; i < h_el_sect_vz.size(); i++ ){
	    Vector<H1F> v_temp = h_el_sect_vz.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H1F h_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h_temp);
		//ctemp.draw(h_temp);
		//ctemp.save(savepath + h_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h_el_sect_timing/");
 	dir.cd("/cutlvls/h_el_sect_timing/");              		
	for( int i = 0; i < h_el_sect_timing.size(); i++ ){
	    Vector<H1F> v_temp = h_el_sect_timing.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H1F h_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h_temp);
		//ctemp.draw(h_temp);
		//ctemp.save(savepath + h_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h_el_sect_nphe/");
 	dir.cd("/cutlvls/h_el_sect_nphe/");              		
	for( int i = 0; i < h_el_sect_nphe.size(); i++ ){
	    Vector<H1F> v_temp = h_el_sect_nphe.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H1F h_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h_temp);
		//ctemp.draw(h_temp);
		//ctemp.save(savepath + h_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h_el_sect_pcaltot/");
 	dir.cd("/cutlvls/h_el_sect_pcaltot/");              		
	for( int i = 0; i < h_el_sect_pcal.size(); i++ ){
	    Vector<H1F> v_temp = h_el_sect_pcal.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H1F h_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h_temp);
		//	ctemp.draw(h_temp);
		//ctemp.save(savepath + h_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h_el_sect_ecei/");
 	dir.cd("/cutlvls/h_el_sect_ecei/");              		
	for( int i = 0; i < h_el_sect_ecei.size(); i++ ){
	    Vector<H1F> v_temp = h_el_sect_ecei.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H1F h_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h_temp);
		//ctemp.draw(h_temp);
		//ctemp.save(savepath + h_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h_el_sect_eceo/");
 	dir.cd("/cutlvls/h_el_sect_eceo/");              		
	for( int i = 0; i < h_el_sect_eceo.size(); i++ ){
	    Vector<H1F> v_temp = h_el_sect_eceo.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H1F h_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h_temp);
		//ctemp.draw(h_temp);
		//ctemp.save(savepath + h_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h_el_sect_ectot/");
 	dir.cd("/cutlvls/h_el_sect_ectot/");              		
	for( int i = 0; i < h_el_sect_ectot.size(); i++ ){
	    Vector<H1F> v_temp = h_el_sect_ectot.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H1F h_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h_temp);
		//ctemp.draw(h_temp);
		//ctemp.save(savepath + h_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h_el_sect_w/");
 	dir.cd("/cutlvls/h_el_sect_w/");
	System.out.println(" >> W " + h_el_sect_w.size() );
	for( int i = 0; i < h_el_sect_w.size(); i++ ){
	    EmbeddedCanvas c_w = new EmbeddedCanvas();
	    c_w.setSize(900,1200);
	    c_w.divide(3,4);
	    Vector<H1F> w_temp = h_el_sect_w.get(i);       
	    for( int j = 0; j < w_temp.size(); j++){	
		c_w.cd(j);
		w_temp.get(j).setTitleX("W");
		//c_w.draw( w_temp.get(j) );
		dir.addDataSet( w_temp.get(j) );
		//c_w.save(savepath + w_temp.get(j).getTitle() + ".png");
	    
	    }
	    c_w.save(savepath + "h_"+s_run_number+"_el_w_"+Integer.toString(i)+".png");
	}

	/*	double totalaccumulatedcharge = Math.abs(RunPropertiesLoader.getTotalAccumulatedCharge());
	dir.mkdir("/cutlvls/h_el_sect_acnorm_w/");
 	dir.cd("/cutlvls/h_el_sect_acnorm_w");              			
	for( int i = 0; i < h_el_sect_w.size(); i++ ){
	    EmbeddedCanvas c_w = new EmbeddedCanvas();
	    c_w.setSize(900,900);
	    c_w.divide(2,4);
	    Vector<H1F> v_temp = h_el_sect_w.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){	
		c_w.cd(j);
		v_temp.get(j).setTitleX("W");
		System.out.println(" >> NORMALIZING TO " + totalaccumulatedcharge);
		v_temp.get(j).divide(totalaccumulatedcharge);
		c_w.draw(v_temp.get(j));
		dir.addDataSet(v_temp.get(j));
	    }
	    c_w.save(savepath + "h_"+s_run_number+"_el_w_acnorm_"+Integer.toString(i)+".png");
	}
	*/

	dir.mkdir("/cutlvls/h2_el_sect_etotnphe/");
 	dir.cd("/cutlvls/h2_el_sect_etotnphe/");              		
	for( int i = 0; i < h2_el_sect_etotnphe.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_etotnphe.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_ectotp/");
 	dir.cd("/cutlvls/h2_el_sect_ectotp/");              		
	for( int i = 0; i < h2_el_sect_ectotp.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_ectotp.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){				
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_ectotp2/");
 	dir.cd("/cutlvls/h2_el_sect_ectotp2/");              		
	for( int i = 0; i < h2_el_sect_ectotp2.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_ectotp2.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){				
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2rb_el_sect_ectotp/");
 	dir.cd("/cutlvls/h2rb_el_sect_ectotp/");              		
	for( int i = 0; i < h2_el_sect_ectotp.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_ectotp.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){				
		H2F h2_rebinX = v_temp.get(j).rebinX(n_rebinx);
		H2F h2_rebinXY = v_temp.get(j).rebinY(n_rebiny);
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_rebinXY);
		//ctemp.draw(h2_rebinXY,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_pcalp/");
 	dir.cd("/cutlvls/h2_el_sect_pcalp/");              		
	for( int i = 0; i < h2_el_sect_pcalp.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_pcalp.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_eieo/");
 	dir.cd("/cutlvls/h2_el_sect_eieo/");              		
	for( int i = 0; i < h2_el_sect_eieo.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_eieo.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){	
		EmbeddedCanvas c_eieo = new EmbeddedCanvas();
		c_eieo.setSize(800,800);
		v_temp.get(j).setTitleX("EI [GeV]");
		v_temp.get(j).setTitleY("EO [GeV]");
		//v_temp.get(j).setLogz();
		//c_eieo.draw(v_temp.get(j),"colz");
		//c_eieo.save(savepath + v_temp.get(j).getTitle()+".png");
		dir.addDataSet(v_temp.get(j));
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_thetap/");
 	dir.cd("/cutlvls/h2_el_sect_thetap");              		
	for( int i = 0; i < h2_el_sect_thetap.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_thetap.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_phip/");
 	dir.cd("/cutlvls/h2_el_sect_phip/");              		
	for( int i = 0; i < h2_el_sect_phip.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_phip.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_thetavz/");
 	dir.cd("/cutlvls/h2_el_sect_thetavz/");              		
	for( int i = 0; i < h2_el_sect_thetavz.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_thetavz.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_phivz/");
 	dir.cd("/cutlvls/h2_el_sect_phivz/");              		
	for( int i = 0; i < h2_el_sect_phivz.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_phivz.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_pvz/");
 	dir.cd("/cutlvls/h2_el_sect_pvz/");              		
	for( int i = 0; i < h2_el_sect_pvz.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_pvz.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_pcalecal/");
 	dir.cd("/cutlvls/h2_el_sect_pcalecal/");              		
	for( int i = 0; i <  h2_el_sect_pcalecal.size(); i++ ){
	    Vector<H2F> v_temp =  h2_el_sect_pcalecal.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//	ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_wp/");
 	dir.cd("/cutlvls/h2_el_sect_wp/");              		
	for( int i = 0; i < h2_el_sect_wp.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_wp.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_ptime/");
 	dir.cd("/cutlvls/h2_el_sect_ptime/");              		
	for( int i = 0; i < h2_el_sect_ptime.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_ptime.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_dc_R3_xy/");
 	dir.cd("/cutlvls/h2_el_sect_dc_R3_xy/");              		
	for( int i = 0; i < h2_el_sect_dc_R3_xy.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_dc_R3_xy.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_dc_R1_xy/");
 	dir.cd("/cutlvls/h2_el_sect_dc_R1_xy/");              		
	for( int i = 0; i < h2_el_sect_dc_R1_xy.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_dc_R1_xy.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_dc_R3_lxy/");
 	dir.cd("/cutlvls/h2_el_sect_dc_R3_lxy/");              		
	for( int i = 0; i < h2_el_sect_dc_R3_lxy.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_dc_R3_lxy.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_dc_R1_lxy/");
 	dir.cd("/cutlvls/h2_el_sect_dc_R1_lxy/");              		
	for( int i = 0; i < h2_el_sect_dc_R1_lxy.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_dc_R1_lxy.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_pcalhitxy/");
 	dir.cd("/cutlvls/h2_el_sect_pcalhitxy/");              		
	for( int i = 0; i < h2_el_sect_pcalhitxy.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_pcalhitxy.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		///ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_q2w/");
 	dir.cd("/cutlvls/h2_el_sect_q2w/");              		
	for( int i = 0; i < h2_el_sect_q2w.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_q2w.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		v_temp.get(j).setTitleX("W [GeV]");
		v_temp.get(j).setTitleY("Q2 [GeV^2]");
		H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvl/h2_el_sect_ecalinhitxy/");
	dir.cd("/cutlvl/h2_el_sect_ecalinhitxy/");
	for( int i = 0; i < h2_el_sect_ecalinhitxy.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_ecalinhitxy.get(i);
	    for( int j = 0; j<v_temp.size(); j++ ){
		H2F h2_temp = v_temp.get(j);
		h2_temp.setTitleX("ECAL X [cm]");
		h2_temp.setTitleY("ECAL Y [cm]");		
		dir.addDataSet(h2_temp);
	    }
	}

	dir.mkdir("/cutlvl/h2_el_sect_ecalouthitxy/");
	dir.cd("/cutlvl/h2_el_sect_ecalouthitxy/");
	for( int i = 0; i < h2_el_sect_ecalouthitxy.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_ecalouthitxy.get(i);
	    for( int j = 0; j<v_temp.size(); j++ ){
		H2F h2_temp = v_temp.get(j);
		h2_temp.setTitleX("ECAL X [cm]");
		h2_temp.setTitleY("ECAL Y [cm]");		
		dir.addDataSet(h2_temp);
	    }
	}

	dir.mkdir("/cutlvl/h2_el_sect_pcalhitxy/");
	dir.cd("/cutlvl/h2_el_sect_pcalhitxy/");
	for( int i = 0; i < h2_el_sect_pcalhitxy.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_pcalhitxy.get(i);
	    for( int j = 0; j<v_temp.size(); j++ ){
		H2F h2_temp = v_temp.get(j);
		h2_temp.setTitleX("PCAL X [cm]");
		h2_temp.setTitleY("PCAL Y [cm]");		
		dir.addDataSet(h2_temp);
	    }
	}


	dir.mkdir("/cutlvl/h2_el_sect_ecalin_sfxy/");
	dir.cd("/cutlvl/h2_el_sect_ecalin_sfxy/");
	for( int i = 0; i < h2_el_sect_ecalin_sfxy.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_ecalin_sfxy.get(i);
	    for( int j = 0; j<v_temp.size(); j++ ){
		H2F h2_temp = v_temp.get(j);		
		h2_temp.setTitleX("ECAL X [cm]");
		h2_temp.setTitleY("ECAL Y [cm]");		
		dir.addDataSet(h2_temp);
	    }
	}

	dir.mkdir("/cutlvl/h2_el_sect_ecalout_sfxy/");
	dir.cd("/cutlvl/h2_el_sect_ecalout_sfxy/");
	for( int i = 0; i < h2_el_sect_ecalout_sfxy.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_ecalout_sfxy.get(i);
	    for( int j = 0; j<v_temp.size(); j++ ){
		H2F h2_temp = v_temp.get(j);		
		h2_temp.setTitleX("ECAL X [cm]");
		h2_temp.setTitleY("ECAL Y [cm]");		
		dir.addDataSet(h2_temp);
	    }
	}

	dir.mkdir("/cutlvl/h2_el_sect_pcal_sfxy/");
	dir.cd("/cutlvl/h2_el_sect_pcal_sfxy/");
	for( int i = 0; i < h2_el_sect_pcal_sfxy.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_pcal_sfxy.get(i);
	    for( int j = 0; j<v_temp.size(); j++ ){
		H2F h2_temp = v_temp.get(j);		
		h2_temp.setTitleX("PCAL X [cm]");
		h2_temp.setTitleY("PCAL Y [cm]");		
		dir.addDataSet(h2_temp);
	    }
	}

 	dir.mkdir("/cutlvl/h2_el_sect_Recalin_sfxy/");
	dir.cd("/cutlvl/h2_el_sect_Recalin_sfxy/");
	for( int i = 0; i < h2_el_sect_ecalin_sfxy.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_ecalin_sfxy.get(i);
	    Vector<H2F> v2_temp = h2_el_sect_ecalinhitxy.get(i);
	    for( int j = 0; j<v_temp.size(); j++ ){
		H2F h2_temp = v_temp.get(j);		
		H2F h2_temp2 = v2_temp.get(j);
		h2_temp.divide(h2_temp2);
		h2_temp.setTitleX("R SF ECAL X [cm]");
		h2_temp.setTitleY("R SF ECAL Y [cm]");		
		dir.addDataSet(h2_temp);
	    }
	}

	dir.mkdir("/cutlvl/h2_el_sect_Recalout_sfxy/");
	dir.cd("/cutlvl/h2_el_sect_Recalout_sfxy/");
	for( int i = 0; i < h2_el_sect_ecalout_sfxy.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_ecalout_sfxy.get(i);
	    Vector<H2F> v2_temp = h2_el_sect_ecalouthitxy.get(i);
	    for( int j = 0; j<v_temp.size(); j++ ){
		H2F h2_temp = v_temp.get(j);		
		H2F h2_temp2 = v2_temp.get(j);
		h2_temp.divide(h2_temp2);
		h2_temp.setTitleX("R SF ECAL X [cm]");
		h2_temp.setTitleY("R SF ECAL Y [cm]");		
		dir.addDataSet(h2_temp);
	    }
	}

	dir.mkdir("/cutlvl/h2_el_sect_Rpcal_sfxy/");
	dir.cd("/cutlvl/h2_el_sect_Rpcal_sfxy/");
	for( int i = 0; i < h2_el_sect_pcal_sfxy.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_pcal_sfxy.get(i);
	    Vector<H2F> v2_temp = h2_el_sect_pcalhitxy.get(i);
	    for( int j = 0; j<v_temp.size(); j++ ){
		H2F h2_temp = v_temp.get(j);		
		H2F h2_temp2 = v2_temp.get(j);
		h2_temp.divide(h2_temp2);
		h2_temp.setTitleX("R SF PCAL X [cm]");
		h2_temp.setTitleY("R SF PCAL Y [cm]");		
		dir.addDataSet(h2_temp);
	    }
	}
	

	dir.mkdir("/cutlvls/h_el_cutrates/");
	dir.cd("/cutlvls/h_el_cutrates/");
	int cut_lvl2 = 0;
	for( int total_rate2 : v_cutrates ){ 
	    //System.out.println(" >> CUT LVL " + cut_lvl2 + " RATES " + total_rate2 );
 	    h_el_cutrates.setBinContent(cut_lvl2,total_rate2);       
	    cut_lvl2++;
	}
	dir.addDataSet(h_el_cutrates);
	

	/*
	dir.mkdir("/cutlvls/h2_el_sect_dc_R1_traj/");
 	dir.cd("/cutlvls/h2_el_sect_dc_R1_traj");              		
	for( int i = 0; i < h2_el_sect_dc_R1_traj.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_dc_R1_traj.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		//H2F h2_temp = v_temp.get(j);
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		H2F h2_temp = v_temp.get(j);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_dc_R2_traj/");
 	dir.cd("/cutlvls/h2_el_sect_dc_R2_traj");              		
	for( int i = 0; i < h2_el_sect_dc_R2_traj.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_dc_R2_traj.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		//H2F h2_temp = v_temp.get(j)
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		H2F h2_temp = v_temp.get(j);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}

	dir.mkdir("/cutlvls/h2_el_sect_dc_R3_traj/");
 	dir.cd("/cutlvls/h2_el_sect_dc_R3_traj");              		
	for( int i = 0; i < h2_el_sect_dc_R3_traj.size(); i++ ){
	    Vector<H2F> v_temp = h2_el_sect_dc_R3_traj.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas ctemp = new EmbeddedCanvas();
		ctemp.setSize(800,800);
		H2F h2_temp = v_temp.get(j);
		dir.addDataSet(h2_temp);
		//ctemp.draw(h2_temp,"colz");
		//ctemp.save(savepath + h2_temp.getTitle() + ".png");
	    }
	}
	*/
	//dir.writeFile(savepath+"testing_cutlvl.hipo");
	
	saveHipoOut();

    }


    public void saveHipoOut(){
	System.out.println(" >> SAVING HIPO FILE NOW ");
	dir.writeFile(savepath+"h_"+s_run_number+"_"+n_thread+"_el_pid_clary.hipo");
    }


    public void printHistograms(){

	System.out.println(">> WRITING TO JSON NOW ");
	///%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	///%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	///%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	///%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	///%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	//THE GOOD STUFF FOR FITTING THE SF HISTOGRAMS PER SECTOR
	//UPDATES TO WRITE TO JSON FILE

	BufferedWriter writer = null;

	Vector<GraphErrors> g_plus_threesig = new Vector<GraphErrors>();
	Vector<GraphErrors> g_minus_threesig = new Vector<GraphErrors>();
	
	for( int s = 0 ; s < 6; s++ ){
	    GraphErrors g_temp = g_sf_sect_means_bc.get(s);	    
	    GraphErrors g_sig = g_sf_sect_sigmas_bc.get(s); 
	    g_plus_threesig.add( new GraphErrors() );
	    g_minus_threesig.add( new GraphErrors() );
	    for( int b = 0; b < g_temp.getVectorX().size(); b++){		
		//System.out.println(" >> X " + g_temp.getDataX(b) + " Y " + g_temp.getDataY(b) + " ERR " + 3*g_sig.getDataY(b) );
		double y_sig = 2.75*(Math.abs(g_sig.getDataY(b)));
		
		g_plus_threesig.get(s).addPoint(g_temp.getDataX(b), g_temp.getDataY(b) + y_sig, g_temp.getDataEX(b), g_temp.getDataEY(b) );
		g_minus_threesig.get(s).addPoint(g_temp.getDataX(b),  g_temp.getDataY(b) - y_sig, g_sig.getDataEX(b), g_sig.getDataEY(b) );		    
	    }		
	}
	
       	EmbeddedCanvas c_g_meanfits_bc = new EmbeddedCanvas();
	c_g_meanfits_bc.setSize(1200,900);
	c_g_meanfits_bc.divide(2,3);
	//System.out.println(">> CHECKING G_SF SIZE BCLARY " + g_sf_sect_means_bc.size() );

	///////////////////////////////////////////////////////
	//CREATE MAP FOR STORING FIT PARAMETERS TO PUSH TO JSON
	Map<Integer, ArrayList<Double> > m_maxfit_para = new HashMap<Integer, ArrayList<Double> >();	
	Map<Integer, ArrayList<Double> > m_minfit_para = new HashMap<Integer, ArrayList<Double> >();	
	ArrayList<Double> temp_maxpara = new ArrayList<Double>();
	ArrayList<Double> temp_minpara = new ArrayList<Double>();

	for( int s = 0; s < 6; s++ ){
	    c_g_meanfits_bc.cd(s);
	    
	    Vector<H2F> v_temp = h2_el_sect_ectotp.get(s);
	    H2F h_temp = v_temp.get(4); //GET THE HISTOGRAM AFTER FIDUCIAL CUTS ARE APPLIED AND FIT THAT
	    GraphErrors g_mean2 = g_sf_sect_means_bc.get(s);
	    GraphErrors g_plus = g_plus_threesig.get(s);
	    GraphErrors g_minus= g_minus_threesig.get(s);

	    for( int b = 0; b < g_mean2.getVectorX().size(); b++ ){
 		//System.out.println(" >> " + g_mean2.getDataX(b) + " " + g_mean2.getDataY(b) );
	    }
	    
	    F1D f_sf_mean2 = new F1D("f_sf_mean2","[a] + [b]/(x-0.1)", 0.8, beam_energy);                                                                                                        
	    DataFitter.fit(f_sf_mean2, g_mean2,"REQ");

	    F1D f_sf_top = new F1D("f_sf_top","[a] + [b]/x + [c]/(x*x)", 0.8, beam_energy);
	    DataFitter.fit(f_sf_top, g_plus,"REQ");

	    F1D f_sf_bot = new F1D("f_sf_bot","[a] + [b]/x + [c]/(x*x)", 0.8, beam_energy);	    
	    DataFitter.fit(f_sf_bot, g_minus,"REQ");

	   
	    Double ma = f_sf_mean2.getParameter(0);
	    Double mb = f_sf_mean2.getParameter(1);
	    Double mc = 0.0;//f_sf_mean2.getParameter(2);
	    Double md = 0.0;//f_sf_mean2.getParameter(3);
	    
	    Double topa = f_sf_top.getParameter(0);
	    Double topb = f_sf_top.getParameter(1);
	    Double topc = f_sf_top.getParameter(2);
	    
	    Double bota = f_sf_bot.getParameter(0);
	    Double botb = f_sf_bot.getParameter(1);
	    Double botc = f_sf_bot.getParameter(2);
	    
	    temp_maxpara.clear();
	    temp_minpara.clear();

 	    temp_maxpara.add(topa);
 	    temp_maxpara.add(topb);
 	    temp_maxpara.add(topc);

 	    temp_minpara.add(bota);
 	    temp_minpara.add(botb);
 	    temp_minpara.add(botc);

	    m_maxfit_para.put(s,temp_maxpara);
	    m_minfit_para.put(s,temp_minpara);

	    f_sf_mean2.setLineWidth(5);
	    f_sf_mean2.setLineStyle(0);
	    f_sf_mean2.setLineColor(2);
	    
	    f_sf_top.setLineWidth(5);
	    f_sf_top.setLineStyle(2);
	    f_sf_top.setLineColor(2);

	    f_sf_bot.setLineWidth(5);
	    f_sf_bot.setLineStyle(2);
	    f_sf_bot.setLineColor(2);
	    
	    g_mean2.setTitle("Mean SF with #sigma vs p");
	    g_mean2.setTitleX("p [GeV]");
	    g_mean2.setTitleY("Mean SF");
	    g_mean2.setMarkerSize(2);
	    g_mean2.setMarkerStyle(0);
	    g_mean2.setMarkerColor(2);
	    	    
	    h_temp.setTitle("Sampling Fraction for Sector " + Integer.toString(s));
	    h_temp.setTitleX("p [GeV]");
	    h_temp.setTitleY("Etot/p");

	    c_g_meanfits_bc.draw(h_temp,"colz");       
	    c_g_meanfits_bc.draw(f_sf_top,"same");
	    c_g_meanfits_bc.draw(f_sf_bot,"same");
	    c_g_meanfits_bc.draw(f_sf_mean2,"same");
	    	
	    c_g_meanfits_bc.save(savepath  + "/g_meansf_sector_"+s_run_number+"_bc.png");

	}


	/////////////////////////////////////////////////////////////
	//WRITE TO JSON FILE
	/*RunPropertiesLoader run_properties = new RunPropertiesLoader();
	//run_properties.loadRunProperties(run_number);

	run_properties.getRunInfoClass().getRunParametersClass("r"+s_run_number).getCutTypeClass("cut_nom").getECCutParametersClass("ec_sf_cut").setMaxFitParametersSector1( m_maxfit_para.get(0) );
	run_properties.getRunInfoClass().getRunParametersClass("r"+s_run_number).getCutTypeClass("cut_nom").getECCutParametersClass("ec_sf_cut").setMaxFitParametersSector2( m_maxfit_para.get(1) );
	run_properties.getRunInfoClass().getRunParametersClass("r"+s_run_number).getCutTypeClass("cut_nom").getECCutParametersClass("ec_sf_cut").setMaxFitParametersSector3( m_maxfit_para.get(2) );
	run_properties.getRunInfoClass().getRunParametersClass("r"+s_run_number).getCutTypeClass("cut_nom").getECCutParametersClass("ec_sf_cut").setMaxFitParametersSector4( m_maxfit_para.get(3) );
	run_properties.getRunInfoClass().getRunParametersClass("r"+s_run_number).getCutTypeClass("cut_nom").getECCutParametersClass("ec_sf_cut").setMaxFitParametersSector5( m_maxfit_para.get(4) );
	run_properties.getRunInfoClass().getRunParametersClass("r"+s_run_number).getCutTypeClass("cut_nom").getECCutParametersClass("ec_sf_cut").setMaxFitParametersSector6( m_maxfit_para.get(5) );
	
	run_properties.getRunInfoClass().getRunParametersClass("r"+s_run_number).getCutTypeClass("cut_nom").getECCutParametersClass("ec_sf_cut").setMinFitParametersSector1( m_minfit_para.get(0) );
	run_properties.getRunInfoClass().getRunParametersClass("r"+s_run_number).getCutTypeClass("cut_nom").getECCutParametersClass("ec_sf_cut").setMinFitParametersSector2( m_minfit_para.get(1) );
	run_properties.getRunInfoClass().getRunParametersClass("r"+s_run_number).getCutTypeClass("cut_nom").getECCutParametersClass("ec_sf_cut").setMinFitParametersSector3( m_minfit_para.get(2) );
	run_properties.getRunInfoClass().getRunParametersClass("r"+s_run_number).getCutTypeClass("cut_nom").getECCutParametersClass("ec_sf_cut").setMinFitParametersSector4( m_minfit_para.get(3) );
	run_properties.getRunInfoClass().getRunParametersClass("r"+s_run_number).getCutTypeClass("cut_nom").getECCutParametersClass("ec_sf_cut").setMinFitParametersSector5( m_minfit_para.get(4) );
	run_properties.getRunInfoClass().getRunParametersClass("r"+s_run_number).getCutTypeClass("cut_nom").getECCutParametersClass("ec_sf_cut").setMinFitParametersSector6( m_minfit_para.get(5) );
	//run_properties.writeRunProperties();
	
	*/
	for( int sect = 0; sect < 6; sect++ ){	    
	    ArrayList<H1F> al_h_temp = m_el_sect_slices_ectotp.get(sect);
	    EmbeddedCanvas c_slices = new EmbeddedCanvas();
	    c_slices.setSize(900,900);
	    c_slices.divide(4,5 );
	    for( int slice = 0; slice < al_h_temp.size(); slice++ ){
		c_slices.cd(slice);
		al_h_temp.get(slice).setTitle("SECTOR " + sect + " SLICE " + slice );
		al_h_temp.get(slice).setTitleX("p [GeV]");
		al_h_temp.get(slice).setTitleY("SF");
		al_h_temp.get(slice).setOptStat(1110);
		c_slices.draw(al_h_temp.get(slice));
	    }
	    c_slices.save(savepath  + "h_"+s_run_number+"sf_slices_sector_"+sect+".png"); 
	}


	///%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	///%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	///%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	///%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	///%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%



	for( int i = 0; i < h2_el_sect_thetap.size(); i++ ){
 	    Vector<H2F> v_temp = h2_el_sect_thetap.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("p [GeV]");
		v_temp.get(j).setTitleY("#theta [deg]");
		//c_temp.draw(v_temp.get(j),"colz");
		//c_temp.save(savepath+v_temp.get(j).getTitle()+".png");
	    }
	}

	for( int i = 0; i < h2_el_sect_phip.size(); i++ ){
 	    Vector<H2F> v_temp = h2_el_sect_phip.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("p [GeV]");
		v_temp.get(j).setTitleY("#phi [deg]");
		//c_temp.draw(v_temp.get(j),"colz");
		//c_temp.save(savepath + v_temp.get(j).getTitle()+".png");
	    }
	}

	for( int i = 0; i < h2_el_sect_pcalecal.size(); i++ ){
 	    Vector<H2F> v_temp = h2_el_sect_pcalecal.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("PCAL [GeV]");
		v_temp.get(j).setTitleY("ECAL [GeV]");		
		//c_temp.draw(v_temp.get(j),"colz");
		//c_temp.save(savepath + v_temp.get(j).getTitle()+".png");
	    }
	}

	for( int i = 0; i < h2_el_sect_ectotp.size(); i++ ){
 	    Vector<H2F> v_temp = h2_el_sect_ectotp.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("p [GeV]");
		v_temp.get(j).setTitleY("Total Energy (PCAL + ECAL ) [GeV]");
		//c_temp.draw(v_temp.get(j),"colz");
		//c_temp.save(savepath +v_temp.get(j).getTitle()+".png");
	    }
	}

	for( int i = 0; i < h2_el_sect_ectotp2.size(); i++ ){
 	    Vector<H2F> v_temp = h2_el_sect_ectotp2.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("Total Energy [GeV]");
		v_temp.get(j).setTitleY("Total Energy (PCAL + ECAL )/p");
		//c_temp.draw(v_temp.get(j),"colz");
		//c_temp.save(savepath + v_temp.get(j).getTitle()+".png");
	    }
	}

	for( int i = 0; i < h2_el_sect_pcalp.size(); i++ ){
 	    Vector<H2F> v_temp = h2_el_sect_pcalp.get(i);       
	    for( int j = 0; j < v_temp.size(); j++){		
		EmbeddedCanvas c_temp = new EmbeddedCanvas();
		c_temp.setSize(800,800);
		v_temp.get(j).setTitleX("p [GeV]");
		v_temp.get(j).setTitleY("PCAL [GeV]");
		//c_temp.draw(v_temp.get(j),"colz");
		//c_temp.save(savepath + v_temp.get(j).getTitle()+".png");
	    }
	}


	/*for( int sect = 0; sect < 6; sect++ ){	    
	    ArrayList<H1F> al_h_temp = m_el_sect_slices_ectotp.get(sect);
	    EmbeddedCanvas c_slices = new EmbeddedCanvas();
	    c_slices.setSize(900,900);
	    c_slices.divide(4,5 );
	    for( int slice = 0; slice < al_h_temp.size(); slice++ ){
		c_slices.cd(slice);
		al_h_temp.get(slice).setTitle("SECTOR " + sect + " SLICE " + slice );
		al_h_temp.get(slice).setTitleX("p [GeV]");
		al_h_temp.get(slice).setTitleY("SF");
		al_h_temp.get(slice).setOptStat(1110);
		c_slices.draw(al_h_temp.get(slice));
	    }
	    c_slices.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/h_"+s_run_number+"sf_slices_sector_"+sect+".png"); 
	}

	*/

    }


    public void printCutRatesToText(){

	BufferedWriter writer = null;
	try{
	    String cut_rate_text = "cut_rates.txt";
	    writer = new BufferedWriter( new FileWriter(cut_rate_text) );
	    
	    int cut_lvl = 0;
	    for( H1F h_temp : h_el_p ){
		//dir.addDataSet(h_temp);
		int total_rate = h_temp.getEntries(); // h_temp.getUnderflow() + h_temp.getEntries() + h_temp.getOverflow();
		int under = h_temp.getUnderflow(); 
		int over = h_temp.getOverflow(); 
		System.out.println(" >> CUT LVL " + cut_lvl + " RATES " + total_rate + " UNDER " + under + " OVER " + over );
		writer.write(cut_lvl + " " + Integer.toString(total_rate) + "\n");				
		cut_lvl++;
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

	System.out.println(">> PRINTING CUT DISRIMINATOR POWER ");

	BufferedWriter writer2 = null;
	try{
	    String cut_rate_text2 = "cut_descrim.txt";
	    writer2 = new BufferedWriter( new FileWriter(cut_rate_text2) );
	    
 	    int cut_lvl2 = 0;
	    for( int total_rate2 : v_cutrates ){ 
		System.out.println(" >> CUT LVL " + cut_lvl2 + " RATES " + total_rate2 );
		writer2.write(cut_lvl2 + " " + Integer.toString(total_rate2) + "\n");	
		cut_lvl2++;
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
			if ( writer2 != null)
			    writer2.close( );
		    }
		catch ( IOException e)
		    {
			System.out.println(">> ANOTHER ERROR");
		    }
	    }    

	System.out.println(">> COMPLETED SAVING ELECTRON HISTOGRAMS");

    }

    public void viewHipoOut(){


	//dir.readFile("/u/home/bclary/CLAS12/phi_analysis/v2/v1/testing_cutlvl.hipo");
	//TBrowser browser = new TBrowser(dir);

	//dir.ls();
	
    }

}
