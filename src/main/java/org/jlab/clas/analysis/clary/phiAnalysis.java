package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import com.google.gson.*;
import org.json.*;
import org.jlab.jnp.utils.json.*;


import org.jlab.io.hipo.HipoDataSource;
import org.jlab.jnp.hipo.io.HipoWriter;
import org.jlab.clas.physics.LorentzVector;
import org.jlab.clas.physics.Particle;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import java.text.DecimalFormat;

public class phiAnalysis{

    public static void main( String[] analysisInfo ){
	EventProcessor process = new EventProcessor();

	CoolText coolprint = new CoolText();	
	/////////////////////////////////////////////////////
	//
	// INPUT INFORMATION
	//
	//////////////////////////////////////////////////////////
	String inName = analysisInfo[0];
	String s_run_number = analysisInfo[1];
	int nth_file = Integer.valueOf(analysisInfo[2]); //FILE TO START AT 
	int num_files_process = Integer.valueOf(analysisInfo[3]);
	int end_file = nth_file + num_files_process;
	int max_file = num_files_process;// Integer.valueOf(analysisInfo[4]); //FILE TO STOP AT
	
	String dataSim = analysisInfo[4];

	coolprint.printToScreen(">> STARTING ANALYSIS FOR RUN " + s_run_number + " FOR " + dataSim + " PROCESS FILES FROM "  + analysisInfo[0] + " STARTING FILE " + analysisInfo[2] + " ENDING AT " + Integer.toString(end_file), "red" );

	//////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////
	

	System.out.println(">> SIM OR DATA? " + dataSim );
	int run_number = Integer.valueOf(s_run_number);

       	String file_location = "/lustre/expphy/volatile/halla/sbs/bclary/clas12Analysis/RECclas12/rec_clary_norad_phi/";
	String file_location2 = "/lustre/expphy/volatile/halla/sbs/bclary/clas12Analysis/RECclas12/rec_fx_norad_phi/";///volatile/clas12/fxgirod/phi/prod_phi_tp1_s1/";
	String file_location3 = "/volatile/halla/sbs/bclary/clas12Analysis/RECclas12/rec_markov_flat_accp/";
	String file_location4 = "/lustre/expphy/volatile/clas12/data/pass0_5/cooked/";
	String file_location5 = "/volatile/clas12/data/TorusFieldTest/";
	String file_location6 = "/volatile/clas12/data/online/cooked/";
	String file_location7 = "/volatile/clas12/data/pass0/cooked/";
 	String file_location8 = "/volatile/clas12/bclary/clas12RunData/RGAclas12/";
	String file_location9 = "/volatile/clas12/mkunkel/DeepLearning/PID/ReconstructedFiles/Torus1.0Sol0.8/PiPlus/";
	String file_location10 = "/volatile/clas12/kenjo/bclary/";
	String file_location11 = "/volatile/clas12/data/rg-a/calibration/recooked/";
	String file_location12 = "/lustre/expphy/volatile/halla/sbs/bclary/clas12Analysis/SKIM/";
	String file_location13 = "/lustre/expphy/volatile/halla/sbs/bclary/clas12Analysis/SKIMclas12/skim_00"+s_run_number+"_pass1/";
	String file_location14 = "/volatile/clas12/data/rg-a/pass0/tag5b.3.3/"; ///volatile/clas12/data/rg-a/pass0/tag5b.3.3/
 	String file_location15 = "/lustre/expphy/volatile/clas/clase1/markov/12GeVANA/newMF/";
	String file_location16 = "/home/bclary/data/clas12_data/skimclas12/skim_"+s_run_number+"/";
	String file_location17 = "/run/media/sdiehl/easystore1/brandon/";
	//String file_location16 = "/volatile/halla/sbs/bclary/clas12Analysis/SKIMclas12/skim_00"+s_run_number+"_pass1/";
	String file_loc = ""; 
	String fieldconf = "";
	String file_name = "";
	String file_ext = "";
	String f_type = "";
	boolean skip = false;

	if( inName.equals("Clary") ){
	    file_loc = file_location;
	    fieldconf = "/u/home/bclary/CLAS12/phi_analysis/PIX_clary/";
	    file_name = "out_phi_lund_";
	    file_ext = ".hipo";
	    f_type = "clary_pid_phi_";
	}
	else if( inName.equals("FX") ){
	    file_loc = file_location2;
	    fieldconf = "/u/home/bclary/CLAS12/phi_analysis/PIX_fx/";
	    file_name = "out_phi_lund_";
	    file_ext = ".hipo";
	    f_type = "pid_phi_";
	}
	else if( inName.equals("AC") ){
	    file_loc = file_location3;
	    file_name = "out_flatLund";//flatCJ4.8.5";
	    file_ext = ".hipo";
	    f_type = "accp_phi_";				
	}
	else if( inName.equals("RES") ){
	    file_loc = file_location9;
	    file_name = "out_PiPlus_";
	    file_ext = ".hipo";
	    f_type = "proton_res_";
	    skip = true;
	}
	else if( inName.equals("CLAS12") ){
	    file_loc = file_location14;
	    file_name = "out_clas_00"+Integer.toString(run_number)+".evio.";
	    file_ext = ".hipo";
	    f_type = "clas12_";
	}
	else if( inName.equals("SCLAS12") ){
	    file_loc = file_location13;
	    //file_name = "phys2_3050.hipo";
	    file_name = "skim_clas_00"+Integer.toString(run_number)+".evio.";
	    file_ext = ".hipo";
	    f_type = "clas12_";
	}
	else if( inName.equals("MCLAS12") ){
	    file_loc = file_location15;
	    file_name = "out_clas_002476.evio.";
	    file_ext = ".hipo";
	}
	else if( inName.equals("MACHINE") ){
	    file_loc = file_location17;
	    file_name = "skim_clas_00"+Integer.toString(run_number)+".evio.";	       
	    file_ext = ".hipo";
	}
	else if( inName.equals("NICK") ){
	    file_loc = analysisInfo[5];
	    file_name = "out_inclusive2FileSolenoid0.6.Torus0.6"; //"out_inclusive12FileSolenoid-1.Torus-1InclusiveNonElastic";
	    file_ext = "April_24_TorusSymmetric.dat.hipo";
	}
	else{	    
	    file_loc = analysisInfo[5];
	    file_name =  analysisInfo[6];
	    file_ext = ".hipo"; //JUST TAKE FILE INFO FROM INPUT PARAMETERS BC IT KEEPS CHANGING TOO MUCH
	}
	int count = 0;
	
	/////////////////////////////
	//
	//   RATE COUNTERS
	//
	////////////////////////////
 	int tot_el_pass = 0;
 	int tot_pr_pass = 0;
 	int tot_kp_pass = 0;
 	int tot_km_pass = 0;

	int tot_epkpkm_pass = 0;
	int tot_ep_pass = 0;
	int tot_epkp_pass = 0;
	int tot_epkm_pass = 0;
	int tot_ekpkm_pass = 0;
	int tot_all_pass = 0;

	int tot_gen_events = 0;
	int tot_rec_events = 0;

	double fc_integratedcharge = 0.0;

	////////////////////////////
	//
	//   INIT CLASSES & CUTS
	//
 	////////////////////////////
	RunPropertiesLoader run_properties = new RunPropertiesLoader(run_number,dataSim);

	System.out.println(" LOADING CUTS" );
 	CutLoader cut_loader = new CutLoader(run_number,dataSim,"cut_nom");
	System.out.println(" >> LOADING CUT LIST " );
	CutConfig cutconfig = new CutConfig("/home/bclary/CLAS12/phi_analysis/v3/v2/v1/run_db/cut_config.json");	

	
	System.out.println(" >> " + cutconfig.getElectronCuts());
	System.out.println(" >> " + cutconfig.getProtonCuts());
	
	List<String> my_el_cuts = cutconfig.getElectronCuts();
	 	             		    
	String jobName = dataSim + "_" + Integer.toString(end_file);
	String threadName = jobName;

	////////////////////////////
	//
	//   INIT HISTOGRAMS
	//
 	////////////////////////////

	BHistoPIDLevel h_pid_cutlvls = new BHistoPIDLevel(run_number, threadName);       
	BHistoCLAS12PID h_pid_clas12 = new BHistoCLAS12PID(run_number, threadName);
	BHistoMLE h_pid_mle = new BHistoMLE(run_number,threadName);
	BHistoPhys h_phys = new BHistoPhys(run_number,threadName);

	h_pid_cutlvls.CreateHistograms();
	h_pid_clas12.createHistograms();
	h_phys.createHistograms();

	////////////////////////////
	//
	//   INIT FINDERS
	//
 	////////////////////////////

	ElectronPID find_el = new ElectronPID(my_el_cuts);
 
	//CLAS12ElectronPID clas12_el = new CLAS12ElectronPID();
	//CLAS12ProtonPID clas12_pr = new CLAS12ProtonPID();
	//CLAS12KaonPlusPID clas12_kp = new CLAS12KaonPlusPID();
	//CLAS12KaonMinusPID clas12_km = new CLAS12KaonMinusPID();
	
	MLEParticleFinder mle_particle = new MLEParticleFinder("pos"); 
	MLEParticleFinder mle_particle_neg = new MLEParticleFinder("neg"); 
	       
	long startTime = System.currentTimeMillis();

	String f_prefix = file_loc + file_name;
 	int total_nevents = 0;



	////////////////////////////
	//
	// START PID ANALYSIS 
	//
	////////////////////////////
	int f_counter = 0;
	coolprint.printToScreen(">> BEGINNING LOOP OVER FILES NOW:","red");

	while( nth_file < end_file ){	    

	    ////////////////////////////
	    //
	    // LOAD FILES WITHIN RANGE nth_file to max_file 
	    //
	    ////////////////////////////


	    String in_file = file_loc + file_name + Integer.toString(nth_file) + file_ext ;
	    File file_to_analyze = new File(in_file);
	    //double max_file_size = 200000000;
	    System.out.println(" >> FILE TO ANALYZE " + in_file);

	    if( file_to_analyze.exists() ){//  && file_to_analyze.length() >= max_file_size ){
		System.out.println("File Exists");
		f_counter+=1;
	    }
	    if( !file_to_analyze.exists() ){// || file_to_analyze.length() <= max_file_size){
		System.out.println("File DOES NOT Exists");
		nth_file++;
		continue;
	    }
	    
	    
	    HipoDataSource reader = new HipoDataSource();
	    reader.open(in_file);
	    int counter = 0;
	    DataEvent nullevent = reader.getNextEvent();

	    //CREATING OUTPUT TXT FILE
	    String outdir = "/run/media/sdiehl/easystore1/brandon/test_skim_pip/";
	    String out_file = outdir + "phys_clas_" + dataSim + "_" + Integer.toString(run_number) + "_" + Integer.toString(nth_file) + ".hipo";
	    String out_file_txt = outdir + "phys_clas_" + dataSim + "_" + Integer.toString(run_number) + "_" + Integer.toString(nth_file) + ".txt";	    	    

	    BufferedWriter buffwriter = null;
	    try{
		buffwriter = new BufferedWriter( new FileWriter(out_file_txt) );
	    }
	    catch ( IOException e){           
		System.out.println(">> ERROR CREATING TO FILE ");
	    }	
	    
	    
	    coolprint.printToScreen(">> BEGINNING EVENT LOOP NOW:","blue");
	    nth_file++; //INCREASE FILE NUMBER HERE 

	    int max_event = reader.getSize(); 
	    int limit = max_event/10; 
	    int current_value = 0;
	    ////////////////////////////
	    //
	    // START EVENT LOOP NOW 
	    //
	    ////////////////////////////
	    
	    while( reader.hasEvent() ){
	      DataEvent event = reader.getNextEvent();


	      if( current_value % limit == 0 ){
		  double percent_complete = (double)current_value/(double)max_event * 100.0;
		  DecimalFormat df = new DecimalFormat();
		  df.setMaximumFractionDigits(2);
		  System.out.println(">> " + threadName + " " + df.format(percent_complete) + "% @ CURRENT EVENT " + current_value ); 
	      }
	      current_value++;
	      //System.out.println(" >> PROCESSING EVENT " + current_value);

	      //BEvent bevent = new BEvent();
	      //bevent.setEvent(event);
	      
	      ////////////////////////////////////////////////////////
	      //REFACTORING TESTS
	      //BEventWriter bwriter = new BEventWriter();
	      //BEvent bevent = bwriter.getBEvent(event);
	      
	      //bwriter.loadBankMaps(event);
	      
	      boolean recBank_pres = event.hasBank("REC::Particle");
	      boolean eventBank_pres = event.hasBank("REC::Event");
	      boolean scintBank_pres = event.hasBank("REC::Scintillator");
	      boolean calBank_pres = event.hasBank("REC::Calorimeter");
	      
	      boolean recBank_present = event.hasBank("REC::Particle");
	      boolean genBank_present = event.hasBank("MC::Particle");
	      boolean eventBank_present = event.hasBank("REC::Event");
	      
	      //System.out.println(" >> EVENT " + counter );
	      
	      float start_time = -1000;
	      if( eventBank_present && recBank_present ) {
		  DataBank eventBank = event.getBank("REC::Event");
		  start_time = eventBank.getFloat("STTime",0);		    
	      }
	      
	      //PhysicsBuilder physicsbuild = new PhysicsBuilder();
	      	      
	      //double fc_bcurrent = Calculator.faradayCupCurrent(event);
	      //double fc_event_integratedcharge = Calculator.faradayCupIntegratedCharge(event);
	      //fc_integratedcharge = fc_integratedcharge + fc_event_integratedcharge; 
	      //System.out.println(" >> " + fc_bcurrent + " " + fc_integratedcharge);


	      /*
	      
	      ///	      clas12_el_test = clas12_el.processCuts(event, 0);
	      //if( clas12_el_test ){
	      //  int rec_pid = recBank.getInt("pid",0);	
	      //  if( rec_pid == 11 ){			
	      //     for( int reci = 1; reci < recBank.rows(); reci++ ){
	      //	  //clas12_pr_test = clas12_pr.processCuts(event, reci);
	      //if( clas12_pr_test ){
	      //	  int rec_hadron_pid = recBank.getInt("pid",reci);
	      
	      //	  if( rec_hadron_pid  == 2212 ){
	      //	      h_clas12_pid.fillProtonCLAS12PID(event, reci);
	      //	  }
	      //	  else if( rec_hadron_pid == 321 ){
	      //	      h_clas12_pid.fillKaonPCLAS12PID(event, reci);
	      //	  }
	      //	  else if( rec_hadron_pid == 211 ){
	       	      h_clas12_pid.fillPionPCLAS12PID(event, reci);
		      }
		      //}
		      }
		  }
		  //h_clas12_pid.fillElectronCLAS12PID( event, 0 );			
		  }
	      */    
	      
	      //BEvent bev = new BEvent();
	      //BEventWriter bwriter = new BEventWriter();
	      /// BEventLoader bload = new BEventLoader(event);
	      //bload.setBEventInfo();
	      //BEventInfo bevinf = bload.getBEventInfo();
	      //bwriter.loadBankMaps(event);
	      
	      
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
		
		
		boolean good_mle_pr = false;
		boolean good_mle_kp = false;
		boolean good_mle_km = false;
		boolean el_present = false;
		int good_el = -1;
		
		int clas12_el_index = -1;
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
		
		for( int k = 0; k < recBank.rows(); k++ ){
		    ///
		    //  ADDING STATUS TO REMOVE PARTICLES FROM THE CENTRAL DETECTORS
		    //
		    int status = recBank.getShort("status",k);
		    if( status >= 4000 || status <= 1999 ) continue;			
		    v_el_tests = find_el.processCutsVector(event, k);
		    
		    ///System.out.println(">> RESULT VECTOR " + v_el_tests );
		    // Let the thread sleep for a while.
		    h_pid_cutlvls.FillElectronPID( v_el_tests, event, k );
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
				
				//System.out.println( " >> NEGATIVE PARTICLE IDD index " + m );
				
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
				
				//System.out.println( " >> NEG PARTICLE IS " + pid_mle_neg );
				
				//System.out.println(" >> MLE NEGATIVE PARTICLE PID " + pid_mle_neg + " CONF " + conf_part_neg + " INDEX " + m );
				//System.out.println(" >> KAON MINUS " + km_conf );
				//System.out.println(" >> PION MINUS " + pim_conf );
				
				if( pid_mle_neg == -321 && conf_part_neg >= 0.07 && pim_conf <= 0.93 ){
				    h_pid_mle.fillKaonMinusMLE(event,m);
				    
				    double temp_km_e = Calculator.lv_energy(recBank,m,321);
				    
				    /*System.out.println(" >> MLE Kaon MINUS PID " + pid_mle_neg + " CONF " + conf_part_neg );
				    System.out.println(" >> VALUES OF CONF LVL FOR PARTICLES ");
				    System.out.println(" >> Km: " + km_conf);
				    System.out.println(" >> Pim: " + pim_conf);
				    System.out.println(" >> HERE " );
				    */
				    if( temp_km_e > km_e ){ 
					km_e = temp_km_e;
					good_km = m;
					good_mle_km = true;
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
				
				//System.out.println( " >> POSITIVE PARTICLE IDD index " + m );
				
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
				    /*System.out.println(" >> MLE KAON PID " + pid_mle + " CONF " + conf_part );
				    System.out.println(" >> VALUES OF CONF LVL FOR PARTICLES ");
				    System.out.println(" >> Pr: " + pr_conf);
				    System.out.println(" >> Kp: " + kp_conf);
				    System.out.println(" >> Pip: " + pip_conf);
				    */
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
				    /*
				    System.out.println(" >> MLE PROTON PID " + pid_mle + " CONF " + conf_part );
				    System.out.println(" >> VALUES OF CONF LVL FOR PARTICLES ");
				    System.out.println(" >> Pr: " + pr_conf);
				    System.out.println(" >> Kp: " + kp_conf);
				    System.out.println(" >> Pip: " + pip_conf);
				    */
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
		
		//System.out.println(" >> RESULTS OF EVENT " + counter + " ELECTRON RESULT: " + el_present + " PROTON RESULT: " + good_mle_pr + " KAON RESULT: " + good_mle_kp );
		/*
		if( el_present && good_mle_pr && !good_mle_kp) {
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
		*/
		if( good_el >= 0 && good_pr > 0 && good_kp > 0 ){
		    //eventtopology = 2;
		    //real_event = true;
		    
		    golden_el_index = good_el;
		    golden_pr_index = good_pr;
		    golden_kp_index = good_kp;			    
		    
	    }
		
		
		if( el_present && good_mle_pr && good_mle_kp ){
		    //System.out.println(" >> STATUS OF KAON MINUS " + good_mle_km );
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

		if( el_present && good_mle_pr && good_mle_kp && good_mle_km ){
		    eventtopology = 4;
		    real_event = true;
		    System.out.println(" >> --------------------------------------------------------------------------  << " );
		    System.out.println(" >>                       PHI EVENT YEAH! " );

		    lv_el = Calculator.lv_particle(recBank,good_el,11);
		    lv_kp = Calculator.lv_particle(recBank, good_kp, 321);
		    lv_km = Calculator.lv_particle(recBank, good_km, 321);
		    lv_pr = Calculator.lv_particle(recBank, good_pr, 2212);
		}
			   

		if( eventtopology == 4 ){
		    real_event = true;
		    //DataEvent physics_event = writer.createEvent();
		    //DataBank physics_bank = physics_event.createBank("REC::Particle", 10);// (int)(eventtopology+1));
		
		    //physics_bank.setInt("pid",0,(int)11);
			    
		    //physics_event.appendBank(physics_bank);
		    //writer.writeEvent(physics_event);

		    //physics_event.show();
			    
			    
		    try{
			int helicity = -10;
			if( event.hasBank("REC::Event") ){
			    helicity = event.getBank("REC::Event").getInt("Helic",0);
			}
			System.out.println(" >> WRITING TO TXT FILE " );
			//System.out.println(" >> ELECTRON PX " + lv_el.px() );
			buffwriter.write( Integer.toString(run_number ) + " " + Integer.toString(eventtopology) + " " + Integer.toString(helicity) + " "  +  Double.toString(lv_el.px()) + " " + Double.toString(lv_el.py()) + " " + Double.toString(lv_el.px()) + " " + Double.toString(lv_pr.px()) + " " + Double.toString(lv_pr.py()) + " " + Double.toString(lv_pr.pz()) + " " + Double.toString(lv_kp.px()) + " " + Double.toString(lv_kp.py()) + " " + Double.toString(lv_kp.pz()) + " " + Double.toString(lv_km.px()) + " " + Double.toString(lv_km.py()) + " " + Double.toString(lv_km.pz()) + "\n");

		    }
		    catch(IOException e ){
			System.out.println(" ERROR WRITING " + e );
		    }
			   
			    
			
			    
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
	    }
	    
	    
	    
    	    counter = counter + 1;
	    
	    }
	    
	    try{
		if( buffwriter != null ){
		    buffwriter.close();
		}
	    }
	    catch(IOException e){
		System.out.println(" >> ERROR CLOSING FILE " + e );
	    }
	    
	}    
	   
    
	
    	
	
    

	
	/// ------------------------------------------------------------------------------------
	//RIP MULTITHREADING
	//for( int i = 0; i < n_threads; i++ ){
	//    el_threads.get(i).saveHisto();
	//	}
	/// -----------------------------------------------------------------------------------
	
	   
	   
	System.out.println(">> SAVING HISTOGRAMS FROM THREADS " );
	long stopTime = System.currentTimeMillis();
	System.out.println("Elapsed time was " + (stopTime - startTime) + " miliseconds.");
    
	//RECORD RUN PROPERTIES:
	//bevent.setAccumulatedCharge(fc_integratedcharge);
   
	//START NEXT FILE
	count = 0;
	System.out.println(">> CLOSING OUTPUT FILE ");
	//output_writer.close();
	//output.closeTxtFile();
	        
	System.out.println("------------- RESULTS ------------- ");
	System.out.println("TOTAL FILES ANALYZED : " + f_counter);
	System.out.println(">> TOTAL GEN : " + tot_gen_events );
	System.out.println(">> TOTAL REC : " + tot_rec_events );
	System.out.println(">> TOTAL INTEGRATED CHARGE: " + fc_integratedcharge);
	System.out.println("---- PARTICLE RATES ---- ");
	System.out.println(">> TOTAL PASSING ELECTRONS: " + tot_el_pass);
	System.out.println(">> TOTAL PASSING PROTONS: " + tot_pr_pass);
	System.out.println(">> TOTAL PASSING K PLUS: " + tot_kp_pass);
	System.out.println(">> TOTAL PASSING K MINUS: " + tot_km_pass);
	System.out.println("---- EVENT RATES ---- ");
	System.out.println(">> TOTAL e+p EVENTS : " + tot_ep_pass );
	System.out.println(">> TOTAL e+p+kp EVENTS : " + tot_epkp_pass );
	System.out.println(">> TOTAL e+p+km EVENTS : " + tot_epkm_pass );
	System.out.println(">> TOTAL e+kp+km EVENTS : " + tot_ekpkm_pass );	
	System.out.println(">> TOTAL e+p+kp+km EVENTS : " + tot_all_pass );
	int tot_events = tot_ep_pass + tot_epkp_pass + tot_epkm_pass + tot_ekpkm_pass + tot_all_pass;
	System.out.println(">> TOTAL EVENTS PASSING : " + tot_events );
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// SET RUN PROPERTIES INFORMATION HERE POST RUN SUCH AS INTEGRATED CHARGE, ETC.
	//
	//run_properties.setTotalAccumulatedCharge(fc_integratedcharge);
	
	
	System.out.println(">> SAVING HISTOGRAMS IS COMPLETE");
	boolean view_phys = false;
	h_pid_cutlvls.savePIDHistograms(view_phys);
	h_pid_clas12.saveCLAS12PIDHistograms(view_phys);
	h_pid_mle.saveMLEHistograms(view_phys);
	h_phys.savePhysHistograms(view_phys);
	System.out.println(">> COMPLETED SAVING HISTOGRAMS");

	
	BufferedWriter writer = null;
	try{
	    String particle_multiplicity_results = "partMultiplicity.txt";
	    writer = new BufferedWriter( new FileWriter(particle_multiplicity_results));
	    writer.write(Integer.toString(tot_gen_events) + "\n");
	    writer.write(Integer.toString(tot_rec_events) + "\n");
	    writer.write(Integer.toString(tot_el_pass) + "\n");
	    writer.write(Integer.toString(tot_pr_pass) + "\n");
	    writer.write(Integer.toString(tot_kp_pass) + "\n");
	    writer.write(Integer.toString(tot_km_pass) + "\n");
	    writer.write(Integer.toString(tot_ep_pass) + "\n");
	    writer.write(Integer.toString(tot_epkp_pass) + "\n");
	    writer.write(Integer.toString(tot_epkm_pass) + "\n");
	    writer.write(Integer.toString(tot_ekpkm_pass) + "\n");
	    writer.write(Integer.toString(tot_all_pass) + "\n");
	    writer.write(Integer.toString(tot_events) + "\n");
	    writer.write(Double.toString(fc_integratedcharge) + "\n");
	}
	catch ( IOException e){           
	    System.out.println(">> ERROR WRITING TO FILE ");
	}	
	finally{
	    try
		{
		    if ( writer != null)
			writer.close( );
		}
	    catch ( IOException e)
		{
		    System.out.println(">> ANOTHER ERROR");
		}
	}
	
    }
}

