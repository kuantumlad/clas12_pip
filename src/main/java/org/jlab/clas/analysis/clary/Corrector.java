package org.jlab.clas.analysis.clary;

import org.freehep.math.minuit.*;
import java.util.*;
import java.io.*;

public class Corrector {

    double loss_value;

    Vector<Double> fit_parameters = new Vector<Double>();

    public Corrector() {

    }

    public void setFitParameters() {

        fit_parameters.add(1.0);
        fit_parameters.add(0.0);
        fit_parameters.add(0.0);
        fit_parameters.add(0.0);
        fit_parameters.add(0.0);
        fit_parameters.add(0.0);
        fit_parameters.add(0.0);

    }

    public void setCorrectionFunction() {

        //minuit function here
    }

    public double getCorrectionValue(double temp_p, double temp_theta, double temp_phi, Vector<Double> fit_para) {

        return 0.0;
    }

    public double getDeltaShift() {

        return 0.0;

    }

    public double getScore(Vector<Double> v_measured_p, Vector<Double> v_measured_theta, Vector<Double> v_measured_phi) {

        double score = 0.0;
        for (int data = 0; data < v_measured_p.size(); data++) {

            double p_measured = v_measured_p.get(data);
            double theta_measured = v_measured_theta.get(data);
            double phi_measured = v_measured_phi.get(data);

            double p_corrected = 0;//p_measured * getCorrectionValue(p_measured, theta_measured, phi_measured);
            System.out.println(" >> CORRECTED VALUE " + p_corrected);

            double delta_p = 0.0;
            score = score + (1 - delta_p) * (1 - delta_p);

        }

        return score;
    }

    public void getMinimization() {

        //do minuit stuff in here
        //put the score function in here and put into the minimizer
    }

}
