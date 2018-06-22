import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
import org.apache.commons.math3.linear.*;

import org.jlab.analysis.plotting.H1FCollection2D;
import org.jlab.analysis.plotting.H1FCollection3D;
import org.jlab.analysis.plotting.TCanvasP;
import org.jlab.analysis.plotting.TCanvasPTabbed;
import org.jlab.groot.graphics.EmbeddedCanvas;

import org.jlab.io.hipo.HipoDataSource;
import org.jlab.jnp.hipo.data.HipoEvent;
import org.jlab.jnp.hipo.data.HipoGroup;
import org.jlab.jnp.hipo.io.HipoReader;
import org.jlab.jnp.hipo.io.HipoWriter;
import org.jlab.jnp.hipo.schema.SchemaFactory;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.math.Func1D;
import org.jlab.groot.math.F1D;
import org.jlab.groot.data.DataVector;
import org.jlab.groot.data.GraphErrors;

import org.jlab.clas.physics.LorentzVector;
import org.jlab.groot.fitter.*;

public class flatAnalysis {

    public static double resolution( double rec_val, double gen_val ){
	return rec_val - gen_val;
    }

    public static void main(String[] args ){

	int max_files = 10;
	
	String gen_type = args[0];
	System.out.println(">> GENERATOR TYPE " + gen_type);

	String dir_path = "/volatile/halla/sbs/bclary/clas12Analysis/RECclas12/rec_markov_"+gen_type+"_accp/";
	System.out.println(">> FILES FROM " + dir_path);

	H1F h_el_p = new H1F("h_el_p","h_el_p", 100, 0.0, 10.5);
	H2F h_el_ptheta = new H2F("h_el_ptheta","h_el_ptheta", 100, 0.0, 10.5, 100, 0.0, 60.0);
	H2F h_el_pphi = new H2F("h_el_pphi","h_el_pphi", 100, 0.0, 10.5, 200, -180.0, 180.0);

	H1F h_el_mm = new H1F("h_el_mm","h_el_mm", 100, 0.0, 10.5);
	H2F h_el_xq2 = new H2F("h_el_xq2","h_el_xq2", 100, 0.0, 1.5, 100, 0.0, 15.0);
	H2F h_el_q2w = new H2F("h_el_q2w","h_el_q2w", 200, 0.0, 5.0, 200, 0.0, 10.5);

	H1F h_gel_p = new H1F("h_gel_p","h_gel_p", 100, 0.0, 10.5);
	H2F h_gel_ptheta = new H2F("h_gel_ptheta","h_gel_ptheta", 100, 0.0, 10.5, 100, 0.0, 60.0);
	H2F h_gel_pphi = new H2F("h_gel_pphi","h_gel_pphi", 100, 0.0, 10.5, 200, -180.0, 180.0);

	H1F h_gel_mm = new H1F("h_gel_mm","h_gel_mm", 100, 0.0, 14.5);
	H2F h_gel_xq2 = new H2F("h_gel_xq2","h_gel_xq2", 100, 0.0, 1.2, 100, 0.0, 12.0);
	H2F h_gel_q2w = new H2F("h_gel_q2w","h_gel_q2w", 100, 0.0, 15, 200, 0.0, 15.0);

	H1F h_el_q2 = new H1F("h_el_q2","h_el_q2", 100, 0.0, 10.5);
	H1F h_el_w = new H1F("h_el_w","h_el_w", 100, 0.0, 5.5);
	H1F h_gel_q2 = new H1F("h_gel_q2","h_gel_q2", 100, 0.0, 10.5);
	H1F h_gel_w = new H1F("h_gel_w","h_gel_w", 100, 0.0, 5.5);

	H1F h_res_q2 = new H1F("h_res_q2","h_res_q2",100, -0.50, 0.50 );
	H1F h_res_w = new H1F("h_res_w","h_res_w",100, -0.50, 0.50 );

	H2F h2_res_q2 = new H2F("h2_res_q2","h2_res_q2",100, 0.0, 10.75, 100, -0.50, 0.50 );
	H2F h2_res_w = new H2F("h2_res_w","h2_res_w",100, 0.0, 15.0, 100, -0.50, 0.50 );
	
	//RealMatrix m_accep = new RealMatrix();
	int n_bins = 100;
	
	double min_q2 = 1.0;
	double max_q2 = 9.99;
 	H1F h_accep_gen_q2 = new H1F("h_accep_gen_q2","h_accep_gen_q2", n_bins, min_q2, max_q2 );
 	H1F h_accep_rec_q2 = new H1F("h_accep_rec_q2","h_accep_rec_q2", n_bins, min_q2, max_q2 );

	H1F h_corr_q2 = new H1F("h_corr_q2","h_corr_q2", n_bins, min_q2, max_q2);
	H1F h_final_q2 = new H1F("h_final_q2","h_final_q2", n_bins, min_q2, max_q2);
	H1F h_final_gen_q2 = new H1F("h_final_gen_q2","h_final_gen_q2", n_bins, min_q2, max_q2);
	H1F h_trad_final_q2 = new H1F("h_trad_final_q2","h_trad_final_q2", n_bins, min_q2, max_q2);
	
	RealMatrix m_acc_q2 = new Array2DRowRealMatrix( new double[n_bins][n_bins]);
			
	File[] files = new File(dir_path).listFiles();
	System.out.println(" >> PROCESSING " + files.length + " FILES ");
	int f_counter = 0;

	for( File file : files ){
	    if( !(file.isDirectory()) ){
		f_counter+=1;
		System.out.println(" >> PROCESSING FILE : " + file.getName() );
		//if( f_counter > max_files ){ break; }
		//if( f_counter < max_files ){
		String fname = dir_path + file.getName();
		HipoDataSource reader = new HipoDataSource();
		reader.open(fname);
		    
		double mass_electron = 0.00051;
		double beam_energy = 10.5;
		double mass_proton = 0.938;
		LorentzVector lv_initial_electron = new LorentzVector( 0, 0, 0, 10.5);
		    
		System.out.println(" >> LOOPING OVER EVENTS " );
		    
		boolean mc_present = false;
		double mc_q2 = 0.0; double mc_w = 0.0;
		double rec_q2 = 0.0; double rec_w = 0.0;
		    
		while(reader.hasEvent()){
			
		    DataEvent event = reader.getNextEvent();
		    //event.show();
			
		    //System.out.println(" >> IN READER " );
		    if( event.hasBank("MC::Particle") ){
			    
			DataBank genbank = event.getBank("MC::Particle");
			float px = genbank.getFloat("px",0);
			float py = genbank.getFloat("py",0);
			float pz = genbank.getFloat("pz",0);
			double p = Math.sqrt( px*px + py*py + pz*pz );
			double energy = Math.sqrt( p*p + mass_electron*mass_electron );
			    
			LorentzVector lv_el = new LorentzVector(px, py, pz, energy);
			LorentzVector lv_q = new LorentzVector(0.0,0.0,0.0,0.0);
			lv_q.add(lv_initial_electron);
			lv_q.sub(lv_el);
			    
			double q2 = 4.0*beam_energy*lv_el.e()*Math.sin( lv_el.theta()/2.0 )*Math.sin(lv_el.theta()/2.0);
			    
			double xB = ( q2 ) / (2.0*mass_proton * (beam_energy - lv_el.e()));
			double w = Math.sqrt( -q2 +  + mass_proton*mass_proton + 2.0*mass_proton*( beam_energy - lv_el.e() ) );

			h_gel_p.fill(p);
			h_gel_ptheta.fill( p, Math.toDegrees(lv_el.theta()) );
			h_gel_pphi.fill( p, Math.toDegrees(lv_el.phi()) );
		
			h_gel_xq2.fill( xB, q2 );
			h_gel_q2w.fill( w, q2 );//q2, w );
		
			///System.out.println(" >> gen q2, x " + q2 +  " " + xB );
			h_gel_q2.fill( q2 );
			h_gel_w.fill( w );

			mc_present = true;
			mc_q2 = q2;
			mc_w = w;


		    }
	    
		    if( event.hasBank("REC::Particle") ){
			DataBank recbank = event.getBank("REC::Particle");
			//pbank.show();
		 	//System.out.println(" Number of entries to process is " + pbank.rows() );
		
			//for( int i = 0; i < recbank.rows(); i++){
			//int i = 0;
			int  pid = recbank.getInt("pid",0);		    
			    if( pid == 11 ){
				float px = recbank.getFloat("px",0);
				float py = recbank.getFloat("py",0);
				float pz = recbank.getFloat("pz",0);
				int charge = recbank.getInt("charge",0);
				double p = Math.sqrt( px*px + py*py + pz*pz );
				double energy = Math.sqrt( p*p + mass_electron*mass_electron );

				LorentzVector lv_el = new LorentzVector(px, py, pz, energy);
				LorentzVector lv_q = new LorentzVector(0.0,0.0,0.0,0.0);
				lv_q.add(lv_initial_electron);
				lv_q.sub(lv_el);
			
				double q2 = 4.0*beam_energy*lv_el.e()*Math.sin( lv_el.theta()/2.0 )*Math.sin(lv_el.theta()/2.0);
				double xB = ( q2 ) / (2.0*mass_proton * (beam_energy - lv_el.e()));
 				double w = Math.sqrt( -q2 +  + mass_proton*mass_proton + 2.0*mass_proton*( beam_energy - lv_el.e() ) );

				h_el_p.fill(p);
				h_el_ptheta.fill( p, Math.toDegrees(lv_el.theta()) );
				h_el_pphi.fill( p, Math.toDegrees(lv_el.phi()) );
			
				h_el_xq2.fill( xB, q2 );
				h_el_q2w.fill( w ,q2 );

				h_el_q2.fill( q2 );
				h_el_w.fill( w );

				if( mc_present ){
				    double res_q2 = resolution( q2, mc_q2 );
				    double res_w = resolution( w, mc_w );
				    
				    h_res_q2.fill(res_q2);
				    h_res_w.fill(res_w);
				    
				    h2_res_q2.fill( mc_q2, res_q2 );
				    h2_res_w.fill( mc_w, res_w );
 
				    ///System.out.println(" >> CHECKING REC BIN FOR " + q2 + " " + h_accep_rec_q2.getXaxis().getBin(q2) );
				    ///System.out.println(" >> CHECKING GEN BIN FOR " + mc_q2 + " " + h_accep_gen_q2.getXaxis().getBin(mc_q2) );
				    
				    int rec_bin_q2 = h_accep_rec_q2.getXaxis().getBin(q2); // bin i  - row
				    int gen_bin_q2 = h_accep_gen_q2.getXaxis().getBin(mc_q2); // bin j - column
				    //if( rec_bin_q2 >= n_bins || gen_bin_q2 >= n_bins || rec_bin_q2 < 0 || gen_bin_q2 < 0 ){ 
				    //	break;
				    //}
				     if( (rec_bin_q2 < n_bins && gen_bin_q2 < n_bins ) && (rec_bin_q2 >= 0 && gen_bin_q2 >= 0)  ){ //OTHERWISE WE GET OVERFLOW BINS 
					double bin_val = m_acc_q2.getEntry( rec_bin_q2, gen_bin_q2 );
					bin_val+=1;
				   
					m_acc_q2.setEntry(rec_bin_q2, gen_bin_q2, bin_val );
					h_accep_rec_q2.fill( q2 );
					//System.out.println(" >> GETTING REC BIN FOR " + q2 + " " + rec_bin_q2 );
					//System.out.println(" >> GETTING GEN BIN FOR " + mc_q2 + " " + gen_bin_q2 );
					if( f_counter > max_files ){ h_final_q2.fill(q2); }
				     }
				    				    
				}
			    }
			    
		       //}
		
		    }
		    h_accep_gen_q2.fill( mc_q2 );
		    if( f_counter >= max_files ){ h_final_gen_q2.fill( mc_q2 ); }
		    
		}
		reader.close();
	    }

	}


	//////////////////////////////////////////
	//REC AND GEN BIN VALUES
	for( int i = 0; i < h_accep_rec_q2.getXaxis().getNBins(); i++ ){
	    double rec_bin_val = h_accep_rec_q2.getBinContent(i);
	    System.out.println(" >> REC Q2 bin " + i +  ", value " + rec_bin_val);
	}

	Vector<Double> v_norm = new Vector<Double>();
	for( int i = 0; i < h_accep_gen_q2.getXaxis().getNBins(); i++ ){
	    double gen_bin_val = h_accep_gen_q2.getBinContent(i);
	    System.out.println(" >> GEN  Q2 bin " + i +  ", value " + gen_bin_val);
	    v_norm.add(i, gen_bin_val);
	}
	    
	System.out.println(" >> m_acc_q2 MATRIX " + m_acc_q2 );


	///////////////////////////////////////////
	//TAKE CARE OF ACCEPTANCE CORRECTIONS HERE
	/*Vector<Double> v_norm = new Vector<Double>();
	int n_col = 3;
	int n_row = 3;
	for( int i = 0; i < ; i++ ){
	    double[] temp_col = m_acc_q2.getColumn(i);
	    double gen_normalize = 0;
	    for( double temp_element : temp_col  ){
		System.out.println(" >> temp col " + temp_element );
		gen_normalize+=temp_element;
	    }
	    System.out.println(" >> COL " + i + " " + gen_normalize );
	    v_norm.add(i, gen_normalize);
	    }*/
	

	/////////////////////////////////////////
	//NORMALIZE MATRIX 
	for( int col = 0; col < n_bins; col++ ){
	    double norm = v_norm.get(col);
	    for( int row = 0; row < n_bins; row++ ){
		double temp_entry = m_acc_q2.getEntry(row, col);
		System.out.println( " >> row + col " + row + " "  + col + " " + temp_entry);
		m_acc_q2.setEntry( row, col, temp_entry/norm );
	    }
	}

	System.out.println(" >> A matrix: " + m_acc_q2 );
	/////////////////////////////////////////////////
	//WRITE MATRIX ELEMENTS TO TEXT FILE TO USE LATER
	String matrix_results =  "m_result_" + gen_type + "_b" + n_bins + ".txt";
	System.out.println(" >> PRINTING MATRIX FOR " + gen_type + " TO " + matrix_results);

	BufferedWriter matrix_writer = null;
	try{
	    matrix_writer = new BufferedWriter( new FileWriter(matrix_results) );
	    

	    for( int row = 0; row < n_bins; row++ ){
		for( int col = 0; col < n_bins; col++ ){
		    double temp_entry = m_acc_q2.getEntry(row,col);
		    System.out.println(" >> COL " + col + " ROW " + row + " " + temp_entry ); 
		    DecimalFormat numberFormat = new DecimalFormat("0.00000");
		    System.out.println(" >> WRITING TO TXT: " + numberFormat.format(temp_entry) );
		    matrix_writer.write(numberFormat.format(temp_entry) + " ");
		}
		matrix_writer.write(" \n");
	    }
	    
	}
	catch( IOException e){
	    System.out.println(" >> ERROR WRITING TO FILE " );
	}
	   try{
	       if( matrix_writer != null ){
		   matrix_writer.close();
	       }
	   }
	   catch( IOException e ){
	       System.out.println(" >> ERROR CLOSING FILE " );
	   }
	

	LUDecomposition lud_imp = new LUDecomposition(m_acc_q2);
	DecompositionSolver solver = lud_imp.getSolver();
	///////////////////////////////////////
	//LOOP OVER REC Q2 VALUES AND CHANGE
  	double[] rhs = new double[v_norm.size()];
	for( int bin = 0; bin < n_bins; bin++ ){
	    //double q2_bin_value = h_accep_rec_q2.getBinContent(bin); UNCOMMENT OUT FOR TRAINING
	    double q2_bin_value = h_final_q2.getBinContent(bin);
	    System.out.println(" q2 rec bin value " + q2_bin_value + " bin " + bin );
	    rhs[bin] = q2_bin_value;	   
	}
	
	RealVector b = new ArrayRealVector(rhs);
	RealVector x = solver.solve(b);
	System.out.println("solution x: " + x );

	for( int bin = 0; bin < n_bins; bin++ ){

	    double corr_q2 = x.getEntry(bin);
	    System.out.println(" >> CORRECT VALUE FOR BIN " + bin + ": " + corr_q2 );	    
	    h_corr_q2.setBinContent(bin,corr_q2);
	    
	}
	


	//////////////////////////////////////////////////////////////////////
	//TRADITIONAL ACCEPTANCE CORRECTION TECHNIQUE
 	for( int bin = 0; bin <  h_accep_rec_q2.getXaxis().getNBins(); bin++ ){

	    double gen_value =  h_accep_gen_q2.getBinContent(bin);
	    double rec_value =  h_accep_rec_q2.getBinContent(bin);
	    double accp = rec_value/gen_value;
	    double true_value  = (1.0/accp) * h_final_q2.getBinContent(bin);
	    System.out.println(" >> TRADITIONAL ACCP IS 1/" + accp + " " + h_final_q2.getBinContent(bin) + " " +  true_value + " bin: " + bin );
	    h_trad_final_q2.setBinContent(bin, true_value);
	    

	}

	correctTradAcceptance( h_accep_gen_q2, h_accep_rec_q2, gen_type, min_q2, max_q2 );
	

	///////////////////////////////////////////////////////////////////////
	//APPLY ALL MATRICES TO FLAT DIST.
	String matrix_flat = "m_result_flat_b" + n_bins + ".txt";    
	String matrix_q4 = "m_result_q4_b" + n_bins + ".txt";    
	String matrix_sin = "m_result_sin_b" + n_bins + ".txt";    

	RealMatrix m_flat_final = textToMatrix(matrix_flat,n_bins);
	RealMatrix m_q4_final = textToMatrix(matrix_q4,n_bins);
	RealMatrix m_sin_final = textToMatrix(matrix_sin,n_bins);


	LUDecomposition lud_imp_flat = new LUDecomposition(m_flat_final);
	DecompositionSolver solver_flat = lud_imp_flat.getSolver();

	LUDecomposition lud_imp_q4 = new LUDecomposition(m_q4_final);
	DecompositionSolver solver_q4 = lud_imp_q4.getSolver();

	LUDecomposition lud_imp_sin = new LUDecomposition(m_sin_final);
	DecompositionSolver solver_sin = lud_imp_sin.getSolver();

	RealVector x_flat = solver_flat.solve(b);
	RealVector x_q4 = solver_q4.solve(b);
	RealVector x_sin = solver_sin.solve(b);


	System.out.println("solution x FLAT: " + x_flat );
	System.out.println("solution x Q4: " + x_q4 );
	System.out.println("solution x SIN: " + x_sin );

	H1F h_final_flat = new H1F("h_final_flat","h_final_flat", n_bins, min_q2, max_q2);
	H1F h_final_q4 = new H1F("h_final_q4","h_final_q4", n_bins, min_q2, max_q2);
	H1F h_final_sin = new H1F("h_final_sin","h_final_sin", n_bins, min_q2, max_q2);
		
	for( int bin = 0; bin < n_bins; bin++ ){

	    h_final_flat.setBinContent(bin, x_flat.getEntry(bin) );
	    h_final_q4.setBinContent(bin, x_q4.getEntry(bin) );
	    h_final_sin.setBinContent(bin, x_sin.getEntry(bin) );

	}

	EmbeddedCanvas c_matrix_final_all = new EmbeddedCanvas();
	c_matrix_final_all.setSize(800,800);
	c_matrix_final_all.divide(1,1);
	c_matrix_final_all.cd(0);
	h_final_flat.setLineColor(4);	
	h_final_q4.setLineColor(3);
	h_final_sin.setLineColor(2);
	h_final_gen_q2.setLineColor(1);
	h_final_gen_q2.setLineWidth(3);
	h_final_gen_q2.setTitle("Matrix Method Acceptance Correction for Q2");
	h_final_gen_q2.setTitleX("Q2 [GeV^2]");
	c_matrix_final_all.draw(h_final_gen_q2,"same");
	c_matrix_final_all.draw(h_final_flat,"same");
	c_matrix_final_all.draw(h_final_q4,"same");
	c_matrix_final_all.draw(h_final_sin,"same");
	c_matrix_final_all.save("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h_matrix_comp_final_b"+n_bins+".png");


	H1F h_bbb_flat = new H1F("h_bbb_flat_q2","h_bbb_flat_q2",n_bins,min_q2,max_q2);
	H1F h_bbb_sin = new H1F("h_bbb_sin_q2","h_bbb_sin_q2",n_bins,min_q2,max_q2);
	H1F h_bbb_q4 = new H1F("h_bbb_q4_q2","h_bbb_q4_q2",n_bins,min_q2,max_q2);

	Vector v_accp_flat = textToVector("h_accep_rec_q2_flat_b"+n_bins+".txt", n_bins, min_q2, max_q2,1 );
	Vector v_accp_sin = textToVector("h_accep_rec_q2_sin_b"+n_bins+".txt", n_bins, min_q2, max_q2,1 );
 	Vector v_accp_q4 = textToVector("h_accep_rec_q2_q4_b"+n_bins+".txt", n_bins, min_q2, max_q2,1 );

	for( int bin = 0; bin < n_bins; bin++ ){
	    
	    double accp_flat = (double)v_accp_flat.get(bin);
	    double accp_sin = (double)v_accp_sin.get(bin);
	    double accp_q4 = (double)v_accp_q4.get(bin);

	    double recon_flat = h_final_q2.getBinContent(bin);

	    System.out.println(" >> ACCP FLAT " + accp_flat + " " + accp_sin + " " + accp_q4 + " " + recon_flat);
	    
	    h_bbb_flat.setBinContent(bin,accp_flat*recon_flat);
	    h_bbb_sin.setBinContent(bin,accp_sin*recon_flat);
	    h_bbb_q4.setBinContent(bin,accp_q4*recon_flat);
	}


	EmbeddedCanvas c_bbb_final_all = new EmbeddedCanvas();
	c_bbb_final_all.setSize(800,800);
	c_bbb_final_all.divide(1,1);
	c_bbb_final_all.cd(0);
	h_bbb_flat.setLineColor(4);
	h_bbb_q4.setLineColor(3);
	h_bbb_sin.setLineColor(2);
	h_final_gen_q2.setLineColor(1);
	h_final_gen_q2.setLineWidth(3);
	h_final_gen_q2.setTitle("Matrix Method Acceptance Correction for Q2");
	h_final_gen_q2.setTitleX("Q2 [GeV^2]");
	c_bbb_final_all.draw(h_final_gen_q2,"same");
	c_bbb_final_all.draw(h_bbb_flat,"same");
	c_bbb_final_all.draw(h_bbb_sin,"same");
	c_bbb_final_all.draw(h_bbb_q4,"same");
	c_bbb_final_all.save("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h_bbb_comp_final.png"); 
	

	///////////////////////////////////////////////////////////////////////
	//FITS FOR RESOLUTIONS
	//
	//
	F1D f_res_q2 = FitHistogram(h_res_q2);
	F1D f_res_w = FitHistogram(h_res_w);

	///////////////////////////////////////////////////////////////////////
	//CONVERT SIGMAS SLICES TO TXT FOR ROOT 
	//
	//
	graphToText(h2_res_q2);
	graphToText(h2_res_w);

	System.out.println(" >> SAVING TO HISTOGRAMS ");
	EmbeddedCanvas c_el_prop = new EmbeddedCanvas();
	c_el_prop.setSize(1200,400);
	c_el_prop.divide(3,1);
	c_el_prop.cd(0);
	h_el_p.setTitle("Inclusive Electron Momentum");
	h_el_p.setTitleX("p [GeV]");
	c_el_prop.draw(h_el_p);
	c_el_prop.cd(1);
	h_el_ptheta.setTitle("Inclusive Electron Momentum vs #theta");
	h_el_ptheta.setTitleX("p [GeV]");
	h_el_ptheta.setTitleY("#theta [deg]");
	c_el_prop.draw(h_el_ptheta,"colz");
	c_el_prop.cd(2);
	h_el_pphi.setTitle("Inclusive Electron Momentum vs #phi");
	h_el_pphi.setTitleX("p [GeV]");
	h_el_pphi.setTitleY("#phi [deg]");
	c_el_prop.draw(h_el_pphi,"colz");

	EmbeddedCanvas c_el_phy = new EmbeddedCanvas();
	c_el_phy.setSize(800,400);
	c_el_phy.divide(2,1);
	c_el_phy.cd(0);
	h_el_xq2.setTitle("Inclusive Electron Q^2 vs xB");
	h_el_xq2.setTitleX("Xb");
	h_el_xq2.setTitleY("Q^2 [GeV^2]");
	c_el_phy.draw(h_el_xq2,"colz");
	c_el_phy.cd(1);
	h_el_q2w.setTitle("Inclusive Electron Q2 vs W");
	h_el_q2w.setTitleX("W [GeV] ");
	h_el_q2w.setTitleY("Q^2 [GeV^2]");
	c_el_phy.draw(h_el_q2w,"colz");

	c_el_prop.save("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h_rec_el_"+gen_type+"_properties.png");
	c_el_phy.save("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h_rec_el_"+gen_type+"phy.png");

	EmbeddedCanvas c_gel_prop = new EmbeddedCanvas();
	c_gel_prop.setSize(1200,400);
	c_gel_prop.divide(3,1);
	c_gel_prop.cd(0);
	h_gel_p.setTitle("Inclusive Electron Momentum");
	h_gel_p.setTitleX("p [GeV]");
	c_gel_prop.draw(h_gel_p);
	c_gel_prop.cd(1);
	h_gel_ptheta.setTitle("Inclusive Electron Momentum vs #theta");
	h_gel_ptheta.setTitleX("p [GeV]");
	h_gel_ptheta.setTitleY("#theta [deg]");
	c_gel_prop.draw(h_gel_ptheta,"colz");
	c_gel_prop.cd(2);
	h_gel_pphi.setTitle("Inclusive Electron Momentum vs #phi");
	h_gel_pphi.setTitleX("p [GeV]");
	h_gel_pphi.setTitleY("#phi [deg]");
	c_gel_prop.draw(h_gel_pphi,"colz");

	EmbeddedCanvas c_gel_phy = new EmbeddedCanvas();
	c_gel_phy.setSize(800,400);
	c_gel_phy.divide(2,1);
	c_gel_phy.cd(0);
	h_gel_xq2.setTitle("Inclusive Electron Q^2 vs xB");
	h_gel_xq2.setTitleX("Xb");
	h_gel_xq2.setTitleY("Q^2 [GeV^2]");
	c_gel_phy.draw(h_gel_xq2,"colz");
	c_gel_phy.cd(1);
	h_gel_q2w.setTitle("Inclusive Electron Q2 vs W");
	h_gel_q2w.setTitleY(" W [GeV] ");
	h_gel_q2w.setTitleX(" Q^2 [GeV^2]");
	c_gel_phy.draw(h_gel_q2w,"colz");

	EmbeddedCanvas c1_el_phy = new EmbeddedCanvas();
	c1_el_phy.setSize(800,400);
	c1_el_phy.divide(2,1);
	c1_el_phy.cd(0);
	h_gel_q2.setTitle("MC Q^2");
	h_gel_q2.setTitleX("Q2 [GeV^2]");
	c1_el_phy.draw(h_gel_q2);
	c1_el_phy.cd(1);
	h_gel_w.setTitle("MC W");
	h_gel_w.setTitleX("W [GeV]");
	c1_el_phy.draw(h_gel_w);
	
	EmbeddedCanvas c1_rel_phy = new EmbeddedCanvas();
	c1_rel_phy.setSize(800,400);
	c1_rel_phy.divide(2,1);
	c1_rel_phy.cd(0);
	h_el_q2.setTitle("REC Q2");
	h_el_q2.setTitleX("Q2 [GeV^2]");
	c1_rel_phy.draw(h_el_q2);
	c1_rel_phy.cd(1);
	h_el_w.setTitle("REC W");
	h_el_w.setTitleX("W [GeV]");
	c1_rel_phy.draw(h_el_w);
	

	EmbeddedCanvas c_res_phy = new EmbeddedCanvas();
	c_res_phy.setSize(800,400);
	c_res_phy.divide(2,1);
	c_res_phy.cd(0);
	h_res_q2.setTitle("Res Q^2");
	h_res_q2.setTitleX(" #delta Q^2 [GeV^2]");
	f_res_q2.setLineColor(2);
	c_res_phy.draw(h_res_q2);
	c_res_phy.draw(f_res_q2,"same");
	c_res_phy.cd(1);
	h_res_w.setTitle("Res W");
	h_res_w.setTitleX(" #delta W [GeV]");
	c_res_phy.draw(h_res_w);

	EmbeddedCanvas c2_res_phy = new EmbeddedCanvas();
	c2_res_phy.setSize(800,400);
	c2_res_phy.divide(2,1);
	c2_res_phy.cd(0);
	h2_res_q2.setTitle("Res Q^2");
	h2_res_q2.setTitleX(" Q^2 [GeV^2]");
	h2_res_q2.setTitleY(" #delta Q^2 [GeV^2]");
	c2_res_phy.draw(h2_res_q2, "colz");
	c2_res_phy.cd(1);
	h2_res_w.setTitle("Res W");
	h2_res_w.setTitleX("  W [GeV]");
	h2_res_w.setTitleY(" #delta W [GeV]");
	c2_res_phy.draw(h2_res_w, "colz");

	EmbeddedCanvas c_accp_q2 = new EmbeddedCanvas();
	c_accp_q2.setSize(800,400);
	c_accp_q2.divide(2,1);
	c_accp_q2.cd(0);
	h_accep_gen_q2.setTitle("GEN EVENTS Q2");
	h_accep_gen_q2.setTitleX("Q2 [GeV^2]");
	c_accp_q2.draw(h_accep_gen_q2);
	c_accp_q2.cd(1);
	h_accep_rec_q2.setTitle("REC EVENTS Q2");
	h_accep_rec_q2.setTitleX("Q2 [GeV^2]");
	c_accp_q2.draw(h_accep_rec_q2);


	EmbeddedCanvas c_corr_q2 = new EmbeddedCanvas();
	c_corr_q2.setSize(800,800);
	c_corr_q2.divide(1,1);
	c_corr_q2.cd(0);
	h_corr_q2.setTitle("ACCEPTANCE CORRECTED Q^2");
	h_corr_q2.setTitleX("Q^2 [GeV^2]");
	h_corr_q2.setLineColor(2);
	h_corr_q2.setLineWidth(3);
	//h_accep_rec_q2.setLineColor(4); UNCOMMENT FOR TRAINING
	h_final_q2.setLineColor(4);
	h_trad_final_q2.setLineColor(5);
	c_corr_q2.draw(h_corr_q2);
	c_corr_q2.draw(h_final_q2,"same");
	c_corr_q2.draw(h_final_gen_q2,"same");
	c_corr_q2.draw(h_trad_final_q2,"same");


	c_gel_prop.save("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h_gen_el_"+gen_type+"properties.png");
	c_gel_phy.save("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h_gen_"+gen_type+"phy.png");
	c1_el_phy.save("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h_gel_"+gen_type+"phy.png");
	c1_rel_phy.save("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h_rel_"+gen_type+"phy.png");
	c_res_phy.save("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h_res_"+gen_type+"phy.png");
	c2_res_phy.save("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h2_res_"+gen_type+"phy.png");
	c_accp_q2.save("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h_accp_"+gen_type+"q2.png");
	c_corr_q2.save("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h_corr_"+gen_type+"q2.png");
	
    }

