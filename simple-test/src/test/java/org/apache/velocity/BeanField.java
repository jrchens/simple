package org.apache.velocity;

public class BeanField implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2836342796692691L;

    public BeanField() {
	super();
    }

    public BeanField(String name, String type, Boolean isPrimaryKey) {
	super();
	this.type = type;
	this.name = name;
	this.isPrimaryKey = isPrimaryKey;
    }

    public String type;
    public String name;
    public Boolean isPrimaryKey;

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Boolean getIsPrimaryKey() {
	return isPrimaryKey;
    }

    public void setIsPrimaryKey(Boolean isPrimaryKey) {
	this.isPrimaryKey = isPrimaryKey;
    }
}
