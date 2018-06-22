package org.jlab.clas.analysis.clary;

import java.util.*; //ArrayList;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.clas.analysis.clary.Calculator;

public class Detectors {

    static public int pcal = 1;
    static public int ec_ei = 4;
    static public int ec_eo = 7;
    static public int ec_cal = 7;
    
    public static HashMap ECHit( DataEvent tempevent, int temp_pindex ){
	HashMap<Integer ,ArrayList<Double> > m_ec_hit = new HashMap<Integer, ArrayList<Double> >();
	
	if( tempevent.hasBank("REC::Calorimeter") ){
	    DataBank ecBank = tempevent.getBank("REC::Calorimeter");
	    for( int i = 0; i < tempevent.getBank("REC::Calorimeter").rows(); i++ ){
		ArrayList<Double> hit_pos = new ArrayList<Double>();

		int detector = ecBank.getInt("layer",i);
		double hit_x = ecBank.getFloat("x",i);
		double hit_y = ecBank.getFloat("y",i);
		double hit_z = ecBank.getFloat("z",i);
		hit_pos.add(hit_x);
		hit_pos.add(hit_y);
		hit_pos.add(hit_z);		
		//System.out.println(" >> " + detector );
		if( ecBank.getShort("pindex",i) == temp_pindex && (detector == ec_ei ) ){
		    m_ec_hit.put( detector, hit_pos );
		}
		if( ecBank.getShort("pindex",i) == temp_pindex && (detector == ec_eo ) ){
		    m_ec_hit.put( detector, hit_pos );
		}
	    }
	}
	
	return m_ec_hit;
    }

    public static HashMap ECHitOuter( DataEvent tempevent, int temp_pindex ){
	HashMap<Integer ,ArrayList<Double> > m_ec_hit = new HashMap<Integer, ArrayList<Double> >();
	
	if( tempevent.hasBank("REC::Calorimeter") ){
	    DataBank ecBank = tempevent.getBank("REC::Calorimeter");
	    for( int i = 0; i < tempevent.getBank("REC::Calorimeter").rows(); i++ ){
		ArrayList<Double> hit_pos = new ArrayList<Double>();

		int detector = ecBank.getInt("layer",i);
		double hit_x = ecBank.getFloat("x",i);
		double hit_y = ecBank.getFloat("y",i);
		double hit_z = ecBank.getFloat("z",i);
		hit_pos.add(hit_x);
		hit_pos.add(hit_y);
		hit_pos.add(hit_z);		
		//System.out.println(" >> " + detector );
		if( ecBank.getShort("pindex",i) == temp_pindex && (detector == ec_ei ) ){
		    m_ec_hit.put( detector, hit_pos );
		}
		if( ecBank.getShort("pindex",i) == temp_pindex && (detector == ec_eo ) ){
		    m_ec_hit.put( detector, hit_pos );
		}
	    }
	}
	
	return m_ec_hit;
    }
    
    public static ArrayList PCALHit( DataEvent tempevent, int temp_pindex ){
	ArrayList<Double> pcal_hit = new ArrayList<Double>();

	if( tempevent.hasBank("REC::Calorimeter") ){
	    //  System.out.println(" >> EVENT HAS CAL BANK " );
	    DataBank ecBank = tempevent.getBank("REC::Calorimeter");
	    for( int i = 0; i < tempevent.getBank("REC::Calorimeter").rows(); i++ ){
		//System.out.println(" >> LOOPING OVER REC::CAL index " + i );
		int layer = ecBank.getInt("layer",i);
		int pindex = ecBank.getShort("pindex",i);
		if( ecBank.getShort("pindex",i) == temp_pindex && layer == pcal ){
		    //System.out.println(" >> pindex " + pindex + " rec index " + temp_pindex );
		    double hit_x = ecBank.getFloat("x",i);
		    double hit_y = ecBank.getFloat("y",i);
		    double hit_z = ecBank.getFloat("z",i);
		    pcal_hit.add(hit_x);
		    pcal_hit.add(hit_y);
		    pcal_hit.add(hit_z);
		}
	    }
	}
	else{
	    pcal_hit.add(-1000.0);
	    pcal_hit.add(-1000.0);
	    pcal_hit.add(-1000.0);
	}
	//System.out.println(" >> SIZE OF pcal_hit array " + pcal_hit.size() );
	return pcal_hit;
    }

