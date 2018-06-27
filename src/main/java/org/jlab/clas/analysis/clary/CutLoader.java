package org.jlab.clas.analysis.clary;
import com.google.gson.*;

import java.io.*;
import java.util.*;
import java.net.*;

import org.jlab.clas.analysis.clary.RunInformation;
import org.json.*;
import org.jlab.jnp.utils.json.*;

public class CutLoader{

    String run_number = null;
    String cut_type = null;
    String analysis_type = null;

    RunInformation runinfo;
    static List<Double> ecsf_max_s1;
    static List<Double> ecsf_max_s2;
    static List<Double> ecsf_max_s3; 
    static List<Double> ecsf_max_s4;
    static List<Double> ecsf_max_s5;
    static List<Double> ecsf_max_s6;

    static List<Double> ecsf_min_s1;
    static List<Double> ecsf_min_s2;
    static List<Double> ecsf_min_s3;
    static List<Double> ecsf_min_s4;
    static List<Double> ecsf_min_s5;
    static List<Double> ecsf_min_s6;

    /*
      BETA CUTS FOR FTOF L2 
    */
    static List<Double> pr_beta_max_fit_values_sector_1;
    static List<Double> pr_beta_max_fit_values_sector_2;
    static List<Double> pr_beta_max_fit_values_sector_3;
    static List<Double> pr_beta_max_fit_values_sector_4;
    static List<Double> pr_beta_max_fit_values_sector_5;
    static List<Double> pr_beta_max_fit_values_sector_6;

    static List<Double> pr_beta_min_fit_values_sector_1;
    static List<Double> pr_beta_min_fit_values_sector_2;
    static List<Double> pr_beta_min_fit_values_sector_3;
    static List<Double> pr_beta_min_fit_values_sector_4;
    static List<Double> pr_beta_min_fit_values_sector_5;
    static List<Double> pr_beta_min_fit_values_sector_6;

    static List<Double> pip_beta_max_fit_values_sector_1;
    static List<Double> pip_beta_max_fit_values_sector_2;
    static List<Double> pip_beta_max_fit_values_sector_3;
    static List<Double> pip_beta_max_fit_values_sector_4;
    static List<Double> pip_beta_max_fit_values_sector_5;
    static List<Double> pip_beta_max_fit_values_sector_6;

    static List<Double> pip_beta_min_fit_values_sector_1;
    static List<Double> pip_beta_min_fit_values_sector_2;
    static List<Double> pip_beta_min_fit_values_sector_3;
    static List<Double> pip_beta_min_fit_values_sector_4;
    static List<Double> pip_beta_min_fit_values_sector_5;
    static List<Double> pip_beta_min_fit_values_sector_6;

    static List<Double> kp_beta_max_fit_values_sector_1;
    static List<Double> kp_beta_max_fit_values_sector_2;
    static List<Double> kp_beta_max_fit_values_sector_3;
    static List<Double> kp_beta_max_fit_values_sector_4;
    static List<Double> kp_beta_max_fit_values_sector_5;
    static List<Double> kp_beta_max_fit_values_sector_6;

    static List<Double> kp_beta_min_fit_values_sector_1;
    static List<Double> kp_beta_min_fit_values_sector_2;
    static List<Double> kp_beta_min_fit_values_sector_3;
    static List<Double> kp_beta_min_fit_values_sector_4;
    static List<Double> kp_beta_min_fit_values_sector_5;
    static List<Double> kp_beta_min_fit_values_sector_6;


    /*
     VERTEX CUT
     */
    static List<Double> neg_vz_sec_1;
    static List<Double> neg_vz_sec_2;
    static List<Double> neg_vz_sec_3;
    static List<Double> neg_vz_sec_4;
    static List<Double> neg_vz_sec_5;
    static List<Double> neg_vz_sec_6;

