package org.jlab.clas.analysis.clary;

import org.jlab.clas.analysis.clary.PhysicalConstants;
import org.jlab.clas.analysis.clary.PhysicsEvent;

import java.io.*;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.clas.physics.Vector3;
import org.jlab.clas.physics.LorentzVector;

public class PhysicsBuilder {

    PhysicsEvent physev = new PhysicsEvent();

    LorentzVector lv_beam = new LorentzVector(0,0,PhysicalConstants.eBeam,PhysicalConstants.eBeam);
    LorentzVector target = new LorentzVector(0,0,0,PhysicalConstants.mass_proton);

    LorentzVector lv_el = new LorentzVector(0,0,0,0);
    LorentzVector lv_pr = new LorentzVector(0,0,0,0);
    LorentzVector lv_kp = new LorentzVector(0,0,0,0);
    LorentzVector lv_km = new LorentzVector(0,0,0,0);
    LorentzVector lv_phi = new LorentzVector(0,0,0,0);
 
    LorentzVector missing_kp = new LorentzVector(0,0,0,0);
    LorentzVector missing_km = new LorentzVector(0,0,0,0);
    LorentzVector missing_proton = new LorentzVector(0,0,0,0);

    LorentzVector lv_q = new LorentzVector(0,0,0,0);
    LorentzVector lv_w = new LorentzVector(0,0,0,0);
    LorentzVector lv_virtualphoton = new LorentzVector(0,0,0,0);
    LorentzVector lv_t = new LorentzVector(0,0,0,0);

    LorentzVector lv_CM = new LorentzVector(0,0,0,0);
    LorentzVector lv_CM_target = new LorentzVector(0,0,0,0);
    LorentzVector lv_CM_pr = new LorentzVector(0,0,0,0);
    LorentzVector lv_CM_q = new LorentzVector(0,0,0,0);
    LorentzVector lv_CM_kp = new LorentzVector(0,0,0,0);
    LorentzVector lv_CM_km = new LorentzVector(0,0,0,0);

    Vector3 v_CM_boost = new Vector3(0,0,0);
    Vector3 v_CM_phi = new Vector3(0,0,0);
    
    double q2 = 0.0;
    double xB = 0.0;
    double t = 0.0;
    double w2 = 0.0;
    double cm_phi = 0.0;
    double cm_theta = 0.0;

    LorentzVector mc_lv_el = new LorentzVector(0,0,0,0);
    LorentzVector mc_lv_pr = new LorentzVector(0,0,0,0);
    LorentzVector mc_lv_kp = new LorentzVector(0,0,0,0);
    LorentzVector mc_lv_km = new LorentzVector(0,0,0,0);
    LorentzVector mc_lv_phi = new LorentzVector(0,0,0,0);
 
    LorentzVector mc_missing_kp = new LorentzVector(0,0,0,0);
    LorentzVector mc_missing_km = new LorentzVector(0,0,0,0);
    LorentzVector mc_missing_pr = new LorentzVector(0,0,0,0);

    LorentzVector mc_lv_q = new LorentzVector(0,0,0,0);
    LorentzVector mc_lv_w = new LorentzVector(0,0,0,0);
    LorentzVector mc_lv_virtualphoton = new LorentzVector(0,0,0,0);
    LorentzVector mc_lv_t = new LorentzVector(0,0,0,0);

    LorentzVector mc_lv_CM = new LorentzVector(0,0,0,0);
    LorentzVector mc_lv_CM_target = new LorentzVector(0,0,0,0);
    LorentzVector mc_lv_CM_pr = new LorentzVector(0,0,0,0);
    LorentzVector mc_lv_CM_q = new LorentzVector(0,0,0,0);
    LorentzVector mc_lv_CM_kp = new LorentzVector(0,0,0,0);
    LorentzVector mc_lv_CM_km = new LorentzVector(0,0,0,0);

