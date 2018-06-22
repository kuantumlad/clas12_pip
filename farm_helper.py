import sys
#####################################
##
##   USE TO APPEND FILES TO ANALYZE
## 
##
##
#####################################

f = open("farm_submission.jsub","w+");


run_number = sys.argv[1]
max_files = sys.argv[2]
s_run_number = str(run_number)
print '>> CREATING JSUB FOR RUN ' + s_run_number
print '>> NUMBER OF FILES TO PROCESS ' + str(max_files)
file_location = '/volatile/halla/sbs/bclary/clas12Analysis/SKIMclas12/skim_00'+s_run_number+'_pass1/skim_clas_00'+s_run_number+'.evio.'
file_suf = '.hipo'

f.write('JOBNAME: bcPhiAnalyis' + ' \n')
f.write('OS: centos7' + ' \n');
f.write('TRACK: debug' + ' \n');
f.write('MEMORY: 100 GB' + ' \n');
f.write('DISK_SPACE: 100 GB' + ' \n');
f.write('CPU: 12' + ' \n');
f.write('PROJECT: clas12' + ' \n');
f.write('COMMAND: /u/home/bclary/CLAS12/phi_analysis/v3/v2/v1/./compile_script.sh SCLAS12 '+ s_run_number + ' ' + str(max_files) + ' \n');
f.write('OTHER_FILE:');
f.write('/u/home/bclary/CLAS12/phi_analysis/v3/v2/v1/compile_script.sh' + ' \n')
f.write('/u/home/bclary/CLAS12/phi_analysis/v3/v2/v1/./electronPID' + ' \n')
f.write('/u/home/bclary/CLAS12/phi_analysis/v3/v2/v1/target/ElectronPID-1.0-SNAPSHOT.jar' + ' \n')
f.write('OUTPUT_DATA: out_temp ' + '\n')
f.write('OUTPUT_TEMPLATE:' + ' \n')
f.write('/w/hallb-scifs17exp/clas12/bclary/pics/pid_clary/*.hipo' + ' \n')

f.close()