    static List<Double> pos_vz_sec_1;
    static List<Double> pos_vz_sec_2;
    static List<Double> pos_vz_sec_3;
    static List<Double> pos_vz_sec_4;
    static List<Double> pos_vz_sec_5;
    static List<Double> pos_vz_sec_6;

    public CutLoader(int run, String an_type, String cut_strength){
		
	setAnalysisType(an_type);
	loadRunCuts(run,cut_strength);
	setRunCuts();
	printRunCuts();

    }

    public void setAnalysisType(String analy_type){

	analysis_type = analy_type;

    }

    public void loadRunCuts(int run, String temp_cut_type){

	run_number = "r" + Integer.toString(run);
	cut_type = temp_cut_type;
	System.out.println(">> GETTING JSON FILE FOR " + analysis_type);

	try{
	    Gson gson = new Gson();
	    
	    URL oracle = new URL("https://userweb.jlab.org/~bclary/clas12_rundb/CutDB_"+analysis_type+"_v3.json");
	    BufferedReader br = new BufferedReader(new InputStreamReader(oracle.openStream()));
	    //BufferedReader br = new BufferedReader( new FileReader("/home/bclary/CLAS12/phi_analysis/v2/v1/run_db/CutDB_"+analysis_type+"_v3.json") );
	    //BufferedReader br = new BufferedReader( new FileReader("/w/hallb-scifs17exp/clas12/bclary/CutDB_v3.json") );
	    runinfo = gson.fromJson(br, RunInformation.class );
	}
	catch( IOException e ){
	    System.out.println(" >> ERROR LOADING CUT DB JSON FILE " );
	}
	    
    }

    public void writeRunCuts(int run, String temp_cut_type){

	run_number = "r" + Integer.toString(run);
	cut_type = temp_cut_type;

	try{
	    Gson gson = new Gson();
	    String jsonstring = gson.toJson(runinfo);

	    URL url = new URL("https://userweb.jlab.org/~bclary/clas12_rundb/CutDB_"+analysis_type+"_v3.json");
	    URLConnection connection = url.openConnection();
	    connection.setDoOutput(true);
	    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
	    out.write(jsonstring);
	    out.close();
	    //FileWriter fileWriter = new FileWriter("/home/bclary/CLAS12/phi_analysis/v2/v1/run_db/CutDB_"+analysis_type+"_v3.json");
	    //FileWriter fileWriter = new FileWriter("/w/hallb-scifs17exp/clas12/bclary/CutDB_v3.json");
	    //fileWriter.write( jsonstring );
	    //fileWriter.close();
	   
		}
	catch( IOException e ){
	    System.out.println(" >> ERROR WRITING CUT DB JSON FILE " );
	}
	    
    }


    public void setECMaxSectorAllCuts( ){

	ecsf_max_s1 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getECCutParameters().get("ec_sf_cut").getMaxFitParametersSector1();
	ecsf_max_s2 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getECCutParameters().get("ec_sf_cut").getMaxFitParametersSector2();
	ecsf_max_s3 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getECCutParameters().get("ec_sf_cut").getMaxFitParametersSector3();
	ecsf_max_s4 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getECCutParameters().get("ec_sf_cut").getMaxFitParametersSector4();
	ecsf_max_s5 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getECCutParameters().get("ec_sf_cut").getMaxFitParametersSector5();
	ecsf_max_s6 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getECCutParameters().get("ec_sf_cut").getMaxFitParametersSector6();

    }

    public void setECMinSectorAllCuts( ){

	ecsf_min_s1 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getECCutParameters().get("ec_sf_cut").getMinFitParametersSector1();
	ecsf_min_s2 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getECCutParameters().get("ec_sf_cut").getMinFitParametersSector2();
	ecsf_min_s3 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getECCutParameters().get("ec_sf_cut").getMinFitParametersSector3();
	ecsf_min_s4 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getECCutParameters().get("ec_sf_cut").getMinFitParametersSector4();
	ecsf_min_s5 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getECCutParameters().get("ec_sf_cut").getMinFitParametersSector5();
	ecsf_min_s6 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getECCutParameters().get("ec_sf_cut").getMinFitParametersSector6();

    }


