import java.io.*;
import java.util.*;
import org.jlab.io.hipo.*;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.io.hipo.HipoDataSource;        

public class HippoFilter{


    public static void main(String[] args) {

	String fin_dir = args[0];
	String fout_dir = args[1];
	String run_number = args[2];
	int max_skim = 1; //235;

	System.out.println(" >> SKIMMING FILES FOR ANALYSIS " );
	System.out.println(" >> SKIMMING FILES LOCATED IN " + fin_dir );
	
	for( int f_num = 146; f_num < max_skim; f_num++ ){
	    String fin_name = fin_dir + "out_clas_00" + run_number + ".evio." + Integer.toString(f_num) + ".hipo";
	    String fout_name = fout_dir + "skim_out_clas_00" + run_number + ".evio." + Integer.toString(f_num) + ".hipo";
	    File fIn = new File(fin_name);

	    if( fIn.exists() ){
		System.out.println(" >> FILE EXISTS " );
		skimFile( fin_name, fout_name );		
	    }
	}
	    
	System.out.println(" >> COMPLETED SKIMMING " );
    }

    public static void skimFile( String fin_name, String fout_name ){

	System.out.println(" >> READING FILE " + fin_name + " ---> WRITING TO " + fout_name );

	HipoDataSource reader = new HipoDataSource();
	HipoDataSync writer = reader.createWriter();
	writer.open(fout_name);
	
	reader.open(fin_name);
	int evnum=0;

	while(reader.hasEvent()){
	    DataEvent event = reader.gotoEvent(evnum);
	    evnum++;                           
	    if( event.hasBank("REC::Event") && event.hasBank("REC::Particle") && event.hasBank("REC::Calorimeter") ){		                                      
		System.out.println("here");
		writer.writeEvent(event);
		event.show();
	    }
	}
	reader.close();             
	writer.close();



    }

}
