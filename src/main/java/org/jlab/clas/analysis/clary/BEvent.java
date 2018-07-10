package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.io.hipo.HipoDataSource;

public class BEvent {

    float mc_el_px;
    float mc_el_py;
    float mc_el_pz;
    float mc_el_vx;
    float mc_el_vy;
    float mc_el_vz;

    float mc_pr_px;
    float mc_pr_py;
    float mc_pr_pz;
    float mc_pr_vx;
    float mc_pr_vy;
    float mc_pr_vz;

    float mc_kp_px;
    float mc_kp_py;
    float mc_kp_pz;
    float mc_kp_vx;
    float mc_kp_vy;
    float mc_kp_vz;

    float mc_km_px;
    float mc_km_py;
    float mc_km_pz;
    float mc_km_vx;
    float mc_km_vy;
    float mc_km_vz;

    float rc_el_px;
    float rc_el_py;
    float rc_el_pz;
    float rc_el_vx;
    float rc_el_vy;
    float rc_el_vz;

    float rc_pr_px;
    float rc_pr_py;
    float rc_pr_pz;
    float rc_pr_vx;
    float rc_pr_vy;
    float rc_pr_vz;

    float rc_kp_px;
    float rc_kp_py;
    float rc_kp_pz;
    float rc_kp_vx;
    float rc_kp_vy;
    float rc_kp_vz;

    float rc_km_px;
    float rc_km_py;
    float rc_km_pz;
    float rc_km_vx;
    float rc_km_vy;
    float rc_km_vz;

    /////////////////////////////////////////////////////////////////////////////////
    //BEVENT BASED ON CLAS6 - IS THIS BAD TO DO?  ( IT JUST WORKS SO NICELY! )
    //

    double start_time;// = null;
    double rf_value;// = null;


    Vector<Integer> charge = new Vector<Integer>();
    Vector<Double> px = new Vector<Double>();
    Vector<Double> py = new Vector<Double>();
    Vector<Double> pz = new Vector<Double>();
    Vector<Double> vz = new Vector<Double>();
    
    Vector<Integer> ecal_sect = new Vector<Integer>();
    Vector<Double> ecsf = new Vector<Double>();
    Vector<Double> pcal_e = new Vector<Double>();
    Vector<Double> ec_ei = new Vector<Double>();
    Vector<Double> ec_eo = new Vector<Double>();
    Vector<Double> htcc_nphe = new Vector<Double>();
    
    Vector<Integer> dcr1_sect = new Vector<Integer>();
    Vector<Double> dcr1_cx = new Vector<Double>();
    Vector<Double> dcr1_cy = new Vector<Double>();

    Vector<Integer> dcr2_sect = new Vector<Integer>();
    Vector<Double> dcr2_cx = new Vector<Double>();
    Vector<Double> dcr2_cy = new Vector<Double>();

    Vector<Integer> dcr3_sect = new Vector<Integer>();
    Vector<Double> dcr3_cx = new Vector<Double>();
    Vector<Double> dcr3_cy = new Vector<Double>();

    Vector<Double> pcal_x = new Vector<Double>();
    Vector<Double> pcal_y = new Vector<Double>();

    Vector<Double> clas12_beta = new Vector<Double>();
    Vector<Integer> scint_sector = new Vector<Integer>();
    Vector<Integer> scint_detector = new Vector<Integer>();
    Vector<Integer> scint_layer = new Vector<Integer>();
    Vector<Integer> scint_bar = new Vector<Integer>();

    Vector<Double> ftofl1_energy = new Vector<Double>();
    Vector<Double> ftofl1_path = new Vector<Double>();
    Vector<Double> ftofl1_tof = new Vector<Double>();

    Vector<Double> ftofl2_energy = new Vector<Double>();
    Vector<Double> ftofl2_path = new Vector<Double>();
    Vector<Double> ftofl2_tof = new Vector<Double>();

    public Map< Integer, List<Integer> > recCalBankMap = new HashMap<Integer, List<Integer> >();
    public Map< Integer, List<Integer> > recScintBankMap = new HashMap<Integer, List<Integer> >();
    public Map< Integer, List<Integer> > recTrackBankMap = new HashMap<Integer, List<Integer> >();
    public Map< Integer, List<Integer> > recCherenkovBankMap = new HashMap<Integer, List<Integer> >();


    public static int golden_electron_index;
    public static int golden_proton_index;
    public static int golden_kp_index;
    public static int golden_km_index;

    public static double total_accumulatedcharge; 
	