   public static F1D FitHistogram( H1F h_temp ){

 	System.out.println(" >> FITTING HISTOGRAM " + h_temp.getName() );
	double xlow, xhigh, histmax;
	int binlow, binhigh, binmax;

	double percentofmax = 0.55;

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
	    
	    DataFitter.fit(fit_temp, h_temp, "R");
	    fit = fit_temp;  

	    //}
	    System.out.println(" >> PARAMETER SET " + fit_temp.getParameter(0) + " " + fit_temp.getParameter(1) + " " + fit_temp.getParameter(2) );

	}
	catch(Exception e){
	    System.out.println("ERROR WITH FITTING - LIKELY IT DID NOT CONVERGE");
	}
	return fit;
	    
    }


   public static void graphToText( H2F h_temp ){
       System.out.println(" >> CONVERTING GRAPH ERRORS RESULTS TO TEXT FILE " );

       ParallelSliceFitter temp_psf = new ParallelSliceFitter(h_temp);
       temp_psf.fitSlicesX(10);
       GraphErrors temp_ge = temp_psf.getSigmaSlices();
       
       DataVector temp_x = temp_ge.getVectorX();
       DataVector temp_y = temp_ge.getVectorY();

       double[] x = new double[temp_x.size()];
       double[] y = new double[temp_y.size()];
       
       BufferedWriter writer = null;
       try{
	   String res_results = h_temp.getName() + "_" + ".txt";
	   System.out.println(" >> WRITING TO " + res_results );
	   writer = new BufferedWriter( new FileWriter(res_results) );
	   
	   for( int bin = 0; bin < temp_ge.getDataSize(0); bin++ ){
	       double data_x = temp_ge.getDataX(bin);    
	       double data_y = temp_ge.getDataY(bin);    

	       DecimalFormat numberFormat = new DecimalFormat("0.00000");

	       System.out.println(" >> WRITING TO TXT: " + numberFormat.format(data_x) + " " + numberFormat.format(data_y) );
	       writer.write(numberFormat.format(data_x) + " " + numberFormat.format(data_y) + "\n" );

	   }		    
       }
       catch( IOException e){
	   System.out.println(" >> ERROR WRITING TO FILE " );
       }
       finally{
	   try{
	       if( writer != null ){
		   writer.close();
	       }
	   }
	   catch( IOException e ){
	       System.out.println(" >> ERROR CLOSING FILE " );
	   }
	   
       }

   }

    public static RealMatrix textToMatrix( String fname, int n_bins ){
	System.out.println(" >> READING FROM TXT FILE TO CONSTRUCT MATRIX ");
	RealMatrix temp_m = new Array2DRowRealMatrix( new double[n_bins][n_bins]);
	try{
	    FileReader filereader = new FileReader(fname);
	    BufferedReader buffreader = new BufferedReader(filereader);
	    String line;
	    int row = 0; 
	    while( (line = buffreader.readLine()) != null ){
		    System.out.println(" >> " + line );
		    String[] temp_elements = line.split(" ");
		    int col = 0;
		    for( int i = 0; i < n_bins; i++ ){
			System.out.println(" >> ROW " + row + " COL " + col +  " element " + temp_elements[i] );
			temp_m.setEntry( row, col, Double.parseDouble(temp_elements[i]) ); 
			col++;
		    }
		    row++;
		}

	}
	catch( IOException e ){
	    e.printStackTrace();
	}

	return temp_m;
    }


    public static void correctTradAcceptance( H1F h_temp_gen, H1F h_temp_rec, String gentype, double min_q2, double max_q2 ){


       BufferedWriter writer = null;
       try{
	   int n_bins = h_temp_rec.getXaxis().getNBins();
	   String res_results = h_temp_rec.getName() + "_" + gentype + "_b"+n_bins+".txt";
	   System.out.println(" >> WRITING TO " + res_results );
	   writer = new BufferedWriter( new FileWriter(res_results) );
	   
	   DecimalFormat numberFormat = new DecimalFormat("0.00000");

	   for( int bin = 0; bin <  h_temp_rec.getXaxis().getNBins(); bin++ ){
	      
	       double gen_value =  h_temp_gen.getBinContent(bin);
	       double rec_value =  h_temp_rec.getBinContent(bin);
	       double accp = rec_value/gen_value;

	       System.out.println(" >> TRADITIONAL ACCP IS 1/" + accp + " " + bin );
	       System.out.println(" >> WRITING TO TXT: " + numberFormat.format(accp) );
	       writer.write(bin + " " + numberFormat.format(1.0/accp) + "\n");	       
	       
	   }
	   		    
       }
       catch( IOException e){
	   System.out.println(" >> ERROR WRITING TO FILE " );
       }
       finally{
	   try{
	       if( writer != null ){
		   writer.close();
	       }
	   }
	   catch( IOException e ){
	       System.out.println(" >> ERROR CLOSING FILE " );
	   }
	   
       }
    }

    public static Vector<Double> textToVector( String fname, int nbins, double min_q2, double max_q2, int col ){

	System.out.println(" >> READING FROM TXT FILE TO CONSTRUCT HISTOGRAM ");
	Vector v_temp = new Vector<Double>();
	try{
	    FileReader filereader = new FileReader(fname);
	    BufferedReader buffreader = new BufferedReader(filereader);
	    String line;
	    int row = 0; 
	    while( (line = buffreader.readLine()) != null ){
		    System.out.println(" >> " + line );
		    String[] temp_elements = line.split(" ");
		    
		    System.out.println(" >> ACCP " + temp_elements[1] ); 
		    int bin = Integer.parseInt(temp_elements[0]);
		    double accp = Double.parseDouble(temp_elements[1]);
		    if( col == 0 ){ v_temp.add(bin); }
		    if( col == 1 ){ v_temp.add(accp); }

		   
		}

	}
	catch( IOException e ){
	    e.printStackTrace();
	}
	return v_temp;
    }



    

}


