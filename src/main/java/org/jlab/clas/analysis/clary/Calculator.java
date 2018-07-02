package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.clas.physics.LorentzVector;

import org.jlab.groot.fitter.*;
import org.jlab.groot.data.H1F;
import org.jlab.groot.math.F1D;
import org.jlab.groot.math.Func1D;

import java.util.*;

public class Calculator{

    public static double lv_theta( DataEvent tempevent, int rec_i){       
	DataBank recbank = tempevent.getBank("REC::Particle");
	float rec_px = recbank.getFloat("px",rec_i);
	float rec_py = recbank.getFloat("py",rec_i);
	float rec_pz = recbank.getFloat("pz",rec_i);
	float rec_vz = recbank.getFloat("vz",rec_i);
	
	double rec_p = Math.sqrt( rec_px*rec_px + rec_py*rec_py + rec_pz*rec_pz);
	double theta = Math.toDegrees(Math.acos(rec_pz/rec_p));

	return theta;
    }


    public static double lv_phi( DataEvent tempevent, int rec_i){       
	DataBank recbank = tempevent.getBank("REC::Particle");
	float rec_px = recbank.getFloat("px",rec_i);
	float rec_py = recbank.getFloat("py",rec_i);
	float rec_pz = recbank.getFloat("pz",rec_i);
	float rec_vz = recbank.getFloat("vz",rec_i);
	
	double rec_p = Math.sqrt( rec_px*rec_px + rec_py*rec_py + rec_pz*rec_pz);
	
	double rec_ang1 = Math.toDegrees(Math.acos(rec_pz/rec_p));
	double rec_ang2 = Math.toDegrees(Math.asin(rec_py/rec_p));
	double phi = Math.toDegrees(Math.atan2(rec_py,rec_px));
	phi = phi + 180 - 30.0;

	return phi;
    }


    public static LorentzVector lv_particle( DataEvent tempevent, int rec_i){

	LorentzVector lv_temp = new LorentzVector(0,0,0,0);

	DataBank recbank = tempevent.getBank("REC::Particle");
	float rec_px = recbank.getFloat("px",rec_i);
	float rec_py = recbank.getFloat("py",rec_i);
	float rec_pz = recbank.getFloat("pz",rec_i);
	float rec_vz = recbank.getFloat("vz",rec_i);

	

	double rec_e = Math.sqrt( rec_px*rec_px + rec_py*rec_py + rec_pz*rec_pz + PhysicalConstants.mass_electron*PhysicalConstants.mass_electron );
	lv_temp.setPxPyPzE(rec_px,rec_py,rec_pz,rec_e);
	return lv_temp;
    }

    public static LorentzVector lv_particle( DataBank tempbank, int rec_i){

	LorentzVector lv_temp = new LorentzVector(0,0,0,0);

	float px = tempbank.getFloat("px",rec_i);
	float py = tempbank.getFloat("py",rec_i);
	float pz = tempbank.getFloat("pz",rec_i);
	float vz = tempbank.getFloat("vz",rec_i);

	double e = Math.sqrt( px*px + py*py + pz*pz + PhysicalConstants.mass_electron*PhysicalConstants.mass_electron );
	lv_temp.setPxPyPzE(px,py,pz,e);
	return lv_temp;
    }

    public static LorentzVector lv_particle( DataBank tempbank, int rec_i, int partID){

	LorentzVector lv_temp = new LorentzVector(0,0,0,0);
	//System.out.println(" >> " + rec_i );
	float px = tempbank.getFloat("px",rec_i);
	float py = tempbank.getFloat("py",rec_i);
	float pz = tempbank.getFloat("pz",rec_i);
	float vz = tempbank.getFloat("vz",rec_i);
	//System.out.println(" >> LV elements " + px + " " + py + " " + pz );
	
	double mass = getParticleMass( partID );

	double e = Math.sqrt( px*px + py*py + pz*pz + mass*mass );
	lv_temp.setPxPyPzE(px,py,pz,e);
	return lv_temp;
    }


    public static double lv_energy(DataBank tempbank, int rec_i, int partID){
	double energy = 0.0;

	LorentzVector lv_temp = new LorentzVector(0,0,0,0);
	//System.out.println(" >> " + rec_i );
	float px = tempbank.getFloat("px",rec_i);
	float py = tempbank.getFloat("py",rec_i);
	float pz = tempbank.getFloat("pz",rec_i);
	float vz = tempbank.getFloat("vz",rec_i);
	//System.out.println(" >> LV elements " + px + " " + py + " " + pz );
	
	double mass = getParticleMass( partID );

	double e = Math.sqrt( px*px + py*py + pz*pz + mass*mass );
	lv_temp.setPxPyPzE(px,py,pz,e);
	
	return lv_temp.e();

    }