    Vector3 mc_v_CM_boost = new Vector3(0,0,0);
    Vector3 mc_v_CM_phi = new Vector3(0,0,0);
    
    double mc_q2 = 0.0;
    double mc_xB = 0.0;
    double mc_t = 0.0;
    double mc_w2 = 0.0;
    double mc_cm_phi = 0.0;
    double mc_cm_theta = 0.0;

    int mc_el_index = 0;
    int mc_pr_index = 1;
    int mc_kp_index = 2;
    int mc_km_index = 3;

    public PhysicsEvent setPhysicsEvent( boolean goodevent, DataEvent tempevent, int el_index, int pr_index, int kp_index, int km_index, int eventtopology ){
	
	System.out.println(" >> " + el_index + " " + pr_index + " " + kp_index + " " + km_index );
	physev.clearPhysicsEvent();
	setEventProperties( eventtopology );

	if( goodevent ){
	    setElectronKinematics( tempevent, el_index );
	    setProtonKinematics( tempevent, pr_index );
	    setKaonKinematics( tempevent, kp_index, km_index );
	    setMissingLorentzVectors();	    
	}
	setPhysicsKinematics(goodevent);

	return new PhysicsEvent();
    }

    public PhysicsEvent setMCPhysicsEvent(DataEvent tempevent){
	//System.out.println(">> Setting MC Physics Event ");
	physev.clearMCPhysicsEvent();
	setMCEventProperties();
	setMCElectronKinematics(tempevent);
	setMCProtonKinematics(tempevent);
	setMCKaonKinematics(tempevent);
	setMCMissingLorentzVectors();

	setMCPhysicsKinematics();

	return new PhysicsEvent();
    }

    public void setEventProperties( int tempeventtopology ){
	physev.topology = tempeventtopology;
	physev.l_physev.add((double)tempeventtopology);
		
    }

    public void setMCEventProperties(){


    }
    
    public void  setElectronKinematics( DataEvent tempevent, int el_index ){
	System.out.println(" >> SETTING ELECTRON KINEMATICS " );

	DataBank recBank = tempevent.getBank("REC::Particle");

	float rc_el_px = recBank.getFloat("px",el_index);
        float rc_el_py = recBank.getFloat("py",el_index);
	float rc_el_pz = recBank.getFloat("pz",el_index);
 	float rc_el_vz = recBank.getFloat("vz",el_index);
	
	//double rc_el_px = recBank.getFloat("px",el_index);
        //double rc_el_py = recBank.getFloat("py",el_index);
	//double rc_el_pz = recBank.getFloat("pz",el_index);
 	//double rc_el_vz = recBank.getFloat("vz",el_index);
	
	double rc_el_E = Math.sqrt( rc_el_px*rc_el_px +  rc_el_py*rc_el_py +  rc_el_pz*rc_el_pz + PhysicalConstants.mass_electron*PhysicalConstants.mass_electron);
	
	
	lv_el.setPxPyPzE(recBank.getFloat("px",el_index),recBank.getFloat("py",el_index),recBank.getFloat("pz",el_index),rc_el_E);
	//physev.el_p = lv_el.p();
	physev.lv_el = lv_el;

    }

    public void setMCElectronKinematics(DataEvent tempevent){

	DataBank mcBank = tempevent.getBank("MC::Particle");

	float mc_el_px = mcBank.getFloat("px",mc_el_index);
        float mc_el_py = mcBank.getFloat("py",mc_el_index);
	float mc_el_pz = mcBank.getFloat("pz",mc_el_index);
 	float mc_el_vz = mcBank.getFloat("vz",mc_el_index);
	double mc_el_E = Math.sqrt( mc_el_px*mc_el_px + mc_el_py*mc_el_py +  mc_el_pz*mc_el_pz + PhysicalConstants.mass_electron*PhysicalConstants.mass_electron);
	
	mc_lv_el.setPxPyPzE(mcBank.getFloat("px",mc_el_index),mcBank.getFloat("py",mc_el_index),mcBank.getFloat("pz",mc_el_index),mc_el_E);

	physev.mc_lv_el = mc_lv_el;

    }