    public static double ftofTiming( DataEvent tempevent, int temp_pindex, int layer ){
	//ArrayList<double> ftof_time = new ArrayList<double>();
	double ftof_hit_time = 0.0;
	//System.out.println(" >> GETTING FTOF TIME " );
	if( tempevent.hasBank("REC::Scintillator") ){
	    //System.out.println(" >> HAS SCINTILLATOR " );
	    DataBank scintBank = tempevent.getBank("REC::Scintillator");
	    for( int i = 0; i < scintBank.rows(); i++ ){
		if( scintBank.getShort("pindex",i) == temp_pindex ){
		    //System.out.println(" >> HAS PINDEX : index " + i + " " + scintBank.getShort("pindex",i) + " " + temp_pindex );
		    int scint_layer = scintBank.getInt("layer",i);
		    //System.out.println(" >> SCINT LAYER " + scint_layer + " for " + layer );
		    if( scint_layer == layer ){
			//System.out.println(" >> MATCHING SCINT LAYER " + scint_layer );
			float time = scintBank.getFloat("time",i);
			ftof_hit_time = time;
		    }
		}
	    }
	}
	//System.out.println(" >> TIME IS  " + ftof_hit_time );
	return ftof_hit_time;
    }


    public static HashMap<Integer, Double> getEDepCal( DataEvent tempevent, int temp_pindex ){

	HashMap m_edep = new HashMap();
	if( tempevent.hasBank("REC::Calorimeter") ){

	    DataBank calBank = tempevent.getBank("REC::Calorimeter");
	    for( int i = 0; i < calBank.rows(); i++ ){
		if( calBank.getShort("pindex", i) == temp_pindex ){
		    int cal_detector = calBank.getInt("layer", i);
		    double edep = calBank.getFloat("energy",i );		    		    
		    //System.out.println(" >> CAL HIT IN " + cal_detector + " ENERGY " + edep );
		    m_edep.put(cal_detector, edep);
		}
	    }
	}
	return m_edep;
    }


    public static int getSectorDC( DataEvent tempevent, int temp_pindex ){ //WTF DID I TYPE HERE CHANGE NAME TO SCINT
	int sector = -1;
	if( tempevent.hasBank("REC::Scintillator") ){
	    DataBank  scintBank = tempevent.getBank("REC::Scintillator");
	    for( int i = 0; i < scintBank.rows(); i++ ){
		if( scintBank.getShort("pindex",i) == temp_pindex ){
		    sector = scintBank.getInt("sector",i);
		}
	    }
	}
	return sector;
    }

    public static int getSectorECAL( DataEvent tempevent, int temp_pindex ){
	int sector = -1;
	if( tempevent.hasBank("REC::Calorimeter") ){
	    DataBank  scintBank = tempevent.getBank("REC::Calorimeter");
	    for( int i = 0; i < scintBank.rows(); i++ ){
		if( scintBank.getShort("pindex",i) == temp_pindex ){
		    sector = scintBank.getInt("sector",i);
		}
	    }
	}
	return sector;
    }

    public static int getSectorPCAL( DataEvent tempevent, int temp_pindex ){
	int sector = -1;
	if( tempevent.hasBank("REC::Calorimeter") ){
	    DataBank calBank = tempevent.getBank("REC::Calorimeter");
	    for( int i = 0; i < calBank.rows(); i++ ){
		if( calBank.getShort("pindex",i) == temp_pindex ){
		    int layer = calBank.getInt("layer",i);
		    if( layer == pcal ){
			sector = calBank.getInt("sector",i);
		    }
		}
	    }
	}
	return sector;
    }


    
    
