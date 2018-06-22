package org.jlab.clas.analysis.clary;

import java.io.*;
import javax.swing.JFrame;

import org.jlab.clas.analysis.clary.BEvent;
import org.jlab.clas.analysis.clary.PhysicsBuilder;
import org.jlab.clas.analysis.clary.Calculator;

import org.jlab.analysis.plotting.H1FCollection2D;
import org.jlab.analysis.plotting.H1FCollection3D;
import org.jlab.analysis.plotting.TCanvasP;
import org.jlab.analysis.plotting.TCanvasPTabbed;
import org.jlab.groot.graphics.EmbeddedCanvas;

import org.jlab.groot.group.*;
import org.jlab.groot.ui.TBrowser;
import org.jlab.groot.tree.*;
import org.jlab.groot.data.TDirectory;

import org.jlab.analysis.math.ClasMath;
import org.jlab.clas.physics.Vector3;
import org.jlab.clas.physics.LorentzVector;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.fitter.*;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.math.Axis;
import org.jlab.groot.graphics.GraphicsAxis;

import org.jlab.groot.math.Func1D;
import org.jlab.groot.math.F1D;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

public class BHistoPlotter {

    /*    JFrame frame_err_el = new JFrame();
    JFrame frame_err_pr = new JFrame();
    JFrame frame_err_kin = new JFrame();
    JFrame frame_comp_kin = new JFrame();
    JFrame f_fit_el = new JFrame();
    JFrame f_fit_pr = new JFrame();
    */
    TDirectory dir = new TDirectory();
    
    EmbeddedCanvas c_mc_el = new EmbeddedCanvas();
    EmbeddedCanvas c_mc_pr = new EmbeddedCanvas();
    EmbeddedCanvas c_mc_kp = new EmbeddedCanvas();
    EmbeddedCanvas c_mc_km = new EmbeddedCanvas();

    EmbeddedCanvas c_rc_el = new EmbeddedCanvas();
    EmbeddedCanvas c_rc_pr = new EmbeddedCanvas();
    EmbeddedCanvas c_rc_kp = new EmbeddedCanvas();
    EmbeddedCanvas c_rc_km = new EmbeddedCanvas();

    EmbeddedCanvas c_rc_el_2 = new EmbeddedCanvas();

    EmbeddedCanvas c_rec_el_kin = new EmbeddedCanvas();
    EmbeddedCanvas c_rec_el_res = new EmbeddedCanvas();
    EmbeddedCanvas c_rec_el_kinres = new EmbeddedCanvas();

    EmbeddedCanvas c2_rec_el_kin = new EmbeddedCanvas();
    EmbeddedCanvas c2_rec_el_inv = new EmbeddedCanvas();
    EmbeddedCanvas c2_rec_el_res = new EmbeddedCanvas();
    EmbeddedCanvas c2_rec_el_invres = new EmbeddedCanvas();

    EmbeddedCanvas c_rc_pr_2 = new EmbeddedCanvas();

    EmbeddedCanvas c_rec_pr_kin = new EmbeddedCanvas();
    EmbeddedCanvas c_rec_pr_res = new EmbeddedCanvas();
    EmbeddedCanvas c_rec_pr_kinres = new EmbeddedCanvas();

    EmbeddedCanvas c2_rec_pr_kin = new EmbeddedCanvas();
    EmbeddedCanvas c2_rec_pr_inv = new EmbeddedCanvas();
    EmbeddedCanvas c2_rec_pr_res = new EmbeddedCanvas();
    EmbeddedCanvas c2_rec_pr_invres = new EmbeddedCanvas();

    EmbeddedCanvas c_rec_kp_kin = new EmbeddedCanvas();
    EmbeddedCanvas c_rec_kp_res = new EmbeddedCanvas();
    EmbeddedCanvas c2_rec_kp_res = new EmbeddedCanvas();

    EmbeddedCanvas c_err_el = new EmbeddedCanvas();
    EmbeddedCanvas c_err_pr = new EmbeddedCanvas();
    EmbeddedCanvas c_err_kp = new EmbeddedCanvas();
    EmbeddedCanvas c_err_km = new EmbeddedCanvas();
    EmbeddedCanvas c_err_kin = new EmbeddedCanvas();

    EmbeddedCanvas c_comp_el_kin = new EmbeddedCanvas();
    EmbeddedCanvas c_comp_pr_kin = new EmbeddedCanvas();

    EmbeddedCanvas c_comp_el_inv = new EmbeddedCanvas();
    EmbeddedCanvas c_comp_pr_inv = new EmbeddedCanvas();

    EmbeddedCanvas c_fit_el = new EmbeddedCanvas();
    EmbeddedCanvas c_fit_pr = new EmbeddedCanvas();
        
    H1F h_mc_el_px; H1F h_mc_el_py; H1F h_mc_el_pz;
    H1F h_mc_pr_px; H1F h_mc_pr_py; H1F h_mc_pr_pz;
    H1F h_mc_kp_px; H1F h_mc_kp_py; H1F h_mc_kp_pz;
    H1F h_mc_km_px; H1F h_mc_km_py; H1F h_mc_km_pz;

    H1F h_rc_el_px; H1F h_rc_el_py; H1F h_rc_el_pz; H1F h_rc_el_e;
    H1F h_rc_pr_px; H1F h_rc_pr_py; H1F h_rc_pr_pz; H1F h_rc_pr_e;
    H1F h_rc_kp_px; H1F h_rc_kp_py; H1F h_rc_kp_pz; H1F h_rc_kp_e;
    H1F h_rc_km_px; H1F h_rc_km_py; H1F h_rc_km_pz; H1F h_rc_km_e;

    //////////////////////////////////////////////////////////////////////////////////

    H1F h_rc_el_p; H1F h_rc_el_theta; H1F h_rc_el_phi;
    H1F h_rc_el_q2; H1F h_rc_el_xb; H1F h_rc_el_w;
    H1F h_rc_el_res_delp; H1F h_rc_el_res_p; H1F h_rc_el_res_theta; H1F h_rc_el_res_phi;
    H1F h_rc_el_res_q2; H1F h_rc_el_res_xb; H1F h_rc_el_res_w;

    H2F h2_rc_el_p;  H2F h2_rc_el_fracp;  H2F h2_rc_el_theta;  H2F h2_rc_el_phi;
    H2F h2_rc_el_q2; H2F h2_rc_el_x; H2F h2_rc_el_w;
    H2F h2_rc_el_q2_x; H2F h2_rc_el_q2_w;

    H1F h_rc_pr_p; H1F h_rc_pr_theta; H1F h_rc_pr_phi;
    H1F h_rc_pr_t; H1F h_rc_pr_phicm;
    H1F h_rc_pr_res_delp; H1F h_rc_pr_res_p; H1F h_rc_pr_res_theta; H1F h_rc_pr_res_phi;
    H1F h_rc_pr_res_t; H1F h_rc_pr_res_phicm;    

    H2F h2_rc_pr_p;  H2F h2_rc_pr_fracp;  H2F h2_rc_pr_theta;  H2F h2_rc_pr_phi;
    H2F h2_rc_pr_t;  H2F h2_rc_pr_phicm;
    H2F h2_rc_pr_t_phicm;
    //////////////
    H1F h_rc_kp_p; H1F h_rc_kp_theta; H1F h_rc_kp_phi;
    H1F h_rc_kp_t; H1F h_rc_kp_phicm;
    H1F h_rc_kp_res_delp; H1F h_rc_kp_res_p; H1F h_rc_kp_res_theta; H1F h_rc_kp_res_phi;
    H1F h_rc_kp_res_t; H1F h_rc_kp_res_phicm;

    H2F h2_rc_kp_p;  H2F h2_rc_kp_fracp;  H2F h2_rc_kp_theta;  H2F h2_rc_kp_phi;

    /////////////////////////////////////////////////////////////////////////////////
    H2F h2_rc_el_ptheta;
    H2F h2_rc_pr_ptheta;
    H2F h2_rc_kp_ptheta;
    H2F h2_rc_km_ptheta;

    H2F h2_rc_el_pphi;
    H2F h2_rc_pr_pphi;
    H2F h2_rc_kp_pphi;
    H2F h2_rc_km_pphi;
    
    H2F h2_rc_el_vzphi;
    H2F h2_rc_pr_vzphi;
    H2F h2_rc_kp_vzphi;
    H2F h2_rc_km_vzphi;

    H2F h2_rc_el_thetaphi;
    H2F h2_rc_pr_thetaphi;
    H2F h2_rc_kp_thetaphi;
    H2F h2_rc_km_thetaphi;

    ////////////////////////////////////////////////////////////////////////////////
    H1F h_mc_el_p;
    H1F h_mc_el_theta;
    H1F h_mc_el_phi;
    
    H1F h_mc_pr_p;
    H1F h_mc_pr_theta;
    H1F h_mc_pr_phi;  

    H1F h_mc_el_q2, h_mc_el_w, h_mc_el_xb;

    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////

    double min_phi = -60.0; double max_phi = 360.0;
    double joo_angle = 150.0;

