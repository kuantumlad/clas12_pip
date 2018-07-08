package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.clas.physics.Particle; 

import java.io.*;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;

public class MLEPion{
    
    Vector<BIParticleCandidate> v_mle = new Vector<BIParticleCandidate>();
    
    Particle pion_candidate = new Particle();
    MLEPionPlusBeta mle_p_beta = new MLEPionPlusBeta();

    DCFiducialCutR1Positive hard_dcr1 = new DCFiducialCutR1Positive();
    DCFiducialR2CutPositive hard_dcr2 = new DCFiducialR2CutPositive();
    DCFiducialR3CutPositive hard_dcr3 = new DCFiducialR3CutPositive();

    MLEPionMinusBeta mle_pm_beta = new MLEPionMinusBeta();

    DCFiducialCutR1Negative hard_dcr1_neg = new DCFiducialCutR1Negative();
    DCFiducialR2CutNegative hard_dcr2_neg = new DCFiducialR2CutNegative();
    DCFiducialR3CutNegative hard_dcr3_neg = new DCFiducialR3CutNegative();


    public MLEPion(){


    }

    public void initializeLikelihoods(String temp_charge){
	if( temp_charge == "pos"){
	    v_mle.add(mle_p_beta);
	    v_mle.add(hard_dcr1);
	    v_mle.add(hard_dcr2);
	    v_mle.add(hard_dcr3);
	}
	if( temp_charge == "neg"){
	    v_mle.add(mle_pm_beta);
	    v_mle.add(hard_dcr1_neg);
 	    v_mle.add(hard_dcr2_neg);
	    v_mle.add(hard_dcr3_neg);
	}

    }

    public List<Double> calcTotalPionLikelihood( BEventInfo bev, int rec_i ){
	
	List<Double> mle_info = new ArrayList<Double>();

	double final_likelihood = 1.0;
	double final_conflvl = 1.0;
	for( BIParticleCandidate candidate : v_mle ){	   
	    Particle temp_part = candidate.particleCandidate(bev, rec_i );

	    final_likelihood*=temp_part.getProperty("likelihood");
	    final_conflvl *= temp_part.getProperty("conflvl");
	}
	//System.out.println(" >> final PION LIKELIHOOD IS " + final_likelihood );
	
	mle_info.add(final_likelihood);
	mle_info.add(final_conflvl);

	return mle_info;
    }

    public void setMLEParticleProperties( BEventInfo bev, int rec_i, int pdg_pid ){
	      
	List<Double> mle_info = calcTotalPionLikelihood( bev, rec_i);

	pion_candidate.setProperty("pid", pdg_pid);
	pion_candidate.setProperty("pindex", rec_i );
	pion_candidate.setProperty("likelihood", mle_info.get(0));
	pion_candidate.setProperty("conflvl", mle_info.get(1));
	      
    }

    public Particle getMLEPionParticle(){
	return pion_candidate;
    }

    public Vector<BIParticleCandidate> getMLECuts(){
	return v_mle;
    }
              

}
