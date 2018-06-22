package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;
import javax.swing.JFrame;

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
import org.jlab.groot.data.DataVector;
import org.jlab.groot.math.Axis;
import org.jlab.groot.graphics.GraphicsAxis;

import org.jlab.groot.math.Func1D;
import org.jlab.groot.math.F1D;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;


public class BHistoKinPlotter {

    //f_kJFrame f_kin = new JFrame();
    
    EmbeddedCanvas c_beta_el = new EmbeddedCanvas();
    EmbeddedCanvas c_beta_pr = new EmbeddedCanvas();
    EmbeddedCanvas c_beta_kp = new EmbeddedCanvas();
    EmbeddedCanvas c_beta_km = new EmbeddedCanvas();

    EmbeddedCanvas c_beta_pos = new EmbeddedCanvas();
    EmbeddedCanvas c_beta_neg = new EmbeddedCanvas();

    EmbeddedCanvas c_beta_res_pr = new EmbeddedCanvas();
    EmbeddedCanvas c2_beta_res_pr = new EmbeddedCanvas();
    EmbeddedCanvas c_beta_resfits_pr = new EmbeddedCanvas();

    EmbeddedCanvas c_beta_res_pr2 = new EmbeddedCanvas();
    

    H1F h_scint_time_l1;
    H1F h_scint_time_l2;

    H2F h2_betatime_pos_l1;
    H2F h2_betatime_neg_l1;
    H2F h2_betatime_el_l1;
    H2F h2_betatime_pr_l1;
    H2F h2_betatime_kp_l1;
    H2F h2_betatime_km_l1;

    H2F h2_betatime_pos_l2;
    H2F h2_betatime_neg_l2;
    H2F h2_betatime_el_l2;
    H2F h2_betatime_pr_l2;
    H2F h2_betatime_kp_l2;
    H2F h2_betatime_km_l2;

    H2F h2_delbetatime_pos_l1;
    H2F h2_delbetatime_neg_l1;
    H2F h2_delbetatime_el_l1;
    H2F h2_delbetatime_pr_l1;
    H2F h2_delbetatime_kp_l1;
    H2F h2_delbetatime_km_l1;

    H2F h2_delbetatime_pos_l2;
    H2F h2_delbetatime_neg_l2;
    H2F h2_delbetatime_el_l2;
    H2F h2_delbetatime_pr_l2;
    H2F h2_delbetatime_kp_l2;
    H2F h2_delbetatime_km_l2;

    H2F h2_betamntm_pos;
    H2F h2_betamntm_neg;
    H2F h2_betamntm_el;
    H2F h2_betamntm_pr;
    H2F h2_betamntm_kp;
    H2F h2_betamntm_km;

    H2F h2_betabank_pos;
    H2F h2_betabank_neg;

    int scint_layer1 = 1;
    int scint_layer2 = 2;
    
    H1F h_res_beta_pr;
    H1F h_res_mcbeta_pr;

    H2F h_test;

    Map<Integer, Vector<H1F>> m_beta_sector_layers = new HashMap<Integer, Vector<H1F>>();   
    Map<Integer, Vector<H2F>> m_delbeta2_sector_layers = new HashMap<Integer, Vector<H2F>>();   

