package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.io.hipo.HipoDataSource;
import org.jlab.clas.analysis.clary.Detectors;

import java.io.*;
import java.util.*;


public class BEventWriter{

    DataEvent ev = null;
    public BEvent bev;// = null;

    public BEventWriter( DataEvent temp_ev ){ //, int temp_rec_i ){
	ev = temp_ev;
	
    }

    public BEventWriter( ){

	bev = new BEvent();

    }

    public void clearBEvent(){

	bev.start_time = -10000;
	bev.rf_value = -10000;

	bev.charge.clear();
	bev.px.clear();
	bev.py.clear();
	bev.pz.clear();
	bev.vz.clear();
	bev.ecal_sect.clear();
	bev.ecsf.clear();
	bev.pcal_e.clear();
	bev.ec_ei.clear();
	bev.ec_eo.clear();
	bev.htcc_nphe.clear();
	bev.dcr1_sect.clear();
	bev.dcr1_cx.clear();
	bev.dcr1_cy.clear();

	bev.dcr2_sect.clear();
	bev.dcr2_cx.clear();
	bev.dcr2_cy.clear();

	bev.dcr3_sect.clear();
	bev.dcr3_cx.clear();
	bev.dcr3_cy.clear();

	bev.pcal_x.clear();
	bev.pcal_y.clear();

	bev.clas12_beta.clear();
	bev.scint_sector.clear();
	bev.scint_detector.clear();
	bev.scint_layer.clear();
	bev.scint_bar.clear();

	bev.ftofl1_energy.clear();
	bev.ftofl1_path.clear();
	bev.ftofl1_tof.clear();

	bev.ftofl2_energy.clear();
	bev.ftofl2_path.clear();
	bev.ftofl2_tof.clear();

	bev.recCalBankMap.clear();
	bev.recScintBankMap.clear();
	bev.recTrackBankMap.clear();
	bev.recCherenkovBankMap.clear();

    }

    public void setEventVariables( DataEvent temp_ev ){
	boolean haseventBank = temp_ev.hasBank("REC::Event");
	/////////////////////////////////////////////////////////
	//BASIC REC EVENT BANK INFORMATION 
	///////	
	if( haseventBank ){
	    DataBank eventBank = temp_ev.getBank("REC::Event");
	    bev.start_time = eventBank.getFloat("STTime",0);
	    bev.rf_value = eventBank.getFloat("RFTime",0);
	}       
	else{
	    bev.start_time = -10000;
	    bev.rf_value = -10000;
	}

    }

