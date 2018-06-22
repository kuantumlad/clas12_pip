#include "TTree.h"
#include "TFile.h"
#include "TBrowser.h"
#include "TMath.h"
#include "TH1D.h"
#include "TH2D.h"
#include "TF1.h"
#include "TCanvas.h"
#include "TAxis.h"
#include "TLatex.h"
#include "TString.h"
#include "TLorentzVector.h"
#include "TGraph.h"
#include "TGraphErrors.h"
#include "TStyle.h"
#include "TLine.h"
#include "TLegend.h"

#include <vector>
#include <iostream>
#include <map>

#include "tPhi.C"

std::string tempvar = "Q^{2}";

void binQuality(const char* tempdir, int max_files ){


  ////////////////////////////////////////////////////////////////////
  //CHAIN ROOT FILES TOGETHER
  if( max_files < 1 ){ std::cout << ">> ERROR VALUE NEEDS TO BE LARGER THAN 0" << std::endl; exit(1); }
  TChain *fchain = new TChain("tPhi");
  for( int i = 1; i <= max_files; i++ ){    
    //std::cout << ">> ADDING " << Form("%s/pid_phi_%d.root",tempdir,i) << std::endl;
    fchain->Add(Form("%s/pid_phi_%d.root",tempdir,i));
  }

  ////////////////////////////////////////////////////////////////////
  //SET VARIABLES
  /*  std::map< std::string,std::vector<TH1D*> > h_kinvar;
  std::vector<std::string> kinvar;
  kinvar.push_back("q2");
  kinvar.push_back("x");
  kinvar.push_back("t");
  kinvar.push_back("phi");


  
  for( std::vector<std::string>::iterator it = kin.begin(); it != kinvar.end(); ++it ){
    std::cout << ">> CREATING HISTOGRAM FOR Q2 " << std::endl;
    h_kinvar[*it].push_back(new TH1D(Form("h_%s", *it.c_str() ), Form("h_%s", *it.c_str() )
  }
  */

  ////////////////////////////////////////////////////////////////////
  //SET CANVAS AND HISTOGRAMS
  std::vector<TCanvas *> v_canvas;
  TCanvas *c1 = new TCanvas("c1","",900,900);
  TCanvas *c2 = new TCanvas("c2","",1600,800);

  tPhi *treevar = new tPhi(fchain);  
  std::cout << " >> PROCEEDING TO ANALYZE " << fchain->GetEntries() << " ENTRIES " << std::endl;

  Int_t max_q2_bins = 200;
  double max_q2 = 9.5;
  double min_q2 = 1.5;
  TH1D *h_rec_q2 = new TH1D("h_rec_q2",Form("REC %s", tempvar.c_str() ),max_q2_bins, min_q2, max_q2);
  TH1D *h_mc_q2 = new TH1D("h_mc_q2",Form("MC %s", tempvar.c_str() ),max_q2_bins, min_q2, max_q2);
  TH1D *h_recmc_q2 = new TH1D("h_recmc_q2",Form("REC + MC %s",tempvar.c_str() ),max_q2_bins,  min_q2, max_q2);
  TH1D *h_binquality = new TH1D("h_binquality",Form("Bin Quality for %s", tempvar.c_str() ),max_q2_bins,  min_q2, max_q2);

  TH2D *h2_correlate_q2 = new TH2D("h2_correlate_q2","h2_correlate_q2",500,0.0,10.0,500, 0.0, 10.0);
  Double_t min_t = 1.0;
  Double_t max_t = 1.2;

  Long64_t num_entries = fchain->GetEntries();  
  for( Long64_t nn = 0; nn < num_entries; nn++ ){
    fchain->GetEntry(nn);
    
    Double_t rec_q2 = -treevar->Q2;
    Double_t mc_q2 = -treevar->mc_Q2;
    //std::cout << rec_q2 << " " << mc_q2 << std::endl;

    Int_t rec_q2_bin = h_rec_q2->FindBin(rec_q2);
    Int_t mc_q2_bin = h_mc_q2->FindBin(mc_q2);

    //    if( min_t < rec_t && rec_t <= max_t ){
    h_rec_q2->Fill(rec_q2);
    h_mc_q2->Fill(mc_q2);

    h2_correlate_q2->Fill(mc_q2,rec_q2);
    
    if( rec_q2_bin == mc_q2_bin ){
      h_recmc_q2->Fill(rec_q2);
      //std::cout << " MATCHED " << std::endl;
    }
       
  }

  gStyle->SetOptStat(0);
  TLegend *l1 = new TLegend(0.6,0.6,0.9,0.9);
  l1->AddEntry(h_rec_q2, "REC #phi events in bin");
  l1->AddEntry(h_mc_q2, "MC #phi events in bin");
  l1->AddEntry(h_recmc_q2,"REC & MC events in bin");

  c1->Divide(1,1);
  c1->cd(1);
  h_rec_q2->SetName("");
  h_rec_q2->GetXaxis()->SetTitle(Form("%s [GeV^{2}]",tempvar.c_str()) );
  h_rec_q2->GetYaxis()->SetTitle("Entries");
  h_rec_q2->SetLineColor(kRed);
  h_mc_q2->SetLineColor(kBlue+2);
  h_recmc_q2->SetLineColor(kBlack);
  h_rec_q2->Draw();
  h_mc_q2->Draw("same");
  h_recmc_q2->Draw("same");
  l1->Draw("same");

  std::vector<double> binqual_x, binqual_y, binqual_ex, binqual_ey;
  std::vector<double> binstab_x, binstab_y, binstab_ex, binstab_ey;
  for( int bin = 1; bin <= max_q2_bins; bin++ ){
    double temprecmc = h_recmc_q2->GetBinContent(bin);
    double temprec = h_rec_q2->GetBinContent(bin);
    double binqual = temprecmc/temprec;
    if( temprec == 0 ) continue;
    std::cout << ">> " << binqual <<  " " << h_rec_q2->GetBinCenter(bin) << std::endl;
    binqual_x.push_back(h_rec_q2->GetBinCenter(bin));
    binqual_ex.push_back(0);
    binqual_y.push_back(binqual);
    double ey = binqual * sqrt( pow(sqrt(temprecmc)/temprecmc, 2) + pow(sqrt(temprec)/temprec, 2) );
    std::cout << ey << std::endl;
    binqual_ey.push_back(ey);
  }

  for( int bin = 1; bin <= max_q2_bins; bin++ ){
    double temprecmc = h_recmc_q2->GetBinContent(bin);
    double tempmc = h_mc_q2->GetBinContent(bin);
    double binstab = temprecmc/tempmc;
    if( tempmc == 0 ) continue;
    binstab_x.push_back(h_rec_q2->GetBinCenter(bin));
    binstab_ex.push_back(0);
    binstab_y.push_back(binstab);
    double ey = binstab * sqrt( pow(sqrt(temprecmc)/temprecmc, 2) + pow(sqrt(tempmc)/tempmc, 2) );
    std::cout << ey << std::endl;
    binstab_ey.push_back(ey);

  }


  /*  c1->cd(2);
  h_recmc_q2->SetName(Form("REC + MC %s ", tempvar.c_str()) );
  h_recmc_q2->GetXaxis()->SetTitle(Form("%s [GeV]", tempvar.c_str()) );
  h_recmc_q2->GetYaxis()->SetTitle("Entries");
  h_recmc_q2->SetLineColor(kGreen);
  h_recmc_q2->Draw();
  */
  c2->Divide(2,1);
  c2->cd(1);
  TGraphErrors *g_binquality = new TGraphErrors(binqual_x.size(), &(binqual_x[0]), &(binqual_y[0]), &(binqual_ex[0]), &(binqual_ey[0]) );
  g_binquality->SetName("g_binquality");
  g_binquality->SetTitle(Form("Bin Purity for %s",tempvar.c_str() ));
  g_binquality->GetXaxis()->SetTitle(Form("%s [GeV^{2}]", tempvar.c_str()) );
  g_binquality->GetYaxis()->SetTitle("Bin Purity");
  g_binquality->GetYaxis()->CenterTitle();
  g_binquality->GetXaxis()->CenterTitle();
  g_binquality->SetMarkerStyle(20);
  g_binquality->SetMarkerSize(0.7);
  g_binquality->SetMarkerColor(kBlue+2);
  g_binquality->Draw("AP");

  c2->cd(2);
  TGraphErrors *g_binstabquality = new TGraphErrors(binstab_x.size(), &(binstab_x[0]), &(binstab_y[0]), &(binstab_ex[0]), &(binstab_ey[0]) );
  g_binstabquality->SetName("g_binstabquality");
  g_binstabquality->SetTitle(Form("Bin Stability for %s",tempvar.c_str() ));
  g_binstabquality->GetXaxis()->SetTitle(Form("%s [GeV^{2}]", tempvar.c_str()) );
  g_binstabquality->GetYaxis()->SetTitle("Bin Stability");
  g_binstabquality->GetYaxis()->CenterTitle();
  g_binstabquality->GetXaxis()->CenterTitle();
  g_binstabquality->SetMarkerStyle(20);
  g_binstabquality->SetMarkerSize(0.7);
  g_binstabquality->SetMarkerColor(kBlue+2);
  g_binstabquality->Draw("AP");

  c1->SaveAs(Form("results/bin_recmc_%s_b%d.pdf",tempvar.c_str(), max_q2_bins ));
  c2->SaveAs(Form("results/bin_purity_stability_%s_b%d.pdf",tempvar.c_str(), max_q2_bins ));

  TCanvas *c3 = new TCanvas("c3_correlation","c3_correlation",800,800);
  h2_correlate_q2->SetTitle("Correlation between REC and GEN ");
  h2_correlate_q2->GetXaxis()->SetTitle("GEN Q^2 [GeV^2]");
  h2_correlate_q2->GetYaxis()->SetTitle("REC Q^2 [GeV^2]");
  h2_correlate_q2->Draw("colz");
  c3->SaveAs("h2_correlate_q2.pdf");
}
