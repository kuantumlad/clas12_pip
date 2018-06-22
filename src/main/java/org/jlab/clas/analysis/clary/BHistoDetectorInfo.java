package org.jlab.clas.analysis.clary;


import java.io.*;
import javax.swing.JFrame;
import java.util.*;

import org.jlab.clas.analysis.clary.BEvent;
import org.jlab.clas.analysis.clary.PhysicsBuilder;
import org.jlab.clas.analysis.clary.Calculator;
import org.jlab.clas.analysis.clary.Detectors;

import org.jlab.analysis.plotting.H1FCollection2D;
import org.jlab.analysis.plotting.H1FCollection3D;
import org.jlab.analysis.plotting.TCanvasP;
import org.jlab.analysis.plotting.TCanvasPTabbed;
import org.jlab.groot.graphics.EmbeddedCanvas;

import org.jlab.analysis.math.ClasMath;
import org.jlab.clas.physics.Vector3;
import org.jlab.clas.physics.LorentzVector;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.fitter.*;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.math.Axis;
import org.jlab.groot.graphics.GraphicsAxis;

import org.jlab.groot.math.Func1D;
import org.jlab.groot.math.F1D;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;


public class BHistoDetectorInfo {

    EmbeddedCanvas c_ftof_time = new EmbeddedCanvas();
    EmbeddedCanvas c_ec_energy = new EmbeddedCanvas();
    EmbeddedCanvas c_chkov_hit = new EmbeddedCanvas();
    EmbeddedCanvas c_ec_hit = new EmbeddedCanvas();
    EmbeddedCanvas c2_pcal_hit = new EmbeddedCanvas();
	
    H1F h_ftof_tl1, h_ftof_tl2;
    H1F h_ec_pcal;
    H1F h_ec_ei;
    H1F h_ec_eo;
    H1F h_chkov_nphe;
    H2F h2_chkov_nphep;

    H2F h_ec_hitxy;
    H2F h2_ecei_hitxy;
    H2F h2_eceo_hitxy;
    H2F h2_pcal_hitxy;
    H2F h_ec_sf;
    

    public void CreateHistograms(){

	h_ftof_tl1 = new H1F("h_ftof_tl1","h_ftof_tl1", 200, 0.0, 1000.0);
	h_ftof_tl2 = new H1F("h_ftof_tl2","h_ftof_tl2", 200, 0.0, 1000.0);
	h_ec_pcal = new H1F("h_ec_pcal","h_ec_pcal",100, 0.0, 1.5);
	h_ec_ei = new H1F("h_ec_ei","h_ec_ei",100, 0.0, 1.5);
	h_ec_eo = new H1F("h_ec_eo","h_ec_eo",100, 0.0, 1.5);
	h_chkov_nphe = new H1F("h_chkov_nphe","h_chkov_nphe",100, 0.0, 100 );

	h2_ecei_hitxy = new H2F("h2_ecei_hitxy","h2_ecei_hitxy",200, -500.0, 500.0, 200, 500.0, -500.0);
	h2_eceo_hitxy = new H2F("h2_eceo_hitxy","h2_eceo_hitxy",200, -500.0, 500.0, 200, 500.0, -500.0);

	h2_pcal_hitxy = new H2F("h2_pcal_hitxy","h2_pcal_hitxy",200, -500.0, 500.0, 200, 500.0, -500.0);
	h_ec_sf = new H2F("h_ec_sf","h_ec_sf",100, 0.0, 10.5, 100, 0.01, 0.5);
	h2_chkov_nphep = new H2F("h_chkov_nphep","h_chkov_nphep", 100, 0.0, 100, 100, 0.0, 10.5);
	
    }

    public void FillFTOFHist( DataEvent tempevent, int rec_i ){

	int layer_1 = 1;
	int layer_2 = 2;

	double tl1 = Detectors.ftofTiming( tempevent, rec_i, layer_1 );
	double tl2 = Detectors.ftofTiming( tempevent, rec_i, layer_2 );

	h_ftof_tl1.fill( tl1 );
	h_ftof_tl2.fill( tl2 );

	
    }