    public static double getParticleMass( int temp_partID ){

	double mass = -1.0;
	
	if ( temp_partID == PhysicalConstants.electronID ){
	    mass = PhysicalConstants.mass_electron;
	}
	if ( temp_partID == PhysicalConstants.protonID ){
	    mass = PhysicalConstants.mass_proton;
	}
	if ( temp_partID == PhysicalConstants.kaonplusID ){
	    mass = PhysicalConstants.mass_kaon;
	}
	if ( temp_partID == PhysicalConstants.kaonminusID ){
	    mass = PhysicalConstants.mass_kaon;
	}
	if ( temp_partID == PhysicalConstants.pionplusID ) {
	    mass = PhysicalConstants.mass_pion;
	}
	
	return mass;
    }

    public static LorentzVector lv_X( LorentzVector templv_1, LorentzVector templv_2, LorentzVector templv_3, LorentzVector templv_4 ){
	 LorentzVector lv_x = new LorentzVector(0,0,0,0);
	 lv_x.add(templv_1);
	 lv_x.add(templv_2);
	 lv_x.sub(templv_3);
	 lv_x.sub(templv_4);

	 return lv_x;
     }

     public static double Q2( LorentzVector templv ){
	 double q2 = 4*PhysicalConstants.eBeam*templv.e()*Math.sin(templv.theta()/2.0)*Math.sin(templv.theta()/2.0);
	 return q2;
     }

     public static double Xb( LorentzVector templv ){
	 double xb = Q2(templv) / (2.0*PhysicalConstants.mass_proton*(PhysicalConstants.eBeam - templv.e()));
	 return xb;
     }

     public static double W( LorentzVector templv ){
	 double w = Math.sqrt(-Q2(templv) + PhysicalConstants.mass_proton*PhysicalConstants.mass_proton + 2*PhysicalConstants.mass_proton*(PhysicalConstants.eBeam - templv.e()));
	 return w;
     }

     public static double T( LorentzVector templv ){
	 //LorentzVector target = new LorentzVector(0,0,0,PhysicalConstants.mass_proton);
	 //LorentzVector t = templv.sub(target);
	 double t = -2.0*PhysicalConstants.mass_proton*(templv.e() - PhysicalConstants.mass_proton);
	 return t;
     }


