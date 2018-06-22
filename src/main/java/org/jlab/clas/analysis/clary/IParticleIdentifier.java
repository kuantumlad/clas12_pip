package org.jlab.clas.analysis.clary;

import java.util.*;
import java.io.*;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;
import org.jlab.jnp.hipo.schema.SchemaFactory;
import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.clas.physics.Particle;  

interface IParticleIdentifier {

    //SchemaFactory factory;
    //Schema pid_schema = new Schema("PID::pid");

    //public void setParticleIdentifier(){

	//factory  = new SchemaFactory();
	//factory.initFromDirectory("CLAS12DIR","etc/bankdefs/hipo");
	//pid_schema.addEntry("el_pid",1,HipoNodeType.INT);
	//pid_schema.addEntry("pr_pid",2,HipoNodeType.INT);
	//pid_schema.addEntry("kp_pid",3,HipoNodeType.INT);
	//pid_schema.addEntry("km_pid",4,HipoNodeType.INT);

    //}

    public HashMap getResult( DataEvent event );
    //return false;
    //}
    //public Particle getResult( DataEvent event );

    //    public void getResult( DataEvent event, int rec_i );
    
}
