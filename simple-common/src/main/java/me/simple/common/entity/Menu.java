package me.simple.common.entity;

public class Menu extends Base {
    /**
     * 
     */
    private static final long serialVersionUID = -4795216611374301646L;
    private String menuname;
    private String viewname;
    private String menuUrl;
    private int grp;
    private int srt;
    public String getMenuname() {
        return menuname;
    }
    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }
    public String getViewname() {
        return viewname;
    }
    public void setViewname(String viewname) {
        this.viewname = viewname;
    }
    public String getMenuUrl() {
        return menuUrl;
    }
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }
    public int getGrp() {
        return grp;
    }
    public void setGrp(int grp) {
        this.grp = grp;
    }
    public int getSrt() {
        return srt;
    }
    public void setSrt(int srt) {
        this.srt = srt;
    }
    
    
}
