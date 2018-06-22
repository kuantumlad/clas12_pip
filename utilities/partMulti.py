import matplotlib.pyplot as plt
import numpy as np

file = open("partMultiplicity.txt","r");

rates=[]
topologyrates = []
partlabels = 'Electrons','Protons','K+','K-'
colors = ['gold','blue','red','purple'];
maxparticles = 3;
i = 0;
for line in file:
    if i <= maxparticles: 
        rates.append(int(line.rstrip()));
    if i > maxparticles:
        topologyrates.append(int(line.rstrip()));
    i=i+1

#plt.title('Individual Particle Rates');# ,loc = 'right');
plt.pie(rates, labels=partlabels, colors=colors,autopct='%1.1f%%', startangle=40)

plt.axis('equal');
plt.show();
