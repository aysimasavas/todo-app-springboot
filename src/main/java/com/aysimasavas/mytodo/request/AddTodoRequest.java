package com.aysimasavas.mytodo.request;

public class AddTodoRequest {

	String content;

	public AddTodoRequest(String content) {
		super();
		this.content = content;
	}

	public AddTodoRequest() {
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}