    public void createBEvent( DataEvent temp_ev ){
	boolean hasrecBank = temp_ev.hasBank("REC::Particle");
	boolean haseventBank =temp_ev.hasBank("REC::Event");
	if( hasrecBank && haseventBank ){
	    DataBank recBank = temp_ev.getBank("REC::Particle");
	    //System.out.println(" EVENT  HAS " + recBank.rows() + " ROWS " );
	    for( int rec_i = 0; rec_i < recBank.rows(); rec_i++ ){
		

		/////////////////////////////////////////////////////////
		//BASIC REC PARTICLE BANK INFORMATION 
		///////
		bev.charge.add( recBank.getInt("charge", rec_i ) );
		bev.px.add( (double)recBank.getFloat("px", rec_i ) );
		bev.py.add( (double)recBank.getFloat("py", rec_i ) );
		bev.pz.add( (double)recBank.getFloat("pz", rec_i ) );
		bev.vz.add( (double)recBank.getFloat("vz", rec_i ) );

		/////////////////////////////////////////////////////////
		//ADDED CALORIMETER BANK INFORMATION 
		///////	       
		if( temp_ev.hasBank("REC::Calorimeter") ){
		    DataBank recCal = temp_ev.getBank("REC::Calorimeter");
		    HashMap<Integer, Double> m_edep = Detectors.getEDepCal( temp_ev, rec_i );
		    int ec_count = 0;
		    boolean pcale = true;
		    boolean ecei = true;
		    boolean eceo = true;

		    for( Map.Entry<Integer, Double> entry : m_edep.entrySet() ){
			if( entry.getKey() == 1 ){
			    bev.pcal_e.add( entry.getValue() );
			    pcale = false;
			}
			if( entry.getKey() == 4 ){
			    bev.ec_ei.add( entry.getValue() );
			    ecei = false;
			}
			if( entry.getKey() == 7 ){
			    bev.ec_eo.add( entry.getValue() );
			    ecei = false;
			}
			ec_count=+1;
		    }
		    if( pcale ) { bev.pcal_e.add(0.0); }
		    if( ecei ) { bev.ec_ei.add(0.0); }
		    if( eceo ) { bev.ec_eo.add(0.0); }
		    //////////////////////////////////////////////////////////////
		    //CALORIMETER HIT INFORMATION 
		    ///////		    
		    bev.ecal_sect.add( Detectors.getSectorECAL(temp_ev, rec_i) - 1 ); //SECTORS FROM 0 TO 5		
		    ArrayList<Double> v_pcal_hit = Detectors.PCALHit(temp_ev, rec_i);
		    if( v_pcal_hit.size() > 0 ){
			bev.pcal_x.add( v_pcal_hit.get(0) );
			bev.pcal_y.add( v_pcal_hit.get(1) );
		    }
		    else{
			bev.pcal_x.add( -10000.0 );
			bev.pcal_y.add( -10000.0 );
		    }
		}
		else{
		    /////////////////////////////////////////////////////////
		    //SET INFORMATION TO ZERO IF NOTHIN IN CALORIMETER
		    ///////		
		    bev.pcal_e.add( 0.0 );
		    bev.ec_ei.add( 0.0 );
		    bev.ec_eo.add( 0.0 );
		    bev.ecal_sect.add( -1 ); //SECTORS FROM 0 TO 5
		    bev.pcal_x.add( -10000.0 );
		    bev.pcal_y.add( -10000.0 );
		}		
		/////////////////////////////////////////////////////////////////////
		//BASIC HTCC REC BANK INFORMATION FOR NPHE (RETURNS -1 FOR NO HIT)
		///////		
		bev.htcc_nphe.add( Detectors.getCherenkovNPHE( temp_ev, rec_i ) );


		//////////////////////////////////////////////////////////////////////////////////
		//BASIC DRIFT CHAMBER INFORMATION INFORMATION - INCLUDES SECTOR N HIT POSITION 
		///////		
		if( temp_ev.hasBank("REC::Track") && temp_ev.hasBank("TimeBasedTrkg::TBCrosses") && temp_ev.hasBank("TimeBasedTrkg::TBTracks") ){
		    Vector<Double> dc_r1_locxy = Detectors.getDCCrossLocalR1(temp_ev, rec_i);		    
		    Vector<Double> dc_r2_locxy = Detectors.getDCCrossLocalR2(temp_ev, rec_i);		    
		    Vector<Double> dc_r3_locxy = Detectors.getDCCrossLocalR3(temp_ev, rec_i);		    
		   
		    if( dc_r1_locxy.size() > 0 ){
			bev.dcr1_sect.add( Detectors.getDCSectorR1(temp_ev, rec_i) - 1 );
			bev.dcr1_cx.add( dc_r1_locxy.get(0) );
			bev.dcr1_cy.add( dc_r1_locxy.get(1) );
		    }
		    else{
			bev.dcr1_sect.add(-1);
			bev.dcr1_cx.add( -1000.0 );
			bev.dcr1_cy.add( -1000.0 );
		    }
		    if( dc_r2_locxy.size() > 0 ){
			bev.dcr2_sect.add( Detectors.getDCSectorR2(temp_ev, rec_i) - 1 );
			bev.dcr2_cx.add( dc_r2_locxy.get(0) );
			bev.dcr2_cy.add( dc_r2_locxy.get(1) );
		    }
		    else{
			bev.dcr2_sect.add(-1);
			bev.dcr2_cx.add( -1000.0 );
			bev.dcr2_cy.add( -1000.0 );
		    }
		    if( dc_r3_locxy.size() > 0 ){
			bev.dcr3_sect.add( Detectors.getDCSectorR3(temp_ev, rec_i) - 1 );
			bev.dcr3_cx.add( dc_r3_locxy.get(0) );
			bev.dcr3_cy.add( dc_r3_locxy.get(1) );
		    }
		    else{
			bev.dcr3_sect.add(-1);
			bev.dcr3_cx.add( -1000.0 );
			bev.dcr3_cy.add( -1000.0 );
		    }

		    /*bev.dcr2_sect.add( Detectors.getDCSectorR2(temp_ev, rec_i) - 1 );
		    bev.dcr2_cx.add( dc_r2_locxy.get(0) );
		    bev.dcr2_cy.add( dc_r2_locxy.get(1) );

		    bev.dcr3_sect.add( Detectors.getDCSectorR3(temp_ev, rec_i) - 1 );
		    bev.dcr3_cx.add( dc_r3_locxy.get(0) );
		    bev.dcr3_cy.add( dc_r3_locxy.get(1) );
		    */
		}
		else{
		    ///////////////////////////////////////////////////////////
		    //SET INFORMATION TO ZERO IF NOTHING IN THE BANK
		    ///////		
		    bev.dcr1_sect.add(-1);
		    bev.dcr1_cx.add( -1000.0 );
		    bev.dcr1_cy.add( -1000.0 );

		    bev.dcr2_sect.add(-1);
		    bev.dcr2_cx.add( -1000.0 );
		    bev.dcr2_cy.add( -1000.0 );

		    bev.dcr3_sect.add(-1);
		    bev.dcr3_cx.add( -1000.0 );
		    bev.dcr3_cy.add( -1000.0 );
		}
	    
	
		bev.clas12_beta.add( (double)recBank.getFloat("beta",rec_i) );
	
		
		ArrayList<Double> ftofinfo_l1 = Detectors.getFTOFLayerInfo(temp_ev, rec_i, 1);
		ArrayList<Double> ftofinfo_l2 = Detectors.getFTOFLayerInfo(temp_ev, rec_i, 2);

		double ftof1_e = Detectors.getFTOFLayerEnergy(temp_ev, rec_i, 1 );
		double ftof2_e = Detectors.getFTOFLayerEnergy(temp_ev, rec_i, 2 );
		bev.ftofl1_energy.add(ftofinfo_l1.get(0));
		bev.ftofl1_path.add(ftofinfo_l1.get(1));
		bev.ftofl1_tof.add(ftofinfo_l1.get(2));

		bev.ftofl2_energy.add(ftofinfo_l2.get(0));
		bev.ftofl2_path.add(ftofinfo_l2.get(1));
		bev.ftofl2_tof.add(ftofinfo_l2.get(2));

		ftofinfo_l1.clear();
		ftofinfo_l2.clear();

		//////////////////////////////////////////////////////////////////////////////////
		//BASIC SCINT BANK INFORMATION - INCLUDES SECTOR N LAYER N HIT INFO
		///////		
		/*if( temp_ev.hasBank("REC::Scintillator") ){
		    DataBank scintBank = temp_ev.getBank("REC::Scintillator");
		    DataBank eventBank = temp_ev.getBank("REC::Event");
		    System.out.println(" >> scint rows " + scintBank.rows() );


		    int sci_sect = -1;
		    double ftof1_e = -10;
		    double ftof2_e = -10;
		    for( int j = 0; j < scintBank.rows(); j++ ){
			int pindex = scintBank.getShort("pindex",j);
		    	System.out.println(" >> pindex " + pindex + " rec " + rec_i );
			
			if( pindex == rec_i ){			
			    int scint_detector = scintBank.getByte("detector",j);
			    int scint_layer = scintBank.getByte("layer",j);

			    if( scint_detector == 12 ){
				if( scint_layer == 1 ){
				    ftof1_e  = scintBank.getFloat("energy",j)/100.0;		 		    
				    
				}
				if( scint_layer == 2 ){
				    ftof2_e  = scintBank.getFloat("energy",j)/100.0;
				   
				}
			    }
			}

		    }
		*/
	       
		//bev.ftofl1_energy.add(ftof1_e);
		    //bev.ftofl1_path.add(-10.0);
		    //bev.ftofl1_tof.add(-10.0);			    
		    
		//  bev.ftofl2_energy.add(ftof1_e);
		    //bev.ftofl2_path.add(-10.0);
		    //bev.ftofl2_tof.add(-10.0);			    
		//  System.out.println(" >> after scint loop ftofl1 e " + bev.ftofl1_energy ); 

		    /*for( int i = 0; i < scintBank.rows(); i++){		    
			int pindex = scintBank.getShort("pindex",i);
		    	System.out.println(" >> pindex " + pindex + " rec " + rec_i );

			if( pindex == rec_i ){			
			    int scint_sector = scintBank.getInt("sector",i) - 1;
			    int scint_detector = scintBank.getByte("detector",i);
			    int scint_layer = scintBank.getByte("layer",i);
			    int scint_bar = scintBank.getInt("component",i) - 1  ;
			    
			    double ftof_e  = scintBank.getFloat("energy",i)/100.0;
			    
			    double start_time = eventBank.getFloat("STTime",0);
			    double r_path = scintBank.getFloat("path",i);
			    double t_ftof = scintBank.getFloat("time",i);
			    if( scint_detector == 12 ){
				if( scint_layer == 1 ){
		 		    bev.ftofl1_energy.add(ftof_e);
				    bev.ftofl1_path.add(r_path);
				    bev.ftofl1_tof.add(t_ftof);
				}
				else{
				    bev.ftofl1_energy.add(-10.0);
				    bev.ftofl1_path.add(-10.0);
				    bev.ftofl1_tof.add(-10.0);			    
				}
				if( scint_layer == 2 ){
				    bev.ftofl2_energy.add(ftof_e);
				    bev.ftofl2_path.add(r_path);
				    bev.ftofl2_tof.add(t_ftof);
				}   
				else{				    
				    bev.ftofl2_energy.add(-10.0);
				    bev.ftofl2_path.add(-10.0);
				    bev.ftofl2_tof.add(-10.0);			    
				}
			    }
			    else{
				bev.ftofl1_energy.add(-10.0);
				bev.ftofl1_path.add(-10.0);
				bev.ftofl1_tof.add(-10.0);			    
			    			    
				bev.ftofl2_energy.add(-10.0);
				bev.ftofl2_path.add(-10.0);
				bev.ftofl2_tof.add(-10.0);			    			    
			    }
			}
			}*/
		//}
		//else{
		/*    bev.ftofl1_energy.add(-10.0);
		    bev.ftofl1_path.add(-10.0);
		    bev.ftofl1_tof.add(-10.0);			    
		    
		    bev.ftofl2_energy.add(-10.0);
		    bev.ftofl2_path.add(-10.0);
		    bev.ftofl2_tof.add(-10.0);			    
		    }*/
	    }	
	}
	
    }

