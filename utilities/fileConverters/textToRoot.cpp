#include "TTree.h"
#include "TMath.h"
#include "TBranch.h"
#include "TString.h"
#include "TLorentzVector.h"
#include "TFile.h"
#include "TSystemFile.h"
#include "TSystemDirectory.h"
#include "TROOT.h"
#include "TBrowser.h"

#include <iostream>
#include <fstream>
#include <string>
#include <sstream>

void textToRoot( char const* tempdirname ){

  //READ FILE IN AND ASSIGN TXT INPUTS TO A TTREE
  const char* ext = ".txt";
  const char* rootext = ".root";
  std::string root_out_dir = "/volatile/halla/sbs/bclary/clas12Analysis/clary_phiPID/root_pid/";

  TSystemDirectory dir( tempdirname, tempdirname );
  TList *files = dir.GetListOfFiles();
  TSystemFile *file;
  if( files ){
    TIter next(files);
    while( (file = (TSystemFile*)next()) ){
	TString fname = file->GetName();
	TString fileIn = (std::string)tempdirname + fname;
	if( !file->IsDirectory() && fname.EndsWith(ext) ){
	  const char* tempfile = fileIn;
	  std::size_t lastdot = ((std::string)fname).find_last_of(".");

	  //OUTPUT ROOT FILE NAME
	  TString frootname = ((std::string)fname).substr(0,lastdot);
	  frootname = root_out_dir + frootname + (std::string)rootext;
	  TFile *frootout = new TFile(frootname,"recreate");

	  std::cout << " >> CONVERTING TXT FILE " << fname << " TO ROOT FILE " << frootout->GetName() << std::endl;

	  TTree *tPhi = new TTree("tPhi","my hipo to root tree");
	  tPhi->SetDirectory(0);
	 
	  Double_t RunNumber = 0;
	  TString PeriodID("");
	  TString TargetType("");
	  Double_t SolPol = 100;
	  Double_t TorPol = 100;
	  Double_t pol_sign = 0;
	  Double_t wp_status = 0;
	  Double_t fc0 = 0;
	  Double_t fc1 = 0;
	  Double_t b_energy = 0;
	  Double_t helicity = 0;
	  Double_t TarPol = 0;

	  Double_t Q2 = 0;
	  Double_t Xbj = 0;
	  Double_t W = 0;
	  Double_t T = 0;
	  Double_t CM_phi = 0;
	  Double_t CM_theta = 0;

	  Double_t fel_p = 0;
	  Double_t fel_theta = 0;
	  Double_t fel_phi = 0;

	  Double_t fpr_p = 0;
	  Double_t fpr_theta = 0;
	  Double_t fpr_phi = 0;

	  Double_t fkp_p = 0;
	  Double_t fkp_theta = 0;
	  Double_t fkp_phi = 0;

	  Double_t fkm_p = 0;
	  Double_t fkm_theta = 0;
	  Double_t fkm_phi = 0;

	  Double_t mc_Q2 = 0;
	  Double_t mc_Xbj = 0;
	  Double_t mc_W = 0;
	  Double_t mc_T = 0;
	  Double_t mc_CM_phi = 0;
	  Double_t mc_CM_theta = 0;

	  Double_t mc_fel_p = 0;
	  Double_t mc_fel_theta = 0;
	  Double_t mc_fel_phi = 0;

	  Double_t mc_fpr_p = 0;
	  Double_t mc_fpr_theta = 0;
	  Double_t mc_fpr_phi = 0;

	  Double_t mc_fkp_p = 0;
	  Double_t mc_fkp_theta = 0;
	  Double_t mc_fkp_phi = 0;

	  Double_t mc_fkm_p = 0;
	  Double_t mc_fkm_theta = 0;
	  Double_t mc_fkm_phi = 0;

	  tPhi->Branch("RunNumber", &RunNumber);
	  tPhi->Branch("PeriodID", &PeriodID, 16000,0);
	  tPhi->Branch("TargetType", &TargetType, 16000,0);  
	  tPhi->Branch("SolPol", &SolPol);
	  tPhi->Branch("TorPol", &TorPol);
	  tPhi->Branch("pol_sign", &pol_sign);
	  tPhi->Branch("wp_status",&wp_status);
	  tPhi->Branch("fc0",&fc0);
	  tPhi->Branch("fc1",&fc1);
	  tPhi->Branch("b_energy", &b_energy);
	  tPhi->Branch("helicity", &helicity);
	  tPhi->Branch("TarPol", &TarPol);
      
	  tPhi->Branch("Q2", &Q2);
	  tPhi->Branch("Xbj", &Xbj);
	  tPhi->Branch("W", &W);
	  tPhi->Branch("T",&T);
	  tPhi->Branch("CM_phi", &CM_phi);
	  tPhi->Branch("CM_theta", &CM_theta);
      
	  tPhi->Branch("fel_p", &fel_p);
	  tPhi->Branch("fel_theta", &fel_theta);
	  tPhi->Branch("fel_phi", &fel_phi);
	
	  tPhi->Branch("fpr_p", &fpr_p);
	  tPhi->Branch("fpr_theta", &fpr_theta);
	  tPhi->Branch("fpr_phi", &fpr_phi);

	  tPhi->Branch("fkp_p", &fkp_p);
	  tPhi->Branch("fkp_theta", &fkp_theta);
	  tPhi->Branch("fkp_phi", &fkp_phi);

	  tPhi->Branch("fkm_p", &fkm_p);
	  tPhi->Branch("fkm_theta", &fkm_theta);
	  tPhi->Branch("fkm_phi", &fkm_phi);

	  tPhi->Branch("mc_Q2", &mc_Q2);
	  tPhi->Branch("mc_Xbj", &mc_Xbj);
	  tPhi->Branch("mc_W", &mc_W);
	  tPhi->Branch("mc_T",&mc_T);
	  tPhi->Branch("mc_CM_phi", &mc_CM_phi);
	  tPhi->Branch("mc_CM_theta", &mc_CM_theta);
      
	  tPhi->Branch("mc_fel_p", &mc_fel_p);
	  tPhi->Branch("mc_fel_theta", &mc_fel_theta);
	  tPhi->Branch("mc_fel_phi", &mc_fel_phi);
	
	  tPhi->Branch("mc_fpr_p", &mc_fpr_p);
	  tPhi->Branch("mc_fpr_theta", &mc_fpr_theta);
	  tPhi->Branch("mc_fpr_phi", &mc_fpr_phi);

	  tPhi->Branch("mc_fkp_p", &mc_fkp_p);
	  tPhi->Branch("mc_fkp_theta", &mc_fkp_theta);
	  tPhi->Branch("mc_fkp_phi", &mc_fkp_phi);

	  tPhi->Branch("mc_fkm_p", &mc_fkm_p);
	  tPhi->Branch("mc_fkm_theta", &mc_fkm_theta);
	  tPhi->Branch("mc_fkm_phi", &mc_fkm_phi);
	
	  std::ifstream in_file;
	  in_file.open(tempfile,std::ios_base::in);
	  std::string line;	  
	  if( in_file.is_open() ){
	    std::cout << " >> READING TEXT FILE" << std::endl;
	    while( getline(in_file,line) ){
	      if( line[0] == '#' ) continue;
	      double topology, q2, xb, t, w2, cm_theta, cm_phi, el_p, el_theta, el_phi, pr_p, pr_theta, pr_phi, kp_p, kp_theta, kp_phi, km_p, km_theta, km_phi;
	      double mc_q2, mc_xb, mc_t, mc_w2, mc_cm_theta, mc_cm_phi, mc_el_p, mc_el_theta, mc_el_phi, mc_pr_p, mc_pr_theta, mc_pr_phi, mc_kp_p, mc_kp_theta, mc_kp_phi, mc_km_p, mc_km_theta, mc_km_phi;
	      std::stringstream first(line);
	      first >> topology >> q2 >> xb >> t >> w2 >> cm_theta >> cm_phi >> el_p >> el_theta >> el_phi >> pr_p >> pr_theta >> pr_phi >> kp_p >> kp_theta >> kp_phi >> km_p >> km_theta >> km_phi
		    >> mc_q2 >> mc_xb >> mc_t >> mc_w2 >> mc_cm_theta >> mc_cm_phi >> mc_el_p >> mc_el_theta >> mc_el_phi >> mc_pr_p >> mc_pr_theta >> mc_pr_phi >> mc_kp_p >> mc_kp_theta >> mc_kp_phi >> mc_km_p >> mc_km_theta >> mc_km_phi ;
      
	      RunNumber = 1;
	      PeriodID = "NA";
	      helicity  = -1000;
	      //NEED TO READ THIS IN FROM FILE NAME IN FUTURE VERSIONS
	      SolPol = 100.0;
	      TorPol = 100.0;
	      pol_sign = -1; 
	      TargetType = "NA";
	      TarPol = -1000;
	      wp_status = -1000;
	      b_energy = 10.6;
	      fc0 = 0;
	      fc1 = 0;

	      Q2 = q2;
	      Xbj = xb;
	      W = w2;
	      T = t;
	      CM_theta = cm_theta;
	      CM_phi = cm_phi;
	      	
	      fel_p = el_p;
	      fel_theta = el_theta;
	      fel_phi = el_phi;
	
	      fpr_p = pr_p;
	      fpr_theta = pr_theta;
	      fpr_phi = pr_phi;

	      fkp_p = kp_p;
	      fkp_theta = kp_theta;
	      fkp_phi = kp_phi;

	      fkm_p = km_p;
	      fkm_theta = km_theta;
	      fkm_phi = km_phi;

	      mc_Q2 = mc_q2;
	      mc_Xbj = mc_xb;
	      mc_W = mc_w2;
	      mc_T = mc_t;
	      mc_CM_theta = mc_cm_theta;
	      mc_CM_phi = mc_cm_phi;
	      	
	      mc_fel_p = mc_el_p;
	      mc_fel_theta = mc_el_theta;
	      mc_fel_phi = mc_el_phi;
	
	      mc_fpr_p = mc_pr_p;
	      mc_fpr_theta = mc_pr_theta;
	      mc_fpr_phi = mc_pr_phi;

	      mc_fkp_p = mc_kp_p;
	      mc_fkp_theta = mc_kp_theta;
	      mc_fkp_phi = mc_kp_phi;

	      mc_fkm_p = mc_km_p;
	      mc_fkm_theta = mc_km_theta;
	      mc_fkm_phi = mc_km_phi;
	      
	      tPhi->Fill();

	    }   
	  }
	  else{
	    std::cout << " >> ERROR OPENING FILE " << std::endl;
	    break;
	  }
	  tPhi->Write();
	  std::cout << " >> COMPLETED WRITING " << tPhi->GetEntries() << " ENTRIES TO TTREE " << std::endl;
	  tPhi->AutoSave();	  
	  //tPhi->Scan();

	  frootout->Write();
	  frootout->Close();
	}
    }
  }



}
