package org.jlab.clas.analysis.clary;

import java.io.*;

import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;

interface BICandidate{

    public boolean candidate( DataEvent tempdevent, int index);


}
