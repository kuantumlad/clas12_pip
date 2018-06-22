#!/bin/bash

#javac -cp "src/com/FileManager.class:/lustre/expphy/work/halla/sbs/bclary/extras/coatjava_4a.8.1/lib/clas/coat-libs-3.0-SNAPSHOT.jar" phiAnalysis.java

#javac -cp /lustre/expphy/work/halla/sbs/bclary/extras/coatjava_4a.8.2/lib/clas/coat-libs-4.0-SNAPSHOT.jar -d . -sourcepath . phiAnalysis.java
#java -cp ".:src/com/*.class:/lustre/expphy/work/halla/sbs/bclary/extras/coatjava_4a.8.2/lib/clas/coat-libs-4.0-SNAPSHOT.jar" phiAnalysis $2
javac -cp /lustre/expphy/work/halla/sbs/bclary/extras/myClara/plugins/clas12/lib/clas/coat-libs-5.0-SNAPSHOT.jar -d . -sourcepath . flatAnalysis.java
java -cp ".:src/com/*.class:/lustre/expphy/work/halla/sbs/bclary/extras/myClara/plugins/clas12/lib/clas/coat-libs-5.0-SNAPSHOT.jar" flatAnalysis $1
	
