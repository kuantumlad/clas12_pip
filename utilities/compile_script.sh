#!/bin/bash

# $1 is fin 
# $2 is fout
# $3 is run number

javac -cp /lustre/expphy/work/hallb/clas12/bclary/extras/coatjava/lib/clas/coat-libs-5.0-SNAPSHOT.jar -d . -sourcepath . HippoFilter.java
java -cp ".:/lustre/expphy/work/hallb/clas12/bclary/extras/coatjava/lib/clas/coat-libs-5.0-SNAPSHOT.jar" HippoFilter $1 $2 $3
