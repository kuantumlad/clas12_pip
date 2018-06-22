#ifndef BHistogramMaker_hh
#define BHistogramMaker_hh

#include "TH1D.h"
#include "TH2D.h"
#include "TVector3.h"
#include "TMath.h"

#include <string>
#include <cmath>
#include <vector>
#include <map>

class BHistogramMaker{

 public:
  BHistogramMaker();
  ~BHistogramMaker();

  int num_col;

  TH1D *h_temp;
  TH2D *h2_temp;

  void InitHistogram1D(std::string temptitle, int tempbins, double tempmin, double tempmax );
  void InitHistogram2D(std::string temptitle, int, double , double, int , double, double);
  void FillHistogram( double, double );
  void FillHistogram( double );
  void AxisPlotOptions(std::string, int, double );
  void GridPlot( std::string, std::vector< TH1D* > ,int, int );
  void GraphPlotter( std::string, int, int );
  void GraphPlotter( std::string, std::string, int, int, std::vector<double>, std::vector<double> );
  TH1D* GetHisto1();
  TH2D* GetHisto2();
  void Plot(std::string, int, double, double, double);

  TVector3 GausFitter( TH1D* );
  std::vector< TH1D* > Project2D(std::string, std::vector<double>);
  std::vector<double> HistoToVector( TH1D* );

 public:
  std::vector<double> h_bin_centerX;
  std::vector<double> h_bin_centerY;
  std::vector<double> ftemp_fitval;
  std::vector<double> x_range;    
};

  
#endif
