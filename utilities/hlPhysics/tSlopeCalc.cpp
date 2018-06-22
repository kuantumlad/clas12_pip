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

#include <vector>
#include <iostream>
#include <map>

#include "tPhi.C"

void tSlopeCalc(const char* tempdir, int max_files ){

  if( max_files < 1 ){ std::cout << ">> ERROR VALUE NEEDS TO BE LARGER THAN 0" << std::endl; exit(1); }
  TChain *fchain = new TChain("tPhi");
  for( int i = 1; i <= max_files; i++ ){    
    std::cout << ">> ADDING " << Form("%s/pid_phi_%d.root",tempdir,i) << std::endl;
    fchain->Add(Form("%s/pid_phi_%d.root",tempdir,i));
  }

  ///////////////////////////////////////////////////////
  //CREATE COS_THETA HISTOGRAMS BINNED IN -t  
  TCanvas *c1 = new TCanvas("cos theta t binned ","", 800, 800 );
  c1->Divide(2,2);

  TCanvas *test = new TCanvas("test1","", 800,800);
  TF1 *f3 = new TF1("f3", "pol2", -1.0, 1.0 );//"0.75*((1.0 - [0]) + (3.0*[0] - 1.0)*x^2)", -1.00, 1.00 );//(3.0/4.0)*( (1.0 - [0]) + (3.0*[0] - 1.0)*x*x ) ", -1.00001, 1.00001);
  //f3->SetParameter(0,1500.0);
  f3->FixParameter(1,0.0);
  TH1D *h_test = new TH1D("h_test","h_test",10,-1.0,1.0);

  std::vector<TH1D*> h_tbinned;  
  std::vector<TF1*> h_tfits;
  int n_tbins = 4;
  double min_cos = -1.0;
  double max_cos = 1.0;
  double n_cosbins = 10;

  for( int i = 1; i <= n_tbins; i++ ){
    h_tbinned.push_back( new TH1D(Form("h_tbin%d",i), Form("h_tbin%d",i), n_cosbins, min_cos, max_cos ) );
    h_tfits.push_back( new TF1(Form("fit_%d",i), "pol2", -1.0, 1.0 ) );//( (1.0 - [0]) + (3.0*[0] - 1.0)*x^2)", -1.0, 1.0) );
  }

  tPhi *treevar = new tPhi(fchain);  
  std::cout << " >> PROCEEDING TO ANALYZE " << fchain->GetEntries() << " ENTRIES " << std::endl;
  
  Long64_t num_entries = fchain->GetEntries();
  double t_min1 = 0.0;
  double t_max1 = 0.1;
  double t_min2 = 0.1;
  double t_max2 = 0.3;
  double t_min3 = 0.3;
  double t_max3 = 0.5;
  double t_min4 = 0.5;
  double t_max4 = 0.9;
  
  for( Long64_t nn = 0; nn < num_entries; nn++ ){
    fchain->GetEntry(nn);
    double cm_theta = treevar->CM_theta;
    double t = -treevar->T;
    if( t_min1 <= t && t < t_max1 ){
      h_tbinned[0]->Fill(cm_theta);
    }
    if( t_min2 <= t && t < t_max2 ){
      h_tbinned[1]->Fill(cm_theta);
    }
    if( t_min3 <= t && t < t_max3 ){
      h_tbinned[2]->Fill(cm_theta);
      h_test->Fill(cm_theta);
    }
    if( t_min4 <= t && t < t_max4 ){
      h_tbinned[3]->Fill(cm_theta);
    }
    
  }

  gStyle->SetOptFit(1111);

  for( int i = 1; i <= n_tbins; i++ ){
    std::cout << ">> FITTING BIN " << i << std::endl;
    c1->cd(i);
    h_tbinned[i-1]->GetYaxis()->SetRangeUser(0., 350.); 

    h_tfits[i-1]->SetParameters(350.0 ,0.0, -10.0);
    h_tfits[i-1]->FixParameter(1, 0.0);
    //h_tfits[i-1]->SetParLimits(0, -1000.0, 1000.0);
    h_tbinned[i-1]->SetTitle(Form("Bin %d",i));
    h_tbinned[i-1]->GetXaxis()->SetTitle("cos(#theta_{CM})");
    h_tbinned[i-1]->GetYaxis()->SetTitle("d#sigma/d cos(#theta_{CM})");
    h_tbinned[i-1]->GetXaxis()->CenterTitle();
    h_tbinned[i-1]->GetYaxis()->CenterTitle();
    double nentries = h_tbinned[i-1]->GetEntries() + 1.0;
    std::cout << ">> NUMBER OF ENTRIES FOR HISTOGRAM " << i << ": " << nentries << std::endl;
    //h_tbinned[i-1]->Scale(1.0/nentries);
    h_tbinned[i-1]->Fit(h_tfits[i-1],"R");//Form("fit_%d",i));//"", -1.0, 1.0);
    h_tbinned[i-1]->Draw("h");
    // h_tfits[i-1]->Draw("same");
  } 
  
  std::vector<double> test_x, test_y;  
  for( int i = 1; i <= 10; i++ ){
    double test = h_test->GetBinContent(i);
    test_x.push_back(h_test->GetBinCenter(i));    
    test_y.push_back(test);
  }
  TGraph *g_test = new TGraph(test_x.size(), &(test_x[0]), &(test_y[0]));


  test->cd();
  /*  h_test->SetTitle(Form("Bin %d",3));
  h_test->GetXaxis()->SetTitle("cos(#theta_{CM})");
  h_test->GetYaxis()->SetTitle("d#sigma/d cos(#theta_{CM})");
  h_test->GetXaxis()->CenterTitle();
  h_test->GetYaxis()->CenterTitle();
  h_test->Scale(1.0/(h_test->GetEntries() + 1.0) );
  f3->SetParLimits(0, -1000.0, 1000.0);
  h_test->Fit("f3","WR");
  //f3->Draw();
  h_test->Draw("h");
  */
  g_test->SetName("test");
  g_test->SetTitle("test");
  g_test->SetMarkerStyle(20);
  g_test->SetMarkerSize(0.8);
  g_test->GetYaxis()->SetRange(0, 500);
  g_test->Fit("f3","WR");
  //g_test->Draw("AP");
  f3->Draw("same");

  c1->SaveAs("h_costheta_tbinned.pdf");

}
