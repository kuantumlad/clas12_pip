#include "TH1D.h"
#include "TF1.h"
#include "TCanvas.h"
#include "TAxis.h"
#include "TStyle.h"
#include "TRandom.h"
#include "TMatrixD.h"
#include "TMatrixTBase.h"
#include "TDecompSVD.h"

#include <iostream>
#include <vector>
#include <map>
#include <math.h>


void exampleUnFold( const char* argv ){


  int nentries = 1000;
  int nbins = 50;
  int startbin = 10;
  double amp = 1000.0;
  double shift = 0.7;
  
  TH1D *h_true = new TH1D("h_true","h_true",nbins, 0.0, 10.5);
  TH1D *h_meas = new TH1D("h_meas","h_meas",nbins, 0.0, 10.5);
  TH1D *h_smear = new TH1D("h_smear","h_smear",100, -0.30, 0.30);
  TH1D *h_trigger = new TH1D("h_trigger","h_trigger",100, 0.0, 10.5);
  
  double x_i = h_true->GetBinCenter(startbin);

  //GAUSSIAN SMEARING
  TF1 *gaus_smear = new TF1("gaus_smear","[0]*exp(-0.5*((x-[1])/[2])**2)",-5.0, 5.0);
  gaus_smear->SetParameter(0, 1);
  gaus_smear->SetParameter(1, -0.10 );
  gaus_smear->SetParameter(2, 0.01 );
  for( int i = 0; i < nentries; i++ ){
    h_smear->Fill( gaus_smear->GetRandom() );
  }

  //TRUE 
  TF1 *f_true = new TF1("f_true","[0]*exp(-x)",0.0, 10.5);
  f_true->SetParameter(0,amp);
  
  //TRIGGER RESPONSE
  // double trigger_response = 1 - exp(-x + x_i);
  TF1 *f_trigger_response = new TF1("f_trigger_response","1 - exp(-x)", 0.0, 10.5);

  //MEASUREMENT   
  TF1 *f_meas = new TF1("f_meas","([0]*[1]*exp(-x) + [2])*(1 - exp(-x + [3]))",0.10, 10.5);
  

  //CREATE TRUE 
  for( int i = startbin; i < nbins; i++ ){

    double x = h_true->GetBinCenter(i);
    double y_true = amp*exp(-x);
    h_true->SetBinContent(i, y_true);

  }
  h_true->SetEntries(nentries);

  for( int i = startbin; i < nbins; i++ ){


    //MEASURED RESULT
    double x = h_meas->GetBinCenter(i);
    double y_true = amp*exp(-x);
    double smear_y = gaus_smear->GetRandom();
    
    double trigger_response = 1 - exp(-x + x_i);
    double y_meas = (y_true*shift + smear_y)*trigger_response;
    //std::cout << x << " " << y_meas << " " << smear_y << " " << trigger_response << std::endl;

    h_trigger->SetBinContent(i, trigger_response );
    h_meas->SetBinContent(i,y_meas);

  }
  h_meas->SetEntries(nentries);

  //////////
  //UNFOLD
  TH1D *h_unfold = new TH1D("h_unfold","h_unfold",nbins, 0, 10.5);
  TMatrixD m_response(nbins, nbins);
  std::vector<double> v_normalize(nbins+1);

  int nnentries = h_true->GetEntries();
  for( int i = 0; i < nnentries; i++ ){

    double hit_true = h_true->FindBin(i);
    //double hit_meas = h_meas->GetBinContent(i);
    
    //int true_bin = h_true->FindBin(hit_true);
  }
    
  TCanvas *c1 = new TCanvas("c1","c1", 800, 800);
  c1->cd();
  gStyle->SetOptStat(0);
  gPad->SetLogy();
  h_true->SetTitle("True and Meas");
  h_true->SetLineColor(kBlack);
  h_meas->SetLineColor(kRed);
  h_true->Draw();
  h_meas->Draw("same");

  c1->SaveAs("/u/home/bclary/CLAS12/phi_analysis/v1/utilities/hlPhysics/examples/h_example.pdf");

  TCanvas *c2 = new TCanvas("c2","c2",800,800);
  c2->Divide(2,1);
  c2->cd(1);
  h_trigger->SetTitle("Trigger");
  h_trigger->Draw();
  c2->cd(2);
  h_smear->SetTitle("Smear Y");
  h_smear->Draw();

  
}
