//////////////////////////////////////////////////////////
// This class has been automatically generated on
// Wed Oct 18 15:17:04 2017 by ROOT version 6.10/02
// from TChain tPhi/
//////////////////////////////////////////////////////////

#ifndef tPhi_h
#define tPhi_h

#include <TROOT.h>
#include <TChain.h>
#include <TFile.h>

// Header file for the classes stored in the TTree if any.
#include "TString.h"

class tPhi {
public :
   TTree          *fChain;   //!pointer to the analyzed TTree or TChain
   Int_t           fCurrent; //!current Tree number in a TChain

// Fixed size dimensions of array or collections stored in the TTree if any.

   // Declaration of leaf types
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

   // List of branches
   TBranch        *b_RunNumber;   //!
   TBranch        *b_PeriodID;   //!
   TBranch        *b_TargetType;   //!
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

   tPhi(TTree *tree=0);
   virtual ~tPhi();
   virtual Int_t    Cut(Long64_t entry);
   virtual Int_t    GetEntry(Long64_t entry);
   virtual Long64_t LoadTree(Long64_t entry);
   virtual void     Init(TTree *tree);
   virtual void     Loop();
   virtual Bool_t   Notify();
   virtual void     Show(Long64_t entry = -1);
};

#endif

#ifdef tPhi_cxx
tPhi::tPhi(TTree *tree) : fChain(0) 
{
// if parameter tree is not specified (or zero), connect the file
// used to generate this class and read the Tree.
   if (tree == 0) {

#ifdef SINGLE_TREE
      // The following code should be used if you want this class to access
      // a single tree instead of a chain
      TFile *f = (TFile*)gROOT->GetListOfFiles()->FindObject("Memory Directory");
      if (!f || !f->IsOpen()) {
         f = new TFile("Memory Directory");
      }
      f->GetObject("tPhi",tree);

#else // SINGLE_TREE

      // The following code should be used if you want this class to access a chain
      // of trees.
      TChain * chain = new TChain("tPhi","");
      chain->Add("/volatile/halla/sbs/bclary/clas12Analysis/clary_phiPID/root_pid/pid_phi_36.root/tPhi");
      tree = chain;
#endif // SINGLE_TREE

   }
   Init(tree);
}

tPhi::~tPhi()
{
   if (!fChain) return;
   delete fChain->GetCurrentFile();
}

Int_t tPhi::GetEntry(Long64_t entry)
{
// Read contents of entry.
   if (!fChain) return 0;
   return fChain->GetEntry(entry);
}
Long64_t tPhi::LoadTree(Long64_t entry)
{
// Set the environment to read one entry
   if (!fChain) return -5;
   Long64_t centry = fChain->LoadTree(entry);
   if (centry < 0) return centry;
   if (fChain->GetTreeNumber() != fCurrent) {
      fCurrent = fChain->GetTreeNumber();
      Notify();
   }
   return centry;
}

