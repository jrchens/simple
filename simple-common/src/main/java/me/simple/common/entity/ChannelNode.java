package me.simple.common.entity;

import java.util.List;

import com.google.common.collect.Lists;

public class ChannelNode extends Base {
    /**
     * 
     */
    private static final long serialVersionUID = -1436444294677291155L;
    // ===
    private List<ChannelNode> nodes = Lists.newArrayList();
    // ===
    private String text;
    private String href;
    private String name;
    private String parentName;
    public List<ChannelNode> getNodes() {
        return nodes;
    }
    public void setNodes(List<ChannelNode> nodes) {
        this.nodes = nodes;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getHref() {
        return href;
    }
    public void setHref(String href) {
        this.href = href;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getParentName() {
        return parentName;
    }
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    
    
    
}