    public void CreateHistograms(){

	//frame_err_el.setSize(800,800);
	//frame_err_pr.setSize(800,800);
	//frame_err_kin.setSize(800,800);
	//frame_comp_kin.setSize(800,800);

	h_mc_el_px = new H1F("h_mc_el_px","h_mc_el_px",100, -2.0, 2.0 );
	h_mc_el_py = new H1F("h_mc_el_px","h_mc_el_py",100, -2.0, 2.0 );
	h_mc_el_pz = new H1F("h_mc_el_px","h_mc_el_pz",100, 0.0, 12.0 );

	h_mc_pr_px = new H1F("h_mc_pr_px","h_mc_pr_px",100, -2.0, 2.0 );
	h_mc_pr_py = new H1F("h_mc_pr_py","h_mc_pr_py",100, -2.0, 2.0 );
	h_mc_pr_pz = new H1F("h_mc_pr_pz","h_mc_pr_pz",100, 0.0, 12.0 );

	h_mc_kp_px = new H1F("h_mc_kp_px","h_mc_kp_px",100, -2.0, 2.0 );
	h_mc_kp_py = new H1F("h_mc_kp_py","h_mc_kp_py",100, -2.0, 2.0 );
	h_mc_kp_pz = new H1F("h_mc_kp_pz","h_mc_kp_pz",100, 0.0, 12.0 );

	h_mc_km_px = new H1F("h_mc_kp_px","h_mc_km_px",100, -2.0, 2.0 );
	h_mc_km_py = new H1F("h_mc_kp_py","h_mc_km_py",100, -2.0, 2.0 );
	h_mc_km_pz = new H1F("h_mc_kp_pz","h_mc_km_pz",100, 0.0, 12.0 );

	h_rc_el_px = new H1F("h_rc_el_px","h_rc_el_px",100, -2.0, 2.0 );
	h_rc_el_py = new H1F("h_rc_el_py","h_rc_el_py",100, -2.0, 2.0 );
	h_rc_el_pz = new H1F("h_rc_el_pz","h_rc_el_pz",100, 0.0, 12.0 );
	h_rc_el_e = new H1F("h_rc_el_e","h_rc_el_e",100, 0.0, 12.0 );

	h_rc_pr_px = new H1F("h_rc_pr_px","h_rc_pr_px",100, -2.0, 2.0 );
	h_rc_pr_py = new H1F("h_rc_pr_py","h_rc_pr_py",100, -2.0, 2.0 );
	h_rc_pr_pz = new H1F("h_rc_pr_pz","h_rc_pr_pz",100, 0.0, 12.0 );
	h_rc_pr_e = new H1F("h_rc_pr_e","h_rc_pr_e",100, 0.0, 12.0 );

	h_rc_kp_px = new H1F("h_rc_kp_px","h_rc_kp_px",100, -2.0, 2.0 );
	h_rc_kp_py = new H1F("h_rc_kp_py","h_rc_kp_py",100, -2.0, 2.0 );
	h_rc_kp_pz = new H1F("h_rc_kp_pz","h_rc_kp_pz",100, 0.0, 12.0 );
	h_rc_kp_e = new H1F("h_rc_kp_e","h_rc_kp_e",100, 0.0, 12.0 );

	h_rc_km_px = new H1F("h_rc_kp_px","h_rc_km_px",100, -2.0, 2.0 );
	h_rc_km_py = new H1F("h_rc_kp_py","h_rc_km_py",100, -2.0, 2.0 );
	h_rc_km_pz = new H1F("h_rc_kp_pz","h_rc_km_pz",100, 0.0, 12.0 );
	h_rc_km_e = new H1F("h_rc_km_e","h_rc_km_e",100, 0.0, 12.0 );
 
	h2_rc_el_ptheta = new H2F("h2_rc_el_ptheta","h2_rc_el_ptheta",100,0,9.0,100,0,50.0);
	h2_rc_pr_ptheta = new H2F("h2_rc_pr_ptheta","h2_rc_pr_ptheta",100,0,3.0,100,10,70.0);
	h2_rc_kp_ptheta = new H2F("h2_rc_kp_ptheta","h2_rc_kp_ptheta",100,0,4.0,100,0,50.0);
	h2_rc_km_ptheta = new H2F("h2_rc_km_ptheta","h2_rc_km_ptheta",100,0,4.0,100,0,50.0);
 
	h2_rc_el_pphi = new H2F("h2_rc_el_pphi","h2_rc_el_pphi",100,0,9.0,100, min_phi, max_phi);
	h2_rc_pr_pphi = new H2F("h2_rc_pr_pphi","h2_rc_pr_pphi",100, min_phi, max_phi ,100,0.0,3.5);

	h2_rc_el_vzphi = new H2F("h2_rc_el_vzphi","h2_rc_el_vzphi",100, min_phi, max_phi, 100, -10.0,10.0);
	h2_rc_pr_vzphi = new H2F("h2_rc_pr_vzphi","h2_rc_pr_vzphi",100, min_phi, max_phi, 100, -10.0,10.0);
	h2_rc_kp_vzphi = new H2F("h2_rc_kp_vzphi","h2_rc_kp_zphi",100, min_phi, max_phi, 100, -10.0,10.0);
	h2_rc_km_vzphi = new H2F("h2_rc_km_vzphi","h2_rc_km_vzphi",100, min_phi, max_phi, 100, -10.0,10.0);

	h2_rc_el_thetaphi = new H2F("h2_rc_el_thetaphi","h2_rc_el_thetaphi",100, min_phi,max_phi,100,0.0,40.0);
	h2_rc_pr_thetaphi = new H2F("h2_rc_pr_thetaphi","h2_rc_pr_thetaphi",100, min_phi,max_phi,100,0.0,60.0);
	h2_rc_kp_thetaphi = new H2F("h2_rc_kp_thetaphi","h2_rc_kp_thetaphi",100, min_phi,max_phi,100,0.0,40.0);
	h2_rc_km_thetaphi = new H2F("h2_rc_km_thetaphi","h2_rc_km_thetaphi",100, min_phi,max_phi,100,0.0,40.0);


	//////////////////////////////////////////////////////////////////////
	//PARTICLE HISTS
	h_rc_el_p = new H1F("h_rc_el_p","h_rc_el_p",100, 0.0, 11.0);
	h_rc_el_theta = new H1F("h_rc_el_theta","h_rc_el_theta",100, 0.0, 50.0);
	h_rc_el_phi = new H1F("h_rc_el_phi","h_rc_el_phi",100,min_phi,max_phi);

	h_rc_pr_p = new H1F("h_rc_pr_p","h_rc_pr_p",100, 0.0, 4.0);
	h_rc_pr_theta = new H1F("h_rc_pr_theta","h_rc_pr_theta",100, 0.0, 60.0);
	h_rc_pr_phi = new H1F("h_rc_pr_phi","h_rc_pr_phi",100, min_phi,max_phi);

	h_rc_kp_p = new H1F("h_rc_kp_p","h_rc_kp_p",100, 0.0, 5.5);
	h_rc_kp_theta = new H1F("h_rc_kp_theta","h_rc_kp_theta",100, 0.0, 60.0);
	h_rc_kp_phi = new H1F("h_rc_kp_phi","h_rc_kp_phi",100, min_phi,max_phi);
	
	//////////////////////////////////////////////////////////////////////
	//KIN HISTOS
	h_rc_el_q2 = new H1F("h_rc_el_q2","h_rc_el_q2",100, 0.0, 11.0 );
	h_rc_el_xb = new H1F("h_rc_el_xb","h_rc_el_xb",100, 0.0, 1.0 );
	h_rc_el_w = new H1F("h_rc_el_w","h_rc_el_w",100, 0.0, 5.0 );

	h2_rc_el_q2_x = new H2F("h2_rc_el_q2_x","h2_rc_el_q2_x",100, 0.0, 1.1, 100, 0.0, 10.0 );
	h2_rc_el_q2_w = new H2F("h2_rc_el_q2_w","h2_rc_el_q2_w",100, 0.0, 10.0, 100, 0.0, 10.0 );

	h_rc_pr_t = new H1F("h_rc_pr_t","h_rc_pr_t",100, -1.0, 3.0 );
	h_rc_pr_phicm = new H1F("h_rc_pr_phicm","h_rc_pr_phicm",100,min_phi,max_phi);

	h2_rc_pr_t_phicm = new H2F("h2_rc_pr_t_phicm","h2_rc_pr_t_phicm", 100,min_phi,max_phi, 100, 0.0, 4.0 );

	//////////////////////////////////////////////////////////////////////
	//ELECTRON RESOLUTION
	h_rc_el_res_delp = new H1F("h_rc_el_res_delp","h_rc_el_res_delp", 100, -0.5, 0.5 );
	h_rc_el_res_p = new H1F("h_rc_el_res_p","h_rc_el_resp", 100, -0.10, 0.10 );
	h_rc_el_res_theta = new H1F("h_rc_el_res_theta","h_rc_el_res_theta", 100, -0.10, 0.10 );
	h_rc_el_res_phi = new H1F("h_rc_el_res_phi","h_rc_el_res_phi", 100, -0.10, 0.10 );

	h2_rc_el_p = new H2F("h2_rc_el_p","h2_rc_el_p", 100, 0.0, 11.0, 100, -0.2, 0.2);
	h2_rc_el_fracp = new H2F("h2_rc_el_fracp","h2_rc_el_fracp",100, 0.0, 11.0, 100, -0.035, 0.035 );
	h2_rc_el_theta = new H2F("h2_rc_el_theta","h2_rc_el_theta",100, 0.0, 25.0, 100, -0.11, 0.11 );
	h2_rc_el_phi = new H2F("h2_rc_el_phi","h2_rc_el_phi", 100, min_phi, max_phi, 100, -0.20, 0.20 );

	h_rc_el_res_q2 = new H1F("h_rc_el_res_q2","h_rc_el_res_q2", 100, -0.5, 0.5 );
	h_rc_el_res_xb = new H1F("h_rc_el_res_xb","h_rc_el_xb", 100, -0.10, 0.10 );
	h_rc_el_res_w = new H1F("h_rc_el_res_w","h_rc_el_res_w", 100, -0.10, 0.10 );

	h2_rc_el_q2 = new H2F("h2_rc_el_q2","h2_rc_el_q2", 100, 0.0, 10.0, 100, -0.2, 0.2 );
	h2_rc_el_x = new H2F("h2_rc_el_x","h2_rc_el_x", 100, 0.0, 0.8, 100, -0.04, 0.04 );
	h2_rc_el_w = new H2F("h2_rc_el_w","h2_rc_el_w", 100, 0.0, 6.0, 100, -0.10, 0.10 );

	//////////////////////
	//PROTONS
	h_rc_pr_res_delp = new H1F("h_rc_pr_res_delp","h_rc_pr_res_delp", 100, -0.5, 0.5 );
	h_rc_pr_res_p = new H1F("h_rc_pr_res_p","h_rc_pr_resp", 100, -0.10, 0.10 );
	h_rc_pr_res_theta = new H1F("h_rc_pr_res_theta","h_rc_pr_res_theta", 100, -10.0, 10.0 );
	h_rc_pr_res_phi = new H1F("h_rc_pr_res_phi","h_rc_pr_res_phi", 100, -10.0, 10.0 );

	h_rc_pr_res_t = new H1F("h_rc_pr_res_t","h_rc_pr_res_t", 100, -0.5, 0.5 );
	h_rc_pr_res_phicm = new H1F("h_rc_pr_res_phicm","h_rc_pr_phicm", 100, -0.10, 0.10 );

	h2_rc_pr_p = new H2F("h2_rc_pr_p","h2_rc_pr_p", 100, 0.0, 3.5, 100, -0.15, 0.15);
	h2_rc_pr_fracp = new H2F("h2_rc_pr_fracp","h2_rc_pr_fracp",100, 0.0, 3.5, 100, -0.15, 0.15 );
	h2_rc_pr_theta = new H2F("h2_rc_pr_theta","h2_rc_pr_theta",100, 0.0, 70.0, 100, -0.11, 0.11 );
	h2_rc_pr_phi = new H2F("h2_rc_pr_phi","h2_rc_pr_phi", 100, min_phi,max_phi, 100, -5.0, 5.0 );

	h2_rc_pr_t = new H2F("h2_rc_pr_t","h2_rc_pr_t", 100, -2.0, 3.0, 100, -0.2, 0.2 );
	h2_rc_pr_phicm = new H2F("h2_rc_pr_phicm","h2_rc_pr_phicm", 100, min_phi,max_phi, 100, -1.4, 1.4 );

	h2_rc_pr_t_phicm = new H2F("h2_rc_pr_t_phicm","h2_rc_pr_t_phicm",100, 0.0, 4.0, 100,min_phi,max_phi);

	//////////////////////////////////////////////
	//KAON PLUS 
	h_rc_kp_res_delp = new H1F("h_rc_kp_res_delp","h_rc_kp_res_delp", 100, -0.15, 0.15 );
	h_rc_kp_res_p = new H1F("h_rc_kp_res_p","h_rc_kp_resp", 100, -0.05, 0.05 );
	h_rc_kp_res_theta = new H1F("h_rc_kp_res_theta","h_rc_kp_res_theta", 100, -1.0, 1.0 );
	h_rc_kp_res_phi = new H1F("h_rc_kp_res_phi","h_rc_kp_res_phi", 100, -2.2, 2.2 );

	h2_rc_kp_p = new H2F("h2_rc_kp_p","h2_rc_kp_p", 100, 0.0, 6.0, 100, -0.15, 0.15);
	h2_rc_kp_fracp = new H2F("h2_rc_kp_fracp","h2_rc_kp_fracp",100, 0.0, 6.0, 100, -0.05, 0.05 );
	h2_rc_kp_theta = new H2F("h2_rc_kp_theta","h2_rc_kp_theta",100, 0.0, 35.0, 100, -1.0, 1.0 );
	h2_rc_kp_phi = new H2F("h2_rc_kp_phi","h2_rc_kp_phi", 100, min_phi, max_phi, 100, -4.0, 4.0 );	

	////////////////////////////////
	//MC ELECTRONS
	h_mc_el_p = new H1F("h_mc_el_p","h_mc_el_p",100,0.0, 10.5);
	h_mc_el_theta = new H1F("h_mc_el_theta","h_mc_el_theta",100,0.0, 70.0);
	h_mc_el_phi = new H1F("h_mc_el_phi","h_mc_el_phi",100, -60.0, 360.0);
	
	//////////////////////////////
	//MC PROTONS
	h_mc_pr_p = new H1F("h_mc_pr_p","h_mc_pr_p",100,0.0, 4.0);
	h_mc_pr_theta = new H1F("h_mc_pr_theta","h_mc_pr_theta",100,0.0, 60.0);
	h_mc_pr_phi = new H1F("h_mc_pr_phi","h_mc_pr_phi",100, min_phi, max_phi);
	
	////////////////////////////////
	//MC INV KIN
	h_mc_el_q2 = new H1F("h_mc_el_q2","h_mc_el_q2", 100, 0.0, 6.5);
	h_mc_el_xb = new H1F("h_mc_el_xb","h_mc_el_xb", 100, 0.0, 1.0);
	h_mc_el_w = new H1F("h_mc_el_w","h_mc_el_w", 100, 0.0, 5.0);



	
    }

