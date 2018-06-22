#ifndef eventphi_h
#define eventphi_h

#include "TTree.h"
#include "TFile.h"


class EventPHI {


 public:
  EventPHI();
  ~EventPHI();

 public:
   Double_t        RunNumber;
   TString         *PeriodID;
   TString         *TargetType;
   Double_t        SolPol;
   Double_t        TorPol;
   Double_t        pol_sign;
   Double_t        wp_status;
   Double_t        fc0;
   Double_t        fc1;
   Double_t        b_energy;
   Double_t        helicity;
   Double_t        TarPol;
   Double_t        Q2;
   Double_t        Xbj;
   Double_t        W;
   Double_t        T;
   Double_t        CM_phi;
   Double_t        CM_theta;
   Double_t        fel_p;
   Double_t        fel_theta;
   Double_t        fel_phi;
   Double_t        fpr_p;
   Double_t        fpr_theta;
   Double_t        fpr_phi;
   Double_t        fkp_p;
   Double_t        fkp_theta;
   Double_t        fkp_phi;
   Double_t        fkm_p;
   Double_t        fkm_theta;
   Double_t        fkm_phi;
   Double_t        mc_Q2;
   Double_t        mc_Xbj;
   Double_t        mc_W;
   Double_t        mc_T;
   Double_t        mc_CM_phi;
   Double_t        mc_CM_theta;
   Double_t        mc_fel_p;
   Double_t        mc_fel_theta;
   Double_t        mc_fel_phi;
   Double_t        mc_fpr_p;
   Double_t        mc_fpr_theta;
   Double_t        mc_fpr_phi;
   Double_t        mc_fkp_p;
   Double_t        mc_fkp_theta;
   Double_t        mc_fkp_phi;
   Double_t        mc_fkm_p;
   Double_t        mc_fkm_theta;
   Double_t        mc_fkm_phi;




};
#endif
