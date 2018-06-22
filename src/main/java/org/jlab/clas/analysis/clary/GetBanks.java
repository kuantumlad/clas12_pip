package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

public class GetBanks {

    //Vector pr_indices = new Vector();

    public List particleIndices(DataEvent tempevent){
	List el_indices = new ArrayList();

	if( tempevent.hasBank("REC::Particle") && tempevent.getBank("REC::Particle").rows()>0 ){	    
	    DataBank recBank = tempevent.getBank("REC::Particle");
	    for( int i = 0; i < tempevent.getBank("REC::Particle").rows(); i++){
		int pid = recBank.getInt("pid",0);
		//if( pid == 11) { el_indices.add(i); System.out.println(" >> el index " + i );}
		//if( pid == 2212 ){ pr_indices.add(i); System.out.println(" >> pr index " + i );}
	    }
	}
	else{
	    el_indices.add(-1);// = null;
	}
	//System.out.println(el_indices.size());
	return el_indices;
    }

    public void Loop(){
	
	/*	HipoDataSource reader = new HipoDataSource();
	while(reader.hasEvent()){
	    DataEvent event  = reader.getNextEvent();
	    String banker = "REC::Calorimeter";
	    List partlist = particleIndices(event);
	    TestBank(event, banker, partlist );
		     
	}	
	*/

    }

    public void TestBank(DataEvent event, String tempbankname, List tempindex ){
	/*	System.out.println(">> test for bank  " + tempbankname);
	if( event.hasBank(tempbankname) ){
	    System.out.println("has bank");   
	    // int index = 0;
	    System.out.println(">> size " +tempindex.size());
	    for ( int index : tempindex ){
		System.out.println(tempindex.get(index));
		DataBank dbank = event.getBank(tempbankname);
		for( int i = 0; i < dbank.rows(); i++ ){
		    int partindex = dbank.getInt("pindex",i);
		    if( partindex == index ){
			System.out.println("looks like an electron... getting hit position" );
			float x_hit = dbank.getFloat("x",i);
			float y_hit = dbank.getFloat("y",i);
			float z_hit = dbank.getFloat("z",i);
			System.out.println(">> " + x_hit + " " + y_hit + " " + z_hit );		
		    }
		}
	    }
	    
	    }*/
    }

   
}