    public void CreateHistograms(){

	h_scint_time_l1 = new H1F("h_scint_time_l1","h_scint_time_l1",100, 0.0, 800.0);
	h_scint_time_l2 = new H1F("h_scint_time_l2","h_scint_time_l2",100, 0.0, 800.0);	

 	h2_betatime_pos_l1 = new H2F("h2_betatime_pos_l1","h2_betatime_pos_l1",100, 0.0, 5.5, 100, 0.0, 1.1);
	h2_betatime_neg_l1 = new H2F("h2_betatime_neg_l1","h2_betatime_neg_l1",100, 0.0, 5.5, 100, 0.0, 1.1);
 	h2_betatime_el_l1 = new H2F("h2_betatime_el_l1","h2_betatime_el_l1",100, 0.0, 5.5, 100, 0.0, 1.1);
 	h2_betatime_pr_l1 = new H2F("h2_betatime_pr_l1","h2_betatime_pr_l1",100, 0.0, 5.5, 100, 0.0, 1.1);
	h2_betatime_kp_l1 = new H2F("h2_betatime_kp_l1","h2_betatime_kp_l1",100, 0.0, 5.5, 100, 0.0, 1.1);
	h2_betatime_km_l1 = new H2F("h2_betatime_km_l1","h2_betatime_km_l1",100, 0.0, 5.5, 100, 0.0, 1.1);

 	h2_betatime_pos_l2 = new H2F("h2_betatime_pos_l2","h2_betatime_pos_l2",100, 0.0, 5.5, 100, 0.0, 1.1);
	h2_betatime_neg_l2 = new H2F("h2_betatime_neg_l2","h2_betatime_neg_l2",100, 0.0, 5.5, 100, 0.0, 1.1);
	h2_betatime_el_l2 = new H2F("h2_betatime_el_l2","h2_betatime_el_l2",100, 0.0, 5.5, 100, 0.0, 1.1);
	h2_betatime_pr_l2 = new H2F("h2_betatime_pr_l2","h2_betatime_pr_l2",100, 0.0, 5.5, 100, 0.0, 1.1);
	h2_betatime_kp_l2 = new H2F("h2_betatime_kp_l2","h2_betatime_kp_l2",100, 0.0, 5.5, 100, 0.0, 1.1);
	h2_betatime_km_l2 = new H2F("h2_betatime_km_l2","h2_betatime_km_l2",100, 0.0, 5.5, 100, 0.0, 1.1);

 	h2_delbetatime_pos_l1 = new H2F("h2_delbetatime_pos_l1","h2_delbetatime_pos_l1",100, 0.0, 5.5, 100, -1.1, 1.1);
  	h2_delbetatime_neg_l1 = new H2F("h2_delbetatime_neg_l1","h2_delbetatime_neg_l1",100, 0.0, 5.5, 100, -1.1, 1.1);
 	h2_delbetatime_el_l1 = new H2F("h2_delbetatime_el_l1","h2_delbetatime_pos_l1",100, 0.0, 5.5, 100, -1.1, 1.1);
 	h2_delbetatime_pr_l1 = new H2F("h2_delbetatime_pr_l1","h2_delbetatime_pr_l1",100, 0.0, 5.5, 100, -1.1, 1.1);
	h2_delbetatime_kp_l1 = new H2F("h2_delbetatime_kp_l1","h2_delbetatime_kp_l1",100, 0.0, 5.5, 100, -1.1, 1.1);
	h2_delbetatime_km_l1 = new H2F("h2_delbetatime_km_l1","h2_delbetatime_km_l1",100, 0.0, 5.5, 100, -1.1, 1.1);

	h2_delbetatime_pos_l2 = new H2F("h2_delbetatime_pos_l2","h2_delbetatime_pos_l2",100, 0.0, 5.5, 100, -1.1, 1.1);
  	h2_delbetatime_neg_l2 = new H2F("h2_delbetatime_neg_l2","h2_delbetatime_neg_l2",100, 0.0, 5.5, 100, -1.1, 1.1);
 	h2_delbetatime_el_l2 = new H2F("h2_delbetatime_el_l2","h2_delbetatime_el_l2",100, 0.0, 5.5, 100, -1.1, 1.1);
	h2_delbetatime_pr_l2 = new H2F("h2_delbetatime_pr_l2","h2_delbetatime_pr_l2",100, 0.0, 5.5, 100, -1.1, 1.1);
	h2_delbetatime_kp_l2 = new H2F("h2_delbetatime_kp_l2","h2_delbetatime_kp_l2",100, 0.0, 5.5, 100, -1.1, 1.1);
	h2_delbetatime_km_l2 = new H2F("h2_delbetatime_km_l2","h2_delbetatime_km_l2",100, 0.0, 5.5, 100, -1.1, 1.1);

	
	h2_betamntm_el = new H2F("h2_betamntm_el","h2_betamntm_el",100, 0.0, 5.5, 100, 0.0, 1.1);
	h2_betamntm_pr = new H2F("h2_betamntm_pr","h2_betamntm_pr",100, 0.0, 5.5, 100, 0.0, 1.1);
	h2_betamntm_kp = new H2F("h2_betamntm_kp","h2_betamntm_kp",100, 0.0, 5.5, 100, 0.0, 1.1);
	h2_betamntm_km = new H2F("h2_betamntm_km","h2_betamntm_km",100, 0.0, 5.5, 100, 0.0, 1.1);

	h2_betabank_pos = new H2F("h2_betabank_pos","h2_betabank_pos",100, 0.0, 5.5, 100, 0.0, 1.1 );
	h2_betabank_neg = new H2F("h2_betabank_neg","h2_betabank_neg",100, 0.0, 5.5, 100, 0.0, 1.1 );
	
	h_res_beta_pr = new H1F("h_res_beta_pr","h_res_beta_pr",100, 0, 1.5 );
	h_res_mcbeta_pr = new H1F("h_res_mcbeta_pr","h_res_mcbeta_pr",100, 0, 1.5 );

	h_test = new H2F(" h " , " h" , 100, -1.0, 8.0, 100, -1.0, 1.0 );
	
	for(int sect = 1; sect <= 6; sect++){
	    Vector<H1F> v_beta_layers = new Vector<H1F>();
	    for( int layer = 1; layer <= 2; layer++ ){
		String h_title = "h_res_beta_pr_s"+Integer.toString(sect)+"_l"+Integer.toString(layer);
		v_beta_layers.add(new H1F(h_title,h_title,100,-0.15,0.15));
	    }
	    m_beta_sector_layers.put(sect, v_beta_layers);
	}


	for(int sect = 1; sect <= 6; sect++){
	    Vector<H2F> v_beta_layers = new Vector<H2F>();
	    for( int layer = 1; layer <= 2; layer++ ){
		String h_title = "h2_res_beta_pr_s"+Integer.toString(sect)+"_l"+Integer.toString(layer);
		v_beta_layers.add(new H2F(h_title,h_title,100, 0.45, 2.5, 100, -0.09, 0.09));
	    }
	    m_delbeta2_sector_layers.put(sect, v_beta_layers);
	}
    }


