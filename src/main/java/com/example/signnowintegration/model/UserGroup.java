package com.example.signnowintegration.model;

import java.util.Arrays;

public class UserGroup {
	
//(mediaType, "{\n  \"invite_steps\": [\n    {\n  \"order\": 1,\n \"invite_emails\": [\n {\n \"email\": \"signer1@emaildomain.com\",\n          \"subject\": \"Signer 1\",\n          \"expiration_days\": 30\n        }\n      ],\n      \"invite_actions\": [\n        {\n          \"email\": \"signer1@emaildomain.com\",\n          \"role_name\": \"Signer 1\",\n          \"action\": \"sign\",\n          \"document_id\": \"4eXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\",\n          \"allow_reassign\": \"0\",\n          \"decline_by_signature\": \"0\"\n        }\n      ]\n    },\n    {\n      \"order\": 2,\n      \"invite_emails\": [\n        {\n          \"email\": \"signer2@emaildomain.com\",\n          \"subject\": \"Signer 2\",\n          \"message\": \"custom message\",\n          \"expiration_days\": 30\n        },\n        {\n          \"email\": \"signer3@emaildomain.com\",\n          \"subject\": \"Signer 3\",\n          \"message\": \"custom message\",\n          \"expiration_days\": 30\n        }\n      ],\n      \"invite_actions\": [\n        {\n          \"email\": \"signer2@emaildomain.com\",\n          \"role_name\": \"Signer 2\",\n          \"action\": \"sign\",\n          \"document_id\": \"0eXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\",\n          \"allow_reassign\": \"0\",\n          \"decline_by_signature\": \"0\"\n        },\n        {\n          \"email\": \"signer3@emaildomain.com\",\n          \"role_name\": \"Signer 3\",\n          \"action\": \"sign\",\n          \"document_id\": \"0eXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\",\n          \"allow_reassign\": \"0\",\n          \"decline_by_signature\": \"0\"\n        }\n      ]\n    }\n  ],\n  \"completion_emails\": [],\n  \"sign_as_merged\": true,\n  \"cc\": [\n    \"example1@emaildomain.com\",\n    \"example2@emaildomain.com\"\n  ],\n  \"cc_subject\": \"Subject\",\n  \"cc_message\": \"Message\"\n}");
	private String document_group_id;
	private Object[] invite_steps;
	private Object[] completion_emails;
	private boolean sign_as_merged;
	private Integer client_timestamp;
	private String[] cc;
	private String cc_subject;
	private String cc_message;
	
	
	
	public String getDocument_group_id() {
		return document_group_id;
	}
	public void setDocument_group_id(String document_group_id) {
		this.document_group_id = document_group_id;
	}
	public Object[] getInvite_steps() {
		return invite_steps;
	}
	public void setInvite_steps(Object[] invite_steps) {
		this.invite_steps = invite_steps;
	}
	public Object[] getCompletion_emails() {
		return completion_emails;
	}
	public void setCompletion_emails(Object[] completion_emails) {
		this.completion_emails = completion_emails;
	}
	public boolean isSign_as_merged() {
		return sign_as_merged;
	}
	public void setSign_as_merged(boolean sign_as_merged) {
		this.sign_as_merged = sign_as_merged;
	}
	public Integer getClient_timestamp() {
		return client_timestamp;
	}
	public void setClient_timestamp(Integer client_timestamp) {
		this.client_timestamp = client_timestamp;
	}
	public String[] getCc() {
		return cc;
	}
	public void setCc(String[] cc) {
		this.cc = cc;
	}
	public String getCc_subject() {
		return cc_subject;
	}
	public void setCc_subject(String cc_subject) {
		this.cc_subject = cc_subject;
	}
	public String getCc_message() {
		return cc_message;
	}
	public void setCc_message(String cc_message) {
		this.cc_message = cc_message;
	}
	
	
	
	
}
