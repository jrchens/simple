package me.simple.admin.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AttachmentRefRowMapper implements RowMapper<AttachmentRef> {

    @Override
    public AttachmentRef mapRow(ResultSet res, int idx) throws SQLException {
	AttachmentRef obj = new AttachmentRef();
	obj.setAttachId(res.getString("attach_id"));
	obj.setRefId(res.getString("ref_id"));
	obj.setModule(res.getString("module"));
	obj.setEntity(res.getString("entity"));
	obj.setAttr(res.getString("attr"));
	obj.setOwner(res.getString("owner"));
	obj.setSrt(res.getInt("srt"));
	return obj;
    }
    
}