    public void setBetaMaxSectorAllCuts(){
	pr_beta_max_fit_values_sector_1 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pr_beta_cut_ftofl2").getMaxFitParametersSector1("proton");
	pr_beta_max_fit_values_sector_2 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pr_beta_cut_ftofl2").getMaxFitParametersSector2("proton");
	pr_beta_max_fit_values_sector_3 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pr_beta_cut_ftofl2").getMaxFitParametersSector3("proton");
	pr_beta_max_fit_values_sector_4 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pr_beta_cut_ftofl2").getMaxFitParametersSector4("proton");
	pr_beta_max_fit_values_sector_5 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pr_beta_cut_ftofl2").getMaxFitParametersSector5("proton");
	pr_beta_max_fit_values_sector_6 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pr_beta_cut_ftofl2").getMaxFitParametersSector6("proton");

	pip_beta_max_fit_values_sector_1 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pip_beta_cut_ftofl2").getMaxFitParametersSector1("pion");
	pip_beta_max_fit_values_sector_2 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pip_beta_cut_ftofl2").getMaxFitParametersSector2("pion");
	pip_beta_max_fit_values_sector_3 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pip_beta_cut_ftofl2").getMaxFitParametersSector3("pion");
	pip_beta_max_fit_values_sector_4 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pip_beta_cut_ftofl2").getMaxFitParametersSector4("pion");
	pip_beta_max_fit_values_sector_5 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pip_beta_cut_ftofl2").getMaxFitParametersSector5("pion");
	pip_beta_max_fit_values_sector_6 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pip_beta_cut_ftofl2").getMaxFitParametersSector6("pion");

	kp_beta_max_fit_values_sector_1 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("kp_beta_cut_ftofl2").getMaxFitParametersSector1("kaon");
	kp_beta_max_fit_values_sector_2 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("kp_beta_cut_ftofl2").getMaxFitParametersSector2("kaon");
	kp_beta_max_fit_values_sector_3 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("kp_beta_cut_ftofl2").getMaxFitParametersSector3("kaon");
	kp_beta_max_fit_values_sector_4 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("kp_beta_cut_ftofl2").getMaxFitParametersSector4("kaon");
	kp_beta_max_fit_values_sector_5 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("kp_beta_cut_ftofl2").getMaxFitParametersSector5("kaon");
	kp_beta_max_fit_values_sector_6 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("kp_beta_cut_ftofl2").getMaxFitParametersSector6("kaon");

    }

    public void setBetaMinSectorAllCuts(){
	pr_beta_min_fit_values_sector_1 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pr_beta_cut_ftofl2").getMinFitParametersSector1("proton");
	pr_beta_min_fit_values_sector_2 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pr_beta_cut_ftofl2").getMinFitParametersSector2("proton");
	pr_beta_min_fit_values_sector_3 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pr_beta_cut_ftofl2").getMinFitParametersSector3("proton");
	pr_beta_min_fit_values_sector_4 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pr_beta_cut_ftofl2").getMinFitParametersSector4("proton");
	pr_beta_min_fit_values_sector_5 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pr_beta_cut_ftofl2").getMinFitParametersSector5("proton");
	pr_beta_min_fit_values_sector_6 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pr_beta_cut_ftofl2").getMinFitParametersSector6("proton");

	pip_beta_min_fit_values_sector_1 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pip_beta_cut_ftofl2").getMinFitParametersSector1("pion");
	pip_beta_min_fit_values_sector_2 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pip_beta_cut_ftofl2").getMinFitParametersSector2("pion");
	pip_beta_min_fit_values_sector_3 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pip_beta_cut_ftofl2").getMinFitParametersSector3("pion");
	pip_beta_min_fit_values_sector_4 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pip_beta_cut_ftofl2").getMinFitParametersSector4("pion");
	pip_beta_min_fit_values_sector_5 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pip_beta_cut_ftofl2").getMinFitParametersSector5("pion");
	pip_beta_min_fit_values_sector_6 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("pip_beta_cut_ftofl2").getMinFitParametersSector6("pion");

	kp_beta_min_fit_values_sector_1 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("kp_beta_cut_ftofl2").getMinFitParametersSector1("kaon");
	kp_beta_min_fit_values_sector_2 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("kp_beta_cut_ftofl2").getMinFitParametersSector2("kaon");
	kp_beta_min_fit_values_sector_3 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("kp_beta_cut_ftofl2").getMinFitParametersSector3("kaon");
	kp_beta_min_fit_values_sector_4 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("kp_beta_cut_ftofl2").getMinFitParametersSector4("kaon");
	kp_beta_min_fit_values_sector_5 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("kp_beta_cut_ftofl2").getMinFitParametersSector5("kaon");
	kp_beta_min_fit_values_sector_6 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getBetaCutParameters().get("kp_beta_cut_ftofl2").getMinFitParametersSector6("kaon");
    }
   
