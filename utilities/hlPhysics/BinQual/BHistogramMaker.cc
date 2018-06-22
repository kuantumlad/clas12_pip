#define BHistogramMaker_cxx

#include "BHistogramMaker.h"
#include "TH1D.h"
#include "TH2D.h"
#include "TF1.h"
#include "TGraph.h"
#include "TStyle.h"
#include "TMath.h"
#include "TTree.h"
#include "TCanvas.h"
#include "TGaxis.h"
#include "TVector3.h"

#include <vector>
#include <string>
#include <cmath>
#include <iostream>

BHistogramMaker::BHistogramMaker(){

  num_col = 3;

}

BHistogramMaker::~BHistogramMaker(){
    
  h_bin_centerX.clear();

}


void BHistogramMaker::InitHistogram1D( std::string temptitle, int tempbins, double tempmin, double tempmax ){

  h_temp = new TH1D(Form("h_%s",temptitle.c_str() ),Form("h_%s",temptitle.c_str() ), tempbins, tempmin, tempmax );
  h_temp->SetTitle(Form("%s",temptitle.c_str() ));

}


void BHistogramMaker::InitHistogram2D( std::string temptitle, int tempbins, double tempmin, double tempmax, int tempbins2, double tempmin2, double tempmax2 ){

  h2_temp = new TH2D(Form("h2_%s",temptitle.c_str() ),Form("h2_%s",temptitle.c_str() ), tempbins, tempmin, tempmax, tempbins2, tempmin2, tempmax2 );
  h2_temp->SetTitle(Form("%s", temptitle.c_str() ));

}


void BHistogramMaker::FillHistogram( double tempnum ){

  h_temp->Fill( tempnum );

}

void BHistogramMaker::FillHistogram( double tempnum, double tempnum2 ){

  h2_temp->Fill( tempnum, tempnum2 );

}

std::vector< TH1D* > BHistogramMaker::Project2D(std::string temptitle, std::vector<double> templimits){
  
  std::vector< TH1D* > h_slicedY; 
  h_slicedY.push_back(h2_temp->ProjectionY());
  int i = 0;
  for( std::vector<double>::iterator it = templimits.begin(); it != templimits.end(); ++it ){
    //std::cout << *it << std::endl;
    int bin = h2_temp->GetXaxis()->FindBin((*it));
    double bin_center = h2_temp->GetXaxis()->GetBinCenter(bin);
    h_slicedY.push_back(h2_temp->ProjectionY( Form("%d",bin), bin, bin) ) ;

    if( i > 0 ){
      h_bin_centerX.push_back(bin_center);
    }
    i++;
    
    //std::cout << " >> bin center " << bin_center << std::endl;
  }
  return h_slicedY;
}

void BHistogramMaker::AxisPlotOptions( std::string axispos, int statopt, double textsize ){

  if( axispos == "center" ){
    h2_temp->GetXaxis()->CenterTitle();//(axispos.c_str());
    h2_temp->GetYaxis()->CenterTitle();//SetPosition(axispos.c_str());
  }

  h2_temp->GetXaxis()->SetTitleSize(textsize);
  h2_temp->GetYaxis()->SetTitleSize(textsize);
  h2_temp->GetYaxis()->SetNoExponent(kTRUE);
  
  TGaxis *myZ = (TGaxis*)h2_temp->GetZaxis();
  myZ->SetMaxDigits(2);
  gStyle->SetOptStat(statopt);

}

TH1D* BHistogramMaker::GetHisto1(){

  return h_temp;

}

TH2D* BHistogramMaker::GetHisto2(){

  return h2_temp;

}

void BHistogramMaker::Plot( std::string temptitle, int toggle, double l_marg, double r_marg, double b_marg ){

  TCanvas *c1 = new TCanvas(Form("c%d_%s",toggle, temptitle.c_str() ), Form("c%d_%s",toggle, temptitle.c_str() ), 800, 800 );
  
  if( toggle == 1 ){
    h_temp->Draw();
    c1->SaveAs(Form("h2_phi_%s.pdf",temptitle.c_str()));
  }
  
  if( toggle == 2){
    h2_temp->Draw("colz");
    c1->SetLeftMargin(l_marg);
    c1->SetRightMargin(r_marg);
    c1->SetBottomMargin(b_marg);
    c1->SaveAs(Form("h2_phi_%s.pdf",temptitle.c_str()));
  }
  
}


