#!/bin/bash

#mvn package

echo " BEGIN electronPID ANALYSIS for $1 $2 $3 $4"
/home/bclary/CLAS12/phi_analysis/v3/v2/v1/./electronPID $1 $2 $3 $4

echo " COMPLETE "

echo " MERGING HISTOGRAMS NOW "
#inputfile="/w/hallb-scifs17exp/clas12/bclary/pics/pid_clary/h_"$2"_"$4"_el_all.hipo"
#outputfile="/w/hallb-scifs17exp/clas12/bclary/pics/pid_clary/h_"$2"_"$4"thread-*_el_pid_clary.hipo"

#echo $inputfile
#echo $outputfile

#/w/hallb-scifs17exp/clas12/bclary/extras/coatjava_5b.3.3/bin/hadd $inputfile $outputfile
