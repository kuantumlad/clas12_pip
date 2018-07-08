package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;
import org.jlab.io.hipo.*;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.io.hipo.HipoDataSource;
import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.clas.physics.Particle;

public class pip_example{

    public static void main(String[] args) {


  	String s_run = args[0];
	String dataOrSim_type = args[1];
	String fdir = args[2];
	int run_number = Integer.valueOf(s_run);

	RunPropertiesLoader run_properties;

	String my_pid_cuts = "/home/bclary/CLAS12/phi_analysis/v3/v2/v1/run_db/cut_config.json";
	CutConfig cutconfig = new CutConfig(my_pid_cuts);
 	List<String> my_el_cuts = cutconfig.getElectronCuts();

	System.out.println(" >> " + cutconfig.getElectronCuts());	
	ElectronPID find_el = new ElectronPID(my_el_cuts);
	
	run_properties = new RunPropertiesLoader(run_number,dataOrSim_type);

	CutLoader cut_loader = new CutLoader(run_number,dataOrSim_type,"cut_nom");	

	String file_to_skim = FILE_NAME_HERE; 
	File file = new File(file_to_skim);

	HipoDataSource reader = new HipoDataSource();
	reader.open(file);
	    
	int evnum=0;
	while(reader.hasEvent()){
	    DataEvent event = reader.gotoEvent(evnum);
	    evnum++;                           

	    boolean el_present = false;
		
	    HashMap<Boolean,Integer> m_final_el = find_el.getResult(event);
	    //System.out.println(" >> FINAL RESULT FROM MAP " + m_final_el);    
	    for( Map.Entry<Boolean,Integer> entry : m_final_el.entrySet() ){
		boolean el_test_result = entry.getKey();		      
		int el_result_index = entry.getValue(); 
		if( el_test_result ){
		    el_present = el_test_result;
		}
	    }


	    Particle el_particle_candidate = find_el.getParticle(event);
	    int pindex = el_particle_candidate.getProperty("pindex"); // IF < 0 THEN EVENT HAS NOT TRIGGER ELECTRON
	    //DO SOMETHIN WITH PARTICLE

	}
    }
}

