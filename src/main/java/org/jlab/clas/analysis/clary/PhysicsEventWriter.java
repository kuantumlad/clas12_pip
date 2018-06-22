package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

import org.jlab.clas.analysis.clary.PhysicsEvent;

import org.jlab.jnp.hipo.data.HipoEvent;
import org.jlab.jnp.hipo.data.HipoGroup;
import org.jlab.jnp.hipo.io.HipoReader;
import org.jlab.jnp.hipo.io.HipoWriter;
import org.jlab.jnp.hipo.schema.SchemaFactory;

public class PhysicsEventWriter{

    HipoWriter writer = new HipoWriter();
    BufferedWriter writertxt;
    String output_location = "/volatile/halla/sbs/bclary/clas12Analysis/clary_phiPID";
    DecimalFormat numberFormat = new DecimalFormat("#.0000");

    //Constructor 	    
    public PhysicsEventWriter( HipoWriter tempwriter, String tempanalysistype, int tempfile_num ){	
	
	//HipoWriter tempwriter = new HipoWriter(tempinname);
	writer = tempwriter;

	///ADD TARGET POLARIZATION OF BEAM POLARIZATION TO RUNPROP BANK FOR REAL DATA LATER
	writer.defineSchema("RunProperties",00,"beam_energy/D");
	writer.defineSchema("final_momenta",1,"el_p/D:pr_p/D:kp_p/D:km_p/D");
	writer.defineSchema("el_properties",2,"el_px/D:el_py/D:el_pz/D:el_E/D");
	writer.defineSchema("pr_properties",3,"pr_px/D:pr_py/D:pr_pz/D:pr_E/D");
	writer.defineSchema("kp_properties",4,"kp_px/D:kp_py/D:kp_pz/D:kp_E/D");
	writer.defineSchema("km_properties",5,"km_px/D:km_py/D:km_pz/D:km_E/D");
	writer.defineSchema("PhysicsEvent",6,"topology/I:q2/D:xB/D:t/D:w2/D:cm_theta/D:cm_phi/D");

	//CREATE HIPOOUTPUT FILE
	writer.open(String.format("%s/%s_phi_%d.hipo",output_location,tempanalysistype, tempfile_num));
	writer.setCompressionType(2);

	//CREATE TXT OUTPUT FILE
	String output_name = output_location +"/" + tempanalysistype + "_phi_" + tempfile_num + ".txt";
	try{
	writertxt = new BufferedWriter( new FileWriter(output_name) );
	}
	catch ( IOException e ){
	    System.out.println(">> ERROR " );
	}


    }

    public void writeTxtHeader(){

	try{
	    //USER DEFINED (SHOULD BE DETERMINED FROM A TEXTFILE OR EXTERNAL SOURCE)
	    writertxt.write("topology:q2:xb:t:w2:cm_theta:cm_phi:el_p:el_theta:el_phi:pr_p:pr_theta:pr_phi:kp_p:kp_theta:kp_phi:km_p:km_theta:km_phi" + "\n");
	}
	catch( IOException e){
	    System.out.println(">> ERROR SETTING HEADER");
	}
	
    }

    public void writePhysicsEvent(boolean filetype, PhysicsEvent phyev){

	//TRUE == HIPO 
	//FALSE == TXT
	if( filetype == true ){
	    writeToHipo(phyev);
	}
	else if( filetype == false ){
	    writeToTxt(phyev);	
	}
    }
    
