package org.jlab.clas.analysis.clary;

import java.io.*;
import java.util.regex.*;

public class FileManager {

    public int FileRun( String fname ){
	System.out.println("File Name " + fname );
	String regex = "\\d+";
	
	Matcher matcher = Pattern.compile(regex).matcher((CharSequence)fname);
	int run = 0;
	while(matcher.find()){
	    System.out.println(" >> " + matcher.group() );
	    run = Integer.parseInt(matcher.group());

	}
	return run;
    }

}


    
