package com.yan.wang.custom.menu.dao;

public class Plat {
	private int id;
    private String platName;
    
    public Plat(){}
    
    public Plat(String platName) {
        super();
        this.platName = platName;
    }
    
    
  //getters & setters
    
    
    
    @Override
    public String toString() {
        return "Plat [id=" + id + ", platName=" + platName + "]";
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlatName() {
		return platName;
	}

	public void setPlatName(String platName) {
		this.platName = platName;
	}
}