    public void CreatePhysHistograms(){

	

    }

    public void FillMCHistograms(DataEvent tempdevent){	
	DataBank mcbank = tempdevent.getBank("MC::Particle");

	int mc_el_index = PhysicalConstants.el_index;
	int mc_pr_index = PhysicalConstants.pr_index;

	LorentzVector mc_el = Calculator.lv_particle(mcbank, mc_el_index, PhysicalConstants.electronID);
	LorentzVector mc_pr = Calculator.lv_particle(mcbank, mc_pr_index, PhysicalConstants.protonID);
	
	h_mc_el_p.fill(mc_el.p());
	h_mc_el_theta.fill(mc_el.theta());
	h_mc_el_phi.fill(mc_el.phi() + joo_angle);
	
	h_mc_pr_p.fill(mc_pr.p());
	h_mc_pr_theta.fill(Math.toDegrees(mc_pr.theta()));
	h_mc_pr_phi.fill(Math.toDegrees(mc_pr.phi()) + joo_angle);      

	double mc_q2 = Calculator.Q2(mc_el);
	double mc_xb = Calculator.Xb(mc_el);
	double mc_w = Calculator.W(mc_el);

	h_mc_el_q2.fill(mc_q2);
	h_mc_el_xb.fill(mc_xb);
	h_mc_el_w.fill(mc_w);
	

    }

    public void FillElectronHistograms(DataEvent tempdevent, int rec_i){
	DataBank recbank = tempdevent.getBank("REC::Particle");
	float rec_px = recbank.getFloat("px",rec_i);
	float rec_py = recbank.getFloat("py",rec_i);
	float rec_pz = recbank.getFloat("pz",rec_i);
	float rec_vz = recbank.getFloat("vz",rec_i);

	DataBank mcbank = tempdevent.getBank("MC::Particle");
	int mc_el_index = PhysicalConstants.el_index;

	double rec_e = Math.sqrt( rec_px*rec_px + rec_py*rec_py + rec_pz*rec_pz + PhysicalConstants.mass_electron*PhysicalConstants.mass_electron );
	double rec_p = Math.sqrt( rec_px*rec_px + rec_py*rec_py + rec_pz*rec_pz);
	double rec_theta = Calculator.lv_theta(tempdevent,rec_i);
	double rec_phi = Calculator.lv_phi(tempdevent,rec_i);
	
	LorentzVector lv_el = Calculator.lv_particle(recbank, rec_i, PhysicalConstants.electronID);
	LorentzVector mc_lv_el = Calculator.lv_particle(mcbank, mc_el_index, PhysicalConstants.electronID);
	Double eBeam = PhysicalConstants.eBeam;
	
	double q2 = 4*eBeam*lv_el.e()*Math.sin(lv_el.theta()/2.0)*Math.sin(lv_el.theta()/2.0);
	double xb = q2/(2.0*PhysicalConstants.mass_proton*(eBeam - lv_el.e()));
	double w = Math.sqrt(-q2 + PhysicalConstants.mass_proton*PhysicalConstants.mass_proton + 2*PhysicalConstants.mass_proton*(eBeam - lv_el.e()));
	double mc_q2 = Calculator.Q2(mc_lv_el);
	double mc_xb = Calculator.Xb(mc_lv_el);
	double mc_w = Calculator.W(mc_lv_el);

	h_rc_el_px.fill(rec_px);
	h_rc_el_py.fill(rec_py);
	h_rc_el_pz.fill(rec_pz);
	h_rc_el_e.fill(rec_e);
	h2_rc_el_ptheta.fill(rec_p, rec_theta);
	h2_rc_el_pphi.fill( rec_p, rec_phi);
	h2_rc_el_vzphi.fill(rec_phi, rec_vz);
	h2_rc_el_thetaphi.fill(rec_phi, rec_theta);

	h_rc_el_p.fill( lv_el.p() );
	h_rc_el_theta.fill( Math.toDegrees(lv_el.theta()) );
	//	double lv_el_phi = lv_el.phi();
	//if( lv_el.phi() >= 330.0 ){ lv_el_phi = lv_el_phi - 330.0; }
	//else{
	//   lv_el_phi
	h_rc_el_phi.fill( Math.toDegrees(lv_el.phi()) + joo_angle );

	h_rc_el_res_delp.fill( lv_el.p() - mc_lv_el.p() );
	h_rc_el_res_p.fill( (lv_el.p() - mc_lv_el.p())/(mc_lv_el.p()) );
	h_rc_el_res_theta.fill( lv_el.theta() - mc_lv_el.theta() );
	h_rc_el_res_phi.fill( lv_el.phi() - mc_lv_el.phi() );

	h_rc_el_q2.fill(q2);
	h_rc_el_xb.fill(xb);
	h_rc_el_w.fill(w);

	h_rc_el_res_q2.fill(q2 - mc_q2);
	h_rc_el_res_xb.fill(xb - mc_xb);
	h_rc_el_res_w.fill(w - mc_w);

	//////////////////////////////////////////////////////////////////////////////
	//2-D HISTOS
	h2_rc_el_q2_x.fill(xb, q2);
	h2_rc_el_q2_w.fill(w, q2);

	double diff_p = lv_el.p() - mc_lv_el.p();
	double diff_theta = lv_el.theta() - mc_lv_el.theta();
	double diff_phi = lv_el.phi() - mc_lv_el.phi();

	h2_rc_el_p.fill( mc_lv_el.p(), diff_p  );
	h2_rc_el_fracp.fill( mc_lv_el.p(), diff_p/lv_el.p()  );
	h2_rc_el_theta.fill( Math.toDegrees(mc_lv_el.theta()), Math.toDegrees(diff_theta) );
	h2_rc_el_phi.fill( Math.toDegrees(mc_lv_el.phi()) + joo_angle, Math.toDegrees(diff_phi) );

	h2_rc_el_q2.fill( mc_q2, q2 - mc_q2 );
	h2_rc_el_x.fill( mc_xb, xb - mc_xb );
	h2_rc_el_w.fill( mc_w, w - mc_w );
	

    }

    public void FillProtonHistograms(DataEvent tempdevent, int rec_i){

	DataBank recbank = tempdevent.getBank("REC::Particle");
 	float rec_px = recbank.getFloat("px",rec_i);
	float rec_py = recbank.getFloat("py",rec_i);
	float rec_pz = recbank.getFloat("pz",rec_i);
	float rec_vz = recbank.getFloat("vz",rec_i);

	double rec_e = Math.sqrt( rec_px*rec_px + rec_py*rec_py + rec_pz*rec_pz + PhysicalConstants.mass_proton*PhysicalConstants.mass_proton);
	double rec_p = Math.sqrt( rec_px*rec_px + rec_py*rec_py + rec_pz*rec_pz);
	double rec_theta = Calculator.lv_theta(tempdevent,rec_i);
	double rec_phi = Calculator.lv_phi(tempdevent,rec_i);

	DataBank mcbank = tempdevent.getBank("MC::Particle");

	LorentzVector lv_pr = Calculator.lv_particle(recbank, rec_i, PhysicalConstants.protonID);
	int mc_pr_index = PhysicalConstants.pr_index;
	LorentzVector mc_lv_pr = Calculator.lv_particle(mcbank, mc_pr_index, PhysicalConstants.protonID);
	double t = Calculator.T(lv_pr);
	double mc_t = Calculator.T(mc_lv_pr);

	double pr_p = lv_pr.p();
	double pr_theta = Math.toDegrees( lv_pr.theta() );
	double pr_phi = Math.toDegrees(lv_pr.phi()) + joo_angle;

	double mc_pr_p = mc_lv_pr.p();
	double mc_pr_theta = Math.toDegrees( mc_lv_pr.theta() );
	double mc_pr_phi = Math.toDegrees(mc_lv_pr.phi()) + joo_angle;
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////


	h_rc_pr_px.fill(rec_px);
	h_rc_pr_py.fill(rec_py);
	h_rc_pr_pz.fill(rec_pz);
	h_rc_pr_e.fill(rec_e);
 	h2_rc_pr_ptheta.fill(rec_p, rec_theta);
	h2_rc_pr_vzphi.fill(rec_phi, rec_vz);
	h2_rc_pr_pphi.fill( rec_phi, rec_p);
	h2_rc_pr_thetaphi.fill(rec_phi, rec_theta);
	////////////////////////////////////////////////////

	h_rc_pr_p.fill(pr_p);
	h_rc_pr_theta.fill(pr_theta);
	h_rc_pr_phi.fill(pr_phi);

	
	h_rc_pr_res_delp.fill( pr_p - mc_pr_p );
	h_rc_pr_res_p.fill( (pr_p - mc_pr_p)/pr_p );
	h_rc_pr_res_theta.fill( pr_theta - mc_pr_theta );
	h_rc_pr_res_phi.fill( pr_phi - mc_pr_phi );

	h_rc_pr_res_t.fill( t - mc_t );

	h_rc_pr_t.fill( t );
	       	
	h2_rc_pr_p.fill( mc_pr_p, pr_p - mc_pr_p );
	h2_rc_pr_fracp.fill( mc_pr_p, (pr_p - mc_pr_p)/ pr_p );      
	h2_rc_pr_theta.fill(mc_pr_theta, pr_theta - mc_pr_theta);
	h2_rc_pr_phi.fill(mc_pr_phi, pr_phi - mc_pr_phi);
		
	h2_rc_pr_t.fill( mc_t, t - mc_t );
    }