    public void setVertexCut(){
		
	//List<Double> vz_min_1 = runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMaxVertexSector1();
	//neg_vz_sec_1.add(vz_min_1.get(0));

	//System.out.println(" >> " + runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMaxVertexSector1().get(0) );

	double min1  = (double)runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMinVertexSector1().get(0);
	double max1  = (double)runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMaxVertexSector1().get(0);
	List<Double> temp1 = new ArrayList<Double>();
	temp1.add(min1);
	temp1.add(max1);
	neg_vz_sec_1 = temp1;

	double min2  = (double)runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMinVertexSector2().get(0);
	double max2  = (double)runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMaxVertexSector2().get(0);
	List<Double> temp2 = new ArrayList<Double>();
	temp2.add(min2);
	temp2.add(max2);
	neg_vz_sec_2 = temp2;

	double min3  = (double)runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMinVertexSector3().get(0);
	double max3  = (double)runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMaxVertexSector3().get(0);
	List<Double> temp3 = new ArrayList<Double>();
	temp3.add(min3);
	temp3.add(max3);
	neg_vz_sec_3 = temp3;

	double min4  = (double)runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMinVertexSector4().get(0);
	double max4  = (double)runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMaxVertexSector4().get(0);
	List<Double> temp4 = new ArrayList<Double>();
	temp4.add(min4);
	temp4.add(max4);
	neg_vz_sec_4 = temp4;

	double min5  = (double)runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMinVertexSector5().get(0);
	double max5  = (double)runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMaxVertexSector5().get(0);
	List<Double> temp5 = new ArrayList<Double>();
	temp5.add(min5);
	temp5.add(max5);
	neg_vz_sec_5 = temp5;

	double min6  = (double)runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMinVertexSector6().get(0);
	double max6  = (double)runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMaxVertexSector6().get(0);
	List<Double> temp6 = new ArrayList<Double>();
	temp6.add(min6);
	temp6.add(max6);
	neg_vz_sec_6 = temp6;

	/*neg_vz_sec_1.add(runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMaxVertexSector1().get(0));

	neg_vz_sec_2.add(runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMinVertexSector2().get(0));
	neg_vz_sec_2.add(runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMaxVertexSector2().get(0));

	neg_vz_sec_3.add(runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMinVertexSector3().get(0));
	neg_vz_sec_3.add(runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMaxVertexSector3().get(0));

	neg_vz_sec_4.add(runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMinVertexSector4().get(0));
	neg_vz_sec_4.add(runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMaxVertexSector4().get(0));

	neg_vz_sec_5.add(runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMinVertexSector5().get(0));
	neg_vz_sec_5.add(runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMaxVertexSector5().get(0));

	neg_vz_sec_6.add(runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMinVertexSector6().get(0));
	neg_vz_sec_6.add(runinfo.getRunParameters().get(run_number).getCutType().get(cut_type).getVertexCutParameters().get("vz_cut").getMaxVertexSector6().get(0));
	*/

    }


