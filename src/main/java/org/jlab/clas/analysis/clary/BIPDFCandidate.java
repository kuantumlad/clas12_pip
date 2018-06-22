package org.jlab.clas.analysis.clary;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

import java.io.*;


interface BIPDFCandidate {

    public double likelihood( DataEvent tempevent, int rec_i );

}