    public void FillECHist( DataEvent tempevent, int rec_i ){

	ArrayList<Double> pcalhits = new ArrayList<Double>();
	pcalhits = Detectors.PCALHit(tempevent, rec_i);
	if( pcalhits.size() > 0 ){
	    double pcal_hitX = pcalhits.get(0);
	    double pcal_hitY = pcalhits.get(1);
	    h2_pcal_hitxy.fill( pcal_hitX, pcal_hitY );
	}

	double ecdep_tot = 0;
	HashMap<Integer, Double> m_edep = Detectors.getEDepCal( tempevent, rec_i );

	for( Map.Entry<Integer,Double> entry : m_edep.entrySet() ){
	    int layer = entry.getKey();
	    double edep = entry.getValue();
	    if( layer == Detectors.pcal ){
		h_ec_pcal.fill( edep );
	    }
	    if( layer == Detectors.ec_ei ){
		h_ec_ei.fill( edep );
	    }
	    if( layer == Detectors.ec_eo ){
		h_ec_eo.fill( edep );
	    }
	    ecdep_tot = ecdep_tot + edep;
	}


	HashMap<Integer, ArrayList<Double> > m_ecal_hit = new HashMap<Integer, ArrayList<Double> >();
	ArrayList<Double> hit_pos = new ArrayList<Double>();
	m_ecal_hit = Detectors.ECHit(tempevent, rec_i );
	//System.out.println(" >> ecal hit size " + m_ecal_hit.size() );
	for( Map.Entry<Integer, ArrayList<Double> > entry : m_ecal_hit.entrySet() ){
	    int detector = entry.getKey();
	    hit_pos = entry.getValue();
	    //System.out.println(" >> array size " + hit_pos.size() );
	    if( detector == Detectors.ec_ei ){
		h2_ecei_hitxy.fill( hit_pos.get(0), hit_pos.get(1) );
		//System.out.println(" >> ECAL HIT " + hit_pos.get(0) + " " + hit_pos.get(1) );
	    }
	    if( detector == Detectors.ec_eo ){
		h2_eceo_hitxy.fill( hit_pos.get(0), hit_pos.get(1) );
		//System.out.println(" >> ECAL HIT " + hit_pos.get(0) + " " + hit_pos.get(1) );
	    }

	    hit_pos.clear();

	}


	DataBank recBank = tempevent.getBank("REC::Particle");
	double p = Calculator.lv_particle( recBank, rec_i, PhysicalConstants.electronID).p();
	double ec_sf = ecdep_tot/p;	
	//System.out.println(" >> SF " + ec_sf + " " + p );
	h_ec_sf.fill( p, ec_sf );

	//System.out.println(" >> size "  + pcalhits.size() + " " + pcal_hitX + " " + pcal_hitY );
	

    }

    public void FillCherenkovHist( DataEvent tempevent, int rec_i ){

	DataBank chkovBank = tempevent.getBank("REC::Cherenkov");
	int nphe = 0;
	for( int i = 0; i < chkovBank.rows(); i++ ){
	    int pindex =  chkovBank.getShort("pindex",i);                                                                                                                                                                                                                 
	    if( pindex == rec_i ){                                                                                                                                                                                                                                        
		nphe = chkovBank.getShort("nphe",i);                                                                                                                                                                                                                      
		//System.out.println(" >> NPHE " + nphe );                                                                                                                                                                                                                
	    }                                           
	}
	DataBank recBank = tempevent.getBank("REC::Particle");
	double p = Calculator.lv_particle( recBank, rec_i ).p();
	h_chkov_nphe.fill(nphe);
	h2_chkov_nphep.fill( nphe, p );
	
	
    }


    public void fillDriftChamberHist( DataEvent event, int rec_i ){

	


    }


    public void fillFaradayCupInformation( DataEvent event ){

    }

