package org.jlab.clas.analysis.clary;

import org.jlab.clas.analysis.clary.NegativeChargeCut;
import org.jlab.clas.analysis.clary.ECHitCut;
import org.jlab.clas.analysis.clary.FTOFHitCut;
import org.jlab.clas.analysis.clary.ECSamplingFractionCut;
import org.jlab.clas.analysis.clary.PCALHitCut;
import org.jlab.clas.analysis.clary.PhotoElectronCut;
import org.jlab.clas.analysis.clary.SectorMatchCut;
import org.jlab.clas.analysis.clary.DCFiducialCut;
import org.jlab.clas.analysis.clary.DCFiducialR2Cut;
import org.jlab.clas.analysis.clary.DCFiducialR3Cut;
import org.jlab.clas.analysis.clary.ECALInnerOuterCut;
import org.jlab.clas.analysis.clary.PCALFiducialCut;
import org.jlab.clas.analysis.clary.MinMomentumCut;
import org.jlab.clas.analysis.clary.VertexCut;
import org.jlab.clas.analysis.clary.MatchElectronPID;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.clas.physics.Particle;  
import org.jlab.clas.physics.LorentzVector;  

import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.jnp.hipo.schema.*;

import java.util.*;
import java.io.*;

public class ElectronPID implements IParticleIdentifier{

    Vector<BICandidate> v_cuts = new Vector<BICandidate>();
    boolean status = false;

    NegativeChargeCut charge_cut = new NegativeChargeCut();
    DCFiducialCut dcr1_fiducial_cut = new DCFiducialCut();
    DCFiducialR2Cut dcr2_fiducial_cut = new DCFiducialR2Cut();
    DCFiducialR3Cut dcr3_fiducial_cut = new DCFiducialR3Cut();
    PCALFiducialCut pcal_fiducial_cut = new PCALFiducialCut();
    ECHitCut echit_cut = new ECHitCut();
    FTOFHitCut ftof_cut = new FTOFHitCut();
    ECSamplingFractionCut ecsf_cut = new ECSamplingFractionCut();
    PCALHitCut pcalhit_cut = new PCALHitCut();
    PhotoElectronCut nphe_cut = new PhotoElectronCut();
    SectorMatchCut sectormatch_cut = new SectorMatchCut();
    ECALInnerOuterCut ec_eieo_cut = new ECALInnerOuterCut();
    MinMomentumCut minmomentum_cut = new MinMomentumCut();
    VertexCut vz_cut = new VertexCut();
    MatchElectronPID eb_pid_match = new MatchElectronPID();

    SchemaFactory factory;

    Map<String, BICandidate > m_el_cut_dir = new HashMap<String, BICandidate>();
    List<String> l_el_cut_names;// = new List<String>();
    Vector<BICandidate> v_el_cut_inputs = new Vector<BICandidate>();

    public ElectronPID() {

	factory = new SchemaFactory();
	factory.initFromDirectory("CLAS12DIR","etc/bankdefs/hipo");

	initializeCuts();

    }

    public ElectronPID( List<String> l_el_cuts ){

	l_el_cut_names = l_el_cuts;
	initializeCuts();
    }

    public void setElectronCutMap(){

	m_el_cut_dir.put("negative_charge", charge_cut);
	m_el_cut_dir.put("pcal_fiducial", pcal_fiducial_cut);
	m_el_cut_dir.put("dcr1_fiducial", dcr1_fiducial_cut);
	m_el_cut_dir.put("dcr2_fiducial", dcr2_fiducial_cut);
	m_el_cut_dir.put("dcr3_fiducial", dcr3_fiducial_cut);
	m_el_cut_dir.put("ecsf", ecsf_cut);
	m_el_cut_dir.put("ftof_hit", ftof_cut);
	m_el_cut_dir.put("htcc_nphe", nphe_cut);
	m_el_cut_dir.put("min_mntm", minmomentum_cut);
	m_el_cut_dir.put("min_pcal_energy", pcalhit_cut);
	m_el_cut_dir.put("vz_pos", vz_cut);
	m_el_cut_dir.put("track_quality",sectormatch_cut);
	m_el_cut_dir.put("eb_pid_match",eb_pid_match);

				
    }

    public void initializeCuts(){

	setElectronCutMap();
	for( String cut_name : l_el_cut_names ){	    
	    System.out.println(" >> " + cut_name );
	    v_cuts.add( m_el_cut_dir.get(cut_name) );
	}
	System.out.println(" >> SIZE OF PERSONALIZED VECTOR " + v_el_cut_inputs.size() );
	

	////////////////////
	//GEOMETRY CUTS
	/*v_cuts.add(charge_cut);
	v_cuts.add(dcr1_fiducial_cut);
	v_cuts.add(dcr2_fiducial_cut);
	v_cuts.add(dcr3_fiducial_cut);
	v_cuts.add(pcal_fiducial_cut);
	//v_cuts.add(sectormatch_cut); -- ADD AFTER HELPING NICK

	////////////////////
	//TRIGGER CUT
	v_cuts.add(ftof_cut);
 	
	//////////////
	//ENERGY CUTS
	v_cuts.add(ecsf_cut);
	v_cuts.add(nphe_cut);
	v_cuts.add(minmomentum_cut);
	v_cuts.add(pcalhit_cut);
	v_cuts.add(vz_cut);
	*/
    }

