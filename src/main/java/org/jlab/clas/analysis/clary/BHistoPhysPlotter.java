package org.jlab.clas.analysis.clary;

import java.io.*;
import javax.swing.JFrame;
import java.util.Collections; 

import org.jlab.clas.analysis.clary.BEvent;
import org.jlab.clas.analysis.clary.PhysicsEvent;

import org.jlab.analysis.plotting.H1FCollection2D;
import org.jlab.analysis.plotting.H1FCollection3D;
import org.jlab.analysis.plotting.TCanvasP;
import org.jlab.analysis.plotting.TCanvasPTabbed;
import org.jlab.groot.graphics.EmbeddedCanvas;

import org.jlab.groot.group.*;
import org.jlab.groot.ui.TBrowser;
import org.jlab.groot.tree.*;
import org.jlab.groot.data.TDirectory;
import org.jlab.jnp.hipo.io.*;

import org.jlab.analysis.math.ClasMath;
import org.jlab.clas.physics.Vector3;
import org.jlab.clas.physics.LorentzVector;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.fitter.*;
import org.jlab.groot.graphics.GraphicsAxis;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;


public class BHistoPhysPlotter {

    private int run_number = -1;
    private String s_run_number = " ";
    public BHistoPhysPlotter( int temp_run ){
	run_number = temp_run;
	s_run_number = Integer.toString(run_number);

    }

    TDirectory dir = new TDirectory();

    EmbeddedCanvas c_phy_q2xt = new EmbeddedCanvas();
    EmbeddedCanvas c2_phy_q2xt = new EmbeddedCanvas();
    EmbeddedCanvas c3_phy_q2xt = new EmbeddedCanvas();
    EmbeddedCanvas c4_phy_mm2 = new EmbeddedCanvas();
    EmbeddedCanvas c_comp_phys = new EmbeddedCanvas();

    EmbeddedCanvas c_comp_kp = new EmbeddedCanvas();
    EmbeddedCanvas c_comp_km = new EmbeddedCanvas();

    EmbeddedCanvas c_mcphy_q2xt = new EmbeddedCanvas();
    EmbeddedCanvas c2_mcphy_q2xt = new EmbeddedCanvas();
    EmbeddedCanvas c3_mcphy_q2xt = new EmbeddedCanvas();
    EmbeddedCanvas c4_mcphy_mm2 = new EmbeddedCanvas();

    EmbeddedCanvas c_phy_res_inv = new EmbeddedCanvas(); 
    EmbeddedCanvas c_phy_mm_phi = new EmbeddedCanvas(); 
    EmbeddedCanvas c2_phy_res_inv = new EmbeddedCanvas();
    EmbeddedCanvas c2_phy_tcmphi = new EmbeddedCanvas();

    EmbeddedCanvas c1_phy_calc_km_kin = new EmbeddedCanvas();
    EmbeddedCanvas c1_phy_calc_km_res = new EmbeddedCanvas();
    EmbeddedCanvas c2_phy_calc_km_kin = new EmbeddedCanvas();
    EmbeddedCanvas c2_phy_calc_km_res = new EmbeddedCanvas();
    
    EmbeddedCanvas c_err_calc_kp = new EmbeddedCanvas();
    
    H1F h_phy_q2; 
    H1F h_phy_xb;
    H1F h_phy_t; 
    H1F h_phy_w;
    H1F h_phy_cm_phi;
    H1F h_phy_cm_theta;
    H1F h_phy_mm2epkp;
    H1F h_phy_mm2ekp; 
    H1F h_phy_mm2epkm;
    H1F h_phy_mm2ekpkm;
    H1F h_phy_mm2ep;
    H2F h_phy_q2x;
    H2F h_phy_q2t;

    H1F h_mcphy_q2; 
    H1F h_mcphy_xb;
    H1F h_mcphy_t; 
    H1F h_mcphy_w;
    H1F h_mcphy_cm_phi;
    H1F h_mcphy_cm_theta;
    H1F h_mcphy_mm2epkp;
    H1F h_mcphy_mm2ekp; 
    H1F h_mcphy_mm2epkm;
    H1F h_mcphy_mm2ekpkm;
    H1F h_mcphy_mm2ep;
    H2F h_mcphy_q2x;
    H2F h_mcphy_q2t;

    //////////////////////////////////////////////////////////////
    //ADDED FOR JOO PROJECT
    H1F h_phy_delt;
    H1F h_phy_delcmphi;
    H1F h_phy_delw;
    H1F h_phy_mm_epkX;
    H1F h_phy_mm_phi;
    H1F h_phy_mmepX;

    H2F h2_phy_res_t;
    H2F h2_phy_res_phicm;
    H2F h2_phy_res_w;
    
    H2F h2_phy_mm_kmphi;
    H2F h2_phy_tcmphi;

    //////////////////////////////////////////////////////////////
    //ADDED FOR CALCULATING KM
    H1F h_phy_calc_km_p;
    H1F h_phy_calc_km_theta;
    H1F h_phy_calc_km_phi;
    H1F h_phy_calc_km_delp;
    H1F h_phy_calc_km_deltheta;
    H1F h_phy_calc_km_delphi;
    H1F h_phy_calc_km_fracp;

    H2F h2_phy_calc_km_ptheta;
    H2F h2_phy_calc_km_pphi;
    H2F h2_phy_calc_km_phitheta;

    H2F h2_phy_calc_km_p;
    H2F h2_phy_calc_km_fracp;
    H2F h2_phy_calc_km_theta;
    H2F h2_phy_calc_km_phi;

    ///////////////////////////////////////////////////////////////
    //ADDED FOR COMPARING CALC KP TO MC KP

    H1F h_phy_kp_p, h_phy_kp_theta, h_phy_kp_phi;

    H1F h_mcphy_kp_p;
    H1F h_mcphy_kp_theta;
    H1F h_mcphy_kp_phi;

    H1F h_mcphy_km_p;
    H1F h_mcphy_km_theta;
    H1F h_mcphy_km_phi;

    double joo_angle = 150.0;
    double minphi = -60.0;
    double maxphi = 360.0;

