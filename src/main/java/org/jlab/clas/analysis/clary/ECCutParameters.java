package org.jlab.clas.analysis.clary;

import com.google.gson.*;

import java.io.*;
import java.util.*;

public class ECCutParameters{

    String cut_name;
    List<Double> ec_max_fit_values_sector_1;
    List<Double> ec_min_fit_values_sector_1;
    List<Double> ec_max_fit_values_sector_2;
    List<Double> ec_min_fit_values_sector_2;
    List<Double> ec_max_fit_values_sector_3;
    List<Double> ec_min_fit_values_sector_3;
    List<Double> ec_max_fit_values_sector_4;
    List<Double> ec_min_fit_values_sector_4;
    List<Double> ec_max_fit_values_sector_5;
    List<Double> ec_min_fit_values_sector_5;
    List<Double> ec_max_fit_values_sector_6;
    List<Double> ec_min_fit_values_sector_6;

    public ECCutParameters(){ //Detectors(){
	
	System.out.println(" EC CUT PARAMETERS CONSTRUCTOR " );

    }

    
    public String getCutName(){
	return cut_name;
    }
    
    public void setCutName(String temp_name){
	System.out.println(" >> SETTING CUT NAME " + temp_name);
	cut_name = temp_name;
    }

    public List<Double> getMaxFitParametersSector1(){
	return ec_max_fit_values_sector_1;
    }
    public List<Double> getMinFitParametersSector1(){
	return ec_min_fit_values_sector_1;
    }
    public List<Double> getMaxFitParametersSector2(){
	return ec_max_fit_values_sector_2;
    }
    public List<Double> getMinFitParametersSector2(){
	return ec_min_fit_values_sector_2;
    }
    public List<Double> getMaxFitParametersSector3(){
	return ec_max_fit_values_sector_3;
    }
    public List<Double> getMinFitParametersSector3(){
	return ec_min_fit_values_sector_3;
    }
    public List<Double> getMaxFitParametersSector4(){
	return ec_max_fit_values_sector_4;
    }
    public List<Double> getMinFitParametersSector4(){
	return ec_min_fit_values_sector_4;
    }
    public List<Double> getMaxFitParametersSector5(){
	return ec_max_fit_values_sector_5;
    }
    public List<Double> getMinFitParametersSector5(){
	return ec_min_fit_values_sector_5;
    }
    public List<Double> getMaxFitParametersSector6(){
	return ec_max_fit_values_sector_6;
    }
    public List<Double> getMinFitParametersSector6(){
	return ec_min_fit_values_sector_6;
    }

    //////////////////////////////////////////////////////////
    //SETTERS
    public void setMaxFitParametersSector1( List<Double> temp_l ){
	ec_max_fit_values_sector_1 = temp_l;
    }
    public void setMinFitParametersSector1( List<Double> temp_l ){
	ec_min_fit_values_sector_1 = temp_l;
    }
    public void setMaxFitParametersSector2( List<Double> temp_l ){
	ec_max_fit_values_sector_2 = temp_l;
    }
    public void setMinFitParametersSector2( List<Double> temp_l ){
	ec_min_fit_values_sector_2 = temp_l;
    }
    public void setMaxFitParametersSector3( List<Double> temp_l ){
	ec_max_fit_values_sector_3 = temp_l;
    }
    public void setMinFitParametersSector3( List<Double> temp_l ){
	ec_min_fit_values_sector_3 = temp_l;
    }
    public void setMaxFitParametersSector4( List<Double> temp_l ){
	ec_max_fit_values_sector_4 = temp_l;
    }
    public void setMinFitParametersSector4( List<Double> temp_l ){
	ec_min_fit_values_sector_4 = temp_l;
    }
    public void setMaxFitParametersSector5( List<Double> temp_l ){
	ec_max_fit_values_sector_5 = temp_l;
    }
    public void setMinFitParametersSector5( List<Double> temp_l ){
	ec_min_fit_values_sector_5 = temp_l;
    }
    public void setMaxFitParametersSector6( List<Double> temp_l ){
	ec_max_fit_values_sector_6 = temp_l;
    }
    public void setMinFitParametersSector6( List<Double> temp_l ){
	ec_min_fit_values_sector_6 = temp_l;
    }
    


}
