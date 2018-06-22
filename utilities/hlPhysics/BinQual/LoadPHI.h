#ifndef loadPHI_h
#define loadPHI_h

#include "TROOT.h"
#include "TChain.h"
#include "TString.h"
#include "TFile.h"
#include "TBranch.h"

#include "EventPHI.h"
#include <iostream>

class LoadPHI {

 public:
  LoadPHI();
  ~LoadPHI();

 public:
  TChain *fChain;
  
  EventPHI event;
  
  TBranch        *b_RunNumber;   //!
  //TBranch        *b_PeriodID;   //!
  //TBranch        *b_TargetType;   //!
  TBranch        *b_SolPol;   //!
  TBranch        *b_TorPol;   //!
  TBranch        *b_pol_sign;   //!
  TBranch        *b_wp_status;   //!
  TBranch        *b_fc0;   //!
  TBranch        *b_fc1;   //!
  TBranch        *b_b_energy;   //!
  TBranch        *b_helicity;   //!
  TBranch        *b_TarPol;   //!
  TBranch        *b_Q2;   //!
  TBranch        *b_Xbj;   //!
  TBranch        *b_W;   //!
  TBranch        *b_T;   //!
  TBranch        *b_CM_phi;   //!
  TBranch        *b_CM_theta;   //!
  TBranch        *b_fel_p;   //!
  TBranch        *b_fel_theta;   //!
  TBranch        *b_fel_phi;   //!
  TBranch        *b_fpr_p;   //!
  TBranch        *b_fpr_theta;   //!
  TBranch        *b_fpr_phi;   //!
  TBranch        *b_fkp_p;   //!
  TBranch        *b_fkp_theta;   //!
  TBranch        *b_fkp_phi;   //!
  TBranch        *b_fkm_p;   //!
  TBranch        *b_fkm_theta;   //!
  TBranch        *b_fkm_phi;   //!
  TBranch        *b_mc_Q2;   //!
  TBranch        *b_mc_Xbj;   //!
  TBranch        *b_mc_W;   //!
  TBranch        *b_mc_T;   //!
  TBranch        *b_mc_CM_phi;   //!
  TBranch        *b_mc_CM_theta;   //!
  TBranch        *b_mc_fel_p;   //!
  TBranch        *b_mc_fel_theta;   //!
  TBranch        *b_mc_fel_phi;   //!
  TBranch        *b_mc_fpr_p;   //!
  TBranch        *b_mc_fpr_theta;   //!
  TBranch        *b_mc_fpr_phi;   //!
  TBranch        *b_mc_fkp_p;   //!
  TBranch        *b_mc_fkp_theta;   //!
  TBranch        *b_mc_fkp_phi;   //!
  TBranch        *b_mc_fkm_p;   //!
  TBranch        *b_mc_fkm_theta;   //!
  TBranch        *b_mc_fkm_phi;   //!

 public:
  void Init();
  void MakeChain(const char*, int );
  void MakeChain(TChain*);
  EventPHI GetEvent(){ return event; }
  int NumEntries() {return fChain->GetEntries();}
  void GetEntry(int ientry) { fChain->GetEntry(ientry); }

};
#endif
