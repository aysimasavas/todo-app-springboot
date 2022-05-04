package com.aysimasavas.mytodo.response;

import com.aysimasavas.mytodo.entities.User;

public class UserResponse {

	Long id;
	int avatarId;
	String userName;

	public UserResponse(User entity) {
		this.id = entity.getId();
		this.userName = entity.getUserName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(int avatarId) {
		this.avatarId = avatarId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}