    public static double eventStartTime( DataEvent tempevent, int temp_pindex ){

	//GET INFORMATION FROM SCINT BANK FOR TIMING AND PATH LENGTH
	DataBank recBank = tempevent.getBank("REC::Particle");
	DataBank eventBank = tempevent.getBank("REC::Event");

	double temp_p = 0.0;
	//for( int i = 0; i < tempevent.getBank("REC::Particle").rows(); i++ ){	   
	int charge = recBank.getInt("charge", temp_pindex);
	double p = lv_particle(tempevent, temp_pindex).p();
	
 	double start_time = -100000;

	//	DataBank eventBank = tempevent.getBank("REC::Event");
	//System.out.println(" >> event time  " + eventBank.getFloat("STTime",0) );
	
  	if( tempevent.hasBank("REC::Scintillator") ){
	    double v = 29.98; //assume speed of light for particle 
	    DataBank scintBank = tempevent.getBank("REC::Scintillator");	   	    

	    boolean l1_hit = false;
	    boolean l2_hit = false;
	    boolean foundTriggerTime = false;

	    float temp_ftof = 0;
	    float temp_ftof_l2 = 0;
	    float temp_r_path = 0;
	    float temp_r_path_l2 = 0;
	    
	    for( int i = 0; i < tempevent.getBank("REC::Scintillator").rows(); i++ ){
		//System.out.println(" >> pindex " + temp_pindex );
		int detector = scintBank.getInt("detector",i);
		if( scintBank.getShort("pindex", i) == temp_pindex && detector == 12 ){
		    int scint_layer = scintBank.getInt("layer",i);
		    
		    if( scint_layer == 2 ){
			//System.out.println(" >> HIT LAYER 2 " );
			temp_ftof = scintBank.getFloat("time",i);
			temp_r_path = scintBank.getFloat("path",i);
			//start_time = temp_ftof_l2 - temp_r_path_l2/v;
			//System.out.println(">> " + temp_ftof_l2 + " "  + temp_r_path_l2 ); 
			l2_hit = true;
			foundTriggerTime = true;
		    }
		    else if( scint_layer == 1 ){
			//System.out.println(" >> HIT LAYER 1 " );
			temp_ftof = scintBank.getFloat("time",i);
			temp_r_path =  scintBank.getFloat("path",i);		       
			//start_time = temp_ftof_l1 - temp_r_path_l1/v;
			//System.out.println(">> " + temp_ftof_l1 + " "  + temp_r_path_l1 );
			l1_hit = true;
			foundTriggerTime = true;
		    }

		    //if( l1_hit && l2_hit ){
			//System.out.println(" >> HIT IN BOTH LAYERS "  );
			//System.out.println(" >> " + temp_ftof_l1 + " " + temp_ftof_l2  );
		    //	double ftof_avg = (temp_ftof_l1 + temp_ftof_l2)/2.0;
		    //	double r_path_avg = (temp_r_path_l1 + temp_r_path_l2) / 2.0;
		    //	start_time =ftof_avg - r_path_avg/v;
		    //}
		}
		//else{ //(// scintBank.getShort("pindex", i) == temp_pindex && detector == 4 ){  
		    //System.out.println(" >> OH NO IN CTOF " );
		    start_time = -100000;
		    //}

		if( foundTriggerTime ){
		    double tof = temp_r_path/PhysicalConstants.speedOfLight;
		    double starttime = temp_ftof - tof;
		    double delta_trigger_time = -starttime + eventBank.getFloat("RFTime",0) + (800 + 0.5)*2.004 + 0.0;
		    double rfcorr = delta_trigger_time%2.004 - 2.004/2.0;
		    start_time = starttime + rfcorr;
		}
		else{
		    start_time = -100000;
		}
	    }
	}
    	//start_time = 3;
	System.out.println(" >> START TIME CALCULATED " + start_time + " "  );
	return start_time;

    }

     public static double betaTime( DataEvent tempevent, int temp_pindex, int layer){

	 DataBank eventBank = tempevent.getBank("REC::Event");
	 float t_start = eventBank.getFloat("STTime",0);

	 double betaTime = -10000;
	 //System.out.println( " >> PINDEX OR K IS " + temp_pindex );
	 ////////////////////////////////////////////////////////////
	 //GET INFORMATION FROM SCINT BANK FOR TIMING AND PATH LENGTH
	 if( tempevent.hasBank("REC::Scintillator") ){
	     DataBank scintBank = tempevent.getBank("REC::Scintillator");	   	    
 	     for( int i = 0; i < tempevent.getBank("REC::Scintillator").rows(); i++ ){
		 if( scintBank.getShort("pindex", i) == temp_pindex ){
		     int scint_layer = scintBank.getInt("layer",i);		    
		     float t_ftof = scintBank.getFloat("time",i);
		     //System.out.println(" >> LAYER " + scint_layer );
		     if ( scint_layer == layer ){
			 if( t_start != -1000 ){
			     float r_path = scintBank.getFloat("path",i);
			     //System.out.println(" >> t_ftot & r_path  " + t_ftof  + " " + r_path + " sttime "  + t_start);
			     
			     double calc_tof = t_ftof - t_start;
			     betaTime = r_path/calc_tof * (1.0/PhysicalConstants.speedOfLight);
			     //System.out.println(" >> betaTime " + betaTime );
			 }
			 else {			     
			     float r_path = scintBank.getFloat("path",i);
			     //t_start = (float)eventStartTime(tempevent, temp_pindex );
			     double calc_tof = t_ftof - t_start;
			     //System.out.println(" >> t_ftot & r_path  " + t_ftof  + " " + r_path + " sttime "  + t_start);

			     betaTime = r_path/calc_tof * (1.0/PhysicalConstants.speedOfLight);
			 }
		     }
		 }
		 
	     }
	 }
	 //System.out.println(" >> " + betaTime );
	 return betaTime;
     }
	 
    //USEFUL FOR DELTA BETA
    public static double betaMntm( DataEvent tempevent, int rec_i, double had_mass ){
	DataBank recBank = tempevent.getBank("REC::Particle");
	
	LorentzVector lv_part = lv_particle(tempevent, rec_i);
	
	double beta = lv_part.p()/Math.sqrt( lv_part.p()*lv_part.p() + had_mass*had_mass );
	return beta;

    }