    public static double getCherenkovNPHE( DataEvent event, int rec_i ){

	float nphe = -1;

	/*if( event.hasBank("REC::Cherenkov") ){    	
	    DataBank chkovBank = tempevent.getBank("REC::Cherenkov");
	    for( int i = 0; i < chkovBank.rows(); i++ ){
		int pindex =  chkovBank.getShort("pindex",i);                                                                                                                                                              int detector = chkov.getByte("detector",i);                                                
		if( pindex == temp_pindex && detector == 15  ){                                                                                                                                                                                                                  
		    nphe = chkovBank.getInt("nphe",i);                                                                                                                                                                                                                      
		    System.out.println(" >> NPHE " + nphe );                                                                                                                                                                                                                
		}                                           
	    }
	}
	*/


	if( event.hasBank("REC::Cherenkov") ){
	    //System.out.println(" >> HAS CHERENKOV " );
	    DataBank chkovBank = event.getBank("REC::Cherenkov");
	    for( int i = 0; i < chkovBank.rows(); i++ ){
		int pindex =  chkovBank.getShort("pindex",i); 
		// 15 - HTCC
		//16 - LTCC
		int detector =  chkovBank.getByte("detector",i);  
		
		//System.out.println(" >>  REC::CHERENKOV PINDEX " + pindex );
		if( pindex == rec_i && detector == 15 ){		    
		    nphe = chkovBank.getFloat("nphe",i);
		    //System.out.println(" >> NPHE " + nphe );		    
		}
	    }
	}
	return nphe;
       
    }

    public static int getDCSectorR1( DataEvent event, int rec_i ){
 
	int dc_sector_r1 = -1;
	
	if( event.hasBank("REC::Track") && event.hasBank("TimeBasedTrkg::TBCrosses") && event.hasBank("TimeBasedTrkg::TBTracks") ){
	    
	    DataBank recTrack = event.getBank("REC::Track");
 	    DataBank tbcrosses = event.getBank("TimeBasedTrkg::TBCrosses");
	    DataBank tbtracks = event.getBank("TimeBasedTrkg::TBTracks");

	    for( int i = 0; i < recTrack.rows(); i++ ){
		int pindex = recTrack.getShort("pindex",i);
		int detector = recTrack.getByte("detector",i);
		int index = recTrack.getShort("index",i );
		//System.out.println(" >> PINDEX " + pindex  + " REC " + rec_i + " INDEX " +  index );
		if( pindex == rec_i && detector == 6 ){
		    for( int j = 0; j < tbtracks.rows(); j++){
			if( j == index ){
			    int sector_r1 = tbtracks.getInt("sector",j); 
			    dc_sector_r1 = sector_r1;
			    break;
			}
		    }
		}
		
	    }

	}
	return dc_sector_r1;
    
    }

    public static int getDCSectorR2( DataEvent event, int rec_i ){
 
	int dc_sector_r2 = -1;
	
	if( event.hasBank("REC::Track") && event.hasBank("TimeBasedTrkg::TBCrosses") && event.hasBank("TimeBasedTrkg::TBTracks") ){
	    
	    DataBank recTrack = event.getBank("REC::Track");
 	    DataBank tbcrosses = event.getBank("TimeBasedTrkg::TBCrosses");
	    DataBank tbtracks = event.getBank("TimeBasedTrkg::TBTracks");

	    for( int i = 0; i < recTrack.rows(); i++ ){
		int pindex = recTrack.getShort("pindex",i);
		int detector = recTrack.getByte("detector",i);
		int index = recTrack.getShort("index",i );
		//System.out.println(" >> PINDEX " + pindex  + " REC " + rec_i + " INDEX " +  index );
		if( pindex == rec_i && detector == 6 ){
		    for( int j = 0; j < tbtracks.rows(); j++){
			if( j == index ){
			    int sector_r2 = tbtracks.getByte("sector",j);
			    dc_sector_r2 = sector_r2;
			    break;
			}
		    }
		}
		
	    }

	}
	return dc_sector_r2;
    
    }

