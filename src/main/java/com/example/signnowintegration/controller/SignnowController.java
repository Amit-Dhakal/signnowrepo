package com.example.signnowintegration.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.signnowintegration.constants.Constants;
import com.example.signnowintegration.model.FileData;
import com.example.signnowintegration.service.SignnowImplService;
import com.example.signnowintegration.service.SignnowService;

@RestController
public class SignnowController {

	@Autowired
	SignnowService signnowService =new SignnowImplService();	

	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException{
		
		String token=signnowService.getAccessToken();	
		
		System.out.println(token);
		String uploadURI=Constants.uploadURI;
		System.out.println("URI IS"+uploadURI);
		//String url="https://api-eval.signnow.com/document";
		
		signnowService.uploadFile(uploadURI,file);
				
		return new ResponseEntity<>("Response of API :"+"uploaded",HttpStatus.OK);
		
		
	}
	
	 
	
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> upload(@RequestBody FileData fileData) throws IOException{
		
		String token=signnowService.getAccessToken();	
		
		System.out.println(token);
		String uploadURI=Constants.uploadURI;
		System.out.println("URI IS"+uploadURI);
		//String url="https://api-eval.signnow.com/document";
		
		signnowService.upload(uploadURI,fileData);
				
		return new ResponseEntity<>("Response of API :"+"uploaded",HttpStatus.OK);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
