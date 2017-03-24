package me.simple.common.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.poi.util.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.simpleimage.ImageFormat;
import com.alibaba.simpleimage.ImageWrapper;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WriteRender;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import me.simple.common.service.AttachmentService;
import me.simple.commons.entity.Attachment;
import me.simple.commons.entity.AttachmentRef;
import me.simple.util.Constants;
import me.simple.util.PathUtil;
import me.simple.util.SqlUtil;

@Service
@Transactional
public class AttachmentServiceImpl implements AttachmentService {

    @Value("${upload.base.dir}")
    public String uploadBaseDir;
    @Value("${upload.base.domain}")
    public String uploadBaseDomain;
    @Value("${product.image.compression.size}")
    public String productImageCompressionSize;
    @Value("${image.compression.size}")
    public String imageCompressionSize;

    List<String> MODULES = Lists.newArrayList("product");
    private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert attachmentInsert;
    private SimpleJdbcInsert attachmentRefInsert;
    private static final String attachmentableName = "comm_attachment";
    private static final String[] attachmentColumnNames = { "attach_id", "original_attach_id", "attach_original_name",
	    "attach_path", "attach_name", "attach_size", "attach_type", "attach_desc", "attach_sha1", "image_width",
	    "image_height", "cruser", "crtime" };
    private static final String attachmentRefTableName = "comm_attachment_ref";
    private static final String[] attachmentRefColumnNames = { "ref_id", "attach_id", "module", "entity", "attr",
	    "owner", "srt" };