     public void setProtonKinematics( DataEvent tempevent, int pr_index ){
	System.out.println(" >> SETTING PROTON KINEMATICS " );

	DataBank recBank = tempevent.getBank("REC::Particle");

	if( physev.topology == 5 ){
	    lv_pr.setPxPyPzE(0,0,0,0);
	    //physev.lv_pr = lv_pr;
	}
	else{
	    float rc_pr_px = recBank.getFloat("px",pr_index);
	    float rc_pr_py = recBank.getFloat("py",pr_index);
	    float rc_pr_pz = recBank.getFloat("pz",pr_index);
	    float rc_pr_vz = recBank.getFloat("vz",pr_index);
	    
	    double rc_pr_E = Math.sqrt( rc_pr_px*rc_pr_px +  rc_pr_py*rc_pr_py +  rc_pr_pz*rc_pr_pz + PhysicalConstants.mass_proton*PhysicalConstants.mass_proton);	    
	    lv_pr.setPxPyPzE(recBank.getFloat("px",pr_index), recBank.getFloat("py",pr_index) ,recBank.getFloat("pz",pr_index), rc_pr_E);
	    physev.lv_pr = lv_pr;

	}

    }

    public void setMCProtonKinematics(DataEvent tempevent){

	DataBank mcBank = tempevent.getBank("MC::Particle");

	float mc_pr_px = mcBank.getFloat("px",mc_pr_index);
        float mc_pr_py = mcBank.getFloat("py",mc_pr_index);
	float mc_pr_pz = mcBank.getFloat("pz",mc_pr_index);
 	float mc_pr_vz = mcBank.getFloat("vz",mc_pr_index);
	double mc_pr_E = Math.sqrt( mc_pr_px*mc_pr_px + mc_pr_py*mc_pr_py +  mc_pr_pz*mc_pr_pz + PhysicalConstants.mass_proton*PhysicalConstants.mass_proton);
	mc_lv_pr.setPxPyPzE(mcBank.getFloat("px",mc_pr_index),mcBank.getFloat("py",mc_pr_index),mcBank.getFloat("pz",mc_pr_index),mc_pr_E);

	physev.mc_lv_pr = mc_lv_pr;


    }


    public void setKaonKinematics(  DataEvent tempevent, int kp_index, int km_index ){

	DataBank recBank = tempevent.getBank("REC::Particle");
	if( physev.topology == 3 ){
	    lv_kp.setPxPyPzE(0,0,0,0);
	}
	else{
	    System.out.println(" >> SETTING KP KINEMATICS " );

	    float rc_kp_px = recBank.getFloat("px",kp_index);
	    float rc_kp_py = recBank.getFloat("py",kp_index);
	    float rc_kp_pz = recBank.getFloat("pz",kp_index);
	    float rc_kp_vz = recBank.getFloat("vz",kp_index);
	    
	    float rc_km_px = recBank.getFloat("px",km_index);
	    float rc_km_py = recBank.getFloat("py",km_index);
	    float rc_km_pz = recBank.getFloat("pz",km_index);
	    float rc_km_vz = recBank.getFloat("vz",km_index);
	    
	    double rc_kp_E = Math.sqrt( rc_kp_px*rc_kp_px +  rc_kp_py*rc_kp_py +  rc_kp_pz*rc_kp_pz + PhysicalConstants.mass_kaon*PhysicalConstants.mass_kaon);	    
	    lv_kp.setPxPyPzE(recBank.getFloat("px",kp_index), recBank.getFloat("py",kp_index), recBank.getFloat("pz",kp_index), rc_kp_E);//rc_kp_px,rc_kp_py,rc_kp_pz,rc_kp_px, rc_kp_E);
	    physev.lv_kp = lv_kp;

	}
	
	if( physev.topology == 2 ){
	    lv_km.setPxPyPzE(0,0,0,0);
	    //physev.lv_km = lv_km;
	}
	else{
	    System.out.println(" >> SETTING KM KINEMATICS " );

	    float rc_kp_px = recBank.getFloat("px",kp_index);
	    float rc_kp_py = recBank.getFloat("py",kp_index);
	    float rc_kp_pz = recBank.getFloat("pz",kp_index);
	    float rc_kp_vz = recBank.getFloat("vz",kp_index);
	    
	    float rc_km_px = recBank.getFloat("px",km_index);
	    float rc_km_py = recBank.getFloat("py",km_index);
	    float rc_km_pz = recBank.getFloat("pz",km_index);
	    float rc_km_vz = recBank.getFloat("vz",km_index);	    
	    
	    double rc_km_E = Math.sqrt( rc_km_px*rc_km_px +  rc_km_py*rc_km_py +  rc_km_pz*rc_km_pz + PhysicalConstants.mass_kaon*PhysicalConstants.mass_kaon);	    
	    lv_km.setPxPyPzE( recBank.getFloat("px",km_index), recBank.getFloat("py",km_index), recBank.getFloat("pz",km_index), rc_km_E);// rc_km_px,rc_km_py,rc_km_pz,rc_km_px, rc_km_E);
	    physev.lv_km = lv_km;
	}

    }

