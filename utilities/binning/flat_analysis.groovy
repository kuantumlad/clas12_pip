import java.io.*;
import java.util.*;

import org.jlab.io.hipo.HipoDataSource;
import org.jlab.jnp.hipo.data.HipoEvent;
import org.jlab.jnp.hipo.data.HipoGroup;
import org.jlab.jnp.hipo.io.HipoReader;
import org.jlab.jnp.hipo.io.HipoWriter;
import org.jlab.jnp.hipo.schema.SchemaFactory;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import org.jlab.groot.data.H1F;

System.out.println(" FLAT GEN ANALYSIS " );

String fname = args[0];
HipoDataSource reader = new HipoDataSource();
reader.open(fname);

while(reader.hasEvent()){

	DataEvent event = reader.getNextEvent();
	event.show();

	if( event.hasBank("REC::Particle") ){
          pbank = event.getBank("REC::Particle");	
	  pbank.show();
	  System.out.println(" Number of entries to process is " + pbank.rows() );
	  for( int i = 0; i < pbank.rows(); i++){
	    px = pbank.getFloat("px",i);
	    py = pbank.getFloat("py",i);
	    pz = pbank.getFloat("pz",i);
	    pid = pbank.getInt("pid",i);
	    charge = 0;//pbank.getInt("charge",i);
	    p = Math.sqrt( px*px + py*py + pz*pz );
	    //System.out.println(">> " + pid + " " + charge + " << "  + px + " " + py + " " + pz + " " + p );


	}

	    }
 }


reader.close();