package com.example.signnowintegration.constants;

import com.example.signnowintegration.model.FileData;
import com.example.signnowintegration.service.SignnowImplService;

public class Constants {
	
	SignnowImplService signnowService=new SignnowImplService();
    FileData filedata=new FileData();
    
    
   public static String shp_clientId = "a3aefbdcb8ed9e5ce9e19e312e698bef";
   public static String shp_clientSecret ="27e6ede29da798a39fcd3e9680a4da6f";
   public static String uploadURI="https://api-eval.signnow.com/document";
   public static String tokenUrl="https://api-eval.signnow.com/oauth2/token";
   public static String userName="a.dhakal423@gmail.com";
   public static String passWord="Dhakal456?";
   
   //public String inviteUrl="https://api-eval.signnow.com/document/"+data+"/"+"invite";
      
   
}
