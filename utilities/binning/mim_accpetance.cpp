#include "TMath.h"
#include "TH1D.h"
#include "TGraph.h"
#include "TCanvas.h"
#include <fstream>
#include <string>
#include <sstream>
#include <vector>

#include <iostream>


TH1D* readTxtToHist( const char* filename, const char* gentype, int nbins, double min_q2, double max_q2 ){

  TH1D *h_temp = new TH1D(Form("h_accp_corr_%s",gentype), Form("h_accp_corr_%s",gentype), nbins, min_q2, max_q2);
  std::ifstream fIn;
  fIn.open(filename, std::ios_base::in );
  
  std::string line;
  if( fIn.is_open() ){

    while( getline( fIn, line ) ){
      if( line[0] == '#' ) continue;
      Double_t col1, col2, col3;
      std::stringstream first(line);
      first >> col1 >> col2 >> col3;

      double bin = col1;
      double accp = col2;
      double recon = col3;
      
      double corrected = accp*recon;

      std::cout << " CORRECTED VALUE IS " << corrected << std::endl;
      h_temp->SetBinContent(bin,corrected);

    }	
  }
  fIn.close();
  return h_temp;
}

std::vector<double> readTxtToVector(const char* filename, int col){

  std::vector<double> v_temp;

  std::ifstream fIn;
  fIn.open(filename, std::ios_base::in );
  
  std::string line;
  if( fIn.is_open() ){

    while( getline( fIn, line ) ){
      if( line[0] == '#' ) continue;
      Double_t col1, col2, col3;
      std::stringstream first(line);
      first >> col1 >> col2 >> col3;

      double bin = col1;
      double accp = col2;
      double recon = col3;
      if( col == 0 ){ v_temp.push_back(bin); }
      if( col == 1 ){ v_temp.push_back(accp); }
      if( col == 2 ){ v_temp.push_back(recon); }
      
    }	
  }
  fIn.close();
  return v_temp;
}


TH1D* makeHisto(std::vector<double> v_temp_acc, std::vector<double> v_temp_recon,  const char* gentype, int nbins, double min_q2, double max_q2){

  TH1D *h_temp = new TH1D(Form("h_accp_corr_%s",gentype), Form("h_accp_corr_%s",gentype), nbins, min_q2, max_q2);
  for( int bin = 0; bin<nbins; bin++ ){

    double accp = v_temp_acc[bin];
    double recon = v_temp_recon[bin];

    double corrected = accp*recon;

    h_temp->SetBinContent(bin,corrected);

  }
  return h_temp;

}

int mim_accpetance( ){

  std::cout<< " >> CREATING PLOTS FOR ACCEPTANCE CORRECTIONS " << std::endl;

  int nbins = 10;
  double min_q2 = 1.0;
  double max_q2 = 9.99;

  std::vector<double> v_accp_flat = readTxtToVector("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h_accep_rec_q2_flat.txt",1);
  std::vector<double> v_accp_sin = readTxtToVector("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h_accep_rec_q2_sin.txt",1);
  std::vector<double> v_accp_q4 = readTxtToVector("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h_accep_rec_q2_q4.txt",1);

  std::vector<double> v_recon_flat = readTxtToVector("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h_accep_rec_q2_flat.txt",2); 

  TH1D *h_corrected_flat = makeHisto(v_accp_flat,v_recon_flat,"flat",nbins, min_q2, max_q2);
  TH1D *h_corrected_sin = makeHisto(v_accp_sin,v_recon_flat,"sin",nbins, min_q2, max_q2);
  TH1D *h_corrected_q4 = makeHisto(v_accp_q4,v_recon_flat,"q4",nbins, min_q2, max_q2);


  TCanvas *c_trad_corr  = new TCanvas("c_trad_corr","c_trad_corr",800,800);
  c_trad_corr->Divide(1,1);
  c_trad_corr->cd(1);
  h_corrected_flat->SetLineColor(1);
  h_corrected_sin->SetLineColor(2);
  h_corrected_q4->SetLineColor(4);
  h_corrected_q4->SetTitle("Q^2 with Bin-by-Bin Correction");
  h_corrected_flat->GetXaxis()->SetTitle("Q^{2} [GeV^2]");
  h_corrected_flat->Draw("same");
  h_corrected_sin->Draw("same");
  h_corrected_q4->Draw("same");
  
  c_trad_corr->SaveAs("/u/home/bclary/CLAS12/phi_analysis/v2/v1/utilities/binning/h_trad_accp_final.pdf");

  return 0;
}



