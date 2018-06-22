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

import org.jlab.analysis.math.ClasMath;
import org.jlab.clas.physics.Vector3;
import org.jlab.clas.physics.LorentzVector;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;


public class BHistoPhysKinPlotter {

    EmbeddedCanvas c_temp_phykin = new EmbeddedCanvas();
    EmbeddedCanvas c_temp_phykin2 = new EmbeddedCanvas();

    H1F h_temp_p;
    H1F h_temp_theta;
    H1F h_temp_phi;
    H1F h_temp_fracp;
    H1F h_temp_delp;
    H1F h_temp_deltheta;
    H1F h_temp_delphi;

    H1F h_acc = null;

    H1F h_temp_q2;
    H1F h_temp_t;
    H1F h_temp_x;
    H1F h_temp_w;
    H1F h_temp_phicm;

    H2F h_temp_q2x;
    H2F h_temp_q2w;
    H2F h_temp_tphicm;

    String eventtype, temp_particlename;

    double minp = 0.0;
    double maxp = 0.0;
    double mintheta = 0.0;
    double maxtheta = 0.0;

    public BHistoPhysKinPlotter( String eventtypes, String particlename){
	
	this.eventtype = eventtypes;
	this.temp_particlename = particlename;

    }
    
    public void SetMinMax(){// int tempmin, int tempmax) {
	
	if( temp_particlename == "el" ){
	    minp = 0.0;
	    maxp = 10.5;    
	    mintheta = 0.0;
	    maxtheta = 50.0;
	}
	if( temp_particlename == "pr" ){
	    minp = 0.0;
	    maxp = 5.5;    
	    mintheta = 0.0;
	    maxtheta = 80.0;
	}
	if ( temp_particlename == "kp" || temp_particlename == "km" ){
	    minp = 0.0;
	    maxp = 6.5;    
	    mintheta = 0.0;
	    maxtheta = 50.0;

	}
	else{
	    minp = 0.0;
	    maxp = 10.0;
	    mintheta = 0.0;
	    maxtheta = 50.0;
	}
	

	
    }
    //EVENTTYPE IS EITHER MC OR RC
    public void CreateH1Kin(){// String eventtype, String temp_particlename){

	SetMinMax();
	h_temp_p = new H1F("h_"+eventtype+"_"+temp_particlename+"_p","h_"+eventtype+"_"+temp_particlename+"_p:GeV", 100, minp, maxp);
	h_temp_theta = new H1F("h_"+eventtype+"_"+temp_particlename+"_theta","h_"+eventtype+"_"+temp_particlename+"_thea", 100, mintheta, maxtheta);
	h_temp_phi = new H1F("h_"+eventtype+"_"+temp_particlename+"_phi","h_"+eventtype+"_"+temp_particlename+"_phi", 100, -180.0, 180.0);
	
	h_temp_q2 = new H1F("h_"+eventtype+"_"+temp_particlename+"_q2","h_"+eventtype+"_"+temp_particlename+"_q2", 100, 0.0, 9.5);
	h_temp_t = new H1F("h_"+eventtype+"_"+temp_particlename+"_t","h_"+eventtype+"_"+temp_particlename+"_t", 100, 0.0, 4.0);
	h_temp_x = new H1F("h_"+eventtype+"_"+temp_particlename+"_x","h_"+eventtype+"_"+temp_particlename+"_x", 100, 0.0, 1.0);
	h_temp_w = new H1F("h_"+eventtype+"_"+temp_particlename+"_w","h_"+eventtype+"_"+temp_particlename+"_w", 100, 0.0, 4.0);
	h_temp_phicm = new H1F("h_"+eventtype+"_"+temp_particlename+"_phicm","h_"+eventtype+"_"+temp_particlename+"_phicm", 100, -180.0, 180.0);
	
    }
    
    
    public void FillH1Kin( PhysicsEvent physicsevent){
	double p = 0 , theta = 0, phi = 0, q2 = 0, x = 0 , t = 0, w = 0, phicm = 0;
	if( eventtype == "mc" ){
	    if( temp_particlename == "el"){
		//System.out.println(" >> doing electron " );
		p  = physicsevent.mc_lv_el.p();
		theta  = Math.toDegrees(physicsevent.mc_lv_el.theta());
		phi  = Math.toDegrees(physicsevent.mc_lv_el.phi());
	    }
	    if( temp_particlename == "pr"){	
		p  = physicsevent.mc_lv_pr.p();
		theta  = Math.toDegrees(physicsevent.mc_lv_pr.theta());
		phi  = Math.toDegrees(physicsevent.mc_lv_pr.phi());
	    }
	    if( temp_particlename == "kp"){		
		p  = physicsevent.mc_lv_kp.p();
		theta  = Math.toDegrees(physicsevent.mc_lv_kp.theta());
		phi  = Math.toDegrees(physicsevent.mc_lv_kp.phi());
	    }
	    if( temp_particlename == "km"){	    
		p  = physicsevent.mc_lv_km.p();
		theta  = Math.toDegrees(physicsevent.mc_lv_km.theta());
		phi  = Math.toDegrees(physicsevent.mc_lv_km.phi());
	    }
	    q2 = -physicsevent.mc_q2;
	    x = physicsevent.mc_xB;
	    w = physicsevent.mc_w2;
	    t = physicsevent.mc_t;
	    phicm = physicsevent.mc_cm_phi;
	}
	if( eventtype == "RC" ){
	    if( temp_particlename == "el" ){
		p  = physicsevent.lv_el.p();
		theta  = Math.toDegrees(physicsevent.lv_el.theta());
		phi  = Math.toDegrees(physicsevent.lv_el.phi());
	    }
	    if( temp_particlename == "pr"){
		p  = physicsevent.lv_pr.p();
		theta  = Math.toDegrees(physicsevent.lv_pr.theta());
		phi  = Math.toDegrees(physicsevent.lv_pr.phi());
	    }
	    if( temp_particlename == "kp" ){
		p  = physicsevent.lv_kp.p();
		theta  = Math.toDegrees(physicsevent.lv_kp.theta());
		phi  = Math.toDegrees(physicsevent.lv_kp.phi());
	    }
	    if( temp_particlename == "km" ){
		p  = physicsevent.lv_km.p();
		theta  = Math.toDegrees(physicsevent.lv_km.theta());
		phi  = Math.toDegrees(physicsevent.lv_km.phi());
	    }
	    x  = physicsevent.xB;
	    w = Math.sqrt(physicsevent.w2);
	    t = physicsevent.t;
	    phicm = physicsevent.cm_phi;
	    
	}
	//System.out.println(" >> " + p + " " + theta + " " + phi + " " + q2 + " " + t + " " + x + " " + w + " " + phicm );
	h_temp_p.fill( p );
	h_temp_theta.fill( theta );
	h_temp_phi.fill( phi );
	
	h_temp_q2.fill( q2 );
	h_temp_t.fill( t );
	h_temp_x.fill( x );
	h_temp_w.fill( w );
	h_temp_phicm.fill( phicm );
    }       	
    

    
        
