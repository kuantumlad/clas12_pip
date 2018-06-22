package org.jlab.clas.analysis.clary;
import com.google.gson.*;

import org.jlab.clas.analysis.clary.RunParameters;
import java.io.*;
import java.util.*;

public class CutConfigInfo{

    List<String> el_cuts;
    List<String> pr_cuts;
    List<String> kp_cuts;
    List<String> km_cuts;

    ///////////////////////////////
    //ADD OTHER PARTICLE CUTS HERE
    //WITH GETTERS

    public List<String> getElectronCutList(){
	return el_cuts;       
    }

    public List<String> getProtonCutList(){
	return pr_cuts;       
    }

    public List<String> getKaonPlusCutList(){
	return kp_cuts;       
    }

    

}
