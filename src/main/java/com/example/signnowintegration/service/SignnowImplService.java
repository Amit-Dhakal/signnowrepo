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
import java.nio.file.Path;
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
import org.apache.http.util.EntityUtils;
import org.assertj.core.util.Lists;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.signnowintegration.constants.Constants;
import com.example.signnowintegration.model.FileData;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class SignnowImplService implements SignnowService {
	
//  public String inviteUrl="https://api-eval.signnow.com/document/"+data+"/"+"invite";
	
	@Override
	public String getAccessToken(String url) {		
	String accessToken = "";

    String encodedCredential=getBase64Encoded(Constants.shp_clientId,Constants.shp_clientSecret);
      CloseableHttpClient httpClient = HttpClients.createDefault();
       HttpPost postRequest = new HttpPost(url);          
       postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");       
       postRequest.setHeader("Authorization","Basic "+encodedCredential);

       List<NameValuePair> params = Lists.newArrayList(
               new BasicNameValuePair("username",Constants.userName),
               new BasicNameValuePair("password",Constants.passWord ),
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

           String bodyJson = EntityUtils.toString(response.getEntity());
         //  String bodyJson =IOUtils.toString(response.getEntity().getContent(),Charset.defaultCharset());             
    	             	       	            
                if(bodyJson.contains("access_token")) {
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
 
		File convertedFile=convertMultipartFileToFile(file);	
		System.out.println(convertedFile.getAbsolutePath());
		File fileUpload = new File(convertedFile.getAbsolutePath());
		   HttpClient httpClient = HttpClientBuilder.create().build();
	       HttpPost postRequest = new HttpPost(url);         
	       postRequest.setHeader("Content-Type","multipart/form-data");       
			MultipartEntityBuilder entity=MultipartEntityBuilder.create();
			entity.addPart("file", new FileBody(fileUpload));
			postRequest.setEntity(entity.build());
	       HttpResponse response = httpClient.execute(postRequest);	 
	       System.out.println(response);
         //  String bodyJson =IOUtils.toString(response.getEntity().getContent(),Charset.defaultCharset());  
           String bodyJson = EntityUtils.toString(response.getEntity());

           
           System.out.println("JSON Body::"+bodyJson);        
	}

	
	
	
	public File convertMultipartFileToFile(MultipartFile file) throws IOException
	{    
		//File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+file.getOriginalFilename());
		File convFile = new File(System.getProperty("user.dir")+"/"+file.getOriginalFilename());
		file.transferTo(convFile);	
	    return convFile; 
	}
	
	

	@Override
	public String upload(String url,FileData fileData) throws IOException {
	    String pathname= fileData.getFilePath()+fileData.getFileName();	    		
		File fileUpload = new File(pathname);	
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(url);
		postRequest.setHeader("Authorization", "Bearer " +getAccessToken(Constants.tokenUrl));
		
		
		MultipartEntityBuilder entity=MultipartEntityBuilder.create();
		entity.addPart("file", new FileBody(fileUpload));
		postRequest.setEntity(entity.build());
		HttpResponse response = httpClient.execute(postRequest);
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
		StringBuilder builder = new StringBuilder();	
		for (String line = null; (line = reader.readLine()) != null;) {
		    builder.append(line).append("\n");
		}		
		JSONTokener tokener = new JSONTokener(builder.toString());
		JSONObject jsonObj = new JSONObject(tokener);	 
		System.out.println(jsonObj.toString());	
		fileData.setDocument_id(jsonObj.toString());
		return jsonObj.toString();
	}

	
	
	
	
	public String inviteURL(FileData fileData) {
		
	    System.out.println(fileData.getDocument_id());	 
		int beginIndex= fileData.getDocument_id().indexOf("id");
		int endIndex=fileData.getDocument_id().indexOf("}");
		String newstr=fileData.getDocument_id().substring(beginIndex+5,endIndex-1);	 
	   String inviteURL="https://api-eval.signnow.com/document/"+newstr+"/"+"invite";
	    return inviteURL;		
	}
	

	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        String url="https://api-eval.signnow.com/document";
		SignnowService signnowService =new SignnowImplService();	
		System.out.println("Token is :"+signnowService.getAccessToken(Constants.tokenUrl));
		//String result=signnowService.upload(url);	
	  // signnowService.uploadFile(url);
		
	}

	

}
