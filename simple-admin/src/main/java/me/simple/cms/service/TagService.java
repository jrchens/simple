package me.simple.cms.service;

import java.util.List;

import me.simple.admin.entity.Pagination;
import me.simple.admin.entity.Tag;

public interface TagService {

    Tag saveTag(Tag tag);

    int removeTag(String tagname);

    /**
     * just update tag viewname
     * 
     * @param tag
     * @return
     */
    int updateTag(Tag tag);

    boolean checkTagExists(String tagname);

    Tag getTag(String tagname);

    /**
     * query by viewname or tagname
     * 
     * @param tag
     * @param pagination
     * @return
     */
    List<Tag> queryTag(Tag tag, Pagination<Tag> pagination);

    List<Tag> queryAll();
}