   public void setMCKaonKinematics(DataEvent tempevent){
 
	DataBank mcBank = tempevent.getBank("MC::Particle");

	float mc_kp_px = mcBank.getFloat("px",mc_kp_index);
        float mc_kp_py = mcBank.getFloat("py",mc_kp_index);
	float mc_kp_pz = mcBank.getFloat("pz",mc_kp_index);
 	float mc_kp_vz = mcBank.getFloat("vz",mc_kp_index);
	double mc_kp_E = Math.sqrt( mc_kp_px*mc_kp_px + mc_kp_py*mc_kp_py +  mc_kp_pz*mc_kp_pz + PhysicalConstants.mass_kaon*PhysicalConstants.mass_kaon);
	
	mc_lv_kp.setPxPyPzE(mcBank.getFloat("px",mc_kp_index),mcBank.getFloat("py",mc_kp_index),mcBank.getFloat("pz",mc_kp_index),mc_kp_E);

	physev.mc_lv_kp = mc_lv_kp;

	float mc_km_px = mcBank.getFloat("px",mc_km_index);
        float mc_km_py = mcBank.getFloat("py",mc_km_index);
	float mc_km_pz = mcBank.getFloat("pz",mc_km_index);
 	float mc_km_vz = mcBank.getFloat("vz",mc_km_index);
	double mc_km_E = Math.sqrt( mc_km_px*mc_km_px + mc_km_py*mc_km_py +  mc_km_pz*mc_km_pz + PhysicalConstants.mass_kaon*PhysicalConstants.mass_kaon);
	
	mc_lv_km.setPxPyPzE(mcBank.getFloat("px",mc_km_index),mcBank.getFloat("py",mc_km_index),mcBank.getFloat("pz",mc_km_index),mc_km_E);

	physev.mc_lv_km = mc_lv_km;

    }


    public void setMissingLorentzVectors(){

	if( physev.topology == 5 ){	    
	    //System.out.println(" HAVE MISSING PROTON... CREATING KP LV" );
	    lv_pr.add(lv_beam);
	    lv_pr.add(target);
	    lv_pr.sub(lv_el);
	    lv_pr.sub(lv_kp);
	    lv_pr.sub(lv_km);
	    physev.lv_pr = lv_pr;
	}
	if( physev.topology == 3 ){
	    //System.out.println(" HAVE MISSING KP... CREATING KP LV" );
	    lv_kp.add(lv_beam);
	    lv_kp.add(target);
	    lv_kp.sub(lv_el);
	    lv_kp.sub(lv_pr);
	    lv_kp.sub(lv_km);
	    physev.lv_kp = lv_kp;
	}
	if( physev.topology == 2 ){
	    System.out.println(" HAVE MISSING KM... CREATING KM LV" );
	    lv_km.add(lv_beam);
	    lv_km.add(target);
	    lv_km.sub(lv_el);
	    lv_km.sub(lv_pr);
	    lv_km.sub(lv_kp);
	    physev.lv_km = lv_km;
	}
	

    }

