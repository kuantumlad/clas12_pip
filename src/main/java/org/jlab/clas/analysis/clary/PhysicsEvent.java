package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import org.jlab.clas.analysis.clary.PhysicalConstants;
import org.jlab.clas.physics.Vector3;
import org.jlab.clas.physics.LorentzVector;


public class PhysicsEvent {


    public static LorentzVector lv_beam = new LorentzVector(0,0,PhysicalConstants.eBeam,PhysicalConstants.eBeam);
    public static LorentzVector target = new LorentzVector(0,0,0,PhysicalConstants.mass_proton);

    
    public static LorentzVector lv_el  = new LorentzVector(0,0,0,0);
    public static LorentzVector lv_pr  = new LorentzVector(0,0,0,0);
    public static LorentzVector lv_kp  = new LorentzVector(0,0,0,0);
    public static LorentzVector lv_km  = new LorentzVector(0,0,0,0);
    public static LorentzVector lv_phi = new LorentzVector(0,0,0,0);
    
    public static LorentzVector missing_kp  = new LorentzVector(0,0,0,0);
    public static LorentzVector missing_km  = new LorentzVector(0,0,0,0);
    public static LorentzVector missing_pr = new LorentzVector(0,0,0,0);
    
    public static LorentzVector lv_q  = new LorentzVector(0,0,0,0);
    public static LorentzVector lv_w  = new LorentzVector(0,0,0,0);
    public static LorentzVector lv_virtualphoton  = new LorentzVector(0,0,0,0);
    
    public static LorentzVector lv_CM = new LorentzVector(0,0,0,0);
    public static LorentzVector lv_CM_target = new LorentzVector(0,0,0,0);
    public static LorentzVector lv_CM_pr =  new LorentzVector(0,0,0,0);
    public static LorentzVector lv_CM_q =  new LorentzVector(0,0,0,0);
    public static LorentzVector lv_CM_kp =  new LorentzVector(0,0,0,0);
    public static LorentzVector lv_CM_km =  new LorentzVector(0,0,0,0);

    public static Vector3 v_CM_boost =  new Vector3(0,0,0) ;
    public static Vector3 v_CM_phi = new Vector3(0,0,0);

    public static int topology;

    public static double el_p;
    public static double pr_p;
    public static double kp_p;
    public static double km_p;
    public static double phi_p;

    public static double q2; 
    public static double xB;
    public static double t;
    public static double w2;
    public static double cm_phi;
    public static double cm_theta;
    
    ////////////////////////////////////////////
    //
    // MC VARIABLES
    //
    //
    ////////////////////////////////////////////

    public static LorentzVector mc_lv_el  = new LorentzVector(0,0,0,0);
    public static LorentzVector mc_lv_pr  = new LorentzVector(0,0,0,0);
    public static LorentzVector mc_lv_kp  = new LorentzVector(0,0,0,0);
    public static LorentzVector mc_lv_km  = new LorentzVector(0,0,0,0);
    public static LorentzVector mc_lv_phi = new LorentzVector(0,0,0,0);

    public static LorentzVector mc_lv_q  = new LorentzVector(0,0,0,0);
    public static LorentzVector mc_lv_w  = new LorentzVector(0,0,0,0);
    public static LorentzVector mc_lv_virtualphoton  = new LorentzVector(0,0,0,0);

    public static LorentzVector mc_lv_CM = new LorentzVector(0,0,0,0);
    public static LorentzVector mc_lv_CM_target = new LorentzVector(0,0,0,0);
    public static LorentzVector mc_lv_CM_pr =  new LorentzVector(0,0,0,0);
    public static LorentzVector mc_lv_CM_q =  new LorentzVector(0,0,0,0);
    public static LorentzVector mc_lv_CM_kp =  new LorentzVector(0,0,0,0);
    public static LorentzVector mc_lv_CM_km =  new LorentzVector(0,0,0,0);

    public static LorentzVector mc_missing_kp = new LorentzVector(0,0,0,0);
    public static LorentzVector mc_missing_km = new LorentzVector(0,0,0,0);
    public static LorentzVector mc_missing_pr = new LorentzVector(0,0,0,0);

    public static Vector3 mc_v_CM_boost =  new Vector3(0,0,0);
    public static Vector3 mc_v_CM_phi = new Vector3(0,0,0);

