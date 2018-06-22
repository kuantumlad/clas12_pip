package org.jlab.clas.analysis.clary;

import java.io.*;
import com.google.gson.*;
import org.json.*;
import org.jlab.jnp.utils.json.*;
import org.jlab.clas.analysis.clary.CutConfigInfo;


public class CutConfig{

    CutConfigInfo cutconfig = new CutConfigInfo();


    public void loadCutConfig(){

	try{
	    Gson gson = new Gson();
	    BufferedReader br = new BufferedReader( new FileReader("/u/home/bclary/CLAS12/phi_analysis/v2/v1/run_db/cut_config.json") );
	    cutconfig = gson.fromJson(br, CutConfigInfo.class );
	}
	catch( IOException e ){
	    System.out.println(" >> ERROR LOADING CUT DB JSON FILE " );
	}	
    }
    

    public CutConfigInfo getCutConfigInfoClass(){
	return cutconfig;
    }

   

    
}
