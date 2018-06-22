package org.jlab.clas.analysis.clary;
import com.google.gson.*;

import java.io.*;
import java.util.*;

import org.jlab.clas.analysis.clary.CutType;

public class RunParameters{

    int run_number;
    double beam_energy;
    String target;
    double current;
    public Map<String,CutType> cut_type;

    public RunParameters(){
	
    }

    public int getRunNumber(){
	return run_number;
    }

    public void setRunNumber( int temp_runnumber ){
	run_number = temp_runnumber;
    }

    public double getBeamEnergy(){
	return beam_energy;
    }

    public void setBeamEnergy( double temp_beamenergy ){
	beam_energy = temp_beamenergy;
    }

    public String getTarget(){
	return target;
    }

    public void setTarget(String temp_target){
	target = temp_target;
    }

    public double getCurrent(){
	return current;
    }

    public void setCurrent(double temp_current){
	current = temp_current;
    }

    public void setCutTypeMap() {
	cut_type = new HashMap<String,CutType>();
    }

    public Map<String, CutType> getCutType(){
    	return cut_type;
    }

    public CutType getCutTypeClass( String temp_cuttype ){
	return cut_type.get(temp_cuttype);
    }

    public void addCutLevelToCutType( String temp_cutlvl ){
	cut_type.put( temp_cutlvl, new CutType() );
    }

     
    

}
