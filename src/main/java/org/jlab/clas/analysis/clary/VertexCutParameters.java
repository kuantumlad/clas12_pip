package org.jlab.clas.analysis.clary;

import com.google.gson.*;

import java.io.*;
import java.util.*;

/*
CUTS ARE FOR FTOF L2
*/

public class VertexCutParameters{

    String cut_name;
    List<Double> vz_max_sector_1;
    List<Double> vz_max_sector_2;
    List<Double> vz_max_sector_3;
    List<Double> vz_max_sector_4;
    List<Double> vz_max_sector_5;
    List<Double> vz_max_sector_6;

    List<Double> vz_min_sector_1;
    List<Double> vz_min_sector_2;
    List<Double> vz_min_sector_3;
    List<Double> vz_min_sector_4;
    List<Double> vz_min_sector_5;
    List<Double> vz_min_sector_6;

    public VertexCutParameters(){ 
	
	System.out.println(" VERTEX CUT PARAMETERS CONSTRUCTOR " );

    }

    
    public String getCutName(){
	return cut_name;
    }
    
    public void setCutName(String temp_name){
	System.out.println(" >> SETTING CUT NAME " + temp_name);
	cut_name = temp_name;
    }


    //////////
    //GETTERS   
    public List<Double> getMaxVertexSector1(){
	List<Double> temp_l = vz_max_sector_1;
	System.out.println(" >> TESTING " + temp_l );
	return temp_l;
    }

    public List<Double> getMaxVertexSector2(){
	List<Double> temp_l = vz_max_sector_2;
	return temp_l;
    }

    public List<Double> getMaxVertexSector3(){
	List<Double> temp_l = vz_max_sector_3;
	return temp_l;
    }

    public List<Double> getMaxVertexSector4(){
	List<Double> temp_l = vz_max_sector_4;
	return temp_l;
    }

    public List<Double> getMaxVertexSector5(){
	List<Double> temp_l = vz_max_sector_5;
	return temp_l;
    }

    public List<Double> getMaxVertexSector6(){
	List<Double> temp_l = vz_max_sector_6;
	return temp_l;
    }

    public List<Double> getMinVertexSector1(){
	List<Double> temp_l = vz_min_sector_1;
	return temp_l;
    }

    public List<Double> getMinVertexSector2(){
	List<Double> temp_l = vz_min_sector_2;
	return temp_l;
    }

    public List<Double> getMinVertexSector3(){
	List<Double> temp_l = vz_min_sector_3;
	return temp_l;
    }

    public List<Double> getMinVertexSector4(){
	List<Double> temp_l = vz_min_sector_4;
	return temp_l;
    }

    public List<Double> getMinVertexSector5(){
	List<Double> temp_l = vz_min_sector_5;
	return temp_l;
    }

    public List<Double> getMinVertexSector6(){
	List<Double> temp_l = vz_min_sector_6;
	return temp_l;
    }

    public void setMaxVertexSector1( List<Double> temp_l){
	vz_max_sector_1 = temp_l;
    }

    public void setMaxVertexSector2( List<Double> temp_l){
	vz_max_sector_2 = temp_l;
    }

    public void setMaxVertexSector3( List<Double> temp_l){
	vz_max_sector_3 = temp_l;
    }

    public void setMaxVertexSector4( List<Double> temp_l){
	vz_max_sector_4 = temp_l;
    }

    public void setMaxVertexSector5( List<Double> temp_l){
	vz_max_sector_5 = temp_l;
    }

    public void setMaxVertexSector6( List<Double> temp_l){
	vz_max_sector_6 = temp_l;
    }

    public void setMinVertexSector1( List<Double> temp_l){
	vz_min_sector_1 = temp_l;
    }

    public void setMinVertexSector2( List<Double> temp_l){
	vz_min_sector_2 = temp_l;
    }

    public void setMinVertexSector3( List<Double> temp_l){
	vz_min_sector_3 = temp_l;
    }

    public void setMinVertexSector4( List<Double> temp_l){
	vz_min_sector_4 = temp_l;
    }

    public void setMinVertexSector5( List<Double> temp_l){
	vz_min_sector_5 = temp_l;
    }

    public void setMinVertexSector6( List<Double> temp_l){
	vz_min_sector_6 = temp_l;
    }

}