    @Autowired
    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
	this.attachmentInsert = new SimpleJdbcInsert(dataSource);
	attachmentInsert.withTableName(attachmentableName);
	attachmentInsert.usingColumns(attachmentColumnNames);
	this.attachmentRefInsert = new SimpleJdbcInsert(dataSource);
	attachmentRefInsert.withTableName(attachmentRefTableName);
	attachmentRefInsert.usingColumns(attachmentRefColumnNames);
    }

    @Override
    public Attachment saveAttachment(Attachment attachment) {
	logger.info("me.simple.admin.service.impl.AttachmentServiceImpl.saveAttachment");
	try {

	    String owner = (String) SecurityUtils.getSubject().getPrincipal();
	    Timestamp now = new Timestamp(System.currentTimeMillis());
	    String module = attachment.getModule();
	    String entity = attachment.getEntity();
	    String attr = attachment.getAttr();

	    MultipartFile[] files = attachment.getFiles();
	    List<String> refIds = Lists.newArrayList();
	    AttachmentRef attachmentRef = new AttachmentRef();

	    
	    if (MODULES.contains(module)) {
		int srt = 0;
		for (MultipartFile file : files) {
		    if (file.isEmpty()) {
			continue;
		    }

		    String attachOriginalName = file.getOriginalFilename();
		    String attachPath = uploadBaseDir + PathUtil.getYearMonth() + PathUtil.URL_SEPARATOR + module
			    + PathUtil.URL_SEPARATOR + entity;
		    String attachName = String.valueOf(SqlUtil.uuid()).concat(".")
			    .concat(Files.getFileExtension(attachOriginalName));
		    File dest = new File(attachPath, attachName);
		    Files.createParentDirs(dest);

		    long attachSize = file.getSize();
		    String attachType = file.getContentType();
		    String attachDesc = "";
		    String attachSha1 = null; // Hashing.sha1().hashBytes(input).toString();
		    int imageWidth = 0;
		    int imageHeight = 0;
		    String originalAttachId = null;

		    // TODO file sha1 query

		    String attachId = SqlUtil.uuid();
		    attachment.setAttachId(attachId);
		    attachment.setOriginalAttachId(originalAttachId);
		    attachment.setAttachOriginalName(attachOriginalName);
		    attachment.setAttachPath(attachPath);
		    attachment.setAttachName(attachName);
		    attachment.setAttachSize(attachSize);
		    attachment.setAttachType(attachType);
		    attachment.setAttachDesc(attachDesc);
		    attachment.setAttachSha1(attachSha1);
		    attachment.setImageWidth(imageWidth);
		    attachment.setImageHeight(imageHeight);
		    attachment.setCruser(owner);
		    attachment.setCrtime(now);
		    attachmentInsert.execute(new BeanPropertySqlParameterSource(attachment));
		    // StringBuffer insertSql = new StringBuffer();
		    // insertSql.append(
		    // "insert into comm_attachment ( attach_id, crtime ,
		    // attach_type , attach_path , attach_name , ");
		    // insertSql.append("image_height , attach_size , cruser ,
		    // original_attach_id , image_width , ");
		    // insertSql.append("attach_sha1 , attach_original_name ,
		    // attach_desc , deleted ) values ");
		    // insertSql.append("( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
		    // ?, ?) ");

		    String refId = SqlUtil.uuid();
		    attachmentRef.setRefId(refId);
		    attachmentRef.setAttachId(attachId);
		    attachmentRef.setModule(module);
		    attachmentRef.setEntity(entity);
		    attachmentRef.setAttr(attr);
		    attachmentRef.setOwner(owner);
		    attachmentRef.setSrt(++srt);
		    attachmentRefInsert.execute(new BeanPropertySqlParameterSource(attachmentRef));

		    // jdbcTemplate.update(
		    // "insert into
		    // comm_attachment_ref(ref_id,attach_id,module,entity,attr,owner,srt)
		    // values(?,?,?,?,?,?,?)",
		    // refId, attachId, module, entity, attr, owner, ++srt);

		    refIds.add(refId);

		    // FileCopyUtils.copy(input, dest);
		    file.transferTo(dest);

		    processImageCompression(attachment,attachmentRef,refIds);
		    
		}
		attachment.setRefIds(refIds);
	    } else {
		throw new IllegalArgumentException("module unsupport");
	    }

	} catch (IOException e) {
	    throw new RuntimeException("upload io error", e);
	} catch (Exception e) {
	    throw e;
	}

	return attachment;
    }

    @Override
    public int removeAttachment(String refId) {
	logger.info("me.simple.admin.service.impl.AttachmentServiceImpl.removeAttachment");
	return jdbcTemplate.update("delete from comm_attachment_ref where ref_id = ?", refId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> queryAttachment(Attachment attachment) {
	logger.info("me.simple.admin.service.impl.AttachmentServiceImpl.queryAttachment");

	Subject subject = SecurityUtils.getSubject();
	String owner = (String) subject.getPrincipal();
	String module = attachment.getModule();
	String entity = attachment.getEntity();
	String attr = attachment.getAttr();
	List<String> refIds = attachment.getRefIds();

	List<Object> args = Lists.newArrayList();

	StringBuffer querySql = new StringBuffer();
	querySql.append(
		"select car.ref_id as `key`, ca.attach_type as filetype, ca.attach_size as size, ca.attach_original_name as caption,ca.attach_path,ca.attach_name from comm_attachment_ref car left join comm_attachment ca on car.attach_id = ca.attach_id where 1 = 1  ");
	if (!subject.hasRole(Constants.ROLE_SYS_ADMIN)) {
	    querySql.append("and car.owner = ? ");
	    args.add(owner);
	}
	if (StringUtils.hasText(module)) {
	    querySql.append("and car.module = ? ");
	    args.add(module);
	}
	if (StringUtils.hasText(attr)) {
	    querySql.append("and car.attr = ? ");
	    args.add(attr);
	}
	if (StringUtils.hasText(entity)) {
	    querySql.append("and car.entity = ? ");
	    args.add(entity);
	}
	if (refIds != null && !refIds.isEmpty()) {
	    String ids = Strings.repeat("?,", refIds.size());
	    querySql.append("and car.ref_id in ( ");
	    querySql.append(ids.substring(0, ids.length() - 1));
	    querySql.append(") ");
	    args.addAll(refIds);
	}
	querySql.append("order by ca.attach_name , ca.attach_size desc ");

	List<Map<String, Object>> list = jdbcTemplate.queryForList(querySql.toString(), args.toArray());
	for (Map<String, Object> row : list) {
	    String path = ObjectUtils.getDisplayString(row.get("attach_path"));
	    String name = ObjectUtils.getDisplayString(row.get("attach_name"));
	    row.remove("attach_path");
	    row.remove("attach_name");

	    path = path.replaceAll("\\\\", PathUtil.URL_SEPARATOR);
	    path = path.replace(uploadBaseDir, "");
	    row.put("url", uploadBaseDomain + path + PathUtil.URL_SEPARATOR + name);
	}
	return list;

    }

    
    private void processImageCompression(Attachment attach, AttachmentRef ref, List<String> refIdList) {
	logger.info("me.simple.common.service.impl.AttachmentServiceImpl.processImageCompression");

	String filetype = ObjectUtils.getDisplayString(attach.getAttachType());
	String module = ref.getModule();
	String attachId = attach.getAttachId();
	String dir = attach.getAttachPath();
	String name = attach.getAttachName();
	String ext = Files.getFileExtension(name);

	File dest = null;
	InputStream input = null;
	InputStream inputReadWidthHeight = null;
	InputStream sourceInput = null;
	OutputStream output = null;
	ReadRender readRender = null;
	WriteRender writeRender = null;
	ImageWrapper imageWrapper = null;

	if (!filetype.startsWith("image/") || !MODULES.contains(module)) {
	    return;
	}

	String[] sizes = imageCompressionSize.split(";");
	if ("product".equals(module)) {
	    sizes = productImageCompressionSize.split(";");
	}

	try {
	    dest = new File(dir, name);
	    String sha1 = Files.hash(dest, Hashing.sha1()).toString();
	    
	    sourceInput = new FileInputStream(dest);
	    readRender = new ReadRender(sourceInput);
	    imageWrapper = readRender.render();
	    
	    jdbcTemplate.update(
		    "update comm_attachment set attach_sha1 = ?, image_width = ?, image_height = ? where deleted = 0 and attach_id = ?",
		    sha1, imageWrapper.getWidth(), imageWrapper.getHeight(), attachId);
	    readRender.dispose();

	    for (String size : sizes) {
		try {

		    input = new FileInputStream(new File(dir, name));

		    String[] widthHeight = size.split("x");
		    int width = Integer.parseInt(widthHeight[0]);
		    int height = Integer.parseInt(widthHeight[1]);

		    // ScaleParameter param = new ScaleParameter(80,80);
		    ScaleRender scaleRender = new ScaleRender(input, new ScaleParameter(width, height));
		    // ImageWrapper iw = scaleRender.render();

		    dest = new File(dir + PathUtil.URL_SEPARATOR + size, name);
		    Files.createParentDirs(dest);

		    output = new FileOutputStream(dest);
		    // WriteParameter param = new WriteParameter();
		    ImageFormat format = ImageFormat.getImageFormat(ext);
		    if (format == ImageFormat.UNKNOWN) {
			throw new SimpleImageException("ImageFormat.UNKNOWN");
		    }
		    writeRender = new WriteRender(scaleRender, output,
			    format /* ,new WriteParameter() */);

		    writeRender.render();
		    // writeRender.dispose();

		    inputReadWidthHeight = new FileInputStream(dest);
		    readRender = new ReadRender(inputReadWidthHeight);
		    imageWrapper = readRender.render();

		    Attachment attachment = new Attachment();
		    String _attachId = SqlUtil.uuid();
		    attachment.setAttachId(_attachId);
		    attachment.setOriginalAttachId(null);
		    attachment.setAttachOriginalName(attach.getAttachOriginalName());
		    attachment.setAttachPath(dir + PathUtil.URL_SEPARATOR + size);
		    attachment.setAttachName(attach.getAttachName());
		    attachment.setAttachSize(dest.length());
		    attachment.setAttachType(attach.getAttachType());
		    attachment.setAttachDesc(attach.getAttachDesc());
		    attachment.setAttachSha1(Files.hash(dest, Hashing.sha1()).toString());
		    attachment.setImageWidth(imageWrapper.getWidth());
		    attachment.setImageHeight(imageWrapper.getHeight());
		    attachment.setCruser(attach.getCruser());
		    attachment.setCrtime(attach.getCrtime());
		    attachmentInsert.execute(new BeanPropertySqlParameterSource(attachment));

		    AttachmentRef attachmentRef = new AttachmentRef();
		    String refId = SqlUtil.uuid();
		    attachmentRef.setRefId(refId);
		    attachmentRef.setAttachId(_attachId);
		    attachmentRef.setModule(module);
		    attachmentRef.setEntity(ref.getEntity());
		    attachmentRef.setAttr(ref.getAttr());
		    attachmentRef.setOwner(ref.getOwner());
		    attachmentRef.setSrt(ref.getSrt());
		    attachmentRefInsert.execute(new BeanPropertySqlParameterSource(attachmentRef));

		    refIdList.add(refId);
		} finally {
		    IOUtils.closeQuietly(input);
		    IOUtils.closeQuietly(output);
		    writeRender.dispose();
		    IOUtils.closeQuietly(inputReadWidthHeight);
		}
	    }
	} catch (SimpleImageException e) {
	    throw new RuntimeException("image compression simple image error", e);
	} catch (IOException e) {
	    throw new RuntimeException("image compression io error", e);
	} catch (Exception e) {
	    throw new RuntimeException("image compression error", e);
	} finally {
	    IOUtils.closeQuietly(sourceInput);
	}

    }
}
