package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.clas.physics.Particle; 

import java.io.*;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;

public class MLEKaon{
    
    Vector<BIParticleCandidate> v_mle = new Vector<BIParticleCandidate>();
    
    Particle kaon_candidate = new Particle();
    MLEKaonPlusBeta mle_k_beta = new MLEKaonPlusBeta();
    DCFiducialCutR1Positive hard_dcr1 = new DCFiducialCutR1Positive();
    DCFiducialR2CutPositive hard_dcr2 = new DCFiducialR2CutPositive();
    DCFiducialR3CutPositive hard_dcr3 = new DCFiducialR3CutPositive();

    public MLEKaon(){


    }

    public void initializeLikelihoods(){
	v_mle.add(mle_k_beta);
	v_mle.add(hard_dcr1);
	v_mle.add(hard_dcr2);
	v_mle.add(hard_dcr3);

    }

    public List<Double> calcTotalKaonLikelihood( BEventInfo bev, int rec_i ){
	
	List<Double> mle_info = new ArrayList<Double>();

	double final_likelihood = 1.0;
	double final_conflvl = 1.0;
	for( BIParticleCandidate candidate : v_mle ){	   
	    Particle temp_part = candidate.particleCandidate(bev, rec_i );

	    final_likelihood*=temp_part.getProperty("likelihood");
	    final_conflvl *= temp_part.getProperty("conflvl");
	}
	//System.out.println(" >> final KAON LIKELIHOOD IS " + final_likelihood );
	
	mle_info.add(final_likelihood);
	mle_info.add(final_conflvl);

	return mle_info;
    }

    public void setMLEParticleProperties( BEventInfo bev, int rec_i ){
	      
	List<Double> mle_info = calcTotalKaonLikelihood( bev, rec_i);

	kaon_candidate.setProperty("pid",321);
	kaon_candidate.setProperty("pindex", rec_i );
	kaon_candidate.setProperty("likelihood",mle_info.get(0));
	kaon_candidate.setProperty("conflvl",mle_info.get(1));
	      
    }

    public Particle getMLEKaonParticle(){
	return kaon_candidate;
    }

    public Vector<BIParticleCandidate> getMLECuts(){
	return v_mle;
    }
              

}
