package me.simple.entity;

public class Column implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1119967119225693951L;
    private String col_name;
    private String col_type;
    private String col_java_type;
    public String getCol_name() {
        return col_name;
    }
    public void setCol_name(String col_name) {
        this.col_name = col_name;
    }
    public String getCol_type() {
        return col_type;
    }
    public void setCol_type(String col_type) {
        this.col_type = col_type;
    }
    public Column() {
	super();
    }
    public Column(String col_name, String col_type,String col_java_type) {
	super();
	this.col_name = col_name;
	this.col_type = col_type;
	this.col_java_type = col_java_type;
    }
    public String getCol_java_type() {
	return col_java_type;
    }
    public void setCol_java_type(String col_java_type) {
	this.col_java_type = col_java_type;
    }
    
    
}
