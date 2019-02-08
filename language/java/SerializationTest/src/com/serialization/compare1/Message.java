package com.serialization.compare1;

public class Message implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    public static final int MESSAGE_TYPE_USER = 1;
    public static final int MESSAGE_TYPE_QUIT = 2;
 
    public Integer type;
    public String username;
    public Integer userid;
    public Boolean active;
    public String data;
    
    public Message()
    {
    	this.type = MESSAGE_TYPE_QUIT;
    	this.username = "default";
    	this.userid = 0;
    	this.active = false;
    	this.data = "data";
    }
}
