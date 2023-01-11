package com.example.signnowintegration.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.signnowintegration.model.FileData;

public interface SignnowService {
	
	 public String getAccessToken();		
	 public void uploadFile(String url,MultipartFile file) throws IOException;
	 public void upload(String url,FileData fileData) throws IOException;

}