    public void FillKaonPlusHistograms(DataEvent tempdevent, int rec_i){

	DataBank recbank = tempdevent.getBank("REC::Particle");
 	float rec_px = recbank.getFloat("px",rec_i);
	float rec_py = recbank.getFloat("py",rec_i);
	float rec_pz = recbank.getFloat("pz",rec_i);
	float rec_vz = recbank.getFloat("vz",rec_i);

	double rec_e = Math.sqrt( rec_px*rec_px + rec_py*rec_py + rec_pz*rec_pz + PhysicalConstants.mass_kaon*PhysicalConstants.mass_kaon);
	double rec_p = Math.sqrt( rec_px*rec_px + rec_py*rec_py + rec_pz*rec_pz);	
	double rec_theta = Calculator.lv_theta(tempdevent,rec_i);
	double rec_phi = Calculator.lv_phi(tempdevent,rec_i);

	DataBank mcbank = tempdevent.getBank("MC::Particle");

	LorentzVector lv_kp = Calculator.lv_particle(recbank, rec_i, PhysicalConstants.kaonplusID);
	int mc_kp_index = PhysicalConstants.kp_index;
	LorentzVector mc_lv_kp = Calculator.lv_particle(mcbank, mc_kp_index, PhysicalConstants.kaonminusID);


	double kp_p = lv_kp.p();
	double kp_theta = Math.toDegrees( lv_kp.theta() );
	double kp_phi = Math.toDegrees(lv_kp.phi()) + joo_angle;

	double mc_kp_p = mc_lv_kp.p();
	double mc_kp_theta = Math.toDegrees( mc_lv_kp.theta() );
	double mc_kp_phi = Math.toDegrees(mc_lv_kp.phi()) + joo_angle;

	h_rc_kp_px.fill(rec_px);
	h_rc_kp_py.fill(rec_py);
	h_rc_kp_pz.fill(rec_pz);
	h_rc_kp_e.fill(rec_p);
	h2_rc_kp_ptheta.fill(rec_p, rec_theta);
	h2_rc_kp_vzphi.fill(rec_phi, rec_vz);
	h2_rc_kp_thetaphi.fill(rec_phi, rec_theta);
	///////////////////////////////////////////////////////////////////
	h_rc_kp_p.fill(kp_p);
	h_rc_kp_theta.fill(kp_theta);
	h_rc_kp_phi.fill(kp_phi);

	
	h_rc_kp_res_delp.fill( kp_p - mc_kp_p );
	h_rc_kp_res_p.fill( (kp_p - mc_kp_p)/kp_p );
	h_rc_kp_res_theta.fill( kp_theta - mc_kp_theta );
	h_rc_kp_res_phi.fill( kp_phi - mc_kp_phi );

	       	
	h2_rc_kp_p.fill( mc_kp_p, kp_p - mc_kp_p );
	h2_rc_kp_fracp.fill( mc_kp_p, (kp_p - mc_kp_p)/ kp_p );      
	h2_rc_kp_theta.fill(mc_kp_theta, kp_theta - mc_kp_theta);
	h2_rc_kp_phi.fill(mc_kp_phi, kp_phi - mc_kp_phi);
		
    }

    public void FillKaonMinusHistograms(DataEvent tempdevent, int rec_i){

	DataBank recbank = tempdevent.getBank("REC::Particle");
 	float rec_px = recbank.getFloat("px",rec_i);
	float rec_py = recbank.getFloat("py",rec_i);
	float rec_pz = recbank.getFloat("pz",rec_i);
	float rec_vz = recbank.getFloat("vz",rec_i);

	double rec_e = Math.sqrt( rec_px*rec_px + rec_py*rec_py + rec_pz*rec_pz + PhysicalConstants.mass_kaon*PhysicalConstants.mass_kaon);
	double rec_p = Math.sqrt( rec_px*rec_px + rec_py*rec_py + rec_pz*rec_pz);
	double rec_theta = Calculator.lv_theta(tempdevent,rec_i);
	double rec_phi = Calculator.lv_phi(tempdevent,rec_i);

	h_rc_km_px.fill(rec_px);
	h_rc_km_py.fill(rec_py);
	h_rc_km_pz.fill(rec_pz);
	h_rc_km_e.fill(rec_e);

	h2_rc_km_ptheta.fill(rec_p, rec_theta);
	h2_rc_km_vzphi.fill(rec_phi, rec_vz);
	h2_rc_km_thetaphi.fill(rec_phi, rec_theta);

    }
    
    public void FillPhysicsHistograms(PhysicsEvent physicsevent ){

	h_rc_el_px.fill(physicsevent.lv_el.px());
	h_rc_el_py.fill(physicsevent.lv_el.py());
	h_rc_el_pz.fill(physicsevent.lv_el.pz());
	h_rc_el_e.fill(physicsevent.lv_el.e());

	double el_theta = Math.acos(physicsevent.lv_el.pz()/ physicsevent.lv_el.p()) * 180.0/Math.PI;
	//System.out.println(">> " + physicsevent.lv_el.pz() + " " + physicsevent.lv_el.p() + " " + el_theta);
	h2_rc_el_ptheta.fill( physicsevent.lv_el.p(), Math.toDegrees(physicsevent.lv_el.theta()) );
	
	h_rc_pr_px.fill(physicsevent.lv_pr.px());
	h_rc_pr_py.fill(physicsevent.lv_pr.py());
	h_rc_pr_pz.fill(physicsevent.lv_pr.pz());
	h_rc_pr_e.fill(physicsevent.lv_pr.e());
	double pr_theta = Math.acos(physicsevent.lv_pr.pz()/ physicsevent.lv_pr.p()) * 180.0/Math.PI;
	h2_rc_pr_ptheta.fill( physicsevent.lv_pr.p(), Math.toDegrees(physicsevent.lv_pr.theta()) );

	h_rc_kp_px.fill(physicsevent.lv_kp.px() );
	h_rc_kp_py.fill(physicsevent.lv_kp.py() );
	h_rc_kp_pz.fill(physicsevent.lv_kp.pz() );
	h_rc_kp_e.fill(physicsevent.lv_kp.e() );
	double kp_theta = Math.acos(physicsevent.lv_kp.pz()/ physicsevent.lv_kp.p()) * 180.0/Math.PI;
	h2_rc_kp_ptheta.fill( physicsevent.lv_kp.p(), kp_theta);

	h_rc_km_px.fill(physicsevent.lv_km.px() );
	h_rc_km_py.fill(physicsevent.lv_km.py() );
	h_rc_km_pz.fill(physicsevent.lv_km.pz() );
	h_rc_km_e.fill(physicsevent.lv_km.e() );
	double km_theta = Math.acos(physicsevent.lv_km.pz()/ physicsevent.lv_km.p()) * 180.0/Math.PI;
	h2_rc_km_ptheta.fill( physicsevent.lv_km.p(), km_theta);

    }


    public F1D FitHistogram( H1F h_temp ){

 	System.out.println(" >> FITTING HISTOGRAM " + h_temp.getName() );
	double xlow, xhigh, histmax;
	int binlow, binhigh, binmax;

	double percentofmax = 0.25;
	if( h_temp.getName() == "h_rc_el_res_w" ){
	    percentofmax = 0.40;
	} 
    

	F1D fit = null;

	//if( h_temp.getEntries() > 0 ){
	binmax = h_temp.getMaximumBin();
	histmax = h_temp.getMax();
	binlow = binmax;
	binhigh = binmax;
	try{	
	    while( h_temp.getBinContent(binhigh++) >= percentofmax*histmax && binhigh <= h_temp.getAxis().getNBins() ){}
	    while( h_temp.getBinContent(binlow--) >= percentofmax*histmax && binlow > 1 ){}
	    
	    xlow = h_temp.getDataX(binlow) - h_temp.getDataEX(binlow)/2.0; // needs to be low edge, only center now
	    xhigh = h_temp.getDataX(binhigh+1) - h_temp.getDataEX(binhigh+1)/2.0;
	    
	    //System.out.println(" >> values used " + xlow + " " + xhigh + " " + histmax );
	    
	    F1D fit_temp = new F1D("fit_temp","[amp]*gaus(x,[mean],[sigma])", xlow, xhigh );
	    fit_temp.setParameter(0, histmax);
	    fit_temp.setParameter(1, h_temp.getMean() );
	    fit_temp.setParameter(2, h_temp.getRMS() );
	    
	    DataFitter.fit(fit_temp, h_temp, "R");
	    fit = fit_temp;  

	    //}
	    System.out.println(" >> PARAMETER SET " + fit_temp.getParameter(0) + " " + fit_temp.getParameter(1) + " " + fit_temp.getParameter(2) );

	}
	catch(Exception e){
	    System.out.println("ERROR WITH FITTING - LIKELY IT DID NOT CONVERGE");
	}
	return fit;
	    
    }


