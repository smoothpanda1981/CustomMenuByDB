package com.yan.wang.custom.menu.dao;

public class User {
	private int id;
    private String username;
    
    public User(){}
    
    public User(String username) {
        super();
        this.username = username;
    }
    
    
  //getters & setters
    
    
    
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + "]";
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
