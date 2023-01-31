package com.example.signnowintegration.model;

import java.util.Arrays;

public class FileData {
	
	private String document_id;
	private String fileName;
	private String filePath;

	private String[] document_ids;
	private String group_name;
	
	
	

	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String[] getDocument_ids() {
		return document_ids;
	}
	public void setDocument_ids(String[] document_ids) {
		this.document_ids = document_ids;
	}
	public String getDocument_id() {
		return document_id;
	}
	public void setDocument_id(String document_id) {
		this.document_id = document_id;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	@Override
	public String toString() {
		return "FileData [document_id=" + document_id + ", fileName=" + fileName + ", filePath=" + filePath
				+ ", document_ids=" + Arrays.toString(document_ids) + ", group_name=" + group_name + "]";
	}
	


}