    public void ViewHistograms(){

	c_mc_el.setSize(1600,400);
	c_mc_el.divide(3,1);

	c_mc_el.cd(0);
	h_mc_el_px.setTitleX("p_x [GeV]");
 	c_mc_el.draw(h_mc_el_px);
	h_rc_el_px.setLineColor(21);
	h_rc_el_px.setOptStat(1110);
 	c_mc_el.draw(h_rc_el_px,"same");

	c_mc_el.cd(1);
	h_mc_el_py.setTitleX("GEN p_y [GeV]");
 	c_mc_el.draw(h_mc_el_py);
	h_rc_el_py.setLineColor(21);
	h_rc_el_py.setOptStat(1110);
 	c_mc_el.draw(h_rc_el_py,"same");

	c_mc_el.cd(2);
	h_mc_el_pz.setTitleX("GEN p_z [GeV]");
 	c_mc_el.draw(h_mc_el_pz);
	h_rc_el_pz.setLineColor(21);
 	c_mc_el.draw(h_rc_el_pz,"same");

	c_mc_pr.setSize(1600,400);
	c_mc_pr.divide(3,1);
	c_mc_pr.cd(0);
	h_mc_pr_px.setTitleX("GEN p_x [GeV]");
 	c_mc_pr.draw(h_mc_pr_px);
	h_rc_pr_px.setLineColor(21);
 	c_mc_pr.draw(h_rc_pr_px,"same");

	c_mc_pr.cd(1);
	h_mc_pr_py.setTitleX("GEN p_y [GeV]");
 	c_mc_pr.draw(h_mc_pr_py);
	h_rc_pr_py.setLineColor(21);
 	c_mc_pr.draw(h_rc_pr_py,"same");

	c_mc_pr.cd(2);
	h_mc_pr_pz.setTitleX("GEN p_z [GeV]");
 	c_mc_pr.draw(h_mc_pr_pz);
	h_rc_pr_pz.setLineColor(21);
 	c_mc_pr.draw(h_rc_pr_pz,"same");

	c_mc_kp.setSize(1600,400);
	c_mc_kp.divide(3,1);
	c_mc_kp.cd(0);
	h_mc_kp_px.setTitleX(" GEN p_x [GeV]");
 	c_mc_kp.draw(h_mc_kp_px);
	h_rc_kp_px.setLineColor(21);
 	c_mc_kp.draw(h_rc_kp_px,"same");

	c_mc_kp.cd(1);
	h_mc_kp_py.setTitleX(" GEN p_y [GeV]");
 	c_mc_kp.draw(h_mc_kp_py);
	h_rc_kp_py.setLineColor(21);
 	c_mc_kp.draw(h_rc_kp_py,"same");

	c_mc_kp.cd(2);
	h_mc_kp_pz.setTitleX(" GEN p_z [GeV]");
 	c_mc_kp.draw(h_mc_kp_pz);
	h_rc_kp_pz.setLineColor(21);
 	c_mc_kp.draw(h_rc_kp_pz,"same");

	c_mc_km.setSize(1600,800);
	c_mc_km.divide(3,1);
	c_mc_km.cd(0);
	h_mc_km_px.setTitleX(" GEN p_x [GeV]");
 	c_mc_km.draw(h_mc_km_px);
	h_rc_km_px.setLineColor(21);
 	c_mc_km.draw(h_rc_km_px,"same");

	c_mc_km.cd(1);
	h_mc_km_py.setTitleX(" GEN p_y [GeV]");
 	c_mc_km.draw(h_mc_km_py);
	h_rc_km_py.setLineColor(21);
 	c_mc_km.draw(h_rc_km_py,"same");

	c_mc_km.cd(2);
	h_mc_km_pz.setTitleX(" GEN p_z [GeV]");
 	c_mc_km.draw(h_mc_km_pz);
	h_rc_km_pz.setLineColor(21);
 	c_mc_km.draw(h_rc_km_pz,"same");
			
	c_rc_el.setSize(2000,800);
	c_rc_el.divide(4,2);
 	c_rc_el.cd(0);
 	h_rc_el_px.setTitleX("REC p_x [GeV]");
	h_rc_el_px.setOptStat(1110);
 	c_rc_el.draw(h_rc_el_px);
 	c_rc_el.cd(1);
 	h_rc_el_py.setTitleX("REC p_y [GeV]");
	h_rc_el_py.setOptStat(1110);
 	c_rc_el.draw(h_rc_el_py);
 	c_rc_el.cd(2);
	h_rc_el_pz.setOptStat(1110);
 	h_rc_el_pz.setTitleX("REC p_z [GeV]");
 	c_rc_el.draw(h_rc_el_pz);
 	c_rc_el.cd(3);
 	h_rc_el_e.setTitleX("REC E [GeV]");
	F1D f = new F1D("func","[y0]",0.1, 8.0);
	f.setParameters(new double[]{1.0});
	f.setLineColor(4);
	c_rc_el.draw(f);
 	c_rc_el.draw(h_rc_el_e,"same");
	c_rc_el.cd(4);
 	h2_rc_el_ptheta.setTitleX("Momentum [GeV]");
	h2_rc_el_ptheta.setTitleY("#theta [deg]");
	c_rc_el.draw(h2_rc_el_ptheta,"colz");
	c_rc_el.cd(5);
	h2_rc_el_vzphi.setTitleX("Scattered Electron #phi [deg]");
	h2_rc_el_vzphi.setTitleY("Scattered Electron vertex position [cm]");
	c_rc_el.draw(h2_rc_el_vzphi,"colz");
	c_rc_el.cd(6);
	h2_rc_el_thetaphi.setTitleX("Scattered Electron #phi [deg]");
	h2_rc_el_thetaphi.setTitleY("Scattered Electron #theta [deg]");
	c_rc_el.draw(h2_rc_el_thetaphi,"colz");

	c_rc_el_2.setSize(800,800);
	c_rc_el_2.divide(2,2);
	c_rc_el_2.cd(0);
	h_rc_el_p.setOptStat(1110); 
	h_rc_el_p.setTitle("REC Electron Momentum");
	h_rc_el_p.setTitleX("p [GeV]");
	c_rc_el_2.draw(h_rc_el_p);
	c_rc_el_2.cd(1);
	h_rc_el_theta.setOptStat(1110); 
	h_rc_el_theta.setTitle("REC Electron #theta");
	h_rc_el_theta.setTitleX("#theta [deg]");
	c_rc_el_2.draw(h_rc_el_theta);
	c_rc_el_2.cd(2);
	h_rc_el_phi.setOptStat(1110); 
	h_rc_el_phi.setTitle("REC Electron #phi");
	h_rc_el_phi.setTitleX("#phi [deg]");
	c_rc_el_2.draw(h_rc_el_phi);

	c_rc_pr.setSize(2000,800);
	c_rc_pr.divide(4,2);
 	c_rc_pr.cd(0);
	h_rc_pr_px.setOptStat(1110);
 	h_rc_pr_px.setTitleX("REC p_x [GeV]");
 	c_rc_pr.draw(h_rc_pr_px);
 	c_rc_pr.cd(1);
 	h_rc_pr_py.setTitleX("REC p_y [GeV]");
	h_rc_pr_py.setOptStat(1110);
 	c_rc_pr.draw(h_rc_pr_py);
 	c_rc_pr.cd(2);
 	h_rc_pr_pz.setTitleX("REC p_z [GeV]");
	h_rc_pr_pz.setOptStat(1110);
 	c_rc_pr.draw(h_rc_pr_pz);
 	c_rc_pr.cd(3);
 	h_rc_pr_e.setTitleX("REC E [GeV]");
 	c_rc_pr.draw(h_rc_pr_e);
	c_rc_pr.cd(4);
 	h2_rc_pr_ptheta.setTitleX("Momentum [GeV]");
	h2_rc_pr_ptheta.setTitleY("#theta [deg]");
	//h2_rc_pr_ptheta.setOptStat(1110);
	c_rc_pr.draw(h2_rc_pr_ptheta,"colz");
	c_rc_pr.cd(5);
	h2_rc_pr_vzphi.setTitleX("Scattered Proton #phi [deg]");
	h2_rc_pr_vzphi.setTitleY("Scattered Proton vertex position [cm]");
	//h2_rc_pr_vzphi.setOptStat(1110);
	c_rc_pr.draw(h2_rc_pr_vzphi,"colz");
	c_rc_pr.cd(6);
	h2_rc_pr_thetaphi.setTitleX("Scattered Proton #phi [deg]");
	h2_rc_pr_thetaphi.setTitleY("Scattered Proton #theta [deg]");
	//h2_rc_pr_thetaphi.setOptStat(1110);
	c_rc_pr.draw(h2_rc_pr_thetaphi,"colz");


	c_rc_pr_2.setSize(800,800);
	c_rc_pr_2.divide(2,2);
	c_rc_pr_2.cd(0);
	h_rc_pr_p.setOptStat(1110); 
	h_rc_pr_p.setTitle("REC Proton Momentum");
	h_rc_pr_p.setTitleX("p [GeV]");
	c_rc_pr_2.draw(h_rc_pr_p);
	c_rc_pr_2.cd(1);
	h_rc_pr_theta.setOptStat(1110); 
	h_rc_pr_theta.setTitle("REC Proton #theta");
	h_rc_pr_theta.setTitleX("#theta [deg]");
	c_rc_pr_2.draw(h_rc_pr_theta);
	c_rc_pr_2.cd(2);
	h_rc_pr_phi.setOptStat(1110); 
	h_rc_pr_phi.setTitle("REC Proton #phi");
	h_rc_pr_phi.setTitleX("#phi [deg]");
	c_rc_pr_2.draw(h_rc_pr_phi);

	c_rc_kp.setSize(800,800);
	c_rc_kp.divide(2,2);
	c_rc_kp.cd(0);
	h_rc_kp_p.setTitle("REC Kaon^+ Momentum");
	h_rc_kp_p.setTitleX("p [GeV]");
	h_rc_kp_p.setTitleY("Entries");
	h_rc_kp_p.setOptStat(1110);
	c_rc_kp.draw(h_rc_kp_p);
	c_rc_kp.cd(1);
	h_rc_kp_theta.setTitle("Kaon^+ #theta");
	h_rc_kp_theta.setTitleX("#theta [deg]");
	h_rc_kp_theta.setTitleY("Entries");
	h_rc_kp_theta.setOptStat(1110);
	c_rc_kp.draw(h_rc_kp_theta);
	c_rc_kp.cd(2);
	h_rc_kp_phi.setTitle("Kaon^+ #phi");
	h_rc_kp_phi.setTitleX("#phi [deg]");
	h_rc_kp_phi.setTitleY("Entries");
	h_rc_kp_phi.setOptStat(1110);
	c_rc_kp.draw(h_rc_kp_phi);
	

	c_rc_km.setSize(2000,800);
	c_rc_km.divide(4,2);
 	c_rc_km.cd(0);
 	h_rc_km_px.setTitleX("REC p_x [GeV]");
	h_rc_km_px.setOptStat(1110);
 	c_rc_km.draw(h_rc_km_px);
 	c_rc_km.cd(1);
 	h_rc_km_py.setTitleX("REC p_y [GeV]");
	h_rc_km_py.setOptStat(1110);
  	c_rc_km.draw(h_rc_km_py);
 	c_rc_km.cd(2);
 	h_rc_km_pz.setTitleX("REC p_z [GeV]");
	h_rc_km_pz.setOptStat(1110);
 	c_rc_km.draw(h_rc_km_pz);
	c_rc_km.cd(3);
 	h_rc_km_e.setTitleX("REC E [GeV]");
	h_rc_km_e.setOptStat(1110);
 	c_rc_km.draw(h_rc_km_e);
	c_rc_km.cd(4);
 	h2_rc_km_ptheta.setTitleX("Momentum [GeV]");
	h2_rc_km_ptheta.setTitleY("#theta [deg]");
	c_rc_km.draw(h2_rc_km_ptheta,"colz");
	c_rc_km.cd(5);
	h2_rc_km_vzphi.setTitleX("Scattered Kaon Minus #phi [deg]");
	h2_rc_km_vzphi.setTitleY("Scattered Kaon Minus vertex position [cm]");
	c_rc_km.draw(h2_rc_km_vzphi,"colz");
	c_rc_km.cd(6);
	h2_rc_km_thetaphi.setTitleX("Scattered Kaon Minus #phi [deg]");
	h2_rc_km_thetaphi.setTitleY("Scattered Kaon Minus #theta [deg]");
	c_rc_km.draw(h2_rc_km_thetaphi,"colz");

	c_rec_el_kin.setSize(900,900);
	c_rec_el_kin.divide(2,2);
	c_rec_el_kin.cd(0);
	h_rc_el_q2.setOptStat(1110); 
	h_rc_el_q2.setTitle("Q^2 for scattered REC electron");
	h_rc_el_q2.setTitleX("Q^2 [GeV^2]");
	c_rec_el_kin.draw(h_rc_el_q2);
	c_rec_el_kin.cd(1);
	h_rc_el_xb.setOptStat(1110); 
	h_rc_el_xb.setTitle("Xb for scattered REC electron");
	h_rc_el_xb.setTitleX("Xb");
	c_rec_el_kin.draw(h_rc_el_xb);
	c_rec_el_kin.cd(2);
	h_rc_el_w.setOptStat(1110); 
	h_rc_el_w.setTitle("W for scattered REC electron");
	h_rc_el_w.setTitleX("W [GeV]");
	c_rec_el_kin.draw(h_rc_el_w);

	c_rec_el_res.setSize(900,900);
	c_rec_el_res.divide(2,2);
	c_rec_el_res.cd(0);
	h_rc_el_res_delp.setOptStat(1110); 
	h_rc_el_res_delp.setTitle("#Delta p of REC electron");
	h_rc_el_res_delp.setTitleX("#Delta p [GeV]");	
	c_rec_el_res.draw(h_rc_el_res_delp);
	F1D fit_rec_el_p = FitHistogram(h_rc_el_res_delp);
	fit_rec_el_p.setLineColor(2);
	c_rec_el_res.draw(fit_rec_el_p,"same");
	c_rec_el_res.cd(1);
	h_rc_el_res_p.setOptStat(1110); 
	h_rc_el_res_p.setTitle("#Delta p/p of REC electron");
	h_rc_el_res_p.setTitleX("#Delta p/p]");
	c_rec_el_res.draw(h_rc_el_res_p);
	F1D fit_rec_el_p2 = FitHistogram(h_rc_el_res_p);
	fit_rec_el_p2.setLineColor(2);
	c_rec_el_res.draw(fit_rec_el_p2,"same");
	c_rec_el_res.cd(2);
	h_rc_el_res_theta.setOptStat(1110); 
	h_rc_el_res_theta.setTitle("#Delta #theta of REC electron");
	h_rc_el_res_theta.setTitleX("#Delta #theta [deg]");
	c_rec_el_res.draw(h_rc_el_res_theta);
	F1D fit_rec_el_theta = FitHistogram(h_rc_el_res_theta);
	fit_rec_el_theta.setLineColor(2);
	c_rec_el_res.draw(fit_rec_el_theta,"same");
	c_rec_el_res.cd(3);
	h_rc_el_res_phi.setOptStat(1110); 
	h_rc_el_res_phi.setTitle("#Delta #phi of REC electron");
	h_rc_el_res_phi.setTitleX("#Delta #phi [deg]");
	c_rec_el_res.draw(h_rc_el_res_phi);
	F1D fit_rec_el_phi = FitHistogram(h_rc_el_res_phi);
	fit_rec_el_phi.setLineColor(2);
	c_rec_el_res.draw(fit_rec_el_phi,"same");


	c_rec_el_kinres.setSize(900,900);
	c_rec_el_kinres.divide(2,2);
	c_rec_el_kinres.cd(0);
	h_rc_el_res_q2.setOptStat(1110); 
	h_rc_el_res_q2.setTitle("#Delta Q^2 of REC electron");
	h_rc_el_res_q2.setTitleX("#Delta Q^2 [GeV^2]");
	c_rec_el_kinres.draw(h_rc_el_res_q2);
	F1D fit_rec_el_q2 = FitHistogram(h_rc_el_res_q2);
	fit_rec_el_q2.setLineColor(2);
	c_rec_el_kinres.draw(fit_rec_el_q2,"same");
	c_rec_el_kinres.cd(1);	
	h_rc_el_res_xb.setOptStat(1110); 
	h_rc_el_res_xb.setTitle("#Delta Xb of REC electron");
	h_rc_el_res_xb.setTitleX("#Delta Xb");
	c_rec_el_kinres.draw(h_rc_el_res_xb);
	F1D fit_rec_el_xb = FitHistogram(h_rc_el_res_xb);
	fit_rec_el_xb.setLineColor(2);
	c_rec_el_kinres.draw(fit_rec_el_xb,"same");
	c_rec_el_kinres.cd(2);
	h_rc_el_res_w.setOptStat(1110); 
	h_rc_el_res_w.setTitle("#Delta W of REC electron");
	h_rc_el_res_w.setTitleX("#Delta W [GeV]");
	c_rec_el_kinres.draw(h_rc_el_res_w);
	F1D fit_rec_el_w = FitHistogram(h_rc_el_res_w);
	fit_rec_el_w.setLineColor(2);


	c_rec_el_kinres.draw(fit_rec_el_w,"same");
	c2_rec_el_kin.setSize(800,800);
	c2_rec_el_kin.divide(2,2);
	c2_rec_el_kin.cd(0);
	//h2_rc_el_ptheta.setOptStat(1110); 
	h2_rc_el_ptheta.setTitle("Mntm vs #theta of REC electron ");
	h2_rc_el_ptheta.setTitleX("p [GeV]");
	h2_rc_el_ptheta.setTitleY("#theta [deg]");
 	c2_rec_el_kin.draw(h2_rc_el_ptheta, "colz");
	c2_rec_el_kin.cd(1);
	//	h2_rc_el_pphi.setOptStat(1110); 
	h2_rc_el_pphi.setTitle("Mntm vs #phi of REC electron ");
	h2_rc_el_pphi.setTitleX("p [GeV]");
	h2_rc_el_pphi.setTitleY("#phi [deg]");
 	c2_rec_el_kin.draw(h2_rc_el_pphi, "colz");
	c2_rec_el_kin.cd(2);
	//	h2_rc_el_thetaphi.setOptStat(1110); 
	h2_rc_el_thetaphi.setTitle("Mntm vs #theta of REC electron ");
	h2_rc_el_thetaphi.setTitleX("#theta [deg]");
	h2_rc_el_thetaphi.setTitleY("#phi [deg]");
 	c2_rec_el_kin.draw(h2_rc_el_thetaphi,"colz");

	c2_rec_el_inv.setSize(800,400);
	c2_rec_el_inv.divide(2,1);
	c2_rec_el_inv.cd(0);
	//	h2_rc_el_q2_x.setOptStat(1110); 
	h2_rc_el_q2_x.setTitle(" Q^2 vs Xb of REC electron");
	h2_rc_el_q2_x.setTitleX("Xb");
	h2_rc_el_q2_x.setTitleY("Q^2 [GeV^2]");
	c2_rec_el_inv.draw(h2_rc_el_q2_x,"colz");
	c2_rec_el_inv.cd(1);
	//	h2_rc_el_q2_w.setOptStat(1110); 
	h2_rc_el_q2_w.setTitle(" Q^2 vs W of REC electron");
	h2_rc_el_q2_w.setTitleX("W [GeV]");
	h2_rc_el_q2_w.setTitleY("Q^2 [GeV^2]");
	c2_rec_el_inv.draw(h2_rc_el_q2_w,"colz");

	c2_rec_el_res.setSize(900,900);
	c2_rec_el_res.divide(2,2);
	c2_rec_el_res.cd(0);
	//	h2_rc_el_p.setOptStat(1110); 
	h2_rc_el_p.setTitle("#Delta p vs p_mc of REC electron ");
	h2_rc_el_p.setTitleX("p_mc [GeV]");
	h2_rc_el_p.setTitleY("#Delta p [GeV]");
	c2_rec_el_res.draw(h2_rc_el_p, "colz");
	c2_rec_el_res.cd(1);
	//	h2_rc_el_fracp.setOptStat(1110); 
	h2_rc_el_fracp.setTitle("#Delta p /p vs p_mc of REC electron");
	h2_rc_el_fracp.setTitleX("p_mc [GeV]");
	h2_rc_el_fracp.setTitleY("#Delta p / p [GeV]");
	c2_rec_el_res.draw(h2_rc_el_fracp, "colz");
	c2_rec_el_res.cd(2);
	//	h2_rc_el_theta.setOptStat(1110); 
	h2_rc_el_theta.setTitle("#Delta #theta vs #theta_mc of REC electron");
	h2_rc_el_theta.setTitleX("#theta_mc [deg]");
	h2_rc_el_theta.setTitleY("#Delta #theta [deg]");
	c2_rec_el_res.draw(h2_rc_el_theta, "colz");
	c2_rec_el_res.cd(3);
	//	h2_rc_el_phi.setOptStat(1110); 
	h2_rc_el_phi.setTitle("#Delta #phi vs #phi_mc of REC electron ");
	h2_rc_el_phi.setTitleX("#phi_mc [deg]");
	h2_rc_el_phi.setTitleY("#Delta #phi [deg]");
	c2_rec_el_res.draw(h2_rc_el_phi, "colz");

	c2_rec_el_invres.setSize(800,800);
	c2_rec_el_invres.divide(2,2);
	c2_rec_el_invres.cd(0);
	//h2_rc_el_q2.setOptStat(1110); 
	h2_rc_el_q2.setTitle("#Delta Q^2 vs Q^2_mc of REC electron");
	h2_rc_el_q2.setTitleX(" Q^2 [GeV^2]");
	h2_rc_el_q2.setTitleY("#Delta Q^2 [GeV^2]");
	c2_rec_el_invres.draw(h2_rc_el_q2,"colz");
	c2_rec_el_invres.cd(1);
	//h2_rc_el_x.setOptStat(1110); 
	h2_rc_el_x.setTitle("#Delta Xb vs Xb_mc of REC electron");
	h2_rc_el_x.setTitleX(" Xb");
	h2_rc_el_x.setTitleY("#Delta Xb");
	c2_rec_el_invres.draw(h2_rc_el_x,"colz");
	c2_rec_el_invres.cd(2);
	//h2_rc_el_w.setOptStat(1110); 
	h2_rc_el_w.setTitle("#Delta W vs W_mc of REC electron");
	h2_rc_el_w.setTitleX(" W_mc [GeV]");
	h2_rc_el_w.setTitleY("#Delta W [GeV]");
	c2_rec_el_invres.draw(h2_rc_el_w,"colz");


	//////////////////////////////////////////////////////////////////////////
	///PROTONS
	c_rec_pr_kin.setSize(800,800);
	c_rec_pr_kin.divide(1,1);
	c_rec_pr_kin.cd(0);
	h_rc_pr_t.setOptStat(1110); 
	h_rc_pr_t.setTitle(" -t REC proton");
	h_rc_pr_t.setTitleX(" -t [GeV^2]" );
	c_rec_pr_kin.draw(h_rc_pr_t);

	c_rec_pr_res.setSize(900,900);
	c_rec_pr_res.divide(2,2);
	c_rec_pr_res.cd(0);
	h_rc_pr_res_delp.setOptStat(1110); 
	h_rc_pr_res_delp.setTitle("#Delta p of REC proton");
	h_rc_pr_res_delp.setTitleX("#Delta p [GeV]");
	c_rec_pr_res.draw(h_rc_pr_res_delp);
	F1D fit_rec_pr_p = FitHistogram(h_rc_pr_res_delp);
	fit_rec_pr_p.setLineColor(2);
	c_rec_pr_res.draw(fit_rec_pr_p,"same");

	c_rec_pr_res.cd(1);
	h_rc_pr_res_p.setOptStat(1110); 
	h_rc_pr_res_p.setTitle("#Delta p/p of REC proton");
	h_rc_pr_res_p.setTitleX("#Delta p/p]");
	c_rec_pr_res.draw(h_rc_pr_res_p);
	F1D fit_rec_pr_p2 = FitHistogram(h_rc_pr_res_p);
	fit_rec_pr_p2.setLineColor(2);
	c_rec_pr_res.draw(fit_rec_pr_p2,"same");

	c_rec_pr_res.cd(2);
	h_rc_pr_res_theta.setOptStat(1110); 
	h_rc_pr_res_theta.setTitle("#Delta #theta of REC proton");
	h_rc_pr_res_theta.setTitleX("#Delta #theta [deg]");
	c_rec_pr_res.draw(h_rc_pr_res_theta);
	F1D fit_rec_pr_theta = FitHistogram(h_rc_pr_res_theta);
	fit_rec_pr_theta.setLineColor(2);
	c_rec_pr_res.draw(fit_rec_pr_theta,"same");

	c_rec_pr_res.cd(3);
	h_rc_pr_res_phi.setOptStat(1110); 
	h_rc_pr_res_phi.setTitle("#Delta #phi of REC proton");
	h_rc_pr_res_phi.setTitleX("#Delta #phi [deg]");
	c_rec_pr_res.draw(h_rc_pr_res_phi);
	F1D fit_rec_pr_phi = FitHistogram(h_rc_pr_res_phi);
	fit_rec_pr_phi.setLineColor(2);
	c_rec_pr_res.draw(fit_rec_pr_phi,"same");

	c_rec_pr_kinres.setSize(800,400);
	c_rec_pr_kinres.divide(2,1);
	c_rec_pr_kinres.cd(0);
	h_rc_pr_res_t.setOptStat(1110); 
	h_rc_pr_res_t.setTitle("#Delta -t of REC proton");
	h_rc_pr_res_t.setTitleX("#Delta -t [GeV^2]");
	c_rec_pr_kinres.draw(h_rc_pr_res_t);
	c_rec_pr_kinres.cd(1);	
	h_rc_pr_res_phicm.setOptStat(1110); 
	h_rc_pr_res_phicm.setTitle("#Delta #phi CM of REC proton");
	h_rc_pr_res_phicm.setTitleX("#Delta #phi CM");
	c_rec_pr_kinres.draw(h_rc_pr_res_phicm);

	c2_rec_pr_kin.setSize(800,800);
	c2_rec_pr_kin.divide(2,2);
	c2_rec_pr_kin.cd(0);
	//h2_rc_pr_ptheta.setOptStat(1110); 
	h2_rc_pr_ptheta.setTitle("Mntm vs #theta of REC proton");
	h2_rc_pr_ptheta.setTitleX("p [GeV]");
	h2_rc_pr_ptheta.setTitleY("#theta [deg]");
 	c2_rec_pr_kin.draw(h2_rc_pr_ptheta, "colz");
	c2_rec_pr_kin.cd(1);
	//h2_rc_pr_pphi.setOptStat(1110); 
	h2_rc_pr_pphi.setTitle("Mntm vs #phi of REC proton");
	h2_rc_pr_pphi.setTitleX("p [GeV]");
	h2_rc_pr_pphi.setTitleY("#phi [deg]");
 	c2_rec_pr_kin.draw(h2_rc_pr_pphi, "colz");
	c2_rec_pr_kin.cd(2);
	//h2_rc_pr_thetaphi.setOptStat(1110); 
	h2_rc_pr_thetaphi.setTitle("Mntm vs #theta of REC proton");
	h2_rc_pr_thetaphi.setTitleX("#theta [deg]");
	h2_rc_pr_thetaphi.setTitleY("#phi [deg]");
 	c2_rec_pr_kin.draw(h2_rc_pr_thetaphi,"colz");

	c2_rec_pr_res.setSize(900,900);
	c2_rec_pr_res.divide(2,2);
	c2_rec_pr_res.cd(0);
	//h2_rc_pr_p.setOptStat(1110); 
	h2_rc_pr_p.setTitle("#delta p vs p_mc of REC proton ");
	h2_rc_pr_p.setTitleX("p_mc [GeV]");
	h2_rc_pr_p.setTitleY("#Delta p [GeV]");
	c2_rec_pr_res.draw(h2_rc_pr_p, "colz");
	c2_rec_pr_res.cd(1);
	//h2_rc_pr_fracp.setOptStat(1110); 
	h2_rc_pr_fracp.setTitle("#Delta p /p vs p_mc of REC proton");
	h2_rc_pr_fracp.setTitleX("p_mc [GeV]");
	h2_rc_pr_fracp.setTitleY("#Delta p / p [GeV]");
	c2_rec_pr_res.draw(h2_rc_pr_fracp, "colz");
	c2_rec_pr_res.cd(2);
	//h2_rc_pr_theta.setOptStat(1110); 
	h2_rc_pr_theta.setTitle("#Delta #theta vs #theta_mc of REC proton");
	h2_rc_pr_theta.setTitleX("#theta_mc [deg]");
	h2_rc_pr_theta.setTitleY("#Delta #theta [deg]");
	c2_rec_pr_res.draw(h2_rc_pr_theta, "colz");
	c2_rec_pr_res.cd(3);
	//h2_rc_pr_phi.setOptStat(1110); 
	h2_rc_pr_phi.setTitle("#Delta #phi vs #phi_mc of REC proton");
	h2_rc_pr_phi.setTitleX("#phi_mc [deg]");
	h2_rc_pr_phi.setTitleY("#Delta #phi [deg]");
	c2_rec_pr_res.draw(h2_rc_pr_phi, "colz");

	c2_rec_pr_invres.setSize(800,800);
	c2_rec_pr_invres.divide(1,1);
	c2_rec_pr_invres.cd(0);
	//h2_rc_pr_t.setOptStat(1110); 
	h2_rc_pr_t.setTitle("#Delta -t vs -t_mc of REC proton");
	h2_rc_pr_t.setTitleX(" -t [GeV^2]");
	h2_rc_pr_t.setTitleY("#Delta -t [GeV^2]");
	c2_rec_pr_invres.draw(h2_rc_pr_t,"colz");
 

	c_rec_kp_res.setSize(800,800);
	c_rec_kp_res.divide(2,2);
	c_rec_kp_res.cd(0);
	h_rc_kp_res_delp.setTitle("REC Kaon^+ #Delta p");
	h_rc_kp_res_delp.setTitleX("#Delta p [GeV]");
	h_rc_kp_res_delp.setTitleY("Entries");
	c_rec_kp_res.draw(h_rc_kp_res_delp);
	c_rec_kp_res.cd(1);
	h_rc_kp_res_p.setTitle("REC Kaon^+ #Delta p / p");
	h_rc_kp_res_p.setTitleX("#Delta p /p");
	h_rc_kp_res_p.setTitleY("Entries");
	c_rec_kp_res.draw(h_rc_kp_res_p);
	c_rec_kp_res.cd(2);
	h_rc_kp_res_theta.setTitle("REC Kaon^+ #Delta #theta");
	h_rc_kp_res_theta.setTitleX("#Delta #theta");
	h_rc_kp_res_theta.setTitleY("Entries");
	c_rec_kp_res.draw(h_rc_kp_res_theta);
	c_rec_kp_res.cd(3);
	h_rc_kp_res_phi.setTitle("REC Kaon^+ #Delta phi");
	h_rc_kp_res_phi.setTitleX("#Delta #phi");
	h_rc_kp_res_phi.setTitleY("Entries");
	c_rec_kp_res.draw(h_rc_kp_res_phi);

	c2_rec_kp_res.setSize(800,800);
	c2_rec_kp_res.divide(2,2);
	c2_rec_kp_res.cd(0);
	h2_rc_kp_p.setTitle("REC Kaon^+ #Delta p vs p");
	h2_rc_kp_p.setTitleX("p_{MC} [GeV]");
	h2_rc_kp_p.setTitleY("#Delta p [GeV]");
	c2_rec_kp_res.draw(h2_rc_kp_p,"colz");
	c2_rec_kp_res.cd(1);
	h2_rc_kp_fracp.setTitle("REC Kaon^+ #Delta p/p vs p");
	h2_rc_kp_fracp.setTitleX("p_{MC} [GeV]");
	h2_rc_kp_fracp.setTitleY("#Delta p /p");
	c2_rec_kp_res.draw(h2_rc_kp_fracp,"colz");
	c2_rec_kp_res.cd(2);
	h2_rc_kp_theta.setTitle("REC Kaon^+ #Delta #theta vs #theta");
	h2_rc_kp_theta.setTitleX("#theta_{MC} [deg]");
	h2_rc_kp_theta.setTitleY("#Delta #theta [deg]");
	c2_rec_kp_res.draw(h2_rc_kp_theta,"colz");
	c2_rec_kp_res.cd(3);
	h2_rc_kp_phi.setTitle("REC Kaon^+ #Delta #phi vs #phi");
	h2_rc_kp_phi.setTitleX("#phi_{MC} [deg]");
	h2_rc_kp_phi.setTitleY("#Delta #phi [deg]");
	c2_rec_kp_res.draw(h2_rc_kp_phi,"colz");

	
	ParallelSliceFitter fit_el_p = new ParallelSliceFitter(h2_rc_el_p);
	ParallelSliceFitter fit_el_theta = new ParallelSliceFitter(h2_rc_el_theta);
	ParallelSliceFitter fit_el_phi = new ParallelSliceFitter(h2_rc_el_phi);
	fit_el_p.fitSlicesX(10);
	fit_el_theta.fitSlicesX(10);
	fit_el_phi.fitSlicesX(10);

	//TOO LAZY TO CHANGE MEANS TO SIGMAS... :/
	GraphErrors meanY_el_p = fit_el_p.getSigmaSlices();
	GraphErrors meanY_el_theta = fit_el_theta.getSigmaSlices();
	GraphErrors meanY_el_phi = fit_el_phi.getSigmaSlices();

	c_err_el.divide(2,2);
	c_err_el.cd(0);
	meanY_el_p.setTitle("Deviations in RC Electron Momentum");
	meanY_el_p.setTitleX("p [GeV]");
	meanY_el_p.setTitleY("#sigma momentum [GeV]");
	meanY_el_p.setMarkerSize(2);
	meanY_el_p.setMarkerStyle(0);
	meanY_el_p.setMarkerColor(2);
	c_err_el.draw(meanY_el_p);
	c_err_el.cd(1);
	meanY_el_theta.setTitle("Deviations in RC Electron #theta");
	meanY_el_theta.setTitleX(" #theta [deg] ");
	meanY_el_theta.setTitleY(" #sigma #theta [deg] ");
	meanY_el_theta.setMarkerSize(2);
	meanY_el_theta.setMarkerStyle(0);
	meanY_el_theta.setMarkerColor(2);
	c_err_el.draw(meanY_el_theta);
	c_err_el.cd(2);
	meanY_el_phi.setTitle("Deviations in RC Electron #phi");
	meanY_el_phi.setTitleX(" #phi [deg]");
	meanY_el_phi.setTitleY(" #sigma #phi [deg] ");
	meanY_el_phi.setMarkerSize(2);
	meanY_el_phi.setMarkerStyle(0);
	meanY_el_phi.setMarkerColor(2);
	c_err_el.draw(meanY_el_phi);

	ParallelSliceFitter fit_pr_p = new ParallelSliceFitter(h2_rc_pr_p);
	ParallelSliceFitter fit_pr_theta = new ParallelSliceFitter(h2_rc_pr_theta);
	ParallelSliceFitter fit_pr_phi = new ParallelSliceFitter(h2_rc_pr_phi);
	fit_pr_p.fitSlicesX(10);
	fit_pr_theta.fitSlicesX(10);
	fit_pr_phi.fitSlicesX(10);

	GraphErrors meanY_pr_p = fit_pr_p.getSigmaSlices();
	GraphErrors meanY_pr_theta = fit_pr_theta.getSigmaSlices();
	GraphErrors meanY_pr_phi = fit_pr_phi.getSigmaSlices();

	c_err_pr.divide(2,2);
	c_err_pr.cd(0);
	meanY_pr_p.setTitle("Deviations in RC Proton Momentum");
	meanY_pr_p.setTitleX(" p [GeV] ");
	meanY_pr_p.setTitleY(" #sigma momentum [GeV] ");
	meanY_pr_p.setMarkerSize(2);
	meanY_pr_p.setMarkerStyle(0);
	meanY_pr_p.setMarkerColor(3);
	c_err_pr.draw(meanY_pr_p);
	c_err_pr.cd(1);
	meanY_pr_theta.setTitle("Deviations in RC Proton #phi");
	meanY_pr_theta.setTitleX(" #theta [deg] ");
	meanY_pr_theta.setTitleY(" #sigma #theta [deg] ");
	meanY_pr_theta.setMarkerSize(2);
	meanY_pr_theta.setMarkerStyle(0);
	meanY_pr_theta.setMarkerColor(3);
	c_err_pr.draw(meanY_pr_theta);
	c_err_pr.cd(2);
	meanY_pr_phi.setTitle("Deviations in RC Proton #theta");
	meanY_pr_phi.setTitleX(" #phi [deg] ");
	meanY_pr_phi.setTitleY(" #sigma #phi [deg] ");
	meanY_pr_phi.setMarkerSize(2);
	meanY_pr_phi.setMarkerStyle(0);
	meanY_pr_phi.setMarkerColor(3);
	c_err_pr.draw(meanY_pr_phi);


	ParallelSliceFitter fit_kp_p = new ParallelSliceFitter(h2_rc_kp_p);
	ParallelSliceFitter fit_kp_theta = new ParallelSliceFitter(h2_rc_kp_theta);
	ParallelSliceFitter fit_kp_phi = new ParallelSliceFitter(h2_rc_kp_phi);
	fit_kp_p.fitSlicesX(8);
	fit_kp_theta.fitSlicesX(8);
	fit_kp_phi.fitSlicesX(8);

	GraphErrors sigY_kp_p = fit_kp_p.getSigmaSlices();
	GraphErrors sigY_kp_theta = fit_kp_theta.getSigmaSlices();
	GraphErrors sigY_kp_phi = fit_kp_phi.getSigmaSlices();

	c_err_kp.setSize(800,800);
	c_err_kp.divide(2,2);
	c_err_kp.cd(0);
	sigY_kp_p.setTitle("Deviations in RC K^+ Momentum");
	sigY_kp_p.setTitleX("p [GeV]");
	sigY_kp_p.setTitleY("#sigma p [GeV]");
	sigY_kp_p.setMarkerStyle(0);
	sigY_kp_p.setMarkerSize(2);
	sigY_kp_p.setMarkerColor(2);
	c_err_kp.draw(sigY_kp_p);
	c_err_kp.cd(1);
	sigY_kp_theta.setTitle("Deviations in RC K^+ #theta");
	sigY_kp_theta.setTitleX("#theta [deg]");
	sigY_kp_theta.setTitleY("#sigma p [deg]");
	sigY_kp_theta.setMarkerStyle(0);
	sigY_kp_theta.setMarkerSize(2);
	sigY_kp_theta.setMarkerColor(2);
	c_err_kp.draw(sigY_kp_theta);
	c_err_kp.cd(2);
	sigY_kp_phi.setTitle("Deviations in RC K^+ #phi");
	sigY_kp_phi.setTitleX("#phi [deg]");
	sigY_kp_phi.setTitleY("#sigma #phi [deg]");
	sigY_kp_phi.setMarkerStyle(0);
	sigY_kp_phi.setMarkerSize(2);
	sigY_kp_phi.setMarkerColor(2);
	c_err_kp.draw(sigY_kp_phi);


	ParallelSliceFitter fit_el_q2 = new ParallelSliceFitter(h2_rc_el_q2);
	ParallelSliceFitter fit_el_xb = new ParallelSliceFitter(h2_rc_el_x);
	ParallelSliceFitter fit_el_w = new ParallelSliceFitter(h2_rc_el_w);
	fit_el_q2.fitSlicesX(10);
	fit_el_xb.fitSlicesX(10);
	fit_el_w.fitSlicesX(10);

	GraphErrors sigY_el_q2 = fit_el_q2.getSigmaSlices();
	GraphErrors sigY_el_xb = fit_el_xb.getSigmaSlices();
	GraphErrors sigY_el_w = fit_el_w.getSigmaSlices();
	c_err_kin.setSize(800,800);
	c_err_kin.divide(2,2);
	c_err_kin.cd(0);
	sigY_el_q2.setTitle("Deviations in #Delta Q^2");
	sigY_el_q2.setTitleX("Q^2 [GeV^2]");
	sigY_el_q2.setTitleY("#sigma Q^2 [GeV^2]");
	sigY_el_q2.setMarkerSize(2);
	sigY_el_q2.setMarkerStyle(0);
	sigY_el_q2.setMarkerColor(2);
	c_err_kin.draw(sigY_el_q2);
	c_err_kin.cd(1);
	sigY_el_xb.setTitle("Deviations in #Delta Xb");
	sigY_el_xb.setTitleX("Xb");
	sigY_el_xb.setTitleY("#sigma Xb");
	sigY_el_xb.setMarkerSize(2);
	sigY_el_xb.setMarkerStyle(0);
	sigY_el_xb.setMarkerColor(2);
	c_err_kin.draw(sigY_el_xb);
	c_err_kin.cd(2);
	sigY_el_w.setTitle("Deviations in #Delta W");
	sigY_el_w.setTitleX("W [GeV]");
	sigY_el_w.setTitleY("#sigma W [GeV]");
	sigY_el_w.setMarkerSize(2);
	sigY_el_w.setMarkerStyle(0);
	sigY_el_w.setMarkerColor(2);
	c_err_kin.draw(sigY_el_w);

	c_comp_el_kin.setSize(800,800);
	c_comp_el_kin.divide(2,2);
	c_comp_el_kin.cd(0);
	h_rc_el_p.setTitleX("p [Gev]");
	h_rc_el_p.setTitle("MC vs REC electron momentum");
	h_rc_el_p.setLineColor(2);
	h_mc_el_p.setLineColor(30);
	h_mc_el_p.setOptStat(0000);
	h_rc_el_p.setOptStat(0000);
	c_comp_el_kin.draw(h_rc_el_p);
	c_comp_el_kin.draw(h_mc_el_p,"same");
	c_comp_el_kin.getPad(0).getAxisY().setLog(true);
	c_comp_el_kin.cd(1);
	h_rc_el_theta.setTitleX("#theta [deg]");
	h_rc_el_theta.setTitle("MC vs REC electron #theta");
	h_rc_el_theta.setLineColor(2);
	h_mc_el_theta.setLineColor(30);
	h_mc_el_theta.setOptStat(0000);
	h_rc_el_theta.setOptStat(0000);
	c_comp_el_kin.draw(h_rc_el_theta);
	c_comp_el_kin.draw(h_mc_el_theta,"same");
	c_comp_el_kin.getPad(1).getAxisY().setLog(true);
	c_comp_el_kin.cd(2);
	h_rc_el_phi.setTitleX("#phi [deg]");
	h_rc_el_phi.setTitle("MC vs REC electron #phi");
	h_rc_el_phi.setLineColor(2);
	h_mc_el_phi.setLineColor(30);
	h_mc_el_phi.setOptStat(0000);
	h_rc_el_phi.setOptStat(0000);
	c_comp_el_kin.draw(h_rc_el_phi);
	c_comp_el_kin.draw(h_mc_el_phi,"same");
	c_comp_el_kin.getPad(2).getAxisY().setLog(true);

	c_comp_el_inv.setSize(800,800);
	c_comp_el_inv.divide(2,2);
	c_comp_el_inv.cd(0);
	h_rc_el_q2.setTitle("MC vs REC Q^2");
	h_rc_el_q2.setTitleX("Q^2 [GeV^2]");
	h_rc_el_q2.setLineColor(2);
	h_rc_el_q2.setOptStat(0000);
	h_mc_el_q2.setOptStat(0000);
	c_comp_el_inv.draw(h_rc_el_q2);
	c_comp_el_inv.draw(h_mc_el_q2,"same");
	c_comp_el_inv.getPad(0).getAxisY().setLog(true);
	c_comp_el_inv.cd(1);
	h_rc_el_w.setTitle("MC vs REC W");
	h_mc_el_w.setTitleX("W [GeV]");
	h_rc_el_w.setLineColor(2);
	h_mc_el_w.setOptStat(0000);
	h_rc_el_w.setOptStat(0000);
	c_comp_el_inv.draw(h_rc_el_w);
	c_comp_el_inv.draw(h_mc_el_w,"same");
	c_comp_el_inv.getPad(1).getAxisY().setLog(true);
	c_comp_el_inv.cd(2);
	h_rc_el_xb.setTitle("MC vs REC Xb");
	h_mc_el_xb.setTitleX("Xb");
	h_rc_el_xb.setLineColor(2);
	h_mc_el_xb.setOptStat(0000);
	h_rc_el_xb.setOptStat(0000);
	c_comp_el_inv.draw(h_rc_el_xb);
	c_comp_el_inv.draw(h_mc_el_xb,"same");
	c_comp_el_inv.getPad(2).getAxisY().setLog(true);

	//////////////////////////////////////////////////////////
	// COMPARE PROTON MC TO REC
	c_comp_pr_kin.setSize(1200,400);
	c_comp_pr_kin.divide(3,1);
	c_comp_pr_kin.cd(0);
	h_rc_pr_p.setTitle("MC vs REC Proton Momentum");
	h_rc_pr_p.setTitleX("p [GeV]");
	h_rc_pr_p.setLineColor(2);
	h_rc_pr_p.setOptStat(0000);
	h_mc_pr_p.setOptStat(0000);
	c_comp_pr_kin.draw(h_rc_pr_p);
	c_comp_pr_kin.draw(h_mc_pr_p,"same");
	c_comp_pr_kin.getPad(0).getAxisY().setLog(true);
	c_comp_pr_kin.cd(1);
	h_rc_pr_theta.setTitle("MC vs REC Proton #theta");
	h_rc_pr_theta.setTitleX("#theta [deg]");
	h_rc_pr_theta.setLineColor(2);
	h_rc_pr_theta.setOptStat(0000);
	h_mc_pr_theta.setOptStat(0000);
	c_comp_pr_kin.draw(h_rc_pr_theta);
	c_comp_pr_kin.draw(h_mc_pr_theta,"same");
	c_comp_pr_kin.getPad(1).getAxisY().setLog(true);
	c_comp_pr_kin.cd(2);
	h_rc_pr_phi.setTitle("MC vs REC Proton #phi");
	h_rc_pr_phi.setTitleX("#phi [GeV]");
	h_rc_pr_phi.setLineColor(2);
	h_rc_pr_phi.setOptStat(0000);
	h_mc_pr_phi.setOptStat(0000);
	c_comp_pr_kin.draw(h_rc_pr_phi);
	c_comp_pr_kin.draw(h_mc_pr_phi,"same");
	c_comp_pr_kin.getPad(2).getAxisY().setLog(true);
	

		
	/*       	  
	//frame_err_el.add(c_err_el);
	//frame_err_el.setVisible(false);

	frame_err_pr.add(c_err_pr);
	frame_err_pr.setVisible(false);
	
	frame_err_kin.add(c_err_kin);
	frame_err_kin.setVisible(false);

	frame_comp_kin.add(c_comp_el_inv);
	frame_comp_kin.setVisible(false);
	*/
    }