    public void setEvent( DataEvent event ){

	//System.out.println("CREATING EVENT");
	DataBank mcBank = event.getBank("MC::Particle");
	
	mc_el_px = mcBank.getFloat("px",0);
        mc_el_py = mcBank.getFloat("py",0);
	mc_el_pz = mcBank.getFloat("pz",0);
	
	mc_pr_px = mcBank.getFloat("px",1);
	mc_pr_py = mcBank.getFloat("py",1);
	mc_pr_pz = mcBank.getFloat("pz",1);
	
	mc_kp_px = mcBank.getFloat("px",2);
	mc_kp_py = mcBank.getFloat("py",2);
	mc_kp_pz = mcBank.getFloat("pz",2);
	
	mc_km_px = mcBank.getFloat("px",3);
	mc_km_py = mcBank.getFloat("py",3);
	mc_km_pz = mcBank.getFloat("pz",3);
	
        mc_el_vx = mcBank.getFloat("vx",0);
        mc_el_vy = mcBank.getFloat("vy",0);
	mc_el_vz = mcBank.getFloat("vz",0);
	
	mc_pr_vx = mcBank.getFloat("vx",1);
	mc_pr_vy = mcBank.getFloat("vy",1);
	mc_pr_vz = mcBank.getFloat("vz",1);
	
	mc_kp_vx = mcBank.getFloat("vx",2);
	mc_kp_vy = mcBank.getFloat("vy",2);
	mc_kp_vz = mcBank.getFloat("vz",2);
	
	mc_km_vx = mcBank.getFloat("vx",3);
	mc_km_vy = mcBank.getFloat("vy",3);
	mc_km_vz = mcBank.getFloat("vz",3);
	
	

	DataBank recBank = event.getBank("REC::Particle");
	//recBank.show();
	

    }
    

    public void loadBankMaps( ){


    }


    public void SetGoldenIndices( int tempel, int temppr, int tempkp, int tempkm ){
	SetElectronGoldenIndex(tempel);
	SetProtonGoldenIndex(temppr);
	SetKaonPlusGoldenIndex(tempkp);
	SetKaonMinusGoldenIndex(tempkm);
    }
   public void SetElectronGoldenIndex( int temp_el ){
	golden_electron_index = temp_el;
    }

   public  void SetProtonGoldenIndex( int temp_pr ){
	golden_proton_index = temp_pr;
    }

    public void SetKaonPlusGoldenIndex( int temp_kp ){
	golden_kp_index = temp_kp;
    }

   public  void SetKaonMinusGoldenIndex( int temp_km ){
	golden_km_index = temp_km;
    }

    public void setAccumulatedCharge( double temp_acharge ){
	total_accumulatedcharge = temp_acharge;
    }

    public static int GetElGoldenIndex(){
	return golden_electron_index;
    }
    public static int GetPrGoldenIndex(){
	return golden_proton_index;
    }
    public static int GetKPGoldenIndex(){
	return golden_kp_index;
    }
    public static int GetKMGoldenIndex(){
	return golden_km_index;
    }

    public static double getAccumulatedCharge(){
	return total_accumulatedcharge;
    }
}


//FOR DEBUGGING AND GENERAL USE 
/*
  System.out.println(" >> charge out test " + bevent.charge );
  System.out.println(" >> starttime  " + bevent.start_time );
  System.out.println(" >> rf " + bevent.rf_value );
  System.out.println(" >> q " + bevent.charge );
  System.out.println(" >> px " + bevent.px );
  System.out.println(" >> py " + bevent.py );
  System.out.println(" >> pz " + bevent.pz );
  System.out.println(" >> vz " + bevent.vz );
  System.out.println(" >> ecal_sect " + bevent.ecal_sect );
  System.out.println(" >> ecsf " + bevent.ecsf );
  System.out.println(" >> ec_ei " + bevent.ec_ei );
  System.out.println(" >> ec_eo " + bevent.ec_eo);
  System.out.println(" >> pcal_e " + bevent.pcal_e );
  System.out.println(" >> nphe " + bevent.htcc_nphe );
  System.out.println(" >> dcr1 s " + bevent.dcr1_sect );
  System.out.println(" >> dcr1 cx " + bevent.dcr1_cx );
  System.out.println(" >> dcr1 cy " + bevent.dcr1_cy );
  System.out.println(" >> dcr2 s " + bevent.dcr2_sect );
  System.out.println(" >> dcr2 cx " + bevent.dcr2_cx );
  System.out.println(" >> dcr2 cy " + bevent.dcr2_cy );
  System.out.println(" >> dcr3 s " + bevent.dcr3_sect );
  System.out.println(" >> dcr3 cx " + bevent.dcr3_cx );
  System.out.println(" >> dcr3 cy " + bevent.dcr3_cy );
  System.out.println(" >> pcal x " + bevent.pcal_x );
  System.out.println(" >> pcal y " + bevent.pcal_y );
  System.out.println(" >> clas12beta " + bevent.clas12_beta );
  System.out.println(" >> ftof1 energy " + bevent.ftofl1_energy );
  System.out.println(" >> ftof1 path " + bevent.ftofl1_path );
  System.out.println(" >> ftof1 tof " + bevent.ftofl1_tof );
  System.out.println(" >> ftof2 energy " + bevent.ftofl2_energy );
  System.out.println(" >> ftof2 path " + bevent.ftofl2_path );
  System.out.println(" >> ftof2 tof " + bevent.ftofl2_tof );
*/
		
	