   public static int getDCSectorR3( DataEvent event, int rec_i ){
 
	int dc_sector_r3 = -1;
	
	if( event.hasBank("REC::Track") && event.hasBank("TimeBasedTrkg::TBCrosses") && event.hasBank("TimeBasedTrkg::TBTracks") ){
	    
	    DataBank recTrack = event.getBank("REC::Track");
 	    DataBank tbcrosses = event.getBank("TimeBasedTrkg::TBCrosses");
	    DataBank tbtracks = event.getBank("TimeBasedTrkg::TBTracks");

	    for( int i = 0; i < recTrack.rows(); i++ ){
		int pindex = recTrack.getShort("pindex",i);
		int detector = recTrack.getByte("detector",i);
		int index = recTrack.getShort("index",i );
		//System.out.println(" >> PINDEX " + pindex  + " REC " + rec_i + " INDEX " +  index );
		if( pindex == rec_i && detector == 6 ){
		    for( int j = 0; j < tbtracks.rows(); j++){
			if( j == index ){
			    int sector_r3 = tbtracks.getByte("sector",j);
			    dc_sector_r3 = sector_r3;
			    break;
			}
		    }
		}
		
	    }

	}
	return dc_sector_r3;
    
    }


    public static double getDCCrossX1( DataEvent event, int rec_i ){

	double dc_cross_1x = -1000;
	

	if( event.hasBank("REC::Track") && event.hasBank("TimeBasedTrkg::TBCrosses") && event.hasBank("TimeBasedTrkg::TBTracks") ){
	    
	    DataBank recTrack = event.getBank("REC::Track");
 	    DataBank tbcrosses = event.getBank("TimeBasedTrkg::TBCrosses");
	    DataBank tbtracks = event.getBank("TimeBasedTrkg::TBTracks");

	    for( int i = 0; i < recTrack.rows(); i++ ){
		int pindex = recTrack.getShort("pindex",i);
		int detector = recTrack.getByte("detector",i);
		int index = recTrack.getShort("index",i );
		//System.out.println(" >> PINDEX " + pindex  + " REC " + rec_i + " INDEX " +  index );
		if( pindex == rec_i && detector == 6 ){
		    for( int j = 0; j < tbtracks.rows(); j++){
			if( j == index ){
			    double c1_x = tbtracks.getFloat("c1_x",j);
			    dc_cross_1x = c1_x;
			    break;
			}
		    }
		}
		
	    }

	}
	return dc_cross_1x;
    
    }

    public static double getDCCrossY1( DataEvent event, int rec_i ){

	double dc_cross_1y = -1000;
	

	if( event.hasBank("REC::Track") && event.hasBank("TimeBasedTrkg::TBCrosses") && event.hasBank("TimeBasedTrkg::TBTracks") ){
	    
	    DataBank recTrack = event.getBank("REC::Track");
 	    DataBank tbcrosses = event.getBank("TimeBasedTrkg::TBCrosses");
	    DataBank tbtracks = event.getBank("TimeBasedTrkg::TBTracks");

	    for( int i = 0; i < recTrack.rows(); i++ ){
		int pindex = recTrack.getShort("pindex",i);
		int detector = recTrack.getByte("detector",i);
		int index = recTrack.getShort("index",i );
		//System.out.println(" >> PINDEX " + pindex  + " REC " + rec_i + " INDEX " +  index );
		if( pindex == rec_i && detector == 6 ){
		    for( int j = 0; j < tbtracks.rows(); j++){
			if( j == index ){
			    double c1_y = tbtracks.getFloat("c1_y",j);
			    dc_cross_1y = c1_y;
			    break;
			}
		    }
		}
		
	    }

	}
	return dc_cross_1y;
    
    }

    ////////////////////////////////////////////////////////
    //TRACK RECONSTRUCTED ONLY WITHIN THIS DETECTOR VOLUME
    public static double getDCCrossX3( DataEvent event, int rec_i ){

	double dc_cross_3x = -1000;
	

	if( event.hasBank("REC::Track") && event.hasBank("TimeBasedTrkg::TBCrosses") && event.hasBank("TimeBasedTrkg::TBTracks") ){
	    
	    DataBank recTrack = event.getBank("REC::Track");
 	    DataBank tbcrosses = event.getBank("TimeBasedTrkg::TBCrosses");
	    DataBank tbtracks = event.getBank("TimeBasedTrkg::TBTracks");

	    for( int i = 0; i < recTrack.rows(); i++ ){
		int pindex = recTrack.getShort("pindex",i);
		int detector = recTrack.getByte("detector",i);
		int index = recTrack.getShort("index",i );
		//System.out.println(" >> PINDEX " + pindex  + " REC " + rec_i + " INDEX " +  index );
		if( pindex == rec_i && detector == 6 ){
		    for( int j = 0; j < tbtracks.rows(); j++){
			if( j == index ){
			    double c3_x = tbtracks.getFloat("c3_x",j);
			    dc_cross_3x = c3_x;
			    break;
			}
		    }
		}
		
	    }

	}
	return dc_cross_3x;
    
    }

