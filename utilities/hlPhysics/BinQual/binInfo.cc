#define binInfo_cxx

#include "TMath.h"
#include "TCanvas.h"
#include "TROOT.h"
#include "TH1D.h"
#include "TH2D.h"
#include "TGraph.h"
#include "TGraphErrors.h"
#include "TAxis.h"

#include "binInfo.h"

#include <iostream>
#include <string>
#include <vector>
#include <math.h>

binInfo::binInfo(){

  max_histograms = 3;

  for( int i = 0; i < max_histograms; i++){
    v_multiplier.push_back( 2*pow(10.0, i) );
  }
}


binInfo::~binInfo(){


}


void binInfo::SetLimits( std::string kinvar ){
  std::cout << " >> SETTING LIMITS " << std::endl;

  if( kinvar == "Q2" ){
    min = 0.0;
    max = 10.5;
  }
  if( kinvar == "Xb" ){
    min = 0.0;
    max = 1.0;
  }
  if( kinvar == "t" ){
    min = 0.0;
    max = 4.0;
  }
  if( kinvar == "W" ){
    min = 1.0;
    max = 5.0;
  }
   
}

void binInfo::InitBinInfo( std::string kinvar, double resolution ){

  std::cout << " >> SETTING BIN INFO " << std::endl;
  std::cout << " >> MIN AND MAX " << min << " " << max << std::endl;
  
  for( int i = 1; i <= max_histograms; i++ ){
    int bins = floor( (max - min) / (resolution * v_multiplier[i-1]) );
    std::cout << " >> BINS at lvl " << i << " " << bins << std::endl;
    v_rec.push_back(new TH1D(Form("h_bin_rec_%s_lvl_%d",kinvar.c_str(),i),Form("h_bin_rec_%s_lvl_%d",kinvar.c_str(), i),bins, min, max ) );
  }

  for( int i = 1; i <= max_histograms; i++ ){
    int bins = floor( (max - min) / (resolution * v_multiplier[i-1]) );
    v_gen.push_back(new TH1D(Form("h_bin_gen_%s_lvl_%d",kinvar.c_str(),i),Form("h_bin_gen_%s_lvl_%d",kinvar.c_str(), i),bins, min, max ) );
  }

  //for( int i = 1; i <= max_histograms; i++){
    //int bins = floor( (max - min) / resolution ) * v_multiplier[i-1];
  //  v_pur.push_back(new TGraphErrors());//new TH1D(Form("h_bin_purity_lvl_%d",i),Form("h_bin_purity_lvl_%d"),bins, min, max ) );
  //}  


}


void binInfo::Fill( double rec, double gen ){

  for( std::vector<TH1D*>::iterator it = v_rec.begin(); it != v_rec.end(); ++it ){
    int rec_bin = (*it)->FindBin(rec);
    int gen_bin = (*it)->FindBin(gen);

    if( rec_bin == gen_bin ){     
      (*it)->Fill(rec);
    }
  }

  for( std::vector<TH1D*>::iterator it = v_gen.begin(); it != v_gen.end(); ++it ){
    (*it)->Fill(gen);
  }

}


void binInfo::CalcPurity(){
  std::cout << " >> CALCULATING PURITY FROM binInfo " << std::endl;
  std::vector< double > binpurX, binpurY, binerrX, binerrY;
  
  for( int i = 0; i < max_histograms; i++ ){
    int total_bins = v_rec[i]->GetXaxis()->GetNbins();
    std::cout << " >> GET  BINS " << total_bins << std::endl;   
    for( int bin = 1; bin <= total_bins; bin++ ){
      //std::cout << " >> BIN " << bin << std::endl;
      double recbinY = v_rec[i]->GetBinContent(bin);
      double genbinY = v_gen[i]->GetBinContent(bin);
      
      double recbinX = v_rec[i]->GetBinCenter(bin);
      double genbinX = v_gen[i]->GetBinCenter(bin);     
      std::cout << " >> CENTER REC " << recbinX << " " << " GEN " << genbinX << std::endl;
      std::cout << " >> VALUE REC " << recbinY << " " << " GEN " << genbinY << std::endl;
      binerrX.push_back(0);
      binerrY.push_back(0);
      if( genbinY == 0 ) continue;
      double purity = recbinY/genbinY;
     
      binpurX.push_back(recbinX);
      binpurY.push_back(purity);

    }
    
    v_pur.push_back(new TGraphErrors( binpurX.size(), &(binpurX[0]), &(binpurY[0]), &(binerrX[0]), &(binerrY[0]) ) );
    binpurX.clear(); binpurY.clear(); binerrX.clear(); binerrY.clear();
  }
  std::cout << " v_rec size " << v_rec.size() << std::endl;
}

void binInfo::Show(std::string kinvar, std::string units, int x, int y){


  std::cout << " >> SHOWING PLOTS NOW " << std::endl;
  TCanvas *c_temp = new TCanvas(Form("c_%s",kinvar.c_str()),Form("c_%s",kinvar.c_str()), x, y);
  c_temp->Divide(max_histograms,1);
  for( int i = 1; i <= max_histograms; i++ ){
    c_temp->cd(i);    
    v_pur[i-1]->SetName(Form("g_purity_%s_lvl_%d", kinvar.c_str(), i ));
    v_pur[i-1]->SetTitle(Form("Bin Purity for %s at %d #sigma",kinvar.c_str(), v_multiplier[i-1]));
    v_pur[i-1]->GetXaxis()->SetTitle(Form("%s %s", kinvar.c_str(), units.c_str()));
    v_pur[i-1]->GetYaxis()->SetTitle(Form("Purity"));
    v_pur[i-1]->GetXaxis()->CenterTitle();
    v_pur[i-1]->GetYaxis()->CenterTitle();

    v_pur[i-1]->SetMarkerStyle(20);
    v_pur[i-1]->SetMarkerSize(0.7);
    v_pur[i-1]->SetMarkerColor(kBlue+2);
    v_pur[i-1]->Draw("AP");
    
  }
  
  c_temp->SaveAs(Form("bin_purity_%s.pdf", kinvar.c_str()));

}
