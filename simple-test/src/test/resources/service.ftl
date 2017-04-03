package ${package}.service;

import java.util.List;

import me.simple.entity.${beaname};

public interface ${beaname}Service {
    public int save(${beaname} ${beaname.substring(0,1).toUpperCase()}${beaname.substring(1)});

    public int delete(${beaname} ${beaname.substring(0,1).toUpperCase()}${beaname.substring(1)});

    public int update(${beaname} ${beaname.substring(0,1).toUpperCase()}${beaname.substring(1)});

    public ${beaname} get(${PK.type} ${PK.name});

    public List<${beaname}> query(${beaname} ${beaname.substring(0,1).toUpperCase()}${beaname.substring(1)});
}
