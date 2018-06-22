import org.jlab.io.base.DataEvent;
import org.jlab.io.hipo.HipoDataSource;

import java.io.*;


String fname = args[0];
HipoDataSource reader = new HipoDataSource();
System.out.println(" >> READER HAS EVENT " );
reader.open(fname);
System.out.println(" >> READER HAS OPENED FILE " );

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
	    charge = pbank.getInt("charge",i);
	    p = Math.sqrt( px*px + py*py + pz*pz );
	    System.out.println(">> " + pid + " " + charge + " << "  + px + " " + py + " " + pz + " " + p );


	}

	    }
 }


reader.close();	  

