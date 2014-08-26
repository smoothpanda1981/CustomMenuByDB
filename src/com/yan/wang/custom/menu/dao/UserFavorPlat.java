package com.yan.wang.custom.menu.dao;

public class UserFavorPlat {
	private int id;
    private int userId;
    private int platId;
    
    public UserFavorPlat(){}
    
    public UserFavorPlat(int userId, int platId) {
        super();
        this.userId = userId;
        this.platId = platId;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPlatId() {
		return platId;
	}

	public void setPlatId(int platId) {
		this.platId = platId;
	}
}
