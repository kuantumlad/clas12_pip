package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;

import org.jlab.groot.group.*;
import org.jlab.groot.ui.TBrowser;
import org.jlab.groot.tree.*;
import org.jlab.groot.data.TDirectory;

import org.jlab.clas.analysis.clary.Calculator;

import org.jlab.analysis.math.ClasMath;
import org.jlab.clas.physics.Vector3;
import org.jlab.clas.physics.LorentzVector;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.fitter.*;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.math.Axis;
import org.jlab.groot.graphics.GraphicsAxis;
import org.jlab.groot.graphics.EmbeddedCanvas;


import org.jlab.groot.math.Func1D;
import org.jlab.groot.math.F1D;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;


public class BResProton {

    int run_number = -1;
    String s_run = "";
    public BResProton( int temp_run ){
	run_number = temp_run;
	s_run = Integer.toString(temp_run);
    }

    TDirectory dir = new TDirectory();

    //BETA STUDY PLOTS FOR LIKELIHOOD ESTIMATOR
    H2F h2_rc_pr_betap;
    H2F h2_rc_apr_betap;
    H1F h_rc_pr_beta_res;


    public void createResProtonHistograms(){

 	////////////////////////////////////////////////////////////////////
	//BETA STUDY
	h_rc_pr_beta_res = new H1F("h_rc_pr_beta_res","h_rc_pr_beta_res", 200, -0.05, 0.05 );
	h2_rc_pr_betap = new H2F("h2_rc_pr_betap","h2_rc_pr_betap", 100, 0.4, 5.5, 100, -0.04, 0.04);
	h2_rc_apr_betap = new H2F("h2_rc_apr_betap","h2_rc_apr_betap", 100, 0.0, 5.5, 100, 0.0, 1.1);
 
    }

    public void saveResProton(){


    }


    public void sliceNFitResProton() {

	EmbeddedCanvas c_slices = new EmbeddedCanvas();
	EmbeddedCanvas c_betap_fit = new EmbeddedCanvas();

	c_slices.setSize(900,900);
	c_slices.divide(4,5);
	c_betap_fit.setSize(900,900);
	

	H2F h2_temp_rebinX = h2_rc_pr_betap.rebinY(5);
	H2F h2_temp_rebinXY = h2_rc_pr_betap.rebinX(5);
	System.out.println(" >> REBINNED X TO " + h2_temp_rebinX.getXAxis().getNBins() );
	System.out.println(" >> REBINNED XY TO " + h2_temp_rebinXY.getXAxis().getNBins() );

	ArrayList< H1F > h_temp_rebinXY_sliceX = new ArrayList<H1F>();
	h_temp_rebinXY_sliceX = h2_temp_rebinXY.getSlicesX();
	
	Vector<Double> bin_center_temp = new Vector<Double>();
	for( int bin = 1; bin < h2_temp_rebinXY.getXAxis().getNBins(); bin++ ){ /// was bin = 1 to start 
	    double bin_center = h2_temp_rebinXY.getXAxis().getBinCenter(bin);
	    System.out.println(" BIN CENTER " + bin_center ); 
	    bin_center_temp.add(bin_center);	    	    
	}
	
	
	GraphErrors g_mean = new GraphErrors();
	GraphErrors g_sigmas = new GraphErrors();

	g_mean.setTitle(" MEAN BETA FOR PROTON ");
	g_sigmas.setTitle(" MEAN BETA FOR PROTON ");

	for( int n_htemp = 1; n_htemp < h_temp_rebinXY_sliceX.size() - 1; n_htemp++ ){
	    c_slices.cd(n_htemp);
	    H1F h_temp = h_temp_rebinXY_sliceX.get(n_htemp);
	    h_temp.setTitle("SLICE " + n_htemp);
 	    h_temp.setTitleX("#beta");
	    h_temp.setOptStat(1110);
	    c_slices.draw(h_temp);

	    F1D fit_temp = Calculator.fitHistogram(h_temp,0.3);
	    double fit_mean = fit_temp.getParameter(1);
	    double fit_sigma = fit_temp.getParameter(2);
	    double fit_mean_err = fit_temp.parameter(1).error();
	    double fit_sigma_err = fit_temp.parameter(2).error();
	    double bincenter = bin_center_temp.get(n_htemp);

	    System.out.println(" >> BIN CENTER " + bincenter + " MEAN: " + fit_mean + " SIGMA " + fit_sigma + " MEAN ER " + fit_mean_err + " " + fit_sigma_err );
	    if( fit_mean_err > 1.0 ) { continue ; }
	    g_mean.addPoint( bincenter, fit_mean, fit_mean_err, fit_sigma_err );
	    g_sigmas.addPoint( bincenter, fit_sigma, fit_mean_err, fit_sigma_err );
	    
	}

	F1D f_betap_mean  = new F1D("f_betap_mean", " [a] + [b]*x + [c]*x*x + [d]*x*x*x ", 0.8, 5.5);
	DataFitter.fit(f_betap_mean, g_mean, "REQ");

	f_betap_mean.setLineWidth(2);
	f_betap_mean.setLineStyle(0);
	f_betap_mean.setLineColor(2);
	double betap_mean_a = f_betap_mean.getParameter(0);
	double betap_mean_b = f_betap_mean.getParameter(1);
	double betap_mean_c = f_betap_mean.getParameter(2);
	double betap_mean_d = f_betap_mean.getParameter(3);
	System.out.println(" >> DELTA BETA POLYNOMIAL FIT PARAMETERS " + betap_mean_a + " "+ betap_mean_b + " " + betap_mean_c + " " + betap_mean_d );
	
	g_mean.setMarkerSize(2);
	g_mean.setMarkerStyle(0);
	g_mean.setMarkerColor(2);
	
	c_betap_fit.draw(g_mean,"same");
	c_betap_fit.draw(f_betap_mean,"same");
	
	c_slices.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/h_pr_resbeta_slices_allsector.png"); 
	c_betap_fit.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/g_pr_resbeta_fit_allsector.png"); 


    }


    public void histoResProtonToHipo(){
	
	dir.mkdir("/res_pos_betap/");
	dir.cd("/res_pos_betap/");
	dir.addDataSet(h2_rc_pr_betap);

	dir.addDataSet(h_rc_pr_beta_res);
	EmbeddedCanvas c_pr_res = new EmbeddedCanvas();
	c_pr_res.setSize(900,900);
	System.out.println(" >> FITTING BETA RESOLUTION HISTOGRAM " );
	F1D f_beta_res = Calculator.fitHistogram(h_rc_pr_beta_res,0.6);
	System.out.println(" >> BETA RES MEAN " + f_beta_res.getParameter(1) + ", SIGMA " + f_beta_res.getParameter(2) );
	f_beta_res.setLineWidth(2);
	f_beta_res.setLineStyle(0);
	f_beta_res.setLineColor(2);
	c_pr_res.draw(h_rc_pr_beta_res,"same");
	c_pr_res.draw(f_beta_res,"same");
	c_pr_res.save("/lustre/expphy/work/hallb/clas12/bclary/pics/pid_clary/h_rc_pr_beta_res_allsector.png");

    }

    public void viewResProtonHipoOut(){
	TBrowser browser = new TBrowser(dir);
    }   

}
