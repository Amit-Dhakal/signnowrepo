package com.example.signnowintegration.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.assertj.core.util.Lists;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.signnowintegration.constants.Constants;
import com.example.signnowintegration.model.FileData;

@Service
public class SignnowImplService implements SignnowService {
	
	@Override
	public String getAccessToken() {
		
		String accessToken = "";

	String url = "https://api-eval.signnow.com/oauth2/token";
	
    String encodedCredential=getBase64Encoded(Constants.shp_clientId,Constants.shp_clientSecret);

      CloseableHttpClient httpClient = HttpClients.createDefault();
       HttpPost postRequest = new HttpPost(url);          
       postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");       
       postRequest.setHeader("Authorization","Basic "+encodedCredential);

       List<NameValuePair> params = Lists.newArrayList(
               new BasicNameValuePair("username", "a.dhakal423@gmail.com"),
               new BasicNameValuePair("password", "Dhakal456?"),
               new BasicNameValuePair("grant_type", "password")
       );

       try {
           postRequest.setEntity(new UrlEncodedFormEntity(params));
           
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       }
       
       
       String bearerToken = "";

       try {

           HttpResponse response = httpClient.execute(postRequest);
                       			        		         
	
           String bodyJson =IOUtils.toString(response.getEntity().getContent(),Charset.defaultCharset());             
         	             	       	            
           if (bodyJson.contains("access_token")) {
               int i1 = bodyJson.indexOf("access_token");	
               int i3 = bodyJson.indexOf("token_type");	    
               int i4 = bodyJson.indexOf("refresh_token");	    
              
               //String str1 = bodyJson.substring(i1+15,i3-i1-1);
                String str1 = bodyJson.substring(i1+15,i4-3);

               bearerToken = str1;           
           }	                             
       } catch (IOException e) {
           e.printStackTrace();
       }
      	
		return bearerToken;
	}
	
	
	public static String getBase64Encoded(String id,String password) {					
		return Base64.getEncoder().encodeToString((id+":"+password).getBytes(StandardCharsets.UTF_8));
				
			}

	
	@Override
	public void uploadFile(String url,MultipartFile file) throws IOException {
 
		
	//	File convertFile=new File(file.getOriginalFilename());
		
		// convertFile.createNewFile();
       //  FileOutputStream fos=new FileOutputStream(convertFile);
       //  fos.write(file.getBytes());
		
				HttpClient httpClient = HttpClientBuilder.create().build();
	       HttpPost postRequest = new HttpPost(url);  
	      	       
	       postRequest.setHeader("Content-Type","multipart/form-data");       
	       postRequest.setHeader("Authorization","Bearer "+getAccessToken());
	       
			MultipartEntityBuilder entity=MultipartEntityBuilder.create();
			entity.addBinaryBody("file",file.getBytes());
		//	entity.addPart("file", new FileBody(convertFile));

			postRequest.setEntity(entity.build());
			
	       System.out.println(getAccessToken());
	       

		       	       
	       try {
	           HttpResponse response = httpClient.execute(postRequest);	 
	           
	           String bodyJson =IOUtils.toString(response.getEntity().getContent(),Charset.defaultCharset());  
	           System.out.println("JSON Body::"+bodyJson);
	       } catch(IOException e) {
	           e.printStackTrace();
	       }                		
		
	}

	
	@Override
	public void upload(String url,FileData fileData) throws IOException {
		// TODO Auto-generated method stub		
     //	String pathname= "D:\\Task\\3.png";
		
		
	    String pathname= fileData.getFilePath()+fileData.getFileName();
	    		

		File fileUpload = new File(pathname);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(url);
		postRequest.setHeader("Authorization", "Bearer " +getAccessToken());
		//postRequest.setHeader("X-Atlassian-Token","nocheck");
		MultipartEntityBuilder entity=MultipartEntityBuilder.create();
		entity.addPart("file", new FileBody(fileUpload));
		postRequest.setEntity(entity.build());
		HttpResponse response = httpClient.execute(postRequest);
		System.out.println(response);

		
	}


	
	
	
	
	


	public static void main(String[] args) {
		// TODO Auto-generated method stub
        String url="https://api-eval.signnow.com/document";
		SignnowService signnowService =new SignnowImplService();	
		System.out.println("Token is :"+signnowService.getAccessToken());
		//String result=signnowService.upload(url);	
	  // signnowService.uploadFile(url);
		
	}


	
	
	
	

}
