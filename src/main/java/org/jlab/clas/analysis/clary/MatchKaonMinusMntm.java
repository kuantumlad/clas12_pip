package org.jlab.clas.analysis.clary;

import org.jlab.clas.analysis.clary.BICandidate;

import java.io.*;
import org.jlab.io.base.DataEvent;
import org.jlab.io.base.DataBank;


class MatchKaonMinusMntm implements BICandidate2 {
    public boolean candidate2( DataEvent tempdevent, int index, int rec_i ){
	System.out.println("Applying Kp Mntm Cuts" );

	return true;
    }
}
