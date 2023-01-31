package com.example.signnowintegration.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.signnowintegration.constants.Constants;
import com.example.signnowintegration.model.FieldData;
import com.example.signnowintegration.model.FileData;
import com.example.signnowintegration.model.User;
import com.example.signnowintegration.model.UserGroup;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Service
public class SignnowInviteServiceImpl implements SignnowInviteService{

	@Autowired
	SignnowService signnowService =new SignnowImplService();

	@Override
	public String invitePersonToSign(String inviteURL,User user) throws IOException {	
		
		ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        
        
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,json);
		
		System.out.println(user.getFrom());
		System.out.println(user.getTo());
		
		

		/*
		 * RequestBody body = new FormBody.Builder() .add("from",user.getFrom())
		 * .add("to",user.getTo()) .build();
		 */
		
		
		Request request = new Request.Builder()
		  .url(inviteURL)
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Authorization","Bearer "+signnowService.getAccessToken(Constants.tokenUrl))
		  .addHeader("Content-type", "application/json")
		  .build();
		Response response = client.newCall(request).execute();
		
		
	BufferedReader reader = new BufferedReader(response.body().charStream());
		
		StringBuilder builder = new StringBuilder();	
		for (String line = null; (line = reader.readLine()) != null;) {
		    builder.append(line).append("\n");
		}		
		JSONTokener tokener = new JSONTokener(builder.toString());
		JSONObject jsonObj = new JSONObject(tokener);	 
		return jsonObj.toString();	
	}



	@Override
	public String createDocumentGroup(String createGroupURL,String token,FileData fileData) throws IOException {
		// TODO Auto-generated method stub
       // List<String> docsList = Arrays.asList(fileData.getDocument_ids());

  
		ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(fileData);
            System.out.println(json);
		OkHttpClient client = new OkHttpClient();
		
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,json);
		
		Request request = new Request.Builder()
		  .url(createGroupURL)
		  .post(body)
		  .addHeader("Content-Type","application/json")
		  .addHeader("Authorization","Bearer "+token)
		  .build();

		Response response = client.newCall(request).execute();

		BufferedReader reader = new BufferedReader(response.body().charStream());
		
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


	@Override
	public String addAndAssignRole(String assignRoleURL,FieldData fieldData) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(fieldData);  
           
	    OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,json);	
					
		Request request = new Request.Builder()
		 .url(assignRoleURL)
		  .put(body)
		  .addHeader("Content-Type","application/json")
		  .addHeader("Authorization","Bearer "+signnowService.getAccessToken(Constants.tokenUrl))
		  .build();

		Response response = client.newCall(request).execute();
		System.out.println(response.code());
		
		
	BufferedReader reader = new BufferedReader(response.body().charStream());
		
		StringBuilder builder = new StringBuilder();	
		for (String line = null; (line = reader.readLine()) != null;) {
		    builder.append(line).append("\n");
		}		
		JSONTokener tokener = new JSONTokener(builder.toString());
		JSONObject jsonObj = new JSONObject(tokener);	 
		return jsonObj.toString();	
				
	}
	
	
	

	@Override
	public String inviteGroupToSign(String inviteGroupURL,UserGroup userGroup) throws IOException {
		// TODO Auto-generated method stub
			
		ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(userGroup);
        
        
        System.out.println("JSON INVITE DATA"+json);
        
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,json);
		
		Request request = new Request.Builder()
		  .url(inviteGroupURL)
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Authorization","Bearer "+signnowService.getAccessToken(Constants.tokenUrl))
		  .build();

		Response response = client.newCall(request).execute();
	   BufferedReader reader = new BufferedReader(response.body().charStream());
		
		StringBuilder builder = new StringBuilder();	
		for (String line = null; (line = reader.readLine()) != null;) {
		    builder.append(line).append("\n");
		}		
		JSONTokener tokener = new JSONTokener(builder.toString());
		JSONObject jsonObj = new JSONObject(tokener);	 
		return jsonObj.toString();	
	}



	
	
	@Override
	public String downloadDocument(String downloadURL,FileData filedata) throws IOException {
		// TODO Auto-generated method stub
				
		 OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
			  .url(downloadURL)
			  .get()
			  .addHeader("Content-Type", "application/json")
			  .addHeader("Authorization","Bearer "+signnowService.getAccessToken(Constants.tokenUrl))	  
			  .build();
        
	    Response response = client.newCall(request).execute();
        InputStream inputStream=response.body().byteStream();
        		
   // BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String filepath=filedata.getFilePath()+File.separator+filedata.getFileName();
        
        File targetFile = new File(filepath);

        OutputStream outStream = new FileOutputStream(targetFile);

        byte[] buffer = new byte[8 * 1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outStream);
        
       
   		return "File Downloaded to "+targetFile;
   			
	}



	
	
	@Override
	public String downloadDocumentLink(String downloadURL,FileData filedata) throws IOException {
		// TODO Auto-generated method stub
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(null, new byte[]{});
		Request request = new Request.Builder()
		  .url(downloadURL)
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("Authorization","Bearer "+signnowService.getAccessToken(Constants.tokenUrl))	  
		  .build();
		Response response = client.newCall(request).execute();
		
		
BufferedReader reader = new BufferedReader(response.body().charStream());
		
		StringBuilder builder = new StringBuilder();	
		for (String line = null; (line = reader.readLine()) != null;) {
		    builder.append(line).append("\n");
		}		
		JSONTokener tokener = new JSONTokener(builder.toString());
		JSONObject jsonObj = new JSONObject(tokener);
		
		String responsejson = null;
		
		if(jsonObj.toString().contains("link")) {
			
			int i1=jsonObj.toString().indexOf("link");
			int i2=jsonObj.toString().indexOf("}");		
			System.out.println(i2);		
			 responsejson=jsonObj.toString().substring(i1+7,i2-1);			
		}
		
	String res=downloadUsingStream(responsejson,filedata);
		
		System.out.println(responsejson);	
		System.out.println(res);	

		return responsejson;	
						
	}
	
	
	
	 private String downloadUsingStream(String downloadurls,FileData filedata) throws IOException{
	        URL url = new URL(downloadurls);
	        BufferedInputStream bis = new BufferedInputStream(url.openStream());
	        
	        String filepath=filedata.getFilePath()+File.separator+filedata.getFileName();
	        
	        System.out.println(filepath);
	        FileOutputStream fis = new FileOutputStream(new File(filepath));
	        byte[] buffer = new byte[1024];
	        int count=0;
	        while((count = bis.read(buffer,0,1024)) != -1)
	        {
	            fis.write(buffer, 0, count);
	        }
	        fis.close();
	        bis.close();
			return "File downloaded to "+filepath;
	    }
	
	
	 
	 
	 
}