    public void FillElectronDetHist( DataEvent event, int rec_i ){
	
	DataBank recbank = event.getBank("REC::Particle"); 
	double p = Calculator.lv_particle(recbank, rec_i, PhysicalConstants.electronID ).p();

	double beta_time_l1 = Calculator.betaTime( event, rec_i, scint_layer1 );
	double beta_time_l2 = Calculator.betaTime( event, rec_i, scint_layer2 );
	
	double beta_mntm = Calculator.betaMntm( event, rec_i, PhysicalConstants.mass_electron);

	h2_betatime_el_l1.fill( p, beta_time_l1);
	h2_betatime_el_l2.fill( p, beta_time_l2);

	double delta_beta_l1 = beta_time_l1 - beta_mntm;
	double delta_beta_l2 = beta_time_l2 - beta_mntm;

	h2_betamntm_el.fill( p, beta_mntm);
	
	h2_delbetatime_el_l1.fill(p, delta_beta_l1);
	h2_delbetatime_el_l2.fill(p, delta_beta_l2);

    }


    public void FillProtonDetHist( DataEvent event, int rec_i ){
	
	DataBank recbank = event.getBank("REC::Particle"); 
	DataBank mcBank = event.getBank("MC::Particle");

	double mc_p =  Calculator.lv_particle(mcBank, PhysicalConstants.pr_index, PhysicalConstants.protonID ).p();
	double mc_beta = mc_p/Math.sqrt( mc_p*mc_p + PhysicalConstants.mass_proton*PhysicalConstants.mass_proton );

	double p = Calculator.lv_particle(recbank, rec_i, PhysicalConstants.protonID ).p();

	double beta_time_l1 = Calculator.betaTime( event, rec_i, scint_layer1 );
	double beta_time_l2 = Calculator.betaTime( event, rec_i, scint_layer2 );
	
	double beta_mntm = Calculator.betaMntm( event, rec_i, PhysicalConstants.mass_proton);

	int sector = Detectors.getSectorDC( event, rec_i );

	if( sector > 0 ){
	    for( Map.Entry<Integer, Vector<H1F> > entry : m_beta_sector_layers.entrySet() ){
 		if( sector == entry.getKey() ){
		    //System.out.println(" >> sector "  + sector );
		    if( beta_time_l1 > 0.2 ){ 
 			//double beta_time = (beta_time_l1 + beta_time_l2) / 2.0;
 			//System.out.println(" >> beta1 & mc_b " + beta_time_l1 + " " + mc_beta);
			double res_beta1 = beta_time_l1 - mc_beta;
			entry.getValue().get(0).fill(res_beta1); 
		    }
		    if( beta_time_l2 > 0.2 ){ 
			//System.out.println(" >> beta2 & mc_b " + beta_time_l2 + " " + mc_beta);
			double res_beta2 = beta_time_l2 - mc_beta;       
			entry.getValue().get(1).fill(res_beta2);
			//h_temp2.fill(res_beta); 					       
		    }
		    if( beta_time_l2 > 0 && beta_time_l1 > 0 ){
			double beta = (beta_time_l2 + beta_time_l1 )/2.0;
			h_res_beta_pr.fill(beta);
			h_res_mcbeta_pr.fill(mc_beta);
		    }
		}
	    }
	}

	if( sector > 0 ){

	    for( Map.Entry<Integer, Vector<H2F> > entry2 : m_delbeta2_sector_layers.entrySet() ){
 		if( sector == entry2.getKey() ){
		    //System.out.println(" >> sector "  + sector );
		    if( beta_time_l1 > 0 ){ 
 			//double beta_time = (beta_time_l1 + beta_time_l2) / 2.0;
 			//System.out.println(" >> beta1 & mc_b " + beta_time_l1 + " " + mc_beta);
			double res_beta1 = beta_time_l1 - mc_beta;
			//System.out.println(" >> " + res_beta1 + " " + mc_p );
			entry2.getValue().get(0).fill(mc_p, res_beta1); 
			//System.out.println(" >> " +entry2.getValue().get(0).getEntries() );

		    }
		    if( beta_time_l2 > 0 ){ 
			//System.out.println(" >> beta2 & mc_b " + beta_time_l2 + " " + mc_beta);
			double res_beta2 = beta_time_l2 - mc_beta;       
			//System.out.println(" >> " + res_beta2 + " " + mc_p );
			entry2.getValue().get(1).fill(mc_p, res_beta2);
			//System.out.println(" >> " +entry2.getValue().get(1).getEntries() );
			//h_temp2.fill(res_beta); 					       
		    }
		    if( beta_time_l2 > 0 && beta_time_l1 > 0 ){
			double beta = (beta_time_l2 + beta_time_l1 )/2.0;
			h_res_beta_pr.fill(beta);
			h_res_mcbeta_pr.fill(mc_beta);
		    }
		}
	    }
	}

	    
	h2_betatime_pr_l1.fill( p, beta_time_l1);
	h2_betatime_pr_l2.fill( p, beta_time_l2);

	double delta_beta_l1 = beta_time_l1 - beta_mntm;
	double delta_beta_l2 = beta_time_l2 - beta_mntm;

	h2_betamntm_pr.fill( p, beta_mntm);
	
	h2_delbetatime_pr_l1.fill(p, delta_beta_l1);
	h2_delbetatime_pr_l2.fill(p, delta_beta_l2);

    }

