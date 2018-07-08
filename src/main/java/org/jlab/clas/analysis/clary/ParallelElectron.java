package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import com.google.gson.*;
import org.json.*;
import org.jlab.jnp.utils.json.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.clas.physics.Particle;  
import org.jlab.clas.physics.LorentzVector;  

import org.jlab.io.hipo.*;//HipoDataEvent;
import org.jlab.io.hipo.HipoDataSource;
import org.jlab.jnp.hipo.io.HipoWriter;
import org.jlab.io.hipo.HipoDataSync;
import org.jlab.io.evio.EvioDataDescriptor;

import org.jlab.jnp.hipo.schema.*;

import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class ParallelElectron implements Runnable{

    private Thread t;
    private String threadName;

    RunPropertiesLoader run_properties;

    ElectronPID find_el;
    MLEParticleFinder mle_particle;
    MLEParticleFinder mle_particle_neg;

    BHistoPIDLevel h_pid_cutlvls;
    BHistoCLAS12PID h_pid_clas12;
    BHistoMLE h_pid_mle;
    HipoDataSource temphiporeader;
    BHistoPhys h_phys;

    int run_number;
    String f_name;
    File in_file; 
    int min_event; 
    int max_event;
    int min_file;
    int max_file;
    boolean typeAnalysis; 
    String dataOrSim_type = null;

    ParallelElectron(String name, String s_run, String file_name, boolean singleMultiAnalysis, String  dataOrSim ){
	threadName = name;
	System.out.println("Creating " +  threadName );
	run_number = Integer.valueOf(s_run);
	typeAnalysis = singleMultiAnalysis; //TRUE FOR SINGLE FILE ANALYSIS, FALSE FOR MULTI

	dataOrSim_type = dataOrSim;
	initConfig(run_number);

	h_pid_cutlvls = new BHistoPIDLevel(run_number, threadName);
	h_pid_clas12 = new BHistoCLAS12PID(run_number, threadName);
	h_pid_mle = new BHistoMLE(run_number, threadName);
	h_phys = new BHistoPhys(run_number,threadName);

	h_pid_cutlvls.CreateHistograms(); 
	h_pid_clas12.createHistograms();
	h_phys.createHistograms();
	
	Runtime rt = Runtime.getRuntime();
	rt.gc();
	long mem = rt.totalMemory() - rt.freeMemory();
	System.out.println(">> USED IN bytes " + mem);

	////////////
	//
	/*	CutConfigInfo cutconfig = new CutConfigInfo();
	try{
	    Gson gson = new Gson();
	    BufferedReader br = new BufferedReader( new FileReader("/home/bclary/CLAS12/phi_analysis/v3/v2/v1/run_db/cut_config.json") );
	    cutconfig = gson.fromJson(br, CutConfigInfo.class );
	}
	catch( IOException e ){
	    System.out.println(" >> ERROR LOADING CUT DB JSON FILE " );
	}
	*/

	CutConfig cutconfig = new CutConfig("/home/bclary/CLAS12/phi_analysis/v3/v2/v1/run_db/cut_config.json");	
	
	System.out.println(" >> " + cutconfig.getElectronCuts());
	System.out.println(" >> " + cutconfig.getProtonCuts());
	
	List<String> my_el_cuts = cutconfig.getElectronCuts();
	
	find_el = new ElectronPID(my_el_cuts);

	//
	///////////////

	mle_particle = new MLEParticleFinder("pos"); 
	mle_particle_neg = new MLEParticleFinder("neg"); 

	run_number = Integer.valueOf(s_run);
	f_name = file_name;

	if( singleMultiAnalysis ){
	    in_file = new File(f_name);
	    System.out.println(" >> FILE TO ANALYZE " + in_file);
	    temphiporeader = new HipoDataSource();
	
	    
	    if( in_file.exists() ){//  && file_to_analyze.length() >= max_file_size ){
		System.out.println("File Exists");
	    }
	    if( !in_file.exists() ){// || file_to_analyze.length() <= max_file_size){
		System.out.println("File DOES NOT Exists");
		return;
	    }
	}



    }


    ///////////////////////////////////////////////////////////////////////////////
    //ATTEMPTING TO NOT OPEN A FILE FOR EACH THREAD WHEN DOING SINGLE FILE ANALYSIS
    ParallelElectron(String name, String s_run, HipoDataSource temp_hipo, boolean singleMultiAnalysis, String  dataOrSim){
	threadName = name;
	System.out.println("Creating " +  threadName );
	run_number = Integer.valueOf(s_run);
	dataOrSim_type = dataOrSim;
	typeAnalysis = singleMultiAnalysis; //TRUE FOR SINGLE FILE ANALYSIS, FALSE FOR MULTI

	initConfig(run_number);

	h_pid_cutlvls = new BHistoPIDLevel(run_number, threadName);
	h_pid_clas12 = new BHistoCLAS12PID(run_number, threadName);
	h_pid_mle = new BHistoMLE(run_number, threadName);
	h_phys = new BHistoPhys(run_number,threadName);

	h_pid_cutlvls.CreateHistograms();
	h_pid_clas12.createHistograms();
	h_phys.createHistograms();
	
	////////////
	//
	/* CutConfigInfo cutconfig = new CutConfigInfo();
	try{
	    Gson gson = new Gson();
	    BufferedReader br = new BufferedReader( new FileReader("/home/bclary/CLAS12/phi_analysis/v3/v2/v1/run_db/cut_config.json") );
	    cutconfig = gson.fromJson(br, CutConfigInfo.class );
	}
	catch( IOException e ){
	    System.out.println(" >> ERROR LOADING CUT DB JSON FILE " );
	}
	*/	
	
	CutConfig cutconfig = new CutConfig("/home/bclary/CLAS12/phi_analysis/v3/v2/v1/run_db/cut_config.json");	

	System.out.println(" >> " + cutconfig.getElectronCuts());
	System.out.println(" >> " + cutconfig.getProtonCuts());
	
	List<String> my_el_cuts = cutconfig.getElectronCuts();
	
	find_el = new ElectronPID(my_el_cuts);

	//
	///////////////       
	//find_el = new ElectronPID();
	mle_particle = new MLEParticleFinder("pos"); 
	mle_particle_neg = new MLEParticleFinder("neg"); 

	run_number = Integer.valueOf(s_run);
	//f_name = file_name;
	temphiporeader = temp_hipo;

	if( singleMultiAnalysis ){
	    in_file = new File(f_name);
	    System.out.println(" >> FILE TO ANALYZE " + in_file);
	    
	    if( in_file.exists() ){//  && file_to_analyze.length() >= max_file_size ){
		System.out.println("File Exists");
	    }
	    if( !in_file.exists() ){// || file_to_analyze.length() <= max_file_size){
		System.out.println("File DOES NOT Exists");
		return;
	    }
	}

    }


    ParallelElectron(String name, String s_run, File temp_file, boolean singleMultiAnalysis, String  dataOrSim ){
	threadName = name;
	System.out.println("Creating " +  threadName );
	run_number = Integer.valueOf(s_run);
	dataOrSim_type = dataOrSim;
	typeAnalysis = singleMultiAnalysis; //TRUE FOR SINGLE FILE ANALYSIS, FALSE FOR MULTI

	initConfig(run_number);

	h_pid_cutlvls = new BHistoPIDLevel(run_number, threadName);       
	h_pid_mle = new BHistoMLE(run_number,threadName);
	h_phys = new BHistoPhys(run_number,threadName);

	h_pid_cutlvls.CreateHistograms();
	h_phys.createHistograms();

	////////////
	//
	/*CutConfigInfo cutconfig = new CutConfigInfo();
	try{
	    Gson gson = new Gson();
	    BufferedReader br = new BufferedReader( new FileReader("/home/bclary/CLAS12/phi_analysis/v3/v2/v1/run_db/cut_config.json") );
	    cutconfig = gson.fromJson(br, CutConfigInfo.class );
	}
	catch( IOException e ){
	    System.out.println(" >> ERROR LOADING CUT DB JSON FILE " );
	}	
	*/

	CutConfig cutconfig = new CutConfig("/home/bclary/CLAS12/phi_analysis/v3/v2/v1/run_db/cut_config.json");	

	System.out.println(" >> " + cutconfig.getElectronCuts());
	System.out.println(" >> " + cutconfig.getProtonCuts());
	
	List<String> my_el_cuts = cutconfig.getElectronCuts();
	
	find_el = new ElectronPID(my_el_cuts);
	//
	///////////////
	//find_el = new ElectronPID();

	mle_particle = new MLEParticleFinder("pos"); 
	mle_particle_neg = new MLEParticleFinder("neg"); 

	run_number = Integer.valueOf(s_run);
	//f_name = file_name;

	in_file = temp_file;
	//System.out.println(" >> FILE TO ANALYZE " + in_file);
	//temphiporeader = temphiporeader2;
	

	if( in_file.exists() ){//  && file_to_analyze.length() >= max_file_size ){
	    System.out.println("File Exists");
	}
	if( !in_file.exists() ){// || file_to_analyze.length() <= max_file_size){
	    System.out.println("File DOES NOT Exists");
	    return;
	}


    }

    public void initConfig(int run_number){

	run_properties = new RunPropertiesLoader(run_number,dataOrSim_type);
	if( run_number < 0 ){
	    System.out.println(" >> ERROR NEED RUN ");
	    //run_properties.loadRunProperties(run_number);
	    //run_properties.setRunProperties();
	    //run_properties.writeRunProperties();
	}
	//List<String> my_el_cuts = cutconfig.el_cuts;
    }


    public void setThreadEventRange(int min, int max){
	min_event = min;
	max_event = max;      
    }

    public void setFileRange(int min, int max ){
	min_file = min;
	max_file = max;

    }

    public void completionStatus(int current_value){
	
	int limit = max_event/10; 
	if( current_value % limit == 0 ){
	    double percent_complete = (double)current_value/(double)max_event * 100.0;
	    DecimalFormat df = new DecimalFormat();
	    df.setMaximumFractionDigits(2);
	    System.out.println(">> " + threadName + " " + df.format(percent_complete) + "% @ CURRENT EVENT " + current_value ); 
	}

    }

    public void MultiFileAnalysis(String path_to_files, int min_file_num, int max_file_num){

	System.out.println("Running " +  threadName );
	try{
	    int nfile = min_file;
	    System.out.println(">> PROCESSING FILES BETWEEN " + nfile + " " + max_file);
	    while( nfile < max_file ){
		String s_file_to_analyze = path_to_files + Integer.toString(nfile) + ".hipo"; // "April_24_TorusSymmetric.dat.hipo";
		System.out.println(">> PROCESSING FILE " + s_file_to_analyze );
		HipoDataSource reader = new HipoDataSource();      

		//WRITE OUT PHYSICS EVENTS INTO A REC::PARTICLE BANK (CHEATING / WORKAROUND TO GET DATA OUT
		HipoDataSync writer = new HipoDataSync();
		String outdir = "/run/media/sdiehl/easystore1/brandon/test_skim_pip/";
		String out_file = outdir + "phys_clas_" + dataOrSim_type + "_" + Integer.toString(run_number) + "_" + Integer.toString(nfile) + ".hipo";
		String out_file_txt = outdir + "phys_clas_" + dataOrSim_type + "_" + Integer.toString(run_number) + "_" + Integer.toString(nfile) + ".txt";
		//writer.open(out_file);


		BufferedWriter buffwriter = new BufferedWriter( new FileWriter(out_file_txt) );//;ull;
		//try{
		    //buffwriter = new BufferedWriter( new FileWriter(out_file_txt) );
		    //}
		//	catch( IOException e ){
		//   System.out.println(" >> ERROR "  + e );
		//}

		if( !(new File(s_file_to_analyze).exists()) ){
		    nfile++;
		    System.out.println(">> FILE DOES NOT EXISTS " + s_file_to_analyze );
		    continue;
		}

		memStat();
		reader.open(new File(s_file_to_analyze));
		DataEvent event = null;
		int num_ev = 0;
		max_event = reader.getSize(); 
		System.out.println(">> EVENTS TO PROCESS " + max_event);

		while( num_ev < max_event ){
		    //System.out.println("Thread: " + threadName + ", " + num_ev);
		    event = (DataEvent)reader.gotoEvent(num_ev);		
		    boolean recBank_pres = event.hasBank("REC::Particle");

		    completionStatus(num_ev); //PRINT STATUS

		    if ( recBank_pres ){
			Vector<Boolean> v_el_tests = new Vector<Boolean>(); 
			DataBank recBank = event.getBank("REC::Particle");
			
			if( recBank.getInt("pid",0) == 11 ){
			    int status = recBank.getShort("status",0);
			    if( !(status >= 4000 || status <= 1999) ) { //continue;				
				h_pid_clas12.fillElectronCLAS12PID(event,0);
				h_pid_clas12.fillElectronCLAS12Comparison(0);

				for( int i = 0 ; i < recBank.rows(); i++ ){
				    int charge = recBank.getInt("charge",i);
				    if( charge > 0 && i != 0){
					h_pid_mle.fillBetaAll(event,i);
					h_pid_mle.fillDCTraj(event,i);
				    }
				    if( charge < 0 && i != 0 ){
					h_pid_mle.fillBetaAllNeg(event,i);
					h_pid_mle.fillDCTrajNeg(event,i);
				    }

				}
			    }
			}


			//for( int i = 0 ; i < recBank.rows(); i++ ){
			//  int charge = recBank.getInt("charge",i);
			//  if( charge < 0 ){
			//  }				    
			//}

			boolean good_mle_pr = false;
			boolean good_mle_kp = false;
			boolean good_mle_km = false;
			boolean el_present = false;
			int good_el = -1;

			int clas12_el = -1;
			//if( recBank.getInt("charge",0) == -1 && recBank.getInt("pid",0) == 11 ){
			//   clas12_el = recBank.getInt("pid",0);				
			//}
			//System.out.println(" >> EVENT NUMBER " + num_ev );
			//System.out.println(" >> GETTING HASH MAP RESULT " );
			/*
			HashMap<Boolean,Integer> m_final_el = find_el.getResult(event);
			//System.out.println(" >> FINAL RESULT FROM MAP " + m_final_el);
			for( Map.Entry<Boolean,Integer> entry : m_final_el.entrySet() ){
			  boolean el_test_result = entry.getKey();
			  int el_result_index = entry.getValue();
			  if( el_test_result ){
			      h_pid_cutlvls.fillComparisonPID( event,  el_result_index );
			      good_el = el_result_index;
			  }
			}
			*/
			//System.out.println(" >> GETTING VECTOR RESULT " );
			for( int k = 0; k < recBank.rows(); k++ ){
			    ///
			    //  ADDING STATUS TO REMOVE PARTICLES FROM THE CENTRAL DETECTORS
			    //
			    int status = recBank.getShort("status",k);
			    if( status >= 4000 || status <= 1999 ) continue;			
			    v_el_tests = find_el.processCutsVector(event, k);
			    //System.out.println(">> RESULT VECTOR " + v_el_tests );
			    // Let the thread sleep for a while.
			    //h_pid_cutlvls.FillElectronPID( v_el_tests, event, k );
			    int clas12_el_test  = recBank.getInt("pid",k);
			    if( clas12_el_test == 11 ){
				//h_pid_clas12.fillElectronEB(k);
			    }			    			    
			}

			/*
			if( recBank.getInt("pid",0) != 11 && good_el >= 0 ){
			    h_pid_clas12.fillElectronPIP_NEB(good_el);
			}

			if( recBank.getInt("pid",0) == 11 && good_el >= 0 ){
			    h_pid_clas12.fillElectronPIP_EB(good_el);
			}
			*/

 
			  //if( el_result_index == clas12_el ){
			  //MAKE HISTOGRAM TO LOG WHEN PIP INDEX MATCHES CLAS12 EB AND WHEN IT DOES NOT MATCH
			  //}
			  
			//}
			
			double pr_e = -1.0;
			double kp_e = -1.0;
			double km_e = -1.0;
			int good_kp = -1;
			int good_pr = -1;
			int good_km = -1;

			//System.out.println( " >> PROCESSING EVENT " + num_ev );
			HashMap<Boolean,Integer> m_final_el = find_el.getResult(event);			
			for( Map.Entry<Boolean,Integer> entry : m_final_el.entrySet() ){
			  boolean el_test_result = entry.getKey();
			  int el_result_index = entry.getValue();
			    
			  if( el_test_result ){
			      //TO COMPARE AGAINST EVENTBUILDER PID
			      //System.out.println(" >> GOOD ELECTRON PRESENT " );
			      h_pid_cutlvls.fillComparisonPID(event, el_result_index );
			      good_el = el_result_index;
			      el_present = true;
			      for( int m = 0; m < recBank.rows(); m++ ){
				    int charge = recBank.getInt("charge",m);
				    int status = recBank.getShort("status",m);
				    if( status >= 4000 || status <= 1999 ) continue;			


				    if( charge < 0 && m != el_result_index ){
					BEventWriter bwriter = new BEventWriter();
					BEvent bevent = bwriter.getBEvent(event);
					bwriter.loadBankMaps(event);
					BEventLoader bload = new BEventLoader(event);
					bload.setBEventInfo();
					BEventInfo bevinf = bload.getBEventInfo();
					bwriter.loadBankMaps(event);				    
				    
 					Particle mle_part_neg = mle_particle_neg.getMLEParticle(bevinf,m);
					double conf_part_neg = mle_part_neg.getProperty("conflvl");
					double le_part_neg = mle_part_neg.getProperty("likelihood");
					double pid_mle_neg = mle_part_neg.getProperty("pid");

					Particle mle_kaonm = mle_particle_neg.getKaonMinus(bevinf,m);
					Particle mle_pionm = mle_particle_neg.getPionMinus(bevinf,m);

 					double km_conf = mle_kaonm.getProperty("conflvl");
					double pim_conf = mle_pionm.getProperty("conflvl");

					h_pid_mle.fillKaonMinusConfidenceLevel(mle_kaonm.getProperty("conflvl"));
					h_pid_mle.fillPionMinusConfidenceLevel(mle_pionm.getProperty("conflvl"));
					
					//System.out.println(" >> MLE NEGATIVE PARTICLE PID " + pid_mle_neg + " CONF " + conf_part_neg + " INDEX " + m );
					//System.out.println(" >> KAON MINUS " + km_conf );
					//System.out.println(" >> PION MINUS " + pim_conf );

					if( (int)pid_mle_neg == -321 && conf_part_neg >= 0.27 && pim_conf <= 0.73 ){
					    h_pid_mle.fillKaonMinusMLE(event,m);

					    double temp_km_e = Calculator.lv_energy(recBank,m,321);
					    if( temp_km_e > km_e ){ 
						km_e = temp_km_e;
						good_km = m;
						good_mle_km = true;
						System.out.println(" >> ITS A KAON MINUS " );

					    }
					}
					//else if( (int)pid_mle_neg == -211 && conf_part_neg >= 0.27 && km_conf <= 0.73){
					//    System.out.println(" >> ITS A PION  MINUS " );
					//}


				    }

				    
				    else if( charge > 0 && m != el_result_index ){
					BEventWriter bwriter = new BEventWriter();
					BEvent bevent = bwriter.getBEvent(event);
					bwriter.loadBankMaps(event);
					BEventLoader bload = new BEventLoader(event);
					bload.setBEventInfo();
					BEventInfo bevinf = bload.getBEventInfo();
					bwriter.loadBankMaps(event);				    
				    
					Particle mle_part = mle_particle.getMLEParticle(bevinf,m);
					double conf_part = mle_part.getProperty("conflvl");
					double le_part = mle_part.getProperty("likelihood");
					double pid_mle = mle_part.getProperty("pid");

					//System.out.println(" >> MLE PARTICLE PID " + pid_mle + " CONF " + conf_part + " INDEX " + m );
					Particle mle_proton = mle_particle.getProton(bevinf,m);
					Particle mle_kaon = mle_particle.getKaonPlus(bevinf,m);
					Particle mle_pion = mle_particle.getPionPlus(bevinf,m);

					double pr_conf = mle_proton.getProperty("conflvl");
 					double kp_conf = mle_kaon.getProperty("conflvl");
					double pip_conf = mle_pion.getProperty("conflvl");

					//h_pid_mle.fillDCTraj(event, m);
					
					h_pid_mle.fillProtonConfidenceLevel(mle_proton.getProperty("conflvl"));
					h_pid_mle.fillKaonConfidenceLevel(mle_kaon.getProperty("conflvl"));
					h_pid_mle.fillPionPlusConfidenceLevel(mle_pion.getProperty("conf_lvl"));
 					h_pid_mle.fillDCTraj(event,m);
					
					if( (int)pid_mle == 321 && conf_part >= 0.27 && pr_conf <= 0.73 && pip_conf <= 0.73 ){
					    good_kp = m;
					    //tempn_kp = tempn_pr + 1;
					    //fast_kaon.add(m);
					    //  System.out.println(" >> MLE KAON PID " + pid_mle + " CONF " + conf_part );
					    ///System.out.println(" >> VALUES OF CONF LVL FOR PARTICLES ");
					    //System.out.println(" >> Pr: " + pr_conf);
					    //System.out.println(" >> Kp: " + kp_conf);
					    //System.out.println(" >> Pip: " + pip_conf);
					    h_pid_mle.fillKaonMLE( event, good_kp ); 
					    double temp_kp_e = Calculator.lv_energy(recBank,m,321);
					    if( temp_kp_e > kp_e ){ 
						kp_e = temp_kp_e;
						good_kp = m;
						good_mle_kp = true;
					    }
					}
					
					if( (int)pid_mle == 2212 && conf_part >= 0.27 && kp_conf <= 0.73 && pip_conf <= 0.73 ){
					    good_pr = m;
					    //tempn_pr = tempn_pr + 1;
					    //fast_proton.add(m);
			 		    //System.out.println(" >> MLE PROTON PID " + pid_mle + " CONF " + conf_part );
					    //System.out.println(" >> VALUES OF CONF LVL FOR PARTICLES ");
					    //System.out.println(" >> Pr: " + pr_conf);
					    //System.out.println(" >> Kp: " + kp_conf);
					    //System.out.println(" >> Pip: " + pip_conf);
					    h_pid_mle.fillProtonMLE( event, good_pr ); 
					    double temp_pr_e = Calculator.lv_energy(recBank,m,2212);
					    if( temp_pr_e > pr_e ){ 
						pr_e = temp_pr_e; 
						good_pr = m; 
						good_mle_pr = true;
					    }
					}				  				    										
				    }
				}
			  }
		        }
			
			//System.out.println(" >> PR " + good_pr + " KP " + good_kp );
			//if( good_pr > 0 ) System.out.println( " GOOD PROTON " + good_pr ); //h_pid_mle.fillProtonMLE( event, good_pr ); 
			//if( good_kp > 0 ) System.out.println( " GOOD KAON " + good_kp ); // h_pid_mle.fillKaonMLE( event, good_kp ); 
			//if( good_kp > 0 && good_pr > 0 ) System.out.println( " GOOD PROTON " + good_pr + " GOOD KP " + good_kp );
			int eventtopology = -1;
			int golden_el_index = -1;
			int golden_pr_index = -1;
			int golden_kp_index = -1;
			int golden_km_index = -1;
			boolean real_event = false;
			
			//System.out.println(" >> RESULTS OF EVENT " + num_ev + " ELECTRON RESULT: " + el_present + " PROTON RESULT: " + good_mle_pr + " KAON RESULT: " + good_mle_kp );
			/*if( el_present && good_mle_pr && !good_mle_kp) {
			      System.out.println(" >> GOOD ELECTRON AND PROTON PRESENT, NO KAON " );
			}
			else if( el_present && good_mle_kp && !good_mle_pr ){
			    System.out.println(" >> GOOD ELECTRON AND KAON PRESENT, NO PROTON " );
			}
			else if( el_present && good_mle_pr && good_mle_kp ){
			    System.out.println(" >> GOOD ELECTRON AND PROTON PRESENT AND KAON PRESENT " );
			}
			else if( el_present && good_mle_pr ){
			    System.out.println(" >> GOOD ELECTRON AND GOOD PROTON " );
			}
			else if( el_present && good_mle_kp ){
			    System.out.println(" >> GOOD ELECTON AND GOOD KAON " );
			}
			    
			if( good_el >= 0 && good_pr > 0 && good_kp > 0 ){
			    //eventtopology = 2;
			    real_event = true;
			    
			    golden_el_index = good_el;
			    golden_pr_index = good_pr;
			    golden_kp_index = good_kp;			    
			    
			}
			*/
			if( el_present && good_mle_pr && good_mle_kp ){
			    System.out.println(" >> STATUS OF KAON MINUS " + good_mle_km );
			}

			LorentzVector lv_el = new LorentzVector(0,0,0,0);
			LorentzVector lv_pr = new LorentzVector(0,0,0,0);
			LorentzVector lv_kp = new LorentzVector(0,0,0,0);
			LorentzVector lv_km = new LorentzVector(0,0,0,0);
						
			//if( el_present ){
			//  eventtopology = 0;
			//  lv_el = Calculator.lv_particle(recBank, golden_el_index, 11);			    
			//}

			if( el_present && good_mle_pr && !good_mle_kp ){
			    eventtopology = 0;
			    lv_el = Calculator.lv_particle(recBank, good_el, 11);			    
			    lv_pr = Calculator.lv_particle(recBank, good_pr, 2212);
			}
			else if( el_present && good_mle_kp && !good_mle_pr ){
			    eventtopology = 1;
			    lv_el = Calculator.lv_particle(recBank, good_el, 11);			    
			    lv_kp = Calculator.lv_particle(recBank, good_kp, 321);
			}
			else if( el_present && good_mle_pr && good_mle_kp ){
			    eventtopology = 2;
			    lv_el = Calculator.lv_particle(recBank, good_el, 11);			    
			    lv_kp = Calculator.lv_particle(recBank, good_kp, 321);
			    lv_pr = Calculator.lv_particle(recBank, good_pr, 2212);
			}
			else if( el_present && good_mle_pr && good_mle_kp && good_mle_km ){
			    eventtopology = 4;

			    System.out.println(" >> --------------------------------------------------------------------------  << " );
			    System.out.println(" >>                       PHI EVENT YEAH! " );

			    lv_el = Calculator.lv_particle(recBank,good_el,11);
			    lv_kp = Calculator.lv_particle(recBank, good_kp, 321);
			    lv_km = Calculator.lv_particle(recBank, good_km, 321);
			    lv_pr = Calculator.lv_particle(recBank, good_pr, 2212);
			}
			   

			if( eventtopology >= 0 ){
			    real_event = true;
			    //DataEvent physics_event = writer.createEvent();
			    //DataBank physics_bank = physics_event.createBank("REC::Particle", 10);// (int)(eventtopology+1));

			    //physics_bank.setInt("pid",0,(int)11);
			    
			    //physics_event.appendBank(physics_bank);
			    //writer.writeEvent(physics_event);

			    //physics_event.show();

			    /*
			    try{
				int helicity = -10;
				if( event.hasBank("REC::Event") ){
				    helicity = event.getBank("REC::Event").getInt("Helic",0);
				}
				//System.out.println(" >> ELECTRON PX " + lv_el.px() );
				buffwriter.write( Integer.toString(run_number ) + " " + Integer.toString(eventtopology) + " " + Integer.toString(helicity) + " "  +  Double.toString(lv_el.px()) + " " + Double.toString(lv_el.py()) + " " + Double.toString(lv_el.px()) + " " + Double.toString(lv_pr.px()) + " " + Double.toString(lv_pr.py()) + " " + Double.toString(lv_pr.pz()) + " " + Double.toString(lv_kp.px()) + " " + Double.toString(lv_kp.py()) + " " + Double.toString(lv_kp.pz()) + " " + Double.toString(lv_km.px()) + " " + Double.toString(lv_km.py()) + " " + Double.toString(lv_km.pz()) + "\n");

			    }
			    catch(IOException e ){
				System.out.println(" ERROR WRITING " + e );
			    }
			   
			    
			
			    */
			}

			if( good_el >= 0 && good_pr > 0 ){
			    //LorentzVector lv_beam = new LorentzVector(0,0,PhysicalConstants.eBeam,PhysicalConstants.eBeam);
			    //LorentzVector target = new LorentzVector(0,0,0,PhysicalConstants.mass_proton);
			    //LorentzVector lv_el = Calculator.lv_particle(recBank, golden_el_index, 11);
			    //LorentzVector lv_pr = Calculator.lv_particle(recBank, golden_pr_index, 2212);
			    
			    //h_phys.fillMMepX( lv_beam, target, lv_el, lv_pr );

			}

			if ( real_event && eventtopology == 4 ){
			    PhysicsBuilder physicsbuild = new PhysicsBuilder();
			    PhysicsEvent final_phy_event = physicsbuild.setPhysicsEvent( real_event, event, good_el, good_pr, good_kp, good_km, eventtopology );
			    h_phys.fillPhysicsEventHistograms(final_phy_event);

			}
			//if( good_el >= 0 ){ //HAVE A GOOD ELECTRON IN THE EVENT 
	
			//if( pr_e >= 0.0 && kp_e >= 0.0 ){ // HAVE GOOD PROTON AND KAON IN EVENT
				//System.out.println(" >> GOOD EL " + good_el  + " GOOD PR " + good_pr + " GOOD KP " + good_kp );
				//System.out.println(" >> GOOD PR E " + pr_e + " GOOD KP E " + kp_e );

				//System.out.println("WE CAN WRITE TO A PHYSICS FILE HERE FOR LATER ANALYSIS " );
				//h_pid_mle.fillPhiTest(event, good_el, good_pr, good_kp);

			//  }
			//}
			

			
		    }
		    num_ev++;
		    //System.out.println(" >> " + num_ev);
		}
		Thread.sleep(1); //THREAD CONTAINS FILES
		System.out.println(">> " + threadName + " COMPLETED " + nfile + " OF " + max_file + " FILES " );
		nfile++;
		reader = null;
		//writer.close();

		/*try{
		    if( buffwriter != null ){
			buffwriter.close();
		    }
		}
		catch(IOException e){
		    System.out.println(" >> ERROR CLOSING FILE " + e );
		}

		*/
	    }   	    
	}
	catch (Throwable e) {
	    System.out.println("Thread " +  threadName + " interrupted. THROWABLE " + e);
	}
	System.out.println("Thread " +  threadName + " exiting.");

    }
    
    public void SingleFileAnalysis(String file_name ){

	System.out.println("Running SINGLE FILE ANALYSIS " +  threadName );
	try{
	    String s_file_to_analyze = file_name;//path_to_files + Integer.toString(nfile) + ".hipo";
	    
	    //HipoDataSource reader = temphiporeader; //new HipoDataSource(); 	
	    temphiporeader.open(in_file);
	    DataEvent event = null;
	    int num_ev = min_event;
	    System.out.println(">> EVENTS TO PROCESS FROM " + num_ev + " TO " + max_event);
	    while( num_ev < max_event ){
		//System.out.println("Thread: " + threadName + ", " + num_ev);
		event = (DataEvent)temphiporeader.gotoEvent(num_ev);		
		boolean recBank_pres = event.hasBank("REC::Particle");
		boolean eventBank_pres = event.hasBank("REC::Event");
		double start_time = -1000;

		if ( recBank_pres && eventBank_pres ){
		    DataBank eventBank = event.getBank("REC::Event");
		    start_time = eventBank.getFloat("STTime",0);		    
		    
		    if( recBank_pres && start_time > 0 && event.getBank("REC::Particle").rows() >= 0 ){
			DataBank recBank = event.getBank("REC::Particle");		

			if( recBank.getInt("pid",0) == 11 ){
			    h_pid_clas12.fillElectronCLAS12PID(event,0);
			}

			Vector<Boolean> v_el_tests = new Vector<Boolean>(); 
			for( int k = 0; k < recBank.rows(); k++ ){
			    v_el_tests = find_el.processCutsVector(event, k);
			    //System.out.println(">> RESULT VECTOR " + v_el_tests );
			    // Let the thread sleep for a while.
			    //h_pid_cutlvls.FillElectronPID( v_el_tests, event, k );
			}
		    }
		}
		num_ev++;
		Thread.sleep(1); //THREAD CONTAINS EVENTS
	    }	
	}catch (Throwable e) {
	    System.out.println("Thread " +  threadName + " interrupted.");
	}
	System.out.println("Thread " +  threadName + " exiting.");

    
    }


    public void saveHisto(){
	boolean view_phys = false;
	System.out.println(">> SAVING HISTOGRAMS");
	h_pid_cutlvls.savePIDHistograms(view_phys);
	h_pid_clas12.saveCLAS12PIDHistograms(view_phys);
	h_pid_mle.saveMLEHistograms(view_phys);
	h_phys.savePhysHistograms(view_phys);
	System.out.println(">> COMPLETED SAVING HISTOGRAMS");

    }

    
    public void run(){

	if( typeAnalysis ){
	    System.out.println(">> SINGLE FILE ANALYSIS ");
	    SingleFileAnalysis( f_name );

	}
	else{
	    System.out.println(">> MULTI FILE ANALYSIS ");	    
	    MultiFileAnalysis( f_name, min_file, max_file );

	}


    }

    /*
    public void run(){
	System.out.println("Running " +  threadName );
	try{
	    HipoDataSource reader = new HipoDataSource();
	    int nfile = min_file;
	    System.out.println(">> STARTING WITH FILE NUMBER " + nfile );
	    while( nfile < max_file){
		String s_file_to_analyze = "/lustre/expphy/volatile/halla/sbs/bclary/clas12Analysis/SKIMclas12/skim_00"+Integer.toString(run_number)+"_pass1/"+"skim_clas_00"+Integer.toString(run_number)+".evio."+Integer.toString(nfile) + ".hipo";
		
		reader.open( new File(s_file_to_analyze) );
		DataEvent event = null;
		int num_ev = min_event;
		max_event = reader.getSize(); 
		System.out.println(">> EVENTS TO PROCESS " + max_event);
		while( num_ev < max_event ){
		    //System.out.println("Thread: " + threadName + ", " + num_ev);
		    event = (DataEvent)reader.gotoEvent(num_ev);
		    
		    boolean recBank_pres = event.hasBank("REC::Particle");
		    
		    if ( recBank_pres ){
			Vector<Boolean> v_el_tests = new Vector<Boolean>(); 
			DataBank recBank = event.getBank("REC::Particle");
			
			for( int k = 0; k < recBank.rows(); k++ ){
			    v_el_tests = find_el.processCutsVector(event, k);
			    //System.out.println(">> RESULT VECTOR " + v_el_tests );
			    // Let the thread sleep for a while.
			    h_pid_cutlvls.FillElectronPID( v_el_tests, event, k );
			}
		    }
		    num_ev++;
		}
		Thread.sleep(1);
		nfile++;
	    }


	}catch (InterruptedException e) {
	    System.out.println("Thread " +  threadName + " interrupted.");
	}
	System.out.println("Thread " +  threadName + " exiting.");
    }
    */
    
    public void start(){
	System.out.println("Starting " +  threadName );
	if (t == null) {
	    t = new Thread (this, threadName);
	    t.start ();
	}
    }

    public void join(){
	try
	    {
		t.join();
	    }
	catch (Throwable e)
	    {
		System.out.println("Interrupt Occurred");
		e.printStackTrace();
	    }

    }

    public void memStat(){
	Runtime rt = Runtime.getRuntime();
	rt.gc();
	long mem = rt.totalMemory() - rt.freeMemory();
	System.out.println(">> USED IN bytes " + mem);
    }

}