    public void setBankMaps( Map<Integer, List<Integer> > temp_map, DataBank fromBank, DataBank toBank, String idxVarName ){
	temp_map.clear();
	if( fromBank == null ) return;
	if( toBank == null ) return;
	for( int i = 0; i < fromBank.rows(); i++ ){
	    final int iTo = fromBank.getInt(idxVarName,i);
	    if( temp_map.containsKey(iTo) ){
		temp_map.get(iTo).add(i);
	    }
	    else{
		List<Integer> iFrom = new ArrayList<Integer>();
		temp_map.put(iTo,iFrom);
		temp_map.get(iTo).add(i);
	    }
	}
    }

    public void loadDataParticles( DataEvent temp_ev ){
	

    }
    
    public void loadBankMaps( DataEvent temp_ev ){

 	DataBank recBank = null;
	DataBank calBank = null;
	DataBank scintBank = null;
	DataBank trackBank = null;
	DataBank cherenBank = null;

	boolean hasrec = temp_ev.hasBank("REC::Particle");
	boolean hascal = temp_ev.hasBank("REC::Calorimeter");
	boolean hasscint = temp_ev.hasBank("REC::Scintillator");
	boolean hastrack = temp_ev.hasBank("REC::Track");
	boolean haschrkv = temp_ev.hasBank("REC::Cherenkov");

	if( hasrec && hascal &&
	    hasscint && hastrack &&
	    haschrkv ){
	    	    
	    recBank = temp_ev.getBank("REC::Particle");
	    calBank = temp_ev.getBank("REC::Calorimeter");
	    scintBank = temp_ev.getBank("REC::Scintillator"); 
	    trackBank = temp_ev.getBank("REC::Track"); 
	    cherenBank = temp_ev.getBank("REC::Cherenkov"); 

	    setBankMaps( bev.recCalBankMap, calBank, recBank, "pindex");
	    setBankMaps( bev.recScintBankMap, scintBank, recBank, "pindex");
	    setBankMaps( bev.recCalBankMap, trackBank, recBank, "pindex");
	}

	for( Map.Entry<Integer, List<Integer> > entry : bev.recScintBankMap.entrySet() ){
	    //System.out.println(" >> " + entry.getKey() + " " + entry.getValue() );
	}

    }

    public BEvent getBEvent(DataEvent temp_ev){
	
	clearBEvent();
	setEventVariables(temp_ev);
	createBEvent(temp_ev);
	
	
	return bev;

    }

}