    public void FillKaonPlusDetHist( DataEvent event, int rec_i ){
	
	DataBank recbank = event.getBank("REC::Particle");
	DataBank mcBank = event.getBank("MC::Particle");
	
	double p = Calculator.lv_particle(recbank, rec_i, PhysicalConstants.kaonplusID ).p();

	double mc_p = Calculator.lv_particle(mcBank, PhysicalConstants.kp_index, PhysicalConstants.kaonplusID ).p();
	double mc_beta = mc_p/Math.sqrt( mc_p*mc_p + PhysicalConstants.mass_kaon*PhysicalConstants.mass_kaon );

	double beta_time_l1 = Calculator.betaTime( event, rec_i, scint_layer1 );
	double beta_time_l2 = Calculator.betaTime( event, rec_i, scint_layer2 );
	
	double beta_mntm = Calculator.betaMntm( event, rec_i, PhysicalConstants.mass_kaon);

	double delta_beta_l1 = beta_time_l1 - beta_mntm;
	double delta_beta_l2 = beta_time_l2 - beta_mntm;

	h2_betatime_kp_l1.fill( p, beta_time_l1);
	h2_betatime_kp_l2.fill( p, beta_time_l2);

	h2_betamntm_kp.fill( p, beta_mntm);
	
	h2_delbetatime_kp_l1.fill(p, delta_beta_l1);
	h2_delbetatime_kp_l2.fill(p, delta_beta_l2);

	double beta_time = (beta_time_l1 + beta_time_l2) / 2.0;
	double res_beta = beta_time - mc_beta;
	//h_res_beta_kp.fill(res_beta);
	
	
    }

    public void FillKaonMinusDetHist( DataEvent event, int rec_i ){
	
	DataBank recbank = event.getBank("REC::Particle");
	double p = Calculator.lv_particle(recbank, rec_i, PhysicalConstants.kaonminusID ).p();

	double beta_time_l1 = Calculator.betaTime( event, rec_i, scint_layer1 );
	double beta_time_l2 = Calculator.betaTime( event, rec_i, scint_layer2 );
	
	double beta_mntm = Calculator.betaMntm( event, rec_i, PhysicalConstants.mass_kaon);

	double delta_beta_l1 = beta_time_l1 - beta_mntm;
	double delta_beta_l2 = beta_time_l2 - beta_mntm;

	h2_betatime_km_l1.fill( p, beta_time_l1);
	h2_betatime_km_l2.fill( p, beta_time_l2);

	h2_betamntm_km.fill( p, beta_mntm);
	
	h2_delbetatime_km_l1.fill(p, delta_beta_l1);
	h2_delbetatime_km_l2.fill(p, delta_beta_l2);	
	
    }