    public void CreateHistograms(){
	/*frame1.setSize(1600,800);
	frame2.setSize(1600,800);
	frame3.setSize(1600,800);
	frame4.setSize(1600,800);

	frame_err_calc_kp.setSize(800,800);
	
	f_comp_phy.setSize(800,800);
	f_comp_kp.setSize(800,800);
	f_comp_km.setSize(800,800);
	*/
	h_phy_q2 = new H1F("h_phy_q2","h_phy_q2", 100, 0.0, 10.5 );
 	h_phy_xb = new H1F("h_phy_xb","h_phy_xb", 100, 0.0, 1.2 );
	h_phy_t = new H1F("h_phy_t","h_phy_t", 100, 0.0, 5.5 );
	h_phy_w = new H1F("h_phy_w","h_phy_w", 100, 0.0, 6.0 );

	h_phy_cm_phi = new H1F("h_phy_cm_phi","h_phy_cm_phi",100,-180.0, 180.0);
	h_phy_cm_theta = new H1F("h_phy_cm_theta","h_phy_cm_theta",50,-1.0, 1.0);

	h_phy_mm2epkp = new H1F("h_phy_mm2epkp","h_phy_mm2epkp",100,0.2, 0.8 );
	h_phy_mm2ekp = new H1F("h_phy_mm2ekp","h_phy_mm2ekp",100,0.2, 0.8 );
        h_phy_mm2epkm = new H1F("h_phy_mm2epkm","h_phy_mm2epkm",100,0.2, 0.8);
	h_phy_mm2ekpkm= new H1F("h_phy_mm2ekpkm","h_phy_mm2ekpkm",100,0.6, 1.1);
	h_phy_mm2ep = new H1F("h_phy_mm2ep","h_phy_mm2ep",100,-0.5, 1.3);
	
	h_phy_q2x = new H2F("h_phy_q2x","h_phy_q2x",80,0.05, 0.80, 80, 0.0, 9.0);
	h_phy_q2t = new H2F("h_phy_q2t","h_phy_q2t",80,0, 3.5, 80, 0.0, 9.0);

	h_phy_delt = new H1F("h_phy_delt","h_phy_delt",100, -0.3, 0.3);
	h_phy_delcmphi = new H1F("h_phy_delcmphi","h_phy_delcmphi", 100, -100.0, 100.);
	h_phy_delw = new H1F("h_phy_delw","h_phy_delw", 100, -0.15, 0.15);

	h2_phy_res_t = new H2F("h2_phy_res_t","h2_phy_res_t",100, 0.0, 4.5, 100, -0.3, 0.3 );
	h2_phy_res_phicm = new H2F("h2_phy_res_phicm","h2_phy_res_phicm",100, -180.0, 180.0, 100, -20.0, 20.0 );
	h2_phy_res_w = new H2F("h2_phy_res_w","h2_phy_res_w",100, 0.0, 4.5, 100, -0.15, 0.15 );

	h_phy_mm_epkX = new H1F("h_phy_mm_epkX","h_phy_mm_epkX",100, 0.0, 1.0);
	h_phy_mm_phi = new H1F("h_phy_mm_phi","h_phy_mm_phi",100, 0.0, 2.0);
	//	h_phy_mm_epX = new H1F("h_phy_mm_epX","h_phy_mm_epX", 100, 0.0, 10.0);
	
	h2_phy_mm_kmphi = new H2F("h2_phy_mm_kmphi","h2_phy_mm_kmphi", 50, 0.0, 1.5, 50, 0.0, 1.0 );
	h2_phy_tcmphi = new H2F("h2_phy_tcmphi","h2_phy_tcmphi",100, -180.0, 180.0, 100, 0.0, 4.5);

	h_phy_calc_km_p = new H1F("h_phy_calc_km_p","h_phy_calc_km_p",100, 0.0, 6.0);
	h_phy_calc_km_theta = new H1F("h_phy_calc_km_theta","h_phy_calc_km_theta",100, 0.0, 60.0);
	h_phy_calc_km_phi = new H1F("h_phy_calc_km_phi","h_phy_calc_km_phi",100, minphi, maxphi);
	h_phy_calc_km_delp = new H1F("h_phy_calc_km_delp","h_phy_calc_km_delp",100, -0.5, 0.5);
	h_phy_calc_km_deltheta = new H1F("h_phy_calc_km_deltheta","h_phy_calc_km_deltheta",100, -5.0, 5.0);
	h_phy_calc_km_delphi = new H1F("h_phy_calc_km_delphi","h_phy_calc_km_delphi",100, -10.0, 10.0);
	h_phy_calc_km_fracp = new H1F("h_phy_calc_km_fracp","h_phy_calc_km_fracp",100, -0.20, 0.20);

	h2_phy_calc_km_ptheta = new H2F("h2_phy_calc_km_ptheta","h2_phy_calc_km_ptheta",100, 0.0, 6.0, 100, 0.0, 60.0 );
	h2_phy_calc_km_pphi = new H2F("h2_phy_calc_km_pphi","h2_phy_calc_km_pphi",100, 0.0, 6.0, 100, minphi, maxphi );
	h2_phy_calc_km_phitheta = new H2F("h2_phy_calc_km_phitheta","h2_phy_calc_km_phitheta",100, minphi, maxphi, 100, 0.0, 60.0 );
	
	h2_phy_calc_km_p = new H2F("h2_phy_calc_km_p","h2_phy_calc_km_p",100, 0.0, 6.0, 100, -0.5, 0.5 );
	h2_phy_calc_km_fracp = new H2F("h2_phy_calc_km_fracp","h2_phy_calc_km_fracp",100, 0.0, 6.0, 100, -0.25, 0.25 );
	h2_phy_calc_km_theta = new H2F("h2_phy_calc_km_theta","h2_phy_calc_km_theta",100, 0.0, 60.0, 100, -0.5, 0.5 );
	h2_phy_calc_km_phi = new H2F("h2_phy_calc_km_phi","h2_phy_calc_km_phi",100, minphi, maxphi, 100, -10.5, 10.5 );
	
	h_phy_kp_p = new H1F("h_phy_kp_p","h_phy_kp_p", 100, 0.0, 8.0);
	h_phy_kp_theta = new H1F("h_phy_kp_theta","h_phy_kp_theta", 100, 0.0, 45.0);
	h_phy_kp_phi = new H1F("h_phy_kp_phi","h_phy_kp_phi", 100, minphi, maxphi);
       	
    }

