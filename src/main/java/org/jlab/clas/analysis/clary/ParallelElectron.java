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

import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.io.hipo.HipoDataSource;
import org.jlab.jnp.hipo.io.HipoWriter;

import org.jlab.jnp.hipo.schema.*;

import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class ParallelElectron implements Runnable{

    private Thread t;
    private String threadName;

    ElectronPID find_el;
    MLEParticleFinder mle_particle;


    BHistoPIDLevel h_pid_cutlvls;
    BHistoCLAS12PID h_pid_clas12;
    BHistoMLE h_pid_mle;
    RunPropertiesLoader run_properties;
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

	
	find_el = new ElectronPID();
	mle_particle = new MLEParticleFinder(); 
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


	initConfig(run_number);

    }


    ///////////////////////////////////////////////////////////////////////////////
    //ATTEMPTING TO NOT OPEN A FILE FOR EACH THREAD WHEN DOING SINGLE FILE ANALYSIS
    ParallelElectron(String name, String s_run, HipoDataSource temp_hipo, boolean singleMultiAnalysis, String  dataOrSim){
	threadName = name;
	System.out.println("Creating " +  threadName );
	run_number = Integer.valueOf(s_run);
	dataOrSim_type = dataOrSim;
	typeAnalysis = singleMultiAnalysis; //TRUE FOR SINGLE FILE ANALYSIS, FALSE FOR MULTI

	h_pid_cutlvls = new BHistoPIDLevel(run_number, threadName);
	h_pid_clas12 = new BHistoCLAS12PID(run_number, threadName);
	h_pid_mle = new BHistoMLE(run_number, threadName);
	h_phys = new BHistoPhys(run_number,threadName);

	h_pid_cutlvls.CreateHistograms();
	h_pid_clas12.createHistograms();
	h_phys.createHistograms();
	
	
	find_el = new ElectronPID();
	mle_particle = new MLEParticleFinder(); 
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


	initConfig(run_number);

    }


    ParallelElectron(String name, String s_run, File temp_file, boolean singleMultiAnalysis, String  dataOrSim ){
	threadName = name;
	System.out.println("Creating " +  threadName );
	run_number = Integer.valueOf(s_run);
	dataOrSim_type = dataOrSim;
	typeAnalysis = singleMultiAnalysis; //TRUE FOR SINGLE FILE ANALYSIS, FALSE FOR MULTI

	h_pid_cutlvls = new BHistoPIDLevel(run_number, threadName);       
	h_pid_mle = new BHistoMLE(run_number,threadName);
	h_phys = new BHistoPhys(run_number,threadName);

	h_pid_cutlvls.CreateHistograms();
	h_phys.createHistograms();

	find_el = new ElectronPID();
	mle_particle = new MLEParticleFinder(); 
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

	initConfig(run_number);

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
		String s_file_to_analyze = path_to_files + Integer.toString(nfile) + ".hipo";
		System.out.println(">> PROCESSING FILE " + s_file_to_analyze );
		HipoDataSource reader = new HipoDataSource();      
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
				//h_pid_clas12.fillElectronCLAS12PID(event,0);
				//h_pid_clas12.fillElectronCLAS12Comparison(0);

				for( int i = 0 ; i < recBank.rows(); i++ ){
				    int charge = recBank.getInt("charge",i);
				    if( charge > 0 && i != 0){
					h_pid_mle.fillBetaAll(event,i);
					//h_pid_mle.fillDCTraj(event,i);
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
			
			//System.out.println(" >> GETTING VECTOR RESULT " );
			for( int k = 0; k < recBank.rows(); k++ ){
			    ///
			    //  ADDING STATUS TO REMOVE PARTICLES FROM THE CENTRAL DETECTORS
			    //
			    int status = recBank.getShort("status",k);
			    if( status >= 4000 || status <= 1999 ) continue;			
			    //v_el_tests = find_el.processCutsVector(event, k);
			    //System.out.println(">> RESULT VECTOR " + v_el_tests );
			    // Let the thread sleep for a while.
			    //h_pid_cutlvls.FillElectronPID( v_el_tests, event, k );
			    int clas12_el_test  = recBank.getInt("pid",k);
			    if( clas12_el_test == 11 ){
				h_pid_clas12.fillElectronEB(k);
			    }
			    
			    
			}

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
			int good_kp = -1;
			int good_pr = -1;
			
			HashMap<Boolean,Integer> m_final_el = find_el.getResult(event);			
			for( Map.Entry<Boolean,Integer> entry : m_final_el.entrySet() ){
			  boolean el_test_result = entry.getKey();
			  int el_result_index = entry.getValue();
			    
			  if( el_test_result ){
			      //TO COMPARE AGAINST EVENTBUILDER PID
			      //h_pid_cutlvls.fillComparisonPID(event, el_result_index );
			      good_el = el_result_index;
			      for( int m = 0; m < recBank.rows(); m++ ){
				    int charge = recBank.getInt("charge",m);
				    
				    if( charge > 0 && m != el_result_index ){
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

					//System.out.println(" >> MLE PARTICLE PID " + pid_mle + " CONF " + conf_part );
					Particle mle_proton = mle_particle.getProton(bevinf,m);
					Particle mle_kaon = mle_particle.getKaonPlus(bevinf,m);
					Particle mle_pion = mle_particle.getPionPlus(bevinf,m);


					double pr_conf = mle_proton.getProperty("conflvl");
					double kp_conf = mle_kaon.getProperty("conflvl");
					double pip_conf = mle_pion.getProperty("conflvl");

					//h_pid_mle.fillDCTraj(event, m);
					
					h_pid_mle.fillProtonConfidenceLevel(mle_proton.getProperty("conflvl"));
					h_pid_mle.fillKaonConfidenceLevel(mle_kaon.getProperty("conflvl"));
					h_pid_mle.fillDCTraj(event,m);

					
					if( pid_mle == 321 && conf_part >= 0.27 && pr_conf <= 0.73 && pip_conf <= 0.73 ){
					    good_kp = m;
					    //tempn_kp = tempn_pr + 1;
					    //fast_kaon.add(m);
					    good_mle_kp = true;
					    //System.out.println(" >> MLE KAON PID " + pid_mle + " CONF " + conf_part );
					    //System.out.println(" >> VALUES OF CONF LVL FOR PARTICLES ");
					    //System.out.println(" >> Pr: " + pr_conf);
					    //System.out.println(" >> Kp: " + kp_conf);
					    //System.out.println(" >> Pip: " + pip_conf);
					    //h_pid_mle.fillKaonMLE( event, good_kp ); 
					    double temp_kp_e = Calculator.lv_energy(recBank,m,321);
					    if( temp_kp_e > kp_e ){ kp_e = temp_kp_e; good_kp = m;}
					}
					
					if( pid_mle == 2212 && conf_part >= 0.27 && kp_conf <= 0.73 && pip_conf <= 0.73 ){
					    good_pr = m;
					    //tempn_pr = tempn_pr + 1;
					    //fast_proton.add(m);
					    good_mle_pr = true;
					    //System.out.println(" >> MLE PROTON PID " + pid_mle + " CONF " + conf_part );
					    //System.out.println(" >> VALUES OF CONF LVL FOR PARTICLES ");
					    //System.out.println(" >> Pr: " + pr_conf);
					    //System.out.println(" >> Kp: " + kp_conf);
					    //System.out.println(" >> Pip: " + pip_conf);
					    //h_pid_mle.fillProtonMLE( event, good_pr ); 
					    double temp_pr_e = Calculator.lv_energy(recBank,m,2212);
					    if( temp_pr_e > pr_e ){ pr_e = temp_pr_e; good_pr = m; }
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
			
			if( good_el >= 0 && good_pr > 0 && good_kp > 0 ){
			    eventtopology = 2;
			    real_event = true;
			    
			    golden_el_index = good_el;
			    golden_pr_index = good_pr;
			    golden_kp_index = good_kp;			    
			    
			}

			if( good_el >= 0 && good_pr > 0 ){
			    LorentzVector lv_beam = new LorentzVector(0,0,PhysicalConstants.eBeam,PhysicalConstants.eBeam);
			    LorentzVector target = new LorentzVector(0,0,0,PhysicalConstants.mass_proton);
			    LorentzVector lv_el = Calculator.lv_particle(recBank, golden_el_index, 11);
			    LorentzVector lv_pr = Calculator.lv_particle(recBank, golden_pr_index, 2212);
			    
			    h_phys.fillMMepX( lv_beam, target, lv_el, lv_pr );

			}

			if ( real_event ){
			    PhysicsBuilder physicsbuild = new PhysicsBuilder();
			    PhysicsEvent final_phy_event = physicsbuild.setPhysicsEvent( real_event, event, golden_el_index, golden_pr_index, golden_kp_index, golden_km_index, eventtopology );
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
	    }   	    
	}
	catch (Throwable e) {
	    System.out.println("Thread " +  threadName + " interrupted.");
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
			    h_pid_cutlvls.FillElectronPID( v_el_tests, event, k );
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
