#include "BHistogramMaker.h"
#include "binInfo.h"
#include "LoadPHI.h"
#include "EventPHI.h"
#include "TH1D.h"
#include "TChain.h"

#include <vector>
#include <iostream>
#include "TVector3.h"

int main( int argv, const char* argc[] ){

  std::string tempdir = (std::string)argc[1];
  int max_files = atoi(argc[2]);
  std::cout << tempdir << " " << max_files << std::endl;
  if( max_files < 1 ){ std::cout << ">> ERROR VALUE NEEDS TO BE LARGER THAN 0" << std::endl; exit(1); }
  std::cout << ">> CHAINING FILES TOGETHER " << std::endl;

  TChain *fchain = new TChain("tPhi");
  for( int i = 1; i <= max_files; i++ ){    
    //std::cout << ">> ADDING " << Form("%s/pid_phi_%d.root",tempdir.c_str(),i) << std::endl;
    fchain->Add(Form("%s/pid_phi_%d.root",tempdir.c_str() ,i));
  }


  binInfo *h_q2 = new binInfo();
  h_q2->SetLimits("Q2");
  h_q2->InitBinInfo("Q2", 0.0059);
  binInfo *h_xb = new binInfo();
  h_xb->SetLimits("Xb");
  h_xb->InitBinInfo("Xb", 0.002);

  BHistogramMaker *h2_q2 = new BHistogramMaker;
  BHistogramMaker *h2_xb = new BHistogramMaker;
  BHistogramMaker *h2_t = new BHistogramMaker;
  BHistogramMaker *h2_cm_phi = new BHistogramMaker;

  //CHECK CORRELATIONS BETWEEN VARIABLES
  BHistogramMaker *h2_q2_corr = new BHistogramMaker;
  BHistogramMaker *h2_xb_corr = new BHistogramMaker;
  BHistogramMaker *h2_t_corr = new BHistogramMaker;
  BHistogramMaker *h2_cm_phi_corr = new BHistogramMaker;

  h2_q2->InitHistogram2D("Q^{2} resolution; Q^{2}_{rec} [GeV^{2}]; Q^{2}_{rec} - Q^{2}_{mc}", 50, 0.0, 5.5, 50, -0.20, 0.20 );
  h2_xb->InitHistogram2D("Xb resolution; Xb_{rec}; Xb_{rec} - Xb_{mc}", 100, 0.0, 1.0, 100, -0.055, 0.055 );
  h2_t->InitHistogram2D("-t resolution; -t_{rec} [GeV]; (-t_{rec}) - (-t_{mc})", 100, 0.0, 3.5, 100, -0.50, 0.50 );
  h2_cm_phi->InitHistogram2D("#phi^{cm} resolution;  #phi^{cm}_{rec} [deg]; #phi^{cm}_{rec} - #phi^{cm}_{mc}", 100, -180.0, 180.0, 100, -50.0, 50.0 );

  h2_q2_corr->InitHistogram2D("Q^{2} Correlation; Q^{2}_{rec} [GeV^{2}]; Q^{2}_{mc} [GeV^{2}] ", 25, 0.0, 8.5, 25, 0.0, 8.5 );
  h2_xb_corr->InitHistogram2D("Xb Correlation; Xb_{rec}; Xb_{mc}", 25, 0.0, 1.0, 25, 0.0, 1.0 );
  h2_t_corr->InitHistogram2D("-t Correlation; -t_{rec} [GeV^{2}]; -t_{mc} [GeV^{2}]", 25, 0.5, 3.0, 25, 0.5, 3.0 );
  h2_cm_phi_corr->InitHistogram2D("#phi^{cm} Correlation; #phi^{cm}_{rec} [deg]; #phi^{cm}_{mc}", 50, -180.0, 180.0, 50, -180.0, 180.0 );
  



  LoadPHI event;
  event.MakeChain(fchain);
  event.Init();

  std::cout << " >> PROCEEDING TO ANALYZE " << fchain->GetEntries() << " ENTRIES " << std::endl;

  Long64_t num_entries = event.NumEntries();  
  //std::cout << ">> PROCESSING " << std::endl;
  for( Long64_t nn = 0; nn < num_entries; nn++ ){
    //std::cout << ">> GETTING ENTRY " << std::endl;
    event.GetEntry(nn);
    //std::cout << " ENTRY FOUND " << std::endl;

    EventPHI phievent = event.GetEvent();
	
    double q2 = -phievent.Q2;
    double t = phievent.T;
    double xb = phievent.Xbj;
    double cm_phi = phievent.CM_phi;

    double mc_q2 = -phievent.mc_Q2;
    double mc_t = phievent.mc_T;
    double mc_xb = phievent.mc_Xbj;
    double mc_cm_phi = phievent.mc_CM_phi;

    //std::cout << ">> " << cm_phi << " " << mc_cm_phi << std::endl;
    double del_q2 = (q2-mc_q2);
    double del_t = (t-mc_t);
    double del_xb = (xb-mc_xb);
    double del_cm_phi = (cm_phi-mc_cm_phi);

    h2_q2->FillHistogram(q2, del_q2);
    h2_t->FillHistogram(t, del_t);
    h2_xb->FillHistogram(xb, del_xb);
    h2_cm_phi->FillHistogram(cm_phi, del_cm_phi);

    h2_q2_corr->FillHistogram(q2, mc_q2);
    h2_xb_corr->FillHistogram(xb, mc_xb);
    h2_t_corr->FillHistogram(t, mc_t);
    h2_cm_phi_corr->FillHistogram(cm_phi, mc_cm_phi);


    h_q2->Fill(q2, mc_q2);
    h_xb->Fill(xb, mc_xb);

    
  }

  std::vector<double> q2_limits, q2_bin_diff;  
  std::vector<double> xb_limits, xb_bin_diff;
  std::vector<double> t_limits, t_bin_diff;
  std::vector<double> cm_phi_limits, cm_phi_bin_diff;

  for( int i = 0; i <= 6; i++ ){
    q2_limits.push_back(0.55 * i );
  }
  
  for( int i = 0; i <= 6; i++ ){
    xb_limits.push_back(0.16666 * i );
  }

  for( int i = 0; i <= 6; i++ ){
    t_limits.push_back(0.583333 * i );
  }

  for( int i = -3; i <= 3; i++ ){
    cm_phi_limits.push_back(60.0 * i );
  }
  std::vector< std::vector< double > > kinvars;
  kinvars.push_back(q2_limits);
  kinvars.push_back(xb_limits);
  kinvars.push_back(t_limits);
  kinvars.push_back(cm_phi_limits);

  ///

  for( int i = 1; i < xb_limits.size() - 1; i++ ){
    xb_bin_diff.push_back(xb_limits[i] - xb_limits[i-1]);
  }

  for( int i = 1; i <= t_limits.size() - 1; i++ ){
    t_bin_diff.push_back(t_limits[i] - t_limits[i-1]);
  }

  for( int i = 1; i <= q2_limits.size() - 1; i++ ){
    q2_bin_diff.push_back(q2_limits[i] - q2_limits[i-1]);
    //    std::cout << " >> bin range " << q2_limits[i] << " -  " <<  q2_limits[i-1] << " " << q2_limits[i] - q2_limits[i-1] << std::endl;
  }

  for( int i = 1; i <= cm_phi_limits.size() - 1; i++ ){
    cm_phi_bin_diff.push_back(cm_phi_limits[i] - cm_phi_limits[i-1]);
  }

  //std::cout << " >> VECTOR SIZES " << q2_limits.size() << " " << xb_limits.size() << " " << t_limits.size() << " " << cm_phi_limits.size() << std::endl;
  std::vector< TH1D* > h_q2s = h2_q2->Project2D("Q2", q2_limits);
  std::vector< TH1D* > h_xbs = h2_xb->Project2D("Xbj", xb_limits);
  std::vector< TH1D* > h_ts = h2_t->Project2D("-t", t_limits);
  std::vector< TH1D* > h_cm_phis = h2_cm_phi->Project2D("cm_phi", cm_phi_limits);

  h2_q2->AxisPlotOptions("center", 1, 0.05);
  h2_t->AxisPlotOptions("center", 1, 0.05);
  h2_xb->AxisPlotOptions("center", 1, 0.05);
  h2_cm_phi->AxisPlotOptions("center", 1, 0.15);

  h2_q2_corr->AxisPlotOptions("center", 0, 0.05);
  h2_xb_corr->AxisPlotOptions("center", 0, 0.05);
  h2_t_corr->AxisPlotOptions("center", 0, 0.05);
  h2_cm_phi_corr->AxisPlotOptions("center", 0, 0.05);
  //std::cout << " RESOLUTIONS AND MINIMUM BINNING " << std::endl;  

  h2_q2->Plot("Q2",2, 0.16, 0.125, 0.125);
  h2_t->Plot("t",2, 0.16, 0.125, 0.125);
  h2_xb->Plot("xb",2, 0.16, 0.125, 0.125);
  h2_cm_phi->Plot("cm_phi",2, 0.16, 0.125, 0.125);

  h2_q2_corr->Plot("Q2_corr",2, 0.16, 0.13, 0.13);
  h2_xb_corr->Plot("Xb_corr",2, 0.16, 0.13, 0.13);
  h2_t_corr->Plot("t_corr",2, 0.16, 0.13, 0.13);
  h2_cm_phi_corr->Plot("cm_phi_corr",2, 0.16, 0.13, 0.13);

  h2_q2->GridPlot("grid_Q2_b100",h_q2s, 900, 900 );
  h2_q2->GraphPlotter("Q2",800,800);
 
  h2_xb->GridPlot("grid_xb_b100",h_xbs, 900, 900 );
  h2_xb->GraphPlotter("Xb",800,800);
  
  h2_t->GridPlot("grid_t_b100",h_ts, 900, 900 );
  h2_t->GraphPlotter("t",800,800);

  h2_cm_phi->GridPlot("grid_cm_phi_b100",h_cm_phis, 900, 900 );
  h2_cm_phi->GraphPlotter("phi_cm",800,800);
  
  std::cout << " >> RECOMMENDED BINNING " << std::endl;
  std::vector<double> q2_sigma;
  int h_counter = 0;//vec.erase(vec.begin()
  //std::cout << "h_q2s " <<   << std::endl;
  //h_q2s.erase(h_q2s.begin());
  std::cout << "h_q2s " << h_q2s.size() << std::endl;
  TVector3 fit_res1 = h2_q2->GausFitter(h_q2s.front());
  TVector3 fit_res2 = h2_t->GausFitter(h_ts.front());
  TVector3 fit_res3 = h2_xb->GausFitter(h_xbs.front());
  TVector3 fit_res4 = h2_cm_phi->GausFitter(h_cm_phis.front());
  std::vector< double > fits_val;
  fits_val.push_back(fit_res1.Y());
  fits_val.push_back(fit_res2.Y());
  fits_val.push_back(fit_res3.Y());
  fits_val.push_back(fit_res4.Y());
  for( int i = 0; i <= 3; i++ ){
    std::vector<double> tempv =  kinvars[i];
    double kin_range = tempv.back() - tempv.front();
    std::cout << " >> KIN RANGE IS " << kin_range << std::endl; 
    std::cout << " >> NUMBER OF SUGGESTED BINS ACROSS ENTIRE RANGE IS " << kin_range/fits_val[i] << std::endl;
  }

  std::cout << " DOING BIN PURITY CALCULATIONS FOR binInfo " << std::endl;
  h_q2->CalcPurity();
   h_xb->CalcPurity(); 
  h_q2->Show("Q2","[GeV^2]",2100,700);
  h_xb->Show("Xb"," ",2100,700);


  std::cout << ">> COMPLETE " << std::endl;
  return 0;
}