    public void CreateMCHistograms(){
	/*mc_frame1.setSize(1600,800);
	mc_frame2.setSize(1600,800);
	mc_frame3.setSize(1600,800);
	mc_frame4.setSize(1600,800);
	*/
	h_mcphy_q2 = new H1F("h_mcphy_q2","h_mcphy_q2",100,0.00, 10.5 );
 	h_mcphy_xb = new H1F("h_mcphy_xb","h_mcphy_xb",100,0.0, 1.2 );
	h_mcphy_t = new H1F("h_mcphy_t","h_mcphy_t",100,0.0, 5.5 );
	h_mcphy_w = new H1F("h_mcphy_w","h_mcphy_w",100, 0.0, 6.0 );

	h_mcphy_cm_phi = new H1F("h_mcphy_cm_phi","h_mcphy_cm_phi",100,-180.0, 180.0);
	h_mcphy_cm_theta = new H1F("h_mcphy_cm_theta","h_mcphy_cm_theta",50,-1.0, 1.0);

	h_mcphy_mm2epkp = new H1F("h_mcphy_mm2epkp","h_mcphy_mm2epkp",100, 0.2, 0.8 );
	h_mcphy_mm2ekp = new H1F("h_mcphy_mm2ekp","h_mcphy_mm2ekp",100, 0.2, 0.8 );
        h_mcphy_mm2epkm = new H1F("h_mcphy_mm2epkm","h_mcphy_mm2epkm",100, 0.2, 0.8);
	h_mcphy_mm2ekpkm= new H1F("h_mcphy_mm2ekpkm","h_mcphy_mm2ekpkm",100,0.6, 1.1);
	h_mcphy_mm2ep = new H1F("h_mcphy_mm2ep","h_mcphy_mm2ep",100,-0.5, 1.3);

	h_mcphy_q2x = new H2F("h_mcphy_q2x","h_mcphy_q2x",80,0.05, 0.80, 80, 0.0, 9.0);
	h_mcphy_q2t = new H2F("h_mcphy_q2t","h_mcphy_q2t",80,0.0, 3.5, 80, 0.0, 9.0);

	h_mcphy_kp_p = new H1F("h_mcphy_kp_p","h_mcphy_kp_p", 100, 0.0, 8.0);
	h_mcphy_kp_theta = new H1F("h_mcphy_kp_theta","h_mcphy_kp_theta", 100, 0.0, 60.0);
	h_mcphy_kp_phi = new H1F("h_mcphy_kp_phi","h_mcphy_kp_phi", 100, minphi, maxphi);

	h_mcphy_km_p = new H1F("h_mcphy_km_p","h_mcphy_km_p", 100, 0.0, 5.0);
	h_mcphy_km_theta = new H1F("h_mcphy_km_theta","h_mcphy_km_theta", 100, 0.0, 45.0);
	h_mcphy_km_phi = new H1F("h_mcphy_km_phi","h_mcphy_km_phi", 100, minphi, maxphi);


    }


    public void FillCalcKaonMinus( PhysicsEvent physicsevent ){
	
	double p = physicsevent.lv_km.p();
	double theta = Math.toDegrees(physicsevent.lv_km.theta());
	double phi =  Math.toDegrees(physicsevent.lv_km.phi()) + joo_angle;	

	double mc_p = physicsevent.mc_lv_km.p();
	double mc_theta = Math.toDegrees(physicsevent.mc_lv_km.theta());
	double mc_phi =  Math.toDegrees(physicsevent.mc_lv_km.phi()) + joo_angle;	

	h_phy_calc_km_p.fill(p);
	h_phy_calc_km_theta.fill(theta);
	h_phy_calc_km_phi.fill(phi);
	h_phy_calc_km_delp.fill( p - mc_p );
	h_phy_calc_km_deltheta.fill( theta - mc_theta );
	h_phy_calc_km_delphi.fill( phi - mc_phi );
	h_phy_calc_km_fracp.fill( (p - mc_p)/p );

	h2_phy_calc_km_ptheta.fill(p, theta);
	h2_phy_calc_km_pphi.fill(p, phi);
	h2_phy_calc_km_phitheta.fill( phi, theta);

	h2_phy_calc_km_p.fill( mc_p, (p - mc_p) );
	h2_phy_calc_km_fracp.fill( mc_p, (p - mc_p)/p );
	h2_phy_calc_km_theta.fill( mc_theta, theta - mc_theta );
	h2_phy_calc_km_phi.fill( mc_phi, phi - mc_phi );

    }

    public void FillPhysicsHistos( PhysicsEvent physicsevent ){
	
	h_phy_q2.fill(-physicsevent.q2);
	h_phy_xb.fill(physicsevent.xB);
	h_phy_t.fill(physicsevent.t);
	h_phy_w.fill(physicsevent.w2);
	h_phy_cm_phi.fill(physicsevent.cm_phi);
	h_phy_cm_theta.fill(physicsevent.cm_theta);

	h_phy_q2x.fill(physicsevent.xB, -physicsevent.q2);
	h_phy_q2t.fill(-physicsevent.t, -physicsevent.q2);

	int missing_pr = 5;
	int missing_kp = 3;
	int missing_km = 2;
	if( physicsevent.topology == missing_pr ){
	    h_phy_mm2ekpkm.fill(physicsevent.lv_pr.mass());
	}
	if( physicsevent.topology == missing_kp ){
	    h_phy_mm2epkm.fill(physicsevent.lv_kp.mass());	   
	}
	if( physicsevent.topology == missing_km ){
	    h_phy_mm2epkp.fill(physicsevent.lv_km.mass());

	    FillCalcKaonMinus( physicsevent );
	    h_phy_kp_p.fill(physicsevent.lv_kp.p());
	    h_phy_kp_theta.fill(Math.toDegrees(physicsevent.lv_kp.theta()));
	    h_phy_kp_phi.fill(Math.toDegrees(physicsevent.lv_kp.phi()) + joo_angle);

	}

	h_phy_delt.fill((physicsevent.t) - (physicsevent.mc_t));
	h_phy_delcmphi.fill(physicsevent.cm_phi - physicsevent.mc_cm_phi);
	h_phy_delw.fill(Math.sqrt((physicsevent.w2)) - Math.sqrt(physicsevent.mc_w2));

	h2_phy_res_t.fill( physicsevent.mc_t, (physicsevent.t) - (physicsevent.mc_t));
	h2_phy_res_phicm.fill( physicsevent.mc_cm_phi, physicsevent.cm_phi - physicsevent.mc_cm_phi);
	h2_phy_res_w.fill( Math.sqrt(physicsevent.mc_w2), Math.sqrt(physicsevent.w2) - Math.sqrt(physicsevent.mc_w2) );

	h_phy_mm_epkX.fill( physicsevent.lv_km.mass() );
	h_phy_mm_phi.fill( physicsevent.lv_kp.mass() + physicsevent.lv_km.mass() );
	//h_phy_mm_epX.fill( physicsevent.lv
	
	h2_phy_mm_kmphi.fill( physicsevent.lv_kp.mass() + physicsevent.lv_km.mass(), physicsevent.lv_km.mass() ); 
	h2_phy_tcmphi.fill( physicsevent.cm_phi, physicsevent.t );
	

    }

