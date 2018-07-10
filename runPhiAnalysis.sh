#!/bin/bash

echo " >> BEGIN SCRIPT FOR ANAYLSIS " 

echo " >> RUN " $1
echo " >> STARTING FILE " $2
echo " >> ENDING FILE " $3
echo " >> DATA TYPE " $4
echo " >> FILE LOCATION " $5
echo " >> FILE PREFIX " $6

n_start=$2
n_end=$3

shift=$((n_end-n_start))
echo " >> SHIFT IS " $shift 

file_loc='MACHINE'

lim=1
i=0
while [ $i -lt $lim ]
do
echo $n_start $n_end

/home/bclary/CLAS12/phi_analysis/v3/v2/v1/./electronPID $file_loc $1 $n_start $n_end $4 $5 $6

n_start=$((n_end+1))
n_end=$((n_end+shift))
i=$((i+1))
done

# ./electronPID MACHINE 4013 0 200 DATA /run/media/sdiehl/easystore1/brandon/ skim_clas_004013.evio.
