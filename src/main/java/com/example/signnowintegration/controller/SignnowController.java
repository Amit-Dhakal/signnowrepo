package com.example.signnowintegration.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.signnowintegration.constants.Constants;
import com.example.signnowintegration.model.FieldData;
import com.example.signnowintegration.model.FileData;
import com.example.signnowintegration.model.User;
import com.example.signnowintegration.model.UserGroup;
import com.example.signnowintegration.service.SignnowImplService;
import com.example.signnowintegration.service.SignnowInviteService;
import com.example.signnowintegration.service.SignnowInviteServiceImpl;
import com.example.signnowintegration.service.SignnowService;

import okhttp3.Response;

@RestController
@RequestMapping("/signnow")
public class SignnowController {

	@Autowired
	SignnowService signnowService =new SignnowImplService();	
	
	@Autowired
	SignnowInviteService signnowInviteService=new SignnowInviteServiceImpl();

	String inviteURL=null;
	
	
	@RequestMapping(value = "/token", method = RequestMethod.POST, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public String getToken() throws IOException{
		
		String tokenURL="https://api-eval.signnow.com/oauth2/token";
		String token=signnowService.getAccessToken(tokenURL);		
		return token;	
	}

	
	
	
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException{
		
	//	String token=signnowService.getAccessToken();		
	//	System.out.println(token);
		String uploadURI=Constants.uploadURI;
		System.out.println("URI IS"+uploadURI);
		//String url="https://api-eval.signnow.com/document";	
		signnowService.uploadFile(uploadURI,file);		
		return new ResponseEntity<>("Response of API :"+"uploaded",HttpStatus.OK);	
	}

	
	@RequestMapping(value = "/upload", method = RequestMethod.POST,produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> upload(@RequestBody FileData fileData) throws IOException{		
		String token=signnowService.getAccessToken(Constants.tokenUrl);	
		String uploadURI=Constants.uploadURI;
		System.out.println(token);
		System.out.println("URI IS"+uploadURI);
		String response=signnowService.upload(uploadURI,fileData);	
		 inviteURL=signnowService.inviteURL(fileData);		
		System.out.println(inviteURL);	
		return new ResponseEntity<>("Response of API :"+response,HttpStatus.OK);	
	}
	 
	
	@RequestMapping(value = "/invite/{document_id}",method = RequestMethod.POST,produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> inviteToSign(@PathVariable String document_id,@RequestBody User user) throws IOException{
		 String token=signnowService.getAccessToken(Constants.tokenUrl);	
		 System.out.println(document_id);
		 System.out.println(token);

		//String inviteURL=signnowService.inviteURL(fileData);	 
	    String inviteURL="https://api-eval.signnow.com/document/"+document_id+"/"+"invite";              
		String resp=signnowInviteService.invitePersonToSign(inviteURL,user);	
		
		return new ResponseEntity<>("Response of API :"+resp,HttpStatus.OK);	
		
	}

	
	
	@RequestMapping(value = "/creategroup",method = RequestMethod.POST,produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createDocumentGroup(@RequestBody FileData fileData) throws IOException{
		 String token=signnowService.getAccessToken(Constants.tokenUrl);
		 System.out.println("Token is"+token);	 
	    String createGroupURL="https://api-eval.signnow.com/documentgroup";
		String resp=signnowInviteService.createDocumentGroup(createGroupURL,token,fileData);	
		return new ResponseEntity<>("Response of API :"+resp,HttpStatus.OK);	
		
	}
	
	
	@RequestMapping(value ="/assignrole/{document_id}",method=RequestMethod.PUT,produces =org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> assignRole(@RequestBody FieldData fieldData,@PathVariable String document_id) throws IOException{
				
		String token=signnowService.getAccessToken(Constants.tokenUrl); 		 	 
		// https://api-eval.signnow.com/documentgroup/{document_group_id}/groupinvite 
	    String inviteDocumentGroupURL="https://api-eval.signnow.com/"+"document/"+document_id;    
		String resp=signnowInviteService.addAndAssignRole(inviteDocumentGroupURL,fieldData);	
		return new ResponseEntity<>("Response of API :"+resp,HttpStatus.OK);	
		
	}
	
	

	@RequestMapping(value = "/invitegroup/{document_group_id}",method=RequestMethod.POST,produces =org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> inviteDocumentGroup(@RequestBody UserGroup userGroup,@PathVariable String document_group_id) throws IOException{
		
		 String token=signnowService.getAccessToken(Constants.tokenUrl); 	 	 
		// https://api-eval.signnow.com/documentgroup/{document_group_id}/groupinvite
	    String inviteDocumentGroupURL="https://api-eval.signnow.com/"+"documentgroup/"+document_group_id+"/"+"groupinvite"; 	    
		String resp=signnowInviteService.inviteGroupToSign(inviteDocumentGroupURL,userGroup);	
		System.out.println(resp);	
		return new ResponseEntity<>("Response of API :"+resp,HttpStatus.OK);	
	}
	
	
	
	@RequestMapping(value = "/download/{document_id}",method=RequestMethod.GET,produces =org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> downloadDocument(@PathVariable String document_id,@RequestParam String type,@RequestParam String with_history,@RequestBody FileData filedata) throws IOException{	
				
		 String token=signnowService.getAccessToken(Constants.tokenUrl); 	 	 
	    String downloadURL="https://api-eval.signnow.com/"+"document/"+document_id+"/"+"download"; 	
	    
		String resp=signnowInviteService.downloadDocument(downloadURL,filedata);	
		return new ResponseEntity<>("Response of API :"+resp,HttpStatus.OK);	
		
	}
	
	
	
	
	
	@RequestMapping(value = "/download/link/{document_id}",method=RequestMethod.POST,produces =org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> downloadDocumentByLink(@PathVariable String document_id,@RequestBody FileData filedata) throws IOException{	
				
		
		System.out.println(document_id);
		 String token=signnowService.getAccessToken(Constants.tokenUrl); 	 	 
	    String downloadURL="https://api-eval.signnow.com/"+"document/"+document_id+"/"+"download/"+"link"; 	   
	    System.out.println(downloadURL);
		String resp=signnowInviteService.downloadDocumentLink(downloadURL,filedata);	
		return new ResponseEntity<>("Response of API :"+resp,HttpStatus.OK);
		
		
	}
	
	
	@RequestMapping(value = "/downloadgroup/{document_group_id}",method=RequestMethod.POST,produces =org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> downloadDocumentGroup(@PathVariable String document_group_id,@RequestBody FileData filedata) throws IOException{	
				
		System.out.println(document_group_id);
		
		 String token=signnowService.getAccessToken(Constants.tokenUrl); 
		 
	    String downloadURL="https://api-eval.signnow.com/documentgroup/"+document_group_id+"/"+"downloadall"; 	   
	    System.out.println(downloadURL);
	    
	    
		String resp=signnowInviteService.downloadGroupDocument(downloadURL,filedata);	
		return new ResponseEntity<>("Response of API :"+resp,HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value = "/documentinfo/{document_id}",method=RequestMethod.GET,produces =org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getDocumentInfo(@PathVariable String document_id) throws IOException{	
				
		
		 String token=signnowService.getAccessToken(Constants.tokenUrl); 
		 
	    String documentURL="https://api-eval.signnow.com/document/"+document_id; 	   
	    
	    String resp=signnowInviteService.getDocumentInfo(documentURL);	
		
		return new ResponseEntity<>("Response of API :"+resp,HttpStatus.OK);
		
		
	}
	
	
	
	
	
}
  