    public void setRunCuts(){

	setECMaxSectorAllCuts();
	setECMinSectorAllCuts();

	setBetaMaxSectorAllCuts();
	setBetaMinSectorAllCuts();
	
	setVertexCut();

    }

    public void printRunCuts(){
	for( int parameter = 0; parameter < ecsf_max_s1.size(); parameter++){
	    System.out.println("EC Sampling Fraction MAX: S1: " + ecsf_max_s1.get(parameter) + " S2: " + ecsf_max_s2.get(parameter) + " S3: " + ecsf_max_s3.get(parameter) + " S4: " + ecsf_max_s4.get(parameter) + " S5 " + + ecsf_max_s5.get(parameter) + " S6: " + ecsf_max_s6.get(parameter) );
	}

	for( int parameter = 0; parameter < ecsf_min_s1.size(); parameter++){
	    System.out.println("EC Sampling Fraction MIN: S1: " + ecsf_min_s1.get(parameter) + " S2: " + ecsf_min_s2.get(parameter) + " S3: " + ecsf_min_s3.get(parameter) + " S4: " + ecsf_min_s4.get(parameter) + " S5 " + + ecsf_min_s5.get(parameter) + " S6: " + ecsf_min_s6.get(parameter) );
	}

	for( int p = 0; p < pr_beta_min_fit_values_sector_1.size(); p++ ){
	    System.out.println(">> PROTON BETA FTOF L2 MAX: S1 " + pr_beta_max_fit_values_sector_1.get(p) + " S2 " + pr_beta_max_fit_values_sector_2.get(p) + " S3 " + pr_beta_max_fit_values_sector_3.get(p) + " S4 " + pr_beta_max_fit_values_sector_4.get(p) + " S5 " + pr_beta_max_fit_values_sector_5.get(p) + " S6 " + pr_beta_max_fit_values_sector_6.get(p) );
	}

	for( int p = 0; p < pr_beta_min_fit_values_sector_1.size(); p++ ){
	    System.out.println(">> PROTON BETA FTOF L2 MIN : S1 " + pr_beta_min_fit_values_sector_1.get(p) + " S2 " + pr_beta_min_fit_values_sector_2.get(p) + " S3 " + pr_beta_min_fit_values_sector_3.get(p) + " S4 " + pr_beta_min_fit_values_sector_4.get(p) + " S5 " + pr_beta_min_fit_values_sector_5.get(p) + " S6 " + pr_beta_min_fit_values_sector_6.get(p) );
	}

	for( int p = 0; p < pip_beta_min_fit_values_sector_1.size(); p++ ){
	    System.out.println(">> PION PLUS BETA FTOF L2 MAX: S1 " + pip_beta_max_fit_values_sector_1.get(p) + " S2 " + pip_beta_max_fit_values_sector_2.get(p) + " S3 " + pip_beta_max_fit_values_sector_3.get(p) + " S4 " + pip_beta_max_fit_values_sector_4.get(p) + " S5 " + pip_beta_max_fit_values_sector_5.get(p) + " S6 " + pip_beta_max_fit_values_sector_6.get(p) );
	}

	for( int p = 0; p < pip_beta_min_fit_values_sector_1.size(); p++ ){
	    System.out.println(">> PION PLUS BETA FTOF L2 MIN : S1 " + pip_beta_min_fit_values_sector_1.get(p) + " S2 " + pip_beta_min_fit_values_sector_2.get(p) + " S3 " + pip_beta_min_fit_values_sector_3.get(p) + " S4 " + pip_beta_min_fit_values_sector_4.get(p) + " S5 " + pip_beta_min_fit_values_sector_5.get(p) + " S6 " + pip_beta_min_fit_values_sector_6.get(p) );
	}

	for( int p = 0; p < kp_beta_min_fit_values_sector_1.size(); p++ ){
	    System.out.println(">> KAON PLUS BETA FTOF L2 MAX: S1 " + kp_beta_max_fit_values_sector_1.get(p) + " S2 " + kp_beta_max_fit_values_sector_2.get(p) + " S3 " + kp_beta_max_fit_values_sector_3.get(p) + " S4 " + kp_beta_max_fit_values_sector_4.get(p) + " S5 " + kp_beta_max_fit_values_sector_5.get(p) + " S6 " + kp_beta_max_fit_values_sector_6.get(p) );
	}

	for( int p = 0; p < kp_beta_min_fit_values_sector_1.size(); p++ ){
	    System.out.println(">> KAON PLUS BETA FTOF L2 MIN : S1 " + kp_beta_min_fit_values_sector_1.get(p) + " S2 " + kp_beta_min_fit_values_sector_2.get(p) + " S3 " + kp_beta_min_fit_values_sector_3.get(p) + " S4 " + kp_beta_min_fit_values_sector_4.get(p) + " S5 " + kp_beta_min_fit_values_sector_5.get(p) + " S6 " + kp_beta_min_fit_values_sector_6.get(p) );
	}

	System.out.println(" >> PRINTING VERTEX CUTS " );
	System.out.println(" >> VERTEX CUT SECTOR 1 " + neg_vz_sec_1 );
	System.out.println(" >> VERTEX CUT SECTOR 2 " + neg_vz_sec_2 );
	System.out.println(" >> VERTEX CUT SECTOR 3 " + neg_vz_sec_3 );
	System.out.println(" >> VERTEX CUT SECTOR 4 " + neg_vz_sec_4 );
	System.out.println(" >> VERTEX CUT SECTOR 5 " + neg_vz_sec_5 );
	System.out.println(" >> VERTEX CUT SECTOR 6 " + neg_vz_sec_6 );
	
    }