    public void setMCMissingLorentzVectors(){

	mc_missing_kp.add(lv_beam);
	mc_missing_kp.add(target);
	mc_missing_kp.sub(mc_lv_el);
	mc_missing_kp.sub(mc_lv_pr);
	mc_missing_kp.sub(mc_lv_km);
	physev.mc_missing_kp = mc_missing_kp;

	mc_missing_km.add(lv_beam);
	mc_missing_km.add(target);
	mc_missing_km.sub(mc_lv_el);
	mc_missing_km.sub(mc_lv_pr);
	mc_missing_km.sub(mc_lv_kp);
	physev.mc_missing_km = mc_missing_km;

	mc_missing_pr.add(lv_beam);
	mc_missing_pr.add(target);
	mc_missing_pr.sub(mc_lv_el);
	mc_missing_pr.sub(mc_lv_kp);
	mc_missing_pr.sub(mc_lv_km);
	physev.mc_missing_pr = mc_missing_pr;


	
    }

    public void setPhysicsKinematics( boolean good_event ){
	//System.out.println(">> " + lv_q.px() + " " + lv_w.px() + " " + lv_t.px() );
	if( good_event){
	    lv_q.add(lv_beam);
	    lv_q.sub(lv_el);
	    System.out.println(" >> Q2 elements " + lv_beam.e() + " " + lv_el.px() + " " + lv_el.py() + " " + lv_el.pz() );
	    physev.lv_q = lv_q;
	    
	    lv_w.add(lv_beam);
	    lv_w.add(target);
	    lv_w.sub(lv_el);
	    //lv_w.sub(lv_pr);
	    physev.lv_w = lv_w;
	    
	    q2 = lv_q.mass2();
	    physev.q2 = q2;
	    //System.out.println(" >> q2 " + q2 );
	    xB = (-lv_q.mass2()) / (2*PhysicalConstants.mass_proton*(PhysicalConstants.eBeam - lv_el.e() ));
	    physev.xB = xB;
	    
	    //lv_pr.sub(target);
	    //physev.lv_pr = lv_pr;
	    
	    lv_t.add(target);
	    lv_t.sub(lv_pr);
	    t = 2.0*PhysicalConstants.mass_proton*(lv_pr.e() - PhysicalConstants.mass_proton);
	    System.out.println(">> t " + t );
	    //t = lv_t.mass();
	    physev.t = t;
	    
	    w2 = Calculator.W( lv_el );//Math.sqrt( lv_w.mass2() );
	    physev.w2 = w2;
	    
	    lv_CM.add(lv_q);
	    lv_CM.add(target);
	    physev.lv_CM = lv_CM;
	    
	    v_CM_boost.add(lv_CM.boostVector());
	    v_CM_boost.negative();
	    physev.v_CM_boost = v_CM_boost;
	    
	    lv_CM_target.add(target);
	    lv_CM_target.boost(v_CM_boost);
	    physev.lv_CM_target = lv_CM_target;
	    
	    lv_CM_q.add(lv_q);
	    lv_CM_q.boost(v_CM_boost);
	    physev.lv_CM_q = lv_CM_q;
	    
	    lv_CM_pr.add(lv_pr);
	    lv_CM_pr.boost(v_CM_boost);
	    physev.lv_CM_pr = lv_CM_pr;
	    
	    lv_phi.add(lv_kp);
	    lv_phi.add(lv_km);
	    v_CM_phi.add(lv_phi.boostVector());
	    v_CM_phi.negative();
	    physev.v_CM_phi = v_CM_phi;
	    
	    lv_CM_kp.add(lv_kp);
	    lv_CM_km.add(lv_km);
	    physev.lv_CM_kp = lv_CM_kp;
	    
	    lv_CM_kp.boost(v_CM_phi);
	    lv_CM_km.boost(v_CM_phi);
	    physev.lv_CM_km = lv_CM_km;
	    
	    cm_phi = (180.0/Math.PI)*lv_CM_kp.phi();
	    //cm_theta = lv_CM_kp.theta();
	    physev.cm_phi = cm_phi;
	    cm_theta = Math.cos(lv_CM_kp.theta());
	    physev.cm_theta = cm_theta;
	    
	    
	    physev.l_physev.add(q2);
	    physev.l_physev.add(xB);
	    physev.l_physev.add(t);
	    physev.l_physev.add(w2);
	    
	    physev.l_physev.add(cm_theta);
	    physev.l_physev.add(cm_phi);
	    
	    physev.l_physev.add(lv_el.mass());
	    physev.l_physev.add(lv_el.theta());
	    physev.l_physev.add(lv_el.phi());
	    
	    physev.l_physev.add(lv_pr.mass());
	    physev.l_physev.add(lv_pr.theta());
	    physev.l_physev.add(lv_pr.phi());
	    
	    physev.l_physev.add(lv_kp.mass());
	    physev.l_physev.add(lv_kp.theta());
	    physev.l_physev.add(lv_kp.phi());
	    
	    physev.l_physev.add(lv_km.mass());
	    physev.l_physev.add(lv_km.theta());
	    physev.l_physev.add(lv_km.phi());
	}
	else{
	    double no_event_value = 0.0;
	    physev.l_physev.add(no_event_value);
	    physev.l_physev.add(no_event_value);
	    physev.l_physev.add(no_event_value);
	    physev.l_physev.add(no_event_value);
	    
	    physev.l_physev.add(no_event_value);
	    physev.l_physev.add(no_event_value);
	    
	    physev.l_physev.add(no_event_value);
	    physev.l_physev.add(no_event_value);
	    physev.l_physev.add(no_event_value);
	    
	    physev.l_physev.add(no_event_value);
	    physev.l_physev.add(no_event_value);
	    physev.l_physev.add(no_event_value);
	    
	    physev.l_physev.add(no_event_value);
	    physev.l_physev.add(no_event_value);
	    physev.l_physev.add(no_event_value);
	    
	    physev.l_physev.add(no_event_value);
	    physev.l_physev.add(no_event_value);
	    physev.l_physev.add(no_event_value);
	}
	//System.out.println(">> AFTER  " + lv_q.px() + " " + lv_w.px() + " " + lv_t.px() );
	
	//System.out.println(" >> " + lv_CM_kp.px() + " " + lv_CM_kp.py() + " " + lv_CM_kp.pz() );
	//System.out.println(cm_phi + " " + cm_theta);
	
	//System.out.println(">> " + lv_q.e() + " " + xB + " " + t + " " + w2 );
    }
    