void tPhi::Init(TTree *tree)
{
   // The Init() function is called when the selector needs to initialize
   // a new tree or chain. Typically here the branch addresses and branch
   // pointers of the tree will be set.
   // It is normally not necessary to make changes to the generated
   // code, but the routine can be extended by the user if needed.
   // Init() will be called many times when running on PROOF
   // (once per file to be processed).

   // Set object pointer
   PeriodID = 0;
   TargetType = 0;
   // Set branch addresses and branch pointers
   if (!tree) return;
   fChain = tree;
   fCurrent = -1;
   fChain->SetMakeClass(1);

   fChain->SetBranchAddress("RunNumber", &RunNumber, &b_RunNumber);
   fChain->SetBranchAddress("PeriodID", &PeriodID, &b_PeriodID);
   fChain->SetBranchAddress("TargetType", &TargetType, &b_TargetType);
   fChain->SetBranchAddress("SolPol", &SolPol, &b_SolPol);
   fChain->SetBranchAddress("TorPol", &TorPol, &b_TorPol);
   fChain->SetBranchAddress("pol_sign", &pol_sign, &b_pol_sign);
   fChain->SetBranchAddress("wp_status", &wp_status, &b_wp_status);
   fChain->SetBranchAddress("fc0", &fc0, &b_fc0);
   fChain->SetBranchAddress("fc1", &fc1, &b_fc1);
   fChain->SetBranchAddress("b_energy", &b_energy, &b_b_energy);
   fChain->SetBranchAddress("helicity", &helicity, &b_helicity);
   fChain->SetBranchAddress("TarPol", &TarPol, &b_TarPol);
   fChain->SetBranchAddress("Q2", &Q2, &b_Q2);
   fChain->SetBranchAddress("Xbj", &Xbj, &b_Xbj);
   fChain->SetBranchAddress("W", &W, &b_W);
   fChain->SetBranchAddress("T", &T, &b_T);
   fChain->SetBranchAddress("CM_phi", &CM_phi, &b_CM_phi);
   fChain->SetBranchAddress("CM_theta", &CM_theta, &b_CM_theta);
   fChain->SetBranchAddress("fel_p", &fel_p, &b_fel_p);
   fChain->SetBranchAddress("fel_theta", &fel_theta, &b_fel_theta);
   fChain->SetBranchAddress("fel_phi", &fel_phi, &b_fel_phi);
   fChain->SetBranchAddress("fpr_p", &fpr_p, &b_fpr_p);
   fChain->SetBranchAddress("fpr_theta", &fpr_theta, &b_fpr_theta);
   fChain->SetBranchAddress("fpr_phi", &fpr_phi, &b_fpr_phi);
   fChain->SetBranchAddress("fkp_p", &fkp_p, &b_fkp_p);
   fChain->SetBranchAddress("fkp_theta", &fkp_theta, &b_fkp_theta);
   fChain->SetBranchAddress("fkp_phi", &fkp_phi, &b_fkp_phi);
   fChain->SetBranchAddress("fkm_p", &fkm_p, &b_fkm_p);
   fChain->SetBranchAddress("fkm_theta", &fkm_theta, &b_fkm_theta);
   fChain->SetBranchAddress("fkm_phi", &fkm_phi, &b_fkm_phi);
   fChain->SetBranchAddress("mc_Q2", &mc_Q2, &b_mc_Q2);
   fChain->SetBranchAddress("mc_Xbj", &mc_Xbj, &b_mc_Xbj);
   fChain->SetBranchAddress("mc_W", &mc_W, &b_mc_W);
   fChain->SetBranchAddress("mc_T", &mc_T, &b_mc_T);
   fChain->SetBranchAddress("mc_CM_phi", &mc_CM_phi, &b_mc_CM_phi);
   fChain->SetBranchAddress("mc_CM_theta", &mc_CM_theta, &b_mc_CM_theta);
   fChain->SetBranchAddress("mc_fel_p", &mc_fel_p, &b_mc_fel_p);
   fChain->SetBranchAddress("mc_fel_theta", &mc_fel_theta, &b_mc_fel_theta);
   fChain->SetBranchAddress("mc_fel_phi", &mc_fel_phi, &b_mc_fel_phi);
   fChain->SetBranchAddress("mc_fpr_p", &mc_fpr_p, &b_mc_fpr_p);
   fChain->SetBranchAddress("mc_fpr_theta", &mc_fpr_theta, &b_mc_fpr_theta);
   fChain->SetBranchAddress("mc_fpr_phi", &mc_fpr_phi, &b_mc_fpr_phi);
   fChain->SetBranchAddress("mc_fkp_p", &mc_fkp_p, &b_mc_fkp_p);
   fChain->SetBranchAddress("mc_fkp_theta", &mc_fkp_theta, &b_mc_fkp_theta);
   fChain->SetBranchAddress("mc_fkp_phi", &mc_fkp_phi, &b_mc_fkp_phi);
   fChain->SetBranchAddress("mc_fkm_p", &mc_fkm_p, &b_mc_fkm_p);
   fChain->SetBranchAddress("mc_fkm_theta", &mc_fkm_theta, &b_mc_fkm_theta);
   fChain->SetBranchAddress("mc_fkm_phi", &mc_fkm_phi, &b_mc_fkm_phi);
   Notify();
}

Bool_t tPhi::Notify()
{
   // The Notify() function is called when a new file is opened. This
   // can be either for a new TTree in a TChain or when when a new TTree
   // is started when using PROOF. It is normally not necessary to make changes
   // to the generated code, but the routine can be extended by the
   // user if needed. The return value is currently not used.

   return kTRUE;
}

void tPhi::Show(Long64_t entry)
{
// Print contents of entry.
// If entry is not specified, print current entry
   if (!fChain) return;
   fChain->Show(entry);
}
Int_t tPhi::Cut(Long64_t entry)
{
// This function may be called from Loop.
// returns  1 if entry is accepted.
// returns -1 otherwise.
   return 1;
}
#endif // #ifdef tPhi_cxx
