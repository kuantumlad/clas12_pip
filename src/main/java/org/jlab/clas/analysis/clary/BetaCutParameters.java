package org.jlab.clas.analysis.clary;

import com.google.gson.*;

import java.io.*;
import java.util.*;

/*
CUTS ARE FOR FTOF L2
*/

public class BetaCutParameters{

    String cut_name;
    List<Double> pr_beta_max_fit_values_sector_1;
    List<Double> pr_beta_min_fit_values_sector_1;
    List<Double> pr_beta_max_fit_values_sector_2;
    List<Double> pr_beta_min_fit_values_sector_2;
    List<Double> pr_beta_max_fit_values_sector_3;
    List<Double> pr_beta_min_fit_values_sector_3;
    List<Double> pr_beta_max_fit_values_sector_4;
    List<Double> pr_beta_min_fit_values_sector_4;
    List<Double> pr_beta_max_fit_values_sector_5;
    List<Double> pr_beta_min_fit_values_sector_5;
    List<Double> pr_beta_max_fit_values_sector_6;
    List<Double> pr_beta_min_fit_values_sector_6;

    List<Double> pip_beta_max_fit_values_sector_1;
    List<Double> pip_beta_min_fit_values_sector_1;
    List<Double> pip_beta_max_fit_values_sector_2;
    List<Double> pip_beta_min_fit_values_sector_2;
    List<Double> pip_beta_max_fit_values_sector_3;
    List<Double> pip_beta_min_fit_values_sector_3;
    List<Double> pip_beta_max_fit_values_sector_4;
    List<Double> pip_beta_min_fit_values_sector_4;
    List<Double> pip_beta_max_fit_values_sector_5;
    List<Double> pip_beta_min_fit_values_sector_5;
    List<Double> pip_beta_max_fit_values_sector_6;
    List<Double> pip_beta_min_fit_values_sector_6;

    List<Double> kp_beta_max_fit_values_sector_1;
    List<Double> kp_beta_min_fit_values_sector_1;
    List<Double> kp_beta_max_fit_values_sector_2;
    List<Double> kp_beta_min_fit_values_sector_2;
    List<Double> kp_beta_max_fit_values_sector_3;
    List<Double> kp_beta_min_fit_values_sector_3;
    List<Double> kp_beta_max_fit_values_sector_4;
    List<Double> kp_beta_min_fit_values_sector_4;
    List<Double> kp_beta_max_fit_values_sector_5;
    List<Double> kp_beta_min_fit_values_sector_5;
    List<Double> kp_beta_max_fit_values_sector_6;
    List<Double> kp_beta_min_fit_values_sector_6;

    public BetaCutParameters(){ //Detectors(){
	
	System.out.println(" BETA CUT PARAMETERS CONSTRUCTOR " );

    }

    
    public String getCutName(){
	return cut_name;
    }
    
    public void setCutName(String temp_name){
	System.out.println(" >> SETTING CUT NAME " + temp_name);
	cut_name = temp_name;
    }

    //public String getCutForParticleType(String particle_name){
    //	return particle_name;
    //}

