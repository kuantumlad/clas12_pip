#include "TTree.h"
#include "TFile.h"
#include "TBrowser.h"
#include "TMath.h"
#include "TH1D.h"
#include "TH2D.h"
#include "TF1.h"
#include "TColor.h"
#include "TCanvas.h"
#include "TAxis.h"
#include "TLatex.h"
#include "TString.h"
#include "TLorentzVector.h"
#include "TGraph.h"
#include "TGraphErrors.h"
#include "TStyle.h"
#include "TLine.h"

#include <vector>
#include <iostream>
#include <map>

#include "tPhi.C"

void kinBin(const char* tempdir, int max_files ){

  TCanvas *c1 = new TCanvas("c1","1-D integrated over bins",800,800);
  c1->Divide(2,2);
  TCanvas *c2 = new TCanvas("c2","2-D integrated over bins",800,800);
  c2->Divide(2,2);
  
  //ADD PROCESSING LATER
  if( max_files < 1 ){ std::cout << ">> ERROR VALUE NEEDS TO BE LARGER THAN 0" << std::endl; exit(1); }
  TChain *fchain = new TChain("tPhi");
  for( int i = 1; i <= max_files; i++ ){    
    std::cout << ">> ADDING " << Form("%s/pid_phi_%d.root",tempdir,i) << std::endl;
    fchain->Add(Form("%s/pid_phi_%d.root",tempdir,i));
  }

  tPhi *treevar = new tPhi(fchain);  
  std::cout << " >> PROCEEDING TO ANALYZE " << fchain->GetEntries() << " ENTRIES " << std::endl;

  TH1D *h_x = new TH1D("h_x","Xbj", 100, 0.0, 1.0);
  TH1D *h_t = new TH1D("h_t","-t", 100, 0.0, 3.0);
  TH1D *h_w = new TH1D("h_w","W^{2}", 100, 5.0, 40.0);
  TH1D *h_q2 = new TH1D("h_q2","Q^{2}", 100, 0.50, 10.0);
  
  TH2D *h2_q2x = new TH2D("h2_q2x","Q^{2} vs xb",100,0.0, 1.0, 100, 0.0, 11.0);
  TH2D *h2_q2phi = new TH2D("h2_q2phi","Q^{2} vs #phi_{cm}",100,0.0, 180.0, 100, 0.0, 11.0);
  TH2D *h2_tphi = new TH2D("h2_tphi","-t vs #phi_{cm}",100, 0.0, 180.0, 100, 0.0, 3.0);
  TH2D *h2_ttheta = new TH2D("h2_ttheta","-t vs #theta_{cm}", 100, 0.0, 5.0, 100, 0.0, 3.0 );
    
  /////////////////////////////////////////////////////////////
  //
  // BINNING CHOICES ARE FROM THE PACS PROPOSAL 
  //
  /////////////////////////////////////////////////////////////

  Long64_t num_entries = fchain->GetEntries();
  for( Long64_t nn = 0; nn < num_entries; nn++ ){
    fchain->GetEntry(nn);

    Double_t q2 = treevar->Q2;
    Double_t w = treevar->W;
    Double_t t = treevar->T;
    Double_t xb = treevar->Xbj;

    Double_t cm_phi = treevar->CM_phi;
    Double_t cm_theta = treevar->CM_theta;
    
    Double_t el_p = treevar->fel_p;
    Double_t el_theta = treevar->fel_theta;
    Double_t el_phi = treevar->fel_phi;

    Double_t pr_p = treevar->fpr_p;
    Double_t pr_theta = treevar->fpr_theta;
    Double_t pr_phi = treevar->fpr_phi;

    Double_t kp_p = treevar->fkp_p;
    Double_t kp_theta = treevar->fkp_theta;
    Double_t kp_phi = treevar->fkp_phi;

    Double_t km_p = treevar->fkm_p;
    Double_t km_theta = treevar->fkm_theta;
    Double_t km_phi = treevar->fkm_phi;

    //std::cout << q2 << " " << -t << " " << w << " " << xb << std::endl;
    //CONTAIN ALL BINS
    h_q2->Fill(-q2);
    h_t->Fill(-t);
    h_w->Fill(w);
    h_x->Fill(xb);
    
    h2_q2x->Fill(xb, -q2);
    h2_q2phi->Fill(cm_phi, -q2);
    h2_tphi->Fill(cm_phi, -t);
    h2_ttheta->Fill(TMath::ACos(cm_theta), -t);

  }

  /////////////////////////////////////////////////
  //DEFINE KIN REGIONS
  Double_t mass_proton = 0.938;
  Double_t beam_energy = 10.6;
  Double_t min_angle = 47.0;
  Double_t degTorad = 3.1415926/180.0;
  Double_t qcut_min = 0.02;
  Double_t qcut_max = 0.825;
    TF1 *qcut = new TF1("qcut",Form("2*%f*x*(%f - (2*%f*%f*x)/(2*%f*x + 4*%f*sin((%f/2.0)*%f)*sin((%f/2.0)*%f)))", 
  				  mass_proton, beam_energy, mass_proton, beam_energy, mass_proton, beam_energy,
  				  min_angle,degTorad, min_angle, degTorad
				  ), qcut_min, qcut_max );
  TF1 *wcut = new TF1("wcut","(0.938*0.938 - 2*2)*( x/(x-1) )",0.18,0.825);
  TLine *q2cut = new TLine(0.073,1.0,0.52,1.0);
    
  /////////////////////////////////////////////////
  //DRAW THE PLOTS
  c1->cd(1);
  h_q2->GetXaxis()->SetTitle("Q^2 [GeV^2]");
  h_q2->GetXaxis()->CenterTitle();
  h_q2->GetYaxis()->SetTitle("Entries");
  h_q2->GetYaxis()->CenterTitle();
  h_q2->Draw();
  c1->cd(2);
  h_t->GetXaxis()->SetTitle("-t [GeV]");
  h_t->GetXaxis()->CenterTitle();
  h_t->GetYaxis()->SetTitle("Events");
  h_t->GetYaxis()->CenterTitle();
  h_t->Draw();
  c1->cd(3);
  h_w->GetXaxis()->SetTitle("W [GeV^2]");
  h_w->GetXaxis()->CenterTitle();
  h_w->GetYaxis()->SetTitle("Events");
  h_w->GetYaxis()->CenterTitle();
  h_w->Draw();
  c1->cd(4);
  h_x->GetXaxis()->SetTitle("Xbj");
  h_x->GetXaxis()->CenterTitle();
  h_x->GetYaxis()->SetTitle("Events");
  h_x->GetYaxis()->CenterTitle();
  h_x->Draw();

  //////////////////////////////////////////////////////////////////
  //TH2Ds

  c2->cd(1);
  h2_q2x->GetXaxis()->SetTitle("Xbj");
  h2_q2x->GetXaxis()->CenterTitle();
  h2_q2x->GetYaxis()->SetTitle("Q^2 [GeV^2]");
  h2_q2x->GetYaxis()->CenterTitle();
  qcut->SetLineColor(kRed);
  wcut->SetLineColor(kBlue);
  q2cut->SetLineColor(kGreen);
  h2_q2x->Draw("colz");
  qcut->Draw("same");
  wcut->Draw("same");
  q2cut->Draw("same");
  c2->cd(2);
  h2_q2phi->GetXaxis()->SetTitle("#phi [deg]");
  h2_q2phi->GetXaxis()->CenterTitle();
  h2_q2phi->GetYaxis()->SetTitle("Q^2 [GeV^2]");
  h2_q2phi->GetYaxis()->CenterTitle();
  h2_q2phi->Draw("colz");
  c2->cd(3);
  h2_tphi->GetXaxis()->SetTitle("#phi [deg]");
  h2_tphi->GetXaxis()->CenterTitle();
  h2_tphi->GetYaxis()->SetTitle("-t [GeV^2]");
  h2_tphi->GetYaxis()->CenterTitle();
  h2_tphi->Draw("colz");
  c2->cd(4);
  h2_ttheta->GetXaxis()->SetTitle("#theta [deg]");
  h2_ttheta->GetXaxis()->CenterTitle();
  h2_ttheta->GetYaxis()->SetTitle("-t [GeV^2]");
  h2_ttheta->GetYaxis()->CenterTitle();
  h2_ttheta->Draw("colz");

  //////////////////////////////////////////////
  //SAVE PLOTS
  c1->SaveAs("h1_kin.pdf");
  c2->SaveAs("h2_kin.pdf");

}
