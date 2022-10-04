package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Attachment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AttachmentMapper {
    @Select("SELECT * FROM FILES WHERE filename = #{filename} AND userId = #{userId}")
    Attachment getAttachmentInfoByFileName(String filename, Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = ${fileId}")
    Attachment getAttachmentByFileId(Integer fileId);

    @Insert("INSERT INTO FILES (fileId, filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileId}, #{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer saveAttachment(Attachment attachment);

    @Select("SELECT * FROM FILES WHERE userId = ${userId}")
    List<Attachment> getAttachmentsByUserId(Integer userId);

    @Delete("DELETE FROM FILES WHERE fileId = ${fileId}")
    int deleteAttachmentByFileId(Integer fileId);
}