    public static double betaMntm( DataEvent tempevent, int rec_i, int particleID ){
	DataBank recBank = tempevent.getBank("REC::Particle");
	
	LorentzVector lv_part = lv_particle(recBank, rec_i, particleID);
	double had_mass = getParticleMass(particleID);

	double beta = lv_part.p()/Math.sqrt( lv_part.p()*lv_part.p() + had_mass*had_mass );
	return beta;

    }

    //public static GraphErrors graphMeanSlices(){



    //}


    public static F1D fitHistogram( H1F h_temp ){

	//System.out.println(" >> FITTING HISTOGRAM " + h_temp.getName() );
	double xlow, xhigh, histmax;
	int binlow, binhigh, binmax;

	double percentofmax = 0.45;
    
	F1D fit = null;

	//if( h_temp.getEntries() > 0 ){
	binmax = h_temp.getMaximumBin();
	histmax = h_temp.getMax();
	binlow = binmax;
	binhigh = binmax;
	try{	
	    while( h_temp.getBinContent(binhigh++) >= percentofmax*histmax && binhigh <= h_temp.getAxis().getNBins() ){}
	    while( h_temp.getBinContent(binlow--) >= percentofmax*histmax && binlow > 1 ){}
	    
	    xlow = h_temp.getDataX(binlow) - h_temp.getDataEX(binlow)/2.0; // needs to be low edge, only center now
	    xhigh = h_temp.getDataX(binhigh+1) - h_temp.getDataEX(binhigh+1)/2.0;
 	    
	    //System.out.println(" >> values used " + xlow + " " + xhigh + " " + histmax );
	    
	    F1D fit_temp = new F1D("fit_temp","[amp]*gaus(x,[mean],[sigma])", xlow, xhigh );
	    fit_temp.setParameter(0, histmax);
	    fit_temp.setParameter(1, h_temp.getMean() );
	    fit_temp.setParameter(2, h_temp.getRMS() );
	    
	    DataFitter.fit(fit_temp, h_temp, "REQ"); //was only R at first
	    fit = fit_temp;  

	    //}
	    //  System.out.println(" >> PARAMETER SET " + fit_temp.getParameter(0) + " " + fit_temp.getParameter(1) + " " + fit_temp.getParameter(2) );

	}
	catch(Exception e){
	    System.out.println("ERROR WITH FITTING - LIKELY IT DID NOT CONVERGE");
	}
	return fit;	    
    }

    public static F1D fitHistogram( H1F h_temp, double temp_percentofmax ){

	//	System.out.println(" >> FITTING HISTOGRAM " + h_temp.getName() );
	double xlow, xhigh, histmax;
	int binlow, binhigh, binmax;

	double percentofmax = temp_percentofmax;
    
	F1D fit = null;

	//if( h_temp.getEntries() > 0 ){
	binmax = h_temp.getMaximumBin();
	histmax = h_temp.getMax();
	binlow = binmax;
	binhigh = binmax;
	try{	
	    while( h_temp.getBinContent(binhigh++) >= percentofmax*histmax && binhigh <= h_temp.getAxis().getNBins() ){}
	    while( h_temp.getBinContent(binlow--) >= percentofmax*histmax && binlow > 1 ){}
	    
	    xlow = h_temp.getDataX(binlow) - h_temp.getDataEX(binlow)/2.0; // needs to be low edge, only center now
	    xhigh = h_temp.getDataX(binhigh+1) - h_temp.getDataEX(binhigh+1)/2.0;
 	    
	    //System.out.println(" >> values used " + xlow + " " + xhigh + " " + histmax );
	    
	    F1D fit_temp = new F1D("fit_temp","[amp]*gaus(x,[mean],[sigma])", xlow, xhigh );
	    fit_temp.setParameter(0, histmax);
	    fit_temp.setParameter(1, h_temp.getMean() );
	    fit_temp.setParameter(2, h_temp.getRMS() );
	    
	    DataFitter.fit(fit_temp, h_temp, "REQ"); //was only R at first
	    fit = fit_temp;  

	    //}
	    //  System.out.println(" >> PARAMETER SET " + fit_temp.getParameter(0) + " " + fit_temp.getParameter(1) + " " + fit_temp.getParameter(2) );

	}
	catch(Exception e){
	    System.out.println("ERROR WITH FITTING - LIKELY IT DID NOT CONVERGE");
	}
	return fit;	    
    }