TVector3 BHistogramMaker::GausFitter( TH1D* h_temp ){

  Double_t xlow, xhigh, histmax;
  Int_t binlow, binhigh, binmax;
  TVector3 tempv3(0,0,0);

  if( h_temp->GetEntries() > 0 ){

  binmax= h_temp->GetMaximumBin();
  histmax = h_temp->GetMaximum();
  binlow = binmax;
  binhigh = binmax;

  Double_t percentofmax = 0.65;

  while( h_temp->GetBinContent(binhigh++) >= percentofmax*histmax && binhigh <= h_temp->GetNbinsX() ){};
  while( h_temp->GetBinContent(binlow--) >= percentofmax*histmax && binlow>=1 ) {};

  xlow=h_temp->GetBinLowEdge(binlow);
  xhigh=h_temp->GetBinLowEdge(binhigh+1);

  TF1 *ftemp = new TF1("myfits","gaus",xlow,xhigh);
  h_temp->Fit("myfits","RQ");

  //TF1 *ftemp = (TF1*)h_temp->GetListOfFunctions()->FindObject("gaus");

  Double_t ftemp_mean = ftemp->GetParameter(1);
  Double_t ftemp_stdev = ftemp->GetParameter(2);
  
  ftemp_fitval.push_back( ftemp_mean );
  //ftemp_fitval.push_back( ftemp_stdev );
  tempv3.SetX(ftemp_mean);
  tempv3.SetY(ftemp_stdev);
  tempv3.SetZ(0.0);
  //std::cout << ftemp_mean << " " << ftemp_stdev << std::endl;
  }
  else if ( h_temp->GetEntries() == 0 ){
    ///std::cout << " too few entries " << std::endl;
    ftemp_fitval.push_back(0.0);
    tempv3.SetX(-1000000000);
    tempv3.SetY(-1000000000);   
    tempv3.SetZ(0.0);
  }
  return tempv3;

}

void BHistogramMaker::GridPlot( std::string temptitle, std::vector< TH1D* > h_temps, int tempx, int tempy ){

  TCanvas *c2 = new TCanvas(Form("c2_%s", temptitle.c_str()),Form("c2_%s", temptitle.c_str()), tempx, tempy);
  c2->Divide( num_col, ceil(h_temps.size()/2.0) -1 );
  c2->SetTitle(Form("Resolution Grid for %s ", temptitle.c_str() ));

  //std::cout << ">> grid plt " << h_temps.size() << std::endl;
  int c_index = 1;
  for(std::vector< TH1D* >::iterator it = h_temps.begin(); it != h_temps.end(); ++it){
    //std::cout << " DRAWING BIN " << c_index << " TO CANVAS " << std::endl;
    c2->cd(c_index);
    (*it)->SetTitle(Form("Bin %d %s", c_index, temptitle.c_str()));
    (*it)->Draw();
    //if( (*it)->GetEntries() > 0 ){
    //gStyle->SetOptFit(1111);
    gStyle->SetOptStat(1);
      GausFitter(*it);
      //}
    c_index++;
  }  
  c2->SaveAs(Form("h2_phi_%s.pdf",temptitle.c_str()));
  
}

std::vector<double> BHistogramMaker::HistoToVector( TH1D *h_temp ){

  std::vector<double> temp_out;
  int max_bins = h_temp->GetNbinsX();
  for( int bin = 1; bin <= max_bins; bin++ ){
    double tempbinval = h_temp->GetBinContent(bin);
    temp_out.push_back(tempbinval);
  }
  return temp_out;
}

void BHistogramMaker::GraphPlotter(std::string temptitle, int cx, int cy ){

  TCanvas *c3 = new TCanvas(Form("c3_%s",temptitle.c_str()), Form("c3_%s", temptitle.c_str()), cx, cy ); 
  ftemp_fitval.erase(ftemp_fitval.begin());//remove the histogram that is unsliced

  //h_bin_centerX.erase(h_bin_centerX.begin());
  //std::cout << ">> graph plotter " << ftemp_fitval.size() << " " << h_bin_centerX.size() << std::endl;
  for( std::vector<double>::iterator it = ftemp_fitval.begin(); it != ftemp_fitval.end(); ++it ){
    //std::cout << *it << std::endl;
  }
  for( std::vector< double >::iterator it = h_bin_centerX.begin(); it != h_bin_centerX.end(); ++it){
    //std::cout << *it << std::endl;
  }
  
  TGraph *g_temp = new TGraph(h_bin_centerX.size(), &(h_bin_centerX[0]), &(ftemp_fitval[0]) );
  g_temp->SetTitle("");
  g_temp->SetMarkerStyle(20);
  g_temp->SetMarkerColor(kBlue + 2);
  g_temp->GetXaxis()->SetTitle(Form("%s", temptitle.c_str()) );
  g_temp->GetYaxis()->SetTitle(Form("#Delta %s_{bin avg}", temptitle.c_str()));
  g_temp->Draw("AP");
  c3->SaveAs(Form("g_%s.pdf",temptitle.c_str()));

  h_bin_centerX.clear();
  ftemp_fitval.clear();
}

void BHistogramMaker::GraphPlotter( std::string tempXtitle, std::string tempYtitle, int cx, int cy, std::vector<double> tempx, std::vector<double> tempy ){

  TCanvas *c4 = new TCanvas(Form("c4_%s",tempXtitle.c_str()), Form("c4_%s", tempXtitle.c_str()), cx, cy ); 

  TGraph *g_temp = new TGraph(tempx.size(), &(tempx[0]), &(tempy[0]) );
  g_temp->SetTitle("");
  g_temp->SetMarkerStyle(20);
  g_temp->SetMarkerColor(kBlue + 2);
  g_temp->GetXaxis()->SetTitle(Form("%s", tempXtitle.c_str()) );
  g_temp->GetYaxis()->SetTitle(Form("", tempYtitle.c_str()));
  g_temp->Draw("AP");

  c4->SaveAs(Form("g_%s.pdf", tempXtitle.c_str() ));

}
