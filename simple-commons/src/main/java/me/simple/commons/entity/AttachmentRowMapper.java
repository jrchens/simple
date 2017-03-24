package me.simple.commons.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AttachmentRowMapper implements RowMapper<Attachment> {

    @Override
    public Attachment mapRow(ResultSet res, int idx) throws SQLException {
	Attachment obj = new Attachment();
	obj.setAttachId(res.getString("attach_id"));
	obj.setOriginalAttachId(res.getString("original_attach_id"));
	obj.setAttachOriginalName(res.getString("attach_original_name"));
	obj.setAttachPath(res.getString("attach_path"));
	obj.setAttachName(res.getString("attach_name"));
	obj.setAttachSize(res.getLong("attach_size"));
	obj.setAttachType(res.getString("attach_type"));
	obj.setAttachDesc(res.getString("attach_desc"));
	obj.setAttachSha1(res.getString("attach_sha1"));
	obj.setImageWidth(res.getInt("image_width"));
	obj.setImageHeight(res.getInt("image_height"));
	obj.setCruser(res.getString("cruser"));
	obj.setCrtime(res.getTimestamp("crtime"));
	obj.setMduser(res.getString("mduser"));
	obj.setMdtime(res.getTimestamp("mdtime"));
	return obj;
    }
    
}