    public void ViewH1Kin( ){
	

	c_temp_phykin.setSize(1200,600);
	c_temp_phykin.divide(3,2);
	c_temp_phykin.cd(0);
	h_temp_q2.setOptStat(1110);
	h_temp_q2.setTitle(eventtype+" Q^2");
	h_temp_q2.setTitleX("Q^2 [GeV^2]");
	c_temp_phykin.draw(h_temp_q2);
	c_temp_phykin.cd(1);
	h_temp_t.setOptStat(1110);
	h_temp_t.setTitle(eventtype+" -t");
	h_temp_t.setTitleX("-t [GeV^2]");
	c_temp_phykin.draw(h_temp_t);
	c_temp_phykin.cd(2);
	h_temp_x.setOptStat(1110);
	h_temp_x.setTitle(eventtype+" Xb");
	h_temp_x.setTitleX("Xb");
	c_temp_phykin.draw(h_temp_x);
	c_temp_phykin.cd(3);
	h_temp_w.setOptStat(1110);
	h_temp_w.setTitle(eventtype+" W");
	h_temp_w.setTitleX("W [GeV]");
	c_temp_phykin.draw(h_temp_w);
	c_temp_phykin.cd(4);
	h_temp_phicm.setOptStat(1110);
	h_temp_phicm.setTitle(eventtype+" #phi_(cm)");
	h_temp_phicm.setTitleX("#phi cm [deg]");
	c_temp_phykin.draw(h_temp_phicm);
	
	c_temp_phykin2.setSize(800,800);
	c_temp_phykin2.divide(2,2);
	c_temp_phykin2.cd(0);
	h_temp_p.setOptStat(1110);
	h_temp_p.setTitle("Momentum for "+ eventtype+" "+temp_particlename);
	h_temp_p.setTitleX(" p [GeV]" );
	c_temp_phykin2.draw(h_temp_p);
	c_temp_phykin2.cd(1);
	h_temp_theta.setOptStat(1110);
	h_temp_theta.setTitle("#Theta for "+ eventtype+" "+temp_particlename);
	h_temp_theta.setTitleX(" #theta [deg]" );
	c_temp_phykin2.draw(h_temp_theta);
	c_temp_phykin2.cd(2);
	h_temp_phi.setOptStat(1110);
	h_temp_phi.setTitle("#phi for "+ eventtype+" "+temp_particlename);
	h_temp_phi.setTitleX(" #phi [deg]" );
	c_temp_phykin2.draw(h_temp_phi);
	       
    }




    public void SaveH1Kin( ){

	ViewH1Kin();
	if( temp_particlename == "phy"){
	    c_temp_phykin.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_"+eventtype+"_"+temp_particlename+"_finalkin.png");
	}
	else{
	    c_temp_phykin2.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_"+eventtype+"_"+temp_particlename+"_phykin.png");
	}		
    }


}