    public List<Double> getMaxFitParametersSector1(String particle_type){
	List<Double> temp_l = null;
	if( particle_type == "proton") temp_l = pr_beta_max_fit_values_sector_1; 
	if( particle_type == "kaon")  temp_l = kp_beta_max_fit_values_sector_1; 
	if( particle_type == "pion")  temp_l =pip_beta_max_fit_values_sector_1;
	return temp_l;
    }
    public List<Double> getMinFitParametersSector1(String particle_type){
	List<Double> temp_l= null;
	if( particle_type == "proton")  temp_l = pr_beta_min_fit_values_sector_1;
	if( particle_type == "kaon")  temp_l = kp_beta_min_fit_values_sector_1;
	if( particle_type == "pion")  temp_l = pip_beta_min_fit_values_sector_1;
	return temp_l;
    }
    public List<Double> getMaxFitParametersSector2(String particle_type){
	List<Double> temp_l= null;
	if( particle_type == "proton")  temp_l = pr_beta_max_fit_values_sector_2;
	if( particle_type == "kaon")  temp_l = kp_beta_max_fit_values_sector_2;
	if( particle_type == "pion")  temp_l = pip_beta_max_fit_values_sector_2;
	return temp_l;
    }
    public List<Double> getMinFitParametersSector2(String particle_type){
	List<Double> temp_l = null;
	if( particle_type == "proton")  temp_l = pr_beta_min_fit_values_sector_2;
	if( particle_type == "kaon")  temp_l = kp_beta_min_fit_values_sector_2;
	if( particle_type == "pion")  temp_l = pip_beta_min_fit_values_sector_2;
	return temp_l;
    }
    public List<Double> getMaxFitParametersSector3(String particle_type){
	List<Double> temp_l = null;
	if( particle_type == "proton")  temp_l = pr_beta_max_fit_values_sector_3;
	if( particle_type == "kaon")  temp_l = kp_beta_max_fit_values_sector_3;
	if( particle_type == "pion")  temp_l = pip_beta_max_fit_values_sector_3;
	return temp_l;
    }
    public List<Double> getMinFitParametersSector3(String particle_type){
	List<Double> temp_l = null;
	if( particle_type == "proton")  temp_l = pr_beta_min_fit_values_sector_3;
	if( particle_type == "kaon")  temp_l = kp_beta_min_fit_values_sector_3;
	if( particle_type == "pion")  temp_l = pip_beta_min_fit_values_sector_3;
	return temp_l;
    }
    public List<Double> getMaxFitParametersSector4(String particle_type){
	List<Double> temp_l = null;
	if( particle_type == "proton")  temp_l = pr_beta_max_fit_values_sector_4;
	if( particle_type == "kaon")  temp_l = kp_beta_max_fit_values_sector_4;
	if( particle_type == "pion")  temp_l = pip_beta_max_fit_values_sector_4;
	return temp_l;
    }
    public List<Double> getMinFitParametersSector4(String particle_type){
	List<Double> temp_l = null;
	if( particle_type == "proton")  temp_l = pr_beta_min_fit_values_sector_4;
	if( particle_type == "kaon")  temp_l = kp_beta_min_fit_values_sector_4;
	if( particle_type == "pion")  temp_l = pip_beta_min_fit_values_sector_4;
	return temp_l;
    }
    public List<Double> getMaxFitParametersSector5(String particle_type){
	List<Double> temp_l = null;
	if( particle_type == "proton") temp_l = pr_beta_min_fit_values_sector_5;
	if( particle_type == "kaon") temp_l = kp_beta_min_fit_values_sector_5;
	if( particle_type == "pion") temp_l = pip_beta_min_fit_values_sector_5;
	return temp_l;
    }
    public List<Double> getMinFitParametersSector5(String particle_type){
	List<Double> temp_l = null;
	if( particle_type == "proton") temp_l = pr_beta_min_fit_values_sector_5;
	if( particle_type == "kaon")  temp_l = kp_beta_min_fit_values_sector_5;
	if( particle_type == "pion") temp_l = pip_beta_min_fit_values_sector_5;
	return temp_l;
    }
    public List<Double> getMaxFitParametersSector6(String particle_type){
	List<Double> temp_l = null;; 
	if( particle_type == "proton") temp_l = pr_beta_max_fit_values_sector_6;
	if( particle_type == "kaon")  temp_l = kp_beta_max_fit_values_sector_6;
	if( particle_type == "pion")  temp_l = pip_beta_max_fit_values_sector_6;
	return temp_l;
    }
    public List<Double> getMinFitParametersSector6(String particle_type){
	List<Double> temp_l = null;
	if( particle_type == "proton") temp_l = pr_beta_max_fit_values_sector_6;
	if( particle_type == "kaon")  temp_l = kp_beta_max_fit_values_sector_6;
	if( particle_type == "pion")  temp_l = pip_beta_max_fit_values_sector_6;
	return temp_l;
    }

