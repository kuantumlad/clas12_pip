#define loadphi_cxx
#include "LoadPHI.h"
#include "EventPHI.h"

#include "TChain.h"
#include "TTree.h"
#include "TBranch.h"
#include "TString.h"
#include "TSystemDirectory.h"
#include "TList.h"
#include "TSystemDirectory.h"
#include "TRegexp.h"

LoadPHI::LoadPHI(){

  fChain = new TChain("tPhi");
  if( fChain == 0 ){
    std::cout << " ERROR WITH tPHI " << std::endl;
  }
}

LoadPHI::~LoadPHI(){

  fChain->Delete();

}

void LoadPHI::Init(){

  fChain->SetBranchAddress("RunNumber", &event.RunNumber, &b_RunNumber);
  //fChain->SetBranchAddress("PeriodID", &event.PeriodID, &b_PeriodID);
  //fChain->SetBranchAddress("TargetType", &event.TargetType, &b_TargetType);
  fChain->SetBranchAddress("SolPol", &event.SolPol, &b_SolPol);
  fChain->SetBranchAddress("TorPol", &event.TorPol, &b_TorPol);
  fChain->SetBranchAddress("pol_sign", &event.pol_sign, &b_pol_sign);
  fChain->SetBranchAddress("wp_status", &event.wp_status, &b_wp_status);
  fChain->SetBranchAddress("fc0", &event.fc0, &b_fc0);
  fChain->SetBranchAddress("fc1", &event.fc1, &b_fc1);
  fChain->SetBranchAddress("b_energy", &event.b_energy, &b_b_energy);
  fChain->SetBranchAddress("helicity", &event.helicity, &b_helicity);
  fChain->SetBranchAddress("TarPol", &event.TarPol, &b_TarPol);
  fChain->SetBranchAddress("Q2", &event.Q2, &b_Q2);
  fChain->SetBranchAddress("Xbj", &event.Xbj, &b_Xbj);
  fChain->SetBranchAddress("W", &event.W, &b_W);
  fChain->SetBranchAddress("T", &event.T, &b_T);
  fChain->SetBranchAddress("CM_phi", &event.CM_phi, &b_CM_phi);
  fChain->SetBranchAddress("CM_theta", &event.CM_theta, &b_CM_theta);
  fChain->SetBranchAddress("fel_p", &event.fel_p, &b_fel_p);
  fChain->SetBranchAddress("fel_theta", &event.fel_theta, &b_fel_theta);
  fChain->SetBranchAddress("fel_phi", &event.fel_phi, &b_fel_phi);
  fChain->SetBranchAddress("fpr_p", &event.fpr_p, &b_fpr_p);
  fChain->SetBranchAddress("fpr_theta", &event.fpr_theta, &b_fpr_theta);
  fChain->SetBranchAddress("fpr_phi", &event.fpr_phi, &b_fpr_phi);
  fChain->SetBranchAddress("fkp_p", &event.fkp_p, &b_fkp_p);
  fChain->SetBranchAddress("fkp_theta", &event.fkp_theta, &b_fkp_theta);
  fChain->SetBranchAddress("fkp_phi", &event.fkp_phi, &b_fkp_phi);
  fChain->SetBranchAddress("fkm_p", &event.fkm_p, &b_fkm_p);
  fChain->SetBranchAddress("fkm_theta", &event.fkm_theta, &b_fkm_theta);
  fChain->SetBranchAddress("fkm_phi", &event.fkm_phi, &b_fkm_phi);
  fChain->SetBranchAddress("mc_Q2", &event.mc_Q2, &b_mc_Q2);
  fChain->SetBranchAddress("mc_Xbj", &event.mc_Xbj, &b_mc_Xbj);
  fChain->SetBranchAddress("mc_W", &event.mc_W, &b_mc_W);
  fChain->SetBranchAddress("mc_T", &event.mc_T, &b_mc_T);
  fChain->SetBranchAddress("mc_CM_phi", &event.mc_CM_phi, &b_mc_CM_phi);
  fChain->SetBranchAddress("mc_CM_theta", &event.mc_CM_theta, &b_mc_CM_theta);
  fChain->SetBranchAddress("mc_fel_p", &event.mc_fel_p, &b_mc_fel_p);
  fChain->SetBranchAddress("mc_fel_theta", &event.mc_fel_theta, &b_mc_fel_theta);
  fChain->SetBranchAddress("mc_fel_phi", &event.mc_fel_phi, &b_mc_fel_phi);
  fChain->SetBranchAddress("mc_fpr_p", &event.mc_fpr_p, &b_mc_fpr_p);
  fChain->SetBranchAddress("mc_fpr_theta", &event.mc_fpr_theta, &b_mc_fpr_theta);
  fChain->SetBranchAddress("mc_fpr_phi", &event.mc_fpr_phi, &b_mc_fpr_phi);
  fChain->SetBranchAddress("mc_fkp_p", &event.mc_fkp_p, &b_mc_fkp_p);
  fChain->SetBranchAddress("mc_fkp_theta", &event.mc_fkp_theta, &b_mc_fkp_theta);
  fChain->SetBranchAddress("mc_fkp_phi", &event.mc_fkp_phi, &b_mc_fkp_phi);
  fChain->SetBranchAddress("mc_fkm_p", &event.mc_fkm_p, &b_mc_fkm_p);
  fChain->SetBranchAddress("mc_fkm_theta", &event.mc_fkm_theta, &b_mc_fkm_theta);
  fChain->SetBranchAddress("mc_fkm_phi", &event.mc_fkm_phi, &b_mc_fkm_phi);


}

void LoadPHI::MakeChain( const char* tempdirname, int filestoadd ){

  Int_t filecounter = 0;
  const char *ext = ".root";
  TSystemDirectory dir(tempdirname, tempdirname);
  TList *files = dir.GetListOfFiles();
  if (files){
    TSystemFile *file;
    TString filename;
    TIter next(files);
      while((file=(TSystemFile*)next()) && (filecounter < filestoadd)){
  	filename = file->GetName();
	TString filetochain = (std::string)tempdirname + filename;
  	if( !file->IsDirectory() && filename.EndsWith(ext) ){
  	  std::cout << " >> Attaching file " << filename << " to fChain " << std::endl;
  	  fChain->Add(filetochain);
  	  filecounter++;
  	}
      }
  }
  std::cout << " fChain successfully created " << std::endl;



}

void LoadPHI::MakeChain( TChain *tempchain ){

  fChain = tempchain;
  if( tempchain == 0 ){std::cout << " ERROR " << std::endl;}
}
