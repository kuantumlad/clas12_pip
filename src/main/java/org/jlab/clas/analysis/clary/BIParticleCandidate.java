package org.jlab.clas.analysis.clary;                                                                                                                                                                                                                                           
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank; 
import org.jlab.clas.physics.Particle;

import java.io.*;

interface BIParticleCandidate{

    public Particle particleCandidate( BEventInfo temp_bev, int rec_i );
    

}