    public static double getDCCrossY3( DataEvent event, int rec_i ){

	double dc_cross_3y = -1000;
	

	if( event.hasBank("REC::Track") && event.hasBank("TimeBasedTrkg::TBCrosses") && event.hasBank("TimeBasedTrkg::TBTracks") ){
	    
	    DataBank recTrack = event.getBank("REC::Track");
 	    DataBank tbcrosses = event.getBank("TimeBasedTrkg::TBCrosses");
	    DataBank tbtracks = event.getBank("TimeBasedTrkg::TBTracks");

	    for( int i = 0; i < recTrack.rows(); i++ ){
		int pindex = recTrack.getShort("pindex",i);
		int detector = recTrack.getByte("detector",i);
		int index = recTrack.getShort("index",i );
		//System.out.println(" >> PINDEX " + pindex  + " REC " + rec_i + " INDEX " +  index );
		if( pindex == rec_i && detector == 6 ){
		    for( int j = 0; j < tbtracks.rows(); j++){
			if( j == index ){
			    double c3_y = tbtracks.getFloat("c3_y",j);
			    dc_cross_3y = c3_y;
			    break;
			}
		    }
		}
		
	    }

	}
	return dc_cross_3y;
    
    }



    public static Vector<Double> getDCCrossLocalR1( DataEvent event, int rec_i ){
	Vector<Double> v_temp_local_coord = new Vector<Double>();
	
	if( event.hasBank("REC::Track") && event.hasBank("TimeBasedTrkg::TBCrosses") && event.hasBank("TimeBasedTrkg::TBTracks") ){
	    
	    DataBank recTrack = event.getBank("REC::Track");
 	    DataBank tbcrosses = event.getBank("TimeBasedTrkg::TBCrosses");
	    DataBank tbtracks = event.getBank("TimeBasedTrkg::TBTracks");

	    for( int i = 0; i < recTrack.rows(); i++ ){
		int pindex = recTrack.getShort("pindex",i);
		int detector = recTrack.getByte("detector",i);
		int index = recTrack.getShort("index",i);
		if( pindex == rec_i && detector == 6 ){
		    for( int j = 0; j < tbtracks.rows(); j++ ){
			if( j == index ){
			    int cross1_id = tbtracks.getShort("Cross1_ID",j);
			    for( int k = 0; k < tbcrosses.rows(); k++ ){
				int cross_id = tbcrosses.getShort("id",k);
				if( cross_id == cross1_id ){
				    double cross1_hit_x = tbcrosses.getFloat("x",k);
				    double cross1_hit_y = tbcrosses.getFloat("y",k);				   
				    v_temp_local_coord.add(cross1_hit_x);
				    v_temp_local_coord.add(cross1_hit_y);
				    break;
				}
			    }
			}
		    }
		}
	    }
	}
	else{
	    v_temp_local_coord.add(-10000.0);
	    v_temp_local_coord.add(-10000.0);
	}
	return v_temp_local_coord;
    }

