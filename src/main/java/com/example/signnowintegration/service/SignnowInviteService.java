package com.example.signnowintegration.service;

import java.io.IOException;

import com.example.signnowintegration.model.FieldData;
import com.example.signnowintegration.model.FileData;
import com.example.signnowintegration.model.User;
import com.example.signnowintegration.model.UserGroup;

import okhttp3.Response;

public interface SignnowInviteService  {
	
	public String invitePersonToSign(String inviteURL,User user) throws IOException;
	
	public String inviteGroupToSign(String invteGroupURL,UserGroup userGroup) throws IOException;

	public String createDocumentGroup(String createGroupURL,String token,FileData fileData) throws IOException ;
	
	public String addAndAssignRole(String assignRoleURL,FieldData fieldData) throws IOException ;

	public String downloadDocument(String downloadURL,FileData filedata) throws IOException;
	
	public String downloadDocumentLink(String downloadURL,FileData filedata) throws IOException;
	
	public String downloadGroupDocument(String downloadURL,FileData filedata) throws IOException;

	
	public String getDocumentInfo(String url) throws IOException;
	
	
	public String fieldinvitePersonToSign(String inviteURL,FieldData fieldData) throws IOException;

	
}
