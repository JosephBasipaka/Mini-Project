package com.prodapt.learningspring.controller.binding;

public class AddPostForm {
	  private String content;
	  private int userId;
	  
	  	
		public AddPostForm() {
			
		}
		public AddPostForm(String content, int userId) {
			super();
			this.content = content;
			this.userId = userId;
		}
		
		public String getContent() {
			return content;
		}
		
		public void setContent(String content) {
			this.content = content;
		}
		
		public int getUserId() {
			return userId;
		}
		
		public void setUserId(int userId) {
			this.userId = userId;
		}
	  
  
}