    public static double mc_el_p;
    public static double mc_pr_p;
    public static double mc_kp_p;
    public static double mc_km_p;
    public static double mc_phi_p;

    public static double mc_q2; 
    public static double mc_xB;
    public static double mc_t;
    public static double mc_w2;
    public static double mc_cm_phi;
    public static double mc_cm_theta;


    public static List<Double> l_physev = new ArrayList<>();
    public static List<Double> l_mcphysev = new ArrayList<>();

    public void clearPhysicsEvent(){
	//System.out.println(">> CLEARING REC Physics Event ");
	l_physev.clear();

	lv_el.setPxPyPzE(0.0, 0.0, 0.0, 0.0);
	lv_pr.setPxPyPzE(0.0, 0.0, 0.0, 0.0);
	lv_kp.setPxPyPzE(0.0, 0.0, 0.0, 0.0);
	lv_km.setPxPyPzE(0.0, 0.0, 0.0, 0.0);
	lv_phi.setPxPyPzE(0.0, 0.0, 0.0, 0.0);
	
	missing_kp.setPxPyPzE(0.0, 0.0, 0.0, 0.0);
	missing_km.setPxPyPzE(0.0, 0.0, 0.0, 0.0);
	missing_pr.setPxPyPzE(0.0, 0.0, 0.0, 0.0);
	
	lv_q.setPxPyPzE(0,0,0,0);
	lv_w.setPxPyPzE(0,0,0,0);
	lv_virtualphoton.setPxPyPzE(0,0,0,0);
	
	lv_CM.setPxPyPzE(0,0,0,0);
	lv_CM_target.setPxPyPzE(0,0,0,0);
	lv_CM_pr.setPxPyPzE(0,0,0,0);
	lv_CM_q.setPxPyPzE(0,0,0,0);
	lv_CM_kp.setPxPyPzE(0,0,0,0);
	lv_CM_km.setPxPyPzE(0,0,0,0);
	
	v_CM_boost.setXYZ(0,0,0);
	v_CM_phi.setXYZ(0,0,0);
	
	q2 = 0.0;
	xB = 0.0;
	t = 0.0;
	w2 = 0.0;
	cm_phi = 0.0;
	cm_theta = 0.0;
    }
	 //////////////////////////////////////////////
	 //
	 //
	 // RESET MC VARIABLES
	 //
	 /////////////////////////////////////////////

    public void clearMCPhysicsEvent(){
	//System.out.println(">> CLEARING MC Physics Event ");
	l_mcphysev.clear();

	 mc_lv_el.setPxPyPzE(0.0, 0.0, 0.0, 0.0);
	 mc_lv_pr.setPxPyPzE(0.0, 0.0, 0.0, 0.0);
	 mc_lv_kp.setPxPyPzE(0.0, 0.0, 0.0, 0.0);
	 mc_lv_km.setPxPyPzE(0.0, 0.0, 0.0, 0.0);
	 mc_lv_phi.setPxPyPzE(0.0, 0.0, 0.0, 0.0);

	 mc_missing_kp.setPxPyPzE(0.0, 0.0, 0.0, 0.0);
	 mc_missing_km.setPxPyPzE(0.0, 0.0, 0.0, 0.0);
	 mc_missing_pr.setPxPyPzE(0.0, 0.0, 0.0, 0.0);

	 mc_lv_q.setPxPyPzE(0,0,0,0);
	 mc_lv_w.setPxPyPzE(0,0,0,0);
	 mc_lv_virtualphoton.setPxPyPzE(0,0,0,0);

	 mc_lv_CM.setPxPyPzE(0,0,0,0);
	 mc_lv_CM_target.setPxPyPzE(0,0,0,0);
	 mc_lv_CM_pr.setPxPyPzE(0,0,0,0);
	 mc_lv_CM_q.setPxPyPzE(0,0,0,0);
	 mc_lv_CM_kp.setPxPyPzE(0,0,0,0);
	 mc_lv_CM_km.setPxPyPzE(0,0,0,0);

	 mc_v_CM_boost.setXYZ(0,0,0);
	 mc_v_CM_phi.setXYZ(0,0,0);
	
	 mc_q2 = 0.0;
	 mc_xB = 0.0;
	 mc_t = 0.0;
	 mc_w2 = 0.0;
	 mc_cm_phi = 0.0;
	 mc_cm_theta = 0.0;


    }


}