    static public List<Double> getECMaxSector1Cut(){ return ecsf_max_s1; }
    static public List<Double> getECMaxSector2Cut(){ return ecsf_max_s2; }
    static public List<Double> getECMaxSector3Cut(){ return ecsf_max_s3; }
    static public List<Double> getECMaxSector4Cut(){ return ecsf_max_s4; }
    static public List<Double> getECMaxSector5Cut(){ return ecsf_max_s5; }
    static public List<Double> getECMaxSector6Cut(){ return ecsf_max_s6; }

    static public List<Double> getECMinSector1Cut(){ return ecsf_min_s1; }
    static public List<Double> getECMinSector2Cut(){ return ecsf_min_s2; }
    static public List<Double> getECMinSector3Cut(){ return ecsf_min_s3; }
    static public List<Double> getECMinSector4Cut(){ return ecsf_min_s4; }
    static public List<Double> getECMinSector5Cut(){ return ecsf_min_s5; }
    static public List<Double> getECMinSector6Cut(){ return ecsf_min_s6; }

    static public List<Double> getMaxProtonBetaSector1Cut(){ return pr_beta_max_fit_values_sector_1;}
    static public List<Double> getMaxProtonBetaSector2Cut(){ return pr_beta_max_fit_values_sector_2;}
    static public List<Double> getMaxProtonBetaSector3Cut(){ return pr_beta_max_fit_values_sector_3;}
    static public List<Double> getMaxProtonBetaSector4Cut(){ return pr_beta_max_fit_values_sector_4;}
    static public List<Double> getMaxProtonBetaSector5Cut(){ return pr_beta_max_fit_values_sector_5;}
    static public List<Double> getMaxProtonBetaSector6Cut(){ return pr_beta_max_fit_values_sector_6;}

