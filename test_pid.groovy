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

int run_number = 3050;
String in_file = "/lustre/expphy/volatile/halla/sbs/bclary/clas12Analysis/SKIMclas12/skim_003050_pass1/skim_clas_003050.evio.1.hipo"

RunPropertiesLoader run_properties = new RunPropertiesLoader();
run_properties.loadRunProperties(run_number);
run_properties.setRunProperties();

CutLoader cut_loader = new CutLoader();
cut_loader.loadRunCuts(run_number,"cut_nom");
cut_loader.setRunCuts();
cut_loader.printRunCuts();

HipoDataSource reader = new HipoDataSource();

ElectronPID find_el = new ElectronPID();
find_el.initializeCuts();

reader.open(in_file);
DataEvent nullevent;// = 0;

while( reader.hasEvent() ){

       DataEvent event = reader.getNextEvent();
       //HashMap<Boolean, Integer> m_el_result = find_el.getResult(event);
       Particle final_electron = find_el.getParticle(event);
       //System.out.println(">> " +  m_el_result );       
}