    //////////////////////////////////////////////////////////
    //SETTERS
    public void setMaxFitParametersSector1( String particle_type, List<Double> temp_l ){
 	if( particle_type == "proton" )	pr_beta_max_fit_values_sector_1 = temp_l;
	if( particle_type == "kaon" )	kp_beta_max_fit_values_sector_1 = temp_l;
	if( particle_type == "pion" )	pip_beta_max_fit_values_sector_1 = temp_l;
    }
    public void setMinFitParametersSector1( String particle_type, List<Double> temp_l ){
 	if( particle_type == "proton" )	pr_beta_min_fit_values_sector_1 = temp_l;
	if( particle_type == "kaon" )	kp_beta_min_fit_values_sector_1 = temp_l;
	if( particle_type == "pion" )	pip_beta_min_fit_values_sector_1 = temp_l;
    }
    public void setMaxFitParametersSector2( String particle_type, List<Double> temp_l ){
 	if( particle_type == "proton" )	pr_beta_max_fit_values_sector_2 = temp_l;
	if( particle_type == "kaon" )	kp_beta_max_fit_values_sector_2 = temp_l;
	if( particle_type == "pion" )	pip_beta_max_fit_values_sector_2 = temp_l;
    }
    public void setMinFitParametersSector2( String particle_type, List<Double> temp_l ){
 	if( particle_type == "proton" )	pr_beta_min_fit_values_sector_2 = temp_l;
	if( particle_type == "kaon" )	kp_beta_min_fit_values_sector_2 = temp_l;
	if( particle_type == "pion" )	pip_beta_min_fit_values_sector_2 = temp_l;
    }
    public void setMaxFitParametersSector3( String particle_type, List<Double> temp_l ){
 	if( particle_type == "proton" )	pr_beta_max_fit_values_sector_3 = temp_l;
	if( particle_type == "kaon" )	kp_beta_max_fit_values_sector_3 = temp_l;
	if( particle_type == "pion" )	pip_beta_max_fit_values_sector_3 = temp_l;
    }
    public void setMinFitParametersSector3( String particle_type, List<Double> temp_l ){
 	if( particle_type == "proton" )	pr_beta_min_fit_values_sector_3 = temp_l;
	if( particle_type == "kaon" )	kp_beta_min_fit_values_sector_3 = temp_l;
	if( particle_type == "pion" )	pip_beta_min_fit_values_sector_3 = temp_l;
    }
    public void setMaxFitParametersSector4( String particle_type, List<Double> temp_l ){
 	if( particle_type == "proton" )	pr_beta_max_fit_values_sector_4 = temp_l;
	if( particle_type == "kaon" )	kp_beta_max_fit_values_sector_4 = temp_l;
	if( particle_type == "pion" )	pip_beta_max_fit_values_sector_4 = temp_l;
    }
    public void setMinFitParametersSector4( String particle_type, List<Double> temp_l ){
 	if( particle_type == "proton" )	pr_beta_min_fit_values_sector_4 = temp_l;
	if( particle_type == "kaon" )	kp_beta_min_fit_values_sector_4 = temp_l;
	if( particle_type == "pion" )	pip_beta_min_fit_values_sector_4 = temp_l;
    }
    public void setMaxFitParametersSector5( String particle_type, List<Double> temp_l ){
 	if( particle_type == "proton" )	pr_beta_max_fit_values_sector_5 = temp_l;
	if( particle_type == "kaon" )	kp_beta_max_fit_values_sector_5 = temp_l;
	if( particle_type == "pion" )	pip_beta_max_fit_values_sector_5 = temp_l;
    }
    public void setMinFitParametersSector5( String particle_type, List<Double> temp_l ){
 	if( particle_type == "proton" )	pr_beta_min_fit_values_sector_5 = temp_l;
	if( particle_type == "kaon" )	kp_beta_min_fit_values_sector_5 = temp_l;
	if( particle_type == "pion" )	pip_beta_min_fit_values_sector_5 = temp_l;
    }
    public void setMaxFitParametersSector6( String particle_type, List<Double> temp_l ){
 	if( particle_type == "proton" )	pr_beta_max_fit_values_sector_6 = temp_l;
	if( particle_type == "kaon" )	kp_beta_max_fit_values_sector_6 = temp_l;
	if( particle_type == "pion" )	pip_beta_max_fit_values_sector_6 = temp_l;
    }
    public void setMinFitParametersSector6( String particle_type, List<Double> temp_l ){
 	if( particle_type == "proton" )	pr_beta_min_fit_values_sector_6 = temp_l;
	if( particle_type == "kaon" )	kp_beta_min_fit_values_sector_6 = temp_l;
	if( particle_type == "pion" )	pip_beta_min_fit_values_sector_6 = temp_l;
    }
    
}