    public void FillMCPhysicsHistograms( PhysicsEvent physicsevent ){
	
	h_mcphy_q2.fill(-physicsevent.mc_q2);
	h_mcphy_xb.fill(physicsevent.mc_xB);
	h_mcphy_t.fill(physicsevent.mc_t);
	h_mcphy_w.fill(physicsevent.mc_w2);
	h_mcphy_cm_phi.fill(physicsevent.mc_cm_phi);
	h_mcphy_cm_theta.fill(physicsevent.mc_cm_theta);

	h_mcphy_q2x.fill(physicsevent.mc_xB, -physicsevent.mc_q2);
	h_mcphy_q2t.fill(physicsevent.mc_t, -physicsevent.mc_q2);

	h_mcphy_mm2epkp.fill(physicsevent.mc_missing_km.mass());
	h_mcphy_mm2epkm.fill(physicsevent.mc_missing_kp.mass());
	h_mcphy_mm2ekpkm.fill(physicsevent.mc_missing_pr.mass());

	h_mcphy_kp_p.fill(physicsevent.mc_lv_kp.p());
	h_mcphy_kp_theta.fill(Math.toDegrees(physicsevent.mc_lv_kp.theta()));
	h_mcphy_kp_phi.fill(Math.toDegrees(physicsevent.mc_lv_kp.phi()) + joo_angle);

	h_mcphy_km_p.fill(physicsevent.mc_lv_km.p());
	h_mcphy_km_theta.fill(Math.toDegrees(physicsevent.mc_lv_km.theta()));
	h_mcphy_km_phi.fill(Math.toDegrees(physicsevent.mc_lv_km.phi()) + joo_angle);
	
    }

    public void RebinForAcceptance(){

	
    }


    public void physicsHistoToHipo(){

	dir.mkdir("/phys/");
	dir.cd("/phys/");
	dir.addDataSet(h_phy_q2);
	dir.addDataSet(h_phy_xb);
	dir.addDataSet(h_phy_t);
	dir.addDataSet(h_phy_w);
	dir.addDataSet(h_phy_q2x);
	dir.addDataSet(h_phy_q2t);
	dir.addDataSet(h_phy_cm_phi);
	dir.addDataSet(h_phy_cm_theta);

	dir.addDataSet(h_phy_mm2epkp);
	dir.addDataSet(h_phy_mm2ekp);
	dir.addDataSet(h_phy_mm2epkm);
	dir.addDataSet(h_phy_mm2ekpkm);
	dir.addDataSet(h_phy_mm2ep);

	dir.addDataSet(h_phy_mm_epkX);
	dir.addDataSet(h_phy_mm_phi);
	dir.addDataSet(h2_phy_mm_kmphi);

	dir.addDataSet(h2_phy_tcmphi);

	dir.addDataSet(h_phy_calc_km_p);
	dir.addDataSet(h_phy_calc_km_theta);
	dir.addDataSet(h_phy_calc_km_phi);
	dir.addDataSet(h2_phy_calc_km_ptheta);
	dir.addDataSet(h2_phy_calc_km_pphi);
	dir.addDataSet(h2_phy_calc_km_phitheta);


    }

    public void viewHipoOut(){
	TBrowser browser = new TBrowser(dir);
    }


    public void savePhysicsHistograms( boolean view ){

	physicsHistoToHipo();
	if( view ){
	    viewHipoOut();
	}

    }