    public void SaveHistograms() {

	c_mc_el.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_mc_el_mntm.png");
	c_mc_pr.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_mc_pr_mntm.png");
	c_mc_kp.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_mc_kp_mntm.png");
	c_mc_km.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_mc_km_mntm.png");

	c_rc_el.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_rc_el_mntm.png");
	c_rc_pr.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_rc_pr_mntm.png");
	//c_rc_kp.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_rc_kp_mntm.png");
	c_rc_km.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_rc_km_mntm.png");

	c_rc_el_2.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_rc_el_kinvar.png");
	c_rec_el_kin.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_rc_el_invkin.png");
	c_rec_el_res.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_rc_el_reskin.png");
	c_rec_el_kinres.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_rc_el_resinv.png");

	c2_rec_el_kin.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h2_rc_el_kin.png");
	c2_rec_el_inv.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h2_rc_el_inv.png");
	c2_rec_el_res.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h2_rc_el_res.png");
	c2_rec_el_invres.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h2_rc_el_invres.png");

	c_rc_pr_2.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_rc_pr_kinvar.png");
	c_rec_pr_kin.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_rc_pr_invkin.png");
	c_rec_pr_res.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_rc_pr_reskin.png");
	c_rec_pr_kinres.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_rc_pr_resinv.png");

	c2_rec_pr_kin.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h2_rc_pr_kin.png");
	c2_rec_pr_inv.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h2_rc_pr_inv.png");
	c2_rec_pr_res.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h2_rc_pr_res.png");
	c2_rec_pr_invres.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h2_rc_pr_invres.png");


	c_rc_kp.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_rc_kp_kinvar.png");  
	c_rec_kp_res.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_rc_kp_reskin.png");
	c2_rec_kp_res.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h2_rc_kp_reskin.png");

	c_err_el.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/g_err_rc_el_kin.png");
	c_err_pr.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/g_err_rc_pr_kin.png");
	c_err_kp.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/g_err_rc_kp_kin.png"); 

	c_comp_el_kin.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_comp_el_kin.png"); 
	c_comp_el_inv.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_comp_el_inv.png"); 

	c_comp_pr_kin.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_comp_pr_kin.png"); 

    }




}
