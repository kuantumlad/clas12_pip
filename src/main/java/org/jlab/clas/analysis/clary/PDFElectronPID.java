
package org.jlab.clas.analysis.clary;

import org.jlab.clas.analysis.clary.PDFElectronBeta;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import org.jlab.io.hipo.HipoDataEvent;

import java.util.*;
import java.io.*;

public class PDFElectronPID implements IParticleIdentifier{

    Vector<BIPDFCandidate> v_cuts = new Vector<BIPDFCandidate>();

    PDFElectronBeta pdf_el_beta = new PDFElectronBeta();

    public PDFElectronPID() {
    }

    public void initializeCuts(){

	v_cuts.add(pdf_el_beta);

    }

    public boolean processCuts( DataEvent tempevent, int rec_i ){

	boolean result = true;
	for( BIPDFCandidate cut : v_cuts ){
            /*
	    if( cut.candidate( tempevent, rec_i ) < 0.9 ){
		result = false;
		break;
	    }
            */
	}
	return result;
    }



     public HashMap getResult( DataEvent event ){
	 HashMap<Boolean,Integer> m_el_final = new HashMap<Boolean,Integer>();
	 return m_el_final;
    }

}