    public void ViewHistograms( boolean toggleView ){

	c_phy_q2xt.setSize(1600,800);
	c_phy_q2xt.divide(2,2);
	c_phy_q2xt.cd(0);
	h_phy_q2.setTitleX("Q^2 [GeV^2 ]");
 	h_phy_q2.setOptStat(1110);
	c_phy_q2xt.draw(h_phy_q2);
	c_phy_q2xt.cd(1);
	h_phy_xb.setTitleX("Xb");
	h_phy_xb.setOptStat(1110);
	c_phy_q2xt.draw(h_phy_xb);
	c_phy_q2xt.cd(2);
	h_phy_t.setTitleX("t [GeV]");
	h_phy_t.setOptStat(1110);
 	c_phy_q2xt.draw(h_phy_t);
	c_phy_q2xt.cd(3);
	h_phy_w.setTitleX("W [GeV]");
	h_phy_w.setOptStat(1110);
 	c_phy_q2xt.draw(h_phy_w);

	c2_phy_q2xt.setSize(1600,800);
	c2_phy_q2xt.divide(2,1);
	c2_phy_q2xt.cd(0);
	h_phy_q2x.setTitleX("Xb");
	h_phy_q2x.setTitleY("Q^2 [GeV^2 ]");
	c2_phy_q2xt.draw(h_phy_q2x,"colz");
	c2_phy_q2xt.cd(1);
	h_phy_q2t.setTitleX("-t [GeV^2]");
	h_phy_q2t.setTitleY("Q^2 [GeV^2 ]");
	c2_phy_q2xt.draw(h_phy_q2t,"colz");

	c3_phy_q2xt.setSize(1600,800);
	c3_phy_q2xt.divide(2,1);
	c3_phy_q2xt.cd(0);
	h_phy_cm_phi.setTitleX("Center of Mass phi [Deg]");
	c3_phy_q2xt.draw(h_phy_cm_phi);
	c3_phy_q2xt.cd(1);
	h_phy_cm_theta.setTitleX("Center of Mass cos( #theta ) [Deg]");
	c3_phy_q2xt.draw(h_phy_cm_theta);

	c4_phy_mm2.setSize(1600,800);
 	c4_phy_mm2.divide(3,1);
	c4_phy_mm2.cd(0);
	h_phy_mm2epkp.setTitle("Missing Mass^2 [GeV^2] of K^-");
	h_phy_mm2epkp.setOptStat(1110);
	c4_phy_mm2.draw(h_phy_mm2epkp);
	c4_phy_mm2.cd(1);
	h_phy_mm2epkm.setTitle("Missing Mass^2 [GeV^2] of K^+");
	h_phy_mm2epkm.setOptStat(1110);
	c4_phy_mm2.draw(h_phy_mm2epkm);
	c4_phy_mm2.cd(2);
	h_phy_mm2ekpkm.setTitle("Missing Mass^2 [GeV^2] of Proton");
	h_phy_mm2ekpkm.setOptStat(1110);
	c4_phy_mm2.draw(h_phy_mm2ekpkm);

	c_phy_res_inv.setSize(800,800);
	c_phy_res_inv.divide(2,2);
	c_phy_res_inv.cd(0);
	h_phy_delt.setOptStat(1110);
	h_phy_delt.setTitle("#Delta -t ");
	h_phy_delt.setTitleX("#Delta -t [GeV^2]");
	c_phy_res_inv.draw(h_phy_delt);
	c_phy_res_inv.cd(1);
	h_phy_delcmphi.setOptStat(1110);
	h_phy_delcmphi.setTitle("#Delta #phi ");
	h_phy_delcmphi.setTitleX("#Delta #phi [deg]");
	c_phy_res_inv.draw(h_phy_delcmphi);
	c_phy_res_inv.cd(2);
	h_phy_delw.setOptStat(1110);
	h_phy_delw.setTitle("#Delta W ");
	h_phy_delw.setTitleX("#Delta W [GeV]");
	c_phy_res_inv.draw(h_phy_delw);


	c2_phy_res_inv.setSize(800,800);
	c2_phy_res_inv.divide(2,2);
	c2_phy_res_inv.cd(0);
	h2_phy_res_t.setTitle("Resolution -t REC");
	h2_phy_res_t.setTitleX(" -t [GeV^2]");
	h2_phy_res_t.setTitleY(" #Delta -t [GeV^2]");
	c2_phy_res_inv.draw(h2_phy_res_t,"colz");
	c2_phy_res_inv.cd(1);
	//	h2_phy_res_phicm.setOptStat(1110); 
	h2_phy_res_phicm.setTitle("Resolution #phi CM REC");
	h2_phy_res_phicm.setTitleX("#phi CM [deg]");
	h2_phy_res_phicm.setTitleY("#Delta #phi CM [deg]");
	c2_phy_res_inv.draw(h2_phy_res_phicm,"colz");
	c2_phy_res_inv.cd(2);
	h2_phy_res_w.setTitle("Resolution W REC");
	h2_phy_res_w.setTitleX(" W [GeV]");
	h2_phy_res_w.setTitleY(" #Delta W [GeV]");
	c2_phy_res_inv.draw(h2_phy_res_w,"colz");

	c2_phy_tcmphi.setSize(800,800);
	c2_phy_tcmphi.divide(1,1);
	//h2_phy_tcmphi.setOptStat(1110); 
	h2_phy_tcmphi.setTitle("-t vs #phi CM REC");
	h2_phy_tcmphi.setTitleX("#phi CM [deg]");
	h2_phy_tcmphi.setTitleY("-t [GeV^2]");
	c2_phy_tcmphi.draw(h2_phy_tcmphi,"colz");

	c_phy_mm_phi.setSize(800,800);
	c_phy_mm_phi.divide(2,2);
	c_phy_mm_phi.cd(0);
	h_phy_mm_epkX.setOptStat(1110);
	h_phy_mm_epkX.setTitle(" Mass of X from ep->epK_p X");
	h_phy_mm_epkX.setTitleX(" Mass of X [GeV]");
	c_phy_mm_phi.draw(h_phy_mm_epkX);
	c_phy_mm_phi.cd(1);
	h_phy_mm_phi.setOptStat(1110);
	h_phy_mm_phi.setTitle(" Mass from K^+ + K^- ");
	h_phy_mm_phi.setTitleX(" Mass [GeV]" );
	c_phy_mm_phi.draw(h_phy_mm_phi);
	c_phy_mm_phi.cd(2);
	h2_phy_mm_kmphi.setTitleX("Mass from K^+ + K^- [GeV]");
	h2_phy_mm_kmphi.setTitleX("Mass of X from ep->epK_p X [GeV]");  
	c_phy_mm_phi.draw(h2_phy_mm_kmphi,"colz");
	c_phy_mm_phi.cd(3);

	c1_phy_calc_km_kin.setSize(800,800);
	c1_phy_calc_km_kin.divide(2,2);
	c1_phy_calc_km_kin.cd(0);
	h_phy_calc_km_p.setOptStat(1110);
	h_phy_calc_km_p.setTitle("Calc K^- Momentum");
	h_phy_calc_km_p.setTitleX("p [GeV]");
	c1_phy_calc_km_kin.draw(h_phy_calc_km_p);
	c1_phy_calc_km_kin.cd(1);
	h_phy_calc_km_theta.setOptStat(1110);
	h_phy_calc_km_theta.setTitle("Calc K^- #theta");
	h_phy_calc_km_theta.setTitleX("#theta [deg]");
	c1_phy_calc_km_kin.draw(h_phy_calc_km_theta);
	c1_phy_calc_km_kin.cd(2);
	h_phy_calc_km_phi.setOptStat(1110);
	h_phy_calc_km_phi.setTitle("Calc K^- #phi");
	h_phy_calc_km_phi.setTitleX("#phi [deg]");
	c1_phy_calc_km_kin.draw(h_phy_calc_km_phi);

	c1_phy_calc_km_res.setSize(900,900);
	c1_phy_calc_km_res.divide(2,2);
	c1_phy_calc_km_res.cd(0);
	h_phy_calc_km_delp.setOptStat(1110);
	h_phy_calc_km_delp.setTitle(" #Delta Calc K^- Momentum");
	h_phy_calc_km_delp.setTitleX("#Delta p [GeV]");
	c1_phy_calc_km_res.draw(h_phy_calc_km_delp);
	c1_phy_calc_km_res.cd(1);
	h_phy_calc_km_fracp.setOptStat(1110);
	h_phy_calc_km_fracp.setTitle(" #Delta Calc K^- Momentum / p");
	h_phy_calc_km_fracp.setTitleX("#Delta p / p");
	c1_phy_calc_km_res.draw(h_phy_calc_km_delp);
	c1_phy_calc_km_res.cd(2);
	h_phy_calc_km_deltheta.setOptStat(1110);
	h_phy_calc_km_deltheta.setTitle("#Delta Calc K^- #theta");
	h_phy_calc_km_deltheta.setTitleX("#Delta #theta [deg]");
	c1_phy_calc_km_res.draw(h_phy_calc_km_deltheta);
	c1_phy_calc_km_res.cd(3);
	h_phy_calc_km_delphi.setOptStat(1110);
	h_phy_calc_km_delphi.setTitle("#Delta Calc K^- #phi");
	h_phy_calc_km_delphi.setTitleX("#Delta #phi [deg]");
	c1_phy_calc_km_res.draw(h_phy_calc_km_delphi);
	
	c2_phy_calc_km_kin.setSize(900,900);
	c2_phy_calc_km_kin.divide(2,2);
 	c2_phy_calc_km_kin.cd(0);
 	h2_phy_calc_km_ptheta.setTitle("Calc K^- p vs #theta");
	h2_phy_calc_km_ptheta.setTitleX("p [GeV]");
	h2_phy_calc_km_ptheta.setTitleY("#theta [deg]");
	c2_phy_calc_km_kin.draw(h2_phy_calc_km_ptheta,"colz");
 	c2_phy_calc_km_kin.cd(1);
 	h2_phy_calc_km_pphi.setTitle("Calc K^- p vs #phi");
	h2_phy_calc_km_pphi.setTitleX("p [GeV]");
	h2_phy_calc_km_pphi.setTitleY("#phi [deg]");
	c2_phy_calc_km_kin.draw(h2_phy_calc_km_pphi,"colz");
 	c2_phy_calc_km_kin.cd(2);
 	h2_phy_calc_km_phitheta.setTitle("Calc K^- #phi vs #theta");
	h2_phy_calc_km_phitheta.setTitleX("#phi [deg]");
	h2_phy_calc_km_phitheta.setTitleY("#theta [deg]");
	c2_phy_calc_km_kin.draw(h2_phy_calc_km_phitheta,"colz");

	c2_phy_calc_km_res.setSize(800,800);
	c2_phy_calc_km_res.divide(2,2);
 	c2_phy_calc_km_res.cd(0);
 	h2_phy_calc_km_p.setTitle("Calc K^- p vs #Delta p");
	h2_phy_calc_km_p.setTitleX("p [GeV]");
	h2_phy_calc_km_p.setTitleY("#Delta p [GeV]");
	c2_phy_calc_km_res.draw(h2_phy_calc_km_p,"colz");
 	c2_phy_calc_km_res.cd(1);
 	h2_phy_calc_km_fracp.setTitle("Calc K^- p vs #Delta p / p");
	h2_phy_calc_km_fracp.setTitleX("p [GeV]");
	h2_phy_calc_km_fracp.setTitleY("#Delta p / p");
	c2_phy_calc_km_res.draw(h2_phy_calc_km_fracp,"colz");
 	c2_phy_calc_km_res.cd(2);
 	h2_phy_calc_km_theta.setTitle("Calc K^- #theta vs #Delta #theta");
	h2_phy_calc_km_theta.setTitleX("#theta [GeV]");
	h2_phy_calc_km_theta.setTitleY("#Delta #theta[deg]");
	c2_phy_calc_km_res.draw(h2_phy_calc_km_theta,"colz");
 	c2_phy_calc_km_res.cd(3);
 	h2_phy_calc_km_phi.setTitle("Calc K^- #phi vs #Delta #phi");
	h2_phy_calc_km_phi.setTitleX("#phi [deg]");
	h2_phy_calc_km_phi.setTitleY("#Delta #phi [deg]");
	c2_phy_calc_km_res.draw(h2_phy_calc_km_phi,"colz");
			
	ParallelSliceFitter fit_kp_p = new ParallelSliceFitter(h2_phy_calc_km_p);
	ParallelSliceFitter fit_kp_theta = new ParallelSliceFitter(h2_phy_calc_km_theta);
	ParallelSliceFitter fit_kp_phi = new ParallelSliceFitter(h2_phy_calc_km_phi);
	int nslices_p = 10;
	int nslices_theta = 10;
	int nslices_phi = 10;
	fit_kp_p.fitSlicesX(nslices_p);
	fit_kp_theta.fitSlicesX(nslices_theta);
	fit_kp_phi.fitSlicesX(nslices_phi);

	GraphErrors sigY_kp_p = fit_kp_p.getSigmaSlices();
	GraphErrors sigY_kp_theta = fit_kp_theta.getSigmaSlices();
	GraphErrors sigY_kp_phi = fit_kp_phi.getSigmaSlices();

	c_err_calc_kp.setSize(800,800);
	c_err_calc_kp.divide(2,2);
	sigY_kp_p.setTitle("Deviations in Calc Kaon^+ Momentum");
	sigY_kp_p.setTitleX(" p [GeV] ");
	sigY_kp_p.setTitleY(" #sigma momentum [GeV] ");
	sigY_kp_p.setMarkerSize(2);
	sigY_kp_p.setMarkerStyle(0);
	sigY_kp_p.setMarkerColor(2);
	c_err_calc_kp.draw(sigY_kp_p);
	c_err_calc_kp.cd(1);
	sigY_kp_theta.setTitle("Deviations in Calc Kaon^+ #theta");
	sigY_kp_theta.setTitleX(" #theta [deg] ");
	sigY_kp_theta.setTitleY(" #sigma #theta [deg] ");
	sigY_kp_theta.setMarkerSize(2);
	sigY_kp_theta.setMarkerStyle(0);
	sigY_kp_theta.setMarkerColor(2);
	c_err_calc_kp.draw(sigY_kp_theta);
	c_err_calc_kp.cd(2);
	sigY_kp_phi.setTitle("Deviations in Calc Kaon^+ #phi");
	sigY_kp_phi.setTitleX(" #phi [deg]");
	sigY_kp_phi.setTitleY(" #sigma #phi [deg] ");
	sigY_kp_phi.setMarkerSize(2);
	sigY_kp_phi.setMarkerStyle(0);
	sigY_kp_phi.setMarkerColor(2);
	c_err_calc_kp.draw(sigY_kp_phi);
       
	/*	frame1.add(c_phy_q2xt);
	frame2.add(c2_phy_q2xt);
	frame3.add(c3_phy_q2xt);
	frame4.add(c4_phy_mm2);
	
	frame_err_calc_kp.add(c_err_calc_kp);

	frame1.setVisible(toggleView);
	frame2.setVisible(toggleView);
	frame3.setVisible(toggleView);
	frame4.setVisible(toggleView);

	frame_err_calc_kp.setVisible(toggleView);
	*/
	
    }

