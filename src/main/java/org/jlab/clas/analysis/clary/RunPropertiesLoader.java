package org.jlab.clas.analysis.clary;

import java.io.*;
import com.google.gson.*;
import org.json.*;
import org.jlab.jnp.utils.json.*;
import org.jlab.clas.analysis.clary.RunInformation;

public class RunPropertiesLoader{

    static double beamEnergy;
    static double total_accumulated_charge;
    String run_number;
    RunInformation runinfo = new RunInformation();
    String analysis_type = null;

    public RunPropertiesLoader(int run, String analy_type){
	analysis_type = analy_type;
	loadRunProperties(run);
	setRunProperties();       
    }

    public void loadRunProperties(int run){
	run_number = "r" + Integer.toString(run);
	System.out.println(" >> LOADING JSON FILE FOR " + analysis_type + " ANALYSIS " );
	try{
	    Gson gson = new Gson();
	    BufferedReader br = new BufferedReader( new FileReader("/home/bclary/CLAS12/phi_analysis/v2/v1/run_db/CutDB_"+analysis_type+"_v3.json") );
	    //BufferedReader br = new BufferedReader( new FileReader("/w/hallb-scifs17exp/clas12/bclary/CutDB_v3.json") );
	    runinfo = gson.fromJson(br, RunInformation.class );
	}
	catch( IOException e ){
	    System.out.println(" >> ERROR LOADING CUT DB JSON FILE " );
	}	
    }


    public void writeRunProperties(){
	System.out.println(" >> WRITING TO JSON FILE FOR " + analysis_type + " ANALYSIS " );
	try{
	    Gson gson = new Gson();
	    String jsonstring = gson.toJson(runinfo);
	    FileWriter fileWriter = new FileWriter("/home/bclary/CLAS12/phi_analysis/v2/v1/run_db/CutDB_"+analysis_type+"_v3.json");
	    //FileWriter fileWriter = new FileWriter("/w/hallb-scifs17exp/clas12/bclary/CutDB_v3.json");
	    fileWriter.write(jsonstring);
	    fileWriter.close();
	}
	catch( IOException e ){
	    System.out.println(" >> ERROR WRITING TO JSON FILE " );
	}
    }

    public void addRun( String temprun ){

	System.out.println(" >> ADDING RUN " + temprun );
	runinfo.addRunToRunInfo(temprun);//, new RunParameters() );
 	//runinfo.getRun
    }

    public void setRunProperties(){
	setBeamEnergy();
    }


    public void setBeamEnergy(){
	beamEnergy = runinfo.getRunParameters().get(run_number).getBeamEnergy();
    }

    public void updateBeamEnergy( double temp ){
	runinfo.getRunParameters().get(run_number).setBeamEnergy(temp);
    }

    static public double getBeamEnergy(){
	return beamEnergy;	
    } 

    public void setTotalAccumulatedCharge( double temp_totcharge ){
	total_accumulated_charge = temp_totcharge;
    }

    static public double getTotalAccumulatedCharge(){
	return total_accumulated_charge;
    }

    public RunInformation getRunInfoClass(){
	return runinfo;
    }

    
}

