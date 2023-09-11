package com.prodapt.learningspring.controller.binding;


public class AddCommentForm {
    private String content;

    
	public AddCommentForm() {
		super();
	}

	public AddCommentForm(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
    
}