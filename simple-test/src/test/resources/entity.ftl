package me.simple.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class ${beaname} extends Base {
    
    /**
     * TODO serialVersionUID
     */
    
    public ${beaname}() {
      super();
    }
    public ${beaname}(${PK.type} ${PK.name}) {
      super();
      this.${PK.name} = ${PK.name};
    }

#foreach( ${field} in ${fields} )
    private ${field.type} ${field.name};
#end

#foreach( ${field} in ${fields} )
    public ${field.type} get${field.name.substring(0,1).toUpperCase()}${field.name.substring(1)}() {
      return ${field.name};
    }
    public void set${field.name.substring(0,1).toUpperCase()}${field.name.substring(1)}(${field.type} ${field.name}) {
      this.${field.name} = ${field.name};
    }
#end
    
}
