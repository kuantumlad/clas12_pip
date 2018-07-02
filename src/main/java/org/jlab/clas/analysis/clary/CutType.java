package org.jlab.clas.analysis.clary;

import com.google.gson.*;

import java.io.*;
import java.util.*;

import org.jlab.clas.analysis.clary.ECCutParameters;//CutDetectors;
import org.jlab.clas.analysis.clary.BetaCutParameters;//CutDetectors;
import org.jlab.clas.analysis.clary.VertexCutParameters;//CutDetectors;

public class CutType{

    int cutlvl;
    Map<String, VertexCutParameters> cut_vertex;// = new HashMap<String,VertexCutParameters>();
    Map<String, ECCutParameters> cut_ecdetectors;
    Map<String, BetaCutParameters> cut_betadetectors;
    
    public CutType(){
	//setVertexParameterMap();	

    }
   
    public void setCutLevel( int temp_cutlvl ){
	cutlvl = temp_cutlvl;       
    }

    public int getCutLevel(){
	return cutlvl;
    }

    public void setECParameterMap(){
	cut_ecdetectors = new HashMap<String,ECCutParameters>();
    }

    //new
    public void setBetaParameterMap(){
	cut_betadetectors = new HashMap<String,BetaCutParameters>();
    }

    public void setVertexParameterMap(){
	cut_vertex = new HashMap<String,VertexCutParameters>();
    }

    public Map<String, ECCutParameters> getECCutParameters() { //CutDetectors> getCutDetectors(){
	System.out.println(" >> getting sc sf shit " + cut_ecdetectors );
	return cut_ecdetectors;
    }
    
    //new
    public Map<String, BetaCutParameters> getBetaCutParameters() { //CutDetectors> getCutDetectors(){
	return cut_betadetectors;
    }

    public Map<String, VertexCutParameters> getVertexCutParameters() { //CutDetectors> getCutDetectors(){
	System.out.println(" >> GET VERTEXCUTPARAMETERS " + cut_vertex );
	return cut_vertex;
    }

    public void addECCutParameters(String temp_name){
 	cut_ecdetectors.put(temp_name, new ECCutParameters() );
    }

    //new
    public void addBetaCutParameters(String temp_name){
 	cut_betadetectors.put(temp_name, new BetaCutParameters() );
    }

    public void addVertexCutParameters(String temp_name){
 	cut_vertex.put(temp_name, new VertexCutParameters() );
    }

    public ECCutParameters getECCutParametersClass( String temp_name ){
	return cut_ecdetectors.get(temp_name);
    }

    //new
    public BetaCutParameters getBetaCutParametersClass( String temp_name ){
	return cut_betadetectors.get(temp_name);
    }

    public VertexCutParameters getVertexCutParametersClass( String temp_name ){
	return cut_vertex.get(temp_name);
    }

}