    public void setThetaCM(){
	
	
    }
    
    public void setMCPhysicsKinematics(){
	mc_lv_q.add(lv_beam);
	mc_lv_q.sub(mc_lv_el);
	physev.mc_lv_q = mc_lv_q;
	mc_lv_w.add(mc_lv_el);
	mc_lv_w.sub(lv_beam);
	mc_lv_w.sub(mc_lv_pr);
	mc_lv_w.sub(target);
	physev.mc_lv_w = mc_lv_w;

	mc_q2 = mc_lv_q.mass2();
	physev.mc_q2 = mc_q2;
	mc_xB = (-mc_lv_q.mass2()) / (2*PhysicalConstants.mass_proton*(PhysicalConstants.eBeam - mc_lv_el.e() ));
	physev.mc_xB = mc_xB;
	physev.l_mcphysev.add(mc_q2);
	physev.l_mcphysev.add(mc_xB);
	//System.out.println(" >> mc_q2 " + mc_q2 );

	//mc_lv_pr.sub(target);
	//physev.mc_lv_pr = mc_lv_pr;

	mc_lv_t.add(target);
	mc_lv_t.sub(mc_lv_pr);
	//	physev.mc_t = mc_lv_t.mass2());
	mc_t = 2.0*PhysicalConstants.mass_proton*(mc_lv_pr.e() - PhysicalConstants.mass_proton);
	physev.mc_t = mc_t;
	//System.out.println(" >> mc_t " + mc_t );

	physev.l_mcphysev.add(physev.mc_t);

	mc_w2 = Calculator.W(mc_lv_el);//Math.sqrt(mc_lv_w.mass2());
	physev.mc_w2 = Calculator.W(mc_lv_el); //mc_w2;
	physev.l_mcphysev.add(mc_w2);
	//System.out.println(" >> in PB " + mc_w2 );

	mc_lv_CM.add(mc_lv_q);
	mc_lv_CM.add(target);
	physev.mc_lv_CM = mc_lv_CM;

	mc_v_CM_boost.add(mc_lv_CM.boostVector());
	mc_v_CM_boost.negative();
	physev.mc_v_CM_boost = mc_v_CM_boost;
	
	mc_lv_CM_target.add(target);
	mc_lv_CM_target.boost(mc_v_CM_boost);
	physev.mc_lv_CM_target = mc_lv_CM_target;

	mc_lv_CM_q.add(mc_lv_q);
	mc_lv_CM_q.boost(mc_v_CM_boost);
	physev.mc_lv_CM_q = mc_lv_CM_q;

	mc_lv_CM_pr.add(mc_lv_pr);
	mc_lv_CM_pr.boost(mc_v_CM_boost);
	physev.mc_lv_CM_pr = mc_lv_CM_pr;

	mc_lv_phi.add(mc_lv_kp);
	mc_lv_phi.add(mc_lv_km);
	mc_v_CM_phi.add(mc_lv_phi.boostVector());
	mc_v_CM_phi.negative();
	physev.mc_v_CM_phi = mc_v_CM_phi;

	mc_lv_CM_kp.add(mc_lv_kp);
	mc_lv_CM_km.add(mc_lv_km);
	physev.mc_lv_CM_kp = mc_lv_CM_kp;

	mc_lv_CM_kp.boost(mc_v_CM_phi);
	mc_lv_CM_km.boost(mc_v_CM_phi);
	physev.mc_lv_CM_km = mc_lv_CM_km;

	mc_cm_phi = (180.0/Math.PI)*mc_lv_CM_kp.phi();
	mc_cm_theta = mc_lv_CM_kp.theta();
	physev.mc_cm_phi = mc_cm_phi;
	mc_cm_theta = Math.cos(mc_lv_CM_kp.theta());
 	physev.mc_cm_theta = mc_cm_theta;
	physev.l_mcphysev.add(mc_cm_theta);
	physev.l_mcphysev.add(mc_cm_phi);
	
	physev.l_mcphysev.add(mc_lv_el.mass());
	physev.l_mcphysev.add(mc_lv_el.theta());
	physev.l_mcphysev.add(mc_lv_el.phi());

	physev.l_mcphysev.add(mc_lv_pr.mass());
	physev.l_mcphysev.add(mc_lv_pr.theta());
	physev.l_mcphysev.add(mc_lv_pr.phi());

	physev.l_mcphysev.add(mc_lv_kp.mass());
	physev.l_mcphysev.add(mc_lv_kp.theta());
	physev.l_mcphysev.add(mc_lv_kp.phi());

	physev.l_mcphysev.add(mc_lv_km.mass());
	physev.l_mcphysev.add(mc_lv_km.theta());
	physev.l_mcphysev.add(mc_lv_km.phi());


 
    }


}
