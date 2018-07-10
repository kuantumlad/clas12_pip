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

    int charge_type;
    int proton_pid;
    int pion_pid;
    int kaon_pid;

    public MLEParticleFinder(String particle_charge ){
	if( particle_charge == "pos" ){
	    System.out.println(" >> INITIALIZE CUTS FOR POSITION HADRON PARTICLES ");
	    charge_type = 1;
	    proton_pid = PhysicalConstants.protonID;
	    pion_pid = PhysicalConstants.pionplusID;
	    kaon_pid = PhysicalConstants.kaonplusID;
	}
	if( particle_charge == "neg" ){
	    System.out.println(" >> INITIALIZE CUTS FOR NEGATIVE HADRON PARTICLES ");
	    proton_pid = PhysicalConstants.protonID; // BASE RESPONSE FOR PARTICLE
	    pion_pid = PhysicalConstants.pionminusID;
	    kaon_pid = PhysicalConstants.kaonminusID;
	    charge_type = -1;
	}
	
	initializeMLEParticleFinder(particle_charge);

    }

    private void initializeMLEParticleFinder(String particle_charge){

	System.out.println(" >> INIT MLE CUTS " );
	mle_proton.initializeLikelihoods();
	mle_kaon.initializeLikelihoods(particle_charge);
	mle_pion.initializeLikelihoods(particle_charge);

    }
    
    private void setMLEParticle( BEventInfo bev, int rec_i ){

	m_mle_particle.clear();

	mle_proton.setMLEParticleProperties( bev, rec_i );
	if( charge_type == 1 ){
	    mle_kaon.setMLEParticleProperties( bev, rec_i, PhysicalConstants.kaonplusID );
	    mle_pion.setMLEParticleProperties( bev, rec_i, PhysicalConstants.pionplusID );
	}
	else if( charge_type == -1 ){
	    mle_kaon.setMLEParticleProperties( bev, rec_i, PhysicalConstants.kaonminusID );
	    mle_pion.setMLEParticleProperties( bev, rec_i, PhysicalConstants.pionminusID );
	}

	m_mle_particle.put(2212, mle_proton.getMLEProtonParticle().getProperty("likelihood"));
	m_mle_particle.put(charge_type*321, mle_kaon.getMLEKaonParticle().getProperty("likelihood"));
	m_mle_particle.put(charge_type*211, mle_pion.getMLEPionParticle().getProperty("likelihood"));
	
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


	/*	switch( mle_pid ){
	case proton_pid:
	    mle_particle = mle_proton.getMLEProtonParticle();
	    break;
	case pion_pid:
	    mle_particle = mle_pion.getMLEPionParticle();
	    break;
	case kaon_pid:
	    mle_particle = mle_kaon.getMLEKaonParticle();
	    break;
	default: 
	    mle_particle = null;
	    break;
	}
	*/

	if( mle_pid == proton_pid ){
	    mle_particle = mle_proton.getMLEProtonParticle();
	}
	else if( mle_pid == pion_pid ){
	    mle_particle = mle_pion.getMLEPionParticle();
	}
	else if( mle_pid == kaon_pid ){
	    mle_particle = mle_kaon.getMLEKaonParticle();
	}

	
	return mle_particle;

    }

    public Particle getProton( BEventInfo bev, int rec_i ){	
	mle_proton.setMLEParticleProperties( bev, rec_i );
	return mle_proton.getMLEProtonParticle();
    }

    public Particle getKaonPlus( BEventInfo bev, int rec_i ){
	mle_kaon.setMLEParticleProperties( bev, rec_i, PhysicalConstants.kaonplusID );
	return mle_kaon.getMLEKaonParticle();
    }

    public Particle getPionPlus( BEventInfo bev, int rec_i ){
	mle_pion.setMLEParticleProperties( bev, rec_i, PhysicalConstants.pionplusID );
	return mle_pion.getMLEPionParticle();
    }
   
    public Particle getKaonMinus( BEventInfo bev, int rec_i ){
	mle_pion.setMLEParticleProperties( bev, rec_i, PhysicalConstants.kaonminusID);
	return mle_kaon.getMLEKaonParticle();
    }

    public Particle getPionMinus( BEventInfo bev, int rec_i ){
	mle_pion.setMLEParticleProperties( bev, rec_i, PhysicalConstants.pionminusID);
	return mle_pion.getMLEPionParticle();
    }
 
}