    public void FillPositivesDetHist( DataEvent event, int rec_i ){
	
	DataBank recbank = event.getBank("REC::Particle"); 
	//0.0 as an argument is okay because it is only returning the momentum here.
	double p = Calculator.lv_particle(recbank, rec_i, 11 ).p();
	float beta_bank = recbank.getFloat("beta",rec_i);

	double beta_time_l1 = Calculator.betaTime( event, rec_i, scint_layer1 );
	double beta_time_l2 = Calculator.betaTime( event, rec_i, scint_layer2 );
	
	//double beta_mntm = Calculator.betaMntm( event, rec_i, PhysicalConstants.mass_electron);

	h2_betatime_pos_l1.fill( p, beta_time_l1);
	h2_betatime_pos_l2.fill( p, beta_time_l2);

	h2_betabank_pos.fill(p, beta_bank);

	//double delta_beta_l1 = beta_time_l1 - beta_mntm;
	//double delta_beta_l2 = beta_time_l2 - beta_mntm;

	//h2_betamntm_el.fill( p, beta_mntm);
	
	//h2_delbetatime_el_l1.fill(p, delta_beta_l1);
	//h2_delbetatime_el_l2.fill(p, delta_beta_l2);

    }

    public void FillNegativesDetHist( DataEvent event, int rec_i ){
	
	DataBank recbank = event.getBank("REC::Particle"); 
	//0.0 as an argument is okay because it is only returning the momentum here.
	double p = Calculator.lv_particle(recbank, rec_i, 11 ).p();
	float beta_bank = recbank.getFloat("beta",rec_i);

	double beta_time_l1 = Calculator.betaTime( event, rec_i, scint_layer1 );
	double beta_time_l2 = Calculator.betaTime( event, rec_i, scint_layer2 );
	
	//double beta_mntm = Calculator.betaMntm( event, rec_i, PhysicalConstants.mass_electron);

	h2_betatime_neg_l1.fill( p, beta_time_l1);
	h2_betatime_neg_l2.fill( p, beta_time_l2);

	h2_betabank_neg.fill(p, beta_bank);

	//double delta_beta_l1 = beta_time_l1 - beta_mntm;
	//double delta_beta_l2 = beta_time_l2 - beta_mntm;

	//h2_betamntm_el.fill( p, beta_mntm);
	
	//h2_delbetatime_el_l1.fill(p, delta_beta_l1);
	//h2_delbetatime_el_l2.fill(p, delta_beta_l2);

    }


    public void FillFinalDetectorHistos( DataEvent event, int rec_i ){



    }