    public void writeToHipo( PhysicsEvent phyev ){

	//System.out.println(">> WRITING EVENT TO OUTPUT FORMAT");
	//USER SPECIFIC
	HipoGroup bank = writer.getSchemaFactory().getSchema("PhysicsEvent").createGroup(1);
	HipoGroup bank2 = writer.getSchemaFactory().getSchema("RunProperties").createGroup(1);
	HipoGroup final_state = writer.getSchemaFactory().getSchema("final_momenta").createGroup(1);
	HipoGroup el_bank = writer.getSchemaFactory().getSchema("el_properties").createGroup(1);
	HipoGroup pr_bank = writer.getSchemaFactory().getSchema("pr_properties").createGroup(1);
	HipoGroup kp_bank = writer.getSchemaFactory().getSchema("kp_properties").createGroup(1);
	HipoGroup km_bank = writer.getSchemaFactory().getSchema("km_properties").createGroup(1);	

	//System.out.println("GETTING NODES");
	el_bank.getNode("el_px").setDouble(0,phyev.lv_el.px());
 	el_bank.getNode("el_py").setDouble(0,phyev.lv_el.py());
 	el_bank.getNode("el_pz").setDouble(0,phyev.lv_el.pz());
 	el_bank.getNode("el_E").setDouble(0,phyev.lv_el.e());

 	pr_bank.getNode("pr_px").setDouble(0,phyev.lv_pr.px());
 	pr_bank.getNode("pr_py").setDouble(0,phyev.lv_pr.py());
 	pr_bank.getNode("pr_pz").setDouble(0,phyev.lv_pr.pz());
 	pr_bank.getNode("pr_E").setDouble(0,phyev.lv_pr.e());

 	kp_bank.getNode("kp_px").setDouble(0,phyev.lv_kp.px());
 	kp_bank.getNode("kp_py").setDouble(0,phyev.lv_kp.py());
 	kp_bank.getNode("kp_pz").setDouble(0,phyev.lv_kp.pz());
 	kp_bank.getNode("kp_E").setDouble(0,phyev.lv_kp.e());

 	km_bank.getNode("km_px").setDouble(0,phyev.lv_km.px());
 	km_bank.getNode("km_py").setDouble(0,phyev.lv_km.py());
 	km_bank.getNode("km_pz").setDouble(0,phyev.lv_km.pz());
 	km_bank.getNode("km_E").setDouble(0,phyev.lv_km.e());
	
	bank.getNode("topology").setInt(0,phyev.topology);
	bank.getNode("q2").setDouble(0,phyev.q2);
	bank.getNode("xB").setDouble(0,phyev.xB);
	bank.getNode("t").setDouble(0,phyev.t);
	bank.getNode("w2").setDouble(0,phyev.w2);
	bank.getNode("cm_theta").setDouble(0,phyev.cm_theta);
	bank.getNode("cm_phi").setDouble(0,phyev.cm_phi);
       	
	//System.out.println("CREATING EVENT");
	HipoEvent event = writer.createEvent();
	//System.out.println("WRITING GROUP");
	event.writeGroup(bank);
	event.writeGroup(el_bank);
	event.writeGroup(pr_bank);
	event.writeGroup(kp_bank);
	event.writeGroup(km_bank);
	event.writeGroup(final_state);
	//System.out.println("WRITING EVENT");
	writer.writeEvent(event);
	//System.out.println("WRITTEN");

    }

    public void writeToTxt( PhysicsEvent phyev ){
	//if( writetxt ){
	    //WRITE TO TEXT FILE ALL NECESSARY INFORMATION		    
	    for( double kinematic : phyev.l_physev ){
		//System.out.print(numberFormat.format(kinematic) + " ");	
		try{
		    //double temp = numberFormat.format(Double.parseDouble(kinematic));
		    writertxt.write(Double.toString(kinematic) + " ");
		    //System.out.println(" >> " + Double.toString(kinematic) );
		}
		catch( IOException e){
		    System.out.println(">> ERROR WITH WRITING KINEMATICS TO FILE");
		}
		
	    }
	    for( double mc_kinematic : phyev.l_mcphysev ){
		//System.out.print(numberFormat.format(kinematic) + " ");	
		try{
		    //double temp = numberFormat.format(Double.parseDouble(kinematic));
		    writertxt.write(Double.toString(mc_kinematic) + " ");
		}
		catch( IOException e){
		    System.out.println(">> ERROR WITH WRITING MC KINEMATICS TO FILE");
		}
	    }
	    try{
		writertxt.write("\n");    
	    }
	    catch( IOException e){	   
		System.out.println(">> ERROR WITH NEW LINE");
	    }
    }
    	
    public void closeTxtFile(){
	try{
	    writertxt.close();
	}
	catch( IOException e ){
	    System.out.println(">> ERROR CLOSING FILE");  
	}
	
    }
}



