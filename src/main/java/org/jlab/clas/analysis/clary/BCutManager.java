package org.jlab.clas.analysis.clary;

import org.jlab.clas.analysis.clary.MatchElectronPID;
import org.jlab.clas.analysis.clary.MatchProtonPID;
import org.jlab.clas.analysis.clary.MatchKaonMinusPID;
import org.jlab.clas.analysis.clary.MatchKaonPlusPID;
import org.jlab.clas.analysis.clary.MatchElectronPhi;
import org.jlab.clas.analysis.clary.MatchProtonPhi;
import org.jlab.clas.analysis.clary.MatchKaonPhi;
import org.jlab.clas.analysis.clary.MatchElectronTheta;
import org.jlab.clas.analysis.clary.MatchProtonTheta;
import org.jlab.clas.analysis.clary.MatchKaonTheta;
import org.jlab.clas.analysis.clary.MatchElectronMntm;
import org.jlab.clas.analysis.clary.MatchProtonMntm;
import org.jlab.clas.analysis.clary.MatchKaonMntm;

import java.util.*;
import java.io.*;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

public class BCutManager {

    //    ElectronCuts el_cut_parameters = new ElectronCuts();

    MatchElectronPID el_pid_cut = new MatchElectronPID();
    MatchProtonPID pr_pid_cut = new MatchProtonPID();
    MatchKaonPlusPID kp_pid_cut = new MatchKaonPlusPID();
    MatchKaonMinusPID km_pid_cut = new MatchKaonMinusPID();
    
    MatchElectronMntm el_mntm_cut = new MatchElectronMntm();
    MatchProtonMntm pr_mntm_cut = new MatchProtonMntm();
    MatchKaonMntm kpkm_mntm_cut = new MatchKaonMntm();

    MatchElectronTheta el_theta_cut = new MatchElectronTheta();
    MatchProtonTheta pr_theta_cut = new MatchProtonTheta();
    MatchKaonTheta kpkm_theta_cut = new MatchKaonTheta();

    MatchElectronPhi el_phi_cut = new MatchElectronPhi();
    MatchProtonPhi pr_phi_cut = new MatchProtonPhi();
    MatchKaonPhi kpkm_phi_cut = new MatchKaonPhi();

    /*NegativeChargeCut el_charge_cut = new NegativeChargeCut();
    PositiveChargeCut pr_charge_cut = new PositiveChargeCut();
    FTOFHitCut el_trigger_cut = new FTOFHitCut();
    FTOFHitLayer1Cut ftof_layer1_cut = new FTOFHitLayer1Cut();
    ECSamplingFractionCut el_ecdep_cut = new ECSamplingFractionCut();
    PhotoElectronCut el_nphe_cut = new PhotoElectronCut();
    ECHitCut el_echit_cut = new ECHitCut();
    PCALHitCut el_pcalhit_cut = new PCALHitCut();
    CalorimeterCut el_cal_cut = new CalorimeterCut();
    */
    ///////////////////////////////////////////////////////
    //USE FOR RESOLUTION STUDIES
    //Vector<BICandidate2> applied_electron_cuts = new Vector<BICandidate2>();
    //Vector<BICandidate2> applied_proton_cuts = new Vector<BICandidate2>();
    //Vector<BICandidate2> applied_kp_cuts = new Vector<BICandidate2>();
    //Vector<BICandidate2> applied_km_cuts = new Vector<BICandidate2>();

    Vector<BICandidate> applied_phy_electron_cuts = new Vector<BICandidate>();
    Vector<BICandidate> applied_phy_proton_cuts = new Vector<BICandidate>();
    Vector<BICandidate> applied_phy_kp_cuts = new Vector<BICandidate>();
    Vector<BICandidate> applied_phy_km_cuts = new Vector<BICandidate>();
    
    ///////////////////////////////////////////////////////
    //USE FOR REAL DATA STUDIES
    boolean status = true;

    String s_electron = "electron";
    String s_proton = "proton";
    String s_kaon_plus = "kaon_plus";
    String s_kaon_minus = "kaon_minus";
    String analysis_type;
    String resolution = "RESOLUTION";
    String physics = "PHYSICS";

    //ALLOWS USER TO USE CUTS MEANT FOR RESOLUTION STUDIES OR ANALYSIS
    public BCutManager( String analysis_type ){
	this.analysis_type = analysis_type;
    
	if( analysis_type == "RESOLUTION"){
	    System.out.println(" >> ANALYSIS FOR " + analysis_type );
	}
	if( analysis_type == "PHYSICS"){
	 System.out.println(" >> ANALYSIS FOR " + analysis_type );
	}   
    }
    

    public void InitializeCutsFor(String tempparticlename ){
	//	System.out.println(">> INIT CUTS ");
	if( tempparticlename == s_electron ){
	    ElectronCuts(analysis_type);
	}
	else if ( tempparticlename == s_proton ){
	    ProtonCuts(analysis_type);
	}
	else if( tempparticlename == s_kaon_plus ){
	    KaonPlusCuts(analysis_type);	    
	}
	else if( tempparticlename == s_kaon_minus ){
	    KaonMinusCuts(analysis_type);	
	}
    }

