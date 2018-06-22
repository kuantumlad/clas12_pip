package org.jlab.clas.analysis.clary; 

import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;

import java.util.*;
import java.io.*;


public class BEventInfo{

    public Map<Integer, List<Integer> > recScintBankMap = new HashMap<Integer, List<Integer> >();
    public Map<Integer, List<Integer> > recCalBankMap = new HashMap<Integer, List<Integer> >();

    public DataBank recBank = null;    
    public DataBank scintBank = null;    
    public DataBank calBank = null;    
    public DataEvent bevEvent = null;

    public double start_time = -1000.0;


    public BEventInfo(){
	

    }

    public void setRECScintInfo(){
	

    }

    public void clearBEventInfo(){

	recScintBankMap.clear();
	recCalBankMap.clear();

    }

    


}
