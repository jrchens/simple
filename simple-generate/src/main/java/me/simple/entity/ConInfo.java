package me.simple.entity;

public class ConInfo implements java.io.Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -3176384896296867940L;
    public ConInfo() {
	super();
    }
    
    public ConInfo(String con_name, String con_ip, String con_db,String con_username, String con_password) {
	super();
	this.con_name = con_name;
	this.con_ip = con_ip;
	this.setCon_db(con_db);
	this.con_username = con_username;
	this.con_password = con_password;
    }

    private String con_name;
    private String con_ip;
    private String con_db;
    private int con_port = 3306;
    private String con_username;
    private String con_password;
    public String getCon_name() {
        return con_name;
    }
    public void setCon_name(String con_name) {
        this.con_name = con_name;
    }
    public String getCon_ip() {
        return con_ip;
    }
    public void setCon_ip(String con_ip) {
        this.con_ip = con_ip;
    }
    public String getCon_username() {
        return con_username;
    }
    public void setCon_username(String con_username) {
        this.con_username = con_username;
    }
    public String getCon_password() {
        return con_password;
    }
    public void setCon_password(String con_password) {
        this.con_password = con_password;
    }

    public int getCon_port() {
	return con_port;
    }

    public void setCon_port(int con_port) {
	this.con_port = con_port;
    }

    public String getCon_db() {
	return con_db;
    }

    public void setCon_db(String con_db) {
	this.con_db = con_db;
    }
    
}