    static public List<Double> getMinProtonBetaSector1Cut(){ return pr_beta_min_fit_values_sector_1;}
    static public List<Double> getMinProtonBetaSector2Cut(){ return pr_beta_min_fit_values_sector_2;}
    static public List<Double> getMinProtonBetaSector3Cut(){ return pr_beta_min_fit_values_sector_3;}
    static public List<Double> getMinProtonBetaSector4Cut(){ return pr_beta_min_fit_values_sector_4;}
    static public List<Double> getMinProtonBetaSector5Cut(){ return pr_beta_min_fit_values_sector_5;}
    static public List<Double> getMinProtonBetaSector6Cut(){ return pr_beta_min_fit_values_sector_6;}
    
    static public List<Double> getMaxPIPBetaSector1Cut(){ return pip_beta_max_fit_values_sector_1;}
    static public List<Double> getMaxPIPBetaSector2Cut(){ return pip_beta_max_fit_values_sector_2;}
    static public List<Double> getMaxPIPBetaSector3Cut(){ return pip_beta_max_fit_values_sector_3;}
    static public List<Double> getMaxPIPBetaSector4Cut(){ return pip_beta_max_fit_values_sector_4;}
    static public List<Double> getMaxPIPBetaSector5Cut(){ return pip_beta_max_fit_values_sector_5;}
    static public List<Double> getMaxPIPBetaSector6Cut(){ return pip_beta_max_fit_values_sector_6;}

    static public List<Double> getMinPIPBetaSector1Cut(){ return pip_beta_min_fit_values_sector_1;}
    static public List<Double> getMinPIPBetaSector2Cut(){ return pip_beta_min_fit_values_sector_2;}
    static public List<Double> getMinPIPBetaSector3Cut(){ return pip_beta_min_fit_values_sector_3;}
    static public List<Double> getMinPIPBetaSector4Cut(){ return pip_beta_min_fit_values_sector_4;}
    static public List<Double> getMinPIPBetaSector5Cut(){ return pip_beta_min_fit_values_sector_5;}
    static public List<Double> getMinPIPBetaSector6Cut(){ return pip_beta_min_fit_values_sector_6;}

    static public List<Double> getMaxKPBetaSector1Cut(){ return kp_beta_max_fit_values_sector_1; }
    static public List<Double> getMaxKPBetaSector2Cut(){ return kp_beta_max_fit_values_sector_2; }
    static public List<Double> getMaxKPBetaSector3Cut(){ return kp_beta_max_fit_values_sector_3; }
    static public List<Double> getMaxKPBetaSector4Cut(){ return kp_beta_max_fit_values_sector_4; }
    static public List<Double> getMaxKPBetaSector5Cut(){ return kp_beta_max_fit_values_sector_5; }
    static public List<Double> getMaxKPBetaSector6Cut(){ return kp_beta_max_fit_values_sector_6; }

    static public List<Double> getMinKPBetaSector1Cut(){ return kp_beta_min_fit_values_sector_1; }
    static public List<Double> getMinKPBetaSector2Cut(){ return kp_beta_min_fit_values_sector_2; }
    static public List<Double> getMinKPBetaSector3Cut(){ return kp_beta_min_fit_values_sector_3; }
    static public List<Double> getMinKPBetaSector4Cut(){ return kp_beta_min_fit_values_sector_4; }
    static public List<Double> getMinKPBetaSector5Cut(){ return kp_beta_min_fit_values_sector_5; }
    static public List<Double> getMinKPBetaSector6Cut(){ return kp_beta_min_fit_values_sector_6; }

    static public List<Double> getVertexCutSector1(){ return neg_vz_sec_1; }
    static public List<Double> getVertexCutSector2(){ return neg_vz_sec_2; }
    static public List<Double> getVertexCutSector3(){ return neg_vz_sec_3; }
    static public List<Double> getVertexCutSector4(){ return neg_vz_sec_4; }
    static public List<Double> getVertexCutSector5(){ return neg_vz_sec_5; }
    static public List<Double> getVertexCutSector6(){ return neg_vz_sec_6; }

}
