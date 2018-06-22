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

public class phiAnalysis{

    public static void main( String[] analysisInfo ){
	EventProcessor process = new EventProcessor();

	CoolText coolprint = new CoolText();
	coolprint.printToScreen(">> STARTING " + analysisInfo[2] + " ANALYSIS FOR RUN " + analysisInfo[1] + " TYPE "  + analysisInfo[0], "red" );

	String inName = analysisInfo[0];
	String s_run_number = analysisInfo[1];
	int num_files_process = Integer.valueOf(analysisInfo[2]);
	int run_number = Integer.valueOf(analysisInfo[1]);
	String dataSim = analysisInfo[3];
	System.out.println(">> SIM OR DATA? " + dataSim );

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

	int max_files = num_files_process;
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
	//RunPropertiesLoader run_properties = new RunPropertiesLoader(run_number,dataSim);
	//run_properties.addRun("r3050");

	if( run_number > 0 ){
	    //run_properties.loadRunProperties(run_number);
	    //run_properties.setRunProperties();
	    //run_properties.writeRunProperties();
	}
	/*
	run_properties.getRunInfoClass().addRunToRunInfo("3059");
	run_properties.getRunInfoClass().getRunParametersClass("3059").setRunNumber(3059);
	run_properties.getRunInfoClass().getRunParametersClass("3059").setBeamEnergy(3.4582);
	run_properties.getRunInfoClass().getRunParametersClass("3059").setCurrent(5.0);
	run_properties.getRunInfoClass().getRunParametersClass("3059").setTarget("LH2");
	run_properties.writeRunProperties();
	
	run_properties.getRunInfoClass().getRunParametersClass("3059").setCutTypeMap();
	run_properties.getRunInfoClass().getRunParametersClass("3059").addCutLevelToCutType("cut_nom");
	run_properties.getRunInfoClass().getRunParametersClass("3059").getCutTypeClass("cut_nom").setCutLevel(0);
	run_properties.getRunInfoClass().getRunParametersClass("3059").getCutTypeClass("cut_nom").setECParameterMap();
	run_properties.getRunInfoClass().getRunParametersClass("3059").getCutTypeClass("cut_nom").addECCutParameters("ec_sf_cut");
	run_properties.getRunInfoClass().getRunParametersClass("3059").getCutTypeClass("cut_nom").getECCutParametersClass("ec_sf_cut").setCutName("ECSFCut");
	List<Double> testlist = new ArrayList<Double>();
	testlist.add(0.3);
	testlist.add(0.4);
	testlist.add(0.5);
	testlist.add(0.6);
	testlist.add(0.6);
	run_properties.getRunInfoClass().getRunParametersClass("3059").getCutTypeClass("cut_nom").getECCutParametersClass("ec_sf_cut").setMaxFitParametersSector1( testlist );
	run_properties.writeRunProperties();
	*/

	CutConfigInfo cutconfig = new CutConfigInfo();
	try{
	    Gson gson = new Gson();
	    BufferedReader br = new BufferedReader( new FileReader("/home/bclary/CLAS12/phi_analysis/v2/v1/run_db/cut_config.json") );
	    cutconfig = gson.fromJson(br, CutConfigInfo.class );
	}
	catch( IOException e ){
	    System.out.println(" >> ERROR LOADING CUT DB JSON FILE " );
	}	
	
	System.out.println(" >> " + cutconfig.el_cuts);
	System.out.println(" >> " + cutconfig.pr_cuts);
	
	List<String> my_el_cuts = cutconfig.el_cuts;
	System.out.println(">> STUCK HERE " );
	 	             		    
	HipoDataSource reader = new HipoDataSource();
	System.out.println(">> STUCK HERE " );

	/*BHistoPlotter h_plot = new BHistoPlotter();
	BHistoKinPlotter h_kin = new BHistoKinPlotter();
	BHistoPhysPlotter h_physplot = new BHistoPhysPlotter(run_number);
	BHistoPhysKinPlotter h_mc_el_phy = new BHistoPhysKinPlotter("mc","kp");
	BHistoPhysKinPlotter h_mc_pr_phy = new BHistoPhysKinPlotter("mc","km");
	BHistoPhysKinPlotter h_mc_kp_phy = new BHistoPhysKinPlotter("mc","el");
	BHistoPhysKinPlotter h_mc_km_phy = new BHistoPhysKinPlotter("mc","pr");
	BHistoPhysKinPlotter h_mc_physics = new BHistoPhysKinPlotter("mc","phy");
	BHistoDetectorInfo h_detectors = new BHistoDetectorInfo();
	*/
	System.out.println(">> STUCK HERE " );

	//	BHistoPIDLevel h_pid_cutlvls = new BHistoPIDLevel(run_number, "-10");
	//BHistoMLE h_pid_mle = new BHistoMLE(run_number);
	//BHistoCLAS12PID h_clas12_pid = new BHistoCLAS12PID(run_number);
	//BHistoResolutions h_res_pid = new BHistoResolutions(run_number);
	System.out.println(">> STUCK HERE " );
	
	//MatchingElectronPID match_el = new MatchingElectronPID();
	//MatchingProtonPID match_pr = new MatchingProtonPID();
	//MatchingPionPlusPID match_pp = new MatchingPionPlusPID();
	//MatchingKaonPlusPID match_kp = new MatchingKaonPlusPID();
	System.out.println(">> STUCK HERE " );

	ElectronPID find_el = new ElectronPID(my_el_cuts);
	ProtonPID find_pr = new ProtonPID();
	KaonPlusPID find_kp = new KaonPlusPID();
	System.out.println(">> STUCK HERE " );

	CLAS12ElectronPID clas12_el = new CLAS12ElectronPID();
	CLAS12ProtonPID clas12_pr = new CLAS12ProtonPID();
	CLAS12KaonPlusPID clas12_kp = new CLAS12KaonPlusPID();
	CLAS12KaonMinusPID clas12_km = new CLAS12KaonMinusPID();

	MLEParticleFinder mle_particle = new MLEParticleFinder(); 
	System.out.println(">> STUCK HERE " );

	//match_el.initializeCuts();
	//match_pr.initializeCuts();
	//match_kp.initializeCuts();
	System.out.println(" LOADING CUTS" );
	if( run_number > 0 ){
	    CutLoader cut_loader = new CutLoader(run_number,dataSim,"cut_nom");
	    //System.out.println(" LOADING CUTS 2" );
 	    //cut_loader.loadRunCuts(run_number,"cut_nom");
	    //System.out.println(" LOADING CUTS 3" );		
	    //cut_loader.setRunCuts();
	    //System.out.println(" LOADING CUTS 4" );
	    //cut_loader.printRunCuts();
	}
	
	find_el.initializeCuts();
	find_pr.initializeCuts();
	find_kp.initializeCuts();

	clas12_el.initializeCuts();
	clas12_pr.initializeCuts();
	clas12_kp.initializeCuts();
	clas12_km.initializeCuts();
	
	/*h_plot.CreateHistograms();
	h_kin.CreateHistograms();
	h_physplot.CreateHistograms();
	h_physplot.CreateMCHistograms();
	h_mc_el_phy.CreateH1Kin();
	h_mc_pr_phy.CreateH1Kin();
	h_mc_kp_phy.CreateH1Kin();
	h_mc_km_phy.CreateH1Kin();
	h_mc_physics.CreateH1Kin();
	
	h_detectors.CreateHistograms();
	*/
	//h_pid_cutlvls.CreateHistograms();
	//h_clas12_pid.createHistograms();
	//h_res_pid.createHistograms();

	//h_pid_cutlvls.createElectronHistoToHipoOut(0);

	long startTime = System.currentTimeMillis();

	boolean singleMultiFile = false; //TRUE FOR SINGLE FILE TO THREAD EVENTS FALSE FOR THREADING MULTIFILES
	String f_prefix = file_loc + file_name;
	int total_nevents = 0;
	int split_ev = 0;
	int n_threads = 4;
	if( dataSim == "DATA"){ n_threads = 4; }

	int nevents_thread=0;
	Vector<ParallelElectron> el_threads = new Vector<ParallelElectron>();
	String simOrdata = dataSim;
	if( singleMultiFile ){
	    f_prefix = "/lustre/expphy/volatile/clas/clase1/markov/12GeVANA/newMF/3432.hipo"; //"/lustre/expphy/volatile/clas/clase1/markov/12GeV/inclusive/inclusiveDiffPol/farm/filesClara/10.6GeV/InclusiveNonElastic/inclusiveNonElasS-1T-1.hipo";
	    reader.open(new File(f_prefix));
	    total_nevents = reader.getSize();
	    nevents_thread = total_nevents/n_threads;
	    //System.out.println(" >> EVENTS SPLIT BETWEEN TWO THREADS: " + total_nevents/n_threads);	    

	    for( int n = 0;  n < n_threads; n++ ){
		el_threads.add( new ParallelElectron(simOrdata+"_thread-"+Integer.toString(n),s_run_number,f_prefix,singleMultiFile,simOrdata) );
		System.out.println(">> THREAD PROCESSING EVENTS FROM " + Integer.toString(n*nevents_thread) + " " + Integer.toString((n+1)*nevents_thread) );
		el_threads.get(n).setThreadEventRange(n*nevents_thread, (n+1)*nevents_thread);	    
	    }	    	   	    
	}
	else{
	    f_prefix = file_loc + file_name;	    
	    System.out.println(">> PROCESSING FROM FILE LOCATION " + f_prefix);
	    if( max_files == 1 ){ n_threads = 1; }
	    int nfiles_thread = max_files/n_threads;
	    int shift = 250;
	    for( int n = 0;  n < n_threads; n++ ){
		el_threads.add( new ParallelElectron(simOrdata+"_thread-"+Integer.toString(n),s_run_number,f_prefix,singleMultiFile,simOrdata) );
		System.out.println(">> THREAD PROCESSING FILES FROM " + Integer.toString(n*nfiles_thread) + " " + Integer.toString((n+1)*nfiles_thread) );
		el_threads.get(n).setFileRange(shift + n*nfiles_thread, shift + (n+1)*nfiles_thread);	    
	    }
	}


	    
	for( int n = 0; n < n_threads; n++ ){
	    el_threads.get(n).start();	 
	}
	for( int n = 0; n < n_threads; n++ ){
	    el_threads.get(n).join();	 
	
	}

	
	/*	ParallelElectron threaded_electron  = new ParallelElectron("sim_thread-1",s_run_number,f_prefix, singleMultiFile);
	//threaded_electron.setFileRange(0,5);
	threaded_electron.setThreadEventRange(0,total_nevents/4);
	
	ParallelElectron threaded_electron_2  = new ParallelElectron("sim_thread-2",s_run_number,f_prefix, singleMultiFile);
	//threaded_electron_2.setFileRange(5,11);
	threaded_electron_2.setThreadEventRange(total_nevents/4, total_nevents/2);

	ParallelElectron threaded_electron3  = new ParallelElectron("sim_thread-3",s_run_number,f_prefix, singleMultiFile);
	//threaded_electron.setFileRange(0,5);
	threaded_electron3.setThreadEventRange(total_nevents/2, (total_nevents*3)/4);
	
	ParallelElectron threaded_electron4  = new ParallelElectron("sim_thread-4",s_run_number,f_prefix, singleMultiFile);
	//threaded_electron_2.setFileRange(5,11);
	threaded_electron4.setThreadEventRange((total_nevents*3)/4, total_nevents);

	threaded_electron.start();
	threaded_electron_2.start();
	threaded_electron3.start();
	threaded_electron4.start();
	
	threaded_electron.join();
	threaded_electron_2.join();
	threaded_electron3.join();
	threaded_electron4.join();
	*/
	////////////////////////////
	//
	// START PID ANALYSIS 
	//
	////////////////////////////
	int f_counter = 0;
	coolprint.printToScreen(">> BEGINNING LOOP OVER FILES NOW:","red");
	
	/*	for( int i = 0; i <= max_files; i++ ){
	    
	    String in_file = file_loc + file_name + Integer.toString(i) + file_ext ;
	    //String in_file = "/lustre/expphy/volatile/clas/clase1/markov/12GeVANA/newMF/2476.hipo";
	    //String in_file = "/lustre/expphy/volatile/clas/clase1/markov/2GeV/inclusive/elastgen/farm/files3Particles/diffFields/elas2GeVS0.6T0.6LongApril_24_TorusSymmetricCorrect.dat.hipo";
	    //String in_file = "/volatile/clas12/data/rg-a/pass0/tag5b.3.3/filtered/phys2_3432.hipo";
	    //File file_to_analyze = new File(in_file);
	    //double max_file_size = 200000000;
	    //System.out.println(" >> FILE TO ANALYZE " + in_file);

	    if( file_to_analyze.exists() ){//  && file_to_analyze.length() >= max_file_size ){
		System.out.println("File Exists");
		f_counter+=1;
	    }
	    if( !file_to_analyze.exists() ){// || file_to_analyze.length() <= max_file_size){
		System.out.println("File DOES NOT Exists");
		continue;
	    }
	    
	    
	    //System.out.println(">> NUMBER OF EVENTS FOR TWO THREADS " + nevents );
	    //ParallelElectron threaded_electron  = new ParallelElectron("thread-1",s_run_number,in_file);
	    //threaded_electron.setThreadEventRange(0,2000);
	    //threaded_electron.start();

	    //ParallelElectron threaded_electron_2  = new ParallelElectron("thread-2",s_run_number,in_file);
	    //threaded_electron_2.setThreadEventRange(1000,2000);
	    //threaded_electron_2.start();
	        
	    
	    //System.out.println(">> DONE THREADING " );
	    //HipoWriter output_writer = new HipoWriter();
	    //System.out.println(String.format(">> CREATING OUTPUT FILE pid_phi_%d.hipo",i));
	    //String f_output = f_type +i+".hipo";
	    //PhysicsEventWriter output = new PhysicsEventWriter(output_writer,"pid",i);
	    //output.writeTxtHeader();

	    // reader.open(in_file);
	    int counter = 0;
	    //DataEvent nullevent = reader.getNextEvent();
	    //coolprint.printToScreen(">> BEGINNING EVENT LOOP NOW:","blue");
	    while( reader.hasEvent() ){
	      DataEvent event = reader.getNextEvent();

		//if( count == 500000 ){ break; }
		
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
		//if( recBank_pres ) event.getBank("REC::Particle").show(); 
		//if( eventBank_pres ) event.getBank("REC::Event").show(); 
		//if( scintBank_pres ) event.getBank("REC::Scintillator").show(); 
		//if( calBank_pres ) event.getBank("REC::Calorimeter").show(); 
		//if( event.hasBank("REC::Track") && event.hasBank("TimeBasedTrkg::TBCrosses") && event.hasBank("TimeBasedTrkg::TBTracks") ){ 
		    //event.getBank("REC::Track").show();
		    //event.getBank("TimeBasedTrkg::TBCrosses").show();
		    //event.getBank("TimeBasedTrkg::TBTracks").show();
		//}

			System.out.println(" >> charge out test " + bevent.charge );
		System.out.println(" >> starttime  " + bevent.start_time );
		System.out.println(" >> rf " + bevent.rf_value );
		System.out.println(" >> q " + bevent.charge );
		System.out.println(" >> px " + bevent.px );
		System.out.println(" >> py " + bevent.py );
		System.out.println(" >> pz " + bevent.pz );
		System.out.println(" >> vz " + bevent.vz );
		System.out.println(" >> ecal_sect " + bevent.ecal_sect );
		System.out.println(" >> ecsf " + bevent.ecsf );
		System.out.println(" >> ec_ei " + bevent.ec_ei );
		System.out.println(" >> ec_eo " + bevent.ec_eo);
		System.out.println(" >> pcal_e " + bevent.pcal_e );
		System.out.println(" >> nphe " + bevent.htcc_nphe );
		System.out.println(" >> dcr1 s " + bevent.dcr1_sect );
		System.out.println(" >> dcr1 cx " + bevent.dcr1_cx );
		System.out.println(" >> dcr1 cy " + bevent.dcr1_cy );
		System.out.println(" >> dcr2 s " + bevent.dcr2_sect );
		System.out.println(" >> dcr2 cx " + bevent.dcr2_cx );
		System.out.println(" >> dcr2 cy " + bevent.dcr2_cy );
		System.out.println(" >> dcr3 s " + bevent.dcr3_sect );
		System.out.println(" >> dcr3 cx " + bevent.dcr3_cx );
		System.out.println(" >> dcr3 cy " + bevent.dcr3_cy );
		System.out.println(" >> pcal x " + bevent.pcal_x );
		System.out.println(" >> pcal y " + bevent.pcal_y );
		System.out.println(" >> clas12beta " + bevent.clas12_beta );
		System.out.println(" >> ftof1 energy " + bevent.ftofl1_energy );
		System.out.println(" >> ftof1 path " + bevent.ftofl1_path );
		System.out.println(" >> ftof1 tof " + bevent.ftofl1_tof );
		System.out.println(" >> ftof2 energy " + bevent.ftofl2_energy );
		System.out.println(" >> ftof2 path " + bevent.ftofl2_path );
		System.out.println(" >> ftof2 tof " + bevent.ftofl2_tof );
		
		
		boolean recBank_present = event.hasBank("REC::Particle");
		boolean genBank_present = event.hasBank("MC::Particle");
		boolean eventBank_present = event.hasBank("REC::Event");

		//System.out.println(" >> EVENT " + counter );

		float start_time = -1000;
		if( eventBank_present && recBank_present ) {
		    DataBank eventBank = event.getBank("REC::Event");
		    start_time = eventBank.getFloat("STTime",0);		    
		}

		PhysicsBuilder physicsbuild = new PhysicsBuilder();

		//System.out.println(" >> FINDING FASTEST PARTICLE " );
		//find_el.getResult(event, 0)

		double fc_bcurrent = Calculator.faradayCupCurrent(event);
		double fc_event_integratedcharge = Calculator.faradayCupIntegratedCharge(event);
		fc_integratedcharge = fc_integratedcharge + fc_event_integratedcharge; 
		//System.out.println(" >> " + fc_bcurrent + " " + fc_integratedcharge);


		if( genBank_present && event.getBank("MC::Particle").rows() == 4 ){
		    //DataBank genBank = event.getBank("MC::Particle");
		    //System.out.println(" GEN ROWS " + genBank.rows() );
		    tot_gen_events+=1;
		    //System.out.println(" GEN " );
			}
		
		///ADDED TO GETTHE BETA DISTRIBUTIONS FOR THE IMPROVED LIKELIHOOD RATION CUT TEST
		if( genBank_present && recBank_present ){
		    DataBank recBank = event.getBank("REC::Particle");
		    DataBank genBank = event.getBank("MC::Particle");
		    boolean match_pr_test = false;
		    boolean match_kp_test = false;
		    boolean match_pp_test = false;

 		    for( int p = 0; p < recBank.rows(); p++ ){
			if( !match_pp_test  ){
			    match_pp_test = match_pp.processCuts(event, p );
 			    if( match_pp_test ){
 				//System.out.println(" >> found partile for res ");
				h_res_pid.fillPPionResHistograms(event, p);
			    }
			}			
		    }
		}
		////////////////////////////////////////
		//END LIKELIHOOD TEST CODE

		if( skip ) continue;
		

		boolean rec_time_based = false;
		if( recBank_present && start_time > 0 && event.getBank("REC::Particle").rows() >= 0){

		    DataBank recBank = event.getBank("REC::Particle");
		    
		    boolean el_test = false;
		    boolean pr_test = false;
		    boolean kp_test = false;
		    boolean km_test = false;
		    boolean event_candidate = false;
		    
		    boolean clas12_el_test = false;
		    boolean clas12_pr_test = false;
		    boolean clas12_kp_test = false;
		    
		    //System.out.println(">> BOOLEAN STATUS OF PARTICLES " + el_test + " " + pr_test + " " + kp_test + " " + km_test + " " + event_candidate);
		    int n_el = 0;
		    int n_pr = 0;
		    int n_kp = 0;
		    int n_km = 0;
		    
		    int golden_el_index = -1;
		    int golden_pr_index = -1;
		    int golden_kp_index = -1;
		    int golden_km_index = -1;
		    //System.out.println(">> GOLDEN INDEX OF PARTICLES " +  golden_el_index + " " +  golden_pr_index + " " +  golden_kp_index + " " + golden_km_index);
		    
		    //VALUES FOR EVENT TOPOLOGY : -1 null, 1 = e+p, 2 = e+p+kp, 3 = e+p+km, 4 = e+p+kp+km, 5 = e+kp+km
		    int eventtopology = -1;
		    
		    Vector<Boolean> v_el_tests = new Vector<Boolean>();
		    Vector<Boolean> v_pr_tests = new Vector<Boolean>();
		    Vector<Boolean> v_kp_tests = new Vector<Boolean>();
		    //System.out.println(" >> NEW EVENT " );


		    /////////////////////////////////////////////////////////////////////////////////////////////////
		    //USE THIS ELECTRON PID - COMMENT OUT WHEN NOT IN USE
		    HashMap<Boolean,Integer> m_find_el = find_el.getResult(event);
		    for (Map.Entry<Boolean, Integer> entry : m_find_el.entrySet()) {
			boolean el_test_result = entry.getKey();
			int el_result_index = entry.getValue();
			if( entry.getKey() ){
			    //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			Particle ge = find_el.getParticle(event);
			//System.out.println(" >> Ge " + ge);

			}
		    //v_el_tests.add(true);
		    //h_pid_cutlvls.FillElectronPID( v_el_tests, event, el_result_index );
		    }
		    

		    

		    
		     
		    clas12_el_test = clas12_el.processCuts(event, 0);
		    if( clas12_el_test ){
		    	int rec_pid = recBank.getInt("pid",0);	
		    	if( rec_pid == 11 ){			
		    	    for( int reci = 1; reci < recBank.rows(); reci++ ){
				//clas12_pr_test = clas12_pr.processCuts(event, reci);
				//if( clas12_pr_test ){
		    		    int rec_hadron_pid = recBank.getInt("pid",reci);

				    if( rec_hadron_pid  == 2212 ){
					h_clas12_pid.fillProtonCLAS12PID(event, reci);
				    }
				    else if( rec_hadron_pid == 321 ){
					h_clas12_pid.fillKaonPCLAS12PID(event, reci);
				    }
		    		    else if( rec_hadron_pid == 211 ){
					h_clas12_pid.fillPionPCLAS12PID(event, reci);
				    }
				    //}
			    }
			}
			//h_clas12_pid.fillElectronCLAS12PID( event, 0 );			
		    }
		    

		    BEvent bev = new BEvent();
		    BEventWriter bwriter = new BEventWriter();
		    BEventLoader bload = new BEventLoader(event);
		    bload.setBEventInfo();
		    BEventInfo bevinf = bload.getBEventInfo();
		    bwriter.loadBankMaps(event);

		        for( int k = 0; k < recBank.rows(); k++ ){								      			
			    int clas12charge = event.getBank("REC::Particle").getInt("charge",k);
			    if( clas12charge > 0 || clas12charge < 0 ){
				
				//if( clas12pid == 11 || clas12pid == -11 || clas12pid == 2212 || clas12pid == -2212 || clas12pid == 211 || clas12pid == -211 ){
				//h_pid_cutlvls.fillPNDriftChamber(event, k);
			    }
			    //el_test = find_el.processCuts(event, k);
			    //
			    //System.out.println(">> here"  );
			    //v_el_tests = find_el.processCutsVector(event, k);
			    //h_pid_cutlvls.FillElectronPID( v_el_tests, event, k );
			    
		    }

		    

			//System.out.println(" >> INDEX " + k );
			//if( el_test ){
			    //LorentzVector lv_part = Calculator.lv_particle(recBank, k, 11);
			    //double energy_new = lv_part.e();
			    //System.out.println(" >> " + k + " energy " + energy + " energy_new " + energy_new);
			    //if( energy_new > energy ){
			    //energy = energy_new;
			    //fast_index = k;
			    //}
			    //}

		    ////////////////////////////////////////////////////////////////////////////////
		    int clas12pid = 0;//event.getBank("REC::Particle").getInt("pid",0);		    
		    int tempn_pr = 0;
		    int tempn_kp = 0;
		    int good_pr = -1;
		    int good_kp = -1;
		    if( clas12pid == 11 ){		       			
			//Vector<int> fast_proton  = new Vector<int>();
			//Vector<int> fast_kaon = new Vector<int>();
			double pr_e = 0;
			double kp_e = 0;
			boolean good_mle_pr = false;
			boolean good_mle_kp = false;
			//for( int m  = 0; m < recBank.rows(); m++ ){
			//  if( m != 0 ){
			//	int  charge = recBank.getInt("charge",m);
			//	if( charge > 0 ){   		   
				    //Particle mle_part = mle_particle.getMLEParticle(bevinf, m);
				    //double conf_part = mle_part.getProperty("conflvl");
				    //double pidmle = mle_part.getProperty("pid");
	
				    //if( pidmle == 211 ) h_pid_mle.fillProtonConfidenceLevel(conf_part);
				    //if( pidmle == 321 ) h_pid_mle.fillKaonConfidenceLevel(conf_part);
			    
				    if( pidmle == 321 && conf_part > 0.01){
					//h_pid_mle.fillKaonMLE( event, m );
					//good_kp = m;
					tempn_kp = tempn_pr + 1;
					//fast_kaon.add(m);
					good_mle_kp = true;
					double temp_kp_e = Calculator.lv_energy(recBank,m,321);
					if( temp_kp_e > kp_e ){ kp_e = temp_kp_e; good_kp = m; }
					}

				    //if( pidmle == 2212 && conf_part > 0.01){
				    //	v_pr_tests.add(true);
					//h_pid_mle.fillProtonMLE( event, m );
				    //v_pr_tests.clear();
					//good_pr = m;
				    //tempn_pr = tempn_pr + 1;
					//fast_proton.add(m);
				    //good_mle_pr = true;
				    //double temp_pr_e = Calculator.lv_energy(recBank,m,2212);
				    //if( temp_pr_e > pr_e ){ pr_e = temp_pr_e; good_pr = m; }
				    //}				  				    
				    //h_pid_mle.fillBetaAll(event, m);
				    //	    h_pid_mle.fillProtonMLE( event, m ); //here for timing info, move later.
				
			//		}
		    //		    }
		//}

			//if( good_mle_pr ) h_pid_mle.fillProtonMLE( event, good_pr ); 
			//if( good_mle_kp ) h_pid_mle.fillKaonMLE( event, good_kp ); 

		       		       
		    }

		    

		    //if( clas12pid == 11 && tempn_pr == 1 && tempn_kp == 1 ){		      
		    //h_pid_mle.fillPhiTest(event,0,good_pr,good_kp);
		    //}
		    ////////////////////////////////////////////////////////////////////////////////////////


			//v_el_tests = find_el.processCutsVector( event, k );
			//System.out.println(" >> fisrt " + v_el_tests);
			//h_pid_cutlvls.fillElectronCutMonitor( v_el_tests, event,  );
			//System.out.println(" >> second " + v_el_tests);
			



		    //if ( !el_test ){			    
			    //el_test = find_el.processCutsVectorResults(v_el_tests);
		    //if( el_test ){				
		    //tot_el_pass+=1;
		    //n_el+=1;
		    //		golden_el_index = k;
				//find_pid.processResult(event,k);
				//find_el.getResult(event,k);
				//System.out.println(" >> INDEX IN REC::PARTICLE FOR ELECTRON IS " + k );
				//h_kin.FillNegativesDetHist(event, golden_el_index);
				//System.out.println(" >> passed electron cuts " );
				//Calculator.eventStartTime( event, golden_el_index );
				//h_plot.FillElectronHistograms(event,golden_el_index);
				//h_detectors.FillECHist( event, golden_el_index );
				//h_detectors.FillCherenkovHist( event, golden_el_index );
		    //}			    
		    //}
		
		    //System.out.println(" >> fastindex " + fast_index + " final energy " + energy ); 


		    
		    
		    /////////////////////////////////////////////////////////////////////
		    //PROTON TEST IF ELECTRON IS PRESENT AND NOT EQUAL TO ELECTRON INDEX
		        if( el_test && n_el == 1){
			for( int j = 0; j < recBank.rows(); j++ ){
			    if( j != golden_el_index ){
				//System.out.println(" >> testing protons " );
				v_pr_tests = find_pr.processCutsVector(event, j);
				h_pid_cutlvls.fillProtonPID( v_pr_tests, event, j );
				pr_test = find_pr.processCutsVectorResults(v_pr_tests);				
				if( pr_test ){
				    ///System.out.println(" >> gp " + j );
				    n_pr+=1;
				    golden_pr_index = j;
				}
			    }
			}
			if( pr_test ){
			    for( int m = 0; m < recBank.rows(); m++ ){
				if( m != golden_pr_index ){
				    v_kp_tests = find_kp.processCutsVector(event, m);
				    h_pid_cutlvls.fillKaonPPID( v_kp_tests, event, m ); 
				    kp_test = find_kp.processCutsVectorResults(v_kp_tests);
				    if( kp_test ){
					System.out.println(" >> gkp " + m );
					n_kp+=1;
					golden_kp_index = m;
				    }
				}
			    }
			}
		    }
			    
		    if( golden_el_index >= 0 && golden_pr_index >= 0 && golden_kp_index >= 0 ){
			System.out.println(" >> TOTAL PARTICLES RATES " + n_el + " " + n_pr + " " + n_kp );			
			System.out.println(" >> E P KP " + golden_el_index + " " + golden_pr_index +  " " + golden_kp_index );
		    }
		    
		    
		    // System.out.println(">> POST TEST GOLDEN VALUES " + golden_el_index + " " + golden_pr_index + " " + golden_kp_index + " " + golden_km_index);
		    if( golden_el_index == golden_pr_index && golden_pr_index != -1 ){ System.out.println("OH NO 1"); }
		    //	    if( golden_kp_index == golden_pr_index && golden_pr_index != -1 ){ System.out.println("OH NO 2"); }
		    //if( golden_km_index == golden_pr_index && golden_pr_index != -1 ){ System.out.println("OH NO 3"); }
		    //if( golden_kp_index == golden_km_index && golden_kp_index != -1  ){ System.out.println("OH NO 4"); }
		    
		    		    if( n_el == 1 && n_pr == 1 && (n_kp == 0 && n_km == 0 ) ){
				    tot_ep_pass+=1;
				    eventtopology=1;
				    //System.out.println("ep : " + golden_el_index + " " + golden_pr_index + " " + golden_kp_index + " " + golden_km_index );
				    //			event_candidate = true;
				    } 
		
		    boolean real_event = false;			    		
		    if( n_el == 1 && n_pr == 1 && (n_kp == 1) ){// && n_km == 0 ) ){
			tot_epkp_pass+=1;
			eventtopology = 2;
			event_candidate = true;
			real_event = true;
			System.out.println(" >> EVENT " + tot_epkp_pass );
			//bevent.SetGoldenIndices( golden_el_index, golden_pr_index, golden_kp_index, golden_km_index );
		    }
		    
		      if( n_el == 1 && n_pr == 1 && (n_kp == 0 && n_km == 1 ) ){
		      tot_epkm_pass+=1;
		      eventtopology = 3;
		      event_candidate = true;
		      real_event = true;
		      //System.out.println("epkm : " + golden_el_index + " " + golden_pr_index + " " + golden_kp_index + " " + golden_km_index );
			  
		      } 
		      if( n_el == 1 && n_pr == 1 && (n_kp == 1 && n_km == 1 ) ){						       
		      tot_all_pass+=1;
		      eventtopology = 4;
		      event_candidate = true;
		      //real_event = true;
		      //System.out.println("epkpkm : " + golden_el_index + " " + golden_pr_index + " " + golden_kp_index + " " + golden_km_index );

		      }
		      if( n_el == 1 && n_pr == 0 && (n_kp == 1 && n_km == 1 ) ){
		      tot_ekpkm_pass+=1;
		      eventtopology = 5;
		      //event_candidate = true;
		      real_event = true;
		      //System.out.println("ekpkm : " + golden_el_index + " " + golden_pr_index + " " + golden_kp_index + " " + golden_km_index );
		      } 
		    
		    boolean toHipo = false;
		    boolean toTxt = false;
		    if(real_event){
			//PhysicsEvent final_phy_event = physicsbuild.setPhysicsEvent( real_event, event, golden_el_index, golden_pr_index, golden_kp_index, golden_km_index, eventtopology );
			//PhysicsEvent mc_final_phyev = physicsbuild.setMCPhysicsEvent( event );
				
			//h_physplot.FillPhysicsHistos(final_phy_event);				
			//h_plot.FillElectronHistograms(event,golden_el_index);
			//h_plot.FillProtonHistograms(event,golden_pr_index);
			//h_plot.FillKaonPlusHistograms(event,golden_kp_index);

			//System.out.println(">> golden kp "  + golden_kp_index );

				
			//				System.out.println("event " + counter );
			//output.writePhysicsEvent(toHipo, final_phy_event);
		    }
			    
		    //}
		    //}
		    //if( !real_event ){ System.out.println(" >> NOT REAL EVENT " ); }
		    tot_rec_events+=1;

		    /////PhysicsEvent final_phy_event = physicsbuild.setPhysicsEvent( real_event, event, golden_el_index, golden_pr_index, golden_kp_index, golden_km_index, eventtopology );
		    //PhysicsEvent mc_final_phyev = physicsbuild.setMCPhysicsEvent(event);
		    /////output.writeToTxt(final_phy_event);

		    //h_plot.FillMCHistograms(event);
			    
		    //h_mc_pr_phy.FillH1Kin(mc_final_phyev);
		    //h_mc_kp_phy.FillH1Kin(mc_final_phyev);
		    //h_mc_km_phy.FillH1Kin(mc_final_phyev);
		    //h_mc_el_phy.FillH1Kin(mc_final_phyev);
		    //h_mc_physics.FillH1Kin(mc_final_phyev);
			    
		    //h_physplot.FillMCPhysicsHistograms(mc_final_phyev);
		    count++;
		}
	    }
    	
	
    
	    counter = counter + 1;
	}
	*/   
    	
	for( int i = 0; i < n_threads; i++ ){
	    el_threads.get(i).saveHisto();
	}

	

	//	threaded_electron.saveHisto();
	//threaded_electron_2.saveHisto();

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
	
	
	boolean view_phys = true;
	System.out.println(" >> VIEWING HISTOGRAMS " );
	//h_plot.ViewHistograms();
	//h_kin.ViewHistograms();
	//h_physplot.CompareRECMCHist(false);
	//h_physplot.ViewHistograms(view_phys);
	//h_physplot.viewMCHistograms(view_phys);
	//h_detectors.ViewHistograms();
	//h_physplot.viewAcceptance();
	
	///UNCOMMENT BELOW FOR WORK
	//h_pid_cutlvls.savePIDHistograms(view_phys);
	//h_pid_mle.saveMLEHistograms(view_phys);
	//h_clas12_pid.saveCLAS12PIDHistograms(view_phys);
	//h_physplot.savePhysicsHistograms(view_phys);
	//h_res_pid.saveResolutionsHistograms(view_phys);
	//h_pid_cutlvls.mergeHistograms();

	System.out.println(">> SAVING HISTOGRAMS IS COMPLETE");
	//h_plot.SaveHistograms();
	//h_kin.SaveHistograms();
	//h_physplot.SavePhysHistograms();
	//h_physplot.SaveMCPhysHistograms();
	//h_detectors.SaveHistograms();
	//h_mc_el_phy.SaveH1Kin();
	//h_mc_pr_phy.SaveH1Kin();
	//h_mc_kp_phy.SaveH1Kin();
	//h_mc_km_phy.SaveH1Kin();
	//h_mc_physics.SaveH1Kin();
	//h_clas12_pid.printHistograms();
	//h_pid_cutlvls.printHistograms();


	BufferedWriter writer = null;
	try
	    {
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
	catch ( IOException e)
	    {
		System.out.println(">> ERROR WRITING TO FILE ");
	    }	
	finally
	    {
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


    public void ProcessData(){
	//DO SOMETHING HERE FOR HANDLING DATA VS MC/REC
    
    }
}