    public static Vector<Double> getDCTrajR1(DataEvent event, int rec_i ){
	Vector<Double> dc_traj = new Vector<Double>();

	if( event.hasBank("REC::Traj") ){
	    DataBank trajBank = event.getBank("REC::Traj");
	    for( int i = 0; i < trajBank.rows(); i++ ){
		int detID = trajBank.getShort("detId",i);
		int pindex = trajBank.getInt("pindex",i);
		if( detID == 12 && pindex == rec_i ){
		    double cx = trajBank.getFloat("x",i);
		    double cy = trajBank.getFloat("y",i);
		    double cz = trajBank.getFloat("z",i);

		    dc_traj.add(cx);
		    dc_traj.add(cy);
		    dc_traj.add(cz);
		}		
	    }
	}
	if( dc_traj.size() == 0 ){
	    dc_traj.add(-1000.0);
	    dc_traj.add(-1000.0);
	    dc_traj.add(-1000.0);
	}
	//	System.out.println(" >> DC TRAJ HIT " + dc_traj);
	return dc_traj;
    }
    
    public static Vector<Double> getDCTrajR2(DataEvent event, int rec_i ){
	Vector<Double> dc_traj = new Vector<Double>();

	if( event.hasBank("REC::Traj") ){
	    DataBank trajBank = event.getBank("REC::Traj");
	    for( int i = 0; i < trajBank.rows(); i++ ){
		int detID = trajBank.getShort("detId",i);
		int pindex = trajBank.getInt("pindex",i);
		if( detID == 24 && pindex == rec_i ){
		    double cx = trajBank.getFloat("x",i);
		    double cy = trajBank.getFloat("y",i);
		    double cz = trajBank.getFloat("z",i);

		    dc_traj.add(cx);
		    dc_traj.add(cy);
		    dc_traj.add(cz);
		}		
	    }
	}
	if( dc_traj.size() == 0 ){
	    dc_traj.add(-1000.0);
	    dc_traj.add(-1000.0);
	    dc_traj.add(-1000.0);
	}
	//System.out.println(" >> DC TRAJ HIT " + dc_traj);
	return dc_traj;
    }


    public static Vector<Double> getDCTrajR3(DataEvent event, int rec_i ){
	Vector<Double> dc_traj = new Vector<Double>();

	if( event.hasBank("REC::Traj") ){
	    DataBank trajBank = event.getBank("REC::Traj");
	    for( int i = 0; i < trajBank.rows(); i++ ){
		//System.out.println(">> in traj BANK " + i );
		int detID = trajBank.getShort("detId",i);
		int pindex = trajBank.getInt("pindex",i);
		//System.out.println(" >> DETID " + detID + " PINDEX " + pindex + " RECI " + rec_i);
		if( detID == 36 && pindex == rec_i ){
		    double cx = trajBank.getFloat("x",i);
		    double cy = trajBank.getFloat("y",i);
		    double cz = trajBank.getFloat("z",i);
		    dc_traj.add(cx);
		    dc_traj.add(cy);
		    dc_traj.add(cz);
		    //System.out.println(" RESULT " + dc_traj );
		}		
	    }
	    if( dc_traj.size() == 0 ){
		dc_traj.add(-1000.0);
		dc_traj.add(-1000.0);
		dc_traj.add(-1000.0);
	    }	    
	}
	//System.out.println(" >> DC TRAJ HIT " + dc_traj);
	return dc_traj;
    }