    public void viewMCHistograms( boolean toggleView ){

	c_mcphy_q2xt.setSize(1600,800);
	c_mcphy_q2xt.divide(2,2);
	c_mcphy_q2xt.cd(0);
	h_mcphy_q2.setTitleX("Q^2 [GeV^2 ]");
	h_mcphy_q2.setOptStat(1110);
	c_mcphy_q2xt.draw(h_mcphy_q2);
	c_mcphy_q2xt.cd(1);
	h_mcphy_xb.setTitleX("Xb");
	h_mcphy_xb.setOptStat(1110);
	c_mcphy_q2xt.draw(h_mcphy_xb);
	c_mcphy_q2xt.cd(2);
	h_mcphy_t.setTitleX("t [GeV]");
	h_mcphy_t.setOptStat(1110);
 	c_mcphy_q2xt.draw(h_mcphy_t);
	c_mcphy_q2xt.cd(3);
	h_mcphy_w.setTitleX("W [GeV]");
	h_mcphy_w.setOptStat(1110);
 	c_mcphy_q2xt.draw(h_mcphy_w);

	c2_mcphy_q2xt.setSize(1600,800);
	c2_mcphy_q2xt.divide(2,1);
	c2_mcphy_q2xt.cd(0);
	h_mcphy_q2x.setTitleX("Xb");
	h_mcphy_q2x.setTitleY("Q^2 [GeV^2 ]");
	c2_mcphy_q2xt.draw(h_mcphy_q2x,"colz");
	c2_mcphy_q2xt.cd(1);
	h_mcphy_q2t.setTitleX("-t [GeV^2]");
	h_mcphy_q2t.setTitleY("Q^2 [GeV^2 ]");
	c2_mcphy_q2xt.draw(h_mcphy_q2t,"colz");

	c3_mcphy_q2xt.setSize(1600,800);
	c3_mcphy_q2xt.divide(2,1);
	c3_mcphy_q2xt.cd(0);
	h_mcphy_cm_phi.setTitleX("Center of Mass phi [Deg]");
	c3_mcphy_q2xt.draw(h_mcphy_cm_phi);
	c3_mcphy_q2xt.cd(1);
	h_mcphy_cm_theta.setTitleX("Center of Mass cos( #theta ) [Deg]");
	c3_mcphy_q2xt.draw(h_mcphy_cm_theta);

	c4_mcphy_mm2.setSize(1600,800);
 	c4_mcphy_mm2.divide(3,1);
	c4_mcphy_mm2.cd(0);
	h_mcphy_mm2epkp.setTitle("Missing Mass^2 [GeV^2] of K^-");
	c4_mcphy_mm2.draw(h_mcphy_mm2epkp);
	c4_mcphy_mm2.cd(1);
	h_mcphy_mm2epkm.setTitle("Missing Mass^2 [GeV^2] of K^+");
	c4_mcphy_mm2.draw(h_mcphy_mm2epkm);
	c4_mcphy_mm2.cd(2);
	h_mcphy_mm2ekpkm.setTitle("Missing Mass^2 [GeV^2] of Proton");
	c4_mcphy_mm2.draw(h_mcphy_mm2ekpkm);

	/*	mc_frame1.add(c_mcphy_q2xt);
	mc_frame2.add(c2_mcphy_q2xt);
	mc_frame3.add(c3_mcphy_q2xt);
	mc_frame4.add(c4_mcphy_mm2);
       
	mc_frame1.setVisible(toggleView);
	mc_frame2.setVisible(toggleView);
	mc_frame3.setVisible(toggleView);
	mc_frame4.setVisible(toggleView);
	*/
    }


