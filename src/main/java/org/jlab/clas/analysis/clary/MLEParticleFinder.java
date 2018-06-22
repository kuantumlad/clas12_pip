package org.jlab.clas.analysis.clary;                                                                                                                                                                     import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank; 
import org.jlab.clas.physics.Particle;

import java.util.*;
import java.io.*;

public class MLEParticleFinder{

    private Map<Integer, Double> m_mle_particle = new LinkedHashMap<Integer,Double>();
    
    MLEProton mle_proton = new MLEProton();
    MLEKaon mle_kaon = new MLEKaon();
    MLEPion mle_pion = new MLEPion();

    public MLEParticleFinder(){
	initializeMLEParticleFinder();

    }


    private void initializeMLEParticleFinder(){

	System.out.println(" >> INIT MLE CUTS " );
	mle_proton.initializeLikelihoods();
	mle_kaon.initializeLikelihoods();
	mle_pion.initializeLikelihoods();

    }
    
    private void setMLEParticle( BEventInfo bev, int rec_i ){

	m_mle_particle.clear();

	mle_proton.setMLEParticleProperties( bev, rec_i );
	mle_kaon.setMLEParticleProperties( bev, rec_i );
	mle_pion.setMLEParticleProperties( bev, rec_i );

	m_mle_particle.put(2212, mle_proton.getMLEProtonParticle().getProperty("likelihood") );
	m_mle_particle.put(321, mle_kaon.getMLEKaonParticle().getProperty("likelihood")  );
	m_mle_particle.put(211, mle_pion.getMLEPionParticle().getProperty("likelihood")  );
	
	//System.out.println(" >> MAP OF HADRONS " + m_mle_particle);

    }


    private int sortMLEParticles(){

 	Map<Integer, Double> result =  new LinkedHashMap<Integer,Double>();
         m_mle_particle.entrySet().stream()
	     .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
	    .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));

	return result.entrySet().iterator().next().getKey();
    }

    

    public Particle getMLEParticle( BEventInfo bev, int rec_i ){

	Particle mle_particle = new Particle();
	setMLEParticle( bev, rec_i );
	int mle_pid = sortMLEParticles();

	switch( mle_pid ){
	case 2212:
	    mle_particle = mle_proton.getMLEProtonParticle();
	    break;
	case 211:
	    mle_particle = mle_pion.getMLEPionParticle();
	    break;
	case 321:
	    mle_particle = mle_kaon.getMLEKaonParticle();
	    break;
	default: 
	    mle_particle = null;
	    break;
	}
	
	return mle_particle;

    }

    public Particle getProton( BEventInfo bev, int rec_i ){	
	mle_proton.setMLEParticleProperties( bev, rec_i );
	return mle_proton.getMLEProtonParticle();
    }

    public Particle getKaonPlus( BEventInfo bev, int rec_i ){
	mle_kaon.setMLEParticleProperties( bev, rec_i );
	return mle_kaon.getMLEKaonParticle();
    }

    public Particle getPionPlus( BEventInfo bev, int rec_i ){
	mle_pion.setMLEParticleProperties( bev, rec_i );
	return mle_pion.getMLEPionParticle();

    }

}