    public static int getDCSector( DataEvent event, int rec_i ){

	int sector = -1;
	if( event.hasBank("REC::Track") ){
	    DataBank recTrack = event.getBank("REC::Track");
	    for( int i = 0; i < recTrack.rows(); i++ ){
		int pindex = recTrack.getShort("pindex",i);
		int detector = recTrack.getByte("detector",i);
		
		if( pindex == rec_i && detector == 6 ){ //6 is DC set
		    sector = recTrack.getInt("sector",i);
		    break;
		}
	    }
	}
	return sector;
    }

    
    public static int getDCTrajSect(DataEvent event, int rec_i, int region ){
	int sector = -1;

	if( event.hasBank("REC::Traj") && event.hasBank("REC::Track") ){
	    //DataBank trajBank = event.getBank("REC::Traj");
	    DataBank trackBank = event.getBank("REC::Track");

	    for( int j = 0; j < trackBank.rows(); j++ ){
		int pindex  = trackBank.getInt("pindex",j);
		int det = trackBank.getInt("detector",j);
		int sect = trackBank.getInt("sector",j);
		if( pindex == rec_i && det == 6 ){
		    sector = sect;
		    break;
		}
	    }

	    /*for( int i = 0; i < trajBank.rows(); i++ ){
		int detID = trajBank.getShort("detId",i);
		if( detID == region ){ // 12, 24, 36
		    double x = trajBank.getFloat("x",i);
		    double y = trajBank.getFloat("y",i);
		    double z = trajBank.getFloat("z",i);

		    double angle = Math.toDegrees(Math.atan2(y, x));
		    if( angle < 0.0 ) angle+=360.0;	 

		    //System.out.println(" >> SECT CALC " + angle);

		    if ( angle <= 30.0 && angle >= 0.0 ){
			sector = 0;
		    }		    
		    else if ( angle <= 90.0 && angle > 30.0 ){
			sector = 1;
		    }
		    else if ( angle <= 150.0 && angle > 90.0 ){
			sector = 2;
		    }
		    else if ( angle <= 210.0 && angle > 150.0 ){
			sector = 3;
		    }
		    else if ( angle <= 270.0 && angle > 210.0  ){
			sector = 4;
		    }
		    else if ( angle < 330.0 && angle > 270.0 ){
			sector = 5;
		    }
		    else if( angle >= 330 && angle <= 360.0 ){
			sector = 0;
		    }
		}		
	    }
	}
	
	    */
	}
	//System.out.println(" >> sector " + sector );
	return sector;
    }
    
    public static Vector<Double> getDCCrossLocalR2( DataEvent event, int rec_i ){
	Vector<Double> v_temp_local_coord = new Vector<Double>();
	
	if( event.hasBank("REC::Track") && event.hasBank("TimeBasedTrkg::TBCrosses") && event.hasBank("TimeBasedTrkg::TBTracks") ){
	    
	    DataBank recTrack = event.getBank("REC::Track");
 	    DataBank tbcrosses = event.getBank("TimeBasedTrkg::TBCrosses");
	    DataBank tbtracks = event.getBank("TimeBasedTrkg::TBTracks");

	    for( int i = 0; i < recTrack.rows(); i++ ){
		int pindex = recTrack.getShort("pindex",i);
		int detector = recTrack.getByte("detector",i);
		int index = recTrack.getShort("index",i);
		if( pindex == rec_i && detector == 6 ){
		    for( int j = 0; j < tbtracks.rows(); j++ ){
			if( j == index ){
			    int cross2_id = tbtracks.getShort("Cross2_ID",j);
			    for( int k = 0; k < tbcrosses.rows(); k++ ){
				int cross_id = tbcrosses.getShort("id",k);
				if( cross_id == cross2_id ){
				    double cross2_hit_x = tbcrosses.getFloat("x",k);
				    double cross2_hit_y = tbcrosses.getFloat("y",k);				   
				    v_temp_local_coord.add(cross2_hit_x);
				    v_temp_local_coord.add(cross2_hit_y);
				    break;
				}
			    }
			}
		    }
		}
	    }
	}
	else{
	    v_temp_local_coord.add(-10000.0);
	    v_temp_local_coord.add(-10000.0);
	}
	return v_temp_local_coord;
    }

    public static Vector<Double> getDCCrossLocalR3( DataEvent event, int rec_i ){
	Vector<Double> v_temp_local_coord = new Vector<Double>();
	
	if( event.hasBank("REC::Track") && event.hasBank("TimeBasedTrkg::TBCrosses") && event.hasBank("TimeBasedTrkg::TBTracks") ){
	    
	    DataBank recTrack = event.getBank("REC::Track");
 	    DataBank tbcrosses = event.getBank("TimeBasedTrkg::TBCrosses");
	    DataBank tbtracks = event.getBank("TimeBasedTrkg::TBTracks");

	    for( int i = 0; i < recTrack.rows(); i++ ){
		int pindex = recTrack.getShort("pindex",i);
		int detector = recTrack.getByte("detector",i);
		int index = recTrack.getShort("index",i);
		if( pindex == rec_i && detector == 6 ){
		    for( int j = 0; j < tbtracks.rows(); j++ ){
			if( j == index ){
			    int cross3_id = tbtracks.getShort("Cross3_ID",j);
			    for( int k = 0; k < tbcrosses.rows(); k++ ){
				int cross_id = tbcrosses.getShort("id",k);
				if( cross_id == cross3_id ){
				    double cross3_hit_x = tbcrosses.getFloat("x",k);
				    double cross3_hit_y = tbcrosses.getFloat("y",k);
				    //System.out.println(" >> LOCAL HIT COORDINATE IS " + cross3_hit_x );
				    v_temp_local_coord.add(cross3_hit_x);
				    v_temp_local_coord.add(cross3_hit_y);
				    break;
				}
			    }
			}
		    }
		}
	    }
	}
	else{
	    v_temp_local_coord.add(-10000.0);
	    v_temp_local_coord.add(-10000.0);
	}
	//System.out.println(" >> " + v_temp_local_coord.size() );
	return v_temp_local_coord;
    }