    public void ElectronCuts(String a_type){	
	if( a_type == resolution ){
	    //applied_electron_cuts.add(el_pid_cut);
	    ///applied_electron_cuts.add(el_mntm_cut);
	    //applied_electron_cuts.add(el_theta_cut);
	    //applied_electron_cuts.add(el_phi_cut);
	}
	if( a_type == physics ){
	    //applied_phy_electron_cuts.add(el_charge_cut);
	    //applied_phy_electron_cuts.add(el_trigger_cut);
	    //applied_phy_electron_cuts.add(el_ecdep_cut);
	    //applied_phy_electron_cuts.add(el_nphe_cut);
	    //applied_phy_electron_cuts.add(el_echit_cut);
	    //applied_phy_electron_cuts.add(el_pcalhit_cut);
	    //applied_phy_electron_cuts.add(el_cal_cut);
	    //applied_phy_electron_cuts.add(ftof_layer1_cut);
	}

    }

    public void ProtonCuts(String a_type){
	if( a_type == resolution ){
	    //applied_proton_cuts.add(pr_pid_cut);
	    //applied_proton_cuts.add(pr_mntm_cut);
	    //applied_proton_cuts.add(pr_theta_cut);
	    //applied_proton_cuts.add(pr_phi_cut);
	}
	if( a_type == physics ){
	    //applied_phy_proton_cuts.add(pr_charge_cut);
	    //applied_phy_proton_cuts.add(ftof_layer1_cut);
	}
    }

    public void KaonPlusCuts(String a_type){
	if( a_type == resolution ){
	    /*	    applied_kp_cuts.add(kp_pid_cut);
	    applied_kp_cuts.add(kpkm_mntm_cut);
	    applied_kp_cuts.add(kpkm_theta_cut);
	    applied_kp_cuts.add(kpkm_phi_cut);
	    */
	}
	if( a_type == physics ){


	}
    }

    public void KaonMinusCuts(String a_type){
	if( a_type == resolution ){
	    //applied_km_cuts.add(km_pid_cut);
	    //applied_km_cuts.add(kpkm_mntm_cut);
	    //applied_km_cuts.add(kpkm_theta_cut);
	    //applied_km_cuts.add(kpkm_phi_cut);
	}
	if( a_type == physics ){
	    //aaplied_phy_km_cuts.add();
	}
    }

    public void KaonLongCuts(){
	//FOR LATER

    }

    public void PionCuts(){
	//FOR LATER

    }

    //////////////////////////////////////////////////////////////////////////////////
    //THIS METHOD IS USED FOR COMPARING MC TO REC IN RESOLUTION STUDIES.
    public boolean passCut(String tempparticle, DataEvent tempevent, int index, int rec_i ){
	status = true;
	//System.out.println(">> COMMENCE PASS CUT ROUTINE ");
	
	if( tempparticle == "electron" ){
	    //status = passRoutine( applied_electron_cuts, tempevent, index, rec_i );
	}
	else if ( tempparticle == "proton" ){
	    //status = passRoutine( applied_proton_cuts, tempevent, index, rec_i );
	}

	else if ( tempparticle == "kaon_plus"){
	    //status = passRoutine( applied_kp_cuts, tempevent, index, rec_i );
	}
	else if ( tempparticle == "kaon_minus"){
	    //status = passRoutine( applied_km_cuts, tempevent, index, rec_i );
	}
	else{
	    System.out.println(">> PLEASE ENTER 'electron', 'proton', 'kaon_plus', 'kaon_minus'");
	}
	return status;

    }

    //////////////////////////////////////////////////////////////////////////////////
    //THIS METHOD IS USED FOR PHYSICS STUDIES. THERE IS NO MC INDEX IN THE ARGUMENT
    public boolean passCut(String tempparticle, DataEvent tempevent, int rec_i ){
	status = true;
	//System.out.println(">> COMMENCE PASS CUT ROUTINE ");
	
	if( tempparticle == "electron" ){
	    status = passRoutine( applied_phy_electron_cuts, tempevent, rec_i );
	}
	else if ( tempparticle == "proton" ){
	    status = passRoutine( applied_phy_proton_cuts, tempevent, rec_i );
	}

	else if ( tempparticle == "kaon_plus"){
	    status = passRoutine( applied_phy_kp_cuts, tempevent, rec_i );
	}
	else if ( tempparticle == "kaon_minus"){
	    status = passRoutine( applied_phy_km_cuts, tempevent, rec_i );
	}
	else{
	    System.out.println(">> PLEASE ENTER 'electron', 'proton', 'kaon_plus', 'kaon_minus'");
	}
	return status;

    }


    public boolean passRoutine( Vector<BICandidate2> tempapplied, DataEvent tempevent, int index, int rec_i ){
	//System.out.println(">> PROCESSING CUTS..");
	//System.out.println(">> NUMBER OF CUTS: " + tempapplied.size() );
	boolean result = true;
	for(BICandidate2 cut: tempapplied ){
	    //System.out.println(i);	    
	    if( !(cut.candidate2(tempevent, index, rec_i)) ){
		result = false;
		break;
	    }
	}
	return result;
    }

    public boolean passRoutine( Vector<BICandidate> tempapplied, DataEvent tempevent, int rec_i ){
	//System.out.println(">> PROCESSING CUTS..");
	//System.out.println(">> NUMBER OF CUTS: " + tempapplied.size() );
	boolean result = true;
	for(BICandidate cut: tempapplied ){
	    //System.out.println(i);	    
	    if( !(cut.candidate(tempevent, rec_i)) ){
		result = false;
		break;
	    }
	}
	return result;
    }

   
}