    public void CompareRECMCHist( boolean toggleView ){

	c_comp_phys.setSize(1200,800);
	c_comp_phys.divide(3,2);
	c_comp_phys.cd(0);
 	h_mcphy_q2.setTitle("MC vs REC Q^2");
	h_mcphy_q2.setTitleX("Q^2 [GeV^2]");
	h_mcphy_q2.setLineColor(2);
	h_mcphy_q2.setOptStat(0000);
	h_phy_q2.setOptStat(0000);
	c_comp_phys.draw(h_mcphy_q2);
	c_comp_phys.draw(h_phy_q2,"same");
	c_comp_phys.getPad(0).getAxisY().setLog(true);
	c_comp_phys.cd(1);
 	h_mcphy_xb.setTitle("MC vs REC Xb");
	h_mcphy_xb.setTitleX("Xb");
	h_mcphy_xb.setLineColor(2);
	h_mcphy_xb.setOptStat(0000);
	h_phy_xb.setOptStat(0000);
	c_comp_phys.draw(h_mcphy_xb);
	c_comp_phys.draw(h_phy_xb,"same");
	c_comp_phys.getPad(1).getAxisY().setLog(true);
	c_comp_phys.cd(2);
 	h_mcphy_w.setTitle("MC vs REC W");
	h_mcphy_w.setTitleX("W [GeV]");
	h_mcphy_w.setLineColor(2);
	h_mcphy_w.setOptStat(0000);
	h_phy_w.setOptStat(0000);
	c_comp_phys.draw(h_mcphy_w);
	c_comp_phys.draw(h_phy_w,"same");
	c_comp_phys.getPad(2).getAxisY().setLog(true);
 	c_comp_phys.cd(3);
 	h_mcphy_t.setTitle("MC vs REC -t");
	h_mcphy_t.setTitleX("-t [GeV^2]");
	h_mcphy_t.setLineColor(2);
	h_mcphy_t.setOptStat(0000);
	h_phy_t.setOptStat(0000);
	c_comp_phys.draw(h_mcphy_t);
	c_comp_phys.draw(h_phy_t,"same");
	c_comp_phys.getPad(3).getAxisY().setLog(true);
	c_comp_phys.cd(4);
 	h_mcphy_cm_phi.setTitle("MC vs REC #phi_cm");
	h_mcphy_cm_phi.setTitleX("#phi [deg]");
	h_mcphy_cm_phi.setLineColor(2);
	h_mcphy_cm_phi.setOptStat(0000);
	h_phy_cm_phi.setOptStat(0000);
	c_comp_phys.draw(h_mcphy_cm_phi);
	c_comp_phys.draw(h_phy_cm_phi,"same");
	c_comp_phys.getPad(4).getAxisY().setLog(true);

	c_comp_kp.setSize(800,800);
	c_comp_kp.divide(2,2);
	c_comp_kp.cd(0);
	h_mcphy_kp_p.setTitle("MC vs REC p K^+");
	h_mcphy_kp_p.setTitleX("p [GeV]");
	h_phy_kp_p.setLineColor(2);
	c_comp_kp.draw(h_mcphy_kp_p);
	c_comp_kp.draw(h_phy_kp_p,"same");
	c_comp_kp.getPad(0).getAxisY().setLog(true);
	c_comp_kp.cd(1);
	h_mcphy_kp_theta.setTitle("MC vs REC #theta K^+");
	h_mcphy_kp_theta.setTitleX("#theta [deg]");
	h_phy_kp_theta.setLineColor(2);
	c_comp_kp.draw(h_mcphy_kp_theta);
	c_comp_kp.draw(h_phy_kp_theta,"same");
	c_comp_kp.getPad(1).getAxisY().setLog(true);
	c_comp_kp.cd(2);
	h_mcphy_kp_phi.setTitle("MC vs REC #phi K^+");
	h_mcphy_kp_phi.setTitleX("#phi [deg]");
	h_phy_kp_phi.setLineColor(2);
	c_comp_kp.draw(h_mcphy_kp_phi);
	c_comp_kp.draw(h_phy_kp_phi,"same");
	c_comp_kp.getPad(2).getAxisY().setLog(true);
	

	c_comp_km.setSize(800,800);
	c_comp_km.divide(2,2);
	c_comp_km.cd(0);
	h_mcphy_km_p.setTitle("MC vs REC p K^-");
	h_mcphy_km_p.setTitleX("p [GeV]");
	h_phy_calc_km_p.setLineColor(2);
	c_comp_km.draw(h_mcphy_km_p);
	c_comp_km.draw(h_phy_calc_km_p,"same");
	c_comp_km.getPad(0).getAxisY().setLog(true);
	c_comp_km.cd(1);
	h_mcphy_km_theta.setTitle("MC vs REC #theta K^-");
	h_mcphy_km_theta.setTitleX("#theta [deg]");
	h_phy_calc_km_theta.setLineColor(2);
	c_comp_km.draw(h_mcphy_km_theta);
	c_comp_km.draw(h_phy_calc_km_theta,"same");
	c_comp_km.getPad(1).getAxisY().setLog(true);
	c_comp_km.cd(2);
	h_mcphy_km_phi.setTitle("MC vs REC #phi K^-");
	h_mcphy_km_phi.setTitleX("#phi [deg]");
	h_phy_calc_km_phi.setLineColor(2);
	c_comp_km.draw(h_mcphy_km_phi);
	c_comp_km.draw(h_phy_calc_km_phi,"same");
	c_comp_km.getPad(2).getAxisY().setLog(true);

	/*	f_comp_phy.add(c_comp_phys);
	f_comp_kp.add(c_comp_kp);
	f_comp_km.add(c_comp_km);

	f_comp_phy.setVisible(toggleView);
	f_comp_kp.setVisible(toggleView);
	f_comp_km.setVisible(toggleView);
	*/
    }


