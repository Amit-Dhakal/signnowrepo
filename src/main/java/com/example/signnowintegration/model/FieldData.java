package com.example.signnowintegration.model;

public class FieldData {
	
	
	//Field Invite data 
	
	private String document_id;
	private Object[] to;
	private String from;
	private Object[] cc;
	private Object[] cc_step;
	private Object[] viewers;

	private String subject;
	private String message;
	private String cc_subject;
	private String cc_message;
	
	
	
	
	
		public String getDocument_id() {
		return document_id;
	}
	public void setDocument_id(String document_id) {
		this.document_id = document_id;
	}
		public Object[] getTo() {
		return to;
	}
	public void setTo(Object[] to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public Object[] getCc() {
		return cc;
	}
	public void setCc(Object[] cc) {
		this.cc = cc;
	}
	public Object[] getCc_step() {
		return cc_step;
	}
	public void setCc_step(Object[] cc_step) {
		this.cc_step = cc_step;
	}
	public Object[] getViewers() {
		return viewers;
	}
	public void setViewers(Object[] viewers) {
		this.viewers = viewers;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
		private Object[] fields;  
		
		/*
		  private Number x;  
		private Number y;  
		  private Number width; 
		  private Number height;  
		  private String type;
		  private Number page_number;
		  private boolean required;
		  
		  private String role;
		  private boolean custom_defined_option;
		  private String name;
		  private String validator_id;  
		  */
		
		
		  private Object[] elements;
		  private String client_timestamp;
		  
	  
		public Object[] getFields() {
			return fields;
		}
		public void setFields(Object[] fields) {
			this.fields = fields;
		}
		
		/*
		public Number getX() {
			return x;
		}
		public void setX(Number x) {
			this.x = x;
		}
		public Number getY() {
			return y;
		}
		public void setY(Number y) {
			this.y = y;
		}
		public Number getWidth() {
			return width;
		}
		public void setWidth(Number width) {
			this.width = width;
		}
		public Number getHeight() {
			return height;
		}
		public void setHeight(Number height) {
			this.height = height;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Number getPage_number() {
			return page_number;
		}
		public void setPage_number(Number page_number) {
			this.page_number = page_number;
		}
		public boolean isRequired() {
			return required;
		}
		public void setRequired(boolean required) {
			this.required = required;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public boolean isCustom_defined_option() {
			return custom_defined_option;
		}
		public void setCustom_defined_option(boolean custom_defined_option) {
			this.custom_defined_option = custom_defined_option;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValidator_id() {
			return validator_id;
		}
		public void setValidator_id(String validator_id) {
			this.validator_id = validator_id;
		}
		
		*/
		
		
		public Object[] getElements() {
			return elements;
		}
		public void setElements(Object[] elements) {
			this.elements = elements;
		}
		public String getClient_timestamp() {
			return client_timestamp;
		}
		public void setClient_timestamp(String client_timestamp) {
			this.client_timestamp = client_timestamp;
		}
		  
		  
		 		  
				  
		  
	
}