    public static ArrayList getFTOFLayerInfo( DataEvent event, int rec_i, int layer ){
	
	ArrayList<Double> ftof_info = new ArrayList<Double>();
	double ftof_e = -10.0;
	double ftof_path = -10.0;
	double ftof_tof = -10.0;

	if( event.hasBank("REC::Scintillator") ){
	    DataBank scintBank = event.getBank("REC::Scintillator"); 
	    for( int i = 0; i < scintBank.rows(); i++ ){
		int pindex = scintBank.getShort("pindex",i);
		if( pindex == rec_i ){		    
		    int scint_sector = scintBank.getInt("sector",i) - 1;
		    int scint_detector = scintBank.getByte("detector",i);
		    int scint_layer = scintBank.getByte("layer",i);
		    
		    if( scint_detector == 12 && scint_layer == layer ){
			ftof_e  = scintBank.getFloat("energy",i)/100.0;
			ftof_path = scintBank.getFloat("path",i);
			ftof_tof = scintBank.getFloat("time",i);
			break;
		    }			   
		}
	    }
	}
	ftof_info.add(ftof_e);
	ftof_info.add(ftof_path);
	ftof_info.add(ftof_tof);
	return ftof_info;

    }

    public static double getFTOFLayerEnergy( DataEvent event, int rec_i, int layer ){
	
	double ftof_e = -10.0;
	if( event.hasBank("REC::Scintillator") ){
	    DataBank scintBank = event.getBank("REC::Scintillator"); 
	    for( int i = 0; i < scintBank.rows(); i++ ){
		int pindex = scintBank.getShort("pindex",i);
		if( pindex == rec_i ){		    
		    int scint_sector = scintBank.getInt("sector",i) - 1;
		    int scint_detector = scintBank.getByte("detector",i);
		    int scint_layer = scintBank.getByte("layer",i);
		    
		    if( scint_detector == 12 && scint_layer == layer ){
			ftof_e  = scintBank.getFloat("energy",i)/100.0;
			break;
		    }			   
		}
	    }
	}
	return ftof_e;
    }

    public static double getFTOFPathLength( DataEvent event, int rec_i ){
	double path_length = -1000.0;

	if( event.hasBank("CTOF::hits") && event.hasBank("REC::Scintillator") ){
	    DataBank scintBank = event.getBank("REC::Scintillator");
	    DataBank ctofhits = event.getBank("CTOF::hits");
	    
	    //scintBank.show();
	    //ctofhits.show();

	    for( int i = 0; i < scintBank.rows(); i++ ){
		int pindex = scintBank.getShort("pindex",i);
		int scint_layer = scintBank.getInt("layer",i);
		int scint_detector = scintBank.getByte("detector",i); 
		int index = scintBank.getShort("index",i);
		
		if( pindex == rec_i && scint_detector == 12){
		    System.out.println(" >> pindex" + pindex );
		    
		    for( int j = 0; j < ctofhits.rows(); j++ ){
			if( j == index ){
			    System.out.println(" index " + j );
			    double temppathlength = ctofhits.getFloat("pathLengthThruBar",j);
			    path_length = temppathlength; 
			    System.out.println("pathlength in detector class " + temppathlength );
			    break;
			}		    
		    }
		}
	    }
	}
	return path_length;
    }
       
}
    
