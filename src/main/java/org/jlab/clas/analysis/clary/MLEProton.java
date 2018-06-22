package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.clas.physics.Particle; 

import java.io.*;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;

public class MLEProton{
    
    Vector<BIParticleCandidate> v_mle = new Vector<BIParticleCandidate>();
    
    Particle proton_candidate = new Particle();
    MLEProtonBeta mle_pr_beta = new MLEProtonBeta();

    DCFiducialCutR1Positive hard_dcr1 = new DCFiducialCutR1Positive();
    DCFiducialR2CutPositive hard_dcr2 = new DCFiducialR2CutPositive();
    DCFiducialR3CutPositive hard_dcr3 = new DCFiducialR3CutPositive();

    public MLEProton(){
	
    }

    //put in constructor 
    public void initializeLikelihoods(){
	v_mle.add(mle_pr_beta);
	v_mle.add(hard_dcr1);
	v_mle.add(hard_dcr2);
	v_mle.add(hard_dcr3);

    }

    public List<Double> calcTotalProtonLikelihood( BEventInfo bev, int rec_i ){
	
	List<Double> mle_info = new ArrayList<Double>();

	double final_likelihood = 1.0;
	double final_conflvl = 1.0;
	//System.out.println(" >> CALCULATING PROTON LIKELIHOOD ");
	for( BIParticleCandidate candidate : v_mle ){	   
	    Particle temp_part = candidate.particleCandidate(bev, rec_i );
	    //System.out.println(" >> PR L: " + temp_part.getProperty("pr_likelihood") + " " + temp_part.getProperty("pr_conflvl") );

	    final_likelihood*=temp_part.getProperty("likelihood");
	    final_conflvl *= temp_part.getProperty("conflvl");
	}

	//System.out.println(" >> final PROTON LIKELIHOOD IS " + final_likelihood );
	
	mle_info.add(final_likelihood);
	mle_info.add(final_conflvl);

	return mle_info;
    }

    public void setMLEParticleProperties( BEventInfo bev, int rec_i ){
	      
	List<Double> mle_info = calcTotalProtonLikelihood( bev, rec_i);

	//SET PID IN PARTICLE CLASS
	proton_candidate.setProperty("pid",2212);
	proton_candidate.setProperty("pindex", rec_i );
	proton_candidate.setProperty("likelihood",mle_info.get(0));
	proton_candidate.setProperty("conflvl",mle_info.get(1));
	      
    }

    public Particle getMLEProtonParticle(){
	return proton_candidate;
    }

    public Vector<BIParticleCandidate> getMLECuts(){
	return v_mle;
    }
              

}