    public static F1D fitHistogramRange( H1F h_temp, double temp_percentofmax, double min, double max){

	//System.out.println(" >> FITTING HISTOGRAM " + h_temp.getName() );
	double xlow, xhigh, histmax;

 	double percentofmax = temp_percentofmax; 
	int n_copybins = (int) ((int)h_temp.getXaxis().getNBins()*( max - min)/(h_temp.getXaxis().max() - h_temp.getXaxis().min() ));

	H1F h_copy = new H1F("h_copy","h_copy",n_copybins, min, max );
	for( int bins = 0; bins < n_copybins; bins++){
	    h_copy.setBinContent(bins, h_temp.getBinContent(bins) );	
	}
	histmax = h_copy.getMax();
	
	F1D fit = null;

	try{
	    F1D fit_temp = new F1D("fit_temp","[amp]*gaus(x,[mean],[sigma])", min, max );
	    fit_temp.setParameter(0, histmax);
	    fit_temp.setParameter(1, h_copy.getMean() );
	    fit_temp.setParameter(2, h_copy.getRMS() );
	    
	    DataFitter.fit(fit_temp, h_temp, "REQ"); //was only R at first
	    fit = fit_temp;  
		
	    //  System.out.println(" >> PARAMETER SET " + fit_temp.getParameter(0) + " " + fit_temp.getParameter(1) + " " + fit_temp.getParameter(2) );
	}
	catch( Exception e ){
	    System.out.println(" ERROR FITTING HISTOGRAM RANGE ");
	}
	return fit;	    
    }
    
    public static HashMap xyzToUVW( DataEvent tempevent, int temp_pindex ){

	HashMap<Integer, ArrayList<Double> > m_ec_hit_uvw = new HashMap<Integer, ArrayList<Double> >();

	////////////////////////////////////////////////
	//EC PARAMETERS KEEP HERE FOR TESTING, MOVE LATER
	//ALSO ALL FROM CLAS6 SO MIGHT BE DATED
	double ec_the = 0.4363323;
	double ylow = -182.974;
	double yhi = 189.956;
	double tgrho = 1.95325;
	double sinrho = 0.8901256;
	double cosrho = 0.455715;
	
	Double m_rot[][] = new Double[3][3];

	if( tempevent.hasBank("REC::Calorimeter") ){
	    DataBank ecBank = tempevent.getBank("REC::Calorimeter");
	    for( int i = 0; i < tempevent.getBank("REC::Calorimeter").rows(); i++ ){
		int detector = ecBank.getInt("detector",i );
		if( ecBank.getShort("pindex",i) == temp_pindex && (detector == Detectors.ec_eo || detector == Detectors.ec_ei ) ){
		    ArrayList<Double> hit_pos = new ArrayList<Double>();
		    
		    double hit_x = ecBank.getFloat("x",i);
		    double hit_y = ecBank.getFloat("y",i);
		    double hit_z = ecBank.getFloat("z",i);
		    
		    Double phi = Math.atan2( hit_y, hit_x )*57.29578;
		    if( phi<0.0 ){ phi = phi + 360.0; }
		    phi = phi + 30.0;
		    if( phi >= 360.0 ){ phi = phi - 360.0; }
		    Double angle_seg = phi/60.0;
		    double ec_phi = ((angle_seg.intValue()))*1.0471975;
		    m_rot[0][0] = Math.cos(ec_the)*Math.cos(ec_phi);
		    m_rot[0][1] = -Math.sin(ec_phi);
		    m_rot[0][2] = Math.sin(ec_the)*Math.cos(ec_phi);
		    m_rot[1][0] = Math.cos(ec_the)*Math.sin(ec_phi);
		    m_rot[1][1] = Math.cos(ec_phi);
		    m_rot[1][2] = Math.sin(ec_the)*Math.sin(ec_phi);
		    m_rot[2][0] = -Math.sin(ec_the);
		    m_rot[2][1] = 0.0;
		    m_rot[2][2] = Math.cos(ec_the);
		    
		    double yi = hit_x*m_rot[0][0] + hit_y*m_rot[1][0] + hit_z*m_rot[2][0];
		    double xi = hit_x*m_rot[0][1] + hit_y*m_rot[1][1] + hit_z*m_rot[2][1];
		    double zi = hit_x*m_rot[0][2] + hit_y*m_rot[1][2] + hit_z*m_rot[2][2];
		    zi = zi - 510.32 ;// IS THIS DISTANCE TO TARGET ???? WILL NEED TO CHANGE 
		    
		    double u_cord = (yi - ylow )/sinrho;
		    double v_cord = (yhi - ylow)/tgrho - xi + (yhi - yi)/tgrho;
		    double w_cord = ((yhi - ylow)/tgrho + xi + (yhi-yi)/tgrho)/2.0/cosrho;
		    //System.out.println(" >> XYZ " + hit_x +  " " + hit_y + " " + hit_z); 
		    //System.out.println(" >> UVW " + u_cord +  " " + v_cord + " " + w_cord); 
		    hit_pos.add(u_cord);
		    hit_pos.add(v_cord);
		    hit_pos.add(w_cord);
		    m_ec_hit_uvw.put( detector, hit_pos );
		}
	    }
	}
	return m_ec_hit_uvw;
    }