    public boolean processCuts( DataEvent tempevent, int rec_i ){
	//System.out.println(" >> IN PROCESS CUTS BOOLEAN ");
	boolean result = true;
	int j = 0;
	for( BICandidate cut : v_cuts ){
	    //System.out.println(" >> CUT " + j + " RESULT " + cut.candidate( tempevent, rec_i ) );
	    if( !cut.candidate( tempevent, rec_i ) ){
		result = false;
		//System.out.println(">> CUT NUM " + j + " FALSE ");
		break;
	    }
	    j++;
	}
	return result;
    }


    public Vector<Boolean> processCutsVector( DataEvent tempevent, int rec_i ){
	//System.out.println(" >> IN PROCESS CUTS VECTOR " );
	Vector<Boolean> v_results = new Vector<Boolean>();
	v_results.clear();
	int i = 0;
	for( BICandidate cut : v_cuts ){
	    v_results.add(cut.candidate(tempevent, rec_i ));
	    i++;
	    //System.out.println( " CUT VECTOR RESULT " + i + " "  + cut.candidate(tempevent, rec_i ) );
	}
	return v_results;

    } 

    public boolean processCutsVectorResults( Vector<Boolean> v_tempcuts ){
	
	boolean outcome = false;
	for( boolean results : v_tempcuts ){
	    if( !results ){
		outcome = false;
		break;
		//return false;
	    }
	    else if( results ){
		outcome = true;
	    }	    
	}
	return outcome;

    }

    public HashMap getResult( DataEvent event ){

	HashMap<Boolean, Integer> m_final_el = new HashMap<Boolean, Integer>();

	double energy = 0.0;
	int fast_index = -1;
	boolean el_test = false;
	boolean final_test = false;
	if( event.hasBank("REC::Particle")  ){		
	    DataBank recBank = event.getBank("REC::Particle");		
	    //System.out.println(" >> NUMBER OF ROWS " + event.getBank("REC::Particle").rows());
	    for( int k = 0; k < event.getBank("REC::Particle").rows(); k++ ){ 
		int status = recBank.getShort("status",k);
		if( status >= 4000 || status <= 1999 ) continue;

		el_test = processCuts(event, k);
		//System.out.println(" >> k " + k + " TEST RESULT " + el_test );
		if( el_test ){		   
		    double energy_new = Calculator.lv_energy(recBank, k, 11 );
		    //System.out.println(" >> " + energy_new + " OLD " + energy );
		    if( energy_new > energy ){
			energy = energy_new;
			final_test = el_test;
			fast_index = k;
			//System.out.println(" >> ENERGY RESULT " + " " + el_test + " " + fast_index  );
		    }
		}
	    }
	}
	//System.out.println(" >> FAST ELECTRON RESULT " + final_test + " " +fast_index + " " + energy);
	m_final_el.put( final_test, fast_index);
	    
	return m_final_el;
    }

    public Particle getParticle( DataEvent event ){

	//HashMap<Boolean, Particle> m_final_el = new HashMap<Boolean, Particle>();
	//System.out.println(" >> > " + final_particle.getProperty("pid" ));
	Particle particle = new Particle();

	double energy = 0.0;
	int fast_index = -1;
	boolean el_test = false;
	boolean final_test = false;
	if( event.hasBank("REC::Particle")  ){		
	    DataBank recBank = event.getBank("REC::Particle");		
	    //System.out.println(" >> NUMBER OF ROWS " + event.getBank("REC::Particle").rows());
	    for( int k = 0; k < event.getBank("REC::Particle").rows(); k++ ){ 
		el_test = processCuts(event, k);
		//System.out.println(" >> k " + k + " " + el_test );
		if( el_test ){		   
		    double energy_new = Calculator.lv_energy(recBank, k, 11 );
		    LorentzVector lv_new = Calculator.lv_particle(recBank, k, 11 );
		    if( energy_new > energy ){
			energy = energy_new;
			final_test = el_test;
			fast_index = k;
			//System.out.println(" >> " + " " + el_test + " " + fast_index  );
			Particle final_particle = new Particle(11,lv_new.px(),lv_new.py(),lv_new.pz(),recBank.getFloat("vx",fast_index), recBank.getFloat("vy",fast_index), recBank.getFloat("vz",fast_index));
			final_particle.setProperty("pid",11);
			final_particle.setProperty("pindex",fast_index );
			final_particle.setProperty("px",lv_new.px());
			final_particle.setProperty("py",lv_new.py());
			final_particle.setProperty("pz",lv_new.pz());
			final_particle.setProperty("vx",recBank.getFloat("vx",fast_index));
			final_particle.setProperty("vy",recBank.getFloat("vy",fast_index));
			final_particle.setProperty("vz",recBank.getFloat("vz",fast_index));
			particle = final_particle;
		    }
		}
	    }
	}
	//System.out.println(">> FINAL PARTICLE " + particle);
	//System.out.println(" >> final result " + final_test + " " +fast_index);
		    //m_final_el.put( el_test, fast_index);	    
	return particle;
    }
    
}
