package org.jlab.clas.analysis.clary;

import java.io.*;

public class CoolText {

    public static final String ANSI_RESET = "\u001B[0m";

    //////////////////////////////////////////////////////////
    //COLOR THE TEXT
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    ////////////////////////////////////////////////////////////////
    //COLOR THE TEXT BACKGROUND
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public void printToScreen( String tempstring, String tempcolor ){

	String color = printColor( tempcolor );
	System.out.println( color + tempstring + ANSI_RESET );

    }

    public String printColor( String tempcolor ){
	String color_choice;
	if( tempcolor == "red" ){
	    color_choice = ANSI_RED;
	}
	else if( tempcolor == "yellow" ){
	    color_choice = ANSI_YELLOW;
	}
	else if( tempcolor == "green" ){
	    color_choice = ANSI_GREEN;
	}
	else if( tempcolor == "blue" ){
	    color_choice = ANSI_BLUE;
	}
	else if( tempcolor == "white" ){
	    color_choice = ANSI_WHITE;
	}
	else{
	    System.out.println(">> PLEASE ENTER A LISTED COLOR ");
	    color_choice = ANSI_BLACK;
	}
	return color_choice;
    }
    
    public String printBGColor( String tempcolor ){

	String bgcolor_choice;
	if( tempcolor == "red" ){
	    bgcolor_choice = ANSI_RED;
	}
	else if( tempcolor == "yellow" ){
	    bgcolor_choice = ANSI_YELLOW;
	}
	else if( tempcolor == "green" ){
	    bgcolor_choice = ANSI_GREEN;
	}
	else if( tempcolor == "white" ){
	    bgcolor_choice = ANSI_WHITE;
	}
	else{
	    System.out.println(">> PLEASE ENTER A LISTED COLOR ");
	    bgcolor_choice = " ";
	}	
	return bgcolor_choice;
    }
	
}