    public static double faradayCupCurrent(DataEvent event ){
	double beamCurrent = 0.0;
	// Decoding Faraday Cup Scaler
	if(event.hasBank("RAW::scaler")){
	    DataBank rawScaler = event.getBank("RAW::scaler");
	    for(int k=0;k<rawScaler.rows(); k++){
		if(rawScaler.getInt("channel",k)==0 && rawScaler.getInt("slot",k)==0){
		    int FCscaler = rawScaler.getInt("value",k);
		    // 30 Hz minus 0.5 ms dead for Helicity
		    double trueFreq = FCscaler / (0.03333 - 0.0005);
		    beamCurrent = (trueFreq-100.0)/906.2;
		    //System.out.println(String.format("Scaler count " + FCscaler + " , frequency " + trueFreq*0.001f + " kHz , beam current " + beamCurrent + " nA"));
		}
	    }
	}
	return beamCurrent;
    }

    public static double faradayCupIntegratedCharge( DataEvent event){

	double beamCharge = 0.0;
	// Decoding Faraday Cup Scaler
	if(event.hasBank("RAW::scaler")){
	    DataBank rawScaler = event.getBank("RAW::scaler");
	    for(int k=0;k<rawScaler.rows(); k++){
		if(rawScaler.getInt("channel",k)==32 && rawScaler.getInt("slot",k)==0){ //1 for ungated 
		    int FCscaler = rawScaler.getInt("value",k);
		    // 30 Hz minus 0.5 ms dead for Helicity
		    double trueFreq = FCscaler / (0.03333 - 0.0005);
		    double beamCurrent = (trueFreq-100f)/906.2;
		    beamCharge = beamCurrent * (0.03333f - 0.0005);
		    //System.out.println(String.format("Scaler count " + FCscaler + " , frequency " + trueFreq*0.001f + " kHz , beam current " + beamCurrent + " nA, beam charge " + beamCharge));
		}
	    }
	}
	return beamCharge;
    }
    
    public static Vector<Double> getTrajRotatedCoordindate( double cx , double cy, int sec ){
	Vector<Double> v_temp = new Vector<Double>();
	
	double x1_rot = cy * Math.sin(sec*60.0*Math.PI/180) + cx * Math.cos(sec*60.0*Math.PI/180);
	double y1_rot = cy * Math.cos(sec*60.0*Math.PI/180) - cx * Math.sin(sec*60.0*Math.PI/180);

	v_temp.add(x1_rot);
	v_temp.add(y1_rot);

	return v_temp;


    }

    public static Vector<Double> getRotatedCoordinates( double temp_x, double temp_y, int sector ){
	
	Vector<Double> v_temp = new Vector<Double>();

	double rot_angle = 0.0;
	switch (sector ){
	case 0: 
	    rot_angle = Math.PI/2.0;
	    break;
	case 1:
	    rot_angle = Math.PI/6.0;
	    break;
	case 2:
	    rot_angle = -Math.PI/6.0;
	    break;
	case 3:
	    rot_angle = -Math.PI/2.0;
	    break;
	case 4:
	    rot_angle = -(5.0/6.0)*Math.PI;
	    break;
	case 5:
	    rot_angle = (5.0/6.0)*Math.PI;
	    break;
	}
	
	double x_rot = -temp_y*Math.sin(rot_angle) + temp_x*Math.cos(rot_angle);
	double y_rot = temp_x*Math.sin(rot_angle) + temp_y*Math.cos(rot_angle);
	
	v_temp.add(x_rot);
	v_temp.add(y_rot);

	return v_temp;
    }

}