    public void ViewHistograms(){

	c_beta_el.setSize(1200,800);
	c_beta_el.divide(3,2);
	c_beta_el.cd(0);
 	h2_betatime_el_l1.setTitle("#beta electron FTOT Layer 1");
 	h2_betatime_el_l1.setTitleX("p [GeV]");
 	h2_betatime_el_l1.setTitleY("#beta");
	c_beta_el.draw(h2_betatime_el_l1,"colz");
	c_beta_el.cd(1);
 	h2_betatime_el_l2.setTitle("#beta electron FTOT Layer 2");
 	h2_betatime_el_l2.setTitleX("p [GeV]");
 	h2_betatime_el_l2.setTitleY("#beta");
	c_beta_el.draw(h2_betatime_el_l2,"colz");
	c_beta_el.cd(2);
 	h2_betamntm_el.setTitle("#beta electron Momentum");
 	h2_betamntm_el.setTitleX("p [GeV]");
 	h2_betamntm_el.setTitleY("#beta");
	c_beta_el.draw(h2_betamntm_el,"colz");
	c_beta_el.cd(3);
	h2_delbetatime_el_l1.setTitle("#Delta #beta Electron Layer 1");
	h2_delbetatime_el_l1.setTitleX("p [GeV]");
	h2_delbetatime_el_l1.setTitleY("#Delta #beta");
	c_beta_el.draw(h2_delbetatime_el_l1,"colz");
	c_beta_el.getPad(3).getAxisZ().setLog(true);
	c_beta_el.cd(4);
	h2_delbetatime_el_l2.setTitle("#Delta #beta Electron Layer 2");
	h2_delbetatime_el_l2.setTitleX("p [GeV]");
	h2_delbetatime_el_l2.setTitleY("#Delta #beta");
	c_beta_el.draw(h2_delbetatime_el_l2,"colz");
	c_beta_el.getPad(4).getAxisZ().setLog(true);

	c_beta_pr.setSize(1200,800);
	c_beta_pr.divide(3,2);
	c_beta_pr.cd(0);
 	h2_betatime_pr_l1.setTitle("#beta proton FTOT Layer 1");
 	h2_betatime_pr_l1.setTitleX("p [GeV]");
 	h2_betatime_pr_l1.setTitleY("#beta");
	c_beta_pr.draw(h2_betatime_pr_l1,"colz");
	c_beta_pr.cd(1);
 	h2_betatime_pr_l2.setTitle("#beta proton FTOT Layer 2");
 	h2_betatime_pr_l2.setTitleX("p [GeV]");
 	h2_betatime_pr_l2.setTitleY("#beta");
	c_beta_pr.draw(h2_betatime_pr_l2,"colz");
	c_beta_pr.cd(2);
 	h2_betamntm_pr.setTitle("#beta proton Momentum");
 	h2_betamntm_pr.setTitleX("p [GeV]");
 	h2_betamntm_pr.setTitleY("#beta");
	c_beta_pr.draw(h2_betamntm_pr,"colz");
	c_beta_pr.cd(3);
	h2_delbetatime_pr_l1.setTitle("#Delta #beta Proton Layer 1");
	h2_delbetatime_pr_l1.setTitleX("p [GeV]");
	h2_delbetatime_pr_l1.setTitleY("#Delta #beta");
	c_beta_pr.draw(h2_delbetatime_pr_l1,"colz");
	c_beta_pr.getPad(3).getAxisZ().setLog(true);
	c_beta_pr.cd(4);
	h2_delbetatime_pr_l2.setTitle("#Delta #beta Proton Layer 2");
	h2_delbetatime_pr_l2.setTitleX("p [GeV]");
	h2_delbetatime_pr_l2.setTitleY("#Delta #beta");
	c_beta_pr.draw(h2_delbetatime_pr_l2,"colz");
	c_beta_pr.getPad(4).getAxisZ().setLog(true);
	c_beta_pr.cd(5);
	h_res_beta_pr.setTitle("#beta Resolution");
	h_res_beta_pr.setTitleX("#beta");
	c_beta_pr.draw(h_res_beta_pr);


	c_beta_kp.setSize(1200,800);
	c_beta_kp.divide(3,2);
	c_beta_kp.cd(0);
 	h2_betatime_kp_l1.setTitle("#beta Kaon^+ FTOT Layer 1");
 	h2_betatime_kp_l1.setTitleX("p [GeV]");
 	h2_betatime_kp_l1.setTitleY("#beta");
	c_beta_kp.draw(h2_betatime_kp_l1,"colz");
	c_beta_kp.cd(1);
 	h2_betatime_kp_l2.setTitle("#beta Kaon^+ FTOT Layer 2");
 	h2_betatime_kp_l2.setTitleX("p [GeV]");
 	h2_betatime_kp_l2.setTitleY("#beta");
	c_beta_kp.draw(h2_betatime_kp_l2,"colz");
	c_beta_kp.cd(2);
 	h2_betamntm_kp.setTitle("#beta Kaon^+ Momentum");
 	h2_betamntm_kp.setTitleX("p [GeV]");
 	h2_betamntm_kp.setTitleY("#beta");
	c_beta_kp.draw(h2_betamntm_kp,"colz");
	c_beta_kp.cd(3);
	h2_delbetatime_kp_l1.setTitle("#Delta #beta Kaon^+ Layer 1");
	h2_delbetatime_kp_l1.setTitleX("p [GeV]");
	h2_delbetatime_kp_l1.setTitleY("#Delta #beta");
	c_beta_kp.draw(h2_delbetatime_kp_l1,"colz");
	c_beta_kp.getPad(3).getAxisZ().setLog(true);
	c_beta_kp.cd(4);
	h2_delbetatime_kp_l2.setTitle("#Delta #beta Kaon^+ Layer 2");
	h2_delbetatime_kp_l2.setTitleX("p [GeV]");
	h2_delbetatime_kp_l2.setTitleY("#Delta #beta");
	c_beta_kp.draw(h2_delbetatime_kp_l2,"colz");
	c_beta_kp.getPad(4).getAxisZ().setLog(true);

	c_beta_km.setSize(1200,800);
	c_beta_km.divide(3,2);
	c_beta_km.cd(0);
 	h2_betatime_km_l1.setTitle("#beta Kaon^- FTOT Layer 1");
 	h2_betatime_km_l1.setTitleX("p [GeV]");
 	h2_betatime_km_l1.setTitleY("#beta");
	c_beta_km.draw(h2_betatime_km_l1,"colz");
	c_beta_km.cd(1);
 	h2_betatime_km_l2.setTitle("#beta Kaon^- FTOT Layer 2");
 	h2_betatime_km_l2.setTitleX("p [GeV]");
 	h2_betatime_km_l2.setTitleY("#beta");
	c_beta_km.draw(h2_betatime_km_l2,"colz");
	c_beta_km.cd(2);
 	h2_betamntm_km.setTitle("#beta Kaon^- Momentum");
 	h2_betamntm_km.setTitleX("p [GeV]");
 	h2_betamntm_km.setTitleY("#beta");
	c_beta_km.draw(h2_betamntm_km,"colz");
	c_beta_km.cd(3);
	h2_delbetatime_km_l1.setTitle("#Delta #beta Kaon^- Layer 1");
	h2_delbetatime_km_l1.setTitleX("p [GeV]");
	h2_delbetatime_km_l1.setTitleY("#Delta #beta");
	c_beta_km.draw(h2_delbetatime_km_l1,"colz");
	c_beta_km.getPad(3).getAxisZ().setLog(true);
	c_beta_km.cd(4);
	h2_delbetatime_km_l2.setTitle("#Delta #beta Kaon^- Layer 2");
	h2_delbetatime_km_l2.setTitleX("p [GeV]");
	h2_delbetatime_km_l2.setTitleY("#Delta #beta");
	c_beta_km.draw(h2_delbetatime_km_l2,"colz");
	c_beta_km.getPad(4).getAxisZ().setLog(true);

	c_beta_pos.setSize(800,800);
	c_beta_pos.divide(2,2);
	c_beta_pos.cd(0);
 	h2_betatime_pos_l1.setTitle("#beta all positives FTOT Layer 1");
 	h2_betatime_pos_l1.setTitleX("p [GeV]");
 	h2_betatime_pos_l1.setTitleY("#beta");
	c_beta_pos.draw(h2_betatime_pos_l1,"colz");
	c_beta_pos.cd(1);
 	h2_betatime_pos_l2.setTitle("#beta all positives FTOT Layer 2");
 	h2_betatime_pos_l2.setTitleX("p [GeV]");
 	h2_betatime_pos_l2.setTitleY("#beta");
	c_beta_pos.draw(h2_betatime_pos_l2,"colz");
	c_beta_pos.cd(2);
	h2_betabank_pos.setTitle("#beta all positives REC::Particle");
	h2_betabank_pos.setTitleX("p [GeV]");
	h2_betabank_pos.setTitleY("#beta bank");
	c_beta_pos.draw(h2_betabank_pos,"colz");
	
	c_beta_neg.setSize(800,800);
	c_beta_neg.divide(2,2);
	c_beta_neg.cd(0);
 	h2_betatime_neg_l1.setTitle("#beta all negatives FTOT Layer 1");
 	h2_betatime_neg_l1.setTitleX("p [GeV]");
 	h2_betatime_neg_l1.setTitleY("#beta");
	c_beta_neg.draw(h2_betatime_neg_l1,"colz");
	c_beta_neg.cd(1);
 	h2_betatime_neg_l2.setTitle("#beta all negatives FTOT Layer 2");
 	h2_betatime_neg_l2.setTitleX("p [GeV]");
 	h2_betatime_neg_l2.setTitleY("#beta");
	c_beta_neg.draw(h2_betatime_neg_l2,"colz");
	c_beta_neg.cd(2);
	h2_betabank_neg.setTitle("#beta all negatives REC::Particle");
	h2_betabank_neg.setTitleX("p [GeV]");
	h2_betabank_neg.setTitleY("#beta bank");
	c_beta_neg.draw(h2_betabank_neg,"colz");

	c_beta_res_pr.setSize(800,800);
	c_beta_res_pr.divide(2,6);
	int counter = 0;
	for( Map.Entry<Integer, Vector<H1F> > entry : m_beta_sector_layers.entrySet() ){
	    //System.out.println(" >>>>>>>>>>>>> MAKING HISTOGRAMS FOR SECTOR  " + entry.getKey() );
	    Vector<H1F> v_temp = entry.getValue();	    
	    //System.out.println(" > > > + " + v_temp.size() );
	    for( H1F h_layer : v_temp ){		
		c_beta_res_pr.cd(counter);
		//System.out.println(" >> h_name " + h_layer.getName() ); 
		h_layer.setTitle(h_layer.getName());
		F1D f_res_beta = Calculator.fitHistogram(h_layer);
		f_res_beta.setLineColor(2);
		c_beta_res_pr.draw(h_layer);
		c_beta_res_pr.draw(f_res_beta,"same");
		//System.out.println(" >> " + counter );
		counter++;
	    }
	}
	

	
	int counter2 = 0;
	c_beta_resfits_pr.setSize(800,800);
	c_beta_resfits_pr.divide(2,6);
	c2_beta_res_pr.setSize(800,800);
	c2_beta_res_pr.divide(2,6);

       	for( Map.Entry<Integer, Vector<H2F> > entry2 : m_delbeta2_sector_layers.entrySet() ){
	    System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> FITTING  HISTOGRAMS FOR SECTOR  " + entry2.getKey() );
	    Vector<H2F> v2_temp = entry2.getValue();	    
	    System.out.println(" >>> + " + v2_temp.size() );
	    //System.out.println(" >>>>>> COUNTER2 " + counter2);	    
	    //if( counter2 < 12 ){
	    
	    for( H2F h2_layer : v2_temp ){		
		c_beta_resfits_pr.cd(counter2);
		System.out.println(" >> h_name " + h2_layer.getName() ); 
		ParallelSliceFitter fit_temp = new ParallelSliceFitter(h2_layer);
		fit_temp.fitSlicesX(5);
		//GraphErrors temp_sigmas = fit_temp.getSigmaSlices();
		GraphErrors ge_sigmas = fit_temp.getSigmaSlices();
		//System.out.println(" >> getDataSize " + temp_sigmas.getDataSize(1) );

		/*Vector<Double> temp_x = new Vector<Double>();
		Vector<Double> temp_y = new Vector<Double>();
		*/
		
		/*DataVector temp_x = temp_sigmas.getVectorX();
		DataVector temp_y = temp_sigmas.getVectorY();
		System.out.println(" >> tempx " + temp_x.size());
		System.out.println(" >> tempy " + temp_y.size());

		double[] x_sig = new double[temp_x.size()];
		double[] y_sig = new double[temp_y.size()];
	       
		for( int bin = 0; bin < temp_sigmas.getDataSize(0); bin++ ){
		    double x_val = temp_sigmas.getDataX(bin);
		    double y_val = temp_sigmas.getDataY(bin);
		    System.out.println(" >> " + x_val + " " + y_val );
		    x_sig[bin] = x_val;
		    y_sig[bin] = y_val;
		}

		*/
		//GraphErrors ge_sigmas = new GraphErrors("Sigmas vs P", x_sig, y_sig );
		ge_sigmas.setTitle("#sigma vs GEN p");
		ge_sigmas.setTitleX("GEN p [GeV]");
		ge_sigmas.setTitleY("#sigma");
		ge_sigmas.setMarkerSize(3);
		ge_sigmas.setMarkerStyle(0);
		ge_sigmas.setMarkerColor(2);	       

		/*
		temp_sigmas.setTitle("#sigma vs GEN p");
		temp_sigmas.setTitleX("GEN p [GeV]");
		temp_sigmas.setTitleY("#sigma");
		temp_sigmas.setMarkerSize(3);
		temp_sigmas.setMarkerStyle(0);
		temp_sigmas.setMarkerColor(2);	       
		//c_beta_resfits_pr.draw(h2_layer,"colz");
		*/

		F1D beta_fit = new F1D("beta_fit","[a]*x*x*x + [b]*x*x + [c]*x + [d]", 0.49, 2.5);
		DataFitter.fit(beta_fit, ge_sigmas, "EQ");
	
		double fit_a = beta_fit.getParameter(0);
		double fit_b = beta_fit.getParameter(1);
		double fit_c = beta_fit.getParameter(2);
		double fit_d = beta_fit.getParameter(3);
		
		System.out.println(" >> BETA FIT PARAMETERS " + fit_a + " " + fit_b + " " + fit_c + " " + fit_d);
		
		c_beta_resfits_pr.draw(ge_sigmas);
		c_beta_resfits_pr.draw(beta_fit,"same");

		//x_sig = null;		
		//y_sig = null;
		//}

 		c2_beta_res_pr.cd(counter2);
		h2_layer.setTitle(h2_layer.getName());
		c2_beta_res_pr.draw(h2_layer,"colz");

		counter2++;

	    }
	}
	System.out.println("done here " );

	/*	c_beta_res_pr2.setSize(800,800);       
	h_res_beta_pr.setLineColor(2);
	c_beta_res_pr2.draw(h_res_beta_pr);
	c_beta_res_pr2.draw(h_res_mcbeta_pr,"same");
	*/
	
    }

    public void SaveHistograms(){

	c_beta_pos.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_beta_pos.png");
	c_beta_neg.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_beta_neg.png");

	c_beta_el.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_beta_el.png");
	c_beta_pr.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_beta_pr.png");
	c_beta_kp.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_beta_kp.png");
	c_beta_km.save("/u/home/bclary/CLAS12/phi_analysis/v1/pics/h_beta_km.png");

	System.out.println(" herer ");

	c_beta_res_pr.save("/u/home/bclary/CLAS12/phi_analysis/v2/v1/pics/h_beta_res_pr.png");
	System.out.println(" herer 2" );

	c_beta_resfits_pr.save("/u/home/bclary/CLAS12/phi_analysis/v2/v1/pics/h_beta_resfits_pr.png");
	System.out.println(" herer 3" );

	c_beta_res_pr2.save("/u/home/bclary/CLAS12/phi_analysis/v2/v1/pics/h_beta_res_pr2.png");

	c2_beta_res_pr.save("/u/home/bclary/CLAS12/phi_analysis/v2/v1/pics/h2_beta_res_pr.png");

    }

}
