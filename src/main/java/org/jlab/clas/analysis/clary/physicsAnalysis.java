package org.jlab.clas.analysis.clary;

import java.io.*;

import org.jlab.io.hipo.HipoDataSource;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import org.jlab.analysis.plotting.H1FCollection2D;
import org.jlab.analysis.plotting.H1FCollection3D;
import org.jlab.analysis.plotting.TCanvasP;
import org.jlab.analysis.plotting.TCanvasPTabbed;
import org.jlab.groot.graphics.EmbeddedCanvas;

import org.jlab.analysis.math.ClasMath;
import org.jlab.clas.physics.Vector3;
import org.jlab.clas.physics.LorentzVector;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;


public class physicsAnalysis{


    public void SetBaseHistograms(){

       	

    }


    public static void main( String[] analysisName ){


	String file_loc = "/volatile/halla/sbs/bclary/clas12Analysis/clary_phiPID/";
	String file_name = "pid_phi_";
	String file_ext = ".hipo";

	HipoDataSource reader = new HipoDataSource();
	int f_counter = 0;
	int max_files = 30;

	/////////////////////////////////////
	//HISTOGRAMS
	
	H1F h_tb0_cos = new H1F("h_tb0_cos","h_tb0_cos", 10, -1.0, 1.0);
	H1F h_tb1_cos = new H1F("h_tb1_cos","h_tb1_cos", 10, -1.0, 1.0);
	H1F h_tb2_cos = new H1F("h_tb2_cos","h_tb2_cos", 10, -1.0, 1.0);
	H1F h_tb3_cos = new H1F("h_tb3_cos","h_tb3_cos", 10, -1.0, 1.0);
	H1F h_tb4_cos = new H1F("h_tb4_cos","h_tb4_cos", 10, -1.0, 1.0);

	//////////////////////////////////////
	//BIN LIMITS
	double min_b1 = 0.0; double max_b1 = 0.1;
	double min_b2 = 0.1; double max_b2 = 0.3;
	double min_b3 = 0.3; double max_b3 = 0.5;
	double min_b4 = 0.5; double max_b4 = 0.7;
    	
	for( int i = 1; i <= max_files; i++ ){

	    String f_name = file_loc + file_name + Integer.toString(i) + file_ext;
	    File file_to_analyze = new File(f_name);
	    System.out.println("PROCESSING FILE " + f_name);

	    if( file_to_analyze.exists() ){
		System.out.println("FILE EXISTS");
	    }
	    else if( !file_to_analyze.exists() ){
		System.out.println("SOMETHING WRONG WITH FILE");
		continue;
	    }

	    reader.open(f_name);
	    DataEvent nullevent = reader.getNextEvent();
	    int event_n = 0;
	    int nevent = reader.getSize();
	    System.out.println(">> NUMBER OF EVENTS TO PROCESS " + nevent );
	    //while(reader.hasEvent()){
	    for ( int n = 0; n <= nevent; n++ ){
		event_n++;
		try{
		DataEvent event = reader.gotoEvent(n);//getNextEvent();

		/*boolean phyevent_present = event.hasBank("PhysicsEvent");
		if( phyevent_present ){
		    DataBank phyevent = event.getBank("PhysicsEvent");

		    double t = -(phyevent.getDouble("t",0));
		    double cos_theta = phyevent.getDouble("cm_theta",0);

		    h_tb0_cos.fill(cos_theta);
		    if( min_b1 < t && t <= max_b1 ){
			h_tb1_cos.fill(cos_theta);
		    }
		    else if( min_b2 < t && t <= max_b2 ){
			h_tb2_cos.fill(cos_theta);
		    }
		    else if( min_b3 < t && t <= max_b3 ){
			h_tb3_cos.fill(cos_theta);
		    }
		    else if( min_b4 < t ){
			h_tb4_cos.fill(cos_theta);
		    }
		    }*/
		}
		catch( Exception e){
		    //throw e;
		    System.out.println(">>EXCEPTION WAS THROWN");
		    //continue;
		}

	    }
	    

	}

	EmbeddedCanvas c_t_cos = new EmbeddedCanvas();
	c_t_cos.setSize(1200,400);
	c_t_cos.divide(4,1);
	c_t_cos.cd(0);
	h_tb0_cos.setTitle("-t = 0.1 GeV");
	h_tb0_cos.setTitleX("cos(#theta) K^+");
	c_t_cos.draw(h_tb0_cos);

	c_t_cos.cd(1);
	h_tb2_cos.setTitle("-t = 0.3 GeV");
	h_tb2_cos.setTitleX("cos(#theta) K^+");
	c_t_cos.draw(h_tb2_cos);

	c_t_cos.cd(2);
	h_tb3_cos.setTitle("-t = 0.5 GeV");
	h_tb3_cos.setTitleX("cos(#theta) K^+");
	c_t_cos.draw(h_tb3_cos);

	c_t_cos.cd(3);
	h_tb4_cos.setTitle("-t = 0.7 GeV");
	h_tb4_cos.setTitleX("cos(#theta) K^+");
	c_t_cos.draw(h_tb4_cos);

	c_t_cos.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_t_cos.png");

    }
    
}



