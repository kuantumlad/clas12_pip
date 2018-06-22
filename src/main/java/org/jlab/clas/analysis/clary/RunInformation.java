package org.jlab.clas.analysis.clary;
import com.google.gson.*;

import org.jlab.clas.analysis.clary.RunParameters;
import java.io.*;
import java.util.*;

public class RunInformation{

    RunParameters runpar = new RunParameters();

    public Map<String,RunParameters> run_info;

    public RunInformation(){


    }

    public Map<String,RunParameters> getRunParameters(){
	return run_info;
    }

    public RunParameters getRunParametersClass( String temp_runnumber){
	return run_info.get(temp_runnumber);
    }

    public void addRunToRunInfo( String temp_runnumber ){
 	run_info.put(temp_runnumber,  new RunParameters() );		
    }

    

}
