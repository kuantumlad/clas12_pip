package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import org.jlab.io.hipo.HipoDataSource;
import org.jlab.jnp.hipo.io.HipoWriter;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.clas.physics.Particle;  
import org.jlab.clas.physics.LorentzVector;  
       
System.out.println(" >> TEST ELECTRONPID ");

int run_number = 4031;
String in_file = INSERT FILE PATH HERE;

//SET RUN PROPERTIES - VERY IMPORTANT
RunPropertiesLoader run_properties = new RunPropertiesLoader(run_number,"DATA");

//SET CUT VALUES - CRITICAL, IF NOT DONE THE CODE WILL CRASH ON PURPOSE
CutLoader cut_loader = new CutLoader(run_number,"DATA","cut_nom");

ElectronPID find_el = new ElectronPID();

HipoDataSource reader = new HipoDataSource();

reader.open(in_file);
DataEvent nullevent;// = 0;

while( reader.hasEvent() ){

       DataEvent event = reader.getNextEvent();
       // CAN GET EITHER A MAP WITH INDEX AND TRUTH VALUE OF CANDIDATE 
       // OR A PARTICLE AFTER FINDING THE GOOD ELECTRON CANDIDATE
       
       HashMap<Boolean, Integer> m_el_result = find_el.getResult(event);
       //Particle final_electron = find_el.getParticle(event);
       System.out.println(">> " +  m_el_result );       


       // DO WHATEVER YOU WANT TO DO HERE     
}