    public void ViewHistograms(){

	c_ftof_time.setSize(1600,800);
	c_ftof_time.divide(2,1);
	c_ftof_time.cd(0);
	h_ftof_tl1.setTitle("Local Time of hit Layer 1");
	h_ftof_tl1.setTitleX("time [ns]");
	h_ftof_tl1.setTitleY("Entries");
	h_ftof_tl1.setOptStat(1110);
	c_ftof_time.draw(h_ftof_tl1);
	c_ftof_time.cd(1);
	h_ftof_tl2.setTitle("Local Time of hit Layer 2");
	h_ftof_tl2.setTitleX("time [ns]");
	h_ftof_tl2.setTitleY("Entries");
	h_ftof_tl2.setOptStat(1110);
	c_ftof_time.draw(h_ftof_tl2);

	c_ec_energy.setSize(800,800);
	c_ec_energy.divide(2,2);
	c_ec_energy.cd(0);
	h_ec_pcal.setTitle("PCAL Edep Electron Candidate");
	h_ec_pcal.setTitleX("Energy Deposited [GeV]");
	h_ec_pcal.setOptStat(1110);
	c_ec_energy.draw(h_ec_pcal);
	c_ec_energy.cd(1);
	h_ec_ei.setTitle("EC EI Edep Electron Candidate");
	h_ec_ei.setTitleX("Energy Deposited [GeV]");
	h_ec_ei.setOptStat(1110);
	c_ec_energy.draw(h_ec_ei);
	c_ec_energy.cd(2);
	h_ec_eo.setTitle("EC EO Edep Electron Candidate");
	h_ec_eo.setTitleX("Energy Deposited [GeV]");
	h_ec_eo.setOptStat(1110);
	c_ec_energy.draw(h_ec_eo);
	c_ec_energy.cd(3);
	h_ec_sf.setTitle("Sampling Fraction Electron Candidate");
	h_ec_sf.setTitleX("Momentum [GeV]");
	h_ec_sf.setTitleY("Energy Deposited [GeV]" );
	c_ec_energy.draw(h_ec_sf,"colz");
	
	//h_ec_hitxy.setTitle("EC Hit Position");
	//h_ec_hitxy.setTitleX("EC Hit positon X [cm]");
	//h_ec_hitxy.setTitleY("EC Hit position Y [cm]");
	//c_ec_hit.draw(h_ec_hitxy,"colz");

	c_chkov_hit.setSize(1600,800);
	c_chkov_hit.divide(2,1);
	c_chkov_hit.cd(0);
	h_chkov_nphe.setTitle("NPHE Cherenkov");
	h_chkov_nphe.setTitleX("Number of Photoelectrons");
	c_chkov_hit.draw(h_chkov_nphe);
	c_chkov_hit.cd(1);
	h2_chkov_nphep.setTitle("NPHE Cherenkov");
	h2_chkov_nphep.setTitleX("Number of Photoelectrons");
	h2_chkov_nphep.setTitleY("p [GeV]");
	c_chkov_hit.draw(h2_chkov_nphep, "colz");
	
	c2_pcal_hit.setSize(800,800);
	h2_pcal_hitxy.setTitle(" PCAL Hit Position " );
	h2_pcal_hitxy.setTitleX("x [cm]");
	h2_pcal_hitxy.setTitleY("y [cm]");
	c2_pcal_hit.draw(h2_pcal_hitxy,"colz");	

	c_ec_hit.setSize(1600,800);
	c_ec_hit.divide(2,1);
	c_ec_hit.cd(0);
	h2_ecei_hitxy.setTitleX("x [cm]");
	h2_ecei_hitxy.setTitleY("y [cm]");
	c_ec_hit.draw(h2_ecei_hitxy,"colz");
	c_ec_hit.cd(1);
	h2_eceo_hitxy.setTitleX("x [cm]");
	h2_eceo_hitxy.setTitleY("y [cm]");
	c_ec_hit.draw(h2_eceo_hitxy,"colz");
	
	

    }


    public void SaveHistograms(){

	c_ftof_time.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_ftof_time.png");
	c_ec_hit.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_ec_hitxy.png");
	c_chkov_hit.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h12_chkov_hit.png");
	c2_pcal_hit.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h2_pcal_hitxy.png");
    }


}
