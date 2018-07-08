#!/bin/bash

#mvn package

echo " BEGIN electronPID ANALYSIS for $1 $2 $3 $4"

# $1 = PROCESSING TYPE - LOOK INSIDE phiAnalysis FOR MORE
# $2 = RUN NUMBER
# $3 = NUMBER OF FILES TO PROCESS
# $4 = DATA OR SIM
# $5 = INPUT FILE PATH
# $6 = FILE PREFIX

fix_files=100
num_jobs=$(($3/fix_files))
min_jobs=1 
pid_files='/home/bclary/CLAS12/pics/pid_clary/'

echo $num_jobs " " $min_jobs

if [ $num_jobs -lt $min_jobs ]
then
    num_jobs=1
    fix_files=1
fi
#else
    

i=0
j=0
echo ">> CREATING " $num_jobs 
while [ $i -lt $num_jobs ]
do
    echo " ITERATION " $i "->> SETTINGS: PROCESSING " $1 " >>  RUN " $2 " >> FIXED FILES " $fix_files " >> TYPE " $4 " >> FILE PATH " $5 " >> FILE PREFIX " $6 " >> SHIFTING BY " $j
    /home/bclary/CLAS12/phi_analysis/v3/v2/v1/./electronPID $1 $2 $fix_files $4 $5 $6 $j
    j=$((j+fix_files))
    el_dir_name='h_el_'$2'_'$4'_pid_clary_job_'$i'/'
    pr_dir_name='h_pr_'$2'_'$4'_pid_clary_job_'$i'/'
    kp_dir_name='h_kp_'$2'_'$4'_pid_clary_job_'$i'/'

    #mkdir $pid_files$el_dir_name
    #mkdir $pid_files$pr_dir_name
    #mkdir $pid_files$kp_dir_name

    k=0
    n_threads=4
    while [ $k -lt $n_threads ]
    do
	mv $pid_files'h_'$2'_'$4'_thread-'$k'_el_pid_clary.hipo' $pid_files$el_dir_name
	mv $pid_files'h_'$2'_'$4'_thread-'$k'_proton_pid_clary.hipo' $pid_files$pr_dir_name
	mv $pid_files'h_'$2'_'$4'_thread-'$k'_kaonP_pid_clary.hipo' $pid_files$kp_dir_name
	k=$((k+1))
    done

    #echo ' >> HADD NOW FOR ELECTRON PROTON AND KAON FILES '
    #echo ' >> ELECTRON FILES: '$pid_files$el_dir_name'h_'$2'_'$4'_el_pid_clary_job_'$i'.hipo' $pid_files$el_dir_name'h_'$2'_'$4"_thread-0_el_pid_clary.hipo"

    #cd $pid_files$el_dir_name
    #pwd
    #input_file="h_"$2"_"$4"_thread-"*"_el_pid_clary.hipo"
    #output_file="h_"$2"_"$4"_el_pid_clary_job_"$i".hipo"
    #echo " >> hadd " $output_file " " $input_file
    #$COATJAVA/bin/hadd $output_file $input_file
    #hadd $pid_files$pr_dir_name'h_'$2'_'$4'_pr_pid_clary_job_'$1'.hipo' $pid_files$el_dir_name'h_'$2'_'$4'_thread-'*'_proton_pid_clary.hipo' 
    #hadd $pid_files$kp_dir_name'h_'$2'_'$4'_kp_pid_clary_job_'$1'.hipo' $pid_files$el_dir_name'h_'$2'_'$4'_thread-'*'_kaonP_pid_clary.hipo' 

    i=$((i+1))
done

echo " COMPLETE "
