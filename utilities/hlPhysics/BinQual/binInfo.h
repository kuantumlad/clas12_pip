#ifndef bininfo_hh
#define bininfo_hh

#include "TH1D.h"
#include "TMath.h"
#include "TCanvas.h"
#include "TROOT.h"
#include "TStyle.h"
#include "TH2D.h"
#include "TGraph.h"
#include "TGraphErrors.h"

#include <vector>
#include <string>

class binInfo {

 public:
  binInfo();
  ~binInfo();

 public:
  TH1D *h_temp;
    
  void InitBinInfo(std::string, double);
  void SetBinSize(std::string);
  void SetLimits(std::string);
  void Fill(double, double );
  void CalcPurity();
  void Show(std::string, std::string, int, int);


  std::vector< TH1D* > v_rec, v_gen;
  std::vector<int> v_multiplier;
  std::vector< TGraphErrors* > v_pur;
  double min, max;
  int max_histograms;
  

};
#endif
