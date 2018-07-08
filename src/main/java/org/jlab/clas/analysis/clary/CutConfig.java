package org.jlab.clas.analysis.clary;

import java.io.*;
import com.google.gson.*;
import org.json.*;
import org.jlab.jnp.utils.json.*;
import org.jlab.clas.analysis.clary.CutConfigInfo;
import java.util.List;

public class CutConfig{

    CutConfigInfo cutconfig = new CutConfigInfo();


    CutConfig(){
	//Empty Constructor
    }

    CutConfig( String temp_file ){

	loadCutConfig( temp_file );

    }

    public void loadCutConfig(String temp_file){

	try{
	    Gson gson = new Gson();
	    BufferedReader br = new BufferedReader( new FileReader(temp_file) ); //"/u/home/bclary/CLAS12/phi_analysis/v2/v1/run_db/cut_config.json") );
	    cutconfig = gson.fromJson(br, CutConfigInfo.class );
	}
	catch( IOException e ){
	    System.out.println(" >> ERROR LOADING CUT DB JSON FILE " );
	}	
    }
    

    public CutConfigInfo getCutConfigInfoClass(){
	return cutconfig;
    }


    public List<String> getElectronCuts(){

	return cutconfig.getElectronCutList();

    }

    public List<String> getProtonCuts(){

	return cutconfig.getProtonCutList();

    }

   

    
}