    public void Acceptance( H1F h_rec, H1F h_mc, String kinname, String units, int tempx, int tempy ){
	
 	System.out.println(" >> FINDING ACCEPTANCE FOR " + kinname );
	EmbeddedCanvas c_acc = new EmbeddedCanvas();
	//JFrame f_acc = new JFrame();
	//f_acc.setSize(tempx,tempy);
	c_acc.setSize(tempx, tempy);	
	H1F h_acc = H1F.divide(h_rec,h_mc);
	h_acc.setTitle("Acceptance for "+kinname);
	h_acc.setTitleX(kinname + " " +  units);
	h_acc.setTitleY("acceptance");
	//h_acc.setMarkerColor(2);
	//h_acc.setMarkerSize(2);
	c_acc.draw(h_acc);

	//f_acc.add(c_acc);
	//f_acc.setVisible(true);

	if( kinname == "#phi" )kinname = "phi";

	c_acc.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_acc_"+kinname+".png");
     	
    }


    public void viewAcceptance(){

	System.out.println(" >> VIEWING ACCEPTANCE NOW " );

	Acceptance(h_phy_q2,h_mcphy_q2,"Q2","[GeV^2]",800,800);
	Acceptance(h_phy_xb,h_mcphy_xb,"Xb","",800,800);
	Acceptance(h_phy_t,h_mcphy_t,"t","[GeV^2]",800,800);
	Acceptance(h_phy_w,h_mcphy_w,"W","[GeV]",800,800);
	Acceptance(h_phy_cm_phi,h_mcphy_cm_phi,"#phi","[deg]",800,800);

    }

    public void SavePhysHistograms(){

	c_phy_q2xt.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_phy_q2xt.png");
	c2_phy_q2xt.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h2_phy_q2xt.png");
	c3_phy_q2xt.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_phy_angles.png");
	c4_phy_mm2.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_phy_mm2.png");

	
	c_phy_res_inv.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_res_inv_tcmphi.png");
	c_phy_mm_phi.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h12_mm_phi.png");
	c2_phy_res_inv.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h2_res_inv_tcmphi.png");
	c2_phy_tcmphi.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h2_tcmphi.png");

	c1_phy_calc_km_kin.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_calc_km_kin.png"); 
	c1_phy_calc_km_res.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_calc_km_res.png"); 
	c2_phy_calc_km_kin.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h2_calc_km_kin.png"); 
	c2_phy_calc_km_res.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h2_calc_km_res.png"); 

	c_err_calc_kp.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/g_err_calc_kp.png");  	

	c_comp_kp.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_comp_kp.png");
	c_comp_km.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_comp_km.png");
	c_comp_phys.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_comp_phys_all.png"); 
       

    }

    public void SaveMCPhysHistograms(){

	c_mcphy_q2xt.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_mcphy_q2xt.png");
	c2_mcphy_q2xt.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h2_mcphy_q2xt.png");
	c3_mcphy_q2xt.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_mcphy_angles.png");
	c4_mcphy_mm2.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_mcphy_mm2.png");
	
    }


}
