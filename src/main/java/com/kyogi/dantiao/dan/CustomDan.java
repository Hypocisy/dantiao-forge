package com.kyogi.dantiao.dan;

public class CustomDan {
    private final String editName;
    private final String displayName;
    private final int exp;
    
    public CustomDan(String editName, String displayName, int exp) {
    	this.editName = editName;
    	this.displayName = displayName;
    	this.exp = exp;
    }
    
    public String getEditName() {
    	return editName;
    }
    
    public String getDisplayName() {
    	return displayName;
    }
    
    public int getExp() {
    	return exp;
    }
}
